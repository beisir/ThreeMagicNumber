package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.RealtimeStaticMonth;
import com.hc360.dataweb.model.RealtimeStaticWeek;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RealtimeStaticMonthMapper {

    /**
     * 获取当前月份今天/昨天最后时间的数据
     * @param param {data_type,day:yyyyMMdd,month:yyyyMM}
     * @return
     */
    List<RealtimeStaticMonth> findRealTimeDataMonth(Map<String, Object> param) throws Exception;
    /**
     * 获取当前月份今天/昨天最后时间的数据
     * @param param {List<data_type>,day:yyyyMMdd,month:yyyyMM}
     * @return
     */
    List<RealtimeStaticMonth> findRealTimeDatasMonth(Map<String, Object> param) throws Exception;

    /**
     * 根据数据类型以及当前年份获取本年度每个月的数据
     * @param param
     * @return
     * @throws Exception
     */
    List<RealtimeStaticMonth> fingYearMonthData(Map<String, Object> param)throws Exception;
    List<RealtimeStaticMonth> fingYearMonthDatanew(Map<String, Object> param)throws Exception;
    List<RealtimeStaticMonth> fingYearStartMonthData(Map<String, Object> param)throws Exception;
    /**
     * 根据最新数据时间获取数据
     * @param param
     * @return
     * @throws Exception
     */
    List<RealtimeStaticMonth> findMonthDataByDataDate(Map<String, Object> param)throws Exception;

    /**
     *查找每个月月份最后一天的数据
     * @param param
     * @return
     * @throws Exception
     */
    List<RealtimeStaticMonth>  findMonthDataByIrsl_date(Map<String, Object> param)throws Exception;
    /**
     * 获取最新月份
     * @param param
     * @return
     * @throws Exception
     */
    String findMonthRecentlyIrsl_date(Map<String, Object> param)throws Exception;
    /**
     * 根据月份&数据类型获取数据(没有分组排重)
     * @param param
     * @return
     * @throws Exception
     */
    List<RealtimeStaticMonth> findEstimatesByPractical(Map<String, Object> param)throws Exception;

    List<RealtimeStaticMonth> findEstimatesByPracticalnew(Map<String, Object> param)throws Exception;

    List<RealtimeStaticMonth> findYearMonthData(Map<String, Object> param);
    /**
     * 根据当前时间获取前N个月的数据，包括当前月
     * */
    List<RealtimeStaticMonth> findLastNMonthsData(Map<String, Object> param);
}
