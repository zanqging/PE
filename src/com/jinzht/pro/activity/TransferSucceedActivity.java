package com.jinzht.pro.activity;

import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.utils.RippleUtils;

import butterknife.Bind;

/**
 * Created by pc on 2016/3/18.
 * 转出成功页面
 */
public class TransferSucceedActivity extends BaseActivity{

    @Bind(R.id.title)
    TextView title;

    private TextView paysucceed_tv_money;
    private Button paysucceed_btn_confirm;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_transfersucceed;
    }

    @Override
    protected void init() {
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        title.setText("余额转出成功");
        paysucceed_tv_money = (TextView) findViewById(R.id.paysucceed_tv_money);
        paysucceed_btn_confirm = (Button) findViewById(R.id.paysucceed_btn_confirm);

        double amount = Double.parseDouble(getIntent().getStringExtra("amount"));
        paysucceed_tv_money.setText(String.valueOf(amount));

        RippleUtils.rippleNormal(paysucceed_btn_confirm);
        paysucceed_btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransferSucceedActivity.this.finish();
            }
        });
    }

    @Override
    public void errorPage() {
    }

    @Override
    public void blankPage() {
    }
}
