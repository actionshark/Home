package com.shk.home.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

public class StatDB extends Database {
    public static class StatInfo {
        public String pkgName;
        public int clickTimes;
        public long lastClick;
    }

    private static final String TABLE_NAME = "stat";

    private static final String COL_PKG_NAME = "pkg_name";
    private static final String COL_CLICK_TIMES = "click_times";
    private static final String COL_LAST_CLICK = "last_click";

    public StatDB(Context context) {
        super(context);
    }

    @Override
    public void open(SQLiteDatabase db) {
        String sql = String.format("create table %s(%s varchar unique, %s integer, %s integer)",
                TABLE_NAME, COL_PKG_NAME, COL_CLICK_TIMES, COL_LAST_CLICK);
        db.execSQL(sql);
    }

    public StatInfo queryInfo(String pkgName) {
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, new String[]{COL_CLICK_TIMES, COL_LAST_CLICK},
                COL_PKG_NAME + " = ?", new String[]{pkgName},
                null, null, null);

        StatInfo si = null;

        if (cursor.moveToNext()) {
            si = new StatInfo();
            si.pkgName = pkgName;
            si.clickTimes = cursor.getInt(cursor.getColumnIndex(COL_CLICK_TIMES));
            si.lastClick = cursor.getLong(cursor.getColumnIndex(COL_LAST_CLICK));
        }

        cursor.close();

        return si;
    }

    public Map<String, StatInfo> queryMap() {
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, new String[]{COL_PKG_NAME, COL_CLICK_TIMES, COL_LAST_CLICK},
                null, null, null, null, null);

        Map<String, StatInfo> map = new HashMap<>();

        while (cursor.moveToNext()) {
            StatInfo si = new StatInfo();

            si.pkgName = cursor.getString(cursor.getColumnIndex(COL_PKG_NAME));
            si.clickTimes = cursor.getInt(cursor.getColumnIndex(COL_CLICK_TIMES));
            si.lastClick = cursor.getLong(cursor.getColumnIndex(COL_LAST_CLICK));

            map.put(si.pkgName, si);
        }

        cursor.close();

        return map;
    }

    public boolean updateClick(String pkgName) {
        ContentValues cv = new ContentValues();
        cv.put(COL_PKG_NAME, pkgName);
        cv.put(COL_LAST_CLICK, System.currentTimeMillis());

        StatInfo si = queryInfo(pkgName);

        if (si == null) {
            cv.put(COL_CLICK_TIMES, 1);

            long count = getWritableDatabase().insert(TABLE_NAME, null, cv);
            return count == 1;
        } else {
            cv.put(COL_CLICK_TIMES, si.clickTimes + 1);

            int count = getWritableDatabase().update(TABLE_NAME, cv,
                    COL_PKG_NAME + " = ?", new String[]{pkgName});
            return count == 1;
        }
    }
}
