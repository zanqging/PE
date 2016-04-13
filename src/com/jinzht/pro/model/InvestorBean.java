package com.jinzht.pro.model;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/7/27
 * @time 19:00
 */

public class InvestorBean {
    private int id;
    private String company;
    private String name;
    private String thinktank_img;
    private String title;

    public InvestorBean(int id, String company, String name, String thinktank_img, String title) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.thinktank_img = thinktank_img;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThinktank_img() {
        return thinktank_img;
    }

    public void setThinktank_img(String thinktank_img) {
        this.thinktank_img = thinktank_img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
