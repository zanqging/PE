package com.jinzht.pro.model;



/**
 * Created by Administrator on 2015/9/14.
 */
public class NewsShareBean {


    /**
     * data : {"url":"http://www.jinzht.com:8000/phone/sanban/8630","content":"我国的总人口都将会在2023年左右开始下降。但是实行全面二孩政策，每年会增加几百万甚至更多的新生儿，这将一定程度上减缓我国未来人口下降的速度。","title":"二胎政策放开 掘金新三板概念股","src":"兴业证券·研报"}
     * code : 0
     * msg :
     */

    private DataEntity data;
    private int code;
    private String msg;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataEntity getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static class DataEntity {
        /**
         * url : http://www.jinzht.com:8000/phone/sanban/8630
         * content : 我国的总人口都将会在2023年左右开始下降。但是实行全面二孩政策，每年会增加几百万甚至更多的新生儿，这将一定程度上减缓我国未来人口下降的速度。
         * title : 二胎政策放开 掘金新三板概念股
         * src : 兴业证券·研报
         */

        private String url;
        private String content;
        private String title;
        private String src;
        private  String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getUrl() {
            return url;
        }

        public String getContent() {
            return content;
        }

        public String getTitle() {
            return title;
        }

        public String getSrc() {
            return src;
        }
    }
}
