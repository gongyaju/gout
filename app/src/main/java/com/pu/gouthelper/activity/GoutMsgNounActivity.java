package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.adapter.NounParsetAdapter;
import com.pu.gouthelper.bean.GoutKnow;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.GoutKnowListRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Requiem on 2016/3/22.
 * 名词解释
 */
@ContentView(R.layout.activity_goutmsg_noun)
public class GoutMsgNounActivity extends SwipeBackActivity {
    private Context mContext;
    @ViewInject(R.id.noun_ls_show)
    private ListView noun_ls_show;
    private List<GoutKnow> goutKnowList = new ArrayList<>();
    private NounParsetAdapter adapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GoutKnowListRequest.SUCCESS:
                    List<GoutKnow> mList = (List<GoutKnow>) msg.obj;
                    goutKnowList.clear();
                    goutKnowList.addAll(mList);
                    adapter.notifyDataSetChanged();
                    break;
                case GoutKnowListRequest.ERROR:
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
        noun_ls_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, GoutMsgDetailActivity.class);
                intent.putExtra("id", goutKnowList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        adapter = new NounParsetAdapter(mContext, goutKnowList);
        noun_ls_show.setAdapter(adapter);
        new GoutKnowListRequest(mHandler, "100", "3","0");//名词
        showLoading(mContext);
    }

    @Event(value = {R.id.noun_btn_goback}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.noun_btn_goback:
                finish();
                break;
        }
    }
}
