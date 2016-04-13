package com.jinzht.pro.eventbus;

/**
 * Created by Administrator on 2015/11/30.
 */
public class NoticeDissmissEvent {
    private String main;

    public NoticeDissmissEvent(String main) {
        this.main = main;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
