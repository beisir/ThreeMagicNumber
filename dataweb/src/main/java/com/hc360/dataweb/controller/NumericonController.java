package com.hc360.dataweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc360.dataweb.model.ChartBean;
import com.hc360.dataweb.model.RegionDataBean;
import com.hc360.dataweb.service.FeeUserService;
import com.hc360.dataweb.service.NumericonService;
import com.hc360.dataweb.util.EmailUtil;
import com.hc360.dataweb.util.ParseUtil;
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
 * Created by zhangwanru on 2017/5/3.
 */
@Controller
public class NumericonController {
    private Logger logger = Logger.getLogger(NumericonController.class);

    @Autowired
    private NumericonService dataService;

    @RequestMapping(value = "/sourcedata",method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void findSourceData(ChartBean chartBean,HttpServletRequest request,HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        Map<String,Object> data = new HashMap<String,Object>();
        try {
            if (chartBean != null && ParseUtil.isStr2Num(chartBean.getType())) {
                Integer dataType = Integer.valueOf(chartBean.getType());
                dataService.findNumericonData(dataType, data);
            }else{
                EmailUtil.warnEveryOne("NumericonController.findSourceData param is error" + chartBean,request);
            }
        }catch (Exception e ){
            EmailUtil.warnEveryOne("NumericonController.findSourceData has error，param=" + chartBean + "," + e.getMessage());
            logger.error("NumericonController.findSourceData has error，param=" + chartBean,e);
        }
        Map<String,Object> _dataMap = new HashMap<String,Object>();
        _dataMap.put("data", data);
        _dataMap.put("errno", 0);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("feeuserdata:", e);
        }
    }
    @RequestMapping(value = "/regiondata",method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void findRegionData(ChartBean chartBean,HttpServletRequest request,HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        List<RegionDataBean> beans = new ArrayList<>();
        try {
            if (chartBean != null && ParseUtil.isStr2Num(chartBean.getType())) {
                Integer dataType = Integer.valueOf(chartBean.getType());
                dataService.findRegionData(dataType, beans);
            }else{
                EmailUtil.warnEveryOne("NumericonController.findRegionData param is error" + chartBean,request);
            }
        }catch(Exception e){
            EmailUtil.warnEveryOne("NumericonController.findRegionData has error，param=" + chartBean + "," + e.getMessage());
            logger.error("NumericonController.findRegionData has error，param=" + chartBean,e);
        }
        Map<String,Object> _dataMap = new HashMap<String,Object>();
        _dataMap.put("data", beans);
        _dataMap.put("errno", 0);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("feeuserdata:", e);
        }
    }
}
