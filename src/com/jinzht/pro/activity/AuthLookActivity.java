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
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * �û���Ϣҳ���ϴ���Ϣ��û���õ���ҳ��
 *
 * @auther Mr Jobs
 * @date 2015/11/30
 * @time 21:25
 */
public class AuthLookActivity extends BaseActivity {

    private Intent intent;
    @Bind({R.id.auth_one, R.id.auth_two, R.id.auth_three, R.id.auth_four, R.id.auth_five})
    List<TextView> textViews;// ��������
    @Bind(R.id.iv_identity)
    ImageView iv_identity;// ���֤��Ƭ
    @Bind({R.id.seal, R.id.iv_auth_image})
    List<ImageView> imageViews;// �����������֤Ԥ��ͼ
    GetInformationTask task;// ��ȡ�û���Ϣ����
    String img = "";
    private int flag = -1;
    @Bind(R.id.tv_modify)
    TextView tv_modify;// �޸�

    // ����޸ģ�����ѡ��Ͷ�������ͶԻ���
    @OnClick(R.id.tv_modify)
    void sss() {
        /**�޸�*/
        DialogUtils.showInvestDialogs(AuthLookActivity.this);
    }

    // �����ݣ���ת��ͼƬ�鿴ҳ�棬AuthPhotoLookActivity
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

    // ��֤���鿴�û���Ϣ
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

    // �����û�������Ϣ�����浽InformationBean������ʾ
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
                    // ��ʾ���֤��***
                    if (aVoid.getData().getIdno().length() != 0) {
                        if (aVoid.getData().getIdno().length() == 18) {
                            textViews.get(1).setText(aVoid.getData().getIdno().substring(0, 4) + "**********" + aVoid.getData().getIdno().substring(aVoid.getData().getIdno().length() - 4, aVoid.getData().getIdno().length()));
                        } else {
                            textViews.get(1).setText(aVoid.getData().getIdno().substring(0, 4) + "*******" + aVoid.getData().getIdno().substring(aVoid.getData().getIdno().length() - 4, aVoid.getData().getIdno().length()));
                        }
                    }
                    // ��ʾ����***
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
                    textViews.get(2).setText(aVoid.getData().getAddr());// ��ʾ��˾��ַ
                    textViews.get(3).setText(aVoid.getData().getCompany());// ��ʾ��˾��
                    textViews.get(4).setText(aVoid.getData().getPosition());// ��ʾְλ
                    img = aVoid.getData().getIdpic();// ���֤��Ƭ
                    UpLoadImageUtils.getAuthImage(AuthLookActivity.this, aVoid.getData().getIdpic(), iv_identity);// �ϴ����֤��Ƭ
//                    UpLoadImageUtils.getImage(aVoid.getData().getIdpic(), iv_identity);
                    AnimatorUtils.authLook(imageViews.get(1), imageViews.get(0));// �ϴ������еĽ�������Ԥ��ͼ����
                }
            }
        }
    }

    // �������磬�������ݵ�AuthBean��������ʱִ��GetInformationTask
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