package com.hc360.dataweb.model

import com.hc360.dataweb.util.RandomColor

/**
 * Created by home on 2019/1/11.
 */
class LineColorBean {
    String name
    List<Double> data
    String unit;
    String color

    LineColorBean(String name ,List<Double> data,String unit){
        this.name= name
        this.data=data
        this.unit = unit
        this.color =RandomColor.getColor()
    }
}
