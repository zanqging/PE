package com.jinzht.pro.model;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/9/1
 * @time 10:34
 */

public class WaitFinacingBean {

    /**
     * status : -1
     * msg : 最后一页
     */

    private int status;
    private String msg;
    private List<DataEntity> data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * color : 13935141
         * vote_sum : 234
         * company_name : 西塞罗家居有限公司
         * stage : 融资进行
         * like_sum : 997
         * province : 上海
         * roadshow_start_datetime : 2015-08-29
         * city : 外滩
         * id : 6
         * project_summary : 西塞罗家居
         * collect_sum : 346
         * industry_type : ["房产建筑"]
         * project_img : http://www.jinzht.com:8000/media/project/img/2015/08/b0f9f21cacbd454c8c4a3b220c9f6899.png
         * thumbnail : http://www.jinzht.com:8000/media/project/thumbnail/2015/08/c2c83e736add4bbf8ccf3b286dd74a5b.jpg
         */

        private int color;
        private int vote_sum;
        private String company_name;
        private String stage;
        private int like_sum;
        private String province;
        private String roadshow_start_datetime;
        private String city;
        private int id;
        private String project_summary;
        private int collect_sum;
        private String thumbnail;
        private String invest_amount_sum;
        private List<String> industry_type;
        private String reason;
        public void setColor(int color) {
            this.color = color;
        }

        public void setVote_sum(int vote_sum) {
            this.vote_sum = vote_sum;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public void setLike_sum(int like_sum) {
            this.like_sum = like_sum;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setRoadshow_start_datetime(String roadshow_start_datetime) {
            this.roadshow_start_datetime = roadshow_start_datetime;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getInvest_amount_sum() {
            return invest_amount_sum;
        }

        public void setInvest_amount_sum(String invest_amount_sum) {
            this.invest_amount_sum = invest_amount_sum;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setProject_summary(String project_summary) {
            this.project_summary = project_summary;
        }

        public void setCollect_sum(int collect_sum) {
            this.collect_sum = collect_sum;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setIndustry_type(List<String> industry_type) {
            this.industry_type = industry_type;
        }

        public int getColor() {
            return color;
        }

        public int getVote_sum() {
            return vote_sum;
        }

        public String getCompany_name() {
            return company_name;
        }

        public String getStage() {
            return stage;
        }

        public int getLike_sum() {
            return like_sum;
        }

        public String getProvince() {
            return province;
        }

        public String getRoadshow_start_datetime() {
            return roadshow_start_datetime;
        }

        public String getCity() {
            return city;
        }

        public int getId() {
            return id;
        }

        public String getProject_summary() {
            return project_summary;
        }

        public int getCollect_sum() {
            return collect_sum;
        }


        public String getThumbnail() {
            return thumbnail;
        }

        public List<String> getIndustry_type() {
            return industry_type;
        }
    }
}
