package com.hc360.dataweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc360.dataweb.model.DataType;
import com.hc360.dataweb.service.P4pService;
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
 * 运营数据导航的接口
 * 2018-11-20
 */
@Controller
public class P4pOutcomeController {
    private Logger logger = Logger.getLogger(P4pOutcomeController.class);
    @Autowired
    private P4pService p4pServiceImpl;
    /***
     * 头部的两个算式
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/p4pformula", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void p4pFormula(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> _dataMap  = new HashMap<>();
        try {
            List<Integer> typeList = new ArrayList<>();
            typeList.add(DataType.P4PPRICE.getType()); // 客单价
            typeList.add(DataType.P4PUSER.getType()); //会员数
            typeList.add(DataType.P4PXIANJINCHARGETOTAL.getType()); //销售额
            typeList.add(DataType.P4PALLEXPENDTOTAL.getType()); //会员数
            typeList.add(DataType.P4PALLBALANCE.getType()); //会员数
            typeList.add(DataType.P4PALLCHARGETOTAL.getType()); //会员数
            _dataMap  = p4pServiceImpl.p4pFormula(typeList);
            _dataMap.put("errno",0);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            _dataMap.put("errno",1);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
            logger.error("p4pFormula:", e);
        }
    }

    /**
     * 客单价分布 378
     * 日消耗 379
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/dist", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void distribution(HttpServletRequest request, HttpServletResponse response,@RequestParam int type) throws Exception{
        response.setContentType("application/json; charset=UTF-8");
        Map<String,Object> _dataMap  = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            _dataMap  = p4pServiceImpl.priceDist(type, ControllerDateUtil.getToday());
            _dataMap.put("errno",0);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            _dataMap.put("errno",1);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
            logger.error("priceDistribution:type="+type, e);
        }
    }

    /**
     * 同心圆： 充值=消耗+余额
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/twocircle", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void twoCircle(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("application/json; charset=UTF-8");
        Map<String,Object> _dataMap  = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Integer> dataTypeList = new ArrayList<>();
            dataTypeList.add(DataType.P4PALLEXPENDTOTAL.getType());//累计消耗
            dataTypeList.add(DataType.P4PXIANJINEXPENDTOTAL.getType()); //现金消耗
            dataTypeList.add(DataType.P4PFANDIANJINEXPENDTOTAL.getType()); //返点金
            dataTypeList.add(DataType.P4PXUNIEXPENDTOTAL.getType());// 虚拟

            dataTypeList.add(DataType.P4PXIANJINBALANCE.getType());// 现金余额
            dataTypeList.add(DataType.P4PFANDIANJINBALANCE.getType());// 返点金余额
            dataTypeList.add(DataType.P4PXUNIBALANCE.getType());// 虚拟余额
            dataTypeList.add(DataType.P4PALLBALANCE.getType());// 累计余额

            _dataMap  = p4pServiceImpl.twoCircle(dataTypeList, ControllerDateUtil.getToday());
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
     *
     * @param request
     * @param response
     * @param flag  expend==>累计消耗信息  charge =>累计充值信息
     * @throws Exception
     */
    @RequestMapping(value = "/column3d", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void column3D(HttpServletRequest request, HttpServletResponse response,@RequestParam String flag) throws Exception{
        response.setContentType("application/json; charset=UTF-8");
        Map<String,Object> _dataMap  = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Integer> dataTypeList = new ArrayList<>();
            if("expend".equals(flag)){
                dataTypeList.add(DataType.P4PFANDIANJINEXPENDTOTAL.getType()); //返点金
                dataTypeList.add(DataType.P4PXUNIEXPENDTOTAL.getType());// 虚拟
                dataTypeList.add(DataType.P4PXIANJINEXPENDTOTAL.getType()); //现金消耗
            }
            if("charge".equals(flag)){
                dataTypeList.add(DataType.P4PFANDIANJINCHARGETOTAL.getType());// 返点金余额
                dataTypeList.add(DataType.P4PXUNICHARGETOTAL.getType());// 虚拟余额
                dataTypeList.add(DataType.P4PXIANJINCHARGETOTAL.getType());// 现金余额
            }
            _dataMap  = p4pServiceImpl.columd3D(dataTypeList,6);
            _dataMap.put("errno",0);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            _dataMap.put("errno",1);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
            logger.error("column3D,flag="+flag, e);
        }
    }

}
