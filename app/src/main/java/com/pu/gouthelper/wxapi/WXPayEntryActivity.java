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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wxe2bd4b20bf63fe19");
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
                UIHelper.ToastMessage(this, resp.errCode + "");
                break;
            case -1:    //可能是签名不一致导致
                UIHelper.ToastMessage(this, "支付错误");
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