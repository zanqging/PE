package com.jinzht.pro.model;

import java.util.List;

/**
 * Created by Administrator on 2015/10/17.
 */
public class CircleCommentDetailBean {

    /**
     * status : 0
     * data : [{"id":3,"uid":1,"name":"公积金","photo":"http://www.jinzht.com:8000/media/user/img/2015/10/deb5b9ee67c54f7eba8543caed36e4aa.jpg","content":"同学昨晚","flag":true,"label_suffix":":"},{"id":2,"uid":1,"name":"公积金","photo":"http://www.jinzht.com:8000/media/user/img/2015/10/deb5b9ee67c54f7eba8543caed36e4aa.jpg","content":"我刚刚胡孤独","flag":true,"label_suffix":":"}]
     * msg : 评论列表
     */

    private int status;
    private String msg;
    private List<DataEntity> data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * id : 3
         * uid : 1
         * name : 公积金
         * photo : http://www.jinzht.com:8000/media/user/img/2015/10/deb5b9ee67c54f7eba8543caed36e4aa.jpg
         * content : 同学昨晚
         * flag : true
         * label_suffix : :
         */

        private int id;
        private int uid;
        private String name;
        private String photo;
        private String content;
        private boolean flag;
        private String label_suffix;

        public void setId(int id) {
            this.id = id;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public void setLabel_suffix(String label_suffix) {
            this.label_suffix = label_suffix;
        }

        public int getId() {
            return id;
        }

        public int getUid() {
            return uid;
        }

        public String getName() {
            return name;
        }

        public String getPhoto() {
            return photo;
        }

        public String getContent() {
            return content;
        }

        public boolean getFlag() {
            return flag;
        }

        public String getLabel_suffix() {
            return label_suffix;
        }
    }
}
