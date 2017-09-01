package com.hc360.dataweb.util;

import com.hc360.dataweb.common.constants.ChartsConstant;
import com.hc360.dataweb.model.DataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HC360 on 2017/3/6.
 */
public class CommonUtil {
    /**
     * 初始化数据类型的单位
     *
     * @param otherType
     * @return
     */
    public static String initUnit(Integer otherType) {
        String unit;
        if (otherType == DataType.IP.getType().intValue() || otherType == DataType.PV.getType().intValue() || otherType == DataType.UV.getType().intValue()
                || otherType == DataType.INQUIRYCOUNT.getType().intValue() || otherType == DataType.VISCIDITYBUYER.getType().intValue()) {
            unit = "万";
        } else if (otherType == DataType.P4PCONSUMPTION.getType().intValue() || otherType == DataType.P4PCONSUMPTIONTOTAL.getType().intValue()
                || otherType == DataType.DXTURNOVERZL.getType().intValue() || otherType == DataType.QDTURNOVERZL.getType().intValue()
                || otherType == DataType.DXTURNOVERYS.getType().intValue() || otherType == DataType.DXTURNOVEYG.getType().intValue()
                || otherType == DataType.DXTURNOVELJ.getType().intValue()  || otherType == DataType.QDTURNOVERYS.getType().intValue()
                || otherType == DataType.QDTURNOVERYG.getType().intValue() || otherType == DataType.QDTURNOVERLJ.getType().intValue()
                || otherType == DataType.EVERYDAY_INCOME.getType().intValue() || otherType == DataType.P4P_KEY_TOP50_PRICE.getType().intValue()
                || otherType == DataType.BAIDU_LM_DAY.getType().intValue() || otherType == DataType.BAIDU_LM_MONTH.getType().intValue()|| otherType == DataType.BAIDU_LM_MONTH_E.getType().intValue()
                ){
            unit = "元";
        } else if(otherType == DataType.VARIOUS_FAMILIES_ARTICLE.getType().intValue() || otherType == DataType.EVERYDAY_NEW.getType().intValue() || otherType == DataType.EXTERNALSENDDRAFT.getType().intValue()){
            unit = "篇";
        }else if(otherType == DataType.ZNONGUARDEMPLOYEE.getType().intValue() || otherType == DataType.LEADSTRANSFORM.getType().intValue()
                || otherType == DataType.Z_REGISTER_ONLINE.getType().intValue() || otherType == DataType.Z_ALIBABA_CHARGE.getType().intValue()|| otherType == DataType.Z_APPLY_ONLINE.getType().intValue()
                || otherType == DataType.Z_PROCESS.getType().intValue()  || otherType == DataType.Z_ALIBABA_FREE.getType().intValue()
                || otherType == DataType.Z_BAIDU_GRAB.getType().intValue() || otherType == DataType.Z_EXTEND_GRAB.getType().intValue()
                || otherType == DataType.Z_SPECIALWEBSITE_GRAB.getType().intValue()|| otherType == DataType.Z_MANNUL_INPUT.getType().intValue()
                || otherType == DataType.Z_PHONE.getType().intValue()
                ){
            unit = "%";
        }else if(otherType == DataType.PERCAPITAONLINETIME.getType().intValue() || otherType == DataType.LEADS.getType().intValue()|| otherType == DataType.DAUTYPE.getType().intValue() ||
                otherType == DataType.USER3CLASS.getType().intValue() || otherType == DataType.YLEADSCOUNT.getType().intValue() ||otherType == DataType.FEEUSERTRANSACTION.getType().intValue()){
            unit = "";//人均在线时长&leads数&卖家DAU
        }else{
            unit = "个";
        }
        return unit;
    }

    /**
     * 判断数据类型是否需要除法运算
     *
     * @param otherType
     * @return
     */
    public static boolean initArith(Integer otherType) {
        boolean unit = false;
        if (otherType == DataType.IP.getType().intValue() || otherType == DataType.PV.getType().intValue() || otherType == DataType.UV.getType().intValue()
                || otherType == DataType.INQUIRYCOUNT.getType().intValue() || otherType == DataType.VISCIDITYBUYER.getType().intValue()) {
            unit = true;
        }
        return unit;
    }

    /*获取24h时间轴(格式:YYYY MM DD HH)*/
    public static List<String> getTimeShaft(String day) {
        List<String> timeList = new ArrayList<String>();
        for (int i = 0; i < ChartsConstant.SHOWHOUR; i++) {
            if (String.valueOf(i).length() == 1) {
                timeList.add(day + "0" + String.valueOf(i));
            } else {
                timeList.add(day + String.valueOf(i));
            }
        }
        return timeList;
    }

    /*获取24h时间轴(格式:HH)*/
    public static List<String> getTimeShaft() {
        List<String> timeList = new ArrayList<String>();
        for (int i = 0; i < ChartsConstant.SHOWHOUR; i++) {
            if (String.valueOf(i).length() == 1) {
                timeList.add("0" + String.valueOf(i));
            } else {
                timeList.add(String.valueOf(i));
            }
        }
        return timeList;
    }

    //初始化查询的天数
    public static int initTime(String timeFlag) {
        int datCount = 0;
        if (ChartsConstant.WEEK.equals(timeFlag)) {//之前一周
            datCount = ChartsConstant.SHOWWEEK;
        } else if (ChartsConstant.MONTH.equals(timeFlag)) {//之前一月
            datCount = ChartsConstant.SHOWMONTH;
        } else if (ChartsConstant.TOTAL.equals(timeFlag)) {//全部
            datCount = ChartsConstant.SHOWMONTH;
        }else if (ChartsConstant.CURMONTH.equals(timeFlag)) {//当月
            datCount =Integer.parseInt((ControllerDateUtil.getToday()).substring(6,8));
        }
        return datCount;
    }

    /*获取周/月时间轴*/
    public static List<String> getTimeShaftD(int time) {
        List<String> timeList = new ArrayList<String>();
        for (int i = ControllerDateUtil.getPreDayNum(-(time - 1)); i <= 0; i++) {
            if (timeList.size() == time) {
                break;
            }
            timeList.add(DateUtil.plusDays("yyyyMMdd", i)); //时间横坐标
        }
        return timeList;
    }

    public static List<String> initWeekTime(List<String> a) {
        List<String> times = new ArrayList<String>();
        for (int i = 0; i < a.size(); i++) {
            times.add(a.get(i).substring(0, 4) + "年" + a.get(i).substring(5, 6) + "月" + a.get(i).substring(7, 8) + "周");
        }
        return times;
    }
    /*查询其他折线图使用*/
    public static Map<String, Double> initDayTimeMap(List<String> timeList) {
        Map<String, Double> initMap = new HashMap<String, Double>(); // 按照时间初始化参照map数据。
        for (String time : timeList) {
            initMap.put(time, 0d);//赋值为0时,折线图节点为0
        }
        return initMap;
    }
    /*只有查询今日折线图时使用*/
    public static Map<String, Object> initHourTimeMap(List<String> timeList) {
        Map<String, Object> initMap = new HashMap<String, Object>(); // 按照时间初始化参照map数据。
        for (String time : timeList) {
            initMap.put(time, "");//赋值为""空串时页面为折线图不显示
        }
        return initMap;
    }

    /*初始化name*/
    public static String initName(Integer dataType){
        if(dataType == DataType.DXCOVENEMPLOYEE.getType().intValue()){
            return "电销转正人数";
        }else if(dataType == DataType.QDCOVENEMPLOYEE.getType().intValue()){
            return "渠道转正人数";
        }else if(dataType == DataType.ZNONGUARDEMPLOYEE.getType().intValue()){
            return "职能满编率";
        }else if(dataType == DataType.EXTERNALSENDDRAFT.getType().intValue()){
            return "市场部发稿量";
        }else if(dataType == DataType.DXTURNOVERZL.getType().intValue() || dataType == DataType.QDTURNOVERZL.getType().intValue()){
            return "实际销售额";
        }else {
            return DataType.getName(dataType);
        }
    }
    /*根据数据时间list转换年月周*/
    public static List<String> initYearWeekTime(List<String> a) {
        List<String> times = new ArrayList<String>();
        for (int i = 0; i < a.size(); i++) {
            if("0".equals(a.get(i).substring(4,5))){
                times.add(a.get(i).substring(0, 4) + "年" + "第"+a.get(i).substring(5,6) + "周" );
            }else {
                times.add(a.get(i).substring(0, 4) + "年" + "第"+a.get(i).substring(4,6) + "周" );
            }
        }
        return times;
    }
    /*根据数据时间list转换年月*/
    public static List<String> initYearMonthTime(List<String> a) {
        List<String> times = new ArrayList<String>();
        for (int i = 0; i < a.size(); i++) {
            if("0".equals(a.get(i).substring(4,5))){
                times.add(a.get(i).substring(0, 4) + "年" + "第"+a.get(i).substring(5,6) + "月" );
            }else {
                times.add(a.get(i).substring(0, 4) + "年" + "第"+a.get(i).substring(4,6) + "月" );
            }
        }
        return times;
    }
    /**
     * 初始化数据类型是否为包含全部选项的类型
     * @param otherType
     */
    public static Integer initOtherType(Integer otherType) {
        if(otherType.intValue()==DataType.P4PCONSUMPTION.getType().intValue()){//P4P消耗
            otherType = DataType.P4PCONSUMPTIONTOTAL.getType();
        }else if(otherType==DataType.WECHATBINDUSERCOUNT.getType().intValue()){//微信绑定用户数
            otherType = DataType.WECHATBINDUSERTOTAL.getType();
        }else if(otherType==DataType.FANSINCREMENT.getType().intValue()){//粉丝数量
            otherType = DataType.FANSCOUNT.getType();
        }else if(otherType==DataType.USER3CLASS.getType().intValue()){
            otherType = DataType.USER3CLASSTOTAL.getType();
        }
        return otherType;
    }


}
