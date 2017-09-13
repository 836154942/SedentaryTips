package com.spc.sedentary.tips.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by spc on 2017/9/11.
 */

public class TimeUtils {
    //13位置时间戳 算开始时间和 结束时间的间隔
    public static String countdownTime(long startMilli, long endMilli) {
        long l = (startMilli - endMilli) / 1000;
        long day = l / (24 * 60 * 60);
        long hour = (l / (60 * 60) - day * 24);
        long min = ((l / (60)) - day * 24 * 60 - hour * 60);
        long s = (l - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day > 1L) {
            return day + "天" + hour + "小时" + min + "分" + s + "秒";
        }
        return hour + "小时" + min + "分" + s + "秒";
    }

    public static String getDays(long startMilli, long endMilli) {
        long l = (startMilli - endMilli) / 1000;
        long day = l / (24 * 60 * 60);
        long hour = (l / (60 * 60) - day * 24);
        long min = ((l / (60)) - day * 24 * 60 - hour * 60);
        long s = (l - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day > 1L) {
            return day + "";
        }
        return "1";
    }

    public static Date rmarkStingTODate(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            TLog.e(e.getMessage());
        }
        return null;
    }

    public static String getYYMMDD(String timeMilli) {//可根据需要自行截取数据显示
        Date date = new Date(Long.parseLong(timeMilli));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
