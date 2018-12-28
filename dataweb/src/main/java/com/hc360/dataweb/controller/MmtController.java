package com.hc360.dataweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc360.dataweb.model.DataType;
import com.hc360.dataweb.service.OperateService;
import com.hc360.dataweb.util.ControllerDateUtil;
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

/**
 * Created by home on 2018/12/27.
 */
@Controller
public class MmtController {
    private Logger logger = Logger.getLogger(MmtController.class);
    @Autowired
    private OperateService operateService;

    /***
     * 头部的两个算式
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/mmtFormula", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void p4pFormula(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> _dataMap  = new HashMap<>();
        try {
            List<Integer> typeList = new ArrayList<>();
            typeList.add(DataType.MMT2018AVGPRICE.getType()); // 客单价
            typeList.add(DataType.MMT2018USER.getType()); //会员数
            typeList.add(DataType.MMT2018TOTALPRICE.getType()); //销售额
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

    /**
     * 折线图
     * @param request
     * @param response
     * @param flag
     * @throws Exception
     */
    @RequestMapping(value = "/mmtLine", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void line(HttpServletRequest request, HttpServletResponse response,@RequestParam String flag) throws Exception{
        response.setContentType("application/json; charset=UTF-8");
        Map<String,Object> _dataMap  = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Integer> dataTypeList = new ArrayList<>();

            if("sale".equals(flag)){//销售额
                dataTypeList.add(DataType.MMT2018AVGPRICE.getType()); //客单价
                dataTypeList.add(DataType.MMT2018USER.getType());// 会员数
                Map<String,Object> dataMap  = operateService.line(dataTypeList, 6);
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
     * 同心圆： 充值=消耗+余额
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/mmt_twocircle", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void twoCircle(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("application/json; charset=UTF-8");
        Map<String,Object> _dataMap  = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Integer> dataTypeList = new ArrayList<>();
            dataTypeList.add(DataType.MMTALLUSER.getType());//累计消耗
            dataTypeList.add(DataType.MMTNOPCALLUSER.getType()); //现金消耗
            dataTypeList.add(DataType.MMTHASPCALLUSER.getType()); //返点金

            _dataMap  = operateService.twoCircleMmt();
            _dataMap.put("errno",0);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            _dataMap.put("errno",1);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
            logger.error("twoCircle=", e);
        }
    }

    /**
     * 气泡填充图和地图
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/complex", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void bubble(HttpServletRequest request, HttpServletResponse response,@RequestParam String flag) throws Exception{
        response.setContentType("application/json; charset=UTF-8");
        Map<String,Object> _dataMap  = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            int type = 0;
            if("bubble".equals(flag)){
                type = DataType.MMTPAREAUSER.getType();
            }else if ("map".equals(flag)){
                type = DataType.MMTPROVINCEUSER.getType();
            }
            Map<String,Object> dataMap = this.operateService.bubble(type);
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
            logger.error("twoCircle=", e);
        }
    }
}
