package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.RealTimeStaticDay;
import com.hc360.dataweb.model.RealTimeStaticHour;
import com.hc360.dataweb.model.RealTimeStaticDoubleHour;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface RealTimeStaticHourMapper {

    /**
     * 查询非ip、pv、uv的当天的总数。
     * ip、pv、uv的总数在sjpt_realtime_static_day表中查询（因为每小时的累加不一定等于每日的总和）。
     * @param param 当天的日期，格式为 yyyyMMdd list:data_type
     * @return
     */
    List<RealTimeStaticHour> findAllByDay(Map<String, Object> param);

    List<RealTimeStaticHour>  findAllByYesterDay(@Param("day")String day , @Param("yesterday")String yesterday);
    /***
     * 查询ip、pv、uv、收费会员每个小时的数据。
     * @param day 当天的日期，格式为 yyyyMMdd
     * @return
     */
    List<RealTimeStaticHour> findAllByHour(@Param("day")String day);

    /***
     *查询一段时间内的收费会员的数据。以日为纬度。
     * @param startDay 开始日期，格式为 yyyyMMdd
     * @param endDay  结束日期，格式为 yyyyMMdd
     * @return
     */
    List<RealTimeStaticHour> findFeeUseBy60Day(@Param("startDay")String startDay , @Param("endDay")String endDay);

    /**
     * 根据data_type获取当天每个小时的数据
     * @param param 时间传入格式为 yyyyMMdd
     * @return
     */
    List<RealTimeStaticHour> findTodayData(Map<String, Object> param);

    /**
     * 根据多个data_type获取当天每个小时的数据
     * @param param
     * @return
     */
    List<RealTimeStaticHour>findAllByDay2(Map<String, Object> param);


    /**
     * 查询当天的每个小时的数据
     * @param param
     * @return
     */
    List<RealTimeStaticDoubleHour> findDoubleTodayData(Map<String, Object> param);
    /**
     * 查询当天的每个小时的数据 : 将mip站的数据累加到原有的IP,PV,UV上2018.03.21
     * @param param
     * @return
     */
    List<RealTimeStaticDoubleHour> findDoubleTodayDataMip(Map<String, Object> param);
    /**
     * N天的 最后一个小时的数据
     * @param param :day 日期， list，数据类型的list preday: 起始日期
     * @return
     */
    List<RealTimeStaticDoubleHour> findDoubleByDay(Map<String, Object>  param);

    List<RealTimeStaticDoubleHour> findDoubleByType(Map<String, Object>  param);
    /***
     * 某天的 小时数据
     * @param param : day 日期， list，数据类型的list
     * @return
     */
    List<RealTimeStaticDoubleHour> findDoubleByTodayDay(Map<String, Object> param);

    /**
     * 查看N天的数据
     * @param param
     * @return
     */
    List<RealTimeStaticHour>  findAllBy30Day (Map<String, Object> param);
    /**
     * 获取昨天的最后一条数据
     * @param param2
     * @return
     */
    List<RealTimeStaticHour> findRealTimeLastDataYester(Map<String, Object> param2)throws Exception;

    /**
     * 获取昨天的某一小时的一条数据
     * @param param2
     * @return
     */
    List<RealTimeStaticHour> findDoubleByHour(Map<String, Object> param2)throws Exception;
}
