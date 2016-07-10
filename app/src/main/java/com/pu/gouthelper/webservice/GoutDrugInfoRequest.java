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
 * Created by Requiem on 2016/3/20
 * 药品口碑 详情
 */
public class GoutDrugInfoRequest extends BaseRequest {
    public static final int SUCCESS = 0x16;
    public static final int ERROR = 0x15;
    private Handler mHandler;

    /**
     * @param mHandler
     * @param id:
     */
    public GoutDrugInfoRequest(Handler mHandler, String id) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.GOUT_DRUG_INFO);
        params.addBodyParameter("id", id);
        sendGet(params);
    }


    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("state");
        if (state == 1) {
            Message msg = mHandler.obtainMessage();
            msg.obj = result;
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
