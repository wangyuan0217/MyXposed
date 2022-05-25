package com.trump.myxposed.hook;

import com.google.gson.Gson;
import com.trump.myxposed.util.Utils;

import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class TestHook extends AbsHook {

    @Override
    void onHandleLoadPackage(ClassLoader classLoader, XC_LoadPackage.LoadPackageParam lpparam) {
        Utils.log("qianxun hook in");
        try {
            XposedHelpers.findAndHookMethod("com.qujing.qianxun.common.utils.DataManager",
                    classLoader, "doPost", String.class, Map.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            //第一个参数
                            String str1 = (String) param.args[0];
                            //第二个参数
                            Map<String, String> map = (Map<String, String>) param.args[1];
                            String json = new Gson().toJson(map);

                            Utils.log("qianxun doPost param  " + str1 + ", " + json);

                            super.afterHookedMethod(param);
                        }
                    });
        } catch (Throwable t) {
            t.printStackTrace();
            Utils.log("qianxun doPost E " + t.getMessage());
        }
        try {
            XposedHelpers.findAndHookMethod("com.qujing.qianxun.common.utils.DataManager",
                    classLoader, "postString", String.class, Map.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            //第一个参数
                            String str1 = (String) param.args[0];
                            //第二个参数
                            Map<String, String> map = (Map<String, String>) param.args[1];
                            String json = new Gson().toJson(map);

                            Utils.log("qianxun postString param  " + str1 + ", " + json);

                            super.afterHookedMethod(param);
                        }
                    });
        } catch (Throwable t) {
            t.printStackTrace();
            Utils.log("qianxun postString E " + t.getMessage());
        }
        try {
            XposedHelpers.findAndHookMethod("com.qujing.qianxun.common.utils.AesUtil",
                    classLoader, "aesEncrypt", String.class, String.class, String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            //第一个参数
                            String str1 = (String) param.args[0];
                            String str2 = (String) param.args[1];
                            String str3 = (String) param.args[2];

                            Utils.log("qianxun aesEncrypt param  " + str1 + ", " + str2 + ", " + str3);

                            super.afterHookedMethod(param);
                        }
                    });
        } catch (Throwable t) {
            t.printStackTrace();
            Utils.log("qianxun doGet E " + t.getMessage());
        }
    }


}
