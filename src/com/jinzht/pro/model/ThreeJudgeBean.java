package com.jinzht.pro.model;

import java.util.List;

/**
 * ��������������ң���Ȼ����д�ģ����������Լ������ˡ�
 *
 * @auther Mr Jobs
 * @date 2015/9/9
 * @time 21:47
 */
public class ThreeJudgeBean {

    /**
     * data : ["����","����","Ͷ����"]
     * msg : ���Źؼ���
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
