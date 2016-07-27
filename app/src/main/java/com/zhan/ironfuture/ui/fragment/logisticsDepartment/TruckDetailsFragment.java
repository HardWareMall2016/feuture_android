package com.zhan.ironfuture.ui.fragment.logisticsDepartment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.TruckDetailsContent;
import com.zhan.ironfuture.beans.TruckDetailsListRequestBean;
import com.zhan.ironfuture.beans.TruckDetailsListResponseBean;
import com.zhan.ironfuture.event.PositionChangedEvent;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.EventUtil;
import com.zhan.ironfuture.utils.Tools;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 货车详情
 */
public class TruckDetailsFragment extends ABaseFragment implements ExpandableListView.OnChildClickListener {

    @ViewInject(id = R.id.pull_refresh_list)
    private PullToRefreshExpandableListView mPullToRefreshExpandableListView;

    private ExpandableListView mExpandableListView;

    //Tools
    private Handler mHandler = new Handler();
    private ExpandableAdapter mAdapter;
    private LayoutInflater mInflater;

    //Data
    private List<GroupDataInfo> mDataList = new LinkedList<>();


    @Override
    protected int inflateContentView() {
        return R.layout.frag_truck_details;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        mInflater = inflater;
        getActivity().setTitle("货车详情");
        mExpandableListView = mPullToRefreshExpandableListView.getRefreshableView();
        mPullToRefreshExpandableListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mPullToRefreshExpandableListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                requestData();
            }
        });

        mAdapter = new ExpandableAdapter();
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setOnChildClickListener(this);
        //不能点击收缩
        /*mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventUtil.register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        EventUtil.unregister(this);
        mHandler.removeCallbacks(mRefreshCompleteRunnable);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void positionChangedEventBus(PositionChangedEvent event){
        requestData();
    }

    @Override
    public void requestData() {
        TruckDetailsListRequestBean requestBean = new TruckDetailsListRequestBean();
        TruckDetailsListRequestBean.DataEntity dataEntity = new TruckDetailsListRequestBean.DataEntity();
        dataEntity.setComId(UserInfo.getCurrentUser().getComId() + "");
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.GETCARSINFOLIST, requestBean, new BaseHttpRequestTask<TruckDetailsListResponseBean>() {

            @Override
            public TruckDetailsListResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, TruckDetailsListResponseBean.class);
            }
            @Override
            public String verifyResponseResult(TruckDetailsListResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(TruckDetailsListResponseBean bean) {
                super.onSuccess(bean);
                mDataList.clear();
                for (TruckDetailsListResponseBean.DataEntity.CarsInfoListEntity carsInfoListEntity :bean.getData().getCarsInfoList()) {
                    GroupDataInfo departmentInfo = new GroupDataInfo();
                    departmentInfo.setRodeLineId(carsInfoListEntity.getRodeLineId());
                    for (TruckDetailsListResponseBean.DataEntity.CarsInfoListEntity.CbListEntity cbListEntity :carsInfoListEntity.getCbList()) {
                        ChildDataInfo staffInfo = new ChildDataInfo();
                        staffInfo.setCarId(cbListEntity.getCarId());
                        staffInfo.setCarName(cbListEntity.getCarName());
                        staffInfo.setComName(cbListEntity.getComName());
                        staffInfo.setPeopleSum(cbListEntity.getPeopleSum());
                        staffInfo.setFuelSum(cbListEntity.getFuelSum());
                        staffInfo.setAddFuel(cbListEntity.getAddFuel());
                        staffInfo.setComId(cbListEntity.getComId());
                        staffInfo.setAlreadyMax(cbListEntity.getAlreadyMax());
                        departmentInfo.childDataInfoList.add(staffInfo);
                    }
                    mDataList.add(departmentInfo);
                }
                mAdapter.notifyDataSetChanged();
                //默认展开
                        /*for (int i=0;i<mDataList.size();i++){
                            mExpandableListView.expandGroup(i);
                        }*/
            }
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                super.onRequestFinished(resultCode, result);
                onRefreshViewComplete();
            }
        });
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        TruckDetailsContent content = new TruckDetailsContent();
        content.setRodeLineId(mDataList.get(groupPosition).getRodeLineId());
        content.setCarId(mDataList.get(groupPosition).childDataInfoList.get(childPosition).getCarId());
        content.setComId(mDataList.get(groupPosition).childDataInfoList.get(childPosition).getComId());
        StevedoringFragment.launch(getActivity(), content);
        return true;
    }

    public class ExpandableAdapter extends BaseExpandableListAdapter{
        private class GroupHolder {
            TextView routeNO;
        }

        private class ChildHolder {
            TextView mTruckNum ;
            TextView mWorkNum ;
            TextView mTruchPosition ;
            TextView mFuelPercentage ;
            TextView mLoadingAmount ;
        }

        @Override
        public int getGroupCount() {
            return mDataList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mDataList.get(groupPosition).childDataInfoList.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mDataList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mDataList.get(groupPosition).childDataInfoList.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_truck_details_group, null);
                holder = new GroupHolder();
                holder.routeNO = (TextView) convertView.findViewById(R.id.route_num);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            holder.routeNO.setText("#"+mDataList.get(groupPosition).getRodeLineId()+"");

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_truck_details_child, null);
                holder = new ChildHolder();
                holder.mTruckNum = (TextView) convertView.findViewById(R.id.truck_name);
                holder.mWorkNum = (TextView) convertView.findViewById(R.id.worker_num);
                holder.mTruchPosition = (TextView) convertView.findViewById(R.id.scheduled_position);
                holder.mFuelPercentage = (TextView) convertView.findViewById(R.id.fuel_percentage);
                holder.mLoadingAmount = (TextView) convertView.findViewById(R.id.loading_amount);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }
            holder.mTruckNum.setText(mDataList.get(groupPosition).childDataInfoList.get(childPosition).getCarId()+"号车");
            holder.mWorkNum.setText(mDataList.get(groupPosition).childDataInfoList.get(childPosition).getPeopleSum()+"人");
            holder.mTruchPosition.setText(mDataList.get(groupPosition).childDataInfoList.get(childPosition).getComName()+"");
            int mFuelSums = mDataList.get(groupPosition).childDataInfoList.get(childPosition).getFuelSum() ;
            holder.mLoadingAmount.setText(mDataList.get(groupPosition).childDataInfoList.get(childPosition).alreadyMax);
            if(mFuelSums == 0){
                holder.mFuelPercentage.setText("0");
            }else{
                holder.mFuelPercentage.setText(mFuelSums+"%");
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    private void onRefreshViewComplete() {
        mHandler.removeCallbacks(mRefreshCompleteRunnable);
        mHandler.postDelayed(mRefreshCompleteRunnable, 50);
    }

    private Runnable mRefreshCompleteRunnable = new Runnable() {
        @Override
        public void run() {
            mPullToRefreshExpandableListView.onRefreshComplete();
        }
    };

    private class GroupDataInfo {
        private int rodeLineId;
        List<ChildDataInfo> childDataInfoList = new LinkedList<>();

        public int getRodeLineId() {
            return rodeLineId;
        }

        public void setRodeLineId(int rodeLineId) {
            this.rodeLineId = rodeLineId;
        }
    }

    private class ChildDataInfo {
        private int carId;
        private String carName;
        private String comName;
        private int peopleSum;
        private int fuelSum;
        private int addFuel;
        private int comId ;
        private String alreadyMax;

        public int getComId() {
            return comId;
        }

        public void setComId(int comId) {
            this.comId = comId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

        public void setComName(String comName) {
            this.comName = comName;
        }

        public void setPeopleSum(int peopleSum) {
            this.peopleSum = peopleSum;
        }

        public void setFuelSum(int fuelSum) {
            this.fuelSum = fuelSum;
        }

        public void setAddFuel(int addFuel) {
            this.addFuel = addFuel;
        }

        public int getCarId() {
            return carId;
        }

        public String getCarName() {
            return carName;
        }

        public String getComName() {
            return comName;
        }

        public int getPeopleSum() {
            return peopleSum;
        }

        public int getFuelSum() {
            return fuelSum;
        }

        public int getAddFuel() {
            return addFuel;
        }

        public String getAlreadyMax() {
            return alreadyMax;
        }

        public void setAlreadyMax(String alreadyMax) {
            this.alreadyMax = alreadyMax;
        }
    }
}