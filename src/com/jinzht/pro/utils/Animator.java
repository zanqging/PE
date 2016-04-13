package com.jinzht.pro.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.util.Property;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jinzht.pro.numberprogressbar.NumberProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * @auther Mr Jobs
 * @date 2015/5/18
 * @time 22:22
 */
public class Animator {
    public static void incrementProgress(Timer timer,NumberProgressBar numberProgressBar,Activity activity){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        numberProgressBar.incrementProgressBy(1);
                    }
                });
            }
        }, 20, 20);
    }
    public static void LoveScale (CompoundButton compoundButton){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(compoundButton,"scaleX",1f,2f,2f).setDuration(1000);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(compoundButton,"scaleY",1f,2f,2f).setDuration(1000);

        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(objectAnimator,objectAnimator1);
        animSet.start();
        animSet.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animator) {

            }

            @Override
            public void onAnimationEnd(android.animation.Animator animator) {
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(compoundButton, "scaleX", 2f, 1f, 1f).setDuration(1000);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(compoundButton, "scaleY", 2f, 1f, 1f).setDuration(1000);
                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(objectAnimator2, objectAnimator3);
                animSet.start();
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animator) {

            }

            @Override
            public void onAnimationRepeat(android.animation.Animator animator) {

            }
        });
    }

    public static void CollectScale (ImageView compoundButton){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(compoundButton,"scaleX",1f,1.5f,1.5f).setDuration(500);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(compoundButton,"scaleY",1f,1.5f,1.5f).setDuration(500);

        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(objectAnimator,objectAnimator1);
        animSet.start();
        animSet.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animator) {

            }

            @Override
            public void onAnimationEnd(android.animation.Animator animator) {
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(compoundButton, "scaleX", 1.5f, 1f, 1f).setDuration(500);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(compoundButton, "scaleY", 1.5f, 1f, 1f).setDuration(500);
                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(objectAnimator2, objectAnimator3);
                animSet.start();
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animator) {

            }

            @Override
            public void onAnimationRepeat(android.animation.Animator animator) {

            }
        });
    }
    public static void addMenu( ImageView imageView){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView,"Rotation",0f,45f).setDuration(500);
//        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageView,"rotationY",0f,90f).setDuration(500);
        objectAnimator.setInterpolator(new OvershootInterpolator());
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(objectAnimator);
        animSet.start();
    }
    public static void exitMenu(ImageView imageView){

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView,"Rotation",45f,0).setDuration(500);
//        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageView,"rotationY",0f,90f).setDuration(500);
        objectAnimator.setInterpolator(new OvershootInterpolator());
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(objectAnimator);
        animSet.start();
    }
    public static void moreMenu(LinearLayout ll_road,LinearLayout ll_invest){
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(ll_road,"translationY",700f,0f,0f).setDuration(1500);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(ll_invest,"translationY",500f,0f,0f).setDuration(2000);
        objectAnimator1.setInterpolator(new OvershootInterpolator());
        objectAnimator2.setInterpolator(new OvershootInterpolator());
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(objectAnimator1,objectAnimator2);
        animSet.start();
    }
    public static void moreExitMenu(LinearLayout ll_road,LinearLayout ll_invest){
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(ll_road,"translationY",0f,700f,700f).setDuration(1500);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(ll_invest,"translationY",0f,500f,500f).setDuration(2000);
        objectAnimator1.setInterpolator(new OvershootInterpolator());
        objectAnimator2.setInterpolator(new OvershootInterpolator());
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(objectAnimator1,objectAnimator2);
        animSet.start();
    }
    public static void noLoginMenu(ImageView clound,ImageView earth,ImageView email,ImageView register_anim){
        ObjectAnimator ob1 = ObjectAnimator.ofFloat(clound, "alpha", 0f, 1f, 1f).setDuration(2000);
        ObjectAnimator ob2 = ObjectAnimator.ofFloat(earth, "translationX", -400f, 30f, 0f).setDuration(3000);
        ObjectAnimator ob3 = ObjectAnimator.ofFloat(email, "translationX", 400f, -30f, 0f).setDuration(3000);
        ObjectAnimator ob4 = ObjectAnimator.ofFloat(register_anim, "translationY", -400f, 50f, 0f).setDuration(5000);
        ob4.setInterpolator(new OvershootInterpolator());
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(ob1,ob2,ob3,ob4);
        animSet.start();
    }
    public static void shareLogin(TextView wechat,TextView wechat_moment,TextView qq,TextView message ,ImageView finish){
        ObjectAnimator ob1 = ObjectAnimator.ofFloat(wechat, "translationX", -400f, 30f, 0f).setDuration(3000);
        ObjectAnimator ob2 = ObjectAnimator.ofFloat(wechat_moment, "translationX", -400f, 30f, 0f).setDuration(2500);
        ObjectAnimator ob3 = ObjectAnimator.ofFloat(qq, "translationX", 400f, -30f, 0f).setDuration(2500);
        ObjectAnimator ob4 = ObjectAnimator.ofFloat(message, "translationX", 400f, -30f, 0f).setDuration(3000);
        ObjectAnimator ob5 = ObjectAnimator.ofFloat(finish, "Rotation",  -360f, 0f).setDuration(3000);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(ob1,ob2,ob3,ob4,ob5);
        animSet.start();
    }
    public static void shareCircle(TextView wechat,TextView wechat_moment,TextView qq,TextView message ,TextView circle,ImageView finish){
        circle.setVisibility(View.VISIBLE);
        ObjectAnimator ob1 = ObjectAnimator.ofFloat(wechat, "translationX", -400f, 30f, 0f).setDuration(3000);
        ObjectAnimator ob2 = ObjectAnimator.ofFloat(wechat_moment, "translationX", -400f, 30f, 0f).setDuration(2500);
        ObjectAnimator ob3 = ObjectAnimator.ofFloat(qq, "translationX", 400f, -30f, 0f).setDuration(2500);
        ObjectAnimator ob4 = ObjectAnimator.ofFloat(message, "translationX", 400f, -30f, 0f).setDuration(3000);
        ObjectAnimator ob5 = ObjectAnimator.ofFloat(finish, "Rotation",  -360f, 0f).setDuration(3000);
        ObjectAnimator ob6 = ObjectAnimator.ofFloat(circle,"alpha",0f,1f,1f).setDuration(3000);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(ob1,ob2,ob3,ob4,ob5,ob6);
        animSet.start();
    }
    public static void welcomeLogo(ImageView finish){
        ObjectAnimator ob5 = ObjectAnimator.ofFloat(finish, "rotationY",  -360f, 0f).setDuration(1500);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(ob5);
        animSet.start();
    }
    public static void wantSignIn(ImageView sign_wave,ImageView iv_location){
        ObjectAnimator ob1 = ObjectAnimator.ofFloat(sign_wave, "rotationX",  -360f, 0f).setDuration(1500);
        ObjectAnimator ob2 = ObjectAnimator.ofFloat(iv_location, "rotationY",  -360f, 0f).setDuration(1500);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(ob1,ob2);
        animSet.start();
    }

}
