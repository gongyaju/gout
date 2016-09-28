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

import com.pu.gouthelper.R;
import com.pu.gouthelper.activity.DrugSearchActivity;
import com.pu.gouthelper.activity.GoutMsgDetailActivity;
import com.pu.gouthelper.activity.GoutMsgNounActivity;
import com.pu.gouthelper.adapter.GoutMsgAdapter;
import com.pu.gouthelper.base.BaseFragment;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.bean.GoutKnow;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.pulltorefresh.PullToRefreshBase;
import com.pu.gouthelper.ui.pulltorefresh.PullToRefreshListView;
import com.pu.gouthelper.webservice.GoutKnowCateRequest;
import com.pu.gouthelper.webservice.GoutKnowListRequest;


import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 痛风常识
 */
public class GoutMsgFragment extends BaseFragment {

    @ViewInject(R.id.gout_ls_msg)
    PullToRefreshListView listView;
    private Context mContext;
    private List<GoutKnow> goutKnowList = new ArrayList<>();
    private GoutMsgAdapter msgAdapter = null;

    private int page = 1;
    private int pagesize = F.PAGE_SIZE;
    private int datalength = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GoutKnowListRequest.SUCCESS:
                    datalength = msg.arg1;
                    List<GoutKnow> mList = (List<GoutKnow>) msg.obj;
                    goutKnowList.addAll(mList);
                    msgAdapter.notifyDataSetChanged();
                    break;
                case GoutKnowListRequest.ERROR:
                    UIHelper.ToastMessage(mContext, "您的网络开小差了~");
                    break;
            }
            listView.onRefreshComplete();
            listView.updateLoadMoreViewText(goutKnowList);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_goutmsg, container, false);
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
        msgAdapter = new GoutMsgAdapter(mContext, goutKnowList);

        listView.setAdapter(msgAdapter);
        // 下拉刷新
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                goutKnowList.clear();
                loadData();
            }
        });
        // 加载更多
        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (page * pagesize <= datalength) {
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
                Intent intent = new Intent(mContext, GoutMsgDetailActivity.class);
                intent.putExtra("id", goutKnowList.get(i - 1).getId());
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        listView.withLoadMoreView();
        new GoutKnowListRequest(mHandler, pagesize + "", "" + page, "2");//常识
        page++;
        // new GoutKnowCateRequest(mHandler);
    }


    @Event(value = {R.id.gout_tv_noun}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.gout_tv_noun:
                Intent intent_search = new Intent(mContext, GoutMsgNounActivity.class);
                startActivity(intent_search);
                break;
        }
    }
}
