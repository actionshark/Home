package com.shk.home.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.shk.home.activity.SettingActivity;
import com.shk.home.data.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<AppInfo> getAppInfos(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> activities = pm.queryIntentActivities(mainIntent, 0);

        String myPkgName = context.getPackageName();

        List<AppInfo> list = new ArrayList<>();

        for (ResolveInfo activity : activities) {
            AppInfo ai = new AppInfo();
            list.add(ai);

            String pkgName = activity.activityInfo.packageName;
            ai.pkgName = pkgName;

            ai.icon = activity.activityInfo.applicationInfo.loadIcon(pm);
            ai.label = activity.activityInfo.applicationInfo.loadLabel(pm).toString();

            ai.intent = new Intent();
            if (pkgName.equals(myPkgName)) {
                ai.intent.setClass(context, SettingActivity.class);
            } else {
                ai.intent.setComponent(new ComponentName(pkgName,
                        activity.activityInfo.name));
            }
        }
        return list;
    }
}
