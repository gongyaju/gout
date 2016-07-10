package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.adapter.MyTopicAdapter;
import com.pu.gouthelper.bean.MyTopic;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.TopicMyRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Requiem on 2016/3/22.
 * 我的帖子
 */
@ContentView(R.layout.activity_center_mytopic)
public class CenterMyTopicActivity extends SwipeBackActivity {

    @ViewInject(R.id.mytopic_ls_show)
    private ListView mytopic_ls_show;

    private List<MyTopic> mList = new ArrayList<>();
    private MyTopicAdapter myTopicAdapter = null;
    private Context mContext = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TopicMyRequest.SUCCESS:
                    mList.clear();
                    mList.addAll((List<MyTopic>) msg.obj);
                    myTopicAdapter.notifyDataSetChanged();
                    break;
                case TopicMyRequest.ERROR:
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
        myTopicAdapter = new MyTopicAdapter(mContext, mList);
        mytopic_ls_show.setAdapter(myTopicAdapter);
    }

    private void initData() {
        new TopicMyRequest(mHandler, "100");
        showLoading(this);
    }

    @Event(value = {R.id.mytopic_btn_goback, R.id.topic_btn_send}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.mytopic_btn_goback:
                finish();
                break;
            case R.id.topic_btn_send:
                Intent intent = new Intent(mContext, SendTopicActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
