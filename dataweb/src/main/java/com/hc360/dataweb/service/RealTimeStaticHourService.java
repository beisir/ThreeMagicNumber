package com.hc360.dataweb.service;

import com.hc360.dataweb.model.ChartBean;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangwanru on 2017/2/22.
 */
public interface RealTimeStaticHourService {

    /**
     * 根据数据类型获取当天的数据_以小时为维度
     * @param otherType
     */
    Map<String, Object> initTodayData(Integer otherType)throws Exception;
    /**
     * 根据数据类型获取周/月/全部的数据_以天为维度
     * @param otherType
     */
    Map<String, Object> initBeforeData(Integer otherType,String time)throws Exception;

    /**
     * 获取第二张图表:今天的数据(查询小时表)
     * @param otherType
     * @return
     */
    Map<String,Object> initSecondTodayData(Integer otherType)throws Exception;
    Map<String,Object> initSecondTodayDataNew(ChartBean chartBean)throws Exception;
    Map<String,Object>  loadP4pScatter(ChartBean chartBean);
    /**
     * 获取第二张图表:周/月/全部数据(查询天表)
     * @param otherType
     * @param time
     * @return
     * @throws Exception
     */
    Map<String,Object> initSecondBeforeData(Integer otherType, String time)throws Exception;

    /**
     * 总览页面:今日趋势图
     * @param otherType
     * @return
     * @throws Exception
     */
    Map<String,Object> initZLTodayData(Integer otherType)throws Exception;

    /**
     * 添加P4P的色块数据
     * @param dataList
     * @throws Exception
     */
    void addP4pData(Map<String,Object> dataList ) throws Exception;

    /**
     * 根据数据类型获取月度数据以月为维度
     * @param otherType
     */
    Map<String, Object> initMonthData(Integer otherType,String time)throws Exception;
}
