package com.jinzht.pro.model;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/20
 * @time 15:13
 */

public class ShareBean {
    private String url;
    private String img;
    private String content;

    public ShareBean(String url, String img, String content) {
        this.url = url;
        this.img = img;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
