package com.jinzht.pro.eventbus;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MainNoticeEvent {
    private String msg;

    public MainNoticeEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
