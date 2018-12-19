package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.RealTimeStatic4Data;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface RealTimeStatic4DataMapper{
    List<RealTimeStatic4Data> findByType(Map<String,Object> map);
}