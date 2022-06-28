package com.trump.myxposed;

import android.util.Log;

import com.socks.library.KLog;
import com.trump.myxposed.hook.TestHook;
import com.trump.myxposed.hook.WeicoHook;
import com.trump.myxposed.util.Utils;

import java.util.Map;

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
            case "com.trump.sample":
                //testHookSampleFunReturnString(lpparam);
                break;
            case Constant.PackageIds.wechat:
                break;
            case Constant.PackageIds.weico:
                new WeicoHook().handleLoadPackage(lpparam);
                break;
            case "com.everysing.lysn":
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


                        //打印堆栈
                        // 函数调用完成之后打印堆栈调用的信息
                        // 方法一:
                        printStackThrowable();
                        // 方法二:
                        new Exception().printStackTrace(); // 直接干脆
                        // 方法三:
                        Thread.dumpStack(); // 直接暴力
                        // 方法四:
                        // 打印调用堆栈: http://blog.csdn.net/jk38687587/article/details/51752436
                        RuntimeException e = new RuntimeException("<Start dump Stack !>");
                        e.fillInStackTrace();
                        Log.i("<Dump Stack>:", "++++++++++++", e);
                        // 方法五:
                        printStackThreadAllStackTraces();
                    }
                });
    }

    private void printStackThrowable() {
        Log.i("Dump Stack: ", "---------------start----------------");
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        for (int i = 0; i < stackElements.length; i++) {
            Log.i("Dump Stack" + i + ": ", stackElements[i].getClassName()
                    + "----" + stackElements[i].getFileName()
                    + "----" + stackElements[i].getLineNumber()
                    + "----" + stackElements[i].getMethodName());
        }
        Log.i("Dump Stack: ", "---------------over----------------");
    }

    private void printStackThreadAllStackTraces() {
        // Thread类的getAllStackTraces（）方法获取虚拟机中所有线程的StackTraceElement对象，可以查看堆栈
        for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {
            Thread thread = stackTrace.getKey();
            StackTraceElement[] stack = stackTrace.getValue();

            // 进行过滤
            if (thread.equals(Thread.currentThread())) continue;

            Log.i("[Dump Stack]", "**********Thread name：" + thread.getName() + "**********");
            int index = 0;
            for (StackTraceElement stackTraceElement : stack) {
                Log.i("[Dump Stack]" + index + ": ", stackTraceElement.getClassName()
                        + "----" + stackTraceElement.getFileName()
                        + "----" + stackTraceElement.getLineNumber()
                        + "----" + stackTraceElement.getMethodName());
            }
            // 增加序列号
            index++;
        }
        Log.i("[Dump Stack]", "********************* over **********************");
    }

}
