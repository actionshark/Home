package com.shk.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shk.home.R;
import com.shk.home.data.AppInfo;
import com.shk.home.database.SettingDB;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends BaseAdapter {
    private class Holder {
        ImageView icon;
        TextView label;
    }

    private Context mContext;
    private List<AppInfo> mDataList;

    private SettingDB mSettingDB;

    public AppAdapter(Context context, SettingDB settingDB) {
        mContext = context;
        mSettingDB = settingDB;
    }

    public void setDataList(List<AppInfo> dataList) {
        mDataList = new ArrayList<>(dataList);
    }

    public List<AppInfo> getDataList() {
        return mDataList;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_app, null);
        }

        Holder holder = (Holder) view.getTag();
        if (holder == null) {
            holder = new Holder();
            view.setTag(holder);

            holder.icon = view.findViewById(R.id.iv_icon);
            holder.label = view.findViewById(R.id.tv_label);
        }

        int height = mSettingDB.getInt(SettingDB.KEY_GRID_HEIGHT, 0);

        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            view.setLayoutParams(lp);
        } else {
            lp.height = height;
        }

        AppInfo data = mDataList.get(position);

        lp = holder.icon.getLayoutParams();
        lp.width = lp.height = mSettingDB.getInt(SettingDB.KEY_ICON_SIZE, 0);
        holder.icon.setImageDrawable(data.icon);

        holder.label.setText(data.label);
        holder.label.setTextColor(mSettingDB.getInt(SettingDB.KEY_LABEL_COLOR, SettingDB.LABEL_COLOR_DEF));

        return view;
    }
}
