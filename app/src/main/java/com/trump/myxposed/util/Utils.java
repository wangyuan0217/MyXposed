package com.trump.myxposed.util;

import android.app.ActivityManager;
import android.content.Context;

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

}
