package com.trump.myxposed.hook;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MiuiPersonalAssistantHook extends AbsHook {

    @Override
    void onHandleLoadPackage(String versionName, ClassLoader classLoader, XC_LoadPackage.LoadPackageParam lpparam) {
        log("MiuiPersonalAssistantHook hook start");

//        final Class<?> clazz = XposedHelpers.findClass("c.h.d.p.s", classLoader);
//        XposedHelpers.setStaticBooleanField(clazz, "h", true);

//        XposedHelpers.findAndHookMethod("c.h.d.f.g.b", classLoader, "b", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                param.setResult(true);
//            }
//        });

    }

}
