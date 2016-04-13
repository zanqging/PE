package com.jinzht.pro.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/28.
 */
public class CirclePublishBean {


    /**
     * data : {"flag":true,"addr":"西安","position":"不告诉你","photo":"http://www.jinzht.com:8000/media/user/photo/2015/11/f25743ce235d40ca88dbdbda83c7db89.jpeg","company":"公司","name":"乔元培","datetime":"1分钟前","uid":14,"remain_comment":0,"content":"国际经济","is_like":false,"id":35,"remain_like":0}
     * code : 0
     * msg : 发表成功
     */

    private DataEntity data;
    private int code;
    private String msg;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataEntity getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static class DataEntity {
        /**
         * flag : true
         * addr : 西安
         * position : 不告诉你
         * photo : http://www.jinzht.com:8000/media/user/photo/2015/11/f25743ce235d40ca88dbdbda83c7db89.jpeg
         * company : 公司
         * name : 乔元培
         * datetime : 1分钟前
         * uid : 14
         * remain_comment : 0
         * content : 国际经济
         * is_like : false
         * id : 35
         * remain_like : 0
         */
        public DataEntity() {
            super();
        }

        public DataEntity(String datetime, int id, String addr, int uid, int remain_comment, boolean is_like, String content, String name, int remain_like,
                          String photo, boolean flag, ArrayList<String> pic, String position) {
            this.datetime = datetime;
            this.id = id;
            this.addr = addr;
            this.uid = uid;
            this.remain_comment = remain_comment;
            this.is_like = is_like;
            this.content = content;
            this.name = name;
            this.remain_like = remain_like;
            this.photo = photo;
            this.flag = flag;
            this.pic = pic;
            this.position = position;
        }
        private boolean flag;
        private String addr;
        private String position;
        private String photo;
        private String company;
        private String name;
        private String datetime;
        private int uid;
        private int remain_comment;
        private String content;
        private boolean is_like;
        private int id;
        private int remain_like;
        private ArrayList<String> pic;

        public ArrayList<String> getPic() {
            return pic;
        }

        public void setPic(ArrayList<String> pic) {
            this.pic = pic;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setRemain_comment(int remain_comment) {
            this.remain_comment = remain_comment;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setIs_like(boolean is_like) {
            this.is_like = is_like;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setRemain_like(int remain_like) {
            this.remain_like = remain_like;
        }

        public boolean getFlag() {
            return flag;
        }

        public String getAddr() {
            return addr;
        }

        public String getPosition() {
            return position;
        }

        public String getPhoto() {
            return photo;
        }

        public String getCompany() {
            return company;
        }

        public String getName() {
            return name;
        }

        public String getDatetime() {
            return datetime;
        }

        public int getUid() {
            return uid;
        }

        public int getRemain_comment() {
            return remain_comment;
        }

        public String getContent() {
            return content;
        }

        public boolean getIs_like() {
            return is_like;
        }

        public int getId() {
            return id;
        }

        public int getRemain_like() {
            return remain_like;
        }
    }
}
