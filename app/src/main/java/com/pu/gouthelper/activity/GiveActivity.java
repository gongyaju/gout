package com.pu.gouthelper.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
    @ViewInject(R.id.topic_edt_content)
    private EditText topic_edt_content;
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
            endLoading();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setPricePoint(give_tv_money);
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
                        finish();
                        //UIHelper.ToastMessage(context, "调用下一个接口~");
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
                try {
                    String money = give_tv_money.getText().toString().trim();
                    String content = topic_edt_content.getText().toString().trim();
                    if (TextUtils.isEmpty(money)) {
                        UIHelper.ToastMessage(mContext, "请输入打赏金额~");
                        return;
                    }
                    if (TextUtils.isEmpty(content)) {
                        UIHelper.ToastMessage(mContext, "对作者说点什么吧~");
                        return;
                    }
                    money =(int)(Double.parseDouble(money) * 100 )+"";
                    new CreatePrepayIdRequest(mHandler, mContext, money, getIntent().getStringExtra("type"), getIntent().getStringExtra("sid"));
                    showLoading(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    public void setPricePoint(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }
}
