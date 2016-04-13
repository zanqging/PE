package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/9/22.
 */
public class SystemNoticeBean {

    /**
     * msg :
     * data : [{"id":3,"create_datetime":"2015-09-21 11:54:49","extras":{"_id":null,"url":"http://www.jinzht.com:8000/app/news/6788","api":"web"},"read":null,"content":"最新资讯"}]
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
         * id : 3
         * create_datetime : 2015-09-21 11:54:49
         * extras : {"_id":null,"url":"http://www.jinzht.com:8000/app/news/6788","api":"web"}
         * read : null
         * content : 最新资讯
         */

        private int id;
        private String create_datetime;
        private ExtrasEntity extras;
        private Object read;
        private String content;
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setCreate_datetime(String create_datetime) {
            this.create_datetime = create_datetime;
        }

        public void setExtras(ExtrasEntity extras) {
            this.extras = extras;
        }

        public void setRead(Object read) {
            this.read = read;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public String getCreate_datetime() {
            return create_datetime;
        }

        public ExtrasEntity getExtras() {
            return extras;
        }

        public Object getRead() {
            return read;
        }

        public String getContent() {
            return content;
        }

        public static class ExtrasEntity {
            /**
             * _id : null
             * url : http://www.jinzht.com:8000/app/news/6788
             * api : web
             */

            private Object _id;
            private String url;
            private String api;

            public void set_id(Object _id) {
                this._id = _id;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setApi(String api) {
                this.api = api;
            }

            public Object get_id() {
                return _id;
            }

            public String getUrl() {
                return url;
            }

            public String getApi() {
                return api;
            }
        }
    }
}
