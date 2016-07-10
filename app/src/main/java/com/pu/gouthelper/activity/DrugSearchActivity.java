package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.pu.gouthelper.R;

import com.pu.gouthelper.adapter.DrugSearchAdapter;
import com.pu.gouthelper.bean.GoutDrug;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.GoutDrugListRequest;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by Requiem on 2016/3/22.
 * 药品查询
 */
@ContentView(R.layout.activity_search_drug)
public class DrugSearchActivity extends SwipeBackActivity {
    @ViewInject(R.id.drug_edt_search)
    private EditText drug_edt_search;
    @ViewInject(R.id.drug_ls_search)
    private ListView drug_ls_search;

    private final int TEXT_CHANGE = 0X21;
    private Context mContext;
    private List<GoutDrug> mList = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TEXT_CHANGE:
                    new GoutDrugListRequest(mHandler, "20", msg.obj + "");
                    break;
                case GoutDrugListRequest.ERROR:
                    break;
                case GoutDrugListRequest.SUCCESS:
                    mList = (List<GoutDrug>) msg.obj;
                    drug_ls_search.setAdapter(new DrugSearchAdapter(mContext, mList));
                    break;
            }
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
        drug_edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Message msg = mHandler.obtainMessage();
                msg.what = TEXT_CHANGE;
                msg.obj = s.toString();
                mHandler.sendMessageDelayed(msg, 1000);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initData() {
        drug_ls_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList==null){
                    return;
                }
                GoutDrug entity = mList.get(position);
                UIHelper.ToastMessage(mContext,entity.getTitle());
                Intent it = new Intent(mContext, DrugDetailActivity.class);
                it.putExtra("id", entity.getId());
                startActivity(it);
            }
        });
    }

    @Event(value = {R.id.search_tv_cancel}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_tv_cancel:
                finish();

                break;
        }
    }
}
