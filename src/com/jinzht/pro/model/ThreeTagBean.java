package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/9/18.
 */
public class ThreeTagBean {

    /**
     * msg :
     * data : [{"key":1,"value":"��ҵ��̬"},{"key":5,"value":"��˾����"},{"key":6,"value":"�����۵�"},{"key":7,"value":"ָ������"}]
     * code : 0
     */

    private String msg;
    private int code;
    private List<DataEntity> data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * key : 1
         * value : ��ҵ��̬
         */

        private int key;
        private String value;

        public void setKey(int key) {
            this.key = key;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }
}
