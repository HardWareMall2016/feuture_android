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
import android.widget.SeekBar;
import android.widget.TextView;

import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.framework.view.pickerview.LoopView;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.beans.StorePurchaseOrderResponseBean;
import com.zhan.ironfuture.beans.WarehouseInfo;
import com.zhan.ironfuture.beans.StorePurchaseOrderRequestBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.MyListView;
import com.zhan.ironfuture.ui.widget.timePicker.TimePickerView;
import com.zhan.ironfuture.utils.Tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 贸易订单
 */
public class StoreTradeOrderFragment extends ABaseFragment{
    private static final String ARG_KEY="SELECTED_WAREHOUSE_LIST";
    private final static int REQUEST_CODE_PURCHASE_ORDER = 102;

    @ViewInject(id = R.id.store_trade_order_listview)
    MyListView mTradeOrderListView ;
    @ViewInject(id = R.id.rl_store_trade_order_company,click = "OnClick")
    RelativeLayout mChangeCompany ;
    @ViewInject(id = R.id.store_trade_order_company)
    TextView mCompanyName ;
    @ViewInject(id = R.id.rl_store_trade_order_out_data,click = "OnClick")
    RelativeLayout mOrderOutData ;
    @ViewInject(id = R.id.tv_store_trade_order_out_data)
    TextView mOutData ;
    @ViewInject(id = R.id.trade_order_type,click = "OnClick")
    RelativeLayout mTradeOrderType ;
    @ViewInject(id = R.id.trade_order_type_name)
    TextView mTypeName;
    @ViewInject(id = R.id.mylesson_picker_view)
    LoopView mPickView;
    @ViewInject(id = R.id.trade_order_money)
    EditText mOrderMoney ;

    //data
    private ArrayList<StoreTradeOrderInfo> mOrderList = new ArrayList<>();
    private List<String> list_name = new ArrayList<>();
    private ArrayList<WarehouseInfo> mSelWarehouseList;

    private StoreTradeOrderAdapter mAdapter ;
    private TimePickerView pvTime;

    private String productId = "0";
    private long remainingTime;



    public static void launcherForResult(ABaseFragment from, int requestCode,ArrayList<WarehouseInfo> mWarehouseList){
        FragmentArgs args=new FragmentArgs();
        args.add(ARG_KEY, mWarehouseList);
        FragmentContainerActivity.launchForResult(from, StoreTradeOrderFragment.class, args, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSelWarehouseList = savedInstanceState == null ? (ArrayList<WarehouseInfo>) getArguments().getSerializable(ARG_KEY)
                : (ArrayList<WarehouseInfo>) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mSelWarehouseList);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_store_trade_order;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("贸易订单");
        list_name.clear();
        list_name.add("单件");
        list_name.add("整单");

        mOrderList.clear();
        for (int i = 0; i < mSelWarehouseList.size(); i++) {
            StoreTradeOrderInfo storeTradeOrderInfo = new StoreTradeOrderInfo();
            storeTradeOrderInfo.setGoodsId(mSelWarehouseList.get(i).getGoodsId());
            storeTradeOrderInfo.setGoodsCount(mSelWarehouseList.get(i).getGoodsCount());
            storeTradeOrderInfo.setGoodsName(mSelWarehouseList.get(i).getGoodsName());
            mOrderList.add(storeTradeOrderInfo);
        }

        mAdapter = new StoreTradeOrderAdapter(mOrderList, getActivity());
        mTradeOrderListView.setAdapter(mAdapter);

        //时间选择器
        pvTime = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 100);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                Date nowdate = new Date();
                boolean flag = date.before(nowdate);
                if (flag) {
                    ToastUtils.toast("请选择晚于今天的日期");
                } else {
                    mOutData.setText(getTime(date));
                    remainingTime = date.getTime() ;
                }
            }
        });


    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
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

        List<String> list = new ArrayList<>();
        for(int i = 0;i<mOrderList.size();i++){
            LinearLayout layout = (LinearLayout) mTradeOrderListView.getChildAt(i);
            TextView textView = (TextView)layout.findViewById(R.id.product_count_change);
            list.add(mOrderList.get(i).getGoodsId()+"_"+ textView.getText().toString());
        }

        StorePurchaseOrderRequestBean requestBean = new StorePurchaseOrderRequestBean();
        StorePurchaseOrderRequestBean.DataEntity dataEntity = new StorePurchaseOrderRequestBean.DataEntity();
        dataEntity.setPickCompanyId(Integer.parseInt(productId));
        dataEntity.setRemainingTime(remainingTime);
        dataEntity.setOrderType(1);
        dataEntity.setTotalAmount(Integer.valueOf(mOrderMoney.getText().toString()));
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
                getActivity().setResult(Activity.RESULT_OK);//让上个页面刷新
                getActivity().finish();
            }
        });
    }

    private boolean checkInput() {
        if(TextUtils.isEmpty(mOrderMoney.getText().toString())){
            ToastUtils.toast("请输入订单金额");
            return false;
        }
        if ("请选择逾期时间".equals(mOutData.getText().toString())) {
            ToastUtils.toast("请选择逾期时间");
            return false;
        }
        return true;
    }

    void OnClick(View view){
        switch (view.getId()){
            case R.id.rl_store_trade_order_company:
                StoreTradeOrderCompanyFragment.launcher(StoreTradeOrderFragment.this, REQUEST_CODE_PURCHASE_ORDER);
                break;
            case R.id.rl_store_trade_order_out_data:
                InputMethodManager immm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                immm.hideSoftInputFromWindow(mTypeName.getWindowToken(), 0); //强制隐藏键盘
                pvTime.show();
                break;
            case R.id.trade_order_type:
//                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(mTypeName.getWindowToken(), 0); //强制隐藏键盘
//                showChooseMenu();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PURCHASE_ORDER && resultCode == Activity.RESULT_OK) {
            mCompanyName.setText(data.getSerializableExtra(StoreTradeOrderCompanyFragment.KEY_PURCHASE_ORDER_COMPANY) + "");
            productId = data.getSerializableExtra(StoreTradeOrderCompanyFragment.KEY_PURCHASE_ORDER_COMPANY_ID) + "";
        }
    }


    private class StoreTradeOrderAdapter extends ABaseAdapter<StoreTradeOrderInfo> {

        public StoreTradeOrderAdapter(ArrayList<StoreTradeOrderInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<StoreTradeOrderInfo> newItemView() {
            return new StoreTradeOrderItemView();
        }
    }

    private class StoreTradeOrderItemView extends ABaseAdapter.AbstractItemView<StoreTradeOrderInfo> {

        @ViewInject(id = R.id.product_name)
        TextView mProductName ;
        @ViewInject(id = R.id.product_count)
        TextView mProductCount ;
        @ViewInject(id = R.id.product_count_change)
        TextView mCountChange ;
        @ViewInject(id = R.id.product_seekbar)
        SeekBar mProductSeekBar ;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_logistics_orders_product;
        }

        @Override
        public void bindingData(View convertView, final StoreTradeOrderInfo data) {
            mProductSeekBar.setMax(data.getGoodsCount());
            mProductSeekBar.setProgress(0);
            mProductSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        data.goodsCount = progress;
                    }
                    if (progress < 1) {
                        mCountChange.setText("1");
                    } else {
                        mCountChange.setText(String.valueOf(progress));
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            mProductName.setText("物品：" + data.getGoodsName());
            mProductCount.setText(data.getGoodsCount() + "个");
        }
    }

    public class StoreTradeOrderInfo{
        private int goodsId;
        private int goodsCount;
        private String goodsName;

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

    }
}
