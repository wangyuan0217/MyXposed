package com.trump.myxposed.hook;

import android.content.Context;

import com.trump.myxposed.util.Utils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class StubHook extends AbsHook {

    @Override
    void onHandleLoadPackage(ClassLoader classLoader, XC_LoadPackage.LoadPackageParam lpparam) {
        Utils.log("StubHook hook in");
        XposedHelpers.findAndHookMethod("com.stub.StubApp", lpparam.classLoader, "a", Context.class, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                ClassLoader classLoader = param.getClass().getClassLoader();
                XposedHelpers.findAndHookMethod("com.bytedance.sdk.openadsdk.TTAdConfig$1", classLoader, "sdkVersionName", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        param.setResult(null);
                    }
                });

                XposedHelpers.findAndHookMethod("com.qq.e.comm.managers.plugin.PM", classLoader, "b", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        param.setResult(null);
                    }
                });

                XposedHelpers.findAndHookMethod("com.kwad.sdk.core.network.BaseResultData", classLoader, "isResultOk", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        param.setResult(null);
                    }
                });

                XposedHelpers.findAndHookMethod("com.jxedt.aloneright.sync.c.a", classLoader, "isHaveSkillRight", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        param.setResult(true);
                    }
                });
            }
        });
    }

}
