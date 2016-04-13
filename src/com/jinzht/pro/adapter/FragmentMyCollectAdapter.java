package com.jinzht.pro.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.jinzht.pro.fragment.*;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * @auther Mr Jobs
 * @date 2015/7/26
 * @time 21:19
 */
public class FragmentMyCollectAdapter extends FragmentPagerAdapter {
    public FragmentMyCollectAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int positon) {
        Fragment fragment = null;
        switch (positon) {
            case 0:
                fragment = new CollectRoadShowFragment();
                break;
            case 1:
                fragment = new FinacingFragment();
                break;
            case 2:
                fragment = new CollectHaveFinaceFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
