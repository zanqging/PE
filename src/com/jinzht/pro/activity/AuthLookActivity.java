package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.AuthBean;
import com.jinzht.pro.model.InformationBean;
import com.jinzht.pro.utils.AnimatorUtils;
import com.jinzht.pro.utils.DialogUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.StringUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * 用户信息页，上传信息，没有用到该页面
 *
 * @auther Mr Jobs
 * @date 2015/11/30
 * @time 21:25
 */
public class AuthLookActivity extends BaseActivity {

    private Intent intent;
    @Bind({R.id.auth_one, R.id.auth_two, R.id.auth_three, R.id.auth_four, R.id.auth_five})
    List<TextView> textViews;// 五个输入框
    @Bind(R.id.iv_identity)
    ImageView iv_identity;// 身份证照片
    @Bind({R.id.seal, R.id.iv_auth_image})
    List<ImageView> imageViews;// 进度条；身份证预览图
    GetInformationTask task;// 读取用户信息任务
    String img = "";
    private int flag = -1;
    @Bind(R.id.tv_modify)
    TextView tv_modify;// 修改

    // 点击修改，弹出选择投资人类型对话框
    @OnClick(R.id.tv_modify)
    void sss() {
        /**修改*/
        DialogUtils.showInvestDialogs(AuthLookActivity.this);
    }

    // 点击身份，跳转至图片查看页面，AuthPhotoLookActivity
    @OnClick(R.id.iv_identity)
    void ons() {
        if (img.equals("")) {
            return;
        }
        intent = new Intent(mContext, AuthPhotoLookActivity.class);
        intent.putExtra("img", img);
        startActivity(intent);
    }


    @Override
    protected int getResourcesId() {
        return R.layout.activity_look_auth;
    }

    // 验证并查看用户信息
    @Override
    protected void init() {
        IsInvestTask isInvestTask = new IsInvestTask();
        isInvestTask.execute();
    }

    @Override
    public void errorPage() {

    }

    @Override
    public void blankPage() {

    }

    // 访问用户基本信息，保存到InformationBean，并显示
    private class GetInformationTask extends AsyncTask<Void, Void, InformationBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected InformationBean doInBackground(Void... voids) {

            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.GETINFORMATION, mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, InformationBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(InformationBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid == null) {
                SuperToastUtils.showSuperToast(AuthLookActivity.this, 1, R.string.no_wifi);
            } else {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    // 显示身份证号***
                    if (aVoid.getData().getIdno().length() != 0) {
                        if (aVoid.getData().getIdno().length() == 18) {
                            textViews.get(1).setText(aVoid.getData().getIdno().substring(0, 4) + "**********" + aVoid.getData().getIdno().substring(aVoid.getData().getIdno().length() - 4, aVoid.getData().getIdno().length()));
                        } else {
                            textViews.get(1).setText(aVoid.getData().getIdno().substring(0, 4) + "*******" + aVoid.getData().getIdno().substring(aVoid.getData().getIdno().length() - 4, aVoid.getData().getIdno().length()));
                        }
                    }
                    // 显示姓名***
                    if (aVoid.getData().getName().length() != 0) {
                        if (aVoid.getData().getName().length() <= 2) {
                            textViews.get(0).setText(aVoid.getData().getName().substring(0, 1) + "*");
                        } else {
                            String ss = "";
                            for (int i = 0; i < aVoid.getData().getName().length() - 2; i++) {
                                ss = "*" + ss;
                            }
                            textViews.get(0).setText(aVoid.getData().getName().substring(0, 1) + ss + aVoid.getData().getName().substring(aVoid.getData().getName().length() - 1, aVoid.getData().getName().length()));
                        }
                    }
                    textViews.get(2).setText(aVoid.getData().getAddr());// 显示公司地址
                    textViews.get(3).setText(aVoid.getData().getCompany());// 显示公司名
                    textViews.get(4).setText(aVoid.getData().getPosition());// 显示职位
                    img = aVoid.getData().getIdpic();// 身份证照片
                    UpLoadImageUtils.getAuthImage(AuthLookActivity.this, aVoid.getData().getIdpic(), iv_identity);// 上传身份证照片
//                    UpLoadImageUtils.getImage(aVoid.getData().getIdpic(), iv_identity);
                    AnimatorUtils.authLook(imageViews.get(1), imageViews.get(0));// 上传过程中的进度条和预览图动画
                }
            }
        }
    }

    // 访问网络，保存数据到AuthBean，有数据时执行GetInformationTask
    private class IsInvestTask extends AsyncTask<Void, Void, AuthBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected AuthBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(AuthLookActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.ISINVESTOR, AuthLookActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return FastJsonTools.getBean(body, AuthBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(AuthBean common) {
            super.onPostExecute(common);
            dismissProgressDialog();
            if (common != null) {
                if (common.getCode() == -1) {
                    if (!SharePreferencesUtils.contains(AuthLookActivity.this, "telephone") && !SharePreferencesUtils.contains(AuthLookActivity.this, "passwd")
                            && SharePreferencesUtils.getTelephone(AuthLookActivity.this).equals("") && SharePreferencesUtils.getPassWd(mContext).equals("")) {
                        loginAgain();
                    } else {
                        LoginTask loginTask = new LoginTask();
                        loginTask.execute();
                    }
                    return;
                }
                if (common.getCode() == 0) {
//                    Log.e(TAG,"ismyproject");
                    Log.e("ss", common.getData().getAuth() + "");
                    SharePreferencesUtils.setAuth(mContext, common.getData().getAuth() + "");
                    if (StringUtils.isEquals(common.getData().getAuth() + "", "")) {
                        flag = 0;
                        tv_modify.setVisibility(View.GONE);
                    } else if (StringUtils.isEquals(common.getData().getAuth() + "", "null")) {
                        tv_modify.setVisibility(View.VISIBLE);
                        GetInformationTask getInformationTask = new GetInformationTask();
                        getInformationTask.execute();
                    } else if (StringUtils.isEquals(common.getData().getAuth() + "", "true")) {
                        tv_modify.setVisibility(View.GONE);
                        GetInformationTask getInformationTask = new GetInformationTask();
                        getInformationTask.execute();
                    } else if (StringUtils.isEquals(common.getData().getAuth() + "", "false")) {
                        tv_modify.setVisibility(View.GONE);
                    }
                }
            } else {

            }
        }
    }

}