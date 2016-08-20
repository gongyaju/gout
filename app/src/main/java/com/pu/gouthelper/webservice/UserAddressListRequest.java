package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.Address;
import com.pu.gouthelper.bean.MyInfo;
import com.pu.gouthelper.bean.Topic;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import java.util.List;


/**
 * Created by Requiem on 2016/3/13
 * 收货地址列表
 */
public class UserAddressListRequest extends BaseRequest {
    public static final int SUCCESS = 0x11;
    public static final int ERROR = 0x12;
    private Handler mHandler;


    public UserAddressListRequest(Handler mHandler) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.URL_USER_ADDRESS);
        params.addBodyParameter("len", F.PAGE_SIZE+"");
        sendGet(params);
    }


    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("state");

        if (state == 1) {
            List<Address> mList = JSONArray.parseArray(object.getString("data"), Address.class);
            Message msg = mHandler.obtainMessage();
            msg.obj = mList;
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
