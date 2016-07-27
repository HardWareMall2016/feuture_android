package com.zhan.ironfuture.ui.fragment.more;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.QueryPersonListRequestBean;
import com.zhan.ironfuture.beans.QueryPersonListResponseBean;
import com.zhan.ironfuture.beans.StaffListContent;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.PinnedHeaderExpandableListView;
import com.zhan.ironfuture.ui.widget.SideBar;
import com.zhan.ironfuture.utils.Tools;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 人员列表
 */
public class StaffListFragment extends ABaseFragment implements PinnedHeaderExpandableListView.OnHeaderUpdateListener {

    private final static int REQUEST_CODE_CHANGE_POST=100;

    @ViewInject(id = R.id.company_list)
    PinnedHeaderExpandableListView mExpandableListView;

    @ViewInject(id = R.id.staff_list_sidrbar)
    SideBar mSideBar ;

    @ViewInject(id = R.id.search)
    EditText mViewSearch ;

    @ViewInject(id = R.id.search_btn, click = "OnClick")
    View mSearchBtn;

    private ExpandableAdapter mAdapter;
    private LayoutInflater mInflater;
    private InputMethodManager mInputMethodManager;

    private String mSearchKey;

    //Data
    private List<GroupDataInfo> mDataList = new LinkedList<>();

    @Override
    protected int inflateContentView() {
        return R.layout.frag_staff_list;
    }

    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                Tools.hideSoftInputFromWindow(mViewSearch);
                mSearchKey = mViewSearch.getText().toString();
                requestData();
                break;
        }
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("人员列表");
        mInflater = inflater;
        mAdapter = new ExpandableAdapter();
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setGroupIndicator(null);
        //不能点击收缩
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        mExpandableListView.setOnHeaderUpdateListener(this);

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                StaffListContent content = new StaffListContent();
                content.setPersonId(mDataList.get(groupPosition).staffInfoList.get(childPosition).personId);
                content.setName(mDataList.get(groupPosition).staffInfoList.get(childPosition).accountName);
                content.setPersonLogo(mDataList.get(groupPosition).staffInfoList.get(childPosition).personLogo);
                content.setSalary(mDataList.get(groupPosition).staffInfoList.get(childPosition).salary);
                content.setJobname(mDataList.get(groupPosition).staffInfoList.get(childPosition).jobName);
                content.setJobid(mDataList.get(groupPosition).staffInfoList.get(childPosition).jobId);
                StaffListDetailFragment.launchForResult(StaffListFragment.this, REQUEST_CODE_CHANGE_POST,content);
                return true;
            }
        });

        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s);
                if (position != -1) {
                    mExpandableListView.setSelectedGroup(position);
                }
            }
        });


        mInputMethodManager= ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));

        mViewSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    mInputMethodManager.hideSoftInputFromWindow(mViewSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    mSearchKey = mViewSearch.getText().toString();

                    requestData();

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHANGE_POST && resultCode == Activity.RESULT_OK) {
            requestData();
        }
    }

    @Override
    public void requestData() {
        QueryPersonListRequestBean requestBean = new QueryPersonListRequestBean();
        QueryPersonListRequestBean.DataEntity dataEntity = new QueryPersonListRequestBean.DataEntity();
        dataEntity.setDeptid(UserInfo.getCurrentUser().getDeptid());
        dataEntity.setSerachKey(mSearchKey);
        requestBean.setData(dataEntity);

        startJsonRequest(ApiUrls.QUERYPERSONLIST, requestBean, new BaseHttpRequestTask<QueryPersonListResponseBean>() {
            @Override
            public QueryPersonListResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, QueryPersonListResponseBean.class);
            }
            @Override
            public String verifyResponseResult(QueryPersonListResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(QueryPersonListResponseBean result) {
                super.onSuccess(result);
                mDataList.clear();
                for(QueryPersonListResponseBean.DataEntity.PersonEntity staffs:result.getData().getPerson()){
                    GroupDataInfo sectionInfo = new GroupDataInfo();
                    sectionInfo.section=staffs.getBigClass();

                    if(staffs.getPersonList()!=null){
                        for(QueryPersonListResponseBean.DataEntity.PersonEntity.PersonListEntity staff:staffs.getPersonList()){
                            StaffInfo staffInfo = new StaffInfo();
                            staffInfo.accountName = staff.getAccountName();
                            staffInfo.jobId=staff.getJobId();
                            staffInfo.jobName=staff.getJobName();
                            staffInfo.personId=staff.getPersonId();
                            staffInfo.personLogo=staff.getPersonLogo();
                            staffInfo.salary=staff.getSalary();

                            sectionInfo.staffInfoList.add(staffInfo);
                        }
                    }

                    mDataList.add(sectionInfo);
                }

                mAdapter.notifyDataSetChanged();
                //默认展开
                for (int i = 0; i < mDataList.size(); i++) {
                    mExpandableListView.expandGroup(i);
                }
            }
        });
    }
    @Override
    public View getPinnedHeader() {
        View headerView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
        headerView.setBackgroundColor(0xffdddddd);
        headerView.setLayoutParams(new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        GroupDataInfo firstVisibleGroup = (GroupDataInfo) mAdapter.getGroup(firstVisibleGroupPos);
        TextView textView = (TextView) headerView;
        textView.setText(firstVisibleGroup.section);
    }


    public class ExpandableAdapter extends BaseExpandableListAdapter{

        public int getPositionForSection(String sectionIndex) {
            for (int i = 0; i < getGroupCount(); i++) {
                String sortStr = mDataList.get(i).section;
                //char firstChar = sortStr.toUpperCase().charAt(0);
                if (sectionIndex.equalsIgnoreCase(sortStr)) {
                    return i;
                }
            }
            return -1;
        }


        private class GroupHolder {

        }

        private class ChildHolder {

        }

        @Override
        public int getGroupCount() {
            return mDataList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mDataList.get(groupPosition).staffInfoList.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mDataList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mDataList.get(groupPosition).staffInfoList.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
                convertView.setBackgroundColor(0xffdddddd);
                holder = new GroupHolder();
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }
            TextView textView = (TextView) convertView;
            textView.setText(mDataList.get(groupPosition).section);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_staff_list, null);
                holder = new ChildHolder();
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            TextView textView = (TextView) convertView.findViewById(R.id.staff_list_name);
            textView.setText(mDataList.get(groupPosition).staffInfoList.get(childPosition).accountName);

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


    private class GroupDataInfo {
        String section;
        List<StaffInfo> staffInfoList = new LinkedList<>();
    }

    private class StaffInfo {
        int personId;
        String accountName;
        int jobId;
        String jobName;
        String personLogo;
        int salary;
    }
}