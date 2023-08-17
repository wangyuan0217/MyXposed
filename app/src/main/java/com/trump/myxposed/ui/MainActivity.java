package com.trump.myxposed.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.trump.myxposed.Constant;
import com.trump.myxposed.R;
import com.trump.myxposed.databinding.ActivityMainBinding;
import com.trump.myxposed.util.Utils;
import com.trump.myxposed.util.XSpUtil;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        initListener();

        mBinding.tvStatusXp.setText(XSpUtil.isOk() ? "已激活" : "未激活");
        mBinding.tvStatusXp.setTextColor(ContextCompat.getColor(this, XSpUtil.isOk() ? R.color.black : R.color.red));
    }

    private void initListener() {
        //Launcher
        mBinding.swHideLauncher.setChecked(!Utils.isLauncherIconVisible(this));
        mBinding.swHideLauncher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!buttonView.isPressed()) return;
            Utils.setLauncherIconVisible(MainActivity.this, !isChecked);
        });

        //暗黑模式
        mBinding.swDarkMode.setChecked(XSpUtil.getBoolean(false, Constant.SpKey.darkMode));
        mBinding.swDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!buttonView.isPressed()) return;
            XSpUtil.put(Constant.SpKey.darkMode, isChecked);
        });

        //暗黑模式
        mBinding.swHidePostBtn.setChecked(XSpUtil.getBoolean(false, Constant.SpKey.hidePostBtn));
        mBinding.swHidePostBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!buttonView.isPressed()) return;
            XSpUtil.put(Constant.SpKey.hidePostBtn, isChecked);
        });

        //阻止GuardProvider跟踪
        mBinding.swFuckMiuiGuard.setChecked(XSpUtil.getBoolean(false, Constant.SpKey.swFuckMiuiGuard));
        mBinding.swFuckMiuiGuard.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!buttonView.isPressed()) return;
            XSpUtil.put(Constant.SpKey.swFuckMiuiGuard, isChecked);
        });

        //暗黑模式
        mBinding.swFuckMiuiAnalytics.setChecked(XSpUtil.getBoolean(false, Constant.SpKey.swFuckMiuiAnalytics));
        mBinding.swFuckMiuiAnalytics.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!buttonView.isPressed()) return;
            XSpUtil.put(Constant.SpKey.swFuckMiuiAnalytics, isChecked);
        });
    }

}