package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.URLlist;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;


/**
 * Created by Requiem on 2016/3/13
 * 验证短信验证码
 */
public class MobileCodeCheckRequest extends BaseRequest {
    public static final String TAG = "CollectAddRequest";
    public static final int SUCCESS = 0x13;
    public static final int ERROR = 0x14;
    private Handler mHandler;

    /**
     * @param mHandler
     * @param mobile:  手机号
     * @param code:    验证码
     */
    public MobileCodeCheckRequest(Handler mHandler, String mobile, String code) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.VERIFY_MOBILE_CODE);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("code", code);
        sendGet(params);
    }


    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("state");
        if (state == 1) {
            Message msg = mHandler.obtainMessage();
            msg.obj = object.getString("date");
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