package com.hc360.dataweb.model
/***
 * 整体趋势每日的数据PO
 */
class DayChartBean {
    String name;
    List<Double> data;
    String unit;
    boolean isShow = true;//判断是否显示图例{仅产品运营数据页面}
    DayChartBean() {
        super();
    }
    DayChartBean(String name, String unit) {
        this.name = name
        this.unit = unit
    }
}
