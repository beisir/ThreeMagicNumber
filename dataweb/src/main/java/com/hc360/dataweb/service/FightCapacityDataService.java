package com.hc360.dataweb.service;

import com.hc360.dataweb.model.ChartBean;

import java.util.Map;

/**
 * Created by HC360 on 2017/3/3.
 */
public interface FightCapacityDataService {
    /**
     * 初始化战斗力顶部色块数据
     * @param dataTotal
     * @throws Exception
     */
    void initFightData(Map<String, Object> dataTotal)throws Exception;

    void initFightChartData(Map<String, Object> data, ChartBean chartBean)throws Exception;

    /**
     * 初始化电销&渠道流水(当月周度数据实际与预估对比图)
     * @param data
     * @param flag 1:电销  2:渠道
     */
    void initTurnoverWeek(Map<String, Object> data, int flag)throws Exception;

    /**
     * 当年周度数据趋势图{百分比,周度实际值,周度预估值}电销&渠道
     * @param data
     * @param i
     * @throws Exception
     */
    /*void initTurnoverChart(Map<String, Object> data, int i) throws Exception;*/

    /**
     * 实际值最近三十天的折线图{电销&渠道}
     * @param data
     * @param dataType
     * @throws Exception
     */
    void initTurnoverDay(Map<String, Object> data, Integer dataType) throws Exception;
    /**
     * 初始化电销&渠道流水(当年月度数据实际与预估与预算值对比图)
     * @param data
     * @param flag 1:电销  2:渠道
     */
    void initTurnoverMonth(Map<String, Object> data, int flag) throws Exception;

    void initTurnoverWeek_DX(Map<String, Object> data) throws Exception;
    void initTurnoverMonth_DX(Map<String, Object> data) throws Exception;
}
