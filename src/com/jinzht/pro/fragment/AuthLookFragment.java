package com.jinzht.pro.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.AuthPhotoLookActivity;
import com.jinzht.pro.model.AuthBean;
import com.jinzht.pro.model.InformationBean;
import com.jinzht.pro.utils.DialogUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.StringUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/30.
 * <p>
 * ��֤�鿴ҳ��
 */
public class AuthLookFragment extends BaseFragment {
    private Intent intent;
    private int flag = -1;
    GetInformationTask task;
    String img = "";

    @Bind({R.id.auth_one, R.id.auth_two, R.id.auth_three, R.id.auth_four, R.id.auth_five})
    List<TextView> textViews;// ��ʵ���������֤�š���ס��ַ����˾���ơ�ְλ
    @Bind(R.id.iv_identity)
    ImageView iv_identity;// ���֤
    @Bind({R.id.seal, R.id.iv_auth_image})
    List<ImageView> imageViews;// �Ƿ���֤ͨ���ı�ʶ��ˮӡ
    @Bind(R.id.tv_modify)
    TextView tv_modify;// �޸�
    @Bind(R.id.tv_auth_status)
    TextView tv_auth_status;// �����֤
    @Bind({R.id.lv_one, R.id.lv_two})
    List<LinearLayout> linearLayouts;// ��֤��Ϣ����û����֤

    // ����޸ģ�ѡ��Ͷ����
    @OnClick(R.id.tv_modify)
    void sss() {
        /**�޸�*/
        DialogUtils.showInvestDialogs(getActivity());
    }

    // �鿴���֤
    @OnClick(R.id.iv_identity)
    void ons() {
        if (img.equals("")) {
            return;
        }
        intent = new Intent(mContext, AuthPhotoLookActivity.class);
        intent.putExtra("img", img);
        startActivity(intent);
    }

    // �����֤��ѡ��Ͷ����
    @OnClick(R.id.tv_auth_status)
    void osd() {
        if (flag == 0) {
            DialogUtils.showInvestDialogs(getActivity());
        }
    }

    @Override
    protected int setLayout(LayoutInflater inflater) {
        return R.layout.fragment_look_auth;
    }

    // ��ȡ�û���֤��Ϣ�͸�����Ϣ���������
    @Override
    protected void onFirstUserVisible() {
        IsInvestTask isInvestTask = new IsInvestTask();
        isInvestTask.execute();
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    protected void onFirstUserInvisble() {
    }

    @Override
    protected void onUserVisble() {
    }

    @Override
    protected void init() {
    }

    @Override
    public void errorPage() {
    }

    @Override
    public void blankPage() {
    }

    @Override
    public void successRefresh() {
        super.successRefresh();
        IsInvestTask isInvestTask = new IsInvestTask();
        isInvestTask.execute();
    }

    // ��ȡ�û���Ϣ���������
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
                Log.i("�û���Ϣ", body);
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
                SuperToastUtils.showSuperToast(getActivity(), 1, R.string.no_wifi);
            } else {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    if (aVoid.getData().getIdno().length() != 0) {
                        if (aVoid.getData().getIdno().length() == 18) {
                            textViews.get(1).setText(aVoid.getData().getIdno().substring(0, 4) + "**********" + aVoid.getData().getIdno().substring(aVoid.getData().getIdno().length() - 4, aVoid.getData().getIdno().length()));
                        } else {
                            textViews.get(1).setText(aVoid.getData().getIdno().substring(0, 4) + "*******" + aVoid.getData().getIdno().substring(aVoid.getData().getIdno().length() - 4, aVoid.getData().getIdno().length()));
                        }
                    }
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
                    textViews.get(2).setText(aVoid.getData().getAddr());
                    textViews.get(3).setText(aVoid.getData().getCompany());
                    textViews.get(4).setText(aVoid.getData().getPosition());
                    img = aVoid.getData().getIdpic();
                    UpLoadImageUtils.getAuthImage(getActivity(), aVoid.getData().getIdpic(), iv_identity);
//                    UpLoadImageUtils.getImage(aVoid.getData().getIdpic(), iv_identity);
//                    AnimatorUtils.authLook(imageViews.get(1),imageViews.get(0));
                    imageViews.get(1).setImageResource(R.drawable.auth_iv1);
                    if (SharePreferencesUtils.getAuth(mContext).equals("null")) {
                        imageViews.get(0).setImageResource(R.drawable.authing);
                    } else if (SharePreferencesUtils.getAuth(mContext).equals("true")) {
                        imageViews.get(0).setImageResource(R.drawable.seal);
                    } else if (SharePreferencesUtils.getAuth(mContext).equals("false")) {
                        imageViews.get(0).setImageResource(R.drawable.auth_fail);
                    }
                }
            }
        }
    }

    // ��ȡ�Ƿ���֤
    private class IsInvestTask extends AsyncTask<Void, Void, AuthBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected AuthBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.ISINVESTOR, getActivity());
                } catch (Exception e) {
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
                    if (!SharePreferencesUtils.contains(getActivity(), "telephone") && !SharePreferencesUtils.contains(getActivity(), "passwd")
                            && SharePreferencesUtils.getTelephone(getActivity()).equals("") && SharePreferencesUtils.getPassWd(mContext).equals("")) {
                        loginAgain();
                    } else {
                        LoginTask loginTask = new LoginTask();
                        loginTask.execute();
                    }
                    return;
                }
                if (common.getCode() == 0) {
                    Log.i("��֤��Ϣ", common.getData().getAuth() + "");
                    SharePreferencesUtils.setAuth(mContext, common.getData().getAuth() + "");
                    if (StringUtils.isEquals(common.getData().getAuth() + "", "")) {
                        flag = 0;
                        twoVisible();
                        tv_modify.setVisibility(View.GONE);
                        tv_auth_status.setText(getResources().getString(R.string.auth_look));
                    } else if (StringUtils.isEquals(common.getData().getAuth() + "", "null")) {
                        oneVisible();
                        tv_modify.setVisibility(View.VISIBLE);
                        GetInformationTask getInformationTask = new GetInformationTask();
                        getInformationTask.execute();
                    } else if (StringUtils.isEquals(common.getData().getAuth() + "", "true")) {
                        oneVisible();
                        tv_modify.setVisibility(View.GONE);
                        GetInformationTask getInformationTask = new GetInformationTask();
                        getInformationTask.execute();
                    } else if (StringUtils.isEquals(common.getData().getAuth() + "", "false")) {
                        oneVisible();
                        tv_modify.setVisibility(View.VISIBLE);
                        GetInformationTask getInformationTask = new GetInformationTask();
                        getInformationTask.execute();
                    }
                }
            } else {
                twoVisible();
                tv_auth_status.setText(getResources().getString(R.string.look_error));
            }
        }
    }

    // �Ѿ���֤�Ľ���
    private void oneVisible() {
        linearLayouts.get(0).setVisibility(View.VISIBLE);
        linearLayouts.get(1).setVisibility(View.GONE);
    }

    // ��û����֤����
    private void twoVisible() {
        linearLayouts.get(0).setVisibility(View.GONE);
        linearLayouts.get(1).setVisibility(View.VISIBLE);
    }
}