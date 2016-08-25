package com.pu.gouthelper.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.R;
import com.pu.gouthelper.base.BitmapUtils;
import com.pu.gouthelper.base.BitmapView;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.bean.PurinFoodInfoEntity;
import com.pu.gouthelper.bean.PurinNews;
import com.pu.gouthelper.bean.PurinRecipe;
import com.pu.gouthelper.fragment.PurinDetailTabOneFragment;
import com.pu.gouthelper.fragment.PurinDetailTabTwoFragment;

import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.ui.tabstrip.PagerSlidingTabStrip;
import com.pu.gouthelper.utils.BitmapUtil;
import com.pu.gouthelper.webservice.PurinListInfoRequest;

import org.json.JSONException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * Created by Requiem on 16/03/19.
 */
@ContentView(R.layout.activity_purin_detail)
public class PurinDetailActivity extends SwipeBackActivity {

    @ViewInject(R.id.detail_tv_title)
    private TextView detail_tv_title;
    @ViewInject(R.id.detail_img_show)
    private ImageView detail_img_show;
    @ViewInject(R.id.purin_tv_content)
    private TextView purin_tv_content;
    @ViewInject(R.id.purin_tv_calorie)
    private TextView purin_tv_calorie;
    @ViewInject(R.id.purin_tv_fat)
    private TextView purin_tv_fat;
    @ViewInject(R.id.purin_tv_purin)
    private TextView purin_tv_purin;
    @ViewInject(R.id.detail_ly_bg)
    private LinearLayout detail_ly_bg;
    @ViewInject(R.id.purin_tv_warninfo)
    private TextView purin_tv_warninfo;
    private String id;

    private String[] TITLES;

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private Context mContext;

    private PurinDetailTabOneFragment purinDetailTabOneFragment;
    private PurinDetailTabTwoFragment purinDetailTabTwoFragment;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PurinListInfoRequest.SUCCESS:
                    JSONObject object = JSON.parseObject(msg.obj + "");
                    PurinFoodInfoEntity entity = JSONObject.parseObject(object.getString("data"), PurinFoodInfoEntity.class);
                    JSONObject jsonObject = object.getJSONObject("correlation");
                    List<PurinNews> pList = JSONArray.parseArray(jsonObject.getString("news"), PurinNews.class);
                    entity.setPurinNews(pList);
                    List<PurinRecipe> rList = JSONArray.parseArray(jsonObject.getString("news"), PurinRecipe.class);
                    entity.setPurinRecipes(rList);
                    setData(entity);
                    break;
                case PurinListInfoRequest.ERROR:
                    UIHelper.ToastMessage(PurinDetailActivity.this, msg.obj + "");
                    break;

            }
            endLoading();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        TITLES = new String[]{"相关痛风菜谱", "相关痛风知识"};
        id = getIntent().getStringExtra("id");
        initView();
        initData();
    }


    private void initView() {
        pager = (ViewPager) this.findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) this.findViewById(R.id.tabs);
        FragmentPagerAdapter adapter = new NewsAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);
        purinDetailTabOneFragment = new PurinDetailTabOneFragment();
        purinDetailTabTwoFragment = new PurinDetailTabTwoFragment();
    }

    class NewsAdapter extends FragmentPagerAdapter {
        public NewsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return purinDetailTabOneFragment;
            }
            return purinDetailTabTwoFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position % TITLES.length];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }

    private void initData() {
        new PurinListInfoRequest(mHandler, id);
        showLoading(this);
    }

    private void setData(PurinFoodInfoEntity entity) {
        detail_tv_title.setText(entity.getTitle());
        BitmapView.getInstance().display(detail_img_show, entity.getPic());
        purin_tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
        purin_tv_content.setText(entity.getDesc());
        purin_tv_calorie.setText(entity.getCalorie());
        purin_tv_fat.setText(entity.getFat());
        purin_tv_purin.setText(entity.getPurine());
        double purin = Double.parseDouble(entity.getPurine());
        if (purin <= 70) {
            detail_ly_bg.setBackgroundResource(R.drawable.purin_green);
            purin_tv_warninfo.setText("放心食用");
        } else if (purin > 70 && purin <= 100) {
            detail_ly_bg.setBackgroundResource(R.drawable.purin_yellow);
            purin_tv_warninfo.setText("少吃为妙");
        } else if (purin > 100) {
            detail_ly_bg.setBackgroundResource(R.drawable.purin_red);
            purin_tv_warninfo.setText("高危食品");
        }
        purinDetailTabOneFragment.setdata(entity.getPurinRecipes());
        purinDetailTabTwoFragment.setdata(entity.getPurinNews());
    }

    @Event(value = {R.id.detail_btn_goback}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_btn_goback:
                finish();
                break;
        }
    }
}
