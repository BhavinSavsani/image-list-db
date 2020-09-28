package com.bhavinpracticalcavista.utils;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {


    /**
     * Visible thresold
     */
    private int visibleThreshold = 10;
    /**
     * The total number of items in the dataset after the last load
     */
    private int mPreviousTotal = 0;
    /**
     * True if we are still waiting for the last set of data to load.
     */
    private boolean mLoading = true;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (mLoading && dy > 0) {
            if (totalItemCount>0&& totalItemCount > mPreviousTotal) {
                Log.i("point a", "m" + mLoading + " " + visibleItemCount + " " + " " + totalItemCount
                        + " " + firstVisibleItem);

           //     mLoading = false;
                mPreviousTotal = totalItemCount;

            }
        }


        if (!mLoading && (totalItemCount)
                <= (firstVisibleItem + visibleThreshold + visibleItemCount)) {
            // End has been reached
            Log.i("point b", "m" + mLoading + " " + visibleItemCount + " " + " " + totalItemCount
                    + " " + firstVisibleItem);
            onLoadMore();
            mLoading = true;
        }

    }

    public void setLoading() {
        mLoading = false;
    }

    public void setLoaded() {
        mPreviousTotal = 0;
        mLoading = false;
    }

    public abstract void onLoadMore();
}