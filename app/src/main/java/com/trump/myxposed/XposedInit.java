package com.trump.myxposed;

import com.socks.library.KLog;
import com.trump.myxposed.hook.WeicoHook;
import com.trump.myxposed.util.XSpUtil;

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
        KLog.d("trump package = " + lpparam.packageName);
        KLog.d("weico ttt = " + XSpUtil.getIntance().getBoolean(Constant.SpKey.weicoHomeAddButton, false));

        switch (lpparam.packageName) {
            case "com.trump.sample":
                //testHookSampleFunReturnString(lpparam);
                break;
            case Constant.PackageIds.wechat:
                break;
            case Constant.PackageIds.weico:
                new WeicoHook().handleLoadPackage(lpparam);
                break;
        }

    }

    private void testHookSampleFunReturnString(XC_LoadPackage.LoadPackageParam lpparam) {
        KLog.d("trump hook in :" + lpparam.packageName);

        XposedHelpers.findAndHookMethod("com.trump.home.HomeFragment", lpparam.classLoader, "getText", String.class, new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                KLog.d("trump hook in beforeHookedMethod");
                super.beforeHookedMethod(param);
                KLog.d("trump hook in beforeHookedMethod2");

                //第一个参数
                String str1 = (String) param.args[0];

                KLog.d("trump com.trump.home.HomeFragment.getText() 的入参为：" + str1);


                //修改参数
                param.args[0] = "samuel";
            }

            // 对方法执行后进行hook
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                KLog.d("trump hook in afterHookedMethod");
                super.afterHookedMethod(param);
                KLog.d("trump hook in afterHookedMethod2");

                //返回值
                String resultStr = (String) param.getResult();
                KLog.d("trump com.trump.home.HomeFragment.getText() 的返回值为：" + resultStr);

                //param.setResult("Hooked2");

                KLog.d("trump Hooked");
            }
        });
    }

}
