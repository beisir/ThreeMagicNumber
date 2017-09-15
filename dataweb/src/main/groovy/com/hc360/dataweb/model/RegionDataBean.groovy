package com.hc360.dataweb.model

/**
 * Created by zhangwanru on 2017/5/8.  
 */
class RegionDataBean {
    String fullname;
    Long value;
    Long yestodayvalue;

    RegionDataBean() {
        super();
    }

    RegionDataBean(String fullname, Long value, Long yestodayvalue) {
        this.fullname = fullname
        this.value = value
        this.yestodayvalue = yestodayvalue
    }

    @Override
    public String toString() {
        return "RegionDataBean{" +
                "fullname='" + fullname + '\'' +
                ", value=" + value +
                ", yestodayvalue=" + yestodayvalue +
                '}';
    }
}
