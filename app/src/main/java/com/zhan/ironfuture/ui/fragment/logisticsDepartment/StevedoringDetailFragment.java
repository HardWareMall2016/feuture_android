package com.zhan.ironfuture.ui.fragment.logisticsDepartment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.beans.StevedoringDetailAddLoadRequestBean;
import com.zhan.ironfuture.beans.StevedoringDetailAddLoadResponseBean;
import com.zhan.ironfuture.beans.StevedoringDetailContent;
import com.zhan.ironfuture.beans.StevedoringDetailRequestBean;
import com.zhan.ironfuture.beans.StevedoringDetailResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class StevedoringDetailFragment extends ABaseFragment {
    private final static String ARG_KEY = "stevedoringdetail";

    @ViewInject(id = R.id.lv_stevedoring_detail)
    ListView mListView;
    @ViewInject(id = R.id.stevedoring_detail_ordernum)
    TextView mDetailNum;
    @ViewInject(id = R.id.stevedoring_detail_money)
    TextView mDetailMoney;
    @ViewInject(id = R.id.stevedoring_detail_data)
    TextView mDetailData;
    @ViewInject(id = R.id.stevedoring_detail_outdata_money)
    TextView mOutDataMoney;
    @ViewInject(id = R.id.stevedoring_detail_place)
    TextView mDetailPlace;

    private ArrayList<StevedoringDetailInfo> mDataList = new ArrayList<>();
    private StevedoringDetailAdapter mAdapter;

    private StevedoringDetailContent content;
    private int mOrderId;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_stevedoring_detail;
    }

    public static void launch(Fragment fragment, StevedoringDetailContent content,int requestCode) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launchForResult(fragment, StevedoringDetailFragment.class, args, requestCode);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle(content.getTitle());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = savedInstanceState == null ? (StevedoringDetailContent) getArguments().getSerializable(ARG_KEY)
                : (StevedoringDetailContent) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, content);
    }

    @Override
    public void onPrepareActionbarMenu(TextView menu, Activity activity) {
        super.onPrepareActionbarMenu(menu, activity);
        menu.setText("确定");
    }

    @Override
    public void onActionBarMenuClick() {
        if (isRequestProcessing(ApiUrls.ADDLOADING)) {
            return;
        }
        StevedoringDetailAddLoadRequestBean requestBean = new StevedoringDetailAddLoadRequestBean();
        List<StevedoringDetailAddLoadRequestBean.DataEntity> dataEntityList = new ArrayList<>();
        for (int i = 0; i < mDataList.size(); i++) {

            StevedoringDetailAddLoadRequestBean.DataEntity dataEntity = new StevedoringDetailAddLoadRequestBean.DataEntity();
            dataEntity.setAddSum(mDataList.get(i).getSelelctSum());
            dataEntity.setOrderId(mOrderId + "");
            dataEntity.setGoodsId(mDataList.get(i).getGoodsId() + "");
            dataEntity.setSpaceId(mDataList.get(i).getSpaceId() + "");
            dataEntity.setCarId(content.getCarId() + "");
            dataEntityList.add(dataEntity);
        }
        requestBean.setData(dataEntityList);
        startJsonRequest(ApiUrls.ADDLOADING, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        StevedoringDetailAddLoadResponseBean bean = Tools.parseJsonTostError(result, StevedoringDetailAddLoadResponseBean.class);
                        if (bean != null) {
                            ToastUtils.toast(bean.getMessage());
                            Intent intent = new Intent();
                            //intent.putExtra(StevedoringFragment.KEY_PURCHASE_ORDER_COMPANY, 1);
                            getActivity().setResult(Activity.RESULT_OK, intent);
                            getActivity().finish();
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }

    @Override
    public void requestData() {
        if(isRequestProcessing(ApiUrls.GETCARSADDINFO)){
            return;
        }
        StevedoringDetailRequestBean requestBean = new StevedoringDetailRequestBean();
        StevedoringDetailRequestBean.DataEntity dataEntity = new StevedoringDetailRequestBean.DataEntity();
        dataEntity.setOrderId(content.getOrderId());
        dataEntity.setState(1);
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.GETCARSADDINFO, requestBean, new BaseHttpRequestTask<StevedoringDetailResponseBean>() {

                    @Override
                    public StevedoringDetailResponseBean parseResponseToResult(String content) {
                        return Tools.parseJson(content, StevedoringDetailResponseBean.class);
                    }

                    @Override
                    public String verifyResponseResult(StevedoringDetailResponseBean result) {
                        return Tools.verifyResponseResult(result);
                    }

                    @Override
                    protected void onSuccess(StevedoringDetailResponseBean bean) {
                        super.onSuccess(bean);
                        if (bean != null && bean.getData() != null) {
                            mDetailNum.setText(bean.getData().getGoodsBack().getOrderId() + "");
                            mDetailMoney.setText(bean.getData().getGoodsBack().getTotalAmount() + "");
                            mDetailData.setText(Tools.parseTime(bean.getData().getGoodsBack().getRemainingTime()));
                            mOutDataMoney.setText(bean.getData().getGoodsBack().getPenalty() + "");
                            mDetailPlace.setText("货物从" + bean.getData().getGoodsBack().getProductAddressName() + "运送到" + bean.getData().getGoodsBack().getDestinationName());
                            mOrderId = bean.getData().getGoodsBack().getOrderId();
                            mDataList.clear();

                            for (StevedoringDetailResponseBean.DataEntity.GoodsBackEntity.GoodsBackLsitEntity item : bean.getData().getGoodsBack().getGoodsBackLsit()) {
                                StevedoringDetailInfo stevedoringDetailInfo = new StevedoringDetailInfo();
                                stevedoringDetailInfo.setGoodsId(item.getGoodsId());
                                stevedoringDetailInfo.setGoodsName(item.getGoodsName());
                                stevedoringDetailInfo.setGoodsSum(item.getGoodsSum());
                                stevedoringDetailInfo.setSpaceId(item.getSpaceId());
                                stevedoringDetailInfo.setRecipeId(item.getRecipeId());
                                stevedoringDetailInfo.setSelelctSum(item.getGoodsSum());
                                mDataList.add(stevedoringDetailInfo);
                            }
                            mAdapter = new StevedoringDetailAdapter(mDataList, getActivity());
                            mListView.setAdapter(mAdapter);
                        }
                    }
                }
        );
    }


    private class StevedoringDetailAdapter extends ABaseAdapter<StevedoringDetailInfo> {

        public StevedoringDetailAdapter(ArrayList datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView newItemView() {
            return new StevedoringDetailItemView();
        }
    }

    private class StevedoringDetailItemView extends ABaseAdapter.AbstractItemView<StevedoringDetailInfo> {

        @ViewInject(id = R.id.stevedoring_detail_productname)
        TextView mProductName;
        @ViewInject(id = R.id.stevedoring_detail_goods_num)
        TextView mGoodsNum;
        @ViewInject(id = R.id.stevedoring_detail_allgoods_num)
        TextView mAllGoodsNum;
        @ViewInject(id = R.id.seekbar)
        SeekBar mSeekBar;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_stevedoring_detail;
        }

        @Override
        public void bindingData(View convertView, final StevedoringDetailInfo data) {
            mProductName.setText("货物：" + data.getGoodsName());
            mAllGoodsNum.setText(data.getGoodsSum() + "");
            mGoodsNum.setText(data.getGoodsSum() + "");
            mSeekBar.setMax(data.getGoodsSum());
            mSeekBar.setProgress(data.getSelelctSum());
            mGoodsNum.setText(String.valueOf(data.getSelelctSum()));
            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    data.setSelelctSum(progress);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }
    }

    private class StevedoringDetailInfo {
        private int goodsId;
        private String goodsName;
        private int goodsSum;
        private int spaceId;
        private int recipeId;
        private int selelctSum;

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public void setGoodsSum(int goodsSum) {
            this.goodsSum = goodsSum;
        }

        public void setSpaceId(int spaceId) {
            this.spaceId = spaceId;
        }

        public void setRecipeId(int recipeId) {
            this.recipeId = recipeId;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public int getGoodsSum() {
            return goodsSum;
        }

        public int getSpaceId() {
            return spaceId;
        }

        public int getRecipeId() {
            return recipeId;
        }

        public int getSelelctSum() {
            return selelctSum;
        }

        public void setSelelctSum(int selelctSum) {
            this.selelctSum = selelctSum;
        }
    }
}
