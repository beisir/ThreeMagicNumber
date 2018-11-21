package com.hc360.dataweb.model

/**
 * Created by home on 2018/11/21.
 */
class ColumnBean {
    String name
    List<Double> data

    ColumnBean(String name ,List<Double> data){
        this.name= name
        this.data=data
    }

}
