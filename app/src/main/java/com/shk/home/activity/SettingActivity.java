package com.shk.home.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.shk.home.R;
import com.shk.home.data.AppInfo;
import com.shk.home.database.SettingDB;
import com.shk.home.data.AppAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends BaseActivity {
    private SettingDB mDatabase;

    private Handler mHandler;
    private long mRepeatDelay = 20;

    private int mScreenWidth;

    private View mAppGrid;
    private AppAdapter mAppAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = new SettingDB(this);

        mHandler = new Handler(Looper.getMainLooper());

        Window window = getWindow();
        WindowManager wm = window.getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;

        setContentView(R.layout.activity_setting);

        View statusBar = findViewById(R.id.view_status_bar);
        statusBar.getLayoutParams().height = getStatusBarHeight();

        AppInfo ai = new AppInfo();
        ai.icon = getDrawable(R.drawable.icon);
        ai.label = getString(R.string.app_name);

        List<AppInfo> dataList = new ArrayList<>();
        dataList.add(ai);

        mAppAdapter = new AppAdapter(this, mDatabase);
        mAppAdapter.setDataList(dataList);

        mAppGrid = findViewById(R.id.grid_sample);
        updateAppGrid();

        changeGridNum(0);

        findViewById(R.id.tv_grid_num_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGridNum(1);
            }
        });

        findViewById(R.id.tv_grid_num_sub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGridNum(-1);
            }
        });

        changeGridHeight(0);

        TextView tvGridHeightAdd = findViewById(R.id.tv_grid_height_add);
        tvGridHeightAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGridHeight(1);
            }
        });
        tvGridHeightAdd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeGridHeight(1);
                mHandler.postDelayed(mGridHeightAddRun, mRepeatDelay);
                return true;
            }
        });
        tvGridHeightAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    mHandler.removeCallbacks(mGridHeightAddRun);
                }

                return false;
            }
        });

        TextView tvGridHeightSub = findViewById(R.id.tv_grid_height_sub);
        tvGridHeightSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGridHeight(-1);
            }
        });
        tvGridHeightSub.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeGridHeight(-1);
                mHandler.postDelayed(mGridHeightSubRun, mRepeatDelay);
                return true;
            }
        });
        tvGridHeightSub.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    mHandler.removeCallbacks(mGridHeightSubRun);
                }

                return false;
            }
        });

        changeIconSize(0);

        TextView tvIconSizeAdd = findViewById(R.id.tv_icon_size_add);
        tvIconSizeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeIconSize(1);
            }
        });
        tvIconSizeAdd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeIconSize(1);
                mHandler.postDelayed(mIconSizeAddRun, mRepeatDelay);
                return true;
            }
        });
        tvIconSizeAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    mHandler.removeCallbacks(mIconSizeAddRun);
                }

                return false;
            }
        });

        TextView tvIconSizeSub = findViewById(R.id.tv_icon_size_sub);
        tvIconSizeSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeIconSize(-1);
            }
        });
        tvIconSizeSub.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeIconSize(-1);
                mHandler.postDelayed(mIconSizeSubRun, mRepeatDelay);
                return true;
            }
        });
        tvIconSizeSub.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    mHandler.removeCallbacks(mIconSizeSubRun);
                }

                return false;
            }
        });

        changeLabelSize(0);

        TextView tvLabelSizeAdd = findViewById(R.id.tv_label_size_add);
        tvLabelSizeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLabelSize(1);
            }
        });
        tvLabelSizeAdd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeLabelSize(1);
                mHandler.postDelayed(mLabelSizeAddRun, mRepeatDelay);
                return true;
            }
        });
        tvLabelSizeAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    mHandler.removeCallbacks(mLabelSizeAddRun);
                }

                return false;
            }
        });

        TextView tvLabelSizeSub = findViewById(R.id.tv_label_size_sub);
        tvLabelSizeSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLabelSize(-1);
            }
        });
        tvLabelSizeSub.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeLabelSize(-1);
                mHandler.postDelayed(mLabelSizeSubRun, mRepeatDelay);
                return true;
            }
        });
        tvLabelSizeSub.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    mHandler.removeCallbacks(mLabelSizeSubRun);
                }

                return false;
            }
        });

        changeLabelColor(0, 0);

        TextView tvLabelRedAdd = findViewById(R.id.tv_label_red_add);
        tvLabelRedAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLabelColor(16, 1);
            }
        });
        tvLabelRedAdd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeLabelColor(16, 1);
                mHandler.postDelayed(mLabelRedAddRun, mRepeatDelay);
                return true;
            }
        });
        tvLabelRedAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    mHandler.removeCallbacks(mLabelRedAddRun);
                }

                return false;
            }
        });

        TextView tvLabelRedRub = findViewById(R.id.tv_label_red_sub);
        tvLabelRedRub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLabelColor(16, -1);
            }
        });
        tvLabelRedRub.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeLabelColor(16, -1);
                mHandler.postDelayed(mLabelRedSubRun, mRepeatDelay);
                return true;
            }
        });
        tvLabelRedRub.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    mHandler.removeCallbacks(mLabelRedSubRun);
                }

                return false;
            }
        });

        TextView tvLabelGreenAdd = findViewById(R.id.tv_label_green_add);
        tvLabelGreenAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLabelColor(8, 1);
            }
        });
        tvLabelGreenAdd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeLabelColor(8, 1);
                mHandler.postDelayed(mLabelGreenAddRun, mRepeatDelay);
                return true;
            }
        });
        tvLabelGreenAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    mHandler.removeCallbacks(mLabelGreenAddRun);
                }

                return false;
            }
        });

        TextView tvLabelGreenSub = findViewById(R.id.tv_label_green_sub);
        tvLabelGreenSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLabelColor(8, -1);
            }
        });
        tvLabelGreenSub.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeLabelColor(8, -1);
                mHandler.postDelayed(mLabelGreenSubRun, mRepeatDelay);
                return true;
            }
        });
        tvLabelGreenSub.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    mHandler.removeCallbacks(mLabelGreenSubRun);
                }

                return false;
            }
        });

        TextView tvLabelBlueAdd = findViewById(R.id.tv_label_blue_add);
        tvLabelBlueAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLabelColor(0, 1);
            }
        });
        tvLabelBlueAdd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeLabelColor(0, 1);
                mHandler.postDelayed(mLabelBlueAddRun, mRepeatDelay);
                return true;
            }
        });
        tvLabelBlueAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    mHandler.removeCallbacks(mLabelBlueAddRun);
                }

                return false;
            }
        });

        TextView tvLabelBlueSub = findViewById(R.id.tv_label_blue_sub);
        tvLabelBlueSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLabelColor(0, -1);
            }
        });
        tvLabelBlueSub.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                changeLabelColor(0, -1);
                mHandler.postDelayed(mLabelBlueSubRun, mRepeatDelay);
                return true;
            }
        });
        tvLabelBlueSub.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                    mHandler.removeCallbacks(mLabelBlueSubRun);
                }

                return false;
            }
        });
    }

    private void updateAppGrid() {
        mAppAdapter.getView(0, mAppGrid, null);

        int gridNum = mDatabase.getInt(SettingDB.KEY_GRID_NUM, SettingDB.GRID_NUM_DEF);
        mAppGrid.getLayoutParams().width = mScreenWidth / gridNum;
    }

    private void changeGridNum(int change) {
        int value = mDatabase.getInt(SettingDB.KEY_GRID_NUM, SettingDB.GRID_NUM_DEF) + change;
        if (value >= SettingDB.GRID_NUM_MIN  && value <= SettingDB.GRID_NUM_MAX) {
            mDatabase.set(SettingDB.KEY_GRID_NUM, value);

            TextView tvGridNum = findViewById(R.id.tv_grid_num);
            tvGridNum.setText(String.valueOf(value));

            updateAppGrid();
        }
    }

    private void changeGridHeight(int change) {
        int value = mDatabase.getInt(SettingDB.KEY_GRID_HEIGHT, 0) + change;

        if (value >= SettingDB.GRID_HEIGHT_MIN && value <= SettingDB.GRID_HEIGHT_MAX) {
            mDatabase.set(SettingDB.KEY_GRID_HEIGHT, value);

            TextView view = findViewById(R.id.tv_grid_height);
            view.setText(String.valueOf(value));

            updateAppGrid();
        }
    }
    private Runnable mGridHeightAddRun = new Runnable() {
        @Override
        public void run() {
            changeGridHeight(1);
            mHandler.postDelayed(this, mRepeatDelay);
        }
    };

    private Runnable mGridHeightSubRun = new Runnable() {
        @Override
        public void run() {
            changeGridHeight(-1);
            mHandler.postDelayed(this, mRepeatDelay);
        }
    };

    private void changeIconSize(int change) {
        int value = mDatabase.getInt(SettingDB.KEY_ICON_SIZE, 0) + change;

        if (value >= SettingDB.ICON_SIZE_MIN && value <= SettingDB.ICON_SIZE_MAX) {
            mDatabase.set(SettingDB.KEY_ICON_SIZE, value);

            TextView view = findViewById(R.id.tv_icon_size);
            view.setText(String.valueOf(value));

            updateAppGrid();
        }
    }
    private Runnable mIconSizeAddRun = new Runnable() {
        @Override
        public void run() {
            changeIconSize(1);
            mHandler.postDelayed(this, mRepeatDelay);
        }
    };

    private Runnable mIconSizeSubRun = new Runnable() {
        @Override
        public void run() {
            changeIconSize(-1);
            mHandler.postDelayed(this, mRepeatDelay);
        }
    };

    private void changeLabelSize(int change) {
        int value = mDatabase.getInt(SettingDB.KEY_LABEL_SIZE, 0) + change;

        if (value >= SettingDB.LABEL_SIZE_MIN && value <= SettingDB.LABEL_SIZE_MAX) {
            mDatabase.set(SettingDB.KEY_LABEL_SIZE, value);

            TextView view = findViewById(R.id.tv_label_size);
            view.setText(String.valueOf(value));

            updateAppGrid();
        }
    }
    private Runnable mLabelSizeAddRun = new Runnable() {
        @Override
        public void run() {
            changeLabelSize(1);
            mHandler.postDelayed(this, mRepeatDelay);
        }
    };

    private Runnable mLabelSizeSubRun = new Runnable() {
        @Override
        public void run() {
            changeLabelSize(-1);
            mHandler.postDelayed(this, mRepeatDelay);
        }
    };

    private void changeLabelColor(int shift, int change) {
        int value = mDatabase.getInt(SettingDB.KEY_LABEL_COLOR, SettingDB.LABEL_COLOR_DEF);
        int color = value >> shift & 0xff;
        color += change;

        if (color >= 0 && color <= 255) {
            value = value & ~(0xff << shift) | color << shift;

            mDatabase.set(SettingDB.KEY_LABEL_COLOR, value);

            TextView view = findViewById(R.id.tv_label_red);
            view.setText(String.valueOf(value >> 16 & 0xff));

            view = findViewById(R.id.tv_label_green);
            view.setText(String.valueOf(value >> 8 & 0xff));

            view = findViewById(R.id.tv_label_blue);
            view.setText(String.valueOf(value & 0xff));

            updateAppGrid();
        }
    }

    private Runnable mLabelRedAddRun = new Runnable() {
        @Override
        public void run() {
            changeLabelColor(16, 1);
            mHandler.postDelayed(this, mRepeatDelay);
        }
    };

    private Runnable mLabelRedSubRun = new Runnable() {
        @Override
        public void run() {
            changeLabelColor(16, -1);
            mHandler.postDelayed(this, mRepeatDelay);
        }
    };

    private Runnable mLabelGreenAddRun = new Runnable() {
        @Override
        public void run() {
            changeLabelColor(8, 1);
            mHandler.postDelayed(this, mRepeatDelay);
        }
    };

    private Runnable mLabelGreenSubRun = new Runnable() {
        @Override
        public void run() {
            changeLabelColor(8, -1);
            mHandler.postDelayed(this, mRepeatDelay);
        }
    };

    private Runnable mLabelBlueAddRun = new Runnable() {
        @Override
        public void run() {
            changeLabelColor(0, 1);
            mHandler.postDelayed(this, mRepeatDelay);
        }
    };

    private Runnable mLabelBlueSubRun = new Runnable() {
        @Override
        public void run() {
            changeLabelColor(0, -1);
            mHandler.postDelayed(this, mRepeatDelay);
        }
    };
}
