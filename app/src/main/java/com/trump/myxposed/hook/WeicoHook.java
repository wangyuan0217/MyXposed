package com.trump.myxposed.hook;

import android.view.View;

import com.socks.library.KLog;
import com.trump.myxposed.Constant;
import com.trump.myxposed.util.XSpUtil;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Author: TRUMP
 * Date:   2022/2/11 0011 10:35
 * Desc:   微博国际版
 * Functions :
 * 1.隐藏首页右下角加号按钮
 */
public class WeicoHook extends AbsHook {

    @Override
    void onHandleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        KLog.d("trump hook in");

        Class indexFragment;
        try {
            indexFragment = lpparam.classLoader.loadClass("com.weico.international.ui.indexv2.IndexV2Fragment");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        if (indexFragment == null) return;
        XposedHelpers.findAndHookMethod(indexFragment, "initData", new XC_MethodHook() {

            // 对方法执行后进行hook
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                hideHomeAddButton(param, indexFragment);
            }
        });
    }

    /**
     * 隐藏首页右下角加号按钮
     */
    private void hideHomeAddButton(XC_MethodHook.MethodHookParam param, Class indexFragment) throws IllegalAccessException {
        if (XSpUtil.getIntance().getBoolean(Constant.SpKey.weicoHomeAddButton, false)) {
            Field field = XposedHelpers.findField(indexFragment, "mIndexFab");
            View view = (View) field.get(param.thisObject);
            view.setVisibility(View.INVISIBLE);
        }
    }

}
