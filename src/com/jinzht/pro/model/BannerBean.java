package com.jinzht.pro.model;

import java.util.List;


/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * @auther Mr Jobs
 * @date 2015/7/11
 * @time 21:00
 */
public class BannerBean {

    /**
     * status : 0
     * data : [{"img":"http://www.jinzht.com:8000/media/banner/img/2015/08/9372cf14f0a64f7ba842db54fb9ab9a7.jpg","url":"http://www.jinzht.com/app/news/6899/","project":null},{"img":"http://www.jinzht.com:8000/media/banner/img/2015/08/33033bfa87ea470088d88d2989a33a85.jpg","url":"http://www.jinzht.com/","project":null},{"img":"http://www.jinzht.com:8000/media/banner/img/2015/08/f644e0c03f38497689831f47b65a2ea3.jpg","url":"http://www.jinzht.com/","project":1},{"img":"http://www.jinzht.com:8000/media/banner/img/2015/08/68a7345441f14c13ab301721d5f40d6c.jpg","url":"","project":null}]
     * msg :
     */

    private int code;
    private String msg;
    private List<DataEntity> data;

    public void setStatus(int status) {
        this.code = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getStatus() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * img : http://www.jinzht.com:8000/media/banner/img/2015/08/9372cf14f0a64f7ba842db54fb9ab9a7.jpg
         * url : http://www.jinzht.com/app/news/6899/
         * project : null
         */

        private String img;
        private String url;
        private Object project;

        public void setImg(String img) {
            this.img = img;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setProject(Object project) {
            this.project = project;
        }

        public String getImg() {
            return img;
        }

        public String getUrl() {
            return url;
        }

        public Object getProject() {
            return project;
        }
    }
}
