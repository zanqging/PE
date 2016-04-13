package com.jinzht.pro.model;


/**
 * Created by Administrator on 2015/12/31.
 */
public class PersonInvestorBean {

    /**
     * code : 0
     * data : {"profile":"","investfield":"","investscale":""}
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
         * profile :
         * investfield :
         * investscale :
         */

        private String profile;
        private String investfield;
        private String investscale;

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public void setInvestfield(String investfield) {
            this.investfield = investfield;
        }

        public void setInvestscale(String investscale) {
            this.investscale = investscale;
        }

        public String getProfile() {
            return profile;
        }

        public String getInvestfield() {
            return investfield;
        }

        public String getInvestscale() {
            return investscale;
        }
    }
}
