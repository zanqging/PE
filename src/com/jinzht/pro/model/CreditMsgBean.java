package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/11/24.
 */
public class CreditMsgBean {

    /**
     * msg :
     * code : 0
     * data : {"company":["��������������⼼���ɷ����޹�˾","��ݹ����������޹�˾","�����޼Ҿ����޹�˾","������ʢ��̩�����Ƽ����޹�˾","�½���ľҩҵ�������ι�˾"]}
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
         * company : ["��������������⼼���ɷ����޹�˾","��ݹ����������޹�˾","�����޼Ҿ����޹�˾","������ʢ��̩�����Ƽ����޹�˾","�½���ľҩҵ�������ι�˾"]
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
