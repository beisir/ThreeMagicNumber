package com.hc360.dataweb.model

/**
 * Created by home on 2017/2/6.
 * 主要的四大模块： ip, pv ,uv, 收费会员
 */
class MainBean {
    String name;
    String num;
//    int flag
    MainBean(){
        super();
    }
    MainBean(String name, String num) {
        this.name = name
        this.num = num
    }
}
