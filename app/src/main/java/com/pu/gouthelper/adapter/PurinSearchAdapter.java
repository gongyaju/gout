package com.pu.gouthelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.BitmapView;
import com.pu.gouthelper.bean.GoutDrug;
import com.pu.gouthelper.bean.PurinFoodEntity;

import java.util.List;

/**
 * purin查找
 */
public class PurinSearchAdapter extends BaseAdapter {
    private List<PurinFoodEntity> mlist;
    private Context context;

    public PurinSearchAdapter(Context context, List<PurinFoodEntity> mlist) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_search_list, null);
        TextView search_tv_info = (TextView) convertView.findViewById(R.id.search_tv_info);
        search_tv_info.setText(entity.getTitle());
        return convertView;
    }


}
