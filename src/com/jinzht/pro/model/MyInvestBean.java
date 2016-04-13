package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/11/16.
 */
public class MyInvestBean {


    /**
     * msg : �������
     * data : [{"stop":"2015-11-30","company":"�½���ɭ��ѧ�豸�������޹�˾","img":"http://www.jinzht.com:8000/media/project/img/2015/11/1967e279d95e4c3f92f852df3094c70a.jpg","id":7,"start":"2015-11-07"}]
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
         * company : �½���ɭ��ѧ�豸�������޹�˾
         * img : http://www.jinzht.com:8000/media/project/img/2015/11/1967e279d95e4c3f92f852df3094c70a.jpg
         * id : 7
         * start : 2015-11-07
         */

        private String stop;
        private String company;
        private String img;
        private int id;
        private String start;
        private String abbrevcompany;

        public String getAbbrevcompany() {
            return abbrevcompany;
        }

        public void setAbbrevcompany(String abbrevcompany) {
            this.abbrevcompany = abbrevcompany;
        }

        public void setStop(String stop) {
            this.stop = stop;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getStop() {
            return stop;
        }

        public String getCompany() {
            return company;
        }

        public String getImg() {
            return img;
        }

        public int getId() {
            return id;
        }

        public String getStart() {
            return start;
        }
    }
}
