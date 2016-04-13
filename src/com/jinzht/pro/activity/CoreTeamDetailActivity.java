package com.jinzht.pro.activity;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.mycircleimage.PolygonImageView;
import com.jinzht.pro.utils.RippleUtils;
import com.jinzht.pro.utils.UpLoadImageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * 核心团队成员详情页面
 *
 * @auther Mr.Jobs
 * @date 2015/7/23
 * @time 18:41
 */

public class CoreTeamDetailActivity extends BaseActivity {
    @Bind(R.id.back)
    LinearLayout back;

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.image)
    PolygonImageView image;// 头像
    List<String> list = new ArrayList<>();
    @Bind(R.id.core_team_detail)
    TextView context;// 个人档案
    @Bind({R.id.name, R.id.position})
    List<TextView> textViewList;// 姓名，职位
    @Bind(R.id.blur_image)
    ImageView blur_image;

    @Override
    protected int getResourcesId() {
        return R.layout.activity_core_team_detail;
    }

    @Override
    protected void init() {
        RippleUtils.rippleNormal(back);
        image.setImageResource(R.drawable.user_loading);
        textViewList.get(0).setText(getIntent().getExtras().getString("name"));
        textViewList.get(1).setText(getIntent().getExtras().getString("postion"));
        UpLoadImageUtils.getImage(getIntent().getExtras().getString("image"), image);
        context.setText(getIntent().getExtras().getString("profile"));
//        UpLoadImageUtils.getImage(getIntent().getExtras().getString("image"),blur_image);
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