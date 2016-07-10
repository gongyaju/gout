
package com.pu.gouthelper.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ColorPickerView extends View {

    private Paint paint;
    private float centerX = 0;
    private float centerY = 0;

    public ColorPickerView(Context context,int color) {
        super(context);
        paint = new Paint(); //设置一个笔刷大小是3的黄色的画笔
        paint.setColor(color);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
    }

    public ColorPickerView(Context context, AttributeSet attrs,
                           int defStyle) {
        super(context, attrs, defStyle);

    }

    public ColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, 30, paint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {

        centerX = (right - left) / 2;
        centerY = (bottom - top) / 2;

        super.onLayout(changed, left, top, right, bottom);
    }

}