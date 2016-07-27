package com.zhan.ironfuture.ui.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.zhan.framework.ui.fragment.ABaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 */
public class Page {
    String TAG;
    ABaseFragment pageFragment;
    int selectedDrawableId;
    int normalDrawableId;
    TextView bottomTitle;
    //ImageView bottomImageView ;
    List<MorePageInfo> morePages=new ArrayList<>();
}
