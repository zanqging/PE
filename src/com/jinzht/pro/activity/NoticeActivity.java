package com.jinzht.pro.activity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.NoticeBean;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.SuperToastUtils;

import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 世上唯有贫穷可以不劳而获。
 * <p>
 * 各种协议界面
 *
 * @auther Mr.Jobs
 * @date 2015/8/20
 * @time 16:53
 */

public class NoticeActivity extends BaseActivity {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;

    @OnClick(R.id.back)
    void finsh() {
        finish();
    }

    @Bind(R.id.context)
    TextView context;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_notice;
    }

    @Override
    protected void init() {
        try {
            title.setText(getIntent().getStringExtra("title"));
        } catch (Exception e) {

        }

        // 涟漪效果
        RippleUtils.rippleNormal(back);
        // 访问网络，显示数据
        NormalTask task = new NormalTask();
        task.execute();
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


    // 访问网络，显示数据
    private class NormalTask extends AsyncTask<Void, Void, NoticeBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected NoticeBean doInBackground(Void... voids) {
            String body = "";
            String md5 = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //有网
                try {
                    if (getIntent().getExtras().getInt("flag") == 1) {
                        body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.REGISTERXY, mContext);
                    } else if (getIntent().getExtras().getInt("flag") == 2) {
                        body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.AUTHNOTICE, mContext);
                    } else if (getIntent().getExtras().getInt("flag") == 3) {
                        body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.ROADNOTICE, mContext);
                    } else if (getIntent().getExtras().getInt("flag") == 4) {
                        body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.PRINOTICE, mContext);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, NoticeBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(NoticeBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid == null) {
                dismissProgressDialog();
                SuperToastUtils.showSuperToast(NoticeActivity.this, 1, R.string.no_wifi);
                return;
            } else {
                if (aVoid.getStatus() == 0) {
                    context.setText(aVoid.getData());
                    dismissProgressDialog();
                } else {
                    dismissProgressDialog();
                }
//                SuperToastUtils.showSuperToast(NoticeActivity.this, 1, aVoid.getMsg());
            }
        }
    }
}