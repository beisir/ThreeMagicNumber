package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.RealTimeStatic3Data;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface RealTimeStatic3DataMapper {
    List<RealTimeStatic3Data> findByType(Map<String,Object> map);
    List<RealTimeStatic3Data> findByTypeElement(Map<String,Object> map);
}
