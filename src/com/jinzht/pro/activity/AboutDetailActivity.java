package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.DataBean;
import com.jinzht.pro.utils.AsynOkUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.ResourceUtils;
import com.jinzht.pro.utils.SuperToastUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 * <p>
 * ��˽����ҳ���������磬��ȡ���ݱ��浽DataBean����չʾ������
 *
 * @auther Mr.Jobs
 * @date 2015/6/2
 * @time 9:32
 */

public class AboutDetailActivity extends BaseActivity {

    public static final String ENCODING = "UTF-8";
    private Intent intent;

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.context)
    TextView context;

    @OnClick(R.id.back)
    void back() {
        this.finish();
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_about_detail;
    }

    @Override
    protected void init() {
        title.setText(getIntent().getExtras().getString("title"));

        // �������磬��ȡ���ݱ��浽DataBean����չʾ������
        FreeBackTask task = new FreeBackTask();
        task.execute();

        switch (getIntent().getExtras().getInt("detail")) {
            case 0://����·��
                context.setText(ResourceUtils.getFromAssets(mContext, "about_roadshow.txt"));
                break;
            case 1://�����ڳ�
//                context.setText(ResourceUtils.geFileFromAssets(mContext,"crowdfunding.txt"));
                context.setText(ResourceUtils.getFromAssets(mContext, "crowdfunding.txt"));
                break;
            case 2://��Ͷ��ͷ
//                context.setText(ResourceUtils.geFileFromAssets(mContext,"lingtou.txt"));
                context.setText(ResourceUtils.getFromAssets(mContext, "lingtou.txt"));
                break;
            case 3://Ͷ����֤ʱ���
                context.setText(ResourceUtils.getFromAssets(mContext, "risk_tips.txt"));
                break;
            case 4://���������˽����
                context.setText(ResourceUtils.getFromAssets(mContext, "privacy_policy.txt"));
                break;
            case 5://�û�Э�飬ע��ڶ���
                context.setText(ResourceUtils.getFromAssets(mContext, "user_agreement.txt"));
                break;
        }
    }

    // �������磬��ȡ���ݱ��浽DataBean����չʾ������
    private class FreeBackTask extends AsyncTask<Void, Void, DataBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected DataBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = AsynOkUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.PRINOTICE, mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("Э��", body);
                return FastJsonTools.getBean(body, DataBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(DataBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    context.setText(aVoid.getData());
                } else {
                    SuperToastUtils.showSuperToast(AboutDetailActivity.this, 1, aVoid.getMsg());
                }
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