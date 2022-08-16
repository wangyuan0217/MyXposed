package com.trump.myxposed;

import com.socks.library.KLog;
import com.trump.myxposed.hook.StubHook;
import com.trump.myxposed.hook.TestHook;
import com.trump.myxposed.hook.WeicoHook;
import com.trump.myxposed.util.Utils;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Author: TRUMP
 * Date:   2022/2/10 0010 14:56
 * Desc:
 */
public class XposedInit implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        Utils.log("LoadPackage:" + lpparam.packageName);
        switch (lpparam.packageName) {
            case "加固app":
                new StubHook().handleLoadPackage(lpparam);
                break;
            case Constant.PackageIds.wechat:
                break;
            case Constant.PackageIds.weico:
                new WeicoHook().handleLoadPackage(lpparam);
                break;
            case "com.leozihu.dingk64":
                new TestHook().handleLoadPackage(lpparam);
                break;
        }
    }

    private void example(XC_LoadPackage.LoadPackageParam lpparam) {
        KLog.d("trump hook in :" + lpparam.packageName);
        XposedHelpers.findAndHookMethod("com.trump.home.HomeFragment", lpparam.classLoader,
                "getText", String.class, new XC_MethodHook() {

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
