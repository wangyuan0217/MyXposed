package com.trump.myxposed.hook;

import com.trump.myxposed.util.Utils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class TestHook extends AbsHook {

    @Override
    void onHandleLoadPackage(ClassLoader classLoader, XC_LoadPackage.LoadPackageParam lpparam) {
        Utils.log("lysn hook in");
        try {
            XposedHelpers.findAndHookMethod("toolancode.FAppProtect", classLoader, "de", java.lang.String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Utils.log("lysn 参数 " + (String) param.args[0]);
                    Utils.log("lysn 结果 " + param.getResult());
                }
            });
        } catch (Throwable t) {
            t.printStackTrace();
            Utils.log("lysn E " + t.getMessage());
        }

    }


}
