package com.trump.myxposed.hook;

import android.content.Context;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * VMOS Pro vip hook
 */
public class VmosProHook extends AbsHook {

    @Override
    void onHandleLoadPackage(ClassLoader classLoader, XC_LoadPackage.LoadPackageParam lpparam) {
        log("VmosProHook hook start");

        try {
            XposedHelpers.findAndHookMethod("com.stub.StubApp", lpparam.classLoader,
                    "attachBaseContext", Context.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            //获取到Context对象，通过这个对象来获取classloader
                            Context context = (Context) param.args[0];
                            //获取classloader，之后hook加固后的就使用这个classloader
                            ClassLoader classLoader = context.getClassLoader();
                            //已经绕过360加固取得classloader

                            log("VmosProHook hook start true");

                            XposedHelpers.findAndHookMethod(
                                    "com.vmos.pro.bean.UserBean",
                                    classLoader,
                                    "getNickName",
                                    new XC_MethodHook() {

                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) {
                                            log("VmosProHook hook after getNickName");
                                            param.setResult("Trump");
                                        }
                                    });

                            XposedHelpers.findAndHookMethod(
                                    "com.vmos.pro.bean.UserBean",
                                    classLoader,
                                    "isTasteMember",
                                    new XC_MethodHook() {

                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) {
                                            log("VmosProHook hook after isTasteMember");
                                            param.setResult(true);
                                        }
                                    });

                            XposedHelpers.findAndHookMethod(
                                    "com.vmos.pro.account.AccountHelper",
                                    classLoader,
                                    "isVipVM",
                                    new XC_MethodHook() {

                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) {
                                            log("VmosProHook hook after isVipVM");
                                            param.setResult(true);
                                        }
                                    });

                            XposedHelpers.findAndHookMethod(
                                    "com.vmos.pro.account.AccountHelper",
                                    classLoader,
                                    "permanentMember",
                                    new XC_MethodHook() {

                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) {
                                            param.setResult(true);
                                        }
                                    });

                            XposedHelpers.findAndHookMethod(
                                    "com.vmos.pro.bean.UserBean",
                                    classLoader,
                                    "isExists",
                                    new XC_MethodHook() {

                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) {
                                            param.setResult(true);
                                        }
                                    });

                            XposedHelpers.findAndHookMethod(
                                    "com.vmos.pro.bean.UserBean",
                                    classLoader,
                                    "isAuthorFlag",
                                    new XC_MethodHook() {

                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) {
                                            param.setResult(true);
                                        }
                                    });

                            XposedHelpers.findAndHookMethod(
                                    "com.vmos.pro.account.AccountHelper",
                                    classLoader,
                                    "notLogin",
                                    new XC_MethodHook() {

                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) {
                                            param.setResult(false);
                                        }
                                    });

                            XposedHelpers.findAndHookMethod(
                                    "com.vmos.pro.account.AccountHelper",
                                    classLoader,
                                    "allowedDisplayAd",
                                    new XC_MethodHook() {

                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) {
                                            param.setResult(false);
                                        }
                                    });

                            XposedHelpers.findAndHookMethod(
                                    "com.vmos.pro.account.AccountHelper",
                                    classLoader,
                                    "unlimited",
                                    new XC_MethodHook() {

                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) {
                                            param.setResult(true);
                                        }
                                    });

                        }
                    });
        } catch (Throwable t) {
            t.printStackTrace();
            log("callApplicationCreate jiagu e " + t.getMessage());
        }
    }

}
