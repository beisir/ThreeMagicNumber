package com.hc360.dataweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc360.dataweb.model.DataType;
import com.hc360.dataweb.service.P4pService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    private P4pService p4pService;
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
        try {
            List<Integer> typeList = new ArrayList<>();
            typeList.add(DataType.P4PPRICE.getType()); // 客单价
            typeList.add(DataType.P4PUSER.getType()); //会员数
            typeList.add(DataType.P4PXIANJINCHARGETOTAL.getType()); //销售额
            typeList.add(DataType.P4PALLEXPENDTOTAL.getType()); //会员数
            typeList.add(DataType.P4PALLBALANCE.getType()); //会员数
            typeList.add(DataType.P4PALLCHARGETOTAL.getType()); //会员数
            Map<String,Object> _dataMap  = p4pService.p4pFormula(typeList);
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("p4pFormula:", e);
        }
    }


}
