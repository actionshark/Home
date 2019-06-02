package com.shk.home.util;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import com.shk.home.activity.SettingActivity;
import com.shk.home.data.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<AppInfo> getAppInfos(Context context) {
        List<AppInfo> list = new ArrayList<>();

        list.addAll(queryApps(context));
        // list.addAll(queryShortcuts(context));

        return list;
    }

    private static List<AppInfo> queryApps(Context context) {
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

    private static String getAuthorityFromPermission(Context context, String permission) {
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);

        for (PackageInfo pack : packs) {
            ProviderInfo[] providers = pack.providers;
            if (providers == null) {
                continue;
            }

            for (ProviderInfo provider : providers) {
                if (permission.equals(provider.readPermission)) {
                    return provider.authority;
                }

                if (permission.equals(provider.writePermission)) {
                    return provider.authority;
                }
            }
        }

        return null;
    }

    public static List<AppInfo> queryShortcuts(Context context) {
        ContentResolver cr = context.getContentResolver();
        String AUTHORITY = getAuthorityFromPermission(context, "com.android.launcher.permission.READ_SETTINGS");
        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");

        Cursor cursor = cr.query(CONTENT_URI, null, null, null, null);
        List<AppInfo> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            String intent = cursor.getString(cursor.getColumnIndex("intent"));
            if (intent == null) {
                continue;
            }

            if (intent.contains("SHORTCUT")) {
                AppInfo ai = new AppInfo();

                ai.pkgName = cursor.getString(cursor.getColumnIndex("iconPackage"));
                ai.label = cursor.getString(cursor.getColumnIndex("label"));

                byte[] bs = cursor.getBlob(cursor.getColumnIndex("icon"));
                Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
                ai.icon = new BitmapDrawable(context.getResources(), bitmap);

                try {
                    ai.intent = Intent.parseUri(intent, 0);
                } catch (Exception e) {
                    AppLog.print(e);
                }

                list.add(ai);
            }
        }

        cursor.close();

        return list;
    }
}
