package com.shk.home.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.shk.home.R;
import com.shk.home.data.AppAdapter;
import com.shk.home.data.AppInfo;
import com.shk.home.database.SettingDB;
import com.shk.home.database.StatDB;
import com.shk.home.util.Util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private SettingDB mSettingDB;
    private StatDB mStatDB;

    private GridView mGvApp;
    private AppAdapter mAdapterApp;

    private Map<String, Comparator<AppInfo>> mComparators = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mSettingDB = new SettingDB(this);
        mStatDB = new StatDB(this);

        View statusBar = findViewById(R.id.view_status_bar);
        statusBar.getLayoutParams().height = getStatusBarHeight();

        mGvApp = findViewById(R.id.gv_app);
        mGvApp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo ai = mAdapterApp.getDataList().get(position);
                mStatDB.updateClick(ai.pkgName);
                startActivity(ai.intent);
            }
        });

        mAdapterApp = new AppAdapter(this, mSettingDB);
        mGvApp.setAdapter(mAdapterApp);

        mComparators.put(SettingDB.SORT_KEY_APP_LABEL, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo a, AppInfo b) {
                return a.label.compareTo(b.label);
            }
        });

        mComparators.put(SettingDB.SORT_KEY_CLICK_TIMES, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo a, AppInfo b) {
                return a.clickTimes - b.clickTimes;
            }
        });

        mComparators.put(SettingDB.SORT_KEY_LAST_CLICK, new Comparator<AppInfo>() {
            @Override
            public int compare(AppInfo a, AppInfo b) {
                if (a.lastClick > b.lastClick) {
                    return 1;
                } else if (a.lastClick < b.lastClick) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
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
            iconSize = gridHeight * 2 / 3;
            mSettingDB.set(SettingDB.KEY_ICON_SIZE, iconSize);
        }
        SettingDB.ICON_SIZE_MIN = dm.widthPixels / SettingDB.GRID_NUM_MAX * 2 / 3;
        SettingDB.ICON_SIZE_MAX = dm.widthPixels / SettingDB.GRID_NUM_MIN * 2 / 3;

        int labelSize = mSettingDB.getInt(SettingDB.KEY_LABEL_SIZE, 0);
        if (labelSize == 0) {
            labelSize = gridHeight * 6 / 100;
            mSettingDB.set(SettingDB.KEY_LABEL_SIZE, labelSize);
        }
        SettingDB.LABEL_SIZE_MIN = 6;
        SettingDB.LABEL_SIZE_MAX = gridHeight / 4;

        List<AppInfo> appInfos = Util.getAppInfos(this);
        Map<String, StatDB.StatInfo> appInfoMap = mStatDB.queryMap();

        for (AppInfo ai : appInfos) {
            StatDB.StatInfo si = appInfoMap.get(ai.pkgName);

            if (si != null) {
                ai.clickTimes = si.clickTimes;
                ai.lastClick = si.lastClick;
            }
        }

        String sortKey = mSettingDB.getString(SettingDB.KEY_SORT_KEY, SettingDB.SORT_KEY_APP_LABEL);
        String sortOrder = mSettingDB.getString(SettingDB.KEY_SORT_ORDER, SettingDB.SORT_ORDER_ASC);
        final Comparator<AppInfo> comparator = mComparators.get(sortKey);

        if (SettingDB.SORT_ORDER_DESC.equals(sortOrder)) {
            Collections.sort(appInfos, new Comparator<AppInfo>() {
                @Override
                public int compare(AppInfo a, AppInfo b) {
                    return comparator.compare(b, a);
                }
            });
        } else {
            Collections.sort(appInfos, comparator);
        }

        mAdapterApp.setDataList(appInfos);
        mAdapterApp.notifyDataSetChanged();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }
}
