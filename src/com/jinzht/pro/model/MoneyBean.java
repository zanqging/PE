package com.jinzht.pro.model;

/**
 * Created by Administrator on 2015/11/9.
 */
public class MoneyBean {

/**
* code : 0
* msg :
* data : {"share2give":3,"minfund":100,"quitway":"�����塢IPO","usage":"�����ʽ�1500��Ԫ\r\n�з�����500��Ԫ\r\n�豸����500��Ԫ\r\n��������500��Ԫ","planfinance":300}
*/

    private  int code ;
    private  String msg ;
    private  DataEntity data ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity{
        private String share2give;
        private int  minfund;
        private String quitway;
        private String usage;
        private int  planfinance;

        public String getShare2give() {
            return share2give;
        }

        public void setShare2give(String share2give) {
            this.share2give = share2give;
        }

        public int getMinfund() {
            return minfund;
        }

        public void setMinfund(int minfund) {
            this.minfund = minfund;
        }

        public String getQuitway() {
            return quitway;
        }

        public void setQuitway(String quitway) {
            this.quitway = quitway;
        }

        public String getUsage() {
            return usage;
        }

        public void setUsage(String usage) {
            this.usage = usage;
        }

        public int getPlanfinance() {
            return planfinance;
        }

        public void setPlanfinance(int planfinance) {
            this.planfinance = planfinance;
        }
    }
}
