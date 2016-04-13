package com.jinzht.pro.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.*;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;

import butterknife.Bind;
import butterknife.OnClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.eventbus.CircleRefreshEvent;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.model.NewsShareBean;
import com.jinzht.pro.utils.*;

import de.greenrobot.event.EventBus;

import java.io.IOException;

/**
 * Created by Administrator on 2015/9/11.
 * <p>
 * 新三板和圈子中的新闻详情网页
 */
public class ThreeWebviewActivity extends BaseActivity {

    @OnClick(R.id.back)
    void back() {
        this.finish();
    }

    private Intent intent;
    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.my_webview)
    WebView webView;
    private AlertDialog dialog;
    private NewsShareBean.DataEntity newsBean;
    private TextView tv_cancle, tv_send, tv_share;
    private EditText ed_share;// 分享时自己写的内容
    private ImageView iv_share;// 分享的新闻图片
    private ShareUtils shareUtils;
    private boolean share_loaded = false;

    // 点击右上角分享图标，如果已读取到新闻内容，直接弹出分享对话框，没有的话先访问网络，读取新闻内容，再弹出分享对话框
    @OnClick({R.id.share})
    void onCli(RelativeLayout relativeLayout) {
        switch (relativeLayout.getId()) {
            case R.id.share:
                if (!UiHelp.isFastClick()) {
                    if (share_loaded) {
                        ShareDialog(ThreeWebviewActivity.this, title, shareUtils, newsBean.getTitle(),
                                newsBean.getContent(), newsBean.getImg(), newsBean.getUrl());
                    } else {
                        NewsShareTask newsShareTask = new NewsShareTask();
                        newsShareTask.execute();
                    }

                }
                break;
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_three_webview;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
//        title.setText(getIntent().getStringExtra("title"));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getIntent().getStringExtra("url"));
        Log.e(TAG, getIntent().getStringExtra("url"));
        shareUtils = new ShareUtils(ThreeWebviewActivity.this);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showProgressDialog(getResources().getString(R.string.loading));
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e(TAG, "");
                dismissProgressDialog();
            }

            // 重定向
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        // 读取赞的信息
        ReadTask readTask = new ReadTask();
        readTask.execute();
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

    // 读取赞的信息
    private class ReadTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.THREE_READ + getIntent().getExtras().getString("id") + "/", ThreeWebviewActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean mapss) {
            super.onPostExecute(mapss);
        }
    }

    // 访问网络，读取要分享的新闻信息保存到NewsShareBean，然后弹出分享对话框
    private class NewsShareTask extends AsyncTask<Void, Void, NewsShareBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected NewsShareBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.NEWS_SHARE + getIntent().getStringExtra("id") + "/", mContext);
                    if (FastJsonTools.getBean(body, NewsShareBean.class) != null && FastJsonTools.getBean(body, NewsShareBean.class).getData() != null) {
                        newsBean = FastJsonTools.getBean(body, NewsShareBean.class).getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, NewsShareBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(NewsShareBean common) {
            super.onPostExecute(common);
            dismissProgressDialog();
            if (common != null) {
                if (common.getCode() == -1) {
                    return;
                }
                if (common.getCode() == 0) {

                    ShareDialog(ThreeWebviewActivity.this, title, shareUtils, common.getData().getTitle(),
                            common.getData().getContent(), common.getData().getImg(), common.getData().getUrl());
                    share_loaded = true;
                } else {
                    SuperToastUtils.showSuperToast(ThreeWebviewActivity.this, 1, common.getMsg());
                }
            }
        }
    }

    // 读取回复信息？
    private class CirclePostTask extends AsyncTask<Void, Void, CommonBean> {
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
                    body = OkHttpUtils.doForgetPost("news", getIntent().getStringExtra("id"), "content", ed_share.getText().toString(), Constant.BASE_URL + Constant.PHONE + Constant.PUBLISH_STATES, mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);

                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean common) {
            super.onPostExecute(common);
            dismissProgressDialog();
            if (common == null) {
                return;
            } else {
                if (common.getCode() == -1) {
                    return;
                }
                if (common.getCode() == 0) {
                    Log.e(TAG, "success");
                    sendBroadcast(new Intent(Constant.MY_ACTION));
                    EventBus.getDefault().post(new CircleRefreshEvent("refresh"));
                }
                SuperToastUtils.showSuperToast(ThreeWebviewActivity.this, 1, common.getMsg());
            }
        }
    }

    // 弹出分享要输入内容的对话框
    private void showDialogs() {
        dialog = new AlertDialog.Builder(ThreeWebviewActivity.this).create();
        dialog.setView(new EditText(ThreeWebviewActivity.this));
        /**Ҫ����������*/
        Window view = dialog.getWindow();
        view.setGravity(Gravity.CENTER);  //�˴���������dialog��ʾ��λ��
        view.setWindowAnimations(R.style.dialog_animator);  //��Ӷ���
        dialog.show();
        view.setContentView(R.layout.dialog_share_news);
        dialog.setCanceledOnTouchOutside(true);
        ed_share = (EditText) view.findViewById(R.id.ed_share);
        tv_send = (TextView) view.findViewById(R.id.tv_send);
        tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        tv_share = (TextView) view.findViewById(R.id.tv_share);
        iv_share = (ImageView) view.findViewById(R.id.iv_share);
        UpLoadImageUtils.getRoundImage(newsBean.getImg(), iv_share);
        tv_share.setText(newsBean.getTitle());// 要分享的内容
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                // 读取回复信息？
                CirclePostTask task = new CirclePostTask();
                task.execute();
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    // 弹出分享对话框
    public void ShareDialog(Activity activity, View parent, ShareUtils shareUtils, String title, String context, String image_url, String url) {
        PopupWindow popWindow = null;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.share_popup, null);
        // ����һ��PopuWidow����
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        TextView wechat = (TextView) view.findViewById(R.id.wechat);
        TextView wechat_moment = (TextView) view.findViewById(R.id.wechat_moment);
        TextView jinzht = (TextView) view.findViewById(R.id.jinzht);
        TextView qq = (TextView) view.findViewById(R.id.qq);
        TextView message = (TextView) view.findViewById(R.id.message);
        final PopupWindow finalPopWindow = popWindow;

        // 微信
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareUtils.wechat(title, context, image_url, url);
                finalPopWindow.dismiss();
            }
        });

        // 朋友圈
        wechat_moment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareUtils.wechatMement(title, context, image_url, url);
                finalPopWindow.dismiss();
            }
        });

        // QQ
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareUtils.qq(title, context, image_url, url);
                finalPopWindow.dismiss();
            }
        });

        // 短信
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareUtils.message(title, context, image_url, url);
                finalPopWindow.dismiss();
            }
        });

        // 金指投的圈子
        jinzht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalPopWindow.dismiss();
                showDialogs();
            }
        });
        popWindow.setAnimationStyle(R.style.dialog_zoom);
        // ʹ��ۼ� ��Ҫ������˵���ؼ����¼��ͱ���Ҫ���ô˷���
        popWindow.setFocusable(true);
        // ����������������ʧ
        popWindow.setOutsideTouchable(true);
        // ���ñ����������Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı���
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //����̲��ᵲ��popupwindow
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //���ò˵���ʾ��λ��
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }
}