package com.pu.gouthelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.SharedPreferences;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Requiem on 2016/3/22.
 * 设置
 */
@ContentView(R.layout.activity_center_setting)
public class CenterSettingActivity extends SwipeBackActivity {
    @ViewInject(R.id.open)
    private Switch open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }


    private void initView() {
        open.setChecked(SharedPreferences.getInstance().getBoolean("center_set_msgopen", true));
        open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.getInstance().putBoolean("center_set_msgopen", isChecked);
            }
        });
    }

    private void initData() {
    }

    @Event(value = {R.id.setting_tv_msg, R.id.setting_tv_psw, R.id.setting_tv_complain, R.id.setting_tv_us, R.id.setting_tv_hlepme, R.id.setting_tv_exit}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_tv_msg:

                break;
            case R.id.setting_tv_psw:
                startActivity(new Intent(this, SettingPasswordActivity.class));
                break;
            case R.id.setting_tv_complain:
                startActivity(new Intent(this, SettingComplainActivity.class));
                break;
            case R.id.setting_tv_us:
                startActivity(new Intent(this, SettingAboutUsActivity.class));
                break;
            case R.id.setting_tv_hlepme:
                UIHelper.ToastMessage(this, "");
                break;
            case R.id.setting_tv_exit:
                SharedPreferences.getInstance().putString("username", "");
                SharedPreferences.getInstance().putString("password", "");
                SharedPreferences.getInstance().getString("userid", "");
                SharedPreferences.getInstance().putString("mobile", "");
                UIHelper.showLogin(CenterSettingActivity.this);
                finish();
                break;
        }
    }
}
