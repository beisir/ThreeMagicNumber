package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.RealTimeMonthWeekBase;

import java.util.List;

public interface RealTimeMonthWeekBaseMapper {
    /**
     *
     * @param day 当前日期 格式: yyyy-MM-dd
     * @param weeks 周数
     * @return 获取某日期之前的前几周
     */
    List<RealTimeMonthWeekBase> findMonthWeekBase(String day,int weeks);
}