package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.SharedPreferences;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.bean.RemindEntity;
import com.pu.gouthelper.dialog.PopupDrugDays;
import com.pu.gouthelper.dialog.PopupDrugDosage;
import com.pu.gouthelper.dialog.PopupDrugTime;
import com.pu.gouthelper.ui.ColorPickerView;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.AddClockRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * Created by Requiem on 2016/3/22.
 * 打赏
 */
@ContentView(R.layout.activity_give)
public class GiveActivity extends SwipeBackActivity {

    private Context mContext;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
    }

    private void initData() {

    }

    @Event(value = {R.id.remind_btn_goback}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.remind_btn_goback:
                finish();
                break;

        }
    }

}
