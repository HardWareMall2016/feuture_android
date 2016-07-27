package com.zhan.ironfuture.ui.fragment.constructionDepartment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.BaseResponseBean;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.DeleteRodeLineRequestBean;
import com.zhan.ironfuture.beans.RotateLineContent;
import com.zhan.ironfuture.beans.RouteManagementRequestBean;
import com.zhan.ironfuture.beans.RouteManagementResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.APullToRefreshSwipeMenuListFragment;
import com.zhan.ironfuture.ui.widget.PullToRefreshSwipeMenuListView;
import com.zhan.ironfuture.ui.widget.swipemenulistview.SwipeMenu;
import com.zhan.ironfuture.ui.widget.swipemenulistview.SwipeMenuItem;
import com.zhan.ironfuture.ui.widget.swipemenulistview.SwipeMenuLayout;
import com.zhan.ironfuture.ui.widget.swipemenulistview.SwipeMenuView;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 路线管理
 */
public class RouteManagementFragment extends APullToRefreshSwipeMenuListFragment<RouteManagementFragment.RouteManageInfo> implements SwipeMenuView.OnSwipeItemClickListener{

    private ArrayList<RouteManageInfo> beanList=new ArrayList<>();
    @ViewInject(id = R.id.pull_refresh_list)
    private PullToRefreshSwipeMenuListView mPullToRefreshSwipeMenuListView;

    private LayoutInflater mInflater;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_route_manage;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        mInflater=inflater;
        getActivity().setTitle("路线管理");
    }

    @Override
    protected ABaseAdapter.AbstractItemView<RouteManageInfo> newItemView() {
        return new RouteManagementItemView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Tools.isFastClick()) {
            return ;
        }
        RotateLineContent content = new RotateLineContent();
        content.setRodeLineId(getAdapterItems().get(position - 1).getRodeLineId());
        RouteManagementDetailFragment.lunch(getActivity(), content);
    }

    @Override
    protected void requestData(RefreshMode mode) {
        beanList.clear();
        RouteManagementRequestBean request = new RouteManagementRequestBean();
        RouteManagementRequestBean.DataEntity data = new RouteManagementRequestBean.DataEntity();
        request.setData(data);

        startJsonRequest(ApiUrls.RODELINELIST, request, new PagingTask<RouteManagementResponseBean>(mode) {
            @Override
            public RouteManagementResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, RouteManagementResponseBean.class);
            }

            @Override
            public String verifyResponseResult(RouteManagementResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected List<RouteManageInfo> parseResult(RouteManagementResponseBean baseResponseBean) {
                if (baseResponseBean != null && baseResponseBean.getData() != null) {
                    for (RouteManagementResponseBean.DataEntity.RlbbListEntity item : baseResponseBean.getData().getRlbbList()) {
                        RouteManageInfo bean = new RouteManageInfo();
                        bean.setRodeLineId(item.getRodeLineId());
                        bean.setRodeLineName(item.getRodeLineName());
                        bean.setRodeLineSum(item.getRodeLineSum());
                        bean.setRodeLineBeginId(item.getRodeLineBeginId());
                        bean.setRodeLineBeginName(item.getRodeLineBeginName());
                        bean.setRodeLineEndId(item.getRodeLineEndId());
                        bean.setRodeLineEndName(item.getRodeLineEndName());
                        beanList.add(bean);
                    }
                }
                return beanList;
            }
        });
    }

    @Override
    public void onItemClick(SwipeMenuItem menuItem, int groupPosition, int childPosition) {
        //ToastUtils.toast(getAdapterItems().get(childPosition).getRodeLineId()+"");
        if(isRequestProcessing(ApiUrls.DEL_RODE_LINE)){
            return;
        }
        DeleteRodeLineRequestBean requestBean=new DeleteRodeLineRequestBean();
        DeleteRodeLineRequestBean.DataEntity data=new DeleteRodeLineRequestBean.DataEntity();
        data.setRodeLineId(getAdapterItems().get(childPosition).getRodeLineId());
        data.setComId(UserInfo.getCurrentUser().getComId());
        data.setDeptid(UserInfo.getCurrentUser().getDeptid());
        requestBean.setData(data);
        startJsonRequest(ApiUrls.DEL_RODE_LINE, requestBean, new HttpRequestHandler(RouteManagementFragment.this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        BaseResponseBean responseBean = Tools.parseJsonTostError(result, BaseResponseBean.class);
                        if (responseBean != null) {
                            ToastUtils.toast("删除成功!");
                            //刷新
                            requestData();
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        });
    }

    private class RouteManagementItemView extends ABaseAdapter.AbstractItemView<RouteManageInfo> {

        @ViewInject(id = R.id.route_manage_name)
        TextView mName ;
        @ViewInject(id = R.id.route_manage_begin_end)
        TextView mBeginEnd ;
        @ViewInject(id = R.id.route_manage_num)
        TextView mNumber ;
        @ViewInject(id = R.id.route_name)
        TextView mRouteName ;


        public void createMenu(SwipeMenu menu) {
            SwipeMenuItem fireItem = new SwipeMenuItem(getActivity());
            fireItem.setBackground(new ColorDrawable(0xffFE501B));
            fireItem.setWidth(PixelUtils.dp2px(90));
            fireItem.setTitle("删除");
            fireItem.setTitleSize(18);
            fireItem.setTitleColor(Color.WHITE);
            menu.addMenuItem(fireItem);
        }


        @Override
        public View createConvertView(int position) {
            SwipeMenu menu = new SwipeMenu(getActivity());
            createMenu(menu);
            SwipeMenuView menuView = new SwipeMenuView(menu);
            menuView.setOnSwipeItemClickListener(RouteManagementFragment.this);

            View itemView = mInflater.inflate(R.layout.list_item_route_manage, null);

            SwipeMenuLayout swipeMenuLayout=new SwipeMenuLayout(itemView, menuView);

            return swipeMenuLayout;
        };

        @Override
        public int inflateViewId() {
            return R.layout.list_item_route_manage;
        }

        @Override
        public void bindingView(View convertView) {
            super.bindingView(convertView);
        }

        @Override
        public void bindingData(View convertView, RouteManageInfo data) {
            SwipeMenuLayout swipeMenuLayout= (SwipeMenuLayout) convertView;
            swipeMenuLayout.setPosition(0, getPosition());
            swipeMenuLayout.closeMenu();

            mName.setText("#"+data.getRodeLineId()+"");
            mRouteName.setText(data.getRodeLineName());
            mBeginEnd.setText("(" + data.getRodeLineBeginName() + "-" + data.getRodeLineEndName() + ")");
            mNumber.setText(data.getRodeLineSum() + "站");
        }
    }

    public class RouteManageInfo{
        private int rodeLineId;
        private String rodeLineName;
        private int rodeLineSum;
        private int rodeLineBeginId;
        private String rodeLineBeginName;
        private int rodeLineEndId;
        private String rodeLineEndName;

        public void setRodeLineId(int rodeLineId) {
            this.rodeLineId = rodeLineId;
        }

        public void setRodeLineName(String rodeLineName) {
            this.rodeLineName = rodeLineName;
        }

        public void setRodeLineSum(int rodeLineSum) {
            this.rodeLineSum = rodeLineSum;
        }

        public void setRodeLineBeginId(int rodeLineBeginId) {
            this.rodeLineBeginId = rodeLineBeginId;
        }

        public void setRodeLineBeginName(String rodeLineBeginName) {
            this.rodeLineBeginName = rodeLineBeginName;
        }

        public void setRodeLineEndId(int rodeLineEndId) {
            this.rodeLineEndId = rodeLineEndId;
        }

        public void setRodeLineEndName(String rodeLineEndName) {
            this.rodeLineEndName = rodeLineEndName;
        }

        public int getRodeLineId() {
            return rodeLineId;
        }

        public String getRodeLineName() {
            return rodeLineName;
        }

        public int getRodeLineSum() {
            return rodeLineSum;
        }

        public int getRodeLineBeginId() {
            return rodeLineBeginId;
        }

        public String getRodeLineBeginName() {
            return rodeLineBeginName;
        }

        public int getRodeLineEndId() {
            return rodeLineEndId;
        }

        public String getRodeLineEndName() {
            return rodeLineEndName;
        }
    }
}