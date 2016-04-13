package com.jinzht.pro.eventbus;

/**
 * Created by Administrator on 2015/11/30.
 */
public class NoticeEvent {

    private String msg;

    public NoticeEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
