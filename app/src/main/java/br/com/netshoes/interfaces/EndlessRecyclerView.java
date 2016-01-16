package br.com.netshoes.interfaces;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerView extends RecyclerView.OnScrollListener {

    private int mCurrentPage = 0;
    boolean mLoading = true;
    int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal, visibleThreshold;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if (mLoading) {
            if (totalItemCount > previousTotal) {
                mLoading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!mLoading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            mCurrentPage++;

            onLoad(mCurrentPage);

            mLoading = true;
        }
    }

    public abstract void onLoad(int page);

    /**
     * Reseta EndlessScroll
     */
    public void reset() {
        mCurrentPage = 0;
        previousTotal = 0;
        mLoading = true;
    }
}