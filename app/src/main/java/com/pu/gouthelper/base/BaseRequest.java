package com.pu.gouthelper.base;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.common.util.MD5;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class BaseRequest {

    Callback.Cancelable cancelable;

    public void cancel() {
        if (cancelable != null) {
            cancelable.cancel();
        }

    }

    public void onSuccessed(String result) {

    }

    public void onFailed(HttpException arg0, String arg1) {
    }

    protected void sendGet(RequestParams params) {
        sendGet(params, false);
    }

    protected void sendGet(RequestParams params, boolean isCache) {

        String time = System.currentTimeMillis() / 1000 + "";
        params.addBodyParameter("uid", SharedPreferences.getInstance().getString("userid", ""));
        params.addBodyParameter("time", time);
        params.addBodyParameter("appid", "tf001");
        params.addBodyParameter("auth", MD5.md5(SharedPreferences.getInstance().getString("userid", "") + time + "tf001" + "890Ofas5UjJAzFFBkV1Qjt4FQMUhWtUX"));

        params.setMaxRetryCount(0);
        params.setConnectTimeout(1000 * 30);

        if (isCache) {
//            params.setCacheMaxAge(1000 * 60 * 100);
//            params.setCacheDirName(x.app().getCacheDir().getAbsolutePath());
//            params.setCacheSize(1024 * 1024 * 5);
        }
        Logger.e(params.getUri());
        Logger.w(params.toJSONString());
        cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    onSuccessed(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    onFailed(new HttpException(400, "ERROR"), "返回错误" + result);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                onFailed(new HttpException(400, ex.getMessage()), ex.getMessage().toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                cancel();
            }

            @Override
            public void onFinished() {

            }
        });

    }

    protected void sendPost(RequestParams params) {
        sendPost(params, false);
    }

    protected void sendPost(RequestParams params, Boolean bcache) {
        String time = System.currentTimeMillis() / 1000 + "";
        params.addBodyParameter("uid", SharedPreferences.getInstance().getString("userid", ""));
        params.addBodyParameter("time", time);
        params.addBodyParameter("appid", "tf001");
        params.addBodyParameter("auth", MD5.md5(SharedPreferences.getInstance().getString("userid", "") + time + "tf001" + "890Ofas5UjJAzFFBkV1Qjt4FQMUhWtUX"));
        params.setMaxRetryCount(0);
        params.setConnectTimeout(1000 * 30);
        if (bcache) {
//            params.setCacheMaxAge(1000 * 60 * 100);
//            params.setCacheDirName(x.app().getCacheDir().getAbsolutePath());
//            params.setCacheSize(1024 * 1024 * 5);
        }
//        Logger.e(params.getUri());
//        Logger.w(params.toJSONString());
        cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    onSuccessed(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    onFailed(new HttpException(400, "ERROR"), "返回错误" + result);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                onFailed(new HttpException(400, ex.getMessage()), ex.getMessage().toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                cancel();
            }

            @Override
            public void onFinished() {

            }
        });
    }

}
