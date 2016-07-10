package com.pu.gouthelper.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.ChangePswRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Requiem on 2016/3/22.
 * 修改密码
 */
@ContentView(R.layout.activity_setting_psw)
public class SettingPasswordActivity extends SwipeBackActivity {

    @ViewInject(R.id.psw_edt_oldpsw)
    private EditText psw_edt_oldpsw;
    @ViewInject(R.id.psw_edt_newpsw1)
    private EditText psw_edt_newpsw1;
    @ViewInject(R.id.psw_edt_newpsw2)
    private EditText psw_edt_newpsw2;
    private Context mContext;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ChangePswRequest.SUCCESS:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    finish();
                    break;
                case ChangePswRequest.ERROR:
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
    }

    @Event(value = {R.id.break_btn_goback, R.id.psw_btn_submit}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.break_btn_goback:
                finish();
                break;
            case R.id.psw_btn_submit:
                String old = psw_edt_oldpsw.getText().toString().trim();
                String new1 = psw_edt_newpsw1.getText().toString().trim();
                String new2 = psw_edt_newpsw2.getText().toString().trim();
                if (StringUtils.isEmpty(old)) {
                    UIHelper.ToastMessage(mContext, "请输入旧密码~");
                    return;
                }
                if (StringUtils.isEmpty(new1) || StringUtils.isEmpty(new2)) {
                    UIHelper.ToastMessage(mContext, "请输入新密码~");
                    return;
                }
                new ChangePswRequest(mHandler, old, new1, new2);
                showLoading(mContext);
                break;
        }
    }
}
