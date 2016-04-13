package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2016/1/9.
 */
public class OriInvestorDetailBean {


    /**
     * msg :
     * code : 0
     * data : {"investcase":[{"logo":"http://www.jinzht.com:8000/media/investcase/logo/2016/01/de3ba243e11a4b0a9954d1a217f04907.jpg","company":"聚英国际控股集团"},{"logo":"http://www.jinzht.com:8000/media/investcase/logo/2016/01/7efa39f9d89646fdaba651cd730518a3.jpg","company":"北京金指投信息科技有限公司"}],"thumbnail":"http://www.jinzht.com:8000/media/institute/thumbnail/2016/01/32b3a27b2e9048a3a1957e6629eab7ed.jpg","profile":"上海众合创业投资管理有限公司位于直辖市 上海 浦东新区福山路450号新天国际大厦18楼B室，公司座机电话是021-50815116， 上海众合创业投资管理有限公司愿与社会各界同仁携手合作，谋求共同发展，继续为新老客户提供最优秀的产品和服务。公司与多家上海零售商和代理商建立了长期稳定的合作关系，品种齐全、价格合理，企业实力雄厚，重信用、守合同、保证产品质量，以多品种经营特色和薄利多销的原则，赢得了广大客户的信任。","foundingtime":"2016-01-09","fundsize":"900万","homepage":"http://www.baidu.com"}
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
         * investcase : [{"logo":"http://www.jinzht.com:8000/media/investcase/logo/2016/01/de3ba243e11a4b0a9954d1a217f04907.jpg","company":"聚英国际控股集团"},{"logo":"http://www.jinzht.com:8000/media/investcase/logo/2016/01/7efa39f9d89646fdaba651cd730518a3.jpg","company":"北京金指投信息科技有限公司"}]
         * thumbnail : http://www.jinzht.com:8000/media/institute/thumbnail/2016/01/32b3a27b2e9048a3a1957e6629eab7ed.jpg
         * profile : 上海众合创业投资管理有限公司位于直辖市 上海 浦东新区福山路450号新天国际大厦18楼B室，公司座机电话是021-50815116， 上海众合创业投资管理有限公司愿与社会各界同仁携手合作，谋求共同发展，继续为新老客户提供最优秀的产品和服务。公司与多家上海零售商和代理商建立了长期稳定的合作关系，品种齐全、价格合理，企业实力雄厚，重信用、守合同、保证产品质量，以多品种经营特色和薄利多销的原则，赢得了广大客户的信任。
         * foundingtime : 2016-01-09
         * fundsize : 900万
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
             * company : 聚英国际控股集团
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
