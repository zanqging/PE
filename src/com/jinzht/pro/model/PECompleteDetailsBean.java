package com.jinzht.pro.model;


/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/7/30
 * @time 16:10
 */

public class PECompleteDetailsBean {


    /**
     * msg :
     * data : {"is_attend":false,"model":"服务输出模式：日费制承包方式，即油田企业根据钻废处理设备","planfinance":300,"company":"北京华盛坤泰环保科技有限公司","video":"","event":"入了解一线员工的的实地情况并详细记录，扎扎实实的为一线员工服务。","collect":0,"stage":{"flag":1,"end":{"name":"报名截止","datetime":"待定"},"daysLeave":"待定","start":{"name":"路演时间","datetime":"待定"},"color":15112065,"code":"路演预告"},"invest":0,"like":2,"img":"http://www.jinzht.com:8000/media/project/img/2015/12/23e38aeb3db449ae894c1c83c4561f67.png","profile":"司在西安市西咸新区建立了技术研发中心，在新疆库尔勒开发区建立了制造装配中心，在新疆库车建立了生产运营中心。","is_like":false,"business":"钻理设备输出域","id":6,"is_collect":false,"minfund":0}
     * code : 0
     */

    private String msg;
    private DataEntity data;
    private int code;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public static class DataEntity {
        /**
         * is_attend : false
         * model : 服务输出模式：日费制承包方式，即油田企业根据钻废处理设备
         * planfinance : 300
         * company : 北京华盛坤泰环保科技有限公司
         * video :
         * event : 入了解一线员工的的实地情况并详细记录，扎扎实实的为一线员工服务。
         * collect : 0
         * stage : {"flag":1,"end":{"name":"报名截止","datetime":"待定"},"daysLeave":"待定","start":{"name":"路演时间","datetime":"待定"},"color":15112065,"code":"路演预告"}
         * invest : 0
         * like : 2
         * img : http://www.jinzht.com:8000/media/project/img/2015/12/23e38aeb3db449ae894c1c83c4561f67.png
         * profile : 司在西安市西咸新区建立了技术研发中心，在新疆库尔勒开发区建立了制造装配中心，在新疆库车建立了生产运营中心。
         * is_like : false
         * business : 钻理设备输出域
         * id : 6
         * is_collect : false
         * minfund : 0
         */

        private boolean is_attend;
        private String model;
        private double planfinance;
        private String company;
        private String video;
        private String event;
        private int collect;
        private StageEntity stage;
        private double invest;
        private int like;
        private String img;
        private String profile;// 简介
        private boolean is_like;
        private String business;
        private int id;
        private boolean is_collect;
        private int minfund;// 最小投资额度
        private String brrow_user_no;// 借款人
        private double profit;// 利润分成

        public String getBrrow_user_no() {
            return brrow_user_no;
        }

        public void setBrrow_user_no(String brrow_user_no) {
            this.brrow_user_no = brrow_user_no;
        }

        public double getProfit() {
            return profit;
        }

        public void setProfit(double profit) {
            this.profit = profit;
        }

        public void setIs_attend(boolean is_attend) {
            this.is_attend = is_attend;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public void setPlanfinance(double planfinance) {
            this.planfinance = planfinance;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }

        public void setStage(StageEntity stage) {
            this.stage = stage;
        }

        public void setInvest(double invest) {
            this.invest = invest;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public void setIs_like(boolean is_like) {
            this.is_like = is_like;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setIs_collect(boolean is_collect) {
            this.is_collect = is_collect;
        }

        public void setMinfund(int minfund) {
            this.minfund = minfund;
        }

        public boolean getIs_attend() {
            return is_attend;
        }

        public String getModel() {
            return model;
        }

        public double getPlanfinance() {
            return planfinance;
        }

        public String getCompany() {
            return company;
        }

        public String getVideo() {
            return video;
        }

        public String getEvent() {
            return event;
        }

        public int getCollect() {
            return collect;
        }

        public StageEntity getStage() {
            return stage;
        }

        public double getInvest() {
            return invest;
        }

        public int getLike() {
            return like;
        }

        public String getImg() {
            return img;
        }

        public String getProfile() {
            return profile;
        }

        public boolean getIs_like() {
            return is_like;
        }

        public String getBusiness() {
            return business;
        }

        public int getId() {
            return id;
        }

        public boolean getIs_collect() {
            return is_collect;
        }

        public int getMinfund() {
            return minfund;
        }

        public static class StageEntity {
            /**
             * flag : 1
             * end : {"name":"报名截止","datetime":"待定"}
             * daysLeave : 待定
             * start : {"name":"路演时间","datetime":"待定"}
             * color : 15112065
             * code : 路演预告
             */

            private int flag;
            private EndEntity end;
            private String daysLeave;
            private StartEntity start;
            private int color;
            private String code;
            private int daysTotal;

            public int getDaysTotal() {
                return daysTotal;
            }

            public void setDaysTotal(int daysTotal) {
                this.daysTotal = daysTotal;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public void setEnd(EndEntity end) {
                this.end = end;
            }

            public void setDaysLeave(String daysLeave) {
                this.daysLeave = daysLeave;
            }

            public void setStart(StartEntity start) {
                this.start = start;
            }

            public void setColor(int color) {
                this.color = color;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getFlag() {
                return flag;
            }

            public EndEntity getEnd() {
                return end;
            }

            public String getDaysLeave() {
                return daysLeave;
            }

            public StartEntity getStart() {
                return start;
            }

            public int getColor() {
                return color;
            }

            public String getCode() {
                return code;
            }

            public static class EndEntity {
                /**
                 * name : 报名截止
                 * datetime : 待定
                 */

                private String name;
                private String datetime;

                public void setName(String name) {
                    this.name = name;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }

                public String getName() {
                    return name;
                }

                public String getDatetime() {
                    return datetime;
                }
            }

            public static class StartEntity {
                /**
                 * name : 路演时间
                 * datetime : 待定
                 */

                private String name;
                private String datetime;

                public void setName(String name) {
                    this.name = name;
                }

                public void setDatetime(String datetime) {
                    this.datetime = datetime;
                }

                public String getName() {
                    return name;
                }

                public String getDatetime() {
                    return datetime;
                }
            }
        }
    }
}
