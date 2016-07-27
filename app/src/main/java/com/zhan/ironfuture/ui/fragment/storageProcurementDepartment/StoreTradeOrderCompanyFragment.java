package com.zhan.ironfuture.ui.fragment.storageProcurementDepartment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.APullToRefreshListFragment;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.beans.TransferRequestBean;
import com.zhan.ironfuture.beans.TransferResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.Tools;

import java.util.LinkedList;
import java.util.List;

/**
 * 作者：伍岳 on 2016/5/11 13:41
 * 邮箱：wuyue8512@163.com
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
public class StoreTradeOrderCompanyFragment extends APullToRefreshListFragment<StoreTradeOrderCompanyFragment.StoreTradeOrderCompanyInfo> {
    public final static String KEY_PURCHASE_ORDER_COMPANY = "company_name";
    public final static String KEY_PURCHASE_ORDER_COMPANY_ID = "company_id";

    @ViewInject(id = R.id.store_trade_order_search)
    EditText mSearchCompany;

    @ViewInject(id = R.id.search_btn, click = "OnClick")
    View mSearchBtn;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_store_trade_order_company;
    }

    public static void launcher(Fragment fragment, int requestCode) {
        FragmentContainerActivity.launchForResult(fragment, StoreTradeOrderCompanyFragment.class, null, requestCode);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {

        getActivity().setTitle("指定公司");

        mSearchCompany.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Tools.hideSoftInputFromWindow(mSearchCompany);
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
                Tools.hideSoftInputFromWindow(mSearchCompany);
                requestData();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        Intent intent = new Intent();
        intent.putExtra(KEY_PURCHASE_ORDER_COMPANY, getAdapterItems().get((int) id).getComName());
        intent.putExtra(KEY_PURCHASE_ORDER_COMPANY_ID, getAdapterItems().get((int) id).getComId());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }


    @Override
    protected ABaseAdapter.AbstractItemView<StoreTradeOrderCompanyInfo> newItemView() {
        return new StoreTradeOrderCompanyItemView();
    }

    @Override
    protected void configRefresh(RefreshConfig config) {
        config.minResultSize=IronFutureConstants.DEF_PAGE_SIZE;
    }

    @Override
    protected void requestData(RefreshMode mode) {

        String search = mSearchCompany.getText().toString().trim();

        int startPageId;
        if (mode == RefreshMode.refresh || mode == RefreshMode.reset || isContentEmpty()) {
            startPageId = 0;
        } else {
            startPageId = getAdapterItems().get(getAdapterItems().size() - 1).comId;
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
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected List<StoreTradeOrderCompanyInfo> parseResult(TransferResponseBean baseResponseBean) {
                List<StoreTradeOrderCompanyInfo> beanList = new LinkedList<>();
                if (baseResponseBean != null && baseResponseBean.getData() != null) {
                    for (TransferResponseBean.DataEntity item : baseResponseBean.getData()) {
                        StoreTradeOrderCompanyInfo companyInfo = new StoreTradeOrderCompanyInfo();
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

    private class StoreTradeOrderCompanyItemView extends ABaseAdapter.AbstractItemView<StoreTradeOrderCompanyInfo> {

        @ViewInject(id = R.id.transfer_company_name)
        TextView mCompanyName;
        @ViewInject(id = R.id.transfer_company_contury)
        TextView mCountry;
        @ViewInject(id = R.id.transfer_company_time)
        TextView mCompanyTime;
        @ViewInject(id = R.id.img)
        ImageView mCompanyLogo ;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_transfer;
        }

        @Override
        public void bindingData(View convertView, StoreTradeOrderCompanyInfo data) {
            mCompanyName.setText(data.getComName());
            mCountry.setText("来自国家：" + data.getComNation());

            mCompanyTime.setText(Tools.parseTime(data.getComCreatTime()));

            String logoUrl = data.getComLogo();
            ImageLoader.getInstance().displayImage(logoUrl, mCompanyLogo, Tools.buildDefaultDisplayCompanyLogoImageOptions());
        }
    }

    public class StoreTradeOrderCompanyInfo {
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
