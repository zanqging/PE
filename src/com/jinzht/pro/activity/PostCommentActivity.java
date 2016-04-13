package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.edittext.MaterialEditText;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.utils.*;

import java.io.IOException;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * 互动专栏中点击右上角写评论，进入输入评论页面
 *
 * @auther Mr.Jobs
 * @date 2015/8/28
 * @time 10:02
 */

public class PostCommentActivity extends BaseActivity {

    @OnClick(R.id.back) void back(){
        finish();
    }
    @Bind(R.id.title)
    TextView title;
    private Intent intent;
    @Bind(R.id.publish_edit)
    MaterialEditText publish_edit;

    @Bind(R.id.back)
    LinearLayout back;
    @OnClick(R.id.publish_btn) void publish(){
        RippleUtils.rippleNormal(back);
        if (StringUtils.isBlank(publish_edit.getText().toString())){
            SuperToastUtils.showSuperToast(PostCommentActivity.this,1,getResources().getString(R.string.please_input_comments));
        }else {
            PostCommentsTask task = new PostCommentsTask();
            task.execute();
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_post_comments;
    }

    @Override
    protected void init() {
        title.setText(getResources().getString(R.string.publish_btn));
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


    private class PostCommentsTask extends AsyncTask<Void,Void,CommonBean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //有网
                try {
                    body = AsynOkUtils.doPushPost("content", publish_edit.getText().toString(), Constant.BASE_URL + Constant.PHONE + Constant.POSTCOMEMNTS + getIntent().getExtras().getString("id") + "/", mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG,body);
                return FastJsonTools.getBean(body,CommonBean.class);
            }else{
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid!=null){
                if (aVoid.getCode()==-1){
                    return;
                }
                if (aVoid.getCode()==0){
                    intent = new Intent(mContext,InteractActivity.class);
                    setResult(1,intent);
                    SuperToastUtils.showSuperToast(PostCommentActivity.this,1,aVoid.getMsg());
                    finish();
                }else {
                    SuperToastUtils.showSuperToast(PostCommentActivity.this,1,aVoid.getMsg());
                }
            }
        }
    }
}