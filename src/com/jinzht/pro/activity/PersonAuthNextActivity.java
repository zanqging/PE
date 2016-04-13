package com.jinzht.pro.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.utils.*;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 * <p>
 * 个人投资者认证后过场界面
 *
 * @auther Mr Jobs
 * @date 2016/1/14
 * @time 22:07
 */
public class PersonAuthNextActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_pass)
    TextView tvPass;// 跳过
    @Bind(R.id.ed_send_word)
    EditText edSendWord;// 寄语
    @Bind(R.id.ed_invest_project)
    EditText edInvestProject;// 投资规划
    @Bind(R.id.ed_pro_invest_case)
    TextView edProInvestCase;// 投资案例
    @Bind(R.id.tv_post_information)
    TextView tvPostInformation;// 提交资料

    // 点击跳过
    @OnClick(R.id.tv_pass)
    void tv_pass() {
        DialogUtils.authSuccessDialog(PersonAuthNextActivity.this, getResources().getString(R.string.auth_success));
    }

    // 点击提交资料
    @OnClick(R.id.tv_post_information)
    void tv() {
        PostTask postTask = new PostTask();
        postTask.execute();
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_person_auth_two;
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

    // 提交资料到服务端
    private class PostTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(PersonAuthNextActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = AsynOkUtils.doPushPost("signature", edSendWord.getText().toString(), "investplan", edInvestProject.getText().toString(),
                            "investcase", edProInvestCase.getText().toString(),
                            Constant.BASE_URL + Constant.PHONE + Constant.PERSON_AUTH_NEXT, mContext);
                } catch (Exception e) {
                    okHttpException.httpException(e);
                    e.printStackTrace();
                }
                Log.i("资料提交后的返回值", body);
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
            }
            if (aVoid.getCode() == -1) {
                return;
            }
            if (aVoid.getCode() == 0) {
                /**此处成功了改干什么*/
//                SharePreferencesUtils.setPerfectInformation(mContext,true);
                /**这一块的对话框到底调到哪里暂时还没有定*/
                DialogUtils.authSuccessDialog(PersonAuthNextActivity.this, aVoid.getMsg());
            } else {
                UiHelp.printMsg(aVoid.getCode(), aVoid.getMsg(), mContext);
            }
        }
    }
}