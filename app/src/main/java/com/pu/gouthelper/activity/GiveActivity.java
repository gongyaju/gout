package com.pu.gouthelper.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.pu.gouthelper.R;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.wechat.Wechat;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;


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

    @Event(value = {R.id.remind_btn_goback, R.id.give_btn_sure}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.remind_btn_goback:
                finish();
                break;
            case R.id.give_btn_sure:

                new Wechat(this,"");
                break;
        }
    }

}
