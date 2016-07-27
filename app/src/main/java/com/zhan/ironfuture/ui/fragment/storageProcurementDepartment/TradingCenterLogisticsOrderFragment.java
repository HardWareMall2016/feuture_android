package com.zhan.ironfuture.ui.fragment.storageProcurementDepartment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.StorePurchaseOrderResponseBean;
import com.zhan.ironfuture.beans.TradingCenterCreateOrderRequestBean;
import com.zhan.ironfuture.beans.TradingCenterLogisticsOrderInfoBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.timePicker.TimePickerView;
import com.zhan.ironfuture.utils.PopMenuHelper;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 作者：伍岳 on 2016/6/29 10:40
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
public class TradingCenterLogisticsOrderFragment extends ABaseFragment implements SeekBar.OnSeekBarChangeListener {

    private final static int REQUEST_CODE_SELECT_COMPANY=100;
    private final static String ARG_KEY="arg_key";

    //Views
    @ViewInject(id = R.id.trade_order_type, click = "OnClick")
    View mViewOrderTypeContent;

    @ViewInject(id = R.id.store_trade_order_company_content, click = "OnClick")
    View mViewChoosePickCompanyContent;

    @ViewInject(id = R.id.overdue_time_content, click = "OnClick")
    View mViewOverdueTimeContent;

    @ViewInject(id = R.id.trade_order_type_name)
    TextView mViewOrderType;

    @ViewInject(id = R.id.store_trade_order_company)
    TextView mViewSelectCompany;

    @ViewInject(id = R.id.selected_goods_sum)
    TextView mViewSelectedGoodsSum;

    @ViewInject(id = R.id.goods_name)
    TextView mViewGoodsName;

    @ViewInject(id = R.id.goods_seekBar)
    SeekBar mViewGoodsSeekBar;

    @ViewInject(id = R.id.goods_count)
    TextView mViewGoodsCount;

    @ViewInject(id = R.id.trade_order_money)
    EditText mViewOrderAmount;

    @ViewInject(id = R.id.et_liquidated_damage)
    EditText mViewOrderPenalty;

    @ViewInject(id = R.id.tv_overdue_time)
    TextView mViewOverdueTime;

    private TimePickerView mViewTimePicker;

    //Tools
    private PopMenuHelper mPopMenuHelper;

    //Data
    private TradingCenterLogisticsOrderInfoBean mGoodsInfo;
    private ArrayList<String> mOrderTypeList = new ArrayList<>();
    private int mOrderTye=0;
    private long mOverdueTime=0;
    private int mCompanyId=0;
    private int mSelectGoodSum=1;


    public static void launcher(Activity activity,TradingCenterLogisticsOrderInfoBean bean){
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, bean);
        FragmentContainerActivity.launch(activity, TradingCenterLogisticsOrderFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoodsInfo = savedInstanceState == null ? (TradingCenterLogisticsOrderInfoBean) getArguments().getSerializable(ARG_KEY) : (TradingCenterLogisticsOrderInfoBean) savedInstanceState.getSerializable(ARG_KEY);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mGoodsInfo);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_trading_center_logistics_order;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("物流订单");

        mPopMenuHelper=new PopMenuHelper(getActivity());

        mOrderTypeList.clear();
        mOrderTypeList.add("整单");
        mOrderTypeList.add("单件");

        mViewGoodsName.setText(mGoodsInfo.getGoodsName());
        mViewGoodsCount.setText(String.valueOf(mGoodsInfo.getGoodsSum()));
        mViewGoodsSeekBar.setMax(mGoodsInfo.getGoodsSum());
        mViewGoodsSeekBar.setProgress(mSelectGoodSum);
        mViewGoodsSeekBar.setOnSeekBarChangeListener(this);

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

    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.trade_order_type:
                mPopMenuHelper.showChooseMenu(mOrderTypeList, new PopMenuHelper.OnPickViewListener() {
                    @Override
                    public void onFinishClickListener(int selectedIndex) {
                        mViewOrderType.setText(mOrderTypeList.get(selectedIndex));
                        if (selectedIndex == 0) {
                            mViewOrderType.setText("整单");
                            mOrderTye = 2;
                        } else {
                            mViewOrderType.setText("单件");
                            mOrderTye = 3;
                        }
                    }
                });
                break;
            case R.id.store_trade_order_company_content:
                StoreTradeOrderCompanyFragment.launcher(this, REQUEST_CODE_SELECT_COMPANY);
                break;
            case R.id.overdue_time_content:
                Tools.hideSoftInputFromWindow(mViewOverdueTime);
                mViewTimePicker.show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_CODE_SELECT_COMPANY&&resultCode==Activity.RESULT_OK){
            mCompanyId=data.getIntExtra(StoreTradeOrderCompanyFragment.KEY_PURCHASE_ORDER_COMPANY_ID,0);
            String companyName=data.getStringExtra(StoreTradeOrderCompanyFragment.KEY_PURCHASE_ORDER_COMPANY);
            mViewSelectCompany.setText(companyName);
        }
    }

    private void submit() {
        if(isRequestProcessing(ApiUrls.ORDER_BS_CREATE)){
            return;
        }
        if (mOrderTye == 0) {
            ToastUtils.toast("请选择订单类型");
            return;
        }
        if (mOverdueTime == 0) {
            ToastUtils.toast("请选择逾期时间");
            return;
        }

        if (TextUtils.isEmpty(mViewOrderAmount.getText().toString())) {
            ToastUtils.toast("请输入订单金额");
            return;
        }

        if (TextUtils.isEmpty(mViewOrderPenalty.getText().toString())) {
            ToastUtils.toast("请输入违约金");
            return;
        }

        String key = mGoodsInfo.getGoodsId()+"_"+mSelectGoodSum;

        TradingCenterCreateOrderRequestBean requestBean = new TradingCenterCreateOrderRequestBean();
        TradingCenterCreateOrderRequestBean.DataEntity dataEntity = new TradingCenterCreateOrderRequestBean.DataEntity();
        dataEntity.setCompanyId(UserInfo.getCurrentUser().getComId());
        dataEntity.setPenalty(Integer.valueOf(mViewOrderPenalty.getText().toString()));
        dataEntity.setType("1");
        dataEntity.setPickCompanyId(mCompanyId);
        dataEntity.setRemainingTime(mOverdueTime);
        dataEntity.setOrderType(mOrderTye);
        dataEntity.setPriceOrder(mSelectGoodSum*mGoodsInfo.getPrice());
        dataEntity.setTotalAmount(Integer.valueOf(mViewOrderAmount.getText().toString()));
        List<String> list = new ArrayList<>();
        list.add(key);
        dataEntity.setDetails(list);
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.ORDER_BS_CREATE, requestBean,  new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        StorePurchaseOrderResponseBean responseBean=Tools.parseJsonTostError(result,StorePurchaseOrderResponseBean.class);
                        if(responseBean!=null){
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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(progress>=1){
            mSelectGoodSum=progress;
        }else{
            mSelectGoodSum=1;
        }
        mViewSelectedGoodsSum.setText(String.valueOf(mSelectGoodSum));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

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
}
