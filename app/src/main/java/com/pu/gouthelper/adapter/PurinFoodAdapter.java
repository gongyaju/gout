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
import com.pu.gouthelper.bean.GoutKnow;
import com.pu.gouthelper.bean.PurinRecipe;
import com.pu.gouthelper.ui.CircleImageView;
import com.pu.gouthelper.utils.DateUtil;

import java.util.List;

/**
 * 嘌呤食谱
 */
public class PurinFoodAdapter extends BaseAdapter {
    private List<PurinRecipe> mlist;
    private Context context;

    public PurinFoodAdapter(Context context, List<PurinRecipe> mlist) {
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
        PurinRecipe entity = mlist.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_pruin_food, null);
        ImageView msg_img_pic = (ImageView) convertView.findViewById(R.id.food_tv_icon);
        BitmapUtils.getInstance().display(msg_img_pic, entity.getPic());
        TextView food_tv_title = (TextView) convertView.findViewById(R.id.food_tv_title);
        TextView food_tv_tm = (TextView) convertView.findViewById(R.id.food_tv_tm);
        food_tv_title.setText(entity.getTitle());
        food_tv_tm.setText(DateUtil.date2String(Long.parseLong(entity.getTm()) * 1000, "yyyy-MM-dd HH:mm"));
        return convertView;
    }


}
