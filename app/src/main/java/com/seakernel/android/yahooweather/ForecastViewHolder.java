package com.seakernel.android.yahooweather;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.seakernel.android.yahooweather.model.Forecast;

/**
 * Created by Calvin on 5/26/17.
 * Copyright © 2017 SeaKernel. All rights reserved.
 */

class ForecastViewHolder extends RecyclerView.ViewHolder {

    private final TextView mDateView;
    private final TextView mDayView;
    private final TextView mWeatherView;
    private final TextView mLowView;
    private final TextView mHighView;

    public ForecastViewHolder(final View itemView) {
        super(itemView);

        mDateView = (TextView) itemView.findViewById(R.id.forecast_date);
        mDayView = (TextView) itemView.findViewById(R.id.forecast_day);
        mWeatherView = (TextView) itemView.findViewById(R.id.forecast_weather);
        mLowView = (TextView) itemView.findViewById(R.id.forecast_low);
        mHighView = (TextView) itemView.findViewById(R.id.forecast_high);
    }

    public void bindView(final Forecast forecast) {
        mDateView.setText(forecast.getDate());
        mDayView.setText(forecast.getDay());
        mWeatherView.setText(forecast.getText());
        mLowView.setText(forecast.getLow());
        mHighView.setText(forecast.getHigh());
    }
}
