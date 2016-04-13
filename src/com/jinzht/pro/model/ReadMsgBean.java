package com.jinzht.pro.model;


/**
 * Created by Administrator on 2015/9/22.
 */
public class ReadMsgBean {

    /**
     * msg : 系统消息
     * status : 0
     * data : {"count":1}
     */

    private String msg;
    private int code;
    private DataEntity data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.code = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * count : 1
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
