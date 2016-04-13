package com.jinzht.pro.model;

import java.util.List;

/**
 * Created by pc on 2016/3/16.
 * 易宝投标输入参数
 */
public class ToCpTransactionBean {
    private String platformNo;// 商户编号
    private String requestNo;// 请求流水号
    private String platformUserNo;// 出款人平台用户编号
    private String userType;// 出款人用户类型，目前只支持传入MEMBER
    private String bizType;// 业务类型，投标TENDER
    private List<ToCpTransactionDetailBean> details;// 资金明细集合
    private List<ToCpTransactionPropertyBean> extend;// 业务扩展属性集合
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
