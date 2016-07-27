package com.zhan.ironfuture.ui.fragment.managementDepartment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.ui.fragment.APullToRefreshListFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.InviteJobRequestBean;
import com.zhan.ironfuture.beans.InviteJobResponseBean;
import com.zhan.ironfuture.beans.TalentMarketQueryRequestBean;
import com.zhan.ironfuture.beans.TalentMarketQueryResponseBean;
import com.zhan.ironfuture.beans.TalentMarketSelectSeekerRequestBean;
import com.zhan.ironfuture.beans.TalentMarketSelectSeekerResponseBean;
import com.zhan.ironfuture.db.beans.TalentInformationBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 人才市场
 */
public class TalentMarketFragment extends APullToRefreshListFragment<TalentInformationBean> {

    //Views
    @ViewInject(id = R.id.talent_market_school_content, click = "OnClick")
    View mViewFilterSchoolContent;
    @ViewInject(id = R.id.talent_market_school)
    TextView mViewFilterSchool;

    @ViewInject(id = R.id.talent_market_expect_salary_content, click = "OnClick")
    View mViewFilterExpectSalaryContent;
    @ViewInject(id = R.id.talent_market_expect_salary)
    TextView mViewFilterExpectSalary;

    @ViewInject(id = R.id.talent_market_last_position_held_content, click = "OnClick")
    View mViewFilterLasPositionHeldContent;
    @ViewInject(id = R.id.talent_market_last_position_held)
    TextView mViewFilterLasPositionHeld;

    @ViewInject(id = R.id.filter_content, click = "OnClick")
    View mViewFilterContent;

    @ViewInject(id = R.id.talenfilter_list)
    ListView mViewFilterList;

    private Dialog mConformDlg;

    private TalentMarketQueryResponseBean bean;

    //Data
    private ArrayList<FilterData> mComCriteriaList = new ArrayList<>();
    private ArrayList<FilterData> mSalarCriteriaList = new ArrayList<>();
    private ArrayList<FilterData> mPostCriteriaList = new ArrayList<>();

    //Flag
    private static final int FILTER_NONE = 0;
    private static final int FILTER_SCHOOL = 1;
    private static final int FILTER_EXPECT_SALARY = 2;
    private static final int FILTER_POSITION = 3;
    private int mFilterType = FILTER_NONE;


    @Override
    protected int inflateContentView() {
        return R.layout.frag_talent_market;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle(R.string.talent_market);
        //搜索按钮
        if (getActivity() instanceof ActionBarActivity) {
            ActionBarActivity actionBarActivity = (ActionBarActivity) getActivity();
            LinearLayout rightContent = actionBarActivity.getActionBarRightContent();
            ImageView searchImgBtn = new ImageView(getActivity());
            searchImgBtn.setImageResource(R.drawable.bg_search_selector);
            rightContent.addView(searchImgBtn);
            searchImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.toast("点击");
                }
            });
        }
        mViewFilterList.setOnItemClickListener(mOnFilterItemClickListener);
        mFilterType = FILTER_NONE;
        refreshFilerStatus();

        getQueryCriteria();
    }

    private void getQueryCriteria() {
        TalentMarketQueryRequestBean requestBean = new TalentMarketQueryRequestBean();
        TalentMarketQueryRequestBean.DataEntity dataEntity = new TalentMarketQueryRequestBean.DataEntity();
        requestBean.setToken(UserInfo.getCurrentUser().getToken());
        requestBean.setUserID(UserInfo.getCurrentUser().getUserID());
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.GET_QUERYCRITERIA, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(HttpRequestHandler.ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        mSalarCriteriaList.clear();
                        FilterData allSalaryCriteria = new FilterData();
                        allSalaryCriteria.setId(-1);
                        allSalaryCriteria.setName("全部");
                        mSalarCriteriaList.add(allSalaryCriteria);

                        mComCriteriaList.clear();
                        FilterData allComCriteria = new FilterData();
                        allComCriteria.setId(-1);
                        allComCriteria.setName("全部");
                        mComCriteriaList.add(allSalaryCriteria);

                        mPostCriteriaList.clear();
                        FilterData allPostCriteria = new FilterData();
                        allPostCriteria.setId(-1);
                        allPostCriteria.setName("全部");
                        mPostCriteriaList.add(allSalaryCriteria);

                        bean = Tools.parseJsonTostError(result, TalentMarketQueryResponseBean.class);
                        if (bean != null) {
                            for (TalentMarketQueryResponseBean.DataEntity.SalarCriteriaEntity item : bean.getData().getSalarCriteria()) {
                                FilterData salarCriteria = new FilterData();
                                salarCriteria.setId(item.getId());
                                salarCriteria.setName(item.getName());
                                mSalarCriteriaList.add(salarCriteria);
                            }

                            for (TalentMarketQueryResponseBean.DataEntity.ComCriteriaEntity item : bean.getData().getComCriteria()) {
                                FilterData comCriteria = new FilterData();
                                comCriteria.setId(item.getId());
                                comCriteria.setName(item.getName());

                                mComCriteriaList.add(comCriteria);
                            }

                            for (TalentMarketQueryResponseBean.DataEntity.PostCriteriaEntity item : bean.getData().getPostCriteria()) {
                                FilterData postCriteria = new FilterData();
                                postCriteria.setId(item.getId());
                                postCriteria.setName(item.getName());

                                mPostCriteriaList.add(postCriteria);
                            }
                        }
                        break;

                }
                super.onRequestFinished(resultCode, result);
            }
        });

    }

    @Override
    protected void configRefresh(RefreshConfig config) {
        config.minResultSize = 20;
    }

    @Override
    protected ABaseAdapter.AbstractItemView<TalentInformationBean> newItemView() {
        return new TalentMarketItemView();
    }

    @Override
    protected void requestData(RefreshMode mode) {

        int startPageId;
        if (mode == RefreshMode.refresh || mode == RefreshMode.reset||isContentEmpty()) {
            startPageId = 0;
        }else{
            startPageId=getAdapterItems().get(getAdapterItems().size()-1).getTalentsId();
        }

        String bigType="";
        String searchId="";

        for(FilterData data:mComCriteriaList){
            if(data.isSelected()&&data.getId()!=-1){
                bigType="comCriteria";
                searchId=String.valueOf(data.getId());
            }
        }
        for(FilterData data:mSalarCriteriaList){
            if(data.isSelected()&&data.getId()!=-1){
                bigType="salarCriteria";
                searchId=String.valueOf(data.getId());
            }
        }
        for(FilterData data:mPostCriteriaList){
            if(data.isSelected()&&data.getId()!=-1){
                bigType="postCriteria";
                searchId=String.valueOf(data.getId());
            }
        }

        TalentMarketSelectSeekerRequestBean request = new TalentMarketSelectSeekerRequestBean();
        TalentMarketSelectSeekerRequestBean.DataEntity data = new TalentMarketSelectSeekerRequestBean.DataEntity();
        data.setPageCount(IronFutureConstants.DEF_PAGE_SIZE);
        data.setPageDirection(IronFutureConstants.PAGE_DIRECTION_DOWN);
        data.setPageId(startPageId);
        data.setBigType(bigType);
        data.setSearchKey(searchId);
        request.setData(data);

        startJsonRequest(ApiUrls.SELECTSEEKERS, request, new PagingTask<TalentMarketSelectSeekerResponseBean>(mode) {
            @Override
            public TalentMarketSelectSeekerResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, TalentMarketSelectSeekerResponseBean.class);
            }

            @Override
            public String verifyResponseResult(TalentMarketSelectSeekerResponseBean result) {
                return  Tools.verifyResponseResult(result);
            }
            @Override
            protected List<TalentInformationBean> parseResult(TalentMarketSelectSeekerResponseBean selectSeekerResponseBean) {
                List<TalentInformationBean> talentInformationList = new ArrayList<>();

                if(selectSeekerResponseBean != null && selectSeekerResponseBean.getData() != null){
                    for (TalentMarketSelectSeekerResponseBean.DataEntity item : selectSeekerResponseBean.getData()) {
                        TalentInformationBean talentInformationBean = new TalentInformationBean();
                        talentInformationBean.setTalentsId(item.getTalentsId());
                        talentInformationBean.setAnticipateSalary(item.getAnticipateSalary());
                        talentInformationBean.setPersonId(item.getPersonId());
                        talentInformationBean.setPostId(item.getPostId());
                        talentInformationBean.setComId(item.getComId());
                        talentInformationBean.setDeptId(item.getDeptId());
                        talentInformationBean.setRealSalaryLast(item.getRealSalaryLast());
                        talentInformationBean.setPeopleJobState(item.getPeopleJobState());
                        talentInformationBean.setDepartureTime(item.getDepartureTime());
                        talentInformationBean.setCreateTime(item.getCreateTime());
                        talentInformationBean.setDepartName(item.getDepartName());
                        talentInformationBean.setComName(item.getComName());
                        talentInformationBean.setPostName(item.getPostName());
                        talentInformationBean.setAccountName(item.getAccountName());
                        talentInformationBean.setPeopleName(item.getPeopleName());
                        talentInformationBean.setSchoolId(item.getSchoolId());
                        talentInformationBean.setImgUrl(item.getImgUrl());

                        talentInformationList.add(talentInformationBean);
                    }

                }
                return talentInformationList;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Tools.isFastClick()) {
            return ;
        }
        TalentInformationBean data=getAdapterItems().get((int) id);
        mConformDlg=Tools.showConfirmDialog(getActivity(), "邀请", String.format("确认邀请%s加入公司?", data.getPeopleName()), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TalentInformationBean data = (TalentInformationBean) v.getTag();
                showRotateProgressDialog("邀请中...", true);
                JoinJob(data.getTalentsId());
            }
        }, data);
    }

    private void JoinJob(int inviteId) {
        InviteJobRequestBean requestBean = new InviteJobRequestBean();
        InviteJobRequestBean.DataEntity dataEntity = new InviteJobRequestBean.DataEntity();
        dataEntity.setInviteId(inviteId);
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.INVITE_JOB, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                closeRotateProgressDialog();
                switch (resultCode) {
                    case success:
                        InviteJobResponseBean responseBean = Tools.parseJsonTostError(result, InviteJobResponseBean.class);
                        if (responseBean != null) {
                            ToastUtils.toast(responseBean.getMessage());
                            if (mConformDlg != null) {
                                mConformDlg.dismiss();
                                mConformDlg = null;
                            }
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });

    }

    private class TalentMarketItemView extends ABaseAdapter.AbstractItemView<TalentInformationBean> {

        @ViewInject(id = R.id.name)
        TextView name;
        @ViewInject(id = R.id.job)
        TextView job;
        @ViewInject(id = R.id.expect_salary)
        TextView salary;
        @ViewInject(id = R.id.school)
        TextView school;
        @ViewInject(id = R.id.user_img)
        ImageView userImg;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_talent_market;
        }

        @Override
        public void bindingData(View convertView, TalentInformationBean data) {
            name.setText(data.getPeopleName());
            job.setText(data.getPostName());
            salary.setText(data.getRealSalaryLast() + "");
            school.setText(data.getDepartName());
            ImageLoader.getInstance().displayImage(data.getImgUrl(), userImg, Tools.buildDisplayImageOptionsForAvatar());
        }
    }

    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.talent_market_school_content:
                if(mFilterType != FILTER_SCHOOL){
                    mFilterType = FILTER_SCHOOL;
                }else{
                    mFilterType = FILTER_NONE;
                }
                break;
            case R.id.talent_market_expect_salary_content:
                if(mFilterType != FILTER_EXPECT_SALARY){
                    mFilterType = FILTER_EXPECT_SALARY;
                }else{
                    mFilterType = FILTER_NONE;
                }
                break;
            case R.id.talent_market_last_position_held_content:
                if(mFilterType != FILTER_POSITION){
                    mFilterType = FILTER_POSITION;
                }else{
                    mFilterType = FILTER_NONE;
                }
                break;
            case R.id.filter_content:
                mFilterType = FILTER_NONE;
                break;
        }
        refreshFilerStatus();
    }

    private void refreshFilerStatus() {
        switch (mFilterType) {
            case FILTER_NONE:
                mViewFilterSchool.setTextColor(0xff9390A5);
                mViewFilterSchool.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterExpectSalary.setTextColor(0xff9390A5);
                mViewFilterExpectSalary.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterLasPositionHeld.setTextColor(0xff9390A5);
                mViewFilterLasPositionHeld.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                break;
            case FILTER_SCHOOL:
                mViewFilterSchool.setTextColor(0xffffc500);
                mViewFilterSchool.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_small, 0);
                mViewFilterExpectSalary.setTextColor(0xff9390A5);
                mViewFilterExpectSalary.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterLasPositionHeld.setTextColor(0xff9390A5);
                mViewFilterLasPositionHeld.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                FilterAdapter comCriteriaAdapter = new FilterAdapter(mComCriteriaList, getActivity());
                mViewFilterList.setAdapter(comCriteriaAdapter);
                comCriteriaAdapter.notifyDataSetChanged();
                break;
            case FILTER_EXPECT_SALARY:
                mViewFilterSchool.setTextColor(0xff9390A5);
                mViewFilterSchool.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterExpectSalary.setTextColor(0xffffc500);
                mViewFilterExpectSalary.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_small, 0);
                mViewFilterLasPositionHeld.setTextColor(0xff9390A5);
                mViewFilterLasPositionHeld.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                FilterAdapter salarAdapter = new FilterAdapter(mSalarCriteriaList, getActivity());
                mViewFilterList.setAdapter(salarAdapter);
                salarAdapter.notifyDataSetChanged();
                break;
            case FILTER_POSITION:
                mViewFilterSchool.setTextColor(0xff9390A5);
                mViewFilterSchool.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterExpectSalary.setTextColor(0xff9390A5);
                mViewFilterExpectSalary.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_small, 0);
                mViewFilterLasPositionHeld.setTextColor(0xffffc500);
                mViewFilterLasPositionHeld.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_small, 0);
                FilterAdapter postCriteraAdapter = new FilterAdapter(mPostCriteriaList, getActivity());
                mViewFilterList.setAdapter(postCriteraAdapter);
                postCriteraAdapter.notifyDataSetChanged();
                break;
        }
        mViewFilterContent.setVisibility(mFilterType == FILTER_NONE ? View.GONE : View.VISIBLE);
    }

    private class FilterData {
        private int id;
        private String name;
        private boolean selected;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    private class FilterAdapter extends ABaseAdapter<FilterData> {
        public FilterAdapter(ArrayList<FilterData> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<FilterData> newItemView() {
            return new FilterItemView();
        }
    }

    private class FilterItemView extends ABaseAdapter.AbstractItemView<FilterData> {
        @ViewInject(id = R.id.radio)
        RadioButton mViewRadioButton;

        @ViewInject(id = R.id.talent_filter_name)
        TextView mViewFilterName;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_talent_market_filter;
        }

        @Override
        public void bindingData(View convertView, FilterData data) {
            mViewFilterName.setText(data.name);
            mViewRadioButton.setChecked(data.isSelected());
        }
    }


    private AdapterView.OnItemClickListener mOnFilterItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            for(FilterData data:mComCriteriaList){
                data.setSelected(false);
            }
            for(FilterData data:mSalarCriteriaList){
                data.setSelected(false);
            }
            for(FilterData data:mPostCriteriaList){
                data.setSelected(false);
            }
            if (mFilterType == FILTER_SCHOOL) {
                mComCriteriaList.get(position).setSelected(true);
            } else if (mFilterType == FILTER_EXPECT_SALARY) {
                mSalarCriteriaList .get(position).setSelected(true);
            } else {
               mPostCriteriaList.get(position).setSelected(true);
            }
            mFilterType = FILTER_NONE;
            refreshFilerStatus();
            requestData();
        }
    };

}
