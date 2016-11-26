package com.pu.gouthelper.activity;

import android.os.Bundle;
import android.view.View;
import com.pu.gouthelper.R;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by Requiem on 2016/3/22
 * 关于我们
 */
@ContentView(R.layout.activity_setting_aboutus)
public class SettingAboutUsActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
