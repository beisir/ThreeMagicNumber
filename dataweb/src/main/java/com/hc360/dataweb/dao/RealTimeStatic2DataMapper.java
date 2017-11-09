package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.RealTimeStatic2Data;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface RealTimeStatic2DataMapper{
    /***
     * 查询某个类型的数据
     * @param params : day：某天 ，type:数据类型 sort ：排序字段名称
     * @return
     */
    List<RealTimeStatic2Data> findByLastHour(Map<String,Object > params);

    /**
     * 找到最大的日期
     * @return
     */
    List<RealTimeStatic2Data> findMaxDate();
}
