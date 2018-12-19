package com.hc360.dataweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc360.dataweb.model.DataType;
import com.hc360.dataweb.service.OperateService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**友客看板
 * Created by home on 2018/12/5.
 */
@Controller
public class YoukeOutComeController {
    private Logger logger = Logger.getLogger(YoukeOutComeController.class);
    @Autowired
    private OperateService operateService;

    /***
     * 头部的两个算式
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/youkeFormula", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void p4pFormula(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> _dataMap  = new HashMap<>();
        try {
            List<Integer> typeList = new ArrayList<>();
            typeList.add(DataType.YOUKEPRICE.getType()); // 客单价
            typeList.add(DataType.YOUKEUSER.getType()); //会员数
            typeList.add(DataType.YOUKETOTOALFEE.getType()); //销售额
            _dataMap  = operateService.formula(typeList);
            _dataMap.put("errno",0);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            _dataMap.put("errno",1);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
            logger.error("youkeformula:", e);
        }
    }


    @RequestMapping(value = "/youkeLine", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void line(HttpServletRequest request, HttpServletResponse response,@RequestParam String flag) throws Exception{
        response.setContentType("application/json; charset=UTF-8");
        Map<String,Object> _dataMap  = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Integer> dataTypeList = new ArrayList<>();

            if("sale".equals(flag)){//销售额
                dataTypeList.add(DataType.YOUKEPRICE.getType()); //客单价
                dataTypeList.add(DataType.YOUKEUSER.getType());// 会员数
                Map<String,Object> dataMap  = operateService.line(dataTypeList, 6);
                _dataMap.put("data",dataMap);
            }
            if("userKey".equals(flag)){//订阅关键词
                dataTypeList.add(DataType.YOUKEUSERKEYNSUM.getType());// 总数
                dataTypeList.add(DataType.YOUKEUSERKEYAVG.getType());// 户均数
                Map<String,Object> dataMap  = operateService.line(dataTypeList, 6);
                _dataMap.put("data",dataMap);
            }
            if("clue".equals(flag)){ //线索漏斗
                dataTypeList.add(DataType.YOUKEXIANSUOSUM.getType());// 总数
                dataTypeList.add(DataType.YOUKEXIANSUOSEESUM.getType());// 被查看的线索数
                Map<String,Object> dataMap  = operateService.lineFromDayTable(dataTypeList, 6);
                _dataMap.put("data",dataMap);
            }

            if("clueKey".equals(flag)){ //线索关键词
                dataTypeList.add(DataType.YOUKEXIANSUOSKEYNUM.getType());// 总数
                Map<String,Object> dataMap  = operateService.lineFromDayTable(dataTypeList, 6);
                _dataMap.put("data",dataMap);
            }
            if("clueHot".equals(flag)){ //线索热度
                dataTypeList.add(DataType.YOUKEXIANSUOSEETIME.getType());// 被查看的次数
                dataTypeList.add(DataType.YOUKEXIANSUOSEESUM.getType());// 被查看的线索数
                Map<String,Object> dataMap  = operateService.lineFromDayTable(dataTypeList, 6);
                _dataMap.put("data",dataMap);
            }


            _dataMap.put("errno",0);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            _dataMap.put("errno",1);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
            logger.error("youkeLine,flag="+flag, e);
        }
    }

    /**
     * 匹配度
     * @param request
     * @param response
     * @param flag  ab:订阅关键词线索匹配度  ba 线索关键词订阅匹配度
     * @throws Exception
     */
    @RequestMapping(value = "/keyMatch", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void keyMatch(HttpServletRequest request, HttpServletResponse response,@RequestParam String flag) throws Exception{
        response.setContentType("application/json; charset=UTF-8");
        Map<String,Object> _dataMap  = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            int dataType = 0;
            if("ab".equals(flag)){//订阅关键词线索匹配度
                dataType = DataType.YOUKEKEYAMATCHB.getType();

            }else if("ba".equals(flag)){//线索关键词订阅匹配度
                dataType = DataType.YOUKEKEYBMATCHA.getType();
            }
            Map<String,Object> dataMap  = this.operateService.match(dataType);

            _dataMap.put("data",dataMap);
            _dataMap.put("errno",0);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            _dataMap.put("errno",1);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
            logger.error("keyMatch,flag="+flag, e);
        }
    }
}
