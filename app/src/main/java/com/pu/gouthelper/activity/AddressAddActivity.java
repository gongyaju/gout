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

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.pu.gouthelper.R;
import com.pu.gouthelper.adapter.AddressAdapter;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.bean.Address;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.DelClockRequest;
import com.pu.gouthelper.webservice.DrugClockRequest;
import com.pu.gouthelper.webservice.UserAddAddressRequest;
import com.pu.gouthelper.webservice.UserAddressListRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Requiem on 2016/3/22.
 * 增加地址
 */
@ContentView(R.layout.activity_add_address)
public class AddressAddActivity extends SwipeBackActivity {
    private Context mContext;
    @ViewInject(R.id.address_tv_name)
    private EditText address_tv_name;
    @ViewInject(R.id.address_tv_phone)
    private EditText address_tv_phone;
    @ViewInject(R.id.address_tv_address)
    private EditText address_tv_address;
    @ViewInject(R.id.address_tv_youbian)
    private EditText address_tv_youbian;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            endLoading();
            switch (msg.what) {
                case UserAddAddressRequest.SUCCESS:
                    finish();
                    break;
                case UserAddAddressRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }


    @Event(value = {R.id.address_btn_goback, R.id.address_btn_save}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_btn_goback:
                finish();
                break;
            case R.id.address_btn_save:
                String name = address_tv_name.getText().toString().trim();
                String phone = address_tv_phone.getText().toString().trim();
                String address = address_tv_address.getText().toString().trim();
                String youbian = address_tv_youbian.getText().toString().trim();
                if (StringUtils.isEmpty(name)) {
                    UIHelper.ToastMessage(mContext, "姓名不能为空");
                    return;
                }
                if (!StringUtils.isMobileNum(phone)) {
                    UIHelper.ToastMessage(mContext, "请输入正确的手机号码");
                    return;
                }
                if (StringUtils.isEmpty(address)) {
                    UIHelper.ToastMessage(mContext, "地址不能为空");
                    return;
                }
                if (StringUtils.isEmpty(youbian)) {
                    UIHelper.ToastMessage(mContext, "邮编不能为空");
                    return;
                }

                new UserAddAddressRequest(mHandler, name, phone, address, youbian);
                showLoading(mContext);
                break;
        }
    }

}
