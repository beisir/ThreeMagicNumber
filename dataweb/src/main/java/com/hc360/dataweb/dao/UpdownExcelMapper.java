package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.OrderMoveTable;
import com.hc360.dataweb.model.TaskTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by dell on 2017/12/8.
 */
@Repository
public interface UpdownExcelMapper {

    public int insertTasks(@Param("tasks") ArrayList<TaskTable> tasks) throws Exception;

    public int insertTask(@Param("task") TaskTable tasks) throws Exception;


    public ArrayList<TaskTable> getTasks(@Param("params") Map<String, Object> params) throws Exception;


    public int insertOrders(@Param("orders") ArrayList<OrderMoveTable> orders) throws Exception;

    public ArrayList<OrderMoveTable> getOrders(@Param("params") Map<String, Object> params) throws Exception;
}
