package com.pu.gouthelper.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.pu.gouthelper.R;
import com.pu.gouthelper.adapter.Callback;
import com.pu.gouthelper.adapter.CommentAdapter;
import com.pu.gouthelper.base.BitmapView;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.SDCardUtils;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.bean.Comment;
import com.pu.gouthelper.bean.GoutKnowDetail;
import com.pu.gouthelper.bean.RewardEntity;
import com.pu.gouthelper.bean.UserEntity;
import com.pu.gouthelper.common.CommentFun;
import com.pu.gouthelper.ui.MyListView;
import com.pu.gouthelper.ui.UIHelper;
import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.utils.ShareUtils;
import com.pu.gouthelper.webservice.CommentAddRequest;
import com.pu.gouthelper.webservice.CommentListRequest;
import com.pu.gouthelper.webservice.GoutKnowInfoRequest;
import com.pu.gouthelper.webservice.RewardListRequest;
import com.pu.gouthelper.webservice.ZDownRequest;
import com.pu.gouthelper.webservice.ZUpRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * Created by Requiem on 2016/3/22.
 * 痛风知识详情
 */
@ContentView(R.layout.activity_goutmsg_detail)
public class GoutMsgDetailActivity extends SwipeBackActivity implements Callback, CommentFun.CommentDialogListener {
    private Context mContext;
    private String id = "";//search id
    @ViewInject(R.id.noun_btn_title)
    private TextView noun_btn_title;
    @ViewInject(R.id.detail_web_content)
    private WebView detail_web_content;
    @ViewInject(R.id.say_edt_content)
    private EditText say_edt_content;
    @ViewInject(R.id.purin_ls_say)
    private MyListView purin_ls_say;
    @ViewInject(R.id.textView5)
    private TextView textView5;
    @ViewInject(R.id.textView6)
    private TextView textView6;
    @ViewInject(R.id.msg_tv_zan)
    private TextView msg_tv_zan;
    @ViewInject(R.id.detail_tv_dashangcount)
    private TextView detail_tv_dashangcount;
    @ViewInject(R.id.detail_img_pics)
    private LinearLayout detail_img_pics;
    private DynamicBox box;
    private List<Comment> mList = new ArrayList<>();
    private CommentAdapter adapter = null;
    private GoutKnowDetail entity = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GoutKnowInfoRequest.SUCCESS:
                    entity = (GoutKnowDetail) msg.obj;
                    new RewardListRequest(mHandler, F.PAGE_SIZE + "", "2", entity.getId());
                    if (entity != null) {
                        setData();
                    }
                    break;
                case GoutKnowInfoRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
                case CommentAddRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
                case CommentAddRequest.SUCCESS:
                    getCommentList(id);
                    say_edt_content.setText("");
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
                case CommentListRequest.SUCCESS:
                    mList.clear();
                    mList.addAll((List<Comment>) msg.obj);
                    adapter.notifyDataSetChanged();
                    break;
                case CommentListRequest.ERROR:
                    box.showCustomView("hint_no_message");
                    break;

                case ZUpRequest.SUCCESS:
                    Drawable drawable = mContext.getResources().getDrawable(R.drawable.zan_click);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                    msg_tv_zan.setCompoundDrawables(drawable, null, null, null);
                    new GoutKnowInfoRequest(mHandler, id);
                    break;
                case ZDownRequest.SUCCESS:
                    Drawable drawable1 = mContext.getResources().getDrawable(R.drawable.icon_zan);
                    drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());//必须设置图片大小，否则不显示
                    msg_tv_zan.setCompoundDrawables(drawable1, null, null, null);
                    new GoutKnowInfoRequest(mHandler, id);
                    break;
                case ZDownRequest.ERROR:
                case ZUpRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
                case RewardListRequest.SUCCESS:
                    List<RewardEntity> mList = (List<RewardEntity>) msg.obj;
                    detail_tv_dashangcount.setText(mList.size() + "");
                    if (mList != null && mList.size() == 0) {
                        detail_img_pics.setVisibility(View.GONE);
                    }
                    for (RewardEntity rewardEntity : mList) {
                        ImageView imageView = new ImageView(mContext);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.MATCH_PARENT);
                        imageView.setLayoutParams(lp);
                        lp.setMargins(0, 0, 10, 0);
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        BitmapView.getInstance().display(imageView, rewardEntity.getUser().getAvatar());
                        detail_img_pics.addView(imageView);
                    }
                    break;
                case RewardListRequest.ERROR:
                    detail_tv_dashangcount.setText("0");
                    detail_img_pics.setVisibility(View.GONE);
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
        initView();
        initData();
    }

    private void initView() {

        box = new DynamicBox(mContext, purin_ls_say);
        View customView = getLayoutInflater().inflate(R.layout.hint_no_message, null, false);
        box.addCustomView(customView, "hint_no_message");
        adapter = new CommentAdapter(mContext, mList, this, replyToReplyListener);
        purin_ls_say.setAdapter(adapter);
    }

    private void initData() {
        new GoutKnowInfoRequest(mHandler, id);
        getCommentList(id);
        showLoading(mContext);
    }

    private void setData() {
        try {
            textView5.setText(entity.getAuthor().getTitle());
            textView6.setText(entity.getAuthor().getDesc());
            if (entity.getLike() != null && entity.getLike().getUp().equals("1")) {
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.zan_click);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                msg_tv_zan.setCompoundDrawables(drawable, null, null, null);
            }else{
                Drawable drawable1 = mContext.getResources().getDrawable(R.drawable.icon_zan);
                drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());//必须设置图片大小，否则不显示
                msg_tv_zan.setCompoundDrawables(drawable1, null, null, null);
            }
            msg_tv_zan.setText(entity.getUps());
            noun_btn_title.setText(entity.getTitle());
            detail_web_content.loadDataWithBaseURL(null, entity.getContent(), "text/html", "utf-8", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCommentList(String id) {
        new CommentListRequest(mHandler, "2", id, "" + F.PAGE_SIZE);
    }

    @Event(value = {R.id.noun_btn_goback, R.id.say_bnt_send, R.id.btn_share, R.id.msg_tv_zan, R.id.goutmsg_tv_shang}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.noun_btn_goback:
                finish();
                break;
            case R.id.say_bnt_send:
                String content = say_edt_content.getText().toString().trim();
                if (StringUtils.isEmpty(content)) {
                    UIHelper.ToastMessage(mContext, "先说点什么再发表吧~");
                    return;
                }
                if (StringUtils.isEmpty(id)) {
                    UIHelper.ToastMessage(mContext, "出错啦！稍等一会再评论吧~");
                    return;
                }
                new CommentAddRequest(mHandler, "2", id, "", "", content);
                showLoading(mContext);
                break;
            case R.id.btn_share:
                String path = SDCardUtils.getCurrentScreen(this);
                ShareUtils.share(this, entity.getTitle(), "",path);
                break;
            case R.id.msg_tv_zan:
                if (entity.getLike() != null && entity.getLike().getDown().equals("0")) {
                    new ZUpRequest(mHandler, entity.getId(), "");
                } else {
                    new ZDownRequest(mHandler, entity.getId(), "");
                }
                showLoading(mContext);
                break;
            case R.id.goutmsg_tv_shang:
                Intent intent = new Intent(this, GiveActivity.class);
                intent.putExtra("sid",entity.getId());
                intent.putExtra("type","2");
                startActivity(intent);
                break;
        }
    }

    /**
     * 互相回复的监听（楼中楼）
     */
    private View.OnClickListener replyToReplyListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                UserEntity userEntity = (UserEntity) v.getTag();
                CommentFun.inputComment(GoutMsgDetailActivity.this, purin_ls_say, v, userEntity, GoutMsgDetailActivity.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void click(View v) {
        try {
            Comment comment = mList.get(Integer.parseInt(v.getTag() + ""));
            comment.getUser().setCid(comment.getId());
            CommentFun.inputComment(GoutMsgDetailActivity.this, purin_ls_say, v, comment.getUser(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickPublish(Dialog dialog, EditText input, TextView btn, UserEntity userEntity) {
        final String content = input.getText().toString();
        if (content.trim().equals("")) {
            UIHelper.ToastMessage(mContext, "评论不能为空");
            return;
        } else {
            new CommentAddRequest(mHandler, "2", id, userEntity.getCid(), userEntity.getId(), content);
        }
        dialog.dismiss();
    }

    @Override
    public void onShow(int[] inputViewCoordinatesOnScreen) {
//        if (purin_ls_say != null) {
//            int span = btnComment.getId() == R.id.comment_btn_comm ? 0 : btnComment.getHeight();
//            listView.smoothScrollBy(coord[1] + span - inputViewCoordinatesInScreen[1], 1000);
//        }
    }

    @Override
    public void onDismiss() {

    }
}
