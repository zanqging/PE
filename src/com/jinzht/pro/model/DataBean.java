package com.jinzht.pro.model;


/**
 * Created by Administrator on 2015/11/23.
 */
public class DataBean {

    /**
     * code : 0
     * msg :
     * data : 协议任何一方对对方的保密信息（包括但不限于平台的技术秘密、商业秘密、管理信息、资源，投资人的注册资料等基本信息）及本协议所涉保密事项承担同等保密义务。未经协议双方许可不得擅自将有关信息、资料披露给第三方（根据法律要求履行必要信息披露义务除外）。如因违反本保密义务给对方造成经济损失的，违约方应予以赔偿。

     */

    private int code;
    private String msg;
    private String data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getData() {
        return data;
    }
}
