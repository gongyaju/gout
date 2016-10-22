package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.pu.gouthelper.R;
import com.pu.gouthelper.adapter.RemindAdapter;
import com.pu.gouthelper.bean.RemindEntity;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.utils.AlarmManagerUtil;
import com.pu.gouthelper.webservice.DelClockRequest;
import com.pu.gouthelper.webservice.DrugClockRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Requiem on 2016/3/22.
 * 吃药提醒
 */
@ContentView(R.layout.activity_center_remind)
public class CenterRemindActivity extends SwipeBackActivity {

    private Context mContext;
    @ViewInject(R.id.remind_ls_show)
    private SwipeMenuListView remind_ls_show;
    private RemindAdapter adapter = null;
    private List<RemindEntity> mlist = new ArrayList<>();


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DrugClockRequest.SUCCESS:
                    mlist.clear();
                    mlist.addAll((List<RemindEntity>) msg.obj);
                    adapter.notifyDataSetChanged();
                    endLoading();
                    break;
                case DrugClockRequest.ERROR:
                    mlist.clear();
                    adapter.notifyDataSetChanged();
                    UIHelper.ToastMessage(mContext, "您还没有添加提醒呢~");
                    endLoading();
                    break;
                case DelClockRequest.SUCCESS:
                    UIHelper.ToastMessage(mContext, "删除成功~");
                    new DrugClockRequest(mHandler, "40");
                    showLoading(mContext);
                    break;
                case DelClockRequest.ERROR:
                    UIHelper.ToastMessage(mContext, "删除失败~");
                    endLoading();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
        new DrugClockRequest(mHandler, "40");
        showLoading(mContext);

        AlarmManagerUtil.setAlarm(this, 1, 17, 30, 3, 0, "提醒内容", 2);
        AlarmManagerUtil.setAlarm(this, 1, 17, 31, 2, 0, "提醒内容", 2);
        AlarmManagerUtil.setAlarm(this, 1, 17, 32, 1, 0, "提醒内容", 2);
    }

    private void initData() {
        adapter = new RemindAdapter(mContext, mlist);
        remind_ls_show.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };

        remind_ls_show.setMenuCreator(creator);
        remind_ls_show.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        //删除提醒
                        new DelClockRequest(mHandler, mlist.get(position).getId());
                        break;
                }
                return false;
            }
        });

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Event(value = {R.id.remind_btn_goback, R.id.remind_btn_add}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.remind_btn_goback:
                finish();
                break;
            case R.id.remind_btn_add:
                Intent intent = new Intent(mContext, RemindAddActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        new DrugClockRequest(mHandler, "60");
    }
}
