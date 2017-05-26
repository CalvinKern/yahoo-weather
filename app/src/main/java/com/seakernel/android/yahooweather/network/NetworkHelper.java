package com.seakernel.android.yahooweather.network;

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
public final class NetworkHelper {

    private static final String DATA_FORMAT = "json";

    private static final String BASE_URL = "https://query.yahooapis.com";
    private static final String QUERY_BASE = "v1/public/yql";
    private static final String QUERY = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")";
    private static final String DEFAULT_LOCATION = "nome, ak";

    private static final Retrofit mRetrofit;

    // Static constructor to initialize retrofit
    static {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * A call to load the weather. Defaults to "nome, ak" when no location specified.
     *
     * @param callback the callback to give the response to
     */
    public static void getWeather(final Callback<YahooResponse> callback) {
        getWeather(callback, DEFAULT_LOCATION);
    }

    public static void getWeather(final Callback<YahooResponse> callback, final String location) {
        final WeatherForecastService service = mRetrofit.create(WeatherForecastService.class);
        final Call<YahooResponse> weather = service.getWeather(String.format(QUERY, location), DATA_FORMAT);
        weather.enqueue(callback);
    }

    /**
     * Used to make a RESTful API request with Retrofit
     */
    interface WeatherForecastService {
        @GET(QUERY_BASE)
        Call<YahooResponse> getWeather(@Query("q") String query, @Query("format") String format);
    }
}
