package com.trump.myxposed.hook;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.trump.myxposed.util.Utils;

import de.robv.android.xposed.XC_MethodHook;
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
        Utils.log("trump handleLoadPackage");
        callApplicationCreate(lpparam);
    }

    private void callApplicationCreate(XC_LoadPackage.LoadPackageParam lpparam) {
        String packageName = lpparam.packageName;
        Utils.log("trump callApplicationCreate " + packageName);
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
                Utils.log("trump callApplicationCreate jiagu" + t.getMessage());
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
                Utils.log("trump callApplicationCreate " + t.getMessage());
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


}