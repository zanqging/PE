package com.jinzht.pro.activity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.utils.*;

import java.io.IOException;

/**
 * 世上唯有贫穷可以不劳而获。
 * <p>
 * 在线反馈页面
 *
 * @auther Mr.Jobs
 * @date 2015/5/28
 * @time 9:15
 */

public class FeedbackActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.advice)
    EditText advice;// 反馈的意见

    @OnClick(R.id.back)
    void back() {
        this.finish();
    }

    @OnClick(R.id.send)
    void send() {
        if (StringUtils.isBlank(advice.getText().toString())) {
            UiHelp.ToastMessageShort(mContext, R.string.more_notice);
        } else {
            FreeBackTask freeBackTask = new FreeBackTask();
            freeBackTask.execute();
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText(getResources().getString(R.string.feed_back));
    }

    // 提价反馈信息
    private class FreeBackTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            String md5 = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = AsynOkUtils.doPushPost("advice", advice.getText().toString(), Constant.BASE_URL + Constant.PHONE + Constant.FREEBACK, mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("反馈返回数据", body);
                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
//                    dismissProgressDialog();
                    finish();
                } else {
//                    dismissProgressDialog();
                }
                SuperToastUtils.showSuperToast(FeedbackActivity.this, 1, aVoid.getMsg());
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