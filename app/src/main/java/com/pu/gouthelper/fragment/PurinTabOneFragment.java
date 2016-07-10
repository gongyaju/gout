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

import com.pu.gouthelper.R;
import com.pu.gouthelper.activity.PurinDetailActivity;
import com.pu.gouthelper.adapter.PurinListAdapter;
import com.pu.gouthelper.base.BaseFragment;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.bean.PurinFoodEntity;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.webservice.PurinListInfoRequest;
import com.pu.gouthelper.webservice.PurinListRequest;


import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


public class PurinTabOneFragment extends BaseFragment {

    @ViewInject(R.id.purin_gv_show)
    private GridView purin_gv_show;

    private PurinListAdapter purinListAdapter;
    private List<PurinFoodEntity> mList = new ArrayList<>();
    private Context mContext;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PurinListRequest.SUCCESS:
                    List<PurinFoodEntity> plist = (List<PurinFoodEntity>) msg.obj;
                    mList.clear();
                    mList.addAll(plist);
                    purinListAdapter.notifyDataSetChanged();
                    break;
                case PurinListRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;

            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purin_tab1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        purinListAdapter = new PurinListAdapter(mContext, mList);
        purin_gv_show.setAdapter(purinListAdapter);
        new PurinListRequest(mHandler, "9", "1");
        initView();
    }

    private void initView() {
        purin_gv_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PurinFoodEntity entity = mList.get(position);
                Intent it = new Intent(mContext, PurinDetailActivity.class);
                it.putExtra("id", entity.getId());
                startActivity(it);
            }
        });
    }
}
