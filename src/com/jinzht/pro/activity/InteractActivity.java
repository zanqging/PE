package com.jinzht.pro.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.InteractBean;
import com.jinzht.pro.adapter.InteractsAdapter;
import com.jinzht.pro.callback.ExpandReplyInterface;
import com.jinzht.pro.edittext.MaterialEditText;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.utils.*;
import com.jinzht.pro.view.MultiStateView;
import com.jinzht.pro.view.OnRcvScrollListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * 互动专栏页面
 *
 * @auther Mr.Jobs
 * @date 2015/8/27
 * @time 11:30
 */

public class InteractActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    InteractsAdapter adapter;
    @Bind(R.id.title)
    TextView title;
    Intent intent;
    @Bind(R.id.mult_refresh)
    SwipeRefreshLayout mult_refresh;
    @Bind(R.id.rv_recyclerview_data)
    RecyclerView mDataRv;
    InteractBean interactBean;
    List<InteractBean.DataEntity> header_list = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    InteractTask task;
    AlertDialog dialog;
    private MaterialEditText materialEditText;

    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private TextView ok;
    private boolean isEnd = false;
    private boolean isRefresh = true;
    private boolean isLoadingData = false;
    private int pageNo = 0;
    private boolean isFirst = true;
    private boolean isError = false;
    private boolean isBottom = false;

    @OnClick(R.id.publish_btn)
    void publih() {
        intent = new Intent(InteractActivity.this, PostCommentActivity.class);
        intent.putExtra("id", getIntent().getExtras().getString("id"));
        startActivityForResult(intent, 0);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.acitivy_interact;
    }

    @Override
    protected void init() {
        title.setText(getResources().getString(R.string.interaction));
        mDataRv.setHasFixedSize(true);
        mDataRv.addItemDecoration(new SpaceItemDecoration(15));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mDataRv.setLayoutManager(linearLayoutManager);
        mDataRv.setItemAnimator(new DefaultItemAnimator());
        mDataRv.setOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onBottom() {
                super.onBottom();
                Log.e(TAG, "sssss");
                if (!isLoadingData) {
                    Log.e(TAG, "eeee");
                    if (!isBottom) {
                        mult_refresh.setRefreshing(false);
                        isFirst = false;
                        pageNo++;
                        isRefresh = false;
                        task = new InteractTask();
                        task.execute();
                        isLoadingData = true;
                    } else {
                        UiHelp.canToast(InteractActivity.this);
                    }
                }
            }
        });
        mult_refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mult_refresh.setOnRefreshListener(this);
        onRefresh();
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        task = new InteractTask();
                        task.execute();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            pageNo = 0;
            isFirst = true;
            isBottom = false;
            isRefresh = true;
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            task = new InteractTask();
            task.execute();
        }
    }

    @Override
    public void onRefresh() {
        Log.e(TAG, "refresh'");
        pageNo = 0;
        isFirst = true;
        isBottom = false;
        isRefresh = true;
        task = new InteractTask();
        task.execute();
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
        task = new InteractTask();
        task.execute();
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            //�������ҵļ����������õĻ��������ã������ò�����ע�͵���
//            outRect.left = space;
//            outRect.right = space;

            //       System.out.println("position"+parent.getChildPosition(view));
            //       System.out.println("count"+parent.getChildCount());

            //         if(parent.getChildPosition(view) != parent.getChildCount() - 1)
            //         outRect.bottom = space;

            //�ĳ�ʹ������ļ��������
            if (parent.getChildPosition(view) != 0)
                outRect.top = space;
        }
    }

    private class InteractTask extends AsyncTask<Void, Void, InteractBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isError = false;
            showLoadingProgressDialog();
        }

        @Override
        protected InteractBean doInBackground(Void... voids) {
            String body = "";
            if (NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                errorPage();
                return null;
            } else {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.COMMENTSLIST + getIntent().getStringExtra("id") + "/" + pageNo + "/", mContext);
                    Log.e(TAG, body);
                    if (isRefresh && header_list.size() != 0) {
                        header_list.clear();
                    }
                    if (FastJsonTools.getBean(body, InteractBean.class) != null && FastJsonTools.getBean(body, InteractBean.class).getData() != null) {
                        header_list.addAll(FastJsonTools.getBean(body, InteractBean.class).getData());
                    }
                } catch (Exception e) {
                    isError = true;
                    okHttpException.httpException(e);
                    e.printStackTrace();
                    return null;
                }
            }
            return FastJsonTools.getBean(body, InteractBean.class);
        }

        @Override
        protected void onPostExecute(InteractBean maps) {
            super.onPostExecute(maps);
            dismissProgressDialog();
            if (maps != null) {
                if (maps.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                if (pageNo == 0) {
                    if (header_list.size() == 0 && !isError) {
                        blankPage();
                    } else {
                        adapter = new InteractsAdapter(InteractActivity.this, header_list);
                        mDataRv.setAdapter(adapter);
                    }
                } else {
                    if (maps.getCode() == 2 && maps.getData().size() == 0) {
                        Log.e(TAG, "bottom");
                        isBottom = true;
                    } else {
                        adapter.notifyItemInserted(adapter.getItemCount());
                    }
                }
                if (header_list.size() != 0) {
                    adapter.setOnReplyBtnOnclick(new ExpandReplyInterface() {
                        @Override
                        public void HeaderReplyOnclick(View view, int header_position) {
                            showDialogs(header_position);
                        }
                    });
                }
            }
            mult_refresh.setRefreshing(false);
            isLoadingData = false;
            isFirst = false;
        }
    }

    private void showDialogs(int position) {
        dialog = new AlertDialog.Builder(InteractActivity.this).create();
        dialog.setView(new EditText(InteractActivity.this));
        /**Ҫ����������*/
        Window view = dialog.getWindow();
        view.setGravity(Gravity.CENTER);  //�˴���������dialog��ʾ��λ��
        view.setWindowAnimations(R.style.dialog_animator);  //��Ӷ���
        dialog.show();
        view.setContentView(R.layout.replay_dialog);
        dialog.setCanceledOnTouchOutside(true);
        materialEditText = (MaterialEditText) view.findViewById(R.id.publish_edit);
        ok = (TextView) view.findViewById(R.id.reply_btn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String ss = header_list.get(position).getId() + "";
                ReplayFirstTask replayFirstTask = new ReplayFirstTask(ss);
                replayFirstTask.execute();
            }
        });
    }

    private class ReplayFirstTask extends AsyncTask<Void, Void, CommonBean> {
        String ss = "";

        public ReplayFirstTask(String ss) {
            this.ss = ss;
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
                    body = OkHttpUtils.doForgetPost("content", materialEditText.getText().toString(), "at", ss, Constant.BASE_URL + Constant.PHONE + Constant.POSTCOMEMNTS + getIntent().getStringExtra("id") + "/", mContext);
                    Log.e(TAG, body);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, CommonBean.class);
            }
        }

        @Override
        protected void onPostExecute(CommonBean maps) {
            super.onPostExecute(maps);
            if (maps == null) {
            } else {
                SuperToastUtils.showSuperToast(InteractActivity.this, 1, maps.getMsg());
                onRefresh();
            }
            dismissProgressDialog();
        }
    }
}