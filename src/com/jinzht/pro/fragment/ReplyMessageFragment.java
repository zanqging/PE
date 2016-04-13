package com.jinzht.pro.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.BaseActivity;
import com.jinzht.pro.activity.InteractActivity;
import com.jinzht.pro.adapter.SwipeListAdapter;
import com.jinzht.pro.callback.ListViewOnBottomListener;
import com.jinzht.pro.edittext.MaterialEditText;
import com.jinzht.pro.eventbus.MainNoticeEvent;
import com.jinzht.pro.eventbus.NoticeDissmissEvent;
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
 */
public class ReplyMessageFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener, SwipeListAdapter.SwipeOnClick, ListViewOnBottomListener {

    private boolean isEnd = false;
    private int pageNo = 0;
    private boolean isFirst = true;
    private boolean isBottom = false;
    private boolean isRefresh = true;
    private boolean isLoadingData = false;
    private SwipeListAdapter adapter;
    private List<ReplyMessageBean.DataEntity> list = new ArrayList<>();// 回复消息列表
    ReplyTask task;
    private MaterialEditText materialEditText;// 消息回复输入框
    private TextView ok;// 消息回复按钮
    private int flag = 0;
    //    @Bind(R.id.swipyrefreshlayout) SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��
    private int index = 0;
    private int top = 0;
    AlertDialog dialog;// 消息回复对话框
    private int reply_id = 0;// 回复id
    private int project_id = 0;// 回复项目id

    @Bind(R.id.lv_reply_message)
    ListView listView;// 消息回复列表
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private boolean is_error = false;

    // 访问回复消息，进入具体项目的互动专栏
    @OnItemClick(R.id.lv_reply_message)
    void pos(int pos) {
        Log.i("条目位置", "ontem" + pos);
        /**������ڼ���ʱ���ܵ����Ŷ*/
        reply_id = list.get(pos).getId();
        project_id = list.get(pos).getPid();
        ReadTask readTask = new ReadTask();
        readTask.execute();
    }

    @Override
    protected int setLayout(LayoutInflater inflater) {
        return R.layout.fragment_replay_message;
    }

    @Override
    protected void onFirstUserVisible() {
//        mSwipyRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
//        mSwipyRefreshLayout.setOnRefreshListener(this);

        // 获取有回复的消息并填充数据
        task = new ReplyTask();
        task.execute();

        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        task = new ReplyTask();
                        task.execute();
                    }
                });

        listView.setOnScrollListener(new ListviewBottomListener(ReplyMessageFragment.this));
    }

    private void preRefresh() {
        isFirst = true;
        isEnd = false;
        pageNo = 0;
        isRefresh = true;
        top = 0;
        index = 0;
    }

    // 上拉加载
    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            preRefresh();
            task = new ReplyTask();
            task.execute();
        } else {
            index = listView.getFirstVisiblePosition();
            View view3 = listView.getChildAt(0);
            top = (view3 == null) ? 0 : view3.getTop();
            if (isEnd) {
//                mSwipyRefreshLayout.setRefreshing(false);
                UiHelp.canToast(getActivity());
            } else {
                pageNo = pageNo + 1;
                isRefresh = false;
                task = new ReplyTask();
                task.execute();
            }
        }
    }

    // 删除和回复消息
    @Override
    public void onClicks(View view, int position) {
        if (view.getId() == R.id.delete) {// 删除消息
            flag = list.get(position).getId();
            Log.i("消息id", flag + "");
            DeleteTask deleteTask = new DeleteTask(flag);
            deleteTask.execute();
        } else if (view.getId() == R.id.reply_message) {// 回复消息
            showDialogs(position);
        }
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    protected void onFirstUserInvisble() {
    }

    @Override
    protected void onUserVisble() {
    }

    @Override
    protected void init() {
    }

    @Override
    public void errorPage() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        });
    }

    @Override
    public void blankPage() {
        getActivity().runOnUiThread(new Runnable() {
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
        Log.i("底部", "bottom");
        if (!isLoadingData) {
            index = listView.getFirstVisiblePosition();
            View view3 = listView.getChildAt(0);
            top = (view3 == null) ? 0 : view3.getTop();
            if (isBottom) {
//                mSwipyRefreshLayout.setRefreshing(false);
                UiHelp.canToast(getActivity());
            } else {
                pageNo = pageNo + 1;
                isRefresh = false;
                task = new ReplyTask();
                task.execute();
            }
        }
    }

    // 获取回复的消息并填充数据
    private class ReplyTask extends AsyncTask<Void, Void, ReplyMessageBean> {
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
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.TOPIC_READ + pageNo + "/", getActivity());
                    if (isRefresh && list.size() != 0) {
                        list.clear();
                    }
                    if (FastJsonTools.getBean(body, ReplyMessageBean.class) != null && FastJsonTools.getBean(body, ReplyMessageBean.class).getData() != null) {
                        list.addAll(FastJsonTools.getBean(body, ReplyMessageBean.class).getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    okHttpException.httpException(e);
                    is_error = true;
                }
                Log.i("回复的消息", body);
                return FastJsonTools.getBean(body, ReplyMessageBean.class);
            } else {
                return null;
            }
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(ReplyMessageBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                if (aVoid.getCode() == 2 && aVoid.getData() == null) {
                    isEnd = true;
                }
                if (pageNo == 0 && list.size() == 0) {
                    blankPage();
                } else {
                    if (aVoid.getCode() == 2 && aVoid.getData().size() == 0) {
                        isBottom = true;
                    }
                    adapter = new SwipeListAdapter(getActivity(), list, ReplyMessageFragment.this);
                    listView.setAdapter(adapter);
                    listView.setSelectionFromTop(index, top);
                    adapter.setMode(Attributes.Mode.Single);
                    adapter.notifyDataSetChanged();
                    isLoadingData = false;
                }
            }
        }
    }

    // 访问回复的消息，进入具体项目的互动专栏
    public class ReadTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            super.onPreExecute();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.TOP_READMSG + reply_id + "/", getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("最顶消息", body);
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
                    EventBus.getDefault().post(new NoticeEvent("msg"));
                    Intent intent = new Intent(mContext, InteractActivity.class);
                    intent.putExtra("id", project_id + "");
                    getActivity().startActivity(intent);
                }
                Log.i("点了具体条目", "进入互动专栏");
            }
        }
    }

    // 删除消息并刷新
    private class DeleteTask extends AsyncTask<Void, Void, CommonBean> {
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
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.TOP_READMSG + id + "/", getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("删除消息后返回的信息", body);
                return FastJsonTools.getBean(body, CommonBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommonBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    preRefresh();
                    task = new ReplyTask();
                    task.execute();
                }
            }
        }
    }

    // 弹出消息回复框
    private void showDialogs(int position) {
        dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setView(new EditText(getActivity()));
        Window view = dialog.getWindow();
        view.setGravity(Gravity.CENTER);
        view.setWindowAnimations(R.style.dialog_animator);
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

    // 回复消息
    private class ReplayFirstTask extends AsyncTask<Void, Void, CommonBean> {
        String ss = "";
        String ss1 = "";

        public ReplayFirstTask(String ss, String ss1) {
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
            if (NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                return null;
            } else {
                try {
                    body = OkHttpUtils.doForgetPost("content", materialEditText.getText().toString(), "at", ss, Constant.BASE_URL + Constant.PHONE + Constant.POSTCOMEMNTS + ss1 + "/", mContext);
                    Log.i("回复消息", body);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, CommonBean.class);
            }
        }

        @Override
        protected void onPostExecute(CommonBean maps) {
            super.onPostExecute(maps);
            if (maps != null) {
                SuperToastUtils.showSuperToast(getActivity(), 1, maps.getMsg());
            }
            dismissProgressDialog();
        }
    }
}