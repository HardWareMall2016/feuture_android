package com.zhan.ironfuture.ui.fragment.storageProcurementDepartment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.beans.StorePurchaseGetGoodsRequestBean;
import com.zhan.ironfuture.beans.StorePurchaseGetGoodsResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.MyGridView;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/5/11.
 */
public class StorePurchaseChangeProductFragment extends ABaseFragment {
    public final static String KEY_PRODUCT_CHANGE = "change";
    public final static String KEY__PURCHASE_ORDER_PRODUCT = "product_name";
    public final static String KEY__PURCHASE_ORDER_ID = "product_id";

    @ViewInject(id = R.id.rl_store_purchase_order_change_one, click = "OnClick")
    RelativeLayout mOneChange;
    @ViewInject(id = R.id.rl_store_purchase_order_change_two, click = "OnClick")
    RelativeLayout mTwoChange;
    @ViewInject(id = R.id.rl_store_purchase_order_change_three, click = "OnClick")
    RelativeLayout mThreeChange;

    @ViewInject(id = R.id.store_change_gridView_one)
    MyGridView mOneGridView;
    @ViewInject(id = R.id.store_change_gridView_two)
    MyGridView mTwoGridView;
    @ViewInject(id = R.id.store_change_gridView_goods)
    MyGridView mThreeGridView;

    private boolean mflagOne = true;
    private boolean mflagTwo = true;
    private boolean mflagThree = true;

    //data
    private ArrayList<StorePurchaseChangeOneInfo> mOneInfoList = new ArrayList<>();
    private ArrayList<StorePurchaseChangeTwoInfo> mTwoInfoList = new ArrayList<>();
    private ArrayList<StorePurchaseChangeThreeInfo> mThreeInfoList = new ArrayList<>();

    private StorePurchaseChangeOneAdapter mOneAdapter;
    private StorePurchaseChangeTwoAdapter mTwoAdapter;
    private StorePurchaseChangeThreeAdapter mThreeAdapter;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_store_purchase_change;
    }

    public static void launcher(Fragment fragment, int requestCode) {
        FragmentContainerActivity.launchForResult(fragment, StorePurchaseChangeProductFragment.class, null, requestCode);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("选择物品");

        mOneAdapter = new StorePurchaseChangeOneAdapter(mOneInfoList, getActivity());
        mOneGridView.setAdapter(mOneAdapter);
        mOneGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(KEY__PURCHASE_ORDER_PRODUCT, mOneInfoList.get(position).getGoodsName());
                intent.putExtra(KEY_PRODUCT_CHANGE, "一级原料");
                intent.putExtra(KEY__PURCHASE_ORDER_ID,mOneInfoList.get(position).getGoodsId());
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });

        mTwoAdapter = new StorePurchaseChangeTwoAdapter(mTwoInfoList, getActivity());
        mTwoGridView.setAdapter(mTwoAdapter);
        mTwoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent2 = new Intent();
                intent2.putExtra(KEY__PURCHASE_ORDER_PRODUCT, mTwoInfoList.get(position).getGoodsName());
                intent2.putExtra(KEY_PRODUCT_CHANGE, "二级原料");
                intent2.putExtra(KEY__PURCHASE_ORDER_ID,mTwoInfoList.get(position).getGoodsId());
                getActivity().setResult(Activity.RESULT_OK, intent2);
                getActivity().finish();
            }
        });

        mThreeAdapter = new StorePurchaseChangeThreeAdapter(mThreeInfoList, getActivity());
        mThreeGridView.setAdapter(mThreeAdapter);
        mThreeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent3 = new Intent();
                intent3.putExtra(KEY__PURCHASE_ORDER_PRODUCT, mThreeInfoList.get(position).getGoodsName());
                intent3.putExtra(KEY_PRODUCT_CHANGE, "商品");
                intent3.putExtra(KEY__PURCHASE_ORDER_ID,mThreeInfoList.get(position).getGoodsId());
                getActivity().setResult(Activity.RESULT_OK, intent3);
                getActivity().finish();
            }
        });
    }

    @Override
    public void requestData() {
        mOneInfoList.clear();
        mTwoInfoList.clear();
        mThreeInfoList.clear();

        StorePurchaseGetGoodsRequestBean request = new StorePurchaseGetGoodsRequestBean();
        StorePurchaseGetGoodsRequestBean.DataEntity dataEntity = new StorePurchaseGetGoodsRequestBean.DataEntity();
        dataEntity.setProductSum("10");
        request.setData(dataEntity);

        startJsonRequest(ApiUrls.GETGOODSLIST, request, new BaseHttpRequestTask<StorePurchaseGetGoodsResponseBean>() {
            @Override
            public StorePurchaseGetGoodsResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, StorePurchaseGetGoodsResponseBean.class);
            }

            @Override
            public String verifyResponseResult(StorePurchaseGetGoodsResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(StorePurchaseGetGoodsResponseBean bean) {
                super.onSuccess(bean);
                for (StorePurchaseGetGoodsResponseBean.DataEntity.P1ListEntity p1ListEntity : bean.getData().getP1List()) {
                    StorePurchaseChangeOneInfo onedata = new StorePurchaseChangeOneInfo();
                    onedata.setGoodsId(p1ListEntity.getGoodsId());
                    onedata.setGoodsType(p1ListEntity.getGoodsType());
                    onedata.setGoodsName(p1ListEntity.getGoodsName());
                    onedata.setGoodsCoefficient(p1ListEntity.getGoodsCoefficient());
                    onedata.setGoodsPrice(p1ListEntity.getGoodsPrice());
                    onedata.setGoodsState(p1ListEntity.getGoodsState());
                    onedata.setGoodsLevel(p1ListEntity.getGoodsLevel());
                    onedata.setGoodsSum(p1ListEntity.getGoodsSum());
                    onedata.setSellSum(p1ListEntity.getSellSum());
                    onedata.setDescs(p1ListEntity.getDescs());
                    mOneInfoList.add(onedata);
                }

                for (StorePurchaseGetGoodsResponseBean.DataEntity.P2ListEntity p2ListEntity : bean.getData().getP2List()) {
                    StorePurchaseChangeTwoInfo twodata = new StorePurchaseChangeTwoInfo();
                    twodata.setGoodsId(p2ListEntity.getGoodsId());
                    twodata.setGoodsType(p2ListEntity.getGoodsType());
                    twodata.setGoodsName(p2ListEntity.getGoodsName());
                    twodata.setGoodsCoefficient(p2ListEntity.getGoodsCoefficient());
                    twodata.setGoodsPrice(p2ListEntity.getGoodsPrice());
                    twodata.setGoodsState(p2ListEntity.getGoodsState());
                    twodata.setGoodsLevel(p2ListEntity.getGoodsLevel());
                    twodata.setGoodsSum(p2ListEntity.getGoodsSum());
                    twodata.setSellSum(p2ListEntity.getSellSum());
                    twodata.setDescs(p2ListEntity.getDescs());
                    mTwoInfoList.add(twodata);
                }

                for (StorePurchaseGetGoodsResponseBean.DataEntity.ProductListEntity productListEntity : bean.getData().getProductList()) {
                    StorePurchaseChangeThreeInfo threedata = new StorePurchaseChangeThreeInfo();
                    threedata.setGoodsId(productListEntity.getGoodsId());
                    threedata.setGoodsType(productListEntity.getGoodsType());
                    threedata.setGoodsName(productListEntity.getGoodsName());
                    threedata.setGoodsCoefficient(productListEntity.getGoodsCoefficient());
                    threedata.setGoodsPrice(productListEntity.getGoodsPrice());
                    threedata.setGoodsState(productListEntity.getGoodsState());
                    threedata.setGoodsLevel(productListEntity.getGoodsLevel());
                    threedata.setGoodsSum(productListEntity.getGoodsSum());
                    threedata.setSellSum(productListEntity.getSellSum());
                    threedata.setDescs(productListEntity.getDescs());
                    mThreeInfoList.add(threedata);
                }

                mOneAdapter.notifyDataSetChanged();
                mTwoAdapter.notifyDataSetChanged();
                mThreeAdapter.notifyDataSetChanged();
            }
        });
    }


    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.rl_store_purchase_order_change_one:
                if (mflagOne) {
                    mOneGridView.setVisibility(View.VISIBLE);
                    mflagOne = false;
                } else {
                    mOneGridView.setVisibility(View.GONE);
                    mflagOne = true;
                }
                break;
            case R.id.rl_store_purchase_order_change_two:
                if (mflagTwo) {
                    mTwoGridView.setVisibility(View.VISIBLE);
                    mflagTwo = false;
                } else {
                    mTwoGridView.setVisibility(View.GONE);
                    mflagTwo = true;
                }
                break;
            case R.id.rl_store_purchase_order_change_three:
                if (mflagThree) {
                    mThreeGridView.setVisibility(View.VISIBLE);
                    mflagThree = false;
                } else {
                    mThreeGridView.setVisibility(View.GONE);
                    mflagThree = true;
                }
                break;
        }
    }


    private class StorePurchaseChangeOneAdapter extends ABaseAdapter<StorePurchaseChangeOneInfo> {

        public StorePurchaseChangeOneAdapter(ArrayList<StorePurchaseChangeOneInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<StorePurchaseChangeOneInfo> newItemView() {
            return new StorePurchaseChangeOneItemView();
        }
    }

    private class StorePurchaseChangeOneItemView extends ABaseAdapter.AbstractItemView<StorePurchaseChangeOneInfo> {
        @ViewInject(id = R.id.title)
        TextView mTitle ;
        @ViewInject(id = R.id.summary)
        TextView mPrice ;
        @Override
        public int inflateViewId() {
            return R.layout.list_item_store_purchase_change;
        }

        @Override
        public void bindingData(View convertView, StorePurchaseChangeOneInfo data) {
            mTitle.setText(data.getGoodsName());
            mPrice.setText(data.getGoodsPrice()+"");
        }
    }

    private class StorePurchaseChangeTwoAdapter extends ABaseAdapter<StorePurchaseChangeTwoInfo> {

        public StorePurchaseChangeTwoAdapter(ArrayList<StorePurchaseChangeTwoInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<StorePurchaseChangeTwoInfo> newItemView() {
            return new StorePurchaseChangeTwoItemView();
        }
    }

    private class StorePurchaseChangeTwoItemView extends ABaseAdapter.AbstractItemView<StorePurchaseChangeTwoInfo> {
        @ViewInject(id = R.id.title)
        TextView mTitle ;
        @ViewInject(id = R.id.summary)
        TextView mPrice ;
        @Override
        public int inflateViewId() {
            return R.layout.list_item_store_purchase_change;
        }

        @Override
        public void bindingData(View convertView, StorePurchaseChangeTwoInfo data) {
            mTitle.setText(data.getGoodsName());
            mPrice.setText(data.getGoodsPrice()+"");
        }
    }


    private class StorePurchaseChangeThreeAdapter extends ABaseAdapter<StorePurchaseChangeThreeInfo> {

        public StorePurchaseChangeThreeAdapter(ArrayList<StorePurchaseChangeThreeInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<StorePurchaseChangeThreeInfo> newItemView() {
            return new StorePurchaseChangeThreeItemView();
        }
    }

    private class StorePurchaseChangeThreeItemView extends ABaseAdapter.AbstractItemView<StorePurchaseChangeThreeInfo> {
        @ViewInject(id = R.id.title)
        TextView mTitle ;
        @ViewInject(id = R.id.summary)
        TextView mPrice ;
        @Override
        public int inflateViewId() {
            return R.layout.list_item_store_purchase_change;
        }

        @Override
        public void bindingData(View convertView, StorePurchaseChangeThreeInfo data) {
            mTitle.setText(data.getGoodsName());
            mPrice.setText(data.getGoodsPrice()+"");
        }
    }


    class StorePurchaseChangeOneInfo {
        private int goodsId;
        private String goodsType;
        private String goodsName;
        private String goodsCoefficient;
        private int goodsPrice;
        private int goodsState;
        private int goodsLevel;
        private int goodsSum;
        private int sellSum;
        private String descs;

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public void setGoodsCoefficient(String goodsCoefficient) {
            this.goodsCoefficient = goodsCoefficient;
        }

        public void setGoodsPrice(int goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public void setGoodsState(int goodsState) {
            this.goodsState = goodsState;
        }

        public void setGoodsLevel(int goodsLevel) {
            this.goodsLevel = goodsLevel;
        }

        public void setGoodsSum(int goodsSum) {
            this.goodsSum = goodsSum;
        }

        public void setSellSum(int sellSum) {
            this.sellSum = sellSum;
        }

        public void setDescs(String descs) {
            this.descs = descs;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public String getGoodsCoefficient() {
            return goodsCoefficient;
        }

        public int getGoodsPrice() {
            return goodsPrice;
        }

        public int getGoodsState() {
            return goodsState;
        }

        public int getGoodsLevel() {
            return goodsLevel;
        }

        public int getGoodsSum() {
            return goodsSum;
        }

        public int getSellSum() {
            return sellSum;
        }

        public String getDescs() {
            return descs;
        }
    }

    class StorePurchaseChangeTwoInfo {
        private int goodsId;
        private String goodsType;
        private String goodsName;
        private String goodsCoefficient;
        private int goodsPrice;
        private int goodsState;
        private int goodsLevel;
        private int goodsSum;
        private int sellSum;
        private String descs;

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public void setGoodsCoefficient(String goodsCoefficient) {
            this.goodsCoefficient = goodsCoefficient;
        }

        public void setGoodsPrice(int goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public void setGoodsState(int goodsState) {
            this.goodsState = goodsState;
        }

        public void setGoodsLevel(int goodsLevel) {
            this.goodsLevel = goodsLevel;
        }

        public void setGoodsSum(int goodsSum) {
            this.goodsSum = goodsSum;
        }

        public void setSellSum(int sellSum) {
            this.sellSum = sellSum;
        }

        public void setDescs(String descs) {
            this.descs = descs;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public String getGoodsCoefficient() {
            return goodsCoefficient;
        }

        public int getGoodsPrice() {
            return goodsPrice;
        }

        public int getGoodsState() {
            return goodsState;
        }

        public int getGoodsLevel() {
            return goodsLevel;
        }

        public int getGoodsSum() {
            return goodsSum;
        }

        public int getSellSum() {
            return sellSum;
        }

        public String getDescs() {
            return descs;
        }
    }

    class StorePurchaseChangeThreeInfo {
        private int goodsId;
        private String goodsType;
        private String goodsName;
        private String goodsCoefficient;
        private int goodsPrice;
        private int goodsState;
        private int goodsLevel;
        private int goodsSum;
        private int sellSum;
        private String descs;

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public void setGoodsCoefficient(String goodsCoefficient) {
            this.goodsCoefficient = goodsCoefficient;
        }

        public void setGoodsPrice(int goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public void setGoodsState(int goodsState) {
            this.goodsState = goodsState;
        }

        public void setGoodsLevel(int goodsLevel) {
            this.goodsLevel = goodsLevel;
        }

        public void setGoodsSum(int goodsSum) {
            this.goodsSum = goodsSum;
        }

        public void setSellSum(int sellSum) {
            this.sellSum = sellSum;
        }

        public void setDescs(String descs) {
            this.descs = descs;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public String getGoodsCoefficient() {
            return goodsCoefficient;
        }

        public int getGoodsPrice() {
            return goodsPrice;
        }

        public int getGoodsState() {
            return goodsState;
        }

        public int getGoodsLevel() {
            return goodsLevel;
        }

        public int getGoodsSum() {
            return goodsSum;
        }

        public int getSellSum() {
            return sellSum;
        }

        public String getDescs() {
            return descs;
        }
    }


}
