package com.seakernel.android.yahooweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Calvin on 5/26/17.
 * Copyright © 2017 SeaKernel. All rights reserved.
 */

public class YahooResponse {
    @SerializedName("query")
    @Expose
    private YahooQuery mQuery;

    public YahooQuery getQuery() {
        return mQuery;
    }

    public void setQuery(YahooQuery query) {
        mQuery = query;
    }
}
