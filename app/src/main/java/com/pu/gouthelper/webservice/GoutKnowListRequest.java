package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.GoutKnow;
import com.pu.gouthelper.bean.PurinFoodEntity;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import java.util.List;


/**
 * Created by Requiem on 2016/3/20
 * 痛风知识列表
 */
public class GoutKnowListRequest extends BaseRequest {
    public static final int SUCCESS = 0x11;
    public static final int ERROR = 0x12;
    private Handler mHandler;

    /**
     * @param mHandler
     * @param len:     pagesize
     * @param cid:     1 分类id
     * @param p:       page
     */
    public GoutKnowListRequest(Handler mHandler, String len, String p, String cid) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.GOUT_MSG_LIST);
        params.addBodyParameter("len", len);
        params.addBodyParameter("cid", cid);
        params.addBodyParameter("p", p);
        sendGet(params, true);
    }


    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("state");
        if (state == 1) {
            List<GoutKnow> mList = JSONArray.parseArray(object.getString("data"), GoutKnow.class);
            Message msg = mHandler.obtainMessage();
            msg.obj = mList;
            msg.what = SUCCESS;
            msg.arg1=Integer.parseInt(object.getString("count"));
            mHandler.sendMessage(msg);
        } else {
            mHandler.sendEmptyMessage(ERROR);
        }

    }

    public void onFailed(HttpException arg0, String arg1) {
        Message msg = mHandler.obtainMessage();
        msg.obj = "请求服务器失败";
        msg.what = ERROR;
        mHandler.sendMessage(msg);
    }

}
