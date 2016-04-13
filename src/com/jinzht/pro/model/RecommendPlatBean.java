package com.jinzht.pro.model;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/7/17
 * @time 10:56
 */

public class RecommendPlatBean {
    private String industry_type;
    private String project_summary;//一句话介绍
    private int likes__sum;//love
    private int collects__sum;//收藏
    private String company_name;//项目名称
    private String company_location;//地区
    private String stage;//是否路演完成
    private String project_img;
    private String recommend_reason;//推荐理由
    private int id;
    private int vote_num;//投票
    private String city;

    public RecommendPlatBean( int id,String industry_type, String project_summary, int likes__sum, int collects__sum, String company_name, String company_location, String stage,String project_img,String recommend_reason,int vote_num) {
        this.id = id;
        this.industry_type = industry_type;
        this.project_summary = project_summary;
        this.likes__sum = likes__sum;
        this.collects__sum = collects__sum;
        this.company_name = company_name;
        this.company_location = company_location;
        this.stage = stage;
        this.project_img = project_img;
        this.recommend_reason = recommend_reason;
        this.vote_num = vote_num;
    }

    public RecommendPlatBean(String industry_type, String project_summary, int likes__sum, int collects__sum, String company_name, String company_location, String project_img,int id,int vote_num) {
        this.industry_type = industry_type;
        this.project_summary = project_summary;
        this.likes__sum = likes__sum;
        this.collects__sum = collects__sum;
        this.company_name = company_name;
        this.company_location = company_location;
        this.project_img = project_img;
        this.id = id;
        this.vote_num = vote_num;
    }

    public RecommendPlatBean(int id, String project_summary, int likes__sum, int collects__sum, String company_name, String industry_type, String project_img, int vote_num, String city,String stage) {
        this.id = id;
        this.project_summary = project_summary;
        this.likes__sum = likes__sum;
        this.collects__sum = collects__sum;
        this.company_name = company_name;
        this.industry_type = industry_type;
        this.project_img = project_img;
        this.vote_num = vote_num;
        this.city = city;
        this.stage = stage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getVote_num() {
        return vote_num;
    }

    public void setVote_num(int vote_num) {
        this.vote_num = vote_num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndustry_type() {
        return industry_type;
    }

    public void setIndustry_type(String industry_type) {
        this.industry_type = industry_type;
    }

    public String getProject_summary() {
        return project_summary;
    }

    public void setProject_summary(String project_summary) {
        this.project_summary = project_summary;
    }

    public int getLikes__sum() {
        return likes__sum;
    }

    public void setLikes__sum(int likes__sum) {
        this.likes__sum = likes__sum;
    }

    public int getCollects__sum() {
        return collects__sum;
    }

    public void setCollects__sum(int collects__sum) {
        this.collects__sum = collects__sum;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_location() {
        return company_location;
    }

    public void setCompany_location(String company_location) {
        this.company_location = company_location;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }






























































































































    public String getProject_img() {
        return project_img;
    }

    public void setProject_img(String project_img) {
        this.project_img = project_img;
    }

    public String getRecommend_reason() {
        return recommend_reason;
    }

    public void setRecommend_reason(String recommend_reason) {
        this.recommend_reason = recommend_reason;
    }
}
