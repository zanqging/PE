package com.jinzht.pro.model;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/7/29
 * @time 18:07
 */

public class FancePlanBean {

    private String finance_pattern;
    private String quit_way;
    private int plan_finance;
    private String fund_purpose;
    private double share2give;

    public FancePlanBean(String finance_pattern, String quit_way, int plan_finance, String fund_purpose, double share2give) {
        this.finance_pattern = finance_pattern;
        this.quit_way = quit_way;
        this.plan_finance = plan_finance;
        this.fund_purpose = fund_purpose;
        this.share2give = share2give;
    }

    public String getFinance_pattern() {
        return finance_pattern;
    }

    public void setFinance_pattern(String finance_pattern) {
        this.finance_pattern = finance_pattern;
    }

    public String getQuit_way() {
        return quit_way;
    }

    public void setQuit_way(String quit_way) {
        this.quit_way = quit_way;
    }

    public int getPlan_finance() {
        return plan_finance;
    }

    public void setPlan_finance(int plan_finance) {
        this.plan_finance = plan_finance;
    }

    public String getFund_purpose() {
        return fund_purpose;
    }

    public void setFund_purpose(String fund_purpose) {
        this.fund_purpose = fund_purpose;
    }

    public double getShare2give() {
        return share2give;
    }

    public void setShare2give(double share2give) {
        this.share2give = share2give;
    }
}
