package com.trump.myxposed.hook;

import android.content.Context;

import com.trump.myxposed.Constant;
import com.trump.myxposed.util.XSpUtil;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class MiuiAnalyticsHook extends AbsHook {

    @Override
    void onHandleLoadPackage(String versionName, ClassLoader classLoader, XC_LoadPackage.LoadPackageParam lpparam) {
        log("MiuiAnalyticsHook hook start");
        boolean swFuckMiuiAnalytics = XSpUtil.getBoolean(true, Constant.SpKey.swFuckMiuiAnalytics);
        log("MiuiAnalyticsHook hook swFuckMiuiAnalytics = " + swFuckMiuiAnalytics);
        if (swFuckMiuiAnalytics) {
            int i;
            try {
                XposedHelpers.findAndHookMethod(XposedHelpers.findClass("com.miui.analytics.onetrack.p.u", classLoader), "w", new Object[]{Long.TYPE, Long.TYPE, new XC_MethodHook() { // from class: cn.fyyr.noguardpls.MainHook.2
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        log("com.miui.analytics:执行拦截！ --- 1");
                        methodHookParam.setResult((Object) null);
                    }
                }});
                i = 1;
            } catch (NoSuchMethodError unused3) {
                log("Method 不存在，版本可能不兼容 --- com.miui.analytics --- com.miui.analytics.onetrack.p.u.w");
                i = 0;
                XposedHelpers.findAndHookMethod(XposedHelpers.findClass("com.miui.analytics.c.f.k", classLoader), "y", new Object[]{Long.TYPE, Long.TYPE, new XC_MethodHook() { // from class: cn.fyyr.noguardpls.MainHook.3
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        log("com.miui.analytics:执行拦截！ --- 2");
                        methodHookParam.setResult((Object) null);
                    }
                }});
                i++;
                Class findClass = XposedHelpers.findClass("com.miui.analytics.onetrack.q.c", classLoader);
                try {
                    XposedHelpers.findAndHookMethod(findClass, "a", new Object[]{Context.class, new XC_MethodHook() { // from class: cn.fyyr.noguardpls.MainHook.4
                        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                            log("com.miui.analytics:执行拦截！ --- 3");
                            methodHookParam.setResult(0);
                        }
                    }});
                    i++;
                } catch (NoSuchMethodError unused4) {
                    log("Method 不存在，版本可能不兼容 --- com.miui.analytics --- com.miui.analytics.onetrack.q.c.a");
                }
                try {
                    XposedHelpers.findAndHookMethod(findClass, "b", new Object[]{Context.class, new XC_MethodHook() { // from class: cn.fyyr.noguardpls.MainHook.5
                        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                            log("com.miui.analytics:执行拦截！ --- 4");
                            methodHookParam.setResult("NONE");
                        }
                    }});
                    i++;
                } catch (NoSuchMethodError unused5) {
                    log("Method 不存在，版本可能不兼容 --- com.miui.analytics --- com.miui.analytics.onetrack.q.c.b");
                }
                try {
                    XposedHelpers.findAndHookMethod(findClass, "c", new Object[]{new XC_MethodHook() { // from class: cn.fyyr.noguardpls.MainHook.6
                        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                            log("com.miui.analytics:执行拦截！ --- 5");
                            methodHookParam.setResult(false);
                        }
                    }});
                    i++;
                } catch (NoSuchMethodError unused6) {
                    log("Method 不存在，版本可能不兼容 --- com.miui.analytics --- com.miui.analytics.onetrack.q.c.c");
                }
                log("com.miui.analytics:已修改 " + i + " 次");
            } catch (XposedHelpers.ClassNotFoundError unused7) {
                log("Class 不存在，版本可能不兼容 --- com.miui.analytics --- com.miui.analytics.onetrack.p.u");
                i = 0;
                XposedHelpers.findAndHookMethod(XposedHelpers.findClass("com.miui.analytics.c.f.k", classLoader), "y", new Object[]{Long.TYPE, Long.TYPE, new XC_MethodHook() { // from class: cn.fyyr.noguardpls.MainHook.3
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        log("com.miui.analytics:执行拦截！ --- 2");
                        methodHookParam.setResult((Object) null);
                    }
                }});
                i++;
                Class findClass2 = XposedHelpers.findClass("com.miui.analytics.onetrack.q.c", classLoader);
                XposedHelpers.findAndHookMethod(findClass2, "a", new Object[]{Context.class, new XC_MethodHook() { // from class: cn.fyyr.noguardpls.MainHook.4
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        log("com.miui.analytics:执行拦截！ --- 3");
                        methodHookParam.setResult(0);
                    }
                }});
                i++;
                XposedHelpers.findAndHookMethod(findClass2, "b", new Object[]{Context.class, new XC_MethodHook() { // from class: cn.fyyr.noguardpls.MainHook.5
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        log("com.miui.analytics:执行拦截！ --- 4");
                        methodHookParam.setResult("NONE");
                    }
                }});
                i++;
                XposedHelpers.findAndHookMethod(findClass2, "c", new Object[]{new XC_MethodHook() { // from class: cn.fyyr.noguardpls.MainHook.6
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        log("com.miui.analytics:执行拦截！ --- 5");
                        methodHookParam.setResult(false);
                    }
                }});
                i++;
                log("com.miui.analytics:已修改 " + i + " 次");
            }
            try {
                XposedHelpers.findAndHookMethod(XposedHelpers.findClass("com.miui.analytics.c.f.k", classLoader), "y", new Object[]{Long.TYPE, Long.TYPE, new XC_MethodHook() { // from class: cn.fyyr.noguardpls.MainHook.3
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        log("com.miui.analytics:执行拦截！ --- 2");
                        methodHookParam.setResult((Object) null);
                    }
                }});
                i++;
            } catch (NoSuchMethodError unused8) {
                log("Method 不存在，版本可能不兼容 --- com.miui.analytics --- com.miui.analytics.c.f.k.y");
            } catch (XposedHelpers.ClassNotFoundError unused9) {
                log("Class 不存在，版本可能不兼容 --- com.miui.analytics --- com.miui.analytics.c.f.k");
            }
            try {
                Class findClass22 = XposedHelpers.findClass("com.miui.analytics.onetrack.q.c", classLoader);
                XposedHelpers.findAndHookMethod(findClass22, "a", new Object[]{Context.class, new XC_MethodHook() { // from class: cn.fyyr.noguardpls.MainHook.4
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        log("com.miui.analytics:执行拦截！ --- 3");
                        methodHookParam.setResult(0);
                    }
                }});
                i++;
                XposedHelpers.findAndHookMethod(findClass22, "b", new Object[]{Context.class, new XC_MethodHook() { // from class: cn.fyyr.noguardpls.MainHook.5
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        log("com.miui.analytics:执行拦截！ --- 4");
                        methodHookParam.setResult("NONE");
                    }
                }});
                i++;
                XposedHelpers.findAndHookMethod(findClass22, "c", new Object[]{new XC_MethodHook() { // from class: cn.fyyr.noguardpls.MainHook.6
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                        log("com.miui.analytics:执行拦截！ --- 5");
                        methodHookParam.setResult(false);
                    }
                }});
                i++;
            } catch (XposedHelpers.ClassNotFoundError unused10) {
                log("Class 不存在，版本可能不兼容 --- com.miui.analytics --- com.miui.analytics.onetrack.q.c");
            }
            log("com.miui.analytics:已修改 " + i + " 次");
        }
    }

}
