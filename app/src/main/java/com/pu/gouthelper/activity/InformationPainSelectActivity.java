package com.pu.gouthelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * Created by Requiem on 16/04/17.
 * 完善资料 疼痛区域选择
 */
@ContentView(R.layout.activity_infomation_pain)
public class InformationPainSelectActivity extends SwipeBackActivity {
    @ViewInject(R.id.pain_cbx_1)
    private CheckBox pain_cbx_1;
    @ViewInject(R.id.pain_cbx_2)
    private CheckBox pain_cbx_2;
    @ViewInject(R.id.pain_cbx_3)
    private CheckBox pain_cbx_3;
    @ViewInject(R.id.pain_cbx_4)
    private CheckBox pain_cbx_4;
    @ViewInject(R.id.pain_cbx_5)
    private CheckBox pain_cbx_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Event(value = {R.id.infomation_btn_submit, R.id.recipe_btn_goback}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.infomation_btn_submit:
                StringBuilder pain = new StringBuilder();
                if (pain_cbx_1.isChecked()) {
                    pain.append("1,");
                }
                if (pain_cbx_2.isChecked()) {
                    pain.append("2,");
                }
                if (pain_cbx_3.isChecked()) {
                    pain.append("3,");
                }
                if (pain_cbx_4.isChecked()) {
                    pain.append("4,");
                }
                if (pain_cbx_5.isChecked()) {
                    pain.append("5,");
                }
                Intent it = new Intent(this,InformationActivity.class);
                it.putExtra("pain",pain.toString());
                setResult(1, it);
                finish();
                break;
            case R.id.recipe_btn_goback:
                finish();
                break;
        }
    }
}
