package com.pu.gouthelper.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


import com.pu.gouthelper.R;
import com.pu.gouthelper.activity.GoutMsgDetailActivity;
import com.pu.gouthelper.activity.ImageGalleryActivity;
import com.pu.gouthelper.activity.PurinSearchActivity;
import com.pu.gouthelper.activity.SosActivity;
import com.pu.gouthelper.activity.WebViewActivity;
import com.pu.gouthelper.base.BaseFragment;
import com.pu.gouthelper.base.BitmapView;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.bean.SlideEntity;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.loopviewpager.AutoLoopViewPager;
import com.pu.gouthelper.ui.tabstrip.PagerSlidingTabStrip;
import com.pu.gouthelper.ui.viewpagerindicator.CirclePageIndicator;
import com.pu.gouthelper.webservice.SidleListRequest;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 首页嘌呤查询
 */
public class PurinSearchFragment extends BaseFragment {

    private static String[] TITLES;

    @ViewInject(R.id.tabs)
    private PagerSlidingTabStrip tabs;
    @ViewInject(R.id.pager)
    private ViewPager pager;
    private Context mContext;
    private List<SlideEntity> mList = new ArrayList<>();

    @ViewInject(R.id.purin_pager)
    AutoLoopViewPager purin_pager;
    @ViewInject(R.id.indicator)
    CirclePageIndicator indicator;
    private GalleryPagerAdapter galleryAdapter;


    private List<String> imageList = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SidleListRequest.SUCCESS:
                    List<SlideEntity> slideEntities = (List<SlideEntity>) msg.obj;
                    mList.clear();
                    mList.addAll(slideEntities);
                    imageList.clear();
                    for (SlideEntity entity : slideEntities) {
                        imageList.add(entity.getPic());
                    }
                    /**
                     * 轮播图
                     */
                    galleryAdapter = new GalleryPagerAdapter();
                    purin_pager.setAdapter(galleryAdapter);
                    indicator.setViewPager(purin_pager);
                    indicator.setPadding(5, 5, 10, 5);
                    break;
                case SidleListRequest.ERROR:
                    UIHelper.ToastMessage(mContext, "广告图加载失败：" + msg.obj);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_purin, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new SidleListRequest(mHandler, "1", "10");

        TITLES = new String[]{"热门查询", "低嘌呤推荐", "高危食品"};
        mContext = getActivity();
        FragmentPagerAdapter adapter = new NewsAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);

    }


    class NewsAdapter extends FragmentPagerAdapter {
        public NewsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new PurinTabOneFragment();
            }
            if (position == 1) {
                return new PurinTabTwoFragment();
            }
            return new PurinTabThreeFragment();
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

    @Event(value = {R.id.purin_edt_search}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.purin_edt_search:
                Intent intent_search = new Intent(mContext, PurinSearchActivity.class);
                getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_in_from_top);
                startActivity(intent_search);

                break;
        }
    }

    //轮播图适配器
    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(mContext);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
            item.setScaleType(ImageView.ScaleType.FIT_XY);
            BitmapView.getInstance().display(item, imageList.get(position));
            container.addView(item);

            final int pos = position;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SlideEntity entity=mList.get(pos);
                    if (!TextUtils.isEmpty(entity.getSid())){
                        Intent intent = new Intent(mContext, GoutMsgDetailActivity.class);
                        intent.putExtra("id", entity.getSid());
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(mContext, WebViewActivity.class);
                        intent.putExtra("url", entity.getUrl());
                        startActivity(intent);
                    }

                }
            });

            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        purin_pager.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        purin_pager.stopAutoScroll();
    }
}
