package com.jinzht.pro.activity;

import android.widget.TextView;

import com.jinzht.pro.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * ϵͳ֪ͨ���飬һ�����֣���֪�����õ�
 *
 * @auther Mr.Jobs
 * @date 2015/8/8
 * @time 9:49
 */

public class SystemNoticeDetailActivity extends BaseActivity {


    @OnClick(R.id.back) void back(){
        finish();
    }
     @Bind(R.id.title)
    TextView title;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_system_notice_details;
    }

    @Override
    protected void init() {

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