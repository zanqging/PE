package com.jinzht.pro.model;

import java.util.List;


/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/7/30
 * @time 13:01
 */

public class InvestListBean {

    /**
     * msg :
     * code : 0
     * data : [{"amount":180,"name":"��Ԫ��","photo":"http://www.jinzht.com:8000/media/user/photo/2015/11/f25743ce235d40ca88dbdbda83c7db89.jpeg"}]
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
         * amount : 180
         * name : ��Ԫ��
         * photo : http://www.jinzht.com:8000/media/user/photo/2015/11/f25743ce235d40ca88dbdbda83c7db89.jpeg
         */

        private double amount;
        private String name;
        private String photo;
        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public double getAmount() {
            return amount;
        }

        public String getName() {
            return name;
        }

        public String getPhoto() {
            return photo;
        }
    }
}
