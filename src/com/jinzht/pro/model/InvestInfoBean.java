package com.jinzht.pro.model;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/16
 * @time 10:55
 */

public class InvestInfoBean {
    private int investor_type;
    private String industry;
    private String fund_size_range;
    private String company;
//    一下乃自然人
    private String province;
    private String city;
    private String telephone;
    private String position;
    private String real_name;
    /**机构*/
    public InvestInfoBean(int investor_type, String industry, String fund_size_range, String company) {
        this.investor_type = investor_type;
        this.industry = industry;
        this.fund_size_range = fund_size_range;
        this.company = company;
    }
    /**自然人*/
    public InvestInfoBean(int investor_type, String company, String province, String city, String telephone, String position, String real_name) {
        this.investor_type = investor_type;
        this.company = company;
        this.province = province;
        this.city = city;
        this.telephone = telephone;
        this.position = position;
        this.real_name = real_name;
    }

    public int getInvestor_type() {
        return investor_type;
    }

    public void setInvestor_type(int investor_type) {
        this.investor_type = investor_type;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getFund_size_range() {
        return fund_size_range;
    }

    public void setFund_size_range(String fund_size_range) {
        this.fund_size_range = fund_size_range;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }
}
