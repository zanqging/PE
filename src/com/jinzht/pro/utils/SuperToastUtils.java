package com.jinzht.pro.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;

import com.jinzht.pro.supertoasts.SuperToast;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * @auther Mr Jobs
 * @date 2015/8/28
 * @time 0:07
 */
public class SuperToastUtils {

    public static void showSuperToast(Context activity, int flag, String message) {
        final SuperToast superToast = new SuperToast(activity);
        toastEffect(flag, superToast);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setBackground(SuperToast.Background.ORANGE);
        superToast.setTextSize(SuperToast.TextSize.MEDIUM);
//        superToast.setIcon(R.drawable.icon_light_share, SuperToast.IconPosition.LEFT);
        superToast.setText(message);
        superToast.setGravity(Gravity.CENTER, 10, 50);
        superToast.show();
    }

    public static void showSuperToast(Context activity, int flag, int message) {
        final SuperToast superToast = new SuperToast(activity);
        toastEffect(flag, superToast);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setBackground(SuperToast.Background.ORANGE);
        superToast.setTextSize(SuperToast.TextSize.MEDIUM);
//        superToast.setIcon(R.drawable.icon_light_share, SuperToast.IconPosition.LEFT);
        superToast.setText(activity.getResources().getString(message));
        superToast.setGravity(Gravity.CENTER, 10, 50);
        superToast.show();
    }

    public static void showSuperToast(Activity activity, int flag, int message) {
        final SuperToast superToast = new SuperToast(activity);
        toastEffect(flag, superToast);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setBackground(SuperToast.Background.ORANGE);
        superToast.setTextSize(SuperToast.TextSize.MEDIUM);
//        superToast.setIcon(R.drawable.icon_light_share, SuperToast.IconPosition.LEFT);
        superToast.setText(activity.getResources().getString(message));
        superToast.setGravity(Gravity.CENTER, 10, 50);
        superToast.show();
    }

    private static void toastEffect(int flag, SuperToast superToast) {
        switch (flag) {
            case 0:
                superToast.setAnimations(SuperToast.Animations.FADE);
                break;
            case 1:
                superToast.setAnimations(SuperToast.Animations.FLYIN);
                break;
            case 2:
                superToast.setAnimations(SuperToast.Animations.POPUP);
                break;
            case 3:
                superToast.setAnimations(SuperToast.Animations.SCALE);
                break;
        }
    }
}
