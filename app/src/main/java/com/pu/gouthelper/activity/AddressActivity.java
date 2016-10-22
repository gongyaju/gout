package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.pu.gouthelper.R;
import com.pu.gouthelper.adapter.AddressAdapter;
import com.pu.gouthelper.bean.Address;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;

import com.pu.gouthelper.webservice.UserAddressListRequest;
import com.pu.gouthelper.webservice.UserDelImageRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Requiem on 2016/3/22.
 * 修改地址
 */
@ContentView(R.layout.activity_address)
public class AddressActivity extends SwipeBackActivity {
    private Context mContext;
    @ViewInject(R.id.address_ls_show)
    private SwipeMenuListView remind_ls_show;
    private AddressAdapter adapter = null;
    private List<Address> mlist = new ArrayList<>();


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UserAddressListRequest.SUCCESS:
                    mlist.clear();
                    mlist.addAll((List<Address>) msg.obj);
                    adapter.notifyDataSetChanged();
                    endLoading();
                    break;
                case UserAddressListRequest.ERROR:
                    mlist.clear();
                    adapter.notifyDataSetChanged();
                    endLoading();
                    break;
                case UserDelImageRequest.SUCCESS:
                    UIHelper.ToastMessage(mContext, "删除成功~");
                    new UserAddressListRequest(mHandler);
                    break;
                case UserDelImageRequest.ERROR:
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        new UserAddressListRequest(mHandler);
        showLoading(mContext);
    }

    private void initData() {
        adapter = new AddressAdapter(mContext, mlist);
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
                        //删除
                        new UserDelImageRequest(mHandler, mlist.get(position).getId());
                        showLoading(mContext);
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

    @Event(value = {R.id.address_btn_goback, R.id.address_btn_add}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_btn_goback:
                finish();
                break;
            case R.id.address_btn_add:
                Intent intent = new Intent(mContext, AddressAddActivity.class);
                startActivity(intent);
                break;
        }
    }

}
