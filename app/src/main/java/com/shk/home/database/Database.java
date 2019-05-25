package com.shk.home.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "home.db";

    protected final SQLiteDatabase mWriter;
    protected final SQLiteDatabase mReader;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);

        mWriter = getWritableDatabase();
        mReader = getReadableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
