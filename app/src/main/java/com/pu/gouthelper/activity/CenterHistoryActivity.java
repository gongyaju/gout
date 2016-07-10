package com.pu.gouthelper.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.bean.LoginResult;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.utils.DateUtil;
import com.pu.gouthelper.webservice.MyInfoGetRequest;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Requiem on 2016/3/22.
 * 病历
 */
@ContentView(R.layout.activity_center_history)
public class CenterHistoryActivity extends SwipeBackActivity {
    private Context mContext;
    @ViewInject(R.id.history_tv_age)
    private TextView history_tv_age;
    @ViewInject(R.id.history_tv_height)
    private TextView history_tv_height;
    @ViewInject(R.id.history_tv_year)
    private TextView history_tv_year;
    @ViewInject(R.id.history_tv_weight)
    private TextView history_tv_weight;
    @ViewInject(R.id.history_tv_buwei)
    private TextView history_tv_buwei;
    @ViewInject(R.id.history_tv_breaktime)
    private TextView history_tv_breaktime;
    @ViewInject(R.id.history_tv_durg)
    private TextView history_tv_durg;
    @ViewInject(R.id.history_tv_other)
    private TextView history_tv_other;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MyInfoGetRequest.ERROR:
                    UIHelper.ToastMessage(mContext, "" + msg.obj);
                    break;
                case MyInfoGetRequest.SUCCESS:
                    LoginResult login_msg = (LoginResult) msg.obj;
                    if (login_msg != null) {
                        setData(login_msg);
                    }
                    break;
            }
            endLoading();
        }
    };

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
        new MyInfoGetRequest(mHandler);
    }

    private void setData(LoginResult login_msg) {
        history_tv_age.setText("年龄：" + login_msg.getAge());
        history_tv_height.setText("身高：" + login_msg.getHeight() + "cm");
        history_tv_year.setText("病史：" + login_msg.getHistory() + "年");
        history_tv_weight.setText("体重：" + login_msg.getWeight() + "KG");
        history_tv_buwei.setText("发作部位：" + login_msg.getTarea());
        history_tv_breaktime.setText("上次发作时间：" + DateUtil.date2String(Long.parseLong(login_msg.getTm()) *
                1000, "yyyy-MM-dd HH:mm"));
        history_tv_durg.setText("服用药物：" + login_msg.getDrug());
        history_tv_other.setText("有无其他病史：无");
    }

    @Event(value = {R.id.history_btn_close}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.history_btn_close:
                finish();
                break;
        }
    }
}
