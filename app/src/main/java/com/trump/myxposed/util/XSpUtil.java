package com.trump.myxposed.util;

import com.trump.myxposed.Constant;

import de.robv.android.xposed.XSharedPreferences;

/**
 * Author: TRUMP
 * Date:   2022/2/11 0011 16:48
 * Desc: 只能在Xposed模块的类中使用
 */
public class XSpUtil {

    private static XSharedPreferences intance = null;

    public static XSharedPreferences getIntance() {
        if (intance == null) {
            intance = new XSharedPreferences("com.trump.myxposed", Constant.SpFileName);
            intance.makeWorldReadable();
        } else {
            intance.reload();
        }
        return intance;
    }

}
