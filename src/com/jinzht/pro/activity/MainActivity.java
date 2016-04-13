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
 * ????��??????????????
 * <p>
 * ��ҳ�Ͳ�������
 *
 * @auther Mr.Jobs
 * @date 2015/5/18
 * @time 14:17
 */

public class MainActivity extends FragmentActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    public static ViewPager vpFragment;// ��ҳ��4��Fragment
    public RadioGroup radioGroup;// ����ĵ�ѡ��ť
    long firstTime = 0;
    private RadioButton homepage, road_show, invest, mine;// ��ҳ��Ͷ���ʡ������塢Ȧ��
    public static int flag = -1;
    public static MenuDrawer menuDrawer;// ��������Ŀ��
    private PolygonImageView image;// Բ��ͷ��ؼ�
    private Intent intent;
    private RelativeLayout setting;// ����
    private ImageView to_person;// ������е�ͷ��
    private LinearLayout about_me, my_account, my_collection, my_invest_finacing, share, about_us;// ������е�ѡ��
    private RelativeLayout login_bg;// ���������
    private RelativeLayout rl_rootview;// ��ײ�Ĳ���
    MenuPersonBean bean;
    private ProjectShareBean.DataEntity shareBean;// ����
    private TextView user_name;// �û����ǳ�
    List<View> list = new ArrayList<>();// �Ӻŵ㿪��view����
    private UpdateVersonBean updateVersonBean;// �汾��������
    private AlertDialog alertDialog1;// ���½��ȶԻ���
    private TextView houtai_btn;// ��̨���°�ť
    private NumberProgressBar numberProgressBar;// ����ʱ��ʾ�����ֽ�����
    private static Button circle_button;// �м�Բ�μӺŰ�ť
    public static int loading_process = 0;// ����
    private static ArcLayout arcLayout;// �Ӻŵ㿪��Ļ��β���
    private static ClipRevealFrame menuLayout;// ����ʾ�������Զ���ȫ����ʾ�Ŀؼ�
    public static int read_two = 0;// ������ص���Ϣ����
    private TextView about_cricle;// ������ص���Ϣ��ʾ
    private TextView tv_one, tv_two, tv_three, tv_four, tv_update;// �ϴ���Ŀ����Ҫ��֤����Ҫǩ�����������ġ��޸�����
    public static boolean isFirst = true;
    ShareUtils shareUtils = null;
    private boolean share_loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// ȡ��Ӧ�ñ���
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ����
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);// ת������
        menuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.OVERLAY);// ������������������Ϸ�
        menuDrawer.setContentView(R.layout.activity_main);
        menuDrawer.setMenuView(R.layout.menu_slide);
        UiHelp.setTranslucentStatus(true, MainActivity.this);// ͸��״̬��
        MyApplication.getInstance().addActivity(this);
        MobclickAgent.setDebugMode(true);// ����
        JPushInterface.init(getApplicationContext());// �����ƶ�
        menuDrawer.setMenuSize((int) (ScreenUtils.getScreenWidth(MainActivity.this) * 0.8));
        menuDrawer.setDropShadowEnabled(false);// ��ӰЧ��
        menuDrawer.setOffsetMenuEnabled(true);// �����֧�ֻ���
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();// ��ʼ��
        shareUtils = new ShareUtils(MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "start");
        // ע��EventBus���������Intent,Handler,BroadCast��Fragment��Activity��Service���߳�֮�䴫����Ϣ
        EventBus.getDefault().register(MainActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(MainActivity.this);
    }

    // �м�ӺŰ�ť���㿪
    @SuppressWarnings("NewApi")
    private void showMenu() {
        menuLayout.setVisibility(View.VISIBLE);
        List<Animator> animList = new ArrayList<>();
        for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
        }
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());// �ò�Ͳ���ﵽ���Զ���Ч��
        animSet.playTogether(animList);
        animSet.start();
        circle_button.setSelected(!circle_button.isSelected());
    }

    // �м�ӺŰ�ť�������
    @SuppressWarnings("NewApi")
    public static void hideMenu() {
        List<Animator> animList = new ArrayList<>();
        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());// ��ʼ���Ȼ����ǰ˦�Ĺ��Զ���
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

    // �м�Ӻ���ʾʱ�Ķ���
    private Animator createShowItemAnimator(View item) {
        float dx = circle_button.getX() - item.getX();
        float dy = circle_button.getY() - item.getY();
        item.setRotation(0f);// ��ת
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

    // �м�Ӻ�����ʱ�Ķ���
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

    // ��ʼ������ȡ�豸ID��appKey�������£���ȡ������ص���Ϣ����ҹ��Ļ���ʾ����ҹ��ģʽ
    private void init() {
        findView();

        String udid = JpushUitls.getImei(getApplicationContext(), "");// �豸ID
        if (null != udid)
            Log.i("Jpush", "IMEI: " + udid);

        String appKey = JpushUitls.getAppKey(getApplicationContext());// appKey
        if (null == appKey) appKey = "AppKey??";
        Log.i("AppKey", "AppKey" + appKey);

        // ������
        UpdateVersonTask updateVersonTask = new UpdateVersonTask();
        updateVersonTask.execute();

        // �������磬��ȡ������ص���Ϣ�����浽MsgCountBean������about_cricle�ؼ�����ʾ�������ٷ��͹㲥
        Read2Task read1Task = new Read2Task();
        read1Task.execute();

        // ҹ����ʾ����ҹ��ģʽ
        if (timeCompare()) {
            /**ҹ�����ߵ㵽������*/
            Log.i("ҹ��ģʽ", "night");
            if (!SharePreferencesUtils.getLightModel(MainActivity.this)) {
                DialogUtils.nightModelDialog(MainActivity.this, getResources().getString(R.string.night_model_title));
            }
        } else {
            Log.i("ҹ��ģʽ", "not_night");
        }
    }

    // �ж��Ƿ�ҹ��
    private boolean timeCompare() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        Log.i("ʱ��", "hour:" + hour);
        if (hour > 19 || hour < 6) {
            return true;
        } else {
            return false;
        }
    }

    // ���ؿؼ�
    private void findView() {
        about_cricle = (TextView) findViewById(R.id.about_cricle);// ������ص���Ϣ��ʾ
        menuLayout = (ClipRevealFrame) findViewById(R.id.menu_layout);// ����ʾ�������Զ���ȫ����ʾ�Ŀؼ�
        arcLayout = (ArcLayout) findViewById(R.id.arc_layout);// �Ӻŵ㿪��Ļ��β���
        circle_button = (Button) findViewById(R.id.circle_button);// �м�Բ�μӺŰ�ť
        circle_button.setOnClickListener(this);
        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(this);
        }
        tv_update = (TextView) findViewById(R.id.tv_update);// [�޸�����]
        tv_one = (TextView) findViewById(R.id.tv_one);// �ϴ���Ŀ
        tv_two = (TextView) findViewById(R.id.tv_two);// ��Ҫ��֤
        tv_three = (TextView) findViewById(R.id.tv_three);// ��Ҫǩ�����ѱ�����
        tv_four = (TextView) findViewById(R.id.tv_four);// ��������
        list = FloatBtnUtils.floatView(this, list);
        user_name = (TextView) findViewById(R.id.user_name);// �û����ǳ�
        rl_rootview = (RelativeLayout) findViewById(R.id.rl_rootview);// ��ײ�Ĳ���
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);// ����İ�ť
        login_bg = (RelativeLayout) findViewById(R.id.login_bg);// ���������
        to_person = (ImageView) findViewById(R.id.to_person);// ������е�ͷ��
        to_person.setOnClickListener(this);
        about_me = (LinearLayout) findViewById(R.id.about_me);// �������
        about_me.setOnClickListener(this);
        my_account = (LinearLayout) findViewById(R.id.my_account);// �ʽ��˻�
        my_account.setOnClickListener(this);
        my_collection = (LinearLayout) findViewById(R.id.my_collection);// �ҵ��ղ�
        my_collection.setOnClickListener(this);
        my_invest_finacing = (LinearLayout) findViewById(R.id.my_invest_finacing);// �ҵ�Ͷ����
        my_invest_finacing.setOnClickListener(this);
        share = (LinearLayout) findViewById(R.id.share);// �������
        share.setOnClickListener(this);
        about_us = (LinearLayout) findViewById(R.id.about_us);// ��������
        about_us.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        image = (PolygonImageView) findViewById(R.id.user_iamge);// Բ��ͷ��ؼ�
        image.setImageResource(R.drawable.user_loading);
        setting = (RelativeLayout) findViewById(R.id.setting);// ����
        setting.setOnClickListener(this);
        homepage = (RadioButton) findViewById(R.id.homepage);// ��ҳ
        road_show = (RadioButton) findViewById(R.id.road_show);// Ͷ����
        invest = (RadioButton) findViewById(R.id.invest);// ������
        mine = (RadioButton) findViewById(R.id.mine);// Ȧ��
        vpFragment = (ViewPager) findViewById(R.id.viewpager);
        vpFragment.setOffscreenPageLimit(4);// ȫ��Ԥ����
        MainViewPagerFragmentAdapter vpAdapter = new MainViewPagerFragmentAdapter(getSupportFragmentManager());// ��ҳViewPagerFragmentҳ�����
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

        // �������ʱ���������磬��ȡ���ݱ��浽MenuPersonBean�����ڲ������չʾ���ݣ������޸����ϰ�ť
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

        // �ѵ�¼����ʾ���������
        if (SharePreferencesUtils.getIsLogin(MainActivity.this)) {
            login_bg.setVisibility(View.VISIBLE);
        } else {
            login_bg.setVisibility(View.GONE);
        }

        // �򿪲������ʾ
        if (!SharePreferencesUtils.getIsToastMenu(MainActivity.this)) {
            DialogUtils.easyMenuToast(MainActivity.this, getResources().getString(R.string.toast_menu), DisplayUtils.convertDipOrPx(MainActivity.this, 29),
                    ScreenUtils.getStatusHeight(MainActivity.this) + DisplayUtils.convertDipOrPx(MainActivity.this, 43), circle_button);
        }
    }

    // ����EventBus�ķ�����Ϣ���������磬��ȡ������ص���Ϣ�����浽MsgCountBean������about_cricle�ؼ�����ʾ�������ٷ��͹㲥
    @Subscribe
    public void onEvent(MainNoticeEvent msg) {
        if (msg.getMsg().equals("receiver")) {
            Log.i("ע�⣡�յ���Ϣ����", "receiver");
            Read2Task read2Task = new Read2Task();
            read2Task.execute();
        }
    }

    // ����EventBus�ķ�����Ϣ���������磬��ȡ������ص���Ϣ�����浽MsgCountBean������about_cricle�ؼ�����ʾ�������ٷ��͹㲥
    @Subscribe
    public void onEventMainThread(NoticeDissmissEvent msgs) {
        if (msgs.getMain().equals("main")) {
            Log.i("ע�⣡���յ���Ϣ����", "main");
            Read2Task read2Task = new Read2Task();
            read2Task.execute();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_iamge:// ���ͷ�񣬹رղ��������ת����������ҳ��PersonActivity
                menuDrawer.closeMenu();
                intent = new Intent(MainActivity.this, PersonActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:// ������ã��رղ��������ת������ҳ��SettingActivity
                menuDrawer.closeMenu();
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.to_person:// ���ͷ������ı���ͼƬ���رղ��������ת����������ҳ��PersonActivity
                menuDrawer.closeMenu();
                intent = new Intent(MainActivity.this, PersonActivity.class);
                startActivity(intent);
                break;
            case R.id.about_me:// ���������أ��رղ��������ת���������ҳ��AboutMeActivity
                menuDrawer.closeMenu();
                intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
                break;
            case R.id.my_account:// ����ʽ��˻����رղ��������ת���ʽ��˻�ҳ��YibaoAccountActivity
                menuDrawer.closeMenu();
                // TODO: 2016/3/24 ���ж��û��Ƿ�������Ϣ����֤���ٻ�ȡ�û���Ϣ�����ж��Ƿ�ע���
                if (SharePreferencesUtils.getPerfectInformation(MainActivity.this)) {
                    if (!UiHelp.isLongFastClick()) {
                        UiHelp.ToastMessageShort(MainActivity.this, getResources().getString(R.string.loading));// ������
                        IsInvestTask2 isInvestTask2 = new IsInvestTask2();
                        isInvestTask2.execute();
                    }
                } else {// ����������Ϣ�Ի�����ʾ
                    DialogUtils.improveInformationDialog(MainActivity.this);
                }
                break;
            case R.id.my_collection:// ����ҵ��ղأ��رղ��������ת���ҵ��ղ�ҳ��MyCollectActivity
                menuDrawer.closeMenu();
                intent = new Intent(MainActivity.this, MyCollectActivity.class);
                startActivity(intent);
                break;
            case R.id.my_invest_finacing:// ����ҵ�Ͷ���ʣ��رղ��������ת���ҵ�Ͷ����ҳ��MyFinacingInvestActivity
                menuDrawer.closeMenu();
                intent = new Intent(MainActivity.this, MyFinacingInvestActivity.class);
                startActivity(intent);
                break;
            case R.id.share:// ���������ѣ��رղ��������������Ի���
                menuDrawer.closeMenu();
                if (!UiHelp.isLongFastClick()) {
                    if (share_loaded) {
                        DialogUtils.ShareDialog(MainActivity.this, homepage, shareUtils, shareBean.getTitle(),
                                shareBean.getContent(), shareBean.getImg(), shareBean.getUrl());
                    } else {// �������磬��ȡ�������ݣ����浽ProjectShareBean�� ��������Ի���
                        UiHelp.ToastMessageShort(MainActivity.this, getResources().getString(R.string.loading));
                        AppShareTask task = new AppShareTask();
                        task.execute();
                    }
                }
                break;
            case R.id.about_us:// ����������ǣ��رղ��������ת���ҵ�Ͷ����ҳ��AboutUsActivity
                if (!UiHelp.isFastClick()) {
                    intent = new Intent(MainActivity.this, AboutUsActivity.class);
                    startActivity(intent);
                    menuDrawer.closeMenu();
                }
                break;
            case R.id.back:
                menuDrawer.closeMenu();
                break;
            case R.id.houtai_btn:// �����̨���£�������̨���·���DownloadService���رո��½��ȿ�
                intent = new Intent(MainActivity.this, DownloadService.class);
                startService(intent);
                alertDialog1.dismiss();
                break;
            case R.id.circle_button:// ����м�Բ�μӺ�
                onFabClick(view);
                break;
            case R.id.one:// ����Ӻ��ٵ���ϴ���Ŀ���رռӺţ����ϴ���Ŀҳ��WantRoadShowActivity
                hideMenu();
                intent = new Intent(MainActivity.this, WantRoadShowActivity.class);
                startActivity(intent);
                break;
            case R.id.two:// ����Ӻ��ٵ����Ҫ��֤���رռӺţ��ж��Ƿ���������Ϣ�������Ի�����ʾ
                if (SharePreferencesUtils.getPerfectInformation(MainActivity.this)) {
                    hideMenu();
                    if (!UiHelp.isLongFastClick()) {
                        UiHelp.ToastMessageShort(MainActivity.this, getResources().getString(R.string.loading));// ������
                        IsInvestTask isInvestTask = new IsInvestTask();
                        isInvestTask.execute();
                    }
                } else {// ����������Ϣ�Ի�����ʾ
                    hideMenu();
                    DialogUtils.improveInformationDialog(MainActivity.this);
                }
                break;
            case R.id.three:// ��ȡ��
                if (SharePreferencesUtils.getIsLogin(MainActivity.this)) {
                    hideMenu();
                    intent = new Intent(MainActivity.this, WantSignInActivity.class);
                    startActivity(intent);
                } else {
                    hideMenu();
                    DialogUtils.loginDialog(MainActivity.this);
                }
                break;
            case R.id.four:// ����Ӻ��ٵ���������ģ��رռӺţ��򿪲����
                hideMenu();
                if (!UiHelp.isLongFastClick()) {
                    menuDrawer.toggleMenu();
                }
                break;
        }
    }

    // ����м�Բ�μӺţ���ʾ������
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

    // ��������˳�������
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
                sendBroadcast(intent1);// �����˳��㲥
                MobclickAgent.onKillProcess(MainActivity.this);
                MyApplication.getInstance().exit();//��������Activity ��finish
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
        Log.i("onResume", "���ܿ�����");
        super.onResume();
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
        // ��������ص���Ϣ����ʾ���������͹㲥
        showOrHind();
    }

    // ����Fragment���ݵ�����
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("�ٷ���ʱ�յ�����Ϣ", "requestCode" + requestCode);
        Fragment f = getSupportFragmentManager().findFragmentByTag("oder");
        f.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // ��ת����¼ҳ��LoginActivity
    private void loginAgain() {
        intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // �������磬��ȡ���ݱ��浽MenuPersonBean�����ڲ������չʾ����
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
                Log.i("�������Ϣ", body);
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

    // ��¼����
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
                    Log.i("��¼��Ϣ", body);
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
                SuperToastUtils.showSuperToast(MainActivity.this, 1, R.string.no_wifi);// ��������
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

    // �������񣬲鿴�Ƿ����°汾����ȡ���ݱ��浽UpdateVersonBean�У�����force�����ֵ��ʾ�Ƿ�ǿ�Ƹ���
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
                Log.i("�汾������Ϣ", body);
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
                                    BroadcastHandler.sendEmptyMessage(1);// ��ʾǿ�Ƹ���
                                } else {//?????
                                    BroadcastHandler.sendEmptyMessage(2);// ��ʾ����
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

    // ��ʾ���¶Ի���
    public void UpdateVersonDialog(Activity activity, String context) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_update_verson);
        dialog.setCanceledOnTouchOutside(true);// ����Ի�����ȡ��
        view.setWindowAnimations(R.style.dialog_zoom);  //??????
        TextView right_update = (TextView) view.findViewById(R.id.right_update);// ��������
        TextView give_up = (TextView) view.findViewById(R.id.give_up);// ���ķ���
        TextView update_context = (TextView) view.findViewById(R.id.update_context);// ��������
        final AlertDialog finalDialog = dialog;
        update_context.setText(context);
        right_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressBarDialog();// ��ʾ������
                new Thread() {
                    public void run() {
                        loadFile();
                    }// �����°�
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

    // ǿ�Ƹ��¶Ի��������°�
    public void MustUpdateDialog(Activity activity, String context) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_must_update);
        dialog.setCanceledOnTouchOutside(false);// �Ի���������������ʧ
        dialog.setCancelable(false);// ����ȡ��
        view.setWindowAnimations(R.style.dialog_zoom);  //����Ч��
        TextView right_update = (TextView) view.findViewById(R.id.right_update);// �������°�ť
        TextView update_context = (TextView) view.findViewById(R.id.update_context);// ��������
        final AlertDialog finalDialog = dialog;
        update_context.setText(context);
        right_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressBarDialog();// ��ʾ���½�����
                new Thread() {
                    public void run() {
                        loadFile();
                    }// ������app
                }.start();
                dialog.dismiss();
            }
        });
    }

    // ��ʾ���½��ȶԻ���
    private void showProgressBarDialog() {
        alertDialog1 = new AlertDialog.Builder(MainActivity.this, R.style.Translucent_NoTitle).create();
        alertDialog1.show();
        Window window = alertDialog1.getWindow();
        window.setContentView(R.layout.dialog_update_progress);
        window.setWindowAnimations(R.style.dialog_animator);  //??????
        houtai_btn = (TextView) window.findViewById(R.id.houtai_btn);// ��̨����
        numberProgressBar = (NumberProgressBar) window.findViewById(R.id.numberPro);
        numberProgressBar.setMax(100);
        houtai_btn.setOnClickListener(this);
        alertDialog1.setCanceledOnTouchOutside(false);// ����Ի����ⲻ��ȡ��
    }

    // ����jinzhts.apk�����سɹ�sendMsg(2, 0);���ɹ�sendMsg(-1, 0);
    public void loadFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// �ж�дȨ��
            Request request = new Request.Builder().url(updateVersonBean.getUrl()).build();
            Log.i("���µ�ַ",updateVersonBean.getUrl());
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
                    sendMsg(1, (int) (count * 100 / ints));// ��������
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

    // ��������ɹ�ʱhandler����sendMsg(2, 0)��ʧ��ʱ����sendMsg(-1, 0);
    private void sendMsg(int flag, int c) {
        Message msg = new Message();
        msg.what = flag;
        msg.arg1 = c;
        handler.sendMessage(msg);
    }

    // ��ʾ���ؽ��ȣ��ɹ���ֱ�Ӱ�װ��ʧ����ʾ�û�
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {//
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 1:// �������أ���ʾ����
                        loading_process = msg.arg1;
                        numberProgressBar.setProgress(loading_process);
                        break;
                    case 2:// ���سɹ�������ϵͳ��װӦ��
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(Environment
                                        .getExternalStorageDirectory(), "jinzhts.apk")),
                                "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case -1:// ����ʧ�ܣ���ʾ�û�
                        String error = msg.getData().getString("error");
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            super.handleMessage(msg);
        }
    };

    // �������¶Ի�����ʾ���»�ǿ�Ƹ��¡�msg.what == 1ǿ�Ƹ��£�msg.what == 2��ʾ����
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

    // �������磬��ȡ������ص���Ϣ�����浽MsgCountBean������about_cricle�ؼ�����ʾ�������ٷ��͹㲥
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
                Log.i("������ص���Ϣ", body);
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
                    // EventBus����������ص���Ϣ����
                    EventBus.getDefault().post(new MsgCount(aVoid.getData().getCount()));
                    showOrHind();
                }
            }
        }

    }

    // ��������ص���Ϣ����ʾ���������͹㲥
    private void showOrHind() {
        if (read_two > 0) {
            showRead();
            about_cricle.setText(read_two + "");
        } else {
            Log.i("������ص���Ϣ����", read_two + "count");
            hideRead();
        }
    }

    // ����������ص���Ϣ�ؼ�
    private void hideRead() {
        about_cricle.setVisibility(View.GONE);
    }

    // ��ʾ������ص���Ϣ�����������͹㲥
    private void showRead() {
        about_cricle.setVisibility(View.VISIBLE);
        UiHelp.showBroad(MainActivity.this);

    }

    // ��ҳViewPagerFragmentҳ�����
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
                    // ��HomePageFragment��ӵ�Activity�����ϲ�
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

    // ��֤�ɹ���ת���������ҳ��AboutMeActivity���������ݵ�AuthBean
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
                    Log.i("�Ƿ���Ͷ����", common.getData().getAuth() + "");
                    SharePreferencesUtils.setAuth(MainActivity.this, common.getData().getAuth() + "");
                    UiHelp.mainAuth(MainActivity.this, common.getData().getAuth() + "");
                }
            }
        }
    }

    // �������磬��ȡ�������ݣ����浽ProjectShareBean�� ��������Ի���
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
                Log.i("���������", body);
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
                SuperToastUtils.showSuperToast(MainActivity.this, 1, R.string.no_wifi);// ��ʾ��������
            }

        }
    }

    // TODO: 2016/3/24 ����ʽ��˻����ж��Ƿ����ڽ�ָͶ��֤
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
                    Log.i("��֤��", common.getData().getAuth() + "");
                    SharePreferencesUtils.setAuth(MainActivity.this, common.getData().getAuth() + "");
                    isAuth(common.getData().getAuth() + "");
                }
            }
        }
    }

    private void isAuth(String auth) {
        if (StringUtils.isEquals(auth, "")) {
            /**��δ��֤����������*/
            DialogUtils.showInvestDialogs(this);
        } else if (StringUtils.isEquals(auth, "null")) {
            /**�Ѿ���֤�������*/
            DialogUtils.waitAuthDialog(this);
        } else if (StringUtils.isEquals(auth, "true")) {
            /**�ɹ�*/
            GetInformationTask getInformationTasktask = new GetInformationTask();
            getInformationTasktask.execute();
        } else {
            DialogUtils.authFailDialog(this, getResources().getString(R.string.fail_auth), getResources().getString(R.string.crop__done));
            /**ʧ��*/
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
                    Log.i("��Ա��Ϣ", aVoid.getData().toString());
                    // ��ȡǩ��
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
            showProgressDialog("������...");
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
                Log.i("ǩ��", sign);
                // ��ѯ�Ƿ����ױ�ע���
                IsRegisteredTask isRegisteredTask = new IsRegisteredTask();
                isRegisteredTask.execute();
            }
        }
    }

    // ��ѯ�Ƿ����ױ�ע���
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
                    xStream.processAnnotations(YibaoUserInfoBean.class);// ָ����Ӧ��Bean
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
                Log.i("���صĲ�ѯ���", bean.toString());
                if ("1".equals(bean.getCode())) {
                    Log.i("������1", "��ע���");
                    // ����YibaoAccount2Activity
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
                    Log.i("������101", "ûע��");
                    // ����YibaoAccountActivity
                    intent = new Intent(MainActivity.this, YibaoAccountActivity.class);
                    intent.putExtra("uid", informationBean.getData().getUid() + "");
                    intent.putExtra("name", informationBean.getData().getName());
                    intent.putExtra("idNo", informationBean.getData().getIdno());
                    intent.putExtra("tel", informationBean.getData().getTel());
                    intent.putExtra("email", informationBean.getData().getEmail());
                    startActivity(intent);
                } else {
                    // ��ʾ�û�����
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