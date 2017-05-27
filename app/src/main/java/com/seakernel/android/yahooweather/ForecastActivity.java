package com.seakernel.android.yahooweather;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seakernel.android.yahooweather.model.YahooChannel;
import com.seakernel.android.yahooweather.model.YahooItem;
import com.seakernel.android.yahooweather.model.YahooResponse;
import com.seakernel.android.yahooweather.network.NetworkHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastActivity extends AppCompatActivity implements Callback<YahooResponse>, TextView.OnEditorActionListener {

    private static final String KEY_LOCATION = "KEY_LOCATION";

    private ForecastRecyclerAdapter mAdapter;
    private EditText mSearchView;

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
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_LOCATION)) {
            mSearchView.setText(savedInstanceState.getString(KEY_LOCATION));
        }

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
    public void onSaveInstanceState(final Bundle outState, final PersistableBundle outPersistentState) {
        if (mSearchView != null) {
            outState.putString(KEY_LOCATION, mSearchView.getText().toString());
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load weather whenever we resume to get the most up to date forecast
        if (mSearchView != null && mSearchView.getText().length() > 0) {
            loadWeatherData(mSearchView.getText().toString());
        } else {
            loadWeatherData(null);
        }
    }

    @Override
    public void onResponse(@NonNull final Call<YahooResponse> call, @NonNull final Response<YahooResponse> response) {
        // Load weather views
        final YahooResponse yahooResponse = response.body();
        if (yahooResponse != null) {
            try {
                final YahooChannel channel = yahooResponse.getQuery().getResult().getChannel();
                final YahooItem item = channel.getItem();

                // Show location in title bar edit text and update list
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                if (mSearchView != null) {
                    mSearchView.setText(channel.getLocation().toString());
                }
                if (mAdapter != null) {
                    mAdapter.updateForecasts(item.getForecasts());
                }
            } catch (final Exception e) {
                // We had an error parsing somewhere down the line, so show that we failed to load
                onFailure(call, e);
            }
        } else {
            onFailure(call, new Throwable("Invalid response from yahoo: " + response));
        }
    }

    @Override
    public void onFailure(@NonNull final Call<YahooResponse> call, @NonNull final Throwable t) {
        if (mAdapter != null) {
            mAdapter.updateForecasts(null); // Clear the forecasts so we show the right thing to the user
        }
    }

    @Override
    public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
        // When users hits search, find the new location
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            loadWeatherData(v.getText().toString());

            // Close the keyboard
            final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            // Clear focus
            if (mSearchView != null) {
                mSearchView.clearFocus();
            }
            return true;
        }
        return false;
    }

    /**
     * Load the weather data and tell the adapter to update its state
     *
     * @param location the location to load weather for, can be null
     */
    private void loadWeatherData(@Nullable final String location) {
        if (mAdapter != null) {
            mAdapter.setIsLoading(true);
        }
        NetworkHelper.getWeather(this, location);
    }
}
