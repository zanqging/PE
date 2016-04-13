package com.jinzht.pro.model;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/7/31
 * @time 15:46
 */

public class AddCompanyBean {

    private String msg ;
    private int status;
    private String company_id;

    public AddCompanyBean(String msg, int status, String company_id) {
        this.msg = msg;
        this.status = status;
        this.company_id = company_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
}
