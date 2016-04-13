package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/11/24.
 */
public class CreditMsgBean {

    /**
     * msg :
     * code : 0
     * data : {"company":["西安国联质量检测技术股份有限公司","泽惠果蔬配送有限公司","西塞罗家居有限公司","北京华盛坤泰环保科技有限公司","新疆神木药业有限责任公司"]}
     */

    private String msg;
    private int code;
    private DataEntity data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * company : ["西安国联质量检测技术股份有限公司","泽惠果蔬配送有限公司","西塞罗家居有限公司","北京华盛坤泰环保科技有限公司","新疆神木药业有限责任公司"]
         */

        private List<String> company;

        public void setCompany(List<String> company) {
            this.company = company;
        }

        public List<String> getCompany() {
            return company;
        }
    }
}
