package com.zhan.ironfuture.ui.fragment.ordersCenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.QueryOrderRequestBean;
import com.zhan.ironfuture.ui.fragment.base.ABaseOrderFragment;

/**
 * Created by WuYue on 2016/4/25.
 * 订单中心
 */
public class OrdersCenterFragment extends ABaseOrderFragment {

    //Views
    private TextView mViewLogistics;
    private TextView mViewGoods;

    //Flag
    private OrderType mOrderType=OrderType.LOGISTICS;

    //Types
    private enum OrderType{LOGISTICS,GOODS}

    @Override
    public QueryOrderRequestBean getRequestBean() {
        QueryOrderRequestBean requestBean=new QueryOrderRequestBean();
        QueryOrderRequestBean.DataEntity data=new QueryOrderRequestBean.DataEntity();
        data.setOrderCategory(mOrderType==OrderType.LOGISTICS?2:1);//1购销 2物流
        data.setQueryDeep(1);
        data.setCompanyId(UserInfo.getCurrentUser().getComId());
        requestBean.setData(data);
        return requestBean;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        mOrderType=OrderType.LOGISTICS;
        if(getActivity() instanceof ActionBarActivity) {
            ActionBarActivity actionBarActivity = (ActionBarActivity) getActivity();
            actionBarActivity.setShowActionBarCustomerContent(true);
            FrameLayout actionbarContent=actionBarActivity.getActionBarCustomerContent();

            actionbarContent.removeAllViews();
            View headerLayout = inflater.inflate(R.layout.header_orders_center, null);
            mViewLogistics=(TextView)headerLayout.findViewById(R.id.logistics);
            mViewGoods=(TextView)headerLayout.findViewById(R.id.goods);
            mViewLogistics.setOnClickListener(mOnTabClick);
            mViewGoods.setOnClickListener(mOnTabClick);
            actionbarContent.addView(headerLayout);

            mViewLogistics.setBackgroundResource(R.drawable.bg_color3_underline);
            mViewLogistics.setTextColor(0xff17112B);
            mViewLogistics.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
            mViewGoods.setBackgroundResource(R.drawable.default_bg);
            mViewGoods.setTextColor(0xff999999);
            mViewGoods.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
        }
    }

    private View.OnClickListener mOnTabClick=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.logistics:
                    mViewLogistics.setBackgroundResource(R.drawable.bg_color3_underline);
                    mViewLogistics.setTextColor(0xff17112B);
                    mViewLogistics.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mViewGoods.setBackgroundResource(R.drawable.default_bg);
                    mViewGoods.setTextColor(0xff999999);
                    mViewGoods.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);

                    mOrderType=OrderType.LOGISTICS;

                    setRefreshing();
                    break;
                case R.id.goods:
                    mViewLogistics.setBackgroundResource(R.drawable.default_bg);
                    mViewLogistics.setTextColor(0xff999999);
                    mViewLogistics.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mViewGoods.setBackgroundResource(R.drawable.bg_color3_underline);
                    mViewGoods.setTextColor(0xff17112B);
                    mViewGoods.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16),0);

                    mOrderType=OrderType.GOODS;

                    setRefreshing();
                    break;
            }
        }
    };

}
