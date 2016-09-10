package com.pu.gouthelper.wechat.webservice;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;
import com.pu.gouthelper.base.AppUtils;
import com.pu.gouthelper.base.BaseRequest;
import com.pu.gouthelper.base.MD5;
import com.pu.gouthelper.base.NetUtils;
import com.pu.gouthelper.base.RamdonUtils;
import com.pu.gouthelper.base.SharedPreferences;
import com.pu.gouthelper.base.Sign;
import com.pu.gouthelper.base.URLlist;
import com.pu.gouthelper.base.XMLBeanUtils;
import com.pu.gouthelper.bean.AttackRecord;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Requiem on 2016/8/30.
 */
public class CreatePrepayIdRequest {
    public static final int SUCCESS = 0x16;
    public static final int ERROR = 0x15;
    private Handler mHandler;
    private Callback.Cancelable cancelable;

    /**
     * @param mHandler
     */
    public CreatePrepayIdRequest(Handler mHandler, Context context, String money) {
        this.mHandler = mHandler;
        String signStr = Sign.getCertificateSHA1Fingerprint(context);
        Logger.e(signStr);
        String ramdon = RamdonUtils.generateString(32);
        String orderid = "TF" + System.currentTimeMillis();
        String ip = AppUtils.getIP(context);
        Map<String, String> map = new HashMap<>();
        map.put("appid", "wx7c5f5dad45203852");
        map.put("body", "tongfeng");
        map.put("mch_id", "1372950802");
        map.put("nonce_str", ramdon);
        map.put("notify_url", "http://www.tfzs999.com/notify/weixin");
        map.put("out_trade_no", orderid);
        map.put("spbill_create_ip", ip);//终端IP
        map.put("total_fee", money);
        map.put("trade_type", "APP");
        String stringA = "appid=wx7c5f5dad45203852&body=tongfeng&mch_id=1372950802&nonce_str=" + ramdon + "&notify_url=http://www.tfzs999.com/notify/weixin&out_trade_no=" + orderid + "&spbill_create_ip=" + ip + "&total_fee=" + money + "&trade_type=APP";
        String stringSignTemp = stringA + "&key=tfzs999pool200820160816ma3he4222";
        String sign = MD5.getMessageDigest(stringSignTemp.getBytes()).toUpperCase();//参数签名
        map.put("sign", sign);
        RequestParams params = new RequestParams("https://api.mch.weixin.qq.com/pay/unifiedorder");
        params.setBodyContent(XMLBeanUtils.map2XmlString(map));
        sendPost(params);
    }


    protected void sendPost(RequestParams params) {
        params.setMaxRetryCount(0);
        params.setConnectTimeout(1000 * 30);

        cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Map<String, String> map = XMLBeanUtils.readStringXmlOut(result);

                if (map.get("return_code").equals("SUCCESS") && map.get("result_code").equals("SUCCESS")) {
                    Message msg = mHandler.obtainMessage();
                    msg.obj = map.get("prepay_id");
                    Logger.e("微信创建订单：" + map.get("prepay_id"));
                    msg.what = SUCCESS;
                    mHandler.sendMessage(msg);
                } else {
                    Message msg = mHandler.obtainMessage();
                    msg.obj = "创建订单失败";
                    msg.what = ERROR;
                    mHandler.sendMessage(msg);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Message msg = mHandler.obtainMessage();
                msg.obj = ex.getMessage().toString();
                msg.what = ERROR;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}

