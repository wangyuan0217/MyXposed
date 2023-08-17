package com.trump.myxposed.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.trump.myxposed.App;
import com.trump.myxposed.BuildConfig;
import com.trump.myxposed.Constant;

import de.robv.android.xposed.XSharedPreferences;

/**
 * Author: TRUMP
 * Date:   2022/2/11 0011 16:48
 * Desc: 只能在Xposed模块的类中使用
 */
public class XSpUtil {

    public static boolean isOk() {
        SharedPreferences sp = getPref(Constant.SpFileName);
        return sp != null;
    }

    public static boolean getBoolean(boolean inHookModule, String key) {
        SharedPreferences sp = inHookModule ? getXPref(Constant.SpFileName) : getPref(Constant.SpFileName);
        if (sp == null) {
            return false;
        }
        return sp.getBoolean(key, false);
    }

    public static void put(String key, Object value) {
        SharedPreferences sp = getPref(Constant.SpFileName);
        if (sp == null) {
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        editor.apply();
    }

    private static SharedPreferences getXPref(String path) {
        XSharedPreferences pref = new XSharedPreferences(BuildConfig.APPLICATION_ID, path);
        pref.makeWorldReadable();
        if (pref.hasFileChanged()) {
            pref.reload();
        }
        return pref.getFile().canRead() ? pref : null;
    }

    private static SharedPreferences getPref(String path) {
        SharedPreferences pref;
        try {
            pref = App.getContext().getSharedPreferences(path, Context.MODE_WORLD_READABLE);
        } catch (SecurityException ignored) {
            // The new XSharedPreferences is not enabled or module's not loading
            pref = null; // other fallback, if any
        }
        return pref;
    }


}
