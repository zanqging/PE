package com.jinzht.pro.model;


/**
 * Created by Administrator on 2015/12/31.
 */
public class OriInvestorBean {

    /**
     * code : 0
     * data : {"profile":"�Ϻ��ںϴ�ҵͶ�ʹ������޹�˾λ��ֱϽ�� �Ϻ� �ֶ�������ɽ·450��������ʴ���18¥B�ң���˾�����绰��021-50815116�� �Ϻ��ںϴ�ҵͶ�ʹ������޹�˾Ը��������ͬ��Я�ֺ�����ı��ͬ��չ������Ϊ���Ͽͻ��ṩ������Ĳ�Ʒ�ͷ��񡣹�˾�����Ϻ������̺ʹ����̽����˳����ȶ��ĺ�����ϵ��Ʒ����ȫ���۸������ҵʵ���ۺ������á��غ�ͬ����֤��Ʒ�������Զ�Ʒ�־�Ӫ��ɫ�ͱ���������ԭ��Ӯ���˹��ͻ�������","fundsize":""}
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
         * profile : �Ϻ��ںϴ�ҵͶ�ʹ������޹�˾λ��ֱϽ�� �Ϻ� �ֶ�������ɽ·450��������ʴ���18¥B�ң���˾�����绰��021-50815116�� �Ϻ��ںϴ�ҵͶ�ʹ������޹�˾Ը��������ͬ��Я�ֺ�����ı��ͬ��չ������Ϊ���Ͽͻ��ṩ������Ĳ�Ʒ�ͷ��񡣹�˾�����Ϻ������̺ʹ����̽����˳����ȶ��ĺ�����ϵ��Ʒ����ȫ���۸������ҵʵ���ۺ������á��غ�ͬ����֤��Ʒ�������Զ�Ʒ�־�Ӫ��ɫ�ͱ���������ԭ��Ӯ���˹��ͻ�������
         * fundsize :
         */

        private String profile;
        private String fundsize;

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public void setFundsize(String fundsize) {
            this.fundsize = fundsize;
        }

        public String getProfile() {
            return profile;
        }

        public String getFundsize() {
            return fundsize;
        }
    }
}
