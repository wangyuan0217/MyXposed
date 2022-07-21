package com.trump.myxposed;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.trump.myxposed.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


    }

    public void openOtherAppPage(View view) {
        String pkg = "";
        String actName = "";
        ComponentName componentName = new ComponentName(pkg, actName);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        //intent.putExtra("type", "110");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void test() {
        tttt(true);
    }

    public void tttt(boolean flag) {

    }

    public void test3(View view) {
        showTestToast();
    }

    public void showTestToast() {
        Toast.makeText(this, "test123", Toast.LENGTH_LONG).show();
    }

}