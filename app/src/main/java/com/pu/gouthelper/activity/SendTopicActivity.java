package com.pu.gouthelper.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.AppUtils;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.webservice.TopicSendRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Requiem on 2016/3/22.
 * 发布帖子
 */
@ContentView(R.layout.activity_send_topic)
public class SendTopicActivity extends SwipeBackActivity {
    private static final int REQUEST_IMAGE = 2;
    @ViewInject(R.id.topic_edt_title)
    private EditText topic_edt_title;
    @ViewInject(R.id.topic_edt_content)
    private EditText topic_edt_content;
    @ViewInject(R.id.topic_img_1)
    private ImageView topic_img_1;
    @ViewInject(R.id.topic_img_2)
    private ImageView topic_img_2;
    @ViewInject(R.id.topic_img_3)
    private ImageView topic_img_3;
    @ViewInject(R.id.topic_img_4)
    private ImageView topic_img_4;


    private Context mContext = null;

    private ArrayList<String> mSelectPath;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TopicSendRequest.SUCCESS:
                    UIHelper.ToastMessage(mContext, msg.obj + "");

                    break;
                case TopicSendRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
            }
            Intent intent = new Intent(mContext, SendTopicSuccessActivity.class);
            startActivity(intent);
            finish();
            endLoading();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initView();
        initData();
    }


    private void initView() {

    }

    private void initData() {
    }

    @Event(value = {R.id.topic_tv_sendok, R.id.noun_btn_goback, R.id.topic_img_1, R.id.topic_img_2, R.id.topic_img_3, R.id.topic_img_4}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.noun_btn_goback:
                finish();
                break;
            case R.id.topic_tv_sendok:
                String title = "";
                String content = topic_edt_content.getText().toString().trim();
                if (StringUtils.isEmpty(content)) {
                    UIHelper.ToastMessage(mContext, "内容不能空着~");
                } else {
                    new TopicSendRequest(mHandler, title, content, mSelectPath);
                    showLoading(mContext);
                }
                break;
            case R.id.topic_img_1:
            case R.id.topic_img_2:
            case R.id.topic_img_3:
            case R.id.topic_img_4:
                Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
                // 是否显示拍摄图片
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                // 最大可选择图片数量
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 4);
                // 选择模式
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                // 默认选择
                if (mSelectPath != null && mSelectPath.size() > 0) {
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
                }
                startActivityForResult(intent, REQUEST_IMAGE);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                switch (mSelectPath.size()) {
                    case 0:
                        break;
                    case 1:
                        topic_img_1.setImageBitmap(AppUtils.getImageOption(mSelectPath.get(0)));
                        topic_img_2.setImageResource(0);
                        topic_img_3.setImageResource(0);
                        topic_img_4.setImageResource(0);
                        break;
                    case 2:
                        topic_img_1.setImageBitmap(AppUtils.getImageOption(mSelectPath.get(0)));
                        topic_img_2.setImageBitmap(AppUtils.getImageOption(mSelectPath.get(1)));
                        topic_img_3.setImageResource(0);
                        topic_img_4.setImageResource(0);
                        topic_img_2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        topic_img_1.setImageBitmap(AppUtils.getImageOption(mSelectPath.get(0)));
                        topic_img_2.setImageBitmap(AppUtils.getImageOption(mSelectPath.get(1)));
                        topic_img_3.setImageBitmap(AppUtils.getImageOption(mSelectPath.get(2)));
                        topic_img_4.setImageResource(0);
                        topic_img_2.setVisibility(View.VISIBLE);
                        topic_img_3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        topic_img_1.setImageBitmap(AppUtils.getImageOption(mSelectPath.get(0)));
                        topic_img_2.setImageBitmap(AppUtils.getImageOption(mSelectPath.get(1)));
                        topic_img_3.setImageBitmap(AppUtils.getImageOption(mSelectPath.get(2)));
                        topic_img_4.setImageBitmap(AppUtils.getImageOption(mSelectPath.get(3)));
                        topic_img_2.setVisibility(View.VISIBLE);
                        topic_img_3.setVisibility(View.VISIBLE);
                        topic_img_4.setVisibility(View.VISIBLE);
                        break;

                }

            }
        }
    }
}
