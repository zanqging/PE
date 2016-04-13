package com.jinzht.pro.activity;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.utils.UpLoadImageUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/28.
 * <p>
 * 认证查看页面
 */
public class AuthPhotoLookActivity extends BaseActivity {

    @Bind(R.id.iv_image)
    ImageView iv_image;
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
        return R.layout.activity_auth_photo_look;
    }

    @Override
    protected void init() {
        title.setText(getResources().getString(R.string.photo_look));
        UpLoadImageUtils.getImage(getIntent().getStringExtra("img"), iv_image);
    }

    @Override
    public void errorPage() {
    }

    @Override
    public void blankPage() {
    }
}