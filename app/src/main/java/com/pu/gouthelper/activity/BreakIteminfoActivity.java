package com.pu.gouthelper.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.bean.AttackRecord;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.utils.DateUtil;
import com.pu.gouthelper.utils.ShareUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Requiem on 2016/05/02
 * 发作记录详情
 */
@ContentView(R.layout.activity_break_info)
public class BreakIteminfoActivity extends SwipeBackActivity {

    private AttackRecord entity = null;
    private Context mContext;
    @ViewInject(R.id.break_tv_title)
    private TextView break_tv_title;
    @ViewInject(R.id.break_tv_titleinfo)
    private TextView break_tv_titleinfo;
    @ViewInject(R.id.break_tv_time)
    private TextView break_tv_time;
    @ViewInject(R.id.break_tv_content)
    private TextView break_tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        entity = (AttackRecord) getIntent().getExtras().getSerializable("record");
        setData();
    }

    private void setData() {
        break_tv_title.setText(DateUtil.date2String(Long.parseLong(entity.getTm()) * 1000, "yyyy年MM月dd日") + "发作记录");
        break_tv_titleinfo.setText("本次发作于" + DateUtil.date2String(Long.parseLong(entity.getTm()) * 1000, "yyyy年MM月dd日") + "标记");
        break_tv_time.setText(DateUtil.date2String(Long.parseLong(entity.getTm()) * 1000, "yyyy-MM-dd HH:mm"));
        break_tv_content.setText(entity.getDesc());
    }

    @Event(value = {R.id.break_btn_goback, R.id.psw_btn_submit,R.id.break_tv_share1,R.id.break_tv_share2,R.id.break_tv_share3}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.break_btn_goback:
                finish();
                break;
            case R.id.break_tv_share1:
            case R.id.break_tv_share2:
            case R.id.break_tv_share3:
                ShareUtils.share(this, "分享痛风助手到...");
                break;
        }
    }
}
