package com.jinzht.pro.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jinzht.pro.Constant;
import com.jinzht.pro.R;
import com.jinzht.pro.activity.AboutMeActivity;
import com.jinzht.pro.activity.InvestListActivity;
import com.jinzht.pro.activity.WantInvestActivity;
import com.jinzht.pro.view.SystemBarTintManager;

import java.util.List;
import java.util.UUID;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * @auther Mr Jobs
 * @date 2015/4/15
 * @time 13:04
 */
public class UiHelp {
    public UiHelp() {
        super();
    }

    public static void ToastMessageShort(Context context, int messgae) {
        Toast.makeText(context, context.getResources().getString(messgae), Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessageShort(Context context, String messgae) {
        Toast.makeText(context, messgae, Toast.LENGTH_SHORT).show();
    }

    public static void exceptionLog(Exception e) {
        Log.e("error", e.toString());
    }

    public static Boolean IsNumber(String string) {
        boolean result = string.matches("[0-9]+");
        return result;
    }

    public static Boolean EditisEmpty(EditText editText) {
        if ("".equals(editText.getText().toString()) || editText.getText().equals("null") || editText.getText().toString() == null) {
            return true;
        } else {
            return false;
        }
    }

    public static Object[] getListPostion(ListView listView) {
        int index = listView.getFirstVisiblePosition();
        View view = listView.getChildAt(0);
        int top = (view == null) ? 0 : view.getTop();
        return new Integer[]{index, top};
    }

    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    public static String getVersionName(Activity activity) throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = activity.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
        return packInfo.versionName;
    }

    public static int getVersionCode(Activity activity) throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = activity.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
        return packInfo.versionCode;
    }

    /**
     * 设置透明状态栏
     */
    @TargetApi(19)
    public static void setTranslucentStatus(boolean on, Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = context.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
            // 创建状态栏的管理实例
            SystemBarTintManager tintManager = new SystemBarTintManager(context);
            // 激活状态栏设置
            tintManager.setStatusBarTintEnabled(true);
            // 激活导航栏设置就是华为p6底下的导航栏
            tintManager.setNavigationBarTintEnabled(true);
            // 设置一个颜色给系统栏
            tintManager.setTintColor(/*Color.parseColor("#e94819")*/context.getResources().getColor(R.color.title_bg));
//    // 设置一个样式背景给导航栏
            tintManager.setNavigationBarTintResource(R.color.title_bg);
//        // 设置一个状态栏资源
//        tintManager.setStatusBarTintDrawable(MyDrawable);
        }

    }

    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public synchronized static boolean isLongFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static void canToast(Activity activity) {
        if (!isLongFastClick()) {
            SuperToastUtils.showSuperToast(activity, 1, R.string.last_page);
        }
    }

    public static void textBold(TextView view) {
        TextPaint tp = view.getPaint();
        tp.setFakeBoldText(true);
    }

    public static void textBold(RadioButton radioButton) {
        TextPaint tp = radioButton.getPaint();
        tp.setFakeBoldText(true);
    }

    public static void sendBroad(Activity activity) {
        Intent intentss = new Intent(Constant.MY_ACTION);
        /*  设置Intent对象的action属性  */
        /* 为Intent对象添加附加信息 */
        intentss.putExtra("msg", 1);
        /* 发布广播 */
        activity.sendBroadcast(intentss);
    }

    public static void hindBroad(Context activity) {
        Intent intentss = new Intent(Constant.HIDE_CIRCLE);
        /*  设置Intent对象的action属性  */
        /* 为Intent对象添加附加信息 */
        intentss.putExtra("msg", 1);
        /* 发布广播 */
        activity.sendBroadcast(intentss);
    }

    public static void showBroad(Context activity) {
        Intent intentss = new Intent(Constant.SHOW_CIRCLE);
        /*  设置Intent对象的action属性  */
        /* 为Intent对象添加附加信息 */
        intentss.putExtra("msg", 1);
        /* 发布广播 */
        activity.sendBroadcast(intentss);
    }

    public static boolean isTopActivity(Context context) {
        String packageName = "com.jinzht.pro";
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            //应用程序位于堆栈的顶层
            if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static void RunApp(String packageName, Context context) {
        PackageInfo pi;
        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.setPackage(pi.packageName);
            PackageManager pManager = context.getPackageManager();
            List apps = pManager.queryIntentActivities(
                    resolveIntent, 0);

            ResolveInfo ri = (ResolveInfo) apps.iterator().next();
            if (ri != null) {
                packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                context.startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void openJinZht(Context context, String packageName) {
        try {
            Intent intent = new Intent();
            PackageManager packageManager = context.getPackageManager();
            intent = packageManager.getLaunchIntentForPackage(packageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        } catch (Exception e) {

        }
    }

    //此方法只是关闭软键盘
    public static void hintKbTwo(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static void openOrHide(Context activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void printMsg(int code, Object object, Context activity) {
        switch (code) {
            case 0:
                if (object != null) {
                    SuperToastUtils.showSuperToast(activity, 1, object + "");
                }
                break;
            case 1:
                if (object != null) {
                    SuperToastUtils.showSuperToast(activity, 1, object + "");
                }
                break;
        }
    }

    public static void wechatMsg(int code, Object object, Context activity) {
        switch (code) {
            case 0:
                if (object != null) {
                    SuperToastUtils.showSuperToast(activity, 1, object + "");
                }
                break;
            case 1:
                if (object != null) {
                    SuperToastUtils.showSuperToast(activity, 1, object + "");
                }
                break;
        }
    }

    public static void isAuth(Activity activity, String auth, String id, String companyName, String abbrevName,String image,String profile,String brrow_user_no,double profit,int minfund) {
        if (StringUtils.isEquals(auth, "")) {
            /**从未认证过，哈哈哈*/
            DialogUtils.showInvestDialogs(activity);
        } else if (StringUtils.isEquals(auth, "null")) {
            /**已经认证，审核中*/
            DialogUtils.waitAuthDialog(activity);
        } else if (StringUtils.isEquals(auth, "true")) {
            /**成功*/
            // 进入投资付款页面
            Intent intent = new Intent(activity, WantInvestActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id", id);
            intent.putExtra("companyName", companyName);
            intent.putExtra("abbrevName", abbrevName);
            intent.putExtra("image", image);
            intent.putExtra("profile", profile);
            intent.putExtra("brrow_user_no",brrow_user_no);// 借款人
            intent.putExtra("profit",profit);// 利润分成
            intent.putExtra("minfund",minfund);// 最小投资额度
            activity.startActivity(intent);
        } else {
            DialogUtils.authFailDialog(activity, activity.getResources().getString(R.string.fail_auth), activity.getResources().getString(R.string.crop__done));
            /**失败*/
        }
    }


    public static void mainAuth(Activity activity, String auth) {
        if (StringUtils.isEquals(auth, "")) {
            /**从未认证过，哈哈哈*/
            DialogUtils.showInvestDialogs(activity);
        } else if (StringUtils.isEquals(auth, "null")) {
            /**已经认证，审核中*/
            DialogUtils.waitAuthDialog(activity);
        } else if (StringUtils.isEquals(auth, "true")) {
            /**成功*/
            Intent intent = new Intent(activity, AboutMeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } else {
            DialogUtils.authFailDialog(activity, activity.getResources().getString(R.string.fail_auth), activity.getResources().getString(R.string.crop__done));
            /**失败*/
        }
    }

    // 投资列表
    public static void investorList(Activity activity, String auth, String id) {
        if (StringUtils.isEquals(auth, "")) {
            /**从未认证过，哈哈哈*/
            DialogUtils.lookEmptyDialog(activity, activity.getResources().getString(R.string.invest_list_empty));
        } else if (StringUtils.isEquals(auth, "null")) {
            /**已经认证，审核中*/
            DialogUtils.lookNullDialog(activity, activity.getResources().getString(R.string.invest_list_null));
        } else if (StringUtils.isEquals(auth, "true")) {
            /**成功*/
            Intent intent = new Intent(activity, InvestListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id", id);
            activity.startActivity(intent);
        } else {
            DialogUtils.lookFailDialog(activity, activity.getResources().getString(R.string.invest_list_fail), activity.getResources().getString(R.string.crop__done));
            /**失败*/
        }
    }
}
