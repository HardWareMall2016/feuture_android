package com.zhan.ironfuture.ui.fragment.storageProcurementDepartment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.APullToRefreshListFragment;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.GetInOutStorageInfoRequestBean;
import com.zhan.ironfuture.beans.GetInOutStorageInfoResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 仓储进出记录
 */
public class StorageRecordFragment extends APullToRefreshListFragment<StorageRecordFragment.StorageIOInfo> {

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("仓储进出记录");
    }

    @Override
    protected ABaseAdapter.AbstractItemView<StorageIOInfo> newItemView() {
        return new StorageIOItemView();
    }

    @Override
    protected void configRefresh(RefreshConfig config) {
        config.minResultSize=IronFutureConstants.DEF_PAGE_SIZE;
    }

    @Override
    protected void requestData(RefreshMode mode) {
        int startPageId;
        if (mode == RefreshMode.refresh || mode == RefreshMode.reset||isContentEmpty()) {
            startPageId = 0;
        }else{
            startPageId=getAdapterItems().get(getAdapterItems().size()-1).actionId;
        }

        GetInOutStorageInfoRequestBean requestBean=new GetInOutStorageInfoRequestBean();
        GetInOutStorageInfoRequestBean.DataEntity data=new GetInOutStorageInfoRequestBean.DataEntity();
        requestBean.setData(data);
        data.setComId(UserInfo.getCurrentUser().getComId());
        data.setPageCount(IronFutureConstants.DEF_PAGE_SIZE);
        data.setPageDirection(IronFutureConstants.PAGE_DIRECTION_DOWN);
        data.setPageId(startPageId);

        startJsonRequest(ApiUrls.STORAGE_IO_INFO, requestBean, new PagingTask<GetInOutStorageInfoResponseBean>(mode) {
            @Override
            public GetInOutStorageInfoResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, GetInOutStorageInfoResponseBean.class);
            }

            @Override
            public String verifyResponseResult(GetInOutStorageInfoResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected List<StorageIOInfo> parseResult(GetInOutStorageInfoResponseBean getInOutStorageInfoResponseBean) {
                List<StorageIOInfo> dataList = new ArrayList<>();

                if (getInOutStorageInfoResponseBean != null && getInOutStorageInfoResponseBean.getData().getLogList() != null) {
                    for (GetInOutStorageInfoResponseBean.DataEntity.LogListEntity log : getInOutStorageInfoResponseBean.getData().getLogList()) {
                        StorageIOInfo storageIOInfo = new StorageIOInfo();
                        storageIOInfo.actionId=log.getActionId();
                        storageIOInfo.actionType=log.getActionType();
                        storageIOInfo.userId=log.getUserId();
                        storageIOInfo.descs=log.getDescs();
                        storageIOInfo.counts=log.getCounts();
                        storageIOInfo.eventTime=log.getEventTime();
                        storageIOInfo.accountName=log.getAccountName();
                        storageIOInfo.peopleName=log.getPeopleName();
                        storageIOInfo.codeNameDesc=log.getCodeNameDesc();

                        dataList.add(storageIOInfo);
                    }
                }

                return dataList;
            }
        });

    }

    private class StorageIOItemView extends ABaseAdapter.AbstractItemView<StorageIOInfo>{
        @ViewInject(id = R.id.summary)
        TextView mViewSummary;

        @ViewInject(id = R.id.people)
        TextView mViewPeople;

        @ViewInject(id = R.id.time)
        TextView mViewTime;


        @Override
        public int inflateViewId() {
            return R.layout.list_item_storage_io_info;
        }

        @Override
        public void bindingData(View convertView, StorageIOInfo data) {
            mViewSummary.setText(data.descs);
            mViewPeople.setText(data.peopleName);
            mViewTime.setText(Tools.parseTime(data.eventTime));
        }
    }


    public class StorageIOInfo{
        int actionId;
        String actionType;
        int userId;
        String descs;
        int counts;
        long eventTime;
        String accountName;
        String peopleName;
        String codeNameDesc;
    }

}
