package com.trump.myxposed.hook;

import com.socks.library.KLog;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class TestHook extends AbsHook {

    @Override
    void onHandleLoadPackage(String versionName, ClassLoader classLoader, XC_LoadPackage.LoadPackageParam lpparam) {
        log("TestHook start");

        XposedHelpers.findAndHookMethod("com.trump.home.HomeFragment", lpparam.classLoader, "getText", String.class, new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                KLog.d("trump hook in beforeHookedMethod");
                //第一个参数
                String str1 = (String) param.args[0];
                KLog.d("trump com.trump.home.HomeFragment.getText() 的入参为：" + str1);
                //修改参数
                param.args[0] = "samuel";
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                KLog.d("trump hook in afterHookedMethod");

                //返回值
                String resultStr = (String) param.getResult();
                KLog.d("trump com.trump.home.HomeFragment.getText() 的返回值为：" + resultStr);

                //修改返回值
                param.setResult("Hooked2");
            }
        });

    }

}
