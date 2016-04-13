package com.jinzht.pro.model;

/**
 * Created by pc on 2016/3/21.
 */
public class VerifySignBean {
    private int code;
    private DataEntity data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataEntity {
        private String verify;

        public String getVerify() {
            return verify;
        }

        public void setVerify(String verify) {
            this.verify = verify;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "verify='" + verify + '\'' +
                    '}';
        }
    }

}
