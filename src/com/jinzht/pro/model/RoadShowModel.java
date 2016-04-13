package com.jinzht.pro.model;

import java.util.List;

/**
 * 世上唯有贫穷可以不劳而获。
 *
 * @auther Mr.Jobs
 * @date 2015/8/25
 * @time 16:20
 */

public class RoadShowModel {

    private List<data> datas ;
    private int status;
    private String msg;
    public void setData(List<data> data){
        this.datas = data;
    }
    public List<data> getData(){
        return this.datas;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public class data {
        private String img;

        private String url;

        public void setImg(String img){
            this.img = img;
        }
        public String getImg(){
            return this.img;
        }
        public void setUrl(String url){
            this.url = url;
        }
        public String getUrl(){
            return this.url;
        }

    }
}
