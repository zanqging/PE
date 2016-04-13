package com.jinzht.pro.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinzht.pro.R;
import com.jinzht.pro.activity.AboutMeActivity;
import com.jinzht.pro.activity.ImproveActivity;
import com.jinzht.pro.activity.InvestorAuthenticationActivity;
import com.jinzht.pro.activity.LoginActivity;
import com.jinzht.pro.activity.MyFinacingInvestActivity;
import com.jinzht.pro.activity.OrganizationAuthActivity;
import com.jinzht.pro.activity.PersonAuthenticationActivity;
import com.jinzht.pro.view.EasyDialog;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 * <p>
 * 各种对话框
 *
 * @auther Mr.Jobs
 * @date 2015/5/30
 * @time 9:23
 */

public class DialogUtils {
    public static void loginDialog(Activity context) {
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_login);
        dialog.setCanceledOnTouchOutside(true);
        view.setWindowAnimations(R.style.dialog_animator);  //��Ӷ���
        TextView go_login = (TextView) view.findViewById(R.id.go_login);
        TextView later = (TextView) view.findViewById(R.id.later);
        final AlertDialog finalDialog = dialog;
        //����¼
        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                context.finish();
                finalDialog.dismiss();
            }
        });
        final AlertDialog finalDialog1 = dialog;
        //�Ժ���˵
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDialog1.dismiss();
            }
        });
    }

    public static void authDialog(Activity context) {
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_go_authertition);
        dialog.setCanceledOnTouchOutside(true);
        view.setWindowAnimations(R.style.dialog_animator);  //��Ӷ���
        TextView go_authertition = (TextView) view.findViewById(R.id.go_authertition);
        TextView later = (TextView) view.findViewById(R.id.later);
        final AlertDialog finalDialog = dialog;
        //����¼
        go_authertition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InvestorAuthenticationActivity.class);
                context.startActivity(intent);
                context.finish();
                finalDialog.dismiss();
            }
        });
        final AlertDialog finalDialog1 = dialog;
        //�Ժ���˵
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDialog1.dismiss();
            }
        });
    }

    public static void investPassDialog(Activity activity, String msg, String submit, String id) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_submit);
        dialog.setCanceledOnTouchOutside(false);
        view.setWindowAnimations(R.style.dialog_animator);  //��Ӷ���
        TextView dialog_title = (TextView) view.findViewById(R.id.msg);
        TextView submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        dialog_title.setText(msg);
        submit_btn.setText(submit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(activity, MyFinacingInvestActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    public static void isYoursDialog(Activity activity, String msg, String submit) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_submit);
        dialog.setCanceledOnTouchOutside(false);
        view.setWindowAnimations(R.style.dialog_animator);  //��Ӷ���
        TextView dialog_title = (TextView) view.findViewById(R.id.msg);
        TextView submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        dialog_title.setText(msg);
        submit_btn.setText(submit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
//                Intent intent = new Intent(activity, MyFinacingInvestActivity.class);
//                activity.startActivity(intent);
//                activity.finish();
            }
        });
    }

    public static void UpdateVersonDialog(Activity activity, String context) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_update_verson);
        dialog.setCanceledOnTouchOutside(true);
        TextView right_update = (TextView) view.findViewById(R.id.right_update);
        TextView give_up = (TextView) view.findViewById(R.id.give_up);
        TextView update_context = (TextView) view.findViewById(R.id.update_context);
        final AlertDialog finalDialog = dialog;
        update_context.setText(context);
        right_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        give_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public static void easyMenuToast(Activity activity, String message, int width, int height, View bottom_view) {
        View view = activity.getLayoutInflater().inflate(R.layout.layout_tip_content_horizontal, null);
        EasyDialog easyDialog = new EasyDialog(activity);
        easyDialog.setLayout(view)
                .setBackgroundColor(activity.getResources().getColor(R.color.background_color_black))
//                .setLocationByAttachedView(showView)
                .setLocation(new int[]{width, height})
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(false)
                .setMatchParent(true)
                .setCancelable(false)
                .setMarginLeftAndRight(24, 24)
                .setOutsideColor(activity.getResources().getColor(R.color.transparent))
                .show();
        ((TextView) view.findViewById(R.id.message)).setText(message);
        view.findViewById(R.id.i_know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePreferencesUtils.setIsToastMenu(activity, true);
                DialogUtils.easyMenuToast(activity, R.string.toast_bottom_add, bottom_view);
                easyDialog.dismiss();
            }
        });
    }

    public static void easyMenuToast(Activity activity, int message, View views) {
        View view = activity.getLayoutInflater().inflate(R.layout.layout_tip_content_horizontal, null);
        EasyDialog easyDialog = new EasyDialog(activity);
        easyDialog.setLayout(view)
                .setBackgroundColor(activity.getResources().getColor(R.color.background_color_black))
                .setLocationByAttachedView(views)
                .setGravity(EasyDialog.GRAVITY_TOP)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(false)
                .setMatchParent(true)
                .setCancelable(false)
                .setMarginLeftAndRight(24, 24)
                .setOutsideColor(activity.getResources().getColor(R.color.transparent))
                .show();
        ((TextView) view.findViewById(R.id.message)).setText(activity.getResources().getString(message));
        view.findViewById(R.id.i_know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePreferencesUtils.setIsBottomToast(activity, true);
                easyDialog.dismiss();
            }
        });
    }

    public static void easyMenuToast1(Activity activity, int message, int width, int height) {
        View view = activity.getLayoutInflater().inflate(R.layout.layout_tip_content_horizontal, null);
        EasyDialog easyDialog = new EasyDialog(activity);
        easyDialog.setLayout(view)
                .setBackgroundColor(activity.getResources().getColor(R.color.background_color_black))
                .setLocation(new int[]{width, height})
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_X, 1000, -600, 100, -50, 50, 0)
                .setAnimationAlphaShow(1000, 0.3f, 1.0f)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_X, 500, -50, 800)
                .setAnimationAlphaDismiss(500, 1.0f, 0.0f)
                .setTouchOutsideDismiss(false)
                .setMatchParent(true)
                .setCancelable(false)
                .setMarginLeftAndRight(24, 24)
                .setOutsideColor(activity.getResources().getColor(R.color.transparent))
                .show();
        ((TextView) view.findViewById(R.id.message)).setText(activity.getResources().getString(message));
        view.findViewById(R.id.i_know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePreferencesUtils.setIsToastMenuOther(activity, true);
                easyDialog.dismiss();
            }
        });
    }

    // 选择投资人类型对话框
    public static void showInvestDialogs(Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        Window view = dialog.getWindow();
        view.setGravity(Gravity.CENTER);  //�˴���������dialog��ʾ��λ��
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        dialog.show();
        view.setContentView(R.layout.dialog_shenfeng);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        LinearLayout ll_person = (LinearLayout) view.findViewById(R.id.ll_person);
        LinearLayout ll_oria = (LinearLayout) view.findViewById(R.id.ll_organization);
        RelativeLayout rl_dissmiss = (RelativeLayout) view.findViewById(R.id.rl_dissmiss);
        rl_dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ll_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(activity, PersonAuthenticationActivity.class);
                intent.putExtra("flag", 1);
                activity.startActivity(intent);
            }
        });
        ll_oria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(activity, OrganizationAuthActivity.class);
                intent.putExtra("flag", 2);
                activity.startActivity(intent);
            }
        });
    }

    public static void waitAuthDialog(Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_wait_auth);
        dialog.setCanceledOnTouchOutside(false);
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        TextView dialog_title = (TextView) view.findViewById(R.id.msg);
        TextView submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        TextView cancel_btn = (TextView) view.findViewById(R.id.cancel_btn);
        dialog_title.setText(activity.getResources().getString(R.string.wait_auth));
        submit_btn.setText(activity.getResources().getString(R.string.modify));
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                showInvestDialogs(activity);
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public static void failAuthDialog(Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_wait_auth);
        dialog.setCanceledOnTouchOutside(true);
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        TextView dialog_title = (TextView) view.findViewById(R.id.msg);
        TextView submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        dialog_title.setText(activity.getResources().getString(R.string.fail_auth));
        TextView cancel_btn = (TextView) view.findViewById(R.id.cancel_btn);
        submit_btn.setText(activity.getResources().getString(R.string.auth_again));
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    // 提示完善个人资料对话框
    public static void perfectInformationDialog(Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        Window view = dialog.getWindow();
        view.setGravity(Gravity.CENTER);  //�˴���������dialog��ʾ��λ��
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        dialog.show();
        view.setContentView(R.layout.dialog_improve_information);
        dialog.setCanceledOnTouchOutside(true);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(activity, ImproveActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public static void toastDialog(Activity activity, String msg, String submit) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_submit);
        dialog.setCanceledOnTouchOutside(true);
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        TextView dialog_title = (TextView) view.findViewById(R.id.msg);
        TextView submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        dialog_title.setText(msg);
        submit_btn.setText(submit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public static void authSuccessDialog(Activity activity, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_submit);
        dialog.setCanceledOnTouchOutside(true);
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        TextView dialog_title = (TextView) view.findViewById(R.id.msg);
        TextView submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        dialog_title.setText(msg);
        submit_btn.setText(activity.getResources().getString(R.string.crop__done));
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(activity, AboutMeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    // 我要认证前完善信息的对话框
    public static void improveInformationDialog(Activity context) {
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_two_btn);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
        tv_msg.setText(context.getResources().getString(R.string.perfect_information));
        final AlertDialog finalDialog = dialog;
        //����¼
        tv_sure.setOnClickListener(new View.OnClickListener() {// 打开完善资料界面ImproveActivity
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImproveActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                finalDialog.dismiss();
            }
        });
        final AlertDialog finalDialog1 = dialog;
        //�Ժ���˵
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDialog1.dismiss();
            }
        });
    }

    public static void authFailDialog(Activity activity, String msg, String submit) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_submit);
        dialog.setCanceledOnTouchOutside(true);
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        TextView dialog_title = (TextView) view.findViewById(R.id.msg);
        TextView submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        dialog_title.setText(msg);
        submit_btn.setText(submit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    // 弹出分享对话框
    public static void ShareDialog(Activity activity, View parent, ShareUtils shareUtils, String title, String context, String image_url, String url) {
        PopupWindow popWindow = null;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.share_popup, null);
        // ����һ��PopuWidow����
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        TextView wechat = (TextView) view.findViewById(R.id.wechat);
        TextView wechat_moment = (TextView) view.findViewById(R.id.wechat_moment);
        TextView jinzht = (TextView) view.findViewById(R.id.jinzht);
        jinzht.setVisibility(View.GONE);
        TextView qq = (TextView) view.findViewById(R.id.qq);
        TextView message = (TextView) view.findViewById(R.id.message);
        final PopupWindow finalPopWindow = popWindow;
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareUtils.wechat(title, context, image_url, url);
                finalPopWindow.dismiss();
            }
        });
        wechat_moment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareUtils.wechatMement(title, context, image_url, url);
                finalPopWindow.dismiss();
            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareUtils.qq(title, context, image_url, url);
                finalPopWindow.dismiss();
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareUtils.message(title, context, image_url, url);
                finalPopWindow.dismiss();
            }
        });
        popWindow.setAnimationStyle(R.style.dialog_zoom);
        // ʹ��ۼ� ��Ҫ������˵���ؼ����¼��ͱ���Ҫ���ô˷���
        popWindow.setFocusable(true);
        // ����������������ʧ
        popWindow.setOutsideTouchable(true);
        // ���ñ����������Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı���
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        //����̲��ᵲ��popupwindow
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //���ò˵���ʾ��λ��
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    public static void lookNullDialog(Activity activity, String context) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_wait_auth);
        dialog.setCanceledOnTouchOutside(false);
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        TextView dialog_title = (TextView) view.findViewById(R.id.msg);
        TextView submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        TextView cancel_btn = (TextView) view.findViewById(R.id.cancel_btn);
        dialog_title.setText(context);
        submit_btn.setText(activity.getResources().getString(R.string.modify));
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                showInvestDialogs(activity);
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public static void lookEmptyDialog(Activity activity, String context) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_wait_auth);
        dialog.setCanceledOnTouchOutside(false);
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        TextView dialog_title = (TextView) view.findViewById(R.id.msg);
        TextView submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        TextView cancel_btn = (TextView) view.findViewById(R.id.cancel_btn);
        dialog_title.setText(context);
        submit_btn.setText(activity.getResources().getString(R.string.modify));
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                showInvestDialogs(activity);
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public static void lookFailDialog(Activity activity, String msg, String submit) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_submit);
        dialog.setCanceledOnTouchOutside(true);
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        TextView dialog_title = (TextView) view.findViewById(R.id.msg);
        TextView submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        dialog_title.setText(msg);
        submit_btn.setText(submit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public static void nightModelDialog(Activity activity, String msg) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_night_model);
        dialog.setCanceledOnTouchOutside(true);
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        TextView dialog_title = (TextView) view.findViewById(R.id.msg);
        TextView submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        TextView ok = (TextView) view.findViewById(R.id.give_up);
        dialog_title.setText(msg);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**实际上夜间模式很是简单*/
                if (!DisplayUtils.isAutoBrightness(activity)) {
                    DisplayUtils.startAutoBrightness(activity);
                }
                SharePreferencesUtils.setLightModel(activity, true);
                dialog.dismiss();
            }
        });
    }

    public static void changeAppBrightness(Activity activity, int brightness) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 100f;
        }
        window.setAttributes(lp);
    }

    // 提示用户进行易宝实名认证
//    public static void gotoYibaoRegister(Activity context) {
//        AlertDialog dialog = new AlertDialog.Builder(context, R.style.Translucent_NoTitle).create();
//        dialog.show();
//        Window view = dialog.getWindow();
//        view.setContentView(R.layout.dialog_two_btn);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
//        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
//        TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);// 确定按钮
//        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);// 取消按钮
//        TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
//        tv_msg.setText(context.getResources().getString(R.string.yibao_register));
//        final AlertDialog finalDialog = dialog;
//        //����¼
//        tv_sure.setOnClickListener(new View.OnClickListener() {// 跳转至易宝注册页面YibaoRegisterWebViewActivity
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, YibaoRegisterWebViewActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//                finalDialog.dismiss();
//            }
//        });
//        final AlertDialog finalDialog1 = dialog;
//        //�Ժ���˵
//        tv_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finalDialog1.dismiss();
//            }
//        });
//    }

    // 提示用户投标流程还没有完成
    public static void goonCompleteInvestDialog(Activity context) {
        AlertDialog dialog = new AlertDialog.Builder(context, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_two_btn);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);// 确定按钮
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);// 取消按钮
        TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
        tv_msg.setText(context.getResources().getString(R.string.yibao_recharge));
        final AlertDialog finalDialog = dialog;
        // 退出页面
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDialog.dismiss();
                context.finish();
            }
        });
        final AlertDialog finalDialog1 = dialog;
        // 留在页面
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDialog1.dismiss();
            }
        });
    }

    // 提示出错
    public static void errorDialog(Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity, R.style.Translucent_NoTitle).create();
        dialog.show();
        Window view = dialog.getWindow();
        view.setContentView(R.layout.dialog_submit);
        dialog.setCanceledOnTouchOutside(true);
        view.setWindowAnimations(R.style.dialog_zoom);  //��Ӷ���
        TextView dialog_title = (TextView) view.findViewById(R.id.msg);
        TextView submit_btn = (TextView) view.findViewById(R.id.submit_btn);
        dialog_title.setText("服务器错误，请稍后重试");
        submit_btn.setText(activity.getResources().getString(R.string.crop__done));
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
