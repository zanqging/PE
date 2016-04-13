package com.jinzht.pro.model;

/**
 * Created by Administrator on 2016/1/13.
 */
public class PreDetailBean {


    /**
     * msg :
     * code : 0
     * data : {"is_collect":false,"business":"","is_like":false,"like":0,"profile":"","model":"","planfinance":"","collect":0}
     */
    private String msg;
    private int code;
    private DataEntity data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public class DataEntity {
        /**
         * is_collect : false
         * business :
         * is_like : false
         * like : 0
         * profile :
         * model :
         * planfinance :
         * collect : 0
         */
        private boolean is_collect;
        private String business;
        private boolean is_like;
        private int like;
        private String profile;
        private String model;
        private String planfinance;
        private int collect;
        private String vcr;

        public String getVcr() {
            return vcr;
        }

        public void setVcr(String vcr) {
            this.vcr = vcr;
        }

        public void setIs_collect(boolean is_collect) {
            this.is_collect = is_collect;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public void setIs_like(boolean is_like) {
            this.is_like = is_like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public void setPlanfinance(String planfinance) {
            this.planfinance = planfinance;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }

        public boolean isIs_collect() {
            return is_collect;
        }

        public String getBusiness() {
            return business;
        }

        public boolean isIs_like() {
            return is_like;
        }

        public int getLike() {
            return like;
        }

        public String getProfile() {
            return profile;
        }

        public String getModel() {
            return model;
        }

        public String getPlanfinance() {
            return planfinance;
        }

        public int getCollect() {
            return collect;
        }
    }
}
