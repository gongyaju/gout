package com.pu.gouthelper.webservice;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.AttackRecord;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Requiem on 2016/3/20
 */
public class JpushRequest extends BaseRequest {
    /**
     * client:  ios/android
     * client_id:  极光推送的  registration_id 由sdk 生成的，用户登录后，上报。。
     */
    public JpushRequest(Context context) {
        RequestParams params = new RequestParams(URLlist.JPUSH);
        params.addBodyParameter("client", "android");
        params.addBodyParameter("client_id", JPushInterface.getRegistrationID(context));
        sendPost(params);
    }
}
