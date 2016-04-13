package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/11/20.
 */
public class AuthInformation {

    /**
     * code : 0
     * data : {"legalperson":"乔元培 乔元培","position":"北京市 东城区","company":"福JJ及","idpic":"http://www.jinzht.com:8000/media/company/idpic/2015/11/44c4aeb98b9047d7a63a6c1abb936d4c.jpg","institute":"乔元培 乔元培","qua":"{'1'}","qualification":[{"value":"(一)《私募投资基金监督管理暂行办法》规定的合格投资者","key":1},{"value":"(二)投资单个融资项目的最低金额不低于100万元人民币的单位或个人","key":2},{"value":"(三)社会保障基金、企业年金等养老基金，慈善基金等社会公益基金，以及依法设立并在中国证券投资基金业协会备案的投资计划","key":3},{"value":"(四)净资产不低于1000万元人民币的单位","key":4},{"value":"(五)金融资产不低于300万元人民币或最近三年个人年均收入不低于50万元人民币的个人。上述个人除能提供相关财产、收入证明外，还应当能辨识、判断和承担相应投资风险","key":5},{"value":"(六)证券业协会规定的其他投资者","key":6}]}
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
         * legalperson : 乔元培 乔元培
         * position : 北京市 东城区
         * company : 福JJ及
         * idpic : http://www.jinzht.com:8000/media/company/idpic/2015/11/44c4aeb98b9047d7a63a6c1abb936d4c.jpg
         * institute : 乔元培 乔元培
         * qua : {'1'}
         * qualification : [{"value":"(一)《私募投资基金监督管理暂行办法》规定的合格投资者","key":1},{"value":"(二)投资单个融资项目的最低金额不低于100万元人民币的单位或个人","key":2},{"value":"(三)社会保障基金、企业年金等养老基金，慈善基金等社会公益基金，以及依法设立并在中国证券投资基金业协会备案的投资计划","key":3},{"value":"(四)净资产不低于1000万元人民币的单位","key":4},{"value":"(五)金融资产不低于300万元人民币或最近三年个人年均收入不低于50万元人民币的个人。上述个人除能提供相关财产、收入证明外，还应当能辨识、判断和承担相应投资风险","key":5},{"value":"(六)证券业协会规定的其他投资者","key":6}]
         */

        private String legalperson;
        private String position;
        private String company;
        private String idpic;
        private String institute;
        private String qua;
        private List<QualificationEntity> qualification;

        public void setLegalperson(String legalperson) {
            this.legalperson = legalperson;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setIdpic(String idpic) {
            this.idpic = idpic;
        }

        public void setInstitute(String institute) {
            this.institute = institute;
        }

        public void setQua(String qua) {
            this.qua = qua;
        }

        public void setQualification(List<QualificationEntity> qualification) {
            this.qualification = qualification;
        }

        public String getLegalperson() {
            return legalperson;
        }

        public String getPosition() {
            return position;
        }

        public String getCompany() {
            return company;
        }

        public String getIdpic() {
            return idpic;
        }

        public String getInstitute() {
            return institute;
        }

        public String getQua() {
            return qua;
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
