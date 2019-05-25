package com.shk.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shk.home.R;
import com.shk.home.database.SettingDB;

public class SettingActivity extends BaseActivity {
    private SettingDB mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        View statusBar = findViewById(R.id.view_status_bar);
        statusBar.getLayoutParams().height = getStatusBarHeight();

        mDatabase = new SettingDB(this);

        int gridNum = mDatabase.getInt(SettingDB.KEY_GRID_NUM, SettingDB.GRID_NUM_DEF);
        final TextView tvGridNum = findViewById(R.id.tv_grid_num);
        tvGridNum.setText(String.valueOf(gridNum));

        findViewById(R.id.tv_grid_num_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gridNum = mDatabase.getInt(SettingDB.KEY_GRID_NUM, SettingDB.GRID_NUM_DEF);
                if (gridNum < SettingDB.GRID_NUM_MAX) {
                    gridNum++;
                    mDatabase.set(SettingDB.KEY_GRID_NUM, gridNum);
                    tvGridNum.setText(String.valueOf(gridNum));
                }
            }
        });

        findViewById(R.id.tv_grid_num_sub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gridNum = mDatabase.getInt(SettingDB.KEY_GRID_NUM, SettingDB.GRID_NUM_DEF);
                if (gridNum > SettingDB.GRID_NUM_MIN) {
                    gridNum--;
                    mDatabase.set(SettingDB.KEY_GRID_NUM, gridNum);
                    tvGridNum.setText(String.valueOf(gridNum));
                }
            }
        });
    }
}
