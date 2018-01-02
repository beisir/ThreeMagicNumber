package com.hc360.dataweb.service;

import java.util.Map;

/**
 * Created by HC360 on 2017/3/9.
 */
public interface FeeUserService {
    /**
     * 初始化付费会员今日/昨日对比数据
     * @param dataList
     * @throws Exception
     */
    void initFeeData(Map<String, Object> dataList) throws Exception;

    /**
     * 获取用户满意度数据
     * @param dataMap
     * @throws Exception
     */
    void getUserSatisficing(Map<String, Object> dataMap) throws Exception;

    /**
     * 获取趋势图部分数据
     * @param dataType
     * @param time
     * @param dataMap
     * @throws Exception
     */
    void initChartData(Integer dataType, String time, Map<String, Object> dataMap) throws Exception;

  /**
   * 百度联盟月度数据获取
   * */
    void initBdMonthData(Integer dataType,Map<String, Object> dataMap) throws Exception;
  /**
   * 百度联盟月度数据获取
   * */
  void initDueMonthData(Integer dataType,Map<String, Object> dataMap) throws Exception;
}
