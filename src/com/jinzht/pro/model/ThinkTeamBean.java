package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/11/7.
 */
public class ThinkTeamBean {

    /**
     * data : [{"name":"������","photo":"http://www.jinzht.com:8000/media/thinktank/img/2015/11/6cb2539a845b483fbaba09cd9c71ab04.jpg","id":1,"position":"���ܾ�������ϻ���","company":"��Ӣ������ѯ����"}]
     * msg : �������
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
         * name : ������
         * photo : http://www.jinzht.com:8000/media/thinktank/img/2015/11/6cb2539a845b483fbaba09cd9c71ab04.jpg
         * id : 1
         * position : ���ܾ�������ϻ���
         * company : ��Ӣ������ѯ����
         */

        private String name;
        private String photo;
        private int id;
        private String position;
        private String company;

        public void setName(String name) {
            this.name = name;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getName() {
            return name;
        }

        public String getPhoto() {
            return photo;
        }

        public int getId() {
            return id;
        }

        public String getPosition() {
            return position;
        }

        public String getCompany() {
            return company;
        }
    }
}
