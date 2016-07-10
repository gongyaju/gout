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
import com.pu.gouthelper.bean.Doasge;
import com.pu.gouthelper.bean.GoutDrug;

import java.util.List;

/**
 * 计量选择列表
 */
public class DosageListAdapter extends BaseAdapter {
    private List<Doasge> mlist;
    private Context context;

    public DosageListAdapter(Context context, List<Doasge> mlist) {
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
        Doasge entity = mlist.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_dosage_list, null);
        TextView doasge_tv = (TextView) convertView.findViewById(R.id.doasge_tv);
        doasge_tv.setText(entity.getDoasge());
        if (entity.isSelect()) {
            doasge_tv.setBackgroundResource(R.drawable.btn_yes_select);
        }else{
            doasge_tv.setBackgroundResource(R.drawable.btn_no_select);
        }
        return convertView;
    }


}
