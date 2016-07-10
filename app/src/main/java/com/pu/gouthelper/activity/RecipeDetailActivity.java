package com.pu.gouthelper.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.bean.RecipeEntity;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.GoutRecipeInfoRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * Created by Requiem on 16/03/19.
 * 食谱详情
 */
@ContentView(R.layout.activity_recipe_detail)
public class RecipeDetailActivity extends SwipeBackActivity {

    @ViewInject(R.id.recipe_tv_title)
    private TextView recipe_tv_title;
    @ViewInject(R.id.recipe_btn_name)
    private TextView recipe_btn_name;
    @ViewInject(R.id.recipe_btn_ingredient)
    private TextView recipe_btn_ingredient;
    @ViewInject(R.id.recipe_btn_material)
    private TextView recipe_btn_material;
    @ViewInject(R.id.recipe_ls_step)
    private LinearLayout recipe_ls_step;
    @ViewInject(R.id.recipe_tv_source)
    private TextView recipe_tv_source;
    private String id;
    private Context mContext;
    private RecipeEntity entity;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GoutRecipeInfoRequest.SUCCESS:
                    entity = (RecipeEntity) msg.obj;
                    if (entity != null) {
                        setData();
                    }
                    break;
                case GoutRecipeInfoRequest.ERROR:
                    UIHelper.ToastMessage(RecipeDetailActivity.this, msg.obj + "");
                    break;

            }
            endLoading();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        id = getIntent().getStringExtra("id");

        initData();
    }

    private void initData() {
        new GoutRecipeInfoRequest(mHandler, id);
        showLoading(this);
    }

    private void setData() {
        recipe_tv_source.setText("来自："+entity.getSource());
        recipe_tv_title.setText(entity.getTitle());
        recipe_btn_name.setText(entity.getTitle());
        recipe_btn_ingredient.setText("主料：" + get_ingredient());
        recipe_btn_material.setText("配料：" + get_material());
        for (RecipeEntity.StepEntity step : entity.getStep()) {
            View convertView = LayoutInflater.from(this).inflate(R.layout.item_recipe_step, null);
            WebView recipe_web_content = (WebView) convertView.findViewById(R.id.recipe_web_content);
            recipe_web_content.loadDataWithBaseURL(null, step.getContent(), "text/html", "utf-8", null);
            recipe_ls_step.addView(convertView);
        }

    }

    private String get_ingredient() {
        StringBuilder ingredient = new StringBuilder();
        List<RecipeEntity.IngredientEntity> mList = entity.getIngredient();
        for (RecipeEntity.IngredientEntity entity : mList) {
            ingredient.append(entity.getTitle() + entity.getNum() + "\t");
        }
        return ingredient.toString();
    }

    private String get_material() {
        StringBuilder material = new StringBuilder();
        List<RecipeEntity.MaterialEntity> mList = entity.getMaterial();
        for (RecipeEntity.MaterialEntity entity : mList) {
            material.append(entity.getTitle() + entity.getNum() + "\t");
        }
        return material.toString();
    }

    @Event(value = {R.id.recipe_btn_goback}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.recipe_btn_goback:
                finish();
                break;

        }
    }

}
