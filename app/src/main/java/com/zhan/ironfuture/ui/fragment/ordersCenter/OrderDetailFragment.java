package com.zhan.ironfuture.ui.fragment.ordersCenter;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.BaseResponseBean;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.CheckNewOrderRequestBean;
import com.zhan.ironfuture.beans.OrderInfoBean;
import com.zhan.ironfuture.beans.PreAcceptOrderRequestBean;
import com.zhan.ironfuture.beans.QueryOrderRequestBean;
import com.zhan.ironfuture.beans.QueryOrderResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.fragment.logisticsDepartment.CreateLogisticsOrderFragment;
import com.zhan.ironfuture.ui.widget.MyListView;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者：伍岳 on 2016/5/10 11:43
 * 邮箱：wuyue8512@163.com
 //
 //         .............................................
 //                  美女坐镇                  BUG辟易
 //         .............................................
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 //
 */
public class OrderDetailFragment extends ABaseFragment {
    private final static String ARG_KEY="order_id";
    private final static String ARG_KEY_FROM_WAREHOUSE="from_warehouse";

    //views
    @ViewInject(id = R.id.order_detail_listview)
    MyListView mProductListView ;
    @ViewInject(id = R.id.order_detail_logistics)
    MyListView mDetailListView ;

    @ViewInject(id = R.id.order_number)
    TextView mViewOrderNum ;

    @ViewInject(id = R.id.order_penalty)
    TextView mViewOrderPenalty ;

    @ViewInject(id = R.id.overdue_time)
    TextView mViewOverdueTime ;

    @ViewInject(id = R.id.logistics_order_detail_type)
    TextView mViewOrderType ;

    @ViewInject(id = R.id.order_status)
    TextView mViewOrderStatus ;

    @ViewInject(id = R.id.create_order_company)
    TextView mViewCreateOrderCompany ;

    @ViewInject(id = R.id.assign_company)
    TextView mViewAssignCompany ;

    @ViewInject(id = R.id.order_amount)
    TextView mViewOrderAmount ;

    private TextView mActionBarMenu;

    private Dialog mDialog;

    //data
    private ArrayList<OrderProductInfo> mProductList = new ArrayList<>();
    private ArrayList<LogisticsDetailInfo> mLogisticsList = new ArrayList<>();
    private int mOrderId;
    private OrderInfoBean mOrderInfo;
    private OrderStatus mOrderStatus=OrderStatus.NONE;
    private boolean mComeFromWarehouse=false;

    private ProductDetailAdapter mDetailAdapter ;
    private LogisticsDetailAdapter mLogisticsAdapter ;

    private enum OrderStatus{NONE,CONFIRM,ACCEPT,SEND_LOGISTICS_ORDER}

    @Override
    protected int inflateContentView() {
        return R.layout.frag_order_detail;
    }

    public static void launch(FragmentActivity activity,int orderId) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, orderId);
        args.add(ARG_KEY_FROM_WAREHOUSE, false);
        FragmentContainerActivity.launch(activity, OrderDetailFragment.class, args);
    }

    public static void launchFromWarehouse(FragmentActivity activity,int orderId) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, orderId);
        args.add(ARG_KEY_FROM_WAREHOUSE, true);
        FragmentContainerActivity.launch(activity, OrderDetailFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderId = savedInstanceState == null ? (int) getArguments().getSerializable(ARG_KEY) : (int) savedInstanceState.getSerializable(ARG_KEY);
        mComeFromWarehouse = savedInstanceState == null ? (boolean) getArguments().getSerializable(ARG_KEY_FROM_WAREHOUSE) : (boolean) savedInstanceState.getSerializable(ARG_KEY_FROM_WAREHOUSE);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mOrderId);
        outState.putSerializable(ARG_KEY_FROM_WAREHOUSE, mOrderId);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle(R.string.logistics_order_detail);
    }

    @Override
    public void onPrepareActionbarMenu(TextView menu, Activity activity) {
        mActionBarMenu=menu;
    }

    @Override
    public void onActionBarMenuClick() {
        switch(mOrderStatus){
            case CONFIRM:
                showApplyDialog();
                break;
            case ACCEPT:
                showAcceptOrderDialog();
                break;
            case SEND_LOGISTICS_ORDER:
                CreateLogisticsOrderFragment.launch(getActivity(),mOrderInfo);
                getActivity().finish();
                break;
            default:
        }
    }

    @Override
    public void requestData() {
        QueryOrderRequestBean requestBean=new QueryOrderRequestBean();
        QueryOrderRequestBean.DataEntity data=new QueryOrderRequestBean.DataEntity();
        data.setOrderId(mOrderId);
        data.setQueryDeep(3);
        requestBean.setData(data);

        startJsonRequest(ApiUrls.ORDER_BS_QUERY_ORDER, requestBean, new BaseHttpRequestTask<QueryOrderResponseBean>() {

            @Override
            public QueryOrderResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, QueryOrderResponseBean.class);
            }

            @Override
            public String verifyResponseResult(QueryOrderResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected boolean resultIsEmpty(QueryOrderResponseBean result) {
                if(result==null
                        ||result.getData()==null
                        ||result.getData().getMarketOrderDtoList()==null
                        ||result.getData().getMarketOrderDtoList().size()==0
                        ||result.getData().getMarketOrderDtoList().get(0)==null){
                    return true;
                }else{
                    return false;
                }
            }

            @Override
            protected void onSuccess(QueryOrderResponseBean result) {
                super.onSuccess(result);
                if(isContentEmpty()){
                    return;
                }

                QueryOrderResponseBean.DataEntity.MarketOrderDtoListEntity data=result.getData().getMarketOrderDtoList().get(0);

                mViewOrderNum.setText(String.valueOf(data.getOrderId()));
                mViewOrderPenalty.setText(String.valueOf(data.getPenalty()));
                mViewOverdueTime.setText(Tools.parseTimeToDateStr(data.getRemainingTime()));
                mViewOrderStatus.setText(parseOrderStatusToStr(data.getState()));
                mViewOrderType.setText(getOrderTypeStr(data.getOrderType()));

                mViewCreateOrderCompany.setText(data.getCompanyName());
                mViewAssignCompany.setText(data.getPickCompanyName());
                mViewOrderAmount.setText(String.valueOf(data.getTotalAmount()));

                //确认订单显示逻辑
                mOrderInfo=new OrderInfoBean();
                mOrderInfo.setOrderId(data.getOrderId());
                mOrderInfo.setState(data.getState());
                mOrderInfo.setProductAddress(data.getProductAddress());
                mOrderInfo.setDestination(data.getDestination());
                mOrderInfo.setOrderType(data.getOrderType());
                mOrderInfo.setPickCompanyId(data.getPickCompanyId());
                refreshActionBarMenu();

                //货物详情
                mProductList.clear();

                List<QueryOrderResponseBean.DataEntity.MarketOrderDtoListEntity.MarketOrderDescDtoListEntity> goodsInfoList=data.getMarketOrderDescDtoList();
                if(goodsInfoList!=null){
                    for(QueryOrderResponseBean.DataEntity.MarketOrderDtoListEntity.MarketOrderDescDtoListEntity goodsInfo:goodsInfoList){

                        OrderProductInfo logisticsOrderDetailInfo = new OrderProductInfo();
                        logisticsOrderDetailInfo.productCount=goodsInfo.getProductQuantity();
                        logisticsOrderDetailInfo.productName=goodsInfo.getGoodsName();
                        mProductList.add(logisticsOrderDetailInfo);

                        OrderInfoBean.ProductInfo productInfo=new OrderInfoBean.ProductInfo();
                        productInfo.setGoodsName(goodsInfo.getGoodsName());
                        productInfo.setProductId(goodsInfo.getProductId());
                        productInfo.setProductPrice(goodsInfo.getProductPrice());
                        productInfo.setProductQuantity(goodsInfo.getProductQuantity());
                        mOrderInfo.getProductInfoList().add(productInfo);
                    }
                }

                mDetailAdapter = new ProductDetailAdapter(mProductList, getActivity());
                mProductListView.setAdapter(mDetailAdapter);

                //物流详情
                mLogisticsList.clear();
                List<QueryOrderResponseBean.DataEntity.MarketOrderDtoListEntity.CarsGoodsDetailDtoListEntity> carsInfoList=data.getCarsGoodsDetailDtoList();
                if(carsInfoList!=null){
                    for (QueryOrderResponseBean.DataEntity.MarketOrderDtoListEntity.CarsGoodsDetailDtoListEntity carinfo:carsInfoList) {
                        LogisticsDetailInfo logisticsDetailInfo = new LogisticsDetailInfo();
                        logisticsDetailInfo.logInfo=carinfo.getLogInfo();
                        mLogisticsList.add(logisticsDetailInfo);
                    }
                }

                mLogisticsAdapter = new LogisticsDetailAdapter(mLogisticsList,getActivity());
                mDetailListView.setAdapter(mLogisticsAdapter);
            }
        });
    }


    private void confirmRequest(boolean agree) {
        if(isRequestProcessing(ApiUrls.CHECK_NEW_ORDER)){
            return;
        }
        CheckNewOrderRequestBean requestBean=new CheckNewOrderRequestBean();
        CheckNewOrderRequestBean.DataEntity data=new CheckNewOrderRequestBean.DataEntity();
        requestBean.setData(data);
        data.setOrderId(mOrderInfo.getOrderId());
        data.setState(agree ? "2" : "1");
        startJsonRequest(ApiUrls.CHECK_NEW_ORDER, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        BaseResponseBean responseBean = Tools.parseJsonTostError(result, BaseResponseBean.class);
                        if (responseBean != null) {
                            //关闭对话框
                            dismissDialog();
                            //隐藏确认按钮
                            mActionBarMenu.setText("");
                            mOrderStatus=OrderStatus.NONE;
                            ToastUtils.toast("处理成功！");
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        });
    }

    private void acceptOrderRequest() {
        if(isRequestProcessing(ApiUrls.PRE_ACCEPT_ORDER)){
            return;
        }
        PreAcceptOrderRequestBean requestBean=new PreAcceptOrderRequestBean();
        PreAcceptOrderRequestBean.DataEntity data=new PreAcceptOrderRequestBean.DataEntity();
        requestBean.setData(data);
        data.setOrderId(mOrderInfo.getOrderId());
        startJsonRequest(ApiUrls.PRE_ACCEPT_ORDER, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        BaseResponseBean responseBean = Tools.parseJsonTostError(result, BaseResponseBean.class);
                        if (responseBean != null) {
                            //关闭对话框
                            dismissDialog();
                            //隐藏确认按钮
                            mActionBarMenu.setText("");
                            mOrderStatus=OrderStatus.NONE;
                            ToastUtils.toast("接单成功！");
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        });
    }

    private void refreshActionBarMenu() {
        //从仓库进入的订单当独处理
        if(mComeFromWarehouse){
            if (mOrderInfo != null&&"3".equals(mOrderInfo.getState()) && UserInfo.getCurrentUser().isStorePurchaseLayer()) {
                //仓库部门发起物流订单
                mActionBarMenu.setText("发起物流订单");
                mOrderStatus=OrderStatus.SEND_LOGISTICS_ORDER;
            }else{
                mActionBarMenu.setText("");
                mOrderStatus=OrderStatus.NONE;
            }
            return;
        }


        if (mOrderInfo == null) {
            mActionBarMenu.setText("");
            mOrderStatus=OrderStatus.NONE;
        } else if ("0".equals(mOrderInfo.getState()) && UserInfo.getCurrentUser().isManagementLayer()&&isPurchaseOrSaleOrder(mOrderInfo)) {
            //管理层确认
            mActionBarMenu.setText("确认");
            mOrderStatus=OrderStatus.CONFIRM;
        } else if ("2".equals(mOrderInfo.getState()) && UserInfo.getCurrentUser().isStorePurchaseLayer()&&isPurchaseOrSaleOrder(mOrderInfo)&&mOrderInfo.getPickCompanyId()==0) {
            //仓库部门接单
            mActionBarMenu.setText("接单");
            mOrderStatus=OrderStatus.ACCEPT;
        }else if ("2".equals(mOrderInfo.getState()) && UserInfo.getCurrentUser().isLogisticsLayer() && isLogisticsOrder(mOrderInfo)&&mOrderInfo.getPickCompanyId()==0) {
            //物流部门接单
            mActionBarMenu.setText("接单");
            mOrderStatus=OrderStatus.ACCEPT;
        } else{
            mActionBarMenu.setText("");
            mOrderStatus=OrderStatus.NONE;
        }
    }

    //"orderType":1销售 2物流 3物流(按件) 4采购,
    private boolean isPurchaseOrSaleOrder(OrderInfoBean orderInfo){
        if("1".equals(orderInfo.getOrderType())||"4".equals(orderInfo.getOrderType())){
            return true;
        }
        return false;
    }

    //"orderType":1销售 2物流 3物流(按件) 4采购,
    private boolean isLogisticsOrder(OrderInfoBean orderInfo){
        if("2".equals(orderInfo.getOrderType())||"3".equals(orderInfo.getOrderType())){
            return true;
        }
        return false;
    }

    private String getOrderTypeStr(String orderType){
        String orderTypeStr="";
        if("1".equals(orderType)){
            orderTypeStr="销售 - 订单";
        }else if("2".equals(orderType)){
            orderTypeStr="物流(整单) - 订单";
        }else if("3".equals(orderType)){
            orderTypeStr="物流(按件) - 订单";
        }else if("4".equals(orderType)){
            orderTypeStr="采购 - 订单";
        }
        return orderTypeStr;
    }


    private void showApplyDialog(){
        dismissDialog();

        mDialog =Tools.createDialog(getActivity(), R.layout.dialog_cancel_or_confirm);

        TextView titleView= (TextView) mDialog.findViewById(R.id.title);
        titleView.setText("确认订单");
        TextView summaryView= (TextView) mDialog.findViewById(R.id.summary);
        summaryView.setText("是否同意发布此订单?");

        TextView cancelBtn= (TextView) mDialog.findViewById(R.id.cancel);
        cancelBtn.setText("拒绝");
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmRequest(false);
            }
        });

        TextView confirmBtn= (TextView) mDialog.findViewById(R.id.confirm);
        confirmBtn.setText("同意");
        confirmBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                confirmRequest(true);
            }
        });
        mDialog.show();
    }

    private void showAcceptOrderDialog(){
        dismissDialog();

        mDialog =Tools.createDialog(getActivity(), R.layout.dialog_cancel_or_confirm);

        TextView titleView= (TextView) mDialog.findViewById(R.id.title);
        titleView.setText("确认接单");
        TextView summaryView= (TextView) mDialog.findViewById(R.id.summary);
        summaryView.setText("是否接收此订单?");

        TextView cancelBtn= (TextView) mDialog.findViewById(R.id.cancel);
        //cancelBtn.setText("拒绝");
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });

        TextView confirmBtn= (TextView) mDialog.findViewById(R.id.confirm);
        //confirmBtn.setText("同意");
        confirmBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                acceptOrderRequest();
            }
        });
        mDialog.show();
    }


    private void dismissDialog(){
        if(mDialog !=null&& mDialog.isShowing()){
            mDialog.dismiss();
            mDialog =null;
        }
    }

    private class ProductDetailAdapter extends ABaseAdapter<OrderProductInfo> {

        public ProductDetailAdapter(ArrayList<OrderProductInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<OrderProductInfo> newItemView() {
            return new LogisticsOrderDetailItem();
        }
    }

    private class LogisticsOrderDetailItem extends ABaseAdapter.AbstractItemView<OrderProductInfo> {
        @ViewInject(id = R.id.product_name)
        TextView mViewProductName;

        @ViewInject(id = R.id.product_count)
        TextView mViewProductCount;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_order_details_product;
        }

        @Override
        public void bindingData(View convertView, OrderProductInfo data) {
            mViewProductName.setText(data.productName);
            mViewProductCount.setText(String.format("x%d",data.productCount));
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
            return R.layout.list_item_order_detail_logistics;
        }

        @Override
        public void bindingData(View convertView, LogisticsDetailInfo data) {
            TextView logInfo=(TextView)convertView;
            logInfo.setText(data.logInfo);
        }
    }

    private class OrderProductInfo {
        String productName;
        int productCount;
    }

    private class LogisticsDetailInfo{
        String logInfo;
    }

    private String parseOrderStatusToStr(String status){
        String statusStr="";
        if(status.equals("0")){
            statusStr="待审核";
        }else if(status.equals("1")){
            statusStr = "审核未通过";
        }else if(status.equals("2")){
            statusStr = "已发布";
        }else if(status.equals("3")){
            statusStr = "已接单";
        }else if(status.equals("4")){
            statusStr = "物流中";
        }else if(status.equals("5")){
            statusStr = "已完成";
        }else if(status.equals("6")){
            statusStr = "已逾期";
        }

        return statusStr;
    }

}
