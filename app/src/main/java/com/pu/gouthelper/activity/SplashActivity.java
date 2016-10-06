package com.pu.gouthelper.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.BitmapUtils;
import com.pu.gouthelper.base.BitmapView;
import com.pu.gouthelper.base.SharedPreferences;
import com.pu.gouthelper.bean.SlideEntity;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.viewpagerindicator.CirclePageIndicator;
import com.pu.gouthelper.utils.BitmapUtil;
import com.pu.gouthelper.webservice.LoginRequest;
import com.pu.gouthelper.webservice.SidleListRequest;

import org.xutils.x;

import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Requiem on 16/03/12.
 */
public class SplashActivity extends FragmentActivity {

    private ImageView guideImage;
    private Button btnHome;
    private CirclePageIndicator indicator;
    private ViewPager pager;
    private GalleryPagerAdapter adapter;
    private int[] images = {
            R.drawable.splash_page1,
            R.drawable.splash_page2,
            R.drawable.splash_page3,
            R.drawable.splash_page4
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LoginRequest.SUCCESS:
                    UIHelper.showHome(SplashActivity.this);
                    finish();
                    break;
                case LoginRequest.ERROR:
                    UIHelper.showLogin(SplashActivity.this);
                    SplashActivity.this.finish();
                    UIHelper.ToastMessage(SplashActivity.this, msg.obj + "");
                    break;
                case SidleListRequest.SUCCESS:
                    try {
                        List<SlideEntity> mList = (List<SlideEntity>) msg.obj;
                        if (mList != null && mList.size() > 0) {
                            x.image().bind(guideImage, mList.get(0).getPic());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        guideImage = (ImageView) findViewById(R.id.guideImage);
        new SidleListRequest(mHandler, "2", "1");
        /**
         * / 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
         */
        JPushInterface.init(getApplicationContext());
        boolean firstTimeUse = SharedPreferences.getInstance().getBoolean("first-time-use", true);
        if (firstTimeUse) {
            initGuideGallery();
        } else {
            initLaunchLogo();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    private void initLaunchLogo() {
        guideImage.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String user = SharedPreferences.getInstance().getString("username", "");
                String psw = SharedPreferences.getInstance().getString("password", "");
                SharedPreferences.getInstance().putString("userid", "");
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(psw)) {
                    UIHelper.showLogin(SplashActivity.this);
                    SharedPreferences.getInstance().putString("username", "");
                    SharedPreferences.getInstance().putString("password", "");
                    SharedPreferences.getInstance().putString("mobile", "");
                    SplashActivity.this.finish();
                } else {
                    new LoginRequest(mHandler, user, psw);
                }

            }
        }, 3000);
    }

    private void initGuideGallery() {
        final Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.getInstance().putBoolean("first-time-use", false);
                UIHelper.showLogin(SplashActivity.this);
                SplashActivity.this.finish();
            }
        });

        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setVisibility(View.VISIBLE);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setVisibility(View.VISIBLE);

        adapter = new GalleryPagerAdapter();
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == images.length - 1) {
                    btnHome.setVisibility(View.VISIBLE);
                    btnHome.startAnimation(fadeIn);
                } else {
                    btnHome.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(SplashActivity.this);
            item.setScaleType(ImageView.ScaleType.CENTER_CROP);
            item.setImageResource(images[position]);
            container.addView(item);
            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }

}
