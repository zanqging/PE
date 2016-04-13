package com.jinzht.pro.activity;

import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.CircleCommentDetailAdapter;
import com.jinzht.pro.adapter.CircleLikeDetailAdapter;
import com.jinzht.pro.model.CircleCommentDetailBean;
import com.jinzht.pro.model.CircleSomeOneLikeDeailBean;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UpLoadImageUtils;
import com.jinzht.pro.view.MyListview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/10/16.
 *
 * 圈子人物发表信息页面，赞和评论，貌似没有用到
 */
public class CircleSomeoneActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private int pageLoveNO = 0;// 赞页数
    private int pageCommentNO = 0;// 评论页数
    private CircleLoveTask circleLoveTask;
    private CircleCommentTask circleCommentTask;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    private CircleLikeDetailAdapter adapter;// 赞的列表数据适配器
    private CircleCommentDetailAdapter commentDetailAdapter;// 评论列表数据适配器
    private List<CircleSomeOneLikeDeailBean.DataEntity> love_list = new ArrayList<>();// 赞集合
    private List<CircleCommentDetailBean.DataEntity> comment_list = new ArrayList<>();// 评论集合
    private boolean isLoveRefresh = true;// 赞刷新
    int lastIndexInScreen;// 屏幕最底标识
    private boolean isLoading = true;
    private boolean isLoveFirst = true;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_circle_someone;
    }

    @Bind(R.id.lv_circle)
    MyListview lv_circle;// 赞列表
    @Bind(R.id.lv_circle_comment)
    MyListview lv_circle_comment;// 评论列表
    @Bind(R.id.scroll_view)
    ScrollView scrollView;// 整个页面的ScrollView
    @Bind(R.id.iv_user)
    ImageView iv_user;// 头像
    @Bind({R.id.tv_name, R.id.tv_location, R.id.tv_time, R.id.tv_content})
    List<TextView> textViews;

    // 初始化，显示基本数据和点赞列表
    @Override
    protected void init() {
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });
        if (getIntent().getExtras() != null) {
            UpLoadImageUtils.getRoundImage(getIntent().getStringExtra("photo"), iv_user);
            textViews.get(0).setText(getIntent().getStringExtra("name"));
            textViews.get(1).setText(getIntent().getStringExtra("city") + "|" + getIntent().getStringExtra("position"));
            textViews.get(2).setText(getIntent().getStringExtra("time"));
            textViews.get(3).setText(getIntent().getStringExtra("content"));// 简介
        }
        lv_circle.setVisibility(View.VISIBLE);
        radioGroup.setOnCheckedChangeListener(this);
        // 加载点赞列表
        if (getIntent().getExtras() != null) {
            circleLoveTask = new CircleLoveTask();
            circleLoveTask.execute();
        }
        lv_circle.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                //直接忽略
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && lastIndexInScreen == adapter.getCount() && !isLoading) {// 滑到最底端时
                    Log.e("Main", "loadmore");
                    isLoading = true;
                    loveMore();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.e("Main", totalItemCount + "");
                lastIndexInScreen = visibleItemCount + firstVisibleItem;
                if (lastIndexInScreen >= totalItemCount && !isLoading) {

//                    loadLikeMore();
                }
            }
        });
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (v.getScrollY() <= 0) {
                            Log.d("scroll view", "top");
                        } else if (scrollView.getChildAt(0).getMeasuredHeight() <= v.getHeight() + v.getScrollY()) {
                            Log.e("scroll view", "bottom");
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    // 加载更多
    private void loveMore() {
        isLoveRefresh = false;
        ++pageLoveNO;
        circleLoveTask = new CircleLoveTask();
        circleLoveTask.execute();
    }

    // 点击赞或者评论列表
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.tv_list_love:
                lv_circle.setVisibility(View.VISIBLE);
                lv_circle_comment.setVisibility(View.GONE);
                circleLoveTask = new CircleLoveTask();
                circleLoveTask.execute();
                break;
            case R.id.tv_list_comment:
                lv_circle.setVisibility(View.GONE);
                lv_circle_comment.setVisibility(View.VISIBLE);
                circleCommentTask = new CircleCommentTask();
                circleCommentTask.execute();
                break;
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

    // 读取点赞信息
    private class CircleLoveTask extends AsyncTask<Void, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(Void... voids) {
            String body = "";
            int ss = 0;
            if (!NetWorkUtils.getNetWorkType(CircleSomeoneActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //有网
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.CIRCLE_LOVE_DETAIL + getIntent().getExtras().getInt("id") + "/" + pageLoveNO + "/", CircleSomeoneActivity.this);
                    if (isLoveRefresh && love_list.size() != 0) {
                        love_list.clear();
                    }
                    if (FastJsonTools.getMap(body) != null || FastJsonTools.getMap(body).get("data") != null
                            || !FastJsonTools.getMap(body).get("data").toString().equals("") || !FastJsonTools.getMap(body).get("data").toString().equals("[]")) {
                        love_list = FastJsonTools.getBeanList(FastJsonTools.getMap(body).get("data").toString(), CircleSomeOneLikeDeailBean.DataEntity.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getMap(body);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Map<String, Object> aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid == null) {

            } else if (love_list.size() == 0) {
                if (isLoveFirst) {

                } else {

                }
            } else {
                if (isLoveFirst) {
                    adapter = new CircleLikeDetailAdapter(mContext, love_list);
                    lv_circle.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    lastPageNoToast(aVoid);
                }
            }
            isLoveFirst = false;
            dismissProgressDialog();
            isLoading = false;
        }
    }

    // 点赞提醒
    private void lastPageNoToast(Map<String, Object> map) {
        try {
            if (Integer.parseInt(map.get("status").toString()) == -1 && pageLoveNO != 0) {
                isLoveFirst = false;
                SuperToastUtils.showSuperToast(CircleSomeoneActivity.this, 1, map.get("msg").toString());
            }
        } catch (Exception e) {
        }
    }

    // 读取评论信息
    private class CircleCommentTask extends AsyncTask<Void, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(Void... voids) {
            String body = "";
            int ss = 0;
            if (!NetWorkUtils.getNetWorkType(CircleSomeoneActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //有网
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.CIRCLE_COMMENT_DETAIL + getIntent().getExtras().getInt("id") + "/" + pageCommentNO + "/", CircleSomeoneActivity.this);
//                    if (isRefresh&&title_list.size()!=0){
//                        title_list.clear();
//                    }
                    if (FastJsonTools.getMap(body) != null || FastJsonTools.getMap(body).get("data") != null
                            || !FastJsonTools.getMap(body).get("data").toString().equals("") || !FastJsonTools.getMap(body).get("data").toString().equals("[]")) {
                        Log.e(TAG, FastJsonTools.getMap(body).get("data") + "");
                        comment_list = FastJsonTools.getBeanList(FastJsonTools.getMap(body).get("data").toString(), CircleCommentDetailBean.DataEntity.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getMap(body);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Map<String, Object> aVoid) {
            super.onPostExecute(aVoid);
            commentDetailAdapter = new CircleCommentDetailAdapter(CircleSomeoneActivity.this, comment_list);
            lv_circle_comment.setAdapter(commentDetailAdapter);
        }
    }
}