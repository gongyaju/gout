package com.pu.gouthelper.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.picker.ScrollerNumberPicker;
import com.pu.gouthelper.utils.Utils;


/**
 * Created by Requiem on 2016/4/23.
 * 连续服用多少天
 */
public class PopupDrugDays extends PopupWindow {

    private View view;
    private Handler mHandler;
    public static final int OK = 0x22;
    private Context mContext;
    private ScrollerNumberPicker days_picker_1;
    private ScrollerNumberPicker days_picker_2;
    private ScrollerNumberPicker days_picker_3;
    private String date = "";
    String[] days = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public PopupDrugDays(Context mContext, Handler mHandler) {
        this.mHandler = mHandler;
        this.mContext = mContext;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.share_popup_drugdays, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        initView();

        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_out));
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
    }

    private void initView() {
        Button cancel_btn = (Button) view.findViewById(R.id.birthday_tv_cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopupWindow();
            }
        });
        TextView birthday_tv_submit = (TextView) view.findViewById(R.id.birthday_tv_submit);
        birthday_tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = days_picker_1.getSelectedText() + days_picker_2.getSelectedText() + days_picker_3.getSelectedText();
                if (Integer.parseInt(date) == 0) {
                    UIHelper.ToastMessage(mContext, "必须大于一天才能提交~");
                    return;
                }
                Message msg = mHandler.obtainMessage();
                msg.what = OK;
                msg.obj = date;
                mHandler.sendMessage(msg);
                closePopupWindow();
            }
        });
        days_picker_1 = (ScrollerNumberPicker) view.findViewById(R.id.days_picker_1);
        days_picker_1.setData(Utils.toArrayList(days));
        days_picker_1.setDefault(0);
        days_picker_2 = (ScrollerNumberPicker) view.findViewById(R.id.days_picker_2);
        days_picker_2.setData(Utils.toArrayList(days));
        days_picker_2.setDefault(0);
        days_picker_3 = (ScrollerNumberPicker) view.findViewById(R.id.days_picker_3);
        days_picker_3.setData(Utils.toArrayList(days));
        days_picker_3.setDefault(1);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        } else {
            this.dismiss();
        }
    }

    public void closePopupWindow() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }
}
