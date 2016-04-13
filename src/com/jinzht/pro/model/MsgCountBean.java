package com.jinzht.pro.model;


/**
 * Created by Administrator on 2015/11/21.
 *
 * 消息个数
 */
public class MsgCountBean {

    /**
     * code : 0
     * data : {"count":0}
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
         * count : 0
         */

        private int count;

        public void setCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }
    }
}
