package com.zhan.ironfuture.ui.fragment.login;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.beans.RegisterContent;
import com.zhan.ironfuture.beans.RegisterRequestBean;
import com.zhan.ironfuture.beans.RegisterResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.Tools;

/**
 * Created by Administrator on 2016/5/11.
 */
public class RegisterFragment extends ABaseFragment{

    @ViewInject(id = R.id.register_next, click = "OnClick")
    Button mNext;
    @ViewInject(id = R.id.register_phone_number)
    EditText mPhoneNum ;
    @ViewInject(id = R.id.register_code)
    EditText mCode ;

    private String phonePwd ;
    private String inviteCode ;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_register;
    }

    public static void launch(FragmentActivity activity) {
        FragmentContainerActivity.launch(activity, RegisterFragment.class, null);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("注册");

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


        mCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count >= 1) {
                    Drawable right = getResources().getDrawable(R.drawable.close);
                    right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());
                    mCode.setCompoundDrawables(null, null, right, null);
                    mCode.setOnTouchListener(new View.OnTouchListener() {
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
                    mCode.setCompoundDrawables(null, null, null, null);
                    mCode.setOnTouchListener(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    void OnClick(View view){
        switch (view.getId()){
            case R.id.register_next:
                if (!checkInput()) {
                    return;
                }
                //验证手机号
                if(isRequestProcessing(ApiUrls.CHECK_MOBILE_NO)){
                    return;
                }
                RegisterRequestBean request = new RegisterRequestBean();
                RegisterRequestBean.DataEntity data = new RegisterRequestBean.DataEntity();
                data.setPhone(phonePwd);
                data.setInviteCode(inviteCode);
                request.setData(data);
                startJsonRequest(ApiUrls.CHECK_MOBILE_NO, request, new HttpRequestHandler(this) {
                    @Override
                    public void onRequestFinished(ResultCode resultCode, String result) {
                        switch (resultCode){
                            case success:
                                RegisterResponseBean responseBean= Tools.parseJsonTostError(result, RegisterResponseBean.class);
                                if(responseBean!=null){
                                    RegisterContent content = new RegisterContent();
                                    content.setPhoneNum(phonePwd);
                                    content.setCodeNum(inviteCode);
                                    RegisterNextFragment.lauch(getActivity(), content);
                                    ToastUtils.toast(responseBean.getMessage());
                                }
                                break;
                            default:
                                ToastUtils.toast(result);
                        }
                    }
                });
                break;
        }
    }

    private boolean checkInput() {
        phonePwd = mPhoneNum.getText().toString();
        inviteCode = mCode.getText().toString() ;

        if (TextUtils.isEmpty(phonePwd)) {
            ToastUtils.toast("请输入手机号");
            return false;
        }

        if (!Tools.checkMobilePhoneNumber(phonePwd)) {
            ToastUtils.toast("请输入正确的手机号");
            return false;
        }
        return true;
    }

}
