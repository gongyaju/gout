package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.RemindEntity;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;


/**
 * Created by Requiem on 2016/3/20
 * 嗑药提醒
 */
public class AddClockRequest extends BaseRequest {
    public static final int SUCCESS = 0x16;
    public static final int ERROR = 0x15;
    private Handler mHandler;

    /**
     * @param mHandler
     */
    public AddClockRequest(Handler mHandler, RemindEntity entity) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.ADD_DRUG_CLOCK);
        params.addBodyParameter("title", entity.getTitle());
        params.addBodyParameter("dosage", entity.getDosage());
        params.addBodyParameter("dtm", entity.getDtm() + "");
        params.addBodyParameter("days", entity.getDays());
        params.addBodyParameter("color", entity.getColor());
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
