package com.jinzht.pro.model;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/8
 * @time 14:28
 */

public class UserBean  {

    /**
     * msg :
     * code : 0
     * data : {"uid":14,"photo":"http://www.jinzht.com:8000/media/user/photo/2015/11/f25743ce235d40ca88dbdbda83c7db89.jpeg","position":"不告诉你","tel":"18710948468","name":"乔元培","idno":"140602198907199031","nickname":"龙脉","gender":true,"addr":"西安","company":"公司"}
     */
    private String msg;
    private int code;
    private DataEntity data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * uid : 14
         * photo : http://www.jinzht.com:8000/media/user/photo/2015/11/f25743ce235d40ca88dbdbda83c7db89.jpeg
         * position : 不告诉你
         * tel : 18710948468
         * name : 乔元培
         * idno : 140602198907199031
         * nickname : 龙脉
         * gender : true
         * addr : 西安
         * company : 公司
         */
        private int uid;
        private String photo;
        private String position;
        private String tel;
        private String name;
        private String idno;
        private String nickname;
        private boolean gender;
        private String addr;
        private String company;

        public void setUid(int uid) {
            this.uid = uid;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setIdno(String idno) {
            this.idno = idno;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setGender(boolean gender) {
            this.gender = gender;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public int getUid() {
            return uid;
        }

        public String getPhoto() {
            return photo;
        }

        public String getPosition() {
            return position;
        }

        public String getTel() {
            return tel;
        }

        public String getName() {
            return name;
        }

        public String getIdno() {
            return idno;
        }

        public String getNickname() {
            return nickname;
        }

        public boolean getGender() {
            return gender;
        }

        public String getAddr() {
            return addr;
        }

        public String getCompany() {
            return company;
        }
    }
}
