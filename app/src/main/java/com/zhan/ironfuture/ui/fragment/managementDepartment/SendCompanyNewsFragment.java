package com.zhan.ironfuture.ui.fragment.managementDepartment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.beans.CompanyMessageCreateMsgResponseBean;
import com.zhan.ironfuture.beans.SendCompanyMessageRequestBean;
import com.zhan.ironfuture.beans.SendCompanyNewsContent;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.Tools;

/**
 * 作者：keke on 2016/6/21 16:15
 * //
 * //         .............................................
 * //                  美女坐镇                  BUG辟易
 * //         .............................................
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 * //
 */
public class SendCompanyNewsFragment extends ABaseFragment{
    private final static String ARG_KEY = "sendcompanynems";

    @ViewInject(id = R.id.send_message_detail_receiver)
    TextView mReceiver ;
    @ViewInject(id = R.id.et_send_message)
    EditText mSendMessage ;
    @ViewInject(id = R.id.et_send_message_detail_title)
    EditText mSengMsgTitle ;

    private SendCompanyNewsContent mContent ;
    private String mCompanyId ;
    private String mSendContent ;
    private String mSengTitle ;
    @Override
    protected int inflateContentView() {
        return R.layout.frag_send_company_news;
    }

    public static void launchForResult(ABaseFragment from, int requestCode, SendCompanyNewsContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launchForResult(from, SendCompanyNewsFragment.class, args,requestCode);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("发送消息");
        mReceiver.setText(mContent.getCompanyName());
        mCompanyId = mContent.getProductId() ;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = savedInstanceState == null ? (SendCompanyNewsContent) getArguments().getSerializable(ARG_KEY)
                : (SendCompanyNewsContent) savedInstanceState.getSerializable(ARG_KEY);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mContent);
    }

    @Override
    public void onPrepareActionbarMenu(TextView menu, Activity activity) {
        super.onPrepareActionbarMenu(menu, activity);
        menu.setText("发送");
    }

    @Override
    public void onActionBarMenuClick() {
        if (isRequestProcessing(ApiUrls.CREATEBBS)) {
            return;
        }
        if (!checkInput()) {
            return;
        }

        replayMessage();
    }

    private void replayMessage() {
        if(isRequestProcessing(ApiUrls.CREATEBBS)){
            return;
        }
        SendCompanyMessageRequestBean requestBean = new SendCompanyMessageRequestBean();
        SendCompanyMessageRequestBean.DataEntity dataEntity = new SendCompanyMessageRequestBean.DataEntity();
        dataEntity.setMsgContent(mSendContent);
        dataEntity.setReceiver(mCompanyId);
        dataEntity.setTitle(mSengTitle);
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.CREATEBBS, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        CompanyMessageCreateMsgResponseBean responseBean = Tools.parseJsonTostError(result, CompanyMessageCreateMsgResponseBean.class);
                        if (responseBean != null) {
                            ToastUtils.toast(responseBean.getMessage());
                            getActivity().setResult(Activity.RESULT_OK);//让上个页面刷新
                            getActivity().finish();
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }

    private boolean checkInput() {
        mSendContent = mSendMessage.getText().toString();
        mSengTitle = mSengMsgTitle.getText().toString() ;

        if (TextUtils.isEmpty(mSendContent)) {
            ToastUtils.toast("请输入信息内容");
            return false;
        }

        if (TextUtils.isEmpty(mSengTitle)) {
            ToastUtils.toast("请输入主题");
            return false;
        }
        return true;
    }

}
