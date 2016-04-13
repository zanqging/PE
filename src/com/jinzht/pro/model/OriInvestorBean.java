package com.jinzht.pro.model;


/**
 * Created by Administrator on 2015/12/31.
 */
public class OriInvestorBean {

    /**
     * code : 0
     * data : {"profile":"上海众合创业投资管理有限公司位于直辖市 上海 浦东新区福山路450号新天国际大厦18楼B室，公司座机电话是021-50815116， 上海众合创业投资管理有限公司愿与社会各界同仁携手合作，谋求共同发展，继续为新老客户提供最优秀的产品和服务。公司与多家上海零售商和代理商建立了长期稳定的合作关系，品种齐全、价格合理，企业实力雄厚，重信用、守合同、保证产品质量，以多品种经营特色和薄利多销的原则，赢得了广大客户的信任","fundsize":""}
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
         * profile : 上海众合创业投资管理有限公司位于直辖市 上海 浦东新区福山路450号新天国际大厦18楼B室，公司座机电话是021-50815116， 上海众合创业投资管理有限公司愿与社会各界同仁携手合作，谋求共同发展，继续为新老客户提供最优秀的产品和服务。公司与多家上海零售商和代理商建立了长期稳定的合作关系，品种齐全、价格合理，企业实力雄厚，重信用、守合同、保证产品质量，以多品种经营特色和薄利多销的原则，赢得了广大客户的信任
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
