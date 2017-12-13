package com.hc360.dataweb.service;

import com.hc360.dataweb.model.OrderMoveTable;
import com.hc360.dataweb.model.TaskTable;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by wal on 2017/12/08
 */
public interface UpdownExcelService {
    /**
     * 插入任务数据
     * @param tasks;
     * @throws Exception
     */
    int insertTasks(ArrayList<TaskTable> tasks) throws Exception;


    /**
     * 获取任务数据
     * @param map;
     * @throws Exception
     */
    ArrayList<TaskTable> getTasks(Map<String, Object> map) throws Exception;


    /**
     * 插入订单流转数据
     * @param orders;
     * @throws Exception
     */
    int insertOrders(ArrayList<OrderMoveTable> orders) throws Exception;


}
