package com.jinzht.pro.model;

/**
 * Created by pc on 2016/3/16.
 * �ױ�Ͷ���ʽ���ϸ��¼
 */
public class ToCpTransactionDetailBean {
    private String targetUserType;// �տ����û����ͣ�����MEMBER,�̻�MERCHANT
    private String targetPlatformUserNo;// �տ���ƽ̨�û����
    private String amount;// ת����;
    private String bizType;// ҵ�����ͣ�Ͷ��TENDER,����COMMISSION

    public ToCpTransactionDetailBean(){};

    public ToCpTransactionDetailBean(String targetUserType, String targetPlatformUserNo, String amount, String bizType) {
        this.targetUserType = targetUserType;
        this.targetPlatformUserNo = targetPlatformUserNo;
        this.amount = amount;
        this.bizType = bizType;
    }

    public String getTargetUserType() {
        return targetUserType;
    }

    public void setTargetUserType(String targetUserType) {
        this.targetUserType = targetUserType;
    }

    public String getTargetPlatformUserNo() {
        return targetPlatformUserNo;
    }

    public void setTargetPlatformUserNo(String targetPlatformUserNo) {
        this.targetPlatformUserNo = targetPlatformUserNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
}
