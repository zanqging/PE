package com.jinzht.pro.eventbus;

/**
 * Created by Administrator on 2015/11/30.
 */
public class JPushEvent {
    private String msg;

    public JPushEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
