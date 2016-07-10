package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.RecipeEntity;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;


/**
 * Created by Requiem on 2016/3/20
 * 碱性食谱 详情
 */
public class GoutRecipeInfoRequest extends BaseRequest {
    public static final int SUCCESS = 0x16;
    public static final int ERROR = 0x15;
    private Handler mHandler;

    /**
     * @param mHandler
     * @param id:
     */
    public GoutRecipeInfoRequest(Handler mHandler, String id) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.USER_RECIPE_INFO);
        params.addBodyParameter("id", id);
        sendGet(params);
    }


    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("state");
        if (state == 1) {
            RecipeEntity entity = JSONObject.parseObject(object.getString("data"), RecipeEntity.class);
            Message msg = mHandler.obtainMessage();
            msg.obj = entity;
            msg.what = SUCCESS;
            mHandler.sendMessage(msg);
        } else {
            Message msg = mHandler.obtainMessage();
            msg.obj = object.getString("msg")+"";
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
