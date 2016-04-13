package com.jinzht.pro.model;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/7/4
 * @time 16:29
 */

public class LoginBean {


    /**
     * msg : 登录成功
     * data : {"info":true,"auth":true}
     * code : 0
     */

    private String msg;
    private DataEntity data;
    private int code;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public static class DataEntity {
        /**
         * info : true
         * auth : true
         */

        private boolean info;
        private Object auth;

        public void setInfo(boolean info) {
            this.info = info;
        }



        public boolean getInfo() {
            return info;
        }

        public Object getAuth() {
            return auth;
        }

        public void setAuth(Object auth) {
            this.auth = auth;
        }
    }
}
