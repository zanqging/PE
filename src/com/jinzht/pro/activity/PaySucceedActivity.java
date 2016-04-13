package com.jinzht.pro.activity;

import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.mycircleimage.PolygonImageView;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.UpLoadImageUtils;

import butterknife.Bind;

/**
 * Created by pc on 2016/3/18.
 * 支付成功页面
 */
public class PaySucceedActivity extends BaseActivity{

    @Bind(R.id.title)
    TextView title;

    private TextView paysucceed_tv_money;
    private PolygonImageView paysucceed_iamge;
    private TextView paysucceed_tv_projectname;
    private TextView paysucceed_tv_companyname;
    private Button paysucceed_btn_confirm;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_paysucceed;
    }

    @Override
    protected void init() {
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        title.setText("支付成功");
        paysucceed_tv_money = (TextView) findViewById(R.id.paysucceed_tv_money);
        paysucceed_iamge = (PolygonImageView) findViewById(R.id.paysucceed_iamge);
        paysucceed_tv_projectname = (TextView) findViewById(R.id.paysucceed_tv_projectname);
        paysucceed_tv_companyname = (TextView) findViewById(R.id.paysucceed_tv_companyname);
        paysucceed_btn_confirm = (Button) findViewById(R.id.paysucceed_btn_confirm);

        double amount = Double.parseDouble(getIntent().getStringExtra("amount")) * 10000.00;
        paysucceed_tv_money.setText(String.valueOf(amount));
        paysucceed_tv_projectname.setText(getIntent().getStringExtra("abbrevName"));
        paysucceed_tv_companyname.setText(getIntent().getStringExtra("companyName"));
        UpLoadImageUtils.getUserImage(getIntent().getStringExtra("image"), paysucceed_iamge);

        RippleUtils.rippleNormal(paysucceed_btn_confirm);
        paysucceed_btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaySucceedActivity.this, MyFinacingInvestActivity.class);
                PaySucceedActivity.this.startActivity(intent);
                PaySucceedActivity.this.finish();
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
