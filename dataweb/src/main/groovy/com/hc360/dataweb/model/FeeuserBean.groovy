package com.hc360.dataweb.model

/**
 * Created by home on 2017/2/6.
 * 收费会员模块的po
 */
class FeeuserBean {
    String name;
    String num;
    int flag

    FeeuserBean(String name, String feeuserNum ,int flag ) {
        this.name = name
        this.num = feeuserNum
        this.flag = flag
    }

    @Override
    public String toString() {
        return "FeeuserBean{" +
                "name='" + name + '\'' +
                ", num='" + num + '\'' +
                ", flag=" + flag +
                '}';
    }
}
