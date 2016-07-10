package com.pu.gouthelper.activity;

import android.content.Context;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;


import com.pu.gouthelper.R;
import com.pu.gouthelper.base.SharedPreferences;
import com.pu.gouthelper.ui.colorpicker.ColorPickerPalette;
import com.pu.gouthelper.ui.colorpicker.ColorPickerSwatch;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Requiem on 2016/3/22.
 * 提醒颜色选择
 */
@ContentView(R.layout.activity_color_picker)
public class ColorPickerActivity extends SwipeBackActivity implements ColorPickerSwatch.OnColorSelectedListener {

    private Context mContext;

    @ViewInject(R.id.color_picker)
    private ColorPickerPalette color_picker;
    protected ColorPickerSwatch.OnColorSelectedListener mListener;
    private int[] mColors = new int[]{Color.CYAN, Color.LTGRAY, Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.GRAY, Color.YELLOW};
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            }
            endLoading();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        color_picker.init(2, 4, this);
        int color = SharedPreferences.getInstance().getInt("remind_select_color", 0);
        if (color!=0){
            color_picker.drawPalette(mColors, color);
        }else{
            color_picker.drawPalette(mColors, Color.BLACK);
        }
    }


    @Event(value = {R.id.remind_btn_goback}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.remind_btn_goback:
                finish();
                break;

        }
    }

    @Override
    public void onColorSelected(int color) {
        SharedPreferences.getInstance().putInt("remind_select_color", color);
        color_picker.drawPalette(this.mColors, color);
    }

}
