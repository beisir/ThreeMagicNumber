package com.hc360.dataweb.model

/**
 * Created by home on 2018/11/26.
 */
class P4pBean {
    String name;
    String num;
//    int flag
    P4pBean(){
        super();
    }
    P4pBean(String name, String num ) {
        this.name = name
        this.num = num

    }

    @Override
    public String toString() {
        return "P4pBean{" +
                "name='" + name + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
