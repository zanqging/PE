package com.jinzht.pro.model;



/**
 * Created by Administrator on 2015/9/14.
 */
public class NewsShareBean {


    /**
     * data : {"url":"http://www.jinzht.com:8000/phone/sanban/8630","content":"�ҹ������˿ڶ�������2023�����ҿ�ʼ�½�������ʵ��ȫ��������ߣ�ÿ������Ӽ�����������������������⽫һ���̶��ϼ����ҹ�δ���˿��½����ٶȡ�","title":"��̥���߷ſ� �������������","src":"��ҵ֤ȯ���б�"}
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
         * content : �ҹ������˿ڶ�������2023�����ҿ�ʼ�½�������ʵ��ȫ��������ߣ�ÿ������Ӽ�����������������������⽫һ���̶��ϼ����ҹ�δ���˿��½����ٶȡ�
         * title : ��̥���߷ſ� �������������
         * src : ��ҵ֤ȯ���б�
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
