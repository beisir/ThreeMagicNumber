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
    public static final void main(String[] args){
        //print DateUtil.plusDays('yyyymmdd',-15);
    }
}
