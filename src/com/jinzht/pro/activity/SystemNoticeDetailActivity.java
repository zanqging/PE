package com.jinzht.pro.activity;

import android.widget.TextView;

import com.jinzht.pro.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * 系统通知详情，一大串文字，不知道哪用的
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