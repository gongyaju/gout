package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pu.gouthelper.R;
import com.pu.gouthelper.base.BitmapView;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.bean.ActionItem;
import com.pu.gouthelper.bean.DrugDetail;
import com.pu.gouthelper.dialog.TitlePopup;
import com.pu.gouthelper.fragment.DrugDetailTabOneFragment;
import com.pu.gouthelper.fragment.DrugDetailTabTwoFragment;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.ui.tabstrip.PagerSlidingTabStrip;
import com.pu.gouthelper.utils.Utils;
import com.pu.gouthelper.webservice.CommentAddRequest;
import com.pu.gouthelper.webservice.GoutDrugInfoRequest;
import com.pu.gouthelper.webservice.ZDownRequest;
import com.pu.gouthelper.webservice.ZUpRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * Created by Requiem on 16/03/19.
 * 口碑药品详情
 */
@ContentView(R.layout.activity_drug_detail)
public class DrugDetailActivity extends SwipeBackActivity {
    private String id;
    private String[] TITLES;

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private Context mContext;


    @ViewInject(R.id.drug_liner_vs)
    private LinearLayout drug_liner_vs;
    @ViewInject(R.id.drug_img_pic)
    private ImageView drug_img_pic;
    @ViewInject(R.id.drug_tv_title)
    private TextView drug_tv_title;
    @ViewInject(R.id.drug_tv_price)
    private TextView drug_tv_price;
    @ViewInject(R.id.drug_btn_zan)
    private Button drug_btn_zan;
    @ViewInject(R.id.drug_btn_cai)
    private Button drug_btn_cai;
    @ViewInject(R.id.progressBar1_text1)
    private TextView progressBar1_text1;
    @ViewInject(R.id.progressBar1_text2)
    private TextView progressBar1_text2;
    @ViewInject(R.id.progressBar1)
    private ProgressBar progressBar1;
    @ViewInject(R.id.rl_bottom)
    private RelativeLayout rl_bottom;
    @ViewInject(R.id.group_discuss)
    private EditText group_discuss;
    private DrugDetailTabOneFragment drugDetailTabOneFragment;
    private DrugDetailTabTwoFragment drugDetailTabTwoFragment;
    private DrugDetail entity = null;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GoutDrugInfoRequest.SUCCESS:
                    JSONObject object = JSON.parseObject(msg.obj + "");
                    if (object.getIntValue("state") == 1) {
                        entity = JSONObject.parseObject(object.getString("data"), DrugDetail.class);
                        setDate(entity);
                    }
                    break;
                case GoutDrugInfoRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
                case ZUpRequest.ERROR:
                case ZUpRequest.SUCCESS:
                case ZDownRequest.ERROR:
                case ZDownRequest.SUCCESS:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
                case CommentAddRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
                case CommentAddRequest.SUCCESS:
                    group_discuss.setText("");
                    UIHelper.ToastMessage(mContext, msg.obj + "");
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
        TITLES = new String[]{"药品说明书", "小酸人评论"};
        initView();
        initData();

    }


    private void initView() {
        pager = (ViewPager) this.findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) this.findViewById(R.id.tabs);
        FragmentPagerAdapter adapter = new NewsAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    rl_bottom.setVisibility(View.GONE);
                } else {
                    rl_bottom.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    class NewsAdapter extends FragmentPagerAdapter {
        public NewsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                if (drugDetailTabOneFragment == null) {
                    drugDetailTabOneFragment = new DrugDetailTabOneFragment();
                }
                return drugDetailTabOneFragment;
            } else {
                if (drugDetailTabTwoFragment == null) {
                    drugDetailTabTwoFragment = new DrugDetailTabTwoFragment();
                }
                return drugDetailTabTwoFragment;
            }

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
        new GoutDrugInfoRequest(mHandler, id);
        showLoading(this);
    }

    private void setDate(DrugDetail entity) {
        BitmapView.getInstance().display(drug_img_pic, entity.getPic());
        drug_tv_title.setText(entity.getTitle());
        drug_tv_price.setText("￥" + entity.getMin_price() + "-" + "￥" + entity.getMax_price());
        drug_btn_zan.setText(entity.getUps());
        progressBar1_text1.setText(entity.getUps() + "人顶");
        drug_btn_cai.setText(entity.getDowns());
        progressBar1_text2.setText(entity.getDowns() + "人踩");
        progressBar1.setMax(Integer.parseInt(entity.getUps()) + Integer.parseInt(entity.getDowns()));
        progressBar1.setProgress(Integer.parseInt(entity.getUps()));
        if (drugDetailTabOneFragment != null) {
            drugDetailTabOneFragment.setData(entity);
        }
        if (drugDetailTabTwoFragment != null) {
            drugDetailTabTwoFragment.setData(entity.getId());
        }
    }

    @Event(value = {R.id.drug_btn_goback, R.id.drug_btn_buy, R.id.drug_btn_cai, R.id.drug_btn_zan, R.id.group_discuss_submit}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.drug_btn_goback:
                finish();
                break;
            case R.id.drug_btn_buy:
                if (entity != null && !StringUtils.isEmpty(entity.getUrl())) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("url", entity.getUrl());
                    startActivity(intent);
                }
                break;
            case R.id.drug_btn_zan:
                new ZUpRequest(mHandler, entity.getId());
                showLoading(mContext);
                break;
            case R.id.drug_btn_cai:
                new ZDownRequest(mHandler, entity.getId());
                showLoading(mContext);
                break;
            case R.id.group_discuss_submit:
                String content = group_discuss.getText().toString().trim();
                if (StringUtils.isEmpty(content)) {
                    UIHelper.ToastMessage(mContext, "先说点什么再发表吧~");
                    return;
                }
                if (StringUtils.isEmpty(entity.getId())) {
                    UIHelper.ToastMessage(mContext, "出错啦！稍等一会再评论吧~");
                    return;
                }
                new CommentAddRequest(mHandler, "3", entity.getId(), "", "", content);
                showLoading(mContext);
                break;
        }
    }


    public void isShowLiner(Boolean isShow) {
        if (isShow) {
            drug_liner_vs.setVisibility(View.GONE);
        } else {
            drug_liner_vs.setVisibility(View.VISIBLE);
        }

    }

    public String getId() {
        return entity.getId();
    }
}
