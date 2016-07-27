package com.zhan.ironfuture.ui.fragment.error;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.ironfuture.R;

/**
 * Created by WuYue on 2016/4/25.
 * 错误页面
 */
public class ErrorFragment extends ABaseFragment {

    private static String ERROR_MSG_KET="error_msg";

    @ViewInject(id = R.id.content)
    private TextView mFragContent;


    private String mErrorMsg="";


    public static ErrorFragment getInstance(String errorMsg){
        ErrorFragment errorFragment=new ErrorFragment();
        Bundle bundle=new Bundle();
        bundle.putString(ERROR_MSG_KET,errorMsg);
        errorFragment.setArguments(bundle);
        return errorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorMsg=getArguments().getString(ERROR_MSG_KET);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_temp;
    }
    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("出错页面");
        mFragContent.setText(mErrorMsg);
    }
}

