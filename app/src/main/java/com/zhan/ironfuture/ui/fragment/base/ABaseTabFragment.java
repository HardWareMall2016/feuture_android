package com.zhan.ironfuture.ui.fragment.base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.APullToRefreshListFragment;
import com.zhan.ironfuture.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/22.
 */
public abstract class ABaseTabFragment<T> extends APullToRefreshListFragment<T> {
    @ViewInject(id = R.id.main_tab)
    LinearLayout mMainTabContent;

    @ViewInject(id = R.id.sub_tab)
    LinearLayout mSubTabContent;

    private LayoutInflater mInflater;

    private List<TabInfo> mTabList;

    //-1表示没有选择，默认第一个
    private int mCurMainTabCode = -1;
    //-1表示没有选择，默认第一个
    private int mCurSubTabCode = -1;

    private boolean mIsFirst = true;

    public static class TabInfo {
        public static int selColor = 0xffFFC501;
        public static int unSelColor = 0xff999999;
        public static int selBackgroundRes = R.drawable.bg_color1_underline;
        public static int unSelBackgroundRes = R.drawable.default_bg;

        public int tabCode;
        public String name;
        public boolean isMainTab;
        TextView tabView;
        //该Tab对应的子Tab
        public List<TabInfo> childTabList = new ArrayList<>();
    }

    @Override
    protected int inflateContentView() {
        return R.layout.comm_lay_tabs;
    }

    /***
     * 例子如下
     * List<TabInfo> tabs=new ArrayList<>();
     * TabInfo tab1=new TabInfo();
     * tab1.tabCode=1;
     * tab1.name="Test1";
     * <p/>
     * TabInfo tab2=new TabInfo();
     * tab2.tabCode=2;
     * tab2.name="Test2";
     * <p/>
     * TabInfo tab3=new TabInfo();
     * tab3.tabCode=3;
     * tab3.name="Test3";
     * <p/>
     * tabs.add(tab1);
     * tabs.add(tab2);
     * tabs.add(tab3);
     * <p/>
     * TabInfo tab4=new TabInfo();
     * tab4.tabCode=4;
     * tab4.name="Test4";
     * //子TAB
     * tab1.childTabList.add(tab4);
     * <p/>
     * TabInfo tab5=new TabInfo();
     * tab5.tabCode=5;
     * tab5.name="Test5";
     * //子TAB
     * tab1.childTabList.add(tab5);
     * <p/>
     * TabInfo tab6=new TabInfo();
     * tab6.tabCode=6;
     * tab6.name="Test1";
     * //子TAB
     * tab2.childTabList.add(tab6);
     * <p/>
     * TabInfo tab7=new TabInfo();
     * tab7.tabCode=7;
     * tab7.name="Test7";
     * //子TAB
     * tab2.childTabList.add(tab7);
     * <p/>
     * return tabs;
     *
     * @return
     */
    protected abstract List<TabInfo> generateTabList();

    /***
     * 默认第一个
     *
     * @return
     */
    protected int defaultMainTabCode() {
        return -1;
    }

    /***
     * 默认第一个
     *
     * @return
     */
    protected int defaultSubTabCode() {
        return -1;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);

        mIsFirst = true;

        mInflater = inflater;

        mTabList = generateTabList();

        initDefTabCode();

        initMainTab();

        refreshTabViews();
    }

    private void initDefTabCode() {
        mCurMainTabCode = defaultMainTabCode();
        mCurSubTabCode = defaultSubTabCode();
    }

    private void initMainTab() {
        mMainTabContent.removeAllViews();
        for (TabInfo mainTab : mTabList) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            params.gravity = Gravity.CENTER;

            mainTab.tabView = (TextView) mInflater.inflate(R.layout.comm_tab_item, null);
            mainTab.tabView.setText(mainTab.name);
            mainTab.tabView.setTag(mainTab);
            mainTab.tabView.setLayoutParams(params);
            mainTab.isMainTab = true;
            mainTab.tabView.setOnClickListener(mOnTabClickListener);

            mMainTabContent.addView(mainTab.tabView);
        }
    }

    private void refreshTabViews() {
        if (mTabList == null || mTabList.size() == 0) {
            return;
        }
        //设置主TAB,默认第一个
        TabInfo selMainTab = mTabList.get(0);
        for (int i = 0; i < mTabList.size(); i++) {
            TabInfo mainTab = mTabList.get(i);
            if (mainTab.tabCode == mCurMainTabCode) {
                selMainTab = mainTab;
            }
            mainTab.tabView.setBackgroundResource(TabInfo.unSelBackgroundRes);
            mainTab.tabView.setTextColor(TabInfo.unSelColor);
        }
        selMainTab.tabView.setBackgroundResource(TabInfo.selBackgroundRes);
        selMainTab.tabView.setTextColor(TabInfo.selColor);
        //不管是不是最后更新到这个
        mCurMainTabCode = selMainTab.tabCode;

        //设置次TAB,默认第一个
        if (mSubTabContent!=null&&selMainTab.childTabList.size() > 0) {
            mSubTabContent.removeAllViews();
            mSubTabContent.setVisibility(View.VISIBLE);
            TabInfo selSubTab = selMainTab.childTabList.get(0);
            for (int subIndex = 0; subIndex < selMainTab.childTabList.size(); subIndex++) {
                TabInfo subTab = selMainTab.childTabList.get(subIndex);
                if (subTab.tabCode == mCurSubTabCode) {
                    selSubTab = subTab;
                }
                subTab.isMainTab = false;

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                layoutParams.gravity = Gravity.CENTER;
                subTab.tabView = (TextView) mInflater.inflate(R.layout.comm_tab_item, null);
                subTab.tabView.setText(subTab.name);
                subTab.tabView.setTag(subTab);
                subTab.tabView.setBackgroundResource(TabInfo.unSelBackgroundRes);
                subTab.tabView.setTextColor(TabInfo.unSelColor);
                subTab.tabView.setOnClickListener(mOnTabClickListener);
                subTab.tabView.setLayoutParams(layoutParams);

                mSubTabContent.addView(subTab.tabView);
            }
            selSubTab.tabView.setBackgroundResource(TabInfo.selBackgroundRes);
            selSubTab.tabView.setTextColor(TabInfo.selColor);
            mCurSubTabCode = selSubTab.tabCode;
        } else if(mSubTabContent!=null){
            mSubTabContent.setVisibility(View.GONE);
        }

        //第一次会走queryData,所以不需要调用onTabChanged,去再走一次queryData
        if (mIsFirst) {
            mIsFirst = false;
        } else {
            onTabChanged(mCurMainTabCode, mCurSubTabCode);
        }
    }

    private View.OnClickListener mOnTabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TabInfo tabInfo = (TabInfo) v.getTag();
            if (tabInfo.isMainTab) {
                mCurMainTabCode = tabInfo.tabCode;
            } else {
                mCurSubTabCode = tabInfo.tabCode;
            }
            refreshTabViews();
        }
    };

    protected abstract void onTabChanged(int mainTabCode, int subTabCode);
}
