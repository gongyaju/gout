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
import com.pu.gouthelper.bean.PurinFoodEntity;



import java.util.List;


public class PurinListAdapter extends BaseAdapter {
    private List<PurinFoodEntity> mlist;
    private Context context;

    public PurinListAdapter(Context context, List<PurinFoodEntity> mlist) {
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
        PurinFoodEntity entity = mlist.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_purin_list, null);
        ImageView purin_img_icon = (ImageView) convertView.findViewById(R.id.purin_img_icon);
        BitmapUtils.getInstance().display(purin_img_icon, entity.getPic());
        TextView purin_tv_name = (TextView) convertView.findViewById(R.id.purin_tv_name);
        purin_tv_name.setText(entity.getTitle());

        return convertView;
    }


}
