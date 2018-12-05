package com.hc360.dataweb.model

/**
 * Created by home on 2018/12/5.
 */
class LineBean {
    String name
    List<Double> data
    String unit;

    LineBean(String name ,List<Double> data,String unit){
        this.name= name
        this.data=data
        this.unit = unit
    }
}
