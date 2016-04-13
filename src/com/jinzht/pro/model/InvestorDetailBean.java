package com.jinzht.pro.model;


/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/8/16
 * @time 9:38
 */

public class InvestorDetailBean {


    /**
     * msg :
     * code : 0
     * data : {"thumbnail":"http://www.jinzht.com/static/app/img/icon.png","cases":"强国而努力奋斗\u201d的使命！ 聚英时代进行着······","signature":"生命在于奋斗","domain":"◆投资融资","video":"","experience":"ss"}
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
         * thumbnail : http://www.jinzht.com/static/app/img/icon.png
         * cases : 强国而努力奋斗”的使命！ 聚英时代进行着······
         * signature : 生命在于奋斗
         * domain : ◆投资融资
         * video :
         * experience :
         */
        private String thumbnail;
        private String cases;
        private String signature;
        private String domain;
        private String video;
        private String experience;

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setCases(String cases) {
            this.cases = cases;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public String getCases() {
            return cases;
        }

        public String getSignature() {
            return signature;
        }

        public String getDomain() {
            return domain;
        }

        public String getVideo() {
            return video;
        }

        public String getExperience() {
            return experience;
        }
    }
}
