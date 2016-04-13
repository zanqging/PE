package com.jinzht.pro.model;

/**
 * Created by pc on 2016/3/16.
 * 易宝投标扩展业务
 */
public class ToCpTransactionPropertyBean {
    private String name;
    private String value;

    public ToCpTransactionPropertyBean() {
    }

    public ToCpTransactionPropertyBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
