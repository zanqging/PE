package com.jinzht.pro.model;

import java.util.List;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/8/31
 * @time 12:44
 */

public class InvestorProgressBean {

    /**
     * msg : �ҵ�Ͷ����֤һ����
     * status : 0
     */

    private String msg;
    private int status;
    private List<DataEntity> data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * audit_date : 2015-08-21 20:50:30
         * company : ��������������⼼���ɷ����޹�˾
         * is_qualified : null
         * id : 6
         * reject_reason : �ȴ����, Ԥ�����ύ��2���ڴ���
         * apply_for_certificate_datetime : 2015-08-21 20:50:28
         * certificate_datetime : 2015-08-21 20:50:28
         */

        private String audit_date;
        private String company;
        private Object is_qualified;
        private int id;
        private String reject_reason;
        private String apply_for_certificate_datetime;
        private String certificate_datetime;

        public void setAudit_date(String audit_date) {
            this.audit_date = audit_date;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setIs_qualified(Object is_qualified) {
            this.is_qualified = is_qualified;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setReject_reason(String reject_reason) {
            this.reject_reason = reject_reason;
        }

        public void setApply_for_certificate_datetime(String apply_for_certificate_datetime) {
            this.apply_for_certificate_datetime = apply_for_certificate_datetime;
        }

        public void setCertificate_datetime(String certificate_datetime) {
            this.certificate_datetime = certificate_datetime;
        }

        public String getAudit_date() {
            return audit_date;
        }

        public String getCompany() {
            return company;
        }

        public Object getIs_qualified() {
            return is_qualified;
        }

        public int getId() {
            return id;
        }

        public String getReject_reason() {
            return reject_reason;
        }

        public String getApply_for_certificate_datetime() {
            return apply_for_certificate_datetime;
        }

        public String getCertificate_datetime() {
            return certificate_datetime;
        }
    }
}
