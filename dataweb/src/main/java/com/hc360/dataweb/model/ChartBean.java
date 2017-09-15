package com.hc360.dataweb.model;

/**
 * Created by zhangwanru on 2017/2/22.
 */
public class ChartBean {
    String type;//图表的类型
    String time;//当前图表的时间阶段

    @Override
    public String toString() {
        return "ChartBean{" +
                "type='" + type + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
