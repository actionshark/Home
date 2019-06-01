package com.shk.home.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.shk.home.R;
import com.shk.home.view.AppAdapter;
import com.shk.home.data.AppInfo;
import com.shk.home.database.SettingDB;
import com.shk.home.util.Util;

import java.util.List;

public class MainActivity extends BaseActivity {
    private SettingDB mSettingDB;

    private GridView mGvApp;
    private AppAdapter mAdapterApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mSettingDB = new SettingDB(this);

        View statusBar = findViewById(R.id.view_status_bar);
        statusBar.getLayoutParams().height = getStatusBarHeight();

        mGvApp = findViewById(R.id.gv_app);
        mGvApp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo ai = mAdapterApp.getDataList().get(position);
                startActivity(ai.intent);
            }
        });

        mAdapterApp = new AppAdapter(this, mSettingDB);
        mGvApp.setAdapter(mAdapterApp);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Window window = getWindow();
        WindowManager wm = window.getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        mGvApp.setNumColumns(mSettingDB.getInt(SettingDB.KEY_GRID_NUM, SettingDB.GRID_NUM_DEF));

        int gridHeight = mSettingDB.getInt(SettingDB.KEY_GRID_HEIGHT, 0);
        if (gridHeight == 0) {
            gridHeight = dm.widthPixels / SettingDB.GRID_NUM_DEF;
            mSettingDB.set(SettingDB.KEY_GRID_HEIGHT, gridHeight);
        }
        SettingDB.GRID_HEIGHT_MIN = dm.widthPixels / SettingDB.GRID_NUM_MAX;
        SettingDB.GRID_HEIGHT_MAX = dm.widthPixels / SettingDB.GRID_NUM_MIN;

        int iconSize = mSettingDB.getInt(SettingDB.KEY_ICON_SIZE, 0);
        if (iconSize == 0) {
            iconSize = dm.widthPixels / SettingDB.GRID_NUM_DEF  * 2 / 3;
            mSettingDB.set(SettingDB.KEY_ICON_SIZE, iconSize);
        }
        SettingDB.ICON_SIZE_MIN = dm.widthPixels / SettingDB.GRID_NUM_MAX  * 2 / 3;
        SettingDB.ICON_SIZE_MAX = dm.widthPixels / SettingDB.GRID_NUM_MIN  * 2 / 3;

        List<AppInfo> appInfos = Util.getAppInfos(this);
        mAdapterApp.setDataList(appInfos);
        mAdapterApp.notifyDataSetChanged();
    }
}
