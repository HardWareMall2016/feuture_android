package com.zhan.ironfuture.ui.fragment.managementDepartment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.ui.activity.BaseActivity;
import com.zhan.framework.ui.fragment.APullToRefreshListFragment;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.FinancialManagementContent;
import com.zhan.ironfuture.beans.FinancialManagementRequestBean;
import com.zhan.ironfuture.beans.FinancialManagementResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.Tools;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 财务管理
 */
public class FinancialManagementFragment extends APullToRefreshListFragment<FinancialManagementFragment.TradeInfo> {

    private final static int REQUEST_CODE=100;

    @ViewInject(id = R.id.head)
    LinearLayout mViewHeader;

    @ViewInject(id = R.id.total_assets)
    TextView mViewTotalAssets;

    @ViewInject(id = R.id.btn_transfer, click = "OnClick")
    Button mViewTransfer;

    //size
    private int mStatusBarHeight=0;
    private int mActionbarHeight=0;

    private long mAllmoney ;



    //Tools
    private DecimalFormat mMoneyFormat = new DecimalFormat("###,###,##0.00");

    @Override
    public void onPrepareSetContentView(BaseActivity activity) {
        activity.setOverlay(true);
        activity.showActionbarUnderline(false);
        mStatusBarHeight=activity.getStatusBarHeight();
        mActionbarHeight=activity.getActionbarHeight();
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_financial_management;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        if(UserInfo.getCurrentUser().isManagementLayer()){
            getActivity().setTitle("公司资产");
            mViewTransfer.setVisibility(View.VISIBLE);
        }else{
            getActivity().setTitle("剩余预算");
            mViewTransfer.setVisibility(View.GONE);
        }

        ActionBarActivity actionBarActivity= (ActionBarActivity) getActivity();
        actionBarActivity.getTitleText().setTextColor(Color.WHITE);
        ColorStateList whiteSelector;
        Resources resources = getActivity().getResources();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            whiteSelector = resources.getColorStateList(R.color.text_color_white_selector, null);
        } else {
            whiteSelector = resources.getColorStateList(R.color.text_color_white_selector);
        }
        ((ActionBarActivity)getActivity()).getBackBtn().setTextColor(whiteSelector);
        //((ActionBarActivity)getActivity()).getBackBtn().setTextColor(Color.GRAY);

        mViewHeader.setPadding(0, mStatusBarHeight + mActionbarHeight, 0, 0);
    }

    @Override
    protected ABaseAdapter.AbstractItemView<TradeInfo> newItemView() {
        return new TradeInfoItemView();
    }

    @Override
    protected void configRefresh(RefreshConfig config) {
        config.minResultSize=IronFutureConstants.DEF_PAGE_SIZE;
    }

    @Override
    protected void requestData(RefreshMode mode) {

        int startPageId;
        if (mode == RefreshMode.refresh || mode == RefreshMode.reset||isContentEmpty()) {
            startPageId = 0;
        }else{
            startPageId=getAdapterItems().get(getAdapterItems().size()-1).id;
        }

        FinancialManagementRequestBean request = new FinancialManagementRequestBean();
        FinancialManagementRequestBean.DataEntity data = new FinancialManagementRequestBean.DataEntity();
        if(UserInfo.getCurrentUser().isManagementLayer()){
            data.setComId(UserInfo.getCurrentUser().getComId());
            data.setState(1);
        }else{
            data.setComId(UserInfo.getCurrentUser().getDeptid());
            data.setState(2);
        }
        data.setPageId(startPageId);
        data.setPageCount(IronFutureConstants.DEF_PAGE_SIZE);
        data.setPageDirection(IronFutureConstants.PAGE_DIRECTION_DOWN);
        data.setSearchKey("");
        request.setData(data);
        startJsonRequest(ApiUrls.GETCOMMONEYLOG, request, new PagingTask<FinancialManagementResponseBean>(mode) {
            @Override
            public FinancialManagementResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content,FinancialManagementResponseBean.class);
            }

            @Override
            public String verifyResponseResult(FinancialManagementResponseBean result) {
                return  Tools.verifyResponseResult(result);
            }

            @Override
            protected List<TradeInfo> parseResult(FinancialManagementResponseBean baseResponseBean) {
                List<TradeInfo> beanList=new LinkedList<>();
                if(baseResponseBean != null && baseResponseBean.getData() != null){
                    mAllmoney = baseResponseBean.getData().getSurplusMoney() ;
                    mViewTotalAssets.setText(mMoneyFormat.format(baseResponseBean.getData().getSurplusMoney()));

                    for(FinancialManagementResponseBean.DataEntity.MoneyLogListEntity item : baseResponseBean.getData().getMoneyLogList()){
                        TradeInfo tradeInfo = new TradeInfo();
                        tradeInfo.setId(item.getId());
                        tradeInfo.setComId(item.getComId());
                        tradeInfo.setHandleType(item.getHandleType());
                        tradeInfo.setAmount(item.getAmount());
                        tradeInfo.setDescriptor(item.getDescriptor());
                        tradeInfo.setInOut(item.getInOut());
                        tradeInfo.setCreateTime(item.getCreateTime());
                        beanList.add(tradeInfo);
                    }
                }
                return beanList;
            }
        });
    }


    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_transfer:
                FinancialManagementContent content = new FinancialManagementContent();
                content.setAsset(mAllmoney);
                TransferFragment.launchForResult(this, REQUEST_CODE,content);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(REQUEST_CODE==requestCode&&resultCode== Activity.RESULT_OK){
            setRefreshing();
        }
    }

    private class TradeInfoItemView extends ABaseAdapter.AbstractItemView<TradeInfo> {
        @ViewInject(id = R.id.type_title)
        TextView mViewTitle;

        @ViewInject(id = R.id.time)
        TextView mViewTime;

        @ViewInject(id = R.id.amount)
        TextView mViewAmount;

        @ViewInject(id = R.id.financial_management_time)
        TextView mTime ;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_financial_management;
        }

        @Override
        public void bindingData(View convertView, TradeInfo data) {

            mTime.setText(Tools.parseTime(data.getCreateTime()));
            mViewTitle.setText(data.getDescriptor());
            //支出
            if(data.getInOut() == 1){
                mViewAmount.setTextColor(0xFFFC4D16);
                mViewAmount.setText("-"+data.getAmount()+"");
            }else{//收入
                mViewAmount.setTextColor(0xFF2DC95B);
                mViewAmount.setText("+"+data.getAmount()+"");
            }
        }
    }

    public class TradeInfo{
        boolean expend=true;
        private int id;
        private int comId;
        private Object handleType;
        private long amount;
        private String descriptor;
        private int inOut;
        private long createTime;

        public void setId(int id) {
            this.id = id;
        }

        public void setComId(int comId) {
            this.comId = comId;
        }

        public void setHandleType(Object handleType) {
            this.handleType = handleType;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public void setDescriptor(String descriptor) {
            this.descriptor = descriptor;
        }

        public void setInOut(int inOut) {
            this.inOut = inOut;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public int getComId() {
            return comId;
        }

        public Object getHandleType() {
            return handleType;
        }

        public long getAmount() {
            return amount;
        }

        public String getDescriptor() {
            return descriptor;
        }

        public int getInOut() {
            return inOut;
        }

        public long getCreateTime() {
            return createTime;
        }
    }

}