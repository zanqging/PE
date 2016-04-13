package com.jinzht.pro.model;

/**
 * Created by pc on 2016/3/19.
 * 是否已投资过
 */
public class IsTenderedBean {

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
        private boolean isInvest;

        public boolean isInvest() {
            return isInvest;
        }

        public void setIsInvest(boolean isInvest) {
            this.isInvest = isInvest;
        }
    }
}
