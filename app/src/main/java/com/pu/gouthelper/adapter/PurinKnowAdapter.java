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
import com.pu.gouthelper.bean.PurinNews;
import com.pu.gouthelper.bean.PurinRecipe;
import com.pu.gouthelper.utils.DateUtil;

import java.util.List;

/**
 * 嘌呤知识
 */
public class PurinKnowAdapter extends BaseAdapter {
    private List<PurinNews> mlist;
    private Context context;

    public PurinKnowAdapter(Context context, List<PurinNews> mlist) {
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
        PurinNews entity = mlist.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_pruin_know, null);

        TextView know_tv_title = (TextView) convertView.findViewById(R.id.know_tv_title);
        TextView know_tv_tm = (TextView) convertView.findViewById(R.id.know_tv_tm);
        TextView know_tv_content = (TextView) convertView.findViewById(R.id.know_tv_content);
        know_tv_title.setText(entity.getTitle());
        know_tv_tm.setText(DateUtil.date2String(Long.parseLong(entity.getTm()) * 1000, "yyyy-MM-dd HH:mm"));
        know_tv_content.setText(entity.getSummary());
        return convertView;
    }


}
