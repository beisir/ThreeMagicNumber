package com.hc360.dataweb.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by home on 2017/2/20.
 */
public class ControllerDateUtil {

    public static String getYesterday(){
        String todayHour = DateUtil.getNow("HH");
        String yesterDay = null;
        if("00".equals(todayHour) || "01".equals(todayHour)){
            yesterDay = DateUtil.plusDays("yyyyMMdd", -2);//如果当前时间是凌晨，则取 前天的日期
        }else{
            yesterDay = DateUtil.getYesterday("yyyyMMdd");
        }

        return yesterDay;
    }
    /*Leads转化率与续签率(凌晨4点之前查询前天的数据)*/
    public static String getLeadsYesterday(){
        String todayHour = DateUtil.getNow("HH");
        String yesterDay = null;
        if("00".equals(todayHour) || "01".equals(todayHour) || "02".equals(todayHour) || "03".equals(todayHour) || "04".equals(todayHour)){
            yesterDay = DateUtil.plusDays("yyyyMMdd", -2);
        }else{
            yesterDay = DateUtil.plusDays("yyyyMMdd", -1);
        }

        return yesterDay;
    }
    public static String getLeadsBeforeYesterday(){
        String todayHour = DateUtil.getNow("HH");
        String yesterDay = null;
        if("00".equals(todayHour) || "01".equals(todayHour) || "02".equals(todayHour) || "03".equals(todayHour) || "04".equals(todayHour)){
            yesterDay = DateUtil.plusDays("yyyyMMdd", -3);
        }else{
            yesterDay = DateUtil.plusDays("yyyyMMdd", -2);
        }

        return yesterDay;
    }

    public static String getToday(){
        String todayHour = DateUtil.getNow("HH");
        String day =null;// 当天数据
        if("00".equals(todayHour) || "01".equals(todayHour)){
            day = DateUtil.getYesterday("yyyyMMdd");//如果当前时间是凌晨，则取昨天的数据
        }else{
            day =  DateUtil.getNow("yyyyMMdd");
        }
        return day;
    }
    // 前n天的数据
    private static final int PREDAYNUM = -59;//-14-->修改为-59
    public static String getPre60Day(){
        String todayHour = DateUtil.getNow("HH");
        String day =null;// 当天数据
        if("00".equals(todayHour) || "01".equals(todayHour)){
            day = DateUtil.plusDays("yyyyMMdd", PREDAYNUM-1);//如果当前时间是凌晨，则取昨天的数据
        }else{
            day =  DateUtil.plusDays("yyyyMMdd", PREDAYNUM);
        }
        return day;
    }

    public static int getPreDayNum(){
        String todayHour = DateUtil.getNow("HH");
        int dayNum =0;// 当天数据
        if("00".equals(todayHour)|| "01".equals(todayHour)){
            dayNum= PREDAYNUM-1;//如果当前时间是凌晨，则取昨天的数据
        }else{
            dayNum=PREDAYNUM;
        }
        return dayNum;
    }
    //如果当前时间是凌晨，则取昨天的数据
    public static int getPreDayNum(int day){
        String todayHour = DateUtil.getNow("HH");
        int dayNum =0;// 当天数据
        if("00".equals(todayHour)|| "01".equals(todayHour)){
            dayNum= day-1;//如果当前时间是凌晨，则取昨天的数据
        }else{
            dayNum=day;
        }
        return dayNum;
    }
    /**
     * 获取前N天的数据
     * @param dayParam  -6:七天前  -30 三十天前
     * @return
     */
    public static String getPreNDay(int dayParam){
        //dayParam = -(dayParam - 1);
        String todayHour = DateUtil.getNow("HH");
        String day =null;// 当天数据
        if("00".equals(todayHour) || "01".equals(todayHour)){
            day = DateUtil.plusDays("yyyyMMdd", dayParam-1);//如果当前时间是凌晨，则取昨天的数据
        }else{
            day =  DateUtil.plusDays("yyyyMMdd", dayParam);
        }
        return day;
    }

    public static final void main(String[] args){
        String leadsDay = ControllerDateUtil.getLeadsYesterday();//取leads转化率与续签率的时间

        System.out.println(leadsDay.substring(0,6));
    }
}
