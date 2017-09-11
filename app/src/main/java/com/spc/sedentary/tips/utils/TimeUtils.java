package com.spc.sedentary.tips.utils;

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


    public static String getYYMMDD(long timeMilli) {//可根据需要自行截取数据显示
        Date date = new Date(timeMilli);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH时mm分ss");
        SimpleDateFormat format = new SimpleDateFormat("HH时mm分ss");
        return format.format(date);
    }
}
