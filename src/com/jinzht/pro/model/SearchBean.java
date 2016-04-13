package com.jinzht.pro.model;

import java.util.List;


/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/8/11
 * @time 16:20
 */

public class SearchBean {


    /**
     * msg : �������
     * data : [{"stop":"2015-11-30","start":"2015-11-07","company":"�½���ɭ��ѧ�豸�������޹�˾","profile":"����λλ���½�ά�����������³ľ����ɳ���Ϳ���������������28�ţ���Ӫ��Ŀ�������Ҿ����� �����������2253����Ӫ����2253���ʲ��ܼ�2579�������������ֳ��ĸ�ı䵥һ���ã�ʵ�ж�ҵ���٣��������뽡����չ��ִ����","img":"http://www.jinzht.com:8000/media/project/img/2015/11/1967e279d95e4c3f92f852df3094c70a.jpg","id":7}]
     * code : 2
     */

    private String msg;
    private int code;
    private List<DataEntity> data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * stop : 2015-11-30
         * start : 2015-11-07
         * company : �½���ɭ��ѧ�豸�������޹�˾
         * profile : ����λλ���½�ά�����������³ľ����ɳ���Ϳ���������������28�ţ���Ӫ��Ŀ�������Ҿ����� �����������2253����Ӫ����2253���ʲ��ܼ�2579�������������ֳ��ĸ�ı䵥һ���ã�ʵ�ж�ҵ���٣��������뽡����չ��ִ����
         * img : http://www.jinzht.com:8000/media/project/img/2015/11/1967e279d95e4c3f92f852df3094c70a.jpg
         * id : 7
         */

        private String stop;
        private String start;
        private String company;
        private String profile;
        private String img;
        private int id;

        public void setStop(String stop) {
            this.stop = stop;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStop() {
            return stop;
        }

        public String getStart() {
            return start;
        }

        public String getCompany() {
            return company;
        }

        public String getProfile() {
            return profile;
        }

        public String getImg() {
            return img;
        }

        public int getId() {
            return id;
        }
    }
}
