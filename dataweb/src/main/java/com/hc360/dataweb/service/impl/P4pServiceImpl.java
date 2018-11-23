package com.hc360.dataweb.service.impl;

import com.hc360.dataweb.dao.RealTimeStatic3DataMapper;
import com.hc360.dataweb.dao.RealTimeStaticHourMapper;
import com.hc360.dataweb.model.*;
import com.hc360.dataweb.service.P4pService;
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
public class P4pServiceImpl implements P4pService {
    private Logger logger = Logger.getLogger(P4pServiceImpl.class);
    @Autowired
    private RealTimeStaticHourMapper realTimeStaticHourMapper;
    @Autowired
    private RealTimeStatic3DataMapper realTimeStatic3DataMapper;

    public Map<String, Object> p4pFormula(List<Integer> typeList) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("list", typeList);
        paramMap.put("day", ControllerDateUtil.getToday());
        paramMap.put("preday", ControllerDateUtil.getToday());
        DecimalFormat threeNumDf = new DecimalFormat(",###.00");//每三位分隔一下
        List<RealTimeStaticDoubleHour> resultList = realTimeStaticHourMapper.findDoubleByDay(paramMap);
        if (resultList != null && resultList.size() > 0) {
            List<MainBean> list = new ArrayList<>();
            MainBean mainBean = null;
            for (RealTimeStaticDoubleHour hour : resultList) {

                if (hour.getDataType().intValue() == DataType.P4PPRICE.getType()) {
                    mainBean = new MainBean("客单价", threeNumDf.format(hour.getDataCount()));
                    list.add(mainBean);
                } else if (hour.getDataType().intValue() == DataType.P4PUSER.getType()) {
                    mainBean = new MainBean("会员数", threeNumDf.format(hour.getDataCount()));
                    list.add(mainBean);
                } else if (hour.getDataType().intValue() == DataType.P4PXIANJINCHARGETOTAL.getType()) {
                    mainBean = new MainBean("销售额", threeNumDf.format(hour.getDataCount() ));
                    list.add(mainBean);
                } else if (hour.getDataType().intValue() == DataType.P4PALLEXPENDTOTAL.getType()) {
                    mainBean = new MainBean("消耗",threeNumDf.format( hour.getDataCount() ));
                    list.add(mainBean);
                } else if (hour.getDataType().intValue() == DataType.P4PALLBALANCE.getType()) {
                    mainBean = new MainBean("余额", threeNumDf.format(hour.getDataCount() ));
                    list.add(mainBean);
                } else if (hour.getDataType().intValue() == DataType.P4PALLCHARGETOTAL.getType()) {
                    mainBean = new MainBean("充值", threeNumDf.format(hour.getDataCount() ));
                    list.add(mainBean);
                }
            }
            resultMap.put("formula", list);
        }

        return resultMap;
    }


    public Map<String, Object> priceDist(int type, String day) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("dataType", type);
        paramMap.put("day", day);
        Map<String, Object> resultMap = new HashMap<>();

        List<RealTimeStatic3Data> resultList = realTimeStatic3DataMapper.findByType(paramMap);
        List<CircleBean> list = new ArrayList<>();
        if (resultList != null && resultList.size() > 0) {
            CircleBean circleBean = null;
            for (RealTimeStatic3Data _realTimeStatic3Data : resultList) {
                circleBean = new CircleBean(_realTimeStatic3Data.getElement(), _realTimeStatic3Data.getDataCount() * 100, _realTimeStatic3Data.getDataCount());
                list.add(circleBean);
            }
        }
        resultMap.put("data", list);

        return resultMap;
    }

    public void  twoCircleUsers(List<Integer> typeList, String day , Map<String, Object> resultMap) {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("list", typeList);
        paramMap.put("day", day);
        paramMap.put("preday", day);
        List<RealTimeStaticDoubleHour> resultList = realTimeStaticHourMapper.findDoubleByDay(paramMap);
        List<TwoCircleBean> twoCircleBeans = new ArrayList<>();
        TwoCircleBean twoCircleBean = null;
        DrillDownBean drillDownBean = null;
        if(resultList!=null && resultList.size()>0){
            Map<Integer, Double> selectResult = new HashMap<>();
            for (RealTimeStaticDoubleHour hour : resultList) {
                selectResult.put(hour.getDataType(), hour.getDataCount());
            }
            Double all = selectResult.get(DataType.P4PBALANCEUSER.getType()) + selectResult.get(DataType.P4PNOBALANCEUSERS.getType());
            drillDownBean = new DrillDownBean("有余额",new String[]{"开启关键词","未开启关键词"} ,new Object[]{
                    formartData(selectResult.get(DataType.P4PBALANCEKEYUSERS.getType()), all),
                    formartData(selectResult.get(DataType.P4PBALANCENOKEYUSERS.getType()), all)
            });
            twoCircleBean = new TwoCircleBean(formartData(selectResult.get(DataType.P4PBALANCEUSER.getType()), all), 1, drillDownBean);
            twoCircleBeans.add(twoCircleBean);
            drillDownBean = new DrillDownBean("无余额",new String[]{"无余额"} ,new Object[]{
                    formartData(selectResult.get(DataType.P4PNOBALANCEUSERS.getType()), all)
            });
            twoCircleBean = new TwoCircleBean(formartData(selectResult.get(DataType.P4PNOBALANCEUSERS.getType()), all), 2, drillDownBean);
            twoCircleBeans.add(twoCircleBean);
        }
        resultMap.put("circleData", twoCircleBeans);
    }

    public Map<String, Object> twoCircle(List<Integer> typeList, String day) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("list", typeList);
        paramMap.put("day", day);
        paramMap.put("preday", day);
        List<RealTimeStaticDoubleHour> resultList = realTimeStaticHourMapper.findDoubleByDay(paramMap);
        List<TwoCircleBean> twoCircleBeans = new ArrayList<>();
        TwoCircleBean twoCircleBean = null;
        DrillDownBean drillDownBean = null;
        if (resultList != null && resultList.size() > 0) {
            Map<Integer, Double> selectResult = new HashMap<>();
            for (RealTimeStaticDoubleHour hour : resultList) {
                selectResult.put(hour.getDataType(), hour.getDataCount());
            }
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
        resultMap.put("data", twoCircleBeans);
        return resultMap;
    }

    private Double formartData(Double d, double all) {
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.parseDouble(df.format(d * 100 / all));
    }

    public Map<String, Object> columd3D(List<Integer> typeList, int dayBeyond) {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> timeList = this.getTimeList(dayBeyond);//时间轴
        resultMap.put("time",timeList) ;
        List<ColumnBean> dataList = new ArrayList<>();
        for (Integer type :typeList){
            List<Integer> types = new ArrayList<>();
            types.add(type);
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("list",types);
            paramMap.put("day", ControllerDateUtil.getToday());
            paramMap.put("preday", ControllerDateUtil.getPreNDay(-dayBeyond));
            List<RealTimeStaticDoubleHour> resultList = realTimeStaticHourMapper.findDoubleByDay(paramMap);


            List<Double> resultDatas = this.checkData(timeList, resultList);//结果数据
            ColumnBean columnBean = new ColumnBean(DataType.getName(type) , resultDatas);
            dataList.add(columnBean);
        }
        resultMap.put("data",dataList);
        return resultMap;
    }

    private List<Double> checkData(List<String> timeList, List<RealTimeStaticDoubleHour> dataList) {
        Map<String, Double> dataMap = new HashMap<>();
        if (timeList != null && timeList.size() > 0) {
            for (String time : timeList) {
                dataMap.put(time, 0d);
            }
        }

        if (dataList != null && dataList.size() > 0) {
            for (RealTimeStaticDoubleHour hour : dataList) {
                dataMap.put(hour.getIrslDateH().substring(0,8), hour.getDataCount());
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
            if (timeList.size() == dayBeyond+1) {
                break;
            }
            timeList.add(DateUtil.plusDays("yyyyMMdd", i)); //时间横坐标
        }
        return timeList;
    }
}
