package com.zhan.ironfuture.ui.fragment.logisticsDepartment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.BaseResponseBean;
import com.zhan.ironfuture.beans.QueryOrderRequestBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.MyListView;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/10.
 */
public class LogisticsOrderDetailFragment extends ABaseFragment {
    private final static String ARG_KEY="order_id";

    //views
    @ViewInject(id = R.id.logistics_order_detail_listview)
    MyListView mProductListView ;
    @ViewInject(id = R.id.logistics_order_detail_logistics)
    MyListView mDetailListView ;

    //data
    private ArrayList<LogisticsOrderDetailInfo> mProductList = new ArrayList<>();
    private ArrayList<LogisticsDetailInfo> mLogisticsList = new ArrayList<>();
    private int mOrderId;

    private ProductDetailAdapter mDetailAdapter ;
    private LogisticsDetailAdapter mLogisticsAdapter ;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_logistics_order_detail;
    }

    public static void launch(FragmentActivity activity,int orderId) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, orderId);
        FragmentContainerActivity.launch(activity, LogisticsOrderDetailFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderId = savedInstanceState == null ? (int) getArguments().getSerializable(ARG_KEY) : (int) savedInstanceState.getSerializable(ARG_KEY);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mOrderId);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle(R.string.logistics_order_detail);
    }

    @Override
    public void requestData() {
        QueryOrderRequestBean requestBean=new QueryOrderRequestBean();
        QueryOrderRequestBean.DataEntity data=new QueryOrderRequestBean.DataEntity();
        data.setOrderId(mOrderId);
        data.setQueryDeep(3);
        requestBean.setData(data);

        startJsonRequest(ApiUrls.ORDER_BS_QUERY_ORDER, requestBean, new BaseHttpRequestTask<BaseResponseBean>() {

            @Override
            public BaseResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, BaseResponseBean.class);
            }

            @Override
            public String verifyResponseResult(BaseResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(BaseResponseBean result) {
                super.onSuccess(result);
                mProductList.clear();
                mLogisticsList.clear();
                for (int i = 0; i < 3; i++) {
                    LogisticsOrderDetailInfo logisticsOrderDetailInfo = new LogisticsOrderDetailInfo();
                    mProductList.add(logisticsOrderDetailInfo);
                }
                mDetailAdapter = new ProductDetailAdapter(mProductList, getActivity());
                mProductListView.setAdapter(mDetailAdapter);

                for (int i = 0; i < 5; i++) {
                    LogisticsDetailInfo logisticsDetailInfo = new LogisticsDetailInfo();
                    mLogisticsList.add(logisticsDetailInfo);
                }
                mLogisticsAdapter = new LogisticsDetailAdapter(mLogisticsList,getActivity());
                mDetailListView.setAdapter(mLogisticsAdapter);
            }
        });
    }

    private class ProductDetailAdapter extends ABaseAdapter<LogisticsOrderDetailInfo> {

        public ProductDetailAdapter(ArrayList<LogisticsOrderDetailInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<LogisticsOrderDetailInfo> newItemView() {
            return new LogisticsOrderDetailItem();
        }
    }

    private class LogisticsOrderDetailItem extends ABaseAdapter.AbstractItemView<LogisticsOrderDetailInfo> {

        @Override
        public int inflateViewId() {
            return R.layout.list_item_logistics_orders_product;
        }

        @Override
        public void bindingData(View convertView, LogisticsOrderDetailInfo data) {

        }
    }

    private class LogisticsDetailAdapter extends ABaseAdapter<LogisticsDetailInfo> {

        public LogisticsDetailAdapter(ArrayList<LogisticsDetailInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<LogisticsDetailInfo> newItemView() {
            return new LogisticsDetailItem();
        }
    }

    private class LogisticsDetailItem extends ABaseAdapter.AbstractItemView<LogisticsDetailInfo> {

        @Override
        public int inflateViewId() {
            return R.layout.list_item_logistics_detail;
        }

        @Override
        public void bindingData(View convertView, LogisticsDetailInfo data) {

        }
    }

    private class LogisticsOrderDetailInfo {

    }

    private class LogisticsDetailInfo{

    }


}
