package com.hc360.dataweb.service;

import java.util.List;
import java.util.Map;

/**
 * Created by home on 2018/11/21.
 */
public interface P4pService {
    Map<String,Object> p4pFormula(List<Integer> typeList);
    Map<String,Object> priceDist(int type,String day);
    Map<String,Object> twoCircle(List<Integer> typeList,String day);
    Map<String, Object> columd3D(List<Integer> typeList, int dayBeyond);
    void  twoCircleUsers(List<Integer> typeList, String day , Map<String, Object> resultMap);
}
