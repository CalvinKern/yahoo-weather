package com.seakernel.android.yahooweather;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seakernel.android.yahooweather.model.YahooResponse;
import com.seakernel.android.yahooweather.network.NetworkHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastActivity extends AppCompatActivity implements Callback<YahooResponse>, TextView.OnEditorActionListener {

    private ForecastRecyclerAdapter mAdapter;
    private EditText mSearchView;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        // Set the toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // Allow user to specify location
        mSearchView = (EditText) toolbar.findViewById(R.id.main_search);
        mSearchView.setOnEditorActionListener(this);

        // Create recycler adapter
        mAdapter = new ForecastRecyclerAdapter(null);

        // Set up recycler view
        final RecyclerView recycler = (RecyclerView) findViewById(R.id.main_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mAdapter = null; // Clear reference to adapter

        if (mSearchView != null) {
            mSearchView.setOnEditorActionListener(null);
            mSearchView = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load weather whenever we resume to get the most up to date forecast
        showLoadingDialog();
        NetworkHelper.getWeather(this);
    }

    @Override
    public void onResponse(@NonNull final Call<YahooResponse> call, @NonNull final Response<YahooResponse> response) {
        // Load weather views
        final YahooResponse yahooResponse = response.body();
        if (yahooResponse != null) {
            try {
                mAdapter.updateForecasts(yahooResponse.getQuery().getResult().getChannel().getItem().getForecasts());
            } catch (final Exception e) {
                // We had an error parsing somewhere down the line, so show that we failed to load
                onFailure(call, e);
            }
        } else {
            mAdapter.updateForecasts(null);
        }

        hideLoadingDialog();
    }

    @Override
    public void onFailure(@NonNull final Call<YahooResponse> call, @NonNull final Throwable t) {
        // Report that the weather failed to load
        Toast.makeText(this, R.string.failed_to_load, Toast.LENGTH_LONG).show();
        mAdapter.updateForecasts(null); // Clear the forecasts so we show the right thing to the user

        hideLoadingDialog();
    }

    @Override
    public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
            showLoadingDialog();
            NetworkHelper.getWeather(this, v.getText().toString());
            return true;
        }
        return false;
    }

    /**
     * Show a loading dialog (that is cancelable) informing the user we are loading data
     */
    private void showLoadingDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setTitle(R.string.loading_weather);
            mDialog.setIndeterminate(true);
        }

        mDialog.show();
    }

    private void hideLoadingDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
