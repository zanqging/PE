package com.jinzht.pro.model;


/**
 * Created by Administrator on 2015/11/17.
 */
public class AuthBean {


    /**
     * msg :
     * code : 0
     * data : {"auth":true}
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
         * auth : true
         */

        private Object auth;

        public Object getAuth() {
            return auth;
        }

        public void setAuth(Object auth) {
            this.auth = auth;
        }
    }
}
