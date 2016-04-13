package com.jinzht.pro.model;

import java.util.List;

/**
 * Created by pc on 2016/3/16.
 * �ױ�Ͷ���������
 */
public class ToCpTransactionBean {
    private String platformNo;// �̻����
    private String requestNo;// ������ˮ��
    private String platformUserNo;// ������ƽ̨�û����
    private String userType;// �������û����ͣ�Ŀǰֻ֧�ִ���MEMBER
    private String bizType;// ҵ�����ͣ�Ͷ��TENDER
    private List<ToCpTransactionDetailBean> details;// �ʽ���ϸ����
    private List<ToCpTransactionPropertyBean> extend;// ҵ����չ���Լ���
    private String callbackUrl;
    private String notifyUrl;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<ToCpTransactionDetailBean> getDetails() {
        return details;
    }

    public void setDetails(List<ToCpTransactionDetailBean> details) {
        this.details = details;
    }

    public List<ToCpTransactionPropertyBean> getExtend() {
        return extend;
    }

    public void setExtend(List<ToCpTransactionPropertyBean> extend) {
        this.extend = extend;
    }

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getPlatformUserNo() {
        return platformUserNo;
    }

    public void setPlatformUserNo(String platformUserNo) {
        this.platformUserNo = platformUserNo;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
