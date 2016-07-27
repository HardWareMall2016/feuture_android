package com.zhan.ironfuture.ui.fragment.logisticsDepartment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.framework.view.pickerview.LoopView;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.BaseResponseBean;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.CreateLogisticsOrder;
import com.zhan.ironfuture.beans.OrderInfoBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.fragment.storageProcurementDepartment.StoreTradeOrderCompanyFragment;
import com.zhan.ironfuture.ui.widget.MyListView;
import com.zhan.ironfuture.ui.widget.timePicker.TimePickerView;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 作者：伍岳 on 2016/6/21 17:48
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
public class CreateLogisticsOrderFragment extends ABaseFragment{
    private final static int REQUEST_CODE_SELECT_COMPANY=100;

    private final static String ARG_KEY="order_info";

    //views
    @ViewInject(id = R.id.order_detail_listview)
    MyListView mProductListView ;

    @ViewInject(id = R.id.order_number)
    TextView mViewOrderNum ;

    @ViewInject(id = R.id.logistics_order_type, click = "OnClick")
    TextView mViewOrderType;

    @ViewInject(id = R.id.overdue_time, click = "OnClick")
    TextView mViewOverdueTime;

    @ViewInject(id = R.id.select_company, click = "OnClick")
    TextView mViewSelectCompany;

    @ViewInject(id = R.id.order_amount)
    EditText mViewOrderAmount;

    @ViewInject(id = R.id.order_penalty)
    EditText mViewOrderPenalty;

    private PopupWindow mPopupWindow;
    private LoopView mPickView;

    private TimePickerView mViewTimePicker;

    //Data
    private int mOrderTye=0;
    private long mOverdueTime=0;
    private int mCompanyId=0;

    private OrderInfoBean mOrderInfo;

    public static void launch(FragmentActivity activity,OrderInfoBean orderInfo) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, orderInfo);
        FragmentContainerActivity.launch(activity, CreateLogisticsOrderFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderInfo = savedInstanceState == null ? (OrderInfoBean) getArguments().getSerializable(ARG_KEY) : (OrderInfoBean) savedInstanceState.getSerializable(ARG_KEY);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mOrderInfo);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_create_logistics_order;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("物流订单");
        initView();
        intiPopMenu();
        initTimePicker();
    }

    @Override
    public void onActionBarMenuClick() {
        submit();
    }

    @Override
    public void onPrepareActionbarMenu(TextView menu, Activity activity) {
        menu.setText("完成");
    }

    private void initView(){
        mViewOrderNum.setText(String.valueOf(mOrderInfo.getOrderId()));
        ProductDetailAdapter detailAdapter = new ProductDetailAdapter(mOrderInfo.getProductInfoList(), getActivity());
        mProductListView.setAdapter(detailAdapter);
    }

    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.logistics_order_type:
                showChooseMenu();
                break;
            case R.id.overdue_time:
                Tools.hideSoftInputFromWindow(mViewOverdueTime);
                mViewTimePicker.show();
                break;
            case R.id.select_company:
                StoreTradeOrderCompanyFragment.launcher(this,REQUEST_CODE_SELECT_COMPANY);
                break;
        }
    }

    private void submit(){
        if(mOrderTye==0){
            ToastUtils.toast("请选择订单类型");
            return;
        }
        if(mOverdueTime==0){
            ToastUtils.toast("请选择逾期时间");
            return;
        }
        /*if(mCompanyId==0){
            ToastUtils.toast("请选择公司");
            return;
        }*/

        if(TextUtils.isEmpty(mViewOrderAmount.getText().toString())){
            ToastUtils.toast("请输入订单金额");
            return;
        }
        if(TextUtils.isEmpty(mViewOrderPenalty.getText().toString())){
            ToastUtils.toast("请输入违约金");
            return;
        }

        int amount=Integer.parseInt(mViewOrderAmount.getText().toString());

        int orderPenalty=Integer.parseInt(mViewOrderPenalty.getText().toString());

        CreateLogisticsOrder requestBean=new CreateLogisticsOrder();
        CreateLogisticsOrder.DataEntity data=new CreateLogisticsOrder.DataEntity();
        requestBean.setData(data);
        data.setOrderType(mOrderTye);// 订单类型 2物流 3物流按件
        data.setBsOrderId(mOrderInfo.getOrderId());
        data.setTotalAmount(amount);// 物流订单 费用
        data.setCompanyId(UserInfo.getCurrentUser().getComId());// 发起物流订单公司 id
        data.setProductAddress(mOrderInfo.getProductAddress());// 物品所在地 公司id
        data.setDestination(mOrderInfo.getDestination());// 目的地     公司id
        data.setPenalty(orderPenalty); // 物流 违约金
        if(mCompanyId!=0){
            data.setPickCompanyId(mCompanyId);// 接单公司 id
        }

        data.setRemainingTime(mOverdueTime);

        List<String> details=new ArrayList<>();
        for(OrderInfoBean.ProductInfo productInfo:mOrderInfo.getProductInfoList()){
            details.add(String.format("%d_%d", productInfo.getProductId(), productInfo.getProductQuantity()));
        }
        data.setDetails(details);


        startJsonRequest(ApiUrls.ORDER_LOGIS_CREATE, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        BaseResponseBean responseBean = Tools.parseJsonTostError(result, BaseResponseBean.class);
                        if (responseBean != null) {
                            ToastUtils.toast("创建订单成功");
                            getActivity().finish();
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE_SELECT_COMPANY&&resultCode==Activity.RESULT_OK){
            mCompanyId=data.getIntExtra(StoreTradeOrderCompanyFragment.KEY_PURCHASE_ORDER_COMPANY_ID,0);
            String companyName=data.getStringExtra(StoreTradeOrderCompanyFragment.KEY_PURCHASE_ORDER_COMPANY);
            mViewSelectCompany.setText(companyName);
        }
    }

    private void initTimePicker(){
        mViewTimePicker = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
        Calendar calendar = Calendar.getInstance();
        mViewTimePicker.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 100);
        mViewTimePicker.setTime(new Date());
        mViewTimePicker.setCyclic(false);
        mViewTimePicker.setCancelable(true);
        //时间选择后回调
        mViewTimePicker.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                Date curDate = new Date();
                boolean beforeDate = date.before(curDate);
                if (beforeDate) {
                    ToastUtils.toast("请选择晚于今天的日期");
                } else {
                    mOverdueTime=date.getTime();
                    mViewOverdueTime.setText(Tools.parseTimeToDateStr(mOverdueTime));
                }
            }
        });
    }

    private void showChooseMenu() {
        View viewPopMenuLayout = getActivity().getLayoutInflater().inflate(R.layout.pop_memu_common_pickview, null);
        mPopupWindow.setContentView(viewPopMenuLayout);
        View btnCancel = viewPopMenuLayout.findViewById(R.id.cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopWin();
            }
        });

        View btnFinish = viewPopMenuLayout.findViewById(R.id.finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item=mPickView.getSelectedItem();
                if(item>-1){
                    if(item==0){
                        mViewOrderType.setText("整单");
                        mViewOrderAmount.setHint("请输入总金额");
                        mOrderTye=2;
                    }else{
                        mViewOrderType.setText("单件");
                        mViewOrderAmount.setHint("请输入单件金额");
                        mOrderTye=3;
                    }
                    closePopWin();
                }
            }
        });

        ArrayList<String> nameList = new ArrayList<>();
        nameList.add("整单");//code 2
        nameList.add("单件");//code 3

        mPickView = (LoopView) viewPopMenuLayout.findViewById(R.id.picker_view);
        mPickView.setNotLoop();
        mPickView.setArrayList(nameList);
        mPickView.setInitPosition(0);

        showPopMenu();
    }

    private void intiPopMenu() {
        mPopupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        int bgColor = getResources().getColor(com.zhan.framework.R.color.main_background);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(bgColor));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.pop_menu_animation);
        mPopupWindow.update();
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
    }

    private void showPopMenu() {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            backgroundAlpha(0.7f);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
    }
    public boolean closePopWin() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return true;
        }
        return false;
    }
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }


    private class ProductDetailAdapter extends ABaseAdapter<OrderInfoBean.ProductInfo> {

        public ProductDetailAdapter(ArrayList<OrderInfoBean.ProductInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<OrderInfoBean.ProductInfo> newItemView() {
            return new ProductItemView();
        }
    }

    private class ProductItemView extends ABaseAdapter.AbstractItemView<OrderInfoBean.ProductInfo> {
        @ViewInject(id = R.id.product_name)
        TextView mViewProductName;

        @ViewInject(id = R.id.product_count)
        TextView mViewProductCount;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_order_details_product;
        }

        @Override
        public void bindingData(View convertView, OrderInfoBean.ProductInfo data) {
            mViewProductName.setText(data.getGoodsName());
            mViewProductCount.setText(String.format("x%d",data.getProductQuantity()));
        }
    }
}
