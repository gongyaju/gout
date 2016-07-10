package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.TopicSendRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Requiem on 2016/3/22.
 * 发布帖子成功
 */
@ContentView(R.layout.activity_send_success)
public class SendTopicSuccessActivity extends SwipeBackActivity {

    @ViewInject(R.id.topic_edt_title)
    private EditText topic_edt_title;
    @ViewInject(R.id.topic_edt_content)
    private EditText topic_edt_content;
    private Context mContext = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initView();
        initData();
    }


    private void initView() {

    }

    private void initData() {
    }

    @Event(value = {R.id.success_btn_tocheck, R.id.success_btn_close}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.success_btn_tocheck:
                Intent intent = new Intent(this, CenterMyTopicActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.success_btn_close:
                finish();
                break;
        }
    }
}
