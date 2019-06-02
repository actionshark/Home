package com.shk.home.data;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppInfo {
    public String pkgName;
    public String label;
    public Drawable icon;
    public Intent intent;

    public int clickTimes = 0;
    public long lastClick = 0;
}
