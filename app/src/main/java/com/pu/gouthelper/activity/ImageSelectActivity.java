package com.pu.gouthelper.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Requiem on 2016/3/22.
 * 图片选择
 */
@ContentView(R.layout.activity_image_select)
public class ImageSelectActivity extends SwipeBackActivity {

    private Context mContext;
    @ViewInject(R.id.remind_ls_show)
    private ListView remind_ls_show;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            }
            endLoading();
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

    @Event(value = {R.id.remind_btn_goback, R.id.remind_btn_add}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.remind_btn_goback:
                finish();
                break;
            case R.id.remind_btn_add:
                UIHelper.ToastMessage(mContext, "提交计划");
                break;
        }
    }
}
