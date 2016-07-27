package com.zhan.ironfuture.ui.fragment.storageProcurementDepartment;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.BaseResponseBean;
import com.zhan.ironfuture.beans.DiscardGoodsRequestBean;
import com.zhan.ironfuture.beans.StoreManageInfoRequestBean;
import com.zhan.ironfuture.beans.StoreManageInfoResponseBean;
import com.zhan.ironfuture.beans.StoreManageMoveRequestBean;
import com.zhan.ironfuture.beans.StoreManageMoveResponseBean;
import com.zhan.ironfuture.beans.StoreManageUpgradeRequestBean;
import com.zhan.ironfuture.beans.StoreManageUpgradeResponseBean;
import com.zhan.ironfuture.beans.WarehouseInfo;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.fragment.ordersCenter.OrderDetailFragment;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;

/**
 * Created by WuYue on 2016/4/21.
 * 仓库管理
 */
public class StoreManagementFragment extends ABaseFragment {

    private final static int REQUEST_CODE_SALE=100;

    @ViewInject(id = R.id.store_manage_gridView)
    PullToRefreshGridView mPullToRefreshGridView;

    private GridView mGridView;

    //Views
    private TextView mViewLeftActionMenu;
    private TextView mViewRightActionMenu;
    private Dialog mDialog;
    private Dialog mRemoveConfirmDialog;

    //Tools
    private LayoutInflater mInflater;

    private StoreManageAdapter mGridViewAdapter;
    private ArrayList<WarehouseInfo> mStoreList = new ArrayList<>();
    private int mPrice ;

    //Flag
    final static int MODE_NORMAL = 1;//正常:选择-采购
    final static int MODE_SALE = 2;//选择出售:取消-出售
    final static int MODE_EDIT = 3;//编辑: 空-完成

    private int mMode = MODE_NORMAL;


    //拖动相关
    /**
     * DragGridView自动向下滚动的边界值
     */
    private int mDownScrollBorder;

    private int mNowSpaceId ;
    private int mAfterSpaceId ;
    private int nowSpaceId ;
    private int fromtoSpaceId;
    private EditText mInputNumber ;

    /**
     * DragGridView自动向上滚动的边界值
     */
    private int mUpScrollBorder;
    /**
     * DragGridView自动滚动的速度
     */
    private static final int SPEED = PixelUtils.dp2px(5);

    private int mScrollToPosition = -1;
    private int mScrollOffsetTop = 0;


    @Override
    protected int inflateContentView() {
        return R.layout.frag_store_management;
    }


    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        mInflater = inflater;
        if (getActivity() instanceof ActionBarActivity) {
            ActionBarActivity actionBarActivity = (ActionBarActivity) getActivity();
            actionBarActivity.setShowActionBarCustomerContent(true);
            FrameLayout actionbarContent = actionBarActivity.getActionBarCustomerContent();

            actionbarContent.removeAllViews();
            View headerLayout = mInflater.inflate(R.layout.header_store_manage, null);
            mViewLeftActionMenu = (TextView) headerLayout.findViewById(R.id.left_menu);
            mViewRightActionMenu = (TextView) headerLayout.findViewById(R.id.right_menu);
            mViewLeftActionMenu.setOnClickListener(mOnActionMenuClick);
            mViewRightActionMenu.setOnClickListener(mOnActionMenuClick);
            actionbarContent.addView(headerLayout);

        }

        init();

        mPullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>(){

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {

            }
        });

        mGridView=mPullToRefreshGridView.getRefreshableView();

        mGridViewAdapter = new StoreManageAdapter(mStoreList, getActivity());
        mGridView.setAdapter(mGridViewAdapter);
        mGridView.setOnItemLongClickListener(mOnItemLongClickListener);
        mGridView.setOnDragListener(mOnDragListener);
        mGridView.setOnItemClickListener(mOnItemClickListener);

        refreshViewByMode();
    }

    private void init(){
        mStoreList.clear();
        mMode = MODE_NORMAL;
    }

    @Override
    public boolean isContentEmpty() {
        return mStoreList.size()==0;
    }

    private View.OnClickListener mOnActionMenuClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_menu:
                    switch (mMode) {
                        case MODE_NORMAL:
                            mMode = MODE_SALE;
                            break;
                        case MODE_SALE:
                            mMode = MODE_NORMAL;
                            break;
                    }
                    refreshViewByMode();
                    break;
                case R.id.right_menu:
                    switch (mMode) {
                        case MODE_NORMAL:
                            StorePurchaseOrderFragment.launcher(getActivity());
                            break;
                        case MODE_SALE:
                            ArrayList<WarehouseInfo> saleList = new ArrayList<>();
                            for(WarehouseInfo info:mStoreList){
                                if(info.selected){
                                    saleList.add(info);
                                }
                            }
                            if(saleList.size()>0){
                                StoreTradeOrderFragment.launcherForResult(StoreManagementFragment.this, REQUEST_CODE_SALE, saleList);
                            }else{
                                ToastUtils.toast("没有选择任何仓库");
                            }
                            break;
                        case MODE_EDIT:
                            mMode = MODE_NORMAL;
                            refreshViewByMode();
                            break;
                    }
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SALE && resultCode == Activity.RESULT_OK) {
            mMode = MODE_NORMAL;
            refreshViewByMode();
            requestData();
        }
    }

    private void refreshViewByMode() {
        switch (mMode) {
            case MODE_NORMAL:
                mViewLeftActionMenu.setVisibility(View.VISIBLE);
                mViewLeftActionMenu.setText("选择");
                mViewRightActionMenu.setText("采购");
                break;
            case MODE_SALE:
                mViewLeftActionMenu.setVisibility(View.VISIBLE);
                mViewLeftActionMenu.setText("取消");
                mViewRightActionMenu.setText("出售");
                for(WarehouseInfo info:mStoreList){
                    info.selected=false;
                }
                break;
            case MODE_EDIT:
                mViewLeftActionMenu.setVisibility(View.GONE);
                mViewRightActionMenu.setText("完成");
                break;
        }
        mGridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestData() {
        StoreManageInfoRequestBean request = new StoreManageInfoRequestBean();
        StoreManageInfoRequestBean.DataEntity data = new StoreManageInfoRequestBean.DataEntity();
        request.setData(data);
        startJsonRequest(ApiUrls.WAREHOUSEINFO, request, new BaseHttpRequestTask<StoreManageInfoResponseBean>() {
            @Override
            public StoreManageInfoResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, StoreManageInfoResponseBean.class);
            }

            @Override
            public String verifyResponseResult(StoreManageInfoResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(StoreManageInfoResponseBean bean) {
                super.onSuccess(bean);
                mStoreList.clear();
                mPrice = bean.getData().getStoragePrice();
                for (StoreManageInfoResponseBean.DataEntity.StorageListEntity item : bean.getData().getStorageList()) {
                    WarehouseInfo data = new WarehouseInfo();
                    data.setSpaceId(item.getSpaceId());
                    data.setStorageId(item.getStorageId());
                    data.setSpaceType(item.getSpaceType());
                    data.setGoodsId(item.getGoodsId());
                    data.setGoodsCount(item.getGoodsCount());
                    data.setSpaceSeqNo(item.getSpaceSeqNo());
                    data.setSpaceState(item.getSpaceState());
                    data.setPersonId(item.getPersonId());
                    data.setUnlockTime(item.getUnlockTime());
                    data.setStorageLevel(item.getStorageLevel());
                    data.setCompId(item.getCompId());
                    data.setStorageState(item.getStorageState());
                    data.setSpaceStateInt(item.getSpaceStateInt());
                    data.setCreateTime(item.getCreateTime());
                    data.setModifyTime(item.getModifyTime());
                    data.setComName(item.getComName());
                    data.setGoodsName(item.getGoodsName());
                    data.setGoodsType(item.getGoodsType());

                    mStoreList.add(data);
                }

                //最后添加一个空间,表示添加
                WarehouseInfo data = new WarehouseInfo();
                mStoreList.add(data);

                mGridViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                super.onRequestFinished(resultCode, result);
                mPullToRefreshGridView.onRefreshComplete();
            }
        });
    }

    private class StoreManageAdapter extends ABaseAdapter<WarehouseInfo> {
        public static final int ITEM_VIEW_TYPE_NORMAL = 0;
        public static final int ITEM_VIEW_TYPE_ADD = 1;
        private static final int ITEM_VIEW_TYPE_COUNT = ITEM_VIEW_TYPE_ADD + 1;

        public StoreManageAdapter(ArrayList<WarehouseInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<WarehouseInfo> newItemView() {
            return new StoreManageItemView();
        }

        @Override
        public int getViewTypeCount() {
            return ITEM_VIEW_TYPE_COUNT;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < getCount() - 1) {
                return ITEM_VIEW_TYPE_NORMAL;
            } else {
                return ITEM_VIEW_TYPE_ADD;
            }
        }
    }

    private class StoreManageItemView extends ABaseAdapter.AbstractItemView<WarehouseInfo> {
        @ViewInject(id = R.id.item_content)
        private View mViewContent;

        /*@ViewInject(id = R.id.title)
        private TextView mViewTitle;*/

        @ViewInject(id = R.id.goods_icon)
        private ImageView mViewGoodsType;

        @ViewInject(id = R.id.summary)
        private TextView mViewSummary;

        @ViewInject(id = R.id.count)
        private TextView mViewCount;

        @ViewInject(id = R.id.lock)
        private ImageView mViewLock;

        @ViewInject(id = R.id.icon_remove)
        private ImageView mViewRemove;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_store_manage;
        }

        @Override
        public void bindingView(View convertView) {
            super.bindingView(convertView);
            RelativeLayout.LayoutParams lp=(RelativeLayout.LayoutParams)mViewRemove.getLayoutParams();
            lp.leftMargin=-PixelUtils.dp2px(15);
            lp.topMargin=-PixelUtils.dp2px(15);
        }

        @Override
        public void bindingData(View convertView, WarehouseInfo data) {
            convertView.setVisibility(View.VISIBLE);
            mViewRemove.setVisibility(View.GONE);

            mViewCount.setText(data.getGoodsCount() + "");
            mViewSummary.setText(data.getGoodsName());
            switch (getItemViewType()) {
                case StoreManageAdapter.ITEM_VIEW_TYPE_NORMAL:
                    if(data.getGoodsCount()>0){
                        mViewCount.setVisibility(View.VISIBLE);
                        mViewSummary.setVisibility(View.VISIBLE);
                        ImageLoader.getInstance().displayImage(null, mViewGoodsType, Tools.buildProductDisplayImageOptions(data.getGoodsType()));
                        mViewGoodsType.setVisibility(View.VISIBLE);
                        mViewContent.setBackgroundResource(R.drawable.bg_color4_rounded_white_content_selector);
                    }else{
                        mViewCount.setVisibility(View.GONE);
                        mViewSummary.setVisibility(View.GONE);
                        mViewGoodsType.setVisibility(View.GONE);
                        mViewContent.setBackgroundResource(R.drawable.bg_color4_dash_rounded_white_content_selector);
                    }
                    //1正常状态2订单状态3不可发起物流4可装卸
                    if(data.getSpaceStateInt()!=1){
                        mViewLock.setVisibility(View.VISIBLE);
                        mViewContent.getBackground().setColorFilter(0xFFAAAAAA, PorterDuff.Mode.MULTIPLY);
                        return;
                    }

                    mViewLock.setVisibility(View.GONE);

                    switch (mMode) {
                        case MODE_NORMAL:
                            mViewContent.getBackground().clearColorFilter();
                            break;
                        case MODE_SALE:
                            if (data.selected) {
                                mViewContent.getBackground().setColorFilter(0xffffc500, PorterDuff.Mode.MULTIPLY);
                            } else {
                                mViewContent.getBackground().clearColorFilter();
                            }
                            break;
                        case MODE_EDIT:
                            mViewContent.getBackground().clearColorFilter();
                            mViewRemove.setVisibility(data.getGoodsCount() > 0 ? View.VISIBLE : View.GONE);
                            mViewRemove.setTag(data);
                            mViewRemove.setOnClickListener(mRemoveClickListener);
                            break;
                    }

                    break;
                case StoreManageAdapter.ITEM_VIEW_TYPE_ADD:
                    if(mMode!=MODE_NORMAL){
                        convertView.setVisibility(View.GONE);
                    }else{
                        mViewGoodsType.setVisibility(View.GONE);
                        mViewCount.setVisibility(View.GONE);
                        mViewSummary.setVisibility(View.GONE);
                        mViewContent.setBackgroundResource(R.drawable.add_img);
                        mViewLock.setVisibility(View.GONE);
                    }
                    //mViewContent.getBackground().clearColorFilter();
                    break;
            }
        }
    }


    private AdapterView.OnItemLongClickListener mOnItemLongClickListener = new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            //只有正常模式和编辑模式才能拖动
            if (mMode == MODE_NORMAL || mMode == MODE_EDIT) {
                if (StoreManageAdapter.ITEM_VIEW_TYPE_NORMAL == mGridViewAdapter.getItemViewType(position)) {
                    WarehouseInfo info = (WarehouseInfo) mGridViewAdapter.getItem(position);
                    if(info.getSpaceStateInt()!=1){
                        return false;
                    }

                    if(info.getGoodsCount()<=0){
                        ToastUtils.toast("仓库中没有货物！");
                        return false;
                    }

                    if (mMode == MODE_NORMAL) {
                        mMode = MODE_EDIT;
                        refreshViewByMode();
                    }

                    mScrollToPosition = -1;

                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, info, 0);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    };

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (Tools.isFastClick()) {
                return ;
            }
            if (StoreManageAdapter.ITEM_VIEW_TYPE_NORMAL == mGridViewAdapter.getItemViewType(position)) {
                switch (mMode) {
                    case MODE_NORMAL:
                        //订单类型
                        if(mStoreList.get(position).getSpaceStateInt()==2){
                            OrderDetailFragment.launchFromWarehouse(getActivity(), mStoreList.get(position).getGoodsId());
                        }else if(mStoreList.get(position).getSpaceStateInt()==4&&"GOODS_GARBAGE".equals(mStoreList.get(position).getGoodsType())){
                            ToastUtils.toast(String.format("废料%s正在精炼中",mStoreList.get(position).getGoodsName()));
                        } else {
                            if(mStoreList.get(position).getGoodsCount()==0){
                                return;
                            }
                            final Dialog dialog = Tools.createDialog(getActivity(), R.layout.dialog_warehouse_summary_info);

                            TextView viewOwner=(TextView)dialog.findViewById(R.id.owner);
                            viewOwner.setText(mStoreList.get(position).getComName());

                            TextView viewGoodsName=(TextView)dialog.findViewById(R.id.goods_name);
                            viewGoodsName.setText(mStoreList.get(position).getGoodsName());

                            TextView viewOrderAmount=(TextView)dialog.findViewById(R.id.amount);
                            viewOrderAmount.setText(String.valueOf(mStoreList.get(position).getGoodsCount()));

                            dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                        break;
                    case MODE_SALE:
                        if (mStoreList.get(position).getSpaceStateInt()==1&&mStoreList.get(position).getGoodsCount()>0) {
                            mStoreList.get(position).selected = !mStoreList.get(position).selected;
                            mGridViewAdapter.notifyDataSetChanged();
                        }
                        break;
                    case MODE_EDIT:

                        break;
                }
            } else {
                storeUpgrade();
            }
        }
    };

    private View.OnClickListener mRemoveClickListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            WarehouseInfo data=(WarehouseInfo)v.getTag();
            mRemoveConfirmDialog=Tools.showConfirmDialog(getActivity(),"清空仓库","确定要清空仓库吗？",mRemoveDialogConfirmClick,data);
        }
    };

    private View.OnClickListener mRemoveDialogConfirmClick=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            WarehouseInfo data=(WarehouseInfo)v.getTag();
            discardGoodsQuery(data);
        }
    };

    private void discardGoodsQuery(WarehouseInfo data) {
        if (isRequestProcessing(ApiUrls.DISCARDGOODS)) {
            return;
        }
        DiscardGoodsRequestBean requestBean = new DiscardGoodsRequestBean();
        DiscardGoodsRequestBean.DataEntity requestData=new DiscardGoodsRequestBean.DataEntity();
        requestData.setAfterSpaceId(data.getSpaceId());
        requestData.setGoodsCount(data.getGoodsCount());
        requestBean.setData(requestData);

        startJsonRequest(ApiUrls.DISCARDGOODS, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        BaseResponseBean bean = Tools.parseJsonTostError(result, BaseResponseBean.class);
                        if (bean != null) {
                            //重新请求数据
                            requestData();
                            ToastUtils.toast("清空完成！");
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
                if (mRemoveConfirmDialog != null) {
                    mRemoveConfirmDialog.dismiss();
                }
            }
        });
    }


    private void storeUpgrade() {
        if(mPrice==0){
            ToastUtils.toast("已是最高等级，无需升级");
            return;
        }
        mDialog = Tools.createDialog(getActivity(), R.layout.dialog_upgrade_warehouse);

        TextView summary = (TextView) mDialog.findViewById(R.id.summary);
        summary.setText("仓库升级需要");
        SpannableString strAmount = new SpannableString(mPrice + "");
        strAmount.setSpan(new ForegroundColorSpan(Color.RED), 0, strAmount.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        summary.append(strAmount);
        summary.append(",新增4个格子,确认要升级吗？");

        TextView mCancel = (TextView) mDialog.findViewById(R.id.cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        TextView mUpgrade = (TextView) mDialog.findViewById(R.id.confirm);
        mUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgradeRequest();
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });

        mDialog.show();
    }

    private void upgradeRequest() {
        if(isRequestProcessing(ApiUrls.UPGRADING)){
            return;
        }
        StoreManageUpgradeRequestBean requestBean = new StoreManageUpgradeRequestBean();
        StoreManageUpgradeRequestBean.DataEntity data = new StoreManageUpgradeRequestBean.DataEntity();
        requestBean.setData(data);
        startJsonRequest(ApiUrls.UPGRADING, requestBean, new HttpRequestHandler(this) {

            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        StoreManageUpgradeResponseBean responseBean=Tools.parseJsonTostError(result,StoreManageUpgradeResponseBean.class);
                        if(responseBean!=null){
                            ToastUtils.toast("升级成功!");
                            requestData();
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        });
    }

    private View.OnDragListener mOnDragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int x = Math.round(event.getX());
            final int y = Math.round(event.getY());
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //获取DragGridView自动向上滚动的偏移量，小于这个值，DragGridView向下滚动
                    mDownScrollBorder = mGridView.getChildAt(0).getHeight() / 2;
                    //获取DragGridView自动向下滚动的偏移量，大于这个值，DragGridView向上滚动
                    mUpScrollBorder = mGridView.getHeight() - mGridView.getChildAt(0).getHeight() / 2;
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    int moveY = (int) event.getY();
                    if (moveY > mUpScrollBorder) {
                        mHandler.removeMessages(MSG_MOVE_DOWN);
                        if (!mHandler.hasMessages(MSG_MOVE_UP)) {
                            mHandler.sendEmptyMessage(MSG_MOVE_UP);
                        }
                    } else if (moveY < mDownScrollBorder) {
                        mHandler.removeMessages(MSG_MOVE_UP);
                        if (!mHandler.hasMessages(MSG_MOVE_DOWN)) {
                            mHandler.sendEmptyMessage(MSG_MOVE_DOWN);
                        }
                    } else {
                        mHandler.removeMessages(MSG_MOVE_DOWN);
                        mHandler.removeMessages(MSG_MOVE_UP);
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    int dropPosition = mGridView.pointToPosition(x, y);
                    if (dropPosition != -1) {
                        //最后一个不能放入
                        if(dropPosition==mStoreList.size()-1){

                        } else if(mStoreList.get(dropPosition).getSpaceStateInt()!=1){
                            ToastUtils.toast("锁定了，不能拖入!");
                        }else{
                            WarehouseInfo dragInfo =(WarehouseInfo)event.getLocalState();
                            nowSpaceId = dragInfo.getSpaceId();
                            fromtoSpaceId = mStoreList.get(dropPosition).getSpaceId();
                            if(nowSpaceId != fromtoSpaceId){
                                dropMove(dragInfo.getGoodsCount());
                            }

                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    mHandler.removeMessages(MSG_MOVE_DOWN);
                    mHandler.removeMessages(MSG_MOVE_UP);
                    break;

            }
            return true;
        }
    };

    private void dropMove(final int goodsCount) {
        mDialog = Tools.createDialog(getActivity(), R.layout.dialog_move_warehouse);
        mInputNumber = (EditText)mDialog.findViewById(R.id.loading_number);
        TextView confirm = (TextView)mDialog.findViewById(R.id.confirm);
        TextView cancel = (TextView) mDialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mInputNumber.getText().toString())){
                    ToastUtils.toast("请输入移动数量");
                    return;
                }
                if(goodsCount < Integer.parseInt(mInputNumber.getText().toString())){
                    ToastUtils.toast("输入的数量超出了");
                    return;
                }
                if(Integer.parseInt(mInputNumber.getText().toString()) < 1){
                    ToastUtils.toast("请输入正确的数量");
                    return;
                }
                move(mInputNumber.getText().toString(),nowSpaceId,fromtoSpaceId);
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private void move(String input,int nowSpaceId,int fromtoSpaceId) {
        if(isRequestProcessing(ApiUrls.MOVEGOODS)){
            return;
        }
        StoreManageMoveRequestBean requestBean = new StoreManageMoveRequestBean();
        StoreManageMoveRequestBean.DataEntity dataEntity = new StoreManageMoveRequestBean.DataEntity();
        dataEntity.setGoodsCount(input);
        dataEntity.setNowSpaceId(nowSpaceId);
        dataEntity.setAfterSpaceId(fromtoSpaceId);
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.MOVEGOODS, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        StoreManageMoveResponseBean responseBean = Tools.parseJsonTostError(result, StoreManageMoveResponseBean.class);
                        if (responseBean != null) {
                            ToastUtils.toast(responseBean.getMessage());
                            requestData();
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }

    private final static int MSG_MOVE_UP = 1;
    private final static int MSG_MOVE_DOWN = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int scrollY = 0;
            switch (msg.what) {
                case MSG_MOVE_UP:
                    scrollY = -SPEED;
                    mHandler.sendEmptyMessageDelayed(MSG_MOVE_UP, 25);
                    break;
                case MSG_MOVE_DOWN:
                    scrollY = SPEED;
                    mHandler.sendEmptyMessageDelayed(MSG_MOVE_DOWN, 25);
                    break;
            }

            int firstVisiblePosition = mGridView.getFirstVisiblePosition();
            if (mScrollToPosition == -1) {
                View view = mGridView.getChildAt(0);
                mScrollOffsetTop = view.getTop();
            } else if (mScrollToPosition != firstVisiblePosition) {
                if (msg.what == MSG_MOVE_UP) {
                    View view = mGridView.getChildAt(0);
                    int offset = Math.abs(mScrollOffsetTop + scrollY) % view.getHeight();
                    mScrollOffsetTop = -offset;
                } else {
                    View view = mGridView.getChildAt(0);
                    mScrollOffsetTop = view.getTop();
                }
            } else {
                mScrollOffsetTop += scrollY;
            }

            mScrollToPosition = firstVisiblePosition;

            mGridView.smoothScrollToPositionFromTop(mScrollToPosition, mScrollOffsetTop);
        }
    };
}
