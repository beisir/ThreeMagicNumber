package com.hc360.dataweb.model
/***
 * 整体趋势每日的数据PO
 */
class HourChartBean {
    String name;
    List<Object> data;
    String unit;
    boolean isShow=true;//判断是否显示图例{仅产品运营数据页面}

    HourChartBean() {
        super();
    }

    HourChartBean(String name, String unit) {
        this.name = name
        this.unit = unit
        isShow = true;
    }
}
