package com.hc360.dataweb.service.impl;

import com.hc360.dataweb.dao.RealTimeStatic3DataMapper;
import com.hc360.dataweb.dao.RealTimeStatic4DataMapper;
import com.hc360.dataweb.dao.RealTimeStaticDayMapper;
import com.hc360.dataweb.dao.RealTimeStaticHourMapper;
import com.hc360.dataweb.model.*;
import com.hc360.dataweb.service.OperateService;
import com.hc360.dataweb.util.ControllerDateUtil;
import com.hc360.dataweb.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by home on 2018/11/21.
 */
@Service
public class OperateServiceImpl implements OperateService {

    private Logger logger = Logger.getLogger(OperateServiceImpl.class);
    @Autowired
    private RealTimeStaticHourMapper realTimeStaticHourMapper;
    @Autowired
    private RealTimeStatic3DataMapper realTimeStatic3DataMapper;
    @Autowired
    private RealTimeStaticDayMapper realTimeStaticDayMapper;
    @Autowired
    private RealTimeStatic4DataMapper realTimeStatic4DataMapper;

    public Map<String, Object> match(Integer type) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("dataType", type);
        String[] categories = null;
        Object[] data = null;
        Object[] data2 = null;
        List<BarBean> barBeanList = new ArrayList<>();
        List<RealTimeStatic4Data> resultList = this.realTimeStatic4DataMapper.findByType(paramMap);
        if (resultList != null && resultList.size() > 0) {
            categories = new String[resultList.size()];
            data = new Object[resultList.size()];
            data2 = new Object[resultList.size()];
            int i = 0;
            for (RealTimeStatic4Data realTimeStatic4Data : resultList) {
                categories[i] = realTimeStatic4Data.getElement();
                data[i] = realTimeStatic4Data.getDataCount1();
                data2[i] = realTimeStatic4Data.getDataCount2();
                i++;
            }
            if(type == DataType.YOUKEKEYAMATCHB.getType().intValue()){
                BarBean barBean=  new BarBean("订阅词",data);
                barBeanList.add(barBean);
                barBean=  new BarBean("线索词",data2);
                barBeanList.add(barBean);
            }else if(type == DataType.YOUKEKEYBMATCHA.getType().intValue()){
                BarBean barBean=  new BarBean("线索词",data);
                barBeanList.add(barBean);
                barBean=  new BarBean("订阅词",data2);
                barBeanList.add(barBean);
            }
            resultMap.put("time",categories);
            resultMap.put("dataList",barBeanList);
        }

        return resultMap;
    }

    public Map<String, Object> formula(List<Integer> typeList) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("list", typeList);
        DecimalFormat threeNumDf = new DecimalFormat(",###.00");//每三位分隔一下
        List<RealTimeStaticDoubleHour> resultList = realTimeStaticHourMapper.findDoubleByType(paramMap);
        if (resultList != null && resultList.size() > 0) {
            List<FormulaBean> list = new ArrayList<>();
            FormulaBean mainBean = null;
            for (RealTimeStaticDoubleHour hour : resultList) {

                if (hour.getDataType().intValue() == DataType.P4PPRICE.getType()) {
                    mainBean = new FormulaBean("客单价", threeNumDf.format(hour.getDataCount()));
                    list.add(mainBean);
                } else if (hour.getDataType().intValue() == DataType.P4PUSER.getType()) {
                    mainBean = new FormulaBean("会员数", threeNumDf.format(hour.getDataCount()));
                    list.add(mainBean);
                } else if (hour.getDataType().intValue() == DataType.P4PXIANJINCHARGETOTAL.getType()) {
                    mainBean = new FormulaBean("销售额", threeNumDf.format(hour.getDataCount()));
                    list.add(mainBean);
                } else if (hour.getDataType().intValue() == DataType.P4PALLEXPENDTOTAL.getType()) {
                    mainBean = new FormulaBean("消耗", threeNumDf.format(hour.getDataCount()));
                    list.add(mainBean);
                } else if (hour.getDataType().intValue() == DataType.P4PALLBALANCE.getType()) {
                    mainBean = new FormulaBean("余额", threeNumDf.format(hour.getDataCount()));
                    list.add(mainBean);
                } else if (hour.getDataType().intValue() == DataType.P4PALLCHARGETOTAL.getType()) {
                    mainBean = new FormulaBean("充值", threeNumDf.format(hour.getDataCount()));
                    list.add(mainBean);
                } else {
                    mainBean = new FormulaBean(DataType.getName(hour.getDataType()), threeNumDf.format(hour.getDataCount()));
                    list.add(mainBean);
                }
            }
            resultMap.put("formula", list);
        }

        return resultMap;
    }


    public Map<String, Object> priceDist(int type, String day) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("dataType", type);
        paramMap.put("day", day);
        Map<String, Object> resultMap = new HashMap<>();
        DecimalFormat threeNumDf = new DecimalFormat("0.00");//每三位分隔一下
        List<RealTimeStatic3Data> resultList = realTimeStatic3DataMapper.findByType(paramMap);
        List<CircleBean> list = new ArrayList<>();
        if (resultList != null && resultList.size() > 0) {
            CircleBean circleBean = null;
            for (RealTimeStatic3Data _realTimeStatic3Data : resultList) {
                circleBean = new CircleBean(_realTimeStatic3Data.getElement(), Double.parseDouble(threeNumDf.format(_realTimeStatic3Data.getDataCount() * 100)), _realTimeStatic3Data.getDataCount());
                list.add(circleBean);
            }
        }
        resultMap.put("data", list);

        return resultMap;
    }

    public void twoCircleUsers(List<Integer> typeList, String day, Map<String, Object> resultMap) throws Exception {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("list", typeList);
        List<RealTimeStaticDoubleHour> resultList = realTimeStaticHourMapper.findDoubleByType(paramMap);
        List<TwoCircleBean> twoCircleBeans = new ArrayList<>();
        TwoCircleBean twoCircleBean = null;
        DrillDownBean drillDownBean = null;
        if (resultList != null && resultList.size() > 0) {
            Map<Integer, Double> selectResult = new HashMap<>();
            for (RealTimeStaticDoubleHour hour : resultList) {
                selectResult.put(hour.getDataType(), hour.getDataCount());
            }
            if (selectResult != null && selectResult.size() >= 4) {
                Double all = selectResult.get(DataType.P4PBALANCEUSER.getType()) + selectResult.get(DataType.P4PNOBALANCEUSERS.getType());
                drillDownBean = new DrillDownBean("有余额", new String[]{"开启关键词", "未开启关键词"}, new Object[]{
                        formartData(selectResult.get(DataType.P4PBALANCEKEYUSERS.getType()), all),
                        formartData(selectResult.get(DataType.P4PBALANCENOKEYUSERS.getType()), all)
                });
                twoCircleBean = new TwoCircleBean(formartData(selectResult.get(DataType.P4PBALANCEUSER.getType()), all), 1, drillDownBean);
                twoCircleBeans.add(twoCircleBean);
                drillDownBean = new DrillDownBean("无余额", new String[]{"无余额"}, new Object[]{
                        formartData(selectResult.get(DataType.P4PNOBALANCEUSERS.getType()), all)
                });
                twoCircleBean = new TwoCircleBean(formartData(selectResult.get(DataType.P4PNOBALANCEUSERS.getType()), all), 2, drillDownBean);
                twoCircleBeans.add(twoCircleBean);
            }
        }
        resultMap.put("circleData", twoCircleBeans);
    }

    public Map<String, Object> twoCircle(List<Integer> typeList, String day) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("list", typeList);
        List<RealTimeStaticDoubleHour> resultList = realTimeStaticHourMapper.findDoubleByType(paramMap);
        List<TwoCircleBean> twoCircleBeans = new ArrayList<>();
        TwoCircleBean twoCircleBean = null;
        DrillDownBean drillDownBean = null;
        if (resultList != null && resultList.size() > 0) {
            Map<Integer, Double> selectResult = new HashMap<>();
            for (RealTimeStaticDoubleHour hour : resultList) {
                selectResult.put(hour.getDataType(), hour.getDataCount());
            }
            if (selectResult != null && selectResult.size() >= 8) {
                Double all = selectResult.get(DataType.P4PALLEXPENDTOTAL.getType()) + selectResult.get(DataType.P4PALLBALANCE.getType());
                drillDownBean = new DrillDownBean("消耗", new String[]{"现金", "返点金", "虚拟"},
                        new Object[]{formartData(selectResult.get(DataType.P4PXIANJINEXPENDTOTAL.getType()), all),
                                formartData(selectResult.get(DataType.P4PFANDIANJINEXPENDTOTAL.getType()), all),
                                formartData(selectResult.get(DataType.P4PXUNIEXPENDTOTAL.getType()), all)});
                twoCircleBean = new TwoCircleBean(formartData(selectResult.get(DataType.P4PALLEXPENDTOTAL.getType()), all), 1, drillDownBean);
                twoCircleBeans.add(twoCircleBean);
                drillDownBean = new DrillDownBean("余额", new String[]{"现金", "返点金", "虚拟"},
                        new Object[]{formartData(selectResult.get(DataType.P4PXIANJINBALANCE.getType()), all),
                                formartData(selectResult.get(DataType.P4PFANDIANJINBALANCE.getType()), all),
                                formartData(selectResult.get(DataType.P4PXUNIBALANCE.getType()), all)});
                twoCircleBean = new TwoCircleBean(formartData(selectResult.get(DataType.P4PALLBALANCE.getType()), all), 2, drillDownBean);
                twoCircleBeans.add(twoCircleBean);
            }
        }
        resultMap.put("data", twoCircleBeans);
        return resultMap;
    }

    private Double formartData(Double d, double all) {
        if (d == null) {
            return 0d;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.parseDouble(df.format(d * 100 / all));
    }

    public Map<String, Object> columd3D(List<Integer> typeList, int dayBeyond) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> timeList = this.getTimeList(dayBeyond);//时间轴
        resultMap.put("time", timeList);
        List<ColumnBean> dataList = new ArrayList<>();
        for (Integer type : typeList) {
            List<Integer> types = new ArrayList<>();
            types.add(type);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("list", types);
            paramMap.put("day", ControllerDateUtil.getToday());
            paramMap.put("preday", ControllerDateUtil.getPreNDay(-dayBeyond));
            List<RealTimeStaticDoubleHour> resultList = realTimeStaticHourMapper.findDoubleByDay(paramMap);


            List<Double> resultDatas = this.checkData(timeList, resultList);//结果数据
            ColumnBean columnBean = new ColumnBean(DataType.getName(type), resultDatas);
            dataList.add(columnBean);
        }
        resultMap.put("data", dataList);
        return resultMap;
    }

    public Map<String, Object> line(List<Integer> typeList, int dayBeyond) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> timeList = this.getTimeList(dayBeyond);//时间轴
        resultMap.put("time", timeList);
        List<LineBean> dataList = new ArrayList<>();
        for (Integer type : typeList) {
            List<Integer> types = new ArrayList<>();
            types.add(type);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("list", types);
            paramMap.put("day", ControllerDateUtil.getToday());
            paramMap.put("preday", ControllerDateUtil.getPreNDay(-dayBeyond));
            List<RealTimeStaticDoubleHour> resultList = realTimeStaticHourMapper.findDoubleByDay(paramMap);


            List<Double> resultDatas = this.checkData(timeList, resultList);//结果数据
            LineBean columnBean = new LineBean(DataType.getName(type), resultDatas, DataType.getUnit(type));
            dataList.add(columnBean);
        }
        resultMap.put("dataList", dataList);
        return resultMap;
    }

    public Map<String, Object> lineFromDayTable(List<Integer> typeList, int dayBeyond) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> timeList = this.getTimeListEndYesterday(dayBeyond + 1);//时间轴
        resultMap.put("time", timeList);
        List<LineBean> dataList = new ArrayList<>();
        for (Integer type : typeList) {
            List<Integer> types = new ArrayList<>();
            types.add(type);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("list", types);
            paramMap.put("day", ControllerDateUtil.getYesterday());
            paramMap.put("preday", ControllerDateUtil.getPreNDay(-(dayBeyond + 1)));
            List<RealTimeStaticDoubleDay> resultList = realTimeStaticDayMapper.findDoubleByDay(paramMap);


            List<Double> resultDatas = this.checkDataFromDay(timeList, resultList);//结果数据
            LineBean columnBean = new LineBean(DataType.getName(type), resultDatas, DataType.getUnit(type));
            dataList.add(columnBean);
        }
        resultMap.put("dataList", dataList);
        return resultMap;
    }

    private List<Double> checkData(List<String> timeList, List<RealTimeStaticDoubleHour> dataList) {
        Map<String, Double> dataMap = new HashMap<>();
        if (timeList != null && timeList.size() > 0) {
            for (String time : timeList) {
                dataMap.put(time, 0d);
            }
        }
        DecimalFormat threeNumDf = new DecimalFormat("0.00");//每三位分隔一下
        if (dataList != null && dataList.size() > 0) {
            for (RealTimeStaticDoubleHour hour : dataList) {
                if (DataType.P4PAVGKEYS.getType().intValue() == hour.getDataType().intValue()) {
                    dataMap.put(hour.getIrslDateH().substring(0, 8), Double.parseDouble(threeNumDf.format(hour.getDataCount())));
                } else {
                    dataMap.put(hour.getIrslDateH().substring(0, 8), hour.getDataCount());
                }
            }
        }
        List<Double> resultDataList = new ArrayList<>();
        if (timeList != null && timeList.size() > 0) {
            for (String time : timeList) {
                resultDataList.add(dataMap.get(time));
            }
        }
        return resultDataList;
    }

    private List<Double> checkDataFromDay(List<String> timeList, List<RealTimeStaticDoubleDay> dataList) {
        Map<String, Double> dataMap = new HashMap<>();
        if (timeList != null && timeList.size() > 0) {
            for (String time : timeList) {
                dataMap.put(time, 0d);
            }
        }
        DecimalFormat threeNumDf = new DecimalFormat("0.00");//每三位分隔一下
        if (dataList != null && dataList.size() > 0) {
            for (RealTimeStaticDoubleDay hour : dataList) {
                if (DataType.P4PAVGKEYS.getType().intValue() == hour.getDataType().intValue()) {
                    dataMap.put(hour.getIrslDate(), Double.parseDouble(threeNumDf.format(hour.getDataCount())));
                } else {
                    dataMap.put(hour.getIrslDate(), hour.getDataCount());
                }
            }
        }
        List<Double> resultDataList = new ArrayList<>();
        if (timeList != null && timeList.size() > 0) {
            for (String time : timeList) {
                resultDataList.add(dataMap.get(time));
            }
        }
        return resultDataList;
    }

    private List<String> getTimeList(int dayBeyond) {
        List<String> timeList = new ArrayList<String>();//横坐标，时间轴
        for (int i = -dayBeyond; i <= 0; i++) {
            if (timeList.size() == dayBeyond + 1) {
                break;
            }
            timeList.add(DateUtil.plusDays("yyyyMMdd", i)); //时间横坐标
        }
        return timeList;
    }

    private List<String> getTimeListEndYesterday(int dayBeyond) {
        List<String> timeList = new ArrayList<String>();//横坐标，时间轴
        for (int i = -dayBeyond; i <= 1; i++) {
            if (timeList.size() == dayBeyond) {
                break;
            }
            timeList.add(DateUtil.plusDays("yyyyMMdd", i)); //时间横坐标
        }
        return timeList;
    }
}
