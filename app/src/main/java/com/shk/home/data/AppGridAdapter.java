package com.shk.home.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shk.home.R;

import java.util.ArrayList;
import java.util.List;

public class AppGridAdapter extends BaseAdapter {
    private class Holder {
        ImageView icon;
        TextView label;
    }

    private Context mContext;
    private List<AppInfo> mDataList;

    public AppGridAdapter(Context context) {
        mContext = context;
    }

    public void setDataList(List<AppInfo> dataList) {
        mDataList = new ArrayList<>(dataList);
        notifyDataSetChanged();
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

            Holder holder = new Holder();
            view.setTag(holder);

            holder.icon = view.findViewById(R.id.iv_icon);
            holder.label = view.findViewById(R.id.tv_label);
        }

        Holder holder = (Holder) view.getTag();
        AppInfo data = mDataList.get(position);

        holder.icon.setImageDrawable(data.icon);
        holder.label.setText(data.label);

        return view;
    }
}
