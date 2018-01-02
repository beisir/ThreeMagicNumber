package com.hc360.dataweb.model;

public class RealTimeMonthWeekBase {
    private String day;

    private String weekNum;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    public String getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(String weekNum) {
        this.weekNum = weekNum;
    }
}