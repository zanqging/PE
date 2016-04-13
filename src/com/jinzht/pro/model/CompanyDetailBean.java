package com.jinzht.pro.model;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/9
 * @time 10:58
 */

public class CompanyDetailBean {
    private String industry_type;
    private String province;
    private String city;
    private String company_status;

    public String getIndustry_type() {
        return industry_type;
    }

    public void setIndustry_type(String industry_type) {
        this.industry_type = industry_type;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany_status() {
        return company_status;
    }

    public void setCompany_status(String company_status) {
        this.company_status = company_status;
    }

    public CompanyDetailBean(String industry_type, String province, String city, String company_status) {
        this.industry_type = industry_type;
        this.province = province;
        this.city = city;
        this.company_status = company_status;
    }
}
