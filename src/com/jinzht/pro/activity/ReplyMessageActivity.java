package com.jinzht.pro.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnItemClick;
import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.SwipeListAdapter;
import com.jinzht.pro.callback.ListViewOnBottomListener;
import com.jinzht.pro.edittext.MaterialEditText;
import com.jinzht.pro.eventbus.NoticeEvent;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.model.ReplyMessageBean;
import com.jinzht.pro.swipe.util.Attributes;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayout;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.jinzht.pro.utils.*;
import com.jinzht.pro.view.ListviewBottomListener;
import com.jinzht.pro.view.MultiStateView;
import de.greenrobot.event.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/30.
 *
 * 回复消息？没发现在哪用到
 */
public class ReplyMessageActivity extends BaseActivity implements SwipyRefreshLayout.OnRefreshListener ,SwipeListAdapter.SwipeOnClick,ListViewOnBottomListener {

    @Bind(R.id.lv_reply_message)
    ListView listView;
    private boolean isEnd = false;
    private int pageNo = 0;
    private boolean isFirst= true;
    private boolean isBottom = false;
    private boolean isRefresh =true;
    private boolean isLoadingData =false;
    private SwipeListAdapter adapter;
    private List<ReplyMessageBean.DataEntity> list =new ArrayList<>();
    ReplyTask task;
    private MaterialEditText materialEditText;
    private TextView ok;
    private int flag=0;
    //    @Bind(R.id.swipyrefreshlayout) SwipyRefreshLayout mSwipyRefreshLayout;//刷新
    private int index =0;
    private int top = 0;
    AlertDialog dialog;
    private int reply_id = 0;
    private int project_id = 0;
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private boolean is_error = false;
    @OnItemClick(R.id.lv_reply_message) void pos(int pos){
        Log.e(TAG, "ontem" + pos);
        /**如果正在加载时不能点击的哦*/
        reply_id = list.get(pos).getId();
        project_id = list.get(pos).getPid();
        ReadTask readTask = new ReadTask();
        readTask.execute();
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_replay_message;
    }

    @Override
    protected void init() {
        task = new ReplyTask();
        task.execute();
        /*动态方式注册广播接收者*/
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        task = new ReplyTask();
                        task.execute();
                    }
                });
        listView.setOnScrollListener(new ListviewBottomListener(ReplyMessageActivity.this));
    }
    private void preRefresh(){
        isFirst= true;
        isEnd = false;
        pageNo =0;
        isRefresh = true;
        top=0;
        index=0;
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction==SwipyRefreshLayoutDirection.TOP){
            //下拉刷新
            preRefresh();
            task = new ReplyTask();
            task.execute();
        }else {
            index = listView.getFirstVisiblePosition();
            View view3 = listView.getChildAt(0);
            top = (view3==null)?0:view3.getTop();
            if (isEnd){
//                mSwipyRefreshLayout.setRefreshing(false);
                UiHelp.canToast(ReplyMessageActivity.this);
            }else {
                pageNo = pageNo+1;
                isRefresh = false;
                task = new ReplyTask();
                task.execute();
            }
        }
    }
    @Override
    public void onClicks(View view, int position) {
        if (view.getId()==R.id.delete){
            flag = list.get(position).getId();
            Log.e(TAG,flag+"");
            DeleteTask deleteTask = new DeleteTask(flag);
            deleteTask.execute();
        }else if (view.getId()==R.id.reply_message){
            showDialogs(position);
        }
    }
    @Override
    public void errorPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        });
    }

    @Override
    public void blankPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        });
    }
    @Override
    public void successRefresh() {
        preRefresh();
        task = new ReplyTask();
        task.execute();
    }

    @Override
    public void onBottom() {
        Log.e(TAG, "bottom");
        if (!isLoadingData){
            index = listView.getFirstVisiblePosition();
            View view3 = listView.getChildAt(0);
            top = (view3==null)?0:view3.getTop();
            if (isBottom){
//                mSwipyRefreshLayout.setRefreshing(false);
                UiHelp.canToast(ReplyMessageActivity.this);
            }else {
                pageNo = pageNo+1;
                isRefresh = false;
                task = new ReplyTask();
                task.execute();
            }
        }
    }
    private class ReplyTask extends AsyncTask<Void,Void,ReplyMessageBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isLoadingData = true;
            showLoadingProgressDialog();
//            mSwipyRefreshLayout.setRefreshing(true);
        }
        @Override
        protected ReplyMessageBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(ReplyMessageActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //有网
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.TOPIC_READ + pageNo + "/", ReplyMessageActivity.this);
                    if (isRefresh&&list.size()!=0){
                        list.clear();
                    }
                    if (FastJsonTools.getBean(body, ReplyMessageBean.class)!=null&&FastJsonTools.getBean(body,ReplyMessageBean.class).getData()!=null){
                        list.addAll(FastJsonTools.getBean(body,ReplyMessageBean.class).getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    okHttpException.httpException(e);
                    is_error = true;
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, ReplyMessageBean.class);
            }else{
                return null;
            }
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(ReplyMessageBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid!=null){
                //此处为空
                if (aVoid.getCode()==-1){
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                if (aVoid.getCode()==2&&aVoid.getData()==null){
                    isEnd = true;
                }
                if (pageNo==0&&list.size()==0){
                    blankPage();
                }else {
                    if (aVoid.getCode()==2&&aVoid.getData().size()==0){
                        isBottom= true;
                    }
                    adapter= new SwipeListAdapter(ReplyMessageActivity.this,list,ReplyMessageActivity.this);
                    listView.setAdapter(adapter);
                    listView.setSelectionFromTop(index, top);
                    adapter.setMode(Attributes.Mode.Single);
                    adapter.notifyDataSetChanged();
                    isLoadingData = false;
                }
            }
        }
    }
    public class ReadTask extends AsyncTask<Void,Void,CommonBean> {
        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            super.onPreExecute();
        }
        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(ReplyMessageActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //有网
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.TOP_READMSG+reply_id+"/", ReplyMessageActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, CommonBean.class);
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
                    EventBus.getDefault().post(new NoticeEvent("msg"));
                    Intent intent = new Intent(mContext,InteractActivity.class);
                    intent.putExtra("id", project_id + "");
                    startActivity(intent);
                }
                Log.e(TAG, "ssss");
            }
        }
    }
    private class DeleteTask extends AsyncTask<Void,Void,CommonBean> {
        private int id;

        public DeleteTask(int id) {
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(ReplyMessageActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                //有网
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.TOP_READMSG+id+"/",ReplyMessageActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, CommonBean.class);
            }else{
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid==null){
            }else {
                if (aVoid.getCode()==-1){
                    return;
                }
                if (aVoid.getCode()==0){

                    preRefresh();
                    task = new ReplyTask();
                    task.execute();
                }
            }
        }
    }
    private void showDialogs(int position) {
        dialog = new  AlertDialog.Builder(ReplyMessageActivity.this).create();
        dialog.setView(new EditText(ReplyMessageActivity.this));
        /**要想调出软键盘*/
        Window view = dialog.getWindow();
        view.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        view.setWindowAnimations(R.style.dialog_animator);  //添加动画
        dialog.show();
        view.setContentView(R.layout.replay_dialog);
        dialog.setCanceledOnTouchOutside(true);
        materialEditText = (MaterialEditText) view.findViewById(R.id.publish_edit);
        ok = (TextView) view.findViewById(R.id.reply_btn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String ss = list.get(position).getId() + "";
                ReplayFirstTask replayFirstTask = new ReplayFirstTask(ss, list.get(position).getPid() + "");
                replayFirstTask.execute();
            }
        });
    }
    private class ReplayFirstTask extends AsyncTask<Void,Void,CommonBean> {
        String ss = "";
        String ss1 = "";
        public ReplayFirstTask(String ss,String ss1) {
            this.ss = ss;
            this.ss1 = ss1;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = null;
            if (NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)){
                return null;
            }else {
                try {
                    body = OkHttpUtils.doForgetPost("content", materialEditText.getText().toString(), "at", ss, Constant.BASE_URL + Constant.PHONE + Constant.POSTCOMEMNTS + ss1 + "/", mContext);
                    Log.e(TAG, body);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body,CommonBean.class);
            }
        }

        @Override
        protected void onPostExecute(CommonBean maps) {
            super.onPostExecute(maps);
            if (maps==null){
            }else {
                SuperToastUtils.showSuperToast(ReplyMessageActivity.this, 1, maps.getMsg());
            }
            dismissProgressDialog();
        }
    }
}