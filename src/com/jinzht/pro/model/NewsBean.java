package com.jinzht.pro.model;

import java.util.List;


/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * @auther Mr Jobs
 * @date 2015/9/9
 * @time 22:50
 */
public class NewsBean {

    /**
     * data : [{"content":"\u200b伴随着交投的回暖，三板做市指数11月4日也企稳反弹。三板做市指数在小幅低开及短暂横盘后一度快速跳水，在触底1320.96点后企稳回升，尾盘指数更是直线拉升，收报1325.82点，上涨0.16%。","id":27,"read":0,"title":"新三板交投呈现回暖 成交额7.74亿元","url":"http://www.jinzht.com:8000/phone/xinwei/8680/","share":0,"create_datetime":"11分钟前","img":"http://www.sanban18.com/data/upload/2015/1105/08/563a9fee5d51f_180_120.jpg","src":"中国证券报·中证网"},{"content":"\u200b\u201c针对市场的流动性，将优化协议转让制度，大力发展做市业务，完善市场效益机制，引导做市商加大做市投入。\u201d全国中小企业股份转让系统有限责任公司副总经理隋强在论坛上表示。","id":26,"read":0,"title":"新三板实施优胜劣汰制 分层和摘牌适时推出","url":"http://www.jinzht.com:8000/phone/xinwei/8683/","share":0,"create_datetime":"11分钟前","img":"http://www.sanban18.com/data/upload/2015/1105/08/563aa642deb81_180_120.jpg","src":"北京日报"},{"content":"优先股介于股权融资和债券融资之间，是有想象空间的??但按照现行优先股试点管理办法，上市公司不得发行可转换为普通股的优先股，因此许多机构券商和公司企业现在仍没有得出一致结论。","id":25,"read":0,"title":"新三板债权融资难现一帆风顺 大象狂舞待检验","url":"http://www.jinzht.com:8000/phone/xinwei/8681/","share":0,"create_datetime":"11分钟前","img":"http://www.sanban18.com/data/upload/2015/1105/08/563aa1afdc617_180_120.jpg","src":"21世纪经济报道"},{"content":"挂牌企业近4千家，总市值1.66万亿元，今年融资额超800亿元。但近一段时间仅5亿元左右的日成交金额，让挂牌企业、投资者、全国股转系统等市场各方倍感压力。","id":3,"read":2,"title":"新三板流动性仍是掣肘 利好政策预计年底出台","url":"http://www.jinzht.com:8000/phone/xinwei/8496/","share":0,"create_datetime":"3天前","img":"http://www.sanban18.com/data/upload/2015/1101/09/56356f0600252_180_120.jpg","src":"新华网"},{"content":"目前市场上的新三板基金有500多个，全是私募基金；虽然今年以来股市大跌令新三板也大伤元气，但是随着最近的回暖，除了私募基金，还可以投资公募基金、信托、券商发行的新三板理财产品。","id":2,"read":4,"title":"投资新三板原始股 三种方式各取所需","url":"http://www.jinzht.com:8000/phone/xinwei/8497/","share":0,"create_datetime":"3天前","img":"http://www.sanban18.com/data/upload/2015/1101/09/563570bd0e9b9_180_120.jpg","src":"新民晚报"},{"content":"2015年以来，新三板一直牵动着资本市场的神经，流动性成了业内颇为关心的话题，近期市场上热议分层与摘牌等配套政策年底推出，新三板有望迎来新一波制度红利。","id":1,"read":0,"title":"新三板倒逼人民币美元基金 迎最大并购时代","url":"http://www.jinzht.com:8000/phone/xinwei/8498/","share":0,"create_datetime":"3天前","img":"http://www.sanban18.com/data/upload/2015/1101/12/563596a7e3795_180_120.jpg","src":"新三板在线"}]
     * code : 0
     * msg : 加载完毕
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
         * content : ?伴随着交投的回暖，三板做市指数11月4日也企稳反弹。三板做市指数在小幅低开及短暂横盘后一度快速跳水，在触底1320.96点后企稳回升，尾盘指数更是直线拉升，收报1325.82点，上涨0.16%。
         * id : 27
         * read : 0
         * title : 新三板交投呈现回暖 成交额7.74亿元
         * url : http://www.jinzht.com:8000/phone/xinwei/8680/
         * share : 0
         * create_datetime : 11分钟前
         * img : http://www.sanban18.com/data/upload/2015/1105/08/563a9fee5d51f_180_120.jpg
         * src : 中国证券报·中证网
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
