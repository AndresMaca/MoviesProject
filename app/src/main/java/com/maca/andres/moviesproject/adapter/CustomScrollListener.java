package com.maca.andres.moviesproject.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.maca.andres.moviesproject.devutils.LoggerDebug;

public abstract class CustomScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager linearLayoutManager;
    private static final String TAG = CustomScrollListener.class.getSimpleName();

    public CustomScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = linearLayoutManager.getChildCount();
        int totalItemCount = linearLayoutManager.getItemCount();
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                LoggerDebug.print(TAG, "Last page ");
                loadMoreItems();
            }

        }
    }
    public abstract int getTotalPageCount();


    public abstract boolean isLoading();

    protected abstract void loadMoreItems();


    public abstract boolean isLastPage();

}
