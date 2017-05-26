package com.seakernel.android.yahooweather;

import com.seakernel.android.yahooweather.model.YahooResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Calvin on 5/26/17.
 * Copyright © 2017 SeaKernel. All rights reserved.
 */

public class NetworkHelper {

    private static final String BASE_URL = "https://query.yahooapis.com";
    private static final String QUERY_BASE = "v1/public/yql";
    private static final String QUERY = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"nome, ak\")";

    private static final Retrofit mRetrofit;

    static {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void getWeather(Callback<YahooResponse> callback) {
        final WeatherForecastService service = mRetrofit.create(WeatherForecastService.class);
        final Call<YahooResponse> weather = service.getWeather(QUERY, "json");
        weather.enqueue(callback);
    }

    interface WeatherForecastService {
        @GET(QUERY_BASE)
        Call<YahooResponse> getWeather(@Query("q") String query, @Query("format") String json);
    }
}
