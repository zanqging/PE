package com.jinzht.pro.model;


/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/7/30
 * @time 16:10
 */

public class PECompleteDetailsBean {


    /**
     * msg :
     * data : {"is_attend":false,"model":"�������ģʽ���շ��Ƴа���ʽ����������ҵ������ϴ����豸","planfinance":300,"company":"������ʢ��̩�����Ƽ����޹�˾","video":"","event":"���˽�һ��Ա���ĵ�ʵ���������ϸ��¼������ʵʵ��Ϊһ��Ա������","collect":0,"stage":{"flag":1,"end":{"name":"������ֹ","datetime":"����"},"daysLeave":"����","start":{"name":"·��ʱ��","datetime":"����"},"color":15112065,"code":"·��Ԥ��"},"invest":0,"like":2,"img":"http://www.jinzht.com:8000/media/project/img/2015/12/23e38aeb3db449ae894c1c83c4561f67.png","profile":"˾���������������������˼����з����ģ����½�����տ���������������װ�����ģ����½��⳵������������Ӫ���ġ�","is_like":false,"business":"�����豸�����","id":6,"is_collect":false,"minfund":0}
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
         * model : �������ģʽ���շ��Ƴа���ʽ����������ҵ������ϴ����豸
         * planfinance : 300
         * company : ������ʢ��̩�����Ƽ����޹�˾
         * video :
         * event : ���˽�һ��Ա���ĵ�ʵ���������ϸ��¼������ʵʵ��Ϊһ��Ա������
         * collect : 0
         * stage : {"flag":1,"end":{"name":"������ֹ","datetime":"����"},"daysLeave":"����","start":{"name":"·��ʱ��","datetime":"����"},"color":15112065,"code":"·��Ԥ��"}
         * invest : 0
         * like : 2
         * img : http://www.jinzht.com:8000/media/project/img/2015/12/23e38aeb3db449ae894c1c83c4561f67.png
         * profile : ˾���������������������˼����з����ģ����½�����տ���������������װ�����ģ����½��⳵������������Ӫ���ġ�
         * is_like : false
         * business : �����豸�����
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
        private String profile;// ���
        private boolean is_like;
        private String business;
        private int id;
        private boolean is_collect;
        private int minfund;// ��СͶ�ʶ��
        private String brrow_user_no;// �����
        private double profit;// ����ֳ�

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
             * end : {"name":"������ֹ","datetime":"����"}
             * daysLeave : ����
             * start : {"name":"·��ʱ��","datetime":"����"}
             * color : 15112065
             * code : ·��Ԥ��
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
                 * name : ������ֹ
                 * datetime : ����
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
                 * name : ·��ʱ��
                 * datetime : ����
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
