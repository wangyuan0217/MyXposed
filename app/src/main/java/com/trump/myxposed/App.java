package com.trump.myxposed;

import android.app.Application;

import com.socks.library.KLog;

/**
 * Author: TRUMP
 * Date:   2022/2/10 0010 15:00
 * Desc:   主入口
 */
public class App extends Application {

    private static App instance;

    public static App getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        KLog.init(true);

    }
}
