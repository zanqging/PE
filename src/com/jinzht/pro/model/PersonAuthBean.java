package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/11/6.
 */
public class PersonAuthBean {

    /**
     * data : {"qualification":[{"value":"(һ)��˽ļͶ�ʻ���ල�������а취���涨�ĺϸ�Ͷ����","key":1},{"value":"(��)Ͷ�ʵ���������Ŀ����ͽ�����100��Ԫ����ҵĵ�λ�����","key":2},{"value":"(��)��ᱣ�ϻ�����ҵ�������ϻ��𣬴��ƻ������ṫ������Լ��������������й�֤ȯͶ�ʻ���ҵЭ�ᱸ����Ͷ�ʼƻ�","key":3},{"value":"(��)���ʲ�������1000��Ԫ����ҵĵ�λ","key":4},{"value":"(��)�����ʲ�������300��Ԫ����һ�����������������벻����50��Ԫ����ҵĸ��ˡ��������˳����ṩ��زƲ�������֤���⣬��Ӧ���ܱ�ʶ���жϺͳе���ӦͶ�ʷ���","key":5},{"value":"(��)֤ȯҵЭ��涨������Ͷ����","key":6}],"position":"��ָͶ","company":"��ָͶ"}
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
         * qualification : [{"value":"(һ)��˽ļͶ�ʻ���ල�������а취���涨�ĺϸ�Ͷ����","key":1},{"value":"(��)Ͷ�ʵ���������Ŀ����ͽ�����100��Ԫ����ҵĵ�λ�����","key":2},{"value":"(��)��ᱣ�ϻ�����ҵ�������ϻ��𣬴��ƻ������ṫ������Լ��������������й�֤ȯͶ�ʻ���ҵЭ�ᱸ����Ͷ�ʼƻ�","key":3},{"value":"(��)���ʲ�������1000��Ԫ����ҵĵ�λ","key":4},{"value":"(��)�����ʲ�������300��Ԫ����һ�����������������벻����50��Ԫ����ҵĸ��ˡ��������˳����ṩ��زƲ�������֤���⣬��Ӧ���ܱ�ʶ���жϺͳе���ӦͶ�ʷ���","key":5},{"value":"(��)֤ȯҵЭ��涨������Ͷ����","key":6}]
         * position : ��ָͶ
         * company : ��ָͶ
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
             * value : (һ)��˽ļͶ�ʻ���ල�������а취���涨�ĺϸ�Ͷ����
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
