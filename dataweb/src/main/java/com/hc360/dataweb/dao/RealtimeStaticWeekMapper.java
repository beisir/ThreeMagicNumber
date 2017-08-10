package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.RealtimeStaticDoubleWeek;
import com.hc360.dataweb.model.RealtimeStaticWeek;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RealtimeStaticWeekMapper {

    /**
     * 获取周表最近时间的数据
     * @param param
     * @return
     */
    List<RealtimeStaticWeek> findWeekData(Map<String, Object> param) throws Exception;

    /**
     * 获取累计三十天的数据(根据flag判断查询前n条数据)
     * @param param
     * @return
     * @throws Exception
     */
    List<RealtimeStaticWeek> findWeekData30Day(Map<String, Object> param) throws Exception;
    /**
     * 根据flag判断查询前n条数据(返回结果为Double)
     * @param param
     * @return
     * @throws Exception
     */
    List<RealtimeStaticDoubleWeek> findWeekDataDoubleDay(Map<String, Object> param) throws Exception;

    /**
     * 获取当前年度53个周的数据
     * @param param
     * @return
     * @throws Exception
     */
    List<RealtimeStaticWeek> findYearWeekData(Map<String, Object> param) throws Exception;
    /**
     * 获取当前周每一天的数据
     * @param param
     * @return
     * @throws Exception
     */
    List<RealtimeStaticDoubleWeek> findMonthWeekData(Map<String, Object> param) throws Exception;
    /**
     * 获取最近n天的数据(Double)
     * @param param
     * @return
     * @throws Exception
     */
    List<RealtimeStaticDoubleWeek> findDayData(Map<String, Object> param) throws Exception;
    /**
     * 根据最近数据时间获取数据
     * @param param
     * @return
     * @throws Exception
     */
    List<RealtimeStaticWeek> findWeekDataByDataDate(Map<String, Object> param) throws Exception;
    /**
     * 根据所有数据类型获取最大的周
     * @param param
     * @return
     * @throws Exception
     */
    String findWeekRecentlyIrsl_date(Map<String, Object> param) throws Exception;
    /**
     * 根据实际值的时间查询预估值的数据
     * @param param {type:data_type,list:[实际值对应周,20170402 2017年4月2周]}
     * @return
     * @throws Exception
     */
    List<RealtimeStaticDoubleWeek> findEstimatesByPractical(Map<String, Object> param) throws Exception;

}