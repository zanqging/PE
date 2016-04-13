package com.jinzht.pro.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by pc on 2016/3/15.
 * ��ֵ���ز���
 */
@XStreamAlias("response")
public class RechargeCallbackBean {
    @XStreamAsAttribute
    private String platformNo;// �̻����
    @XStreamAlias("service")
    private String service;// ���������̶�ֵRECHARGE
    @XStreamAlias("requestNo")
    private String requestNo;// ������ˮ��
    @XStreamAlias("code")
    private String code;// ״̬�룬1�ɹ���0ʧ��
    @XStreamAlias("description")
    private String description;// ����

    public String getPlatformNo() {
        return platformNo;
    }

    public void setPlatformNo(String platformNo) {
        this.platformNo = platformNo;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
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

    @Override
    public String toString() {
        return "RechargeCallbackBean{" +
                "platformNo='" + platformNo + '\'' +
                ", service='" + service + '\'' +
                ", requestNo='" + requestNo + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
