package com.jinzht.pro.model;


/**
 * Created by Administrator on 2015/10/22.
 */
public class CircleLikeBean {

    /**
     * status : 0
     * data : {"is_like":false,"photo":"http://www.jinzht.com:8000/media/user/img/2015/10/deb5b9ee67c54f7eba8543caed36e4aa.jpg","name":"公积金","uid":1}
     * msg : 操作成功
     */

    private int code;
    private DataEntity data;
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
         * is_like : false
         * photo : http://www.jinzht.com:8000/media/user/img/2015/10/deb5b9ee67c54f7eba8543caed36e4aa.jpg
         * name : 公积金
         * uid : 1
         */

        private boolean is_like;
        private String photo;
        private String name;
        private int uid;

        public void setIs_like(boolean is_like) {
            this.is_like = is_like;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public boolean getIs_like() {
            return is_like;
        }

        public String getPhoto() {
            return photo;
        }

        public String getName() {
            return name;
        }

        public int getUid() {
            return uid;
        }
    }
}
