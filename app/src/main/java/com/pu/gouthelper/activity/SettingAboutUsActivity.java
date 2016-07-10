package com.pu.gouthelper.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.pu.gouthelper.R;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by Requiem on 2016/3/22.
 * 关于我们
 */
public class SettingAboutUsActivity extends SwipeBackActivity {
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            }
            endLoading();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_aboutus);
        initView();
        initData();
    }


    private void initView() {

    }

    private void initData() {
    }

    @Event(value = {R.id.break_btn_goback}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.break_btn_goback:
                finish();
                break;

        }
    }
}
