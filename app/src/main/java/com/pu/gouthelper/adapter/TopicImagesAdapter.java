package com.pu.gouthelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pu.gouthelper.R;
import com.pu.gouthelper.base.BitmapView;

import java.util.List;

public class TopicImagesAdapter extends
        RecyclerView.Adapter<TopicImagesAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<String> mDatas;
    private Context mContext = null;

    public TopicImagesAdapter(Context context, List<String> datats) {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        ImageView topic_img;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_topic_img,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.topic_img = (ImageView) view.findViewById(R.id.topic_img);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        BitmapView.getInstance().display(viewHolder.topic_img, mDatas.get(i));
    }

}
