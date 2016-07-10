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

import java.util.Calendar;

/**
 * Created by Requiem on 2016/4/23.
 */
public class PopupBirthday extends PopupWindow {

    private View view;
    private Handler mHandler;
    public static final int OK = 0x16;
    private String date = "";

    public PopupBirthday(Context mContext, Handler mHandler) {
        this.mHandler = mHandler;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.share_popup_menu, null);
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
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datepicker);
        datePicker.setCalendarViewShown(false);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {

            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                date = year + "-" + monthOfYear + "-" + dayOfMonth;
            }
        });
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
                Message msg = mHandler.obtainMessage();
                msg.what = OK;
                msg.obj = date;
                mHandler.sendMessage(msg);
                closePopupWindow();
            }
        });
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
