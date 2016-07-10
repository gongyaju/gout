package com.pu.gouthelper.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.pu.gouthelper.ui.UIUtils;
import com.pu.gouthelper.R;
import com.pu.gouthelper.common.AppManager;


import org.xutils.x;

/**
 * Created by Requiem on 16/03/12.
 */
public class BaseFragmentActivity extends FragmentActivity {
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        setTranslucentStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    @TargetApi(19)
    private void setTranslucentStatus() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    public void showLoading(Context context) {
        try {
            dialog = new Dialog(context, R.style.new_circle_progress);
            LinearLayout mLoadIngView = (LinearLayout) UIUtils.inflate(R.layout.dialog_loading_show);
            mLoadIngView.setBackgroundColor(UIUtils.getColor(R.color.transparent));
            dialog.setContentView(mLoadIngView);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endLoading() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
