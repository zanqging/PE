package com.jinzht.pro.activity;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.fragment.MyCreateProjectFragment;
import com.jinzht.pro.fragment.MyInvestFragment;
import com.jinzht.pro.model.AuthBean;
import com.jinzht.pro.smarttablayout.SmartTabLayout;
import com.jinzht.pro.smarttablayout.v4.FragmentPagerItem;
import com.jinzht.pro.smarttablayout.v4.FragmentPagerItemAdapter;
import com.jinzht.pro.smarttablayout.v4.FragmentPagerItems;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.StringUtils;
import com.jinzht.pro.utils.UiHelp;

import java.io.IOException;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * 我的投融资页面
 *
 * @auther Mr Jobs
 * @date 2015/8/2
 * @time 23:41
 */
public class MyFinacingInvestActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager invest_vp;
    private LinearLayout back;
    private TextView titile;
    private SmartTabLayout tl_invest;// ViewPager的页签

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_my_finacing_invest);
        UiHelp.setTranslucentStatus(true, MyFinacingInvestActivity.this);
        init();
    }

    private void init() {
        invest_vp = (ViewPager) findViewById(R.id.invest_vp);
        tl_invest = (SmartTabLayout) findViewById(R.id.tl_invest);
        back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(this);
        titile = (TextView) findViewById(R.id.title);
        titile.setText(getResources().getString(R.string.my_invest_finacing));
        RippleUtils.rippleNormal(back);
        IsInvestTask isInvestTask = new IsInvestTask();
        isInvestTask.execute();
    }

    // 查询是否是投资人，展示fragment
    private class IsInvestTask extends AsyncTask<Void, Void, AuthBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected AuthBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(MyFinacingInvestActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.ISINVESTOR, MyFinacingInvestActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("投资人信息", body);
                return FastJsonTools.getBean(body, AuthBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(AuthBean common) {
            super.onPostExecute(common);
            if (common != null) {
                if (common.getCode() == -1) {// -1应该是没登录吧，他写错了？
                    FragmentPagerItems pages = new FragmentPagerItems(MyFinacingInvestActivity.this);
                    String[] item = getResources().getStringArray(R.array.invest_more);
                    for (int i = 0; i < 2; i++) {
                        if (i == 0) {
                            pages.add(FragmentPagerItem.of(item[i], MyInvestFragment.class));
                        } else {
                            pages.add(FragmentPagerItem.of(item[i], MyCreateProjectFragment.class));
                        }
                    }
                    FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
                    invest_vp.setOffscreenPageLimit(2);
                    invest_vp.setAdapter(adapter);
                    tl_invest.setViewPager(invest_vp);
                    if (getIntent().getExtras() != null && getIntent().getStringExtra("flag").equals("flag")) {
                        invest_vp.setCurrentItem(1);
                    }
                    return;
                }
                if (common.getCode() == 0) {
                    FragmentPagerItems pages = new FragmentPagerItems(MyFinacingInvestActivity.this);
                    String[] item = getResources().getStringArray(R.array.invest_more);
                    for (int i = 0; i < 2; i++) {
                        if (i == 0) {
                            pages.add(FragmentPagerItem.of(item[i], MyInvestFragment.class));
                        } else {
                            pages.add(FragmentPagerItem.of(item[i], MyCreateProjectFragment.class));
                        }
                    }
                    FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
                    invest_vp.setOffscreenPageLimit(2);
                    invest_vp.setAdapter(adapter);
                    tl_invest.setViewPager(invest_vp);
                    if (!StringUtils.isEquals(common.getData().getAuth() + "", "true")) {
                        invest_vp.setCurrentItem(0);
                    }
                    // 从上传项目跳到我上传的项目
                    if (getIntent().getExtras() != null && getIntent().getStringExtra("flag").equals("flag")) {
                        invest_vp.setCurrentItem(1);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}