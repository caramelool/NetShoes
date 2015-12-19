package br.com.netshoes.utils;

import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;

public class EndlessScroll implements AbsListView.OnScrollListener {

    private int mCurrentPage = 0;
    private int mPreviousTotal = 0;
    private boolean mLoading = true;
    private Listener mListener;

    public EndlessScroll(AppCompatActivity activity) {
        mListener = (Listener) activity;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
                mCurrentPage++;
            }
        }

        if (!mLoading && totalItemCount <= (firstVisibleItem+5)) {
            mListener.onLoadMore(mCurrentPage + 1);
            mLoading = true;
        }
    }

    /**
     * Reseta EndlessScroll
     */
    public void reset() {
        mCurrentPage = 0;
        mPreviousTotal = 0;
        mLoading = true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public interface Listener {
        void onLoadMore(int page);
    }
}