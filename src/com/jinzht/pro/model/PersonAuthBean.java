package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/11/6.
 */
public class PersonAuthBean {

    /**
     * data : {"qualification":[{"value":"(一)《私募投资基金监督管理暂行办法》规定的合格投资者","key":1},{"value":"(二)投资单个融资项目的最低金额不低于100万元人民币的单位或个人","key":2},{"value":"(三)社会保障基金、企业年金等养老基金，慈善基金等社会公益基金，以及依法设立并在中国证券投资基金业协会备案的投资计划","key":3},{"value":"(四)净资产不低于1000万元人民币的单位","key":4},{"value":"(五)金融资产不低于300万元人民币或最近三年个人年均收入不低于50万元人民币的个人。上述个人除能提供相关财产、收入证明外，还应当能辨识、判断和承担相应投资风险","key":5},{"value":"(六)证券业协会规定的其他投资者","key":6}],"position":"金指投","company":"金指投"}
     * code : 0
     * msg :
     */

    private DataEntity data;
    private int code;
    private String msg;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataEntity getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static class DataEntity {
        /**
         * qualification : [{"value":"(一)《私募投资基金监督管理暂行办法》规定的合格投资者","key":1},{"value":"(二)投资单个融资项目的最低金额不低于100万元人民币的单位或个人","key":2},{"value":"(三)社会保障基金、企业年金等养老基金，慈善基金等社会公益基金，以及依法设立并在中国证券投资基金业协会备案的投资计划","key":3},{"value":"(四)净资产不低于1000万元人民币的单位","key":4},{"value":"(五)金融资产不低于300万元人民币或最近三年个人年均收入不低于50万元人民币的个人。上述个人除能提供相关财产、收入证明外，还应当能辨识、判断和承担相应投资风险","key":5},{"value":"(六)证券业协会规定的其他投资者","key":6}]
         * position : 金指投
         * company : 金指投
         */

        private String position;
        private String company;
        private List<QualificationEntity> qualification;

        public void setPosition(String position) {
            this.position = position;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setQualification(List<QualificationEntity> qualification) {
            this.qualification = qualification;
        }

        public String getPosition() {
            return position;
        }

        public String getCompany() {
            return company;
        }

        public List<QualificationEntity> getQualification() {
            return qualification;
        }

        public static class QualificationEntity {
            /**
             * value : (一)《私募投资基金监督管理暂行办法》规定的合格投资者
             * key : 1
             */

            private String value;
            private int key;

            public QualificationEntity() {
                super();
            }

            public QualificationEntity(String value, int key) {
                this.value = value;
                this.key = key;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public void setKey(int key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public int getKey() {
                return key;
            }
        }
    }
}
