package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.MyInfo;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;


/**
 * Created by Requiem on 2016/3/13
 * 个人信息提交
 */
public class SubmitMyInfoRequest extends BaseRequest {
    public static final int SUCCESS = 0x11;
    public static final int ERROR = 0x12;
    private Handler mHandler;

    /**
     * @param mHandler nickname:'昵称'
     *                 sex:'1'  // 1男  2女
     *                 birthday:'2015-10-10'  //生日
     *                 history:'1'  //痛风历史  1 有过  2 无
     *                 height:'180' //身高
     *                 weight:'75'  //体重
     *                 tarea:'1,2'  //疼痛区域  这个数字，你们自己商定，每个区域，用数字标识，客户端保持一致就行了,多个区域，用英文逗号分隔
     *                 drug:'1,2'  //用药历史，这里传的是药品的ID， 多个药品ID，用英文逗号分隔
     */
    public SubmitMyInfoRequest(Handler mHandler, MyInfo info) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.SUBMIT_MYINFO);
        params.addBodyParameter("nickname", info.getNickname());
        params.addBodyParameter("sex", info.getSex());
        params.addBodyParameter("birthday", info.getBirthday());
        params.addBodyParameter("history", info.getHistory());
        params.addBodyParameter("height", info.getHeight());
        params.addBodyParameter("weight", info.getWeight());
        params.addBodyParameter("tarea", info.getTarea());
        params.addBodyParameter("drug", info.getDrug());
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
