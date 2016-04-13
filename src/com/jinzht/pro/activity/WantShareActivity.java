package com.jinzht.pro.activity;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.*;
import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.model.NewsShareBean;
import com.jinzht.pro.model.ProjectShareBean;
import com.jinzht.pro.utils.*;
import com.mob.tools.utils.UIHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * 我要分享界面，貌似不用了
 *
 * @auther Mr.Jobs
 * @date 2015/7/24
 * @time 13:32
 */

public class WantShareActivity extends BaseActivity implements PlatformActionListener, Callback{
    private int backgroundFromColor;
     @Bind({R.id.wechat,R.id.wechat_moment,R.id.qq,R.id.message,R.id.jinzht})
    List<TextView> textViews;
    private AlertDialog dialog;
     @OnClick(R.id.rel_finish) void fin(){
        finish();
    }
    private ProjectShareBean.DataEntity bean;
    protected PlatformActionListener paListener;
    @Bind(R.id.rl_main) RelativeLayout rl_main;
    private static final int MSG_TOAST = 1;
    private static final int MSG_ACTION_CCALLBACK = 2;
    private static final int MSG_CANCEL_NOTIFY = 3;
    private NewsShareBean.DataEntity newsBean;
    private TextView tv_cancle,tv_send,tv_share;
    private EditText ed_share;
    private ImageView iv_share;
    @Bind(R.id.ll_share) LinearLayout ll_share;
    @Bind(R.id.finish) ImageView finish;
    @OnClick({R.id.wechat,R.id.wechat_moment,R.id.qq,R.id.message,R.id.jinzht}) void onclick(TextView textView){
         if (NetWorkUtils.getNetWorkType(WantShareActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
             SuperToastUtils.showSuperToast(WantShareActivity.this, 1, R.string.no_wifi);
             return;
         }else {
             switch (textView.getId()){
                 case R.id.wechat:
                     if (getIntent().getIntExtra("flag",0)==Constant.SHARE_APP){
                         wechat(getResources().getString(R.string.app_name),bean.getContent(),bean.getImg(),bean.getUrl());
                     }else if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_PROJECT){
                         wechat(getIntent().getStringExtra("title"),bean.getContent(),bean.getImg(),bean.getUrl());
                     }else if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_NEWS){
                         wechat(newsBean.getTitle(), newsBean.getContent(), newsBean.getImg(), newsBean.getUrl());
                     }
                     break;
                 case R.id.wechat_moment:
                     if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_APP){
                         wechatMement(getResources().getString(R.string.app_name), bean.getContent(), bean.getImg(), bean.getUrl());
                     }else if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_PROJECT){
                         wechatMement(getIntent().getStringExtra("title"), bean.getContent(), bean.getImg(), bean.getUrl());
                     }else if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_NEWS){
                         wechatMement(newsBean.getTitle(), newsBean.getContent(), newsBean.getImg(), newsBean.getUrl());
                     }
                     break;
                 case R.id.qq:
                     if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_APP){
                         qq(getResources().getString(R.string.app_name), bean.getContent(), bean.getImg(), bean.getUrl());
                     }else if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_PROJECT){
                         qq(getIntent().getStringExtra("title"),bean.getContent(),bean.getImg(),bean.getUrl());
                     }else if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_NEWS){
                         qq(newsBean.getTitle(), newsBean.getContent(), newsBean.getImg(), newsBean.getUrl());
                     }
                     break;
                 case R.id.message:
                     if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_APP){
                         message(getResources().getString(R.string.app_name), bean.getContent(), bean.getImg(), bean.getUrl());
                     }else if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_PROJECT){
                         message(getIntent().getStringExtra("title"), bean.getContent(), bean.getImg(), bean.getUrl());
                     }else if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_NEWS){
                         message(newsBean.getTitle(), newsBean.getContent(), newsBean.getSrc(), newsBean.getUrl());
                     }
                     break;
                 case R.id.jinzht:
                     showDialogs();
                     break;
             }
         }
    }

    private void showDialogs() {
        dialog = new  AlertDialog.Builder(WantShareActivity.this).create();
        dialog.setView(new EditText(WantShareActivity.this));
        /**要想调出软键盘*/
        Window view = dialog.getWindow();
        view.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        view.setWindowAnimations(R.style.dialog_animator);  //添加动画
        dialog.show();
        view.setContentView(R.layout.dialog_share_news);
        dialog.setCanceledOnTouchOutside(true);
        ed_share = (EditText) view.findViewById(R.id.ed_share);
        tv_send = (TextView) view.findViewById(R.id.tv_send);
        tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        tv_share = (TextView) view.findViewById(R.id.tv_share);
        iv_share = (ImageView) view.findViewById(R.id.iv_share);
        UpLoadImageUtils.getRoundImage(newsBean.getSrc(),iv_share);
        tv_share.setText(newsBean.getTitle());
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
    @Override
    protected int getResourcesId() {
        return R.layout.dialog_full_share;
    }

    @Override
    protected void init() {
        rl_main.getBackground().setAlpha(100);
        if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_APP){
            //app
            AppShareTask shareTask = new AppShareTask();
            shareTask.execute();
        }else if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_PROJECT){
            //项目
            ProjectShareTask task = new ProjectShareTask();
            task.execute();
        }else if (getIntent().getIntExtra("flag", 0)==Constant.SHARE_NEWS){
            NewsShareTask newsShareTask = new NewsShareTask();
            newsShareTask.execute();
        }
    }


    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        // 成功
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg,this);
    }

    @Override
    public void onError(Platform platform, int action, Throwable t) {
        // 失敗
        //打印错误信息,print the error msg
        t.printStackTrace();
        //错误监听,handle the error msg
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onCancel(Platform platform, int action) {
        // 取消
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    private void wechat(String title,String context,String image_url,String url){
        ShareParams wechat = new ShareParams();
        wechat.setTitle(title);
        wechat.setText(context);
        wechat.setImageUrl(image_url);
        wechat.setUrl(url);
        wechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(WantShareActivity.this, Wechat.NAME);
        weixin.setPlatformActionListener(WantShareActivity.this);
        weixin.share(wechat);
    }
    private void wechatMement(String title,String context,String image_url,String url){
        ShareParams wechat = new ShareParams();
        wechat.setTitle(title);
        wechat.setText(context);
        wechat.setImageUrl(image_url);
        wechat.setUrl(url);
        wechat.setShareType(Platform.SHARE_WEBPAGE);
        Platform weixin = ShareSDK.getPlatform(WantShareActivity.this, WechatMoments.NAME);
        weixin.setPlatformActionListener(WantShareActivity.this);
        weixin.share(wechat);
    }
    private void qq(String title,String context,String image_url,String url){
        Platform.ShareParams sq = new Platform.ShareParams();
        sq.setTitle(title);
        sq.setText(context);
        sq.setImageUrl(image_url);
        sq.setTitleUrl(url);
        sq.setShareType(Platform.SHARE_WEBPAGE);
        Platform platform = ShareSDK.getPlatform(WantShareActivity.this, QQ.NAME);
        platform.setPlatformActionListener(WantShareActivity.this);
        platform.share(sq);
    }
    public void message(String title,String context,String image_url,String url){
        ShortMessage.ShareParams sp = new ShortMessage.ShareParams();
//        sp.setTitle(title);
        sp.setAddress("");
        sp.setText(getResources().getString(R.string.share_text) + url);
        Platform platform = ShareSDK.getPlatform(ShortMessage.NAME);
        platform.setPlatformActionListener(this);
        sp.setShareType(platform.SHARE_TEXT);
        platform.share(sp);
//        sp.setImageUrl(imageurl);
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

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
        case 1: {
            // 成功
            SuperToastUtils.showSuperToast(WantShareActivity.this, 1, R.string.share_success);
            ShareNumTask shareNumTask = new ShareNumTask();
            shareNumTask.execute();
        }
        break;
        case 2: {
            // 失败=
//            SuperToastUtils.showSuperToast(WantShareActivity.this, 1, msg.obj.toString());
            SuperToastUtils.showSuperToast(WantShareActivity.this, 1, R.string.share_fail);
        }
        break;
        case 3: {
            // 取消
            SuperToastUtils.showSuperToast(WantShareActivity.this, 1, R.string.share_cancel);
        }
        break;
    }

        return false;

    }

    private class AppShareTask extends AsyncTask<Void,Void,ProjectShareBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected ProjectShareBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.SHAREAPP, mContext);
                    if (FastJsonTools.getBean(body,ProjectShareBean.class)!=null&&FastJsonTools.getBean(body,ProjectShareBean.class).getData()!=null){
                        bean =FastJsonTools.getBean(body, ProjectShareBean.class).getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG,body);
                return FastJsonTools.getBean(body,ProjectShareBean.class);
            }else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ProjectShareBean common) {
            super.onPostExecute(common);
            dismissProgressDialog();
            if (common!=null){
                if (common.getCode()==-1){
                    return;
                }
                showAni();
                ll_share.setVisibility(View.VISIBLE);
                Animator.shareLogin(textViews.get(0), textViews.get(1), textViews.get(2),textViews.get(3),finish);
            }else {
                showAni();
                SuperToastUtils.showSuperToast(WantShareActivity.this,1,R.string.no_wifi);
            }

        }
    }
    private void showAni(){
        ll_share.setVisibility(View.VISIBLE);
        Animator.shareLogin(textViews.get(0),textViews.get(1),textViews.get(2),textViews.get(3),finish);
    }
    private class ProjectShareTask extends AsyncTask<Void,Void,ProjectShareBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected ProjectShareBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.SHAREPROJECT+getIntent().getStringExtra("id")+"/", mContext);
                    if (FastJsonTools.getBean(body,ProjectShareBean.class)!=null&&FastJsonTools.getBean(body,ProjectShareBean.class).getData()!=null){
                        bean = FastJsonTools.getBean(body, ProjectShareBean.class).getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG,body);
                return FastJsonTools.getBean(body,ProjectShareBean.class);
            }else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ProjectShareBean common) {
            super.onPostExecute(common);
            dismissProgressDialog();
            if (common!=null){
                if (common.getCode()==-1){
                    return;
                }
                showAni();
                ll_share.setVisibility(View.VISIBLE);
                Animator.shareLogin(textViews.get(0), textViews.get(1), textViews.get(2),textViews.get(3),finish);
            }else {
                showAni();
                SuperToastUtils.showSuperToast(WantShareActivity.this,1,R.string.no_wifi);
            }
        }
    }
    private class NewsShareTask extends AsyncTask<Void,Void,NewsShareBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected NewsShareBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.NEWS_SHARE+getIntent().getStringExtra("id")+"/", mContext);
                    if (FastJsonTools.getBean(body,NewsShareBean.class)!=null&&FastJsonTools.getBean(body,NewsShareBean.class).getData()!=null){
                        newsBean= FastJsonTools.getBean(body,NewsShareBean.class).getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG,body);
                return FastJsonTools.getBean(body, NewsShareBean.class);
            }else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(NewsShareBean common) {
            super.onPostExecute(common);
            dismissProgressDialog();
            if (common==null) {
                ll_share.setVisibility(View.VISIBLE);
                Animator.shareCircle(textViews.get(0), textViews.get(1), textViews.get(2), textViews.get(3), textViews.get(4), finish);
                SuperToastUtils.showSuperToast(WantShareActivity.this, 1, R.string.no_wifi);
            }else {
                if (common.getCode()==-1){
                    ll_share.setVisibility(View.VISIBLE);
                    Animator.shareCircle(textViews.get(0), textViews.get(1), textViews.get(2), textViews.get(3), textViews.get(4), finish);
                    return;
                }
                ll_share.setVisibility(View.VISIBLE);
                Animator.shareCircle(textViews.get(0), textViews.get(1), textViews.get(2), textViews.get(3), textViews.get(4), finish);
            }
        }
    }
    private class CirclePostTask extends AsyncTask<Void,Void,CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                try {
                    body = OkHttpUtils.doForgetPost("news", getIntent().getStringExtra("id"), "content", ed_share.getText().toString(), Constant.BASE_URL + Constant.PHONE + Constant.PUBLISH_STATES, mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG,body);

                return FastJsonTools.getBean(body, CommonBean.class);
            }else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean common) {
            super.onPostExecute(common);
            if (common==null){
                return;
            }else {
                SuperToastUtils.showSuperToast(WantShareActivity.this,1,common.getMsg());
                finish();
            }
        }
    }
    private class ShareNumTask extends AsyncTask<Void,Void,CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.SHARE_NUM + getIntent().getExtras().getString("id") + "/", WantShareActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, CommonBean.class);
            }else {
                return null;
            }
        }
        @Override
        protected void onPostExecute(CommonBean mapss) {
            super.onPostExecute(mapss);
        }
    }
}