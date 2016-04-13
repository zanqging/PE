package com.jinzht.pro.model;

import java.util.List;


/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/11
 * @time 16:20
 */

public class SearchBean {


    /**
     * msg : 加载完毕
     * data : [{"stop":"2015-11-30","start":"2015-11-07","company":"新疆鹏森教学设备制造有限公司","profile":"本单位位于新疆维吾尔自治区乌鲁木齐市沙依巴克区环卫街西七巷28号，主营项目：其他家具销售 。上年度收入2253，主营收入2253，资产总计2579。近年来坚持深化林场改革，改变单一经济，实行多业并举，渐渐步入健康发展的执道。","img":"http://www.jinzht.com:8000/media/project/img/2015/11/1967e279d95e4c3f92f852df3094c70a.jpg","id":7}]
     * code : 2
     */

    private String msg;
    private int code;
    private List<DataEntity> data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * stop : 2015-11-30
         * start : 2015-11-07
         * company : 新疆鹏森教学设备制造有限公司
         * profile : 本单位位于新疆维吾尔自治区乌鲁木齐市沙依巴克区环卫街西七巷28号，主营项目：其他家具销售 。上年度收入2253，主营收入2253，资产总计2579。近年来坚持深化林场改革，改变单一经济，实行多业并举，渐渐步入健康发展的执道。
         * img : http://www.jinzht.com:8000/media/project/img/2015/11/1967e279d95e4c3f92f852df3094c70a.jpg
         * id : 7
         */

        private String stop;
        private String start;
        private String company;
        private String profile;
        private String img;
        private int id;

        public void setStop(String stop) {
            this.stop = stop;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStop() {
            return stop;
        }

        public String getStart() {
            return start;
        }

        public String getCompany() {
            return company;
        }

        public String getProfile() {
            return profile;
        }

        public String getImg() {
            return img;
        }

        public int getId() {
            return id;
        }
    }
}
