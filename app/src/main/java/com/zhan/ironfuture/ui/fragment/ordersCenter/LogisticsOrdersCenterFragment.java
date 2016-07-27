package com.zhan.ironfuture.ui.fragment.ordersCenter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.QueryOrderRequestBean;
import com.zhan.ironfuture.beans.QueryOrderResponseBean;
import com.zhan.ironfuture.ui.fragment.base.ABaseOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/29.
 */
public class LogisticsOrdersCenterFragment extends ABaseOrderFragment {

    //Views
    @ViewInject(id = R.id.amount_content, click = "OnClick")
    View mViewFilterAmountContent;

    @ViewInject(id = R.id.amount)
    TextView mViewFilterAmount;

    @ViewInject(id = R.id.can_transport_content, click = "OnClick")
    View mViewFilterCanTransportContent;

    @ViewInject(id = R.id.can_transport)
    TextView mViewFilterCanTransport;

    @ViewInject(id = R.id.filter_content, click = "OnClick")
    View mViewFilterContent;

    @ViewInject(id = R.id.filter_list)
    ListView mViewFilterList;

    //Data
    private ArrayList<FilerData> mFilerData =new ArrayList<>();
    private ArrayList<FilerData> mFilerDataAmountList =new ArrayList<>();
    private ArrayList<FilerData> mFilerDataCanTransportList =new ArrayList<>();

    //Flag
    private static final int FILTER_NONE=0;
    private static final int FILTER_AMOUNT =1;
    private static final int FILTER_CAN_TRANSPORT =2;
    private int mFilterType=FILTER_NONE;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_logistics_orders_center;
    }

    @Override
    public QueryOrderRequestBean getRequestBean() {
        QueryOrderRequestBean requestBean=new QueryOrderRequestBean();
        QueryOrderRequestBean.DataEntity data=new QueryOrderRequestBean.DataEntity();
        data.setOrderCategory(2);//1购销 2物流
        data.setQueryDeep(1);
        //data.setCompanyId(UserInfo.getCurrentUser().getComId());
        requestBean.setData(data);

        if(mFilterType==FILTER_AMOUNT){
            for(FilerData filerData:mFilerDataAmountList){
                if(filerData.isSelected){
                    data.setSearchKey(filerData.filerStr);
                    data.setSearchType("amountList");
                    break;
                }
            }
        }else if(mFilterType==FILTER_CAN_TRANSPORT){
            for(FilerData filerData:mFilerDataCanTransportList){
                if(filerData.isSelected){
                    data.setSearchKey(filerData.filerStr);
                    data.setSearchType("canTransportList");
                    break;
                }
            }
        }
        return requestBean;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("订单中心");
        mViewFilterList.setOnItemClickListener(mOnFilterItemClickListener);
        mFilterType=FILTER_NONE;
        refreshFilerStatus();
    }

    void OnClick(View view) {
        switch (view.getId()){
            case R.id.amount_content:
                mFilterType= FILTER_AMOUNT;
                break;
            case R.id.can_transport_content:
                mFilterType= FILTER_CAN_TRANSPORT;
                break;
            case R.id.filter_content:
                mFilterType=FILTER_NONE;
                break;
        }
        refreshFilerStatus();
    }

    private void refreshFilerStatus() {
        switch (mFilterType) {
            case FILTER_NONE:
                mViewFilterAmount.setTextColor(0xff9390A5);
                mViewFilterAmount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterCanTransport.setTextColor(0xff9390A5);
                mViewFilterCanTransport.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                break;
            case FILTER_AMOUNT:
                mFilerData=mFilerDataAmountList;
                mViewFilterAmount.setTextColor(0xffffc500);
                mViewFilterAmount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_small, 0);
                mViewFilterCanTransport.setTextColor(0xff9390A5);
                mViewFilterCanTransport.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterList.setAdapter(new FilterAdapter(mFilerData, getActivity()));
                break;
            case FILTER_CAN_TRANSPORT:
                mFilerData=mFilerDataCanTransportList;
                mViewFilterAmount.setTextColor(0xff9390A5);
                mViewFilterAmount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterCanTransport.setTextColor(0xffffc500);
                mViewFilterCanTransport.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_small, 0);
                mViewFilterList.setAdapter(new FilterAdapter(mFilerData, getActivity()));
                break;
        }
        mViewFilterContent.setVisibility(mFilterType == FILTER_NONE ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onSearchFilterResponded(List<QueryOrderResponseBean.DataEntity.AmountListEntity> amountList, List<QueryOrderResponseBean.DataEntity.CanTransportListEntity> canTransportList) {
        if(mFilerDataAmountList.size()>0){
            return;
        }
        mFilerDataAmountList.clear();
        if(amountList!=null){
            for(QueryOrderResponseBean.DataEntity.AmountListEntity amountInfo:amountList){
                FilerData data=new FilerData();
                data.code=amountInfo.getId();
                data.filerStr=amountInfo.getName();
                mFilerDataAmountList.add(data);
            }
        }
        mFilerDataCanTransportList.clear();
        if(amountList!=null){
            for(QueryOrderResponseBean.DataEntity.CanTransportListEntity canTransport:canTransportList){
                FilerData data=new FilerData();
                data.code=canTransport.getId();
                data.filerStr=canTransport.getName();
                mFilerDataCanTransportList.add(data);
            }
        }
    }

    private class FilerData{
        int code;
        boolean isSelected;
        String filerStr;
    }

    private class FilterAdapter extends ABaseAdapter<FilerData>{
        public FilterAdapter(ArrayList<FilerData> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<FilerData> newItemView() {
            return new FilterItemView();
        }
    }

    private class FilterItemView extends ABaseAdapter.AbstractItemView<FilerData>{
        @ViewInject(id = R.id.radio)
        RadioButton mViewRadioButton;

        @ViewInject(id = R.id.filter_name)
        TextView mViewFilterName;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_logistics_orders_center_filter;
        }

        @Override
        public void bindingData(View convertView, FilerData data) {
            mViewFilterName.setText(data.filerStr);
            if(data.isSelected){
                mViewRadioButton.setChecked(true);
            }else{
                mViewRadioButton.setChecked(false);
            }
        }
    };

    private AdapterView.OnItemClickListener mOnFilterItemClickListener=new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            for(FilerData filerData:mFilerDataAmountList){
                filerData.isSelected=false;
            }
            for(FilerData filerData:mFilerDataCanTransportList){
                filerData.isSelected=false;
            }
            for(int i=0;i<mFilerData.size();i++){
                mFilerData.get(i).isSelected=i==position;
            }
            requestData();
            mFilterType=FILTER_NONE;
            refreshFilerStatus();
        }
    };
}
