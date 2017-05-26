package com.seakernel.android.yahooweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Calvin on 5/26/17.
 * Copyright © 2017 SeaKernel. All rights reserved.
 */

public class Location {
    @SerializedName("city")
    @Expose
    private String mCity;

    @SerializedName("region")
    @Expose
    private String mRegion;

    @SerializedName("country")
    @Expose
    private String mCountry;

    public String getCity() {
        return mCity;
    }

    public void setCity(final String city) {
        mCity = city;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setRegion(final String region) {
        mRegion = region;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(final String country) {
        mCountry = country;
    }

    @Override
    public String toString() {
        return mCity + ", " + mRegion + ", " + mCountry;
    }
}
