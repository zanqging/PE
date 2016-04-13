package com.jinzht.pro.model;

/**
 * Created by pc on 2016/3/15.
 * �ױ���ֵ�������
 */
public class ToRechargeBean {
    private String platformNo;// �̻����
    private String platformUserNo;// ƽ̨�û����
    private String requestNo;// ������ˮ��
    private String amount;// ��ֵ���
    private String feeMode;// ����ģʽ���̶�ֵPLATFORM
    private String callbackUrl;// ҳ�����URL
    private String notifyUrl;// ������֪ͨURL

    public ToRechargeBean() {
    }

    public ToRechargeBean(String platformNo, String platformUserNo, String requestNo, String amount, String feeMode, String callbackUrl, String notifyUrl) {
        this.platformNo = platformNo;
        this.platformUserNo = platformUserNo;
        this.requestNo = requestNo;
        this.amount = amount;
        this.feeMode = feeMode;
        this.callbackUrl = callbackUrl;
        this.notifyUrl = notifyUrl;
    }

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    public String getPlatformUserNo() {
        return platformUserNo;
    }

    public void setPlatformUserNo(String platformUserNo) {
        this.platformUserNo = platformUserNo;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFeeMode() {
        return feeMode;
    }

    public void setFeeMode(String feeMode) {
        this.feeMode = feeMode;
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

    @Override
    public String toString() {
        return "ToRechargeBean{" +
                "platformNo='" + platformNo + '\'' +
                ", platformUserNo='" + platformUserNo + '\'' +
                ", requestNo='" + requestNo + '\'' +
                ", amount='" + amount + '\'' +
                ", feeMode='" + feeMode + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                '}';
    }
}
