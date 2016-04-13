package com.jinzht.pro.view;

import android.view.View;
import android.widget.AbsListView;
import com.jinzht.pro.callback.ListViewOnBottomListener;

/**
 * Created by Administrator on 2015/11/21.
 */
public class ListviewBottomListener implements AbsListView.OnScrollListener  {
    private ListViewOnBottomListener mListviewBottomListener =null;

    public ListviewBottomListener(ListViewOnBottomListener mListviewBottomListener) {
        this.mListviewBottomListener = mListviewBottomListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            // 当不滚动时
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                // 判断滚动到底部
                if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                    mListviewBottomListener.onBottom();
                }
                break;
        }
    }

    @Override
    public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        int lastItem = firstVisibleItem + visibleItemCount;
//        if(lastItem == totalItemCount) {
//            System.out.println("Scroll to the listview last item");
//            View lastItemView=(View) listView.getChildAt(listView.getChildCount()-1);
//            if ((listView.getBottom())==lastItemView.getBottom()) {
//                mListviewBottomListener.onBottom();
//            }
//        }
    }

}
