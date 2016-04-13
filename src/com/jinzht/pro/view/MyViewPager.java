package com.jinzht.pro.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import com.jinzht.pro.callback.ViewpagerDisplayInterface;

/**
 * 代码有问题别找我！虽然是我写的，但是他们自己长歪了。
 *
 * @auth Mr.Jobs
 * @date 2016/1/25
 * @time 11:47
 */
public class MyViewPager extends ViewPager implements ViewpagerDisplayInterface {
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDisplay(int position) {
    }

}
