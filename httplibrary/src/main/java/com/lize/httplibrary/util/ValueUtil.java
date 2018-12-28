package com.lize.httplibrary.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValueUtil {

    /**
     * 是否字符串有效
     *
     * @param string
     * @return
     */
    public static boolean isStringValid(String string) {
        if (string == null || string.trim().equals("") || string.trim().equals("null")) {
            return false;
        }
        return true;
    }

    public static <T> boolean isArrayValid(T[] t) {
        if (t != null && t.length != 0) {
            return true;
        }
        return false;
    }

    public static <T> boolean isListValid(List<T> list) {
        if (list != null && list.size() != 0) {
            return true;
        }
        return false;
    }

    public static long[] ListStringToArrayLong(List<String> strings) {
        if (strings == null) {
            return null;
        }
        long[] longs = new long[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            longs[i] = Long.valueOf(strings.get(i));
        }
        return longs;
    }

    public static List<String> ListLongToArray(long[] strings) {
        if (strings == null) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        for (long l : strings) {
            list.add(String.valueOf(l));
        }
        return list;
    }

    public static boolean isIntArrayValid(int[] t) {
        if (t != null && t.length != 0) {
            return true;
        }
        return false;
    }

    public static boolean isUUNum(String strName) {
        if (strName == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\d]{8}");
        Matcher matcher = pattern.matcher(strName);
        return matcher.matches();
    }

    public static boolean isEqualList(List l0, List l1) {
        if (l0 == l1) return true;
        if (l0 == null || l1 == null) return false;
        if (l0.size() != l1.size()) return false;
        for (Object o : l0) {
            if (!l1.contains(o)) return false;
        }
        for (Object o : l1) {
            if (!l0.contains(o)) return false;
        }
        return true;
    }


    /*
     *字符串是否可以转为数字
     */

    public static boolean isInteger(String str) {
        if (!isStringValid(str)){
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
