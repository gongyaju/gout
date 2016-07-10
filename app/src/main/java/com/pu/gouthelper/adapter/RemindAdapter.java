package com.pu.gouthelper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pu.gouthelper.R;
import com.pu.gouthelper.bean.RemindEntity;
import com.pu.gouthelper.utils.DateUtil;
import com.pu.gouthelper.utils.Time;

import java.util.List;

/**
 * Created by Requiem on 2016/4/24.
 */
public class RemindAdapter extends BaseAdapter {

    private List<RemindEntity> mlist = null;
    private Context context;

    public RemindAdapter(Context context, List<RemindEntity> mlist) {
        this.mlist = mlist;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public RemindEntity getItem(int position) {
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
                    R.layout.item_remind_list, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        RemindEntity item = getItem(position);
        try {
            String[] usa2 = JSON.parseObject(item.getDtm(), new TypeReference<String[]>() {
            });
            holder.remind_tv_title.setText(item.getTitle());
            holder.remind_tv_days.setText(DateUtil.date2String(Long.parseLong(item.getTm()) * 1000, "yyyy-MM-dd") + "至" + DateUtil.date2String((Long.parseLong(item.getTm()) + Integer.parseInt(item.getDays()) * 60 * 60 * 24) * 1000, "yyyy-MM-dd"));
            holder.remind_tv_dosage.setText(item.getDosage());
            holder.remind_tv_title.setTextColor(Integer.parseInt(item.getColor()));
            holder.remind_tv_dosage.setTextColor(Integer.parseInt(item.getColor()));
            /**
             * 下次嗑药时间  对比当前时间 取下一次
             */
            for (int i = 0; i < usa2.length; i++) {
                if (!usa2[i].equals("无")) {
                    long diff = Time.hourMhour(usa2[i]);
                    if (diff>0){
                        holder.remind_tv_dtm.setText(usa2[i]);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolder {
        TextView remind_tv_title;
        TextView remind_tv_days;
        TextView remind_tv_dosage;
        TextView remind_tv_dtm;

        public ViewHolder(View view) {
            remind_tv_title = (TextView) view.findViewById(R.id.remind_tv_title);
            remind_tv_days = (TextView) view.findViewById(R.id.remind_tv_days);
            remind_tv_dosage = (TextView) view.findViewById(R.id.remind_tv_dosage);
            remind_tv_dtm = (TextView) view.findViewById(R.id.remind_tv_dtm);
            view.setTag(this);
        }
    }

}
