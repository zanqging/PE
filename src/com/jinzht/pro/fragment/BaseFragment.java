package com.jinzht.pro.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.LoginActivity;
import com.jinzht.pro.callback.ErrorException;
import com.jinzht.pro.callback.LoginAgainCallBack;
import com.jinzht.pro.callback.ProgressBarCallBack;
import com.jinzht.pro.model.LoginBean;
import com.jinzht.pro.utils.ACache;
import com.jinzht.pro.utils.AsynOkUtils;
import com.jinzht.pro.utils.DisplayUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpException;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.view.LoadingProssbar;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * @auther Mr Jobs
 * @date 2015/5/17
 * @time 14:37
 */
public abstract class BaseFragment extends Fragment implements ProgressBarCallBack, ErrorException, LoginAgainCallBack {
    //    public  View mMainView;
    protected Context mContext;
    protected ACache aCache;
    private final String className = getClass().getName();
    protected final String TAG = className.substring(className.lastIndexOf(".") + 1, className.length());
    private boolean isPrepared;
    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    public int index = 0;
    public int top = 0;
    LoadingProssbar dialog;
    OkHttpException okHttpException = new OkHttpException(this);

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //��Fragment��Activity��������ʱ���á�
        mContext = activity.getApplicationContext();
        aCache = ACache.get(mContext);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected abstract int setLayout(LayoutInflater inflater);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //fragment���ز��ֵ�ʱ�����
        View mMainView = inflater.inflate(setLayout(inflater), container, false);
        ButterKnife.bind(this, mMainView);
        init();
        return mMainView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initprepare();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen"); //ͳ��ҳ��
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisble();
            if (SharePreferencesUtils.getLightModel(getActivity())) {
                if (!DisplayUtils.isAutoBrightness(getActivity())) {
                    DisplayUtils.startAutoBrightness(getActivity());
                }
            }
        }
    }

    public synchronized void initprepare() {
        //�ֳɰ�ȫ
        if (isPrepared) {
            onFirstUserVisible();
        } else
            isPrepared = true;
    }

    protected abstract void onFirstUserVisible();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initprepare();
            } else
                onUserVisble();
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisble();
            } else
                onUserInvisible();
        }
    }

    protected abstract void onUserInvisible();

    protected abstract void onFirstUserInvisble();

    protected abstract void onUserVisble();


    protected abstract void init();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
//        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }

    // 显示加载中对话框
    @Override
    public void showLoadingProgressDialog() {
        this.showProgressDialog(getResources().getString(R.string.loading));
    }

    // 显示加载中对话框
    @Override
    public void showProgressDialog(String message) {
        if (dialog == null) {
            dialog = new LoadingProssbar(getActivity(), message, R.anim.loading_anim);
            dialog.show();
        } else {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (this.dialog != null) {
            dialog.dismiss();
        }
    }


    public class LoginTask extends AsyncTask<Void, Void, LoginBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected LoginBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = AsynOkUtils.doNewLoginPost("tel", SharePreferencesUtils.getTelephone(mContext),
                            "passwd", SharePreferencesUtils.getPassWd(mContext), "regid", JPushInterface.getRegistrationID(mContext), "version", getResources().getString(R.string.verson_name),
                            Constant.BASE_URL + Constant.PHONE + Constant.LOGIN, mContext);
                    Log.e("Main", body);
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
                return;
            } else {
                if (aVoid.getCode() == 0) {
                    UiHelp.printMsg(aVoid.getCode(), aVoid.getMsg(), mContext);
                    SharePreferencesUtils.setIsLogin(mContext, true);
//                    SharePreferencesUtils.saveInformation(MainActivity.this, login_edit.get(0).getText().toString(), md51);
                    SharePreferencesUtils.setPerfectInformation(mContext, aVoid.getData().getInfo());
                    SharePreferencesUtils.setAuth(mContext, aVoid.getData().getAuth() + "");
                    successRefresh();
                } else if (aVoid.getCode() == -1) {
                    SharePreferencesUtils.setIsLogin(mContext, false);
                    loginAgain();
                } else {
                    SharePreferencesUtils.setIsLogin(mContext, false);
                    loginAgain();
                }
            }
        }
    }

    @Override
    public void successRefresh() {

    }

    public void loginAgain() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}