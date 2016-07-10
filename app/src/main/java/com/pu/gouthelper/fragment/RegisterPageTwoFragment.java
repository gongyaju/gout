package com.pu.gouthelper.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.activity.RegisterActivity;
import com.pu.gouthelper.base.BaseFragment;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.webservice.MobileCodeCheckRequest;
import com.pu.gouthelper.webservice.MobileCodeRequest;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


public class RegisterPageTwoFragment extends BaseFragment {

    @ViewInject(R.id.login_tv_mobilecode)
    private EditText login_tv_mobilecode;
    @ViewInject(R.id.register_btn_next2)
    private Button register_btn_next2;
    @ViewInject(R.id.register_tv_getmsg)
    private TextView register_tv_getmsg;
    private RegisterActivity activity;
    private String code = "";

    private long startTime = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MobileCodeRequest.SUCCESS:
                    UIHelper.ToastMessage(activity, msg.obj + "");
                    break;
                case MobileCodeRequest.ERROR:
                    UIHelper.ToastMessage(activity, msg.obj + "");
                    break;
                case MobileCodeCheckRequest.SUCCESS:
                    activity.stepSet(2);
                    activity.setMobileCode(code);
                    register_btn_next2.setEnabled(true);
                    break;
                case MobileCodeCheckRequest.ERROR:
                    UIHelper.ToastMessage(activity, msg.obj + "");
                    register_btn_next2.setEnabled(true);
                    break;
                case 0x21:
                    if (startTime > 0) {
                        long time = System.currentTimeMillis() - startTime;
                        if (time > 60000) {
                            register_tv_getmsg.setText("获取短信");
                            register_tv_getmsg.setEnabled(true);
                            startTime = 0;
                            break;
                        }
                        time = 60000 - time;
                        register_tv_getmsg.setText("" + time / 1000 + "秒后重新获取");
                        mHandler.sendEmptyMessageDelayed(0x21, 1000);
                    }
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_page2, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (RegisterActivity) getActivity();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }


    @Event(value = {R.id.register_btn_next2, R.id.register_tv_getmsg}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_next2:
                try {
                    register_btn_next2.setEnabled(false);
                    code = login_tv_mobilecode.getText().toString().trim();
                    if (StringUtils.isEmpty(code)) {
                        UIHelper.ToastMessage(activity, "请输入验证码");
                    } else {
                        new MobileCodeCheckRequest(mHandler, activity.getPhonenum(), code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.register_tv_getmsg:
                try {
                    new MobileCodeRequest(mHandler, activity.getPhonenum());
                    register_tv_getmsg.setText("60秒后可重新发送");
                    startTime = System.currentTimeMillis();
                    register_tv_getmsg.setEnabled(false);
                    mHandler.sendEmptyMessage(0x21);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}
