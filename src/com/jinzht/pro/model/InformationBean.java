package com.jinzht.pro.model;



/**
 * Created by Administrator on 2015/11/5.
 */
public class InformationBean {


    /**
     * code : 0
     * data : {"position":"Android开发哈哈","addr":"陕西省 汉中市","email":"","tel":"18710948468","photo":"http://www.jinzht.com:8000/media/user/photo/2015/11/f25743ce235d40ca88dbdbda83c7db89.jpeg","gender":null,"idno":"","name":"","nickname":"龙脉","company":"龙脉"}
     * msg :
     */

    private int code;
    private DataEntity data;
    private String msg;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static class DataEntity {
        /**
         * position : Android开发哈哈
         * addr : 陕西省 汉中市
         * email :
         * tel : 18710948468
         * photo : http://www.jinzht.com:8000/media/user/photo/2015/11/f25743ce235d40ca88dbdbda83c7db89.jpeg
         * gender : null
         * idno :
         * name :
         * nickname : 龙脉
         * company : 龙脉
         */

        private String position;
        private String addr;
        private String email;
        private String tel;
        private String photo;
        private Object gender;
        private String idno;//身份证号
        private String name;
        private String nickname;
        private String company;
        private String idpic;// 身份证照片
        private boolean is_actived;// 是否是运营人员
        private double virtual_currency;// 虚拟币数量，单位万
        private int uid;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getIdpic() {
            return idpic;
        }

        public void setIdpic(String idpic) {
            this.idpic = idpic;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public void setIdno(String idno) {
            this.idno = idno;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPosition() {
            return position;
        }

        public String getAddr() {
            return addr;
        }

        public String getEmail() {
            return email;
        }

        public String getTel() {
            return tel;
        }

        public String getPhoto() {
            return photo;
        }

        public Object getGender() {
            return gender;
        }

        public String getIdno() {
            return idno;
        }

        public String getName() {
            return name;
        }

        public String getNickname() {
            return nickname;
        }

        public String getCompany() {
            return company;
        }

        public boolean is_actived() {
            return is_actived;
        }

        public void setIs_actived(boolean is_actived) {
            this.is_actived = is_actived;
        }

        public double getVirtual_currency() {
            return virtual_currency;
        }

        public void setVirtual_currency(double virtual_currency) {
            this.virtual_currency = virtual_currency;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "position='" + position + '\'' +
                    ", addr='" + addr + '\'' +
                    ", email='" + email + '\'' +
                    ", tel='" + tel + '\'' +
                    ", photo='" + photo + '\'' +
                    ", gender=" + gender +
                    ", idno='" + idno + '\'' +
                    ", name='" + name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", company='" + company + '\'' +
                    ", idpic='" + idpic + '\'' +
                    ", is_actived=" + is_actived +
                    ", virtual_currency=" + virtual_currency +
                    ", uid=" + uid +
                    '}';
        }
    }
}
