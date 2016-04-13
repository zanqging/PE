package com.jinzht.pro.activity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;

import butterknife.OnClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.utils.*;

import java.io.IOException;
import java.util.List;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 * <p>
 * �޸�����ҳ��
 *
 * @auther Mr.Jobs
 * @date 2015/6/15
 * @time 8:51
 */

public class ModifyActivity extends BaseActivity {

    private ModifyTask modifyTask;

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind({R.id.now_passwd_hint, R.id.new_passwd_first_hint, R.id.new_passwd_submit_hint})
    List<com.jinzht.pro.edittext.MaterialEditText> passwd_edit;// ���뵱ǰ���룬���������룬�ٴ���������

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @OnClick(R.id.modify_submit)
    void update() {
        if (StringUtils.isBlank(passwd_edit.get(0).getText().toString())) {
            SuperToastUtils.showSuperToast(ModifyActivity.this, 1, R.string.now_passwd_null);
        } else if (StringUtils.isBlank(passwd_edit.get(1).getText().toString())) {
            SuperToastUtils.showSuperToast(ModifyActivity.this, 1, R.string.new_passwd_null);
        } else if (StringUtils.isBlank(passwd_edit.get(2).getText().toString())) {
            SuperToastUtils.showSuperToast(ModifyActivity.this, 1, R.string.new_passwd_submit_null);
        } else if (!passwd_edit.get(1).getText().toString().equals(passwd_edit.get(2).getText().toString())) {
            SuperToastUtils.showSuperToast(ModifyActivity.this, 1, R.string.passwd_different);
        } else {
            modifyTask = new ModifyTask();
            modifyTask.execute();
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_modify;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText(getResources().getString(R.string.modify_title));
    }

    // �ύ������
    private class ModifyTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    String oldmd5 = MD5Utils.md5(passwd_edit.get(0).getText().toString().trim() + SharePreferencesUtils.getTelephone(mContext) + "lindyang");
                    String newmd5 = MD5Utils.md5(passwd_edit.get(1).getText().toString().trim() + SharePreferencesUtils.getTelephone(mContext) + "lindyang");
                    body = OkHttpUtils.doModifyPost("old", oldmd5, "new", newmd5, Constant.BASE_URL + Constant.PHONE + Constant.MODIFY, mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("�޸������ķ�����Ϣ", body);
                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid == null) {
                return;
            } else {
                if (aVoid.getCode() == 0) {
                    SharePreferencesUtils.setIsLogin(mContext, true);
                    String newmd5 = MD5Utils.md5(passwd_edit.get(1).getText().toString().trim() + SharePreferencesUtils.getTelephone(mContext) + "lindyang");
                    SharePreferencesUtils.saveInformation(mContext, SharePreferencesUtils.getTelephone(ModifyActivity.this), newmd5);
                    finish();
                }
                UiHelp.printMsg(aVoid.getCode(), aVoid.getMsg(), mContext);
            }
        }
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
}