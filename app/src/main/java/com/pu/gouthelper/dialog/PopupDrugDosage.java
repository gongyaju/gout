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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.adapter.DosageListAdapter;
import com.pu.gouthelper.bean.Doasge;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Requiem on 2016/4/23.
 * 药品用量
 */
public class PopupDrugDosage extends PopupWindow {

    private View view;
    private Handler mHandler;
    public static final int OK = 0x26;
    private GridView doasge_gv;
    private String[] doasge = {"1/4", "1/3", "1/2", "1", "1/4", "1/3", "1/2", "1", "1/4", "1/3", "1/2", "1"};
    private List<Doasge> mList = new ArrayList<>();
    private DosageListAdapter dosageListAdapter;
    private Context mContext;

    public PopupDrugDosage(Context mContext, Handler mHandler) {
        this.mHandler = mHandler;
        this.mContext = mContext;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.share_popup_doasge, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        initView();
        initData();
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

    private void initData() {
        dosageListAdapter = new DosageListAdapter(mContext, mList);
        for (int i = 0; i < doasge.length; i++) {
            Doasge doasge_entity = new Doasge();
            doasge_entity.setDoasge(doasge[i]);
            mList.add(doasge_entity);
        }
        doasge_gv.setAdapter(dosageListAdapter);
    }

    private void initView() {
        doasge_gv = (GridView) view.findViewById(R.id.doasge_gv);
        doasge_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Doasge entity = mList.get(position);
                if (entity.isSelect()) {
                    entity.setIsSelect(false);
                } else {
                    entity.setIsSelect(true);
                }
                dosageListAdapter.notifyDataSetChanged();
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
                StringBuilder content = new StringBuilder();
                for (Doasge doasge : mList) {
                    if (doasge.isSelect()) {
                        content.append(doasge.getDoasge() + ",");
                    }
                }
                Message msg = mHandler.obtainMessage();
                msg.what = OK;
                msg.obj = content.toString();
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
