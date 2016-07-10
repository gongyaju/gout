package com.pu.gouthelper.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.adapter.SosListAdapter;

import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;



import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Requiem on 16/03/13.
 */
@ContentView(R.layout.activity_sos)
public class SosActivity extends SwipeBackActivity {

    @ViewInject(R.id.sos_ls_show)
    private ListView sos_ls_show;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.transparent);//通知栏所需颜色
        mList.add("尽量停止一切工作或应酬，卧床休息，平放患处尽量停止一切工作或应酬，卧床休息，");
        mList.add("尽量停止一切工作或应酬，卧床休息，平放患处尽量停止一切工作或应酬，卧床休息，平放患处尽量停止一切工作或应");
        mList.add("尽量停止一切工作或应酬，卧床休息，平放患处尽量停止一切工作或应酬，卧床休息，平放患处尽量");
        mList.add("尽量停止一切工作或应酬，卧床休息，平放患处尽量停止一切工作或应酬，卧床休息，平放患处尽量停止一切工作或应酬，卧");
        sos_ls_show.setAdapter(new SosListAdapter(this, mList));
    }

    @Event(value = {R.id.sos_btn_close}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.sos_btn_close:
                finish();
                break;
        }
    }
}
