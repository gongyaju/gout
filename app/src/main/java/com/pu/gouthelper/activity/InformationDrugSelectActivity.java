package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.adapter.InformationDrugAdapter;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.bean.GoutDrug;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.GoutDrugListRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Requiem on 16/04/17.
 * 完善资料 用过的药物
 */
@ContentView(R.layout.activity_infomation_drug)
public class InformationDrugSelectActivity extends SwipeBackActivity {
    @ViewInject(R.id.infomation_gv_druglist)
    private GridView infomation_gv_druglist;
    private InformationDrugAdapter informationDrugAdapter = null;
    private List<GoutDrug> drugs = new ArrayList<>();

    private Context mContext;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GoutDrugListRequest.SUCCESS:
                    List<GoutDrug> mList = (List<GoutDrug>) msg.obj;
                    drugs.clear();
                    drugs.addAll(mList);
                    informationDrugAdapter.notifyDataSetChanged();
                    break;
                case GoutDrugListRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        new GoutDrugListRequest(mHandler, F.PAGE_SIZE + "", "");
        informationDrugAdapter = new InformationDrugAdapter(mContext, drugs);
        infomation_gv_druglist.setAdapter(informationDrugAdapter);
        infomation_gv_druglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoutDrug entity = drugs.get(position);
                if (entity.isSelect()) {
                    entity.setIsSelect(false);
                } else {
                    entity.setIsSelect(true);
                }
                informationDrugAdapter.notifyDataSetChanged();
            }
        });
    }

    @Event(value = {R.id.infomation_btn_submit, R.id.recipe_btn_goback}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.infomation_btn_submit:
                StringBuilder drug = new StringBuilder();
                for (GoutDrug e : drugs) {
                    if (e.isSelect()) {
                        drug.append(e.getId() + ",");
                    }
                }
                Intent it = new Intent(this, InformationActivity.class);
                it.putExtra("drug", drug.toString());
                setResult(1, it);
                finish();
                break;
            case R.id.recipe_btn_goback:
                finish();
                break;
        }
    }
}
