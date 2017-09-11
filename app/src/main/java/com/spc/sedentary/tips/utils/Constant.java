package com.spc.sedentary.tips.utils;

/**
 * Created by spc on 2017/9/9.
 */

public class Constant {
    public static int TIME_THRESHOLD = 20 * 1000;// 比对是否到达提醒时间的阈值ms
    public static final String SP_KEY_START_TIME = "sp_key_start_time";//开始时间 ms
    public static final String SP_KEY_TIME_DURING = "sp_key_time_during";//隔多少时间休息ms
    public static final String SP_KEY_IS_STARTED = "sp_key_is_started";//是否正在统计
    public static final String SP_KEY_IS_TIME_INTERVAL = "sp_key_is_time_interval";// 轮询时间 min
    public static final String SP_KEY_SLEEP_TIME = "sp_key_sleep_time";// 休息时间 min
    public static final String SP_KEY_DISPLAY_NAME = "sp_key_display_name";// 休息时间 min


    //轮询时间
    public static int get_time_interval() {
        return Integer.parseInt(SpUtil.getPrefString(SP_KEY_SLEEP_TIME, "1")) * 60 * 1000;
    }

    //每次休息时间
    public static int getSleep_milli_time() {
        return Integer.parseInt(SpUtil.getPrefString(SP_KEY_SLEEP_TIME, "2")) * 60 * 1000;
    }
}
