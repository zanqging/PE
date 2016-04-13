package com.jinzht.pro.model;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/27
 * @time 16:52
 */

public class RoadShowProgressBean {

    /**
     * msg : 我的来现场一览表
     * data :{"handle_datetime":"","reason":"等待审核, 预计在提交的2天内处理","project":"港青旅","company":"陕西港青旅国际旅行社有限公司","valid":null,"create_datetime":"2015-08-20 16:15:27","audit_datetime":"2015-08-20 16:15:29","id":9},{"handle_datetime":"","reason":"等待审核, 预计在提交的2天内处理","project":"国联质检","company":"西安国联质量检测技术股份有限公司","valid":null,"create_datetime":"2015-08-20 09:50:39","audit_datetime":"2015-08-20 09:50:41","id":1}]
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
         * handle_datetime :
         * reason : 等待审核, 预计在提交的2天内处理
         * project : 泽惠果蔬
         * company : 泽惠果蔬配送有限公司
         * valid : null
         * create_datetime : 2015-08-20 17:03:53
         * audit_datetime : 2015-08-20 17:03:55
         * id : 10
         */
        private String handle_datetime;
        private String reason;
        private String project;
        private String company;
        private Object valid;
        private String create_datetime;
        private String audit_datetime;
        private int id;

        public void setHandle_datetime(String handle_datetime) {
            this.handle_datetime = handle_datetime;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setValid(Object valid) {
            this.valid = valid;
        }

        public void setCreate_datetime(String create_datetime) {
            this.create_datetime = create_datetime;
        }

        public void setAudit_datetime(String audit_datetime) {
            this.audit_datetime = audit_datetime;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHandle_datetime() {
            return handle_datetime;
        }

        public String getReason() {
            return reason;
        }

        public String getProject() {
            return project;
        }

        public String getCompany() {
            return company;
        }

        public Object getValid() {
            return valid;
        }

        public String getCreate_datetime() {
            return create_datetime;
        }

        public String getAudit_datetime() {
            return audit_datetime;
        }

        public int getId() {
            return id;
        }
    }
}
