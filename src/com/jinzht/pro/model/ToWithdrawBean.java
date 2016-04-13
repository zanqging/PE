package com.jinzht.pro.model;

/**
 * Created by pc on 2016/3/14.
 * 易宝用户注册时提交参数
 */
public class ToWithdrawBean {
    private String platformNo;// 商户编号，固定值
    private String requestNo;// 请求流水号，时间+uid，例如：201603191203241064
    private String callbackUrl;// 页面回跳URL
    private String notifyUrl;// 服务器通知URL
    private String platformUserNo;// 用户编号
    private String feeMode;// 固定值,PLATFORM或者USER

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

    public String getPlatformUserNo() {
        return platformUserNo;
    }

    public void setPlatformUserNo(String platformUserNo) {
        this.platformUserNo = platformUserNo;
    }

    public String getFeeMode() {
        return feeMode;
    }

    public void setFeeMode(String feeMode) {
        this.feeMode = feeMode;
    }

    @Override
    public String toString() {
        return "ToWithdrawBean{" +
                "platformNo='" + platformNo + '\'' +
                ", requestNo='" + requestNo + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", platformUserNo='" + platformUserNo + '\'' +
                ", feeMode='" + feeMode + '\'' +
                '}';
    }
}
