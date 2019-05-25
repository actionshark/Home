package com.shk.home.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.shk.home.R;
import com.shk.home.data.AppGridAdapter;
import com.shk.home.data.AppInfo;
import com.shk.home.data.Util;

import java.util.List;

public class MainActivity extends Activity {
    private GridView mGvApp;
    private AppGridAdapter mAppGridAdatper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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
}
