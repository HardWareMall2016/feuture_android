package com.zhan.ironfuture.ui.fragment.managementDepartment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.ui.fragment.APullToRefreshListFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.FinancialManagementContent;
import com.zhan.ironfuture.beans.TransferMoneyRequestBean;
import com.zhan.ironfuture.beans.TransferMoneyResponseBean;
import com.zhan.ironfuture.beans.TransferRequestBean;
import com.zhan.ironfuture.beans.TransferResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.Tools;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/29.
 */
public class TransferFragment extends APullToRefreshListFragment<TransferFragment.CompanyInfo> {
    private final static String ARG_KEY = "transfer";

    @ViewInject(id = R.id.search)
    EditText mSearchTransfer ;

    @ViewInject(id = R.id.search_btn, click = "OnClick")
    View mSearchBtn;

    private String search = "";
    //view
    private EditText etAmount ;
    private EditText transferUse ;

    //Tools
    //private DecimalFormat mMoneyFormat = new DecimalFormat("$###,###,##0.00");

    private int comId ;
    private long mCompanyAsset;
    private FinancialManagementContent content ;


    public static void launchForResult(ABaseFragment from, int requestCode, FinancialManagementContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launchForResult(from, TransferFragment.class, args, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = savedInstanceState == null ? (FinancialManagementContent) getArguments().getSerializable(ARG_KEY)
                : (FinancialManagementContent) savedInstanceState.getSerializable(ARG_KEY);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, content);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_transfer;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        mCompanyAsset = content.getAsset();
        ActionBarActivity actionBarActivity = (ActionBarActivity) getActivity();
        actionBarActivity.getTitleText().setTextColor(0xFFFFC501);
        getActivity().setTitle(mCompanyAsset + "");

        mSearchTransfer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Tools.hideSoftInputFromWindow(mSearchTransfer);
                    search = mSearchTransfer.getText().toString();
                    requestData();
                    return true;
                }
                return false;
            }
        });

    }

    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                search = mSearchTransfer.getText().toString();
                Tools.hideSoftInputFromWindow(mSearchTransfer);
                requestData();
                break;
        }
    }

    @Override
    protected ABaseAdapter.AbstractItemView<CompanyInfo> newItemView() {
        return new CompanyInfoItemView();
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
            startPageId=getAdapterItems().get(getAdapterItems().size()-1).comId;
        }


        TransferRequestBean request = new TransferRequestBean();
        TransferRequestBean.DataEntity data = new TransferRequestBean.DataEntity();
        data.setPageId(startPageId);
        data.setPageCount(IronFutureConstants.DEF_PAGE_SIZE);
        data.setPageDirection(IronFutureConstants.PAGE_DIRECTION_DOWN);
        data.setSearchKey(search);
        request.setData(data);

        startJsonRequest(ApiUrls.GETCOMLIST, request, new PagingTask<TransferResponseBean>(mode) {
            @Override
            public TransferResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, TransferResponseBean.class);
            }

            @Override
            public String verifyResponseResult(TransferResponseBean result) {
                return  Tools.verifyResponseResult(result);
            }

            @Override
            protected List<CompanyInfo> parseResult(TransferResponseBean baseResponseBean) {
                List<CompanyInfo> beanList = new LinkedList<>();
                if(baseResponseBean != null && baseResponseBean.getData() != null){
                    for (TransferResponseBean.DataEntity item : baseResponseBean.getData()) {
                        CompanyInfo companyInfo = new CompanyInfo();
                        companyInfo.setComId(item.getComId());
                        companyInfo.setComName(item.getComName());
                        companyInfo.setComType(item.getComType());
                        companyInfo.setComNation(item.getComNation());
                        companyInfo.setComDescribe(item.getComDescribe());
                        companyInfo.setComCreatTime(item.getComCreatTime());
                        companyInfo.setComState(item.getComState());
                        companyInfo.setComSumMoney(item.getComSumMoney());
                        companyInfo.setSurplusMoney(item.getSurplusMoney());
                        companyInfo.setComLogo(item.getComLogo());

                        beanList.add(companyInfo);
                    }
                }
                return beanList;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Dialog dialog = Tools.createDialog(getActivity(), R.layout.dialog_transfer);
        TextView hint = (TextView)dialog.findViewById(R.id.transfer_hint);
        hint.setText("转账到" + getAdapterItems().get((int) id).getComName() + "公司，手续费2%");
        //转入公司id
        comId = getAdapterItems().get((int) id).getComId();
        etAmount = (EditText)dialog.findViewById(R.id.transfer_amount);
        transferUse = (EditText) dialog.findViewById(R.id.transfer_use);
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.transfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = etAmount.getText().toString() ;
                String use = transferUse.getText().toString();
                if(TextUtils.isEmpty(amount)){
                    ToastUtils.toast("请输入转账金额");
                    return;
                }
                if(TextUtils.isEmpty(use)){
                    ToastUtils.toast("请输入转账说明");
                    return;
                }
                if(Integer.parseInt(etAmount.getText().toString()) > mCompanyAsset ){
                    ToastUtils.toast("你输入的转账金额超出公司资产");
                    return;
                }
                transfeMoney(comId,amount,use);
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void transfeMoney(int comId,String amount,String use) {
        if(isRequestProcessing(ApiUrls.TRANSFERMONEY)){
            return;
        }
        TransferMoneyRequestBean requestBean = new TransferMoneyRequestBean();
        TransferMoneyRequestBean.DataEntity dataEntity = new TransferMoneyRequestBean.DataEntity();
        dataEntity.setOutDeptId(UserInfo.getCurrentUser().getComId()+"");
        dataEntity.setInDeptId(comId + "");
        dataEntity.setAmount(amount);
        dataEntity.setUse(use);
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.TRANSFERMONEY, requestBean, new BaseHttpRequestTask<TransferMoneyResponseBean>() {

            @Override
            public TransferMoneyResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, TransferMoneyResponseBean.class);
            }

            @Override
            public String verifyResponseResult(TransferMoneyResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(TransferMoneyResponseBean bean) {
                super.onSuccess(bean);
                ToastUtils.toast("转账成功！");
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        });
    }

    private class CompanyInfoItemView extends ABaseAdapter.AbstractItemView<CompanyInfo> {
        @ViewInject(id = R.id.transfer_company_name)
        TextView mCompanyName;
        @ViewInject(id = R.id.transfer_company_contury)
        TextView mCountry ;
        @ViewInject(id = R.id.transfer_company_time)
        TextView mCompanyTime ;
        @ViewInject(id = R.id.img)
        ImageView mCompanyLogo ;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_transfer;
        }

        @Override
        public void bindingData(View convertView, CompanyInfo data) {
            mCompanyName.setText(data.getComName());
            if(data.getComNation()!=null){
                mCountry.setText(String.format("来自国家：%s",data.getComNation()));
            }else{
                mCountry.setText("");
            }

            mCompanyTime.setText(Tools.parseTime(data.getComCreatTime()));

            String logoUrl = data.getComLogo();
            ImageLoader.getInstance().displayImage(logoUrl, mCompanyLogo, Tools.buildDefaultDisplayCompanyLogoImageOptions());
        }
    }

    public class CompanyInfo {
        private int comId;
        private String comName;
        private String comType;
        private String comNation;
        private Object comDescribe;
        private long comCreatTime;
        private int comState;
        private int comSumMoney;
        private int surplusMoney;
        private String comLogo;

        public void setComId(int comId) {
            this.comId = comId;
        }

        public void setComName(String comName) {
            this.comName = comName;
        }

        public void setComType(String comType) {
            this.comType = comType;
        }

        public void setComNation(String comNation) {
            this.comNation = comNation;
        }

        public void setComDescribe(Object comDescribe) {
            this.comDescribe = comDescribe;
        }

        public void setComCreatTime(long comCreatTime) {
            this.comCreatTime = comCreatTime;
        }

        public void setComState(int comState) {
            this.comState = comState;
        }

        public void setComSumMoney(int comSumMoney) {
            this.comSumMoney = comSumMoney;
        }

        public void setSurplusMoney(int surplusMoney) {
            this.surplusMoney = surplusMoney;
        }

        public void setComLogo(String comLogo) {
            this.comLogo = comLogo;
        }

        public int getComId() {
            return comId;
        }

        public String getComName() {
            return comName;
        }

        public String getComType() {
            return comType;
        }

        public String getComNation() {
            return comNation;
        }

        public Object getComDescribe() {
            return comDescribe;
        }

        public long getComCreatTime() {
            return comCreatTime;
        }

        public int getComState() {
            return comState;
        }

        public int getComSumMoney() {
            return comSumMoney;
        }

        public int getSurplusMoney() {
            return surplusMoney;
        }

        public String getComLogo() {
            return comLogo;
        }
    }
}
