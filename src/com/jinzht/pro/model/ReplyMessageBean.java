package com.jinzht.pro.model;

import java.util.List;


/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/9/1
 * @time 17:19
 */

public class ReplyMessageBean {


    /**
     * msg : 加载完毕
     * code : 2
     * data : [{"id":11,"at_name":"乔元培","pid":6,"auth":true,"date":"2小时前","content":"bucuo","name":"chenshengzhu","photo":"http://www.jinzht.com:8000/media/user/photo/2015/11/2b0ec8452ab24c7a98e89a0824f1fd34.jpeg"}]
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
         * id : 11
         * at_name : 乔元培
         * pid : 6
         * auth : true
         * date : 2小时前
         * content : bucuo
         * name : chenshengzhu
         * photo : http://www.jinzht.com:8000/media/user/photo/2015/11/2b0ec8452ab24c7a98e89a0824f1fd34.jpeg
         */

        private int id;
        private String at_name;
        private int pid;
        private boolean auth;
        private String date;
        private String content;
        private String name;
        private String photo;

        public void setId(int id) {
            this.id = id;
        }

        public void setAt_name(String at_name) {
            this.at_name = at_name;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public void setAuth(boolean auth) {
            this.auth = auth;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getId() {
            return id;
        }

        public String getAt_name() {
            return at_name;
        }

        public int getPid() {
            return pid;
        }

        public boolean getAuth() {
            return auth;
        }

        public String getDate() {
            return date;
        }

        public String getContent() {
            return content;
        }

        public String getName() {
            return name;
        }

        public String getPhoto() {
            return photo;
        }
    }
}
