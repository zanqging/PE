package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/10/15.
 */
public class text {

    /**
     * status : 0
     * data : [{"position":["律师"],"pics":["/var/www/html/dev/media/feeling/2015/10/ab8c2f77d56d40e49c30ae2705de9e9b.png","/var/www/html/dev/media/feeling/2015/10/789b451d57a5438ab9d22afe325e8e22.png","/var/www/html/dev/media/feeling/2015/10/1ec698e4a1b6496eb9c05f777db6486a.png","/var/www/html/dev/media/feeling/2015/10/09f9d683c63647a8900d803d1ec7fd7c.png"],"flag":true,"id":8,"city":"新乡市","photo":"http://www.jinzht.com:8000/media/user/img/2015/10/deb5b9ee67c54f7eba8543caed36e4aa.jpg","content":"我不告诉你→_→这是我的测试数据。MrJobs","name":"公积金","datetime":"38分钟前"}]
     * msg : 状态圈
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
         * position : ["律师"]
         * pics : ["/var/www/html/dev/media/feeling/2015/10/ab8c2f77d56d40e49c30ae2705de9e9b.png","/var/www/html/dev/media/feeling/2015/10/789b451d57a5438ab9d22afe325e8e22.png","/var/www/html/dev/media/feeling/2015/10/1ec698e4a1b6496eb9c05f777db6486a.png","/var/www/html/dev/media/feeling/2015/10/09f9d683c63647a8900d803d1ec7fd7c.png"]
         * flag : true
         * id : 8
         * city : 新乡市
         * photo : http://www.jinzht.com:8000/media/user/img/2015/10/deb5b9ee67c54f7eba8543caed36e4aa.jpg
         * content : 我不告诉你→_→这是我的测试数据。MrJobs
         * name : 公积金
         * datetime : 38分钟前
         */

        private boolean flag;
        private int id;
        private String city;
        private String photo;
        private String content;
        private String name;
        private String datetime;
        private List<String> position;
        private List<String> pics;

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public void setPosition(List<String> position) {
            this.position = position;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }

        public boolean getFlag() {
            return flag;
        }

        public int getId() {
            return id;
        }

        public String getCity() {
            return city;
        }

        public String getPhoto() {
            return photo;
        }

        public String getContent() {
            return content;
        }

        public String getName() {
            return name;
        }

        public String getDatetime() {
            return datetime;
        }

        public List<String> getPosition() {
            return position;
        }

        public List<String> getPics() {
            return pics;
        }
    }
}
