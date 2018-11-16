package com.hc360.dataweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc360.dataweb.dao.RealTimeStaticDayMapper;
import com.hc360.dataweb.dao.RealTimeStaticHourMapper;
import com.hc360.dataweb.dao.RealtimeUserInfoMapper;
import com.hc360.dataweb.model.*;
import com.hc360.dataweb.service.RealTimeStaticHourService;
import com.hc360.dataweb.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStreamReader;


/**
 * Created by home on 2017/2/5.
 */
@Controller
public class MainController {
    private Logger logger = Logger.getLogger(MainController.class);
    @Autowired
    private RealTimeStaticHourMapper realTimeStaticHourMapper;
    @Autowired
    private RealTimeStaticDayMapper realTimeStaticDayMapper;
    @Autowired
    private RealTimeStaticHourService realTimeStaticHourService;

    // 没权限
    @RequestMapping("/nolimit/")
    public ModelAndView nolimit() {
        System.out.println("ok2");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/error/nolimit");
        return mv;
    }

    @RequestMapping("unlogin")
    public ModelAndView resource() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/screen/login");
        return mv;
    }

    /**
     * 看板各种颜色的图块的数据
     *
     * @param response
     */
    @RequestMapping(value = "/abovedata",
            method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void findAllByDay(HttpServletResponse response) throws Exception {

        response.setContentType("application/json; charset=UTF-8");
        String day = ControllerDateUtil.getToday();//取今天的数据
        //今天的
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //昨天的数据
        String yesterDay = ControllerDateUtil.getYesterday();
        Map<String, Object> resultYesterdayMap = new HashMap<String, Object>();
        //获取7天前的数据
        String weekDay = ControllerDateUtil.getWeek();
        Map<String, Object> resultWeekMap = new HashMap<String, Object>();

        //获取MIP站的昨日信息
        Map<String, Object> mipDataMap = new HashMap<>();
        try {
            initAboveData(resultMap, day, null,false);
            initAboveData(resultYesterdayMap, day, yesterDay,false);
            initAboveData(resultWeekMap,day,weekDay,true);
        } catch (Exception e) {
            EmailUtil.warnEveryOne("MainController.findAllByDay has error，" + e.getMessage());
            logger.error("MainController.findAllByDay has error，", e);
        }

        Map _dataMap = new HashMap();
        _dataMap.put("errno", 0);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("todaydata", resultMap);
        dataMap.put("weekdata", resultWeekMap);
        dataMap.put("yesterdaydata", resultYesterdayMap);

        _dataMap.put("data", dataMap);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("abovedata:", e);

        }
    }

    private void initAboveData(Map<String, Object> resultMap, String day, String yesterday, boolean isWeekData) throws Exception {
        Map<String, Integer> dataMap = new HashMap<String, Integer>();
        DecimalFormat df = new DecimalFormat("0.00");
        DecimalFormat threeNumDf = new DecimalFormat(",###");//每三位分隔一下
        List<MainBean> mainBeanList = new ArrayList<MainBean>(); //最大的4个颜色图

        List<FightCapacityBean> fightCapacityBeanList = new ArrayList<FightCapacityBean>();//战斗力模块的数据
        //ip、pv、uv、询盘数据的加工
        List<RealTimeStaticDay> userbehaviorList = null;
        String warnDate = day;
        if (StringUtils.isNotBlank(yesterday)) {
            userbehaviorList = this.realTimeStaticDayMapper.findUserBehaviorByYesterday(day, yesterday);
            warnDate = yesterday;
        } else {
            userbehaviorList = this.realTimeStaticDayMapper.findUserBehaviorByToday(day);
        }

        if (userbehaviorList != null && userbehaviorList.size() > 0) {
            for (RealTimeStaticDay realTimeStaticDay : userbehaviorList) {
                if (realTimeStaticDay != null && realTimeStaticDay.getDataCount() != null) {
                    mainBeanList.add(new MainBean(DataType.getName(realTimeStaticDay.getDataType()), threeNumDf.format(realTimeStaticDay.getDataCount().longValue())));
                } else {
                    mainBeanList.add(new MainBean(DataType.getName(realTimeStaticDay.getDataType()), "0"));
                    EmailUtil.warnEveryOne(warnDate + "-" + DataType.getName(realTimeStaticDay.getDataType()) + "-- 数据为空。");
                }
            }
        }
//收费总人数----从天表中获取4001的数作为会员的总数。
        Map<String, Object> map = new HashMap<String, Object>();

        resultMap.put("main", mainBeanList);//最大的4个颜色图
        if(isWeekData){return;}

        //战斗力数据的加工
        List<RealTimeStaticHour> findAllByDayList = null;
        Map<String, Object> param = new HashMap<String, Object>();
        List<Integer> feeUserDataTypes = new ArrayList<Integer>();//data_type:7-16
        initDate_types(feeUserDataTypes);
        param.put("list", feeUserDataTypes);
        if (StringUtils.isNotBlank(yesterday)) {
            param.put("yesterDay", warnDate);
            findAllByDayList = this.realTimeStaticHourMapper.findRealTimeLastDataYester(param);
        } else {
            param.put("day", day);
            findAllByDayList = this.realTimeStaticHourMapper.findAllByDay(param);
        }
        if (findAllByDayList != null && findAllByDayList.size() > 0) {
            for (RealTimeStaticHour realTimeStaticHour : findAllByDayList) {
                if (realTimeStaticHour != null) {
                    dataMap.put(DataType.getName(realTimeStaticHour.getDataType()), realTimeStaticHour.getDataCount());
                }
            }
            List<FightCapacityOneBean> tmpFightCapacityOneBeanList = new ArrayList<FightCapacityOneBean>();
            FightCapacityBean fightZnCapacityBean = new FightCapacityBean("职能");
            //职能满编率
            if (dataMap.get(DataType.ZNONGUARDEMPLOYEE.getName()) != null && dataMap.get(DataType.ZNOBUILDEMPLOYEE.getName()) != null && dataMap.get(DataType.ZNOBUILDEMPLOYEE.getName()) != 0) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("满编率", df.format(dataMap.get(DataType.ZNONGUARDEMPLOYEE.getName()).doubleValue() * 100 / dataMap.get(DataType.ZNOBUILDEMPLOYEE.getName())) + "%", 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("满编率", "0%", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "职能满编率--数据为空。");
            }

            //职能离职率
            if (dataMap.get(DataType.ZNLEAVEEMPLOYEE.getName()) != null && dataMap.get(DataType.ZNSHOULDEMPLOYEE.getName()) != null && dataMap.get(DataType.ZNLEAVEEMPLOYEE.getName()) != 0) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("离职率", df.format(dataMap.get(DataType.ZNSHOULDEMPLOYEE.getName()).doubleValue() * 100 / dataMap.get(DataType.ZNLEAVEEMPLOYEE.getName())) + "%", 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("离职率", "0%", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "职能离职率--数据为空。");
            }
            //职能在岗人数
            if (dataMap.get(DataType.ZNONGUARDEMPLOYEE.getName()) != null) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("在岗人数", dataMap.get(DataType.ZNONGUARDEMPLOYEE.getName()).toString(), 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("在岗人数", "0", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "职能在岗人数--数据为空。");
            }
            //职能编制人数
            if (dataMap.get(DataType.ZNOBUILDEMPLOYEE.getName()) != null) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("编制人数", dataMap.get(DataType.ZNOBUILDEMPLOYEE.getName()).toString(), 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("编制人数", "0", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "职能编制人数--数据为空。");
            }
            fightZnCapacityBean.setFightInfo(tmpFightCapacityOneBeanList);
            fightCapacityBeanList.add(fightZnCapacityBean);


            tmpFightCapacityOneBeanList = new ArrayList<FightCapacityOneBean>();
            FightCapacityBean fightQdCapacityBean = new FightCapacityBean("渠道");
            //渠道转正人数
            if (dataMap.get(DataType.QDCOVENEMPLOYEE.getName()) != null) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("代理商战斗力", threeNumDf.format(dataMap.get(DataType.QDCOVENEMPLOYEE.getName())), 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("代理商战斗力", "0", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "代理商战斗力--数据为空。");
            }
            //渠道离职率
            if (dataMap.get(DataType.QDLEAVEEMPLOYEE.getName()) != null && dataMap.get(DataType.QDSHOULDEMPLOYEE.getName()) != null && dataMap.get(DataType.QDSHOULDEMPLOYEE.getName()) != 0) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("代理商离职率", df.format(dataMap.get(DataType.QDLEAVEEMPLOYEE.getName()).doubleValue() * 100 / dataMap.get(DataType.QDSHOULDEMPLOYEE.getName())) + "%", 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("代理商离职率", "0%", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "代理商离职率--数据为空。");
            }
            //渠道中心销售人员
            if (dataMap.get(DataType.QDSALE.getName()) != null && dataMap.get(DataType.QDSHOULDEMPLOYEE.getName()) != null && dataMap.get(DataType.QDSHOULDEMPLOYEE.getName()) != 0) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("渠道销售人数", threeNumDf.format(dataMap.get(DataType.QDSALE.getName())), 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("渠道销售人数", "0", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "渠道销售人数--数据为空。");
            }
            //渠道中心管理层人员
            if (dataMap.get(DataType.QDMANAGER.getName()) != null && dataMap.get(DataType.QDSHOULDEMPLOYEE.getName()) != null && dataMap.get(DataType.QDSHOULDEMPLOYEE.getName()) != 0) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("渠道职能人数", threeNumDf.format(dataMap.get(DataType.QDMANAGER.getName())), 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("渠道职能人数", "0", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "渠道职能人数--数据为空。");
            }
            fightQdCapacityBean.setFightInfo(tmpFightCapacityOneBeanList);
            if (fightQdCapacityBean.getFightInfo() != null && fightQdCapacityBean.getFightInfo().size() > 0) {
                fightCapacityBeanList.add(fightQdCapacityBean);
            }

            tmpFightCapacityOneBeanList = new ArrayList<FightCapacityOneBean>();
            FightCapacityBean fightDxCapacityBean = new FightCapacityBean("营销中心");
            //电销新兵连人数人数
            if (dataMap.get(DataType.DX_RECRUIT_NOT_POSITIVE.getName()) != null) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("新兵连人数", threeNumDf.format(dataMap.get(DataType.DX_RECRUIT_NOT_POSITIVE.getName())), 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("新兵连人数", "0", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "新兵连人数--数据为空。");
            }
            //电销专员未转正人数
            if (dataMap.get(DataType.DX_ATTACHE_NOT_POSITIVE.getName()) != null) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("部门中未转正", threeNumDf.format(dataMap.get(DataType.DX_ATTACHE_NOT_POSITIVE.getName())), 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("部门中未转正", "0", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "部门中未转正--数据为空。");
            }
            //转正电销专员数
            if (dataMap.get(DataType.DXCOVENEMPLOYEE.getName()) != null) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("部门中已转正", threeNumDf.format(dataMap.get(DataType.DXCOVENEMPLOYEE.getName())), 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("部门中已转正", "0", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "部门中已转正--数据为空。");
            }
            //销售专员总数
            if (dataMap.get(DataType.DX_TOTAL_COUNT.getName()) != null) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("销售专员总计", threeNumDf.format(dataMap.get(DataType.DX_TOTAL_COUNT.getName())), 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("销售专员总计", "0", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "销售专员总计--数据为空。");
            }

            //电销离职率
            if (dataMap.get(DataType.DXLEAVEEMPLOYEE.getName()) != null && dataMap.get(DataType.DXSHOULDEMPLOYEE.getName()) != null && dataMap.get(DataType.DXSHOULDEMPLOYEE.getName()) != 0) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("离职率", df.format(dataMap.get(DataType.DXLEAVEEMPLOYEE.getName()).doubleValue() * 100 / dataMap.get(DataType.DXSHOULDEMPLOYEE.getName())) + "%", 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("离职率", "0%", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "电销离职率--数据为空。");
            }
            //电销管理人数
            if (dataMap.get(DataType.DX_MANAGER_COUNT.getName()) != null) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("管理层人数", threeNumDf.format(dataMap.get(DataType.DX_MANAGER_COUNT.getName())), 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("管理层人数", "0", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "管理层人数--数据为空。");
            }
            //电销职能人数
            if (dataMap.get(DataType.DX_OFFICER_COUNT.getName()) != null) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("职能人数", threeNumDf.format(dataMap.get(DataType.DX_OFFICER_COUNT.getName())), 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("职能人数", "0", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "职能人数--数据为空。");
            }
            //电销总人数
            if (dataMap.get(DataType.DX_ALL_COUNT.getName()) != null) {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("营销中心总人数", threeNumDf.format(dataMap.get(DataType.DX_ALL_COUNT.getName())), 1));
            } else {
                tmpFightCapacityOneBeanList.add(new FightCapacityOneBean("营销中心总人数", "0", 1));
                EmailUtil.warnEveryOne(warnDate + "-" + "营销中心总人数--数据为空。");
            }
            fightDxCapacityBean.setFightInfo(tmpFightCapacityOneBeanList);
            fightCapacityBeanList.add(fightDxCapacityBean);
        }
        map = new HashMap<String, Object>();
        map.put("yesterDay", warnDate);
        List<Integer>  feeUserNumList = new ArrayList<Integer>();

        feeUserNumList.add(DataType.P4PUSER.getType());
        feeUserNumList.add(DataType.P4PDXUSER.getType());
        feeUserNumList.add(DataType.P4PQDUSER.getType());
        feeUserNumList.add(DataType.P4PHYUSER.getType());
        map.put("orderType","asc");
        map.put("list", feeUserNumList);
        List<FeeuserBean>  userBeanList = this.initUserInfos(map,warnDate);

        resultMap.put("p4p", userBeanList);//p4p会员的4个数据

        feeUserNumList = new ArrayList<Integer>();
        feeUserNumList.add(DataType.YKUSER.getType());
        feeUserNumList.add(DataType.YKDXUSER.getType());
        feeUserNumList.add(DataType.YKQDUSER.getType());
        feeUserNumList.add(DataType.YKHYUSER.getType());
        map.put("orderType","asc");
        map.put("list", feeUserNumList);
        userBeanList = this.initUserInfos(map,warnDate);

        resultMap.put("youke", userBeanList);//友客会员的4个数据
        feeUserNumList = new ArrayList<Integer>();
        feeUserNumList.add(DataType.FEEUSERTOTAL.getType());//data_type:7-16
        feeUserNumList.add(DataType.DXFEEUSER.getType());
        feeUserNumList.add(DataType.QDFEEUSER.getType());
        feeUserNumList.add(DataType.HYFEEUSER.getType());

        map.put("list", feeUserNumList);
        map.put("orderType","asc");
        userBeanList = this.initUserInfos(map,warnDate);
        List<FeeuserBean> _userBeanList =new ArrayList<FeeuserBean>();
        if(userBeanList!=null && userBeanList.size()>0){
            _userBeanList.add(userBeanList.get(userBeanList.size()-1));
            for(int i =0 ;i<userBeanList.size()-1;i++){
                _userBeanList.add(userBeanList.get(i));
            }
        }
        resultMap.put("mmt", _userBeanList);//mmt会员的4个数据
//        resultMap.put("feeuser", userBeanList);
        resultMap.put("fight", fightCapacityBeanList);//战斗力模块的数据
    }

    private  List<FeeuserBean> initUserInfos(Map<String, Object> map ,String warnDate) throws Exception {
        DecimalFormat threeNumDf = new DecimalFormat(",###");//每三位分隔一下
        List<RealTimeStaticHour> _yeasterDayFeeAllUsers = this.realTimeStaticHourMapper.findRealTimeLastDataYester(map);
        List<FeeuserBean> feeuserBeanList = new ArrayList<FeeuserBean>(); //会员的4个数据
        if (_yeasterDayFeeAllUsers != null && _yeasterDayFeeAllUsers.size() > 0) {
            for (RealTimeStaticHour yeasterDayFeeAllUser : _yeasterDayFeeAllUsers) {
                if (yeasterDayFeeAllUser != null) {
                    if (yeasterDayFeeAllUser.getDataCount() != null && yeasterDayFeeAllUser.getDataCount() != 0) {
                        feeuserBeanList.add(new FeeuserBean(DataType.getName(yeasterDayFeeAllUser.getDataType()), threeNumDf.format(yeasterDayFeeAllUser.getDataCount().longValue()), 0));
                    } else {
                        feeuserBeanList.add(new FeeuserBean(DataType.getName(yeasterDayFeeAllUser.getDataType()), "0", 0));
                        EmailUtil.warnEveryOne(warnDate + "-" + "会员各个类型--数据为空。" + yeasterDayFeeAllUser.getDataType());
                    }
                } else {
                    EmailUtil.warnEveryOne(warnDate + "-" + "会员各个类型--数据为空。");
                }
            }
        }
        return feeuserBeanList;
    }

    private void initDate_types(List<Integer> feeUserNumList) {//7-16
        feeUserNumList.add(DataType.DXCOVENEMPLOYEE.getType());
        feeUserNumList.add(DataType.DXSHOULDEMPLOYEE.getType());
        feeUserNumList.add(DataType.DXLEAVEEMPLOYEE.getType());
        feeUserNumList.add(DataType.QDCOVENEMPLOYEE.getType());
        feeUserNumList.add(DataType.QDSHOULDEMPLOYEE.getType());
        feeUserNumList.add(DataType.QDLEAVEEMPLOYEE.getType());
        feeUserNumList.add(DataType.ZNSHOULDEMPLOYEE.getType());
        feeUserNumList.add(DataType.ZNONGUARDEMPLOYEE.getType());
        feeUserNumList.add(DataType.ZNOBUILDEMPLOYEE.getType());
        feeUserNumList.add(DataType.ZNLEAVEEMPLOYEE.getType());
        feeUserNumList.add(DataType.DX_RECRUIT_NOT_POSITIVE.getType());//291
        feeUserNumList.add(DataType.DX_ATTACHE_NOT_POSITIVE.getType());//292
        feeUserNumList.add(DataType.DX_TOTAL_COUNT.getType());//293
        feeUserNumList.add(DataType.DX_MANAGER_COUNT.getType());//294
        feeUserNumList.add(DataType.DX_OFFICER_COUNT.getType());//313
        feeUserNumList.add(DataType.DX_ALL_COUNT.getType());//314
        feeUserNumList.add(DataType.QDSALE.getType());//336
        feeUserNumList.add(DataType.QDMANAGER.getType()); //337
    }

    /**
     * 今日趋势图
     *
     * @param response
     */
    @RequestMapping(value = "/hourchart",
            method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void findAllByHour(HttpServletRequest request, ChartBean chartBean, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (chartBean != null && ParseUtil.isStr2Num(chartBean.getType())) {
                Integer otherType = Integer.valueOf(chartBean.getType());
                resultMap = realTimeStaticHourService.initZLTodayData(otherType);//不含ip&uv的公共查询趋势图{今天&昨天&趋势图}
            } else {
                EmailUtil.warnEveryOne("MainController.findAllByHour param is error" + chartBean, request);
            }
        } catch (Exception e) {
            EmailUtil.warnEveryOne("MainController.findAllByHour has error，chartBean=" + chartBean + "--" + e.getMessage());
            logger.error("MainController.findAllByHour has error，chartBean=" + chartBean, e);
        }
        Map _dataMap = new HashMap();
        _dataMap.put("errno", 0);
        _dataMap.put("data", resultMap);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("hourchart:", e);
        }
    }

    public static final int SHOWAYNUM = 60;//15天--修改为两个月

    /**
     * 整体趋势图 ： 60天的数据
     *
     * @param response
     */
    @RequestMapping(value = "/daychart/",
            method = RequestMethod.GET, produces = {"application/xml", "application/json"})
    public void findAllBy15Day(HttpServletResponse response) throws Exception {
        Map _dataMap = new HashMap();
        try {
            String day = ControllerDateUtil.getToday();// 当天数据
            response.setContentType("application/json; charset=UTF-8");
            Map<String, Object> resultMap = new HashMap<String, Object>();
            List<String> timeList = new ArrayList<String>();//横坐标，时间轴
            List<DayChartBean> dataList = new ArrayList<DayChartBean>();//具体数据
            String pre60Day = ControllerDateUtil.getPre60Day(); // 15天前的日期-->修改为60天前日期

            for (int i = ControllerDateUtil.getPreDayNum(); i <= 0; i++) {
                if (timeList.size() == SHOWAYNUM) {
                    break;
                }
                timeList.add(DateUtil.plusDays("yyyyMMdd", i)); //时间横坐标
            }

            DayChartBean tmpDayChartBean = null;


            //收费会员的60天的数据整理
            List<RealTimeStaticHour> feeUseBy15Days = this.realTimeStaticHourMapper.findFeeUseBy60Day(pre60Day, day);
            if (feeUseBy15Days != null && feeUseBy15Days.size() > 0) {
                tmpDayChartBean = new DayChartBean("付费会员", "万");
                tmpDayChartBean.setData(dataAsTimeFeeUser(timeList, feeUseBy15Days));
                tmpDayChartBean.setIsShow(true);
                dataList.add(tmpDayChartBean);
            }
            //用户行为相关的数据 ip ,pv , uv
            List<RealTimeStaticDay> userBehaviorList = realTimeStaticDayMapper.findUserBehaviorBy60Day(pre60Day, day);
            if (userBehaviorList != null && userBehaviorList.size() > 0) {
                userBehaviorDataAsTime(timeList, userBehaviorList, dataList);
            }

            resultMap.put("time", timeList);
            resultMap.put("dataList", dataList);

            _dataMap.put("errno", 0);
            _dataMap.put("data", resultMap);
        } catch (Exception e) {
            EmailUtil.warnEveryOne("MainController..findAllBy15Day has error，" + "," + e.getMessage());
            logger.error("MainController.findRegionData has error，", e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
//            EmailUtil.warnEveryMe("/daychart/处理异常:" + e.getMessage());
            logger.error("daychart:", e);
        }
    }

    /**
     * 整体趋势： 收费会员 按照制定的时间范围进行数据补足，对应时间没有，默认为0.
     *
     * @param timeList 参照时间
     * @param list     具体的实体数据
     * @return
     */
    private List<Double> dataAsTimeFeeUser(List<String> timeList, List<RealTimeStaticHour> list) {
        Map<String, Double> initMap = initDayTimeMap(timeList);

        for (RealTimeStaticHour realTimeStaticHour : list) {
            initMap.put(realTimeStaticHour.getIrslDateH(), (realTimeStaticHour.getDataCount().doubleValue() / 10000)); //单位为万
        }
        List<Double> dataList = new ArrayList<Double>();
        for (String time : timeList) {
            dataList.add(initMap.get(time));
        }
        return dataList;
    }

    private Map<String, Double> initDayTimeMap(List<String> timeList) {
        Map<String, Double> initMap = new HashMap<String, Double>(); // 按照时间初始化参照map数据。
        for (String time : timeList) {
            initMap.put(time, 0d);
        }
        return initMap;
    }

    /**
     * 整体趋势：IP/PV/UV 根据按照制定的时间范围进行数据补足。对应的时间没有，则默认为0
     *
     * @param timeList         制定的时间
     * @param userBehaviorList IP/PV/UV数据集合
     * @param dataChartList    最终展示在chart图上的 数据集合
     */
    private void userBehaviorDataAsTime(List<String> timeList, List<RealTimeStaticDay> userBehaviorList, List<DayChartBean> dataChartList) {
        DecimalFormat df = new DecimalFormat("0.0000");
        Map<String, Double> initTmpMap = null;
        Map<Integer, Map<String, Double>> userBehaviorDataMap = new HashMap<Integer, Map<String, Double>>();
        for (RealTimeStaticDay realTimeStaticDay : userBehaviorList) {
            if (realTimeStaticDay != null) {
                if (userBehaviorDataMap.get(realTimeStaticDay.getDataType()) == null) {
                    initTmpMap = initDayTimeMap(timeList);
                } else {
                    initTmpMap = userBehaviorDataMap.get(realTimeStaticDay.getDataType());
                }
                initTmpMap.put(realTimeStaticDay.getIrslDate(), (realTimeStaticDay.getDataCount().doubleValue() / 10000));//单位为百万

                userBehaviorDataMap.put(realTimeStaticDay.getDataType(), initTmpMap);
            }
        }
        DayChartBean tmpDayChartBean = null;
        List<Double> dataList = null;
        for (Integer key : userBehaviorDataMap.keySet()) {
            if (key <= DataType.UV.getType()) {
                tmpDayChartBean = new DayChartBean(DataType.getName(key), "万");
                dataList = new ArrayList<Double>();
                for (String time : timeList) {
                    if (key == DataType.IP.getType()) { //累加上mip站的PV,UV,IP
                        if (userBehaviorDataMap.containsKey(DataType.MIP_IP.getType())) {
                          if (userBehaviorDataMap.get(DataType.MIP_IP.getType()).containsKey(time)) {
                            dataList.add(Double.valueOf(df.format(userBehaviorDataMap.get(key).get(time) + userBehaviorDataMap.get(DataType.MIP_IP.getType()).get(time))));
                          }
                          else{
                            dataList.add(Double.valueOf(df.format(userBehaviorDataMap.get(key).get(time))));
                          }
                        }
                        else{
                          dataList.add(Double.valueOf(df.format(userBehaviorDataMap.get(key).get(time))));
                        }
                    } else if (key == DataType.PV.getType()) {
                      if (userBehaviorDataMap.containsKey(DataType.MIP_PV.getType())) {
                        if (userBehaviorDataMap.get(DataType.MIP_PV.getType()).containsKey(time)) {
                          dataList.add(Double.valueOf(df.format(userBehaviorDataMap.get(key).get(time) + userBehaviorDataMap.get(DataType.MIP_PV.getType()).get(time))));
                        }
                        else{
                          dataList.add(Double.valueOf(df.format(userBehaviorDataMap.get(key).get(time) )));
                        }
                      }
                      else{
                        dataList.add(Double.valueOf(df.format(userBehaviorDataMap.get(key).get(time) )));
                      }

                    } else if (key == DataType.UV.getType()) {

                      if (userBehaviorDataMap.containsKey(DataType.MIP_UV.getType())) {
                        if (userBehaviorDataMap.get(DataType.MIP_UV.getType()).containsKey(time)) {
                          dataList.add(Double.valueOf(df.format(userBehaviorDataMap.get(key).get(time) + userBehaviorDataMap.get(DataType.MIP_UV.getType()).get(time))));
                        }
                        else{
                          dataList.add(Double.valueOf(df.format(userBehaviorDataMap.get(key).get(time))));
                        }
                      }
                      else{
                        dataList.add(Double.valueOf(df.format(userBehaviorDataMap.get(key).get(time))));
                      }

                    }
                }
                tmpDayChartBean.setData(dataList);
                tmpDayChartBean.setIsShow(true);
                dataChartList.add(tmpDayChartBean);
            }
        }

    }


    @RequestMapping("index.html")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/screen/login");

        //判断用户是否处于登录状态
        Cookie[] cookies = request.getCookies();//获取cookie数组
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if ("dataUser".equalsIgnoreCase(cookie.getName())) {
                    cookie.setMaxAge(30 * 60);// 设置为30min
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    mv.setViewName("/screen/index");
                    return mv;
                }
            }
        }

        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        if (null != username && null != pass) {
            try {
                username = LZString.decompressFromEncodedURIComponent(username);
                pass = LZString.decompressFromEncodedURIComponent(pass);

                RealtimeUserInfoMapper realtimeUserInfoMapper = SpringContextHolder.getBean("realtimeUserInfoMapper");
                List<RealtimeUserInfo> userInfoList = realtimeUserInfoMapper.selectByMisName(username);
                if (userInfoList != null && userInfoList.size() > 0) {
                    username = URLEncoder.encode(URLEncoder.encode(username, "utf-8"), "utf-8");
                    //发送 POST 请求
                    String sr = sendPost("http://sso.hc360.com/internallogin", "LoginType=json&callback=callback&username=" + username + "&pass=" + pass);
                    if (sr.indexOf("success:") > 0) {
                        mv.setViewName("/screen/index");
                        //将用户放进cookie
                        Cookie cookie = new Cookie("dataUser", userInfoList.get(0).getId().toString());
                        cookie.setMaxAge(30 * 60);// 设置为30min
                        cookie.setPath("/");
                        response.addCookie(cookie);

                        Cookie cookie1 = new Cookie("dataUserName", username);
                        cookie1.setMaxAge(30 * 60);// 设置为30min
                        cookie1.setPath("/");
                        response.addCookie(cookie1);
                    }
                } else {
                    mv.setViewName("/error/nolimit");
                }

            } catch (Exception e) {
                EmailUtil.warnEveryOne("login is error" + e.getMessage());
                logger.error("login is error", e);
            }
        }

        return mv;
    }


    private String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            EmailUtil.warnEveryOne("sendPost is error" + e.getMessage());
            logger.error("sendPost is error", e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


}
