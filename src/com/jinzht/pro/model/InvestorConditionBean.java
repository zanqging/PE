package com.jinzht.pro.model;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/8/1
 * @time 17:11
 */

public class InvestorConditionBean {
    private int id;
    private String desc;

    public InvestorConditionBean(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
