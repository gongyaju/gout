package com.pu.gouthelper.base;


import android.widget.ImageView;

import com.pu.gouthelper.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;


/**
 * Created by gyj on 2015/11/16.
 */
public class BitmapView {

    private static BitmapView instance = null;
    private ImageOptions imageOptions = null;

    public static BitmapView getInstance() {
        if (instance == null) {
            instance = new BitmapView();
        }
        return instance;
    }


    private BitmapView() {
        imageOptions = new ImageOptions.Builder()
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.ic_img_user_default)//加载中默认显示图片
                .setUseMemCache(true)
                .setFailureDrawableId(R.drawable.ic_img_user_default)//加载失败后默认显示图片
                .build();
    }

    public void display(ImageView imageView, String url) {
        x.image().bind(imageView, url, imageOptions);
    }
}
