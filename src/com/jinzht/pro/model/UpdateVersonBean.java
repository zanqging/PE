package com.jinzht.pro.model;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/17
 * @time 10:36
 */

public class UpdateVersonBean {
    private String edition;
    private int code;
    private String url;
    private String item;
    private boolean force;
    public UpdateVersonBean(String edition, String url, String item,boolean force) {
        this.edition = edition;
        this.url = url;
        this.item = item;
        this.force = force;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
