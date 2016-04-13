package com.jinzht.pro.model;

import java.util.List;


/**
 * Created by Administrator on 2015/11/12.
 */
public class CreditBean {

    /**
     * code : 0
     * msg :
     * data : [{"title":"<em>金指投<\/em>","url":"http://www.baidu.com/link?url=Cw_-eW844Yso_4zJlto2ASVbfO2_QZH6hCPrgha-88C","content":"Toggle navigation 主页 <em>金指投<\/em>APP 投融资介绍 加入我们}  下载 ..."}]
     */

    private int code;
    private String msg;
    private List<DataEntity> data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getCode() {
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
         * title : <em>金指投</em>
         * url : http://www.baidu.com/link?url=Cw_-eW844Yso_4zJlto2ASVbfO2_QZH6hCPrgha-88C
         * content : Toggle navigation 主页 <em>金指投</em>APP 投融资介绍 加入我们}  下载 ...
         */

        private String title;
        private String url;
        private String content;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getContent() {
            return content;
        }
    }
}
