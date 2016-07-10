package com.pu.gouthelper.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

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
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends SwipeBackActivity {
    @ViewInject(R.id.register_fl_fragment)
    private FrameLayout register_fl_fragment;
    private FragmentManager fragmentManager;
    private RegisterPageOneFragment registerPageOneFragment;
    private RegisterPageTwoFragment registerPageTwoFragment;
    private RegisterPageThreeFragment registerPageThreeFragment;
    private String phonenum = "";
    private String mobileCode="";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            }
            endLoading();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
    }


    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public void stepSet(int index) {
        setTabSelection(index);
    }

    private void setTabSelection(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (registerPageOneFragment == null) {
                    registerPageOneFragment = new RegisterPageOneFragment();
                    transaction.add(R.id.register_fl_fragment, registerPageOneFragment);
                } else {
                    transaction.show(registerPageOneFragment);
                }
                break;
            case 1:
                if (registerPageTwoFragment == null) {
                    registerPageTwoFragment = new RegisterPageTwoFragment();
                    transaction.add(R.id.register_fl_fragment, registerPageTwoFragment);
                } else {
                    transaction.show(registerPageTwoFragment);
                }
                break;
            case 2:
                if (registerPageThreeFragment == null) {
                    registerPageThreeFragment = new RegisterPageThreeFragment();
                    transaction.add(R.id.register_fl_fragment, registerPageThreeFragment);
                } else {
                    transaction.show(registerPageThreeFragment);
                }
                break;

        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (registerPageOneFragment != null) {
            transaction.hide(registerPageOneFragment);
        }
        if (registerPageTwoFragment != null) {
            transaction.hide(registerPageTwoFragment);
        }
        if (registerPageThreeFragment != null) {
            transaction.hide(registerPageThreeFragment);
        }
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }
}
