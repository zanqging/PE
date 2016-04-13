package com.jinzht.pro.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by pc on 2016/3/16..
 * �ױ��˻���ѯ��Ϣ
 */
@XStreamAlias("response")
public class YibaoUserInfoBean {
    @XStreamAsAttribute
    private String platformNo;// �̻����
    @XStreamAlias("code")
    private String code;// ״̬�룬1�ɹ���0ʧ�ܣ�101�û�������
    @XStreamAlias("description")
    private String description;// ������Ϣ
    @XStreamAlias("memberType")
    private String memberType;// ��Ա���ͣ�PERSONAL���˻�Ա��ENTERPRISE��ҵ��Ա��GUARANTEE_CORP��������˾
    @XStreamAlias("activeStatus")
    private String activeStatus;// ��Ա����״̬��ACTIVATED���Ѽ��DEACTIVATED��δ����
    @XStreamAlias("balance")
    private String balance;// �˻����
    @XStreamAlias("availableAmount")
    private String availableAmount;// �������
    @XStreamAlias("freezeAmount")
    private String freezeAmount;// ������
    @XStreamAlias("cardNo")
    private String cardNo;// �󶨵Ŀ��ţ�û�����ʾû�а�
    @XStreamAlias("cardStatus")
    private String cardStatus;// ��״̬��VERIFIED
    @XStreamAlias("bank")
    private String bank;// ���д���
    @XStreamAlias("autoTender")
    private String autoTender;// �Ƿ�����Ȩ�Զ�Ͷ��,true ����false
    @XStreamAlias("paySwift")
    private String paySwift;// ��ʾ�û��Ƿ��ѿ�ͨ���֧����NORMAL ��ʾδ������UPGRADE ��ʾ������
    @XStreamAlias("bindMobileNo")
    private String bindMobileNo;// ƽ̨��Ա�ֻ���

    public YibaoUserInfoBean() {
    }

    public YibaoUserInfoBean(String platformNo, String code, String description, String memberType, String activeStatus, String balance, String availableAmount, String freezeAmount, String cardNo, String cardStatus, String bank, String autoTender, String paySwift, String bindMobileNo) {
        this.platformNo = platformNo;
        this.code = code;
        this.description = description;
        this.memberType = memberType;
        this.activeStatus = activeStatus;
        this.balance = balance;
        this.availableAmount = availableAmount;
        this.freezeAmount = freezeAmount;
        this.cardNo = cardNo;
        this.cardStatus = cardStatus;
        this.bank = bank;
        this.autoTender = autoTender;
        this.paySwift = paySwift;
        this.bindMobileNo = bindMobileNo;
    }

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(String availableAmount) {
        this.availableAmount = availableAmount;
    }

    public String getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(String freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAutoTender() {
        return autoTender;
    }

    public void setAutoTender(String autoTender) {
        this.autoTender = autoTender;
    }

    public String getPaySwift() {
        return paySwift;
    }

    public void setPaySwift(String paySwift) {
        this.paySwift = paySwift;
    }

    public String getBindMobileNo() {
        return bindMobileNo;
    }

    public void setBindMobileNo(String bindMobileNo) {
        this.bindMobileNo = bindMobileNo;
    }

    @Override
    public String toString() {
        return "YibaoUserInfoBean{" +
                "platformNo='" + platformNo + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", memberType='" + memberType + '\'' +
                ", activeStatus='" + activeStatus + '\'' +
                ", balance='" + balance + '\'' +
                ", availableAmount='" + availableAmount + '\'' +
                ", freezeAmount='" + freezeAmount + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", cardStatus='" + cardStatus + '\'' +
                ", bank='" + bank + '\'' +
                ", autoTender='" + autoTender + '\'' +
                ", paySwift='" + paySwift + '\'' +
                ", bindMobileNo='" + bindMobileNo + '\'' +
                '}';
    }
}
