package com.jinzht.pro.model;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/8/6
 * @time 16:01
 */

public class CompanyListBean {
    private int id;
    private String company_name;

    public CompanyListBean(int id, String company_name) {
        this.id = id;
        this.company_name = company_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
