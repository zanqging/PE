package com.jinzht.pro.model;


/**
 * Created by Administrator on 2015/10/21.
 */
public class CommentBean {

    /**
     * msg : 回复成功
     * status : 0
     * data : {"name":"杨林东","id":388,"uid":3,"flag":true,"label_suffix":":","photo":"http://www.jinzht.com:8000/media/user/img/2015/09/a5bef80b1f5646dcad29512ae219db23.jpg"}
     */

    private DataEntity data;
    private int code;
    private String msg;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataEntity {
        /**
         * name : 杨林东
         * id : 388
         * uid : 3
         * flag : true
         * label_suffix : :
         * photo : http://www.jinzht.com:8000/media/user/img/2015/09/a5bef80b1f5646dcad29512ae219db23.jpg
         */

        private String name;
        private int id;
        private int uid;
        private boolean flag;
        private String label_suffix;
        private String photo;
        private String content;
        private int at_uid;
        private String at_label;
        private String at_name;

        public int getAt_uid() {
            return at_uid;
        }

        public void setAt_uid(int at_uid) {
            this.at_uid = at_uid;
        }

        public String getAt_label() {
            return at_label;
        }

        public void setAt_label(String at_label) {
            this.at_label = at_label;
        }

        public String getAt_name() {
            return at_name;
        }

        public void setAt_name(String at_name) {
            this.at_name = at_name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isFlag() {
            return flag;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public void setLabel_suffix(String label_suffix) {
            this.label_suffix = label_suffix;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public int getUid() {
            return uid;
        }

        public boolean getFlag() {
            return flag;
        }

        public String getLabel_suffix() {
            return label_suffix;
        }

        public String getPhoto() {
            return photo;
        }
    }
}
