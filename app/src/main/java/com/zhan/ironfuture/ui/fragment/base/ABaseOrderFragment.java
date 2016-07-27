package com.zhan.ironfuture.ui.fragment.base;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.APullToRefreshListFragment;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.beans.QueryOrderRequestBean;
import com.zhan.ironfuture.beans.QueryOrderResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.fragment.ordersCenter.OrderDetailFragment;
import com.zhan.ironfuture.utils.Tools;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 订单列表基类
 */
public abstract class ABaseOrderFragment extends APullToRefreshListFragment<ABaseOrderFragment.OrderInfo> {

    @Override
    protected int inflateContentView() {
        return R.layout.frag_orderlist_layout;
    }

    @Override
    protected ABaseAdapter.AbstractItemView<OrderInfo> newItemView() {
        return new OrderItemView();
    }


    public abstract QueryOrderRequestBean getRequestBean();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderInfo orderInfo=getAdapterItems().get((int)id);
        OrderDetailFragment.launch(getActivity(), orderInfo.orderId);
    }

    @Override
    protected void configRefresh(RefreshConfig config) {
        config.minResultSize = IronFutureConstants.DEF_PAGE_SIZE;
    }

    @Override
    protected void requestData(RefreshMode mode) {
        int startPageId;
        if (mode == RefreshMode.refresh || mode == RefreshMode.reset||isContentEmpty()) {
            startPageId = 0;
        }else{
            startPageId=getAdapterItems().get(getAdapterItems().size()-1).orderId;
        }

        QueryOrderRequestBean requestBean=getRequestBean();
        if(requestBean!=null){
            requestBean.getData().setPageCount(IronFutureConstants.DEF_PAGE_SIZE);
            requestBean.getData().setPageDirection(IronFutureConstants.PAGE_DIRECTION_DOWN);
            requestBean.getData().setPageId(startPageId);
        }

        startJsonRequest(ApiUrls.ORDER_BS_QUERY_ORDER, requestBean, new PagingTask<QueryOrderResponseBean>(mode) {
            @Override
            public QueryOrderResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, QueryOrderResponseBean.class);
            }

            @Override
            public String verifyResponseResult(QueryOrderResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected List<OrderInfo> parseResult(QueryOrderResponseBean baseResponseBean) {
                List<OrderInfo> beanList = new LinkedList<>();

                if(baseResponseBean.getData()==null||baseResponseBean.getData().getMarketOrderDtoList()==null){
                    return beanList;
                }

                onSearchFilterResponded(baseResponseBean.getData().getAmountList(), baseResponseBean.getData().getCanTransportList());

                for (QueryOrderResponseBean.DataEntity.MarketOrderDtoListEntity data: baseResponseBean.getData().getMarketOrderDtoList()) {
                    OrderInfo bean = new OrderInfo();
                    bean.totalAmount=data.getTotalAmount();
                    bean.orderId=data.getOrderId();
                    bean.kindCount=data.getKindCount();
                    beanList.add(bean);
                }
                return beanList;
            }
        });
    }

    protected void onSearchFilterResponded(
            List<QueryOrderResponseBean.DataEntity.AmountListEntity> amountList
            , List<QueryOrderResponseBean.DataEntity.CanTransportListEntity> canTransportList
    ) {
    }

    private class OrderItemView extends ABaseAdapter.AbstractItemView<OrderInfo> {
        @ViewInject(idStr = "total")
        TextView mViewTotal;

        @ViewInject(idStr = "order_num")
        TextView mViewOrderNum;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_orders_summary;
        }

        @Override
        public void bindingData(View convertView, final OrderInfo data) {

            mViewOrderNum.setText(String.valueOf(data.orderId));

            //商品总计
            mViewTotal.setText(String.format("商品%d种 总计 ",data.kindCount));
            String totalPrice = data.totalAmount+"";
            SpannableString ss = new SpannableString(totalPrice);
            ss.setSpan(new ForegroundColorSpan(0xff333333), 0, totalPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(18, true), 0, totalPrice.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            mViewTotal.append(ss);
        }
    }

    public class OrderInfo {
        public int orderId;
        public int totalAmount;
        public int kindCount;
    }
}
