package com.seakernel.android.yahooweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Calvin on 5/26/17.
 * Copyright © 2017 SeaKernel. All rights reserved.
 */

public class YahooChannel {
    @SerializedName("item")
    @Expose
    private YahooItem mItem;

    public YahooItem getItem() {
        return mItem;
    }

    public void setItem(YahooItem item) {
        mItem = item;
    }
}
