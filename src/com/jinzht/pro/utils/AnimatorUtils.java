package com.jinzht.pro.utils;

import android.animation.*;
import android.animation.Animator;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AnimatorUtils {

  public static final String ROTATION = "rotation";//此属性为平面的转
  public static final String SCALE_X = "scaleX";// 缩放
  public static final String SCALE_Y = "scaleY";
  public static final String TRANSLATION_X = "translationX";
  public static final String TRANSLATION_Y = "translationY";
  public static final String ROTATION_X = "rotationX";//此属性是立体的转
  public static final String ROTATION_Y = "rotationY";
  public static final String ALPHA = "alpha";// 透明度

  private AnimatorUtils() {
    //No instances.
  }

  public static PropertyValuesHolder rotation(float... values) {
    return PropertyValuesHolder.ofFloat(ROTATION, values);
  }

  public static PropertyValuesHolder translationX(float... values) {
    return PropertyValuesHolder.ofFloat(TRANSLATION_X, values);
  }

  public static PropertyValuesHolder translationY(float... values) {
    return PropertyValuesHolder.ofFloat(TRANSLATION_Y, values);
  }

  public static PropertyValuesHolder scaleX(float... values) {
    return PropertyValuesHolder.ofFloat(SCALE_X, values);
  }

  public static PropertyValuesHolder scaleY(float... values) {
    return PropertyValuesHolder.ofFloat(SCALE_Y, values);
  }

  public static void showPerfectInformation(RelativeLayout relativeLayout){
    relativeLayout.setVisibility(View.VISIBLE);
    /**该方法默认是从中心点来操作的*/
    ObjectAnimator ob1 = ObjectAnimator.ofFloat(relativeLayout, ROTATION_X, -90f, 0f,0f).setDuration(1400);
//    PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(ROTATION_X,0f, -90f,0f);
//    PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(ROTATION_X, 0f);
//    ObjectAnimator.ofPropertyValuesHolder(relativeLayout, pvhX/*, pvhY*/).start();
//    ob4.setInterpolator(new OvershootInterpolator());
    relativeLayout. setPivotX(0);
    AnimatorSet animSet = new AnimatorSet();
    animSet.playTogether(ob1);
    animSet.start();
  }
  public static void telephoneShake(ImageView telephone){
    ObjectAnimator ob1 = ObjectAnimator.ofFloat(telephone, ROTATION, 45f,0f,-45f).setDuration(1000);
    ob1.setRepeatMode(ValueAnimator.REVERSE);//反向的来一次
    ob1.setRepeatCount(ValueAnimator.INFINITE);
    ob1.setInterpolator(new BounceInterpolator());
    ob1.start();
  }
  public static void authLook(ImageView seal,ImageView iv_indenty){
    seal.setVisibility(View.VISIBLE);
    ObjectAnimator ob1 = ObjectAnimator.ofFloat(iv_indenty,ALPHA,0f,1f).setDuration(500);
//    AnimatorSet animatorSet = new AnimatorSet();
//    animatorSet.play(ob1).after(obj2);
//    animatorSet.start();
    ob1.start();
    ob1.addListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animator) {

      }

      @Override
      public void onAnimationEnd(Animator animator) {
        iv_indenty.setVisibility(View.VISIBLE);
        ObjectAnimator obj2 = ObjectAnimator.ofFloat(seal,ALPHA,0f,1f).setDuration(700);
        obj2.start();
      }

      @Override
      public void onAnimationCancel(Animator animator) {

      }

      @Override
      public void onAnimationRepeat(Animator animator) {

      }
    });
  }
}
