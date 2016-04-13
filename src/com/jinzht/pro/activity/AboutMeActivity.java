package com.jinzht.pro.activity;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.eventbus.NoticeDissmissEvent;
import com.jinzht.pro.eventbus.NoticeEvent;
import com.jinzht.pro.fragment.AuthLookFragment;
import com.jinzht.pro.fragment.ReplyMessageFragment;
import com.jinzht.pro.model.MsgCountBean;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.UiHelp;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 * <p>
 * 与我相关页面，可显示是否有与我相关的消息
 *
 * @auther Mr Jobs
 * @date 2015/7/26
 * @time 18:37
 */
public class AboutMeActivity extends FragmentActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private LinearLayout back;
    private TextView title;
    private RelativeLayout rv_look;// 消息回复的布局
    private ViewPager vp_about_me;// 界面ViewPager
    private RadioButton rb_one, rb_look;// 认证查看和消息回复选项
    private ImageView tv_one, tv_two;// 指示条
    private ImageView iv_image;// 与我相关的消息提醒标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 转场动画
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_about_me);

        init();// 初始化
        UiHelp.setTranslucentStatus(true, AboutMeActivity.this);
    }

    // 初始化，加载控件，设置点击事件和ViewPager滑动选中事件，默认选中认证查看，访问网络，获取与我相关的消息个数，保存至MsgCountBean，有消息就显示标识，没有就不显示
    private void init() {
        iv_image = (ImageView) findViewById(R.id.iv_image);
        back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.title);
        title.setText(getResources().getString(R.string.mine_relation));
        vp_about_me = (ViewPager) findViewById(R.id.vp_about_me);
        rv_look = (RelativeLayout) findViewById(R.id.rv_look);
        rv_look.setOnClickListener(this);
        rb_one = (RadioButton) findViewById(R.id.rb_one);
        rb_look = (RadioButton) findViewById(R.id.rb_look);
//        rb_look.setEnabled(false);
        rb_one.setOnCheckedChangeListener(this);
        rb_look.setOnCheckedChangeListener(this);
        tv_one = (ImageView) findViewById(R.id.tv_one);
        tv_two = (ImageView) findViewById(R.id.tv_two);
        vp_about_me.setOffscreenPageLimit(2);// 预加载
        AboutMeAdapter vpAdapter = new AboutMeAdapter(getSupportFragmentManager());// ViewPager数据适配器
        vp_about_me.setAdapter(vpAdapter);
        vp_about_me.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        one();
                        break;
                    case 1:
                        two();
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        // 默认选中认证查看
        one();

        // 访问网络，获取与我相关的消息个数，保存至MsgCountBean，有消息就显示标识，没有就不显示
        Read2Task read2Task = new Read2Task();
        read2Task.execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rv_look:
                if (!rb_look.isChecked()) {
                    two();
                    vp_about_me.setCurrentItem(1);
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    // 收到“msg”消息时，访问网络，获取与我相关的消息个数，保存至MsgCountBean，有消息就显示标识，没有就不显示
    @Subscribe
    public void onEvent(NoticeEvent event) {
        Log.d("yzy", "OnEvent-->" + Thread.currentThread().getId());
        if (event.getMsg().equals("msg")) {
            Read2Task read2Task = new Read2Task();
            read2Task.execute();
            EventBus.getDefault().post(new NoticeDissmissEvent("main"));
        }
    }

    // 点击认证查看或消息回复
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.rb_one:
                if (b) {
                    one();
                    vp_about_me.setCurrentItem(0);
                }
                break;
            case R.id.rb_look:
                if (b) {
                    two();
                    vp_about_me.setCurrentItem(1);
                }
                break;
        }
    }

    // 选中认证查看时的状态
    private void one() {
//        radioGroup.clearCheck();
        rb_one.setChecked(true);
        rb_look.setChecked(false);
        tv_one.setVisibility(View.VISIBLE);
        tv_two.setVisibility(View.INVISIBLE);
    }

    // 选中消息回复时的状态
    private void two() {
        rb_one.setChecked(false);
        rb_look.setChecked(true);
        tv_one.setVisibility(View.INVISIBLE);
        tv_two.setVisibility(View.VISIBLE);
    }

    // ViewPager数据适配器，加载两个Fragment，AuthLookFragment和ReplyMessageFragment
    public class AboutMeAdapter extends FragmentPagerAdapter {

        public AboutMeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int positon) {
            Fragment fragment = null;
            switch (positon) {
                case 0:
                    fragment = new AuthLookFragment();
                    break;
                case 1:
                    fragment = new ReplyMessageFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }

    // 访问网络，获取与我相关的消息个数，保存至MsgCountBean，有消息就显示标识，没有就不显示
    private class Read2Task extends AsyncTask<Void, Void, MsgCountBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MsgCountBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(AboutMeActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.READ_TOPOIC_MSG, AboutMeActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("aboutme", body);
                return FastJsonTools.getBean(body, MsgCountBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(MsgCountBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    MainActivity.read_two = aVoid.getData().getCount();// 与我相关的消息个数
                    if (aVoid.getData().getCount() > 0) {
                        iv_image.setVisibility(View.VISIBLE);// 有与我相关的消息就显示标识，否则不显示
                    } else {
                        iv_image.setVisibility(View.GONE);
                    }
                }
            }
        }
    }
}