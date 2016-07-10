package com.pu.gouthelper.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mob.tools.utils.UIHandler;
import com.pu.gouthelper.R;
import com.pu.gouthelper.base.SharedPreferences;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.common.AppManager;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.utils.ShareUtils;
import com.pu.gouthelper.webservice.LoginRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import android.os.Handler.Callback;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by Requiem on 16/03/09.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseFragmentActivity implements Callback, PlatformActionListener {

    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_COMPLETE = 5;

    @ViewInject(R.id.login_tv_username)
    private EditText login_tv_username;
    @ViewInject(R.id.login_tv_password)
    private EditText login_tv_password;
    @ViewInject(R.id.login_cbx_remenber)
    private CheckBox login_cbx_remenber;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LoginRequest.SUCCESS:
                    UIHelper.showHome(LoginActivity.this);
                    break;
                case LoginRequest.ERROR:
                    UIHelper.ToastMessage(LoginActivity.this, msg.obj + "");
                    break;
            }
            endLoading();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(this);

    }

    @Override
    protected void onDestroy() {
        ShareSDK.stopSDK(this);
        super.onDestroy();
    }

    @Event(value = {R.id.login_btn_submit, R.id.login_btn_byqq, R.id.login_btn_bywchat, R.id.login_btn_sos, R.id.login_btn_register}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_submit:
                String username = login_tv_username.getText().toString().trim();
                String password = login_tv_password.getText().toString().trim();

                if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                    UIHelper.ToastMessage(this, "您忘记填写用户名或密码啦~");
                    return;
                }
                new LoginRequest(mHandler, username, password);
                if (login_cbx_remenber.isChecked()) {
                    SharedPreferences.getInstance().putString("username", username);
                    SharedPreferences.getInstance().putString("password", password);
                } else {
                    SharedPreferences.getInstance().putString("username", "");
                    SharedPreferences.getInstance().putString("password", "");
                }
                showLoading(this);
                break;
            case R.id.login_btn_byqq:
                authorize(new QQ(this));
                break;
            case R.id.login_btn_bywchat:
                authorize(new Wechat(this));
                break;
            case R.id.login_btn_sos:
                Intent intent = new Intent(this, SosActivity.class);
                startActivity(intent);
                break;
            case R.id.login_btn_register:
                Intent intent_register = new Intent(this, RegisterActivity.class);
                startActivity(intent_register);
                break;
        }
    }

    private void authorize(Platform plat) {
        if (plat.isValid()) {
            String userId = plat.getDb().getUserId();
            if (!TextUtils.isEmpty(userId)) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                login(plat.getName(), userId, null);
                return;
            }
        }
        plat.setPlatformActionListener(this);
        plat.SSOSetting(true);
        plat.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int action,
                           HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            login(platform.getName(), platform.getDb().getUserId(), res);
        }
        System.out.println(res);
        System.out.println("------User Name ---------" + platform.getDb().getUserName());
        System.out.println("------User ID ---------" + platform.getDb().getUserId());
    }

    public void onError(Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        t.printStackTrace();
    }

    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }

    private void login(String plat, String userId, HashMap<String, Object> userInfo) {
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND:
                UIHelper.ToastMessage(this,"MSG_USERID_FOUND");
                break;
            case MSG_LOGIN:
                UIHelper.ToastMessage(this,"MSG_LOGIN");
                break;
            case MSG_AUTH_CANCEL:
                UIHelper.ToastMessage(this, "MSG_AUTH_CANCEL");
                break;
            case MSG_AUTH_ERROR:
                UIHelper.ToastMessage(this, "MSG_AUTH_ERROR");
                break;
            case MSG_AUTH_COMPLETE:
                UIHelper.ToastMessage(this, "MSG_AUTH_COMPLETE");
                break;
        }
        return false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            UIHelper.ToastMessage(this, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            AppManager.getAppManager().AppExit(this);
        }
    }
}
