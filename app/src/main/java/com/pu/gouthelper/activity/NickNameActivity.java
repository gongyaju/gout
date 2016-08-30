package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.pu.gouthelper.R;
import com.pu.gouthelper.bean.MyInfo;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.SubmitMyInfoRequest;
import com.pu.gouthelper.webservice.UserAddressListRequest;
import com.pu.gouthelper.webservice.UserDelImageRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * Created by Requiem on 2016/3/22.
 * 修改nick
 */
@ContentView(R.layout.activity_nickname)
public class NickNameActivity extends SwipeBackActivity {
    private Context mContext;
    @ViewInject(R.id.nickname_edt)
    private EditText nickname_edt;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SubmitMyInfoRequest.SUCCESS:
                case SubmitMyInfoRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    endLoading();
                    finish();
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {

    }

    @Event(value = {R.id.address_btn_goback, R.id.nickname_btn}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_btn_goback:
                finish();
                break;
            case R.id.nickname_btn:
                String nick = nickname_edt.getText().toString().trim();
                if (nick.length() < 4) {
                    UIHelper.ToastMessage(this, "请以英文字母或汉子开头，限4-16字符");
                    return;
                }
                new SubmitMyInfoRequest(mHandler, nick);
                showLoading(this);
                break;
        }
    }

}
