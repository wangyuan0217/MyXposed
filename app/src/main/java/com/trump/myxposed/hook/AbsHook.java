package com.trump.myxposed.hook;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.socks.library.KLog;
import com.trump.myxposed.util.Utils;

import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author: wangpan
 * @emial: p.wang@aftership.com
 * @date: 2021/4/9
 */
abstract class AbsHook {

    protected Application application;
    protected Handler handler;
    protected boolean isJiagu;


    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        log("trump handleLoadPackage");
        callApplicationCreate(lpparam);
    }

    private void callApplicationCreate(XC_LoadPackage.LoadPackageParam lpparam) {
        String packageName = lpparam.packageName;
        log("trump callApplicationCreate " + packageName);
        if (isJiagu) {
            try {
                XposedHelpers.findAndHookMethod("com.stub.StubApp", lpparam.classLoader, "attachBaseContext", Context.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        //获取到Context对象，通过这个对象来获取classloader
                        Context context = (Context) param.args[0];
                        //获取classloader，之后hook加固后的就使用这个classloader
                        ClassLoader classLoader = context.getClassLoader();
                        //已经绕过360加固取得classloader
                        onHandleLoadPackage(classLoader, lpparam);
                    }
                });
            } catch (Throwable t) {
                t.printStackTrace();
                log("trump callApplicationCreate jiagu" + t.getMessage());
            }
        } else {
            try {
                XposedHelpers.findAndHookMethod(Application.class, "onCreate", new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) {
                                application = (Application) param.thisObject;
                                ClassLoader classLoader = application.getClassLoader();
                                handler = new Handler(Looper.getMainLooper());
                                onApplicationCreate(application, classLoader);
                                if (Utils.getProcessName(application).equals(packageName)) {
                                    onMainApplicationCreate(application, classLoader);
                                }
                                onHandleLoadPackage(classLoader, lpparam);
                            }
                        }
                );
            } catch (Throwable t) {
                t.printStackTrace();
                log("trump callApplicationCreate " + t.getMessage());
            }

        }
    }

    abstract void onHandleLoadPackage(ClassLoader classLoader, XC_LoadPackage.LoadPackageParam lpparam);

    protected void onApplicationCreate(Application application, ClassLoader classLoader) {

    }

    protected void onMainApplicationCreate(Application application, ClassLoader classLoader) {

    }

    protected void runOnUIThread(Runnable runnable) {
        handler.post(runnable);
    }

    protected void showToast(String msg) {
        handler.post(() -> Toast.makeText(application, msg, Toast.LENGTH_SHORT).show());
    }

    protected static void log(String msg) {
        Utils.log(msg);
    }

    /**
     * 打印堆栈
     * // 函数调用完成之后打印堆栈调用的信息
     * // 方法一:
     * printStackThrowable();
     * // 方法二:
     * new Exception().printStackTrace(); // 直接干脆
     * // 方法三:
     * Thread.dumpStack(); // 直接暴力
     * // 方法四:
     * // 打印调用堆栈: http://blog.csdn.net/jk38687587/article/details/51752436
     * RuntimeException e = new RuntimeException("<Start dump Stack !>");
     * e.fillInStackTrace();
     * Log.i("<Dump Stack>:", "++++++++++++", e);
     * // 方法五:
     * printStackThreadAllStackTraces();
     */
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