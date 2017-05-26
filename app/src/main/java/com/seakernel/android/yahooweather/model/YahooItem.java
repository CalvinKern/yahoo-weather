package com.seakernel.android.yahooweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Calvin on 5/26/17.
 * Copyright © 2017 SeaKernel. All rights reserved.
 */

public class YahooItem {
    @SerializedName("forecast")
    @Expose
    private List<Forecast> mForecasts;

    @SerializedName("title")
    @Expose
    private String mTitle;

    public List<Forecast> getForecasts() {
        return mForecasts;
    }

    public void setForecasts(final List<Forecast> forecasts) {
        mForecasts = forecasts;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(final String title) {
        mTitle = title;
    }
}
