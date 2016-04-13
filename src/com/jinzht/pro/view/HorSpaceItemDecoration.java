package com.jinzht.pro.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/1/11.
 */
public class HorSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public HorSpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        if(parent.getChildAdapterPosition(view) != 0)
            outRect.right = space;
    }
}
