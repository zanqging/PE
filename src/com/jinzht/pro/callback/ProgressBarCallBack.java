package com.jinzht.pro.callback;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * ���Ȼص��ӿڣ���ʾ���ؽ��ȵ��������ȵ������رս��ȵ�����
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
