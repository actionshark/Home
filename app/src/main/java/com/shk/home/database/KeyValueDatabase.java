package com.shk.home.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shk.home.util.AppLog;

public class KeyValueDatabase extends Database {
    protected static final String COL_KEY = "key_";
    protected static final String COL_VALUE = "value_";

    protected final String mTableName;

    public KeyValueDatabase(Context context, String tableName) {
        super(context);

        mTableName = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s(%s varchar unique, %s varchar)",
                mTableName, COL_KEY, COL_VALUE);
        db.execSQL(sql);
    }

    public boolean has(String key) {
        Cursor cursor = mReader.query(mTableName, new String[] {COL_VALUE},
                COL_KEY  + " = ?", new String[] {key},
                null, null, null);

        boolean result = cursor.getCount() > 0;

        cursor.close();

        return result;
    }

    public void set(String key, Object value) {
        String string = String.valueOf(value);

        String sql = String.format("insert into %s(%s, %s) values('%s', '%s') on duplicate key update %s='%s'",
                mTableName, COL_KEY, COL_VALUE, key, string, COL_VALUE, string);

        mWriter.execSQL(sql);
    }

    public String getString(String key, String def) {
        Cursor cursor = mReader.query(mTableName, new String[] {COL_VALUE},
                COL_KEY  + " = ?", new String[] {key},
                null, null, null);

        String value = def;

        if (cursor.moveToNext()) {
            value = cursor.getString(cursor.getColumnIndex(COL_VALUE));
        }

        cursor.close();

        return value;
    }

    public int getInt(String key, int def) {
        String string = getString(key, null);
        int value = def;

        if (string != null) {
            try {
                value = Integer.parseInt(string);
            } catch (Exception e) {
                AppLog.print(e);
            }
        }

        return value;
    }

    public long getLong(String key, long def) {
        String string = getString(key, null);

        long value = def;

        if (string != null) {
            try {
                value = Long.parseLong(string);
            } catch (Exception e) {
                AppLog.print(e);
            }
        }

        return value;
    }

    public boolean getBoolean(String key, boolean def) {
        String string = getString(key, null);
        return "true".equals(string);
    }

    public float getFloat(String key, float def) {
        String string = getString(key, null);

        float value = def;

        if (string != null) {
            try {
                value = Float.parseFloat(string);
            } catch (Exception e) {
                AppLog.print(e);
            }
        }

        return value;
    }

    public double getDouble(String key, double def) {
        String string = getString(key, null);

        double value = def;

        if (string != null) {
            try {
                value = Double.parseDouble(string);
            } catch (Exception e) {
                AppLog.print(e);
            }
        }

        return value;
    }
}
