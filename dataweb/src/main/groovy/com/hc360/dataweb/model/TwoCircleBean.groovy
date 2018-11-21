package com.hc360.dataweb.model

/**
 * Created by home on 2018/11/21.
 */
class TwoCircleBean {
    Object y;
    int color;
    DrillDownBean drillDownBean;
    TwoCircleBean(Object y ,int color,DrillDownBean drillDownBean){
        this.y = y ;
        this.color = color;
        this.drillDownBean = drillDownBean;
    }
}
