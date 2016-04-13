package com.jinzht.pro.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.fragment.CollectHaveFinaceFragment;
import com.jinzht.pro.fragment.CollectPreFragment;
import com.jinzht.pro.fragment.CollectRoadShowFragment;
import com.jinzht.pro.fragment.FinacingFragment;
import com.jinzht.pro.smarttablayout.SmartTabLayout;
import com.jinzht.pro.smarttablayout.v4.FragmentPagerItem;
import com.jinzht.pro.smarttablayout.v4.FragmentPagerItemAdapter;
import com.jinzht.pro.smarttablayout.v4.FragmentPagerItems;
import com.jinzht.pro.utils.UiHelp;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * 我的收藏页面
 *
 * @auther Mr Jobs
 * @date 2015/7/26
 * @time 21:02
 */
public class MyCollectActivity extends FragmentActivity {

    private ViewPager viewPager;
    private LinearLayout back;
    private TextView title;
    private SmartTabLayout viewPagerTab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_my_collect);
        init();
        UiHelp.setTranslucentStatus(true, MyCollectActivity.this);
//        FragmentMyCollectAdapter vpAdapter = new FragmentMyCollectAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(vpAdapter);
//        viewPager.setOffscreenPageLimit(3);
    }

    private void init() {
        viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title = (TextView) findViewById(R.id.title);
        title.setText(getResources().getString(R.string.collecting));
        FragmentPagerItems pages = new FragmentPagerItems(MyCollectActivity.this);
        String[] item = getResources().getStringArray(R.array.collect_invest_array);
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                pages.add(FragmentPagerItem.of(item[i], CollectRoadShowFragment.class));
            } else if (i == 1) {
                pages.add(FragmentPagerItem.of(item[i], FinacingFragment.class));
            } else if (i == 2) {
                pages.add(FragmentPagerItem.of(item[i], CollectHaveFinaceFragment.class));
            } else {
                pages.add(FragmentPagerItem.of(item[i], CollectPreFragment.class));
            }
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
    }
}