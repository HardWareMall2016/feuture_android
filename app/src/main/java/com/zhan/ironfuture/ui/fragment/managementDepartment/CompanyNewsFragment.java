package com.zhan.ironfuture.ui.fragment.managementDepartment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.ui.fragment.APullToRefreshListFragment;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.beans.BbsListRequestBean;
import com.zhan.ironfuture.beans.BbsListResponseBean;
import com.zhan.ironfuture.beans.CompanyNewsContent;
import com.zhan.ironfuture.beans.SendCompanyNewsContent;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.fragment.storageProcurementDepartment.StoreTradeOrderCompanyFragment;
import com.zhan.ironfuture.ui.widget.RedDotView;
import com.zhan.ironfuture.utils.Tools;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 公司消息
 */
public class CompanyNewsFragment extends APullToRefreshListFragment<CompanyNewsFragment.CompanyNewsInfo> {
    private final static int REQUEST_CODE_PURCHASE_ORDER = 102;
    private final static int REQUEST_CODE_WRITE_LETTER = 103;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_company_news;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("公司消息");
        ActionBarActivity actionBarActivity= (ActionBarActivity) getActivity();
        TextView rightMenu=actionBarActivity.getActionBarRightMenu();
        rightMenu.setText("写信");
        rightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreTradeOrderCompanyFragment.launcher(CompanyNewsFragment.this, REQUEST_CODE_PURCHASE_ORDER);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Tools.isFastClick()) {
            return ;
        }
        getAdapterItems().get((int) id).setMsgState(1);//设置为已读
        getAdapter().notifyDataSetChanged();

        CompanyNewsContent content = new CompanyNewsContent();
        content.setTitle(getAdapterItems().get((int) id).title);
        content.setMsgContent(getAdapterItems().get((int) id).msgContent);
        content.setMsgId(getAdapterItems().get((int) id).msgId);
        content.setSender(getAdapterItems().get((int) id).sendUserId);
        CompanyMessageDetailFragment.launch(getActivity(),content);
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
            startPageId=getAdapterItems().get(getAdapterItems().size()-1).getMsgId();
        }

        BbsListRequestBean request = new BbsListRequestBean();
        BbsListRequestBean.DataEntity dataEntity = new BbsListRequestBean.DataEntity();
        dataEntity.setPageId(startPageId);
        dataEntity.setPageCount(IronFutureConstants.DEF_PAGE_SIZE);
        dataEntity.setPageDirection(IronFutureConstants.PAGE_DIRECTION_DOWN);
        dataEntity.setType(3);
        request.setData(dataEntity);

        startJsonRequest(ApiUrls.BBSLISTS, request, new PagingTask<BbsListResponseBean>(mode) {

            /*@Override
            public boolean isCacheData() {
                return true;
            }

            //只有带下拉列表，才需要重载该方法
            @Override
            public boolean useCacheData() {
                return mode!=RefreshMode.refresh;
            }*/

            @Override
            public BbsListResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, BbsListResponseBean.class);
            }
            @Override
            public String verifyResponseResult(BbsListResponseBean result) {
                return  Tools.verifyResponseResult(result);
            }

            @Override
            protected List<CompanyNewsInfo> parseResult(BbsListResponseBean bbsListResponseBean) {
                List<CompanyNewsInfo> beanList = new LinkedList<>();
                if(bbsListResponseBean != null && bbsListResponseBean.getData() != null){
                    beanList.clear();
                    for (BbsListResponseBean.DataEntity item : bbsListResponseBean.getData()) {
                        CompanyNewsInfo companyNewsInfo = new CompanyNewsInfo();
                        companyNewsInfo.setMsgId(item.getMsgId());
                        companyNewsInfo.setMsgType(item.getMsgType());
                        companyNewsInfo.setMsgContent(item.getMsgContent());
                        companyNewsInfo.setMsgState(item.getMsgState());
                        companyNewsInfo.setSendUserId(item.getSendUserId());
                        companyNewsInfo.setReceiver(item.getReceiver());
                        companyNewsInfo.setCreateTime(item.getCreateTime());
                        companyNewsInfo.setReadTime(item.getReadTime());
                        companyNewsInfo.setReceiveFlag(item.getReceiveFlag());
                        companyNewsInfo.setTitle(item.getTitle());
                        companyNewsInfo.setBusinessID(item.getBusinessID());
                        companyNewsInfo.setAccountName(item.getAccountName());

                        beanList.add(companyNewsInfo);
                    }
                }
                return beanList;
            }
        });
    }

    @Override
    protected ABaseAdapter.AbstractItemView<CompanyNewsInfo> newItemView() {
        return new CompanyNewsItemView();
    }

    private class CompanyNewsItemView extends ABaseAdapter.AbstractItemView<CompanyNewsInfo> {
        @ViewInject(idStr = "new_content_with_ellipsize")
        TextView mNewsContentWithEllipsize;

        @ViewInject(idStr = "new_content")
        TextView mNewsContent;

        @ViewInject(id = R.id.name)
        TextView mName ;

        @ViewInject(id = R.id.subject)
        TextView mSubject ;

        @ViewInject(id = R.id.time)
        TextView mViewTime ;

        @ViewInject(id = R.id.view_has_read)
        RedDotView mViewHasRead ;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_company_news;
        }

        @Override
        public void bindingData(View convertView, final CompanyNewsInfo data) {

            mName.setText(data.getAccountName());
            mSubject.setText(data.getTitle());
            mNewsContentWithEllipsize.setText(data.getMsgContent());

            mViewTime.setText(Tools.parseTime(data.getCreateTime()));

            mViewHasRead.setVisibility(data.msgState==0?View.VISIBLE:View.INVISIBLE);

            if(data.isFolder){
                mNewsContent.setVisibility(View.GONE);
                mNewsContentWithEllipsize.setVisibility(View.VISIBLE);
            }else{
                mNewsContent.setVisibility(View.VISIBLE);
                mNewsContentWithEllipsize.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PURCHASE_ORDER && resultCode == Activity.RESULT_OK) {
            SendCompanyNewsContent content = new SendCompanyNewsContent();
            content.setProductId(data.getSerializableExtra(StoreTradeOrderCompanyFragment.KEY_PURCHASE_ORDER_COMPANY_ID)+"");
            content.setCompanyName(data.getSerializableExtra(StoreTradeOrderCompanyFragment.KEY_PURCHASE_ORDER_COMPANY) + "");
            SendCompanyNewsFragment.launchForResult(this, REQUEST_CODE_WRITE_LETTER, content);
        }else if (requestCode == REQUEST_CODE_WRITE_LETTER && resultCode == Activity.RESULT_OK) {
            requestData();
        }
    }

    public class CompanyNewsInfo{
        //是否是收起状态
        boolean isFolder=true;

        private int msgId;
        private int pMsgId;
        private String msgType;
        private String msgContent;
        private int msgState;
        private int sendUserId;
        private int receiver;
        private long createTime;
        private long readTime;
        private int receiveFlag;
        private String title;
        private int businessID;
        private String accountName;

        public void setMsgId(int msgId) {
            this.msgId = msgId;
        }

        public void setPMsgId(int pMsgId) {
            this.pMsgId = pMsgId;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public void setMsgContent(String msgContent) {
            this.msgContent = msgContent;
        }

        public void setMsgState(int msgState) {
            this.msgState = msgState;
        }

        public void setSendUserId(int sendUserId) {
            this.sendUserId = sendUserId;
        }

        public void setReceiver(int receiver) {
            this.receiver = receiver;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public void setReadTime(long readTime) {
            this.readTime = readTime;
        }

        public void setReceiveFlag(int receiveFlag) {
            this.receiveFlag = receiveFlag;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setBusinessID(int businessID) {
            this.businessID = businessID;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public int getMsgId() {
            return msgId;
        }

        public int getPMsgId() {
            return pMsgId;
        }

        public String getMsgType() {
            return msgType;
        }

        public String getMsgContent() {
            return msgContent;
        }

        public int getMsgState() {
            return msgState;
        }

        public int getSendUserId() {
            return sendUserId;
        }

        public int getReceiver() {
            return receiver;
        }

        public long getCreateTime() {
            return createTime;
        }

        public long getReadTime() {
            return readTime;
        }

        public int getReceiveFlag() {
            return receiveFlag;
        }

        public String getTitle() {
            return title;
        }

        public int getBusinessID() {
            return businessID;
        }

        public String getAccountName() {
            return accountName;
        }
    }
}