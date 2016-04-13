package com.jinzht.pro.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.jinzht.pro.R;
import com.jinzht.pro.activity_transition.ActivityTransition;
import com.jinzht.pro.activity_transition.ExitActivityTransition;

import butterknife.Bind;

/**
 * Created by Administrator on 2015/10/13.
 *
 * ’’∆¨£ø√≤À∆√ª”√µΩ
 */
public class PhotoDetailActivity extends BaseActivity {

    public static final String EXTRA_RESULT = "result";
    private ExitActivityTransition exitTransition;
    public static final String EXTRA_IMAGE = "DetailActivity:image";
    @Bind(R.id.image)
    ImageView image;
    @Override
    protected int getResourcesId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void init() {
//        ViewCompat.setTransitionName(image, EXTRA_IMAGE);
//        TransitionManager.setTransitionName(image, EXTRA_IMAGE);
//        UpLoadImageUtils.getImage(getIntent().getStringExtra(EXTRA_IMAGE),image);


    }

    public static void launch(Activity activity, View transitionView, String url) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, EXTRA_IMAGE);
        Intent intent = new Intent(activity, PhotoDetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, url);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exitTransition = ActivityTransition
                .with(getIntent())
                .to(image)
//                .interpolator(new BounceInterpolator())
                .start(savedInstanceState);
//        exitTransition= ActivityTransition.with(getIntent()).to(image).start(savedInstanceState);
    }
    @Override
    public void onBackPressed() {
//        exitTransition.exit(this);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT, "ok");
        setResult(RESULT_OK, intent);
        exitTransition.interpolator(new OvershootInterpolator()).exit(this);
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