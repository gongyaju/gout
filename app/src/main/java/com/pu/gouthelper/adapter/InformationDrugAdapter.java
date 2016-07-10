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
import com.pu.gouthelper.bean.AttackRecord;
import com.pu.gouthelper.bean.GoutDrug;
import com.pu.gouthelper.utils.DateUtil;

import java.util.List;

/**
 * 用过的药品
 */
public class InformationDrugAdapter extends BaseAdapter {
    private List<GoutDrug> mlist;
    private Context context;

    public InformationDrugAdapter(Context context, List<GoutDrug> mlist) {
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
        GoutDrug entity = mlist.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_information_drug, null);
        ImageView drug_img_pic = (ImageView) convertView.findViewById(R.id.drug_img_pic);
        TextView drug_tv_name = (TextView) convertView.findViewById(R.id.drug_tv_name);
        BitmapView.getInstance().display(drug_img_pic, entity.getPic());
        drug_tv_name.setText(entity.getTitle());
        ImageView drug_img_isselect = (ImageView) convertView.findViewById(R.id.drug_img_isselect);
        if (entity.isSelect()) {
            drug_img_isselect.setImageResource(R.drawable.drug_select);
        } else {
            drug_img_isselect.setImageResource(R.drawable.drug_unselect);
        }
        return convertView;
    }


}
