package com.jinzht.pro.model;


/**
 * Created by Administrator on 2016/1/9.
 */
public class PersonInvestorDetailBean {

    /**
     * msg :
     * data : {"signature":"","investcase":"","investplan":"","profile":""}
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
         * signature :
         * investcase :
         * investplan :
         * profile :
         */

        private String signature;
        private String investcase;
        private String investplan;
        private String profile;

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public void setInvestcase(String investcase) {
            this.investcase = investcase;
        }

        public void setInvestplan(String investplan) {
            this.investplan = investplan;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getSignature() {
            return signature;
        }

        public String getInvestcase() {
            return investcase;
        }

        public String getInvestplan() {
            return investplan;
        }

        public String getProfile() {
            return profile;
        }
    }
}
