package com.hc360.dataweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc360.dataweb.common.constants.ChartsConstant;
import com.hc360.dataweb.model.ChartBean;
import com.hc360.dataweb.model.DataType;
import com.hc360.dataweb.service.FeeUserService;
import com.hc360.dataweb.util.EmailUtil;
import com.hc360.dataweb.util.ParseUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangwanru on 2017/3/9.
 * 收费会员实时数据看板页面
 */
@Controller
public class FeeUserController {
    private Logger logger = Logger.getLogger(FeeUserController.class);

    @Autowired
    private FeeUserService dataService;
    /**
     * 查询今天昨天数据对比
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/feeuserdata",method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void findTopDataByDay(HttpServletRequest request,HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        //1.初始化数据列表dataList[今天map,昨天map]
        Map<String,Object> dataList = new HashMap<String,Object>();
        Map<String,Object> _dataMap = new HashMap<String,Object>();
        try {
            dataService.initFeeData(dataList);
        }catch (Exception e){
            EmailUtil.warnEveryOne("FeeUserController.findTopDataByDay has error，" + "," + e.getMessage());
            logger.error("FeeUserController.findTopDataByDay has error，",e);
        }
        _dataMap.put("errno", 0);
        _dataMap.put("data", dataList);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("feeuserdata:", e);
        }
    }

    /**
     * 查询趋势图
     * 参数{type:类型值,time:查询时间区段}
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/feeuserchart",method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void findChartData(ChartBean chartBean,HttpServletRequest request,HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        Map<String,Object> _dataMap = new HashMap<String,Object>();
        Map<String,Object> dataMap = new HashMap<String,Object>();
        try {
            if (chartBean != null && ParseUtil.isStr2Num(chartBean.getType())) {
                Integer dataType = Integer.valueOf(chartBean.getType());
                if (chartBean.getTime() == null) {//柱状图{客户满意度(type=82)}
                    if (dataType == DataType.DXACLASS.getType().intValue()) {//客户满意度
                        dataService.getUserSatisficing(dataMap);
                    }
                } else {//折线图
                    dataService.initChartData(dataType, chartBean.getTime(), dataMap);
                }
            } else {
                logger.error("前端传递的ChartBean.type不是一个数值类型");
            }
        }catch (Exception e ){
            EmailUtil.warnEveryOne("FeeUserController.findChartData has error，" +chartBean.toString() + "," + e.getMessage());
            logger.error("FeeUserController.findChartData has error，" +chartBean.toString() ,e);
        }
        _dataMap.put("errno", 0);
        _dataMap.put("data", dataMap);
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
