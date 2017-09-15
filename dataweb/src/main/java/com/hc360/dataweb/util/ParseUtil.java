package com.hc360.dataweb.util;

/**
 * Created by HC360 on 2017/3/3.
 */
public class ParseUtil {
    /**
     * 查看一个字符串是否可以转换为数字
     * @param str 字符串
     * @return true 可以; false 不可以
     */
    public static boolean isStr2Num(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {

            return false;

        }
    }
}
