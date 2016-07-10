package com.pu.gouthelper.adapter;

import android.content.Context;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.BitmapView;
import com.pu.gouthelper.bean.GoutDrug;
import com.pu.gouthelper.bean.GoutKnow;
import com.pu.gouthelper.ui.CircleImageView;
import com.pu.gouthelper.utils.DateUtil;

import java.util.List;

/**
 * 痛风知识列表
 */
public class GoutMsgAdapter extends BaseAdapter {
    private List<GoutKnow> mlist;
    private Context context;

    public GoutMsgAdapter(Context context, List<GoutKnow> mlist) {
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
        GoutKnow entity = mlist.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_goutmsg_list, null);
        CircleImageView msg_img_pic = (CircleImageView) convertView.findViewById(R.id.msg_img_pic);
        msg_img_pic.setCircleRadiu(20);
        BitmapView.getInstance().display(msg_img_pic, entity.getPic());
        TextView goutmsg_tv_title = (TextView) convertView.findViewById(R.id.goutmsg_tv_title);
        TextView goutmsg_tv_time = (TextView) convertView.findViewById(R.id.goutmsg_tv_time);
        TextView msg_tv_zan = (TextView) convertView.findViewById(R.id.msg_tv_zan);
        TextView msg_tv_msg = (TextView) convertView.findViewById(R.id.msg_tv_msg);
        goutmsg_tv_title.setText(entity.getTitle());
        goutmsg_tv_time.setText(DateUtil.date2String(Long.parseLong(entity.getTm()) * 1000, "yyyy-MM-dd HH:mm"));
        msg_tv_zan.setText(entity.getUps());
        msg_tv_msg.setText(entity.getComments());
        return convertView;
    }


}
