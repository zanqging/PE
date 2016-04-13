package com.jinzht.pro.model;


/**
 * Created by Administrator on 2015/11/5.
 */
public class TitleBean {

    /**
     * msg :
     * data : {"cursor":1}
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
         * cursor : 1
         */

        private int cursor;

        public void setCursor(int cursor) {
            this.cursor = cursor;
        }

        public int getCursor() {
            return cursor;
        }
    }
}
