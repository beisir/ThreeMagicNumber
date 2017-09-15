package com.hc360.dataweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc360.dataweb.common.constants.ChartsConstant;
import com.hc360.dataweb.model.ChartBean;
import com.hc360.dataweb.model.DataType;
import com.hc360.dataweb.service.FightCapacityDataService;
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
 * Created by zhangwanru on 2017/3/2.
 * 战斗力实时数据看板页面
 */
@Controller
public class FightCapacityController {
    private Logger logger = Logger.getLogger(FightCapacityController.class);

    @Autowired
    private FightCapacityDataService dataService;

    /**
     * 顶部色块的各种数据
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/fightdata",method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void findTopDataByDay(HttpServletRequest request,HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        //初始化数据列表
        Map<String,Object> dataList = new HashMap<String,Object>();
        try {
            dataService.initFightData(dataList);
        }catch(Exception e ){
            EmailUtil.warnEveryOne("FightCapacityController.findTopDataByDay has error，"  + "," + e.getMessage());
            logger.error("FightCapacityController.findTopDataByDay has error，" ,e);
        }

        Map<String,Object> _dataMap = new HashMap<String,Object>();
        _dataMap.put("errno", 0);
        _dataMap.put("data", dataList);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("fightdata:", e);
        }
    }

    /**
     * 趋势图数据
     * 参数{type:类型值,time:查询时间区段}
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/fightchart",method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void findChartData(ChartBean chartBean,HttpServletRequest request,HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        //初始化数据列表
        Map<String,Object> data = new HashMap<String,Object>();
        try {
            if (chartBean != null && ParseUtil.isStr2Num(chartBean.getType())) {
                Integer dataType = Integer.valueOf(chartBean.getType());
                if (dataType == DataType.DXTURNOVEYG.getType().intValue()) {//当月周度&当年月度数据实际值与预估值对比图表(电销)
                    if (!"".equals(chartBean.getTime()) && ChartsConstant.WEEK_DATA.equals(chartBean.getTime())) {//周
                        dataService.initTurnoverWeek(data, 1);
                    } else if (!"".equals(chartBean.getTime()) && ChartsConstant.MONTH_DATA.equals(chartBean.getTime())) {//月
                        dataService.initTurnoverMonth(data, 1);
                    }
                } else if (dataType == DataType.QDTURNOVERYG.getType().intValue()) {//当月周度&当年月度数据实际值与预估值对比图表(渠道)
                    if (!"".equals(chartBean.getTime()) && ChartsConstant.WEEK_DATA.equals(chartBean.getTime())) {//周
                        dataService.initTurnoverWeek(data, 2);
                    } else if (!"".equals(chartBean.getTime()) && ChartsConstant.MONTH_DATA.equals(chartBean.getTime())) {//月
                        dataService.initTurnoverMonth(data, 2);
                    }
                } else if (dataType == DataType.DXTURNOVERZL.getType().intValue() || dataType == DataType.QDTURNOVERZL.getType().intValue()) {//实际值最近三十天的折线图(电销&渠道)
                    dataService.initTurnoverDay(data, dataType);
                } else {//其他趋势图
                    dataService.initFightChartData(data, chartBean);
                }
            }else{
                EmailUtil.warnEveryOne("FightCapacityController.findChartData param is error" + chartBean);
            }
        }catch (Exception  e ){
            EmailUtil.warnEveryOne("FightCapacityController.findChartData has error，param="  +chartBean.toString() +"," + e.getMessage());
            logger.error("FightCapacityController.findChartData has error，param="  +chartBean.toString(),e);
        }
        Map<String,Object> _dataMap = new HashMap<String,Object>();
        _dataMap.put("errno", 0);
        _dataMap.put("data", data);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("fightchart:", e);
        }
    }
}
