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
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 * <p>
 * ����ҳ��5��ͼ��Ȼ����Ϊ���״δ�Ӧ�ã������¼ҳ��
 *
 * @auther Mr Jobs
 * @date 2015/5/17
 * @time 1:09
 */
public class GuideActivity extends FullBaseActivity implements OnPageChangeListener {

    // �����������View����
    @Bind({R.id.dot_first, R.id.dot_second, R.id.dot_three, R.id.dot_four, R.id.dot_five})
    List<ImageView> dot_image;// СԲ��
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.experience_btn)
    Button experience_btn;

    @OnClick(R.id.experience_btn)
    void experenceBtn() {// �����¼ҳLoginActivity�������Ϊ���״�ʹ��
        Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
        startActivity(intent);
        SharePreferencesUtils.setIsFirst(mContext, true);
        finish();
    }

    private GuideAdapter guideAdapter;
    // ����һ��ArrayList�����View
    private ArrayList<View> views;
    // ����ͼƬ��Դ
    private static final int[] pics = {R.drawable.guide1, R.drawable.guide2,
            R.drawable.guide3, R.drawable.guide4, R.drawable.guide5};
    // ��¼��ǰѡ��λ��
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
        // ����һ�����ֲ����ò���
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        // ��ʼ������ͼƬ�б�
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            //��ֹͼƬ����������Ļ
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //����ͼƬ��Դ
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        // ��������
        viewPager.setAdapter(guideAdapter);
        // ���ü���
        viewPager.setOnPageChangeListener(this);
    }


    private void initView() {
        // ʵ����ArrayList����
        views = new ArrayList<View>();
        // ʵ����ViewPager������
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