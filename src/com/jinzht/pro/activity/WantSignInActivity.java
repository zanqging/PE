package com.jinzht.pro.activity;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.application.MyApplication;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.model.SignInBean;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.StringUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.TimeUtils;
import com.jinzht.pro.view.RushBuyCountDownTimerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * 签到页，已弃用
 *
 * @auther Mr.Jobs
 * @date 2015/7/23
 * @time 17:09
 */

public class WantSignInActivity extends BaseActivity {

    private LocationClient mLocationClient;
    LatLng pt_start, pt_end;
    public MyLocationListener mMyLocationListener;
    private CommonBean commons;
    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private String tempcoor = "gcj02";
    private SignInBean signInBean;
    private boolean can_sign;

    Timer timer = new Timer();

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind({R.id.your_position, R.id.aim_postion, R.id.distance, R.id.sign_status})
    List<TextView> textViewList;
    @Bind(R.id.timerView)
    RushBuyCountDownTimerView timerView;
    @Bind(R.id.sign_in_btn)
    TextView sign_in_btn;
    @Bind({R.id.sign_wave, R.id.iv_loaction})
    List<ImageView> imageViews;
    @Bind({R.id.time_title, R.id.time_context})
    List<TextView> time_list;

    @OnClick(R.id.your_position)
    void dian() {
        location();
    }

    private CommonBean common;

    @OnClick(R.id.sign_in_btn)
    void sign_in() {
        if (StringUtils.isBlank(textViewList.get(0).getText().toString())) {
            SuperToastUtils.showSuperToast(WantSignInActivity.this, 1, R.string.no_location);
        } else if (signInBean == null) {
            SuperToastUtils.showSuperToast(WantSignInActivity.this, 1, R.string.no_wifi);
        } else if (signInBean.getSeconds() <= 0.0) {
            SuperToastUtils.showSuperToast(WantSignInActivity.this, 1, R.string.no_time_sign);
        } else if (!can_sign) {
            SuperToastUtils.showSuperToast(WantSignInActivity.this, 1, R.string.distance_not);
        } else {
            SignInTask signInTask = new SignInTask();
            signInTask.execute();
        }
    }

    @OnClick(R.id.get_loaction)
    void get_loaction() {
        location();
    }

    private void location() {
        ObjectAnimator ob1 = ObjectAnimator.ofFloat(imageViews.get(1), "rotationY", -720f, 0f).setDuration(1500);
        ob1.start();
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        } else {
            mLocationClient.requestLocation();
        }
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.acitivity_sign_in;
    }

    @Override
    protected void init() {

        RippleUtils.rippleNormal(back);
        timer.scheduleAtFixedRate(new MyTask(), 1, 1000);
        ObjectAnimator ob2 = ObjectAnimator.ofFloat(imageViews.get(0), "rotationX", -360f, 0f).setDuration(1500);
        ob2.setRepeatCount(100);
        ob2.start();
        title.setText(getResources().getString(R.string.want_sign_in));
        mLocationClient = ((MyApplication) this.getApplication()).mLocationClient;
        try {
            SDKInitializer.initialize(getApplicationContext());
        } catch (Exception e) {

        }
        InitLocation();
        GetSignInTask getSignInTask = new GetSignInTask();
        getSignInTask.execute();
    }

    @Override
    public void errorPage() {

    }

    @Override
    public void blankPage() {

    }

    @Override
    public void successRefresh() {

    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setLocationMode(tempMode);//设置定位模式
        option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
        option.setScanSpan(200);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }


    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // 构建 route搜索参数以及策略，起终点也可以用name构造
            Log.e("ss", location.getCity() + location.getDistrict() + location.getLatitude() + location.getLongitude() + location.getStreet() + location.getStreetNumber() + location.getSatelliteNumber());
            textViewList.get(0).setText(location.getProvince() + location.getCity() + location.getDistrict() + location.getStreet());
            if (Math.abs(DistanceUtil.getDistance(pt_start, pt_end)) <= 1000) {
                can_sign = true;
            }

            SuperToastUtils.showSuperToast(WantSignInActivity.this, 1, location.getAddrStr());
            pt_start = new LatLng(location.getLatitude(), location.getLongitude());
            textViewList.get(2).setText((int) Math.abs(DistanceUtil.getDistance(pt_start, pt_end)) + getResources().getString(R.string.mi));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerView.stop();
        timer.cancel();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    SuperToastUtils.showSuperToast(WantSignInActivity.this, 1, R.string.not_time);
                    break;
                case 1:
                    updateTime();
                    break;
            }
        }

    };

    private void updateTime() {
        try {
            String ss = TimeUtils.timeTitle(WantSignInActivity.this);
            String[] time = ss.split(" ");
            time_list.get(0).setText(time[0] + time[1]);
            time_list.get(1).setText(time[2]);
            Log.e(TAG, ss);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private class GetSignInTask extends AsyncTask<Void, Void, SignInBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected SignInBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.GETSIGIN, mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    if (jsonObject.getInt("status") == -1) {
                        handler.sendEmptyMessage(100);
                        return null;
                    }
                    if (!jsonObject.has("data")) {
                        commons = new CommonBean();
                        return null;
                    }
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    signInBean = new SignInBean(jsonObject1.optDouble("latitude"), jsonObject1.optDouble("longitude"), jsonObject1.optDouble("seconds"), jsonObject1.optInt("id"), jsonObject1.optString("coordinate"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return signInBean;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(SignInBean common) {
            super.onPostExecute(common);
            if (common == null) {
                if (commons == null) {
                } else {
                    SuperToastUtils.showSuperToast(WantSignInActivity.this, 1, commons.getMsg());
                    textViewList.get(3).setText(commons.getMsg());
                }
                dismissProgressDialog();

                return;
            } else {
                textViewList.get(1).setText(common.getCoordinate());
                if (common.getSeconds() < 0) {
                    textViewList.get(3).setText(getResources().getString(R.string.not_sign_time));
                } else if (common.getSeconds() > 0) {
                    long different = (long) common.getSeconds() * 1000;
                    long secondsInMilli = 1000;
                    long minutesInMilli = secondsInMilli * 60;
                    long hoursInMilli = minutesInMilli * 60;
                    long daysInMilli = hoursInMilli * 24;
                    long elapsedDays = different / daysInMilli;
                    different = different % daysInMilli;
                    long elapsedHours = different / hoursInMilli;
                    different = different % hoursInMilli;
                    long elapsedMinutes = different / minutesInMilli;
                    different = different % minutesInMilli;
                    long elapsedSeconds = different / secondsInMilli;
                    Log.e(TAG, "hour" + elapsedHours + "minutes:" + elapsedMinutes + "second:" + elapsedSeconds);
                    timerView.setTime((int) elapsedHours, (int) elapsedMinutes, (int) elapsedSeconds);
                    // 开始倒计时
                    timerView.start();
                }
                pt_end = new LatLng(common.getLatitude(), common.getLongitude());
                Log.e(TAG, "pts" + pt_start + "pten" + pt_end + "distacne:" + DistanceUtil.getDistance(pt_start, pt_end));
                dismissProgressDialog();
            }
        }
    }

    private class SignInTask extends AsyncTask<Void, Void, CommonBean> {

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    if (signInBean == null) {
                        return null;
                    }
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.SIGNIN + signInBean.getId() + "/", mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean common) {
            super.onPostExecute(common);
            if (common != null) {
                if (common.getCode() == -1) {
                    return;
                }
                if (common.getCode() == 0) {
                    textViewList.get(3).setText(getResources().getString(R.string.sign_suss));
                }
                SuperToastUtils.showSuperToast(WantSignInActivity.this, 1, common.getMsg());
            }
        }
    }

}