package com.jinzht.pro.model;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * @auther Mr Jobs
 * @date 2015/7/28
 * @time 22:37
 */
public class CompanyStatusBean {
    private int companystatus_id;
    private String status_name;

    public CompanyStatusBean(int companystatus_id, String status_name) {

        this.companystatus_id = companystatus_id;
        this.status_name = status_name;
    }

    public int getCompanystatus_id() {
        return companystatus_id;
    }

    public void setCompanystatus_id(int companystatus_id) {
        this.companystatus_id = companystatus_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
