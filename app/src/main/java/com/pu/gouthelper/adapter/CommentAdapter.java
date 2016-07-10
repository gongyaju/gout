package com.pu.gouthelper.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.BitmapView;
import com.pu.gouthelper.bean.Comment;
import com.pu.gouthelper.bean.GoutKnow;
import com.pu.gouthelper.ui.MyListView;
import com.pu.gouthelper.utils.DateUtil;

import java.util.List;

/**
 * 痛风知识列表
 */
public class CommentAdapter extends BaseAdapter implements View.OnClickListener {

    private List<Comment> mlist;
    private Context context;
    private Callback callback;
    private View.OnClickListener replyToReplyListener;

    public CommentAdapter(Context context, List<Comment> mlist, Callback callback, View.OnClickListener replyToReplyListener) {
        this.mlist = mlist;
        this.context = context;
        this.callback = callback;
        this.replyToReplyListener=replyToReplyListener;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment entity = mlist.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
        ImageView comment_img_icon = (ImageView) convertView.findViewById(R.id.comment_img_icon);
        BitmapView.getInstance().display(comment_img_icon, entity.getUser().getAvatar());
        ImageButton comment_btn_comm = (ImageButton) convertView.findViewById(R.id.comment_btn_comm);
        comment_btn_comm.setOnClickListener(this);
        comment_btn_comm.setTag(position);
        TextView comment_tv_name = (TextView) convertView.findViewById(R.id.comment_tv_name);
        TextView comment_tv_zan = (TextView) convertView.findViewById(R.id.comment_tv_zan);
        TextView comment_tv_content = (TextView) convertView.findViewById(R.id.comment_tv_content);
        TextView comment_tv_allsay = (TextView) convertView.findViewById(R.id.comment_tv_allsay);
        comment_tv_name.setText(entity.getUser().getNickname());
        comment_tv_zan.setText(entity.getUps());
        comment_tv_content.setText(entity.getContent());
        MyListView comment_liner_ls = (MyListView) convertView.findViewById(R.id.comment_liner_ls);
        if (entity.getList() != null && entity.getList().size() > 0) {
            comment_liner_ls.setAdapter(new CommentReplyAdapter(context, entity.getList(), position, replyToReplyListener,entity.getUser().getNickname()));
        } else {
            comment_liner_ls.setVisibility(View.GONE);
            comment_tv_allsay.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public void onClick(View v) {
        callback.click(v);
    }
}
