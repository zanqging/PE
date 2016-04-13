package com.jinzht.pro.model;

import java.util.List;

/**
 * 代码有问题别找我！虽然是我写的，但是它们自己长歪了。
 *
 * @auther Mr Jobs
 * @date 2015/9/9
 * @time 21:47
 */
public class ThreeJudgeBean {

    /**
     * data : ["国内","国外","投融资"]
     * msg : 新闻关键词
     * status : 0
     */

    private String msg;
    private int status;
    private List<String> data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getData() {
        return data;
    }
}
