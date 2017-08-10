package com.hc360.dataweb.service.impl;

import com.hc360.dataweb.dao.RealTimeStaticDayMapper;
import com.hc360.dataweb.model.DataType;
import com.hc360.dataweb.model.MainBean;
import com.hc360.dataweb.model.RealTimeStaticDay;
import com.hc360.dataweb.model.RealTimeStaticDoubleDay;
import com.hc360.dataweb.service.RealTimeStaticDayService;
import com.hc360.dataweb.util.CommonUtil;
import com.hc360.dataweb.util.ControllerDateUtil;
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
 * Created by zhangwanru on 2017/2/21.
 */
@Service
public class RealTimeStaticDayServiceImpl implements RealTimeStaticDayService {
    private Logger logger = Logger.getLogger(RealTimeStaticDayServiceImpl.class);
    @Autowired
    private RealTimeStaticDayMapper realTimeStaticDayMapper;

    @Override
    public void initDataList(Map<String, Object> dataList) throws Exception {
        String day = ControllerDateUtil.getToday();//取今天的日期
        String yesterDay = ControllerDateUtil.getYesterday();//取昨天的日期
        List<Integer> dataTypes = new ArrayList<Integer>();//今天
        List<Integer> dataTypes1 = new ArrayList<Integer>();//昨天同一时间
        List<Integer> dataTypes2 = new ArrayList<Integer>();//昨天最后时间
        today(dataTypes);
        meanwhile(dataTypes1);
        lastTime(dataTypes2);

        Map<String,Object> param = new HashMap<String,Object>();
        param.put("list",dataTypes);
        param.put("day",day);
        List<RealTimeStaticDoubleDay> dayData = realTimeStaticDayMapper.findRealTimeDoubleDataToday(param);//查询当前时间的数据

        param.put("list",dataTypes1);
        param.put("yesterDay",yesterDay);
        List<RealTimeStaticDoubleDay> yesterDayData = realTimeStaticDayMapper.findRealTimeDoubleDataYester(param);//查询前一天同一时间的数据

        param.put("list",dataTypes2);
        List<RealTimeStaticDay> yesterDayLastData = realTimeStaticDayMapper.findRealTimeLastDataYester(param);//查询前一天的最后一条数据

        List<MainBean> mainBeans = new ArrayList<MainBean>();
        List<MainBean> mainBeans2 = new ArrayList<MainBean>();
        convertDayData(mainBeans,dayData, day);
        convertDayData(mainBeans2, yesterDayData, day);
        convertDayData2(mainBeans2, yesterDayLastData, day);

        dataList.put("yesterdaydata", mainBeans2);
        dataList.put("todaydata", mainBeans);
    }

    private void meanwhile(List<Integer> dataTypes) {
        dataTypes.add(DataType.PV.getType());
        dataTypes.add(DataType.INQUIRYCOUNT.getType());
        dataTypes.add(DataType.MEMBERREGISTER.getType());
//        dataTypes.add(DataType.WECHATBINDUSERCOUNT.getType());//将微信绑定用户数修改为与前一天同一时间进行对比
        //dataTypes.add(DataType.VISCIDITYBUYER.getType());注释原因:PVUV看板不做粘性买家相关数据了,但后期会做所以后期需将粘性买家独有的部分摘掉
        dataTypes.add(DataType.P4PCONSUMPTION.getType());//545
    }

    private void lastTime(List<Integer> dataTypes2) {
        dataTypes2.add(DataType.FANSCOUNT.getType());//545
    }

    private void today(List<Integer> dataTypes) {
        dataTypes.add(DataType.PV.getType());
        dataTypes.add(DataType.INQUIRYCOUNT.getType());
        dataTypes.add(DataType.MEMBERREGISTER.getType());
       // dataTypes.add(DataType.WECHATBINDUSERCOUNT.getType());//将微信绑定用户数修改为与前一天同一时间进行对比
        //dataTypes.add(DataType.VISCIDITYBUYER.getType());注释原因:PVUV看板不做粘性买家相关数据了,但后期会做所以后期需将粘性买家独有的部分摘掉
//        dataTypes.add(DataType.FANSCOUNT.getType());//545
        dataTypes.add(DataType.P4PCONSUMPTION.getType());//545
    }

    @Override
    public void initIPUVDataList(Map<String, Object> dataTotal) throws Exception {
        String day = ControllerDateUtil.getToday();//取今天的日期
        String yesterDay = ControllerDateUtil.getYesterday();//取昨天的日期
        List<Integer> dataTypes = new ArrayList<Integer>();//PV,询盘量,会员注册
        dataTypes.add(DataType.IP.getType());
        dataTypes.add(DataType.UV.getType());
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("list",dataTypes);
        param.put("day",day);
        List<RealTimeStaticDay> dayData = realTimeStaticDayMapper.findRealTimeDataToday(param);//查询当前时间的数据
        param.put("list",dataTypes);
        param.put("yesterDay",yesterDay);
        param.put("day",day);
        List<RealTimeStaticDay> yesterDayData = realTimeStaticDayMapper.findRealTimeDataYester(param);//查询前一天同一时间的数据

        List<MainBean> mainBeans = new ArrayList<MainBean>();
        convertDayData2(mainBeans, dayData, day);
        List<MainBean> mainBeans2 = new ArrayList<MainBean>();
        convertDayData2(mainBeans2, yesterDayData, day);
        dataTotal.put("yesterdaydata", mainBeans2);
        dataTotal.put("todaydata", mainBeans);
    }

    private void convertDayData(List<MainBean> beans, List<RealTimeStaticDoubleDay> dayData, String time) {
        DecimalFormat threeNumDf = new DecimalFormat(",###");//每三位分隔一下
        DecimalFormat df = new DecimalFormat("#,#00.0#");//分割千分位并保留两位小数
        MainBean bean = null;
        if(dayData != null && dayData.size() > 0){
            for(RealTimeStaticDoubleDay data : dayData){
                bean = new MainBean();
                if (data.getDataType() != null && data.getDataCount() != null){
                    if(data.getDataType().intValue() == DataType.P4PCONSUMPTION.getType()){
                        bean.setName(DataType.getName(data.getDataType()));
                        bean.setNum(df.format(data.getDataCount()));
                    }else{
                        bean.setName(DataType.getName(data.getDataType()));
                        bean.setNum(threeNumDf.format(Math.floor(data.getDataCount())));
                    }
                } else {
                    bean.setName(CommonUtil.initName(data.getDataType()));
                    bean.setNum("0");
                    logger.error("天表:数据时间:" + data.getIrslDate() + "数据类型:" + data.getDataType() + "-- 数据为空。");
                    EmailUtil.warnEveryOne("数据时间:"+time+"---"+DataType.getName(data.getDataType())+"--数据为空。");
                }
                beans.add(bean);
            }
        }
    }
    private void convertDayData2(List<MainBean> beans, List<RealTimeStaticDay> dayData, String time) {
        DecimalFormat threeNumDf = new DecimalFormat(",###");//每三位分隔一下
        DecimalFormat df = new DecimalFormat("#,#00.0#");//分割千分位并保留两位小数
        MainBean bean = null;
        if(dayData != null && dayData.size() > 0){
            for(RealTimeStaticDay data : dayData){
                bean = new MainBean();
                if (data.getDataType() != null && data.getDataCount() != null){
                    if(data.getDataType().intValue() == DataType.P4PCONSUMPTION.getType()){
                        bean.setName(DataType.getName(data.getDataType()));
                        bean.setNum(df.format(data.getDataCount()));
                    }else{
                        bean.setName(DataType.getName(data.getDataType()));
                        bean.setNum(threeNumDf.format(Math.floor(data.getDataCount())));
                    }
                } else {
                    bean.setName(CommonUtil.initName(data.getDataType()));
                    bean.setNum("0");
                    logger.error("天表:数据时间:" + data.getIrslDate() + "数据类型:" + data.getDataType() + "-- 数据为空。");
                    EmailUtil.warnEveryOne("数据时间:"+time+"---"+DataType.getName(data.getDataType())+"--数据为空。");
                }
                beans.add(bean);
            }
        }
    }
}
