package com.pu.gouthelper.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.pu.gouthelper.activity.BaseFragmentActivity;
import com.pu.gouthelper.ui.UIHelper;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends BaseFragmentActivity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;
    public static final String BOADRDCAST_WXPAY = "com.pu.gout.wchatpay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wx7c5f5dad45203852");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case 0:
                Intent it = new Intent(BOADRDCAST_WXPAY);
                it.putExtra("wchat", resp.errCode);
                sendBroadcast(it);
                UIHelper.ToastMessage(this, "支付成功");
                finish();
                break;
            case -1:
                UIHelper.ToastMessage(this, "APP签名不一致");
                finish();
                break;
            case -2:
                UIHelper.ToastMessage(this, "取消支付");
                finish();
                break;
            case -3:
                UIHelper.ToastMessage(this, "发送失败");
                finish();
                break;
            case -4:
                UIHelper.ToastMessage(this, "授权失败");
                finish();
                break;
            case -5:
                UIHelper.ToastMessage(this, "不支持的支付类型");
                finish();
                break;
        }
    }


}