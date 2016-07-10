package com.pu.gouthelper.fragment;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pu.gouthelper.R;
import com.pu.gouthelper.activity.RegisterActivity;
import com.pu.gouthelper.base.BaseFragment;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.SharedPreferences;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.ui.UIHelper;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


public class RegisterPageOneFragment extends BaseFragment {

    @ViewInject(R.id.register_tv_phone)
    private EditText register_tv_phone;
    private RegisterActivity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_page1, container, false);
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

    @Event(value = {R.id.register_btn_next1}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_next1:
                String phone_num = register_tv_phone.getText().toString().trim();
                if (StringUtils.isMobileNum(phone_num)) {
                    activity.stepSet(1);
                    activity.setPhonenum(phone_num);
                    SharedPreferences.getInstance().putString("mobile",phone_num);
                } else {
                    UIHelper.ToastMessage(activity, "请输入正确的手机号码~");
                }

                break;
        }
    }


}
