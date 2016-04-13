package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/11/16.
 */
public class MyUploadBean {


    /**
     * msg :
     * code : 2
     * data : [{"date":"2016-01-12","img":"http://www.jinzht.com/static/app/img/icon.png","abbrevcompany":"金指投的测试","company":"金指投的测试","id":5}]
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

    public class DataEntity {
        /**
         * date : 2016-01-12
         * img : http://www.jinzht.com/static/app/img/icon.png
         * abbrevcompany : 金指投的测试
         * company : 金指投的测试
         * id : 5
         */
        private String date;
        private String img;
        private String abbrevcompany;
        private String company;
        private int id;

        public void setDate(String date) {
            this.date = date;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setAbbrevcompany(String abbrevcompany) {
            this.abbrevcompany = abbrevcompany;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public String getImg() {
            return img;
        }

        public String getAbbrevcompany() {
            return abbrevcompany;
        }

        public String getCompany() {
            return company;
        }

        public int getId() {
            return id;
        }
    }
}
