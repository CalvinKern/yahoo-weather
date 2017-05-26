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

    @SerializedName("location")
    @Expose
    private Location mLocation;

    public YahooItem getItem() {
        return mItem;
    }

    public void setItem(final YahooItem item) {
        mItem = item;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(final Location location) {
        mLocation = location;
    }
}
