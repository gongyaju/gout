package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.RemindEntity;
import com.pu.gouthelper.bean.RewardEntity;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import java.util.List;


/**
 * Created by Requiem on 2016/3/20
 * 捐献列表
 */
public class RewardListRequest extends BaseRequest {
    public static final int SUCCESS = 0x46;
    public static final int ERROR = 0x425;
    private Handler mHandler;

    /**
     * @param mHandler
     * @param len:size
     */
    public RewardListRequest(Handler mHandler, String len, String type, String cid) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.URL_REWARD_LIST);
        params.addBodyParameter("len", len);
        params.addBodyParameter("type", type);
        params.addBodyParameter("id", cid);
        sendGet(params);
    }


    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("state");
        if (state == 1) {
            List<RewardEntity> mList = JSON.parseArray(object.getString("data"), RewardEntity.class);
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
