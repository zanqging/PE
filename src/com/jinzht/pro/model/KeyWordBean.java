package com.jinzht.pro.model;

import java.util.List;


/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/11
 * @time 15:24
 */

public class KeyWordBean {

    /**
     * data : {"keyword":["化工","教育","检测","医药","土木","环保","家具","电子","美食"]}
     * msg :
     * code : 0
     */

    private DataEntity data;
    private String msg;
    private int code;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public static class DataEntity {
        /**
         * keyword : ["化工","教育","检测","医药","土木","环保","家具","电子","美食"]
         */

        private List<String> keyword;

        public void setKeyword(List<String> keyword) {
            this.keyword = keyword;
        }

        public List<String> getKeyword() {
            return keyword;
        }
    }
}
