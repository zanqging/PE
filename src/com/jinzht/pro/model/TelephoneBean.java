package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/11/23.
 */
public class TelephoneBean {

    /**
     * code : 0
     * msg :
     * data : [{"value":"18681838312","key":"投资资讯电话"},{"value":"18729342354","key":"技术支持电话"},{"value":"kf@jinzht.com","key":"客服企业邮箱"}]
     */

    private int code;
    private String msg;
    private List<DataEntity> data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * value : 18681838312
         * key : 投资资讯电话
         */

        private String value;
        private String key;

        public void setValue(String value) {
            this.value = value;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public String getKey() {
            return key;
        }
    }
}
