package com.pu.gouthelper.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.utils.MiscUtil;
import com.pu.gouthelper.webservice.AddAttackRecordRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.concurrent.TimeUnit;

/**
 * Created by Requiem on 2016/3/22.
 * 发作记录
 */
@ContentView(R.layout.activity_write_breackrecord)
public class WriteBreakRecordActivity extends SwipeBackActivity {

    @ViewInject(R.id.break_tv_time)
    private TextView break_tv_time;
    @ViewInject(R.id.breakrecord_edt_content)
    private EditText breakrecord_edt_content;
    private Context mContext;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AddAttackRecordRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
                case AddAttackRecordRequest.SUCCESS:
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
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.main_background);
        mContext = this;
        initView();
        initData();
    }


    private void initView() {

    }

    private void initData() {
        break_tv_time.setText("今日日期：" + StringUtils.toNYR(System.currentTimeMillis()));

    }

    @Event(value = {R.id.break_btn_goback, R.id.break_btn_submit}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.break_btn_goback:
                finish();
                break;
            case R.id.break_btn_submit:
                String content = breakrecord_edt_content.getText().toString().trim();
                if (StringUtils.isEmpty(content)) {
                    UIHelper.ToastMessage(mContext, "请写点什么吧~");
                } else {
                    new AddAttackRecordRequest(mHandler, content);
                    showLoading(mContext);
                }

                break;

        }
    }
}
