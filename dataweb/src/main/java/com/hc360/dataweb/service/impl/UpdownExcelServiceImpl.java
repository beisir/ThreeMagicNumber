package com.hc360.dataweb.service.impl;

import com.hc360.dataweb.dao.UpdownExcelMapper;
import com.hc360.dataweb.model.OrderMoveTable;
import com.hc360.dataweb.model.TaskTable;
import com.hc360.dataweb.service.UpdownExcelService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by dell on 2017/12/8.
 */
@Service
public class UpdownExcelServiceImpl implements UpdownExcelService {

    private Logger logger = Logger.getLogger(UpdownExcelServiceImpl.class);

    @Autowired
    private UpdownExcelMapper udMapper;

    @Override
    public int insertTasks(ArrayList<TaskTable> tasks) throws Exception {
        return udMapper.insertTasks(tasks);
    }

    @Override
    public ArrayList<TaskTable> getTasks(Map<String, Object> params) throws Exception {
        return null;
    }

    @Override
    public int insertOrders(ArrayList<OrderMoveTable> orders) throws Exception {
        return udMapper.insertOrders(orders);
    }
}
