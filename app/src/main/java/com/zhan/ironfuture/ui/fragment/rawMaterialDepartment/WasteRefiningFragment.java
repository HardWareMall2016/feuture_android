package com.zhan.ironfuture.ui.fragment.rawMaterialDepartment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.APullToRefreshListFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.GetGarbageListRequestBean;
import com.zhan.ironfuture.beans.GetGarbageListResponseBean;
import com.zhan.ironfuture.beans.StartWasteRefiningRequestBean;
import com.zhan.ironfuture.beans.StartWasteRefiningResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 废料精炼
 */
public class WasteRefiningFragment extends APullToRefreshListFragment<WasteRefiningFragment.WasteRefiningInfo> {

    private enum Status{PENDING,PROCESSING,FINISHED}

    private Status mStatus=Status.PENDING;

    //View
    @ViewInject(id = R.id.processing)
    TextView mViewProcessing;

    //Tools
    private CountDownTimer mTimer;

    //Data
    private ArrayList<GeneratedGoodsInfo> mGeneratedGoodsList=new ArrayList<>();

    @Override
    protected int inflateContentView() {
        return R.layout.frag_waste_refining;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("废料精炼");
    }

    @Override
    public void onDestroyView() {
        cancelTimer();
        super.onDestroyView();
    }

    @Override
    protected void setInitPullToRefresh(ListView listView, PullToRefreshListView pullToRefreshListView, Bundle savedInstanceState) {
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
    }

    @Override
    protected ABaseAdapter.AbstractItemView<WasteRefiningInfo> newItemView() {
        return new WasteRefiningItemView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Tools.isFastClick()) {
            return ;
        }
        final Dialog dialog= Tools.createDialog(getActivity(), R.layout.dialog_waste_refing);
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView confirmView=(TextView)dialog.findViewById(R.id.confirm);
        confirmView.setTag(getAdapterItems().get((int) id));
        confirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WasteRefiningInfo wasteRefiningInfo=(WasteRefiningInfo)v.getTag();
                startWasteRefiningQuery(wasteRefiningInfo);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean isContentEmpty() {
        if(mStatus==Status.PENDING||mStatus==Status.FINISHED){
            return super.isContentEmpty();
        }else{
            return false;
        }
    }

    @Override
    protected void requestData(RefreshMode mode) {
        GetGarbageListRequestBean request = new GetGarbageListRequestBean();
        GetGarbageListRequestBean.DataEntity data = new GetGarbageListRequestBean.DataEntity();
        data.setComId(UserInfo.getCurrentUser().getComId());
        request.setData(data);

        startJsonRequest(ApiUrls.GET_GARBAGE_LIST, request, new PagingTask<GetGarbageListResponseBean>(mode) {
            @Override
            public GetGarbageListResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content,GetGarbageListResponseBean.class);
            }

            @Override
            public String verifyResponseResult(GetGarbageListResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected List<WasteRefiningInfo> parseResult(GetGarbageListResponseBean baseResponseBean) {
                List<WasteRefiningInfo> beanList=new LinkedList<>();
                if(baseResponseBean != null && baseResponseBean.getData() != null){
                    for(GetGarbageListResponseBean.DataEntity.GoodsBackListEntity goods:baseResponseBean.getData().getGoodsBackList()){
                        WasteRefiningInfo wasteRefiningInfo=new WasteRefiningInfo();
                        wasteRefiningInfo.goodsId=goods.getGoodsId();
                        wasteRefiningInfo.goodsName=goods.getGoodsName();
                        wasteRefiningInfo.goodsSum=goods.getGoodsSum();
                        wasteRefiningInfo.spaceId=goods.getSpaceId();
                        beanList.add(wasteRefiningInfo);
                    }

                    mGeneratedGoodsList.clear();
                    for(GetGarbageListResponseBean.DataEntity.EndGoodsInfoEntity goods:baseResponseBean.getData().getEndGoodsInfo()){
                        GeneratedGoodsInfo goodsInfo=new GeneratedGoodsInfo();
                        goodsInfo.goodsId=goods.getGoodsId();
                        goodsInfo.goodsName=goods.getGoodsName();
                        goodsInfo.goodsSum=goods.getGoodsSum();
                        mGeneratedGoodsList.add(goodsInfo);
                    }

                    if(baseResponseBean.getData().getState()==0&&mGeneratedGoodsList.size()==0){
                        mStatus=Status.PENDING;
                    }else if(baseResponseBean.getData().getState()==0&&mGeneratedGoodsList.size()>0){
                        mStatus=Status.FINISHED;
                    }else{
                        mStatus=Status.PROCESSING;
                        long unlockTime=baseResponseBean.getData().getUnlockTime();
                        long serverTime=baseResponseBean.getServerTime();
                        long timeLeft=unlockTime-serverTime;
                        startTimer(timeLeft);
                    }
                    setVisibilityByStatus();
                }
                return beanList;
            }
        });
    }

    private void startWasteRefiningQuery(WasteRefiningInfo info){
        if(isRequestProcessing(ApiUrls.START_WASTE_REFINING)){
            return;
        }

        StartWasteRefiningRequestBean bean=new StartWasteRefiningRequestBean();
        StartWasteRefiningRequestBean.DataEntity data=new StartWasteRefiningRequestBean.DataEntity();
        bean.setData(data);
        data.setGoodsId(info.goodsId);
        data.setSpaceId(info.spaceId);

        startJsonRequest(ApiUrls.START_WASTE_REFINING,bean,new HttpRequestHandler(this){
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        StartWasteRefiningResponseBean responseBean=Tools.parseJsonTostError(result,StartWasteRefiningResponseBean.class);
                        if(responseBean!=null){
                            mStatus=Status.PROCESSING;
                            setVisibilityByStatus();
                            long unlockTime=responseBean.getData().getUnlockTime();
                            long serverTime=responseBean.getServerTime();
                            long timeLeft=unlockTime-serverTime;
                            startTimer(timeLeft);
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        });
    }

    private void setVisibilityByStatus(){
        mPullToRefreshListView.setVisibility(View.GONE);
        mViewProcessing.setVisibility(View.GONE);
        switch (mStatus){
            case PENDING:
                mPullToRefreshListView.setVisibility(View.VISIBLE);
                break;
            case PROCESSING:
                mViewProcessing.setVisibility(View.VISIBLE);
                break;
            case FINISHED:
                mPullToRefreshListView.setVisibility(View.VISIBLE);
                showWasteRefingFinishDialog();
                break;
        }
    }

    private void startTimer(long timeLeft){
        cancelTimer();
        mTimer = new CountDownTimer(timeLeft+3*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String leftTimeStr=Tools.parseTimeLeftStr(millisUntilFinished);
                mViewProcessing.setText("剩余 "+leftTimeStr);
            }

            @Override
            public void onFinish() {
                requestData();
            }
        };

        mTimer.start();
    }


    private void cancelTimer(){
        if(mTimer!=null){
            mTimer.cancel();
        }
        mTimer=null;
    }

    private void showWasteRefingFinishDialog(){
        final Dialog dialog= Tools.createDialog(getActivity(), R.layout.dialog_waste_refing_finish);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        int screenW = Tools.getScreenWidth(getActivity());
        lp.width = (int) (0.8 * screenW);

        ListView listView= (ListView)dialog.findViewById(R.id.end_goods_list);
        listView.setAdapter(new EndGoodsAdapter(mGeneratedGoodsList,getActivity()));

        dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void configRefresh(RefreshConfig config) {
        config.minResultSize=20;
    }

    private class WasteRefiningItemView extends ABaseAdapter.AbstractItemView<WasteRefiningInfo> {
        @ViewInject(id = R.id.name)
        TextView mWasteRefiningName;

        @ViewInject(id = R.id.count)
        TextView mWasteRefiningCount;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_waste_refining;
        }

        @Override
        public void bindingData(View convertView, WasteRefiningInfo data) {
            mWasteRefiningName.setText(data.goodsName);
            mWasteRefiningCount.setText(String.valueOf(data.goodsSum)+"个");
        }
    }

    private class EndGoodsAdapter extends ABaseAdapter<GeneratedGoodsInfo>{

        public EndGoodsAdapter(ArrayList<GeneratedGoodsInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<GeneratedGoodsInfo> newItemView() {
            return new GeneratedGoodsItemView();
        }
    }

    private class GeneratedGoodsItemView extends ABaseAdapter.AbstractItemView<GeneratedGoodsInfo> {
        @ViewInject(id = R.id.name)
        TextView mWasteRefiningName;

        @ViewInject(id = R.id.count)
        TextView mWasteRefiningCount;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_waste_refining_end_goods;
        }

        @Override
        public void bindingData(View convertView, GeneratedGoodsInfo data) {
            mWasteRefiningName.setText(data.goodsName);
            mWasteRefiningCount.setText(String.valueOf(data.goodsSum)+"个");
        }
    }


    public class WasteRefiningInfo{
        int spaceId;
        int goodsId;
        String goodsName;
        int goodsSum;
    }

    public class GeneratedGoodsInfo{
        int goodsId;
        String goodsName;
        int goodsSum;
    }

}
