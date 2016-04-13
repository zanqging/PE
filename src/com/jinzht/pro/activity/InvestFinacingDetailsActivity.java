package com.jinzht.pro.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.circleprogress.DonutProgress;
import com.jinzht.pro.expandabletextview.ExpandableTextView;
import com.jinzht.pro.model.AuthBean;
import com.jinzht.pro.model.CommonBean;
import com.jinzht.pro.model.IsTenderedBean;
import com.jinzht.pro.model.PECompleteDetailsBean;
import com.jinzht.pro.model.ProjectShareBean;
import com.jinzht.pro.model.TelephoneBean;
import com.jinzht.pro.utils.AndroidQuickStartUtils;
import com.jinzht.pro.utils.Animator;
import com.jinzht.pro.utils.DialogUtils;
import com.jinzht.pro.utils.FastJsonTools;
import com.jinzht.pro.utils.MathUtils;
import com.jinzht.pro.utils.NetWorkUtils;
import com.jinzht.pro.utils.OkHttpUtils;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.ShareUtils;
import com.jinzht.pro.utils.StringUtils;
import com.jinzht.pro.utils.SuperToastUtils;
import com.jinzht.pro.utils.TimeUtils;
import com.jinzht.pro.utils.UiHelp;
import com.jinzht.pro.utils.UpLoadImageUtils;
import com.jinzht.pro.view.MarqueeTextView;
import com.jinzht.pro.view.MultiStateView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 * <p>
 * 正在进行投融资的项目详情
 *
 * @auther Mr Jobs
 * @date 2015/7/19
 * @time 13:28
 */
public class InvestFinacingDetailsActivity extends BaseActivity implements View.OnClickListener {
    private Intent intent;
    private int collect_flag = -1;// 收藏标识
    private int like_flag = -1;// 点赞标识
    private int intent_flag = 0;
    PECompleteDetailsBean peCompleteDetailsBean = null;
    @Bind({R.id.company_intro, R.id.main_business, R.id.business_model, R.id.event_context})
    List<ExpandableTextView> expandableTextViews;// 公司简介、主营业务、商业模式、公司重大新闻
    private List<PECompleteDetailsBean> list = new ArrayList<>();
    @Bind({R.id.get_money, R.id.money_total})
    List<TextView> textViewList;// 已获融资、计划融资
    @Bind(R.id.event_title)
    TextView event_title;// 公司重大新闻新闻标题
    @Bind({R.id.time})
    List<TextView> event_list;// 公司重大新闻时间
    @Bind({R.id.money_plan, R.id.invest_list, R.id.core_team, R.id.interaction})
    List<TextView> title_list;// 融资计划、投资列表、核心团队、互动专栏
    @Bind({R.id.collect, R.id.love})
    List<CheckBox> checkBoxes;// 收藏、赞，就是心形和拇指
    @Bind(R.id.ll_money)
    LinearLayout ll_money;// 已获融资、计划融资的整体布局
    @Bind({R.id.tv_company_intro, R.id.tv_main_business, R.id.tv_business_model})
    List<TextView> left_list;

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.ll_event)
    LinearLayout ll_event;// 公司重大新闻整体布局
    private InvestDetailTask investDetailTask;
    @Bind(R.id.project_image)
    ImageView project_image;// 本页上面的那个大图片
    @Bind(R.id.stage)
    TextView stage;// 融资状态标识，路演预告、融资完毕等
    @Bind({R.id.first_line, R.id.second_line})
    List<View> viewList;// 融资计划、投资列表、核心团队中间的两道线
    @Bind(R.id.multiStateView)
    MultiStateView mMultiStateView;// 状态页面
    @Bind(R.id.ll_bottom)
    LinearLayout ll_bottom;// 最下面的俩按钮，联系我们、来现场之类的
    @Bind(R.id.tv_status)
    TextView tv_status;// 项目状态，融资完毕、融资中之类的
    private int flag = -1;// 貌似0代表投资计划，2代表我要投资
    private ShareUtils shareUtils = null;
    @Bind(R.id.pro_status)
    DonutProgress pro_status;// 项目状态的进度圈
    @Bind(R.id.pro_day)
    DonutProgress pro_day;// 项目剩余天数的进度圈
    private ProjectShareBean bean;

    // 点击跳转至播放器界面video.MainActivity播放项目视频
    @OnClick(R.id.play_btn)
    void play() {
        if (peCompleteDetailsBean == null || peCompleteDetailsBean.getData().getVideo().equals("")) {
            SuperToastUtils.showSuperToast(InvestFinacingDetailsActivity.this, 1, R.string.no_video);
        } else {
            intent = new Intent(mContext, com.jinzht.pro.video.MainActivity.class);
            intent.putExtra("path", peCompleteDetailsBean.getData().getVideo());
            intent.putExtra("title", peCompleteDetailsBean.getData().getCompany());
            startActivity(intent);
        }
    }

    @Bind(R.id.telephone)
    TextView telephone;

    // 点击联系我们，弹出打电话对话框
    @OnClick(R.id.telephone)
    void telephn() {
        /**�绰����ҳ��*/
        if (SharePreferencesUtils.getCoustomTelephot(mContext).equals("")) {
            return;
        }
        AndroidQuickStartUtils.toTelephone(mContext, SharePreferencesUtils.getCoustomTelephot(mContext));
    }

    @Bind(R.id.dash_line)
    LinearLayout dash_line;// 公司重大新闻下的虚线

    // 点击收藏或者点赞，保存信息
    @OnClick({R.id.ll_collect, R.id.ll_love})
    void line(LinearLayout linearLayout) {
        switch (linearLayout.getId()) {
            case R.id.ll_collect:
                if (checkBoxes.get(0).isChecked()) {
                    //�Ѿ�����
                    checkBoxes.get(0).setChecked(false);
                    imageViews.get(0).setImageResource(R.drawable.collect);
                    checkBoxes.get(0).setText(peCompleteDetailsBean.getData().getCollect() + "");
                    Animator.CollectScale(imageViews.get(0));
                    collect_flag = 1;
                    CollectTask task = new CollectTask(collect_flag);
                    task.execute();
                } else {
                    checkBoxes.get(0).setChecked(true);
                    checkBoxes.get(0).setText(peCompleteDetailsBean.getData().getCollect() + 1 + "");
                    imageViews.get(0).setImageResource(R.drawable.collect_select);
                    Animator.CollectScale(imageViews.get(0));
                    collect_flag = 0;
                    CollectTask task = new CollectTask(collect_flag);
                    task.execute();
                }
                break;
            case R.id.ll_love:
                if (checkBoxes.get(1).isChecked()) {
                    //�Ѿ��ղ�
                    like_flag = 1;
                    LinkTask task = new LinkTask(like_flag);
                    task.execute();
                    checkBoxes.get(1).setChecked(false);
                    imageViews.get(1).setImageResource(R.drawable.love);
                    checkBoxes.get(1).setText(peCompleteDetailsBean.getData().getLike() + "");
                    Animator.CollectScale(imageViews.get(1));
                } else {
                    //Ϊ�ղ�
                    like_flag = 0;
                    LinkTask task = new LinkTask(like_flag);
                    task.execute();
                    checkBoxes.get(1).setChecked(true);
                    imageViews.get(1).setImageResource(R.drawable.love_select);
                    checkBoxes.get(1).setText(peCompleteDetailsBean.getData().getLike() + 1 + "");
                    Animator.CollectScale(imageViews.get(1));
                }
                break;
        }
    }

    @Bind(R.id.title)
    TextView title;// 标题，也是公司名
    @Bind(R.id.menu_item)
    RelativeLayout menu_item;// 后上角的分享布局

    // 点击分享，讲项目信息保存到本地，弹出分享对话框
    @OnClick(R.id.menu_item)
    void more() {
        if (!UiHelp.isFastClick()) {
            if (bean == null) {
                ProjectShareTask task = new ProjectShareTask();
                task.execute();
            } else {
                share(bean);
            }
        }
    }

    @Bind(R.id.tv_invest_detail)
    MarqueeTextView marguee_textview;// 具有跑马灯效果的项目众筹时间
    @Bind({R.id.iv_collect, R.id.iv_love})
    List<ImageView> imageViews;// ❤和拇指图标
    @Bind(R.id.bottom_btn)
    TextView bottom_btn;// 来现场按钮

    // 点击来现场按钮，待融资和已融资项目弹出提示，融资中的项目，先判断是否通过审核，再打开投资列表或我要投资
    @OnClick(R.id.bottom_btn)
    void btn() {
        if (peCompleteDetailsBean == null) {

            //待融资项目
        } else if (peCompleteDetailsBean.getData().getStage().getFlag() == 1) {
            if (TimeUtils.isDate(peCompleteDetailsBean.getData().getStage().getStart().getDatetime())) {
                if (!UiHelp.isFastClick()) {
                    ComeTask task = new ComeTask();
                    task.execute();
                }
            } else {
                if (!UiHelp.isLongFastClick()) {
                    SuperToastUtils.showSuperToast(InvestFinacingDetailsActivity.this, 1, R.string.not_come_locale);
                }
            }

            // 融资中的项目，先判断是否通过审核，再打开投资列表或我要投资
        } else if (peCompleteDetailsBean.getData().getStage().getFlag() == 2) {
            if (!UiHelp.isFastClick()) {
                /**投资的时候的判断加一下*/
                if (SharePreferencesUtils.getPerfectInformation(mContext)) {
                    flag = 2;
//                    // 打开我要投资界面
//                    IsInvestTask task = new IsInvestTask();
//                    task.execute();
                    // TODO: 2016/3/18 判断是否已投资过
                    IsTenderedTadk isTenderedTadk = new IsTenderedTadk();
                    isTenderedTadk.execute();
                } else {
                    /**居然没有完善过个人信息，我也是醉了*/// 弹出完善资料对话框
                    DialogUtils.perfectInformationDialog(InvestFinacingDetailsActivity.this);
                }
            }
            // 已融资
        } else {
            if (!UiHelp.isLongFastClick()) {
                SuperToastUtils.showSuperToast(InvestFinacingDetailsActivity.this, 1, R.string.finacing_over);
            }
        }
    }

    // 点击融资计划、投资列表、核心团队、互动专栏
    @OnClick({R.id.money_plan, R.id.invest_list, R.id.core_team, R.id.interaction})
    void onClicks(TextView textView) {
        switch (textView.getId()) {
            case R.id.core_team:// 核心团队
                intent = new Intent(mContext, CoreTeamActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
                break;
            case R.id.interaction:// 互动专栏
                intent = new Intent(mContext, InteractActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
                break;
            case R.id.money_plan:// 融资计划
                intent = new Intent(mContext, FinancingPlansActivity.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
                break;
            case R.id.invest_list://Ͷ投资列表
                if (SharePreferencesUtils.getPerfectInformation(mContext)) {
                    flag = 0;
                    IsInvestTask task = new IsInvestTask();
                    task.execute();
                } else {
                    /**居然没有完善过个人信息，我也是醉了*/
                    DialogUtils.perfectInformationDialog(InvestFinacingDetailsActivity.this);
                }
                break;
        }
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_invest_finacing_detail;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(bottom_btn);
        RippleUtils.rippleNormal(back);
        RippleUtils.rippleNormal(menu_item);
//        stage.setText(getIntent().getStringExtra("stage"));
//        stage.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(+getIntent().getExtras().getInt("color"))));
        dash_line.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        investDetailTask = new InvestDetailTask();
        investDetailTask.execute();
        shareUtils = new ShareUtils(InvestFinacingDetailsActivity.this);

        // 空页面点击重试
        mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
                        investDetailTask = new InvestDetailTask();
                        investDetailTask.execute();
                    }
                });

        // 若果客户电话是空的话就读取客户电话
        if (SharePreferencesUtils.getCoustomTelephot(mContext).equals("")) {
            TelephoneTask telephoneTask = new TelephoneTask();
            telephoneTask.execute();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void errorPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ll_bottom.setVisibility(View.GONE);
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            }
        });
    }

    @Override
    public void blankPage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ll_bottom.setVisibility(View.GONE);
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        });
    }

    @Override
    public void successRefresh() {
        investDetailTask = new InvestDetailTask();
        investDetailTask.execute();
    }

    // 获取项目详情，分为待融资、融资中、已融资
    private class InvestDetailTask extends AsyncTask<Void, Void, PECompleteDetailsBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected PECompleteDetailsBean doInBackground(Void... voids) {
            String body = null;
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.PROGECTDETAILS + getIntent().getStringExtra("id") + "/", mContext);
                    Log.e(TAG, Constant.PROGECTDETAILS + getIntent().getStringExtra("id"));
                    Log.e(TAG, body);
                    peCompleteDetailsBean = FastJsonTools.getBean(body, PECompleteDetailsBean.class);
                } catch (Exception e) {
                    okHttpException.httpException(e);
                    e.printStackTrace();
                    return null;
                }
            } else {
                errorPage();
                return null;
            }
            return FastJsonTools.getBean(body, PECompleteDetailsBean.class);
        }

        @Override
        protected void onPostExecute(PECompleteDetailsBean peCompleteDetailsBean) {
            super.onPostExecute(peCompleteDetailsBean);
            dismissProgressDialog();
            if (peCompleteDetailsBean != null) {
                if (peCompleteDetailsBean.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                    return;
                }
                ll_bottom.setVisibility(View.VISIBLE);
                title.setText(peCompleteDetailsBean.getData().getCompany());

                // 待融资项目
                if (peCompleteDetailsBean.getData().getStage().getFlag() == 1) {
                    title_list.get(0).setVisibility(View.GONE);//融资计划、投资列表、核心团队、互动专栏
                    title_list.get(1).setVisibility(View.GONE);
                    ll_money.setVisibility(View.GONE);// 已获融资、计划融资的整体布局
                    viewList.get(0).setVisibility(View.GONE);// 融资计划、投资列表、核心团队中间的两道线
                    viewList.get(1).setVisibility(View.GONE);
                    bottom_btn.setText(getResources().getString(R.string.come_road));
                    Drawable drawable = getResources().getDrawable(R.drawable.detail_comelocal);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//setBounds(x,y,width,height)
                    bottom_btn.setCompoundDrawables(null, null, null, drawable);// 参数为左、上、右、下
                    if (!TimeUtils.isDate(peCompleteDetailsBean.getData().getStage().getStart().getDatetime())) {
                        bottom_btn.setBackgroundResource(R.drawable.gray_information_btn);
                    } else {
                        bottom_btn.setBackgroundResource(R.drawable.detail_bottom_btn);
                    }

                    //融资中
                } else if (peCompleteDetailsBean.getData().getStage().getFlag() == 2) {
                    title_list.get(0).setVisibility(View.VISIBLE);//融资计划、投资列表、核心团队、互动专栏
                    title_list.get(1).setVisibility(View.VISIBLE);
                    ll_money.setVisibility(View.VISIBLE);// 已获融资、计划融资的整体布局
                    pro_day.setTextSize(24);// 剩余天数
                    pro_day.setText(peCompleteDetailsBean.getData().getStage().getDaysLeave() + getResources().getString(R.string.tian));
                    float ss = Float.parseFloat(peCompleteDetailsBean.getData().getStage().getDaysLeave()) / (float) peCompleteDetailsBean.getData().getStage().getDaysTotal();
                    Log.e(TAG, "sss" + ss);
                    try {
                        pro_day.setProgress(MathUtils.floatNums(ss) * 100);
                    } catch (Exception e) {

                    }
                    bottom_btn.setText(getResources().getString(R.string.want_invest));
                    viewList.get(0).setVisibility(View.VISIBLE);// 融资计划、投资列表、核心团队中间的两道线
                    viewList.get(1).setVisibility(View.VISIBLE);
                    bottom_btn.setBackgroundResource(R.drawable.detail_bottom_btn);
                    Drawable drawable = getResources().getDrawable(R.drawable.detail_invest);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    bottom_btn.setCompoundDrawables(null, null, null, drawable);

                    // 已融资
                } else {
                    title_list.get(0).setVisibility(View.VISIBLE);//融资计划、投资列表、核心团队、互动专栏
                    title_list.get(1).setVisibility(View.VISIBLE);
                    ll_money.setVisibility(View.VISIBLE);// 已获融资、计划融资的整体布局
                    viewList.get(0).setVisibility(View.VISIBLE);// 融资计划、投资列表、核心团队中间的两道线
                    viewList.get(0).setVisibility(View.VISIBLE);
                    bottom_btn.setText(getResources().getString(R.string.invest_over));
                    pro_day.setTextSize(24);// 剩余天数
                    pro_day.setProgress(0);
                    Drawable drawable = getResources().getDrawable(R.drawable.detail_invest);
                    /// 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    bottom_btn.setCompoundDrawables(null, null, null, drawable);
                    pro_day.setText(peCompleteDetailsBean.getData().getStage().getDaysLeave() + getResources().getString(R.string.tian));
                    viewList.get(0).setVisibility(View.VISIBLE);// 融资计划、投资列表、核心团队中间的两道线
                    viewList.get(1).setVisibility(View.VISIBLE);
                    bottom_btn.setBackgroundResource(R.drawable.gray_information_btn);
                }
                if (peCompleteDetailsBean.getData().getIs_like()) {
                    checkBoxes.get(1).setChecked(true);
                    imageViews.get(1).setBackgroundResource(R.drawable.love_select);
                } else {
                    checkBoxes.get(1).setChecked(false);
                    imageViews.get(1).setBackgroundResource(R.drawable.love);
                }
                if (peCompleteDetailsBean.getData().getIs_collect()) {
                    checkBoxes.get(0).setChecked(true);
                    imageViews.get(0).setBackgroundResource(R.drawable.collect_select);
                } else {
                    checkBoxes.get(0).setChecked(false);
                    imageViews.get(0).setBackgroundResource(R.drawable.collect);
                }
                tv_status.setText(peCompleteDetailsBean.getData().getStage().getCode());// 项目状态，融资完毕、融资中之类的
                pro_status.setTextSize(24);
                if (peCompleteDetailsBean.getData().getInvest() == 0) {// 项目状态的进度圈
                    pro_status.setProgress(0);
                } else {
                    float pro_float = (float) ((peCompleteDetailsBean.getData().getInvest() / peCompleteDetailsBean.getData().getPlanfinance()) * 100);
                    // 四舍五入保留两位小数
                    BigDecimal b = new BigDecimal(pro_float);
                    pro_float = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                    if (pro_float >= 100.0) {
                        pro_status.setProgress(100);
                        pro_status.setText(MathUtils.floatNum(pro_float) + "%");
                    } else {
                        pro_status.setProgress(MathUtils.floatNums(pro_float));
                    }
                    Log.e(TAG, (float) ((peCompleteDetailsBean.getData().getInvest() / peCompleteDetailsBean.getData().getPlanfinance()) * 100) + "ss");
                }

                // 具有跑马灯效果的项目众筹时间
                marguee_textview.setText(getResources().getString(R.string.project_this) +
                                peCompleteDetailsBean.getData().getStage().getStart().getName() + ":" + peCompleteDetailsBean.getData().getStage().getStart().getDatetime() + "," +
                                peCompleteDetailsBean.getData().getStage().getEnd().getName() + ":" + peCompleteDetailsBean.getData().getStage().getEnd().getDatetime()
                );

                // 融资状态标识，路演预告、融资完毕等
                stage.setText(peCompleteDetailsBean.getData().getStage().getCode());
                stage.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(+peCompleteDetailsBean.getData().getStage().getColor())));
                // 已获融资和计划融资
                float f = (float) peCompleteDetailsBean.getData().getInvest();
                BigDecimal b = new BigDecimal(f);// 四舍五入保留两位小数
                f = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
                textViewList.get(1).setText(getResources().getString(R.string.get_invest_money) + f + getResources().getString(R.string.wan));
                textViewList.get(0).setText(getResources().getString(R.string.project_plan) + peCompleteDetailsBean.getData().getPlanfinance() + getResources().getString(R.string.wan));
                // 心形和拇指
                checkBoxes.get(0).setText(peCompleteDetailsBean.getData().getCollect() + "");
                checkBoxes.get(1).setText(peCompleteDetailsBean.getData().getLike() + "");
                // 公司简介、主营业务、商业模式
                expandableTextViews.get(0).setText(peCompleteDetailsBean.getData().getProfile());
                expandableTextViews.get(1).setText(peCompleteDetailsBean.getData().getBusiness());
                expandableTextViews.get(2).setText(peCompleteDetailsBean.getData().getModel());
                // 大图片
                if (StringUtils.isEmpty(peCompleteDetailsBean.getData().getImg())) {
                    project_image.setImageResource(R.drawable.user_loadingfail);
                } else {
                    UpLoadImageUtils.getAnimImage(peCompleteDetailsBean.getData().getImg(), project_image);
                }
                // 公司重大新闻
                if (!peCompleteDetailsBean.getData().getEvent().equals("")) {
                    //���ش�����
                    try {
                        String ss = peCompleteDetailsBean.getData().getEvent().trim();
                        String[] events = ss.split("\r\n", 3);
                        event_list.get(0).setText(events[1]);// 公司重大新闻时间
                        expandableTextViews.get(3).setText(events[2]);// 公司重大新闻
                        event_title.setText(events[0]);// 公司重大新闻新闻标题
                    } catch (Exception e) {
                        ll_event.setVisibility(View.GONE);
                    }
                } else {
                    ll_event.setVisibility(View.GONE);
                }
            }
        }
    }

    // 获取来现场的信息，弹出对话框提示
    private class ComeTask extends AsyncTask<Void, Void, CommonBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.COMELOACLE + getIntent().getStringExtra("id") + "/", mContext);
                    Log.e(TAG, getIntent().getStringExtra("id") + "id");
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
        protected void onPostExecute(CommonBean common) {
            super.onPostExecute(common);
            if (common == null) {
                return;
            } else {
                if (common.getCode() == -1) {
                    return;
                } else {
                    DialogUtils.toastDialog(InvestFinacingDetailsActivity.this, common.getMsg(), getResources().getString(R.string.submit));
                }
//                SuperToastUtils.showSuperToast(InvestFinacingDetailsActivity.this, 1, common.getMsg());
            }
            dismissProgressDialog();
        }
    }

    // 保存收藏信息
    private class CollectTask extends AsyncTask<Void, Void, CommonBean> {
        private int id;

        public CollectTask(int id) {
            this.id = id;
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.COLLECT + getIntent().getStringExtra("id") + "/" + id + "/", mContext);
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
        protected void onPostExecute(CommonBean common) {
            super.onPostExecute(common);
            if (common == null) {
                return;
            }
        }
    }

    // 保存赞信息
    private class LinkTask extends AsyncTask<Void, Void, CommonBean> {
        private int id;

        public LinkTask(int id) {
            this.id = id;
        }

        @Override
        protected CommonBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.LOVE + getIntent().getStringExtra("id") + "/" + id + "/", mContext);
                    Log.e(TAG, getIntent().getStringExtra("id") + "id");
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
        protected void onPostExecute(CommonBean common) {
            super.onPostExecute(common);
            if (common == null) {
                SuperToastUtils.showSuperToast(InvestFinacingDetailsActivity.this, 1, R.string.no_wifi);
                return;
            }
        }
    }

    // 打开我要投资界面
    private class IsInvestTask extends AsyncTask<Void, Void, AuthBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected AuthBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.ISINVESTOR, mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, body);
                return FastJsonTools.getBean(body, AuthBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(AuthBean common) {
            super.onPostExecute(common);
            dismissProgressDialog();
            if (common != null) {
                if (common.getCode() == -1) {
                    return;
                }
                if (common.getCode() == 0) {
                    Log.i(TAG, "ismyproject");
                    if (flag == 0) {
                        /**投资列表*/
                        UiHelp.investorList(InvestFinacingDetailsActivity.this, common.getData().getAuth() + "", getIntent().getStringExtra("id"));
                    } else if (flag == 2) {
                        /**我要投资*/
                        UiHelp.isAuth(InvestFinacingDetailsActivity.this, common.getData().getAuth() + "", getIntent().getStringExtra("id"), getIntent().getStringExtra("companyName"), getIntent().getStringExtra("abbrevName"), getIntent().getStringExtra("image"), peCompleteDetailsBean.getData().getProfile(), peCompleteDetailsBean.getData().getBrrow_user_no(), peCompleteDetailsBean.getData().getProfit(), peCompleteDetailsBean.getData().getMinfund());
                    }
                }
            }
        }
    }

    // 读取电话
    private class TelephoneTask extends AsyncTask<Void, Void, TelephoneBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected TelephoneBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(InvestFinacingDetailsActivity.this).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.MAIN_TELEPHONE, InvestFinacingDetailsActivity.this);
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
            if (common != null) {
                if (common.getCode() == -1) {
                    return;
                }
                if (common.getCode() == 0) {
//                    Log.e(TAG,"ismyproject");
                    SharePreferencesUtils.setCoustomTelephone(InvestFinacingDetailsActivity.this, common.getData().get(0).getValue());
                }
            }
        }
    }

    // 访问网络，将要分享的项目保存起来，再弹出分享对话框
    private class ProjectShareTask extends AsyncTask<Void, Void, ProjectShareBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected ProjectShareBean doInBackground(Void... voids) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.SHAREPROJECT + getIntent().getStringExtra("id") + "/", mContext);
                    bean = FastJsonTools.getBean(body, ProjectShareBean.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                Log.e(TAG, body);
                return bean;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ProjectShareBean common) {
            super.onPostExecute(common);
            dismissProgressDialog();
            share(common);
        }
    }

    // TODO: 2016/3/18 判断是否已投资过
    private class IsTenderedTadk extends AsyncTask<Void, Void, IsTenderedBean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingProgressDialog();
        }

        @Override
        protected IsTenderedBean doInBackground(Void... params) {
            String body = "";
            if (!NetWorkUtils.getNetWorkType(mContext).equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
                try {
                    body = OkHttpUtils.doGet(Constant.BASE_URL + Constant.PHONE + Constant.ISTENDERED + getIntent().getStringExtra("id") + "/", mContext);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return FastJsonTools.getBean(body, IsTenderedBean.class);
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(IsTenderedBean isTenderedBean) {
            super.onPostExecute(isTenderedBean);
            dismissProgressDialog();
            if (isTenderedBean == null) {
                SuperToastUtils.showSuperToast(mContext, 1, R.string.no_wifi);
            } else {
                if (isTenderedBean.getCode() == -1) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute();
                }
                if (isTenderedBean.getCode() == 0) {
                    // 已投资过，弹出提示框
                    if (isTenderedBean.getData().isInvest()) {
                        DialogUtils.isYoursDialog(InvestFinacingDetailsActivity.this, getResources().getString(R.string.isinvested), getResources().getString(R.string.submit));
                        // 未投资过，打开我要投资界面
                    } else {
                        IsInvestTask task = new IsInvestTask();
                        task.execute();
                    }
                }
            }
        }
    }

    // 弹出分享对话框
    private void share(ProjectShareBean common) {
        if (common != null) {
            if (common.getCode() == -1) {
                return;
            }
            if (common.getCode() == 0) {
                DialogUtils.ShareDialog(InvestFinacingDetailsActivity.this, stage, shareUtils, peCompleteDetailsBean.getData().getCompany(),
                        common.getData().getContent(), common.getData().getImg(), common.getData().getUrl());
            } else {
                SuperToastUtils.showSuperToast(InvestFinacingDetailsActivity.this, 1, common.getMsg());
            }
        } else {
            SuperToastUtils.showSuperToast(InvestFinacingDetailsActivity.this, 1, R.string.no_wifi);
        }
    }


}