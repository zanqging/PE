package com.jinzht.pro.model;

import java.util.List;



/**
 * Created by Administrator on 2015/11/15.
 */
public class InvestorOriBean {


    /**
     * msg :
     * code : 0
     * data : [{"logo":"http://www.jinzht.com:8000/media/institute/orgcode/2015/11/c55510162c7843e189099601d562208e.png","addr":"","abbrevname":"�ںϴ�Ͷ","id":7,"name":"�ںϴ�Ͷ"},{"logo":"http://www.jinzht.com:8000/media/institute/orgcode/2015/11/b19dd9f8e50b4d998e6dc794dfd70924.png","addr":"","abbrevname":"��������","id":6,"name":"��������"},{"logo":"http://www.jinzht.com:8000/media/institute/orgcode/2015/11/e357cb7f83b84f349bbad28adde5d83a.png","addr":"","abbrevname":"����֤ȯ","id":5,"name":"����֤ȯ"},{"logo":"http://www.jinzht.com:8000/media/institute/orgcode/2015/11/dafecda009d24cb8b46f86e087b565dd.png","addr":"","abbrevname":"ͬ������","id":4,"name":"ͬ������"}]
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
         * logo : http://www.jinzht.com:8000/media/institute/orgcode/2015/11/c55510162c7843e189099601d562208e.png
         * addr :
         * abbrevname : �ںϴ�Ͷ
         * id : 7
         * name : �ںϴ�Ͷ
         */

        private String logo;
        private String addr;
        private String abbrevname;
        private int id;
        private String name;

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public void setAbbrevname(String abbrevname) {
            this.abbrevname = abbrevname;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public String getAddr() {
            return addr;
        }

        public String getAbbrevname() {
            return abbrevname;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
