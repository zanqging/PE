package com.jinzht.pro.model;

/**
 * ����Ψ��ƶ����Բ��Ͷ���
 *
 * @auther Mr.Jobs
 * @date 2015/8/29
 * @time 15:07
 */

public class InteractBean {

    /**
     * img : http://www.jinzht.com:8000/media/user/img/2015/08/2aeee8bdd7d8495591a5b3130112a68a.jpg
     * content : �Ҳ���������������Ŷ
     * id : 1
     * name : ��Ǯ��
     */

    private String img;
    private String content;
    private int id;
    private String name;

    public InteractBean(String img, String content, int id, String name) {
        this.img = img;
        this.content = content;
        this.id = id;
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
