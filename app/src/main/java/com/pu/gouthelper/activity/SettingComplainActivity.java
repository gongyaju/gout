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
import com.pu.gouthelper.webservice.ComplainRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Requiem on 2016/3/22.
 * 意见反馈
 */
@ContentView(R.layout.activity_setting_compalin)
public class SettingComplainActivity extends SwipeBackActivity {
    @ViewInject(R.id.complain_edt_content)
    private EditText complain_edt_content;

    private Context mContext = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ComplainRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
                case ComplainRequest.SUCCESS:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    finish();
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

    @Event(value = {R.id.break_btn_goback, R.id.complain_btn_submit}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.break_btn_goback:
                finish();
                break;
            case R.id.complain_btn_submit:
                String content = complain_edt_content.getText().toString().trim();
                if (StringUtils.isEmpty(content)) {
                    UIHelper.ToastMessage(mContext, "说点什么吧~");
                    return;
                }
                new ComplainRequest(mHandler, content);
                showLoading(mContext);
                break;
        }
    }
}
