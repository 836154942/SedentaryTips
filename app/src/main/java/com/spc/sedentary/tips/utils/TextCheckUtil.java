package com.spc.sedentary.tips.utils;

/**
 * Created by spc on 2017/9/12.
 */

public class TextCheckUtil {
    public static boolean isUseable(String context) {
        if (context == null)
            return false;
        if (context.trim().equals(""))
            return false;
        return true;
    }
}
