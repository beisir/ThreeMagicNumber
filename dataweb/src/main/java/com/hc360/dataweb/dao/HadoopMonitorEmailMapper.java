package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.HadoopMonitorEmail;
import org.springframework.stereotype.Repository;

@Repository
public interface HadoopMonitorEmailMapper {
    int insert(HadoopMonitorEmail record);

}
