package com.jinzht.pro.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.utils.RippleUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pc on 2016/3/21.
 * 没有注册的账户页面
 */
public class YibaoAccountActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.title)
    TextView title;

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_account;
    }

    private TextView account_btn_register;// 实名认证
    private Intent intent;

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        title.setText(getResources().getString(R.string.account));

        account_btn_register = (TextView) findViewById(R.id.account_btn_register);
        account_btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_btn_register:// 去注册YibaoRegister2WebViewActivity
                intent = new Intent(mContext, YibaoRegisterWebViewActivity.class);
                intent.putExtra("activity", "YibaoAccountActivity");
                intent.putExtra("uid", getIntent().getStringExtra("uid"));
                intent.putExtra("name", getIntent().getStringExtra("name"));
                intent.putExtra("idNo", getIntent().getStringExtra("idNo"));
                intent.putExtra("tel", getIntent().getStringExtra("tel"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void errorPage() {

    }

    @Override
    public void blankPage() {

    }
}
