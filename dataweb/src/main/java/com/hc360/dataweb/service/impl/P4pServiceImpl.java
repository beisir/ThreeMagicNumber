package com.hc360.dataweb.service.impl;

import com.hc360.dataweb.dao.RealTimeStatic3DataMapper;
import com.hc360.dataweb.dao.RealTimeStaticHourMapper;
import com.hc360.dataweb.model.*;
import com.hc360.dataweb.service.P4pService;
import com.hc360.dataweb.util.ControllerDateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by home on 2018/11/21.
 */
@Service
public class P4pServiceImpl implements P4pService {
    private Logger logger = Logger.getLogger(P4pServiceImpl.class);
    @Autowired
    private RealTimeStaticHourMapper realTimeStaticHourMapper;
    @Autowired
    private RealTimeStatic3DataMapper realTimeStatic3DataMapper;

    public Map<String,Object> p4pFormula(List<Integer> typeList){
        Map<String,Object> resultMap = new HashMap<>();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("list",typeList);
        paramMap.put("day", ControllerDateUtil.getToday());
        List<RealTimeStaticHour> resultList = realTimeStaticHourMapper.findAllByDay(paramMap);
        if(resultList!=null && resultList.size()>0){
            List<MainBean> list = new ArrayList<>();
            MainBean mainBean = null;
            for(RealTimeStaticHour hour : resultList){

                if(hour.getDataType().intValue()== DataType.P4PPRICE.getType()){
                    mainBean = new MainBean("客单价",hour.getDataCount()+"");
                    list.add(mainBean);
                }else if (hour.getDataType().intValue()== DataType.P4PUSER.getType()){
                    mainBean = new MainBean("会员数",hour.getDataCount()+"");
                    list.add(mainBean);
                }else if (hour.getDataType().intValue()== DataType.P4PXIANJINCHARGETOTAL.getType()){
                    mainBean = new MainBean("销售额",hour.getDataCount()+"");
                    list.add(mainBean);
                }else if (hour.getDataType().intValue()== DataType.P4PALLEXPENDTOTAL.getType()){
                    mainBean = new MainBean("消耗",hour.getDataCount()+"");
                    list.add(mainBean);
                }else if (hour.getDataType().intValue()== DataType.P4PALLBALANCE.getType()){
                    mainBean = new MainBean("余额",hour.getDataCount()+"");
                    list.add(mainBean);
                }else if (hour.getDataType().intValue()== DataType.P4PALLCHARGETOTAL.getType()){
                    mainBean = new MainBean("充值",hour.getDataCount()+"");
                    list.add(mainBean);
                }
            }
            resultMap.put("formula",list);
        }

        return resultMap;
    }


    public Map<String,Object> priceDist(int type,String day){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("dataType",type);
        paramMap.put("day",day);
        Map<String,Object> resultMap = new HashMap<>();

        List<RealTimeStatic3Data> resultList = realTimeStatic3DataMapper.findByType(paramMap);
        List<CircleBean> list = new ArrayList<>();
        if(resultList!=null && resultList.size()>0){
            CircleBean circleBean = null;
            for(RealTimeStatic3Data _realTimeStatic3Data : resultList){
                circleBean = new CircleBean(_realTimeStatic3Data.getElement(),_realTimeStatic3Data.getDataCount()*100,_realTimeStatic3Data.getDataCount());
                list.add(circleBean);
            }
        }
        resultMap.put("data",list);

        return resultMap;
    }
}
