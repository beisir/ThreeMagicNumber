package com.hc360.dataweb.service;

import java.util.List;
import java.util.Map;

/**
 * Created by home on 2018/11/21.
 */
public interface OperateService {
    Map<String,Object> formula(List<Integer> typeList) throws Exception;
    Map<String,Object> priceDist(int type,String day) throws Exception;
    Map<String,Object> twoCircle(List<Integer> typeList,String day) throws Exception;
    Map<String, Object> columd3D(List<Integer> typeList, int dayBeyond) throws Exception;
    void  twoCircleUsers(List<Integer> typeList, String day , Map<String, Object> resultMap) throws Exception;
    public Map<String, Object> line(List<Integer> typeList, int dayBeyond) throws Exception;
    public Map<String, Object> lineFromDayTable(List<Integer> typeList, int dayBeyond) throws Exception;
    public Map<String, Object> match(Integer type) throws Exception;
}
