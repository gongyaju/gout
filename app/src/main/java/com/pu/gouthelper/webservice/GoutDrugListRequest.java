package com.pu.gouthelper.webservice;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.bean.GoutDrug;

import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;

import java.util.List;


/**
 * Created by Requiem on 2016/3/20
 * 药品口碑 列表
 */
public class GoutDrugListRequest extends BaseRequest {
    public static final int SUCCESS = 0x11;
    public static final int ERROR = 0x12;
    private Handler mHandler;

    /**
     * @param mHandler
     * @param page:    页数
     */
    public GoutDrugListRequest(Handler mHandler, String page) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.GOUT_DRUG_lIST);
        params.addBodyParameter("len", F.PAGE_SIZE + "");
        params.addBodyParameter("p", page);
        sendGet(params);
    }

    /**
     * @param mHandler
     * @param len:     数量
     */
    public GoutDrugListRequest(Handler mHandler, String len, String title) {
        this.mHandler = mHandler;
        RequestParams params = new RequestParams(URLlist.GOUT_DRUG_lIST);
        params.addBodyParameter("len", len);
        params.addBodyParameter("title", title);
        sendGet(params);
    }

    public void onSuccessed(String result) {
        JSONObject object = JSON.parseObject(result);
        int state = object.getIntValue("state");
        if (state == 1) {
            List<GoutDrug> mList = JSONArray.parseArray(object.getString("data"), GoutDrug.class);
            Message msg = mHandler.obtainMessage();
            msg.obj = mList;
            msg.what = SUCCESS;
            msg.arg1 = Integer.parseInt(object.getString("count"));
            mHandler.sendMessage(msg);
        } else {
            mHandler.sendEmptyMessage(ERROR);
        }

    }

    public void onFailed(HttpException arg0, String arg1) {
        mHandler.sendEmptyMessage(ERROR);
    }

}
