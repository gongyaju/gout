package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.Topic;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import java.util.List;


/**
 * Created by Requiem on 2016/3/20
 * 帖子列表
 */
public class TopicListRequest extends BaseRequest {
    public static final int SUCCESS = 0x16;
    public static final int ERROR = 0x15;
    private Handler mHandler;

    /**
     * @param mHandler
     * @param page:页码
     */
    public TopicListRequest(Handler mHandler, String page) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.GOUT_TOPIC_LIST);
        params.addBodyParameter("len", F.PAGE_SIZE+"");
        params.addBodyParameter("p", page);
        sendGet(params);
    }


    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("state");
        if (state == 1) {
            List<Topic> mList = JSONArray.parseArray(object.getString("data"), Topic.class);
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
        mHandler.sendEmptyMessage(ERROR);
    }

}
