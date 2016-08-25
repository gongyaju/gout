package com.pu.gouthelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.BitmapUtils;
import com.pu.gouthelper.base.BitmapView;
import com.pu.gouthelper.bean.MyTopic;
import com.pu.gouthelper.utils.DateUtil;
import com.pu.gouthelper.utils.ShareUtils;

import java.util.List;

/**
 * 我的帖子
 */
public class MyTopicAdapter extends BaseAdapter {
    private List<MyTopic> mlist;
    private Context context;

    public MyTopicAdapter(Context context, List<MyTopic> mlist) {
        this.mlist = mlist;
        this.context = context;
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
        MyTopic entity = mlist.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_mytopic_list, null);
        ImageView mytopic_img_icon = (ImageView) convertView.findViewById(R.id.mytopic_img_icon);
        BitmapUtils.getInstance().display(mytopic_img_icon, entity.getUser().getAvatar());
        TextView mytopic_tv_name = (TextView) convertView.findViewById(R.id.mytopic_tv_name);
        mytopic_tv_name.setText(entity.getUser().getNickname());
        TextView mytopic_tv_content = (TextView) convertView.findViewById(R.id.mytopic_tv_content);
        mytopic_tv_content.setText(entity.getTitle());
        TextView mytopic_tv_time = (TextView) convertView.findViewById(R.id.mytopic_tv_time);
        mytopic_tv_time.setText(DateUtil.date2String(Long.parseLong(entity.getTm()) * 1000, "yyyy-MM-dd HH:mm"));

        return convertView;
    }


}
