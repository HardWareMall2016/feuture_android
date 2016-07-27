package com.zhan.ironfuture.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhan.framework.utils.PixelUtils;
import com.zhan.ironfuture.R;

/**
 * Created by WuYue on 2016/5/3.
 */
public class TipView extends LinearLayout {
    public TipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        TypedArray tArray = context.obtainStyledAttributes(attrs, R.styleable.TipView_LayoutParams);
        String name = tArray.getString(R.styleable.TipView_LayoutParams_name);
        Drawable bg = tArray.getDrawable(R.styleable.TipView_LayoutParams_tipDrawable);
        int tipDrawableSize=tArray.getDimensionPixelSize(R.styleable.TipView_LayoutParams_tipDrawableSize,PixelUtils.dp2px(10));
        tArray.recycle();

        setGravity(Gravity.CENTER_VERTICAL);
        View circleView = new View(context);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(tipDrawableSize, tipDrawableSize);
        circleView.setLayoutParams(layoutParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            circleView.setBackground(bg);
        }else{
            circleView.setBackgroundDrawable(bg);
        }
        TextView textView = new TextView(context);
        textView.setText(name);
        textView.setPadding(PixelUtils.dp2px(10), 0, 0, 0);
        addView(circleView);
        addView(textView);
    }
}
