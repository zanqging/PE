package com.jinzht.pro.model;


/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/9
 * @time 16:05
 */

public class AuthenticationBean {

    /**
     * data : {"token":"FpXt02az8Sp5jvmyeYMOEgTMLppXil2LloPDi6QW:jwOp1mUmH4WHhmWeHl0Cu7iu8L4=:eyJkZWFkbGluZSI6MTQ0Nzc1ODQyNCwiY2FsbGJhY2tCb2R5IjoibmFtZT0kKGZuYW1lKSZoYXNoPSQoZXRhZykiLCJzY29wZSI6ImppbnpodDp3YW50cm9hZHNob3cwZTNlNTEubXA0IiwiY2FsbGJhY2tVcmwiOiJodHRwOi8vd3d3LmppbnpodC5jb206ODAwMC9waG9uZS9jYWxsYmFjay8ifQ=="}
     * msg :
     * code : 0
     */

    private DataEntity data;
    private String msg;
    private int code;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public static class DataEntity {
        /**
         * token : FpXt02az8Sp5jvmyeYMOEgTMLppXil2LloPDi6QW:jwOp1mUmH4WHhmWeHl0Cu7iu8L4=:eyJkZWFkbGluZSI6MTQ0Nzc1ODQyNCwiY2FsbGJhY2tCb2R5IjoibmFtZT0kKGZuYW1lKSZoYXNoPSQoZXRhZykiLCJzY29wZSI6ImppbnpodDp3YW50cm9hZHNob3cwZTNlNTEubXA0IiwiY2FsbGJhY2tVcmwiOiJodHRwOi8vd3d3LmppbnpodC5jb206ODAwMC9waG9uZS9jYWxsYmFjay8ifQ==
         */

        private String token;

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}
