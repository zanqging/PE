package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2016/1/9.
 */
public class OriInvestorDetailBean {


    /**
     * msg :
     * code : 0
     * data : {"investcase":[{"logo":"http://www.jinzht.com:8000/media/investcase/logo/2016/01/de3ba243e11a4b0a9954d1a217f04907.jpg","company":"��Ӣ���ʿعɼ���"},{"logo":"http://www.jinzht.com:8000/media/investcase/logo/2016/01/7efa39f9d89646fdaba651cd730518a3.jpg","company":"������ָͶ��Ϣ�Ƽ����޹�˾"}],"thumbnail":"http://www.jinzht.com:8000/media/institute/thumbnail/2016/01/32b3a27b2e9048a3a1957e6629eab7ed.jpg","profile":"�Ϻ��ںϴ�ҵͶ�ʹ������޹�˾λ��ֱϽ�� �Ϻ� �ֶ�������ɽ·450��������ʴ���18¥B�ң���˾�����绰��021-50815116�� �Ϻ��ںϴ�ҵͶ�ʹ������޹�˾Ը��������ͬ��Я�ֺ�����ı��ͬ��չ������Ϊ���Ͽͻ��ṩ������Ĳ�Ʒ�ͷ��񡣹�˾�����Ϻ������̺ʹ����̽����˳����ȶ��ĺ�����ϵ��Ʒ����ȫ���۸������ҵʵ���ۺ������á��غ�ͬ����֤��Ʒ�������Զ�Ʒ�־�Ӫ��ɫ�ͱ���������ԭ��Ӯ���˹��ͻ������Ρ�","foundingtime":"2016-01-09","fundsize":"900��","homepage":"http://www.baidu.com"}
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

    public class DataEntity {
        /**
         * investcase : [{"logo":"http://www.jinzht.com:8000/media/investcase/logo/2016/01/de3ba243e11a4b0a9954d1a217f04907.jpg","company":"��Ӣ���ʿعɼ���"},{"logo":"http://www.jinzht.com:8000/media/investcase/logo/2016/01/7efa39f9d89646fdaba651cd730518a3.jpg","company":"������ָͶ��Ϣ�Ƽ����޹�˾"}]
         * thumbnail : http://www.jinzht.com:8000/media/institute/thumbnail/2016/01/32b3a27b2e9048a3a1957e6629eab7ed.jpg
         * profile : �Ϻ��ںϴ�ҵͶ�ʹ������޹�˾λ��ֱϽ�� �Ϻ� �ֶ�������ɽ·450��������ʴ���18¥B�ң���˾�����绰��021-50815116�� �Ϻ��ںϴ�ҵͶ�ʹ������޹�˾Ը��������ͬ��Я�ֺ�����ı��ͬ��չ������Ϊ���Ͽͻ��ṩ������Ĳ�Ʒ�ͷ��񡣹�˾�����Ϻ������̺ʹ����̽����˳����ȶ��ĺ�����ϵ��Ʒ����ȫ���۸������ҵʵ���ۺ������á��غ�ͬ����֤��Ʒ�������Զ�Ʒ�־�Ӫ��ɫ�ͱ���������ԭ��Ӯ���˹��ͻ������Ρ�
         * foundingtime : 2016-01-09
         * fundsize : 900��
         * homepage : http://www.baidu.com
         */
        private List<InvestcaseEntity> investcase;
        private String thumbnail;
        private String profile;
        private String foundingtime;
        private String fundsize;
        private String homepage;

        public void setInvestcase(List<InvestcaseEntity> investcase) {
            this.investcase = investcase;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public void setFoundingtime(String foundingtime) {
            this.foundingtime = foundingtime;
        }

        public void setFundsize(String fundsize) {
            this.fundsize = fundsize;
        }

        public void setHomepage(String homepage) {
            this.homepage = homepage;
        }

        public List<InvestcaseEntity> getInvestcase() {
            return investcase;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public String getProfile() {
            return profile;
        }

        public String getFoundingtime() {
            return foundingtime;
        }

        public String getFundsize() {
            return fundsize;
        }

        public String getHomepage() {
            return homepage;
        }

        public class InvestcaseEntity {
            /**
             * logo : http://www.jinzht.com:8000/media/investcase/logo/2016/01/de3ba243e11a4b0a9954d1a217f04907.jpg
             * company : ��Ӣ���ʿعɼ���
             */
            private String logo;
            private String company;

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public String getLogo() {
                return logo;
            }

            public String getCompany() {
                return company;
            }
        }
    }
}
