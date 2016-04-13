package com.jinzht.pro.model;

import java.util.List;


/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * @auther Mr Jobs
 * @date 2015/9/9
 * @time 22:50
 */
public class NewsBean {

    /**
     * data : [{"content":"\u200b�����Ž�Ͷ�Ļ�ů����������ָ��11��4��Ҳ���ȷ�������������ָ����С���Ϳ������ݺ��̺�һ�ȿ�����ˮ���ڴ���1320.96������Ȼ�����β��ָ������ֱ���������ձ�1325.82�㣬����0.16%��","id":27,"read":0,"title":"�����彻Ͷ���ֻ�ů �ɽ���7.74��Ԫ","url":"http://www.jinzht.com:8000/phone/xinwei/8680/","share":0,"create_datetime":"11����ǰ","img":"http://www.sanban18.com/data/upload/2015/1105/08/563a9fee5d51f_180_120.jpg","src":"�й�֤ȯ������֤��"},{"content":"\u200b\u201c����г��������ԣ����Ż�Э��ת���ƶȣ�������չ����ҵ�������г�Ч����ƣ����������̼Ӵ�����Ͷ�롣\u201dȫ����С��ҵ�ɷ�ת��ϵͳ�������ι�˾���ܾ�����ǿ����̳�ϱ�ʾ��","id":26,"read":0,"title":"������ʵʩ��ʤ��̭�� �ֲ��ժ����ʱ�Ƴ�","url":"http://www.jinzht.com:8000/phone/xinwei/8683/","share":0,"create_datetime":"11����ǰ","img":"http://www.sanban18.com/data/upload/2015/1105/08/563aa642deb81_180_120.jpg","src":"�����ձ�"},{"content":"���ȹɽ��ڹ�Ȩ���ʺ�ծȯ����֮�䣬��������ռ��??�������������ȹ��Ե����취�����й�˾���÷��п�ת��Ϊ��ͨ�ɵ����ȹɣ����������ȯ�̺͹�˾��ҵ������û�еó�һ�½��ۡ�","id":25,"read":0,"title":"������ծȨ��������һ����˳ ������������","url":"http://www.jinzht.com:8000/phone/xinwei/8681/","share":0,"create_datetime":"11����ǰ","img":"http://www.sanban18.com/data/upload/2015/1105/08/563aa1afdc617_180_120.jpg","src":"21���;��ñ���"},{"content":"������ҵ��4ǧ�ң�����ֵ1.66����Ԫ���������ʶ800��Ԫ������һ��ʱ���5��Ԫ���ҵ��ճɽ����ù�����ҵ��Ͷ���ߡ�ȫ����תϵͳ���г���������ѹ����","id":3,"read":2,"title":"���������������ǳ��� ��������Ԥ����׳�̨","url":"http://www.jinzht.com:8000/phone/xinwei/8496/","share":0,"create_datetime":"3��ǰ","img":"http://www.sanban18.com/data/upload/2015/1101/09/56356f0600252_180_120.jpg","src":"�»���"},{"content":"Ŀǰ�г��ϵ������������500�����ȫ��˽ļ������Ȼ�����������д����������Ҳ����Ԫ����������������Ļ�ů������˽ļ���𣬻�����Ͷ�ʹ�ļ�������С�ȯ�̷��е���������Ʋ�Ʒ��","id":2,"read":4,"title":"Ͷ��������ԭʼ�� ���ַ�ʽ��ȡ����","url":"http://www.jinzht.com:8000/phone/xinwei/8497/","share":0,"create_datetime":"3��ǰ","img":"http://www.sanban18.com/data/upload/2015/1101/09/563570bd0e9b9_180_120.jpg","src":"������"},{"content":"2015��������������һֱǣ�����ʱ��г����񾭣������Գ���ҵ����Ϊ���ĵĻ��⣬�����г�������ֲ���ժ�Ƶ�������������Ƴ�������������ӭ����һ���ƶȺ�����","id":1,"read":0,"title":"�����嵹���������Ԫ���� ӭ��󲢹�ʱ��","url":"http://www.jinzht.com:8000/phone/xinwei/8498/","share":0,"create_datetime":"3��ǰ","img":"http://www.sanban18.com/data/upload/2015/1101/12/563596a7e3795_180_120.jpg","src":"����������"}]
     * code : 0
     * msg : �������
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
         * content : ?�����Ž�Ͷ�Ļ�ů����������ָ��11��4��Ҳ���ȷ�������������ָ����С���Ϳ������ݺ��̺�һ�ȿ�����ˮ���ڴ���1320.96������Ȼ�����β��ָ������ֱ���������ձ�1325.82�㣬����0.16%��
         * id : 27
         * read : 0
         * title : �����彻Ͷ���ֻ�ů �ɽ���7.74��Ԫ
         * url : http://www.jinzht.com:8000/phone/xinwei/8680/
         * share : 0
         * create_datetime : 11����ǰ
         * img : http://www.sanban18.com/data/upload/2015/1105/08/563a9fee5d51f_180_120.jpg
         * src : �й�֤ȯ������֤��
         */

        private String content;
        private int id;
        private int read;
        private String title;
        private String url;
        private int share;
        private String create_datetime;
        private String img;
        private String src;

        public void setContent(String content) {
            this.content = content;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setRead(int read) {
            this.read = read;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setShare(int share) {
            this.share = share;
        }

        public void setCreate_datetime(String create_datetime) {
            this.create_datetime = create_datetime;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getContent() {
            return content;
        }

        public int getId() {
            return id;
        }

        public int getRead() {
            return read;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public int getShare() {
            return share;
        }

        public String getCreate_datetime() {
            return create_datetime;
        }

        public String getImg() {
            return img;
        }

        public String getSrc() {
            return src;
        }
    }
}
