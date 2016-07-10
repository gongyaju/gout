package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.adapter.DrugListAdapter;
import com.pu.gouthelper.adapter.MyMessageAdapter;
import com.pu.gouthelper.bean.GoutDrug;
import com.pu.gouthelper.bean.GoutMessage;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.pulltorefresh.PullToRefreshBase;
import com.pu.gouthelper.ui.pulltorefresh.PullToRefreshListView;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.GoutDrugListRequest;
import com.pu.gouthelper.webservice.MessageRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * Created by Requiem on 2016/3/22.
 * 我的消息
 */
@ContentView(R.layout.activity_center_message)
public class CenterMessageActivity extends SwipeBackActivity {
    @ViewInject(R.id.mymsg_ls_show)
    private PullToRefreshListView listView;
    @ViewInject(R.id.mymsg_ls_dx)
    private LinearLayout mymsg_ls_dx;
    private Context mContext;
    private MyMessageAdapter adapter = null;
    private List<GoutMessage> mList = new ArrayList<>();

    private DynamicBox box;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageRequest.SUCCESS:
                    List<GoutMessage> goutDrugList = (List<GoutMessage>) msg.obj;
                    mList.clear();
                    mList.addAll(goutDrugList);
                    adapter.notifyDataSetChanged();
                    break;
                case MessageRequest.ERROR:
                    box.showCustomView("hint_no_message");
                    break;
            }
            listView.onRefreshComplete();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initView();
        loadData();

    }

    private void initView() {
        box = new DynamicBox(this, mymsg_ls_dx);
        View customView = getLayoutInflater().inflate(R.layout.hint_no_message, null, false);
        box.addCustomView(customView,"hint_no_message");

//        adapter = new MyMessageAdapter(mContext, mList);
//        listView.setAdapter(adapter);
//        // 下拉刷新
//        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                loadData();
//            }
//        });
//        // 加载更多
//        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
//            @Override
//            public void onLastItemVisible() {
//                loadData();
//            }
//        });
//        // 点击事件
//        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
    }

    private void loadData() {
        new MessageRequest(mHandler, "100");
    }


}
