package com.jinzht.pro.model;

import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 */
public class HomePageBean {


    /**
     * msg :
     * code : 0
     * data : {"banner":[{"img":"http://www.jinzht.com:8000/media/banner/img/2016/01/6588ea78c47e4b3c9f13929b398a4821.jpg","project":8,"url":""},{"img":"http://www.jinzht.com:8000/media/banner/img/2016/01/8ec9b45308134fe489c641edd0ba3fc5.jpg","project":10,"url":""},{"img":"http://www.jinzht.com:8000/media/banner/img/2016/01/0a05f6e4fdbc49879c28971993fd97bb.jpg","project":2,"url":""},{"img":"http://www.jinzht.com:8000/media/banner/img/2016/01/dea792a52e184267ba3eebe3d6984628.jpg","project":1,"url":""}],"project":[{"date":"2015-08-25","img":"http://www.jinzht.com:8000/media/project/img/2016/01/b85eeb13b5ac491494c1cbe322ba81c6.jpg","invest":439,"company":"西安国联质量检测技术","tag":"检测","id":10,"planfinance":200},{"date":"2015-06-16","img":"http://www.jinzht.com:8000/media/project/img/2016/01/e02b7ecd90924e3e8787dd64df6c45cb.jpg","invest":950,"company":"新疆神木药业","tag":"医药","id":9,"planfinance":100},{"date":"2015-09-25","img":"http://www.jinzht.com:8000/media/project/img/2016/01/77f44b221de94b1bb89ddc50f5c9d4f4.jpg","invest":365,"company":"西安杰邦科技","tag":"工程","id":8,"planfinance":300},{"date":"2015-12-23","img":"http://www.jinzht.com:8000/media/project/img/2015/12/0063a9fc601b4014b4a8497c1f14848e.png","invest":0,"company":"贵州欧瑞信环保科技","tag":"环保","id":7,"planfinance":300}],"platform":{"title":"融资播报","url":"http://www.jinzht.com:8000/phone/annc/user_guide/"},"announcement":{"title":"新手指南","url":"http://www.jinzht.com:8000/phone/annc/user_guide/"}}
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
         * banner : [{"img":"http://www.jinzht.com:8000/media/banner/img/2016/01/6588ea78c47e4b3c9f13929b398a4821.jpg","project":8,"url":""},{"img":"http://www.jinzht.com:8000/media/banner/img/2016/01/8ec9b45308134fe489c641edd0ba3fc5.jpg","project":10,"url":""},{"img":"http://www.jinzht.com:8000/media/banner/img/2016/01/0a05f6e4fdbc49879c28971993fd97bb.jpg","project":2,"url":""},{"img":"http://www.jinzht.com:8000/media/banner/img/2016/01/dea792a52e184267ba3eebe3d6984628.jpg","project":1,"url":""}]
         * project : [{"date":"2015-08-25","img":"http://www.jinzht.com:8000/media/project/img/2016/01/b85eeb13b5ac491494c1cbe322ba81c6.jpg","invest":439,"company":"西安国联质量检测技术","tag":"检测","id":10,"planfinance":200},{"date":"2015-06-16","img":"http://www.jinzht.com:8000/media/project/img/2016/01/e02b7ecd90924e3e8787dd64df6c45cb.jpg","invest":950,"company":"新疆神木药业","tag":"医药","id":9,"planfinance":100},{"date":"2015-09-25","img":"http://www.jinzht.com:8000/media/project/img/2016/01/77f44b221de94b1bb89ddc50f5c9d4f4.jpg","invest":365,"company":"西安杰邦科技","tag":"工程","id":8,"planfinance":300},{"date":"2015-12-23","img":"http://www.jinzht.com:8000/media/project/img/2015/12/0063a9fc601b4014b4a8497c1f14848e.png","invest":0,"company":"贵州欧瑞信环保科技","tag":"环保","id":7,"planfinance":300}]
         * platform : {"title":"融资播报","url":"http://www.jinzht.com:8000/phone/annc/user_guide/"}
         * announcement : {"title":"新手指南","url":"http://www.jinzht.com:8000/phone/annc/user_guide/"}
         */
        private List<BannerEntity> banner;
        private List<ProjectEntity> project;
        private PlatformEntity platform;
        private AnnouncementEntity announcement;

        public void setBanner(List<BannerEntity> banner) {
            this.banner = banner;
        }

        public void setProject(List<ProjectEntity> project) {
            this.project = project;
        }

        public void setPlatform(PlatformEntity platform) {
            this.platform = platform;
        }

        public void setAnnouncement(AnnouncementEntity announcement) {
            this.announcement = announcement;
        }

        public List<BannerEntity> getBanner() {
            return banner;
        }

        public List<ProjectEntity> getProject() {
            return project;
        }

        public PlatformEntity getPlatform() {
            return platform;
        }

        public AnnouncementEntity getAnnouncement() {
            return announcement;
        }

        // 广告实体
        public class BannerEntity {
            /**
             * img : http://www.jinzht.com:8000/media/banner/img/2016/01/6588ea78c47e4b3c9f13929b398a4821.jpg
             * project : 8
             * url :
             */
            private String img;
            private Object project;
            private String url;

            public void setImg(String img) {
                this.img = img;
            }

            public void setProject(Object project) {
                this.project = project;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getImg() {
                return img;
            }

            public Object getProject() {
                return project;
            }

            public String getUrl() {
                return url;
            }
        }

        public class ProjectEntity {
            /**
             * date : 2015-08-25
             * img : http://www.jinzht.com:8000/media/project/img/2016/01/b85eeb13b5ac491494c1cbe322ba81c6.jpg
             * invest : 439
             * company : 西安国联质量检测技术
             * tag : 检测
             * id : 10
             * planfinance : 200
             */
            private String date;
            private String img;
            private int invest;
            private String company;
            private String tag;
            private int id;
            private int planfinance;
            private String abbrevcompany;

            public String getAbbrevcompany() {
                return abbrevcompany;
            }

            public void setAbbrevcompany(String abbrevcompany) {
                this.abbrevcompany = abbrevcompany;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public void setInvest(int invest) {
                this.invest = invest;
            }

            public void setCompany(String company) {
                this.company = company;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setPlanfinance(int planfinance) {
                this.planfinance = planfinance;
            }

            public String getDate() {
                return date;
            }

            public String getImg() {
                return img;
            }

            public int getInvest() {
                return invest;
            }

            public String getCompany() {
                return company;
            }

            public String getTag() {
                return tag;
            }

            public int getId() {
                return id;
            }

            public int getPlanfinance() {
                return planfinance;
            }
        }

        public class PlatformEntity {
            /**
             * title : 融资播报
             * url : http://www.jinzht.com:8000/phone/annc/user_guide/
             */
            private String title;
            private String url;

            public void setTitle(String title) {
                this.title = title;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTitle() {
                return title;
            }

            public String getUrl() {
                return url;
            }
        }

        public class AnnouncementEntity {
            /**
             * title : 新手指南
             * url : http://www.jinzht.com:8000/phone/annc/user_guide/
             */
            private String title;
            private String url;

            public void setTitle(String title) {
                this.title = title;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTitle() {
                return title;
            }

            public String getUrl() {
                return url;
            }
        }
    }
}
