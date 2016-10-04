package com.pu.gouthelper.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.activity.CenterBreakRecordActivity;
import com.pu.gouthelper.activity.CenterFoodActivity;
import com.pu.gouthelper.activity.CenterHistoryActivity;
import com.pu.gouthelper.activity.CenterMessageActivity;
import com.pu.gouthelper.activity.CenterMyInfoActivity;
import com.pu.gouthelper.activity.CenterMyTopicActivity;
import com.pu.gouthelper.activity.CenterRemindActivity;
import com.pu.gouthelper.activity.CenterSettingActivity;
import com.pu.gouthelper.activity.InformationActivity;
import com.pu.gouthelper.activity.MyinfoActivity;
import com.pu.gouthelper.base.BaseFragment;
import com.pu.gouthelper.base.BitmapUtils;
import com.pu.gouthelper.base.BitmapView;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.SharedPreferences;
import com.pu.gouthelper.bean.LoginResult;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.pulltozoomview.PullToZoomScrollViewEx;
import com.pu.gouthelper.utils.BitmapUtil;
import com.pu.gouthelper.webservice.MyInfoGetRequest;


/**
 * 个人健康中心
 */
public class HealthCenterFragment extends BaseFragment {


    private Activity context;
    private View view;
    private PullToZoomScrollViewEx scrollView;
    private TextView center_tv_toux;
    private ImageView center_img_toux;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MyInfoGetRequest.SUCCESS:
                    LoginResult login_msg = (LoginResult) msg.obj;
                    center_tv_toux.setText(login_msg.getMobile());
                    BitmapUtils.getInstance().display(center_img_toux, login_msg.getAvatar());
                    SharedPreferences.getInstance().putString("avatar", login_msg.getAvatar());
                    SharedPreferences.getInstance().putString("nickname", login_msg.getNickname());
                    if (TextUtils.isEmpty(login_msg.getHeight()) || TextUtils.isEmpty(login_msg.getBirthday())
                            || TextUtils.isEmpty(login_msg.getHistory()) || TextUtils.isEmpty(login_msg.getWeight())
                            || TextUtils.isEmpty(login_msg.getHistory())) {
                        startActivity(new Intent(context, InformationActivity.class));
                    }
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page_health, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
        initData();
    }

    private void initView() {
        scrollView = (PullToZoomScrollViewEx) view.findViewById(R.id.scrollView);
        View headView = LayoutInflater.from(context).inflate(R.layout.member_head_view, null, false);
        View zoomView = LayoutInflater.from(context).inflate(R.layout.member_zoom_view, null, false);
        View contentView = LayoutInflater.from(context).inflate(R.layout.member_content_view, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
        center_tv_toux = (TextView) scrollView.getPullRootView().findViewById(R.id.center_tv_toux);
        center_img_toux = (ImageView) scrollView.getPullRootView().findViewById(R.id.center_img_toux);
        center_tv_toux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent my_info = new Intent(context, MyinfoActivity.class);
                startActivityForResult(my_info, 0x11);
            }
        });
        scrollView.getPullRootView().findViewById(R.id.center_tv_breakrecode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CenterBreakRecordActivity.class));
            }
        });
        scrollView.getPullRootView().findViewById(R.id.center_tv_food).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CenterFoodActivity.class));
            }
        });
        scrollView.getPullRootView().findViewById(R.id.center_tv_drug).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CenterRemindActivity.class));
            }
        });
        scrollView.getPullRootView().findViewById(R.id.center_tv_myhistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CenterHistoryActivity.class));
            }
        });
        scrollView.getPullRootView().findViewById(R.id.center_tv_topic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CenterMyTopicActivity.class));
            }
        });

        scrollView.getPullRootView().findViewById(R.id.center_tv_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CenterMessageActivity.class));
            }
        });
        scrollView.getPullRootView().findViewById(R.id.center_tv_score).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.ToastMessage(context, "暂未开通~");
            }
        });
        scrollView.getPullRootView().findViewById(R.id.center_tv_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, CenterSettingActivity.class));
            }
        });


    }

    private void initData() {
        new MyInfoGetRequest(mHandler);
        center_tv_toux.setText(SharedPreferences.getInstance().getString("mobile", "--"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x11) {
            new MyInfoGetRequest(mHandler);
        }
    }
}
