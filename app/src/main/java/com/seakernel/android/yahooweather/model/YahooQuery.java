package com.seakernel.android.yahooweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Calvin on 5/26/17.
 * Copyright © 2017 SeaKernel. All rights reserved.
 */

public class YahooQuery {
    @SerializedName("results")
    @Expose
    private YahooResult mResult;

    public YahooResult getResult() {
        return mResult;
    }

    public void setResult(YahooResult result) {
        mResult = result;
    }
}
