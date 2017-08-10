package com.hc360.dataweb.service.impl;

import com.hc360.dataweb.common.constants.ChartsConstant;
import com.hc360.dataweb.dao.RealTimeStatic2DataMapper;
import com.hc360.dataweb.dao.RealTimeStaticDayMapper;
import com.hc360.dataweb.dao.RealTimeStaticHourMapper;
import com.hc360.dataweb.model.*;
import com.hc360.dataweb.service.RealTimeStaticHourService;
import com.hc360.dataweb.util.CommonUtil;
import com.hc360.dataweb.util.ControllerDateUtil;
import com.hc360.dataweb.util.EmailUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by zhangwanru on 2017/2/22.
 */
@Service
public class RealTimeStaticHourServiceImpl implements RealTimeStaticHourService {
    private Logger logger = Logger.getLogger(RealTimeStaticHourServiceImpl.class);
    @Autowired
    RealTimeStaticHourMapper realTimeStaticHourMapper;
    @Autowired
    RealTimeStaticDayMapper realTimeStaticDayMapper;
    @Autowired
    RealTimeStatic2DataMapper realTimeStatic2DataMapper;

     @Override
    public Map<String, Object> initTodayData(Integer otherType)throws Exception{
         List<HourChartBean> beans = new ArrayList<HourChartBean>();
         String day = ControllerDateUtil.getToday();//取今天的日期
         String yesterday = ControllerDateUtil.getYesterday();//取昨天的日期
         String weekday = ControllerDateUtil.getPreNDay(-7);//取七天前的日期
         List<String> times = new ArrayList<String>();
         times.add(yesterday);
         times.add(weekday);
         List<Integer> types = new ArrayList<Integer>();
         types.add(DataType.IP.getType());
         types.add(DataType.UV.getType());
         types.add(otherType);

         HourChartBean bean = null;
         for(Integer type:types){//处理IP/UV/其他数据类型今天的数据
             bean = new HourChartBean();
             disposeTodayDataCPYY(bean, day, type);
             beans.add(bean);
         }
         for(String time:times){//处理昨天&七天前的数据
             bean = new HourChartBean();
             disposeTodayDataCPYY(bean, time, otherType);
             beans.add(bean);
         }
        //横坐标，时间轴 24h
        List<String> time = CommonUtil.getTimeShaft();
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("time",time);//时间维度
        dataMap.put("dataList",beans);
        return dataMap;

    }

    /**
     * 趋势图_今天按钮:处理小时数据公用方法{产品运营数据处理}
     * @param bean 空vo对象
     * @param time 查询的时间{yyyymmdd}
     * @param type 查询的类型
     */
    private void disposeTodayDataCPYY(HourChartBean bean, String time, Integer type) {
        List<String> timeList = CommonUtil.getTimeShaft(time);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("day",time);
        param.put("type", type);
        List<RealTimeStaticDoubleHour> hourData = realTimeStaticHourMapper.findDoubleTodayData(param);
        List<Object> data = convertHourDouble(hourData, timeList);
        bean.setData(data);
        if(time.equals(ControllerDateUtil.getYesterday())){
            bean.setName("昨天");
        }else if(time.equals(ControllerDateUtil.getPreNDay(-7))){
            bean.setName("七天前");
        }else if(type == DataType.IP.getType().intValue() || type == DataType.UV.getType().intValue()){
            bean.setName(CommonUtil.initName(type));
        }else{
            bean.setName("今天");
        }
        if(isShow(type, time)){
            bean.setIsShow(true);
        }else{
            bean.setIsShow(false);
        }
        bean.setUnit(CommonUtil.initUnit(type));
    }
    /**
     * 趋势图_今天按钮:处理小时数据公用方法{总览页面处理}
     * @param bean 空vo对象
     * @param time 查询的时间{yyyymmdd}
     * @param type 查询的类型
     */
    private void disposeTodayDataZL(HourChartBean bean, String time, Integer type) {
        List<String> timeList = CommonUtil.getTimeShaft(time);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("day",time);
        param.put("type", type);
        List<RealTimeStaticDoubleHour> hourData = realTimeStaticHourMapper.findDoubleTodayData(param);
        List<Object> data = convertHourDouble(hourData, timeList);
        bean.setData(data);
        if(time.equals(ControllerDateUtil.getYesterday())){
            bean.setName("昨天");
        }else if(time.equals(ControllerDateUtil.getPreNDay(-7))){
            bean.setName("七天前");
        }else{
            bean.setName("今天");
        }
        bean.setIsShow(true);
        bean.setUnit(CommonUtil.initUnit(type));
    }

    /**
     * 判断是否标记默认显示的图例
     * @param type
     * @param time
     * @return
     */
    private boolean isShow(Integer type, String time) {
        boolean flag;
        if(time.equals(ControllerDateUtil.getYesterday()) || time.equals(ControllerDateUtil.getPreNDay(-7)) || type == DataType.IP.getType().intValue() || type == DataType.UV.getType().intValue()){
            flag=false;
        }else{
            flag=true;
        }
        return flag;
    }

    @Override
    public Map<String, Object> initBeforeData(Integer otherType,String timeFlag) throws Exception{
        int dayCount = CommonUtil.initTime(timeFlag);//初始化查询天数
        String day = ControllerDateUtil.getToday();//取今天的日期
        String preDay = ControllerDateUtil.getPreNDay(-(dayCount - 1)); // 查询多少天前的日期
        //横坐标，时间轴
        List<String> timeList = CommonUtil.getTimeShaftD(dayCount);
        List<Integer> types = new ArrayList<Integer>();
        if(!(DataType.P4PCONSUMPTION.getType()==otherType && ChartsConstant.TOTAL.equals(timeFlag))
                && !DataType.BAIDU_LM_DAY.getType().equals(otherType)){
            types.add(DataType.IP.getType());
            types.add(DataType.UV.getType());
        }
        types.add(otherType);
        List<DayChartBean> beans = new ArrayList<DayChartBean>();
        DayChartBean bean = null;
        for(Integer type:types){//处理IP/UV/其他数据类型今天的数据
            bean = new DayChartBean();
            disposeBeforeData(bean,day,preDay,type,timeList,timeFlag);
            beans.add(bean);
        }
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("time",timeList);//时间维度
        dataMap.put("dataList",beans);
        return dataMap;
    }

    /**
     * 趋势图_七天/30天/累计按钮:处理天表数据公用方法
     * @param bean 空vo对象
     * @param day 今天时间
     * @param preDay 起始时间
     * @param type 数据类型
     * @param timeList 比对的时间list
     * @param timeFlag 标记当前是否在查询累计数据
     * @throws Exception
     */
    private void disposeBeforeData(DayChartBean bean, String day, String preDay, Integer type, List<String> timeList, String timeFlag) throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("day",day);
        param.put("preDay",preDay);
        if(timeFlag.equals(ChartsConstant.TOTAL)){
            type = CommonUtil.initOtherType(type);//转换新增或累计数据的data_type值
        }
        param.put("type", type);
        List<RealTimeStaticDoubleDay> dayData = realTimeStaticDayMapper.findDayDoubleData(param);
        bean.setData(convertDayData(dayData, timeList));
        bean.setName(CommonUtil.initName(type));
        bean.setUnit(CommonUtil.initUnit(type));
        if(isShow(type)){
            bean.setIsShow(true);
        }else{
            bean.setIsShow(false);
        }
    }

    private boolean isShow(Integer type) {
        boolean flag;
        if(type == DataType.IP.getType().intValue() || type == DataType.UV.getType().intValue()){
            flag=false;
        }else{
            flag=true;
        }
        return flag;
    }

    @Override
    public Map<String, Object> initSecondTodayData(Integer otherType)throws Exception {
        String day = ControllerDateUtil.getToday();//取今天的日期
        String yesterday = ControllerDateUtil.getYesterday();//取昨天的日期
        String weekday = ControllerDateUtil.getPreNDay(-7);//取七天前的日期
        List<String> times = new ArrayList<String>();
        times.add(day);
        times.add(yesterday);
        times.add(weekday);
        //横坐标，时间轴 24h
        List<String> timeShaft = CommonUtil.getTimeShaft();
        List<HourChartBean> beans = new ArrayList<HourChartBean>();
        HourChartBean beanOther = null;
        for(String time:times) {//处理今天&昨天&七天前的数据
            beanOther = new HourChartBean();
            disposeTodayDataCPYY(beanOther, time, otherType);
            beans.add(beanOther);
        }
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("time",timeShaft);//时间维度
        dataMap.put("dataList",beans);
        return dataMap;
    }

    /***第二个折线图改成p4p的相关数据
     * @param chartBean
     * @return
     * @throws Exception
     */
    public Map<String,Object> initSecondTodayDataNew(ChartBean chartBean)throws Exception  {
        Integer otherType =Integer.parseInt( chartBean.getType());
        String time = chartBean.getTime();

        int dayCount = CommonUtil.initTime(time);//初始化查询天数
        String day = ControllerDateUtil.getToday();//取今天的日期
        //横坐标，时间轴 24h
        List<String> timeShaft = null;
        Map<String,Object> params = new HashMap<>();
        List<Integer> types = new ArrayList<>();
        types.add(otherType);
        if((DataType.P4P_KEY_SUM.getType()+"").equals(chartBean.getType()) ){
            types.add(DataType.P4P_KEY_ZT_COUNT.getType());
        }else if((DataType.P4P_KEY_TOP50_PEOPLE.getType()+"").equals(chartBean.getType())){
            types.add(DataType.P4P_KEY_TOP50_PRICE.getType());
        }
        params.put("list",types);
        params.put("day",day);
        String preDay =ControllerDateUtil.getPreNDay(-(dayCount - 1)); // 查询多少天前的日期
        params.put("preday",preDay);
        String hdFlag = ""; //标志是按小时显示还是按天显示。以便于做日期的截取
        List<RealTimeStaticDoubleHour>  results = null;
        if( ChartsConstant.TODAY.equals(time)) {//今天
            timeShaft = CommonUtil.getTimeShaft();
            results = this.realTimeStaticHourMapper.findDoubleByTodayDay(params);
            hdFlag="h";
        }else if(ChartsConstant.WEEK.equals(time)){//一周
            timeShaft = CommonUtil.getTimeShaftD(7);
            results = this.realTimeStaticHourMapper.findDoubleByDay(params);
            hdFlag="d";

        }else if(ChartsConstant.MONTH.equals(time)){//一个月
            timeShaft = CommonUtil.getTimeShaftD(30);
            results = this.realTimeStaticHourMapper.findDoubleByDay(params);

            hdFlag="d";
        }

        List<HourChartBean> beans = covertData(types,timeShaft,results,hdFlag);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("time",timeShaft);//时间维度
        dataMap.put("dataList",beans);
        return dataMap;
    }

    /**
     * 将数据转化成json需要的格式，并且保证每个时间都有对应的数据。如果没有值，则默认为“”
     * */
    private  List<HourChartBean> covertData(List<Integer> types , List<String> timeShaft , List<RealTimeStaticDoubleHour> results ,String hdFlag ){
        List<HourChartBean> beans = new ArrayList<HourChartBean>();

        Map<Integer,Map<String,Object>> modelMap = new HashMap<>();
        Map<String,Object> timeModelMap =null;

        HourChartBean beanOther = null;

        for(Integer t : types){
            timeModelMap = CommonUtil.initHourTimeMap(timeShaft);
            modelMap.put(t,timeModelMap);
        }
        Map<String,Object> _timeModelMap =null;
        if(results !=null && results.size() > 0){
            for(RealTimeStaticDoubleHour rsh : results){
                _timeModelMap = modelMap.get(rsh.getDataType());
                if("h".equals(hdFlag)){
                    _timeModelMap.put(rsh.getIrslDateH().substring(8,10) , rsh.getDataCount());
                }else if("d".equals(hdFlag)){
                    _timeModelMap.put(rsh.getIrslDateH().substring(0,8) , rsh.getDataCount());
                }
                modelMap.put(rsh.getDataType(),_timeModelMap);
            }
            List<Object> datas = null;
            Map<String,Object> _timeModelMap2 = null;
            for(Integer t : types){
                beanOther = new HourChartBean();
                beanOther.setName(DataType.getName(t));
                beanOther.setUnit(CommonUtil.initUnit(t));
                beanOther.setIsShow(true);
                _timeModelMap2 = modelMap.get(t);
                datas = new ArrayList<>();
                for(String _time : timeShaft){
                    datas.add(_timeModelMap2.get(_time));
                }
                beanOther.setData(datas);
                beans.add(beanOther);
            }

        }
        return beans;
    }


    /**
     * 转换粘性买家时间轴与数据(9:00到23:00)---暂时取消粘性买家的显示
     * @param beanOther
     * @param time
     * @return
     */
    private List<String> convertViscidit(HourChartBean beanOther, List<String> time) {
        List<Object> datas = new ArrayList<Object>();
        List<String> lists = new ArrayList<String>();
        if(beanOther!= null && beanOther.getData()!=null && beanOther.getData().size()>0){
            for (int i = 0; i <beanOther.getData().size() ; i++) {
                if(i >= 9){
                    datas.add(beanOther.getData().get(i));
                }
            }
            for (int i = 0; i <time.size() ; i++) {
                if(i >= 9){
                    lists.add(time.get(i));
                }
            }
            beanOther.setData(datas);
        }
        return lists;
    }

    @Override
    public Map<String, Object> initSecondBeforeData(Integer type, String timeFlag)throws Exception {
        int dayCount = CommonUtil.initTime(timeFlag);//初始化查询天数
        List<String> timeList = CommonUtil.getTimeShaftD(dayCount);
        String day = ControllerDateUtil.getToday();//取今天的日期
        String preDay =ControllerDateUtil.getPreNDay(-(dayCount - 1)); // 查询多少天前的日期
        List<DayChartBean> beans = new ArrayList<DayChartBean>();
        DayChartBean bean = new DayChartBean();
        disposeBeforeData(bean, day, preDay, type, timeList, timeFlag);
        beans.add(bean);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("time",timeList);//时间维度
        dataMap.put("dataList",beans);
        return dataMap;
    }

    @Override
    public Map<String, Object> initZLTodayData(Integer otherType) throws Exception {
        String day = ControllerDateUtil.getToday();//取今天的日期
        String yesterday = ControllerDateUtil.getYesterday();//取昨天的日期
        String weekday = ControllerDateUtil.getPreNDay(-7);//取七天前的日期
        List<String> times = new ArrayList<String>();
        times.add(day);
        times.add(yesterday);
        times.add(weekday);
        List<String> times2 = new ArrayList<String>();
        times2.add(day);
        times2.add(yesterday);
        //横坐标，时间轴 24h
        List<String> timeShaft = CommonUtil.getTimeShaft();
        List<HourChartBean> beans = new ArrayList<HourChartBean>();
        HourChartBean beanOther = null;

        if(otherType == DataType.FEEUSERTOTAL.getType().intValue()){//付费会员只查询今天&昨天
            for(String time:times2) {//处理今天&昨天
                beanOther = new HourChartBean();
                disposeTodayDataZL(beanOther, time, otherType);
                beans.add(beanOther);
            }
        }else{//IP&UV&PV查询今天&昨天&七天前
            for(String time:times) {//处理今天&昨天&七天前的数据
                beanOther = new HourChartBean();
                disposeTodayDataZL(beanOther, time, otherType);
                beans.add(beanOther);
            }
        }
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("time",timeShaft);//时间维度
        dataMap.put("dataList",beans);
        return dataMap;
    }

    /*转换天表数据,p4p消耗 保留两位小数*/
    private List<Double> convertDayData(List<RealTimeStaticDoubleDay> otherData, List<String> times) {
        DecimalFormat df = new DecimalFormat("#####0.00");
        Map<String, Double> initMap = CommonUtil.initDayTimeMap(times);//初始化时间
        List<Double> dataList = new ArrayList<Double>();
        if(otherData != null && otherData.size() > 0){
            String format = "";
            for (RealTimeStaticDoubleDay realTimeStaticDouble : otherData) {
                if(realTimeStaticDouble.getDataCount() != null){
                    if(CommonUtil.initArith(realTimeStaticDouble.getDataType())){
                        initMap.put(realTimeStaticDouble.getIrslDate(), (realTimeStaticDouble.getDataCount().doubleValue() / 10000)); //单位为万
                    }else{
                        if(realTimeStaticDouble.getDataType().intValue() == DataType.P4PCONSUMPTION.getType() || realTimeStaticDouble.getDataType().intValue() == DataType.P4PCONSUMPTIONTOTAL.getType()){
                            format = df.format(realTimeStaticDouble.getDataCount());
                            initMap.put(realTimeStaticDouble.getIrslDate(), Double.parseDouble(format)); //单位为元
                        }else{
                            initMap.put(realTimeStaticDouble.getIrslDate(), (realTimeStaticDouble.getDataCount().doubleValue())); //单位为个/元
                        }
                    }
                }else{
                    logger.error("天表:数据时间:"+ realTimeStaticDouble.getIrslDate()+"数据类型:"+ realTimeStaticDouble.getDataType()+"为空");
                }
            }
            for (String time : times) {
                dataList.add(initMap.get(time));
            }
        }else{
            logger.error("RealTimeStaticHourServiceImpl.折线图获取小时表数据为空");
        }
        return  dataList;
    }





    //转换小时表数据
    private List<Object> convertHourDouble(List<RealTimeStaticDoubleHour> hourDatas, List<String> times) {
        DecimalFormat df = new DecimalFormat("#####0.00");
        Map<String, Object> initMap = CommonUtil.initHourTimeMap(times);//初始化时间
        Map<String,Object> tempMap = new HashMap<>();//用于数据补零,小时数据当前最新数据之前的补零,之后的维持原有""处理
        List<Object> dataList = new ArrayList<Object>();
        if(hourDatas != null && hourDatas.size() > 0){
            for (RealTimeStaticDoubleHour realTimeStaticHour : hourDatas) {
                if(realTimeStaticHour.getDataCount()!=null && realTimeStaticHour.getDataType()!=null){
                    if (CommonUtil.initArith(realTimeStaticHour.getDataType())) {
                        initMap.put(realTimeStaticHour.getIrslDateH(), (realTimeStaticHour.getDataCount().doubleValue() / 10000)); //单位为万
                    } else {
                        if(realTimeStaticHour.getDataType().intValue() == DataType.P4PCONSUMPTION.getType()){//P4P消耗
                            initMap.put(realTimeStaticHour.getIrslDateH(), Double.parseDouble(df.format(realTimeStaticHour.getDataCount()))); //单位为个/元
                        }else{
                            initMap.put(realTimeStaticHour.getIrslDateH(), Math.floor(realTimeStaticHour.getDataCount())); //单位为个/元
                        }
                    }
                    tempMap.put("temp",realTimeStaticHour.getIrslDateH());
                }else{
                    logger.error("小时表:"+"数据时间:"+realTimeStaticHour.getIrslDateH()+"数据类型:"+realTimeStaticHour.getDataType() + "-- 数据为空。");
                }
            }
                for (String time : times) {
                    if(Integer.valueOf(time)<=Integer.valueOf((String) tempMap.get("temp"))){//有数据的最大时间之前数据为空进行补零
                        if("".equals(initMap.get(time))){
                            dataList.add(0);
                        }else{
                            dataList.add(initMap.get(time));
                        }
                    }else{
                        dataList.add(initMap.get(time));
                    }
                }
        }else{
            logger.error("RealTimeStaticHourServiceImpl.折线图获取小时表数据为空");
        }
        return  dataList;
    }


    public void addP4pData(Map<String,Object> dataList ) throws Exception{
        String day = ControllerDateUtil.getToday();//取今天的日期
        Map<String,Object> params = new HashMap<>();
        params.put("yesterDay" ,day);
        initMap("todaydata",dataList,params);
        params = new HashMap<>();
        params.put("yesterDay" ,ControllerDateUtil.getPreNDay(-1));
        initMap("yesterdaydata",dataList,params);
    }

    private void initMap(String dayFlag , Map<String,Object> dataList, Map<String,Object> params ) throws Exception {
        List<Integer> types = new ArrayList<>();
        types .add(DataType.P4P_KEY_SUM.getType());
        types .add(DataType.P4P_KEY_ZT_COUNT.getType());
        params.put("list",types);
        DecimalFormat threeNumDf = new DecimalFormat(",###");//每三位分隔一下
        List<RealTimeStaticHour> results = this.realTimeStaticHourMapper.findRealTimeLastDataYester(params);
        List<MainBean> mainBeans = (List<MainBean>) dataList.get(dayFlag);
        if(results!=null && results.size() > 0){
            MainBean mainBean = null;
            for(RealTimeStaticHour rth : results){
                mainBean = new MainBean(DataType.getName(rth.getDataType()),threeNumDf.format(rth.getDataCount()));
                mainBeans.add(mainBean);
            }
            dataList.put(dayFlag,mainBeans);
        }
    }


    /**
     * 气泡图
     * @param chartBean
     * @return
     */
    public Map<String,Object>  loadP4pScatter(ChartBean chartBean){
        Map<String,Object> jsonObjMap = new HashMap<>();


        List<RealTimeStatic2Data> dateInfos = this.realTimeStatic2DataMapper.findMaxDate();
        if(dateInfos == null || dateInfos.size() ==0 ){
            EmailUtil.warnEveryOne("sjpt_realtime_static_2data 数据是空的");
            return null;
        }
        String day = dateInfos.get(0).getDataDate();

        Map<String,Object> params = new HashMap<>();
        if(ChartsConstant.PEOPLE_SORT.equals(chartBean.getTime())){
            params.put("sort","DATA_COUNT1");
            params.put("type",chartBean.getType());
        }else{
            params.put("sort","DATA_COUNT2");
            params.put("type",DataType.P4P_KEY_TOP50_PRICE.getType());
        }
        params.put("day",day);

        List<RealTimeStatic2Data> results = this.realTimeStatic2DataMapper.findByLastHour(params);
        List<BubbleBean> bubbleBeans = new ArrayList<>();
        if(results!=null && results.size() > 0){
            BubbleBean bubbleBean = null;
            for(RealTimeStatic2Data rt2d : results){
                if(ChartsConstant.PEOPLE_SORT.equals(chartBean.getTime())) {
                    bubbleBean = new BubbleBean(rt2d.getElement(), rt2d.getDataCount1().longValue(), rt2d.getDataCount2(), rt2d.getDataCount2());
                }else{
                    bubbleBean = new BubbleBean(rt2d.getElement(), rt2d.getDataCount1().longValue(), rt2d.getDataCount2(), Double.valueOf(rt2d.getDataCount1().doubleValue()));
                }
                bubbleBeans.add(bubbleBean);
            }
        }
        jsonObjMap.put("dataList",bubbleBeans);
        return jsonObjMap;
    }
    private String randomDouble(Double dataCount){
        DecimalFormat df = new DecimalFormat("0.00");
        int len = (dataCount.longValue()+"").length();
        Double z = dataCount/len *2;
        return df.format(z);
    }

    @Override
    public Map<String, Object> initMonthData(Integer otherType,String timeFlag) throws Exception{
        int dayCount = CommonUtil.initTime(timeFlag);//初始化查询天数
        String day = ControllerDateUtil.getToday();//取今天的日期
        String preDay = ControllerDateUtil.getPreNDay(-(dayCount - 1)); // 查询多少天前的日期
        //横坐标，时间轴
        List<String> timeList = CommonUtil.getTimeShaftD(dayCount);
        List<Integer> types = new ArrayList<Integer>();
        if(!(DataType.P4PCONSUMPTION.getType()==otherType && ChartsConstant.TOTAL.equals(timeFlag))
                && !DataType.BAIDU_LM_DAY.getType().equals(otherType)){
            types.add(DataType.IP.getType());
            types.add(DataType.UV.getType());
        }
        types.add(otherType);
        List<DayChartBean> beans = new ArrayList<DayChartBean>();
        DayChartBean bean = null;
        for(Integer type:types){//处理IP/UV/其他数据类型今天的数据
            bean = new DayChartBean();
            disposeBeforeData(bean,day,preDay,type,timeList,timeFlag);
            beans.add(bean);
        }
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("time",timeList);//时间维度
        dataMap.put("dataList",beans);
        return dataMap;
    }
}
