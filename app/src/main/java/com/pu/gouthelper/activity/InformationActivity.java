package com.pu.gouthelper.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.SharedPreferences;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.bean.MyInfo;
import com.pu.gouthelper.dialog.PopupBirthday;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.SubmitMyInfoRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;



/**
 * Created by Requiem on 16/03/13.
 * 完善资料
 */
@ContentView(R.layout.activity_infomation)
public class InformationActivity extends SwipeBackActivity {

    @ViewInject(R.id.infomation_tv_moblie)
    private TextView infomation_tv_moblie;
    @ViewInject(R.id.infomation_tv_nikename)
    private EditText infomation_tv_nikename;
    @ViewInject(R.id.infomation_rg_sex)
    private RadioGroup infomation_rg_sex;
    @ViewInject(R.id.infomation_tv_birthday)
    private TextView infomation_tv_birthday;
    @ViewInject(R.id.infomation_tv_history)
    private TextView infomation_tv_history;
    @ViewInject(R.id.infomation_edt_height)
    private EditText infomation_edt_height;
    @ViewInject(R.id.infomation_edt_weight)
    private EditText infomation_edt_weight;
    private PopupWindow mpopupWindow;

    private String pain;

    private MyInfo info = null;
    private int sex = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SubmitMyInfoRequest.SUCCESS:
                    UIHelper.ToastMessage(InformationActivity.this, "" + msg.obj);
                    finish();
                    break;
                case SubmitMyInfoRequest.ERROR:
                    UIHelper.ToastMessage(InformationActivity.this, "" + msg.obj);
                    break;
                case PopupBirthday.OK:
                    infomation_tv_birthday.setText(msg.obj + "");
                    break;
            }
            endLoading();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        info = new MyInfo();
        setView();
    }

    private void setView() {
        infomation_rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.infomation_rb_1:
                        sex = 1;
                        break;
                    case R.id.infomation_rb_2:
                        sex = 2;
                        break;
                }
            }
        });
        infomation_tv_moblie.setText("您好，" + SharedPreferences.getInstance().getString("mobile", "--"));

    }

    private String[] timeArray = {"有过", "无"};

    public void showPopup(final TextView choose) {
        try {
            ListView lv = new ListView(this);
            final PopupWindow popup = new PopupWindow(lv, choose.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
            popup.setOutsideTouchable(true);
            popup.setFocusable(true);
            popup.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_edittext));
            lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, timeArray));
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    choose.setText(timeArray[position]);
                    popup.dismiss();
                }
            });
            popup.showAsDropDown(choose);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Event(value = {R.id.infomation_btn_submit, R.id.recipe_btn_goback, R.id.infomation_tv_pain, R.id.infomation_tv_drug, R.id.infomation_tv_birthday, R.id.infomation_tv_history}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.infomation_btn_submit:
                send();
                break;
            case R.id.recipe_btn_goback:
                finish();
                break;
            case R.id.infomation_tv_pain:
                Intent intent1 = new Intent(this, InformationPainSelectActivity.class);
                startActivityForResult(intent1, 0);
                break;
            case R.id.infomation_tv_drug:
                Intent intent2 = new Intent(this, InformationDrugSelectActivity.class);
                startActivityForResult(intent2, 1);
                break;
            case R.id.infomation_tv_birthday:
                PopupBirthday popupBirthday = new PopupBirthday(this, mHandler);
                popupBirthday.showPopupWindow(infomation_tv_birthday);
                break;
            case R.id.infomation_tv_history:
                showPopup(infomation_tv_history);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (data!=null){
                    pain = data.getStringExtra("pain");
                }
                break;
            case 1:
                break;
        }
    }

    private void send() {
        String nikename = infomation_tv_nikename.getText().toString().trim();
        String birthday = infomation_tv_birthday.getText().toString();
        String history = infomation_tv_history.getText().toString();
        String height = infomation_edt_height.getText().toString().trim();
        String weight = infomation_edt_weight.getText().toString().trim();

        if (StringUtils.isEmpty(weight)) {
            UIHelper.ToastMessage(this, "您还没有填写体重呢~");
            return;
        } else {
            info.setWeight(weight);
        }
        if (StringUtils.isEmpty(height)) {
            UIHelper.ToastMessage(this, "您还没有填写身高呢~");
            return;
        } else {
            info.setHeight(height);
        }
        if (StringUtils.isEmpty(nikename)) {
            UIHelper.ToastMessage(this, "您还没有填写昵称呢~");
            return;
        } else {
            info.setNickname(nikename);
        }
        if (StringUtils.isEmpty(birthday) || birthday.equals("请选择")) {
            UIHelper.ToastMessage(this, "您还没有填写生日~");
            return;
        } else {
            info.setBirthday(birthday);
        }
        if (StringUtils.isEmpty(history) || history.equals("请选择")) {
            UIHelper.ToastMessage(this, "您还没有填写病历~");
            return;
        } else {
            if (history.equals("有过")) {
                info.setHistory("1");
            } else {
                info.setHistory("2");
            }
        }
        if (StringUtils.isEmpty(pain)) {
            UIHelper.ToastMessage(this, "您还没有选择疼痛区域呢~");
            return;
        } else {
            info.setTarea(pain);
        }
        info.setSex(sex + "");
        new SubmitMyInfoRequest(mHandler, info);
        showLoading(this);
    }
}
