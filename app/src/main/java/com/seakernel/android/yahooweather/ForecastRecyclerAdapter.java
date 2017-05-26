package com.seakernel.android.yahooweather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.seakernel.android.yahooweather.model.Forecast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Calvin on 5/26/17.
 * Copyright © 2017 SeaKernel. All rights reserved.
 */

class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastViewHolder> {

    private List<Forecast> mForecasts;

    ForecastRecyclerAdapter(final List<Forecast> forecasts) {
        super();
        updateForecasts(forecasts);
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ForecastViewHolder(inflater.inflate(R.layout.forecast_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(final ForecastViewHolder holder, final int position) {
        if (mForecasts.size() <= position) {
            return; // Bad forecast position
        }

        holder.bindView(mForecasts.get(position));
    }

    @Override
    public int getItemCount() {
        return mForecasts.size();
    }

    void updateForecasts(final List<Forecast> updatedForecasts) {
        mForecasts = updatedForecasts == null ? new ArrayList<Forecast>(10) : new ArrayList<>(updatedForecasts);
        notifyDataSetChanged();
    }
}
