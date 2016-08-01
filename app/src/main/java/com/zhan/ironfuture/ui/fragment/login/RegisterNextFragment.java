package com.zhan.ironfuture.ui.fragment.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.SingleTopFragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.MessageCodeRequestBean;
import com.zhan.ironfuture.beans.MessageCodeResponseBean;
import com.zhan.ironfuture.beans.RegisterContent;
import com.zhan.ironfuture.beans.RegisterNextRequestBean;
import com.zhan.ironfuture.beans.RegisterNextResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.activity.MainActivity;
import com.zhan.ironfuture.utils.Tools;

/**
 * Created by Administrator on 2016/5/11.
 */
public class RegisterNextFragment extends ABaseFragment{
    private final static String ARG_KEY = "courseId";

    @ViewInject(id = R.id.register_finish,click = "OnClick")
    Button mFinish;
    @ViewInject(id = R.id.register_next_code)
    EditText mNextCode ;
    @ViewInject(id = R.id.get_message,click = "OnClick")
    Button mMessage ;

    private String messageCode ;
    private String phoneChkNum ;
    private RegisterContent registerContent ;
    private CountDownTimer mTimer;


    @Override
    protected int inflateContentView() {
        return R.layout.frag_register_next;
    }

    public static void lauch(FragmentActivity activity, RegisterContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        SingleTopFragmentContainerActivity.launch(activity, RegisterNextFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerContent = savedInstanceState == null ? (RegisterContent) getArguments().getSerializable(ARG_KEY)
                : (RegisterContent) savedInstanceState.getSerializable(ARG_KEY);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, registerContent);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("注册");
    }


    void OnClick(View view){
        switch (view.getId()){
            case R.id.register_finish:
                if (!checkInput()) {
                    return;
                }
                if(isRequestProcessing(ApiUrls.REGISTER)){
                    return;
                }
                RegisterNextRequestBean request2 = new RegisterNextRequestBean();
                RegisterNextRequestBean.DataEntity data = new RegisterNextRequestBean.DataEntity();
                data.setPhone(registerContent.getPhoneNum());
                data.setInviteCode(registerContent.getCodeNum());
                data.setPhoneChkNo(messageCode);
                request2.setData(data);
                startJsonRequest(ApiUrls.REGISTER, request2, new HttpRequestHandler(this) {
                    @Override
                    public void onRequestFinished(ResultCode resultCode, String result) {
                        switch (resultCode){
                            case success:
                                RegisterNextResponseBean responseBean= Tools.parseJsonTostError(result, RegisterNextResponseBean.class);
                                if(responseBean!=null){
                                    ToastUtils.toast(responseBean.getMessage());
                                    UserInfo user = new UserInfo();
                                    user.setToken(responseBean.getData().getToken());
                                    user.setUserID(responseBean.getData().getPersonId());
                                    user.setPostId(responseBean.getData().getJobId());
                                    user.setComId(responseBean.getData().getCompanyId());
                                    user.setDeptid(responseBean.getData().getDepartmentId());
                                    user.setHeadImgUrl(responseBean.getData().getImgUrl());
                                    user.setIsLogin(true);
                                    UserInfo.saveLoginUserInfo(user);

                                    Intent homePageIntent = new Intent(getActivity(), MainActivity.class);
                                    homePageIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(homePageIntent);
                                    getActivity().finish();
                                }
                                break;
                            default:
                                ToastUtils.toast(result);
                        }
                    }
                });

                //LoginFragment.launchForResult(getActivity());
                break;
            case R.id.get_message:
                startCountDownTimer();
                break;
        }
    }

    private void startCountDownTimer() {
        if(mTimer!=null){
            cancelCountDownTimer();
        }

        mMessage.setEnabled(false);
        mMessage.setTextColor(Color.rgb(102, 102, 102));
        mTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String formatStr = "%d秒后重试";
                mMessage.setText(String.format(formatStr, millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                mMessage.setEnabled(true);
                mMessage.setText(R.string.get_sms_code);
                mMessage.setTextColor(0xff9390A5);
            }
        };
        mTimer.start();

        getCodeRequest();
    }

    private void cancelCountDownTimer(){
        mMessage.setEnabled(true);
        mMessage.setText(R.string.get_sms_code);
        mMessage.setTextColor(0xff9390A5);
        if(mTimer!=null){
            mTimer.cancel();
        }
        mTimer=null;
    }

    //获取短信验证码
    private void getCodeRequest(){
        if(isRequestProcessing(ApiUrls.GET_CHECET_CODE)){
            return;
        }
        MessageCodeRequestBean request = new MessageCodeRequestBean();
        MessageCodeRequestBean.DataEntity dataEntity = new MessageCodeRequestBean.DataEntity();
        dataEntity.setPhone(registerContent.getPhoneNum());
        request.setData(dataEntity);
        startJsonRequest(ApiUrls.GET_CHECET_CODE, request, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        MessageCodeResponseBean responseBean= Tools.parseJsonTostError(result, MessageCodeResponseBean.class);
                        if(responseBean!=null){
                            phoneChkNum = mNextCode.getText().toString();
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }
    private boolean checkInput() {
        messageCode = mNextCode.getText().toString() ;
        if (TextUtils.isEmpty(messageCode)) {
            ToastUtils.toast("请输入短信验证码");
            return false;
        }
      /*  if (messageCode.length()!=6) {
            ToastUtils.toast("请输入正确的验证码");
            return false;
        }*/
        return true;
    }

}
