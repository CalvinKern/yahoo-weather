package com.seakernel.android.yahooweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.seakernel.android.yahooweather.model.YahooResponse;
import com.seakernel.android.yahooweather.network.NetworkHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastActivity extends AppCompatActivity implements Callback<YahooResponse> {

    private ForecastRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

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
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Load weather whenever we resume to get the most up to date forecast
        NetworkHelper.getWeather(this);
    }

    @Override
    public void onResponse(@NonNull final Call<YahooResponse> call, @NonNull final Response<YahooResponse> response) {
        // Load weather views
        final YahooResponse yahooResponse = response.body();
        if (yahooResponse != null) {
            mAdapter.updateForecasts(yahooResponse.getQuery().getResult().getChannel().getItem().getForecasts());
        } else {
            mAdapter.updateForecasts(null);
        }
    }

    @Override
    public void onFailure(@NonNull final Call<YahooResponse> call, @NonNull final Throwable t) {
        // Report that the weather failed to load
        Toast.makeText(this, R.string.failed_to_load, Toast.LENGTH_LONG).show();
        mAdapter.updateForecasts(null); // Clear the forecasts so we show the right thing to the user
    }
}
