package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/11/20.
 */
public class AuthInformation {

    /**
     * code : 0
     * data : {"legalperson":"��Ԫ�� ��Ԫ��","position":"������ ������","company":"��JJ��","idpic":"http://www.jinzht.com:8000/media/company/idpic/2015/11/44c4aeb98b9047d7a63a6c1abb936d4c.jpg","institute":"��Ԫ�� ��Ԫ��","qua":"{'1'}","qualification":[{"value":"(һ)��˽ļͶ�ʻ���ල�������а취���涨�ĺϸ�Ͷ����","key":1},{"value":"(��)Ͷ�ʵ���������Ŀ����ͽ�����100��Ԫ����ҵĵ�λ�����","key":2},{"value":"(��)��ᱣ�ϻ�����ҵ�������ϻ��𣬴��ƻ������ṫ������Լ��������������й�֤ȯͶ�ʻ���ҵЭ�ᱸ����Ͷ�ʼƻ�","key":3},{"value":"(��)���ʲ�������1000��Ԫ����ҵĵ�λ","key":4},{"value":"(��)�����ʲ�������300��Ԫ����һ�����������������벻����50��Ԫ����ҵĸ��ˡ��������˳����ṩ��زƲ�������֤���⣬��Ӧ���ܱ�ʶ���жϺͳе���ӦͶ�ʷ���","key":5},{"value":"(��)֤ȯҵЭ��涨������Ͷ����","key":6}]}
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
         * legalperson : ��Ԫ�� ��Ԫ��
         * position : ������ ������
         * company : ��JJ��
         * idpic : http://www.jinzht.com:8000/media/company/idpic/2015/11/44c4aeb98b9047d7a63a6c1abb936d4c.jpg
         * institute : ��Ԫ�� ��Ԫ��
         * qua : {'1'}
         * qualification : [{"value":"(һ)��˽ļͶ�ʻ���ල�������а취���涨�ĺϸ�Ͷ����","key":1},{"value":"(��)Ͷ�ʵ���������Ŀ����ͽ�����100��Ԫ����ҵĵ�λ�����","key":2},{"value":"(��)��ᱣ�ϻ�����ҵ�������ϻ��𣬴��ƻ������ṫ������Լ��������������й�֤ȯͶ�ʻ���ҵЭ�ᱸ����Ͷ�ʼƻ�","key":3},{"value":"(��)���ʲ�������1000��Ԫ����ҵĵ�λ","key":4},{"value":"(��)�����ʲ�������300��Ԫ����һ�����������������벻����50��Ԫ����ҵĸ��ˡ��������˳����ṩ��زƲ�������֤���⣬��Ӧ���ܱ�ʶ���жϺͳе���ӦͶ�ʷ���","key":5},{"value":"(��)֤ȯҵЭ��涨������Ͷ����","key":6}]
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
             * value : (һ)��˽ļͶ�ʻ���ල�������а취���涨�ĺϸ�Ͷ����
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
