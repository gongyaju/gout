package com.pu.gouthelper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pu.gouthelper.R;
import com.pu.gouthelper.bean.Address;
import com.pu.gouthelper.bean.RemindEntity;
import com.pu.gouthelper.utils.DateUtil;
import com.pu.gouthelper.utils.Time;

import java.util.List;

/**
 * Created by Requiem on 2016/4/24.
 */
public class AddressAdapter extends BaseAdapter {

    private List<Address> mlist = null;
    private Context context;

    public AddressAdapter(Context context, List<Address> mlist) {
        this.mlist = mlist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Address getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.item_address_list, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        Address item = getItem(position);

        holder.address_tv_info.setText(item.getUsername() + item.getPhone());
        holder.address_tv_address.setText(item.getAddress());

        return convertView;
    }

    class ViewHolder {
        TextView address_tv_info;
        TextView address_tv_address;


        public ViewHolder(View view) {
            address_tv_info = (TextView) view.findViewById(R.id.address_tv_info);
            address_tv_address = (TextView) view.findViewById(R.id.address_tv_address);
            view.setTag(this);
        }
    }

}
