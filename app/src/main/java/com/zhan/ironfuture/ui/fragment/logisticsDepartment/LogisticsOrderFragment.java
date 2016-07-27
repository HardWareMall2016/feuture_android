package com.zhan.ironfuture.ui.fragment.logisticsDepartment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.BaseResponseBean;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.LoginRequestBean;
import com.zhan.ironfuture.beans.QueryOrderRequestBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.fragment.base.ABaseOrderTabFragment;
import com.zhan.ironfuture.ui.fragment.base.ABaseTabFragment;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/21.
 * 物流订单
 */
public class LogisticsOrderFragment extends ABaseOrderTabFragment {


    private static final int TAB_CHANGE = 0;
    private static final int TAB_PRODUCT = 1;

    private int mCurTab = TAB_CHANGE;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_logistics_order;
    }

    @Override
    public QueryOrderRequestBean getRequestBean() {
        QueryOrderRequestBean requestBean=new QueryOrderRequestBean();
        QueryOrderRequestBean.DataEntity data=new QueryOrderRequestBean.DataEntity();
        data.setOrderCategory(2);//1购销 2物流
        data.setQueryDeep(1);
        data.setState(mCurTab==TAB_CHANGE?2:4);
        data.setCompanyId(UserInfo.getCurrentUser().getComId());
        requestBean.setData(data);
        return requestBean;
    }

    @Override
    protected List<TabInfo> generateTabList() {
        List<TabInfo> tabs = new ArrayList<>();

        TabInfo tabItem = new TabInfo();
        tabItem.tabCode = TAB_CHANGE;
        tabItem.name = "挂出";
        tabs.add(tabItem);

        tabItem = new TabInfo();
        tabItem.tabCode = TAB_PRODUCT;
        tabItem.name = "履行中";
        tabs.add(tabItem);
        return tabs;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater,savedInstanceSate);
        getActivity().setTitle(R.string.logistics_order);
        mCurTab = TAB_CHANGE;
    }

    @Override
    protected void onTabChanged(int mainTabCode, int subTabCode) {
        mCurTab = mainTabCode;
        setRefreshing();
    }

}
