package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.adapter.BreakRecordAdapter;
import com.pu.gouthelper.bean.AttackRecord;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.utils.DateUtil;
import com.pu.gouthelper.utils.Time;
import com.pu.gouthelper.webservice.AttackRecordRequest;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Requiem on 2016/3/22.
 * 发作记录
 */
@ContentView(R.layout.activity_center_breackrecord)
public class CenterBreakRecordActivity extends SwipeBackActivity {

    @ViewInject(R.id.break_tv_times)
    private TextView break_tv_times;//平安度过天
    @ViewInject(R.id.break_tv_time)
    private TextView break_tv_time;//上次发作时间
    @ViewInject(R.id.break_ls_record)
    private ListView break_ls_record;
    private Context mContext;
    private List<AttackRecord> mList = new ArrayList<>();
    private BreakRecordAdapter mAdapter = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AttackRecordRequest.SUCCESS:
                    mList.clear();
                    mList.addAll((List<AttackRecord>) msg.obj);
                    mAdapter.notifyDataSetChanged();
                    setData();
                    break;
                case AttackRecordRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
            }
            endLoading();
        }
    };

    private void setData() {
        String lastTime = DateUtil.date2String(Long.parseLong(mList.get(0).getTm()) * 1000, "yyyy.MM.dd");
        try {
            break_tv_times.setText(Time.daysBetween(lastTime, DateUtil.date2yyyyMMdd(new Date())) + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        break_tv_time.setText("上次发作: " + lastTime);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.main_click);
        mContext = this;
        initData();
    }


    private void initData() {
        mAdapter = new BreakRecordAdapter(mContext, mList);
        break_ls_record.setAdapter(mAdapter);
        new AttackRecordRequest(mHandler, 40 + "");
        showLoading(mContext);
        break_ls_record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttackRecord entity = mList.get(position);
                Intent it = new Intent(mContext, BreakIteminfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("record", entity);
                it.putExtras(bundle);
                startActivity(it);
            }
        });
    }

    @Event(value = {R.id.break_btn_goback, R.id.break_btn_more, R.id.break_tv_click, R.id.break_tv_next}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.break_btn_goback:
                finish();
                break;
            case R.id.break_btn_more:
                UIHelper.ToastMessage(this, "more");
                break;
            case R.id.break_tv_click:
                Intent it_write_recore = new Intent(this, WriteBreakRecordActivity.class);
                startActivity(it_write_recore);
                break;
            case R.id.break_tv_next:
                startActivity(new Intent(this, CenterMyInfoActivity.class));
                break;
        }
    }
}
