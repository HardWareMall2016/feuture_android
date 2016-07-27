package com.zhan.ironfuture.ui.fragment.logisticsDepartment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.ui.activity.BaseActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.beans.CarsRemoveInfoRequestBean;
import com.zhan.ironfuture.beans.CarsRemoveInfoResponseBean;
import com.zhan.ironfuture.beans.RemoveStevedoringDetailContent;
import com.zhan.ironfuture.beans.StevedoringDetailContent;
import com.zhan.ironfuture.beans.StevedoringLoadRequestBean;
import com.zhan.ironfuture.beans.StevedoringLoadResponseBean;
import com.zhan.ironfuture.beans.TruckDetailsContent;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.fragment.storageProcurementDepartment.StoreTradeOrderCompanyFragment;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;

/**
 * Created by WuYue on 2016/5/3.
 */
public class StevedoringFragment extends ABaseFragment {
    private final static String ARG_KEY = "stevedoring";
    private final static int REQUEST_CODE_PURCHASE_ORDER = 103;
    public static final String KEY_PURCHASE_ORDER_COMPANY = "1";

    private TruckDetailsContent content;
    //Views
    private TextView mViewLogistics;
    private TextView mViewGoods;

    @ViewInject(id = R.id.header)
    LinearLayout mHeader;
    @ViewInject(id = R.id.stevedoring_detail_executePower)
    TextView mExecutePower;

    @ViewInject(id = R.id.gridView)
    GridView mGridView;

    @ViewInject(id = R.id.unloading_list)
    ListView mUnloadingList;

    @ViewInject(id = R.id.filter)
    View mViewFilterUnloadContent;
    @ViewInject(id = R.id.loading_content)
    View mViewLoadingContent;
    @ViewInject(id = R.id.unloading_content)
    View mViewUnLoadingContent;

    @ViewInject(id = R.id.amount_content, click = "OnClick")
    View mViewFilterAmountContent;
    @ViewInject(id = R.id.amount)
    TextView mViewFilterAmount;

    @ViewInject(id = R.id.emergency_content, click = "OnClick")
    View mViewFilterEmergContent;
    @ViewInject(id = R.id.emergency)
    TextView mViewFilterEmergency;

    @ViewInject(id = R.id.ccan_unloading_content, click = "OnClick")
    View mViewFilterUnLoadContent;
    @ViewInject(id = R.id.can_unloading)
    TextView mViewFilterUnLoad;

    @ViewInject(id = R.id.filter_content, click = "OnClick")
    View mViewFilterContentList;
    @ViewInject(id = R.id.filter_list)
    ListView mViewFilterList;


    //params
    private int mStatusBarHeight = 0;
    private int mActionbarHeight = 0;

    //Flag
    private boolean mIsLoadingMode = true;//是否是转货

    private static final int FILTER_NONE = 0;
    private static final int FILTER_AMOUNT = 1;
    private static final int FILTER_EMERGENCY = 2;
    private static final int FILTER_CCAN_UNLOAD = 3;
    private int mFilterType = FILTER_NONE;

    //Tools
    private LoadingAdapter mGridViewAdapter;
    private StevedoringAdapter mUnloadingAdapter;
    private LayoutInflater mInflater;
    private FilterAdapter mFilterAdapter;

    //Data
    private ArrayList<WarehouseInfo> mWarehouseList = new ArrayList<>();
    private ArrayList<OrderDataInfo> mOrderList = new ArrayList<>();

    //Data
    private ArrayList<FilerData> mFilerData = new ArrayList<>();
    private ArrayList<FilerData> mFilerDataAmountList = new ArrayList<>();
    private ArrayList<FilerData> mFilerDataEmergencyList = new ArrayList<>();
    private ArrayList<FilerData> mFilerDataUnLoadList = new ArrayList<>();

    private int carId;
    private int comId;

    private String mSalary ;
    private String mIsUrgent = "1" ;
    private String mIsHandling = "1";

    @Override
    public void onPrepareSetContentView(BaseActivity activity) {
        activity.setOverlay(true);
        activity.showActionbarUnderline(false);
        mStatusBarHeight = activity.getStatusBarHeight();
        mActionbarHeight = activity.getActionbarHeight();
    }

    public static void launch(Activity activity, TruckDetailsContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launch(activity, StevedoringFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = savedInstanceState == null ? (TruckDetailsContent) getArguments().getSerializable(ARG_KEY)
                : (TruckDetailsContent) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, content);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_stevedoring;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("装货卸货");
        carId = content.getCarId();
        comId = content.getComId();
        mInflater = inflater;
        int padding = PixelUtils.dp2px(16);
        mHeader.setPadding(padding, mStatusBarHeight + mActionbarHeight, padding, padding);
        mIsLoadingMode = true;
        initActionbar();
        mGridViewAdapter = new LoadingAdapter(mWarehouseList, getActivity());
        mGridView.setAdapter(mGridViewAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mWarehouseList.get((int) id).getSpaceState() != 0) {
                    StevedoringDetailContent content = new StevedoringDetailContent();
                    content.setTitle("装货详情");
                    content.setOrderId(mWarehouseList.get((int) id).getOrderId());
                    content.setCarId(carId);
                    content.setOrderGoodsCount(mWarehouseList.get((int) id).getOrderGoodsCount());
                    StevedoringDetailFragment.launch(StevedoringFragment.this, content, REQUEST_CODE_PURCHASE_ORDER);
                }
            }
        });

        mUnloadingAdapter = new StevedoringAdapter(mOrderList, getActivity());
        mUnloadingList.setAdapter(mUnloadingAdapter);
        mUnloadingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RemoveStevedoringDetailContent content = new RemoveStevedoringDetailContent();
                content.setTitle("卸货详情");
                content.setOrderId(mOrderList.get((int) id).getOrderId());
                content.setCarId(carId);
                content.setOrderGoodsCount(mOrderList.get((int) id).getGoodsCount());
                content.setComId(comId);
                content.setmIsHandling(mIsHandling);
                //RemoveStevedoringDetailFragment.launch(getActivity(), content);
                RemoveStevedoringDetailFragment.launch(StevedoringFragment.this, content, REQUEST_CODE_PURCHASE_ORDER);
            }
        });


        mFilterAdapter = new FilterAdapter(mFilerData, getActivity());
        mViewFilterList.setAdapter(mFilterAdapter);
        mViewFilterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(FilerData filerData:mFilerDataAmountList){
                    filerData.isSelected=false;
                }
                for(FilerData filerData:mFilerDataEmergencyList){
                    filerData.isSelected=false;
                }
                for(FilerData filerData:mFilerDataUnLoadList){
                    filerData.isSelected=false;
                }
                for(int i=0;i<mFilerData.size();i++){
                    mFilerData.get(i).isSelected=i==position;
                }
                mSalary = mFilerData.get((int)id).getName();
                mIsUrgent = mFilerData.get((int)id).getId()+"";
                mIsHandling = mFilerData.get((int)id).getId()+"";
                queryUnloadingInfo();
                mFilterType = FILTER_NONE;
                refreshFilerStatus();
            }
        });


        refreshView();
    }

    private void initActionbar() {
        if (getActivity() instanceof ActionBarActivity) {
            ActionBarActivity actionBarActivity = (ActionBarActivity) getActivity();
            actionBarActivity.setShowActionBarCustomerContent(true);
            FrameLayout actionbarContent = actionBarActivity.getActionBarCustomerContent();

            actionbarContent.removeAllViews();
            View headerLayout = mInflater.inflate(R.layout.header_stevedoring, null);
            mViewLogistics = (TextView) headerLayout.findViewById(R.id.loading_title);
            mViewGoods = (TextView) headerLayout.findViewById(R.id.unloading_title);
            mViewLogistics.setOnClickListener(mOnTabClick);
            mViewGoods.setOnClickListener(mOnTabClick);
            actionbarContent.addView(headerLayout);

            headerLayout.findViewById(R.id.go_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });

            refreshTabViews();
        }
    }


    @Override
    public boolean isContentEmpty() {
        if (mIsLoadingMode) {
            return mWarehouseList.size() == 0;
        } else {
            return mOrderList.size() == 0;
        }
    }

    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.amount_content:
                mFilterType = FILTER_AMOUNT;
                break;
            case R.id.emergency_content:
                mFilterType = FILTER_EMERGENCY;
                break;
            case R.id.ccan_unloading_content:
                mFilterType = FILTER_CCAN_UNLOAD;
                break;
            case R.id.filter_content:
                mFilterType=FILTER_NONE;
                break;
        }
        refreshFilerStatus();
    }

    private void refreshFilerStatus() {
        switch (mFilterType) {
            case FILTER_NONE:
                mViewFilterAmount.setTextColor(0xff9390A5);
                mViewFilterAmount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterEmergency.setTextColor(0xff9390A5);
                mViewFilterEmergency.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterUnLoad.setTextColor(0xff9390A5);
                mViewFilterUnLoad.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                break;
            case FILTER_AMOUNT:
                mFilerData = mFilerDataAmountList;
                mViewFilterAmount.setTextColor(0xffffc500);
                mViewFilterAmount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_small, 0);
                mViewFilterEmergency.setTextColor(0xff9390A5);
                mViewFilterEmergency.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterUnLoad.setTextColor(0xff9390A5);
                mViewFilterUnLoad.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterList.setAdapter(new FilterAdapter(mFilerData, getActivity()));
                //mFilterAdapter.notifyDataSetChanged();
                break;
            case FILTER_EMERGENCY:
                mFilerData = mFilerDataEmergencyList;
                mViewFilterEmergency.setTextColor(0xffffc500);
                mViewFilterEmergency.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_small, 0);
                mViewFilterAmount.setTextColor(0xff9390A5);
                mViewFilterAmount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterUnLoad.setTextColor(0xff9390A5);
                mViewFilterUnLoad.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterList.setAdapter(new FilterAdapter(mFilerData, getActivity()));
                //mFilterAdapter.notifyDataSetChanged();
                break;
            case FILTER_CCAN_UNLOAD:
                mFilerData = mFilerDataUnLoadList;
                mViewFilterUnLoad.setTextColor(0xffffc500);
                mViewFilterUnLoad.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_small, 0);
                mViewFilterAmount.setTextColor(0xff9390A5);
                mViewFilterAmount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterEmergency.setTextColor(0xff9390A5);
                mViewFilterEmergency.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterList.setAdapter(new FilterAdapter(mFilerData, getActivity()));
                //mFilterAdapter.notifyDataSetChanged();
                break;
        }
        mViewFilterContentList.setVisibility(mFilterType == FILTER_NONE ? View.GONE : View.VISIBLE);
    }

    private class FilerData {
        int id;
        boolean isSelected;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    private class FilterAdapter extends ABaseAdapter<FilerData> {

        public FilterAdapter(ArrayList<FilerData> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<FilerData> newItemView() {
            return new FilterItemView();
        }
    }

    private class FilterItemView extends ABaseAdapter.AbstractItemView<FilerData> {
        @ViewInject(id = R.id.radio)
        RadioButton mViewRadioButton;

        @ViewInject(id = R.id.filter_name)
        TextView mViewFilterName;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_logistics_orders_center_filter;
        }

        @Override
        public void bindingData(View convertView, FilerData data) {
            mViewFilterName.setText(data.name);
            if (data.isSelected) {
                mViewRadioButton.setChecked(true);
            } else {
                mViewRadioButton.setChecked(false);
            }
        }
    }

    @Override
    public void requestData() {
        releaseAllRequest();
        if (mIsLoadingMode) {
            queryLoadingInfo();
        } else {
            queryUnloadingInfo();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PURCHASE_ORDER && resultCode == Activity.RESULT_OK) {
            /*if("1".equals(String.valueOf(data.getIntExtra(KEY_PURCHASE_ORDER_COMPANY)))){
                queryLoadingInfo();
            }else{
                queryUnloadingInfo();
            }*/
            requestData();
        }
    }

    private View.OnClickListener mOnTabClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loading_title:
                    if (!mIsLoadingMode) {
                        mIsLoadingMode = true;
                        refreshTabViews();
                        refreshView();
                        requestData();
                    }
                    break;
                case R.id.unloading_title:
                    if (mIsLoadingMode) {
                        mIsLoadingMode = false;
                        refreshTabViews();
                        refreshView();
                         mFilterType = FILTER_NONE;
                        refreshFilerStatus();

                        requestData();

                    }
                    break;
            }
        }
    };


    private void refreshView() {
        if (mIsLoadingMode) {
            mViewLoadingContent.setVisibility(View.VISIBLE);
            mViewUnLoadingContent.setVisibility(View.GONE);
            mViewFilterUnloadContent.setVisibility(View.GONE);
            mViewFilterContentList.setVisibility(View.GONE);
        } else {
            mViewLoadingContent.setVisibility(View.GONE);
            mViewUnLoadingContent.setVisibility(View.VISIBLE);
            mViewFilterUnloadContent.setVisibility(View.VISIBLE);
        }
    }

    private void refreshTabViews() {
        if (mIsLoadingMode) {
            mViewLogistics.setBackgroundResource(R.drawable.bg_main_color_bg_sub_color3_underline);
            mViewLogistics.setTextColor(0xff17112B);
            mViewLogistics.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
            mViewGoods.setBackgroundResource(R.drawable.default_bg);
            mViewGoods.setTextColor(0xff999999);
            mViewGoods.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
        } else {
            mViewLogistics.setBackgroundResource(R.drawable.default_bg);
            mViewLogistics.setTextColor(0xff999999);
            mViewLogistics.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
            mViewGoods.setBackgroundResource(R.drawable.bg_main_color_bg_sub_color3_underline);
            mViewGoods.setTextColor(0xff17112B);
            mViewGoods.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
        }
    }

    /***
     * 获取装货信息
     */
    private void queryLoadingInfo() {
        StevedoringLoadRequestBean requestBean = new StevedoringLoadRequestBean();
        StevedoringLoadRequestBean.DataEntity dataEntity = new StevedoringLoadRequestBean.DataEntity();
        dataEntity.setCarId(content.getCarId());
        dataEntity.setComId(content.getComId() + "");
        dataEntity.setRodeLineId(content.getRodeLineId());
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.GETCARSADDINFOLIST, requestBean, new BaseHttpRequestTask<StevedoringLoadResponseBean>() {
            @Override
            public StevedoringLoadResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, StevedoringLoadResponseBean.class);
            }

            @Override
            public String verifyResponseResult(StevedoringLoadResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(StevedoringLoadResponseBean bean) {
                super.onSuccess(bean);
                mWarehouseList.clear();
                if(bean != null && bean.getData() != null && bean.getData().getAddGoodsBackList() != null ){
                    for (StevedoringLoadResponseBean.DataEntity.AddGoodsBackListEntity item : bean.getData().getAddGoodsBackList()) {
                        WarehouseInfo data = new WarehouseInfo();
                        data.setStorageGrid(item.getStorageGrid());
                        data.setSeqNomber(item.getSeqNomber());
                        data.setSpaceState(item.getSpaceState());
                        data.setStorageComId(item.getStorageComId());
                        data.setStorageComName(item.getStorageComName());
                        data.setOrderPrice(item.getOrderPrice());
                        data.setOrderType(item.getOrderType());
                        data.setDeditPrice(item.getDeditPrice());
                        data.setOrderGoodsCount(item.getOrderGoodsCount());
                        data.setOverdueTime(item.getOverdueTime());
                        data.setGoodsName(item.getGoodsName());
                        data.setOrderId(item.getOrderId());
                        mWarehouseList.add(data);
                    }
                    mExecutePower.setText(bean.getData().getExecutePower() + "");
                    mGridViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    /***
     * 获取卸货信息
     */
    private void queryUnloadingInfo() {
        mOrderList.clear();

        CarsRemoveInfoRequestBean requestBean = new CarsRemoveInfoRequestBean();
        CarsRemoveInfoRequestBean.DataEntity dataEntity = new CarsRemoveInfoRequestBean.DataEntity();
        dataEntity.setComId(content.getComId() + "");
        dataEntity.setCarId(content.getCarId() + "");
        dataEntity.setPageId(0);
        dataEntity.setPageCount(IronFutureConstants.DEF_PAGE_SIZE);
        dataEntity.setPageDirection(IronFutureConstants.PAGE_DIRECTION_DOWN);

        if (mFilterType == FILTER_CCAN_UNLOAD) {
            dataEntity.setIsHandling(mIsHandling);//1可卸货，0不可卸货
        } else if (mFilterType == FILTER_AMOUNT) {
            dataEntity.setSalary(mSalary);//订单金额区间
        } else if(mFilterType == FILTER_EMERGENCY){
            dataEntity.setIsUrgent(mIsUrgent);//1紧急，0不紧急
        }else {
            dataEntity.setIsHandling(mIsHandling);
        }
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.GET_CARS_REMOVE_INFO_LIST, requestBean, new BaseHttpRequestTask<CarsRemoveInfoResponseBean>() {
            @Override
            public CarsRemoveInfoResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, CarsRemoveInfoResponseBean.class);
            }

            @Override
            public String verifyResponseResult(CarsRemoveInfoResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(CarsRemoveInfoResponseBean result) {
                super.onSuccess(result);

                for (CarsRemoveInfoResponseBean.DataEntity.OrderListEntity orderListitem : result.getData().getOrderList()) {
                    OrderDataInfo data = new OrderDataInfo();
                    data.setOrderId(orderListitem.getOrderId());
                    data.setGoodsCount(orderListitem.getGoodsCount());
                    data.setSalary(orderListitem.getSalary());
                    mOrderList.add(data);
                }

                if(mFilerDataAmountList.size()==0){
                    for (CarsRemoveInfoResponseBean.DataEntity.QueryCriteriaEntity.SalaryListEntity salaryListitem : result.getData().getQueryCriteria().getSalaryList()) {
                        FilerData filerData = new FilerData();
                        filerData.setId(salaryListitem.getId());
                        filerData.setName(salaryListitem.getName());
                        mFilerDataAmountList.add(filerData);
                    }
                    for (CarsRemoveInfoResponseBean.DataEntity.QueryCriteriaEntity.IsUrgentEntity isUrgentEntity : result.getData().getQueryCriteria().getIsUrgent()) {
                        FilerData filerData = new FilerData();
                        filerData.setId(isUrgentEntity.getId());
                        filerData.setName(isUrgentEntity.getName());
                        mFilerDataEmergencyList.add(filerData);
                    }
                    for (CarsRemoveInfoResponseBean.DataEntity.QueryCriteriaEntity.IsHandlingEntity isHandlingEntity : result.getData().getQueryCriteria().getIsHandling()) {
                        FilerData filerData = new FilerData();
                        filerData.setId(isHandlingEntity.getId());
                        filerData.setName(isHandlingEntity.getName());
                        mFilerDataUnLoadList.add(filerData);
                    }
                }

                mUnloadingAdapter.notifyDataSetChanged();
            }
        });
    }


    private class StevedoringAdapter extends ABaseAdapter<OrderDataInfo> {

        public StevedoringAdapter(ArrayList<OrderDataInfo> datas, Activity context) {
            super(datas, context);
        }


        @Override
        protected AbstractItemView<OrderDataInfo> newItemView() {
            return new StevedoringItemView();
        }
    }

    private class StevedoringItemView extends ABaseAdapter.AbstractItemView<OrderDataInfo> {
        @ViewInject(id = R.id.total)
        TextView viewUnloading ;
        @ViewInject(id = R.id.order_num)
        TextView mOrderNum ;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_stevedoring_unloading_group;
        }

        @Override
        public void bindingData(View convertView, OrderDataInfo data) {
            mOrderNum.setText(data.getOrderId()+"");

            //商品总计
            viewUnloading.setText(String.format("商品%d种 总计 ",data.getGoodsCount()));
            String totalPrice = String.valueOf(data.getSalary());
            SpannableString ss = new SpannableString(totalPrice);
            ss.setSpan(new ForegroundColorSpan(0xff333333), 0, totalPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new AbsoluteSizeSpan(18, true), 0, totalPrice.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            viewUnloading.append(ss);
        }
    }

    private class LoadingAdapter extends ABaseAdapter<WarehouseInfo> {
        public LoadingAdapter(ArrayList<WarehouseInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<WarehouseInfo> newItemView() {
            return new WarehouseItemView();
        }
    }

    private class WarehouseItemView extends ABaseAdapter.AbstractItemView<WarehouseInfo> {

        @ViewInject(id = R.id.stevedoring_loading_lock)
        ImageView mViewLock;
        @ViewInject(id = R.id.item_content_stevedoring_loading)
        View mViewContent;
        @ViewInject(id = R.id.stevedoring_loading_item_name)
        TextView mName;
        @ViewInject(id = R.id.stevedoring_loading_item_summary)
        TextView mSummary;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_stevedoring_loading;
        }

        @Override
        public void bindingData(View convertView, WarehouseInfo data) {
            if (data.getSpaceState() == 0||data.getOrderGoodsCount()==0) {
                mViewLock.setVisibility(View.VISIBLE);
                mViewContent.getBackground().setColorFilter(0xFFAAAAAA, PorterDuff.Mode.MULTIPLY);
            } else {
                mViewLock.setVisibility(View.GONE);
                mViewContent.getBackground().clearColorFilter();
            }
            mName.setText(data.getStorageComName());
            mSummary.setText(data.getOrderGoodsCount() + "");
        }
    }

    /***
     * 仓库信息
     */
    public class WarehouseInfo {
        private int storageGrid;
        private int seqNomber;
        private int spaceState;
        private int storageComId;
        private String storageComName;
        private int orderPrice;
        private String orderType;
        private int deditPrice;
        private int orderGoodsCount;
        private long overdueTime;
        private String goodsName;
        private int orderId;

        public void setStorageGrid(int storageGrid) {
            this.storageGrid = storageGrid;
        }

        public void setSeqNomber(int seqNomber) {
            this.seqNomber = seqNomber;
        }

        public void setSpaceState(int spaceState) {
            this.spaceState = spaceState;
        }

        public void setStorageComId(int storageComId) {
            this.storageComId = storageComId;
        }

        public void setStorageComName(String storageComName) {
            this.storageComName = storageComName;
        }

        public void setOrderPrice(int orderPrice) {
            this.orderPrice = orderPrice;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public void setDeditPrice(int deditPrice) {
            this.deditPrice = deditPrice;
        }

        public void setOrderGoodsCount(int orderGoodsCount) {
            this.orderGoodsCount = orderGoodsCount;
        }

        public void setOverdueTime(long overdueTime) {
            this.overdueTime = overdueTime;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getStorageGrid() {
            return storageGrid;
        }

        public int getSeqNomber() {
            return seqNomber;
        }

        public int getSpaceState() {
            return spaceState;
        }

        public int getStorageComId() {
            return storageComId;
        }

        public String getStorageComName() {
            return storageComName;
        }

        public int getOrderPrice() {
            return orderPrice;
        }

        public String getOrderType() {
            return orderType;
        }

        public int getDeditPrice() {
            return deditPrice;
        }

        public int getOrderGoodsCount() {
            return orderGoodsCount;
        }

        public long getOverdueTime() {
            return overdueTime;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public int getOrderId() {
            return orderId;
        }
    }

    /***
     * 订单信息
     */
    private class OrderDataInfo {
        private int orderId;
        private int goodsCount;
        private int salary ;

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }

        public int getOrderId() {
            return orderId;
        }

        public int getGoodsCount() {
            return goodsCount;
        }

        public int getSalary() {
            return salary;
        }

        public void setSalary(int salary) {
            this.salary = salary;
        }
    }

}
