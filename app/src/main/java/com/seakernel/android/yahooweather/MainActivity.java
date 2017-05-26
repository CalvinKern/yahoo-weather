package com.seakernel.android.yahooweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.seakernel.android.yahooweather.model.YahooResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<YahooResponse> {

    private ForecastRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new ForecastRecyclerAdapter(null);

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
        Toast.makeText(this, "Failed to load weather", Toast.LENGTH_LONG).show();
        // TODO: Show placeholder for no weather
    }
}
