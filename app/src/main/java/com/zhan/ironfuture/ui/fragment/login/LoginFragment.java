package com.zhan.ironfuture.ui.fragment.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhan.framework.component.container.SingleTopFragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.BaseActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.ui.widget.MaterialRippleRelativeLayout;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.LoginRequestBean;
import com.zhan.ironfuture.beans.LoginsRequestBean;
import com.zhan.ironfuture.beans.LoginsResponseBean;
import com.zhan.ironfuture.beans.MessageCodeRequestBean;
import com.zhan.ironfuture.beans.MessageCodeResponseBean;
import com.zhan.ironfuture.db.beans.ExampleCacheBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.activity.MainActivity;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：伍岳 on 2016/4/21 13:10
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
public class LoginFragment extends ABaseFragment {

    @ViewInject(id = R.id.register,click = "OnClick")
    MaterialRippleRelativeLayout mRegister ;
    @ViewInject(id = R.id.btn_login, click = "OnClick")
    Button mBtnLogin;
    @ViewInject(id = R.id.login_code,click = "OnClick")
    Button mLoginCode ;
    @ViewInject(id = R.id.login_phone_number)
    EditText mPhoneNum ;
    @ViewInject(id = R.id.login_message_code)
    EditText mMessageCode ;

    private String phoneNum ;
    private String messageCode ;

    //Tools
    private CountDownTimer mTimer;

    public static void launch(Activity from) {
        SingleTopFragmentContainerActivity.launch(from, LoginFragment.class, null);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);

        mPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count >= 1) {
                    Drawable right = getResources().getDrawable(R.drawable.close);
                    right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
                    mPhoneNum.setCompoundDrawables(null, null, right, null);
                    mPhoneNum.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                Drawable drawable = ((EditText) v).getCompoundDrawables()[2];
                                if (drawable == null) {
                                    return false;
                                }
                                if (event.getX() > v.getWidth() - v.getPaddingRight()
                                        - drawable.getIntrinsicWidth()) {
                                    ((EditText) v).setText("");
                                }
                            }
                            return false;
                        }
                    });
                } else {
                    mPhoneNum.setCompoundDrawables(null, null, null, null);
                    mPhoneNum.setOnTouchListener(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onPrepareSetContentView(BaseActivity activity) {
        super.onPrepareSetContentView(activity);
        activity.showBackIcon(false);
        activity.setTitle("登录");
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_login;
    }

    @Override
    public void onDestroyView() {
        cancelCountDownTimer();
        super.onDestroyView();
    }

    void OnClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                if (!checkPhoneNumInput()) {
                    return;
                }
                if(!checkCodeInput()){
                    return;
                }
                login();
                break;
            case R.id.register:
                RegisterFragment.launch(getActivity());
                break;
            case R.id.login_code:
                if (!checkPhoneNumInput()) {
                    return;
                }
                startCountDownTimer();
                break;
        }

    }

    private boolean checkCodeInput() {
        messageCode = mMessageCode.getText().toString();
        if (TextUtils.isEmpty(messageCode)) {
            ToastUtils.toast("请输入验证码");
            return false;
        }
        return true;
    }
    private boolean checkPhoneNumInput() {
        phoneNum = mPhoneNum.getText().toString();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtils.toast("请输入手机号");
            return false;
        }
        if (!Tools.checkMobilePhoneNumber(phoneNum)) {
            ToastUtils.toast("请输入正确的手机号");
            return false;
        }
        return true;
    }

    //登录
    private void login(){
        if(isRequestProcessing(ApiUrls.TEACHER_LOGIN)){
            return;
        }
        LoginsRequestBean requestBean = new LoginsRequestBean();
        LoginsRequestBean.DataEntity dataEntity = new LoginsRequestBean.DataEntity();
        dataEntity.setPhone(phoneNum);
        dataEntity.setPhoneChkNo(messageCode);
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.TEACHER_LOGIN, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        LoginsResponseBean responseBean= Tools.parseJsonTostError(result, LoginsResponseBean.class);
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
    }

    //获取短信验证码
    private void getCodeRequest(){
        if(isRequestProcessing(ApiUrls.GET_CHECET_CODE)){
            return;
        }
        MessageCodeRequestBean request = new MessageCodeRequestBean();
        MessageCodeRequestBean.DataEntity dataEntity = new MessageCodeRequestBean.DataEntity();
        dataEntity.setPhone(phoneNum);
        request.setData(dataEntity);
        startJsonRequest(ApiUrls.GET_CHECET_CODE, request, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                cancelCountDownTimer();
                switch (resultCode) {
                    case success:
                        MessageCodeResponseBean responseBean = Tools.parseJsonTostError(result, MessageCodeResponseBean.class);
                        if (responseBean != null) {
                            //mMessageCode.setText(responseBean.getMessage());
                            messageCode = responseBean.getMessage();
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }

    private void startCountDownTimer(){
        if(mTimer!=null){
            cancelCountDownTimer();
        }

        mLoginCode.setEnabled(false);
        mLoginCode.setTextColor(Color.rgb(102, 102, 102));
        mTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String formatStr = "%d秒后重试";
                mLoginCode.setText(String.format(formatStr, millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                mLoginCode.setEnabled(true);
                mLoginCode.setText(R.string.get_sms_code);
                mLoginCode.setTextColor(0xff9390A5);
            }
        };
        mTimer.start();

        getCodeRequest();
    }


    private void cancelCountDownTimer(){
        mLoginCode.setEnabled(true);
        mLoginCode.setText(R.string.get_sms_code);
        mLoginCode.setTextColor(0xff9390A5);
        if(mTimer!=null){
            mTimer.cancel();
        }
        mTimer=null;
    }


    private void loginDemo() {
        LoginRequestBean request = new LoginRequestBean();
        request.setToken("");
        LoginRequestBean.DataEntity data = new LoginRequestBean.DataEntity();
        data.setLoginName("jesse.huang@zhan.com");
        data.setPassWord(Tools.md5("1111110").substring(0, 20).toUpperCase());
        request.setData(data);
        startJsonRequest(ApiUrls.TEACHER_LOGIN, request, new HttpRequestHandler(this) {

            @Override
            public boolean isCacheData() {
                return true;
            }

            @Override
            public void onBeforeRequest() {
                showRotateProgressDialog("登陆...", true);
                List<ExampleCacheBean> cacheBeanList = getCacheData(ExampleCacheBean.class);
                if (cacheBeanList != null && cacheBeanList.size() > 0) {
                    Log.i("wuyue", "Get Cache success size = " + cacheBeanList.size());
                    for (ExampleCacheBean bean : cacheBeanList) {
                        Log.i("wuyue", "getName = " + bean.getName() + " , time = " + bean.getTime());
                    }
                } else {
                    Log.i("wuyue", "No Cache");
                }
            }

            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                super.onRequestFinished(resultCode, result);
                closeRotateProgressDialog();
            }

            @Override
            public void onRequestSucceeded(String content) {
                super.onRequestSucceeded(content);

                //缓存
                ExampleCacheBean bean = new ExampleCacheBean();
                bean.setName("KEKE");
                bean.setTime(System.currentTimeMillis());

                List<ExampleCacheBean> beans = new ArrayList<ExampleCacheBean>();
                beans.add(bean);

                bean = new ExampleCacheBean();
                bean.setName("WUYUE");
                bean.setTime(System.currentTimeMillis() + 100);
                beans.add(bean);

                bean = new ExampleCacheBean();
                bean.setName("MINGKUI");
                bean.setTime(System.currentTimeMillis() + 200);
                beans.add(bean);

                putCacheData(beans, ExampleCacheBean.class);


                //登陆
                UserInfo user = new UserInfo();
                user.setUserID(101);
                user.setIsLogin(true);
                UserInfo.saveLoginUserInfo(user);

                Intent homePageIntent = new Intent(getActivity(), MainActivity.class);
                homePageIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homePageIntent);

                getActivity().finish();
            }
        });
    }

}
