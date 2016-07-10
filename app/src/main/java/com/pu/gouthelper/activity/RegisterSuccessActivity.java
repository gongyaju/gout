package com.pu.gouthelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.fragment.RegisterPageOneFragment;
import com.pu.gouthelper.fragment.RegisterPageThreeFragment;
import com.pu.gouthelper.fragment.RegisterPageTwoFragment;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * Created by Requiem on 16/03/13.
 * 注册成功
 */
@ContentView(R.layout.activity_register_success)
public class RegisterSuccessActivity extends SwipeBackActivity {
    @ViewInject(R.id.register_tv_count)
    private TextView register_tv_count;//你是第--位小酸人\n你咋才来呢?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Event(value = {R.id.register_tv_tohealthcenter, R.id.register_btn_go}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_tv_tohealthcenter:
                startActivity(new Intent(this, InformationActivity.class));
                finish();
                break;
            case R.id.register_btn_go:
                finish();
                break;
        }
    }
}
