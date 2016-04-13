package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/10/16.
 */
public class CircleSomeOneLikeDeailBean {

    /**
     * msg : 状态点赞情况
     * status : 0
     * data : [{"photo":"http://www.jinzht.com:8000/media/default/coremember.png","uid":492,"name":"王必成"},{"photo":"http://www.jinzht.com:8000/media/default/coremember.png","uid":483,"name":"范建平"},{"photo":"http://www.jinzht.com:8000/media/user/img/2015/10/deb5b9ee67c54f7eba8543caed36e4aa.jpg","uid":1,"name":"公积金"}]
     */

    private String msg;
    private int status;
    private List<DataEntity> data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * photo : http://www.jinzht.com:8000/media/default/coremember.png
         * uid : 492
         * name : 王必成
         */

        private String photo;
        private int uid;
        private String name;

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public int getUid() {
            return uid;
        }

        public String getName() {
            return name;
        }
    }
}
