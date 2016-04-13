package com.jinzht.pro.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.activity.InvestorAuthenticationActivity;
import com.jinzht.pro.activity.WantRoadShowActivity;
import com.jinzht.pro.activity.WantSignInActivity;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/7/18
 * @time 16:51
 */

public class FloatBtnUtils {
    public static ShareCallBack shareCallBacks;

    public FloatBtnUtils(ShareCallBack shareCallBacks) {
        this.shareCallBacks = shareCallBacks;
    }

    public static List<View> floatView(Context context, List<View> lists) {
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < 5; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.float_item, null);//也可以从XML中加载布局
            TextView image = (TextView) view.findViewById(R.id.image);
            TextView text = (TextView) view.findViewById(R.id.text);
            if (i == 0) {
                image.setBackgroundResource(R.drawable.one);
                text.setText(context.getResources().getString(R.string.want_road_show));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, WantRoadShowActivity.class);
                        context.startActivity(intent);
                    }
                });
            } else if (i == 1) {
                image.setBackgroundResource(R.drawable.two);
                text.setText(context.getResources().getString(R.string.want_authion));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, InvestorAuthenticationActivity.class);
                        context.startActivity(intent);
                    }
                });
            } else if (i == 2) {
                image.setBackgroundResource(R.drawable.thrid);
                text.setText(context.getResources().getString(R.string.want_sign_in));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, WantSignInActivity.class);
                        context.startActivity(intent);
                    }
                });
            }/*else if (i==3){
                image.setBackgroundResource(R.drawable.four);
                text.setText(context.getResources().getString(R.string.wani_invest));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, WantInvestActivity.class);
                        context.startActivity(intent);
                    }
                });
            }*/ else if (i == 3) {
                image.setBackgroundResource(R.drawable.five);
                text.setText(context.getResources().getString(R.string.want_share));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, WantRoadShowActivity.class);
                        context.startActivity(intent);
                    }
                });
            }
            view.setLayoutParams(tvParams);
            lists.add(view);
        }
        return lists;
    }


    public interface ShareCallBack {
        public void shareCallBack(View view);
    }
}
