package com.pu.gouthelper.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.BitmapUtils;
import com.pu.gouthelper.base.BitmapView;
import com.pu.gouthelper.bean.Topic;
import com.pu.gouthelper.utils.BitmapUtil;
import com.pu.gouthelper.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 口碑药品列表
 */
public class TopicListAdapter extends BaseAdapter {
    private List<Topic> mlist;
    private Context context;

    public TopicListAdapter(Context context, List<Topic> mlist) {
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
        Topic entity = mlist.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_topics_list, null);
        ImageView topic_img_icon = (ImageView) convertView.findViewById(R.id.topic_img_icon);
        BitmapUtils.getInstance().display(topic_img_icon, entity.getUser().getAvatar());
        TextView topic_tv_title = (TextView) convertView.findViewById(R.id.topic_tv_title);
        TextView topic_tv_time = (TextView) convertView.findViewById(R.id.topic_tv_time);
        TextView topic_tv_content = (TextView) convertView.findViewById(R.id.topic_tv_content);
        topic_tv_title.setText(entity.getUser().getNickname());
        topic_tv_time.setText(DateUtil.date2String(Long.parseLong(entity.getTm()) * 1000, "yyyy-MM-dd HH:mm"));
        topic_tv_content.setText(entity.getContent());
        TextView topic_tv_read = (TextView) convertView.findViewById(R.id.topic_tv_read);
        topic_tv_read.setText(entity.getViews());
        TextView topic_tv_msg = (TextView) convertView.findViewById(R.id.topic_tv_msg);
        topic_tv_msg.setText(entity.getComments());
        LinearLayout topic_gy_imgs = (LinearLayout) convertView.findViewById(R.id.topic_gy_imgs);
        if (entity.getAttrs().size()==0){
            topic_gy_imgs.setVisibility(View.GONE);
        }else{
            for (String url : entity.getAttrs()) {
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(234, LinearLayout.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(lp);
                lp.setMargins(0, 0, 20, 0);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                BitmapView.getInstance().display(imageView, url);
                topic_gy_imgs.addView(imageView);
            }
        }
        return convertView;
    }
}
