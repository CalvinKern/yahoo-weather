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

    // Use an IntDef for the different view types
    @IntDef({ViewType.NONE, ViewType.LOADING, ViewType.FORECAST})
    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {
        int NONE = 0;
        int LOADING = 1;
        int FORECAST = 2;
    }

    private boolean mIsLoading;
    private List<Forecast> mForecasts; // Keep track of our forecasts

    ForecastRecyclerAdapter(final List<Forecast> forecasts) {
        super();
        updateForecasts(forecasts); // Copy over the forecasts
    }

    @Override
    public int getItemViewType(final int position) {
        // Check if we're loading and if the list is empty
        return mIsLoading ? ViewType.LOADING :
                mForecasts.isEmpty() ? ViewType.NONE : ViewType.FORECAST;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder = null;

        // Initialize the holder to the right view holder type
        switch (viewType) {
            case ViewType.NONE: {
                holder = new EmptyForecastViewHolder(inflater.inflate(R.layout.none_forecast_view_holder, parent, false));
                break;
            }
            case ViewType.LOADING: {
                holder = new EmptyForecastViewHolder(inflater.inflate(R.layout.loading_forecast_view_holder, parent, false));
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
        switch (getItemViewType(position)) {
            case ViewType.LOADING:
            case ViewType.NONE: {
                return; // Do nothing for an empty position
            }
            case ViewType.FORECAST: {
                if (mForecasts.size() <= position) {
                    return; // Bad forecast position
                }

                // Update the view holder for the forecast at this position
                ((ForecastViewHolder) holder).bindView(mForecasts.get(position));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        // We want to always have at least 1 item (for an empty/loading state)
        return Math.max(mForecasts.size(), 1);
    }

    /**
     *
     * @param isLoading true if waiting for content to load, false if data has been loaded
     */
    void setIsLoading(final boolean isLoading) {
        mIsLoading = isLoading;
        notifyDataSetChanged();
    }

    /**
     * Update this adapter with the forecasts provided. When null is passed in, the data is cleared
     * and a placeholder is shown to the user (saying that there is no data).
     *
     * Also updates isLoading to false.
     *
     * @param updatedForecasts the list of forecasts to use
     */
    void updateForecasts(final List<Forecast> updatedForecasts) {
        mForecasts = updatedForecasts == null ? new ArrayList<Forecast>(0) : new ArrayList<>(updatedForecasts);
        setIsLoading(false);
    }
}
