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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.picker.ScrollerNumberPicker;
import com.pu.gouthelper.utils.Utils;

import java.util.Calendar;

/**
 * Created by Requiem on 2016/4/23.
 * 药品服用时间
 */
public class PopupDrugTime extends PopupWindow {

    private View view;
    private Handler mHandler;
    public static final int OK = 0x21;
    private Context mContext;
    private ScrollerNumberPicker days_picker_1;
    private ScrollerNumberPicker days_picker_2;
    private ScrollerNumberPicker days_picker_3;
    private ScrollerNumberPicker days_picker_4;
    private String date = "";
    String[] days = new String[]{"无", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00"};

    public PopupDrugTime(Context mContext, Handler mHandler) {
        this.mHandler = mHandler;
        this.mContext = mContext;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.share_popup_drugtime, null);
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
                date = days_picker_1.getSelectedText() + ";" + days_picker_2.getSelectedText() + ";" + days_picker_3.getSelectedText() + ";" + days_picker_4.getSelectedText();
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
        days_picker_3.setDefault(0);
        days_picker_4 = (ScrollerNumberPicker) view.findViewById(R.id.days_picker_4);
        days_picker_4.setData(Utils.toArrayList(days));
        days_picker_4.setDefault(0);
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
