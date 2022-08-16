package com.trump.myxposed.hook;

import android.content.Context;

import com.trump.myxposed.Constant;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class TestHook extends AbsHook {

    @Override
    void onHandleLoadPackage(ClassLoader classLoader, XC_LoadPackage.LoadPackageParam lpparam) {

        log("hook加固示例");
        XposedHelpers.findAndHookMethod(Constant.StubName.c360, lpparam.classLoader,
                "attachBaseContext", Context.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        ClassLoader classLoader = ((Context) param.args[0]).getClassLoader();
                        //ClassLoader classLoader = param.getClass().getClassLoader();

                        //hook逻辑
                        XposedHelpers.findAndHookMethod("类名", classLoader,
                                "方法名", String.class, String.class,

                                new XC_MethodHook() {
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
