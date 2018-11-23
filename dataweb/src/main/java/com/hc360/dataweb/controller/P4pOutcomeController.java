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
            typeList.add(DataType.P4PALLBALANCE.getType()); //余额
            typeList.add(DataType.P4PALLEXPENDTOTAL.getType()); //消耗
            typeList.add(DataType.P4PALLCHARGETOTAL.getType()); //充值
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
     * 关键词云  376
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
                dataTypeList.add(DataType.P4PXUNIEXPENDTOTAL.getType());// 虚拟
                dataTypeList.add(DataType.P4PFANDIANJINEXPENDTOTAL.getType()); //返点金
                dataTypeList.add(DataType.P4PXIANJINEXPENDTOTAL.getType()); //现金消耗
            }
            if("charge".equals(flag)){
                dataTypeList.add(DataType.P4PXUNICHARGETOTAL.getType());// 虚拟余额
                dataTypeList.add(DataType.P4PFANDIANJINCHARGETOTAL.getType());// 返点金余额
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

    /**
     * 折线图
     * @param request
     * @param response
     * @param flag sale是销售额折线图， key是关键词折线图
     * @throws Exception
     */
    @RequestMapping(value = "/p4pline", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void line(HttpServletRequest request, HttpServletResponse response,@RequestParam String flag) throws Exception{
        response.setContentType("application/json; charset=UTF-8");
        Map<String,Object> _dataMap  = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Integer> dataTypeList = new ArrayList<>();
            if("sale".equals(flag)){
                dataTypeList.add(DataType.P4PPRICE.getType()); //客单价
                dataTypeList.add(DataType.P4PUSER.getType());// 会员数
            }
            if("key".equals(flag)){
                dataTypeList.add(DataType.P4PAVGKEYS.getType());// 人均关键词数
                dataTypeList.add(DataType.P4PBALANCEKEY.getType());// 总关键词数
                dataTypeList.add(DataType.P4PBALANCENOKEY.getType());// 无效关键词数
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

    /**
     * 会员情况，混合图
     * @param request
     * @param response
     * @param flag
     * @throws Exception
     */
    @RequestMapping(value = "/p4pcombine", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void combine(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("application/json; charset=UTF-8");
        Map<String,Object> _dataMap  = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Integer> dataTypeList = new ArrayList<>();
            dataTypeList.add(DataType.P4PXIANJINBALANCEUSERS.getType()); //仅现金
            dataTypeList.add(DataType.P4PFANDIANJINBALANCEUSERS.getType());// 仅返点金
            dataTypeList.add(DataType.P4PXUNIBALANCEUSERS.getType());// 仅虚拟
            dataTypeList.add(DataType.P4PBALANCEUSER.getType());// 都有

            _dataMap  = p4pServiceImpl.columd3D(dataTypeList,6);
//同心圆
            dataTypeList = new ArrayList<>();
            dataTypeList.add(DataType.P4PBALANCEUSER.getType());//有余额
            dataTypeList.add(DataType.P4PNOBALANCEUSERS.getType()); //无余额
            dataTypeList.add(DataType.P4PBALANCEKEYUSERS.getType()); //有余额且开启关键词
            dataTypeList.add(DataType.P4PBALANCENOKEYUSERS.getType());// 有余额且未开启关键词
             p4pServiceImpl.twoCircleUsers(dataTypeList, ControllerDateUtil.getToday(), _dataMap);

            _dataMap.put("errno",0);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            _dataMap.put("errno",1);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
            logger.error("combine，", e);
        }
    }
}
