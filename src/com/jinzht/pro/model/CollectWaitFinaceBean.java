package com.jinzht.pro.model;

import java.util.List;

/**
 * Created by Administrator on 2015/11/9.
 */
public class CollectWaitFinaceBean {


    /**
     * msg : 加载完毕
     * code : 2
     * data : [{"abbrevcompany":"利鼎电子","img":"http://www.jinzht.com:8000/media/project/img/2015/12/ab154d78a3664640b012f197d46b71bc.png","stop":"待定","start":"待定","company":"石家庄利鼎电子材料有限公司","id":3}]
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

    public class DataEntity {
        /**
         * abbrevcompany : 利鼎电子
         * img : http://www.jinzht.com:8000/media/project/img/2015/12/ab154d78a3664640b012f197d46b71bc.png
         * stop : 待定
         * start : 待定
         * company : 石家庄利鼎电子材料有限公司
         * id : 3
         */
        private String abbrevcompany;
        private String img;
        private String stop;
        private String start;
        private String company;
        private int id;

        public void setAbbrevcompany(String abbrevcompany) {
            this.abbrevcompany = abbrevcompany;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setStop(String stop) {
            this.stop = stop;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAbbrevcompany() {
            return abbrevcompany;
        }

        public String getImg() {
            return img;
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

        public int getId() {
            return id;
        }
    }
}
