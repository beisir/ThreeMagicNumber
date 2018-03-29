package com.hc360.dataweb.service.impl;

import com.hc360.dataweb.common.constants.ChartsConstant;
import com.hc360.dataweb.dao.*;
import com.hc360.dataweb.model.*;
import com.hc360.dataweb.service.FightCapacityDataService;
import com.hc360.dataweb.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by HC360 on 2017/3/3.
 */
@Service
public class FightCapacityDataServiceImpl implements FightCapacityDataService {
    private Logger logger = Logger.getLogger(FightCapacityDataServiceImpl.class);
    @Autowired
    private RealTimeStaticDayMapper realTimeStaticDayMapper;
    @Autowired
    private RealtimeStaticWeekMapper realtimeStaticWeekMapper;
    @Autowired
    private RealTimeStaticHourMapper realTimeStaticHourMapper;
    @Autowired
    private RealtimeStaticMonthMapper realtimeStaticMonthMapper;
    @Autowired
    private RealTimeMonthWeekBaseMapper realTimeMonthWeekBaseMapper;


    @Override
    public void initFightData(Map<String, Object> dataTotal) throws Exception {
        String day = ControllerDateUtil.getToday();//取今天的日期
        String yesterDay = ControllerDateUtil.getYesterday();//取昨天的日期
        List<Integer> dataTypes = new ArrayList<Integer>();//存放查询今天的数据类型
        List<Integer> togetherTypes = new ArrayList<Integer>();//存放查询昨天同一时间的数据类型
        List<Integer> lastTypes = new ArrayList<Integer>();//存放查询昨天最后时间的数据类型
        initTodayDataTypes(dataTypes);//今天
        List<MainBean> mainBeans = getTodayData(day, dataTypes);

        /*人均有效通话次数*/
        MainBean validcallnum = getValidcallnum(day, null);//今天

        /*查询周表(市场部发稿量)*/
        /*Integer flag = 1;//获取时间最近的一条数据
        MainBean weekDataRecently = getTodayDataWeek(flag);
        flag = 2;//获取时间第二近的一条数据
        MainBean weekData = getTodayDataWeek(flag);

        mainBeans.add(weekDataRecently);*/
        mainBeans.add(validcallnum);

        initTogetherDataTypes(lastTypes);//昨天最后时间
        initLastDataTypes(togetherTypes);//昨天同一时间
        List<MainBean> mainBeans2 = getYesterdayData(day, yesterDay, togetherTypes, lastTypes);
        /*人均有效通话次数*/
        MainBean validcallnumY = getValidcallnum(day, yesterDay);//昨天
        //mainBeans2.add(weekData);
        mainBeans2.add(validcallnumY);
        dataTotal.put("todaydata", mainBeans);
        dataTotal.put("yesterdaydata", mainBeans2);
    }

    @Override
    public void initTurnoverWeek(Map<String, Object> data, int flag) throws Exception {
        //初始化电销&渠道流水(当月周度数据实际与预估对比图)
        List<HourChartBean> dataList = new ArrayList<>();

        Integer practicalType = 0;
        Integer estimateType = 0;
        if (flag == 1) {//电销
            practicalType = DataType.DXTURNOVELJ.getType();//电销当天实际值(累计)
            estimateType = DataType.DXTURNOVEYG.getType();//电销当周预估值
        } else if (flag == 2) {//渠道
            practicalType = DataType.QDTURNOVERLJ.getType();//渠道当天实际值(累计)
            estimateType = DataType.QDTURNOVERYG.getType();//渠道当周预估值
        }
        else if (flag == 3) {//电销渠道整体
          practicalType = DataType.DXQDSJ.getType();//电销渠道整体当天实际值(累计)
          estimateType = DataType.DXQDYG.getType();//电销渠道整体当周预估值
        }else if (flag == 4) {//电销新签
          practicalType = DataType.XQDXTURNOVERZL.getType();//电销新签当天实际值(累计)
          estimateType = DataType.XQDXTURNOVEYG.getType();//电销新签当周预估值
        }else if (flag == 5) {//电销增值
          practicalType = DataType.ZQDXTURNOVERZL.getType();//电销增值当天实际值(累计)
          estimateType = DataType.ZQDXTURNOVEYG.getType();//电销增值当周预估值
        }
        String day = DateUtil.getNow("yyyy-MM-dd");
        //实际值:查询周表最后六条数据
        List<String> times = getWeeks( day,ChartsConstant.WEEKNUMS);
        HourChartBean practicalBean = new HourChartBean();
//        List<String> times = initPractical(practicalBean, practicalType);
        initEstimates(practicalBean, times, practicalType);
        //x轴显示:2017年第n周(根据查询出的数据出x轴)
        List<String> timeList = CommonUtil.initWeekTime(times);
        //预估值:根据实际值最新的数据获取预估值数据
        HourChartBean estimateBean = new HourChartBean();
        initEstimates(estimateBean, times, estimateType);
        dataList.add(estimateBean);
        dataList.add(practicalBean);
        data.put("dataList", dataList);
        data.put("time", timeList);
    }

    /**
     * 获取某日期最近的weeks周，并转化成List<String>格式
     */
    private List<String> getWeeks(String day, int weeks){
        List<String> _weeks = new ArrayList<>();
        List<RealTimeMonthWeekBase> weekBases = this.realTimeMonthWeekBaseMapper.findMonthWeekBase(day,weeks);
        if(weekBases!=null && weekBases.size()>0){
            for(RealTimeMonthWeekBase realTimeMonthWeekBase : weekBases){
                _weeks.add(realTimeMonthWeekBase.getWeekNum().replace("年", "").replace("月","").replace("周",""));
            }
        }
        return _weeks;
    }

    @Override
    public void initTurnoverWeek_DX(Map<String, Object> data) throws Exception {
        //初始化电销&渠道流水(当月周度数据实际与预估对比图)
        List<HourChartBean> dataList = new ArrayList<>();
        String day = DateUtil.getNow("yyyy-MM-dd");
        Integer practicalType = 0;
        Integer estimateType = 0;

        practicalType = DataType.DXTURNOVELJ.getType();//电销当天实际值(累计)
        estimateType = DataType.DXTURNOVEYG.getType();//电销当周预估值
        List<String> times = getWeeks( day,ChartsConstant.WEEKNUMS);
        //实际值:查询周表最后六条数据
        HourChartBean practicalBean = new HourChartBean();
//        initPractical(practicalBean, practicalType);
        initEstimates(practicalBean, times, practicalType);
        //x轴显示:2017年第n周(根据查询出的数据出x轴)
        List<String> timeList = CommonUtil.initWeekTime(times);
        //预估值:根据实际值最新的数据获取预估值数据
        HourChartBean estimateBean = new HourChartBean();
        initEstimates(estimateBean, times, estimateType);
        estimateBean.setName("电销整体预估值");
        dataList.add(estimateBean);

        estimateBean = new HourChartBean();
        initEstimates(estimateBean, times, DataType.XQDXTURNOVEYG.getType());
        estimateBean.setName("电销新签预估值");
        dataList.add(estimateBean);

        estimateBean = new HourChartBean();
        initEstimates(estimateBean, times, DataType.ZQDXTURNOVEYG.getType());
        estimateBean.setName("电销增值预估值");
        dataList.add(estimateBean);


        practicalBean.setName("电销整体实际值");
        dataList.add(practicalBean);

        estimateBean = new HourChartBean();
        initEstimates(estimateBean, times, DataType.XQDXTURNOVERZL.getType());
        estimateBean.setName("电销新签实际值");
        dataList.add(estimateBean);

        estimateBean = new HourChartBean();
        initEstimates(estimateBean, times, DataType.ZQDXTURNOVERZL.getType());
        estimateBean.setName("电销增值实际值");
        dataList.add(estimateBean);

        data.put("dataList", dataList);
        data.put("time", timeList);
    }

    @Override
    public void initFightChartData(Map<String, Object> data, ChartBean chartBean) throws Exception {
        String day = ControllerDateUtil.getToday();//取今天的日期
        String preDay = "";
        List<Object> dataList = new ArrayList<Object>();
        HourChartBean bean = null;
        DayChartBean dayBean = null;
        Integer dataType;
        List<String> time = null;
        List<String> timeF = null;
        if ("0".equals(chartBean.getTime())) {//今天查询小时表
            time = CommonUtil.getTimeShaft();//时间刻度(展示)
            timeF = CommonUtil.getTimeShaft(day);//时间刻度(map比对)
        } else {//周/月/全部
            int datCount = CommonUtil.initTime(chartBean.getTime());
            time = CommonUtil.getTimeShaftD(datCount);//时间刻度
            preDay = ControllerDateUtil.getPreNDay(-(datCount - 1));
        }
        if (ParseUtil.isStr2Num(chartBean.getType())) {
            dataType = Integer.valueOf(chartBean.getType());
            if (dataType == DataType.EXTERNALSENDDRAFT.getType().intValue()) {//发稿量查询周表
                List<String> times = new ArrayList<String>();
                bean = new HourChartBean();
                getWeekData(times, dataType, bean);
                time = CommonUtil.initWeekTime(times);//根据查询出来的数据时间生成time时间轴
                dataList.add(bean);
            } else if (dataType == DataType.VALIDCALLNUMBER.getType().intValue()) {//有效通话次数
                // _ _目前电销&全部&渠道数据是都查询并且传到前端的,
                // 全部与渠道是由汪浩隐藏,色块部分只查询电销的
                List<Integer> dataTypes = new ArrayList<Integer>();
                if (ChartsConstant.TODAY.equals(chartBean.getTime())) {//今天查询小时表
                    initValidCallType(dataTypes);
                    for (Integer type : dataTypes) {
                        bean = new HourChartBean();
                        getHourDoubleData(day, type, bean, timeF);
                        dataList.add(bean);
                    }
                } else if (ChartsConstant.MONTH_DATA.equals(chartBean.getTime())) {//月度数据{月表}
                    bean = new HourChartBean();
                    List<String> weekTimes = DateUtil.getMonthInfo(8, "yyyyMM");
                    initMonthData(DataType.DXVALIDCALLNUMBER.getType(), bean, weekTimes);
                    dataList.add(bean);
                    bean = new HourChartBean();
                    initMonthData(DataType.XQDXVALIDCALLNUMBER.getType(), bean, weekTimes);
                    dataList.add(bean);
                    bean = new HourChartBean();
                    initMonthData(DataType.ZQDXVALIDCALLNUMBER.getType(), bean, weekTimes);
                    dataList.add(bean);
                    time = CommonUtil.initYearMonthTime(weekTimes);
                } else {//周/月/全部
                    initValidCallType(dataTypes);
                    for (Integer type : dataTypes) {
                        dayBean = new DayChartBean();
                        getDayDoubleData(dayBean, type, day, preDay, time);
                        dataList.add(dayBean);
                    }
                }
            } else if (dataType == DataType.ZNONGUARDEMPLOYEE.getType().intValue()) {//"职能满编率"
                //查询13,14的数据加在一起return
                dayBean = new DayChartBean();
                getDayDataZN(dayBean, dataType, day, preDay, time);
                dataList.add(dayBean);
            } else if (dataType == DataType.VARIOUS_FAMILIES_ARTICLE.getType().intValue()) {//"百家文章数"
                List<Integer> types = new ArrayList<>();
                if (ChartsConstant.MONTH.equals(chartBean.getTime())) {//"最近一月"
                    types.add(DataType.EVERYDAY_NEW.getType());//208	百家文章日新增
                    //types.add(DataType.EVERYDAY_INCOME.getType());//209	百家文章日收入
                } else {
                    types.add(dataType);
                }
                for (Integer type : types) {
                    dayBean = new DayChartBean();
                    getDayData(dayBean, type, day, preDay, time);
                    dataList.add(dayBean);
                }
            } else {//其他类型(人均在线时长转换为毫秒)
                if (chartBean.getTime().equals("0")) {//今天查询小时表
                    bean = new HourChartBean();
                    getHourData(day, dataType, bean, timeF);
                    dataList.add(bean);
                    if(dataType == DataType.PERCAPITAONLINETIME.getType()){

                        bean = new HourChartBean();
                        getHourData(day, DataType.XQPERCAPITAONLINETIME.getType(), bean, timeF);
                        dataList.add(bean);
                        bean = new HourChartBean();
                        getHourData(day, DataType.ZQPERCAPITAONLINETIME.getType(), bean, timeF);
                        dataList.add(bean);
                    }
                } else if (ChartsConstant.MONTH_DATA.equals(chartBean.getTime())) {//月度数据{月表}
                    List<String> weekTimes = DateUtil.getMonthInfo(8, "yyyyMM");
                    bean = new HourChartBean();
                    initMonthData(dataType, bean, weekTimes);
                    dataList.add(bean);
                    if(dataType == DataType.PERCAPITAONLINETIME.getType()) {
                        bean = new HourChartBean();
                        initMonthData(DataType.XQPERCAPITAONLINETIME.getType(), bean, weekTimes);
                        dataList.add(bean);

                        bean = new HourChartBean();
                        initMonthData(DataType.ZQPERCAPITAONLINETIME.getType(), bean, weekTimes);
                        dataList.add(bean);
                    }
                    time = CommonUtil.initYearMonthTime(weekTimes);
                } else {//周/月/全部
                    dayBean = new DayChartBean();
                    getDayData(dayBean, dataType, day, preDay, time);
                    dataList.add(dayBean);
                    if(dataType == DataType.PERCAPITAONLINETIME.getType()) {
                        dayBean = new DayChartBean();
                        getDayData(dayBean, DataType.XQPERCAPITAONLINETIME.getType(), day, preDay, time);
                        dataList.add(dayBean);
                        dayBean = new DayChartBean();
                        getDayData(dayBean, DataType.ZQPERCAPITAONLINETIME.getType(), day, preDay, time);
                        dataList.add(dayBean);
                    }
                }
            }
        } else {
            logger.error("前端传递的ChartBean.type不是一个数值类型");
        }
        data.put("time", time);
        data.put("dataList", dataList);
    }

    @Override
    public void initTurnoverDay(Map<String, Object> data, Integer dataType) throws Exception {
        List<Object> dataList = new ArrayList<>();
        DayChartBean bean = new DayChartBean();
        List<String> time = getDayData(bean, dataType);
        dataList.add(bean);
        if (dataType == DataType.DXTURNOVERZL.getType().intValue()) {
            bean = new DayChartBean();
            getDayData(bean, DataType.XQDXTURNOVERZL.getType());
            dataList.add(bean);

            bean = new DayChartBean();
            getDayData(bean, DataType.ZQDXTURNOVERZL.getType());
            dataList.add(bean);
        }

        data.put("dataList", dataList);
        data.put("time", time);
    }

    @Override
    public void  initTurnoverMonth(Map<String, Object> data, int flag) throws Exception {
        //初始化电销&渠道流水(当年月度数据实际与预估对比图)
        List<HourChartBean> dataList = new ArrayList<>();
        List<Integer> dataTypes = new ArrayList<Integer>();
        //List<String> weekTimes =  DateUtil.getMonthInfo(12, "yyyyMM");//周度数据时间轴
        List<String> weekTimes = new ArrayList<String>();
        HourChartBean bean = null;
        HourChartBean practical = new HourChartBean();
        if (flag == 1) {
            initMonthData(DataType.DXTURNOVELJ.getType(), practical, weekTimes);//电销当月实际值
        } else if (flag == 2) {
            initMonthData(DataType.QDTURNOVERLJ.getType(), practical, weekTimes);//渠道当月实际值
        }else if (flag == 3) {
          initStartMonthData(DataType.DXQDSJ.getType(), practical, weekTimes,"201801");//电销渠道整体当月实际值
        }else if (flag == 4) {
          initStartMonthData(DataType.XQDXTURNOVERZL.getType(), practical, weekTimes,"201801");//电销新签当月实际值
        }else if (flag == 5) {
          initStartMonthData(DataType.ZQDXTURNOVERZL.getType(), practical, weekTimes,"201801");//电销增值当月实际值
        }
        if (flag == 1) {//电销
            dataTypes.add(DataType.DXTURNOVERYS.getType());//电销当月预算
            dataTypes.add(DataType.DXTURNOVEYG.getType());//电销当月预估值
        } else if (flag == 2) {//渠道
            dataTypes.add(DataType.QDTURNOVERYS.getType());//渠道当月预算
            dataTypes.add(DataType.QDTURNOVERYG.getType());//渠道当月预估值
        }else if (flag == 3) {//电销渠道整体
          dataTypes.add(DataType.DXQDYS.getType());//电销渠道整体当月预算
          dataTypes.add(DataType.DXQDYG.getType());//电销渠道整体当月预估值
        }else if (flag == 4) {//电销新签
          dataTypes.add(DataType.DXXQYS.getType());//电销新签当月预算
          dataTypes.add(DataType.XQDXTURNOVEYG.getType());//电销新签当月预估值
        }else if (flag == 5) {//电销增值
          dataTypes.add(DataType.DXZZYS.getType());//电销增值当月预算
          dataTypes.add(DataType.ZQDXTURNOVEYG.getType());//电销增值当月预估值
        }
        for (Integer type : dataTypes) {
            bean = new HourChartBean();
            getEstimatesByTime(bean, type, weekTimes);
            dataList.add(bean);
        }
        dataList.add(practical);//保证实际值的位置在最后一个
        List<String> time = CommonUtil.initYearMonthTime(weekTimes);
        data.put("dataList", dataList);
        data.put("time", time);
    }

    @Override
    public void initTurnoverMonth_DX(Map<String, Object> data) throws Exception {
        //初始化电销&渠道流水(当年月度数据实际与预估对比图)
        List<HourChartBean> dataList = new ArrayList<>();
        List<Integer> dataTypes = new ArrayList<Integer>();
//        List<String> weekTimes = new ArrayList<>();
        HourChartBean bean = null;
        HourChartBean practical = new HourChartBean();
        //List<String> weekTimes = DateUtil.getMonthInfo(12, "yyyyMM");//周度数据时间轴
        List<String> weekTimes = new ArrayList<>();
        initMonthData(DataType.DXTURNOVELJ.getType(), practical, weekTimes);//电销当月实际值
        List<String> time = CommonUtil.initYearMonthTime(weekTimes);
        dataTypes.add(DataType.DXTURNOVERYS.getType());//电销当月预算
        dataTypes.add(DataType.DXTURNOVEYG.getType());//电销当月预估值
        dataTypes.add(DataType.XQDXTURNOVEYG.getType());//新签预估
        dataTypes.add(DataType.ZQDXTURNOVEYG.getType());//增值预估
        dataTypes.add(DataType.XQDXTURNOVERZL.getType());//新签实际
        dataTypes.add(DataType.ZQDXTURNOVERZL.getType());//增值实际

        int i = 0;
        for (Integer type : dataTypes) {
            bean = new HourChartBean();
            getEstimatesByTime(bean, type, weekTimes);
            if (i == 4) {
                dataList.add(practical);
            }
            dataList.add(bean);
            i++;
        }

        data.put("dataList", dataList);
        data.put("time", time);
    }

    private void initValidCallType(List<Integer> dataTypes) {
//        dataTypes.add(DataType.VALIDCALLNUMBER.getType());//总
        dataTypes.add(DataType.DXVALIDCALLNUMBER.getType());//电销
//        dataTypes.add(DataType.QDVALIDCALLNUMBER.getType());//渠道

        dataTypes.add(DataType.XQDXVALIDCALLNUMBER.getType());//新签电销
        dataTypes.add(DataType.ZQDXVALIDCALLNUMBER.getType());//渠道电销
    }

    /*获取天表数据(月/全部)*/
    private void getDayData(DayChartBean bean, Integer dataType, String day, String preDay, List<String> time) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("day", day);
        param.put("preDay", preDay);
        param.put("type", dataType);
        List<RealTimeStaticDoubleDay> data = null;
        if (DataType.EVERYDAY_INCOME.getType().equals(dataType)) {
            data = realTimeStaticDayMapper.findEveryDayIncome(param);
        } else {
            data = realTimeStaticDayMapper.findDayDoubleData(param);
        }
        List<Double> dataCount = dayConvert(data, dataType, time);
        bean.setUnit(CommonUtil.initUnit(dataType));
        bean.setName(CommonUtil.initName(dataType));
        bean.setData(dataCount);
    }

    private void getDayDoubleData(DayChartBean bean, Integer dataType, String day, String preDay, List<String> time) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("day", day);
        param.put("preDay", preDay);
        param.put("type", dataType);
        List<RealTimeStaticDoubleDay> data = realTimeStaticDayMapper.findDayDoubleData(param);
        List<Double> dataCount = dayDoubleConvert(data, dataType, time);
        bean.setUnit(CommonUtil.initUnit(dataType));
        bean.setName(CommonUtil.initName(dataType));
        bean.setData(dataCount);
    }

    /*转换天表数据*/
    private List<Double> dayDoubleConvert(List<RealTimeStaticDoubleDay> data, Integer dataType, List<String> times) {
        DecimalFormat df = new DecimalFormat("0.00");
        Map<String, Double> initMap = CommonUtil.initDayTimeMap(times);//初始化时间
        List<Double> dataList = new ArrayList<Double>();
        if (data != null && data.size() > 0) {
            for (RealTimeStaticDoubleDay realTimeStaticDay : data) {
                if (!"".equals(realTimeStaticDay.getIrslDate()) && realTimeStaticDay.getDataCount() != null) {
                    initMap.put(realTimeStaticDay.getIrslDate(), Double.parseDouble(df.format(realTimeStaticDay.getDataCount())));
                } else {
                    logger.error("天表:" + "数据时间:" + realTimeStaticDay.getIrslDate() + "数据类型:" + realTimeStaticDay.getDataType() + "-- 数据为空。");
                }
            }
        }
        if (times != null && times.size() > 0) {
            for (String time : times) {
                dataList.add(initMap.get(time));
            }
        }
        return dataList;
    }

    /*转换天表数据*/
    private List<Double> dayConvert(List<RealTimeStaticDoubleDay> data, Integer dataType, List<String> times) {
        Map<String, Double> initMap = CommonUtil.initDayTimeMap(times);//初始化时间
        List<Double> dataList = new ArrayList<Double>();
        if (data != null && data.size() > 0) {
            for (RealTimeStaticDoubleDay realTimeStaticDay : data) {
                if (!"".equals(realTimeStaticDay.getIrslDate()) && realTimeStaticDay.getDataCount() != null) {
                    initMap.put(realTimeStaticDay.getIrslDate(), realTimeStaticDay.getDataCount().doubleValue());
                } else {
                    logger.error("天表:" + "数据时间:" + realTimeStaticDay.getIrslDate() + "数据类型:" + realTimeStaticDay.getDataType() + "-- 数据为空。");
                }
            }
        }
        if (times != null && times.size() > 0) {
            for (String time : times) {
                if (dataType == DataType.PERCAPITAONLINETIME.getType().intValue()
                        || dataType == DataType.XQPERCAPITAONLINETIME.getType().intValue()
                        || dataType == DataType.ZQPERCAPITAONLINETIME.getType().intValue()
                        ) {
                    if (!"".equals(initMap.get(time)) && initMap.get(time) != null) {
                        dataList.add(initMap.get(time) * 1000);
                    } else {
                        dataList.add(initMap.get(time));
                    }
                } else if (DataType.VARIOUS_FAMILIES_ARTICLE.getType().equals(dataType)) {//百家文章数(累计)
                    String day = ControllerDateUtil.getToday();//取今天的日期
                    String yesterday = ControllerDateUtil.getYesterday();//昨天的日期
                    if (time.equals(day) && initMap.get(day) == 0d) {//今天没有数据
                        dataList.add(initMap.get(yesterday));
                    } else {
                        dataList.add(initMap.get(time));
                    }
                } else {
                    dataList.add(initMap.get(time));
                }
            }
        }
        return dataList;
    }

    /**
     * @param bean     vo类
     * @param dataType 数据类型
     * @param day      当天的时间 格式:YYYYMMDD
     * @param time     时间轴
     */
    private void getDayDataZN(DayChartBean bean, Integer dataType, String day, String preDay, List<String> time) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("day", day);
        param.put("preDay", preDay);
        param.put("type", dataType);//13
        List<RealTimeStaticDay> zgData = realTimeStaticDayMapper.findDayData(param);
        param.put("type", DataType.ZNOBUILDEMPLOYEE.getType());//14
        List<RealTimeStaticDay> bzData = realTimeStaticDayMapper.findDayData(param);
        List<Double> dataCount = dayConvertZN(zgData, bzData, time);
        bean.setUnit(CommonUtil.initUnit(dataType));
        bean.setName(CommonUtil.initName(dataType));
        bean.setData(dataCount);
    }

    /*转换天表数据:职能满编率计算*/
    private List<Double> dayConvertZN(List<RealTimeStaticDay> zgData, List<RealTimeStaticDay> bzData, List<String> times) {
        DecimalFormat df = new DecimalFormat("0.00");
        Map<String, Double> initMap = CommonUtil.initDayTimeMap(times);//初始化时间
        Map<String, Double> initMap2 = CommonUtil.initDayTimeMap(times);//初始化时间
        List<Double> dataList = new ArrayList<Double>();
        if (zgData != null && zgData.size() > 0) {
            for (RealTimeStaticDay realTimeStaticDay : zgData) {
                if (!"".equals(realTimeStaticDay.getIrslDate()) && realTimeStaticDay.getDataCount() != null) {
                    initMap.put(realTimeStaticDay.getIrslDate(), (realTimeStaticDay.getDataCount().doubleValue()));
                } else {
                    logger.error("查询天表数据:数据时间:" + realTimeStaticDay.getIrslDate() + "数据类型:" + realTimeStaticDay.getDataType() + "-- 数据为空。");
                }
            }
        }
        if (bzData != null && bzData.size() > 0) {
            for (RealTimeStaticDay realTimeStaticDay : bzData) {
                if (!"".equals(realTimeStaticDay.getIrslDate()) && realTimeStaticDay.getDataCount() != null) {
                    initMap2.put(realTimeStaticDay.getIrslDate(), (realTimeStaticDay.getDataCount().doubleValue()));
                } else {
                    logger.error("天表:" + "数据时间:" + realTimeStaticDay.getIrslDate() + "数据类型:" + realTimeStaticDay.getDataType() + "-- 数据为空。");
                }
            }
        }
        if (times != null && times.size() > 0) {
            for (String time : times) {
                if (initMap.get(time) != null && initMap2.get(time) != null && initMap2.get(time) != 0.0) {
                    dataList.add(Double.valueOf(df.format((initMap.get(time) * 100) / initMap2.get(time))));
                } else {
                    dataList.add(0.00);
                }
            }
        }
        return dataList;
    }

    /**
     * 拼接vo对象
     *
     * @param times    时间轴
     * @param dataType 数据类型
     * @param bean     vo类
     * @throws Exception
     */
    private void getWeekData(List<String> times, Integer dataType, HourChartBean bean) throws Exception {
        int flag = 30;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("flag", flag);
        /*param.put("type", DataType.EXTERNALSENDDRAFT.getType());取消外部发稿数
        List<RealtimeStaticWeek> weekDatas = realtimeStaticWeekMapper.findWeekData30Day(param);*/
        param.put("type", DataType.EXTERNALLINK.getType());
        List<RealtimeStaticWeek> weekDatas2 = realtimeStaticWeekMapper.findWeekData30Day(param);
        param.put("type", DataType.WEMEDIALINK.getType());
        List<RealtimeStaticWeek> weekDatas3 = realtimeStaticWeekMapper.findWeekData30Day(param);
        bean.setData(weekConvert(weekDatas2, weekDatas3, times));
        bean.setUnit(CommonUtil.initUnit(dataType));
        bean.setName(CommonUtil.initName(dataType));
    }

    /**
     * 转换周表数据
     *
     * @param weekDatas2 电销
     * @param weekDatas3 渠道
     * @param times      时间轴
     * @return
     */
    private List<Object> weekConvert(List<RealtimeStaticWeek> weekDatas2, List<RealtimeStaticWeek> weekDatas3, List<String> times) {
        Map<String, Long> initMap2 = new HashMap<String, Long>();
        Map<String, Long> initMap3 = new HashMap<String, Long>();
        List<Object> dataList = new ArrayList<Object>();
        Long dataCount;
        if (weekDatas2 != null && weekDatas2.size() > 0) {
            for (RealtimeStaticWeek realtimeStaticWeek : weekDatas2) {
                if (!"".equals(realtimeStaticWeek.getIrslDate()) && realtimeStaticWeek.getDataCount() != null) {
                    initMap2.put(realtimeStaticWeek.getIrslDate(), realtimeStaticWeek.getDataCount());
                    times.add(realtimeStaticWeek.getIrslDate());
                } else {
                    logger.error("周表:数据时间" + realtimeStaticWeek.getDataDate() + "data_type值:" + realtimeStaticWeek.getDataType() + "-- 数据为空。");
                }
            }
        }
        if (weekDatas3 != null && weekDatas3.size() > 0) {
            for (RealtimeStaticWeek realtimeStaticWeek : weekDatas3) {
                if (!"".equals(realtimeStaticWeek.getIrslDate()) && realtimeStaticWeek.getDataCount() != null) {
                    initMap3.put(realtimeStaticWeek.getIrslDate(), realtimeStaticWeek.getDataCount());
                } else {
                    logger.error("周表:数据时间" + realtimeStaticWeek.getDataDate() + "data_type值:" + realtimeStaticWeek.getDataType() + "-- 数据为空。");
                }
            }
        }
        if (times != null && times.size() > 0) {
            for (String time : times) {
                dataCount = 0l;
                if (initMap2.get(time) != null) {
                    dataCount += initMap2.get(time);
                }
                if (initMap3.get(time) != null) {
                    dataCount += initMap3.get(time);
                }
                dataList.add(dataCount);
            }
        }
        return dataList;
    }

    /**
     * 拼接vo对象
     *
     * @param day
     * @param dataType
     * @param bean
     * @param timeF
     */
    private void getHourData(String day, Integer dataType, HourChartBean bean, List<String> timeF) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("day", day);
        param.put("type", dataType);
        List<RealTimeStaticHour> hourData = realTimeStaticHourMapper.findTodayData(param);
        List<Object> dataCount = hourConvert(hourData, dataType, timeF);
        bean.setUnit(CommonUtil.initUnit(dataType));
        bean.setName(CommonUtil.initName(dataType));
        bean.setData(dataCount);
    }

    private void getHourDoubleData(String day, Integer dataType, HourChartBean bean, List<String> timeF) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("day", day);
        param.put("type", dataType);
        List<RealTimeStaticDoubleHour> hourData = realTimeStaticHourMapper.findDoubleTodayData(param);
        List<Object> dataCount = hourDoubleConvert(hourData, dataType, timeF);
        bean.setUnit(CommonUtil.initUnit(dataType));
        bean.setName(CommonUtil.initName(dataType));
        bean.setData(dataCount);
    }

    private List<Object> hourDoubleConvert(List<RealTimeStaticDoubleHour> hourDatas, Integer dataType, List<String> times) {
        DecimalFormat df = new DecimalFormat("0.00");
        Map<String, Object> initMap = CommonUtil.initHourTimeMap(times);//初始化时间
        Map<String, Object> tempMap = new HashMap<>();//用于数据补零,小时数据当前最新数据之前的补零,之后的维持原有""处理
        List<Object> dataList = new ArrayList<Object>();
        if (hourDatas != null && hourDatas.size() > 0) {
            for (RealTimeStaticDoubleHour realTimeStaticHour : hourDatas) {
                if (!"".equals(realTimeStaticHour.getIrslDateH()) && realTimeStaticHour.getDataCount() != null) {
                    initMap.put(realTimeStaticHour.getIrslDateH(), Double.parseDouble(df.format(realTimeStaticHour.getDataCount())));
                    tempMap.put("temp", realTimeStaticHour.getIrslDateH());
                } else {
                    logger.error("小时表:" + "数据时间:" + realTimeStaticHour.getIrslDateH() + "数据类型:" + realTimeStaticHour.getDataType() + "-- 数据为空。");
                }
            }
            if (times != null && times.size() > 0) {
                for (String time : times) {
                    if (Integer.valueOf(time) <= Integer.valueOf((String) tempMap.get("temp"))) {//有数据的最大时间之前数据为空进行补零
                        if ("".equals(initMap.get(time))) {
                            dataList.add(0);
                        } else {
                            dataList.add(initMap.get(time));
                        }
                    } else {
                        dataList.add(initMap.get(time));
                    }
                }
            }
        } else {
            logger.error("FightCapacityDataServiceImpl.hourConvert获取小时表数据为空");
        }
        return dataList;
    }

    /**
     * 转换小时表数据
     *
     * @param hourDatas
     * @param times
     * @return
     */
    private List<Object> hourConvert(List<RealTimeStaticHour> hourDatas, Integer dataType, List<String> times) {
        Map<String, Object> initMap = CommonUtil.initHourTimeMap(times);//初始化时间
        List<Object> dataList = new ArrayList<Object>();
        Map<String, Object> tempMap = new HashMap<>();//用于数据补零,小时数据当前最新数据之前的补零,之后的维持原有""处理
        if (hourDatas != null && hourDatas.size() > 0) {
            for (RealTimeStaticHour realTimeStaticHour : hourDatas) {
                if (!"".equals(realTimeStaticHour.getIrslDateH()) && realTimeStaticHour.getDataCount() != null) {
                    initMap.put(realTimeStaticHour.getIrslDateH(), realTimeStaticHour.getDataCount());
                    tempMap.put("temp", realTimeStaticHour.getIrslDateH());
                } else {
                    logger.error("小时表:" + "数据时间:" + realTimeStaticHour.getIrslDateH() + "数据类型:" + realTimeStaticHour.getDataType() + "-- 数据为空。");
                }
            }
            if (times != null && times.size() > 0) {
                Integer data = null;
                for (String time : times) {
                    if (Integer.valueOf(time) <= Integer.valueOf((String) tempMap.get("temp"))) {//有数据的最大时间之前数据为空进行补零
                        if ("".equals(initMap.get(time))) {
                            dataList.add(0);
                        } else {
                            if (dataType == DataType.PERCAPITAONLINETIME.getType().intValue()
                                    || dataType == DataType.XQPERCAPITAONLINETIME.getType().intValue()
                                    ||  dataType ==DataType.ZQPERCAPITAONLINETIME.getType().intValue()) {
                                if (!"".equals(initMap.get(time)) && initMap.get(time) != null) {
                                    data = ((Integer) initMap.get(time)) * 1000;
                                    dataList.add(data);
                                } else {
                                    dataList.add(initMap.get(time));
                                }
                            } else {
                                dataList.add(initMap.get(time));
                            }
                        }
                    } else {
                        dataList.add(initMap.get(time));
                    }
                }
            }
        } else {
            logger.error("FightCapacityDataServiceImpl.hourConvert获取小时表数据为空");
        }
        return dataList;
    }

    private MainBean getTodayDataWeek(Integer flag) throws Exception {
        List<Integer> sendDraftTypes = new ArrayList<Integer>();
        /*外部发稿数*/
        sendDraftTypes.add(DataType.EXTERNALSENDDRAFT.getType());
         /*外部链接数*/
        sendDraftTypes.add(DataType.EXTERNALLINK.getType());
        /*自媒体发稿链接*/
        sendDraftTypes.add(DataType.WEMEDIALINK.getType());
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("list", sendDraftTypes);
        param.put("flag", flag);
        List<RealtimeStaticWeek> dataToday = realtimeStaticWeekMapper.findWeekData(param);
        MainBean bean = new MainBean();
        if (dataToday != null && dataToday.size() > 0) {
            convertTopWeek(bean, DataType.EXTERNALSENDDRAFT.getType(), dataToday);//转换今天的数据
        } else {
            logger.error("获取周表rownumber=" + flag + "的" + sendDraftTypes.toString() + "类型的数据为空");
        }
        return bean;
    }

    private void convertTopWeek(MainBean mainBean, Integer dataType, List<RealtimeStaticWeek> dataToday) {
        if (dataToday != null && dataToday.size() > 0) {
            Integer count = 0;
            for (RealtimeStaticWeek realtimeStaticWeek : dataToday) {
                if (realtimeStaticWeek != null && realtimeStaticWeek.getDataCount() != null) {
                    count = count + realtimeStaticWeek.getDataCount().intValue();
                } else {
                    logger.error("周表:" + "数据时间:" + realtimeStaticWeek.getDataDate() + "数据类型:" + realtimeStaticWeek.getDataType() + "-- 数据为空。");
                }
            }
            mainBean.setName(CommonUtil.initName(dataType));
            mainBean.setNum(count + "");
        }
    }

    private void initLastDataTypes(List<Integer> dataTypes) {
        /*人均在线时长*/
        dataTypes.add(DataType.PERCAPITAONLINETIME.getType());
        /*渠道拜访量*/
        dataTypes.add(DataType.CHANNELVISITCOUNT.getType());
    }

    private void initTodayDataTypes(List<Integer> dataTypes) {
        /*  职能人员在岗人数*/
        dataTypes.add(DataType.ZNONGUARDEMPLOYEE.getType());
        /*职能人员编制人数*/
        dataTypes.add(DataType.ZNOBUILDEMPLOYEE.getType());
        /*电销转正人数**/
        dataTypes.add(DataType.DXCOVENEMPLOYEE.getType());
        /*渠道转正人数**/
        dataTypes.add(DataType.QDCOVENEMPLOYEE.getType());
        /*百家文章数 */
        dataTypes.add(DataType.VARIOUS_FAMILIES_ARTICLE.getType());
        /*渠道拜访量*/
        dataTypes.add(DataType.CHANNELVISITCOUNT.getType());
        /*人均在线时长*/
        dataTypes.add(DataType.PERCAPITAONLINETIME.getType());
    }

    private void initTogetherDataTypes(List<Integer> dataTypes) {
        /*  职能人员在岗人数*/
        dataTypes.add(DataType.ZNONGUARDEMPLOYEE.getType());
        /*职能人员编制人数*/
        dataTypes.add(DataType.ZNOBUILDEMPLOYEE.getType());
        /*电销转正人数**/
        dataTypes.add(DataType.DXCOVENEMPLOYEE.getType());
        /*渠道转正人数**/
        dataTypes.add(DataType.QDCOVENEMPLOYEE.getType());
        /*百家文章数 */
        dataTypes.add(DataType.VARIOUS_FAMILIES_ARTICLE.getType());
    }

    private List<MainBean> getYesterdayData(String day, String yesterDay, List<Integer> togetherTypes, List<Integer> lastTypes) throws Exception {
        Map<String, Object> param2 = new HashMap<String, Object>();
        param2.put("yesterDay", yesterDay);
        param2.put("list", lastTypes);
        List<RealTimeStaticDay> yesterDayLastData = realTimeStaticDayMapper.findRealTimeLastDataYester(param2);//查询昨天最后一条
        param2.put("day", day);
        param2.put("list", togetherTypes);
        List<RealTimeStaticDay> yesterDayData = realTimeStaticDayMapper.findRealTimeDataYester(param2);//查询昨天同一时间
        List<MainBean> mainBeans2 = new ArrayList<MainBean>();
        boolean flag;
        if (yesterDayData != null && yesterDayData.size() > 0) {
            flag = false;
            convertTop(mainBeans2, yesterDayData, yesterDay, flag);
        } else {
            logger.error("天表:数据时间:" + yesterDay + "----获取" + togetherTypes.toString() + "类型的数据为空");
        }
        if (yesterDayLastData != null && yesterDayLastData.size() > 0) {
            flag = true;
            convertTop(mainBeans2, yesterDayLastData, yesterDay, flag);
        } else {
            logger.error("天表:数据时间:" + yesterDay + "----获取" + yesterDayLastData.toString() + "类型的数据为空");
        }
        return mainBeans2;
    }

    private List<MainBean> getTodayData(String day, List dataTypes) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("day", day);
        param.put("list", dataTypes);
        List<RealTimeStaticDay> dataToday = null;
        if (param != null && param.size() > 0) {
            dataToday = realTimeStaticDayMapper.findRealTimeDataToday(param);
        }
        List<MainBean> mainBeans = new ArrayList<MainBean>();
        if (dataToday != null && dataToday.size() > 0) {
            boolean flag = true;
            convertTop(mainBeans, dataToday, day, flag);//转换今天的数据
        }
        return mainBeans;
    }


    private void convertTop(List<MainBean> mainBeans, List<RealTimeStaticDay> dataToday, String day, boolean flag) {
        DecimalFormat df = new DecimalFormat("0.00");
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (dataToday != null && dataToday.size() > 0) {
            for (RealTimeStaticDay realTimeStaticDay : dataToday) {
                if (realTimeStaticDay.getDataCount() != null) {
                    if (realTimeStaticDay.getDataType().intValue() == DataType.ZNONGUARDEMPLOYEE.getType().intValue()) {
                        map.put(DataType.getName(realTimeStaticDay.getDataType()), realTimeStaticDay.getDataCount());
                    } else if (realTimeStaticDay.getDataType().intValue() == DataType.ZNOBUILDEMPLOYEE.getType().intValue()) {
                        map.put(DataType.getName(realTimeStaticDay.getDataType()), realTimeStaticDay.getDataCount());
                    } else {
                        if (realTimeStaticDay.getDataType().intValue() == DataType.PERCAPITAONLINETIME.getType().intValue()) {//人均在线时长*1000 单位:毫秒
                            mainBeans.add(new MainBean(CommonUtil.initName(realTimeStaticDay.getDataType()), (realTimeStaticDay.getDataCount().longValue() * 1000) + ""));
                        } else {
                            mainBeans.add(new MainBean(CommonUtil.initName(realTimeStaticDay.getDataType()), realTimeStaticDay.getDataCount() + ""));
                        }
                    }
                } else {
                    mainBeans.add(new MainBean(DataType.getName(realTimeStaticDay.getDataType()), "0"));
                    logger.error("查询天表数据:数据时间:" + realTimeStaticDay.getIrslDate() + "数据类型:" + realTimeStaticDay.getDataType() + "-- 数据为空。");
                }
            }
            if (flag) {//判断是否需要转换职能满编率的数据
                if (map.get(DataType.ZNONGUARDEMPLOYEE.getName()) != null && map.get(DataType.ZNOBUILDEMPLOYEE.getName()) != null && map.get(DataType.ZNOBUILDEMPLOYEE.getName()) != 0) {//职能人员在岗人数,职能人员编制人数
                    mainBeans.add(new MainBean("职能满编率", df.format(map.get(DataType.ZNONGUARDEMPLOYEE.getName()).doubleValue() * 100 / map.get(DataType.ZNOBUILDEMPLOYEE.getName())) + "%"));
                } else {
                    mainBeans.add(new MainBean("职能满编率", "0.00%"));
                    EmailUtil.warnEveryOne(day + "-" + "职能满编率--数据为空。");
                }
            }

        }
    }

    public MainBean getValidcallnum(String day, String yesterDay) throws Exception {
        //Integer dataType = DataType.VALIDCALLNUMBER.getType();//全部销售
        Integer dataType = DataType.DXVALIDCALLNUMBER.getType();//电销销售---后面改为全部销售
        List<Integer> types = new ArrayList<>();
        types.add(dataType);
        List<RealTimeStaticDoubleDay> dataToday = null;
        Map<String, Object> param = new HashMap<>();
        param.put("day", day);
        param.put("list", types);
        if (yesterDay == null) {//今天
            dataToday = realTimeStaticDayMapper.findRealTimeDoubleDataToday(param);
        } else {
            param.put("yesterDay", yesterDay);
            dataToday = realTimeStaticDayMapper.findRealTimeDoubleDataYester(param);
        }
        MainBean mainBean = new MainBean();
        mainBean.setName("人均有效通话次数");//--全部销售(色块部分显示)
        if (dataToday != null && dataToday.size() > 0) {
            String num = convertCallTop(dataToday, day);//转换今天的数据
            mainBean.setNum(num);
        } else {
            logger.error("天表:数据时间:" + day + "数据类型:" + dataType.toString() + "---数据为空");
        }
        return mainBean;
    }

    private String convertCallTop(List<RealTimeStaticDoubleDay> dataToday, String day) {
        DecimalFormat df = new DecimalFormat("0.00");
        String num = "0";
        for (RealTimeStaticDoubleDay data : dataToday) {
            if (data.getDataCount() != null) {
                num = df.format(data.getDataCount());
            } else {
                logger.error("天表:数据时间:" + data.getIrslDate() + "数据类型:" + data.getDataType() + "-- 数据为空。");
            }
        }
        return num;
    }


    private void initEstimates(HourChartBean bean, List<String> times, Integer type) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("type", type);
        param.put("list", times);
        Map<String, Object> initMap = new HashMap<>();
        for (String time : times) {//初始化数据，防止有不对的数据
            initMap.put(time, 0);
        }
        //List<RealtimeStaticDoubleWeek> estimates = realtimeStaticWeekMapper.findEstimatesByPractical(param);
        List<RealtimeStaticDoubleWeek> estimates = realtimeStaticWeekMapper.findEstimatesByPracticalnew(param);
        List<Object> count = new ArrayList<>();
        if (estimates != null && !estimates.isEmpty()) {
            for (RealtimeStaticDoubleWeek weekData : estimates) {
                if (weekData.getDataCount() != null) {
//                    count.add(Math.floor(weekData.getDataCount()));
                    initMap.put(weekData.getIrslDate(), Math.floor(weekData.getDataCount()));
                } else {
                    logger.error("周表:数据时间:" + weekData.getIrslDate() + "数据类型:" + weekData.getDataType() + "-- 数据为空。");
                }
            }
        }
        for (String time : times) {
            count.add(initMap.get(time));
        }
        bean.setData(count);
        bean.setName(CommonUtil.initName(type));
        bean.setUnit(CommonUtil.initUnit(type));
    }

    /*初始化销售实际值数据*/
    private List<String> initPractical(HourChartBean bean, Integer type) throws Exception {
        List<String> times = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        param.put("type", type);
        param.put("flag", 6);
        List<RealtimeStaticDoubleWeek> Practical = realtimeStaticWeekMapper.findWeekDataDoubleDay(param);
        if (Practical != null && !Practical.isEmpty()) {
            List<Object> count = new ArrayList<>();
            for (RealtimeStaticDoubleWeek weekData : Practical) {
                if (weekData.getDataCount() != null) {
                    count.add(Math.floor(weekData.getDataCount()));
                    times.add(weekData.getIrslDate());
                } else {
                    logger.error("周表:数据时间:" + weekData.getIrslDate() + "数据类型:" + weekData.getDataType() + "-- 数据为空。");
                }
            }
            bean.setData(count);
        }
        bean.setName(CommonUtil.initName(type));
        bean.setUnit(CommonUtil.initUnit(type));
        return times;
    }


    /**
     * 根据实际值的时间获取预估预算值得数据
     *
     * @param bean
     * @param type
     * @param times
     */
    private void getEstimatesByTime(HourChartBean bean, Integer type, List<String> times) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("type", type);
        param.put("list", times);
        Map<String, Object> initMap = new HashMap<>();
        for (String t : times) {
            initMap.put(t, 0);
        }
        //List<RealtimeStaticMonth> estimates = realtimeStaticMonthMapper.findEstimatesByPractical(param);
        List<RealtimeStaticMonth> estimates = realtimeStaticMonthMapper.findEstimatesByPracticalnew(param);
        List<Object> count = new ArrayList<>();
        if (estimates != null && !estimates.isEmpty()) {
            for (RealtimeStaticMonth monthData : estimates) {
                if (monthData.getDataCount() != null) {
//                    count.add(monthData.getDataCount());
                    initMap.put(monthData.getIrslDate(), monthData.getDataCount());
                } else {
                    logger.error("月表:数据时间:" + monthData.getIrslDate() + "数据类型:" + monthData.getDataType() + "-- 数据为空。");
                }
            }
        }
        for (String t : times) {
            count.add(initMap.get(t));
        }
        bean.setData(count);
        bean.setName(CommonUtil.initName(type));
        bean.setUnit(CommonUtil.initUnit(type));

    }

    /*月度数据获取*/
    private void initMonthData(Integer dataType, HourChartBean bean, List<String> weekTimes) throws Exception {
//        String year = DateUtil.getYear("yyyy");//获取当前年度
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("type", dataType);
        param.put("months", 12); //月份的个数
        List<RealtimeStaticMonth> monthData = realtimeStaticMonthMapper.fingYearMonthData(param);
        if (weekTimes == null || weekTimes.size() == 0) {
            for (RealtimeStaticMonth realtimeStaticMonth : monthData) {
                weekTimes.add(realtimeStaticMonth.getIrslDate());
            }


        }
        List<Object> dataCount = monthConvert(monthData, dataType, weekTimes);
        bean.setName(CommonUtil.initName(dataType));
        bean.setUnit(CommonUtil.initUnit(dataType));
        bean.setData(dataCount);
    }
    /*月度数据获取 有开始月份*/
    private void initStartMonthData(Integer dataType, HourChartBean bean, List<String> weekTimes,String startmonth) throws Exception {
      Map<String, Object> param = new HashMap<String, Object>();
      param.put("type", dataType);
      param.put("months", 12); //月份的个数
      param.put("startmonth", startmonth); //月份的个数
      List<RealtimeStaticMonth> monthData = realtimeStaticMonthMapper.fingYearStartMonthData(param);
      if (weekTimes == null || weekTimes.size() == 0) {
        for (RealtimeStaticMonth realtimeStaticMonth : monthData) {
          weekTimes.add(realtimeStaticMonth.getIrslDate());
        }


      }
      List<Object> dataCount = monthConvert(monthData, dataType, weekTimes);
      bean.setName(CommonUtil.initName(dataType));
      bean.setUnit(CommonUtil.initUnit(dataType));
      bean.setData(dataCount);
    }

    private List<Object> monthConvert(List<RealtimeStaticMonth> monthDatas, Integer dataType, List<String> times) {
        DecimalFormat df = new DecimalFormat("0.00");
        List<Object> dataList = new ArrayList<>();
        Map<String, Double> initMap = new HashMap<String, Double>();
        if (times != null && times.size() > 0) {
            for (String t : times) {
                initMap.put(t, 0d);//初始化数据
            }
        }
        if (monthDatas != null && !monthDatas.isEmpty()) {
            for (RealtimeStaticMonth monthData : monthDatas) {
                if (!"".equals(monthData.getIrslDate()) && monthData.getDataCount() != null) {//有效通话次数保留两位小数
                    initMap.put(monthData.getIrslDate(), Double.valueOf(df.format(monthData.getDataCount())));
                } else {
                    logger.error("查询月表数据:数据时间:" + monthData.getDataDate() + "数据类型:" + monthData.getDataType() + "-- 数据为空。");
                }
            }
        }
        if (times != null && times.size() > 0) {
            for (String time : times) {
                if (dataType == DataType.PERCAPITAONLINETIME.getType().intValue()
                      || dataType == DataType.XQPERCAPITAONLINETIME.getType().intValue()
                        || dataType == DataType.ZQPERCAPITAONLINETIME.getType().intValue()
                        ) {
                    if (!"".equals(initMap.get(time)) && initMap.get(time) != null) {
                        dataList.add(initMap.get(time) * 1000);
                    } else {
                        dataList.add(initMap.get(time));
                    }
                } else {
                    dataList.add(initMap.get(time));
                }
            }
        }
        return dataList;
    }

    /*天表数据{时间区段}获取*/
    private List<String> getDayData(DayChartBean bean, Integer dataType) throws Exception {
        String day = ControllerDateUtil.getToday();//取今天的日期
        List<String> time = CommonUtil.getTimeShaftD(ChartsConstant.SHOWMONTH);//时间刻度
        String preDay = ControllerDateUtil.getPreNDay(-(ChartsConstant.SHOWMONTH - 1));
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("day", day);
        param.put("preDay", preDay);
        param.put("type", dataType);
        List<RealTimeStaticDoubleDay> data = realTimeStaticDayMapper.findDayDoubleData(param);
        List<Double> dataCount = dayConvert(data, dataType, time);
        bean.setUnit(CommonUtil.initUnit(dataType));
        bean.setName(CommonUtil.initName(dataType));
        bean.setData(dataCount);
        return time;
    }
}
