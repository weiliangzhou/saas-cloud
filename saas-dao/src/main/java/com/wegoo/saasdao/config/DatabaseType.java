package com.wegoo.saasdao.config;

/**
 * ${列出数据源类型}
 * author 二师兄超级帅
 * create 2018-06-29 13:34
 **/
public enum DatabaseType {

    master("write"), slave("read");


    DatabaseType(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DatabaseType{" +
                "name='" + name + '\'' +
                '}';
    }
}