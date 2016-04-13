package com.jinzht.pro.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jinzht.pro.R;
import com.jinzht.pro.adapter.GuideAdapter;
import com.jinzht.pro.utils.SharePreferencesUtils;
import com.jinzht.pro.utils.UiHelp;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 * <p>
 * 引导页，5张图。然后标记为非首次打开应用，进入登录页。
 *
 * @auther Mr Jobs
 * @date 2015/5/17
 * @time 1:09
 */
public class GuideActivity extends FullBaseActivity implements OnPageChangeListener {

    // 定义各个界面View对象
    @Bind({R.id.dot_first, R.id.dot_second, R.id.dot_three, R.id.dot_four, R.id.dot_five})
    List<ImageView> dot_image;// 小圆点
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.experience_btn)
    Button experience_btn;

    @OnClick(R.id.experience_btn)
    void experenceBtn() {// 进入登录页LoginActivity，并标记为非首次使用
        Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
        startActivity(intent);
        SharePreferencesUtils.setIsFirst(mContext, true);
        finish();
    }

    private GuideAdapter guideAdapter;
    // 定义一个ArrayList来存放View
    private ArrayList<View> views;
    // 引导图片资源
    private static final int[] pics = {R.drawable.guide1, R.drawable.guide2,
            R.drawable.guide3, R.drawable.guide4, R.drawable.guide5};
    // 记录当前选中位置
    private int currentIndex;

    @Bind(R.id.ll_dot)
    LinearLayout ll_dot;

    @Override
    protected int getResourcesId() {
        return R.layout.acitivity_guide;
    }

    @Override
    protected void init() {
        UiHelp.setTranslucentStatus(true, GuideActivity.this);
        initView();
        initDate();
    }

    private void initDate() {
        // 定义一个布局并设置参数
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        // 初始化引导图片列表
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            //防止图片不能填满屏幕
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //加载图片资源
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        // 设置数据
        viewPager.setAdapter(guideAdapter);
        // 设置监听
        viewPager.setOnPageChangeListener(this);
    }


    private void initView() {
        // 实例化ArrayList对象
        views = new ArrayList<View>();
        // 实例化ViewPager适配器
        guideAdapter = new GuideAdapter(views);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 0) {
            dot_image.get(0).setImageResource(R.drawable.dot_select);
            dot_image.get(1).setImageResource(R.drawable.dot_normal);
            dot_image.get(2).setImageResource(R.drawable.dot_normal);
            dot_image.get(3).setImageResource(R.drawable.dot_normal);
            dot_image.get(4).setImageResource(R.drawable.dot_normal);
            experience_btn.setVisibility(View.GONE);
        } else if (i == 1) {
            dot_image.get(0).setImageResource(R.drawable.dot_normal);
            dot_image.get(1).setImageResource(R.drawable.dot_select);
            dot_image.get(2).setImageResource(R.drawable.dot_normal);
            dot_image.get(3).setImageResource(R.drawable.dot_normal);
            dot_image.get(4).setImageResource(R.drawable.dot_normal);
            experience_btn.setVisibility(View.GONE);
        } else if (i == 2) {
            dot_image.get(0).setImageResource(R.drawable.dot_normal);
            dot_image.get(1).setImageResource(R.drawable.dot_normal);
            dot_image.get(2).setImageResource(R.drawable.dot_select);
            dot_image.get(3).setImageResource(R.drawable.dot_normal);
            dot_image.get(4).setImageResource(R.drawable.dot_normal);
            experience_btn.setVisibility(View.GONE);
        } else if (i == 3) {
            dot_image.get(0).setImageResource(R.drawable.dot_normal);
            dot_image.get(1).setImageResource(R.drawable.dot_normal);
            dot_image.get(2).setImageResource(R.drawable.dot_normal);
            dot_image.get(3).setImageResource(R.drawable.dot_select);
            dot_image.get(4).setImageResource(R.drawable.dot_normal);
            experience_btn.setVisibility(View.GONE);
        } else {
            dot_image.get(0).setImageResource(R.drawable.dot_normal);
            dot_image.get(1).setImageResource(R.drawable.dot_normal);
            dot_image.get(2).setImageResource(R.drawable.dot_normal);
            dot_image.get(3).setImageResource(R.drawable.dot_normal);
            dot_image.get(4).setImageResource(R.drawable.dot_select);
            experience_btn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

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
}