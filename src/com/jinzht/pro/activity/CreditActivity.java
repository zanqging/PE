package com.jinzht.pro.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.adapter.CreDitAdapter;
import com.jinzht.pro.adapter.CreditPopAdapter;
import com.jinzht.pro.materialsearchview.MaterialSearchView;
import com.jinzht.pro.model.CreditBean;
import com.jinzht.pro.model.CreditMsgBean;
import com.jinzht.pro.utils.AsynOkUtils;
import com.jinzht.pro.utils.DisplayUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.ScreenUtils;
import com.jinzht.pro.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Administrator on 2015/11/4.
 *
 * ���Ų�ѯҳ�棬����ҳ�������
 */
public class CreditActivity extends BaseActivity {
    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;
    public static PopupWindow popWindow;
    @Bind(R.id.rl_search)
    RelativeLayout rl_search;// �����ؼ�
    private Intent intent;
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;// ������ͼ
    private CreDitAdapter adapter;
    private List<CreditBean.DataEntity> credit_list = new ArrayList<>();
    private List<String> credit_msg = new ArrayList<>();
    String[] credit_tip = null;
    @Bind(R.id.ed_search)
    EditText ed_search;// ��ѯ�����
    @Bind(R.id.lv_ciredit)
    ListView lv_ciredit;// �б�
    private ListView pop_lv_credit;
    private boolean isError = false;
    private GetCreditTask getCreditTask;

    // ����б���Ŀ��ת����ҳ����
    @OnItemClick(R.id.lv_ciredit)
    void ps(int pos) {
        /**���Ų�ѯ*/
        intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("url", credit_list.get(pos).getUrl());
        startActivity(intent);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_credit;
    }

    // ��������
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                searchView.autoShow();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(String msg) {
        if (msg.equals("finish")) {
            finish();
        }
    }

    @Override
    protected void init() {
        getCreditTask = new GetCreditTask();
        getCreditTask.execute();
//        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.showView(rl_search);
        searchView.setCursorDrawable(R.drawable.color_cursor_white);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e(TAG, query);
                /**���Ų�ѯ*/
                CreditTask creditTask = new CreditTask(query);
                creditTask.execute();
                popWindow.dismiss();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
                rl_search.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                rl_search.setVisibility(View.VISIBLE);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        }).start();

        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            if (popWindow != null && popWindow.isShowing()) {
                popWindow.dismiss();
            }
            finish();
        } else {
            if (popWindow != null && popWindow.isShowing()) {
                popWindow.dismiss();
            }
            finish();
            super.onBackPressed();
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

    }

    private void showPopup(View parent) {
//        if (popWindow == null) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pop_credit, null);
        // ����һ��PopuWidow����
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        pop_lv_credit = (ListView) view.findViewById(R.id.pop_lv_credit);
        CreditPopAdapter adapter = new CreditPopAdapter(mContext, credit_msg);
        pop_lv_credit.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        }
        popWindow.setFocusable(false);
        // ����������������ʧ
        popWindow.setOutsideTouchable(false);
        // ���ñ����������Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı���
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //����̲��ᵲ��popupwindow
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //���ò˵���ʾ��λ��
        popWindow.showAtLocation(parent, Gravity.TOP, 0, DisplayUtils.convertDipOrPx(CreditActivity.this, 49) + ScreenUtils.getStatusHeight(mContext));
        //�����˵��Ĺر��¼�
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        pop_lv_credit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchView.closeSearch();
                popWindow.dismiss();
                CreditTask creditTask = new CreditTask(credit_msg.get(i));
                creditTask.execute();
            }
        });
    }

    private class CreditTask extends AsyncTask<Void, Void, CreditBean> {
        private String message;

        public CreditTask(String message) {
            this.message = message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CreditBean doInBackground(Void... voids) {
            String body = "";
            try {
                body = AsynOkUtils.doPushPost("wd", message, Constant.BASE_URL + Constant.PHONE + Constant.CREDIT, CreditActivity.this);
                Log.e(TAG, body);
                if (credit_list.size() != 0) {
                    credit_list.clear();
                }
                if (FastJsonTools.getBean(body, CreditBean.class) != null) {
                    credit_list.addAll(FastJsonTools.getBean(body, CreditBean.class).getData());
                }
            } catch (Exception e) {
                isError = true;
                Log.e(TAG, e.getMessage());
                okHttpException.httpException(e);
                e.printStackTrace();
            }
            return FastJsonTools.getBean(body, CreditBean.class);
        }

        @Override
        protected void onPostExecute(CreditBean stringObjectMap) {
            super.onPostExecute(stringObjectMap);
            dismissProgressDialog();
            if (stringObjectMap == null) {
                return;
            }
            if (!isError && credit_list.size() == 0) {
                blankPage();
            }
            if (stringObjectMap.getCode() == 0) {
                adapter = new CreDitAdapter(mContext, credit_list);
                lv_ciredit.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private class GetCreditTask extends AsyncTask<Void, Void, CreditMsgBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CreditMsgBean doInBackground(Void... voids) {
            String body = "";
            try {
                body = AsynOkUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.CREDIT, CreditActivity.this);
                Log.e(TAG, body);
                if (credit_msg.size() != 0) {
                    credit_msg.clear();
                }
                if (FastJsonTools.getBean(body, CreditMsgBean.class) != null && FastJsonTools.getBean(body, CreditMsgBean.class).getData() != null) {
                    credit_msg.addAll(FastJsonTools.getBean(body, CreditMsgBean.class).getData().getCompany());
                }
                credit_tip = (String[]) credit_msg.toArray(new String[credit_msg.size()]);
                Log.e(TAG, credit_msg.size() + "ss" + credit_tip.length);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
            return FastJsonTools.getBean(body, CreditMsgBean.class);
        }

        @Override
        protected void onPostExecute(CreditMsgBean stringObjectMap) {
            super.onPostExecute(stringObjectMap);
            dismissProgressDialog();
            if (stringObjectMap != null) {
                if (stringObjectMap.getCode() == -1) {
                    return;
                }
                if (stringObjectMap.getCode() == 0) {
//                    searchView.setSuggestions(credit_msg);
                    showPopup(rl_search);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popWindow != null) {
            popWindow.dismiss();
            popWindow = null;
        }
        if (getCreditTask != null && getCreditTask.getStatus() == AsyncTask.Status.RUNNING) {
            getCreditTask.cancel(true);
        }
    }
}