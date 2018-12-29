package com.hc360.dataweb.model

/**
 * Created by home on 2018/12/29.
 */
class MapBean {
    String fullname
    Object value
    String unit
    MapBean(String fullname,Object value,String unit){
        this.fullname=fullname;
        this.unit=unit
        this.value=value
    }
}
