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
import com.pu.gouthelper.bean.Recipe;

import java.util.List;

/**
 * 碱性菜谱列表
 */
public class RecipeListAdapter extends BaseAdapter {
    private List<Recipe> mlist;
    private Context context;

    public RecipeListAdapter(Context context, List<Recipe> mlist) {
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
        Recipe entity = mlist.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_recipe_list, null);
        ImageView recipe_img_icon = (ImageView) convertView.findViewById(R.id.recipe_img_icon);
        BitmapView.getInstance().display(recipe_img_icon, entity.getPic());
        TextView recipe_tv_title = (TextView) convertView.findViewById(R.id.recipe_tv_title);
        TextView recipe_tv_step= (TextView) convertView.findViewById(R.id.recipe_tv_step);

        recipe_tv_title.setText(entity.getTitle());
        recipe_tv_step.setText("3步/炒");

        return convertView;
    }


}
