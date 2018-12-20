package com.hc360.dataweb.model

/**
 * Created by home on 2018/12/20.
 */
class VennBean {
    Object[] sets;
    Object value;
    String name;
    Object percentage  //真实数值。 因为数据太大的时候，venn图就显示异常，所以添加了一个缩放比例，和真实的值
    VennBean(Object[] sets,Object value,Object percentage){
        this.sets=sets
        this.value=value
        this.percentage=percentage
    }
}
