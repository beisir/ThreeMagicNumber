package com.hc360.dataweb.model

/**
 * Created by home on 2017/2/6.
 */
class FightCapacityOneBean {
    String name;
    String num;
    //计算标志 1 减法， 0 除法
    int flag;

    FightCapacityOneBean(String name, String num,int flag) {
        this.name = name
        this.num = num
        this.flag=flag
    }

    @Override
    public String toString() {
        return "FightCapacityOneBean{" +
                "name='" + name + '\'' +
                ", num='" + num + '\'' +
                ", flag=" + flag +
                '}';
    }
}
