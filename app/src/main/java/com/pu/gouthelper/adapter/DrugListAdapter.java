package com.pu.gouthelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.BitmapUtils;
import com.pu.gouthelper.base.BitmapView;
import com.pu.gouthelper.bean.GoutDrug;
import com.pu.gouthelper.utils.BitmapUtil;
import com.pu.gouthelper.utils.DateUtil;

import java.util.List;

/**
 * 口碑药品列表
 */
public class DrugListAdapter extends BaseAdapter {
    private List<GoutDrug> mlist;
    private Context context;

    public DrugListAdapter(Context context, List<GoutDrug> mlist) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_drug_list, null);
        ImageView drug_img_icon = (ImageView) convertView.findViewById(R.id.drug_img_icon);
        BitmapView.getInstance().display(drug_img_icon, entity.getPic());
        TextView drug_tv_title = (TextView) convertView.findViewById(R.id.drug_tv_title);
        TextView drug_tv_content = (TextView) convertView.findViewById(R.id.drug_tv_content);
        TextView drug_tv_zan = (TextView) convertView.findViewById(R.id.progressBar1_text1);
        TextView drug_tv_cai = (TextView) convertView.findViewById(R.id.progressBar1_text2);

        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar1);
        drug_tv_title.setText(entity.getTitle());
        drug_tv_content.setText(entity.getSummary());
        drug_tv_zan.setText(entity.getUps() + "人顶");
        drug_tv_cai.setText(entity.getDowns() + "人踩");
        progressBar.setMax(Integer.parseInt(entity.getUps()) + Integer.parseInt(entity.getDowns()));
        progressBar.setProgress(Integer.parseInt(entity.getUps()));

        return convertView;
    }


}
