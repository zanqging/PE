package com.jinzht.pro.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.CreditActivity;
import com.jinzht.pro.activity.ImproveActivity;
import com.jinzht.pro.activity.InvestFinacingDetailsActivity;
import com.jinzht.pro.activity.MainActivity;
import com.jinzht.pro.activity.VideoWebviewActivity;
import com.jinzht.pro.activity.WebViewActivity;
import com.jinzht.pro.adapter.HomePageAdapter;
import com.jinzht.pro.banner.ImageCycleView;
import com.jinzht.pro.eventbus.MsgCount;
import com.jinzht.pro.model.HomePageBean;
import com.jinzht.pro.model.TelephoneBean;
import com.jinzht.pro.shimmer.Shimmer;
import com.jinzht.pro.shimmer.ShimmerTextView;
import com.jinzht.pro.utils.ACache;
import com.jinzht.pro.utils.AndroidQuickStartUtils;
import com.jinzht.pro.utils.AnimatorUtils;
import com.jinzht.pro.utils.AsynOkUtils;
import com.jinzht.pro.utils.DialogUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.GlideUtils;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.view.MultiStateView;
import com.jinzht.pro.view.MyListview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr Jobs
 * @date 2015/5/17
 * @time 14:37
 */
public class HomePageFragment extends BaseFragment implements AbsListView.OnScrollListener {
    private Intent intent;
    private List<HomePageBean.DataEntity.BannerEntity> bannerBeanList = new ArrayList<>();// 广告集合
    @Bind(R.id.white_circle)
    ImageView white_circle;// 左上角小人图标上的与我相关的消息标识
    List<String> lists = new ArrayList<String>();

    // 打开侧边栏
    @OnClick(R.id.menu)
    void menu() {
        MainActivity.menuDrawer.toggleMenu();
    }

    @Bind(R.id.title)
    TextView title;// 应用标题
    private int read_one;
    @Bind(R.id.ad_view)
    ImageCycleView ad_view;// 广告轮播控件
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;// 状态页面
    @Bind(R.id.main_lv)
    MyListview main_lv;// 首页展示项目的ListView
    private HomePageAdapter adapter;// 首页展示项目的ListView的Adapter
    private List<HomePageBean.DataEntity.ProjectEntity> list_project = new ArrayList<>();// 项目实体集合
    private List<HomePageBean.DataEntity.PlatformEntity> list_plat = new ArrayList<>();// 融资播报实体集合
    @Bind(R.id.scroll_view)
    ScrollView scroll_view;
    @Bind(R.id.rl_perfect)
    RelativeLayout rl_perfect;// 提示完善资料的布局
    private MainTask task;// 填充主页数据
    private String url = "";
    private String tv_news = "";
    private String record_url = "";
    private boolean offline_over = false;
    private Shimmer shimmer;// 闪光效果

//    ACache aCache;

    // 跳转至完善用户资料页面ImproveActivity
    @OnClick(R.id.rl_perfect)
    void per() {
        MainActivity.hideMenu();
        intent = new Intent(mContext, ImproveActivity.class);
        startActivityForResult(intent, 30);
    }

    // 点击客服电话，访问网络，获取客服电话信息，跳转至系统的打电话页面
    @OnClick(R.id.tv_telephoe)
    void ph() {
        MainActivity.hideMenu();
        TelephoneTask telephoneTask = new TelephoneTask();
        telephoneTask.execute();
    }

    // 点击关闭完善资料布局
    @OnClick(R.id.rl_delete)
    void dism() {
        rl_perfect.setVisibility(View.GONE);
    }

    @Bind(R.id.tv_new)
    TextView tv_new;// 新手指南
    @Bind(R.id.tv_telephoe)
    ShimmerTextView tv_telephoe;// 客服电话
    @Bind(R.id.tv_record)
    TextView tv_record;// 融资播报

    // 点击跳转至征信查询、新手指南、融资播报
    @OnClick({R.id.lv_credit, R.id.lv_news, R.id.lv_record})
    void on(RelativeLayout view) {
        switch (view.getId()) {
            case R.id.lv_credit:// 跳转至征信查询页面，CreditActivity
                MainActivity.hideMenu();
                intent = new Intent(getActivity(), CreditActivity.class);
                startActivity(intent);
                break;
            case R.id.lv_news:// 跳转至新手指南页面，一个网页VideoWebviewActivity
                MainActivity.hideMenu();
                intent = new Intent(getActivity(), VideoWebviewActivity.class);
                intent.putExtra("url", url);
//                intent.putExtra("url","http://mp.weixin.qq.com/s?__biz=MjM5MjAwMjIwMA==&mid=203248468&idx=1&sn=e0265d34b9c6cc9729b9b04ae433a910&3rd=MzA3MDU4NTYzMw==&scene=6#rd");
                startActivity(intent);
                break;
            case R.id.lv_record:// 跳转至融资播报页面，一个网页VideoWebviewActivity
                MainActivity.hideMenu();
                intent = new Intent(getActivity(), VideoWebviewActivity.class);
                intent.putExtra("url", record_url);
//                intent.putExtra("url","http://mp.weixin.qq.com/s?__biz=MjM5MjAwMjIwMA==&mid=203248468&idx=1&sn=e0265d34b9c6cc9729b9b04ae433a910&3rd=MzA3MDU4NTYzMw==&scene=6#rd");
                startActivity(intent);
                break;
        }
    }

    // 点击项目列表，跳转至每个具体的项目
    @OnItemClick(R.id.main_lv)
    void main(int pos) {
        MainActivity.hideMenu();
        if (list_project.size() == 0) {
            return;
        }
        intent = new Intent(getActivity(), InvestFinacingDetailsActivity.class);
        intent.putExtra("id", list_project.get(pos).getId() + "");
        intent.putExtra("companyName", list_project.get(pos).getCompany() + "");
        intent.putExtra("abbrevName", list_project.get(pos).getAbbrevcompany() + "");
        intent.putExtra("image", list_project.get(pos).getImg() + "");
        startActivity(intent);
    }

    // 加载主页整体布局
    @Override
    protected int setLayout(LayoutInflater inflater) {
        return R.layout.activity_text;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        Log.i(TAG, "onScroll firstVisibleItem:" + firstVisibleItem +
                " visibleItemCount:" + visibleItemCount +
                " totalItemCount:" + totalItemCount);
    }

    // 第一个可视界面
    @Override
    protected void onFirstUserVisible() {
        title.setText(getResources().getString(R.string.app_name));
        shimmer = new Shimmer();
        shimmer.setDuration(2000).start(tv_telephoe);// 闪光效果

//        aCache = ACache.get(mContext);
// TODO: 2016/3/28 取消了缓存
//        offlineData();// 从本地加载数据
//
//        new Handler().postDelayed(new Runnable() {// 访问网络，获取数据保存到HomePageBean和缓存中，给首页填充数据
//            @Override
//            public void run() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        task = new MainTask();
//                        task.execute();
//                    }
//                });
//            }
//        }, 1500);

        task = new MainTask();
        task.execute();

        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)// 错误页面，点击重试填充主页数据
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        task = new MainTask();
                        task.execute();
                    }
                });

        showOrHide();// 与我相关的消息提醒显示或不显示
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // 接收数据，有与我相关的消息就显示提醒，没有就不提醒
    @Subscribe
    public void onEvent(MsgCount event) {
        Log.e(TAG, "event");
        if (event.getCount() > 0) {
            white_circle.setVisibility(View.VISIBLE);
        } else {
            white_circle.setVisibility(View.GONE);
        }
    }

    // 广告轮播控件监听，点击广告，跳转至网页或者具体项目
    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        // 加载图片
        @Override
        public void displayImage(String imageURL, ImageView imageView) {
//            ImageLoader.getInstance().displayImage(imageURL, imageView);// ʹ��ImageLoader��ͼƬ���м�װ��
            GlideUtils.loadImg(getActivity(), imageURL, imageView);
        }

        // 点击图片跳转
        @Override
        public void onImageClick(String info, int postion, View imageView) {
            bannerImg(postion);
        }
    };
    // 已弃用的广告轮播控件监听
    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener1 = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void displayImage(String imageURL, ImageView imageView) {
//            ImageLoader.getInstance().displayImage(imageURL, imageView);// ʹ��ImageLoader��ͼƬ���м�װ��
            GlideUtils.loadImg(getActivity(), imageURL, imageView);
        }

        @Override
        public void onImageClick(String info, int postion, View imageView) {
            bannerImg(postion);
        }
    };

    // 点击广告，跳转至网页或者具体项目
    private void bannerImg(int postion) {
        if (lists.size() == 0) {
            return;
        }
        // 有链接则跳转至网页，WebViewActivity
        if (!bannerBeanList.get(postion).getUrl().equals("")) {
            if (!UiHelp.isLongFastClick()) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", bannerBeanList.get(postion).getUrl());
                startActivity(intent);
            }
            // 跳转至具体项目，InvestFinacingDetailsActivity
        } else if (bannerBeanList.get(postion).getProject() != null && (!bannerBeanList.get(postion).getProject().equals("null"))) {
            if (!UiHelp.isLongFastClick()) {
                Intent intent = new Intent(mContext, InvestFinacingDetailsActivity.class);
                intent.putExtra("id", bannerBeanList.get(postion).getProject().toString());
                startActivity(intent);
            }
        } else {
            if (!UiHelp.isLongFastClick()) {
                SuperToastUtils.showSuperToast(getActivity(), 1, R.string.no_detail);
            }
        }
    }

    @Override
    protected void onUserInvisible() {
        Log.i("用户暂停了", "pause");
//        MobclickAgent.onEventValue(mContext, "homepage", null, 3000);
//        MobclickAgent.onEvent(mContext, "homepage");
    }

    @Override
    protected void onFirstUserInvisble() {

    }

    // 用户可视时，平缓滑动至原点
    @Override
    protected void onUserVisble() {
        Log.i("用户又回来了", "onresuse");
        scroll_view.smoothScrollTo(0, 0);
        showOrHide();
    }

    // 与我相关的消息提醒显示或不显示
    private void showOrHide() {
        if (MainActivity.read_two > 0) {
            white_circle.setVisibility(View.VISIBLE);
        } else {
            white_circle.setVisibility(View.GONE);
        }
    }

    @Override
    protected void init() {

    }

    // 错误页面
    @Override
    public void errorPage() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!offline_over) {
                    /**�������ݼ���ʧ������������*/
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                }
            }
        });
    }

    // 空页面
    @Override
    public void blankPage() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        });
    }

    // 刷新，访问网络，填充数据
    @Override
    public void successRefresh() {
        task = new MainTask();
        task.execute();
    }

    // 请求码和返回码都是30时，完善资料控件隐藏，弹出选择投资人类型对话框
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "requ" + resultCode + "resule" + resultCode);
        if (requestCode == 30 && resultCode == 30) {
            rl_perfect.setVisibility(View.GONE);
            DialogUtils.showInvestDialogs(getActivity());// 选择投资人类型对话框
        }
    }

    // 访问网络，获取数据保存到HomePageBean和缓存中，给首页填充数据
    private class MainTask extends AsyncTask<Void, Void, HomePageBean> {
        // 显示加载中对话框
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            showLoadingProgressDialog();
        }

        // 访问网络，获取数据保存到HomePageBean和缓存中
        @Override
        protected HomePageBean doInBackground(Void... voids) {
            String body = "";
//            ACache aCache = ACache.get(getActivity());
            try {
                if (NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                    return null;
                } else {
                    body = AsynOkUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.HOME_PAGE, getActivity());
                    Log.i("HomePageBean", body);
//                    if (aCache.get("HomePageBean")==null ||aCache.get("HomePageBean").equals("")){
                    //��һ��//��date/date/com.liyuan.youga/cache/cache11.37kb
//                    }
                }
                bgLoad(body);
            } catch (NullPointerException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                okHttpException.httpException(e);
                e.printStackTrace();
                return null;
            }
            aCache.put("HomePageBean", body, 1 * ACache.TIME_DAY);
            return FastJsonTools.getBean(body, HomePageBean.class);
        }

        // 给首页填充数据
        @Override
        protected void onPostExecute(HomePageBean stringObjectMap) {
            super.onPostExecute(stringObjectMap);
            if (stringObjectMap != null) {
                loadOver(stringObjectMap);
            } else {
                errorPage();
            }
//            dismissProgressDialog();
        }
    }

    // 从本地填充数据
    private void offlineData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String body = "";
//                ACache aCache = ACache.get(getActivity());
                try {
                    body = aCache.getAsString("HomePageBean");
                    if (body == null) {
                        return;
                    }
                    bgLoad(body);// 加载缓存数据
                    final String finalBody = body;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 填充数据
                            Log.i("缓存数据", finalBody);
                            loadOver(FastJsonTools.getBean(finalBody, HomePageBean.class));
                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    okHttpException.httpException(e);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 加载数据
    private void bgLoad(String body) {
        if (bannerBeanList.size() != 0 || lists.size() != 0) {
            bannerBeanList.clear();
            lists.clear();
        }
        if (list_project.size() != 0) {
            list_project.clear();
        }
        if (FastJsonTools.getBean(body, HomePageBean.class) != null && FastJsonTools.getBean(body, HomePageBean.class).getData() != null) {
            list_project.addAll(FastJsonTools.getBean(body, HomePageBean.class).getData().getProject());// 项目实体集合
            list_plat.add(FastJsonTools.getBean(body, HomePageBean.class).getData().getPlatform());// 融资播报集合
            bannerBeanList.addAll(FastJsonTools.getBean(body, HomePageBean.class).getData().getBanner());// 广告集合
            for (int i = 0; i < bannerBeanList.size(); i++) {
                lists.add(bannerBeanList.get(i).getImg());
            }
        }
    }

    // 给首页展示项目的ListView填充数据，给广告轮播控件填充数据
    private void loadOver(HomePageBean stringObjectMap) {
        if (stringObjectMap != null) {
            if (stringObjectMap.getCode() == 0 && stringObjectMap.getData() != null) {
                // 给首页展示项目的ListView填充数据
                if (list_project.size() > 0) {
                    adapter = new HomePageAdapter(mContext, list_project);
                    main_lv.setAdapter(adapter);
                    scroll_view.smoothScrollTo(0, 0);// 平缓滑动至原点
                    adapter.notifyDataSetChanged();
                }
                // 给广告轮播控件填充数据
                if (lists != null && lists.size() != 0) {
                    ad_view.setImageResources(lists, mAdCycleViewListener);
                }
                url = stringObjectMap.getData().getAnnouncement().getUrl();// 新手指南地址
                tv_new.setText(stringObjectMap.getData().getAnnouncement().getTitle());// 新手指南标题
                tv_record.setText(stringObjectMap.getData().getPlatform().getTitle());// 融资播报标题
                record_url = stringObjectMap.getData().getPlatform().getUrl();// 融资播报地址
                // 如果没有完善资料，则提示用户完善资料
                if (offline_over) {
                    if (!SharePreferencesUtils.getPerfectInformation(mContext)) {
                        AnimatorUtils.showPerfectInformation(rl_perfect);
                    }
                }
                offline_over = true;
                // 登录
            } else if (stringObjectMap.getCode() == -1) {
                if (!SharePreferencesUtils.contains(getActivity(), "telephone") && !SharePreferencesUtils.contains(getActivity(), "passwd")
                        && SharePreferencesUtils.getTelephone(getActivity()).equals("") && SharePreferencesUtils.getPassWd(getActivity()).equals("")) {
                    loginAgain();
                } else {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            shimmer.cancel();
        } catch (NullPointerException e) {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView");
    }

    // 访问网络，获取客服电话信息，跳转至系统的打电话页面
    private class TelephoneTask extends AsyncTask<Void, Void, TelephoneBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected TelephoneBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.MAIN_TELEPHONE, getActivity());
                    Log.e(TAG, body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, TelephoneBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(TelephoneBean common) {
            super.onPostExecute(common);
            dismissProgressDialog();
            if (common != null) {
                if (common.getCode() == -1) {
                    return;
                }
                if (common.getCode() == 0) {
//                    Log.e(TAG,"ismyproject");
                    SharePreferencesUtils.setCoustomTelephone(getActivity(), common.getData().get(0).getValue());
                    AndroidQuickStartUtils.toTelephone(getActivity(), common.getData().get(0).getValue());
                }
            }
        }
    }
}