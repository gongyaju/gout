package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.SharedPreferences;
import com.pu.gouthelper.bean.Forecast;
import com.pu.gouthelper.bean.ForecastNew;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.utils.DateUtil;
import com.pu.gouthelper.webservice.ForecastNewsRequest;
import com.pu.gouthelper.webservice.ForecastRequest;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Requiem on 2016/3/22.
 * 个人信息
 */
@ContentView(R.layout.activity_center_myinfo)
public class CenterMyInfoActivity extends SwipeBackActivity {

    private Context mContext;
    @ViewInject(R.id.myinfo_tv_msg)
    private TextView myinfo_tv_msg;
    @ViewInject(R.id.progressBar1)
    private ProgressBar progressBar1;
    @ViewInject(R.id.progressBar2)
    private ProgressBar progressBar2;
    @ViewInject(R.id.progressBar3)
    private ProgressBar progressBar3;
    @ViewInject(R.id.progressBar1_text)
    private TextView progressBar1_text;
    @ViewInject(R.id.progressBar2_text)
    private TextView progressBar2_text;
    @ViewInject(R.id.progressBar3_text)
    private TextView progressBar3_text;
    @ViewInject(R.id.myinfo_tv_nickname)
    private TextView myinfo_tv_nickname;

    @ViewInject(R.id.goutmsg_tv_title)
    private TextView goutmsg_tv_title;
    @ViewInject(R.id.goutmsg_tv_time)
    private TextView goutmsg_tv_time;
    @ViewInject(R.id.goutmsg_tv_content)
    private TextView goutmsg_tv_content;
    @ViewInject(R.id.msg_tv_zan)
    private TextView msg_tv_zan;
    @ViewInject(R.id.msg_tv_msg)
    private TextView msg_tv_msg;



    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ForecastRequest.SUCCESS:
                    Forecast forecast = (Forecast) msg.obj;
                    myinfo_tv_msg.setText(forecast.getMsg());
                    progressBar1_text.setText("安全类食物：" + forecast.getSecurity() + "次");
                    progressBar1.setProgress(forecast.getSecurity());
                    progressBar2_text.setText("限制类食物：" + forecast.getLimited() + "次");
                    progressBar2.setProgress(forecast.getLimited());
                    progressBar3_text.setText("高危类食物：" + forecast.getRisk() + "次");
                    progressBar3.setProgress(forecast.getRisk());
                    break;
                case ForecastNewsRequest.SUCCESS:
                    ForecastNew forecastNew = (ForecastNew) msg.obj;
                    setForecastNew(forecastNew);
                    break;
                case ForecastNewsRequest.ERROR:
                case ForecastRequest.ERROR:
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
        initView();
        initData();
    }


    private void initView() {
        myinfo_tv_nickname.setText(SharedPreferences.getInstance().getString("nickname", "--"));
    }

    private void initData() {
        new ForecastRequest(mHandler);
        new ForecastNewsRequest(mHandler);
        showLoading(mContext);
    }

    private void setForecastNew(ForecastNew forecastNew) {
        goutmsg_tv_title.setText(forecastNew.getTitle());
        goutmsg_tv_time.setText(DateUtil.date2String(Long.parseLong(forecastNew.getTm()) * 1000, "yyyy-MM-dd HH:mm"));
        goutmsg_tv_content.setText(forecastNew.getContent());
        msg_tv_zan.setText(forecastNew.getUps());
        msg_tv_msg.setText(forecastNew.getComments());
    }

    @Event(value = {R.id.break_btn_goback, R.id.break_btn_more, R.id.myinfo_btn_help, R.id.goutmsg_tv_change}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.break_btn_goback:
                finish();
                break;
            case R.id.break_btn_more:
                UIHelper.ToastMessage(this, "more");
                break;
            case R.id.myinfo_btn_help:
                Intent intent = new Intent(this, SendTopicActivity.class);
                startActivity(intent);
                break;
            case R.id.goutmsg_tv_change:
                //换一换
                new ForecastNewsRequest(mHandler);
                showLoading(mContext);
                break;
        }
    }
}
