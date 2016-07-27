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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.GetGoodsSumRequestBean;
import com.zhan.ironfuture.beans.GetGoodsSumResponseBean;
import com.zhan.ironfuture.beans.MerchandiseSalesTradingRequestBean;
import com.zhan.ironfuture.beans.MerchandiseSalesTradingResponseBean;
import com.zhan.ironfuture.beans.MerchandiseSellOrdersRequestBean;
import com.zhan.ironfuture.beans.MerchandiseSellOrdersResponseBean;
import com.zhan.ironfuture.beans.MessageCodeResponseBean;
import com.zhan.ironfuture.beans.TradingCenterLogisticsOrderInfoBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.fragment.base.ABaseTabFragment;
import com.zhan.ironfuture.ui.fragment.ordersCenter.OrderDetailFragment;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/21.
 * 商品销售
 */
public class MerchandiseSalesFragment extends ABaseTabFragment<MerchandiseSalesFragment.MerchandisAllSales> {

    @ViewInject(id = R.id.sale_search_content)
    LinearLayout mSearchContent;
    @ViewInject(id = R.id.main_tab)
    LinearLayout mProductContent;
    //view
    private TextView mViewChange, mViewProduct;

    //Tools
    private LayoutInflater mInflater;

    private static final int TAB_CHANGE = 0;
    private static final int TAB_CONTENT_OUT = 1;
    private static final int TAB_CONTENT_PERFORM = 2;
    private static final int TAB_CONTENT_OVERDUE = 3;

    private static final int TYPE_COUNT = 4;//类型总数

    private int mPreSubTab = TAB_CONTENT_OUT;
    private int mCurTab = TAB_CHANGE;
    private int mState = 2;//2  已挂出，4履行中，5已完成

    @Override
    protected int inflateContentView() {
        return R.layout.frag_merchandise_sales;
    }

    @Override
    protected List<TabInfo> generateTabList() {
        List<TabInfo> tabs = new ArrayList<>();

        TabInfo tabItem = new TabInfo();
        tabItem.tabCode = TAB_CONTENT_OUT;
        tabItem.name = "已挂出";
        tabs.add(tabItem);

        tabItem = new TabInfo();
        tabItem.tabCode = TAB_CONTENT_PERFORM;
        tabItem.name = "履行中";
        tabs.add(tabItem);

        tabItem = new TabInfo();
        tabItem.tabCode = TAB_CONTENT_OVERDUE;
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
        mPreSubTab = TAB_CONTENT_OUT;
        mCurTab = TAB_CHANGE;
        mState = 2;//2  已挂出，4履行中，5已完成
        mInflater = inflater;
        if (getActivity() instanceof ActionBarActivity) {
            ActionBarActivity actionBarActivity = (ActionBarActivity) getActivity();
            actionBarActivity.setShowActionBarCustomerContent(true);
            FrameLayout actionbarContent = actionBarActivity.getActionBarCustomerContent();

            actionbarContent.removeAllViews();
            View headerLayout = mInflater.inflate(R.layout.header_marketing_sales, null);
            mViewChange = (TextView) headerLayout.findViewById(R.id.sales_change);
            mViewProduct = (TextView) headerLayout.findViewById(R.id.sales_product);

            mViewChange.setOnClickListener(mOnTabClick);
            mViewProduct.setOnClickListener(mOnTabClick);

            actionbarContent.addView(headerLayout);

            mViewChange.setBackgroundResource(R.drawable.bg_color3_underline);
            mViewChange.setTextColor(0xff17112B);
            mViewChange.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
            mViewProduct.setBackgroundResource(R.drawable.default_bg);
            mViewProduct.setTextColor(0xff999999);
            mViewProduct.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
        }

    }

    private View.OnClickListener mOnTabClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sales_change:
                    mSearchContent.setVisibility(View.GONE);
                    mProductContent.setVisibility(View.GONE);
                    mViewChange.setBackgroundResource(R.drawable.bg_color3_underline);
                    mViewChange.setTextColor(0xff17112B);
                    mViewChange.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mViewProduct.setBackgroundResource(R.drawable.default_bg);
                    mViewProduct.setTextColor(0xff999999);
                    mViewProduct.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mCurTab = TAB_CHANGE;
                    if(getAdapterItems()!=null){
                        getAdapterItems().clear();
                    }
                    setRefreshing();
                    break;
                case R.id.sales_product:
                    mSearchContent.setVisibility(View.GONE);
                    mProductContent.setVisibility(View.VISIBLE);
                    mViewChange.setBackgroundResource(R.drawable.default_bg);
                    mViewChange.setTextColor(0xff999999);
                    mViewChange.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mViewProduct.setBackgroundResource(R.drawable.bg_color3_underline);
                    mViewProduct.setTextColor(0xff17112B);
                    mViewProduct.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mCurTab = mPreSubTab;
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
        if(mCurTab == TAB_CHANGE){
            int itemId = (int)id ;
            int goodsId = Integer.parseInt(getAdapterItems().get(itemId).merchandiseSalesInfo.getGoodsId());
            checkGoodsSum(goodsId, itemId);
        }else{
            OrderDetailFragment.launch(getActivity(), getAdapterItems().get((int)id).sellOrdersInfo.orderId);
        }
    }

    private void checkGoodsSum(int goodsId, final int itemId) {
        GetGoodsSumRequestBean request = new GetGoodsSumRequestBean();
        GetGoodsSumRequestBean.DataEntity dataEntity = new GetGoodsSumRequestBean.DataEntity();
        dataEntity.setGoodsId(goodsId);
        dataEntity.setComId(UserInfo.getCurrentUser().getComId());
        request.setData(dataEntity);
        startJsonRequest(ApiUrls.GET_GOODS_SUM_BY_GOODSID, request, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        GetGoodsSumResponseBean responseBean = Tools.parseJsonTostError(result, GetGoodsSumResponseBean.class);
                        if (responseBean != null && responseBean.getData() != null) {
                            if (responseBean.getData().getGoodsSum() > 0) {
                                showDialog(itemId, responseBean.getData().getGoodsSum());
                            } else {
                                ToastUtils.toast("仓库中没有" + getAdapterItems().get(itemId).merchandiseSalesInfo.getGoodsNmae());
                            }
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }


    private void showDialog(int id,int goodsSum) {
        int goodsId = Integer.parseInt(getAdapterItems().get(id).merchandiseSalesInfo.getGoodsId());

        final TradingCenterLogisticsOrderInfoBean bean=new TradingCenterLogisticsOrderInfoBean();
        bean.setGoodsSum(goodsSum);
        bean.setGoodsId(goodsId);
        bean.setPrice(getAdapterItems().get(id).merchandiseSalesInfo.getPrice());
        bean.setGoodsName(getAdapterItems().get(id).merchandiseSalesInfo.getGoodsNmae());

        final Dialog dialog = Tools.createDialog(getActivity(), R.layout.dialog_market_sale);
        TextView title = (TextView) dialog.findViewById(R.id.owner_title);
        title.setText("你确认要销售 " + getAdapterItems().get(id).merchandiseSalesInfo.getGoodsNmae() + "物品吗？");
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                TradingCenterLogisticsOrderFragment.launcher(getActivity(), bean);
            }
        });
        dialog.show();
    }

    @Override
    protected void configRefresh(RefreshConfig config) {
        config.minResultSize=IronFutureConstants.DEF_PAGE_SIZE;
    }

    @Override
    protected void requestData(RefreshMode mode) {
        releaseAllRequest();
        if (mCurTab == TAB_CONTENT_OUT || mCurTab == TAB_CONTENT_PERFORM || mCurTab == TAB_CONTENT_OVERDUE) {
            int startPageId;
            if (mode == RefreshMode.refresh || mode == RefreshMode.reset||isContentEmpty()) {
                startPageId = 0;
            }else{
                startPageId=getAdapterItems().get(getAdapterItems().size()-1).sellOrdersInfo.getOrderId();
            }

            MerchandiseSellOrdersRequestBean request = new MerchandiseSellOrdersRequestBean();
            MerchandiseSellOrdersRequestBean.DataEntity data = new MerchandiseSellOrdersRequestBean.DataEntity();
            if (mCurTab == TAB_CONTENT_OUT) {//已挂出
                mState = 2;
            } else if (mCurTab == TAB_CONTENT_PERFORM) {//履行中
                mState = 4;
                data.setCompanyId(UserInfo.getCurrentUser().getComId());
            } else if (mCurTab == TAB_CONTENT_OVERDUE) {//过期
                mState = 5;
                data.setCompanyId(UserInfo.getCurrentUser().getComId());
            }
            data.setPageId(startPageId);
            data.setPageCount(IronFutureConstants.DEF_PAGE_SIZE);
            data.setPageDirection(IronFutureConstants.PAGE_DIRECTION_DOWN);
            data.setQueryDeep(1);
            data.setOrderType(4);
            data.setState(mState);
            request.setData(data);
            startJsonRequest(ApiUrls.ORDER_BS_QUERY_ORDER, request, new PagingTask<MerchandiseSellOrdersResponseBean>(mode) {
                @Override
                public MerchandiseSellOrdersResponseBean parseResponseToResult(String content) {
                    return Tools.parseJson(content, MerchandiseSellOrdersResponseBean.class);
                }

                @Override
                public String verifyResponseResult(MerchandiseSellOrdersResponseBean result) {
                    return  Tools.verifyResponseResult(result);
                }
                @Override
                protected List<MerchandisAllSales> parseResult(MerchandiseSellOrdersResponseBean baseResponseBean) {
                    List<MerchandisAllSales> beanList = new LinkedList<>();
                    if(baseResponseBean == null ? true : baseResponseBean.getData()==null){
                        return beanList;
                    }
                    for (MerchandiseSellOrdersResponseBean.DataEntity.MarketOrderDtoListEntity item : baseResponseBean.getData().getMarketOrderDtoList()) {
                        MerchandisAllSales allItemBean = new MerchandisAllSales();
                        SellOrdersInfo bean = new SellOrdersInfo();
                        bean.setPageId(item.getPageId());
                        bean.setPageCount(item.getPageCount());
                        bean.setPageDirection(item.getPageDirection());
                        bean.setSerachKey(item.getSerachKey());
                        bean.setBigType(item.getBigType());
                        bean.setInviteId(item.getInviteId());
                        bean.setDeptid(item.getDeptid());
                        bean.setOrderId(item.getOrderId());
                        bean.setOrderType(item.getOrderType());
                        bean.setOrderCategory(item.getOrderCategory());
                        bean.setBsOrderId(item.getBsOrderId());
                        bean.setTotalAmount(item.getTotalAmount());
                        bean.setCompanyId(item.getCompanyId());
                        bean.setProductAddress(item.getProductAddress());
                        bean.setDestination(item.getDestination());
                        bean.setFlowId(item.getFlowId());
                        bean.setPenalty(item.getPenalty());
                        bean.setPickCompanyId(item.getPickCompanyId());
                        bean.setKindCount(item.getKindCount());
                        bean.setState(item.getState());
                        bean.setRemainingTime(item.getRemainingTime());
                        bean.setCreateTime(item.getCreateTime());
                        bean.setUpdateTime(item.getUpdateTime());
                        bean.setLogisStatTime(item.getLogisStatTime());
                        bean.setLogisEndTime(item.getLogisEndTime());
                        bean.setQueryDeep(item.getQueryDeep());
                        bean.setMarketOrderDescDtoList(item.getMarketOrderDescDtoList());
                        bean.setCarsGoodsDetailDtoList(item.getCarsGoodsDetailDtoList());
                        allItemBean.sellOrdersInfo = bean;
                        beanList.add(allItemBean);
                    }
                    return beanList;
                }
            });
        }else{
            int startPageId;
            if (mode == RefreshMode.refresh || mode == RefreshMode.reset||isContentEmpty()) {
                startPageId = 0;
            }else{
                startPageId= Integer.parseInt(getAdapterItems().get(getAdapterItems().size() - 1).merchandiseSalesInfo.getGoodsId());
            }

            MerchandiseSalesTradingRequestBean request = new MerchandiseSalesTradingRequestBean();
            MerchandiseSalesTradingRequestBean.DataEntity data = new MerchandiseSalesTradingRequestBean.DataEntity();
            data.setPageId(startPageId);
            data.setPageCount(IronFutureConstants.DEF_PAGE_SIZE);
            data.setPageDirection(IronFutureConstants.PAGE_DIRECTION_DOWN);
            request.setData(data);

            startJsonRequest(ApiUrls.TRADINGCENTER, request, new PagingTask<MerchandiseSalesTradingResponseBean>(mode) {
                @Override
                public MerchandiseSalesTradingResponseBean parseResponseToResult(String content) {
                    return Tools.parseJson(content, MerchandiseSalesTradingResponseBean.class);
                }
                @Override
                public String verifyResponseResult(MerchandiseSalesTradingResponseBean result) {
                    return  Tools.verifyResponseResult(result);
                }

                @Override
                protected List<MerchandisAllSales> parseResult(MerchandiseSalesTradingResponseBean baseResponseBean) {
                    List<MerchandisAllSales> beanList = new LinkedList<>();
                    if(baseResponseBean == null || baseResponseBean.getData() == null){
                        return beanList;
                    }
                    for (MerchandiseSalesTradingResponseBean.DataEntity item : baseResponseBean.getData()) {
                        MerchandisAllSales allItemBean=new MerchandisAllSales();
                        MerchandiseSalesInfo bean = new MerchandiseSalesInfo();
                        bean.setMarketSysId(item.getMarketSysId());
                        bean.setGoodsId(item.getGoodsId());
                        bean.setPrice(item.getPrice());
                        bean.setBuySum(item.getBuySum());
                        bean.setCreatBuytime(item.getCreatBuytime());
                        bean.setEndBuytime(item.getEndBuytime());
                        bean.setSum(item.getSum());
                        bean.setAlreadySum(item.getAlreadySum());
                        bean.setGoodsNmae(item.getGoodsNmae());
                        allItemBean.merchandiseSalesInfo =bean;
                        beanList.add(allItemBean);
                    }
                    return beanList;
                }
            });
        }
    }

    @Override
    protected ABaseAdapter.AbstractItemView<MerchandisAllSales> newItemView() {
        return new MarketingSaleItemView();
    }

    @Override
    public int getItemViewType(int position) {
        return mCurTab;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }


    private class MarketingSaleItemView extends ABaseAdapter.AbstractItemView<MerchandisAllSales> {
        //view1
        @ViewInject(id = R.id.purch_detail__title)
        TextView mDetailTitle;
        @ViewInject(id = R.id.marketing_sales_change_price)
        TextView mChangePrice;
        @ViewInject(id = R.id.marketing_sales_change_time)
        TextView mChangeTime;
        @ViewInject(id = R.id.marketing_sales_change_number)
        TextView mChangeNum;

        //view2
        @ViewInject(idStr = "total")
        TextView mViewTotal;

        @ViewInject(idStr = "order_num")
        TextView mViewOrderNum;

        @Override
        public int inflateViewId() {
            switch (getItemViewType()) {
                case TAB_CHANGE:
                    return R.layout.list_item_marketing_sales_change;

                case TAB_CONTENT_OUT:
                    return R.layout.list_item_orders_summary;
                case TAB_CONTENT_PERFORM:
                    return R.layout.list_item_orders_summary;
                case TAB_CONTENT_OVERDUE:
                    return R.layout.list_item_orders_summary;
                default:
                    return 0;
            }
        }

        @Override
        public void bindingData(View convertView, MerchandisAllSales data) {
            switch (getItemViewType()) {
                case TAB_CHANGE:
                    mDetailTitle.setText(data.merchandiseSalesInfo.getGoodsNmae());
                    mChangePrice.setText("单价" + data.merchandiseSalesInfo.getPrice());
                    mChangeTime.setText("收购时间：" + Tools.parseTime(data.merchandiseSalesInfo.getEndBuytime()));
                    mChangeNum.setText("收购件数：" + data.merchandiseSalesInfo.getAlreadySum()+"/"+data.merchandiseSalesInfo.getSum()+"");
                    break;

                case TAB_CONTENT_OUT:
                    mViewOrderNum.setText(String.valueOf(data.sellOrdersInfo.getOrderId()));

                    //商品总计
                    mViewTotal.setText(String.format("商品%d种 总计 ",data.sellOrdersInfo.getKindCount()));
                    String totalPrice = data.sellOrdersInfo.getTotalAmount()+"";
                    SpannableString ss = new SpannableString(totalPrice);
                    ss.setSpan(new ForegroundColorSpan(0xff333333), 0, totalPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new AbsoluteSizeSpan(18, true), 0, totalPrice.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    mViewTotal.append(ss);
                    break;
                case TAB_CONTENT_PERFORM:
                    mViewOrderNum.setText(String.valueOf(data.sellOrdersInfo.getOrderId()));

                    //商品总计
                    mViewTotal.setText(String.format("商品%d种 总计 ",data.sellOrdersInfo.getKindCount()));
                    String totalPrice2 = data.sellOrdersInfo.getTotalAmount()+"";
                    SpannableString ss2 = new SpannableString(totalPrice2);
                    ss2.setSpan(new ForegroundColorSpan(0xff333333), 0, totalPrice2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss2.setSpan(new AbsoluteSizeSpan(18, true), 0, totalPrice2.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    mViewTotal.append(ss2);
                    break;
                case TAB_CONTENT_OVERDUE:
                    mViewOrderNum.setText(String.valueOf(data.sellOrdersInfo.getOrderId()));

                    //商品总计
                    mViewTotal.setText(String.format("商品%d种 总计 ",data.sellOrdersInfo.getKindCount()));
                    String totalPrice_over = data.sellOrdersInfo.getTotalAmount()+"";
                    SpannableString ss_over = new SpannableString(totalPrice_over);
                    ss_over.setSpan(new ForegroundColorSpan(0xff333333), 0, totalPrice_over.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss_over.setSpan(new AbsoluteSizeSpan(18, true), 0, totalPrice_over.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    mViewTotal.append(ss_over);
                    break;
            }

        }
    }


    public class MerchandisAllSales{
        public MerchandiseSalesInfo merchandiseSalesInfo ;
        public SellOrdersInfo sellOrdersInfo ;
    }

    public class SellOrdersInfo {
        private Object pageId;
        private int pageCount;
        private Object pageDirection;
        private Object serachKey;
        private Object bigType;
        private Object inviteId;
        private Object deptid;
        private int orderId;
        private String orderType;
        private Object orderCategory;
        private Object bsOrderId;
        private int totalAmount;
        private int companyId;
        private Object productAddress;
        private String destination;
        private Object flowId;
        private int penalty;
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

        public void setPageId(Object pageId) {
            this.pageId = pageId;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public void setPageDirection(Object pageDirection) {
            this.pageDirection = pageDirection;
        }

        public void setSerachKey(Object serachKey) {
            this.serachKey = serachKey;
        }

        public void setBigType(Object bigType) {
            this.bigType = bigType;
        }

        public void setInviteId(Object inviteId) {
            this.inviteId = inviteId;
        }

        public void setDeptid(Object deptid) {
            this.deptid = deptid;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public void setOrderCategory(Object orderCategory) {
            this.orderCategory = orderCategory;
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

        public void setProductAddress(Object productAddress) {
            this.productAddress = productAddress;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public void setFlowId(Object flowId) {
            this.flowId = flowId;
        }

        public void setPenalty(int penalty) {
            this.penalty = penalty;
        }

        public void setPickCompanyId(Object pickCompanyId) {
            this.pickCompanyId = pickCompanyId;
        }

        public void setKindCount(int kindCount) {
            this.kindCount = kindCount;
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

        public Object getPageId() {
            return pageId;
        }

        public int getPageCount() {
            return pageCount;
        }

        public Object getPageDirection() {
            return pageDirection;
        }

        public Object getSerachKey() {
            return serachKey;
        }

        public Object getBigType() {
            return bigType;
        }

        public Object getInviteId() {
            return inviteId;
        }

        public Object getDeptid() {
            return deptid;
        }

        public int getOrderId() {
            return orderId;
        }

        public String getOrderType() {
            return orderType;
        }

        public Object getOrderCategory() {
            return orderCategory;
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

        public Object getProductAddress() {
            return productAddress;
        }

        public String getDestination() {
            return destination;
        }

        public Object getFlowId() {
            return flowId;
        }

        public int getPenalty() {
            return penalty;
        }

        public Object getPickCompanyId() {
            return pickCompanyId;
        }

        public int getKindCount() {
            return kindCount;
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

    public class MerchandiseSalesInfo {
        private int marketSysId;
        private String goodsId;
        private int price;
        private Object buySum;
        private long creatBuytime;
        private long endBuytime;
        private int sum;
        private int alreadySum;
        private String goodsNmae;

        public void setMarketSysId(int marketSysId) {
            this.marketSysId = marketSysId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public void setBuySum(Object buySum) {
            this.buySum = buySum;
        }

        public void setCreatBuytime(long creatBuytime) {
            this.creatBuytime = creatBuytime;
        }

        public void setEndBuytime(long endBuytime) {
            this.endBuytime = endBuytime;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public void setAlreadySum(int alreadySum) {
            this.alreadySum = alreadySum;
        }

        public void setGoodsNmae(String goodsNmae) {
            this.goodsNmae = goodsNmae;
        }

        public int getMarketSysId() {
            return marketSysId;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public int getPrice() {
            return price;
        }

        public Object getBuySum() {
            return buySum;
        }

        public long getCreatBuytime() {
            return creatBuytime;
        }

        public long getEndBuytime() {
            return endBuytime;
        }

        public int getSum() {
            return sum;
        }

        public int getAlreadySum() {
            return alreadySum;
        }

        public String getGoodsNmae() {
            return goodsNmae;
        }
    }
}
