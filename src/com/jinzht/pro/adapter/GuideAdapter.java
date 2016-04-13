package com.jinzht.pro.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 * 引导页的ViewPager填充器
 *
 * @auther Mr Jobs
 * @date 2015/5/17
 * @time 13:37
 */
public class GuideAdapter extends PagerAdapter {
    //界面列表
    private ArrayList<View> views;

    public GuideAdapter(ArrayList<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        else return 0;
    }

    /**
     * 判断是否由对象生成界面
     */
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(views.get(position), 0);
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }
}
