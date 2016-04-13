package com.jinzht.pro.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.jinzht.pro.fragment.*;

public class MainViewPagerFragmentAdapter extends FragmentPagerAdapter {

	public MainViewPagerFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int positon) {
		Fragment fragment = null;
		switch (positon) {
		case 0:
			fragment = new HomePageFragment();
			break;
		case 1:
			fragment = new InvestAndFinancingFragment();
			break;
		case 2:
			fragment = new ThreeBoardFragment();
			break;
		case 3:
			fragment = new CircleFragment();
			break;
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 4;
	}

}
