package com.jinzht.pro.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.MainActivity;
import com.jinzht.pro.activity.ThreeSearchDetailActivity;
import com.jinzht.pro.eventbus.MsgCount;
import com.jinzht.pro.model.ThreeTagBean;
import com.jinzht.pro.smarttablayout.SmartTabLayout;
import com.jinzht.pro.smarttablayout.v4.FragmentPagerItem;
import com.jinzht.pro.smarttablayout.v4.FragmentPagerItemAdapter;
import com.jinzht.pro.smarttablayout.v4.FragmentPagerItems;
import com.jinzht.pro.utils.DialogUtils;
import com.jinzht.pro.utils.DisplayUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.ScreenUtils;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.view.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr Jobs
 * @date 2015/5/17
 * @time 14:40
 */
public class ThreeBoardFragment extends BaseFragment {
    private Intent intent;
    protected static ViewPager vp_three;
    @Bind(R.id.tab_threee)
    SmartTabLayout tab_threee;
    private ThreeTask threeTask;
    @Bind(R.id.white_circlep)
    ImageView white_circle;
    @Bind(R.id.rl_search)
    RelativeLayout rl_search;

    @OnClick(R.id.menu)
    void menu() {
        MainActivity.menuDrawer.toggleMenu();
    }

    static List<ThreeTagBean.DataEntity> list_title = new ArrayList<>();

    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @OnClick(R.id.rl_search)
    void sp() {
        intent = new Intent(getActivity(), ThreeSearchDetailActivity.class);
        startActivity(intent);
    }

    @Override
    protected int setLayout(LayoutInflater inflater) {
        return R.layout.fragment_three_board;
    }

    @Override
    protected void onFirstUserVisible() {
        vp_three = (ViewPager) getActivity().findViewById(R.id.vp_three);
        RippleUtils.rippleNormal(rl_search);
        threeTask = new ThreeTask();
        threeTask.execute();
        if (!SharePreferencesUtils.getIsToastMenuOther(getActivity())) {
            DialogUtils.easyMenuToast1(getActivity(), R.string.toast_three, DisplayUtils.convertDipOrPx(getActivity(), 48), DisplayUtils.convertDipOrPx(getActivity(), 90) + ScreenUtils.getStatusHeight(getActivity()));
        }
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        threeTask = new ThreeTask();
                        threeTask.execute();
                    }
                });
        showOrHind();
    }


    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void onFirstUserInvisble() {

    }

    @Override
    protected void onUserVisble() {
        showOrHind();
    }

    private void showOrHind() {
        if (MainActivity.read_two > 0) {
            white_circle.setVisibility(View.VISIBLE);
        } else {
            white_circle.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init() {

    }

    @Subscribe
    public void onEvent(MsgCount event) {
        if (event.getCount() > 0) {
            white_circle.setVisibility(View.VISIBLE);
        } else {
            white_circle.setVisibility(View.GONE);
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
        threeTask = new ThreeTask();
        threeTask.execute();
    }

    private class ThreeTask extends AsyncTask<Void, Void, ThreeTagBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected ThreeTagBean doInBackground(Void... voids) {
            String body = "";
//            ACache aCache = ACache.get(getActivity());
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.NEWS, getActivity());
//                    aCache.put("ThreeTagBean", body, 1 * ACache.TIME_DAY);
                    Log.e(TAG, body);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "exception");
                    okHttpException.httpException(e);
                }
            } else {
//                body = aCache.getAsString("ThreeTagBean");
                if (body == null) {
                    errorPage();
                    return null;
                }
            }
            if (list_title.size() != 0) {
                list_title.clear();
            }
            if (FastJsonTools.getBean(body, ThreeTagBean.class) != null && FastJsonTools.getBean(body, ThreeTagBean.class).getData() != null) {
                list_title.addAll(FastJsonTools.getBean(body, ThreeTagBean.class).getData());
            }
            return FastJsonTools.getBean(body, ThreeTagBean.class);
        }

        @Override
        protected void onPostExecute(ThreeTagBean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid != null) {
                if (aVoid.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    dismissProgressDialog();
                    return;
                }
                if (aVoid.getCode() == 0) {
                    Log.e(TAG, list_title.get(0).getValue() + "ss");
                    FragmentPagerItems pages = new FragmentPagerItems(getActivity());
                    for (int i = 0; i < list_title.size(); i++) {
                        pages.add(FragmentPagerItem.of(list_title.get(i).getValue(), ChinaEnterPriseFragment.class));
                    }
                    FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
                    vp_three.setOffscreenPageLimit(list_title.size());
                    vp_three.setAdapter(adapter);
                    tab_threee.setViewPager(vp_three);
                }
                dismissProgressDialog();
            } else{
                dismissProgressDialog();
            }
        }
    }

}