package com.pu.gouthelper.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.orhanobut.logger.Logger;
import com.pu.gouthelper.R;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.wechat.Wechat;
import com.pu.gouthelper.wechat.webservice.CreatePrepayIdRequest;
import com.pu.gouthelper.wxapi.WXPayEntryActivity;


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
    @ViewInject(R.id.give_tv_money)
    private EditText give_tv_money;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CreatePrepayIdRequest.SUCCESS:
                    new Wechat(mContext, msg.obj + "");
                    break;
                case CreatePrepayIdRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;

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
        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction(WXPayEntryActivity.BOADRDCAST_WXPAY);
        registerReceiver(payResult, dynamic_filter);
    }

    private BroadcastReceiver payResult = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WXPayEntryActivity.BOADRDCAST_WXPAY)) {    //动作检测
                switch (intent.getIntExtra("wchat", -1)) {
                    case 0:
                        UIHelper.ToastMessage(context, "调用下一个接口~");
                        break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(payResult);
    }

    @Event(value = {R.id.remind_btn_goback, R.id.give_btn_sure}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.remind_btn_goback:
                finish();
                break;
            case R.id.give_btn_sure:
                String money = give_tv_money.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    UIHelper.ToastMessage(mContext, "请输入打赏金额~");
                    return;
                }
                new CreatePrepayIdRequest(mHandler, mContext, money);
                break;
        }
    }

}