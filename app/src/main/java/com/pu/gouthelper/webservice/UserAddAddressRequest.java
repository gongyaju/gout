package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.MyInfo;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;


/**
 * Created by Requiem on 2016/3/13
 * 添加收货地址
 */
public class UserAddAddressRequest extends BaseRequest {
    public static final int SUCCESS = 0x11;
    public static final int ERROR = 0x12;
    private Handler mHandler;

    /**
     *
     * @param mHandler
     * @param username 收货人
     * @param phone 电话
     * @param address 地址
     * @param zipcode 邮编
     */
    public UserAddAddressRequest(Handler mHandler, String username, String phone, String address, String zipcode) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.URL_USER_ADD_ADDRESS);
        params.addBodyParameter("username", username);
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("address", address);
        params.addBodyParameter("zipcode", zipcode);
        sendPost(params);
    }


    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("state");
        if (state == 1) {
            Message msg = mHandler.obtainMessage();
            msg.obj = object.getString("msg");
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
