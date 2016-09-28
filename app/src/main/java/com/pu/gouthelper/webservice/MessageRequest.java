package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.GoutMessage;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import java.util.List;


/**
 * Created by Requiem on 2016/3/27
 * 我的消息
 */
public class MessageRequest extends BaseRequest {
    public static final int SUCCESS = 0x11;
    public static final int ERROR = 0x12;
    private Handler mHandler;

    /**
     * @param mHandler
     * @param len:     数量
     */
    public MessageRequest(Handler mHandler, String len) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.USER_MESSAGE);
        params.addBodyParameter("len", len);
        sendGet(params);
    }


    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("state");
        if (state == 1) {
            List<GoutMessage> mList = JSONArray.parseArray(object.getString("data"), GoutMessage.class);
            Message msg = mHandler.obtainMessage();
            msg.obj =mList;
            msg.what = SUCCESS;
            mHandler.sendMessage(msg);
        } else {
            mHandler.sendEmptyMessage(ERROR);
        }

    }

    public void onFailed(HttpException arg0, String arg1) {
        mHandler.sendEmptyMessage(ERROR);
    }

}
