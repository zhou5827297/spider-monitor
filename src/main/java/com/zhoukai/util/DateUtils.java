package com.zhoukai.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 日期格式化
 */

public class DateUtils {
    public final static String YMD_FORMAT = "yyyy-MM-dd";
    public final static String YMDHMS_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String parseDate(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } else {
            return null;
        }
    }

    public static Date getCurrentDate() {
        return new Date();
    }


    public static Date parseDate(String str, String pattern) {
        if (str != null && !"".equals(str)) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date d = null;
            try {
                d = sdf.parse(str);
            } catch (ParseException e) {
               // INGORE
            }
            return d;
        } else {
            return null;
        }
    }

    public static String parseString(String str, String originPattern, String targetPattern) {
        Date dateObj = parseDate(str, originPattern);
        if (dateObj != null) {
            return parseDate(dateObj, targetPattern);
        }
        return str;
    }

    /**
     *日期转换，支持ymd ymdhms
     */
    public static Date parseDate(String str) {
        if (str != null && !"".equals(str)) {
            Date d = parseDate(str, YMD_FORMAT);
            if (d == null) {
                d = parseDate(str, YMDHMS_FORMAT);
            }
            return d;
        } else {
            return null;
        }
    }

}
