package com.seakernel.android.yahooweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.seakernel.android.yahooweather.model.YahooResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<YahooResponse> {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkHelper.getWeather(this);
    }

    @Override
    public void onResponse(@NonNull final Call<YahooResponse> call, @NonNull final Response<YahooResponse> response) {
        // TODO: Load weather views
    }

    @Override
    public void onFailure(@NonNull final Call<YahooResponse> call, @NonNull final Throwable t) {
        Toast.makeText(this, "Failed to load weather", Toast.LENGTH_LONG).show();
        // TODO: Show placeholder for no weather
    }
}
