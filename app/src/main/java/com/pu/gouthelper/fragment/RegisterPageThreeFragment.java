package com.pu.gouthelper.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pu.gouthelper.R;
import com.pu.gouthelper.activity.RegisterActivity;
import com.pu.gouthelper.activity.RegisterSuccessActivity;
import com.pu.gouthelper.base.BaseFragment;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.webservice.RegisterRequest;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


public class RegisterPageThreeFragment extends BaseFragment {

    @ViewInject(R.id.register_edt_pass1)
    private EditText register_edt_pass1;
    @ViewInject(R.id.register_edt_pass2)
    private EditText register_edt_pass2;
    @ViewInject(R.id.register_btn_submit)
    private Button register_btn_submit;
    private RegisterActivity activity;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RegisterRequest.SUCCESS:
                    UIHelper.ToastMessage(activity, msg.obj + "");
                    startActivity(new Intent(activity, RegisterSuccessActivity.class));
                    activity.finish();
                    break;
                case RegisterRequest.ERROR:
                    UIHelper.ToastMessage(activity, msg.obj + "");
                    break;
            }
            activity.endLoading();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_page3, container, false);
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

    @Event(value = {R.id.register_btn_submit}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_submit:
                String psw1 = register_edt_pass1.getText().toString().trim();
                String psw2 = register_edt_pass2.getText().toString().trim();
                if (StringUtils.isEmpty(psw1)) {
                    UIHelper.ToastMessage(activity, "密码不能为空~");
                    return;
                }
                if (!psw1.equals(psw2)) {
                    UIHelper.ToastMessage(activity, "两次输入的密码不一样~");
                    return;
                }
                register_btn_submit.setEnabled(false);
                new RegisterRequest(mHandler, activity.getPhonenum(), activity.getMobileCode(), psw2);
                activity.showLoading(activity);
                break;
        }
    }


}
