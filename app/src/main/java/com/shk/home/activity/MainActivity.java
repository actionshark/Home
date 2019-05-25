package com.shk.home.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import com.shk.home.R;
import com.shk.home.data.AppGridAdapter;
import com.shk.home.data.AppInfo;
import com.shk.home.util.Util;
import com.shk.home.util.AppLog;

import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends Activity {
    private GridView mGvApp;
    private AppGridAdapter mAppGridAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        View decorView = window.getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);

        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_main);

        View statusBar = findViewById(R.id.view_status_bar);
        statusBar.getLayoutParams().height = getStatusBarHeight();

        mGvApp = findViewById(R.id.gv_app);
        mGvApp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo ai = mAppGridAdatper.getDataList().get(position);
                startActivity(ai.intent);
            }
        });

        mAppGridAdatper = new AppGridAdapter(this);
        mGvApp.setAdapter(mAppGridAdatper);
        List<AppInfo> appInfos = Util.getAppInfos(this);
        mAppGridAdatper.setDataList(appInfos);
    }

    private int getStatusBarHeight() {
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
