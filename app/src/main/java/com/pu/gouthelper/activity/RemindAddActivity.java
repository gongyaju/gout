package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.pu.gouthelper.R;
import com.pu.gouthelper.base.SharedPreferences;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.bean.RemindEntity;

import com.pu.gouthelper.dialog.PopupDrugDays;
import com.pu.gouthelper.dialog.PopupDrugDosage;
import com.pu.gouthelper.dialog.PopupDrugTime;
import com.pu.gouthelper.ui.ColorPickerView;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.AddClockRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * Created by Requiem on 2016/3/22.
 * 添加吃药提醒
 */
@ContentView(R.layout.activity_add_remind)
public class RemindAddActivity extends SwipeBackActivity {

    private Context mContext;
    @ViewInject(R.id.remind_ls_show)
    private ListView remind_ls_show;
    @ViewInject(R.id.remind_img_color)
    private LinearLayout remind_img_color;
    @ViewInject(R.id.remind_tv_dosage)
    private TextView remind_tv_dosage;
    @ViewInject(R.id.remind_edt_name)
    private EditText remind_edt_name;

    private RemindEntity entity = new RemindEntity();
    private String doasge = "";
    private String days = "";
    private String time = "";
    private int color = Color.BLACK;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case AddClockRequest.SUCCESS:
                    endLoading();
                    UIHelper.ToastMessage(mContext, "" + msg.obj);
                    setResult(1);
                    finish();
                    break;
                case AddClockRequest.ERROR:
                    UIHelper.ToastMessage(mContext, "" + msg.obj);
                    endLoading();
                    break;
                case PopupDrugDosage.OK:
                    doasge = msg.obj + "";
                    break;
                case PopupDrugTime.OK:
                    time = msg.obj+"";
                    break;
                case PopupDrugDays.OK:
                    days = msg.obj + "";
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initData();
    }

    private void initData() {
        remind_img_color.addView(new ColorPickerView(this, Color.BLUE));
    }

    @Event(value = {R.id.remind_btn_goback, R.id.remind_btn_add, R.id.remind_tv_dosage, R.id.remind_tv_dtm, R.id.remind_tv_days, R.id.remind_img_color}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.remind_btn_goback:
                finish();
                break;
            case R.id.remind_btn_add:
                String name = remind_edt_name.getText().toString().trim();
                if (StringUtils.isEmpty(name)) {
                    UIHelper.ToastMessage(mContext, "名称还没填写呢~");
                    return;
                }
                if (StringUtils.isEmpty(doasge)) {
                    UIHelper.ToastMessage(mContext, "剂量还没选择呢~");
                    return;
                }
                if (StringUtils.isEmpty(time)) {
                    UIHelper.ToastMessage(mContext, "服药时间还没选择呢~");
                    return;
                }
                if (!StringUtils.isEmpty(days) && Integer.parseInt(days) > 0) {
                    entity.setDays(days);
                } else {
                    UIHelper.ToastMessage(mContext, "天数还没选择呢~");
                    return;
                }
                entity.setDosage(doasge);
                entity.setTitle(name);
                entity.setColor(color+"");
                entity.setDtm(time);
                entity.setTm(System.currentTimeMillis() / 1000 + "");
                if (entity != null) {
                    new AddClockRequest(mHandler, entity);
                    showLoading(mContext);
                }
                break;
            case R.id.remind_tv_dosage:
                //计量
                PopupDrugDosage popupDrugDosage = new PopupDrugDosage(this, mHandler);
                popupDrugDosage.showPopupWindow(remind_tv_dosage);
                break;
            case R.id.remind_tv_dtm:
                //提醒时间范围
                PopupDrugTime popupDrugTime = new PopupDrugTime(this, mHandler);
                popupDrugTime.showPopupWindow(remind_tv_dosage);
                break;
            case R.id.remind_tv_days:
                //提醒天数
                PopupDrugDays popupDrugDays = new PopupDrugDays(this, mHandler);
                popupDrugDays.showPopupWindow(remind_tv_dosage);
                break;
            case R.id.remind_img_color:
                Intent intent = new Intent(mContext, ColorPickerActivity.class);
                startActivityForResult(intent, 0x11);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x11) {
            color = SharedPreferences.getInstance().getInt("remind_select_color", 0);
            remind_img_color.removeAllViews();
            remind_img_color.addView(new ColorPickerView(this, color));
        }
    }
}
