package com.jinzht.pro.model;


import java.util.List;

/**
 * Created by Administrator on 2015/10/16.
 */
public class CirclePersonBean {

    /**
     * msg : 用户信息
     * data : {"city":"","user_img":"http://www.jinzht.com:8000/media/default/coremember.png","telephone":"15339121717","real_name":"宋文腾","gender":null,"province":""}
     * status : 0
     */

    private String msg;
    private DataEntity data;
    private int status;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public static class DataEntity {
        /**
         * city :
         * user_img : http://www.jinzht.com:8000/media/default/coremember.png
         * telephone : 15339121717
         * real_name : 宋文腾
         * gender : null
         * province :
         */

        private String city;
        private String user_img;
        private String telephone;
        private String real_name;
        private Object gender;
        private String province;
        private List<String> position_type;

        public List<String> getPosition_type() {
            return position_type;
        }

        public void setPosition_type(List<String> position_type) {
            this.position_type = position_type;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public String getUser_img() {
            return user_img;
        }

        public String getTelephone() {
            return telephone;
        }

        public String getReal_name() {
            return real_name;
        }

        public Object getGender() {
            return gender;
        }

        public String getProvince() {
            return province;
        }
    }
}
