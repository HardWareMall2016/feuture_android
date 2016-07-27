package com.zhan.framework.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.widget.AbsListView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhan.framework.R;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.utils.ToastUtils;

import java.io.Serializable;

/**
 * Created by Administrator
 */
public abstract class APullToRefreshListFragment<T> extends ARefreshFragment<T, ListView> {
    static String TAG = "APullToRefreshListFragment";

    @ViewInject(idStr = "pull_refresh_list")
    protected PullToRefreshListView mPullToRefreshListView;

    private Handler mHandler = new Handler();

    private PullToRefreshBase.Mode mInitPullMode =PullToRefreshBase.Mode.BOTH;

    @Override
    final protected void setInitRefreshView(AbsListView refreshView, Bundle savedInstanceSate) {
        super.setInitRefreshView(refreshView, savedInstanceSate);
        mPullToRefreshListView.setMode(mInitPullMode);
        initPullDownLable();
        initPullUpLable(true);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                initPullUpLable(true);
                APullToRefreshListFragment.this.onPullDownToRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载更多
                if (!getRefreshConfig().canLoadMore) {
                    onRefreshViewComplete();
                    return;
                }
                APullToRefreshListFragment.this.onPullUpToRefresh();
            }
        });

        //子类可在该函数中修改Mode
        setInitPullToRefresh(getListView(), mPullToRefreshListView, savedInstanceSate);

        mInitPullMode =mPullToRefreshListView.getMode();
    }

    protected void setInitPullToRefresh(ListView listView, PullToRefreshListView pullToRefreshListView, Bundle savedInstanceState) {

    }

    @Override
    public AbsListView getRefreshView() {
        return mPullToRefreshListView.getRefreshableView();
    }

    @Override
    protected int inflateContentView() {
        return R.layout.comm_lay_pull_to_freshlist;
    }

    private ListView getListView() {
        return (ListView) getRefreshView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(mRefreshCompleteRunnable);
    }

    @Override
    public boolean setRefreshing() {
        if(isContentEmpty()){
            startRequestData(RefreshMode.refresh);
        }else{
            mPullToRefreshListView.setRefreshing(true);
        }
        return false;
    }

    @Override
    public boolean isRefreshing() {
        if (mPullToRefreshListView.isRefreshing())
            return true;

        return super.isRefreshing();
    }

    @Override
    public void onRefreshViewComplete() {
        mHandler.removeCallbacks(mRefreshCompleteRunnable);
        mHandler.postDelayed(mRefreshCompleteRunnable, 50);
    }

    @Override
    protected void taskStateChanged(ABaseTaskState state, Serializable tag) {
        super.taskStateChanged(state, tag);
        if (state == ABaseTaskState.falid) {
            //对不为空的情况当独处理下,主要是解决TAB切换时出现问题
            if (!isContentEmpty()) {
                if (tag != null){
                    ToastUtils.toast(tag.toString());
                }
            }
        }

        if (state == ABaseTaskState.finished) {
            onRefreshViewComplete();
        } else if (state == ABaseTaskState.prepare) {

        }
    }

    @Override
    public void onChangedByConfig(RefreshConfig config) {
        initPullUpLable(config.canLoadMore);

        if(mInitPullMode !=PullToRefreshBase.Mode.BOTH){
            return;
        }
        //对于加载更多，如果总数小于页数就禁止加载更多
        if(config.minResultSize>getAdapterItems().size()){
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }else{
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        }
    }


    private Runnable mRefreshCompleteRunnable = new Runnable() {
        @Override
        public void run() {
            mPullToRefreshListView.onRefreshComplete();
        }
    };

    private void initPullDownLable() {
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        mPullToRefreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel("释放开始刷新");
    }

    private void initPullUpLable(boolean canLoadMore) {
        if (canLoadMore) {
            mPullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载");
            mPullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载更多");
            mPullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("释放开始加载");
        } else {
            mPullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("没有更多数据了");
            mPullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("没有更多数据了");
            mPullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("没有更多数据了");
        }
    }


    protected String loadMoreBtnLabel() {
        return getString(R.string.comm_request_more);// 加载更多
    }

    protected String loadingLabel() {
        return getString(R.string.comm_request_loading);// 加载中
    }

    protected String loadDisabledLabel() {
        return getString(R.string.comm_request_disable);// 不能加载更多了
    }

}
