package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.URLlist;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by Requiem on 2016/3/20
 * 发送帖子
 */
public class TopicSendRequest extends BaseRequest {
    public static final int SUCCESS = 0x16;
    public static final int ERROR = 0x15;
    private Handler mHandler;

    /**
     * @param mHandler
     * @param title:标题
     * @param content:详情 pic: 图片 四张图片，一起发送  图片bitmap缩小后转byte[]  再上传试试
     */
    public TopicSendRequest(Handler mHandler, String title, String content, ArrayList<String> mSelectPath) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.GOUT_TOPIC_ADD);
        params.addBodyParameter("title", title);
        params.addBodyParameter("content", content);
        if (mSelectPath != null && mSelectPath.size() > 0) {
            for (int i = 0; i < mSelectPath.size(); i++) {
                params.addBodyParameter("pic" + i, new File(mSelectPath.get(i)));
            }
        }
        sendPost(params);
    }


    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("status");
        if (state == 1) {
            Message msg = mHandler.obtainMessage();
            msg.obj = result;
            msg.what = SUCCESS;
            mHandler.sendMessage(msg);
        } else {
            Message msg = mHandler.obtainMessage();
            msg.obj = object.getString("msg") + "";
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
