package com.zhan.ironfuture.ui.fragment.storageProcurementDepartment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.StorePurchaseOrderRequestBean;
import com.zhan.ironfuture.beans.StorePurchaseOrderResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.timePicker.TimePickerView;
import com.zhan.ironfuture.utils.Tools;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 采购订单
 */
public class StorePurchaseOrderFragment extends ABaseFragment{

    private final static int REQUEST_CODE_PURCHASE_ORDER = 102;
    private final static int REQUEST_CODE_PURCHASE_COMPANY = 103;

    @ViewInject(id = R.id.rl_store_purchase_order_change,click = "OnClick")
    RelativeLayout mStoreChange ;
    @ViewInject(id = R.id.rl_store_purchase_order_product)
    LinearLayout mSoreProduct ;
    @ViewInject(id = R.id.purchase_order_product_name)
    TextView mProductName ;
    @ViewInject(id = R.id.purchase_order_change_name)
    TextView mChangeName ;
    @ViewInject(id = R.id.rl_purchase_order_out_data,click = "OnClick")
    RelativeLayout mOrderOutData;
    @ViewInject(id = R.id.tv_purchase_order_out_data)
    TextView mTVOutData ;
    @ViewInject(id = R.id.et_purchase_order_product)
    EditText mETProduct ;
    @ViewInject(id = R.id.et_purchase_order_money)
    EditText mETMoney ;
    @ViewInject(id = R.id.rl_store_purchase_order_company,click = "OnClick")
    RelativeLayout mOrderCompany ;
    @ViewInject(id = R.id.purchase_order_change_company)
    TextView mCompany ;
    private TimePickerView pvTime;
    private String productId = "";
    private String companyId = "0";
    private long remainingTime;


    public static void launcher(Activity activity){
        FragmentContainerActivity.launch(activity,StorePurchaseOrderFragment.class,null);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_store_purchase_order;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("采购订单");
        //时间选择器
        pvTime = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR)+100);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                Date nowdate=new Date();
                boolean flag = date.before(nowdate);
                if(flag){
                    ToastUtils.toast("请选择晚于今天的日期");
                    //mTVOutData.setText(getTime(nowdate));
                }else{
                    mTVOutData.setText(getTime(date));
                    remainingTime = date.getTime() ;
                }
            }
        });
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    void OnClick(View v){
        switch (v.getId()){
            case R.id.rl_store_purchase_order_change:
                StorePurchaseChangeProductFragment.launcher(StorePurchaseOrderFragment.this,REQUEST_CODE_PURCHASE_ORDER);
                break;
            case R.id.rl_purchase_order_out_data:
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mTVOutData.getWindowToken(), 0); //强制隐藏键盘
                pvTime.show();
                break;
            case R.id.rl_store_purchase_order_company:
                StoreTradeOrderCompanyFragment.launcher(StorePurchaseOrderFragment.this, REQUEST_CODE_PURCHASE_COMPANY);
                break;
        }
    }

    @Override
    public void onPrepareActionbarMenu(TextView menu, Activity activity) {
        super.onPrepareActionbarMenu(menu, activity);
        menu.setText("完成");
        menu.setTextColor(Color.rgb(255, 70, 15));
    }

    @Override
    public void onActionBarMenuClick() {
        super.onActionBarMenuClick();
        if (!checkInput()) {
            return;
        }
        String key = productId+"_"+mETProduct.getText().toString();
        StorePurchaseOrderRequestBean requestBean = new StorePurchaseOrderRequestBean();
        StorePurchaseOrderRequestBean.DataEntity dataEntity = new StorePurchaseOrderRequestBean.DataEntity();
        dataEntity.setPickCompanyId(Integer.parseInt(companyId));
        dataEntity.setRemainingTime(remainingTime);
        dataEntity.setOrderType(4);
        dataEntity.setTotalAmount(Integer.valueOf(mETMoney.getText().toString()));
        List<String> list = new ArrayList<>();
        list.add(key);
        dataEntity.setDetails(list);
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.ORDER_BS_CREATE, requestBean, new BaseHttpRequestTask<StorePurchaseOrderResponseBean>() {

            @Override
            public StorePurchaseOrderResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, StorePurchaseOrderResponseBean.class);
            }
            @Override
            public String verifyResponseResult(StorePurchaseOrderResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(StorePurchaseOrderResponseBean bean) {
                super.onSuccess(bean);
                ToastUtils.toast(bean.getMessage());
                getActivity().finish();
            }
        });

    }

    private boolean checkInput() {
        if(TextUtils.isEmpty(productId)){
            ToastUtils.toast("请选择物品");
            return false;
        }
        if(TextUtils.isEmpty(mETProduct.getText().toString())){
            ToastUtils.toast("请输入采购商品件数");
            return false;
        }
        if(TextUtils.isEmpty(mETMoney.getText().toString())){
            ToastUtils.toast("请输入订单金额");
            return false;
        }
        if ("请选择逾期时间".equals(mTVOutData.getText().toString())) {
            ToastUtils.toast("请选择逾期时间");
            return false;
        }

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PURCHASE_ORDER && resultCode == Activity.RESULT_OK) {
            mSoreProduct.setVisibility(View.VISIBLE);
            mChangeName.setText(data.getSerializableExtra(StorePurchaseChangeProductFragment.KEY_PRODUCT_CHANGE) + "");
            mProductName.setText(data.getSerializableExtra(StorePurchaseChangeProductFragment.KEY__PURCHASE_ORDER_PRODUCT) + "");
            productId = data.getSerializableExtra(StorePurchaseChangeProductFragment.KEY__PURCHASE_ORDER_ID) + "";
        }

        if (requestCode == REQUEST_CODE_PURCHASE_COMPANY && resultCode == Activity.RESULT_OK) {
            mCompany.setText(data.getSerializableExtra(StoreTradeOrderCompanyFragment.KEY_PURCHASE_ORDER_COMPANY) + "");
            companyId = data.getSerializableExtra(StoreTradeOrderCompanyFragment.KEY_PURCHASE_ORDER_COMPANY_ID) + "";
        }
    }


}
