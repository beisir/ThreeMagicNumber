package com.hc360.dataweb.model

/**
 * Created by home on 2018/11/21.
 */
class DrillDownBean {
    String name;
    String[] categories;
    Object[] data;
    DrillDownBean(String name, String[] categories,Object[] data){
        this.name=name;
        this.categories=categories;
        this.data=data;
    }
}
