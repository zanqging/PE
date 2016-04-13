package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/11/8.
 */
public class FinishFinacingBean {

    /**
     * code : 2
     * msg : �������
     * data : {"data":[{"id":4,"company":"����ŷ���Ż����Ƽ�","date":"2015-11-26T04:29:08Z","planfinance":300,"img":"http://www.jinzht.com:8000/media/project/img/2015/11/700e537b17124043aa64f7e13497ab6d.jpg","invest":0,"investor":0}],"cursor":2}
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
         * id : 4
         * company : ����ŷ���Ż����Ƽ�
         * date : 2015-11-26T04:29:08Z
         * planfinance : 300
         * img : http://www.jinzht.com:8000/media/project/img/2015/11/700e537b17124043aa64f7e13497ab6d.jpg
         * invest : 0
         * investor : 0
         */

        private int id;
        private String company;
        private String date;
        private double planfinance;
        private String img;
        private double invest;
        private String tag;
        private String abbrevcompany;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getAbbrevcompany() {
            return abbrevcompany;
        }

        public void setAbbrevcompany(String abbrevcompany) {
            this.abbrevcompany = abbrevcompany;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setPlanfinance(double planfinance) {
            this.planfinance = planfinance;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setInvest(double invest) {
            this.invest = invest;
        }


        public int getId() {
            return id;
        }

        public String getCompany() {
            return company;
        }

        public String getDate() {
            return date;
        }

        public double getPlanfinance() {
            return planfinance;
        }

        public String getImg() {
            return img;
        }

        public double getInvest() {
            return invest;
        }

    }
}
