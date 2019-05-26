package com.shk.home.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shk.home.R;
import com.shk.home.database.SettingDB;

public class SettingActivity extends BaseActivity {
    private SettingDB mDatabase;

    private Handler mHandler;
    private long mRepeatDelay = 20;

    private View mAppGrid;
    private ImageView mIvIcon;
    private TextView mTvLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = new SettingDB(this);

        mHandler = new Handler(Looper.getMainLooper());

        setContentView(R.layout.activity_setting);

        View statusBar = findViewById(R.id.view_status_bar);
        statusBar.getLayoutParams().height = getStatusBarHeight();

        mAppGrid = findViewById(R.id.grid_sample);
        mIvIcon = findViewById(R.id.iv_icon);
        mIvIcon.setImageResource(R.drawable.icon);
        mTvLabel = findViewById(R.id.tv_label);
        mTvLabel.setText(R.string.app_name);

        int gridNum = mDatabase.getInt(SettingDB.KEY_GRID_NUM, SettingDB.GRID_NUM_DEF);
        final TextView tvGridNum = findViewById(R.id.tv_grid_num);
        tvGridNum.setText(String.valueOf(gridNum));

        findViewById(R.id.tv_grid_num_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = mDatabase.getInt(SettingDB.KEY_GRID_NUM, SettingDB.GRID_NUM_DEF);
                if (value < SettingDB.GRID_NUM_MAX) {
                    value++;
                    mDatabase.set(SettingDB.KEY_GRID_NUM, value);
                    tvGridNum.setText(String.valueOf(value));
                }
            }
        });

        findViewById(R.id.tv_grid_num_sub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = mDatabase.getInt(SettingDB.KEY_GRID_NUM, SettingDB.GRID_NUM_DEF);
                if (value > SettingDB.GRID_NUM_MIN) {
                    value--;
                    mDatabase.set(SettingDB.KEY_GRID_NUM, value);
                    tvGridNum.setText(String.valueOf(value));
                }
            }
        });

        int gridHeight = mDatabase.getInt(SettingDB.KEY_GRID_HEIGHT, 0);
        TextView tvGridHeight = findViewById(R.id.tv_grid_height);
        tvGridHeight.setText(String.valueOf(gridHeight));

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

        int iconSize = mDatabase.getInt(SettingDB.KEY_ICON_SIZE, 0);
        TextView tvIconSize = findViewById(R.id.tv_icon_size);
        tvIconSize.setText(String.valueOf(iconSize));

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
    }

    private void updateAppGrid() {

    }

    private void changeGridHeight(int change) {
        int value = mDatabase.getInt(SettingDB.KEY_GRID_HEIGHT, 0) + change;

        if (value >= SettingDB.GRID_HEIGHT_MIN && value <= SettingDB.GRID_HEIGHT_MAX) {
            mDatabase.set(SettingDB.KEY_GRID_HEIGHT, value);

            TextView view = findViewById(R.id.tv_grid_height);
            view.setText(String.valueOf(value));
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
}
