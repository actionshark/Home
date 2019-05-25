package com.shk.home.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.shk.home.util.AppLog;

import java.lang.reflect.Field;

public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        View decorView = window.getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);

        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
    }

    protected int getStatusBarHeight() {
        int statusBarHeight = 0;

        try {
            Class cls = Class.forName("com.android.internal.R$dimen");
            Object instance = cls.newInstance();
            Field field = cls.getField("status_bar_height");
            int x = Integer.parseInt(field.get(instance).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            AppLog.print(e);
        }

        return statusBarHeight;
    }
}
