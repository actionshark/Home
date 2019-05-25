package com.shk.home.database;

import android.content.Context;

public class SettingDatabase extends KeyValueDatabase {
    private static final String TABLE_NAME = "setting";

    public static final String KEY_GRID_NUM = "grid_num";
    public static final String KEY_GRID_HEIGHT = "grid_height";

    public static final String KEY_ICON_SIZE = "icon_size";

    public static final String KEY_LABEL_SIZE = "label_size";
    public static final String KEY_LABEL_COLOR = "label_color";

    public SettingDatabase(Context context) {
        super(context, TABLE_NAME);
    }
}
