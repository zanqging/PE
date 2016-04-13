package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/9/22.
 */
public class RoadShowsBean {

    /**
     * data : [{"industry_type":["ũ������"],"stage":{"color":13935141,"flag":2,"start":{"name":"�ڳ�ʱ��","datetime":"2015-08-29"},"end":{"name":"�ڳ��ֹʱ��","datetime":"2015-10-30"},"status":"���ʽ���"},"id":10,"collect_sum":175,"like_sum":116,"province":"�½�","city":"����","thumbnail":"http://www.jinzht.com:8000/media/project/thumbnail/2015/09/cf6d0e094dbe4c349080779fe47a30ef.jpg","vote_sum":162,"company_name":"��ݹ����������޹�˾","project_summary":"��ݹ���"},{"stage":{"color":14436124,"flag":3,"start":{"name":"�ڳ�ʱ��","datetime":"2015-08-22"},"end":{"name":"�ڳ��ֹʱ��","datetime":"2015-08-25"},"status":"�������"},"id":9,"like_sum":187,"vote_sum":120,"thumbnail":"http://www.jinzht.com:8000/media/project/thumbnail/2015/09/2a9f2b7889fe46b093c6b1328ea29ead.jpg","collect_sum":154,"invest_amount_sum":0,"city":"������","industry_type":["����ҵ"],"province":"����ʡ","company_name":"���������ù������������޹�˾","project_summary":"������"},{"stage":{"color":14436124,"flag":3,"start":{"name":"�ڳ�ʱ��","datetime":"2015-08-29"},"end":{"name":"�ڳ��ֹʱ��","datetime":"2015-08-30"},"status":"�������"},"id":8,"like_sum":116,"vote_sum":152,"thumbnail":"http://www.jinzht.com:8000/media/project/thumbnail/2015/09/91ac4b8c436149b18551f48562a2e704.jpg","collect_sum":135,"invest_amount_sum":0,"city":"ʯ��ׯ","industry_type":["��������"],"province":"�ӱ�ʡ","company_name":"ʯ��ׯ�������Ӳ������޹�˾","project_summary":"ʯ��ׯ����"},{"stage":{"color":14436124,"flag":3,"start":{"name":"�ڳ�ʱ��","datetime":"2015-08-29"},"end":{"name":"�ڳ��ֹʱ��","datetime":"2015-08-30"},"status":"�������"},"id":7,"like_sum":142,"vote_sum":191,"thumbnail":"http://www.jinzht.com:8000/media/project/thumbnail/2015/09/75610fd6cdd44ed597cedf3eb73a4713.jpg","collect_sum":190,"invest_amount_sum":0,"city":"������","industry_type":["������ѵ"],"province":"�½�","company_name":"�½���ɭ��ѧ�豸�������޹�˾","project_summary":"�½���ɭ"}]
     * msg :
     * status : 0
     */

    private String msg;
    private int status;
    private List<DataEntity> data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * industry_type : ["ũ������"]
         * stage : {"color":13935141,"flag":2,"start":{"name":"�ڳ�ʱ��","datetime":"2015-08-29"},"end":{"name":"�ڳ��ֹʱ��","datetime":"2015-10-30"},"status":"���ʽ���"}
         * id : 10
         * collect_sum : 175
         * like_sum : 116
         * province : �½�
         * city : ����
         * thumbnail : http://www.jinzht.com:8000/media/project/thumbnail/2015/09/cf6d0e094dbe4c349080779fe47a30ef.jpg
         * vote_sum : 162
         * company_name : ��ݹ����������޹�˾
         * project_summary : ��ݹ���
         */

        private StageEntity stage;
        private int id;
        private int collect_sum;
        private int like_sum;
        private String province;
        private String city;
        private String thumbnail;
        private int vote_sum;
        private String company_name;
        private String project_summary;
        private List<String> industry_type;

        public void setStage(StageEntity stage) {
            this.stage = stage;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setCollect_sum(int collect_sum) {
            this.collect_sum = collect_sum;
        }

        public void setLike_sum(int like_sum) {
            this.like_sum = like_sum;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public void setVote_sum(int vote_sum) {
            this.vote_sum = vote_sum;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public void setProject_summary(String project_summary) {
            this.project_summary = project_summary;
        }

        public void setIndustry_type(List<String> industry_type) {
            this.industry_type = industry_type;
        }

        public StageEntity getStage() {
            return stage;
        }

        public int getId() {
            return id;
        }

        public int getCollect_sum() {
            return collect_sum;
        }

        public int getLike_sum() {
            return like_sum;
        }

        public String getProvince() {
            return province;
        }

        public String getCity() {
            return city;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public int getVote_sum() {
            return vote_sum;
        }

        public String getCompany_name() {
            return company_name;
        }

        public String getProject_summary() {
            return project_summary;
        }

        public List<String> getIndustry_type() {
            return industry_type;
        }

        public static class StageEntity {
            /**
             * color : 13935141
             * flag : 2
             * start : {"name":"�ڳ�ʱ��","datetime":"2015-08-29"}
             * end : {"name":"�ڳ��ֹʱ��","datetime":"2015-10-30"}
             * status : ���ʽ���
             */

            private int color;
            private int flag;
            private StartEntity start;
            private EndEntity end;
            private String status;

            public void setColor(int color) {
                this.color = color;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public void setStart(StartEntity start) {
                this.start = start;
            }

            public void setEnd(EndEntity end) {
                this.end = end;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getColor() {
                return color;
            }

            public int getFlag() {
                return flag;
            }

            public StartEntity getStart() {
                return start;
            }

            public EndEntity getEnd() {
                return end;
            }

            public String getStatus() {
                return status;
            }

            public static class StartEntity {
                /**
                 * name : �ڳ�ʱ��
                 * datetime : 2015-08-29
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

            public static class EndEntity {
                /**
                 * name : �ڳ��ֹʱ��
                 * datetime : 2015-10-30
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
