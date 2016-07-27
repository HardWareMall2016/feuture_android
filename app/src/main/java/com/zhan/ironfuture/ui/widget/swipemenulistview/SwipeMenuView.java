package com.zhan.ironfuture.ui.widget.swipemenulistview;

import java.util.List;


import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SwipeMenuView extends LinearLayout implements OnClickListener {

    private SwipeMenuLayout mLayout;
    private SwipeMenu mMenu;
    private OnSwipeItemClickListener onItemClickListener;
    private int groupPosition;
    private int childPosition;
    //private int position;

    /*public int getPosition() {
        return position;
    }*/

    /*public void setPosition(int position) {
        this.position = position;
    }*/


    public void setPosition(int groupPosition,int childPosition) {
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
    }


    public SwipeMenuView(SwipeMenu menu) {
        super(menu.getContext());
        mMenu = menu;
        List<SwipeMenuItem> items = menu.getMenuItems();
        for (SwipeMenuItem item : items) {
            addItem(item);
        }
    }

    private void addItem(SwipeMenuItem item) {
        LayoutParams params = new LayoutParams(item.getWidth(), LayoutParams.MATCH_PARENT);
        LinearLayout parent = new LinearLayout(getContext());
        parent.setGravity(Gravity.CENTER);
        parent.setOrientation(LinearLayout.HORIZONTAL);
        parent.setLayoutParams(params);
        parent.setTag(item);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            parent.setBackground(item.getBackground());
        }else{
            parent.setBackgroundDrawable(item.getBackground());
        }
        parent.setOnClickListener(this);
        addView(parent);

        if (item.getIcon() != null) {
            parent.addView(createIcon(item));
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            parent.addView(createTitle(item));
        }

    }

    private ImageView createIcon(SwipeMenuItem item) {
        ImageView iv = new ImageView(getContext());
        iv.setImageDrawable(item.getIcon());
        return iv;
    }

    private TextView createTitle(SwipeMenuItem item) {
        TextView tv = new TextView(getContext());
        tv.setText(item.getTitle());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(item.getTitleSize());
        tv.setTextColor(item.getTitleColor());
        return tv;
    }
    
    @Override
    public void onClick(View v) {
        if (onItemClickListener != null && mLayout.isOpen()) {
            SwipeMenuItem item= (SwipeMenuItem) v.getTag();
            onItemClickListener.onItemClick(item, groupPosition,childPosition);
        }
    }

    public OnSwipeItemClickListener getOnSwipeItemClickListener() {
        return onItemClickListener;
    }

    public void setOnSwipeItemClickListener(OnSwipeItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setLayout(SwipeMenuLayout mLayout) {
        this.mLayout = mLayout;
    }

    public interface OnSwipeItemClickListener {
        void onItemClick(SwipeMenuItem menuItem, int groupPosition,int childPosition);
    }
}
