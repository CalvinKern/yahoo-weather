package com.seakernel.android.yahooweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Calvin on 5/26/17.
 * Copyright © 2017 SeaKernel. All rights reserved.
 */

public class YahooResult {
    @SerializedName("channel")
    @Expose
    private YahooChannel mChannel;

    public YahooChannel getChannel() {
        return mChannel;
    }

    public void setChannel(YahooChannel channel) {
        mChannel = channel;
    }
}
