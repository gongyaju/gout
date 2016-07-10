package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.SharedPreferences;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.LoginResult;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;


/**
 * Created by Requiem on 2016/3/13
 */
public class LoginRequest extends BaseRequest {
    public static final int SUCCESS = 0x11;
    public static final int ERROR = 0x12;
    private Handler mHandler;

    /**
     * @param mHandler
     * @param mobile:  手机号
     * @param passwd:  密码
     */
    public LoginRequest(Handler mHandler, String mobile, String passwd) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.LOGIN);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("passwd", passwd);
        sendPost(params);
    }


    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("state");
        // {"state":1,"date":{"id":"3","mobile":"18710278553","nickname":"","avatar":"","age":"0","sex":"0","birthday":"0","history":"0","height":"0","weight":"0","tarea":"","drug":"","state":"1","tm":"1458391324"}}
        if (state == 1) {
            LoginResult login_msg = JSON.parseObject(object.getString("date"), LoginResult.class);
            SharedPreferences.getInstance().putString("mobile", login_msg.getMobile());
            SharedPreferences.getInstance().putString("userid", login_msg.getId());
            SharedPreferences.getInstance().putString("nickname", login_msg.getNickname());
            Message msg = mHandler.obtainMessage();
            msg.obj = JSON.parseObject(object.getString("date"), LoginResult.class);
            msg.what = SUCCESS;
            mHandler.sendMessage(msg);
        } else {
            Message msg = mHandler.obtainMessage();
            msg.obj = object.getString("msg");
            msg.what = ERROR;
            mHandler.sendMessage(msg);
        }

    }

    public void onFailed(HttpException arg0, String arg1) {
        Message msg = mHandler.obtainMessage();
        msg.obj = "请求服务器失败";
        msg.what = ERROR;
        mHandler.sendMessage(msg);
    }

}
