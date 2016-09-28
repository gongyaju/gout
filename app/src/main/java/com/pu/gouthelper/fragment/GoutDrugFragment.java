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
import com.pu.gouthelper.activity.DrugDetailActivity;
import com.pu.gouthelper.activity.DrugSearchActivity;
import com.pu.gouthelper.activity.PurinDetailActivity;
import com.pu.gouthelper.activity.PurinSearchActivity;
import com.pu.gouthelper.adapter.DrugListAdapter;
import com.pu.gouthelper.base.BaseFragment;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.bean.GoutDrug;
import com.pu.gouthelper.bean.PurinFoodEntity;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.pulltorefresh.PullToRefreshBase;
import com.pu.gouthelper.ui.pulltorefresh.PullToRefreshListView;
import com.pu.gouthelper.webservice.GoutDrugInfoRequest;
import com.pu.gouthelper.webservice.GoutDrugListRequest;
import com.pu.gouthelper.webservice.GoutKnowListRequest;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 医药口碑
 */
public class GoutDrugFragment extends BaseFragment {

    @ViewInject(R.id.gout_ls_msg)
    PullToRefreshListView listView;
    private Context mContext;
    private DrugListAdapter adapter = null;
    private List<GoutDrug> mList = new ArrayList<>();

    private int page = 1;
    private int datalength = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GoutDrugListRequest.SUCCESS:
                    datalength = msg.arg1;
                    List<GoutDrug> goutDrugList = (List<GoutDrug>) msg.obj;
                    mList.addAll(goutDrugList);
                    adapter.notifyDataSetChanged();
                    break;
                case GoutDrugListRequest.ERROR:
                    UIHelper.ToastMessage(mContext, "您的网络开小差了~");
                    break;
            }
            listView.onRefreshComplete();
            listView.updateLoadMoreViewText(mList);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_goutdrug, container, false);
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
        adapter = new DrugListAdapter(mContext, mList);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                mList.clear();
                loadData();
            }
        });

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
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    GoutDrug goutDrug = mList.get(i - 1);
                    Intent it = new Intent(mContext, DrugDetailActivity.class);
                    it.putExtra("id", goutDrug.getId());
                    startActivity(it);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadData() {
        listView.withLoadMoreView();
        new GoutDrugListRequest(mHandler, page + "");
        page++;
    }


    @Event(value = {R.id.drug_edt_search}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.drug_edt_search:
                Intent intent_search = new Intent(mContext, DrugSearchActivity.class);
                startActivity(intent_search);
                break;
        }
    }
}
