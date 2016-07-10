package com.pu.gouthelper.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pu.gouthelper.R;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Requiem on 2016/3/22.
 * web
 */
@ContentView(R.layout.activity_webview)
public class WebViewActivity extends SwipeBackActivity {
    @ViewInject(R.id.gout_webview)
    private WebView gout_webview;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        initView();
        initData();
    }


    private void initView() {
        gout_webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        gout_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }
        });
        gout_webview.loadUrl(url);
    }

    private void initData() {
    }

    @Event(value = {R.id.history_btn_goback}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.history_btn_goback:
                finish();
                break;
        }
    }
}
