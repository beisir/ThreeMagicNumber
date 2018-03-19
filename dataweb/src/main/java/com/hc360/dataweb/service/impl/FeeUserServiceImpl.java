package com.hc360.dataweb.service.impl;

import com.hc360.dataweb.common.constants.ChartsConstant;
import com.hc360.dataweb.dao.*;
import com.hc360.dataweb.model.*;
import com.hc360.dataweb.service.FeeUserService;
import com.hc360.dataweb.util.CommonUtil;
import com.hc360.dataweb.util.ControllerDateUtil;
import com.hc360.dataweb.util.DateUtil;
import com.hc360.dataweb.util.EmailUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HC360 on 2017/3/9.
 */
@Service
public class FeeUserServiceImpl implements FeeUserService {
    private Logger logger = Logger.getLogger(FeeUserServiceImpl.class);
    @Autowired
    private RealTimeStaticDayMapper realTimeStaticDayMapper;
    @Autowired
    private RealtimeStaticMonthMapper realtimeStaticMonthMapper;
    @Autowired
    private RealTimeStaticHourMapper realtimeStaticHourMapper;
    @Autowired
    private RealtimeStaticWeekMapper realtimeStaticWeekMapper;

    private RealTimeMonthWeekBaseMapper realTimeMonthWeekBaseMapper;

    @Override
    public void initFeeData(Map<String, Object> dataList) throws Exception {
        Map<String, Object> todayMap = new HashMap<String, Object>();
        Map<String, Object> yesterdayMap = new HashMap<String, Object>();
        List<MainBean> mainBeans = new ArrayList<MainBean>();//色块部分(收费会员,Leads数,LEADS转化率,3类客户数,付费会员交易率,DAU)
        Map<String, Object> renewBeans = new HashMap<String, Object>();//续签率(标王,MMT)
        List<MainBean> champion = new ArrayList<MainBean>();
        List<MainBean> mmt = new ArrayList<MainBean>();
        String leadsDay = ControllerDateUtil.getLeadsYesterday();//取leads转化率与续签率的时间
        String month =leadsDay.substring(0,6);//获取当前月份
        String day = ControllerDateUtil.getToday();//取今天的日期
        initMainBeans(mainBeans, day, null);//查询今天色块部分数据
        initChampionData(champion, leadsDay, month, null);
        initMMTData(mmt, leadsDay, month, null);
        renewBeans.put("增值产品", champion);
        renewBeans.put("基本产品", mmt);
        todayMap.put("main", mainBeans);
        todayMap.put("renew", renewBeans);


        List<MainBean> championY = new ArrayList<MainBean>();
        List<MainBean> mmtY = new ArrayList<MainBean>();
        List<MainBean> mainBeansY = new ArrayList<MainBean>();
        Map<String, Object> renewBeansY = new HashMap<String, Object>();
        String yesterDay = ControllerDateUtil.getYesterday();//取昨天的日期
        initMainBeans(mainBeansY, day, yesterDay);//查询昨天色块部分数据
        initChampionData(championY, leadsDay, month, yesterDay);
        initMMTData(mmtY, leadsDay, month, yesterDay);

        renewBeansY.put("增值产品", championY);
        renewBeansY.put("基本产品", mmtY);

        yesterdayMap.put("main", mainBeansY);
        yesterdayMap.put("renew", renewBeansY);

        dataList.put("todaydata", todayMap);
        dataList.put("yesterdaydata", yesterdayMap);
    }

    @Override
    public void getUserSatisficing(Map<String, Object> dataMap) throws Exception {
        List<String> timeList = new ArrayList<>();//X轴
        List<Integer> dataTypes = new ArrayList<>();
        List<DayChartBean> dataList = new ArrayList<>();
        Map<String, Object> param = new HashMap<String, Object>();
        initXAxis(timeList);
        initDXType(dataTypes);
        dataList.add(initSatisficingData(param,dataTypes, 1));
        dataTypes = new ArrayList<>();
        initHYType(dataTypes);
        dataList.add(initSatisficingData(param,dataTypes, 2));
        dataTypes = new ArrayList<>();
        initQDType(dataTypes);
        dataList.add(initSatisficingData(param,dataTypes, 3));
        dataTypes = new ArrayList<>();
        initZZXSType(dataTypes);
        dataList.add(initSatisficingData(param,dataTypes, 4));
        dataTypes = new ArrayList<>();
        initJMSHType(dataTypes);
        dataList.add(initSatisficingData(param,dataTypes, 5));
        dataMap.put("time", timeList);
        dataMap.put("dataList", dataList);
    }

    private void initJMSHType(List<Integer> dataTypes) {
        dataTypes.add(DataType.JMSHACLASS.getType());
        dataTypes.add(DataType.JMSHBCLASS.getType());
        dataTypes.add(DataType.JMSHCCLASS.getType());
        dataTypes.add(DataType.JMSHDCLASS.getType());
    }
    /**
     * 获取用户产品贡献数据
     */
    private void getUserProductCont(Map<String, Object> dataMap, String time) throws Exception {
        List<String> timeList = new ArrayList<>();//X轴
        List<Integer> dataTypes = new ArrayList<>();
        List<DayChartBean> dataList = new ArrayList<>();
        Map<String, Object> param = new HashMap<String, Object>();
        initXAxisCont(timeList);//x轴刻度
        //电销
        if(ChartsConstant.FINANCE_DIST.equals(time)){//按财务区分
            getFinanceDXTypeCont(dataTypes);
        }else if(ChartsConstant.ORDER_DIST.equals(time)){//按销售订单区分
            getOrderDXTypeCont(dataTypes);
        }
        dataList.add(getFinanceDXProductContData(dataTypes, param, time));
        dataTypes = new ArrayList<>();
        //渠道
        if(ChartsConstant.FINANCE_DIST.equals(time)){//按财务区分
            getFinanceQDTypeCont(dataTypes);
        }else if(ChartsConstant.ORDER_DIST.equals(time)){//按销售订单区分
            getOrderQDTypeCont(dataTypes);
        }
        dataList.add(getFinanceQDProductContData(dataTypes, param, time));

        dataMap.put("time", timeList);
        dataMap.put("dataList", dataList);
    }
    private void getOrderQDTypeCont(List<Integer> dataTypes) {
        dataTypes.add(DataType.ORDER_QD_THIRDPARTNAR.getType());
        dataTypes.add(DataType.ORDER_QD_ELSE.getType());
        dataTypes.add(DataType.ORDER_QD_FLOW.getType());
        dataTypes.add(DataType.ORDER_QD_MMT.getType());
        dataTypes.add(DataType.ORDER_QD_BUSINESS.getType());
        dataTypes.add(DataType.ORDER_QD_CHAMPION.getType());
        dataTypes.add(DataType.ORDER_QD_THIRDPARTNAR_NUM.getType());
        dataTypes.add(DataType.ORDER_QD_ELSE_NUM.getType());
        dataTypes.add(DataType.ORDER_QD_FLOW_NUM.getType());
        dataTypes.add(DataType.ORDER_QD_MMT_NUM.getType());
        dataTypes.add(DataType.ORDER_QD_BUSINESS_NUM.getType());
        dataTypes.add(DataType.ORDER_QD_CHAMPION_NUM.getType());
    }
    private void getOrderDXTypeCont(List<Integer> dataTypes) {
        dataTypes.add(DataType.ORDER_DX_THIRDPARTNAR.getType());
        dataTypes.add(DataType.ORDER_DX_ELSE.getType());
        dataTypes.add(DataType.ORDER_DX_FLOW.getType());
        dataTypes.add(DataType.ORDER_DX_MMT.getType());
        dataTypes.add(DataType.ORDER_DX_BUSINESS.getType());
        dataTypes.add(DataType.ORDER_DX_CHAMPION.getType());
        dataTypes.add(DataType.ORDER_DX_THIRDPARTNAR_NUM.getType());
        dataTypes.add(DataType.ORDER_DX_ELSE_NUM.getType());
        dataTypes.add(DataType.ORDER_DX_FLOW_NUM.getType());
        dataTypes.add(DataType.ORDER_DX_MMT_NUM.getType());
        dataTypes.add(DataType.ORDER_DX_BUSINESS_NUM.getType());
        dataTypes.add(DataType.ORDER_DX_CHAMPION_NUM.getType());
    }

    //产品用户贡献(渠道)
    private DayChartBean getFinanceQDProductContData(List<Integer> dataTypes, Map<String, Object> param, String time) throws Exception {
        Map<String, Integer> dataItem = new HashMap<>();
        param.put("list", dataTypes);
        List<RealTimeStaticDay> datas = realTimeStaticDayMapper.findRealTimeDataNewest(param);
        DayChartBean bean = new DayChartBean();
        if (datas != null && !datas.isEmpty()) {
            for (RealTimeStaticDay data : datas) {
                Integer dataType = data.getDataType();
                dataItem.put(DataType.getName(dataType), data.getDataCount());
            }
            if(ChartsConstant.FINANCE_DIST.equals(time)){//按财务区分
                calculateFinanceQD(bean, dataItem);//计算
            }else if(ChartsConstant.ORDER_DIST.equals(time)){//按销售订单区分
                calculateOrderQD(bean, dataItem);
            }
        }
        bean.setName("渠道");
        bean.setUnit("元");
        return bean;
    }

    /*按财务拆分用户产品贡献*/
    private void calculateFinanceQD(DayChartBean bean, Map<String, Integer> dataItem) {
        DecimalFormat df = new DecimalFormat("0.00");
        List<Double> beanData = new ArrayList<>();
        if (dataItem.get(DataType.FINANCE_QD_CHAMPION.getName()) != null && dataItem.get(DataType.FINANCE_QD_CHAMPION_NUM.getName()) != null && dataItem.get(DataType.FINANCE_QD_CHAMPION_NUM.getName()) > 0) {
            /*标王*/
            Integer money = dataItem.get(DataType.FINANCE_QD_CHAMPION.getName());
            Integer number = dataItem.get(DataType.FINANCE_QD_CHAMPION_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据" + DataType.FINANCE_QD_CHAMPION.getType() + "或" + DataType.FINANCE_QD_CHAMPION_NUM.getType() + "数据为空");
        }
        if (dataItem.get(DataType.FINANCE_QD_FLOW.getName()) != null && dataItem.get(DataType.FINANCE_QD_FLOW_NUM.getName()) != null && dataItem.get(DataType.FINANCE_QD_FLOW_NUM.getName()) > 0) {
            /*流量宝*/
            Integer money = dataItem.get(DataType.FINANCE_QD_FLOW.getName());
            Integer number = dataItem.get(DataType.FINANCE_QD_FLOW_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据" + DataType.FINANCE_QD_FLOW.getType() + "或" + DataType.FINANCE_QD_FLOW_NUM.getType() + "数据为空");
        }
        if (dataItem.get(DataType.FINANCE_QD_MMT.getName()) != null && dataItem.get(DataType.FINANCE_QD_MMT_NUM.getName()) != null && dataItem.get(DataType.FINANCE_QD_MMT_NUM.getName()) > 0) {
            /*买卖通*/
            Integer money = dataItem.get(DataType.FINANCE_QD_MMT.getName());
            Integer number = dataItem.get(DataType.FINANCE_QD_MMT_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据" + DataType.FINANCE_QD_MMT.getType() + "或" + DataType.FINANCE_QD_MMT_NUM.getType() + "数据为空");
        }
        if (dataItem.get(DataType.FINANCE_QD_BUSINESS.getName()) != null && dataItem.get(DataType.FINANCE_QD_BUSINESS_NUM.getName()) != null && dataItem.get(DataType.FINANCE_QD_BUSINESS_NUM.getName()) > 0) {
            /*商营通*/
            Integer money = dataItem.get(DataType.FINANCE_QD_BUSINESS.getName());
            Integer number = dataItem.get(DataType.FINANCE_QD_BUSINESS_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据" + DataType.FINANCE_QD_BUSINESS.getType() + "或" + DataType.FINANCE_QD_BUSINESS_NUM.getType() + "数据为空");
        }
        if (dataItem.get(DataType.FINANCE_QD_THIRDPARTNAR.getName()) != null && dataItem.get(DataType.FINANCE_QD_THIRDPARTNAR_NUM.getName()) != null && dataItem.get(DataType.FINANCE_QD_THIRDPARTNAR_NUM.getName()) > 0) {
            /*代运营*/
            Integer money = dataItem.get(DataType.FINANCE_QD_THIRDPARTNAR.getName());
            Integer number = dataItem.get(DataType.FINANCE_QD_THIRDPARTNAR_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据" + DataType.FINANCE_QD_THIRDPARTNAR.getType() + "或" + DataType.FINANCE_QD_THIRDPARTNAR_NUM.getType() + "数据为空");
        }
        if (dataItem.get(DataType.FINANCE_QD_ELSE.getName()) != null && dataItem.get(DataType.FINANCE_QD_ELSE_NUM.getName()) != null && dataItem.get(DataType.FINANCE_QD_ELSE_NUM.getName()) > 0) {
            /*其他*/
            Integer money = dataItem.get(DataType.FINANCE_QD_ELSE.getName());
            Integer number = dataItem.get(DataType.FINANCE_QD_ELSE_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据" + DataType.FINANCE_QD_ELSE.getType() + "或" + DataType.FINANCE_QD_ELSE_NUM.getType() + "数据为空");
        }
    }
    /*按销售订单拆分用户产品贡献*/
    private void calculateOrderQD(DayChartBean bean, Map<String, Integer> dataItem) {
        DecimalFormat df = new DecimalFormat("0.00");
        List<Double> beanData = new ArrayList<>();
        if (dataItem.get(DataType.ORDER_QD_CHAMPION.getName()) != null && dataItem.get(DataType.ORDER_QD_CHAMPION_NUM.getName()) != null && dataItem.get(DataType.ORDER_QD_CHAMPION_NUM.getName()) > 0) {
            /*标王*/
            Integer money = dataItem.get(DataType.ORDER_QD_CHAMPION.getName());
            Integer number = dataItem.get(DataType.ORDER_QD_CHAMPION_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据" + DataType.ORDER_QD_CHAMPION.getType() + "或" + DataType.ORDER_QD_CHAMPION_NUM.getType() + "数据为空");
        }
        if (dataItem.get(DataType.ORDER_QD_FLOW.getName()) != null && dataItem.get(DataType.ORDER_QD_FLOW_NUM.getName()) != null && dataItem.get(DataType.ORDER_QD_FLOW_NUM.getName()) > 0) {
            /*流量宝*/
            Integer money = dataItem.get(DataType.ORDER_QD_FLOW.getName());
            Integer number = dataItem.get(DataType.ORDER_QD_FLOW_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据" + DataType.ORDER_QD_FLOW.getType() + "或" + DataType.ORDER_QD_FLOW_NUM.getType() + "数据为空");
        }
        if (dataItem.get(DataType.ORDER_QD_MMT.getName()) != null && dataItem.get(DataType.ORDER_QD_MMT_NUM.getName()) != null && dataItem.get(DataType.ORDER_QD_MMT_NUM.getName()) > 0) {
            /*买卖通*/
            Integer money = dataItem.get(DataType.ORDER_QD_MMT.getName());
            Integer number = dataItem.get(DataType.ORDER_QD_MMT_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据" + DataType.ORDER_QD_MMT.getType() + "或" + DataType.ORDER_QD_MMT_NUM.getType() + "数据为空");
        }
        if (dataItem.get(DataType.ORDER_QD_BUSINESS.getName()) != null && dataItem.get(DataType.ORDER_QD_BUSINESS_NUM.getName()) != null && dataItem.get(DataType.ORDER_QD_BUSINESS_NUM.getName()) > 0) {
            /*商营通*/
            Integer money = dataItem.get(DataType.ORDER_QD_BUSINESS.getName());
            Integer number = dataItem.get(DataType.ORDER_QD_BUSINESS_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据" + DataType.ORDER_QD_BUSINESS.getType() + "或" + DataType.ORDER_QD_BUSINESS_NUM.getType() + "数据为空");
        }
        if (dataItem.get(DataType.ORDER_QD_THIRDPARTNAR.getName()) != null && dataItem.get(DataType.ORDER_QD_THIRDPARTNAR_NUM.getName()) != null && dataItem.get(DataType.ORDER_QD_THIRDPARTNAR_NUM.getName()) > 0) {
            /*代运营*/
            Integer money = dataItem.get(DataType.ORDER_QD_THIRDPARTNAR.getName());
            Integer number = dataItem.get(DataType.ORDER_QD_THIRDPARTNAR_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据" + DataType.ORDER_QD_THIRDPARTNAR.getType() + "或" + DataType.ORDER_QD_THIRDPARTNAR_NUM.getType() + "数据为空");
        }
        if (dataItem.get(DataType.ORDER_QD_ELSE.getName()) != null && dataItem.get(DataType.ORDER_QD_ELSE_NUM.getName()) != null && dataItem.get(DataType.ORDER_QD_ELSE_NUM.getName()) > 0) {
            /*其他*/
            Integer money = dataItem.get(DataType.ORDER_QD_ELSE.getName());
            Integer number = dataItem.get(DataType.ORDER_QD_ELSE_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据" + DataType.ORDER_QD_ELSE.getType() + "或" + DataType.ORDER_QD_ELSE_NUM.getType() + "数据为空");
        }
    }

    private void getFinanceQDTypeCont(List<Integer> dataTypes) {
        dataTypes.add(DataType.FINANCE_QD_THIRDPARTNAR.getType());     //    ("thirdpartnar",124),
        dataTypes.add(DataType.FINANCE_QD_ELSE.getType());     //("qdelse",125),
        dataTypes.add(DataType.FINANCE_QD_FLOW.getType());     //("qdflow",126),
        dataTypes.add(DataType.FINANCE_QD_MMT.getType());     //("qdmmt",127),
        dataTypes.add(DataType.FINANCE_QD_BUSINESS.getType());     //("qdbusiness",128),
        dataTypes.add(DataType.FINANCE_QD_CHAMPION.getType());     //("qdchampion",129),
        dataTypes.add(DataType.FINANCE_QD_THIRDPARTNAR_NUM.getType());     //("thirdpartnarnum",130),
        dataTypes.add(DataType.FINANCE_QD_ELSE_NUM.getType());     //("qdelsenum",131),
        dataTypes.add(DataType.FINANCE_QD_FLOW_NUM.getType());     //("qdflownum",132),
        dataTypes.add(DataType.FINANCE_QD_MMT_NUM.getType());     //("qdmmtnum",133),
        dataTypes.add(DataType.FINANCE_QD_BUSINESS_NUM.getType());     //("qdbusinessnum",134),
        dataTypes.add(DataType.FINANCE_QD_CHAMPION_NUM.getType());     //("qdchampionnum",135);
    }
    //产品用户贡献(电销)
    private DayChartBean getFinanceDXProductContData(List<Integer> dataTypes, Map<String, Object> param, String time) throws Exception {
        Map<String, Integer> dataItem = new HashMap<>();
        param.put("list", dataTypes);
        List<RealTimeStaticDay> datas = realTimeStaticDayMapper.findRealTimeDataNewest(param);
        DayChartBean bean = new DayChartBean();
        if (datas != null && !datas.isEmpty()) {
            for (RealTimeStaticDay data : datas) {
                Integer dataType = data.getDataType();
                dataItem.put(DataType.getName(dataType), data.getDataCount());
            }
            if(ChartsConstant.FINANCE_DIST.equals(time)){//按财务区分
                calculateFinanceDX(bean, dataItem);//计算
            }else if(ChartsConstant.ORDER_DIST.equals(time)){//按销售订单区分
                calculateOrderDX(bean, dataItem);//计算
            }
        }
        bean.setName("电销");
        bean.setUnit("元");
        return bean;
    }

    /*计算按财务拆分用户产品贡献*/
    private void calculateFinanceDX(DayChartBean bean, Map<String, Integer> dataItem) {
        DecimalFormat df = new DecimalFormat("0.00");
        List<Double> beanData = new ArrayList<>();
        if (dataItem.get(DataType.FINANCE_DX_CHAMPION.getName()) != null && dataItem.get(DataType.FINANCE_DX_CHAMPION_NUM.getName()) != null && dataItem.get(DataType.FINANCE_DX_CHAMPION_NUM.getName()) > 0) {
            /*标王*/
            Integer money = dataItem.get(DataType.FINANCE_DX_CHAMPION.getName());
            Integer number = dataItem.get(DataType.FINANCE_DX_CHAMPION_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据"+DataType.FINANCE_DX_CHAMPION.getType()+"或"+DataType.FINANCE_DX_CHAMPION_NUM.getType()+"数据为空");
        }
        if (dataItem.get(DataType.FINANCE_DX_FLOW.getName()) != null && dataItem.get(DataType.FINANCE_DX_FLOW_NUM.getName()) != null && dataItem.get(DataType.FINANCE_DX_FLOW_NUM.getName()) > 0) {
            /*流量宝*/
            Integer money = dataItem.get(DataType.FINANCE_DX_FLOW.getName());
            Integer number = dataItem.get(DataType.FINANCE_DX_FLOW_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据"+DataType.FINANCE_DX_FLOW.getType()+"或"+DataType.FINANCE_DX_FLOW_NUM.getType()+"数据为空");
        }
        if (dataItem.get(DataType.FINANCE_DX_MMT.getName()) != null && dataItem.get(DataType.FINANCE_DX_MMT_NUM.getName()) != null && dataItem.get(DataType.FINANCE_DX_MMT_NUM.getName()) > 0) {
            /*买卖通*/
            Integer money = dataItem.get(DataType.FINANCE_DX_MMT.getName());
            Integer number = dataItem.get(DataType.FINANCE_DX_MMT_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据"+DataType.FINANCE_DX_MMT.getType()+"或"+DataType.FINANCE_DX_MMT_NUM.getType()+"数据为空");
        }
        if (dataItem.get(DataType.FINANCE_DX_BUSINESS.getName()) != null && dataItem.get(DataType.FINANCE_DX_BUSINESS_NUM.getName()) != null && dataItem.get(DataType.FINANCE_DX_BUSINESS_NUM.getName()) > 0) {
            /*商营通*/
            Integer money = dataItem.get(DataType.FINANCE_DX_BUSINESS.getName());
            Integer number = dataItem.get(DataType.FINANCE_DX_BUSINESS_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据"+DataType.FINANCE_DX_BUSINESS.getType()+"或"+DataType.FINANCE_DX_BUSINESS_NUM.getType()+"数据为空");
        }
        if (dataItem.get(DataType.FINANCE_DX_THIRDPARTNAR.getName()) != null && dataItem.get(DataType.FINANCE_DX_THIRDPARTNAR_NUM.getName()) != null && dataItem.get(DataType.FINANCE_DX_THIRDPARTNAR_NUM.getName()) > 0) {
            /*代运营*/
            Integer money = dataItem.get(DataType.FINANCE_DX_THIRDPARTNAR.getName());
            Integer number = dataItem.get(DataType.FINANCE_DX_THIRDPARTNAR_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据"+DataType.FINANCE_DX_THIRDPARTNAR.getType()+"或"+DataType.FINANCE_DX_THIRDPARTNAR_NUM.getType()+"数据为空");
        }
        if (dataItem.get(DataType.FINANCE_DX_ELSE.getName()) != null && dataItem.get(DataType.FINANCE_DX_ELSE_NUM.getName()) != null && dataItem.get(DataType.FINANCE_DX_ELSE_NUM.getName()) > 0) {
            /*其他*/
            Integer money = dataItem.get(DataType.FINANCE_DX_ELSE.getName());
            Integer number = dataItem.get(DataType.FINANCE_DX_ELSE_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据"+DataType.FINANCE_DX_ELSE.getType()+"或"+DataType.FINANCE_DX_ELSE_NUM.getType()+"数据为空");
        }
    }
    /*计算按销售订单拆分用户产品贡献*/
    private void calculateOrderDX(DayChartBean bean, Map<String, Integer> dataItem) {
        DecimalFormat df = new DecimalFormat("0.00");
        List<Double> beanData = new ArrayList<>();
        if (dataItem.get(DataType.ORDER_DX_CHAMPION.getName()) != null && dataItem.get(DataType.ORDER_DX_CHAMPION_NUM.getName()) != null && dataItem.get(DataType.ORDER_DX_CHAMPION_NUM.getName()) > 0) {
            /*标王*/
            Integer money = dataItem.get(DataType.ORDER_DX_CHAMPION.getName());
            Integer number = dataItem.get(DataType.ORDER_DX_CHAMPION_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据"+DataType.ORDER_DX_CHAMPION.getType()+"或"+DataType.ORDER_DX_CHAMPION_NUM.getType()+"数据为空");
        }
        if (dataItem.get(DataType.ORDER_DX_FLOW.getName()) != null && dataItem.get(DataType.ORDER_DX_FLOW_NUM.getName()) != null && dataItem.get(DataType.ORDER_DX_FLOW_NUM.getName()) > 0) {
            /*流量宝*/
            Integer money = dataItem.get(DataType.ORDER_DX_FLOW.getName());
            Integer number = dataItem.get(DataType.ORDER_DX_FLOW_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据"+DataType.ORDER_DX_FLOW.getType()+"或"+DataType.ORDER_DX_FLOW_NUM.getType()+"数据为空");
        }
        if (dataItem.get(DataType.ORDER_DX_MMT.getName()) != null && dataItem.get(DataType.ORDER_DX_MMT_NUM.getName()) != null && dataItem.get(DataType.ORDER_DX_MMT_NUM.getName()) > 0) {
            /*买卖通*/
            Integer money = dataItem.get(DataType.ORDER_DX_MMT.getName());
            Integer number = dataItem.get(DataType.ORDER_DX_MMT_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据"+DataType.ORDER_DX_MMT.getType()+"或"+DataType.ORDER_DX_MMT_NUM.getType()+"数据为空");
        }
        if (dataItem.get(DataType.ORDER_DX_BUSINESS.getName()) != null && dataItem.get(DataType.ORDER_DX_BUSINESS_NUM.getName()) != null && dataItem.get(DataType.ORDER_DX_BUSINESS_NUM.getName()) > 0) {
            /*商营通*/
            Integer money = dataItem.get(DataType.ORDER_DX_BUSINESS.getName());
            Integer number = dataItem.get(DataType.ORDER_DX_BUSINESS_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据"+DataType.ORDER_DX_BUSINESS.getType()+"或"+DataType.ORDER_DX_BUSINESS_NUM.getType()+"数据为空");
        }
        if (dataItem.get(DataType.ORDER_DX_THIRDPARTNAR.getName()) != null && dataItem.get(DataType.ORDER_DX_THIRDPARTNAR_NUM.getName()) != null && dataItem.get(DataType.ORDER_DX_THIRDPARTNAR_NUM.getName()) > 0) {
            /*代运营*/
            Integer money = dataItem.get(DataType.ORDER_DX_THIRDPARTNAR.getName());
            Integer number = dataItem.get(DataType.ORDER_DX_THIRDPARTNAR_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据"+DataType.ORDER_DX_THIRDPARTNAR.getType()+"或"+DataType.ORDER_DX_THIRDPARTNAR_NUM.getType()+"数据为空");
        }
        if (dataItem.get(DataType.ORDER_DX_ELSE.getName()) != null && dataItem.get(DataType.ORDER_DX_ELSE_NUM.getName()) != null && dataItem.get(DataType.ORDER_DX_ELSE_NUM.getName()) > 0) {
            /*其他*/
            Integer money = dataItem.get(DataType.ORDER_DX_ELSE.getName());
            Integer number = dataItem.get(DataType.ORDER_DX_ELSE_NUM.getName());
            beanData.add(Double.valueOf(df.format((double) money / number)));
            bean.setData(beanData);
        }else{
            beanData.add(0.00);
            bean.setData(beanData);
            logger.error("天表:产品用户贡献:查询最新时间数据"+DataType.ORDER_DX_ELSE.getType()+"或"+DataType.ORDER_DX_ELSE_NUM.getType()+"数据为空");
        }
    }

    private void getFinanceDXTypeCont(List<Integer> dataTypes) {
        dataTypes.add(DataType.FINANCE_DX_THIRDPARTNAR.getType());
        dataTypes.add(DataType.FINANCE_DX_ELSE.getType());
        dataTypes.add(DataType.FINANCE_DX_FLOW.getType());
        dataTypes.add(DataType.FINANCE_DX_MMT.getType());
        dataTypes.add(DataType.FINANCE_DX_BUSINESS.getType());
        dataTypes.add(DataType.FINANCE_DX_CHAMPION.getType());
        dataTypes.add(DataType.FINANCE_DX_THIRDPARTNAR_NUM.getType());
        dataTypes.add(DataType.FINANCE_DX_ELSE_NUM.getType());
        dataTypes.add(DataType.FINANCE_DX_FLOW_NUM.getType());
        dataTypes.add(DataType.FINANCE_DX_MMT_NUM.getType());
        dataTypes.add(DataType.FINANCE_DX_BUSINESS_NUM.getType());
        dataTypes.add(DataType.FINANCE_DX_CHAMPION_NUM.getType());
    }

    private void initXAxisCont(List<String> timeList) {
        timeList.add("增值产品");
        timeList.add("P4P");
        timeList.add("基本产品");
        timeList.add("深度运营");
        timeList.add("初级运营");
        timeList.add("其他");
    }

    private void initZZXSType(List<Integer> dataTypes) {
        dataTypes.add(DataType.ZZXSACLASS.getType());
        dataTypes.add(DataType.ZZXSBCLASS.getType());
        dataTypes.add(DataType.ZZXSCCLASS.getType());
        dataTypes.add(DataType.ZZXSDCLASS.getType());
    }

    private void initQDType(List<Integer> dataTypes) {
        dataTypes.add(DataType.QDACLASS.getType());
        dataTypes.add(DataType.QDBCLASS.getType());
        dataTypes.add(DataType.QDCCLASS.getType());
        dataTypes.add(DataType.QDDCLASS.getType());
    }

    private void initHYType(List<Integer> dataTypes) {
        dataTypes.add(DataType.HYACLASS.getType());
        dataTypes.add(DataType.HYBCLASS.getType());
        dataTypes.add(DataType.HYCCLASS.getType());
        dataTypes.add(DataType.HYDCLASS.getType());
    }

    /*客户满意度*/
    private DayChartBean initSatisficingData(Map<String, Object> param, List<Integer> dataTypes, int flag) throws Exception {
        param.put("list", dataTypes);
        List<RealTimeStaticDay> datas = realTimeStaticDayMapper.findRealTimeDataNewest(param);
        DayChartBean bean = new DayChartBean();
        if (datas != null && !datas.isEmpty()) {
            List<Double> beanData = new ArrayList<>();
            for (RealTimeStaticDay data : datas) {
                if (data.getDataCount() != null) {
                    beanData.add(data.getDataCount().doubleValue());
                } else {
                    beanData.add(0.00);
                }
            }
            bean.setData(beanData);
        }
        String name = "";
        if (flag == 1) {
            name = "电销";
        } else if (flag == 2) {
            name = "行业";
        } else if (flag == 3) {
            name = "渠道";
        } else if (flag == 4) {
            name = "自助销售";
        } else if (flag == 5) {
            name = "加盟商";
        }
        bean.setName(name);
        bean.setUnit("");
        return bean;
    }

    private void initDXType(List<Integer> dataypes) {
        dataypes.add(DataType.DXACLASS.getType());
        dataypes.add(DataType.DXBCLASS.getType());
        dataypes.add(DataType.DXCCLASS.getType());
        dataypes.add(DataType.DXDCLASS.getType());
    }

    private void initXAxis(List<String> timeList) {
        timeList.add("A类客户");
        timeList.add("B类客户");
        timeList.add("C类客户");
        timeList.add("D类客户");
    }

    /*查询买卖通相关续签率*/
    private void initMMTData(List<MainBean> mmt, String day, String month, String yesterDay) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List<Integer> dataTypes = new ArrayList<Integer>();
        initMMTTypes(dataTypes);
        param.put("month", month);
        param.put("day", day);
        param.put("list", dataTypes);
        List<RealtimeStaticMonth> monthData = null;
        if (yesterDay == null) {
            monthData = realtimeStaticMonthMapper.findRealTimeDatasMonth(param);
        } else {
            yesterDay = ControllerDateUtil.getLeadsBeforeYesterday();//获取前天的日期
            month = yesterDay.substring(0,6);
            param.put("day", yesterDay);
            param.put("month", month);
            monthData = realtimeStaticMonthMapper.findRealTimeDatasMonth(param);
        }
        if (monthData != null && !monthData.isEmpty()) {
            convertMonth(mmt, monthData);
            /*if ("01".equals(day.substring(5, 6)) && yesterDay != null) {//如果查询的为1号那么对比前天的数据,前天数据默认为0
                for (MainBean mainBean : mmt) {
                    mainBean.setNum("0");
                }
            }*/
        } else {
            logger.error("查询买卖通相关续签率数据为空");
        }
    }

    /*查询标王相关续签率*/
    private void initChampionData(List<MainBean> champion, String day, String month, String yesterDay) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        List<Integer> dataTypes = new ArrayList<Integer>();
        initChampionTypes(dataTypes);
        param.put("month", month);
        param.put("day", day);
        param.put("list", dataTypes);
        List<RealtimeStaticMonth> monthData = null;
        if (yesterDay == null) {//今天
            monthData = realtimeStaticMonthMapper.findRealTimeDatasMonth(param);
        } else {//昨天
            yesterDay = ControllerDateUtil.getLeadsBeforeYesterday();//获取前天的日期
            month = yesterDay.substring(0,6);//月份
            param.put("day", yesterDay);
            param.put("month", month);
            monthData = realtimeStaticMonthMapper.findRealTimeDatasMonth(param);
        }
        if (monthData != null && !monthData.isEmpty()) {
            convertMonth(champion, monthData);
            /*if ("01".equals(day.substring(5, 6)) && yesterDay != null) {//查询昨天
                for (MainBean mainBean : champion) {
                    mainBean.setNum("0");
                }
            }*/
        } else {
            logger.error("查询标王相关续签率数据为空");
        }
    }

    private void initChampionTypes(List<Integer> dataTypes) {
        dataTypes.add(DataType.CHAMPIONDS.getType());
        dataTypes.add(DataType.CHAMPIONDX.getType());
        dataTypes.add(DataType.CHAMPIONHY.getType());
        dataTypes.add(DataType.CHAMPIONQD.getType());
    }

    private void initMMTTypes(List<Integer> dataTypes) {
        dataTypes.add(DataType.MMTDS.getType());
        dataTypes.add(DataType.MMTDX.getType());
        dataTypes.add(DataType.MMTHY.getType());
        dataTypes.add(DataType.MMTQD.getType());
    }


    /*查询今天/昨天的数据*/
    private void initMainBeans(List<MainBean> mainBeans, String day, String yesterDay) throws Exception {
        List<RealTimeStaticDay> dataToday = null;
        List<RealTimeStaticDay> dataYester = null;
        Map<String, Object> param = new HashMap<String, Object>();

        List<Integer> types = initMainTypes();
        param.put("list", types);
        param.put("day", day);
        if (yesterDay == null) {//今天
            dataToday = realTimeStaticDayMapper.findRealTimeDataToday(param);
        } else {
            types = initMainAlikeTypes();
            param.put("list", types);
            param.put("yesterDay", yesterDay);
            dataToday = realTimeStaticDayMapper.findRealTimeDataYester(param);
            param = new HashMap<>();//查询收费会员昨天最后时间的数据
            List<Integer> feeuserTotalTypes = new ArrayList<>();
            feeuserTotalTypes.add(DataType.FEEUSERTOTAL.getType());
            param.put("yesterDay", yesterDay);
            param.put("list", feeuserTotalTypes);
            dataYester = realTimeStaticDayMapper.findRealTimeLastDataYester(param);
        }
        if (dataToday != null && !dataToday.isEmpty()) {
            convertTop(dataToday, mainBeans);
        } else {
            logger.error("获取天表" + day + "或" + yesterDay + ":---数据类型:" + types.toString() + "---数据为空");
        }
        if(yesterDay!=null){//处理昨天的收费会员数据
            if(dataYester!=null && !dataYester.isEmpty()){
                convertTop(dataYester, mainBeans);
            }else {
                mainBeans.add(new MainBean(DataType.FEEUSERTOTAL.getName(), "0"));
                logger.error("获取天表" + yesterDay + ":---数据类型:" + DataType.FEEUSERTOTAL.getType() + "---数据为空");
            }

        }
        /*付费会员交易率{交易付费会员数}*/
        /*Integer transaction = DataType.FEEUSERTRANSACTION.getType();
        types = new ArrayList<>();
        types.add(transaction);
        List<RealTimeStaticDay> todayData = null;
        MainBean doubleBean = null;
        param = new HashMap<String, Object>();
        param.put("list",types);
        if (yesterDay == null) {//今天
            todayData = realTimeStaticDayMapper.findRealTimeDataNewest(param);
            if(todayData!=null && !todayData.isEmpty()){
                convertTop(todayData,mainBeans);
            }
        }else {
            todayData = realTimeStaticDayMapper.findRealTimeDataSecond(param);
            if (todayData!=null && !todayData.isEmpty()){
                convertTop(todayData,mainBeans);
            }
        }*/
        /*param = new HashMap<String, Object>();
        Integer leadsType = DataType.LEADSTRANSFORM.getType();//leads转化率
        *//*时间为前一天的日期*//*
        String leadsday = ControllerDateUtil.getLeadsYesterday();
        String month = leadsday.substring(0,6);//获取当前月份
        param.put("month", month);
        param.put("day", leadsday);
        param.put("type", leadsType);
        List<RealtimeStaticMonth> monthData = null;
        List<MainBean> leadsBeans = new ArrayList<MainBean>();
        MainBean leadsBean = new MainBean();
        if (yesterDay == null) {
            monthData = realtimeStaticMonthMapper.findRealTimeDataMonth(param);
        } else {
            if ("01".equals(day.substring(5, 6))) {
                leadsBean.setName("Leads转化率");
                leadsBean.setNum("0");
            } else {
                yesterDay = ControllerDateUtil.getLeadsBeforeYesterday();//获取前天的日期
                param.put("day", yesterDay);
                monthData = realtimeStaticMonthMapper.findRealTimeDataMonth(param);
            }
        }
        if (monthData != null && !monthData.isEmpty()) {
            convertMonth(leadsBeans, monthData);
            mainBeans.add(leadsBeans.get(0));
        } else {
            logger.error("色块部分(收费会员,Leads数,LEADS转化率,3类客户数,付费会员交易率)数据为空");
        }*/

    }

    /*转换月表数据*/
    private void convertMonth(List<MainBean> leadsBeans, List<RealtimeStaticMonth> realTimeDatas) {
        DecimalFormat df = new DecimalFormat("0.00");
        MainBean leadsBean = null;
        for (RealtimeStaticMonth realTimeData : realTimeDatas) {
            leadsBean = new MainBean();
            if (realTimeData.getDataType() != null && realTimeData.getDataCount() != null) {
                leadsBean.setName(DataType.getName(realTimeData.getDataType()));
                leadsBean.setNum(df.format(realTimeData.getDataCount())+"%");
            } else {
                leadsBean.setName(DataType.getName(realTimeData.getDataType()));
                leadsBean.setNum("0.00%");
                EmailUtil.warnEveryOne("月表:数据时间:" + realTimeData.getDataDate() + "---" + realTimeData.getDataType() + "--数据为空。");
            }
            leadsBeans.add(leadsBean);
        }
    }

    /*转换顶部色块的数据--天表*/
    private void convertTop(List<RealTimeStaticDay> realTimeDatas, List<MainBean> mainBeans) {
        for (RealTimeStaticDay realTimeData : realTimeDatas) {
            if (realTimeData.getDataType() != null && realTimeData.getDataCount() != null) {
                mainBeans.add(new MainBean(DataType.getName(realTimeData.getDataType()), realTimeData.getDataCount()+""));
            } else {
                mainBeans.add(new MainBean(DataType.getName(realTimeData.getDataType()), "0"));
                EmailUtil.warnEveryOne("天表:数据时间:" + realTimeData.getIrslDate() + "---" + realTimeData.getDataType() + "--数据为空。");
            }
        }
    }

    /*获取查询日表,对比昨天同一时间的数据类型*/
    private List<Integer> initMainTypes() {
        List<Integer> dataTypes = new ArrayList<Integer>();
        dataTypes.add(DataType.LEADS.getType());
        dataTypes.add(DataType.FEEUSERTOTAL.getType());
        dataTypes.add(DataType.USER3CLASS.getType());
        //dataTypes.add(DataType.DAUTYPE.getType());
        return dataTypes;
    }
    /*查询昨天同一时间数据*/
    private List<Integer> initMainAlikeTypes() {
        List<Integer> dataTypes = new ArrayList<Integer>();
        dataTypes.add(DataType.LEADS.getType());
        dataTypes.add(DataType.USER3CLASS.getType());
        //dataTypes.add(DataType.DAUTYPE.getType());
        return dataTypes;
    }

    @Override
    public void initChartData(Integer dataType, String time, Map<String, Object> dataMap) throws Exception {
        String day = ControllerDateUtil.getToday();//今日日期
        List<Integer> types = new ArrayList<Integer>();
        if(dataType == DataType.FINANCE_DX_THIRDPARTNAR.getType().intValue()){//产品用户贡献
            getUserProductCont(dataMap, time);
        } else if (dataType == DataType.LEADS.getType().intValue() ) {//可分配LEADS数
            if (ChartsConstant.TODAY.equals(time)) {//今天(每小时的增量)
                getTodayData(day, dataType, dataMap);
            } else if (ChartsConstant.MONTH.equals(time)) {//最近三十天
                initDayData(time, day, dataType, dataMap);
            } else if (ChartsConstant.WEEK_DATA.equals(time)) {//周度数据
                initWeekData(dataType, dataMap,day);
            } else if (ChartsConstant.MONTH_DATA.equals(time)) {//月度数据
                initMonthData(dataType, dataMap);
            }
        }  else if (dataType == DataType.STOCK_LEADS.getType().intValue() ) {//可分配存量LEADS总数
            if (ChartsConstant.TODAY.equals(time)) {//今天(每小时的增量)
                getTodayData(day, dataType, dataMap);
            }else if(ChartsConstant.MONTH.equals(time)){ //最近三十天的 ---20170605
//                initDayData(time, day, dataType, dataMap);
//                getTodayDataByDataTypes(day, dataType, dataMap);
                get30DaysDataFromHourTable(dataType,time,day,dataMap);
            }

        } else if (dataType == DataType.K_REGISTER_ONLINE.getType().intValue()) {//可分配LEADS来源(八个)
            if (ChartsConstant.TODAY.equals(time)) {//今天(累计到当前时间的量)
                initTodayData(day, 1, dataMap);
            } else if (ChartsConstant.MONTH.equals(time)) {//最近一个月(天表)
                initMonthDayData(day, 1, dataMap);
            } else if (ChartsConstant.WEEK_DATA.equals(time)) {//周度数据
                initWeekDatas(dataMap, 1,day);
            } else if (ChartsConstant.MONTH_DATA.equals(time)) {//月度数据
                initMonthDatas(dataMap, 1);
            }
        } else if (dataType == DataType.KC_REGISTER_ONLINE.getType().intValue()) {// 可分配存量LEADS来源(八个)
            if (ChartsConstant.TODAY.equals(time)) {//今天(累计到当前时间的量)
                initTodayData(day, 3, dataMap);
            }else if (ChartsConstant.MONTH.equals(time)){ //最近一个月 20170605
                initMonthDayData(day, 3, dataMap);
            }
        } else if (dataType == DataType.YLEADSCOUNT.getType().intValue()) {//已分配LEADS数
            if (ChartsConstant.TODAY.equals(time)) {//今天
                getTodayData(day, dataType, dataMap);
            } else if (ChartsConstant.MONTH.equals(time)) {//最近三十天
                initDayData(time, day, dataType, dataMap);
            } else if (ChartsConstant.WEEK_DATA.equals(time)) {//周度数据
                initWeekData(dataType, dataMap,day);
            } else if (ChartsConstant.MONTH_DATA.equals(time)) {//月度数据
                initMonthData(dataType, dataMap);
            }
        }else if (dataType == DataType.YREGISTERONLINE.getType().intValue()) {//已分配LEADS来源
            if (ChartsConstant.TODAY.equals(time)) {//今天(累计到当前时间的量)
                initTodayData(day, 2, dataMap);
            } else if (ChartsConstant.MONTH.equals(time)) {//最近一个月(天表)
                initMonthDayData(day, 2, dataMap);
            }  else if (ChartsConstant.WEEK_DATA.equals(time)) {//周度数据
                initWeekDatas(dataMap, 2,day);
            } else if (ChartsConstant.MONTH_DATA.equals(time)) {//月度数据
                initMonthDatas(dataMap, 2);
            }
        }else if (dataType == DataType.Z_REGISTER_ONLINE.getType().intValue()){// leads来源转化率 297

            if (ChartsConstant.MONTH_DATA.equals(time)) {//月度数据
                initMonthDatas(dataMap, 4);
            }

        } else if (dataType == DataType.LEADSTRANSFORM.getType().intValue()) {//Leads转化率(月度数据)
            if (ChartsConstant.MONTH_DATA.equals(time)) {
                initMonthData(dataType, dataMap);
            }
        } else if (dataType == DataType.USER3CLASS.getType().intValue()) {//3类客户数(当天,最近三十天)
            if (ChartsConstant.TODAY.equals(time)) {//今天
//                getTodayData(day, dataType, dataMap);
                getTodayDataByDataTypes(day, dataType, dataMap);

            }else if (ChartsConstant.MONTH.equals(time)){//最近三十天
                initDayData(time, day, dataType, dataMap);
            }else if(ChartsConstant.TOTAL.equals(time)){//累计(新增累计数量的data_type值)
                initDayData(time,day,dataType,dataMap);
            }
        } /*else if (dataType == DataType.FEEUSERTRANSACTION.getType().intValue()) {//交易付费会员数(最近三十天)
            if(ChartsConstant.MONTH.equals(time)){
                initDayData(time, day, dataType, dataMap);
            }
        }*/ else if(dataType == DataType.DAUTYPE.getType().intValue()){//卖家DAU(最近三十天)
            if(ChartsConstant.MONTH.equals(time)){//最近三十天
                initDayData(time, day, dataType, dataMap);
            }
        } else if (dataType == DataType.BAIDU_LM_DAY.getType().intValue() ) {//百度联盟数
            if (ChartsConstant.TODAY.equals(time)) {//本月天维度---20170630
                getMonthDayData(dataType, dataMap);
            }else if(ChartsConstant.MONTH_DATA.equals(time)){ //当年每月维度 ---20170630
              initDueMonthData(dataType, dataMap);
            }
        } else if (dataType == DataType.P4P_QWDT_TOTAL.getType().intValue() ) {//全网定投
          if (ChartsConstant.WEEK_DATA.equals(time)) {//周度数据---20171018
              initWeekData(dataType, dataMap,day);
            }

        }

    }




  private void getTodayDataByDataTypes(String day, Integer dataType,Map<String, Object> dataMap) {
        List<String> timeList = CommonUtil.getTimeShaft();//X轴
//        List<HourChartBean> dataList = new ArrayList<HourChartBean>();
        List<Integer> dataTypes = new ArrayList<Integer>();//数据类型
        if(dataType == DataType.USER3CLASS.getType().intValue()){//3类客户数需查询电销&渠道&全部
//            dataTypes.add(DataType.DXUSER3CLASS.getType());

            dataTypes.add(DataType.DXXQWADDUSER3CLASSTOTAL.getType());
            dataTypes.add(DataType.DXXXWADDUSER3CLASSTOTAL.getType());
            dataTypes.add(DataType.QDUSER3CLASS.getType());
        }
        dataTypes.add(dataType);
        Map<String, Object> param = new HashMap<String, Object>();
        List<String> times = CommonUtil.getTimeShaft(day);
        param.put("day", day);
//        param.put("type", dataType);
        param.put("list", dataTypes);
//        List<RealTimeStaticHour> todayData = realtimeStaticHourMapper.findTodayData(param);
        List<RealTimeStaticHour> todayData = realtimeStaticHourMapper.findAllByDay2(param);
        List<HourChartBean> hourChartBeans = new ArrayList<HourChartBean>();//小时数据
        if (todayData != null && !todayData.isEmpty()) {
            hourChartBeans = convertHourList(todayData, times ,dataTypes);
        }
        dataMap.put("dataList", hourChartBeans);
        dataMap.put("time", timeList);
    }

    private void get30DaysDataFromHourTable(int type,String time, String day,Map<String, Object> dataMap){
        int timeFlag = CommonUtil.initTime(time);//区分时间区段
        List<DayChartBean> dayData = new ArrayList<DayChartBean>();//天数据
        List<String> timeList = CommonUtil.getTimeShaftD(timeFlag);
        String preDay = ControllerDateUtil.getPreNDay(-(timeFlag - 1));
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("day", day);
        paramMap.put("preDay", preDay);
        List<Integer> types = new ArrayList();
        types.add(type);
        paramMap.put("list", types);
        List<RealTimeStaticHour> list = this.realtimeStaticHourMapper.findAllBy30Day(paramMap);
        List<Object> doubles = dayConvertFromHourTable(list,timeList);

        List<HourChartBean> hourChartBeans = new ArrayList<>();
        HourChartBean bean = new HourChartBean();
        bean.setData(doubles);
        bean.setIsShow(true);
        bean.setName(DataType.getName(type));
        bean.setUnit("个");
        hourChartBeans.add(bean);
        dataMap.put("dataList", hourChartBeans);
        dataMap.put("time", timeList);
    }
    private List<Object> dayConvertFromHourTable(List<RealTimeStaticHour> data,List<String> times) {
        Map<String, Object> initMap = CommonUtil.initHourTimeMap(times);//初始化时间
        List<Object> dataList = new ArrayList<Object>();
        if (data != null && data.size() > 0) {
            for (RealTimeStaticHour realTimeStaticDay : data) {
                if (!"".equals(realTimeStaticDay.getIrslDateH()) && realTimeStaticDay.getDataCount() != null) {
                    initMap.put(realTimeStaticDay.getIrslDateH(), realTimeStaticDay.getDataCount());
                } else {
                    logger.error("查询时表数据:数据时间:"+realTimeStaticDay.getIrslDateH() +"数据类型:"+realTimeStaticDay.getDataType() + "-- 数据为空。");
                }
            }
        }
        if(times!= null && times.size()>0){
            Object timeData = null;
            for (String time : times) {
                timeData = initMap.get(time);
                dataList.add(timeData);
            }
        }
        return dataList;
    }


    private List<HourChartBean> convertHourList(List<RealTimeStaticHour> hourDatas, List<String> times,List<Integer> dataTypes) {
        List<HourChartBean> hourChartBeans = new ArrayList<HourChartBean>();

        HourChartBean bean = null;
        Map<String, Object> initMap = CommonUtil.initHourTimeMap(times);//初始化时间
        Map<String,Object> tempMap = new HashMap<>();//用于数据补零,小时数据当前最新数据之前的补零,之后的维持原有""处理
        Map<Integer,Map<String, Object>> dataMap = new HashMap<>();
        Integer type = 0;
        Map<String, Object> _dataList = new HashMap<>();
        if (hourDatas != null && hourDatas.size() > 0) {
            for (RealTimeStaticHour realTimeStaticHour : hourDatas) {
                if (realTimeStaticHour.getDataCount() != null && realTimeStaticHour.getDataType() != null) {
                    type = realTimeStaticHour.getDataType();
                    tempMap.put("temp",realTimeStaticHour.getIrslDateH());
                    if(dataMap.get(type) == null ){
                        _dataList =new HashMap<>();
                    }else{
                        _dataList = dataMap.get(type);
                    }
                    if (CommonUtil.initArith(type)) {
                        _dataList.put(realTimeStaticHour.getIrslDateH(), (realTimeStaticHour.getDataCount().doubleValue() / 10000)); //单位为万
                    } else {
                        _dataList.put(realTimeStaticHour.getIrslDateH(), (realTimeStaticHour.getDataCount().doubleValue())); //单位为个/元
                    }
                    dataMap.put( type, _dataList);
                } else {
                    logger.error("查询小时表数据:数据时间:"+realTimeStaticHour.getIrslDateH()+"数据类型:"+realTimeStaticHour.getDataType() + "-- 数据为空。");
                }
            }
            List<Object> dataList = null;
            for (Integer t  : dataTypes){
                bean = new HourChartBean();
                dataList = new ArrayList<Object>();
                for (String time : times) {
                    if(Integer.valueOf(time)<=Integer.valueOf((String) tempMap.get("temp"))){//有数据的最大时间之前数据为空进行补零
                        if("".equals(dataMap.get(t).get(time))){
                            dataList.add(0);
                        }else{
                            dataList.add(dataMap.get(t).get(time));
                        }
                    }else{
                        dataList.add(dataMap.get(t).get(time));
                    }
                }
                bean.setData(dataList);
                bean.setName(CommonUtil.initName(t));
                bean.setUnit(CommonUtil.initUnit(t));
                hourChartBeans.add(bean);
            }
        } else {
            logger.error("FeeuserServiceImpl.折线图获取小时表数据集合为空");
        }
        return hourChartBeans;
    }
    /*Leads来源最近一个月*/
    private void initMonthDayData(String day, int flag, Map<String, Object> dataMap) throws Exception {
        List<DayChartBean> dataList = new ArrayList<DayChartBean>();
        List<Integer> sourceTypes = new ArrayList<>();//8种leads数来源的类型
        Map<String, Object> param = new HashMap<String, Object>();
        DayChartBean bean = null;
        List<String> times = CommonUtil.getTimeShaftD(ChartsConstant.SHOWMONTH);
        String preDay = ControllerDateUtil.getPreNDay(-(ChartsConstant.SHOWMONTH - 1));
        if(flag == 1){//可分配
            inintSourceTypesK(sourceTypes);
        } else if(flag == 2) {//已分配
            inintSourceTypesY(sourceTypes);
        }else if (flag ==3 ){//库存量
            inintSourceTypesKC(sourceTypes);
        }
        for(Integer type:sourceTypes){
            bean = new DayChartBean();
            bean.setName(DataType.getName(type));
            System.out.println(DataType.getName(type)+type);
            param.put("day",day);
            param.put("preDay",preDay);
            param.put("type",type);
            List<RealTimeStaticDay> dataToday = realTimeStaticDayMapper.findDayData(param);
            List<Double> dataCount = dayConvert(dataToday,times);
            bean.setData(dataCount);
            bean.setUnit("");
            dataList.add(bean);
        }
        dataMap.put("dataList", dataList);
        dataMap.put("time", times);
    }

    private void initTodayData(String day,int flag, Map<String, Object> dataMap) throws Exception {
        List<HourChartBean> dataList = new ArrayList<HourChartBean>();
        List<Integer> sourceTypes = new ArrayList<>();//8种leads数来源的类型
        Map<String, Object> param = new HashMap<String, Object>();
        HourChartBean bean = new HourChartBean();
        List<String> time = new ArrayList<>();
        getXAxisSource(time);//X轴
        if(flag == 1){//可分配
            inintSourceTypesK(sourceTypes);
            bean.setName("可分配Leads来源");
        } else if(flag == 2) {//已分配
            inintSourceTypesY(sourceTypes);
            bean.setName("已分配Leads来源");
        }else if(flag == 3){
            inintSourceTypesKC(sourceTypes);
            bean.setName("库存Leads来源");
        }
        Map<Integer, Object> item = new HashMap<Integer, Object>();
        for(Integer type:sourceTypes){
            item.put(type,0);
        }

        List<Object> count = new ArrayList<>();
        param.put("list",sourceTypes);
        List<RealTimeStaticDay> dataToday = realTimeStaticDayMapper.findLastDataByOpDate(param);
        if(dataToday!=null && !dataToday.isEmpty()){
            for(Integer type:sourceTypes){
                for(RealTimeStaticDay data:dataToday){
                    if(type == data.getDataType().intValue() && data.getDataCount()!=null){
                        item.put(type, data.getDataCount());
                    }
                }
            }
        }
        for(Integer type:sourceTypes){
            count.add(item.get(type));
        }
        bean.setData(count);

        bean.setUnit("");
        dataList.add(bean);
        dataMap.put("dataList", dataList);
        dataMap.put("time", time);
    }

    private void getStockXAxisSource(List<String> time) {
        time.add(DataType.KC_REGISTER_ONLINE.getName());
        time.add(DataType.KC_ALIBABA_CHARGE.getName());
        time.add(DataType.KC_APPLY_ONLINE.getName());
        time.add(DataType.KC_PROCESS.getName());
        time.add(DataType.KC_ALIBABA_FREE.getName());
        time.add(DataType.KC_BAIDU_GRAB.getName());
        time.add(DataType.KC_EXTEND_GRAB.getName());
        time.add(DataType.KC_SPECIALWEBSITE_GRAB.getName());
    }

    private void inintSourceTypesKC(List<Integer> sourceTypes) {
        sourceTypes.add(DataType.KC_REGISTER_ONLINE.getType());
        sourceTypes.add(DataType.KC_ALIBABA_CHARGE.getType());
        sourceTypes.add(DataType.KC_APPLY_ONLINE.getType());
        sourceTypes.add(DataType.KC_PROCESS.getType());
        sourceTypes.add(DataType.KC_ALIBABA_FREE.getType());
        sourceTypes.add(DataType.KC_BAIDU_GRAB.getType());
        sourceTypes.add(DataType.KC_EXTEND_GRAB.getType());
        sourceTypes.add(DataType.KC_SPECIALWEBSITE_GRAB.getType());
    }

    private void getXAxisSource(List<String> time) {
        time.add(DataType.K_REGISTER_ONLINE.getName());
        time.add(DataType.K_ALIBABA_CHARGE.getName());
        time.add(DataType.K_APPLY_ONLINE.getName());
        time.add(DataType.K_PROCESS.getName());
        time.add(DataType.K_ALIBABA_FREE.getName());
        time.add(DataType.K_BAIDU_GRAB.getName());
        time.add(DataType.K_EXTEND_GRAB.getName());
        time.add(DataType.K_SPECIALWEBSITE_GRAB.getName());
    }

    /*月度天数据获取*/
    private void getMonthDayData(Integer dataType,Map<String, Object> dataMap) throws Exception {
        List<HourChartBean> dataList = new ArrayList<HourChartBean>();
        String month = DateUtil.getMonth("MM");//获取当前月度
        Map<String, Object> param = new HashMap<String, Object>();
        HourChartBean bean = new HourChartBean();
        List<String> monthTimes = new ArrayList<>();//月度数据时间轴
        param.put("type", dataType);
        param.put("month",month);
        List<RealTimeStaticDoubleDay> monthDatas = realTimeStaticDayMapper.findMonthDayDoubleData(param);
        List<Object> dataCount = dayDoubleConvert(monthDatas,dataType,monthTimes);
        bean.setName(CommonUtil.initName(dataType));
        bean.setUnit(CommonUtil.initUnit(dataType));
        bean.setData(dataCount);
        dataList.add(bean);
        List<String> time = CommonUtil.initYearMonthTime(monthTimes);
        dataMap.put("dataList", dataList);
        dataMap.put("time", time);
    }
    /*月度数据获取*/
    private void initMonthData(Integer dataType,Map<String, Object> dataMap) throws Exception {
        List<HourChartBean> dataList = new ArrayList<HourChartBean>();
        String year = DateUtil.getYear("yyyy");//获取当前年度
        String month =  DateUtil.getYear("MM");
        /*if("01".equals(month)){
            year = (Integer.parseInt(year) -1 )+""; //如果当前是1月份，那么，年度显示去年的数据
        }*/
        year = year + month;
        Map<String, Object> param = new HashMap<String, Object>();
        HourChartBean bean = new HourChartBean();
        List<String> weekTimes = new ArrayList<>();//周度数据时间轴
        param.put("type", dataType);
        param.put("year",year);
        List<RealtimeStaticMonth> monthData = realtimeStaticMonthMapper.fingYearMonthDatanew(param);
        List<Object> dataCount = monthConvert(monthData, weekTimes);
        bean.setName(CommonUtil.initName(dataType));
        bean.setUnit(CommonUtil.initUnit(dataType));
        bean.setData(dataCount);
        dataList.add(bean);
        List<String> time = CommonUtil.initYearMonthTime(weekTimes);
        dataMap.put("dataList", dataList);
        dataMap.put("time", time);
    }
    /*多组月度数据获取*/
    private void initMonthDatas(Map<String, Object> dataMap,int flag) throws Exception {
        List<HourChartBean> dataList = new ArrayList<HourChartBean>();//小时数据
        List<Integer> sourceTypes = new ArrayList<>();//8种leads数来源的类型
        if(flag == 1){//可分配
            inintSourceTypesK(sourceTypes);
        } else if(flag == 2) {//已分配
            inintSourceTypesY(sourceTypes);
        }else if(flag == 4) {//leads来源转化率
            inintSourceTypesC(sourceTypes);
        }
        Map<String, Object> paramTime = new HashMap<String, Object>();
        paramTime.put("list", sourceTypes);
        String monthTime = realtimeStaticMonthMapper.findMonthRecentlyIrsl_date(paramTime);//获取最新月份
        //生成匹配的map
        Map<String, Object> monthMap = new HashMap<String, Object>();
        List<String> monthTimes = new ArrayList<>();//获取map数据需要的时间list
        List<String> time = new ArrayList<>();//月度数据时间轴
        if(monthTime!=null && !"".equals(monthTime)){
            initIrslDateMapnew(monthMap, monthTime, monthTimes);
            Map<String, Object> param = new HashMap<String, Object>();
            if(sourceTypes!=null && !sourceTypes.isEmpty()){
                HourChartBean bean = null;
                List<RealtimeStaticMonth> monthData = null;
                for(Integer type:sourceTypes){
                    bean = new HourChartBean();
                    param.put("type",type);

                    if(flag == 4){
                        monthData = realtimeStaticMonthMapper.findMonthDataByIrsl_date(param);
                    }else{
                        monthData = realtimeStaticMonthMapper.findMonthDataByDataDate(param);
                    }
                    List<Object> dataCount = monthConvertBZ(monthMap, monthData, monthTimes,type);
                    bean.setName(CommonUtil.initName(type));
                    bean.setUnit(CommonUtil.initUnit(type));
                    bean.setData(dataCount);
                    dataList.add(bean);
                    for(int i=0; i<monthTimes.size(); i++){//存放好后,将map清空
                        monthMap.put(monthTimes.get(i),0);
                    }
                }
            }
            time = CommonUtil.initYearMonthTime(monthTimes);
        }else{
            logger.error("realtimeStaticMonthMapper.findMonthRecentlyIrsl_date获取最新月份为空");
        }


        dataMap.put("dataList", dataList);
        dataMap.put("time", time);
    }

    /*周度数据获取
    private void initWeekData(Integer dataType,Map<String, Object> dataMap) throws Exception {
        List<HourChartBean> dataList = new ArrayList<HourChartBean>();//小时数据
        String year = DateUtil.getYear("yyyy");//获取当前年度
        Map<String, Object> param = new HashMap<String, Object>();
        List<String> weekTimes = new ArrayList<>();//周度数据时间轴
        HourChartBean bean = new HourChartBean();
        param.put("type",dataType);
        param.put("year",year);
        List<RealtimeStaticWeek> weekData = realtimeStaticWeekMapper.findYearWeekData(param);
        List<Object> dataCount = weekConvert(weekData, weekTimes);
        bean.setName(CommonUtil.initName(dataType));
        bean.setUnit(CommonUtil.initUnit(dataType));
        bean.setData(dataCount);
        dataList.add(bean);
        List<String> time = CommonUtil.initYearWeekTime(weekTimes);
        dataMap.put("dataList", dataList);
        dataMap.put("time", time);

    }*/
  /*周度数据获取*/
    private void initWeekData(Integer dataType,Map<String, Object> dataMap,String day) throws Exception {
    List<HourChartBean> dataList = new ArrayList<HourChartBean>();//小时数据
        day = ControllerDateUtil.strToDateFmt(day);
    //String year = DateUtil.getYear("yyyy");//获取当前年度
    //获得多少周
   /* List<RealTimeMonthWeekBase> weeks = realTimeMonthWeekBaseMapper.findMonthWeekBase(day,13);
    List<String> time= new ArrayList<>();
    if (weeks!=null && !weeks.isEmpty()){
       for (RealTimeMonthWeekBase rtmw:weeks){
           time.add(rtmw.getWeekNum());
       }
    }*/

    List<Integer> types = new ArrayList<Integer>();
    if(DataType.P4P_QWDT_TOTAL.getType().equals(dataType)){
      types.add(DataType.P4P_QWDT_WEEK_TOTAL.getType());
      types.add(DataType.P4P_QWDT_WEEK_JP.getType());
      types.add(DataType.P4P_QWDT_WEEK_JP_KEY.getType());
    } else{
      types.add(dataType);
    }
        List<String> weekTimes = new ArrayList<>();//周度数据时间轴
    for(Integer type:types){//全网一组周度数据
      HourChartBean bean = new HourChartBean();

      Map<String, Object> param = new HashMap<String, Object>();
      param.put("type",type);
      param.put("flag",12);
      //List<RealtimeStaticWeek> weekData = realtimeStaticWeekMapper.findYearWeekData(param);
        List<RealtimeStaticDoubleWeek> weekData = realtimeStaticWeekMapper.findWeekDataDoubleDay(param);
      //List<Object> dataCount = weekConvert(weekData, weekTimes);
        List<Object> dataCount = weekConvertnew(weekData, weekTimes);
      bean.setName(CommonUtil.initName(type));
      bean.setUnit(CommonUtil.initUnit(type));
      bean.setData(dataCount);
      dataList.add(bean);
      /*if(weekTimes!=null && weekTimes.size()>0 && weekTimes.size()>=time.size()) {
        time = CommonUtil.initYearWeekTime(weekTimes);
      }*/
    }

    dataMap.put("dataList", dataList);
   dataMap.put("time", weekTimes);

  }




    /*处理多组week数据*/
    private void initWeekDatas(Map<String, Object> dataMap, int flag,String day) throws Exception {
        List<HourChartBean> dataList = new ArrayList<HourChartBean>();//小时数据
        List<Integer> sourceTypes = new ArrayList<>();//8种leads数来源的类型
        if(flag == 1){//可分配
            inintSourceTypesK(sourceTypes);
        } else if(flag == 2) {//已分配
            inintSourceTypesY(sourceTypes);
        }
        Map<String, Object> paramTime = new HashMap<String, Object>();
        paramTime.put("list",sourceTypes);
        String weekTime = realtimeStaticWeekMapper.findWeekRecentlyIrsl_date(paramTime);//获取最大的周
        paramTime.put("year",Integer.parseInt(day.substring(0,4))-1);
        String weekTimeYear = realtimeStaticWeekMapper.findWeekRecentlyIrsl_date_week(paramTime);
        //生成匹配的map
        Map<String, Object> weekMap = new HashMap<String, Object>();
        List<String> weekTimes = new ArrayList<>();//匹配需要的周度时间list
        List<String> time = null;//周度数据时间轴
        if(weekTime!=null && !weekTime.isEmpty()){//只有查询出最新的周才可进行以下处理
            initIrslDateMapnews(weekMap, weekTime, weekTimes,weekTimeYear);
            Map<String, Object> param = new HashMap<String, Object>();
            HourChartBean bean = null;
            for(Integer type:sourceTypes){
                bean = new HourChartBean();
                param.put("type",type);
                List<RealtimeStaticWeek> weekData = realtimeStaticWeekMapper.findWeekDataByDataDate(param);
                List<Object> dataCount = weekConvertBZ(weekMap,weekData, weekTimes,type);
                bean.setName(CommonUtil.initName(type));
                bean.setUnit("");
                bean.setData(dataCount);
                dataList.add(bean);
                for(int i=0; i<weekTimes.size(); i++){//存放好数据后,将map清空
                    weekMap.put(weekTimes.get(i),0);
                }
            }
            time = CommonUtil.initYearWeekTime(weekTimes);
        }else{
            logger.error("realtimeStaticWeekMapper.findWeekRecentlyIrsl_date()获取最新周时间为空!");
        }

        dataMap.put("dataList", dataList);
        dataMap.put("time", time);

    }

    /**
     * 生成比对结果集的map集合
     * @param timeMap
     * @param time
     * @param times
     */
    private void initIrslDateMap(Map<String, Object> timeMap, String time, List<String> times) {
        String year = DateUtil.getYear("yyyy");//获取当前年度
        String month =  DateUtil.getYear("MM");
        if("01".equals(month)){
            year = (Integer.parseInt(year) -1 )+""; //如果当前是1月份，那么，年度显示去年的数据
        }
        Integer qw = Integer.valueOf(time.substring(4,6));
        for(int i=1; i<=qw; i++){
            if(i<10){
                timeMap.put(year+0+i,0);
                times.add(year+0+i);
            }else{
                timeMap.put(year+i,0);
                times.add(year+i);
            }
        }
    }

    /**
     * 生成比对结果集的map集合 new
     * @param timeMap
     * @param time
     * @param times
     */
    private void initIrslDateMapnews(Map<String, Object> timeMap, String time, List<String> times, String weekTimeyear) {
        String year = DateUtil.getYear("yyyy");//获取当前年度
        String month =  DateUtil.getYear("MM");

        Integer qw = Integer.valueOf(time.substring(4,6));
        Integer qwyear = Integer.valueOf(weekTimeyear.substring(4,6));
        String year_last = weekTimeyear.substring(0,4);
        if (qw<12){
            int temp = 12 - qw-1;
            String key = null;
            for (int i=temp;i>=0;i--){
                key = year_last + String.valueOf(qwyear-i);
                timeMap.put(key,0);
                times.add(key);
            }
        }
        for(int i=1; i<=qw; i++){
            if(i<10){
                timeMap.put(year+0+i,0);
                times.add(year+0+i);
            }else{
                timeMap.put(year+i,0);
                times.add(year+i);
            }
        }


    }
    //获得连续１２月的时间
    private void initIrslDateMapnew(Map<String, Object> timeMap, String time, List<String> times) {
        List<String> months = DateUtil.getMonthInfo(12,"yyyyMM");
        for (String month:months
             ) {
            timeMap.put(month,0);
            times.add(month);
        }
    }


    /*可分配8种leads数来源的类型*/
    private void inintSourceTypesK(List<Integer> sourceTypes) {
        sourceTypes.add(DataType.K_REGISTER_ONLINE.getType());
        sourceTypes.add(DataType.K_ALIBABA_CHARGE.getType());
        sourceTypes.add(DataType.K_APPLY_ONLINE.getType());
        sourceTypes.add(DataType.K_PROCESS.getType());
        sourceTypes.add(DataType.K_ALIBABA_FREE.getType());
        sourceTypes.add(DataType.K_BAIDU_GRAB.getType());
        sourceTypes.add(DataType.K_EXTEND_GRAB.getType());
        sourceTypes.add(DataType.K_SPECIALWEBSITE_GRAB.getType());
    }
    /*库存leads来源的类型*/
    private void inintSourceTypesC(List<Integer> sourceTypes) {
        sourceTypes.add(DataType.Z_REGISTER_ONLINE.getType());//297
        sourceTypes.add(DataType.Z_ALIBABA_CHARGE.getType());//298
        sourceTypes.add(DataType.Z_APPLY_ONLINE.getType());//299
        sourceTypes.add(DataType.Z_PROCESS.getType());//300
        sourceTypes.add(DataType.Z_ALIBABA_FREE.getType());//301
        sourceTypes.add(DataType.Z_BAIDU_GRAB.getType());//302
        sourceTypes.add(DataType.Z_EXTEND_GRAB.getType());//303
        sourceTypes.add(DataType.Z_SPECIALWEBSITE_GRAB.getType());//304
        sourceTypes.add(DataType.Z_MANNUL_INPUT.getType());//311
        sourceTypes.add(DataType.Z_PHONE.getType());//312

    }

    /*已分配8种leads数来源的类型*/
    private void inintSourceTypesY(List<Integer> sourceTypes) {
        sourceTypes.add(DataType.YREGISTERONLINE.getType());
        sourceTypes.add(DataType.YALIBABACHARGE.getType());
        sourceTypes.add(DataType.YAPPLYONLINE.getType());
        sourceTypes.add(DataType.YPROCESS.getType());
        sourceTypes.add(DataType.YALIBABAFREE.getType());
        sourceTypes.add(DataType.YBAIDUGRAB.getType());
        sourceTypes.add(DataType.YEXTENDGRAB.getType());
        sourceTypes.add(DataType.YSPECIALWEBSITEGRAB.getType());
    }

     /*最近7天&最近30天&累计公共获取*/
    private void initDayData(String time, String day, Integer dataType,Map<String, Object> dataMap) throws Exception {
        int timeFlag = CommonUtil.initTime(time);//区分时间区段
        List<DayChartBean> dayData = new ArrayList<DayChartBean>();//天数据
        List<String> timeList = CommonUtil.getTimeShaftD(timeFlag);
        String preDay = ControllerDateUtil.getPreNDay(-(timeFlag - 1));
        List<Integer> dataTypes = new ArrayList<Integer>();
        if(dataType==DataType.USER3CLASS.getType().intValue()){//data_types用于3类客户数{需查询电销&渠道&全部}
            if(time.equals(ChartsConstant.TOTAL)){//累计
                dataType = CommonUtil.initOtherType(dataType);//转换新增或累计数据的data_type值
//                dataTypes.add(DataType.DXUSER3CLASSTOTAL.getType());
                dataTypes.add(DataType.DXNEWUSER3CLASSTOTAL.getType());
                dataTypes.add(DataType.DXXQUSER3CLASSTOTAL.getType());
                dataTypes.add(DataType.QDUSER3CLASSTOTAL.getType());

            }

            if(time.equals(ChartsConstant.MONTH)){//累计
//                dataTypes.add(DataType.DXUSER3CLASS.getType());
                dataTypes.add(DataType.DXXQWADDUSER3CLASSTOTAL.getType());
                dataTypes.add(DataType.DXXXWADDUSER3CLASSTOTAL.getType());
                dataTypes.add(DataType.QDUSER3CLASS.getType());
            }
        }
        dataTypes.add(dataType);
        Map<String, Object> param = null;
        DayChartBean bean = null;
        for (Integer type : dataTypes){
            bean = new DayChartBean();
            param = new HashMap<String, Object>();
            param.put("day", day);
            param.put("preDay", preDay);
            param.put("type", type);
            List<RealTimeStaticDay> data = realTimeStaticDayMapper.findDayData(param);
            List<Double> dataCount = dayConvert(data,timeList);
            bean.setUnit(CommonUtil.initUnit(type));
            bean.setName(CommonUtil.initName(type));
            bean.setData(dataCount);
            dayData.add(bean);
        }
        dataMap.put("dataList", dayData);
        dataMap.put("time", timeList);
    }
    private List<Object> weekConvert(List<RealtimeStaticWeek> weekDatas,List<String> times) {
        List<Object> dataList = new ArrayList<>();
        Map<String, Long> initMap = new HashMap<String,Long>();
        if(weekDatas!=null && !weekDatas.isEmpty()){
            for(RealtimeStaticWeek weekData:weekDatas){
                if(!"".equals(weekData.getIrslDate()) && weekData.getDataCount()!=null){
                    initMap.put(weekData.getIrslDate(), weekData.getDataCount());
                    times.add(weekData.getIrslDate());
                }else {
                    logger.error("查询周表数据:数据时间:"+weekData.getDataDate()+"数据类型:"+weekData.getDataType() + "-- 数据为空。");
                }
            }
        }
        if(times != null && times.size()>0){
            for (String time : times) {
                dataList.add(initMap.get(time));
            }
        }
        return dataList;
    }
    //对周度数据处理并且补漏数据
    private List<Object> weekConvertnew(List<RealtimeStaticDoubleWeek> weekDatas,
                                        List<String> times) {
        List<Object> dataList = new ArrayList<>();
        if (times != null && times.size()>0 && !times.isEmpty() ){
            times.clear();
        }

        /*Map<String,Object> map = new HashedMap();
        if (times == null || times.isEmpty()){
            return  null;
        }
        for (String time: times
                ) {
            map.put(time,0);
        }*/

        String temp = null;
        //initYearWeekTimeSign
        if(weekDatas!=null && !weekDatas.isEmpty()){
            for (RealtimeStaticDoubleWeek realtimeStaticDoubleWeek:weekDatas
                 ) {
               temp =  CommonUtil.initYearWeekTimeSign(realtimeStaticDoubleWeek.getIrslDate());
               dataList.add(realtimeStaticDoubleWeek.getDataCount());
               times.add(temp);

            }

        }
        return dataList;
    }
    /*leads来源数独有数据补零*/
    private List<Object> weekConvertBZ(Map<String, Object> weekMap, List<RealtimeStaticWeek> weekDatas, List<String> weekTimes, Integer type) throws Exception {
        //每次处理一个类型就清空比对map
        List<Object> dataList = new ArrayList<>();
        if(weekDatas!=null && !weekDatas.isEmpty()){
            for(RealtimeStaticWeek weekData:weekDatas){
                if(!"".equals(weekData.getIrslDate()) && weekData.getDataCount()!=null){
                    weekMap.put(weekData.getIrslDate(), weekData.getDataCount());
                }else {
                    logger.error("查询周表数据:数据时间:"+weekData.getDataDate()+"数据类型:"+weekData.getDataType() + "-- 数据为空。");
                }
            }
        }
        if(weekTimes != null && weekTimes.size()>0){
            for (String time : weekTimes) {
                dataList.add(weekMap.get(time));
            }
        }
        return dataList;
    }
    private List<Object> monthConvert(List<RealtimeStaticMonth> monthDatas,List<String> times) {
        DecimalFormat df = new DecimalFormat("0.00");
        List<Object> dataList = new ArrayList<>();
        Map<String, Double> initMap = new HashMap<String,Double>();
        if(monthDatas!=null && !monthDatas.isEmpty()){
            for(RealtimeStaticMonth monthData:monthDatas){
                if(!"".equals(monthData.getIrslDate()) && monthData.getDataCount()!=null){
                    if(monthData.getDataType()==DataType.LEADSTRANSFORM.getType().intValue()){//leads转化率保留两位小数
                        initMap.put(monthData.getIrslDate(), Double.valueOf(df.format(monthData.getDataCount())));
                    }else {
                        initMap.put(monthData.getIrslDate(), monthData.getDataCount());
                    }
                    times.add(monthData.getIrslDate());
                }else {
                    logger.error("查询月表数据:数据时间:"+monthData.getDataDate()+"数据类型:"+monthData.getDataType() + "-- 数据为空。");
                }
            }
        }
        if(times != null && times.size()>0){
            for (String time : times) {
                dataList.add(initMap.get(time));
            }

        }

        return dataList;
    }
    private List<Object> monthConvertBZ(Map<String, Object> monthMap, List<RealtimeStaticMonth> monthDatas, List<String> monthTimes, Integer type) {
        List<Object> dataList = new ArrayList<>();
        if(monthDatas!=null && !monthDatas.isEmpty()){
            for(RealtimeStaticMonth monthData:monthDatas){
                if(!"".equals(monthData.getIrslDate()) && monthData.getDataCount()!=null){
                    monthMap.put(monthData.getIrslDate(), monthData.getDataCount());
                }else {
                    logger.error("查询月表数据:数据时间:" + monthData.getDataDate() + "数据类型:" + monthData.getDataType() + "-- 数据为空。");
                }
            }
        }
        if(monthTimes != null && monthTimes.size()>0){
            for (String time : monthTimes) {
                dataList.add(monthMap.get(time));
            }

        }

        return dataList;
    }

    private void getTodayData(String day, Integer dataType,Map<String, Object> dataMap) {
        List<String> timeList = CommonUtil.getTimeShaft();//X轴
        List<HourChartBean> dataList = new ArrayList<HourChartBean>();//小时数据
        /*List<Integer> dataTypes = new ArrayList<Integer>();//数据类型
        if(dataType == DataType.USER3CLASS.getType().intValue()){//3类客户数需查询电销&渠道&全部
            dataTypes.add(DataType.DXUSER3CLASS.getType());
            dataTypes.add(DataType.QDUSER3CLASS.getType());
        }
        dataTypes.add(dataType);*/
        Map<String, Object> param = new HashMap<String, Object>();
        List<String> times = CommonUtil.getTimeShaft(day);
        param.put("day", day);
        param.put("type", dataType);
        List<RealTimeStaticHour> todayData = realtimeStaticHourMapper.findTodayData(param);
        HourChartBean bean = new HourChartBean();
        if (todayData != null && !todayData.isEmpty()) {
            bean = convertHour(todayData, times);
        }
        bean.setName(CommonUtil.initName(dataType));
        bean.setUnit(CommonUtil.initUnit(dataType));
        dataList.add(bean);
        dataMap.put("dataList", dataList);
        dataMap.put("time", timeList);
    }

    //转换小时表数据
    private HourChartBean convertHour(List<RealTimeStaticHour> hourDatas, List<String> times) {
        HourChartBean bean = new HourChartBean();
        Map<String, Object> initMap = CommonUtil.initHourTimeMap(times);//初始化时间
        Map<String,Object> tempMap = new HashMap<>();//用于数据补零,小时数据当前最新数据之前的补零,之后的维持原有""处理
        Integer type = 0;
        if (hourDatas != null && hourDatas.size() > 0) {
            for (RealTimeStaticHour realTimeStaticHour : hourDatas) {
                if (realTimeStaticHour.getDataCount() != null && realTimeStaticHour.getDataType() != null) {
                    type = realTimeStaticHour.getDataType();
                    if (CommonUtil.initArith(type)) {
                        initMap.put(realTimeStaticHour.getIrslDateH(), (realTimeStaticHour.getDataCount().doubleValue() / 10000)); //单位为万
                    } else {
                        initMap.put(realTimeStaticHour.getIrslDateH(), (realTimeStaticHour.getDataCount().doubleValue())); //单位为个/元
                    }
                    tempMap.put("temp",realTimeStaticHour.getIrslDateH());
                } else {
                    logger.error("查询小时表数据:数据时间:"+realTimeStaticHour.getIrslDateH()+"数据类型:"+realTimeStaticHour.getDataType() + "-- 数据为空。");
                }
            }
            List<Object> dataList = new ArrayList<Object>();
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
            bean.setData(dataList);
        } else {
            logger.error("FeeuserServiceImpl.折线图获取小时表数据为空");
        }
        return bean;
    }

    /*转换天表数据*/
    private List<Double> dayConvert(List<RealTimeStaticDay> data,List<String> times) {
        Map<String, Double> initMap = CommonUtil.initDayTimeMap(times);//初始化时间
        List<Double> dataList = new ArrayList<Double>();
        if (data != null && data.size() > 0) {
            for (RealTimeStaticDay realTimeStaticDay : data) {
                if (!"".equals(realTimeStaticDay.getIrslDate()) && realTimeStaticDay.getDataCount() != null) {
                    initMap.put(realTimeStaticDay.getIrslDate(), realTimeStaticDay.getDataCount().doubleValue());
                } else {
                    logger.error("查询天表数据:数据时间:"+realTimeStaticDay.getIrslDate() +"数据类型:"+realTimeStaticDay.getDataType() + "-- 数据为空。");
                }
            }
        }
        if(times!= null && times.size()>0){
            Double timeData = null;
            for (String time : times) {
                timeData = initMap.get(time);
                dataList.add(timeData);
            }
        }
        return dataList;
    }
    /*转换天表数据(Double类型接收)*/
    private List<Object> dayDoubleConvert(List<RealTimeStaticDoubleDay> data, Integer dataType, List<String> times) {
        Map<String, Double> initMap = CommonUtil.initDayTimeMap(times);//初始化时间
        List<Object> dataList = new ArrayList<Object>();
        if (data != null && data.size() > 0) {
            for (RealTimeStaticDoubleDay realTimeStaticDay : data) {
                if (!"".equals(realTimeStaticDay.getIrslDate()) && realTimeStaticDay.getDataCount() != null) {
                    initMap.put(realTimeStaticDay.getIrslDate(), realTimeStaticDay.getDataCount());
                } else {
                    logger.error("查询天表数据:数据时间:" + realTimeStaticDay.getIrslDate() + "数据类型:" + realTimeStaticDay.getDataType() + "-- 数据为空。");
                }
            }
        }
        if(times!= null && times.size()>0){
            Double timeData = null;
            for (String time : times) {
                timeData = initMap.get(time);
                dataList.add(timeData);
            }
        }
        return dataList;
    }


//当年月度数据
  @Override
  public void initBdMonthData(Integer dataType, Map<String, Object> dataMap) throws Exception {
    List<HourChartBean> dataList = new ArrayList<HourChartBean>();
    String year = DateUtil.getYear("yyyy");//获取当前年度
    String month = DateUtil.getMonth("yyyyMM");//获取当前月
    List<Integer> dataTypes = new ArrayList<Integer>();
    List<String> monthTimese = new ArrayList<>();//月度数据时间轴
    List<String> monthTimes = new ArrayList<>();//月度数据时间轴
    HourChartBean bean = null;
    //百度联盟月收入
    dataTypes.add(315);
    dataTypes.add(dataType);
    for(Integer type:dataTypes){
      Map<String, Object> param = new HashMap<String, Object>();
      bean = new HourChartBean();
      List<RealtimeStaticMonth> monthData = null;
      List<Object> dataCount = null;
      if (type == 315)
      {
        param.put("type", type);
        param.put("year",year);
        param.put("month",month);
        bean.setName("月收入预算");
        monthData = realtimeStaticMonthMapper.findYearMonthData(param);
        dataCount = monthConvert(monthData, monthTimese);

      }
      else
      {
        param.put("type", type);
        param.put("year",year);
        bean.setName("月实际收入");
        monthData = realtimeStaticMonthMapper.fingYearMonthData(param);
        dataCount = monthConvert(monthData, monthTimes);

      }
      bean.setUnit("元");
      bean.setData(dataCount);
      dataList.add(bean);
    }
      List<String> time = null;

      if(monthTimese.size() > monthTimes.size()){
          time = CommonUtil.initYearMonthTime(monthTimese);
      }else{
          time = CommonUtil.initYearMonthTime(monthTimes);
      }
     dataMap.put("dataList", dataList);
      dataMap.put("time", time);
  }

  //往前推N个月，月度数据
  @Override
  public void initDueMonthData(Integer dataType, Map<String, Object> dataMap) throws Exception {
    List<HourChartBean> dataList = new ArrayList<HourChartBean>();
    String year = DateUtil.getYear("yyyy");//获取当前年度
    String month = DateUtil.plusDays("yyyyMM",-1);//获取当前月(前一天)
    //String month = DateUtil.getMonth("yyyyMM");//获取当前月(前一天)
    List<Integer> dataTypes = new ArrayList<Integer>();
    List<String> monthTimese = new ArrayList<>();//月度数据时间轴
    List<String> monthTimes = new ArrayList<>();//月度数据时间轴
    HourChartBean bean = null;
    //百度联盟月收入
    dataTypes.add(315);
    dataTypes.add(dataType);
    for(Integer type:dataTypes){
      Map<String, Object> param = new HashMap<String, Object>();
      bean = new HourChartBean();
      List<RealtimeStaticMonth> monthData = null;
      List<Object> dataCount = null;
      if (type == 315)
      {
        param.put("type", type);
        param.put("month",month);
        param.put("months",12);
        bean.setName("月收入预算");
        monthData = realtimeStaticMonthMapper.findLastNMonthsData(param);
        dataCount = monthConvert(monthData, monthTimese);

      }
      else
      {
        param.put("type", type);
        param.put("month",month);
        param.put("months",12);
        bean.setName("月实际收入");
        monthData = realtimeStaticMonthMapper.findLastNMonthsData(param);
        dataCount = monthConvert(monthData, monthTimes);

      }
      bean.setUnit("元");
      bean.setData(dataCount);
      dataList.add(bean);
    }
    List<String> time = null;

    if(monthTimese.size() > monthTimes.size()){
      time = CommonUtil.initYearMonthTime(monthTimese);
    }else{
      time = CommonUtil.initYearMonthTime(monthTimes);
    }
    dataMap.put("dataList", dataList);
    dataMap.put("time", time);
  }

  //对周处理
  public List<String> getWeeks(List<RealTimeMonthWeekBase> list){
      List<String> weeks = new ArrayList<>();
      String begin = list.get(0).getDay();
      String end = list.get(0).getWeekNum();




      return weeks;
  }



}
