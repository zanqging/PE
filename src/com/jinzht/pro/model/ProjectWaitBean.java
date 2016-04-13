package com.jinzht.pro.model;

import java.util.List;



/**
 * Created by Administrator on 2015/11/4.
 */
public class ProjectWaitBean {


    /**
     * msg : �������
     * code : 2
     * data : [{"img":"http://www.jinzht.com:8000/media/project/img/2015/12/23e38aeb3db449ae894c1c83c4561f67.png","abbrevcompany":"������ʢ��̩�����Ƽ�","date":"����","tag":"���� ����","company":"������ʢ��̩�����Ƽ����޹�˾","addr":"���� ����","id":6},{"img":"http://www.jinzht.com:8000/media/project/img/2015/12/ab154d78a3664640b012f197d46b71bc.png","abbrevcompany":"ʯ��ׯ�������Ӳ���","date":"����","tag":"����","company":"ʯ��ׯ�������Ӳ������޹�˾","addr":"�ӱ� ʯ��ׯ","id":3}]
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
         * img : http://www.jinzht.com:8000/media/project/img/2015/12/23e38aeb3db449ae894c1c83c4561f67.png
         * abbrevcompany : ������ʢ��̩�����Ƽ�
         * date : ����
         * tag : ���� ����
         * company : ������ʢ��̩�����Ƽ����޹�˾
         * addr : ���� ����
         * id : 6
         */

        private String img;
        private String abbrevcompany;
        private String date;
        private String tag;
        private String company;
        private String addr;
        private int id;

        public void setImg(String img) {
            this.img = img;
        }

        public void setAbbrevcompany(String abbrevcompany) {
            this.abbrevcompany = abbrevcompany;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public String getAbbrevcompany() {
            return abbrevcompany;
        }

        public String getDate() {
            return date;
        }

        public String getTag() {
            return tag;
        }

        public String getCompany() {
            return company;
        }

        public String getAddr() {
            return addr;
        }

        public int getId() {
            return id;
        }
    }
}
