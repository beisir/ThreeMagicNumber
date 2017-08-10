package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.RealTimeStaticDay;
import com.hc360.dataweb.model.RealTimeStaticDoubleDay;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface RealTimeStaticDayMapper {
    /**
     * 查询ip/pv/uv三种数据的当日的最新和。
     * @param day 当天时间，格式yyyyMMdd
     * @return
     */
    List<RealTimeStaticDay> findUserBehaviorByToday(@Param("day")String day)throws Exception;


    List<RealTimeStaticDay>  findUserBehaviorByYesterday(@Param("day")String day,@Param("yesterday")String yesterday)throws Exception;

    /**
     * 查询ip/pv/uv三种数据的一段时间的数据。以日为纬度
     * @param startDay 开始时间。格式yyyyMMdd
     * @param endDay   结束时间。格式yyyyMMdd
     * @return
     */
    List<RealTimeStaticDay> findUserBehaviorBy60Day(@Param("startDay")String startDay , @Param("endDay")String endDay)throws Exception;

    /**
     * 根据data_Type获取前一天的实时数据
     *@param param
     * @return
     */
    List<RealTimeStaticDay> findRealTimeDataYester( Map<String,Object> param)throws Exception;

    /**
     * 根据data_type获取当天的实时数据
     //* @param dataTypes
     * @param param
     * @return
     */
    List<RealTimeStaticDay> findRealTimeDataToday( Map<String,Object> param)throws Exception;
    /**
     * 根据data_type获取周/月/全部的数据
     * @param param 时间传入格式为 yyyyMMdd
     * @return
     */
    List<RealTimeStaticDay> findDayData(Map<String, Object> param)throws Exception;
    /**
     * 根据data_type获取周/月/全部的数据
     * @param param 时间传入格式为 yyyyMMdd
     * @return
     */
    List<RealTimeStaticDay>  findDayData2(Map<String, Object> param)throws Exception;
    /**
     * 查询P4P消耗/人均有效通话次数当天的实时数据
     * @param type
     * @param day
     * @return double类型的数据
     */
    List<RealTimeStaticDoubleDay> findRealTimeDoubleDataToday(Map<String, Object> param)throws Exception;

    /**
     * 查询P4P消耗昨天的实时数据
     * @param type
     * @param day
     * @param yesterDay
     * @return
     */
    List<RealTimeStaticDoubleDay> findRealTimeDoubleDataYester(Map<String, Object> param)throws Exception;

    /**
     * 查询P4P消耗周/月/全部的数据
     * @param param
     * @return
     */
    List<RealTimeStaticDoubleDay> findDayDoubleData(Map<String, Object> param)throws Exception;

    /**
     * 获取昨天的最后一条数据
     * @param param2
     * @return
     */
    List<RealTimeStaticDay> findRealTimeLastDataYester(Map<String, Object> param2)throws Exception;


    /**
     * 根据数据类型分组获取整张表最新时间的数据(使用irsl_date每个date_type对应一个日期yyyymmdd)
     * @param param
     * @return
     * @throws Exception
     */
    List<RealTimeStaticDay> findRealTimeDataNewest(Map<String, Object> param)throws Exception;

    /**
     * 按照irsl_date倒叙取第二条数据
     * @param param
     * @return
     * @throws Exception
     */
    List<RealTimeStaticDay> findRealTimeDataSecond(Map<String, Object> param)throws Exception;

    /**
     * 根据最新的op_date获取数据(多个type)
     * @param param
     * @throws Exception
     */
    List<RealTimeStaticDay> findLastDataByOpDate(Map<String, Object> param)throws Exception;
    /**
     * 获取百家文章数每一天的收入
     * @param param
     * @throws Exception
     */
    List<RealTimeStaticDoubleDay> findEveryDayIncome(Map<String, Object> param)throws Exception;
    /**
     * 获取百度联盟当月每天的收入
     * @param param
     * @throws Exception
     */
    List<RealTimeStaticDoubleDay> findMonthDayDoubleData(Map<String, Object> param)throws Exception;
}