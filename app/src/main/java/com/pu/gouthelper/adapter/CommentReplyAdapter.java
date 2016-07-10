package com.pu.gouthelper.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.bean.Comment;
import com.pu.gouthelper.bean.UserEntity;

import java.util.List;


public class CommentReplyAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private OnClickListener replyToReplyListener;
    private int parentPosition = -1;
    private List<Comment.ListEntity> replyList;
    private Context context;
    private String louzhu;

    public CommentReplyAdapter(Context context, List<Comment.ListEntity> replyList,
                               int parentPosition, OnClickListener replyToReplyListener, String louzhu) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.parentPosition = parentPosition;
        this.replyToReplyListener = replyToReplyListener;
        this.replyList = replyList;
        this.louzhu = louzhu;
    }

    @Override
    public int getCount() {
        return replyList.size();
    }

    public void clearList() {
        this.replyList.clear();
    }

    public void updateList(List<Comment.ListEntity> replyList) {
        this.replyList.addAll(replyList);
    }

    @Override
    public Comment.ListEntity getItem(int position) {
        return replyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.item_comment_reply, null);

        TextView tv_comment_reply_text = (TextView) convertView
                .findViewById(R.id.tv_comment_reply_text);
        TextView tv_comment_reply_writer = (TextView) convertView
                .findViewById(R.id.tv_comment_reply_writer);
        TextView tv_comment_reply1 = (TextView) convertView
                .findViewById(R.id.tv_comment_reply1);
        TextView tv_comment_reply2 = (TextView) convertView
                .findViewById(R.id.tv_comment_reply2);

        Comment.ListEntity reply = getItem(position);
        tv_comment_reply_text.setText(reply.getContent());
        UserEntity to_user = reply.getMemo().getTuser();
        UserEntity user = reply.getMemo().getUser();
        if (to_user == null || user == null) {
            tv_comment_reply_writer.setText(louzhu + ":");
            tv_comment_reply_writer.setTextColor(context.getResources().getColor(R.color.mian_green));
        } else {
            to_user.setCid(reply.getId());
            user.setCid(reply.getId());
            tv_comment_reply1.setText(user.getNickname());
            tv_comment_reply2.setText(to_user.getNickname()+":");
            tv_comment_reply1.setTag(user);
            tv_comment_reply2.setTag(to_user);
            tv_comment_reply1.setOnClickListener(replyToReplyListener);
            tv_comment_reply2.setOnClickListener(replyToReplyListener);
        }
        return convertView;
    }


}
