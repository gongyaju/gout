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

/**
 * Created by Requiem on 2016/4/11.
 */
public class CommentAddRequest extends BaseRequest {
    public static final int SUCCESS = 0xa1;
    public static final int ERROR = 0xa2;
    private Handler mHandler;

    /**
     * @param mHandler
     * @param type：            1社区评论 2文章评论 3药品评论 4食谱评论
     * @param tid：对应的社区/文章/药品/ 食谱 的ID
     * @param cid：被回复的评论ID
     * @param tuid：要回复的人UID
     * @param content：回复内容
     */
    public CommentAddRequest(Handler mHandler, String type, String tid, String cid, String tuid, String content) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.ADD_COMMENT);
        params.addBodyParameter("type", type);
        params.addBodyParameter("tid", tid);
        params.addBodyParameter("cid", cid);
        params.addBodyParameter("tuid", tuid);
        params.addBodyParameter("content", content);
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
