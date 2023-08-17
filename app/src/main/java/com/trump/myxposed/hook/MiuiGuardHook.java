package com.trump.myxposed.hook;

import android.content.Context;

import com.trump.myxposed.Constant;
import com.trump.myxposed.util.XSpUtil;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class MiuiGuardHook extends AbsHook {

    @Override
    void onHandleLoadPackage(ClassLoader classLoader, XC_LoadPackage.LoadPackageParam lpparam) {
        log("MiuiGuardHook hook start");

        boolean swFuckMiuiGuard = XSpUtil.getBoolean(true, Constant.SpKey.swFuckMiuiGuard);
        log("weico hook swFuckMiuiGuard = " + swFuckMiuiGuard);
        if (swFuckMiuiGuard) {
            try {
                XposedHelpers.findAndHookMethod(XposedHelpers.findClass("com.miui.guardprovider.GuardApplication", classLoader), "e", new Object[]{Context.class, new XC_MethodHook() {
                    // from class: cn.fyyr.noguardpls.MainHook.1
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        methodHookParam.setResult(false);
                        log("com.miui.guardprovider:执行拦截！");
                        log("com.miui.guardprovider:已修改 1 次");
                    }
                }});
            } catch (XposedHelpers.ClassNotFoundError unused) {
                log("Class 不存在，版本可能不兼容 --- com.miui.guardprovider --- com.miui.guardprovider.GuardApplication.e");
                return;
            } catch (NoSuchMethodError unused2) {
                log("Method 不存在，版本可能不兼容 --- com.miui.guardprovider --- com.miui.guardprovider.GuardApplication.e");
            }
        }
    }

}
