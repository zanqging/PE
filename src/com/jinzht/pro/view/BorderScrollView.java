package com.jinzht.pro.view;

import android.content.Context;
import android.support.v4.view.ScrollingView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/25
 * @time 13:41
 */

public class BorderScrollView extends ScrollView {
    private OnBorderListener onBorderListener;
    private View contentView;

    public BorderScrollView(Context context) {
        super(context);
    }

    public BorderScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BorderScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        doOnBorderListener();
    }

    public void setOnBorderListener(final OnBorderListener onBorderListener) {
        this.onBorderListener = onBorderListener;
        if (onBorderListener == null) {
            return;
        }

        if (contentView == null) {
            contentView = getChildAt(0);
        }
    }

    /**
     * OnBorderListener, Called when scroll to top or bottom
     *
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-22
     */
    public static interface OnBorderListener {

        /**
         * Called when scroll to bottom
         */
        public void onBottom();

        /**
         * Called when scroll to top
         */
        public void onTop();
    }

    private void doOnBorderListener() {
        if (contentView != null && contentView.getMeasuredHeight() <= getScrollY() + getHeight()) {
            if (onBorderListener != null) {
                onBorderListener.onBottom();
            }
        } else if (getScrollY() == 0) {
            if (onBorderListener != null) {
                onBorderListener.onTop();
            }
        }
    }
}
