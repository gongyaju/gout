package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.AppUtils;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.ChangePswRequest;
import com.pu.gouthelper.webservice.UserAddImageRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Requiem on 2016/05/02
 * 个人信息点击页
 */
@ContentView(R.layout.activity_myinfo)
public class MyinfoActivity extends SwipeBackActivity {


    private Context mContext;
    private static final int REQUEST_IMAGE = 2;

    private ArrayList<String> mSelectPath;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UserAddImageRequest.ERROR:
                    UIHelper.ToastMessage(mContext,""+msg.obj);
                    break;
                case UserAddImageRequest.SUCCESS:
                    UIHelper.ToastMessage(mContext,""+msg.obj);
                    finish();
                    break;
            }
            endLoading();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Event(value = {R.id.break_btn_goback, R.id.myinfo_tv_image, R.id.myinfo_tv_name, R.id.myinfo_tv_address}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.break_btn_goback:
                finish();
                break;
            case R.id.myinfo_tv_image:
                Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
                // 是否显示拍摄图片
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                // 最大可选择图片数量
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                // 选择模式
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                // 默认选择
                if (mSelectPath != null && mSelectPath.size() > 0) {
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
                }
                startActivityForResult(intent, REQUEST_IMAGE);
                break;
            case R.id.myinfo_tv_name:
                break;
            case R.id.myinfo_tv_address:
                startActivity(new Intent(mContext,AddressActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                new UserAddImageRequest(mHandler,mSelectPath.get(0));
                showLoading(this);
            }
        }
    }
}
