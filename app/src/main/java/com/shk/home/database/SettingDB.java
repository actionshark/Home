package com.shk.home.database;

import android.content.Context;

public class SettingDB extends KeyValueDB {
    private static final String TABLE_NAME = "setting";

    public static final String KEY_GRID_NUM = "grid_num";
    public static final int GRID_NUM_DEF = 5;
    public static final int GRID_NUM_MIN = 1;
    public static final int GRID_NUM_MAX = 10;

    public static final String KEY_GRID_HEIGHT = "grid_height";
    public static int GRID_HEIGHT_MIN = -1;
    public static int GRID_HEIGHT_MAX = -1;

    public static final String KEY_ICON_SIZE = "icon_size";
    public static int ICON_SIZE_MIN = -1;
    public static int ICON_SIZE_MAX = -1;

    public static final String KEY_LABEL_SIZE = "label_size";
    public static int LABEL_SIZE_MIN = -1;
    public static int LABEL_SIZE_MAX = -1;

    public static final String KEY_LABEL_COLOR = "label_color";
    public static final int LABEL_COLOR_DEF = 0xff000000;

    public SettingDB(Context context) {
        super(context, TABLE_NAME);
    }
}
