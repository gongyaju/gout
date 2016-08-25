package com.pu.gouthelper.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.activity.SendTopicActivity;
import com.pu.gouthelper.activity.SosActivity;
import com.pu.gouthelper.activity.TopicDetailActivity;
import com.pu.gouthelper.adapter.TopicListAdapter;
import com.pu.gouthelper.base.BaseFragment;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.bean.Topic;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.pulltorefresh.PullToRefreshBase;
import com.pu.gouthelper.ui.pulltorefresh.PullToRefreshListView;
import com.pu.gouthelper.webservice.TopicListRequest;


import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 互动社区
 */
public class InteractiveFragment extends BaseFragment {

    @ViewInject(R.id.gout_ls_msg)
    private PullToRefreshListView listView;
    private Context mContext;
    private TopicListAdapter topicListAdapter;
    private List<Topic> topics = new ArrayList<>();

    private int page = 0;
    private int datalength = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TopicListRequest.SUCCESS:
                    List<Topic> mList = (List<Topic>) msg.obj;
                    datalength = msg.arg1;
                    topics.addAll(mList);
                    topicListAdapter.notifyDataSetChanged();
                    break;
                case TopicListRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
            }
            listView.onRefreshComplete();
            listView.updateLoadMoreViewText(topics);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_interactive, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initView();
        loadData();
    }

    private void initView() {
        topicListAdapter = new TopicListAdapter(mContext, topics);
        listView.withLoadMoreView();
        listView.setAdapter(topicListAdapter);
        // 下拉刷新
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 0;
                topics.clear();
                loadData();
            }
        });
        // 加载更多
        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (page * F.PAGE_SIZE <= datalength) {
                    loadData();
                } else {
                    listView.setLoadMoreViewTextNoMoreData();
                }
            }
        });
        // 点击事件
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(mContext, TopicDetailActivity.class);
                intent.putExtra("id", topics.get(i - 1).getId());
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        listView.withLoadMoreView();
        new TopicListRequest(mHandler, "" + page);
        page++;
    }


    @Event(value = {R.id.topic_btn_send}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.topic_btn_send:
                Intent intent = new Intent(mContext, SendTopicActivity.class);
                startActivity(intent);
                break;
        }
    }
}
