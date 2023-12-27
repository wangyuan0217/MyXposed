package com.trump.myxposed.hook;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.view.View;

import com.trump.myxposed.Constant;
import com.trump.myxposed.util.XSpUtil;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: LINMP4
 * Date:   2023/12/27 0011 10:35
 * Desc:   微博国际版
 * Functions :
 * 1.微博视频允许下载
 */
public class WeicoHook extends AbsHook {

    private ArrayList<String> currFunctionNames;

    private static HashMap<String, ArrayList<String>> currFunctionNamesMap = new HashMap<String, ArrayList<String>>() {{
        put("6.1.7", new ArrayList<String>() {{
            add("queryUveAdRequest$lambda$156");
            add("queryUveAdRequest$lambda$157");
            add("queryUveAdRequest$lambda$158");
        }});
        put("6.2.6", new ArrayList<String>() {{
            add("queryUveAdRequest$lambda$151");
            add("queryUveAdRequest$lambda$152");
            add("queryUveAdRequest$lambda$153");
        }});
    }};

    @Override
    void onHandleLoadPackage(String versionName, ClassLoader classLoader, XC_LoadPackage.LoadPackageParam lpparam) {
        currFunctionNames = currFunctionNamesMap.get(versionName);
        log("WeicoHook hook start version = " + versionName);

        removeSpalshAd(classLoader);

        removeTimeLineAd(classLoader);

        getDownloadAble(classLoader);

        boolean flagDarkMode = XSpUtil.getBoolean(true, Constant.SpKey.darkMode);
        log("weico hook flagDarkMode = " + flagDarkMode);
        if (flagDarkMode) {
            forceDarkMode(classLoader);
        }

        boolean hidePostBtn = XSpUtil.getBoolean(true, Constant.SpKey.hidePostBtn);
        log("weico hook hidePostBtn = " + hidePostBtn);
        if (hidePostBtn) {
            hideIndexPostBtn(classLoader);
        }
    }

    private void getDownloadAble(ClassLoader classLoader) {
        try {
            XposedHelpers.findAndHookMethod("com.weico.international.data.VideoModalOTO", classLoader, "getDownloadAble", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(true);
                }
            });
        } catch (Exception e) {
            log("weico hook getDownloadAble exception = " + e.getMessage());
        }
    }

    private void removeSpalshAd(ClassLoader classLoader) {
        try {
            //劫持oncreate直接转入主界面，未登录需要先登录
            XposedHelpers.findAndHookMethod("com.weico.international.activity.LogoActivity", classLoader, "onCreate", android.os.Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    final Activity mActivity = (Activity) param.thisObject;
                    //mActivity.getWindow().getDecorView().setBackgroundColor(Color.parseColor("#1c1c1e"));
                    XposedHelpers.callMethod(param.thisObject, "initMainTabActivity");
                }
            });

            XposedHelpers.findAndHookMethod("com.weico.international.activity.LogoActivity", classLoader, "doWhatNext", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult("main");
                    log("weico hook doWhatNext:main");
                }
            });

            XposedHelpers.findAndHookMethod("com.weico.international.activity.LogoActivity", classLoader, "triggerPermission", boolean.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.args[0] = true;
                }
            });

            //劫持整个生命周期，“后台到前台”动作失效，不会跳新界面
            XposedHelpers.findAndHookMethod("com.weico.international.manager.ProcessMonitor", classLoader, "attach", Application.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    log("weico hook attach");
                    return null;
                }
            });
        } catch (Exception e) {
            log("weico hook removeSpalshAd exception = " + e.getMessage());
        }
    }

    private void removeTimeLineAd(ClassLoader classLoader) {
        try {
            //6.2.6以后queryUveAdRequest不变
            XposedHelpers.findAndHookMethod("com.weico.international.api.RxApiKt", classLoader, "queryUveAdRequest$lambda$151", java.util.Map.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult("");
                }
            });


            Class Function1 = XposedHelpers.findClass("kotlin.jvm.functions.Function1", classLoader);

            XposedHelpers.findAndHookMethod("com.weico.international.api.RxApiKt", classLoader, "queryUveAdRequest$lambda$152", Function1, java.lang.Object.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult("");
                }
            });


            XposedHelpers.findAndHookMethod("com.weico.international.api.RxApiKt", classLoader, "queryUveAdRequest$lambda$153", Function1, java.lang.Object.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(new ArrayList<>());
                }
            });


            Class Status = XposedHelpers.findClass("com.weico.international.model.sina.Status", classLoader);
            XposedHelpers.findAndHookMethod("com.weico.international.utility.KotlinExtendKt", classLoader, "isWeiboUVEAd", Status, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(false);
                }
            });

            XposedHelpers.findAndHookMethod("com.weico.international.utility.KotlinExtendKt", classLoader, "isVideoLogEnable", Status, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(false);
                }
            });

            Class PageInfo = XposedHelpers.findClass("com.weico.international.model.sina.PageInfo", classLoader);
            XposedHelpers.findAndHookMethod("com.weico.international.utility.KotlinUtilKt", classLoader, "findUVEAd", PageInfo, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(null);
                }
            });

            XposedHelpers.findAndHookMethod("com.weico.international.activity.v4.Setting", classLoader, "loadBoolean", java.lang.String.class, boolean.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    String p0 = (String) param.args[0];
                    if ("BOOL_UVE_FEED_AD".equals(p0)) {
                        param.setResult(false);
                    } else if (p0.startsWith("BOOL_AD_ACTIVITY_BLOCK_")) {
                        param.setResult(true);
                    }
                }

            });

            XposedHelpers.findAndHookMethod("com.weico.international.activity.v4.Setting", classLoader, "loadInt", java.lang.String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    String p0 = (String) param.args[0];
                    if ("ad_interval".equals(p0)) {
                        param.setResult(Integer.MAX_VALUE);
                    } else if ("display_ad".equals(p0)) {
                        param.setResult(0);
                    }
                }
            });

            XposedHelpers.findAndHookMethod("com.weico.international.activity.v4.Setting", classLoader, "loadInt", java.lang.String.class, int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    String p0 = (String) param.args[0];
                    if ("ad_interval".equals(p0)) {
                        param.setResult(Integer.MAX_VALUE);
                    } else if ("display_ad".equals(p0)) {
                        param.setResult(0);
                    }
                }
            });

            XposedHelpers.findAndHookMethod("com.weico.international.activity.v4.Setting", classLoader, "loadStringSet", java.lang.String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    String p0 = (String) param.args[0];
                    if ("CYT_DAYS".equals(p0)) {
                        Set<String> set = new HashSet<>();
                        param.setResult(set);
                    }
                }
            });

            XposedHelpers.findAndHookMethod("com.weico.international.activity.v4.Setting", classLoader, "loadString", java.lang.String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    String p0 = (String) param.args[0];
                    if ("video_ad".equals(p0)) {
                        param.setResult("");
                    }
                }
            });
            XposedHelpers.findAndHookMethod("com.weico.international.activity.v4.Setting", classLoader, "loadString", java.lang.String.class, java.lang.String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    String p0 = (String) param.args[0];
                    if ("video_ad".equals(p0)) {
                        param.setResult("");
                    }
                }
            });
        } catch (Exception e) {
            log("weico hook removeTimeLineAd exception = " + e.getMessage());
        }
    }

    private void forceDarkMode(ClassLoader classLoader) {
        try {
            XposedHelpers.findAndHookMethod("com.skin.loader.SkinManager", classLoader, "isDarkMode", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(true);
                }
            });

            XposedHelpers.findAndHookMethod("com.skin.loader.SkinManager", classLoader, "getDarkModeStatus", android.content.Context.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(true);
                }
            });
        } catch (Exception e) {
            log("weico hook forceDarkMode exception = " + e.getMessage());
        }
    }

    private void hideIndexPostBtn(ClassLoader classLoader) {
        try {
            XposedHelpers.findAndHookMethod("com.google.android.material.floatingactionbutton.FloatingActionButton", classLoader, "setVisibility", int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.args[0] = View.GONE;
                }
            });

            Class indexFragment = null;
            try {
                indexFragment = classLoader.loadClass("com.weico.international.ui.maintab.MainTabFragment");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (indexFragment == null) return;
            Class finalIndexFragment = indexFragment;
            XposedHelpers.findAndHookMethod(indexFragment, "initView", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Field field = XposedHelpers.findField(finalIndexFragment, "mIndexFab");
                    View view = (View) field.get(param.thisObject);
                    view.setVisibility(View.INVISIBLE);
                }
            });
        } catch (Exception e) {
            log("weico hook hideIndexPostBtn exception = " + e.getMessage());
        }
    }

}
