package com.spc.sedentary.tips.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;

import com.spc.sedentary.tips.base.TipsApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Created by spc on 2017/9/9.
 */

public class SpUtil {

    public static String getPrefString(Context context, String key,
                                       final String defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getString(key, defaultValue);
    }

    public static void setPrefString(Context context, final String key,
                                     final String value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putString(key, value).commit();
    }

    public static boolean getPrefBoolean( final String key,
                                         final boolean defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(TipsApplication.getInst());
        return settings.getBoolean(key, defaultValue);
    }

    public static boolean hasKey(Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(
                key);
    }

    public static void setPrefBoolean(final String key,
                                      final boolean value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(TipsApplication.getInst());
        settings.edit().putBoolean(key, value).commit();
    }

    public static void setPrefInt(Context context, final String key,
                                  final int value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putInt(key, value).commit();
    }


    public static void setSettingLong(final String key,
                                      final long value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(TipsApplication.getInst());
        settings.edit().putLong(key, value).commit();
    }

    public static long getPrefLong(final String key,
                                   final long defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(TipsApplication.getInst());
        return settings.getLong(key, defaultValue);
    }

    public static void clearPreference(Context context, final SharedPreferences p) {
        final SharedPreferences.Editor editor = p.edit();
        editor.clear();
        editor.commit();
    }

    public static boolean putObject(Context context, String key, Object obj) {
        final SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        if (obj == null) {
            SharedPreferences.Editor e = sp.edit();
            e.putString(key, "");
            return e.commit();
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                oos.writeObject(null);
                baos.flush();
                baos.close();
                oos.flush();
                oos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                return false;
            }
            String objectBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor e = sp.edit();
            e.putString(key, objectBase64);
            return e.commit();
        }
    }


    public static Object getObject(Context context, String key, Object defaultValue) {
        final SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        String objectBase64 = sp.getString(key, "");
        if (TextUtils.isEmpty(objectBase64)) {
            return null;
        }
        byte[] base64Bytes = Base64.decode(objectBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
        ObjectInputStream ois;
        try {
            if (bais.equals(null)) {
                return null;
            }
            ois = new ObjectInputStream(bais);
            Object myObject = ois.readObject();
            bais.close();
            ois.close();
            return myObject;
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            return defaultValue;
        } catch (IOException e) {
            e.printStackTrace();
            return defaultValue;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    public static void removeData(String key, Context context) {
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        editor.remove(key);
        editor.commit();
    }
}
