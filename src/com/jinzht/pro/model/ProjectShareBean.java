package com.jinzht.pro.model;


/**
 * Created by Administrator on 2015/11/23.
 */
public class ProjectShareBean {

    /**
     * data : {"title":"项目分享","img":"http://www.jinzht.com/static/app/img/icon.png","content":"项目分享","url":"http://a.app.qq.com/o/simple.jsp?pkgname=com.jinzht.pro"}
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
         * title : 项目分享
         * img : http://www.jinzht.com/static/app/img/icon.png
         * content : 项目分享
         * url : http://a.app.qq.com/o/simple.jsp?pkgname=com.jinzht.pro
         */

        private String title;
        private String img;
        private String content;
        private String url;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public String getImg() {
            return img;
        }

        public String getContent() {
            return content;
        }

        public String getUrl() {
            return url;
        }
    }
}
