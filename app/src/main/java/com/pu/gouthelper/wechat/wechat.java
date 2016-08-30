package com.pu.gouthelper.wechat;

import android.content.Context;
import com.pu.gouthelper.base.MD5;
import com.pu.gouthelper.base.RamdonUtils;
import com.pu.gouthelper.ui.UIHelper;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class Wechat {
    private Context context;
    private String prepayId;
    private IWXAPI api;
    boolean sIsWXAppInstalledAndSupported;

    //创建构造函数
    public Wechat(Context context) {
        this.context = context;
        //初始化微信
        api = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        boolean b = api.registerApp("wx7c5f5dad45203852");

    }

    //创建构造函数
    public Wechat(Context context, String prepayId) {
        this.prepayId = prepayId;
        this.context = context;
        //初始化微信
        api = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        boolean b = api.registerApp("wx7c5f5dad45203852");
        isWXAppInstalledAndSupported();
        if (!sIsWXAppInstalledAndSupported) {
            UIHelper.ToastMessage(context, "亲请安装微信,才能使用微信支付");
//            mPayRun.setEnabled(true);
            return;
        }
        init();
    }

    public void setParams(String prepayId) {
        this.prepayId = prepayId;
    }

    /**
     * 检测是否安装了微信
     * false为未安装
     * true为安装
     */
    public boolean check() {
        isWXAppInstalledAndSupported();
        if (!sIsWXAppInstalledAndSupported) {
            UIHelper.ToastMessage(context, "亲请安装微信,才能使用微信支付");
//            mPayRun.setEnabled(true);
            return sIsWXAppInstalledAndSupported;
        }
        return sIsWXAppInstalledAndSupported;
    }


    public void init() {
        //获取随机数
        String time = System.currentTimeMillis() / 1000 + "";
        String ramdon = RamdonUtils.generateString(32);
        PayReq req = new PayReq();
        req.appId = "wx7c5f5dad45203852";//微信分配的公众账号ID
        req.partnerId = "1285893401";     //微信支付分配的商户号
        req.prepayId = prepayId;         //微信返回的支付交易会话ID
        req.nonceStr = ramdon;        //随机字符串，不长于32位。推荐随机数生成算法
        req.timeStamp = time;         //时间戳
        req.packageValue = "Sign=WXPay";//暂填写固定值Sign=WXPay
        String stringA = "appid=wx7c5f5dad45203852&noncestr=" + ramdon + "&package=Sign=WXPay&partnerid=1285893401&prepayid=" + prepayId + "&timestamp=" + time + "";
        String stringSignTemp = stringA + "&key=huimin20151125pool2008lihuan2222";
        req.sign = MD5.getMessageDigest(stringSignTemp.getBytes()).toUpperCase();//参数签名
        api = WXAPIFactory.createWXAPI(context, "wx7c5f5dad45203852");
        //发送参数
        boolean b = api.sendReq(req);
    }

    //判断是否安装了微信
    private boolean isWXAppInstalledAndSupported() {
        sIsWXAppInstalledAndSupported = api.isWXAppInstalled()
                && api.isWXAppSupportAPI();
        return sIsWXAppInstalledAndSupported;
    }


}
