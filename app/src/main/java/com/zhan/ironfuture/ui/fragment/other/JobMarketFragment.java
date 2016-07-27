package com.zhan.ironfuture.ui.fragment.other;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.network.HttpRequestHandler;

import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.APullToRefreshListFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.JobMarketRequestBean;
import com.zhan.ironfuture.beans.JobMarketRequestJobRequestBean;
import com.zhan.ironfuture.beans.JobMarketRequestJobResponseBean;
import com.zhan.ironfuture.beans.JobMarketResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/10.
 */
public class JobMarketFragment extends APullToRefreshListFragment<JobMarketFragment.ChildDataInfo> {

    @ViewInject(id = R.id.job_search)
    EditText mSearch;
    @ViewInject(id = R.id.filter_content, click = "OnClick")
    LinearLayout mViewFilterContent;
    @ViewInject(id = R.id.text_search,click = "OnClick")
    TextView mTextSearch ;

    @ViewInject(id = R.id.search_btn, click = "OnClick")
    View mSearchBtn;

    @ViewInject(id = R.id.talenfilter_list)
    ListView mViewFilterList;

    //Data
    private ArrayList<JobMarketInfo> mDataList = new ArrayList<>();

    private JobMarketDeptAdapter mDeptAdapter ;
    private String mSearchKey = "";
    private JobMarketInfo mSelectedJobMarketInfo;

    //Tools
    private InputMethodManager mInputMethodManager;


    @Override
    protected int inflateContentView() {
        return R.layout.frag_job_market;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("求职市场");
        mInputMethodManager=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mInputMethodManager.hideSoftInputFromWindow(mSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    requestData();
                    return true;
                }
                return false;
            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshFilerStatus();
            }
        });

        mViewFilterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedJobMarketInfo = mDataList.get((int) id);
                mViewFilterContent.setVisibility(View.GONE);
                requestData();
            }
        });

    }

    private void refreshFilerStatus() {
        mViewFilterContent.setVisibility(View.VISIBLE);
        mDeptAdapter = new JobMarketDeptAdapter(mDataList, getActivity());
        mViewFilterList.setAdapter(mDeptAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
        super.onItemClick(parent, view, position, id);
        mViewFilterContent.setVisibility(View.GONE);
    }


    private class JobMarketDeptAdapter extends ABaseAdapter<JobMarketInfo> {

        public JobMarketDeptAdapter(ArrayList<JobMarketInfo> mDataList, FragmentActivity activity) {
            super(mDataList, activity);
        }

        @Override
        protected AbstractItemView<JobMarketInfo> newItemView() {
            return new JobMarketDeptItem();
        }
    };

    private class JobMarketDeptItem extends ABaseAdapter.AbstractItemView<JobMarketInfo> {
        @ViewInject(id = R.id.radio)
        RadioButton mViewRadioButton;

        @ViewInject(id = R.id.job_market_group_name)
        TextView mJobName ;
        @Override
        public int inflateViewId() {
            return R.layout.list_item_job_market_group;
        }

        @Override
        public void bindingData(View convertView, JobMarketInfo data) {
            mJobName.setText(data.getName());
            if (mSelectedJobMarketInfo != null && mSelectedJobMarketInfo.getId() == data.id) {
                mViewRadioButton.setChecked(true);
            } else {
                mViewRadioButton.setChecked(false);
            }
        }
    }

    @Override
    public ABaseAdapter.AbstractItemView<ChildDataInfo> newItemView() {
        return new JobMarketItem();
    }

    private class JobMarketItem extends ABaseAdapter.AbstractItemView<ChildDataInfo> {

        @ViewInject(id = R.id.job_market_group_comname)
        TextView companyName ;
        @ViewInject(id = R.id.job_market_group_money)
        TextView companymoney ;
        @ViewInject(id = R.id.apply_job)
        TextView mApplyJob ;
        @ViewInject(id = R.id.job_market_post_name)
        TextView mPostName ;
        @ViewInject(id = R.id.job_market_group_icon)
        ImageView mGroupIcon ;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_job_market_child;
        }

        @Override
        public void bindingData(View convertView, final ChildDataInfo childDataInfo) {
            companyName.setText(childDataInfo.comName);
            if(childDataInfo.salary < 1000){
                companymoney.setText("薪资："+childDataInfo.salary+"");
            }else{
                companymoney.setText("薪资："+(childDataInfo.salary)/1000 +"K");
            }
            mPostName.setText("职位："+childDataInfo.postName);
            String imgUrl= childDataInfo.comLogo;
            ImageLoader.getInstance().displayImage(imgUrl, mGroupIcon, Tools.buildDisplayImageOptionsForAvatar());

            mApplyJob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewFilterContent.setVisibility(View.GONE);
                    if(UserInfo.getCurrentUser().getComId()  == 0){
                        final Dialog dialog = Tools.createDialog(getActivity(), R.layout.dialog_job_market);
                        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.findViewById(R.id.loading).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int inviteId = childDataInfo.getInviteId();
                                apply(inviteId);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }else{
                        ToastUtils.toast("你目前在职，不能加入其它公司");
                    }

                }
            });
        }
    };

    void OnClick(View view){
        switch (view.getId()){
            case R.id.text_search:
                mViewFilterContent.setVisibility(View.GONE);
                break;
            case R.id.search_btn:
                Tools.hideSoftInputFromWindow(mSearch);
                requestData();
                break;
        }
    }

    @Override
    protected void requestData(RefreshMode mode) {
        int itemCount = getAdapterItems() == null ? 1 : getAdapterItems().size();
        int index = itemCount / getRefreshConfig().minResultSize + 1;

        if (mode == RefreshMode.refresh || mode == RefreshMode.reset) {
            index = 0;
        }

        mSearchKey = mSearch.getText().toString();

        JobMarketRequestBean request = new JobMarketRequestBean();
        JobMarketRequestBean.DataEntity data = new JobMarketRequestBean.DataEntity();
        data.setPageId(index);
        data.setPageCount(getRefreshConfig().minResultSize);
        data.setPageDirection(IronFutureConstants.PAGE_DIRECTION_DOWN);
        data.setSearchKey(mSearchKey);
        data.setDeptid(mSelectedJobMarketInfo==null?0:mSelectedJobMarketInfo.getId());

        request.setData(data);

        startJsonRequest(ApiUrls.SEARCHJOB, request, new PagingTask<JobMarketResponseBean>(mode) {
            @Override
            public JobMarketResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, JobMarketResponseBean.class);
            }

            @Override
            public String verifyResponseResult(JobMarketResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected List<ChildDataInfo> parseResult(JobMarketResponseBean bean) {
                mDataList.clear();

                JobMarketInfo allDepartmentInfo = new JobMarketInfo();
                allDepartmentInfo.setId(0);
                allDepartmentInfo.setName("全部");
                mDataList.add(allDepartmentInfo);

                ArrayList<ChildDataInfo> childDataInfoList = new ArrayList<>();
                if(bean != null && bean.getData() != null){
                    mViewFilterContent.setVisibility(View.GONE);
                    for (JobMarketResponseBean.DataEntity.DeptListEntity deptitem : bean.getData().getDeptList()) {
                        JobMarketInfo departmentInfo = new JobMarketInfo();
                        departmentInfo.setId(deptitem.getId());
                        departmentInfo.setName(deptitem.getName());
                        mDataList.add(departmentInfo);
                    }
                    for (JobMarketResponseBean.DataEntity.JobComListEntity jobitem : bean.getData().getJobComList()) {
                        ChildDataInfo staffInfo = new ChildDataInfo();
                        staffInfo.setInviteId(jobitem.getInviteId());
                        staffInfo.setComId(jobitem.getComId());
                        staffInfo.setPostId(jobitem.getPostId());
                        staffInfo.setRecruitmentSum(jobitem.getRecruitmentSum());
                        staffInfo.setJobDescribe(jobitem.getJobDescribe());
                        staffInfo.setSalary(jobitem.getSalary());
                        staffInfo.setRecState(jobitem.getRecState());
                        staffInfo.setPubTime(jobitem.getPubTime());
                        staffInfo.setComName(jobitem.getComName());
                        staffInfo.setPostName(jobitem.getPostName());
                        staffInfo.setComLogo(jobitem.getComLogo());
                        childDataInfoList.add(staffInfo);
                    }

                }
                return childDataInfoList ;
            }
        });
    }

    private void apply(int inviteId){
        JobMarketRequestJobRequestBean requestBean = new JobMarketRequestJobRequestBean();
        JobMarketRequestJobRequestBean.DataEntity dataEntity = new JobMarketRequestJobRequestBean.DataEntity();
        dataEntity.setInviteId(inviteId);
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.REQUESTJOB, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        JobMarketRequestJobResponseBean responseBean= Tools.parseJsonTostError(result, JobMarketRequestJobResponseBean.class);
                        if(responseBean!=null){
                            ToastUtils.toast(responseBean.getMessage());
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }

    public class JobMarketInfo {
        private int id;
        private String name;

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
    }

    public class ChildDataInfo {
        private int inviteId;
        private int comId;
        private int postId;
        private int recruitmentSum;
        private String jobDescribe;
        private double salary;
        private int recState;
        private long pubTime;
        private String comName;
        private String comLogo;
        private String postName;

        public int getInviteId() {
            return inviteId;
        }

        public void setInviteId(int inviteId) {
            this.inviteId = inviteId;
        }

        public int getComId() {
            return comId;
        }

        public void setComId(int comId) {
            this.comId = comId;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public int getRecruitmentSum() {
            return recruitmentSum;
        }

        public void setRecruitmentSum(int recruitmentSum) {
            this.recruitmentSum = recruitmentSum;
        }

        public String getJobDescribe() {
            return jobDescribe;
        }

        public void setJobDescribe(String jobDescribe) {
            this.jobDescribe = jobDescribe;
        }

        public double getSalary() {
            return salary;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public int getRecState() {
            return recState;
        }

        public void setRecState(int recState) {
            this.recState = recState;
        }

        public long getPubTime() {
            return pubTime;
        }

        public void setPubTime(long pubTime) {
            this.pubTime = pubTime;
        }

        public String getComName() {
            return comName;
        }

        public void setComName(String comName) {
            this.comName = comName;
        }

        public String getComLogo() {
            return comLogo;
        }

        public void setComLogo(String comLogo) {
            this.comLogo = comLogo;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }
    }
}
