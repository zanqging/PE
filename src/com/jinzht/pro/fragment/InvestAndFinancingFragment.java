package com.jinzht.pro.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.MainActivity;
import com.jinzht.pro.activity.SearchActivity;
import com.jinzht.pro.eventbus.MsgCount;
import com.jinzht.pro.model.TitleBean;
import com.jinzht.pro.smarttablayout.SmartTabLayout;
import com.jinzht.pro.smarttablayout.v4.FragmentPagerItem;
import com.jinzht.pro.smarttablayout.v4.FragmentPagerItemAdapter;
import com.jinzht.pro.smarttablayout.v4.FragmentPagerItems;
import com.jinzht.pro.utils.ACache;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.view.MultiStateView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * @auther Mr Jobs
 * @date 2015/5/17
 * @time 14:39
 */
public class InvestAndFinancingFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.rg_invest)
    RadioGroup rg_invest;
    @Bind({R.id.rb_one, R.id.rb_three})
    List<RadioButton> radioButtons;
    protected static ViewPager viewPager;
    protected static ViewPager investor;
    @Bind(R.id.viewpagertab)
    SmartTabLayout viewPagerTab;
    @Bind(R.id.viewpagertabs)
    SmartTabLayout viewPagerTabs;

    @OnClick(R.id.menu)
    void menu() {
        MainActivity.menuDrawer.toggleMenu();
    }

    private ProjectTask task;
    @Bind(R.id.ll_project)
    LinearLayout ll_project;
    @Bind(R.id.ll_investor)
    LinearLayout ll_investor;
    private boolean project_first = true;
    @Bind(R.id.iv_invest)
    ImageView iv_invest;
    @Bind(R.id.rl_search)
    RelativeLayout rl_search;
    private Intent intent;
    private boolean investor_flag = true;
    private boolean offline_over = false;
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;

    @OnClick(R.id.rl_search)
    void rls() {
        intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("flag", Constant.SEARCH_INVEST);
        startActivity(intent);
    }

    @Override
    protected int setLayout(LayoutInflater inflater) {
        return R.layout.fragment_invest_finace;
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
    protected void onFirstUserVisible() {
        Log.i(TAG, "first");
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpagers);
        investor = (ViewPager) getActivity().findViewById(R.id.viewpagerss);
        rg_invest.setOnCheckedChangeListener(this);
        one();
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        task = new ProjectTask();
                        task.execute();
                    }
                });
        // TODO: 2016/3/28 取消了缓存
//        offline();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        task = new ProjectTask();
//                        task.execute();
//                    }
//                });
//            }
//        }, 500);

        task = new ProjectTask();
        task.execute();

        showOrHide();
    }

    private void showOrHide() {
        if (MainActivity.read_two > 0) {
            iv_invest.setVisibility(View.VISIBLE);
        } else {
            iv_invest.setVisibility(View.GONE);
        }
    }

    @Subscribe
    public void onEvent(MsgCount event) {
        Log.e(TAG, "msgread");
        if (event.getCount() > 0) {
            iv_invest.setVisibility(View.VISIBLE);
        } else {
            iv_invest.setVisibility(View.GONE);
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
        showOrHide();
    }

    @Override
    protected void init() {
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_one:
                Log.e(TAG, "one");
                one();
                if (project_first) {
                    task = new ProjectTask();
                    task.execute();
                }
                break;

            case R.id.rb_three:
                Log.e(TAG, "three");
                three();
                if (investor_flag) {
                    FragmentPagerItems pages = new FragmentPagerItems(getActivity());
                    String[] item = getResources().getStringArray(R.array.invest_three);
                    for (int j = 0; j < 3; j++) {
                        if (j == 0) {
                            pages.add(FragmentPagerItem.of(item[j], PersonInvestorFragment.class));
                        } else if (j == 1) {
                            pages.add(FragmentPagerItem.of(item[j], OragInvestorFragment.class));
                        } else {
                            pages.add(FragmentPagerItem.of(item[j], ThinkTankFragment.class));
                        }
                    }
                    FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
                    investor.setOffscreenPageLimit(3);
                    investor.setAdapter(adapter);
                    viewPagerTabs.setViewPager(investor);
                    investor_flag = false;
                }
                break;
        }
    }

    private void one() {
        rl_search.setVisibility(View.VISIBLE);
        ll_project.setVisibility(View.VISIBLE);
        ll_investor.setVisibility(View.GONE);
        radioButtons.get(0).setBackgroundResource(R.drawable.invest_square_select);
        radioButtons.get(1).setBackgroundResource(R.drawable.invest_square_normal);
    }

    private void three() {
        rl_search.setVisibility(View.GONE);
        ll_project.setVisibility(View.GONE);
        ll_investor.setVisibility(View.VISIBLE);
        radioButtons.get(1).setBackgroundResource(R.drawable.invest_square_select);
        radioButtons.get(0).setBackgroundResource(R.drawable.invest_square_normal);
    }

    @Override
    public void errorPage() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!offline_over) {
                    mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                }
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
        Log.e(TAG, "refresh");
        task = new ProjectTask();
        task.execute();
    }

    private class ProjectTask extends AsyncTask<Void, Void, TitleBean> {
        @Override
        protected void onPreExecute() {
            showLoadingProgressDialog();
            super.onPreExecute();
        }

        @Override
        protected TitleBean doInBackground(Void... voids) {
            String body = "";
//            ACache aCache = ACache.get(getActivity());
            if (!NetWorkUtils.getNetWorkType(getActivity()).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                //����
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.FIRST_INVEST, getActivity());
                    aCache.put("TitleBean", body, 1 * ACache.TIME_DAY);
                } catch (Exception e) {
                    e.printStackTrace();
                    okHttpException.httpException(e);
                    return null;
                }
                Log.e(TAG, body);
            } else {
//                body = aCache.getAsString("TitleBean");
                if (body == null) {
                    errorPage();
                    return null;
                }
            }
            return FastJsonTools.getBean(body, TitleBean.class);
        }

        @Override
        protected void onPostExecute(TitleBean aVoid) {
            super.onPostExecute(aVoid);
            dismissProgressDialog();
            if (aVoid != null) {
                loadOver(aVoid);
            }
        }
    }

    private void loadOver(TitleBean aVoid) {
        if (aVoid != null) {
            if (aVoid.getCode() == -1) {
                LoginTask loginTask = new LoginTask();
                loginTask.execute();
                return;
            }
            if (aVoid.getCode() == 0) {
                offline_over = true;
                FragmentPagerItems pages = new FragmentPagerItems(getActivity());
                String[] item = getResources().getStringArray(R.array.invest_array);
                for (int i = 0; i < 4; i++) {
                    switch (i) {
                        case 0:
                            pages.add(FragmentPagerItem.of(item[i], WaitProjectFragment.class));
                            break;
                        case 1:
                            pages.add(FragmentPagerItem.of(item[i], WaitingProjectFragment.class));
                            break;
                        case 2:
                            pages.add(FragmentPagerItem.of(item[i], FinishedProjectFragment.class));
                            break;
                        case 3:
                            pages.add(FragmentPagerItem.of(item[i], PreProjectFragment.class));
                            break;
                    }
                }
                FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
                viewPager.setOffscreenPageLimit(4);
                viewPager.setAdapter(adapter);
                viewPagerTab.setViewPager(viewPager);
                viewPager.setCurrentItem(aVoid.getData().getCursor() - 1);
                project_first = false;
            }
        }
    }

    private void offline() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String body = null;
                try {
//                    ACache aCache = ACache.get(getActivity());
                    body = aCache.getAsString("TitleBean");
                    Log.e(TAG, body);
                    if (body == null) {
                        return;
                    }
                    final String finalBody = body;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadOver(FastJsonTools.getBean(finalBody, TitleBean.class));
                        }
                    });
                } catch (Exception e) {
                }
            }
        }).start();
    }
}