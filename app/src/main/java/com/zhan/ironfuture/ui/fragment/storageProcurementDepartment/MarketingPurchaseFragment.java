package com.zhan.ironfuture.ui.fragment.storageProcurementDepartment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.ui.fragment.ARefreshFragment;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.BaseRequestBean;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.BuyGoodsP1P2RequestBean;
import com.zhan.ironfuture.beans.BuyGoodsP1P2ResponseBean;
import com.zhan.ironfuture.beans.QueryOrderPurchaseResponseBean;
import com.zhan.ironfuture.beans.QueryOrderRequestBean;
import com.zhan.ironfuture.beans.QueryOrderResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.fragment.base.ABaseTabFragment;
import com.zhan.ironfuture.ui.fragment.ordersCenter.OrderDetailFragment;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/21.
 * 市场采购
 */
public class MarketingPurchaseFragment extends ABaseTabFragment<MarketingPurchaseFragment.MarketingAll> {
    @ViewInject(id = R.id.main_tab)
    LinearLayout mProductContent;

    //view
    private TextView mViewGoods, mViewDetail;

    //Tools
    private LayoutInflater mInflater;

    private static final int TAB_DETAIL = 0;
    private static final int TAB_GOODS_OUT = 1;
    private static final int TAB_GOODS_PERFORM = 2;
    private static final int TAB_GOODS_OVERDUE = 3;

    private static final int TYPE_COUNT = 4;//类型总数

    private int mCurTab = TAB_GOODS_OUT;
    private int mPreSubTab = TAB_GOODS_OUT;
    private int mState = 2;//2  已挂出，4履行中，5已完成

    @Override
    protected int inflateContentView() {
        return R.layout.frag_marketing_purchase;
    }

    @Override
    protected List<TabInfo> generateTabList() {
        List<TabInfo> tabs = new ArrayList<>();

        TabInfo tabItem = new TabInfo();
        tabItem.tabCode = TAB_GOODS_OUT;
        tabItem.name = "已挂出";
        tabs.add(tabItem);

        tabItem = new TabInfo();
        tabItem.tabCode = TAB_GOODS_PERFORM;
        tabItem.name = "履行中";
        tabs.add(tabItem);

        tabItem = new TabInfo();
        tabItem.tabCode = TAB_GOODS_OVERDUE;
        tabItem.name = "已完成";
        tabs.add(tabItem);
        return tabs;
    }

    @Override
    protected void onTabChanged(int mainTabCode, int subTabCode) {
        mCurTab = mainTabCode;
        mPreSubTab = mainTabCode;
        setRefreshing();
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        mCurTab = TAB_GOODS_OUT;
        mPreSubTab = TAB_GOODS_OUT;
        mState = 2;//2  已挂出，4履行中，5已完成
        mInflater = inflater;
        if (getActivity() instanceof ActionBarActivity) {
            ActionBarActivity actionBarActivity = (ActionBarActivity) getActivity();
            actionBarActivity.setShowActionBarCustomerContent(true);
            FrameLayout actionbarContent = actionBarActivity.getActionBarCustomerContent();

            actionbarContent.removeAllViews();
            View headerLayout = mInflater.inflate(R.layout.header_marketing_purchase, null);
            mViewGoods = (TextView) headerLayout.findViewById(R.id.goods_purchase);
            mViewDetail = (TextView) headerLayout.findViewById(R.id.detail_purchase);

            mViewGoods.setOnClickListener(mOnTabClick);
            mViewDetail.setOnClickListener(mOnTabClick);

            actionbarContent.addView(headerLayout);

            mViewGoods.setBackgroundResource(R.drawable.bg_color3_underline);
            mViewGoods.setTextColor(0xff17112B);
            mViewGoods.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
            mViewDetail.setBackgroundResource(R.drawable.default_bg);
            mViewDetail.setTextColor(0xff999999);
            mViewDetail.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
        }
    }


    private View.OnClickListener mOnTabClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.goods_purchase:
                    mProductContent.setVisibility(View.VISIBLE);
                    mViewGoods.setBackgroundResource(R.drawable.bg_color3_underline);
                    mViewGoods.setTextColor(0xff17112B);
                    mViewGoods.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mViewDetail.setBackgroundResource(R.drawable.default_bg);
                    mViewDetail.setTextColor(0xff999999);
                    mViewDetail.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mCurTab = mPreSubTab;
                    if(getAdapterItems()!=null){
                        getAdapterItems().clear();
                    }
                    setRefreshing();
                    break;
                case R.id.detail_purchase:
                    mProductContent.setVisibility(View.GONE);
                    mViewGoods.setBackgroundResource(R.drawable.default_bg);
                    mViewGoods.setTextColor(0xff999999);
                    mViewGoods.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mViewDetail.setBackgroundResource(R.drawable.bg_color3_underline);
                    mViewDetail.setTextColor(0xff17112B);
                    mViewDetail.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mCurTab = TAB_DETAIL;
                    if(getAdapterItems()!=null){
                        getAdapterItems().clear();
                    }
                    setRefreshing();
                    break;
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        if(mCurTab == TAB_DETAIL){
            showDialog(getAdapterItems().get((int)id).marketingPurchaseInfo.goodsId,getAdapterItems().get((int)id).marketingPurchaseInfo.goodsName,getAdapterItems().get((int)id).marketingPurchaseInfo.goodsSum);
        }else{
            OrderDetailFragment.launch(getActivity(), getAdapterItems().get((int) id).marketingGoodsInfo.orderId);
        }
    }

    private void showDialog(final int goodsId,final String goodsName,final int goodsSum) {
        final Dialog dialog = Tools.createDialog(getActivity(), R.layout.dialog_market_purchase);
        TextView title = (TextView) dialog.findViewById(R.id.owner_title);
        title.setText("你确认购买 " + goodsName + "物品吗？");
        final TextView countChange = (TextView) dialog.findViewById(R.id.market_purchase_count_change);
        final SeekBar mProductSeekBar = (SeekBar) dialog.findViewById(R.id.market_purchase__seekbar);
        TextView purchaseCount = (TextView) dialog.findViewById(R.id.market_purchase__count);
        if(goodsSum > 10){
            purchaseCount.setText("10");
            mProductSeekBar.setMax(10);
        }else{
            purchaseCount.setText(goodsSum+"");
            mProductSeekBar.setMax(goodsSum);
        }
        mProductSeekBar.setProgress(0);
        int seekBarProgress = mProductSeekBar.getMax();
        mProductSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                /*if (fromUser) {
                    seekBarProgress = progress;
                }*/
                if (progress < 1) {
                    countChange.setText("1");
                } else {
                    countChange.setText(String.valueOf(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("---", "goodsId===>" + goodsId + "-goodsSum---" + countChange.getText().toString());
                int goodsSum = Integer.parseInt(countChange.getText().toString());
                butGoods(goodsId,goodsSum);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void butGoods(int goodsId, int goodsSum) {
        if(isRequestProcessing(ApiUrls.BUY_GOODS_P1P2)){
            return;
        }
        BuyGoodsP1P2RequestBean requestBean = new BuyGoodsP1P2RequestBean();
        BuyGoodsP1P2RequestBean.DataEntity dataEntity = new BuyGoodsP1P2RequestBean.DataEntity();
        dataEntity.setGoodsId(goodsId);
        dataEntity.setGoodsSum(goodsSum);
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.BUY_GOODS_P1P2, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        BuyGoodsP1P2ResponseBean responseBean = Tools.parseJsonTostError(result, BuyGoodsP1P2ResponseBean.class);
                        if (responseBean != null) {
                            ToastUtils.toast(responseBean.getMessage());
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }

    @Override
    protected ABaseAdapter.AbstractItemView<MarketingAll> newItemView() {
        return new MarketingPurchaseItemView();
    }

    @Override
    public int getItemViewType(int position) {
        return mCurTab;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    protected void configRefresh(RefreshConfig config) {
        config.minResultSize=IronFutureConstants.DEF_PAGE_SIZE;
    }

    @Override
    public void onChangedByConfig(RefreshConfig config) {
        super.onChangedByConfig(config);
        if (mCurTab == TAB_DETAIL){
            mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }
    }

    @Override
    protected void requestData(RefreshMode mode) {
        releaseAllRequest();
        if (mCurTab == TAB_GOODS_OUT || mCurTab == TAB_GOODS_PERFORM || mCurTab == TAB_GOODS_OVERDUE) {
            int startPageId;
            if (mode == RefreshMode.refresh || mode == RefreshMode.reset||isContentEmpty()) {
                startPageId = 0;
            }else{
                startPageId=getAdapterItems().get(getAdapterItems().size()-1).marketingGoodsInfo.getOrderId();
            }
            QueryOrderRequestBean request = new QueryOrderRequestBean();
            QueryOrderRequestBean.DataEntity data = new QueryOrderRequestBean.DataEntity();

            if (mCurTab == TAB_GOODS_OUT) {//已挂出
                mState = 2;
            } else if (mCurTab == TAB_GOODS_PERFORM) {//履行中
                mState = 4;
                //data.setDestination("1");
                data.setCompanyId(UserInfo.getCurrentUser().getComId());
            } else if (mCurTab == TAB_GOODS_OVERDUE) {//已完成
                mState = 5;
                //data.setDestination("1");
                data.setCompanyId(UserInfo.getCurrentUser().getComId());
            }
            data.setPageDirection(IronFutureConstants.PAGE_DIRECTION_DOWN);
            data.setPageId(startPageId);
            data.setPageCount(IronFutureConstants.DEF_PAGE_SIZE);
            data.setOrderType(1);
            data.setQueryDeep(1);
            data.setState(mState);
            request.setData(data);

            startJsonRequest(ApiUrls.ORDER_BS_QUERY_ORDER, request, new PagingTask<QueryOrderResponseBean>(mode) {
                @Override
                public QueryOrderResponseBean parseResponseToResult(String content) {
                    return Tools.parseJson(content, QueryOrderResponseBean.class);
                }

                @Override
                public String verifyResponseResult(QueryOrderResponseBean result) {
                    return Tools.verifyResponseResult(result);
                }

                @Override
                protected List<MarketingAll> parseResult(QueryOrderResponseBean baseResponseBean) {
                    List<MarketingAll> beanList = new LinkedList<>();
                    if(baseResponseBean.getData()==null||baseResponseBean.getData().getMarketOrderDtoList()==null){
                        return beanList;
                    }
                    for (QueryOrderResponseBean.DataEntity.MarketOrderDtoListEntity item: baseResponseBean.getData().getMarketOrderDtoList()) {
                        MarketingAll allItemBean=new MarketingAll();
                        MarketingGoodsInfo bean = new MarketingGoodsInfo();
                        bean.setOrderId(item.getOrderId());
                        bean.setOrderType(item.getOrderType());
                        bean.setBsOrderId(item.getBsOrderId());
                        bean.setTotalAmount(item.getTotalAmount());
                        bean.setCompanyId(item.getCompanyId());
                        bean.setProductAddress(item.getProductAddress());
                        bean.setDestination(item.getDestination());
                        bean.setFlowId(item.getFlowId());
                        bean.setPenalty(item.getPenalty());
                        bean.setPickCompanyId(item.getPickCompanyId());
                        bean.setState(item.getState());
                        bean.setRemainingTime(item.getRemainingTime());
                        bean.setCreateTime(item.getCreateTime());
                        bean.setUpdateTime(item.getUpdateTime());
                        bean.setLogisStatTime(item.getLogisStatTime());
                        bean.setLogisEndTime(item.getLogisEndTime());
                        bean.setQueryDeep(item.getQueryDeep());
                        bean.setKindCount(item.getKindCount());
                        bean.setMarketOrderDescDtoList(item.getMarketOrderDescDtoList());
                        bean.setCarsGoodsDetailDtoList(item.getCarsGoodsDetailDtoList());

                        allItemBean.marketingGoodsInfo=bean;
                        beanList.add(allItemBean);
                    }
                    return beanList;
                }
            });
        } else {
            BaseRequestBean request = new BaseRequestBean();
            startJsonRequest(ApiUrls.GET_GOODS_P1P2_LIST, request, new PagingTask<QueryOrderPurchaseResponseBean>(mode) {
                @Override
                public QueryOrderPurchaseResponseBean parseResponseToResult(String content) {
                    return Tools.parseJson(content, QueryOrderPurchaseResponseBean.class);
                }

                @Override
                public String verifyResponseResult(QueryOrderPurchaseResponseBean result) {
                    return  Tools.verifyResponseResult(result);
                }

                @Override
                protected List<MarketingAll> parseResult(QueryOrderPurchaseResponseBean baseResponseBean) {
                    List<MarketingAll> beanList = new LinkedList<>();
                    if(baseResponseBean == null || baseResponseBean.getData() == null){
                        return beanList;
                    }
                    for (QueryOrderPurchaseResponseBean.DataEntity.P1p2ListEntity item : baseResponseBean.getData().getP1p2List()) {
                        MarketingAll allItemBean = new MarketingAll();
                        MarketingPurchaseInfo bean = new MarketingPurchaseInfo();
                        bean.setGoodsId(item.getGoodsId());
                        bean.setGoodsType(item.getGoodsType());
                        bean.setGoodsName(item.getGoodsName());
                        bean.setGoodsCoefficient(item.getGoodsCoefficient());
                        bean.setGoodsPrice(item.getGoodsPrice());
                        bean.setGoodsState(item.getGoodsState());
                        bean.setGoodsLevel(item.getGoodsLevel());
                        bean.setGoodsSum(item.getGoodsSum());
                        bean.setSellSum(item.getSellSum());
                        bean.setDescs(item.getDescs());
                        allItemBean.marketingPurchaseInfo = bean;
                        beanList.add(allItemBean);
                    }
                    return beanList;
                }
            });
        }

    }

    private class MarketingPurchaseItemView extends ABaseAdapter.AbstractItemView<MarketingAll> {
        //view1
        @ViewInject(idStr = "total")
        TextView mViewTotal;

        @ViewInject(idStr = "order_num")
        TextView mViewOrderNum;


        //view2
        @ViewInject(id = R.id.purch_detail_icon)
        ImageView mIcon ;
        @ViewInject(id = R.id.purch_detail__title)
        TextView mDetailTitle ;
        @ViewInject(id = R.id.time_below)
        TextView mTileBlew;
        @ViewInject(id = R.id.time_right)
        TextView mTime ;


        @Override
        public int inflateViewId() {
            switch (getItemViewType()) {
                case TAB_GOODS_OUT:
                    return R.layout.list_item_orders_summary;
                case TAB_GOODS_PERFORM:
                    return R.layout.list_item_orders_summary;
                case TAB_GOODS_OVERDUE:
                    return R.layout.list_item_orders_summary;

                case TAB_DETAIL:
                    return R.layout.list_item_marketing_purchase_detail;
                default:
                    return 0;
            }
        }

        @Override
        public void bindingData(View convertView, MarketingAll data) {
            switch (getItemViewType()) {
                case TAB_GOODS_OUT:
                    //mOrderNum.setText(data.marketingGoodsInfo.getOrderId() + "");
                    mViewOrderNum.setText(String.valueOf(data.marketingGoodsInfo.getOrderId()));

                    //商品总计
                    mViewTotal.setText(String.format("商品%d种 总计 ",data.marketingGoodsInfo.getKindCount()));
                    String totalPrice = data.marketingGoodsInfo.getTotalAmount()+"";
                    SpannableString ss = new SpannableString(totalPrice);
                    ss.setSpan(new ForegroundColorSpan(0xff333333), 0, totalPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new AbsoluteSizeSpan(18, true), 0, totalPrice.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    mViewTotal.append(ss);
                    break;
                case TAB_GOODS_PERFORM:
                    mViewOrderNum.setText(String.valueOf(data.marketingGoodsInfo.getOrderId()));

                    //商品总计
                    mViewTotal.setText(String.format("商品%d种 总计 ",data.marketingGoodsInfo.getKindCount()));
                    String totalPrice2 = data.marketingGoodsInfo.getTotalAmount()+"";
                    SpannableString ss2 = new SpannableString(totalPrice2);
                    ss2.setSpan(new ForegroundColorSpan(0xff333333), 0, totalPrice2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss2.setSpan(new AbsoluteSizeSpan(18, true), 0, totalPrice2.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    mViewTotal.append(ss2);
                    break;
                case TAB_GOODS_OVERDUE:
                    mViewOrderNum.setText(String.valueOf(data.marketingGoodsInfo.getOrderId()));

                    //商品总计
                    mViewTotal.setText(String.format("商品%d种 总计 ",data.marketingGoodsInfo.getKindCount()));
                    String totalPrice_over = data.marketingGoodsInfo.getTotalAmount()+"";
                    SpannableString ss_over = new SpannableString(totalPrice_over);
                    ss_over.setSpan(new ForegroundColorSpan(0xff333333), 0, totalPrice_over.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss_over.setSpan(new AbsoluteSizeSpan(18, true), 0, totalPrice_over.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    mViewTotal.append(ss_over);
                    break;


                case TAB_DETAIL:
                    mDetailTitle.setText(data.marketingPurchaseInfo.getGoodsName());
                    mTileBlew.setText("单价："+data.marketingPurchaseInfo.getGoodsPrice()+"");
                    mTime.setText("剩余："+data.marketingPurchaseInfo.getGoodsSum()+"");
                    break;
            }

        }
    }

    public class MarketingAll {
        public MarketingGoodsInfo marketingGoodsInfo ;
        public MarketingPurchaseInfo marketingPurchaseInfo ;
    }

    public class MarketingPurchaseInfo {
        private int goodsId;
        private String goodsType;
        private String goodsName;
        private String goodsCoefficient;
        private int goodsPrice;
        private int goodsState;
        private int goodsLevel;
        private int goodsSum;
        private int sellSum;
        private String descs;

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsCoefficient() {
            return goodsCoefficient;
        }

        public void setGoodsCoefficient(String goodsCoefficient) {
            this.goodsCoefficient = goodsCoefficient;
        }

        public int getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(int goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public int getGoodsState() {
            return goodsState;
        }

        public void setGoodsState(int goodsState) {
            this.goodsState = goodsState;
        }

        public int getGoodsLevel() {
            return goodsLevel;
        }

        public void setGoodsLevel(int goodsLevel) {
            this.goodsLevel = goodsLevel;
        }

        public int getGoodsSum() {
            return goodsSum;
        }

        public void setGoodsSum(int goodsSum) {
            this.goodsSum = goodsSum;
        }

        public int getSellSum() {
            return sellSum;
        }

        public void setSellSum(int sellSum) {
            this.sellSum = sellSum;
        }

        public String getDescs() {
            return descs;
        }

        public void setDescs(String descs) {
            this.descs = descs;
        }

    }

    public class MarketingGoodsInfo {
        private int orderId;
        private String orderType;
        private Object bsOrderId;
        private int totalAmount;
        private int companyId;
        private String productAddress;
        private Object destination;
        private Object flowId;
        private Object penalty;
        private Object pickCompanyId;
        private int kindCount;
        private String state;
        private long remainingTime;
        private long createTime;
        private long updateTime;
        private long logisStatTime;
        private String logisEndTime;
        private Object queryDeep;
        private Object marketOrderDescDtoList;
        private Object carsGoodsDetailDtoList;


        public int getKindCount() {
            return kindCount;
        }

        public void setKindCount(int kindCount) {
            this.kindCount = kindCount;
        }


        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public void setBsOrderId(Object bsOrderId) {
            this.bsOrderId = bsOrderId;
        }

        public void setTotalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public void setProductAddress(String productAddress) {
            this.productAddress = productAddress;
        }

        public void setDestination(Object destination) {
            this.destination = destination;
        }

        public void setFlowId(Object flowId) {
            this.flowId = flowId;
        }

        public void setPenalty(Object penalty) {
            this.penalty = penalty;
        }

        public void setPickCompanyId(Object pickCompanyId) {
            this.pickCompanyId = pickCompanyId;
        }

        public void setState(String state) {
            this.state = state;
        }

        public void setRemainingTime(long remainingTime) {
            this.remainingTime = remainingTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public void setLogisStatTime(long logisStatTime) {
            this.logisStatTime = logisStatTime;
        }

        public void setLogisEndTime(String logisEndTime) {
            this.logisEndTime = logisEndTime;
        }

        public void setQueryDeep(Object queryDeep) {
            this.queryDeep = queryDeep;
        }

        public void setMarketOrderDescDtoList(Object marketOrderDescDtoList) {
            this.marketOrderDescDtoList = marketOrderDescDtoList;
        }

        public void setCarsGoodsDetailDtoList(Object carsGoodsDetailDtoList) {
            this.carsGoodsDetailDtoList = carsGoodsDetailDtoList;
        }

        public int getOrderId() {
            return orderId;
        }

        public String getOrderType() {
            return orderType;
        }

        public Object getBsOrderId() {
            return bsOrderId;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public int getCompanyId() {
            return companyId;
        }

        public String getProductAddress() {
            return productAddress;
        }

        public Object getDestination() {
            return destination;
        }

        public Object getFlowId() {
            return flowId;
        }

        public Object getPenalty() {
            return penalty;
        }

        public Object getPickCompanyId() {
            return pickCompanyId;
        }

        public String getState() {
            return state;
        }

        public long getRemainingTime() {
            return remainingTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public long getLogisStatTime() {
            return logisStatTime;
        }

        public String getLogisEndTime() {
            return logisEndTime;
        }

        public Object getQueryDeep() {
            return queryDeep;
        }

        public Object getMarketOrderDescDtoList() {
            return marketOrderDescDtoList;
        }

        public Object getCarsGoodsDetailDtoList() {
            return carsGoodsDetailDtoList;
        }
    }
}
