package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/11/10.
 */
public class InvestBean {

    /**
     * data : [{"name":"chenshengzhu","company":"haha","date":"2015-11-03","position":"haha","id":16}]
     * code : 2
     * msg : º”‘ÿÕÍ±œ
     */

    private int code;
    private String msg;
    private List<DataEntity> data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * name : chenshengzhu
         * company : haha
         * date : 2015-11-03
         * position : haha
         * id : 16
         */

        private String name;
        private String company;
        private String date;
        private String position;
        private int id;
        private String photo;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getCompany() {
            return company;
        }

        public String getDate() {
            return date;
        }

        public String getPosition() {
            return position;
        }

        public int getId() {
            return id;
        }
    }
}
