package com.jinzht.pro.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.application.MyApplication;
import com.jinzht.pro.arclayout.ArcLayout;
import com.jinzht.pro.eventbus.MainNoticeEvent;
import com.jinzht.pro.eventbus.MsgCount;
import com.jinzht.pro.eventbus.NoticeDissmissEvent;
import com.jinzht.pro.fragment.CircleFragment;
import com.jinzht.pro.fragment.HomePageFragment;
import com.jinzht.pro.fragment.InvestAndFinancingFragment;
import com.jinzht.pro.fragment.ThreeBoardFragment;
import com.jinzht.pro.menudrawer.MenuDrawer;
import com.jinzht.pro.model.AuthBean;
import com.jinzht.pro.model.GetSignBean;
import com.jinzht.pro.model.InformationBean;
import com.jinzht.pro.model.LoginBean;
import com.jinzht.pro.model.MenuPersonBean;
import com.jinzht.pro.model.MsgCountBean;
import com.jinzht.pro.model.ProjectShareBean;
import com.jinzht.pro.model.UpdateVersonBean;
import com.jinzht.pro.model.YibaoUserInfoBean;
import com.jinzht.pro.mycircleimage.PolygonImageView;
import com.jinzht.pro.numberprogressbar.NumberProgressBar;
import com.jinzht.pro.service.DownloadService;
import com.jinzht.pro.utils.AnimatorUtils;
import com.jinzht.pro.utils.AsynOkUtils;
import com.jinzht.pro.utils.DialogUtils;
import com.jinzht.pro.utils.DisplayUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.FloatBtnUtils;
import com.jinzht.pro.utils.JpushUitls;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.ScreenUtils;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.ShareUtils;
import com.jinzht.pro.utils.StringUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.utils.UpLoadImageUtils;
import com.jinzht.pro.view.ClipRevealFrame;
import com.jinzht.pro.view.LoadingProssbar;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.thoughtworks.xstream.XStream;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * ????Ψ??????????????
 * <p>
 * 主页和侧边栏框架
 *
 * @auther Mr.Jobs
 * @date 2015/5/18
 * @time 14:17
 */

public class MainActivity extends FragmentActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    public static ViewPager vpFragment;// 主页有4张Fragment
    public RadioGroup radioGroup;// 下面的单选按钮
    long firstTime = 0;
    private RadioButton homepage, road_show, invest, mine;// 主页、投融资、新三板、圈子
    public static int flag = -1;
    public static MenuDrawer menuDrawer;// 带侧边栏的框架
    private PolygonImageView image;// 圆形头像控件
    private Intent intent;
    private RelativeLayout setting;// 设置
    private ImageView to_person;// 侧边栏中的头像
    private LinearLayout about_me, my_account, my_collection, my_invest_finacing, share, about_us;// 侧边栏中的选项
    private RelativeLayout login_bg;// 侧边栏背景
    private RelativeLayout rl_rootview;// 最底层的布局
    MenuPersonBean bean;
    private ProjectShareBean.DataEntity shareBean;// 分享
    private TextView user_name;// 用户名昵称
    List<View> list = new ArrayList<>();// 加号点开的view集合
    private UpdateVersonBean updateVersonBean;// 版本更新数据
    private AlertDialog alertDialog1;// 更新进度对话框
    private TextView houtai_btn;// 后台更新按钮
    private NumberProgressBar numberProgressBar;// 更新时显示的数字进度条
    private static Button circle_button;// 中间圆形加号按钮
    public static int loading_process = 0;// 进度
    private static ArcLayout arcLayout;// 加号点开后的弧形布局
    private static ClipRevealFrame menuLayout;// 逐渐显示出来的自定义全屏显示的控件
    public static int read_two = 0;// 与我相关的消息个数
    private TextView about_cricle;// 与我相关的消息提示
    private TextView tv_one, tv_two, tv_three, tv_four, tv_update;// 上传项目、我要认证、我要签到、个人中心、修改资料
    public static boolean isFirst = true;
    ShareUtils shareUtils = null;
    private boolean share_loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消应用标题
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// 转场动画
        menuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.OVERLAY);// 侧边栏划出后在内容上方
        menuDrawer.setContentView(R.layout.activity_main);
        menuDrawer.setMenuView(R.layout.menu_slide);
        UiHelp.setTranslucentStatus(true, MainActivity.this);// 透明状态栏
        MyApplication.getInstance().addActivity(this);
        MobclickAgent.setDebugMode(true);// 友盟
        JPushInterface.init(getApplicationContext());// 极光推动
        menuDrawer.setMenuSize((int) (ScreenUtils.getScreenWidth(MainActivity.this) * 0.8));
        menuDrawer.setDropShadowEnabled(false);// 阴影效果
        menuDrawer.setOffsetMenuEnabled(true);// 侧边栏支持滑动
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();// 初始化
        shareUtils = new ShareUtils(MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "start");
        // 注册EventBus，用来替代Intent,Handler,BroadCast在Fragment，Activity，Service，线程之间传递消息
        EventBus.getDefault().register(MainActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(MainActivity.this);
    }

    // 中间加号按钮被点开
    @SuppressWarnings("NewApi")
    private void showMenu() {
        menuLayout.setVisibility(View.VISIBLE);
        List<Animator> animList = new ArrayList<>();
        for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
        }
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());// 用插筒器达到惯性动画效果
        animSet.playTogether(animList);
        animSet.start();
        circle_button.setSelected(!circle_button.isSelected());
    }

    // 中间加号按钮被点合上
    @SuppressWarnings("NewApi")
    public static void hideMenu() {
        List<Animator> animList = new ArrayList<>();
        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());// 开始向后，然后向前甩的惯性动画
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menuLayout.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();
        circle_button.setSelected(!circle_button.isSelected());
    }

    // 中间加号显示时的动画
    private Animator createShowItemAnimator(View item) {
        float dx = circle_button.getX() - item.getX();
        float dy = circle_button.getY() - item.getY();
        item.setRotation(0f);// 旋转
        item.setTranslationX(dx);
        item.setTranslationY(dy);
        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );
        return anim;
    }

    // 中间加号隐藏时的动画
    private static Animator createHideItemAnimator(View item) {
        float dx = circle_button.getX() - item.getX();
        float dy = circle_button.getY() - item.getY();
        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });
        return anim;
    }

    // 初始化，读取设备ID和appKey，检查更新，读取与我相关的消息，在夜间的话提示开启夜间模式
    private void init() {
        findView();

        String udid = JpushUitls.getImei(getApplicationContext(), "");// 设备ID
        if (null != udid)
            Log.i("Jpush", "IMEI: " + udid);

        String appKey = JpushUitls.getAppKey(getApplicationContext());// appKey
        if (null == appKey) appKey = "AppKey??";
        Log.i("AppKey", "AppKey" + appKey);

        // 检查更新
        UpdateVersonTask updateVersonTask = new UpdateVersonTask();
        updateVersonTask.execute();

        // 访问网络，读取与我相关的消息，保存到MsgCountBean，并在about_cricle控件上显示个数，再发送广播
        Read2Task read1Task = new Read2Task();
        read1Task.execute();

        // 夜间提示开启夜间模式
        if (timeCompare()) {
            /**夜间晚七点到早六点*/
            Log.i("夜间模式", "night");
            if (!SharePreferencesUtils.getLightModel(MainActivity.this)) {
                DialogUtils.nightModelDialog(MainActivity.this, getResources().getString(R.string.night_model_title));
            }
        } else {
            Log.i("夜间模式", "not_night");
        }
    }

    // 判断是否到夜间
    private boolean timeCompare() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        Log.i("时间", "hour:" + hour);
        if (hour > 19 || hour < 6) {
            return true;
        } else {
            return false;
        }
    }

    // 加载控件
    private void findView() {
        about_cricle = (TextView) findViewById(R.id.about_cricle);// 与我相关的消息提示
        menuLayout = (ClipRevealFrame) findViewById(R.id.menu_layout);// 逐渐显示出来的自定义全屏显示的控件
        arcLayout = (ArcLayout) findViewById(R.id.arc_layout);// 加号点开后的弧形布局
        circle_button = (Button) findViewById(R.id.circle_button);// 中间圆形加号按钮
        circle_button.setOnClickListener(this);
        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(this);
        }
        tv_update = (TextView) findViewById(R.id.tv_update);// [修改资料]
        tv_one = (TextView) findViewById(R.id.tv_one);// 上传项目
        tv_two = (TextView) findViewById(R.id.tv_two);// 我要认证
        tv_three = (TextView) findViewById(R.id.tv_three);// 我要签到，已被弃用
        tv_four = (TextView) findViewById(R.id.tv_four);// 个人中心
        list = FloatBtnUtils.floatView(this, list);
        user_name = (TextView) findViewById(R.id.user_name);// 用户名昵称
        rl_rootview = (RelativeLayout) findViewById(R.id.rl_rootview);// 最底层的布局
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);// 下面的按钮
        login_bg = (RelativeLayout) findViewById(R.id.login_bg);// 侧边栏背景
        to_person = (ImageView) findViewById(R.id.to_person);// 侧边栏中的头像
        to_person.setOnClickListener(this);
        about_me = (LinearLayout) findViewById(R.id.about_me);// 与我相关
        about_me.setOnClickListener(this);
        my_account = (LinearLayout) findViewById(R.id.my_account);// 资金账户
        my_account.setOnClickListener(this);
        my_collection = (LinearLayout) findViewById(R.id.my_collection);// 我的收藏
        my_collection.setOnClickListener(this);
        my_invest_finacing = (LinearLayout) findViewById(R.id.my_invest_finacing);// 我的投融资
        my_invest_finacing.setOnClickListener(this);
        share = (LinearLayout) findViewById(R.id.share);// 邀请好友
        share.setOnClickListener(this);
        about_us = (LinearLayout) findViewById(R.id.about_us);// 关于我们
        about_us.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        image = (PolygonImageView) findViewById(R.id.user_iamge);// 圆形头像控件
        image.setImageResource(R.drawable.user_loading);
        setting = (RelativeLayout) findViewById(R.id.setting);// 设置
        setting.setOnClickListener(this);
        homepage = (RadioButton) findViewById(R.id.homepage);// 主页
        road_show = (RadioButton) findViewById(R.id.road_show);// 投融资
        invest = (RadioButton) findViewById(R.id.invest);// 新三板
        mine = (RadioButton) findViewById(R.id.mine);// 圈子
        vpFragment = (ViewPager) findViewById(R.id.viewpager);
        vpFragment.setOffscreenPageLimit(4);// 全部预加载
        MainViewPagerFragmentAdapter vpAdapter = new MainViewPagerFragmentAdapter(getSupportFragmentManager());// 主页ViewPagerFragment页面填充
        vpFragment.setAdapter(vpAdapter);
        vpFragment.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        flag = 0;
                        homepage.setChecked(true);
                        road_show.setChecked(false);
                        invest.setChecked(false);
                        mine.setChecked(false);
                        break;
                    case 1:
                        flag = 1;
                        homepage.setChecked(false);
                        road_show.setChecked(true);
                        invest.setChecked(false);
                        mine.setChecked(false);
                        break;
                    case 2:
                        flag = 2;
                        homepage.setChecked(false);
                        road_show.setChecked(false);
                        invest.setChecked(true);
                        mine.setChecked(false);
                        break;
                    case 3:
                        flag = 3;
                        homepage.setChecked(false);
                        road_show.setChecked(false);
                        invest.setChecked(false);
                        mine.setChecked(true);
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

        // 侧边栏打开时，访问网络，获取数据保存到MenuPersonBean，并在侧边栏中展示数据，隐藏修改资料按钮
        menuDrawer.setOnDrawerStateChangeListener(new MenuDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (menuDrawer.getDrawerState() == MenuDrawer.STATE_OPEN) {
                    hideMenu();
                    GetImageTask task = new GetImageTask();
                    task.execute();
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
            }
        });

        // 已登录则显示侧边栏背景
        if (SharePreferencesUtils.getIsLogin(MainActivity.this)) {
            login_bg.setVisibility(View.VISIBLE);
        } else {
            login_bg.setVisibility(View.GONE);
        }

        // 打开侧边栏提示
        if (!SharePreferencesUtils.getIsToastMenu(MainActivity.this)) {
            DialogUtils.easyMenuToast(MainActivity.this, getResources().getString(R.string.toast_menu), DisplayUtils.convertDipOrPx(MainActivity.this, 29),
                    ScreenUtils.getStatusHeight(MainActivity.this) + DisplayUtils.convertDipOrPx(MainActivity.this, 43), circle_button);
        }
    }

    // 接收EventBus的发送消息，访问网络，读取与我相关的消息，保存到MsgCountBean，并在about_cricle控件上显示个数，再发送广播
    @Subscribe
    public void onEvent(MainNoticeEvent msg) {
        if (msg.getMsg().equals("receiver")) {
            Log.i("注意！收到消息啦！", "receiver");
            Read2Task read2Task = new Read2Task();
            read2Task.execute();
        }
    }

    // 接收EventBus的发送消息，访问网络，读取与我相关的消息，保存到MsgCountBean，并在about_cricle控件上显示个数，再发送广播
    @Subscribe
    public void onEventMainThread(NoticeDissmissEvent msgs) {
        if (msgs.getMain().equals("main")) {
            Log.i("注意！又收到消息啦！", "main");
            Read2Task read2Task = new Read2Task();
            read2Task.execute();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_iamge:// 点击头像，关闭侧边栏，跳转至个人资料页面PersonActivity
                menuDrawer.closeMenu();
                intent = new Intent(MainActivity.this, PersonActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:// 点击设置，关闭侧边栏，跳转至设置页面SettingActivity
                menuDrawer.closeMenu();
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.to_person:// 点击头像上面的背景图片，关闭侧边栏，跳转至个人资料页面PersonActivity
                menuDrawer.closeMenu();
                intent = new Intent(MainActivity.this, PersonActivity.class);
                startActivity(intent);
                break;
            case R.id.about_me:// 点击与我相关，关闭侧边栏，跳转至与我相关页面AboutMeActivity
                menuDrawer.closeMenu();
                intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
                break;
            case R.id.my_account:// 点击资金账户，关闭侧边栏，跳转至资金账户页面YibaoAccountActivity
                menuDrawer.closeMenu();
                // TODO: 2016/3/24 先判断用户是否完善信息？认证？再获取用户信息，以判断是否注册过
                if (SharePreferencesUtils.getPerfectInformation(MainActivity.this)) {
                    if (!UiHelp.isLongFastClick()) {
                        UiHelp.ToastMessageShort(MainActivity.this, getResources().getString(R.string.loading));// 加载中
                        IsInvestTask2 isInvestTask2 = new IsInvestTask2();
                        isInvestTask2.execute();
                    }
                } else {// 弹出完善信息对话框提示
                    DialogUtils.improveInformationDialog(MainActivity.this);
                }
                break;
            case R.id.my_collection:// 点击我的收藏，关闭侧边栏，跳转至我的收藏页面MyCollectActivity
                menuDrawer.closeMenu();
                intent = new Intent(MainActivity.this, MyCollectActivity.class);
                startActivity(intent);
                break;
            case R.id.my_invest_finacing:// 点击我的投融资，关闭侧边栏，跳转至我的投融资页面MyFinacingInvestActivity
                menuDrawer.closeMenu();
                intent = new Intent(MainActivity.this, MyFinacingInvestActivity.class);
                startActivity(intent);
                break;
            case R.id.share:// 点击邀请好友，关闭侧边栏，弹出分享对话框
                menuDrawer.closeMenu();
                if (!UiHelp.isLongFastClick()) {
                    if (share_loaded) {
                        DialogUtils.ShareDialog(MainActivity.this, homepage, shareUtils, shareBean.getTitle(),
                                shareBean.getContent(), shareBean.getImg(), shareBean.getUrl());
                    } else {// 访问网络，读取分享数据，保存到ProjectShareBean， 弹出分享对话框
                        UiHelp.ToastMessageShort(MainActivity.this, getResources().getString(R.string.loading));
                        AppShareTask task = new AppShareTask();
                        task.execute();
                    }
                }
                break;
            case R.id.about_us:// 点击关于我们，关闭侧边栏，跳转至我的投融资页面AboutUsActivity
                if (!UiHelp.isFastClick()) {
                    intent = new Intent(MainActivity.this, AboutUsActivity.class);
                    startActivity(intent);
                    menuDrawer.closeMenu();
                }
                break;
            case R.id.back:
                menuDrawer.closeMenu();
                break;
            case R.id.houtai_btn:// 点击后台更新，开启后台更新服务DownloadService，关闭跟新进度款
                intent = new Intent(MainActivity.this, DownloadService.class);
                startService(intent);
                alertDialog1.dismiss();
                break;
            case R.id.circle_button:// 点击中间圆形加号
                onFabClick(view);
                break;
            case R.id.one:// 点击加号再点击上传项目，关闭加号，打开上传项目页面WantRoadShowActivity
                hideMenu();
                intent = new Intent(MainActivity.this, WantRoadShowActivity.class);
                startActivity(intent);
                break;
            case R.id.two:// 点击加号再点击我要认证，关闭加号，判断是否已完善信息，弹出对话框提示
                if (SharePreferencesUtils.getPerfectInformation(MainActivity.this)) {
                    hideMenu();
                    if (!UiHelp.isLongFastClick()) {
                        UiHelp.ToastMessageShort(MainActivity.this, getResources().getString(R.string.loading));// 加载中
                        IsInvestTask isInvestTask = new IsInvestTask();
                        isInvestTask.execute();
                    }
                } else {// 弹出完善信息对话框提示
                    hideMenu();
                    DialogUtils.improveInformationDialog(MainActivity.this);
                }
                break;
            case R.id.three:// 已取消
                if (SharePreferencesUtils.getIsLogin(MainActivity.this)) {
                    hideMenu();
                    intent = new Intent(MainActivity.this, WantSignInActivity.class);
                    startActivity(intent);
                } else {
                    hideMenu();
                    DialogUtils.loginDialog(MainActivity.this);
                }
                break;
            case R.id.four:// 点击加号再点击个人中心，关闭加号，打开侧边栏
                hideMenu();
                if (!UiHelp.isLongFastClick()) {
                    menuDrawer.toggleMenu();
                }
                break;
        }
    }

    // 点击中间圆形加号，显示或隐藏
    private void onFabClick(View v) {
        if (v.isSelected()) {
            hideMenu();
        } else {
            showMenu();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.homepage:
                flag = 0;
                vpFragment.setCurrentItem(0);
                break;
            case R.id.road_show:
                flag = 1;
                vpFragment.setCurrentItem(1);
                break;
            case R.id.invest:
                vpFragment.setCurrentItem(2);
                break;
            case R.id.mine:
                vpFragment.setCurrentItem(3);
                break;
        }
    }

    // 点击两次退出到桌面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                SuperToastUtils.showSuperToast(MainActivity.this, 1, R.string.back_again);
                firstTime = secondTime;
                return true;
            } else {
                Intent intent1 = new Intent();
                intent1.setAction(BaseActivity.EXITACTION);
                sendBroadcast(intent1);// 发送退出广播
                MobclickAgent.onKillProcess(MainActivity.this);
                MyApplication.getInstance().exit();//遍历所有Activity 并finish
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }

    @Override
    public void onResume() {
        Log.i("onResume", "又能看见了");
        super.onResume();
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
        // 有与我相关的消息就显示个数并发送广播
        showOrHind();
    }

    // 接收Fragment传递的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("再返回时收到的消息", "requestCode" + requestCode);
        Fragment f = getSupportFragmentManager().findFragmentByTag("oder");
        f.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // 跳转至登录页面LoginActivity
    private void loginAgain() {
        intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // 访问网络，获取数据保存到MenuPersonBean，并在侧边栏中展示数据
    private class GetImageTask extends AsyncTask<Void, Void, MenuPersonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MenuPersonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(MainActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //????
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.MODIFYIMAGE, MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("侧边栏信息", body);
                return FastJsonTools.getBean(body, MenuPersonBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(MenuPersonBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid == null) {
            } else {
                if (aVoid.getCode() == -1) {
                    isFirst = true;
                    return;
                }
                if ((aVoid.getData() != null && !StringUtils.isBlank(aVoid.getData().getPhoto()))) {
                    isFirst = false;
                    UpLoadImageUtils.getUserImage(aVoid.getData().getPhoto(), image);
                }
                user_name.setText(aVoid.getData().getNickname());
                tv_update.setVisibility(View.GONE);
            }
        }
    }

    // 登录任务
    private class LoginTask extends AsyncTask<Void, Void, LoginBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LoginBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(MainActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //????
                try {
                    body = AsynOkUtils.doNewLoginPost("tel", SharePreferencesUtils.getTelephone(MainActivity.this),
                            "passwd", SharePreferencesUtils.getPassWd(MainActivity.this), "regid", JPushInterface.getRegistrationID(MainActivity.this), "version", getResources().getString(R.string.verson_name),
                            Constant.BASE_URL + Constant.PHONE + Constant.LOGIN, MainActivity.this);
                    Log.i("登录消息", body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, LoginBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(LoginBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid == null) {
                SuperToastUtils.showSuperToast(MainActivity.this, 1, R.string.no_wifi);// 请先联网
                return;
            } else {
                if (aVoid.getCode() == 0) {
                    UiHelp.printMsg(aVoid.getCode(), aVoid.getMsg(), MainActivity.this);
                    SharePreferencesUtils.setIsLogin(MainActivity.this, true);
//                    SharePreferencesUtils.saveInformation(MainActivity.this, login_edit.get(0).getText().toString(), md51);
                    SharePreferencesUtils.setPerfectInformation(MainActivity.this, aVoid.getData().getInfo());
                    SharePreferencesUtils.setAuth(MainActivity.this, aVoid.getData().getAuth() + "");
                } else if (aVoid.getCode() == -1) {
                    SharePreferencesUtils.setIsLogin(MainActivity.this, false);
                    loginAgain();
                    UiHelp.printMsg(aVoid.getCode(), aVoid.getMsg(), MainActivity.this);
                } else {
                    UiHelp.printMsg(aVoid.getCode(), aVoid.getMsg(), MainActivity.this);
                    SharePreferencesUtils.setIsLogin(MainActivity.this, false);
                    loginAgain();
                }
            }
        }
    }

    // 更新任务，查看是否有新版本，获取数据保存到UpdateVersonBean中，根据force的真假值提示是否强制更新
    private class UpdateVersonTask extends AsyncTask<Void, Void, UpdateVersonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected UpdateVersonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(MainActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //????
                try {
                    body = OkHttpUtils.doPushPost(Constant.BASE_URL + Constant.PHONE + Constant.UPDATEVERSON + "2/", MainActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                Log.i("版本更新信息", body);
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    if (jsonObject.isNull("data")) {
                        return null;
                    }
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    updateVersonBean = new UpdateVersonBean(jsonObject1.optString("edition"), jsonObject1.optString("href"), jsonObject1.optString("item"), jsonObject1.optBoolean("force"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return updateVersonBean;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(UpdateVersonBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid == null) {
                return;
            } else {
                try {
                    if (!StringUtils.isEquals(aVoid.getEdition(), UiHelp.getVersionName(MainActivity.this))) {
                        new Thread() {
                            public void run() {
                                if (aVoid.isForce()) {//???
                                    BroadcastHandler.sendEmptyMessage(1);// 提示强制更新
                                } else {//?????
                                    BroadcastHandler.sendEmptyMessage(2);// 提示更新
                                }
//							??????????????????handler???????
                            }
                        }.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 提示更新对话框
    public void UpdateVersonDialog(Activity activity, String context) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_update_verson);
        dialog.setCanceledOnTouchOutside(true);// 点击对话框外取消
        view.setWindowAnimations(R.style.dialog_zoom);  //??????
        TextView right_update = (TextView) view.findViewById(R.id.right_update);// 立即更新
        TextView give_up = (TextView) view.findViewById(R.id.give_up);// 狠心放弃
        TextView update_context = (TextView) view.findViewById(R.id.update_context);// 更新内容
        final AlertDialog finalDialog = dialog;
        update_context.setText(context);
        right_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressBarDialog();// 显示进度条
                new Thread() {
                    public void run() {
                        loadFile();
                    }// 下载新版
                }.start();
                dialog.dismiss();
            }
        });
        give_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    // 强制更新对话框，下载新版
    public void MustUpdateDialog(Activity activity, String context) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_must_update);
        dialog.setCanceledOnTouchOutside(false);// 对话框区域外点击不消失
        dialog.setCancelable(false);// 不可取消
        view.setWindowAnimations(R.style.dialog_zoom);  //动画效果
        TextView right_update = (TextView) view.findViewById(R.id.right_update);// 立即更新按钮
        TextView update_context = (TextView) view.findViewById(R.id.update_context);// 更新内容
        final AlertDialog finalDialog = dialog;
        update_context.setText(context);
        right_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressBarDialog();// 显示更新进度条
                new Thread() {
                    public void run() {
                        loadFile();
                    }// 下载新app
                }.start();
                dialog.dismiss();
            }
        });
    }

    // 显示跟新进度对话框
    private void showProgressBarDialog() {
        alertDialog1 = new AlertDialog.Builder(MainActivity.this, R.style.Translucent_NoTitle).create();
        alertDialog1.show();
        Window window = alertDialog1.getWindow();
        window.setContentView(R.layout.dialog_update_progress);
        window.setWindowAnimations(R.style.dialog_animator);  //??????
        houtai_btn = (TextView) window.findViewById(R.id.houtai_btn);// 后台更新
        numberProgressBar = (NumberProgressBar) window.findViewById(R.id.numberPro);
        numberProgressBar.setMax(100);
        houtai_btn.setOnClickListener(this);
        alertDialog1.setCanceledOnTouchOutside(false);// 点击对话框外不可取消
    }

    // 下载jinzhts.apk，下载成功sendMsg(2, 0);不成功sendMsg(-1, 0);
    public void loadFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 有读写权限
            Request request = new Request.Builder().url(updateVersonBean.getUrl()).build();
            Log.i("更新地址",updateVersonBean.getUrl());
            try {
                Response response = MyApplication.getInstance().okHttpClient.newCall(request).execute();
                InputStream inputStream = response.body().byteStream();
                float ints = response.body().contentLength();
                File file = new File(Environment.getExternalStorageDirectory(), "jinzhts.apk");
                FileOutputStream fos = new FileOutputStream(file);
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                byte[] buffer = new byte[1024];
                int length = -1;
                float count = 0;
                while ((length = bis.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                    count += length;
                    //????????????
                    sendMsg(1, (int) (count * 100 / ints));// 正在下载
                }
                sendMsg(2, 0);
                fos.close();
                bis.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                sendMsg(-1, 0);
            }
        }

    }

    // 更新软件成功时handler发送sendMsg(2, 0)，失败时发送sendMsg(-1, 0);
    private void sendMsg(int flag, int c) {
        Message msg = new Message();
        msg.what = flag;
        msg.arg1 = c;
        handler.sendMessage(msg);
    }

    // 显示下载进度，成功后直接安装，失败提示用户
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {//
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 1:// 正在下载，显示进度
                        loading_process = msg.arg1;
                        numberProgressBar.setProgress(loading_process);
                        break;
                    case 2:// 下载成功，调用系统安装应用
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(Environment
                                        .getExternalStorageDirectory(), "jinzhts.apk")),
                                "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case -1:// 下载失败，提示用户
                        String error = msg.getData().getString("error");
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };

    // 弹出更新对话框，提示更新或强制更新。msg.what == 1强制更新；msg.what == 2提示更新
    private Handler BroadcastHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//			super.handleMessage(msg);
            if (msg.what == 1) {
                MustUpdateDialog(MainActivity.this, updateVersonBean.getItem());
            } else {
                UpdateVersonDialog(MainActivity.this, updateVersonBean.getItem());
            }
        }
    };

    // 访问网络，读取与我相关的消息，保存到MsgCountBean，并在about_cricle控件上显示个数，再发送广播
    private class Read2Task extends AsyncTask<Void, Void, MsgCountBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MsgCountBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(MainActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //????
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.READ_TOPOIC_MSG, MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                Log.i("与我相关的消息", body);
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
                    read_two = aVoid.getData().getCount();
                    // EventBus发送与我相关的消息个数
                    EventBus.getDefault().post(new MsgCount(aVoid.getData().getCount()));
                    showOrHind();
                }
            }
        }

    }

    // 有与我相关的消息就显示个数并发送广播
    private void showOrHind() {
        if (read_two > 0) {
            showRead();
            about_cricle.setText(read_two + "");
        } else {
            Log.i("与我相关的消息个数", read_two + "count");
            hideRead();
        }
    }

    // 隐藏与我相关的消息控件
    private void hideRead() {
        about_cricle.setVisibility(View.GONE);
    }

    // 显示与我相关的消息个数，并发送广播
    private void showRead() {
        about_cricle.setVisibility(View.VISIBLE);
        UiHelp.showBroad(MainActivity.this);

    }

    // 主页ViewPagerFragment页面填充
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
                    // 将HomePageFragment添加到Activity的最上层
                    getSupportFragmentManager().beginTransaction().add(new HomePageFragment(), "oder").commit();
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

    // 认证成功跳转至与我相关页面AboutMeActivity，保存数据到AuthBean
    private class IsInvestTask extends AsyncTask<Void, Void, AuthBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected AuthBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(MainActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.ISINVESTOR, MainActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, AuthBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(AuthBean common) {
            super.onPostExecute(common);
            if (common != null) {
                if (common.getCode() == -1) {
                    if (!SharePreferencesUtils.contains(MainActivity.this, "telephone") && !SharePreferencesUtils.contains(MainActivity.this, "passwd")
                            && SharePreferencesUtils.getTelephone(MainActivity.this).equals("") && SharePreferencesUtils.getPassWd(MainActivity.this).equals("")) {
                        loginAgain();
                    } else {
                        LoginTask loginTask = new LoginTask();
                        loginTask.execute();
                    }
                    return;
                }
                if (common.getCode() == 0) {
                    Log.i("是否是投资人", common.getData().getAuth() + "");
                    SharePreferencesUtils.setAuth(MainActivity.this, common.getData().getAuth() + "");
                    UiHelp.mainAuth(MainActivity.this, common.getData().getAuth() + "");
                }
            }
        }
    }

    // 访问网络，读取分享数据，保存到ProjectShareBean， 弹出分享对话框
    private class AppShareTask extends AsyncTask<Void, Void, ProjectShareBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ProjectShareBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(MainActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.SHAREAPP, MainActivity.this);
                    if (FastJsonTools.getBean(body, ProjectShareBean.class) != null && FastJsonTools.getBean(body, ProjectShareBean.class).getData() != null) {
                        shareBean = FastJsonTools.getBean(body, ProjectShareBean.class).getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("分享的数据", body);
                return FastJsonTools.getBean(body, ProjectShareBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ProjectShareBean common) {
            super.onPostExecute(common);
            if (common != null) {
                if (common.getCode() == -1) {
                    return;
                }
                if (common.getCode() == 0) {
                    DialogUtils.ShareDialog(MainActivity.this, homepage, shareUtils, common.getData().getTitle(),
                            common.getData().getContent(), common.getData().getImg(), common.getData().getUrl());
                    share_loaded = true;
                } else {
                    SuperToastUtils.showSuperToast(MainActivity.this, 1, common.getMsg());
                }
            } else {
                SuperToastUtils.showSuperToast(MainActivity.this, 1, R.string.no_wifi);// 提示请先联网
            }

        }
    }

    // TODO: 2016/3/24 点击资金账户后，判断是否已在金指投认证
    private class IsInvestTask2 extends AsyncTask<Void, Void, AuthBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected AuthBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(MainActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.ISINVESTOR, MainActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, AuthBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(AuthBean common) {
            super.onPostExecute(common);
            if (common == null) {
                SuperToastUtils.showSuperToast(MainActivity.this, 1, R.string.no_wifi);
            } else {
                if (common.getCode() == -1) {
                    if (!SharePreferencesUtils.contains(MainActivity.this, "telephone") && !SharePreferencesUtils.contains(MainActivity.this, "passwd")
                            && SharePreferencesUtils.getTelephone(MainActivity.this).equals("") && SharePreferencesUtils.getPassWd(MainActivity.this).equals("")) {
                        loginAgain();
                    } else {
                        LoginTask loginTask = new LoginTask();
                        loginTask.execute();
                    }
                    return;
                }
                if (common.getCode() == 0) {
                    Log.i("认证？", common.getData().getAuth() + "");
                    SharePreferencesUtils.setAuth(MainActivity.this, common.getData().getAuth() + "");
                    isAuth(common.getData().getAuth() + "");
                }
            }
        }
    }

    private void isAuth(String auth) {
        if (StringUtils.isEquals(auth, "")) {
            /**从未认证过，哈哈哈*/
            DialogUtils.showInvestDialogs(this);
        } else if (StringUtils.isEquals(auth, "null")) {
            /**已经认证，审核中*/
            DialogUtils.waitAuthDialog(this);
        } else if (StringUtils.isEquals(auth, "true")) {
            /**成功*/
            GetInformationTask getInformationTasktask = new GetInformationTask();
            getInformationTasktask.execute();
        } else {
            DialogUtils.authFailDialog(this, getResources().getString(R.string.fail_auth), getResources().getString(R.string.crop__done));
            /**失败*/
        }
    }

    private InformationBean informationBean;
    private String request;
    private String sign;
    private YibaoUserInfoBean yibaoUserInfoBean;

    private class GetInformationTask extends AsyncTask<Void, Void, InformationBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected InformationBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(MainActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.GETINFORMATION, MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, InformationBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(InformationBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid == null) {
                SuperToastUtils.showSuperToast(MainActivity.this, 1, R.string.no_wifi);
            } else {
                informationBean = aVoid;
                if (aVoid.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                }
                if (aVoid.getCode() == 0) {
                    Log.i("人员信息", aVoid.getData().toString());
                    // 获取签名
                    GetSignTask getSignTask = new GetSignTask();
                    getSignTask.execute();
                }
            }
        }
    }

    private class GetSignTask extends AsyncTask<Void, Void, GetSignBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("加载中...");
            request = "<request platformNo=\"10013200657\"><platformUserNo>" + "jinzht_0000_" + informationBean.getData().getUid() + "</platformUserNo></request>";
//            request = "<request platformNo=\"10013200657\"><platformUserNo>" + "jinzht_0000_" + informationBean.getData().getUid() + "33" + "</platformUserNo></request>";
        }

        @Override
        protected GetSignBean doInBackground(Void... params) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(MainActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.SIGNVERIFY + "?method=sign&req=" + request, MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, GetSignBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(GetSignBean body) {
            super.onPostExecute(body);
            dismissProgressDialog();
            if (body == null) {
                SuperToastUtils.showSuperToast(MainActivity.this, 1, R.string.service_black);
            } else {
                sign = body.getData().getSign();
                Log.i("签名", sign);
                // 查询是否在易宝注册过
                IsRegisteredTask isRegisteredTask = new IsRegisteredTask();
                isRegisteredTask.execute();
            }
        }
    }

    // 查询是否在易宝注册过
    private class IsRegisteredTask extends AsyncTask<Void, Void, YibaoUserInfoBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected YibaoUserInfoBean doInBackground(Void... params) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(MainActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doPost("service", Constant.YIBAOACCOUNTINFO, "req", request, "sign", sign, Constant.YIBAODIRECT);
                    XStream xStream = new XStream();
                    xStream.processAnnotations(YibaoUserInfoBean.class);// 指定对应的Bean
                    yibaoUserInfoBean = (YibaoUserInfoBean) xStream.fromXML(body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return yibaoUserInfoBean;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(YibaoUserInfoBean bean) {
            super.onPostExecute(bean);
            if (bean == null) {
                SuperToastUtils.showSuperToast(MainActivity.this, 1, R.string.no_wifi);
            } else {
                Log.i("返回的查询结果", bean.toString());
                if ("1".equals(bean.getCode())) {
                    Log.i("返回码1", "已注册过");
                    // 进入YibaoAccount2Activity
                    intent = new Intent(MainActivity.this, YibaoAccount2Activity.class);
                    intent.putExtra("uid", informationBean.getData().getUid() + "");
                    intent.putExtra("bankName", yibaoUserInfoBean.getBank());
                    intent.putExtra("bankNo", yibaoUserInfoBean.getCardNo());
                    intent.putExtra("yue", yibaoUserInfoBean.getBalance());
                    intent.putExtra("keyongyue", yibaoUserInfoBean.getAvailableAmount());
                    intent.putExtra("dongjieyue", yibaoUserInfoBean.getFreezeAmount());
                    intent.putExtra("platformPhone", yibaoUserInfoBean.getBindMobileNo());
                    intent.putExtra("cardStatus", yibaoUserInfoBean.getCardStatus());
                    intent.putExtra("main", "main");
                    startActivity(intent);
                } else if ("101".equals(bean.getCode())) {
                    Log.i("返回码101", "没注册");
                    // 进入YibaoAccountActivity
                    intent = new Intent(MainActivity.this, YibaoAccountActivity.class);
                    intent.putExtra("uid", informationBean.getData().getUid() + "");
                    intent.putExtra("name", informationBean.getData().getName());
                    intent.putExtra("idNo", informationBean.getData().getIdno());
                    intent.putExtra("tel", informationBean.getData().getTel());
                    intent.putExtra("email", informationBean.getData().getEmail());
                    startActivity(intent);
                } else {
                    // 提示用户出错
                    SuperToastUtils.showSuperToast(MainActivity.this, 1, bean.getDescription());
                }
            }
        }
    }

    LoadingProssbar loadingDialog;

    public void showProgressDialog(String message) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingProssbar(this, message, R.anim.loading_anim);
            loadingDialog.show();
        } else {
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        }
    }

    public void dismissProgressDialog() {
        if (this.loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}