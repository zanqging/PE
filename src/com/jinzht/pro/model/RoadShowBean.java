package com.jinzht.pro.model;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/7/13
 * @time 15:47
 */

public class RoadShowBean {
    private int id;//详情页要用
    private String stage;//true为路演完成
    private String company_img;//图片
    private String roadshow_date;//路演时间
    private String company_name;//公司名称
    private int color;
    private String thumbnail;
    private int like_sum;
    private int vote_sum;
    private int collect_sum;
    private String name;

    public RoadShowBean(int id, String stage, String company_img, String roadshow_date, String company_name,int color,
                        int like_sum,int vote_sum,int collect_sum,String name) {
        this.id = id;
        this.stage = stage;
        this.company_img = company_img;
        this.roadshow_date = roadshow_date;
        this.company_name = company_name;
        this.color = color;
        this.like_sum = like_sum;
        this.vote_sum = vote_sum;
        this.collect_sum = collect_sum;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLike_sum() {
        return like_sum;
    }

    public void setLike_sum(int like_sum) {
        this.like_sum = like_sum;
    }

    public int getVote_sum() {
        return vote_sum;
    }

    public void setVote_sum(int vote_sum) {
        this.vote_sum = vote_sum;
    }

    public int getCollect_sum() {
        return collect_sum;
    }

    public void setCollect_sum(int collect_sum) {
        this.collect_sum = collect_sum;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getCompany_img() {
        return company_img;
    }

    public void setCompany_img(String company_img) {
        this.company_img = company_img;
    }

    public String getRoadshow_date() {
        return roadshow_date;
    }

    public void setRoadshow_date(String roadshow_date) {
        this.roadshow_date = roadshow_date;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
