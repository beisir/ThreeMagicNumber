package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.RealTimeMonthWeekBase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RealTimeMonthWeekBaseMapper {
    /**
     *
     * @param day 当前日期 格式: yyyy-MM-dd
     * @param weeks 周数
     * @return 获取某日期之前的前几周
     */
    List<RealTimeMonthWeekBase> findMonthWeekBase(@Param("day") String day,@Param("weeks")int weeks);
}