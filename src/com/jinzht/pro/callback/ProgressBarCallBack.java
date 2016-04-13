package com.jinzht.pro.callback;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * 进度回调接口，显示加载进度弹窗、进度弹窗、关闭进度弹窗。
 *
 * @auther Mr.Jobs
 * @date 2015/6/3
 * @time 10:37
 */

public interface ProgressBarCallBack {
    public void showLoadingProgressDialog();

    public void showProgressDialog(String message);

    public void dismissProgressDialog();
}
