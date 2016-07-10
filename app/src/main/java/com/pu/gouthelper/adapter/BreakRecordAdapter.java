package com.pu.gouthelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.bean.AttackRecord;
import com.pu.gouthelper.bean.GoutDrug;
import com.pu.gouthelper.utils.DateUtil;

import java.util.List;

/**
 * 发作列表
 */
public class BreakRecordAdapter extends BaseAdapter {
    private List<AttackRecord> mlist;
    private Context context;

    public BreakRecordAdapter(Context context, List<AttackRecord> mlist) {
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
        AttackRecord entity = mlist.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_breakrecord_list, null);
        TextView search_tv_info = (TextView) convertView.findViewById(R.id.search_tv_info);
        search_tv_info.setText(DateUtil.date2String(Long.parseLong(entity.getTm()) * 1000, "yyyy.MM.dd HH:mm"));
        return convertView;
    }


}
