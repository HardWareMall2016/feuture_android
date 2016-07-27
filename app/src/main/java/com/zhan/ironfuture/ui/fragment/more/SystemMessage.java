package com.zhan.ironfuture.ui.fragment.more;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.BbsListRequestBean;
import com.zhan.ironfuture.beans.BbsListResponseBean;
import com.zhan.ironfuture.beans.MsgReviewRequestBean;
import com.zhan.ironfuture.beans.MsgReviewResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.fragment.base.ABaseTabFragment;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 系统消息
 */
public class SystemMessage extends ABaseTabFragment<SystemMessage.MessageInfo> {

    private static final int TAB_SYSTEM =0;
    private static final int TAB_REMINDER =1;
    private static final int TAB_LOG =2;

    private static final int TYPE_COUNT=TAB_REMINDER+1;//类型总数

    private int mCurTab= TAB_REMINDER;

    private Dialog mApplyDialog;

    private DisplayImageOptions mDisplayImageOptions;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_system_message;
    }

    @Override
    protected List<TabInfo> generateTabList() {
        List<TabInfo> tabs = new ArrayList<>();

        TabInfo tabItem = new TabInfo();
        tabItem.tabCode = TAB_REMINDER;
        tabItem.name = getString(R.string.system_message_reminder);
        tabs.add(tabItem);

        tabItem = new TabInfo();
        tabItem.tabCode = TAB_SYSTEM;
        tabItem.name = getString(R.string.system_message_system_notice);
        tabs.add(tabItem);

        /*tabItem = new TabInfo();
        tabItem.tabCode = TAB_LOG;
        tabItem.name = getString(R.string.system_message_operation_log);
        tabs.add(tabItem);*/

        return tabs;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle(R.string.system_message);
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_msg_remind)
                .showImageForEmptyUri(R.drawable.icon_msg_remind)
                .showImageOnFail(R.drawable.icon_msg_remind)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }

    @Override
    protected void onTabChanged(int mainTabCode, int subTabCode) {
        mCurTab=mainTabCode;
        setRefreshing();
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return mCurTab;
    }

    @Override
    protected ABaseAdapter.AbstractItemView<MessageInfo> newItemView() {
        return new MessageItemView();
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
        switch (mCurTab){
            case TAB_SYSTEM:
                dataEntity.setType(0);
                break;
            case TAB_REMINDER:
                dataEntity.setType(1);
                break;
            case TAB_LOG:
                dataEntity.setType(2);
                break;
        }

        request.setData(dataEntity);

        startJsonRequest(ApiUrls.BBSLISTS, request, new PagingTask<BbsListResponseBean>(mode) {
            @Override
            public BbsListResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, BbsListResponseBean.class);
            }

            @Override
            public String verifyResponseResult(BbsListResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected List<MessageInfo> parseResult(BbsListResponseBean bbsListResponseBean) {
                List<MessageInfo> beanList = new LinkedList<>();
                if(bbsListResponseBean != null && bbsListResponseBean.getData() != null){
                    beanList.clear();
                    for (BbsListResponseBean.DataEntity item : bbsListResponseBean.getData()) {
                        MessageInfo companyNewsInfo = new MessageInfo();
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
                        companyNewsInfo.setMsgLogo(item.getMsgLogo());
                        beanList.add(companyNewsInfo);
                    }
                }
                return beanList;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MessageInfo data = getAdapterItems().get((int) id);
        switch (mCurTab){
            case TAB_SYSTEM:
                data.isFolder = !data.isFolder;
                notifyDataSetChanged();
                break;
            case TAB_REMINDER:
                if(!checkCanApproval(data)){
                    return;
                }
                showApplyDialog("消息提示", data.getMsgContent(), data);
                break;
            case TAB_LOG:
                data.isFolder = !data.isFolder;
                notifyDataSetChanged();
                break;
        }
    }

    private class MessageItemView extends ABaseAdapter.AbstractItemView<MessageInfo> {
        //common
        @ViewInject(idStr = "type_title")
        TextView mViewTitle;

        @ViewInject(idStr = "summary")
        TextView mViewSummary;

        @ViewInject(idStr = "time")
        TextView mViewTime;

        @ViewInject(idStr = "result")
        TextView mViewResult;

        @ViewInject(idStr = "icon")
        ImageView mViewIcon;

        @ViewInject(idStr = "arrow")
        ImageView mViewArrow;

        @Override
        public int inflateViewId() {
            int layoutResId = 0;
            switch (getItemViewType()){
                case TAB_SYSTEM:
                    layoutResId=R.layout.list_item_system_message_notice;
                    break;
                case TAB_REMINDER:
                    layoutResId=R.layout.list_item_system_message_reminder;
                    break;
                case TAB_LOG:
                    layoutResId=R.layout.list_item_system_message_log;
                    break;
            }
            return layoutResId;
        }

        @Override
        public void bindingView(View convertView) {
            super.bindingView(convertView);
            switch (getItemViewType()){
                case TAB_SYSTEM:
                    mViewArrow.setVisibility(View.VISIBLE);
                    break;
                case TAB_REMINDER:
                    mViewSummary.setSingleLine(false);
                    mViewArrow.setVisibility(View.GONE);
                    break;
                case TAB_LOG:
                    mViewArrow.setVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override
        public void bindingData(View convertView, final MessageInfo data) {
            mViewTitle.setText(data.getTitle());
            switch (getItemViewType()){
                case TAB_SYSTEM:
                    ImageLoader.getInstance().displayImage(data.getMsgLogo(),mViewIcon,mDisplayImageOptions);
                    if(data.isFolder){
                        mViewArrow.setImageResource(R.drawable.arrow_down_small);
                        mViewSummary.setSingleLine(true);
                    }else{
                        mViewArrow.setImageResource(R.drawable.arrow_up_small);
                        mViewSummary.setSingleLine(false);
                    }
                    break;
                case TAB_REMINDER:
                    ImageLoader.getInstance().displayImage(data.getMsgLogo(), mViewIcon, mDisplayImageOptions);
                    mViewTime.setText(Tools.parseTime(data.getCreateTime()));
                    if(checkCanApproval(data)){
                        mViewResult.setText("待审核");
                        mViewResult.setTextColor(0xffFE501B);
                    }else if(checkCanApprovalFinished(data)){
                        mViewResult.setText("已审核");
                        mViewResult.setTextColor(0xffffc500);
                    }else{
                        mViewResult.setText("");
                    }
                    break;
                case TAB_LOG:
                    if(data.isFolder){
                        mViewArrow.setImageResource(R.drawable.arrow_down_small);
                        mViewSummary.setSingleLine(true);
                    }else{
                        mViewArrow.setImageResource(R.drawable.arrow_up_small);
                        mViewSummary.setSingleLine(false);
                    }
                    break;
            }


            mViewSummary.setText(data.getMsgContent());
        }
    }

    private boolean checkCanApproval(MessageInfo message){
        boolean canApproval=true;
        if("MSG_APPROVAL_JOIN".equals(message.msgType)){

        }else if("MSG_APPROVAL_LEAVE".equals(message.msgType)){

        }else if("MSG_APPROVAL_INVITE".equals(message.msgType)){

        }else if("MSG_APPROVAL_CREATORDER".equals(message.msgType)){

        }else if("MSG_APPROVAL_APPLYORDER".equals(message.msgType)){

        }else if("MSG_APPROVAL_AFFIRMORDER".equals(message.msgType)){

        }else {
            canApproval=false;
        }
        return canApproval;
    }

    private boolean checkCanApprovalFinished(MessageInfo message){
        boolean canApproval=true;
        if("MSG_APPROVAL_JOIN_FINISH".equals(message.msgType)){

        }else if("MSG_APPROVAL_LEAVE_FINISH".equals(message.msgType)){

        }else if("MSG_APPROVAL_INVITE_FINISH".equals(message.msgType)){

        }else if("MSG_APPROVAL_CREATORDER_FINISH".equals(message.msgType)){

        }else if("MSG_APPROVAL_APPLYORDER_FINISH".equals(message.msgType)){

        }else if("MSG_APPROVAL_AFFIRMORDER_FINISH".equals(message.msgType)){

        }else {
            canApproval=false;
        }
        return canApproval;
    }

    private void showApplyDialog(String title,String summary,MessageInfo message){
        dismissApplyDialog();

        mApplyDialog=Tools.createDialog(getActivity(), R.layout.dialog_cancel_or_confirm);

        TextView titleView= (TextView)mApplyDialog.findViewById(R.id.title);
        titleView.setText(title);
        TextView summaryView= (TextView)mApplyDialog.findViewById(R.id.summary);
        summaryView.setText(summary);

        TextView cancelBtn= (TextView)mApplyDialog.findViewById(R.id.cancel);
        cancelBtn.setText("拒绝");
        cancelBtn.setTag(message);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageInfo message=(MessageInfo)v.getTag();
                approvalRequest(message,false);
            }
        });

        TextView confirmBtn= (TextView)mApplyDialog.findViewById(R.id.confirm);
        confirmBtn.setText("同意");
        confirmBtn.setTag(message);
        confirmBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MessageInfo message=(MessageInfo)v.getTag();
                approvalRequest(message,true);
            }
        });

        mApplyDialog.show();
    }

    private void dismissApplyDialog(){
        if(mApplyDialog!=null&&mApplyDialog.isShowing()){
            mApplyDialog.dismiss();
            mApplyDialog=null;
        }
    }

    private void approvalRequest(MessageInfo message,boolean approve){
        if(isRequestProcessing(ApiUrls.MSG_REVIEW)){
            return;
        }
        MsgReviewRequestBean requestBean=new MsgReviewRequestBean();
        MsgReviewRequestBean.DataEntity data=new MsgReviewRequestBean.DataEntity();
        data.setMsgId(message.getMsgId());
        data.setResultCode(approve ? 1 : 2);
        requestBean.setData(data);
        startJsonRequest(ApiUrls.MSG_REVIEW, requestBean, new ApprovalHttpRequestHandler(this, message));
    }

    private class ApprovalHttpRequestHandler extends HttpRequestHandler{
        private MessageInfo mMessage;

        public ApprovalHttpRequestHandler(Fragment fragment,MessageInfo message) {
            super(fragment);
            mMessage=message;
        }

        @Override
        public void onRequestFinished(ResultCode resultCode, String result) {
            switch (resultCode){
                case success:
                    MsgReviewResponseBean responseBean=Tools.parseJsonTostError(result,MsgReviewResponseBean.class);
                    if(responseBean!=null){
                        dismissApplyDialog();
                        mMessage.setMsgType(responseBean.getData().getMsgType());
                        ToastUtils.toast("处理成功！");
                        notifyDataSetChanged();
                    }
                    break;
                default:
                    ToastUtils.toast(result);
                    break;
            }
        }
    }

    public class MessageInfo {
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
        private String msgLogo;

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

        public String getMsgLogo() {
            return msgLogo;
        }

        public void setMsgLogo(String msgLogo) {
            this.msgLogo = msgLogo;
        }
    }
}