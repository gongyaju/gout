package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.PurinFoodEntity;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import java.util.List;


/**
 * Created by Requiem on 2016/3/13
 */
public class PurinListRequest extends BaseRequest {
    public static final int SUCCESS = 0x11;
    public static final int ERROR = 0x12;
    private Handler mHandler;

    /**
     * @param mHandler
     * @param len:     数量
     * @param type:    1 热门查询  2 低嘌呤查询  3 高危食物
     */
    public PurinListRequest(Handler mHandler, String len, String type) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.PURIN_LIST);
        params.addBodyParameter("len", "12");
        params.addBodyParameter("type", type);
        sendGet(params);
    }

    /**
     * @param mHandler
     * @param len:     数量
     * @param title:   食物名称(可选, 填了title之后，就不要传type了)
     */
    public PurinListRequest(Handler mHandler, String len, String title, String other) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.PURIN_LIST);
        params.addBodyParameter("len", len);
        params.addBodyParameter("title", title);
        sendGet(params);
    }

    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("state");
        if (state == 1) {
            List<PurinFoodEntity> mList = JSONArray.parseArray(object.getString("data"), PurinFoodEntity.class);
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
