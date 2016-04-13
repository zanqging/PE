package com.jinzht.pro.model;


/**
 * Created by Administrator on 2015/11/30.
 */
public class InvestConfirmBean {

    /**
     * data : {"flag":false}
     * code : 1
     * msg : 金额必须大于50万
     */

    private DataEntity data;
    private int code;
    private String msg;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataEntity getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static class DataEntity {
        /**
         * flag : false
         */

        private boolean flag;

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public boolean getFlag() {
            return flag;
        }
    }
}
