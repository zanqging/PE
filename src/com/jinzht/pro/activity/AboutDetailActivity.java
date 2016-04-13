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
 * 世上唯有贫穷可以不劳而获。
 * <p>
 * 隐私政策页，访问网络，获取数据保存到DataBean，再展示出内容
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

        // 访问网络，获取数据保存到DataBean，再展示出内容
        FreeBackTask task = new FreeBackTask();
        task.execute();

        switch (getIntent().getExtras().getInt("detail")) {
            case 0://关于路演
                context.setText(ResourceUtils.getFromAssets(mContext, "about_roadshow.txt"));
                break;
            case 1://关于众筹
//                context.setText(ResourceUtils.geFileFromAssets(mContext,"crowdfunding.txt"));
                context.setText(ResourceUtils.getFromAssets(mContext, "crowdfunding.txt"));
                break;
            case 2://领投跟头
//                context.setText(ResourceUtils.geFileFromAssets(mContext,"lingtou.txt"));
                context.setText(ResourceUtils.getFromAssets(mContext, "lingtou.txt"));
                break;
            case 3://投资认证时候的
                context.setText(ResourceUtils.getFromAssets(mContext, "risk_tips.txt"));
                break;
            case 4://设置里的隐私政策
                context.setText(ResourceUtils.getFromAssets(mContext, "privacy_policy.txt"));
                break;
            case 5://用户协议，注册第二部
                context.setText(ResourceUtils.getFromAssets(mContext, "user_agreement.txt"));
                break;
        }
    }

    // 访问网络，获取数据保存到DataBean，再展示出内容
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
                Log.i("协议", body);
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