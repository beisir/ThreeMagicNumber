package com.hc360.dataweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc360.dataweb.common.constants.ChartsConstant;
import com.hc360.dataweb.model.ChartBean;
import com.hc360.dataweb.model.DataType;
import com.hc360.dataweb.service.FeeUserService;
import com.hc360.dataweb.service.RealTimeStaticDayService;
import com.hc360.dataweb.service.RealTimeStaticHourService;
import com.hc360.dataweb.util.EmailUtil;
import com.hc360.dataweb.util.ParseUtil;
import org.apache.commons.lang.StringUtils;
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
 * Created by zhangwanru on 2017/2/21.
 * IP/UV实时数据看板页面
 */
@Controller
public class LiveDataController {
    private Logger logger = Logger.getLogger(LiveDataController.class);
    @Autowired
    private RealTimeStaticDayService realTimeStaticDayService;
    @Autowired
    private RealTimeStaticHourService realTimeStaticHourService;
    @Autowired
    private FeeUserService feeUserService;

    /**
     * 顶部各种颜色的图块的数据
     *
     * @param response
     */
    @RequestMapping(value = "/realtimedata/", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void findTopDataByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        //数据
        Map<String, Object> dataList = new HashMap<String, Object>();
        Map<String, Object> dataTotal = new HashMap<String, Object>();//IP/UV
        Map _dataMap = new HashMap();
        Map dataMap = new HashMap();
        try {
            realTimeStaticDayService.initDataList(dataList);

            realTimeStaticDayService.initIPUVDataList(dataTotal);//IP/UV


            dataMap.put("dataTotal", dataTotal);
            dataMap.put("dataList", dataList);
//添加P4P的数据,从小时表中添加数据
            this.realTimeStaticHourService.addP4pData(dataList);

            _dataMap.put("errno", 0);
            _dataMap.put("data", dataMap);
        } catch (Exception e) {
            EmailUtil.warnEveryOne("LiveDataController.findTopDataByDay has error，" + "," + e.getMessage());
            logger.error("findTopDataByDay has error，", e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("LiveDataController.realtimedata:", e);
        }
    }

    /**
     * 图表的数据
     *
     * @param chartBean
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/chartdata", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void findChartData(ChartBean chartBean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        String time = chartBean.getTime();
        Integer otherType = 0;
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            if (ParseUtil.isStr2Num(chartBean.getType())) {
                otherType = Integer.valueOf(chartBean.getType());
                //以时间为分割
                if (ChartsConstant.TODAY.equals(time)) {//今天
                    //查询小时表
                    dataMap = realTimeStaticHourService.initTodayData(otherType);
                } else if (ChartsConstant.MONTH_DATA.equals(time)) { //当年每月维度 ---20170630
                    feeUserService.initChartData(otherType, time, dataMap);
                } else {//查询之前的数据
                    dataMap = realTimeStaticHourService.initBeforeData(otherType, time);
                }
            } else {
                logger.error("ChartBean.type值不是数值");
            }
        } catch (Exception e) {
            EmailUtil.warnEveryOne("LiveDataController.findChartData has error，param=" + chartBean.toString() + "," + e.getMessage());
            logger.error("LiveDataController.findChartData has error，param=" + chartBean.toString(), e);
        }
        Map<String, Object> _dataMap = new HashMap<String, Object>();
        _dataMap.put("data", dataMap);
        _dataMap.put("errno", 0);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("LiveDataController.chartdata:", e);
        }
    }

    /**
     * 第二张图表的数据
     * 粉丝数量,微信绑定用户数,粘性买家
     *
     * @param response
     */
//    @RequestMapping(value = "/secondchartdata", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void findSecondChartData(ChartBean chartBean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        String time = chartBean.getTime();
        Integer otherType = Integer.valueOf(chartBean.getType());
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //以时间为分割
        if (ChartsConstant.TODAY.equals(time)) {//今天
            //查询小时表
            dataMap = realTimeStaticHourService.initSecondTodayData(otherType);
        } else {//查询之前的数据
            dataMap = realTimeStaticHourService.initSecondBeforeData(otherType, time);
        }

        Map<String, Object> _dataMap = new HashMap<String, Object>();
        _dataMap.put("data", dataMap);
        _dataMap.put("errno", 0);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            EmailUtil.warnEveryOne("LiveDataController.findSecondChartData has error，param=" + chartBean.toString() + "," + e.getMessage());
            logger.error("LiveDataController.secondchartdata:time=" + time + ",param=" + chartBean.toString(), e);
        }
    }

    /**
     * @param chartBean
     * @param request
     * @param response  第二图：P4P 关键词 或竞价词
     */
    @RequestMapping(value = "/secondchartdata", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void findSecondChartDataNew(ChartBean chartBean, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json; charset=UTF-8");
        Map<String, Object> dataMap = null;
        Map<String, Object> _dataMap = new HashMap<String, Object>();
        Integer otherType = 0;
        try {
            if (StringUtils.isNotBlank(chartBean.getType()) && StringUtils.isNumeric(chartBean.getType())) {
                if ((DataType.P4P_KEY_SUM.getType() + "").equals(chartBean.getType())) {
                    dataMap = this.realTimeStaticHourService.initSecondTodayDataNew(chartBean);
                } else if ((DataType.P4P_KEY_TOP50_PEOPLE.getType() + "").equals(chartBean.getType())) {
                    //散点图
                    dataMap = this.realTimeStaticHourService.loadP4pScatter(chartBean);
                } else {
                    String time = chartBean.getTime();
                    otherType = Integer.valueOf(chartBean.getType());
                    //以时间为分割
                    if (ChartsConstant.TODAY.equals(time)) {//今天
                        //查询小时表
                        dataMap = realTimeStaticHourService.initSecondTodayData(otherType);
                    } else {//查询之前的数据
                        dataMap = realTimeStaticHourService.initSecondBeforeData(otherType, time);
                    }
                }
            }else{
                logger.warn("传入的参数为空:"+chartBean);
            }
            _dataMap.put("data", dataMap);
            _dataMap.put("errno", 0);
        } catch (Exception e) {
            logger.error("param=" + chartBean.toString(), e);
            EmailUtil.warnEveryOne("LiveDataController.findSecondChartDataNew has error，param=" + chartBean.toString() + "," + e.getMessage());
            _dataMap.put("errno", -1);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("LiveDataController.secondchartdata:", e);
        }
    }
}
