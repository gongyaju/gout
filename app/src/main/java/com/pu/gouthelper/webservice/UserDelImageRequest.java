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
 * 删除收藏地址
 */
public class UserDelImageRequest extends BaseRequest {
    public static final int SUCCESS = 0x21;
    public static final int ERROR = 0x22;
    private Handler mHandler;


    public UserDelImageRequest(Handler mHandler, String id) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.URL_USER_DEL_ADDRESS);
        params.addBodyParameter("id", id);
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
