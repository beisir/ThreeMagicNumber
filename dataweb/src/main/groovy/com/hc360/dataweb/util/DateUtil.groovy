package com.hc360.dataweb.util

import org.codehaus.groovy.runtime.DateGroovyMethods

/**
 * Created by home on 2016/9/6.
 */
class DateUtil {
    public static String getYesterday(fmt){
        return  DateGroovyMethods.format(new Date().previous(), fmt)
    }
    public static String getNow(fmt){
        return DateGroovyMethods.format(new Date(), fmt)
    }
    public static String getMonth(fmt){
        return DateGroovyMethods.format(new Date(), fmt)
    }
    public static String getYear(fmt){
        return DateGroovyMethods.format(new Date(), fmt)
    }

    public static String plusDays(fmt, int days){
        return DateGroovyMethods.format(new Date().plus(days), fmt)
    }

    public static List<String> getMonthInfo(int monthNum,fmt){
        List<String> months = new ArrayList<String>();
        for(int i=monthNum -1 ;i>= 0; i--){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH , -i);
            months.add(DateGroovyMethods.format(calendar.getTime(), fmt));
        }
        return months;
    }

    public static final void main(String[] args){
        List<String> months = getMonthInfo(7,"yyyyMMWW");
        for(String s : months){
            println(s)
        }
    }
}
