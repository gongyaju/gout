package com.pu.gouthelper.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.adapter.Callback;
import com.pu.gouthelper.adapter.CommentAdapter;
import com.pu.gouthelper.base.BitmapUtils;
import com.pu.gouthelper.base.BitmapView;
import com.pu.gouthelper.base.F;
import com.pu.gouthelper.base.StringUtils;
import com.pu.gouthelper.bean.ActionItem;
import com.pu.gouthelper.bean.Comment;
import com.pu.gouthelper.bean.TopicDetail;
import com.pu.gouthelper.bean.UserEntity;
import com.pu.gouthelper.common.CommentFun;
import com.pu.gouthelper.dialog.TitlePopup;
import com.pu.gouthelper.ui.MyListView;
import com.pu.gouthelper.ui.UIHelper;

import com.pu.gouthelper.ui.swipebacklayout.SwipeBackActivity;
import com.pu.gouthelper.utils.DateUtil;
import com.pu.gouthelper.utils.ShareUtils;
import com.pu.gouthelper.utils.Utils;
import com.pu.gouthelper.webservice.CommentAddRequest;
import com.pu.gouthelper.webservice.CommentListRequest;
import com.pu.gouthelper.webservice.TopicDetailRequest;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.dynamicbox.DynamicBox;


/**
 * Created by Requiem on 2016/4/2.
 * 帖子详情
 */
@ContentView(R.layout.activity_topic_detail)
public class TopicDetailActivity extends SwipeBackActivity implements TitlePopup.OnItemOnClickListener, Callback, CommentFun.CommentDialogListener {

    private Context mContext;
    private String id = "";
    @ViewInject(R.id.topicdetail_img_icon)
    private ImageView topicdetail_img_icon;
    @ViewInject(R.id.topicdetail_tv_name)
    private TextView topicdetail_tv_name;
    @ViewInject(R.id.topicdetail_tv_title)
    private TextView topicdetail_tv_title;
    @ViewInject(R.id.topicdetail_tv_time)
    private TextView topicdetail_tv_time;
    @ViewInject(R.id.topicdetail_tv_content)
    private TextView topicdetail_tv_content;
    @ViewInject(R.id.topic_tv_read)
    private TextView topic_tv_read;
    @ViewInject(R.id.topic_tv_msg)
    private TextView topic_tv_msg;
    @ViewInject(R.id.topicdetail_img_pic)
    private LinearLayout topicdetail_img_pic;

    @ViewInject(R.id.group_discuss)
    private EditText group_discuss;
    @ViewInject(R.id.topic_ls_comment)
    private MyListView topic_ls_comment;
    private TitlePopup titlePopup;
    private CommentAdapter adapter = null;
    private List<Comment> mList = new ArrayList<>();
    private DynamicBox box;
    private TopicDetail detail;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TopicDetailRequest.SUCCESS:
                    detail = (TopicDetail) msg.obj;
                    if (detail != null) {
                        setData(detail);
                    }
                    break;
                case TopicDetailRequest.ERROR:
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
                case CommentAddRequest.ERROR:
                    UIHelper.ToastMessage(mContext, msg.obj + "");
                    break;
                case CommentAddRequest.SUCCESS:
                    new CommentListRequest(mHandler, "1", id, "" + F.PAGE_SIZE);
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
        initView();
        loadData();
    }

    private void initView() {
        box = new DynamicBox(mContext, topic_ls_comment);
        View customView = getLayoutInflater().inflate(R.layout.hint_no_message, null, false);
        box.addCustomView(customView, "hint_no_message");
        popup();
    }

    private void loadData() {
        adapter = new CommentAdapter(mContext, mList, this, replyToReplyListener);
        topic_ls_comment.setAdapter(adapter);
        new TopicDetailRequest(mHandler, id);
        new CommentListRequest(mHandler, "1", id, "" + F.PAGE_SIZE);
        showLoading(mContext);

    }
    /**
     * 互相回复的监听（楼中楼）
     */
    /**
     * 互相回复的监听（楼中楼）
     */
    private View.OnClickListener replyToReplyListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                UserEntity userEntity = (UserEntity) v.getTag();
                CommentFun.inputComment(TopicDetailActivity.this, topic_ls_comment, v, userEntity, TopicDetailActivity.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Event(value = {R.id.btn_share, R.id.topic_btn_goback, R.id.topic_tv_share, R.id.group_discuss_submit}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.topic_btn_goback:
                finish();
                break;
            case R.id.btn_share:
                ShareUtils.share(this, detail.getTitle(), "");
                break;
            case R.id.topic_tv_share:
                ShareUtils.share(this, detail.getTitle(), "");
                break;
            case R.id.group_discuss_submit:
                String content = group_discuss.getText().toString().trim();
                if (StringUtils.isEmpty(content)) {
                    UIHelper.ToastMessage(mContext, "先说点什么再发表吧~");
                    return;
                }
                new CommentAddRequest(mHandler, "1", id, "", "", content);
                showLoading(mContext);
                break;
        }
    }

    public void setData(TopicDetail data) {
        for (String url : data.getAttrs()) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 20, 30, 20);
            imageView.setLayoutParams(lp);
            BitmapView.getInstance().display(imageView, url);
            topicdetail_img_pic.addView(imageView);
        }

        BitmapUtils.getInstance().display(topicdetail_img_icon, data.getUser().getAvatar());
        topicdetail_tv_name.setText(data.getUser().getNickname());
        topicdetail_tv_title.setText(data.getTitle());
        topicdetail_tv_time.setText(DateUtil.date2String(Long.parseLong(data.getTm()) * 1000, "yyyy-MM-dd HH:mm"));
        topicdetail_tv_content.setText(data.getContent());
        topic_tv_read.setText(data.getViews());
        topic_tv_msg.setText(data.getComments());

    }

    /**
     * 弹出评论,赞菜单加载
     */
    private void popup() {
        titlePopup = new TitlePopup(mContext, Utils.dip2px(mContext, 165), Utils.dip2px(mContext, 40));
        titlePopup.addAction(new ActionItem(mContext, "赞", R.drawable.circle_praise));
        titlePopup.addAction(new ActionItem(mContext, "评论", R.drawable.circle_comment));
        titlePopup.setItemOnClickListener(this);
    }

    @Override
    public void onItemClick(ActionItem item, int position) {
        switch (position) {
            case 0://赞
                UIHelper.ToastMessage(mContext, "您已经赞过了~");
                break;
            case 1://评论
                UIHelper.ToastMessage(mContext, "评论~");
//                EditText disInputText = (EditText) findViewById(R.id.group_discuss);
//                disInputText.requestFocus();
                break;
        }
    }

    @Override
    public void click(View v) {
        try {
            Comment comment = mList.get(Integer.parseInt(v.getTag() + ""));
            comment.getUser().setCid(comment.getId());
            CommentFun.inputComment(TopicDetailActivity.this, topic_ls_comment, v, comment.getUser(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        titlePopup.setAnimationStyle(R.style.cricleBottomAnimation);
//        titlePopup.show(v);
    }

    @Override
    public void onClickPublish(Dialog dialog, EditText input, TextView btn, UserEntity userEntity) {
        final String content = input.getText().toString();
        if (content.trim().equals("")) {
            UIHelper.ToastMessage(mContext, "评论不能为空");
            return;
        } else {
            new CommentAddRequest(mHandler, "1", id, userEntity.getCid(), userEntity.getId(), content);
        }
        dialog.dismiss();
    }

    @Override
    public void onShow(int[] inputViewCoordinatesOnScreen) {

    }

    @Override
    public void onDismiss() {

    }
}