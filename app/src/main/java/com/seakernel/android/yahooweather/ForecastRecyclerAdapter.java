package com.seakernel.android.yahooweather;

import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.seakernel.android.yahooweather.model.Forecast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Calvin on 5/26/17.
 * Copyright © 2017 SeaKernel. All rights reserved.
 */

class ForecastRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @IntDef
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {
        int EMPTY = 0;
        int FORECAST = 1;
    }

    private List<Forecast> mForecasts;

    ForecastRecyclerAdapter(final List<Forecast> forecasts) {
        super();
        updateForecasts(forecasts);
    }

    @Override
    public int getItemViewType(final int position) {
        return mForecasts.isEmpty() ? ViewType.EMPTY : ViewType.FORECAST;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case ViewType.EMPTY: {
                holder = new EmptyForecastViewHolder(inflater.inflate(R.layout.empty_forecast_view_holder, parent, false));
                break;
            }
            case ViewType.FORECAST: {
                holder = new ForecastViewHolder(inflater.inflate(R.layout.forecast_view_holder, parent, false));
                break;
            }
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (mForecasts.size() <= position) {
            return; // Bad forecast position
        }

        switch (getItemViewType(position)) {
            case ViewType.EMPTY: {
                return; // Do nothing
            }
            case ViewType.FORECAST: {
                ((ForecastViewHolder) holder).bindView(mForecasts.get(position));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return Math.max(mForecasts.size(), 1);
    }

    void updateForecasts(final List<Forecast> updatedForecasts) {
        mForecasts = updatedForecasts == null ? new ArrayList<Forecast>(0) : new ArrayList<>(updatedForecasts);
        notifyDataSetChanged();
    }
}
