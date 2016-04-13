package com.jinzht.pro.fragment;

import android.app.AlertDialog;
import android.content.*;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.*;
import android.widget.*;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.MainActivity;
import com.jinzht.pro.activity.PublishCircleActivity;
import com.jinzht.pro.adapter.CircleMainListViewAdapter;
import com.jinzht.pro.eventbus.CircleRefreshEvent;
import com.jinzht.pro.lookpictrue.ImageInfo;
import com.jinzht.pro.model.*;
import com.jinzht.pro.mycircleimage.PolygonImageView;
import com.jinzht.pro.photoselecter.model.PhotoModel;
import com.jinzht.pro.photoselecter.ui.PhotoPreviewActivity;
import com.jinzht.pro.photoselecter.util.CommonUtils;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayout;
import com.jinzht.pro.swipyrefreshlayout.SwipyRefreshLayoutDirection;
import com.jinzht.pro.transitionseverywhere.Scene;
import com.jinzht.pro.utils.*;
import com.jinzht.pro.view.InputMethodRelativeLayout;
import com.jinzht.pro.view.MultiStateView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/10.
 */
public class CircleFragment extends BaseFragment implements CircleMainListViewAdapter.OnPhotoItemClickListener,
        SwipyRefreshLayout.OnRefreshListener, CircleMainListViewAdapter.TextCallBack, CircleMainListViewAdapter.OnCommentItemClickListener,
        InputMethodRelativeLayout.OnSizeChangedListenner, View.OnClickListener {
    @Bind(R.id.ll_main_listView)
    ListView ll_main_listView;
    @Bind(R.id.keyboardRelativeLayout)
    InputMethodRelativeLayout inputMethodRelativeLayout;
    public static final int REQUEST_CODE = 100;
    private ArrayList<String> imgList = new ArrayList<>();
    private ArrayList<ImageInfo> imgImageInfos = new ArrayList<>();
    private boolean isRefresh = true;
    private boolean isFirst = true;
    private int pageNO = 0;
    private boolean isEnd = false;
    private Scene mScene1;
    private CircleMainListViewAdapter adapter;
    CircleTask task;//listView�е�һ�������
    private int pos;
    private int flag = 0;
    private int pos_one = 0;
    private int pos_two = 0;
    private ArrayList<CircleBean.DataEntity> title_list = new ArrayList<>();
    private List<CircleBean.DataEntity.LikeEntity> lover_list = new ArrayList<>();
    private EditText ed_message;
    private TextView send;
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyRefreshLayout;//ˢ��
    private ReplySomeOneTask replySomeOneTask;
    private ReplyDetailSomeOneTask replyDetailSomeOneTask;
    private int second_id;
    private ArrayList<PhotoModel> selected = new ArrayList<>();
    private CircleBean.DataEntity.CommentEntity contentEntity = null;
    private CircleBean.DataEntity.LikeEntity likersEntity = null;
    private ImageView iv_header;
    private PolygonImageView polygonImageView;
    private ImageUploadTask imageUploadTask;
    public static PopupWindow popWindow;
    private boolean is_over = true;
    private boolean is_loading = false;
    @Bind(R.id.white_circle)
    ImageView white_circle;
    private int heightDifference = 0;

    @OnClick(R.id.back_to_top)
    void ins() {
        if (UiHelp.isFastClick()) {
            if (title_list.size() != 0) {
                ll_main_listView.smoothScrollToPosition(0);
            }
        }
    }

    @OnClick(R.id.rl_search)
    void in() {
        Intent intent = new Intent(getActivity(), PublishCircleActivity.class);
        startActivityForResult(intent, 119);
    }

    @OnClick(R.id.menu)
    void menu() {
        MainActivity.menuDrawer.toggleMenu();
    }

    @OnItemClick(R.id.ll_main_listView)
    void ll(int pos) {
        if (pos == 0) {
            showImageDialog();
        }
    }

    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private int scrolledX, scrolledY;

    @Override
    protected int setLayout(LayoutInflater inflater) {
        return R.layout.fragment_circle;
    }

    @Override
    protected void onFirstUserVisible() {
        inputMethodRelativeLayout.setOnSizeChangedListenner(this);
        LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = lif.inflate(R.layout.item_circle_header, ll_main_listView, false);
        iv_header = (ImageView) headerView.findViewById(R.id.iv_circle);
        polygonImageView = (PolygonImageView) headerView.findViewById(R.id.result_iamge);
//        polygonImageView.setImageResource(R.drawable.core_team_detail);
        iv_header.setImageDrawable(getResources().getDrawable(R.drawable.core_team_detail));
        polygonImageView.setEnabled(false);
        ll_main_listView.addHeaderView(headerView);
        mSwipyRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        ImageHeaderTask imageHeaderTask = new ImageHeaderTask();
        imageHeaderTask.execute();
//        offLine();
        task = new CircleTask();
        task.execute();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                    }
//                });
//            }
//        },1000);
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        preRefresh();
                        ImageHeaderTask imageHeaderTask = new ImageHeaderTask();
                        imageHeaderTask.execute();
                        task = new CircleTask();
                        task.execute();
                    }
                });
        showOrHind();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.MY_ACTION);
        filter.setPriority(Integer.MAX_VALUE);
        getActivity().registerReceiver(myReceiver, filter);

    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "myReceiver receive", Toast.LENGTH_SHORT)
                    .show();
            task = new CircleTask();
            task.execute();
        }

    };

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEvent(CircleRefreshEvent event) {
//            SuperToastUtils.showSuperToast(getActivity(),1,event.msg);
        preRefresh();
        task = new CircleTask();
        task.execute();
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void onFirstUserInvisble() {
        Log.e(TAG, "onstop");
    }

    private void showOrHind() {
        if (MainActivity.read_two > 0) {
            white_circle.setVisibility(View.VISIBLE);
        } else {
            white_circle.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onUserVisble() {
        showOrHind();
    }

    @Override
    protected void init() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, int pos_second) {
        switch (parent.getId()) {
            case R.id.lv_item_listView:
                updatePosition();
                titleReturn();
                flag = 2;
                /**�ظ�������*/
                replyDetail(view, position, pos_second);
                break;
            case R.id.gv_listView_main_gridView:
                /**�����ͼƬ*/
                if (!is_loading) {
                    titleReturn();
                    selected.clear();
                    for (int i = 0; i < title_list.get(position).getPic().size(); i++) {
                        PhotoModel photoModel = new PhotoModel(title_list.get(position).getPic().get(i), true);
                        selected.add(photoModel);
                    }
                    priview(pos_second);
                }
                break;
        }
    }

    private void titleReturn() {
        if (title_list.size() == 0) {
            return;
        }
    }

    private void priview(int pos) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("photos", selected);
        bundle.putInt("flag", 1);
        bundle.putInt("pos", pos);
        CommonUtils.launchActivity(getActivity(), PhotoPreviewActivity.class, bundle);
    }

    private void preRefresh() {
        index = 0;
        top = 0;
        isFirst = true;
        isEnd = false;
        pageNO = 0;
        isRefresh = true;
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            //����ˢ��
            preRefresh();
            task = new CircleTask();
            task.execute();
            Log.e(TAG, title_list.size() + "size0");
        } else {
            updatePosition();
            isRefresh = false;
            if (isEnd) {
                if (!UiHelp.isLongFastClick()) {
                    SuperToastUtils.showSuperToast(getActivity(), 1, R.string.last_page);
                }
                mSwipyRefreshLayout.setRefreshing(false);
            } else {
                pageNO = pageNO + 1;
                isRefresh = false;
                task = new CircleTask();
                task.execute();
            }
            Log.e(TAG, title_list.size() + "size");
        }
    }

    private void updatePosition() {
        index = ll_main_listView.getFirstVisiblePosition();
        View view1 = ll_main_listView.getChildAt(0);
        top = (view1 == null) ? 0 : view1.getTop();
        Log.e(TAG, "index" + index + "top" + top);
    }

    @Override
    public void onTextClicks(View view, int position) {
        switch (view.getId()) {
            case R.id.tv_love:
                if (position == 0) {
                    if (is_over) {
                        love(position);
                    }
                } else {
                    love(position);
                }
                break;
            case R.id.tv_comment:
                if (position == 0) {
                    if (is_over) {
                        comment(view, position);
                        ll_main_listView.smoothScrollBy(getKeyboardHeight(view) - heightDifference, 100);
//                        ll_main_listView.scrollBy(0,ver_location[1]-663);
                    }
                } else {
                    comment(view, position);
                    ll_main_listView.smoothScrollBy(getKeyboardHeight(view) - heightDifference, 100);
//                    ll_main_listView.smoothScrollToPosition(ver_location[1]-631);
                }
                break;
            case R.id.iv_user:
//                Intent intent = new Intent(getActivity(), CirclePersonActivity.class);
//                intent.putExtra("id",title_list.get(position).getUid());
//                startActivity(intent);
                break;
            case R.id.tv_delete:
                updatePosition();
                showDeleteDialog(position);
                break;
        }
    }

    private void comment(View view, int position) {
        updatePosition();
        flag = 1;
        pos_one = position;
        UiHelp.openOrHide(getActivity());
        showPopup(view);
        ed_message.setHint(getResources().getString(R.string.reply) + title_list.get(position).getName());
        pos = title_list.get(position).getId();
    }

    private int getKeyboardHeight(View view) {
        int ver_location[] = new int[2];
        view.getLocationInWindow(ver_location);
        Log.e(TAG, ver_location[1] + "ver");
        return ver_location[1];
    }

    private void love(int position) {
        updatePosition();
        Log.e(TAG, title_list.get(position).getIs_like() + "" + title_list.get(position).getName() + "'");
        int is_love = (title_list.get(position).getIs_like()) ? 1 : 0;
        LikeSomeOneTask likeSomeOneTask = new LikeSomeOneTask(position, is_love);
        likeSomeOneTask.execute();
    }

    private void showDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.submit_delete))
                .setPositiveButton(getResources().getString(R.string.submit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        int item_id = title_list.get(position).getId();
                        DeleteTask tasks = new DeleteTask(position);
                        tasks.execute();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.crop__cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }

    private void showDeleteReplyDialog(int position, int pos_second) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.submit_delete))
                .setPositiveButton(getResources().getString(R.string.submit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        int item_id = title_list.get(position).getComment().get(pos_second).getId();
                        DeleteDetailTask taskss = new DeleteDetailTask(item_id);
                        taskss.execute();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.crop__cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }

    private void showPopup(View parent) {
//        if (popWindow == null) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.circle_popup_edit, null);
        // ����һ��PopuWidow����
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtils.convertDipOrPx(mContext, 50), true);
        ed_message = (EditText) view.findViewById(R.id.ed_message);
        send = (TextView) view.findViewById(R.id.send);
        send.setOnClickListener(this);
//        }
        ed_message.requestFocus();
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                return false;
            }
        });
    }

    @Override
    public void onItemClick(TextView view, int pos1, int position, int pos_second) {
        updatePosition();
        titleReturn();
        flag = 2;
        replyDetail(view, position, pos_second);
        /**�ظ�������*/
    }

    private void replyDetail(View view, int position, int pos_second) {
        pos_one = position;
        pos_two = pos_second;
        pos = title_list.get(position).getId();
        second_id = title_list.get(position).getComment().get(pos_second).getId();
        if (!title_list.get(position).getComment().get(pos_second).getFlag()) {
            showPopup(view);
            UiHelp.openOrHide(getActivity());
            Log.e(TAG, popWindow.getContentView().getHeight() + "pop");
            ll_main_listView.smoothScrollBy(getKeyboardHeight(view) - heightDifference, 100);
            ed_message.setHint(getResources().getString(R.string.reply) + title_list.get(position).getComment().get(pos_second).getName());
        } else {
            showDeleteReplyDialog(position, pos_second);
        }
    }

    @Override
    public void onSizeChange(boolean paramBoolean, int w, int h) {
        if (paramBoolean) {
            //???
            Log.e(TAG, "one" + h);
            Rect r = new Rect();
            inputMethodRelativeLayout.getWindowVisibleDisplayFrame(r);
            int screenHeight = inputMethodRelativeLayout.getRootView().getHeight();
            heightDifference = screenHeight - (r.bottom - r.top) - DisplayUtils.convertDipOrPx(mContext, 50);
            Log.e("Keyboard Size", "Size: " + heightDifference);
        } else {
            Log.e(TAG, "ones");
            if (popWindow != null && popWindow.isShowing()) {
                popWindow.dismiss();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send:
                if (flag == 1) {
                    if (StringUtils.isEmpty(ed_message.getText().toString())) {
                        if (!UiHelp.isFastClick()) {
                            SuperToastUtils.showSuperToast(getActivity(), 1, R.string.null_reply);
                        }
                    } else {
                        replySomeOneTask = new ReplySomeOneTask(pos);
                        replySomeOneTask.execute();
                        dissmissPop();
                    }
                } else if (flag == 2) {
                    if (StringUtils.isEmpty(ed_message.getText().toString())) {
                        if (!UiHelp.isFastClick()) {
                            SuperToastUtils.showSuperToast(getActivity(), 1, R.string.null_reply);
                        }
                    } else {
                        replyDetailSomeOneTask = new ReplyDetailSomeOneTask(pos, second_id);
                        replyDetailSomeOneTask.execute();
                        dissmissPop();
                    }
                }
                break;
        }
    }

    private void dissmissPop() {
        if (popWindow != null & popWindow.isShowing()) {
            popWindow.dismiss();
        }
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
        task = new CircleTask();
        task.execute();
    }

    private void offLine() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String body = null;
//                ACache aCache = ACache.get(getActivity());
//                body = aCache.getAsString("CircleBean" + pageNO);
                Log.e(TAG, body + "ss");
                if (body == null) {
                    return;
                }
                final String finalBody = body;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadOver(FastJsonTools.getBean(finalBody, CircleBean.class));
                    }
                });
            }
        }).start();
    }

    private class CircleTask extends AsyncTask<Void, Void, CircleBean> {
        @Override
        protected void onPreExecute() {
            mSwipyRefreshLayout.setRefreshing(true);
            is_loading = true;
            super.onPreExecute();
        }

        @Override
        protected CircleBean doInBackground(Void... voids) {
            String body = "";
//            ACache aCache = ACache.get(getActivity());
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.CIRCLE_LIST + pageNO + "/", getActivity());
//                    aCache.put("CircleBean" + pageNO, body, 1 * ACache.TIME_DAY);
                } catch (Exception e) {
                    e.printStackTrace();
                    okHttpException.httpException(e);
                }
                Log.e(TAG, body);
            } else {
//                body = aCache.getAsString("CircleBean" + pageNO);
                if (body == null) {
                    errorPage();
                    return null;
                }
            }
            preLoad(body);
            return FastJsonTools.getBean(body, CircleBean.class);
        }

        @Override
        protected void onPostExecute(CircleBean aVoid) {
            super.onPostExecute(aVoid);
            mSwipyRefreshLayout.setRefreshing(false);
            if (aVoid != null) {
                loadOver(aVoid);
            }
        }
    }

    private void loadOver(CircleBean aVoid) {
        if (aVoid != null) {
            if (aVoid.getCode() == -1) {
                LoginTask loginTask = new LoginTask();
                loginTask.execute();
                is_loading = false;
                return;
            }
            lastPage(aVoid);
            if (aVoid.getData() != null) {
                try {
                    is_over = true;
                    adapter = new CircleMainListViewAdapter(getActivity(), CircleFragment.this, CircleFragment.this, title_list, imgList, CircleFragment.this);
                    ll_main_listView.setAdapter(adapter);
                    ll_main_listView.setSelectionFromTop(index, top);
                    adapter.notifyDataSetChanged();
                    is_loading = false;
                } catch (Exception e) {
                }
            }
        }
    }

    private void preLoad(String body) {
        if (isRefresh && title_list.size() != 0) {
            title_list.clear();
        }
        if (FastJsonTools.getBean(body, CircleBean.class) != null && FastJsonTools.getBean(body, CircleBean.class).getData() != null) {
            title_list.addAll(FastJsonTools.getBean(body, CircleBean.class).getData());
        }
    }

    private void lastPage(CircleBean map) {
        try {
            if (map.getCode() == 2 && pageNO != 0) {
                SuperToastUtils.showSuperToast(getActivity(), 1, R.string.last_page);
                isEnd = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ReplySomeOneTask extends AsyncTask<Void, Void, CommentBean> {
        private int id;

        public ReplySomeOneTask(int id) {
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommentBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    Log.e(TAG, "id" + id + "context:" + ed_message.getText().toString());
                    body = AsynOkUtils.doPushPost("content", ed_message.getText().toString(), Constant.BASE_URL + Constant.PHONE + Constant.REPLY_SOMEONE + id + "/", mContext);
                    Log.e(TAG, body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, CommentBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommentBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                try {
                    contentEntity = new CircleBean.DataEntity.CommentEntity(aVoid.getData().getName(), aVoid.getData().getUid(), aVoid.getData().getId(),
                            aVoid.getData().getPhoto(), aVoid.getData().getFlag(), aVoid.getData().getContent());
                    title_list.get(pos_one).getComment().add(0, contentEntity);
                    adapter = new CircleMainListViewAdapter(getActivity(), CircleFragment.this, CircleFragment.this, title_list, imgList, CircleFragment.this);
                    ll_main_listView.setAdapter(adapter);
                    ll_main_listView.setSelectionFromTop(index, top);
                    adapter.notifyDataSetChanged();
                    ed_message.setText("");
//                    SuperToastUtils.showSuperToast(getActivity(),1,aVoid.getMsg());
                } catch (Exception e) {
                }
            }

        }
    }

    private class ReplyDetailSomeOneTask extends AsyncTask<Void, Void, CommentBean> {
        private int ids;//������id
        private int id_one;//comment��ͷ��id

        public ReplyDetailSomeOneTask(int ids, int id_one) {
            this.ids = ids;
            this.id_one = id_one;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommentBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doForgetPost("at", id_one + "", "content", ed_message.getText().toString(), Constant.BASE_URL + Constant.PHONE + Constant.REPLY_SOMEONE + ids + "/", mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, CommentBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CommentBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                contentEntity = new CircleBean.DataEntity.CommentEntity(aVoid.getData().getName(), aVoid.getData().getUid(), aVoid.getData().getId(),
                        aVoid.getData().getPhoto(), aVoid.getData().getFlag(), aVoid.getData().getContent(), aVoid.getData().getAt_name(), aVoid.getData().getAt_uid());
                title_list.get(pos_one).getComment().add(0, contentEntity);
                adapter = new CircleMainListViewAdapter(getActivity(), CircleFragment.this, CircleFragment.this, title_list, imgList, CircleFragment.this);
                ll_main_listView.setAdapter(adapter);
                ll_main_listView.setSelectionFromTop(index, top);
                adapter.notifyDataSetChanged();
                ed_message.setText("");
            }
        }
    }

    private class LikeSomeOneTask extends AsyncTask<Void, Void, CircleLikeBean> {
        private int ids;//��item��id
        private int flag;//1���ǵ��ޣ�0��ȡ������

        public LikeSomeOneTask(int ids, int flag) {
            this.ids = ids;
            this.flag = flag;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CircleLikeBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.LOVE_SOMEONE + title_list.get(ids).getId() + "/" + flag + "/", mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, CircleLikeBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CircleLikeBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid == null) {
                return;
            }
            if (aVoid.getCode() == -1) {
                return;
            }
            try {
                if (aVoid.getCode() == 0) {
                    if (aVoid.getData().getIs_like()) {
                        likersEntity = new CircleBean.DataEntity.LikeEntity(aVoid.getData().getName(), aVoid.getData().getPhoto(), aVoid.getData().getUid());
                        title_list.get(ids).getLike().add(0, likersEntity);
                        title_list.get(ids).setIs_like(aVoid.getData().getIs_like());
                        update();
                    } else {
                        int remove = 0;
                        for (int i = 0; i < title_list.get(ids).getLike().size(); i++) {
                            if (title_list.get(ids).getLike().get(i).getUid() == aVoid.getData().getUid()) {
                                remove = i;
                            }
                        }
                        title_list.get(ids).getLike().remove(remove);
                        title_list.get(ids).setIs_like(aVoid.getData().getIs_like());
                        update();
                    }
                }
            } catch (Exception e) {

            }

        }
    }

    private void update() {
        adapter = new CircleMainListViewAdapter(getActivity(), CircleFragment.this, CircleFragment.this, title_list, imgList, CircleFragment.this);
        ll_main_listView.setAdapter(adapter);
        ll_main_listView.setSelectionFromTop(index, top);
        adapter.notifyDataSetChanged();
    }

    private class DeleteTask extends AsyncTask<Void, Void, CommonBean> {
        private int ids;//��item��id

        public DeleteTask(int ids) {
            this.ids = ids;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.DELETE_SOMEONE + title_list.get(ids).getId() + "/", mContext);
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
        protected void onPostExecute(CommonBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    title_list.remove(ids);
                    update();
                } else {
                    SuperToastUtils.showSuperToast(getActivity(), 1, aVoid.getMsg());
                }
            }

        }
    }

    private class ImageHeaderTask extends AsyncTask<Void, Void, CircleBackbgBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected CircleBackbgBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.CIRCLEHEADER, mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, CircleBackbgBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CircleBackbgBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    UpLoadImageUtils.getImage(aVoid.getData().getBg(), iv_header);
                    UpLoadImageUtils.getImage(aVoid.getData().getPhoto(), polygonImageView);
                    if (title_list.size() != 0) {
                        updatePosition();
                        update();
                    }
                }
            }

        }
    }

    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.dialog_image_title))
//                .setIcon(R.drawable.ic_launcher)
//                .setAdapter();//�Զ����б�������image,,,
                .setItems(R.array.image_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "circle.jpg"));
                            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(openCameraIntent, Constant.TAKE_PICTURE);
                        } else {
                            Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            openAlbumIntent.setType("image/*");
                            startActivityForResult(openAlbumIntent, Constant.CHOOSE_PICTURE);
                        }
                    }
                });
        builder.create().show();
    }

    private class DeleteDetailTask extends AsyncTask<Void, Void, CommonBean> {
        private int ids;//��item��id

        public DeleteDetailTask(int ids) {
            this.ids = ids;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.DELETE_REPLY_SOME + ids + "/", mContext);
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
        protected void onPostExecute(CommonBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                if (aVoid.getCode() == 0) {
                    try {
                        title_list.get(pos_one).getComment().remove(pos_two);
                        update();
                    } catch (Exception e) {

                    }
                } else {
                    SuperToastUtils.showSuperToast(getActivity(), 1, aVoid.getMsg());
                }
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (119 == requestCode) {
            if (resultCode == 119) {
                is_over = false;
                preRefresh();
                String position = SharePreferencesUtils.getCircleInformation(mContext, "position");
                CircleBean.DataEntity cir = new CircleBean.DataEntity(SharePreferencesUtils.getCircleInformation(mContext, "image"),
                        data.getExtras().getString("edit"), /*SharePreferencesUtils.getCircleInformations(mContext,"id")*/0,
                        SharePreferencesUtils.getCircleInformation(mContext, "name"), position,
                        SharePreferencesUtils.getCircleInformation(mContext, "city"), data.getExtras().getStringArrayList("list"));
                title_list.add(0, cir);
                adapter = new CircleMainListViewAdapter(getActivity(), CircleFragment.this, CircleFragment.this, title_list, imgList, CircleFragment.this);
                ll_main_listView.setAdapter(adapter);
                ll_main_listView.setSelectionFromTop(index, top);
                adapter.notifyDataSetChanged();
                PhotoTask photoTask = new PhotoTask(data);
                photoTask.execute();
            }
        }
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case Constant.TAKE_PICTURE:
                    iv_header.setImageBitmap(ImageHandleUtils.compress440ImageFromFile(Environment.getExternalStorageDirectory() + "/circle.jpg"));
                    imageUploadTask = new ImageUploadTask(Environment.getExternalStorageDirectory() + "/circle.png");
                    imageUploadTask.execute();
                    adapter.notifyDataSetChanged();
                    break;
                case Constant.CHOOSE_PICTURE:
                    Uri originalUri = data.getData();
//                    ImageHandleUtils.getRealFilePath(getActivity(),originalUri);
                    iv_header.setImageBitmap(ImageHandleUtils.compress440ImageFromFile(ImageHandleUtils.getRealFilePath(getActivity(), originalUri)));
                    adapter.notifyDataSetChanged();
                    imageUploadTask = new ImageUploadTask(Environment.getExternalStorageDirectory() + "/circle.png");
                    imageUploadTask.execute();
                    break;
            }
        }
    }

    private class ImageUploadTask extends AsyncTask<Void, Void, CommonBean> {
        private String paths;

        public ImageUploadTask(String paths) {
            this.paths = paths;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doOnePhotoPost(paths, Constant.BASE_URL + Constant.PHONE + Constant.CIRCLEHEADER, mContext);
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
                    if (title_list.size() != 0) {
                        updatePosition();
                        update();
                    }
                }
            }

        }
    }

    private class PhotoTask extends AsyncTask<Void, Void, CirclePublishBean> {
        private Intent date;

        public PhotoTask(Intent date) {
            this.date = date;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected CirclePublishBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doPhotoPost(date.getStringArrayListExtra("list"),
                            Constant.BASE_URL + Constant.PHONE + Constant.PUBLISH_STATES, date.getExtras().getString("edit"), getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, body);
                return FastJsonTools.getBean(body, CirclePublishBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CirclePublishBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    return;
                }
                try {
                    if (aVoid.getCode() == 0) {
                        CircleBean.DataEntity dataEntity = new CircleBean.DataEntity(aVoid.getData().getPhoto(),
                                aVoid.getData().getContent(), aVoid.getData().getId(), aVoid.getData().getIs_like(),
                                aVoid.getData().getRemain_comment(), aVoid.getData().getName(), aVoid.getData().getDatetime(),
                                aVoid.getData().getAddr(), new CircleBean.DataEntity().getLike(), new CircleBean.DataEntity().getComment(),
                                aVoid.getData().getRemain_like(), aVoid.getData().getPic(), aVoid.getData().getPosition(),
                                aVoid.getData().getUid(), aVoid.getData().getFlag());
                        title_list.set(0, dataEntity);
                        adapter = new CircleMainListViewAdapter(getActivity(), CircleFragment.this, CircleFragment.this, title_list, imgList, CircleFragment.this);
                        ll_main_listView.setAdapter(adapter);
                        ll_main_listView.setSelectionFromTop(index, top);
                        adapter.notifyDataSetChanged();
                        is_over = true;
                    }
                } catch (Exception e) {
                }
                try {
                    FileUtil.deleteAllFiles(new File(Constant.FILE_PATH + "/JinZht/CircleImage/"));
                } catch (Exception e) {
                }
            }
        }
    }
}