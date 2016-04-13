package com.jinzht.pro.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.jinzht.pro.R;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * 加载进度条
 *
 * @auther Mr Jobs
 * @date 2015/4/21
 * @time 13:10
 */
public class LoadingProssbar extends ProgressDialog {
    private AnimationDrawable mAnimation;
    private Context mContext;
    private ImageView mImageView;
    private String mLoadingTip;
    private TextView mLoadingTv;
    private int mResid;
    public LoadingProssbar(Context context, String content, int mResid) {
        super(context, R.style.MyWidget_DialogFull);
        this.mContext = context;
        this.mLoadingTip = content;
        this.mResid = mResid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        mImageView.setBackgroundResource(mResid);
        // 通过ImageView对象拿到背景显示的AnimationDrawable
        mAnimation = (AnimationDrawable) mImageView.getBackground();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
        mLoadingTv.setText(mLoadingTip);
    }

    private void initView() {
        this.setProgressStyle(R.style.MyWidget_DialogFull);
        setContentView(R.layout.dialog_loading);
        mLoadingTv = (TextView) findViewById(R.id.loadingTv);
        mImageView = (ImageView) findViewById(R.id.loadingIv);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mAnimation.start();
        super.onWindowFocusChanged(hasFocus);
    }
}
