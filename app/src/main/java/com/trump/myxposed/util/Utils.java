package com.trump.myxposed.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import com.socks.library.KLog;
import com.trump.myxposed.Constant;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

import java.util.List;


/**
 * Author: TRUMP
 * Date:   2022/2/10 0010 16:00
 * Desc:
 */
public class Utils {

    public static String getProcessName(Context context) {
        ActivityManager actMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appList = actMgr.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : appList) {
            if (info.pid == android.os.Process.myPid()) {
                return info.processName;
            }
        }
        return "unknown";
    }

    public static void log(String msg) {
        msg = "TRUMP====> " + msg;
        KLog.d(msg);
        XposedBridge.log(msg);
    }

    /**
     * 判断Launcher有没有隐藏
     *
     * @param activity
     * @return
     */
    public static boolean isLauncherIconVisible(Activity activity) {
        ComponentName component = new ComponentName(activity, Constant.activityNameAlias);
        PackageManager manager = activity.getPackageManager();
        Intent intent = new Intent().setComponent(component);
        List<ResolveInfo> list;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            list = manager.queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY));
        }
        else {
            list = manager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        }
        return !list.isEmpty();
    }


    /**
     * 设置隐藏与显示
     *
     * @param visible
     */
    public static void setLauncherIconVisible(Activity activity, boolean visible) {
        if (isLauncherIconVisible(activity) == visible) return;
        ComponentName component = new ComponentName(activity, Constant.activityNameAlias);
        PackageManager manager = activity.getPackageManager();
        int newState = visible ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        manager.setComponentEnabledSetting(component, newState, PackageManager.DONT_KILL_APP);
    }

    /**
     * 利用 Reflection 获取当前的系统 Context-- 只能用在xp环境中
     */
    public static Context getSystemContextInXp() {
        Class<?> activityThreadClass = XposedHelpers.findClass("android.app.ActivityThread", null);
        Object activityThread = XposedHelpers.callStaticMethod(activityThreadClass, "currentActivityThread");
        Context context = (Context) XposedHelpers.callMethod(activityThread, "getSystemContext");
        if (context == null) {
            log("getSystemContext is null");
        }
        return context;
    }

    /**
     * 获取包名对应的版本号
     *
     * @param packageName
     * @return
     */
    public static String getPackageVersionNameInXp(String packageName) {
        try {
            PackageManager packageManager = getSystemContextInXp().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
