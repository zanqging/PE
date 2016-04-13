package com.jinzht.pro.adapter;

import java.util.List;


/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/28
 * @time 14:49
 */

public class InteractBean {


    /**
     * msg : 加载完毕
     * data : [{"name":"乔元培","pid":6,"date":"40分钟前","id":6,"auth":false,"at_name":"chenshengzhu","photo":"http://www.jinzht.com:8000/media/user/photo/2015/11/f25743ce235d40ca88dbdbda83c7db89.jpeg","content":"你好啊"},{"name":"chenshengzhu","pid":6,"id":5,"auth":true,"date":"50分钟前","photo":"http://www.jinzht.com:8000/media/user/photo/2015/11/2b0ec8452ab24c7a98e89a0824f1fd34.jpeg","content":"dsanfmsabngnmfbdsgmnbdfnsm"}]
     * code : 0
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
         * name : 乔元培
         * pid : 6
         * date : 40分钟前
         * id : 6
         * auth : false
         * at_name : chenshengzhu
         * photo : http://www.jinzht.com:8000/media/user/photo/2015/11/f25743ce235d40ca88dbdbda83c7db89.jpeg
         * content : 你好啊
         */

        private String name;
        private int pid;
        private String date;
        private int id;
        private boolean auth;
        private String at_name;
        private String photo;
        private String content;

        public void setName(String name) {
            this.name = name;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setAuth(boolean auth) {
            this.auth = auth;
        }

        public void setAt_name(String at_name) {
            this.at_name = at_name;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public int getPid() {
            return pid;
        }

        public String getDate() {
            return date;
        }

        public int getId() {
            return id;
        }

        public boolean getAuth() {
            return auth;
        }

        public String getAt_name() {
            return at_name;
        }

        public String getPhoto() {
            return photo;
        }

        public String getContent() {
            return content;
        }
    }
}
