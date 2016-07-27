package com.zhan.ironfuture.ui.fragment.managementDepartment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.beans.CompanyMessageCreateMsgRequestBean;
import com.zhan.ironfuture.beans.CompanyMessageCreateMsgResponseBean;
import com.zhan.ironfuture.beans.CompanyMessageGetDetailRequestBean;
import com.zhan.ironfuture.beans.CompanyMessageGetDetailResponseBean;
import com.zhan.ironfuture.beans.CompanyNewsContent;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.MyListView;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;

/**
 * 作者：伍岳 on 2016/5/12 10:28
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
public class CompanyMessageDetailFragment extends ABaseFragment {
    private final static String ARG_KEY = "companymessage";

    @ViewInject(id = R.id.message_detail_receiver)
    TextView mReceiverName;
    @ViewInject(id = R.id.message_detail_title)
    TextView mReceiverTitle;
    @ViewInject(id = R.id.message_detail_content)
    TextView mDetailContent;
    @ViewInject(id = R.id.message_detail_listview)
    MyListView mListView;
    @ViewInject(id = R.id.company_message_detail_reply_content)
    EditText mReplyContent;
    @ViewInject(id = R.id.company_message_detail_send,click = "OnClick")
    TextView mSend ;

    //data
    private ArrayList<ReplyMsgList> mReplyMsgList = new ArrayList<>();

    private CompanyNewsContent content;
    private String mTitle;
    private String msgContent;
    private int mSender;
    private String replyContent ;

    private CompanyMessageDetailAdapter mCompanyMessageDetailAdapter;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_company_message_detail;
    }

    public static void launch(FragmentActivity activity, CompanyNewsContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launch(activity, CompanyMessageDetailFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = savedInstanceState == null ? (CompanyNewsContent) getArguments().getSerializable(ARG_KEY) : (CompanyNewsContent) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, content);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("消息详情");
        mTitle = content.getTitle();
        msgContent = content.getMsgContent();
        mSender = content.getSender();

        mCompanyMessageDetailAdapter = new CompanyMessageDetailAdapter(mReplyMsgList,getActivity());
        mListView.setAdapter(mCompanyMessageDetailAdapter);
    }

    void OnClick(View view){
        switch (view.getId()){
            case R.id.company_message_detail_send:
                if (!checkInput()) {
                    return;
                }

                replayMessage();
                break;
        }
    }

    private void replayMessage() {
        if(isRequestProcessing(ApiUrls.CREATEBBS)){
            return;
        }
        CompanyMessageCreateMsgRequestBean requestBean = new CompanyMessageCreateMsgRequestBean();
        CompanyMessageCreateMsgRequestBean.DataEntity dataEntity = new CompanyMessageCreateMsgRequestBean.DataEntity();
        dataEntity.setMsgId(content.getMsgId());
        dataEntity.setTitle(mTitle);
        dataEntity.setMsgContent(replyContent);
        dataEntity.setReceiver(content.getSender());

        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.CREATEBBS, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        CompanyMessageCreateMsgResponseBean responseBean= Tools.parseJsonTostError(result, CompanyMessageCreateMsgResponseBean.class);
                        if(responseBean!=null){
                            mReplyContent.setText("");
                            Tools.hideSoftInputFromWindow(mReplyContent);
                            requestData();
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }

    private boolean checkInput() {
        replyContent = mReplyContent.getText().toString();
        if (TextUtils.isEmpty(replyContent)) {
            ToastUtils.toast("请输入回复内容");
            return false;
        }
        return true;
    }


    @Override
    public void requestData() {
        CompanyMessageGetDetailRequestBean requestBean = new CompanyMessageGetDetailRequestBean();
        CompanyMessageGetDetailRequestBean.DataEntity dataEntity = new CompanyMessageGetDetailRequestBean.DataEntity();
        dataEntity.setMsgId(content.getMsgId());
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.GETMSGDETAIL, requestBean, new BaseHttpRequestTask<CompanyMessageGetDetailResponseBean>() {

            @Override
            public CompanyMessageGetDetailResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, CompanyMessageGetDetailResponseBean.class);
            }

            @Override
            public String verifyResponseResult(CompanyMessageGetDetailResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(CompanyMessageGetDetailResponseBean bean) {
                super.onSuccess(bean);
                mReceiverName.setText(bean.getData().getAccountName());
                mReceiverTitle.setText(bean.getData().getTitle());
                mDetailContent.setText(bean.getData().getMsgContent());
                mReplyContent.setHint("回复:"+bean.getData().getAccountName());

                mReplyMsgList.clear();
                for (CompanyMessageGetDetailResponseBean.DataEntity.ReplyMsgListEntity item : bean.getData().getReplyMsgList()) {
                    ReplyMsgList replyMsgList = new ReplyMsgList();
                    replyMsgList.setMsgId(item.getMsgId());
                    replyMsgList.setpMsgId(item.getPMsgId());
                    replyMsgList.setMsgType(item.getMsgType());
                    replyMsgList.setMsgContent(item.getMsgContent());
                    replyMsgList.setMsgState(item.getMsgState());
                    replyMsgList.setSendUserId(item.getSendUserId());
                    replyMsgList.setReceiver(item.getReceiver());
                    replyMsgList.setCreateTime(item.getCreateTime());
                    replyMsgList.setReadTime(item.getReadTime());
                    replyMsgList.setReceiveFlag(item.getReceiveFlag());
                    replyMsgList.setTitle(item.getTitle());
                    replyMsgList.setBusinessID(item.getBusinessID());
                    replyMsgList.setAccountName(item.getAccountName());
                    replyMsgList.setMsgLogo(item.getMsgLogo());
                    replyMsgList.setPersonLogo(item.getPersonLogo());
                    mReplyMsgList.add(replyMsgList);
                }
                mCompanyMessageDetailAdapter.notifyDataSetChanged();
            }
        });

    }

    private class CompanyMessageDetailAdapter extends ABaseAdapter<ReplyMsgList> {

        public CompanyMessageDetailAdapter(ArrayList<ReplyMsgList> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<ReplyMsgList> newItemView() {
            return companyMessageDetailReplyMsgItem;
        }
    }

    public ABaseAdapter.AbstractItemView<ReplyMsgList> companyMessageDetailReplyMsgItem = new ABaseAdapter.AbstractItemView<ReplyMsgList>() {
        @ViewInject(id = R.id.reply_msg_user)
        ImageView mReplyUserImg ;
        @ViewInject(id = R.id.reply_msg_username)
        TextView userName ;
        @ViewInject(id = R.id.reply_msg_time)
        TextView mTime ;
        @ViewInject(id = R.id.reply_msg_intro)
        TextView mIntro ;
        @ViewInject(id = R.id.reply_msg_content)
        TextView mContent ;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_company_message_reply_msg;
        }

        @Override
        public void bindingData(View convertView, ReplyMsgList data) {
            userName.setText(data.getAccountName());
            mTime.setText(Tools.parseToTimeOrDateStr(data.getCreateTime()));
            mContent.setText(data.getMsgContent());
            ImageLoader.getInstance().displayImage(data.getPersonLogo(),mReplyUserImg, Tools.buildDisplayImageOptionsForAvatar());
        }
    };


    private class ReplyMsgList {
        private int msgId;
        private int pMsgId;
        private String msgType;
        private String msgContent;
        private int msgState;
        private int sendUserId;
        private int receiver;
        private long createTime;
        private long readTime;
        private Object receiveFlag;
        private Object title;
        private Object businessID;
        private String accountName;
        private String msgLogo;
        private String personLogo ;

        public int getMsgId() {
            return msgId;
        }

        public void setMsgId(int msgId) {
            this.msgId = msgId;
        }

        public int getpMsgId() {
            return pMsgId;
        }

        public void setpMsgId(int pMsgId) {
            this.pMsgId = pMsgId;
        }

        public String getMsgType() {
            return msgType;
        }

        public void setMsgType(String msgType) {
            this.msgType = msgType;
        }

        public String getMsgContent() {
            return msgContent;
        }

        public void setMsgContent(String msgContent) {
            this.msgContent = msgContent;
        }

        public int getMsgState() {
            return msgState;
        }

        public void setMsgState(int msgState) {
            this.msgState = msgState;
        }

        public int getSendUserId() {
            return sendUserId;
        }

        public void setSendUserId(int sendUserId) {
            this.sendUserId = sendUserId;
        }

        public int getReceiver() {
            return receiver;
        }

        public void setReceiver(int receiver) {
            this.receiver = receiver;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getReadTime() {
            return readTime;
        }

        public void setReadTime(long readTime) {
            this.readTime = readTime;
        }

        public Object getReceiveFlag() {
            return receiveFlag;
        }

        public void setReceiveFlag(Object receiveFlag) {
            this.receiveFlag = receiveFlag;
        }

        public Object getTitle() {
            return title;
        }

        public void setTitle(Object title) {
            this.title = title;
        }

        public Object getBusinessID() {
            return businessID;
        }

        public void setBusinessID(Object businessID) {
            this.businessID = businessID;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getMsgLogo() {
            return msgLogo;
        }

        public void setMsgLogo(String msgLogo) {
            this.msgLogo = msgLogo;
        }

        public String getPersonLogo() {
            return personLogo;
        }

        public void setPersonLogo(String personLogo) {
            this.personLogo = personLogo;
        }
    }

}
