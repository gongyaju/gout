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
import android.widget.GridView;
import android.widget.ListView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.activity.GoutMsgDetailActivity;
import com.pu.gouthelper.activity.PurinDetailActivity;
import com.pu.gouthelper.adapter.PurinKnowAdapter;
import com.pu.gouthelper.adapter.PurinListAdapter;
import com.pu.gouthelper.base.BaseFragment;
import com.pu.gouthelper.bean.PurinFoodEntity;
import com.pu.gouthelper.bean.PurinNews;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.webservice.PurinListRequest;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


public class PurinDetailTabTwoFragment extends BaseFragment {


    @ViewInject(R.id.purin_ls_detail)
    private ListView purin_ls_detail;


    private Context mContext;
    private List<PurinNews> rList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purin_detail, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initView();
    }

    private void initView() {
        purin_ls_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, GoutMsgDetailActivity.class);
                intent.putExtra("id", rList.get(position).getId());
                startActivity(intent);
            }
        });
    }

    public void setdata(List<PurinNews> rList) {
        this.rList = rList;
        if (rList != null) {
            purin_ls_detail.setAdapter(new PurinKnowAdapter(mContext, rList));
        }
    }
}
