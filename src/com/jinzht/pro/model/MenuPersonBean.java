package com.jinzht.pro.model;


/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/15
 * @time 16:38
 */

public class MenuPersonBean {


    /**
     * data : {"nickname":"匿名","photo":"http://www.jinzht.com:8000/media/default/coremember.png"}
     * msg :
     * code : 0
     */

    private DataEntity data;
    private String msg;
    private int code;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public static class DataEntity {
        /**
         * nickname : 匿名
         * photo : http://www.jinzht.com:8000/media/default/coremember.png
         */

        private String nickname;
        private String photo;

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getNickname() {
            return nickname;
        }

        public String getPhoto() {
            return photo;
        }
    }
}
