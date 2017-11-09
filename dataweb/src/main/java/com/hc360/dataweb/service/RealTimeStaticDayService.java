package com.hc360.dataweb.service;

import java.util.Map;

/**
 * Created by HC360 on 2017/2/21.
 */
public interface RealTimeStaticDayService {

    /**
     * 获取顶部实时数据
     * @param dataList
     */
    void initDataList(Map<String, Object> dataList)throws Exception;
    /**
     * 获取IP/UV的数据
     * @param dataTotal
     * @throws Exception
     */
    void initIPUVDataList(Map<String, Object> dataTotal)throws Exception;

    /**
     * 获取顶部P4P实时数据
     * @param dataList
     */
    void initP4PDataList(Map<String, Object> dataList)throws Exception;
}
