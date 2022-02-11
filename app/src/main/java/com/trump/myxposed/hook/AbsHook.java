package com.trump.myxposed.hook;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.socks.library.KLog;
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
    protected ClassLoader classLoader;
    protected Handler handler;

    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        KLog.d("trump weico handleLoadPackage");
        onHandleLoadPackage(lpparam);
        callApplicationCreate(lpparam);
    }

    private void callApplicationCreate(XC_LoadPackage.LoadPackageParam lpparam) {

        KLog.d("trump weico callApplicationCreate");
        String packageName = lpparam.packageName;
        try {
            XposedHelpers.findAndHookMethod(Application.class, "onCreate", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            application = (Application) param.thisObject;
                            classLoader = application.getClassLoader();
                            handler = new Handler(Looper.getMainLooper());
                            onApplicationCreate(application, classLoader);
                            if (Utils.getProcessName(application).equals(packageName)) {
                                onMainApplicationCreate(application, classLoader);
                            }
                        }
                    }
            );
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    abstract void onHandleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam);

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