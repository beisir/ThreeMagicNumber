package com.hc360.dataweb.model

/**
 * Created by home on 2018/12/28.
 */
class PackedBubble {
    String name
    Object value
    String unit
    PackedBubble(String name,Object value,String unit){
        this.name=name;
        this.unit=unit
        this.value=value
    }
}
