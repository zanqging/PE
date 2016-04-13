package com.jinzht.pro.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.SystemNoticeAdapter;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.model.SystemNoticeBean;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayout;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.StringUtils;
import com.jinzht.pro.utils.UiHelp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 * <p>
 * ϵͳ֪ͨ����֪�������õ�
 *
 * @auther Mr Jobs
 * @date 2015/8/7
 * @time 23:51
 */
public class SystemNoticeActivity extends BaseActivity implements AdapterView.OnItemClickListener, SwipyRefreshLayout.OnRefreshListener, SystemNoticeAdapter.SwipeOnClick {

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.list)
    ListView listView;
    private List<SystemNoticeBean.DataEntity> list = new ArrayList<>();
    private List<SystemNoticeBean.DataEntity.ExtrasEntity> extra_list = new ArrayList<>();
    private SystemNoticeBean.DataEntity.ExtrasEntity bean;
    private boolean isEnd = false;
    private boolean isRefresh = true;
    private boolean isFirst = true;
    private int pageNo = 0;
    @Bind(R.id.back)
    LinearLayout back;
    SystemTask task;
    @Bind(R.id.tv_no_context)
    TextView tv_no_context;
    private Intent intent;
    @Bind(R.id.ll_no_context)
    LinearLayout ll_no_context;
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��
    private int index = 0;
    private int top = 0;
    private SystemNoticeAdapter adapter;
    private ReadTask readTask;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_ststem_notice;
    }

    private void prePage() {
        mSwipyRefreshLayout.setVisibility(View.VISIBLE);
        ll_no_context.setVisibility(View.GONE);
    }

    private void preRefresh() {
        isFirst = true;
        isEnd = false;
        pageNo = 0;
        isRefresh = true;
        top = 0;
        index = 0;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText(getResources().getString(R.string.system_notifaciton));
        mSwipyRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        task = new SystemTask();
        task.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e(TAG, "ssss");
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            //����ˢ��
            preRefresh();
            task = new SystemTask();
            task.execute();
        } else {
            index = listView.getFirstVisiblePosition();
            View view3 = listView.getChildAt(0);
            top = (view3 == null) ? 0 : view3.getTop();
            if (isEnd) {
                mSwipyRefreshLayout.setRefreshing(false);
                UiHelp.canToast(SystemNoticeActivity.this);
            } else {
                pageNo = pageNo + 1;
                isRefresh = false;
                task = new SystemTask();
                task.execute();
            }
        }
    }

    @Override
    public void onClicks(View view, int position) {
        DeleteTask deleteTask = new DeleteTask(list.get(position).getId());
        deleteTask.execute();
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

    private class SystemTask extends AsyncTask<Void, Void, Map<String, Object>> {
        @Override
        protected void onPreExecute() {
            prePage();
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(SystemNoticeActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doPushPost(Constant.BASE_URL + Constant.PHONE + Constant.SYSTEM_NOTICE + pageNo + "/", SystemNoticeActivity.this);
                    if (isRefresh && list.size() != 0) {
                        list.clear();
                    }
                    if (FastJsonTools.getMap(body) != null && FastJsonTools.getMap(body).get("data") != null && !FastJsonTools.getMap(body).get("data").toString().equals("[]")) {
                        list.addAll(FastJsonTools.getBeanList(FastJsonTools.getMap(body).get("data").toString(), SystemNoticeBean.DataEntity.class));
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
            Log.e(TAG, aVoid.get("data") + "");
            if (aVoid == null || aVoid.get("data") == null || aVoid.get("data").toString().equals("[]")) {
                //�˴�Ϊ��
                mSwipyRefreshLayout.setRefreshing(false);
                if (pageNo == 0) {
                    noContext();
                    tv_no_context.setText(getResources().getString(R.string.no_system_notice));
                } else {
                    lastPageNoToast(aVoid);
                }
            } else {
                hasContext();
                lastPageNoToast(aVoid);
                mSwipyRefreshLayout.setRefreshing(false);
                adapter = new SystemNoticeAdapter(SystemNoticeActivity.this, list, SystemNoticeActivity.this);
                listView.setAdapter(adapter);
                listView.setSelectionFromTop(index, top);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(new SystemNoticeAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, SystemNoticeBean.DataEntity.ExtrasEntity data, int pos) {
                        if (list != null || list.size() != 0 || extra_list.size() != 0) {
                            if (!UiHelp.isFastClick()) {
                                try {
                                    Log.e(TAG, list.get(pos) + "");
                                    Log.e(TAG, list.get(pos).getExtras().getApi() + "");
                                    if (list.get(pos).getExtras().getApi() != null) {
                                        if (StringUtils.isEquals("web", list.get(pos).getExtras().getApi())) {
                                            readTask = new ReadTask(list.get(pos).getId());
                                            readTask.execute();
                                            UiHelp.sendBroad(SystemNoticeActivity.this);
                                            intent = new Intent(SystemNoticeActivity.this, ThreeWebviewActivity.class);
                                            intent.putExtra("title", "");
                                            intent.putExtra("url", list.get(pos).getExtras().getUrl());
                                            intent.putExtra("id", list.get(pos).getId() + "");
                                            startActivity(intent);
                                        } else if (StringUtils.isEquals("projectdetail", list.get(pos).getExtras().getApi())) {
                                            readTask = new ReadTask(list.get(pos).getId());
                                            readTask.execute();
                                            UiHelp.sendBroad(SystemNoticeActivity.this);
                                            intent = new Intent(SystemNoticeActivity.this, InvestFinacingDetailsActivity.class);
//                                            intent.putExtra("title","");
//                                            intent.putExtra("url",list.get(pos).getExtras().getUrl());
                                            intent.putExtra("id", list.get(pos).getId() + "");
                                            startActivity(intent);
                                        } else if (StringUtils.isEquals("investor", list.get(pos).getExtras().getApi())) {
                                            readTask = new ReadTask(list.get(pos).getId());
                                            readTask.execute();
                                            UiHelp.sendBroad(SystemNoticeActivity.this);
                                        } else if (StringUtils.isEquals("roadshow", list.get(pos).getExtras().getApi())) {
                                            readTask = new ReadTask(list.get(pos).getId());
                                            readTask.execute();
                                            UiHelp.sendBroad(SystemNoticeActivity.this);
                                        } else if (StringUtils.isEquals("participate", list.get(pos).getExtras().getApi())) {
                                            readTask = new ReadTask(list.get(pos).getId());
                                            readTask.execute();
                                            UiHelp.sendBroad(SystemNoticeActivity.this);
                                        } else if (StringUtils.isEquals("system", list.get(pos).getExtras().getApi())) {
                                            readTask = new ReadTask(list.get(pos).getId());
                                            readTask.execute();
                                            UiHelp.sendBroad(SystemNoticeActivity.this);
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                });
            }
        }


    }

    private void hasContext() {
        ll_no_context.setVisibility(View.GONE);
        mSwipyRefreshLayout.setVisibility(View.VISIBLE);
    }

    private void lastPageNoToast(Map<String, Object> map) {
        if (Integer.parseInt(map.get("status").toString()) == -1 && pageNo != 0) {
            isEnd = true;
        }
    }

    private void noContext() {
        ll_no_context.setVisibility(View.VISIBLE);
        mSwipyRefreshLayout.setVisibility(View.GONE);
    }

    private class DeleteTask extends AsyncTask<Void, Void, CommonBean> {
        private int id;

        public DeleteTask(int id) {
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            prePage();
            super.onPreExecute();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(SystemNoticeActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.DELETE_SYSMSG + id + "/", SystemNoticeActivity.this);
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
        protected void onPostExecute(CommonBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    task = new SystemTask();
                    task.execute();
                }
            }
        }
    }

    private class ReadTask extends AsyncTask<Void, Void, CommonBean> {
        private int id;

        public ReadTask(int id) {
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            prePage();
            super.onPreExecute();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(SystemNoticeActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.READ_SYSOVER + id + "/", SystemNoticeActivity.this);
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
        protected void onPostExecute(CommonBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    task = new SystemTask();
                    task.execute();
                }
            }
        }
    }
}