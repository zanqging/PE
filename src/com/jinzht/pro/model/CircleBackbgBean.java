package com.jinzht.pro.model;


/**
 * Created by Administrator on 2015/10/22.
 */
public class CircleBackbgBean {
    /**
     * data : {"photo":"http://www.jinzht.com:8000/media/user/img/2015/09/a5bef80b1f5646dcad29512ae219db23.jpg",
     * "background":"http://www.jinzht.com:8000/media/user/background/2015/10//9d331a3b4238480d91e7a24b2ec2bae9"}
     * status : 0
     * msg :
     */

    private DataEntity data;
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static class DataEntity {
        /**
         * photo : http://www.jinzht.com:8000/media/user/img/2015/09/a5bef80b1f5646dcad29512ae219db23.jpg
         * background : http://www.jinzht.com:8000/media/user/background/2015/10//9d331a3b4238480d91e7a24b2ec2bae9
         */

        private String photo;
        private String bg;

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getPhoto() {
            return photo;
        }

        public String getBg() {
            return bg;
        }

        public void setBg(String bg) {
            this.bg = bg;
        }
    }




}
