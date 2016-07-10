package com.pu.gouthelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.pu.gouthelper.R;


import java.util.List;


public class SosListAdapter extends BaseAdapter {
    private List<String> mlist;
    private Context context;

    public SosListAdapter(Context context, List<String> mlist) {
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
        String entity = mlist.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_sos_list, null);
        TextView sos_tv_content = (TextView) convertView.findViewById(R.id.sos_tv_content);
        sos_tv_content.setText(entity);
        View sos_v_topline=convertView.findViewById(R.id.sos_v_topline);
        View sos_v_btline=convertView.findViewById(R.id.sos_v_btline);
        if (position==0){
            sos_v_topline.setVisibility(View.INVISIBLE);
        }
        if (position==3){
            sos_v_btline.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }


}
