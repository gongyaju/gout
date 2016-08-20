package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.Comment;
import com.pu.gouthelper.bean.RemindEntity;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import java.util.List;


/**
 * Created by Requiem on 2016/3/20
 * 获取评论
 */
public class CommentListRequest extends BaseRequest {
    public static final int SUCCESS = 0xa6;
    public static final int ERROR = 0xa5;
    private Handler mHandler;

    /**
     * @param mHandler
     * @param type：            1社区评论 2文章评论 3药品评论 4食谱评论
     * @param tid：对应的社区/文章/药品/ 食谱 的ID
     * @param len：             10数量
     */
    public CommentListRequest(Handler mHandler, String type, String tid, String len) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.COMMENT_LIST);
        params.addBodyParameter("type", type);
        params.addBodyParameter("tid", tid);
        params.addBodyParameter("len", len);
        sendGet(params);
    }


    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("state");
        if (state == 1) {
            List<Comment> mList = JSONArray.parseArray(object.getString("data"), Comment.class);
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
