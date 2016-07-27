package com.zhan.ironfuture.ui.fragment.managementDepartment;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.framework.view.pickerview.LoopView;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.BaseRequestBean;
import com.zhan.ironfuture.base.BaseResponseBean;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.DepartmentManagementResponseBean;
import com.zhan.ironfuture.beans.EditJobPositionRequestBean;
import com.zhan.ironfuture.beans.FireRequestBean;
import com.zhan.ironfuture.beans.GetDeptPostResponse;
import com.zhan.ironfuture.beans.JobPositionCodeRequestBean;
import com.zhan.ironfuture.beans.JobPositionCodeResponseBean;
import com.zhan.ironfuture.beans.PublishDepartmentInfo;
import com.zhan.ironfuture.beans.SetBudgetRequestBean;
import com.zhan.ironfuture.beans.UnlockDeptRequestBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.PullToRefreshSwipeMenuExpandableListView;
import com.zhan.ironfuture.ui.widget.SwipeMenuExpandableListView;
import com.zhan.ironfuture.ui.widget.swipemenulistview.SwipeMenu;
import com.zhan.ironfuture.ui.widget.swipemenulistview.SwipeMenuItem;
import com.zhan.ironfuture.ui.widget.swipemenulistview.SwipeMenuLayout;
import com.zhan.ironfuture.ui.widget.swipemenulistview.SwipeMenuView;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 部门管理
 */
public class DepartmentManagementFragment extends ABaseFragment {

    private final static int ITEM_OPERATOR_PROMOTION=100;
    private final static int ITEM_OPERATOR_FIRE=101;

    @ViewInject(id = R.id.department_list)
    private PullToRefreshSwipeMenuExpandableListView mPullToRefreshExpandableListView;

    private SwipeMenuExpandableListView mExpandableListView;

    private PopupWindow mPopupWindow;
    private View mDepartmentPopMenuContent;
    private LoopView mPickView;

    private Dialog mDialogSetBudget;
    private Dialog mDialogConfirmUnlockDept;

    private Dialog mDialogInvite;
    private String mComLogo ;

    //Data
    private List<DepartmentInfo> mAllDepartmentList = new ArrayList<>();
    private List<DepartmentInfo> mUnlockedDepartmentList = new ArrayList<>();
    private List<DepartmentInfo> mDepartmentList = mUnlockedDepartmentList;
    private GetDeptPostResponse mGetDeptPostResponse;

    //flag
    private boolean mIsEditMode=false;
    private long mServiceTime=0;

    //Tools
    private ExpandableAdapter mAdapter;
    private LayoutInflater mInflater;
    private Handler mHandler = new Handler();

    @Override
    protected int inflateContentView() {
        return R.layout.frag_department_manager;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("部门管理");

        mInflater = inflater;
        mIsEditMode=false;

        mExpandableListView = mPullToRefreshExpandableListView.getRefreshableView();

        refreshMenu();

        mAdapter = new ExpandableAdapter();
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener(){
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return mIsEditMode;
            }
        });
        mExpandableListView.setOnChildClickListener(mOnChildClickListener);
        mExpandableListView.setGroupIndicator(null);
        mPullToRefreshExpandableListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mPullToRefreshExpandableListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<SwipeMenuExpandableListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<SwipeMenuExpandableListView> refreshView) {
                requestData();
            }
        });

        intiPopMenu();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(mRefreshCompleteRunnable);
    }

    @Override
    public void requestData() {
        BaseRequestBean request = new BaseRequestBean();
        startJsonRequest(ApiUrls.DEPARTMENTINFO, request, new BaseHttpRequestTask<DepartmentManagementResponseBean>() {
            @Override
            public DepartmentManagementResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, DepartmentManagementResponseBean.class);
            }

            @Override
            public String verifyResponseResult(DepartmentManagementResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected boolean resultIsEmpty(DepartmentManagementResponseBean result) {
                return result == null || result.getData() == null;
            }

            @Override
            protected void onSuccess(DepartmentManagementResponseBean bean) {
                super.onSuccess(bean);
                mServiceTime=bean.getServerTime();
                mAllDepartmentList.clear();
                mUnlockedDepartmentList.clear();
                if (isContentEmpty()) {
                    return;
                }
                mComLogo = bean.getData().getComLogo();
                for (DepartmentManagementResponseBean.DataEntity.ReulstListEntity item : bean.getData().getReulstList()) {
                    DepartmentInfo departmentInfo = new DepartmentInfo();
                    departmentInfo.setDeptId(item.getDeptId());
                    departmentInfo.setDeptName(item.getDeptName());
                    departmentInfo.setCompanyId(item.getCompanyId());
                    departmentInfo.setDeptType(item.getDeptType());
                    departmentInfo.setBranch(item.getBranch());
                    departmentInfo.setBudget(item.getBudget());
                    departmentInfo.setRemark(item.getRemark());
                    departmentInfo.setCreateTime(item.getCreateTime());
                    departmentInfo.setUpdateTime(item.getUpdaeTime());
                    departmentInfo.setEndLockTime(item.getEndLockTime());
                    departmentInfo.setIsTurnOn(item.getIsTurnOn());
                    departmentInfo.setDeptBussinessID(item.getDeptBussinessID());
                    departmentInfo.setTurnOnSum(item.getTurnOnSum());

                    List<StaffInfo> staffInfoList = new ArrayList<>();
                    if (item.getMemberList() != null) {
                        for (DepartmentManagementResponseBean.DataEntity.ReulstListEntity.MemberListEntity member : item.getMemberList()) {
                            StaffInfo staffInfo = new StaffInfo();
                            staffInfo.setPersonId(member.getPersonId());
                            staffInfo.setPeopleName(member.getPeopleName());
                            staffInfo.setPhone(member.getPhone());
                            staffInfo.setDepartmentId(member.getDepartmentId());
                            staffInfo.setJobId(member.getJobId());
                            staffInfo.setJobName(member.getJobName());
                            staffInfo.setImgUrl(member.getImgUrl());
                            staffInfoList.add(staffInfo);
                        }
                    }
                    departmentInfo.setStaffInfoList(staffInfoList);
                    if (departmentInfo.isTurnOn ==0) {
                        mUnlockedDepartmentList.add(departmentInfo);
                    }
                    mAllDepartmentList.add(departmentInfo);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                super.onRequestFinished(resultCode, result);
                onRefreshViewComplete();
            }
        });
    }

    public class ExpandableAdapter extends BaseExpandableListAdapter implements SwipeMenuView.OnSwipeItemClickListener {

        private class GroupHolder {
            ImageView arrow;
            LinearLayout extContent;
            LinearLayout extTurnon ;
            TextView departmentName;
            TextView budget;
            TextView invite;
            TextView publish;
            TextView number ;
            TextView turnonMoney ;
        }

        private class ChildHolder {
            TextView staffName;
            TextView staffPosition ;
            TextView info;
            ImageView img;
            /*LinearLayout extContent;
            TextView budget;
            TextView invite;*/
        }

        @Override
        public int getGroupCount() {
            return mDepartmentList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mDepartmentList.get(groupPosition).getStaffInfoList().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mDepartmentList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mDepartmentList.get(groupPosition).getStaffInfoList().get(childPosition);
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
                convertView = mInflater.inflate(R.layout.list_item_department_group, null);
                holder = new GroupHolder();
                holder.departmentName = (TextView) convertView.findViewById(R.id.name);
                holder.arrow = (ImageView) convertView.findViewById(R.id.arrow);
                holder.extContent= (LinearLayout) convertView.findViewById(R.id.ext_content);
                holder.budget = (TextView) convertView.findViewById(R.id.budget);
                holder.invite = (TextView) convertView.findViewById(R.id.invite);
                holder.publish= (TextView) convertView.findViewById(R.id.publish);
                holder.number = (TextView) convertView.findViewById(R.id.number);
                holder.extTurnon = (LinearLayout) convertView.findViewById(R.id.ext_turnon);
                holder.turnonMoney = (TextView) convertView.findViewById(R.id.turnon_money);
                convertView.setTag(holder);
            } else {
                holder = (GroupHolder) convertView.getTag();
            }

            DepartmentInfo departmentInfo=mDepartmentList.get(groupPosition);

            if (isExpanded) {
                holder.arrow.setImageResource(R.drawable.arrow_down_small);
            } else {
                holder.arrow.setImageResource(R.drawable.arrow_right_small);

            }

            if(departmentInfo.getIsTurnOn() == 0){
                holder.extTurnon.setVisibility(View.GONE);
                holder.extContent.setVisibility(mIsEditMode&&departmentInfo.deptType!=null? View.VISIBLE : View.GONE);
            }else{
                holder.extContent.setVisibility(View.GONE);
                holder.extTurnon.setVisibility(mIsEditMode ? View.VISIBLE : View.GONE);
            }

            holder.extTurnon.setTag(departmentInfo);
            holder.extTurnon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtils.toast("点击解锁");
                    DepartmentInfo departmentInfo = (DepartmentInfo) v.getTag();
                    unlockDept(departmentInfo);
                }
            });
            holder.turnonMoney.setText("解锁部门需要" + departmentInfo.getTurnOnSum() + "，点击解锁");
            holder.departmentName.setText(departmentInfo.getDeptName());
            holder.number.setText(departmentInfo.getStaffInfoList().size()+"");
            holder.budget.setText(departmentInfo.getBudget()+"");
            holder.budget.setTag(departmentInfo);
            holder.budget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DepartmentInfo departmentInfo = (DepartmentInfo) v.getTag();
                    setBudget(departmentInfo);
                }
            });
            holder.invite.setTag(departmentInfo);
            holder.invite.setOnClickListener(mOnInviteClickListener);

            holder.publish.setTag(departmentInfo);
            holder.publish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DepartmentInfo departmentInfo= (DepartmentInfo) v.getTag();

                    PublishDepartmentInfo info=new PublishDepartmentInfo();
                    info.setCompanyId(departmentInfo.getCompanyId());
                    info.setDeptId(departmentInfo.getDeptId());
                    info.setDeptName(departmentInfo.getDeptName());
                    info.setDeptType(departmentInfo.getDeptType());

                    PostingPositionsFragment.launch(getActivity(),info);
                }
            });
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildHolder holder;
            SwipeMenuLayout swipeMenuLayout;
            if (convertView == null) {
                SwipeMenu menu = new SwipeMenu(getActivity());
                createMenu(menu);
                SwipeMenuView menuView = new SwipeMenuView(menu);
                menuView.setOnSwipeItemClickListener(this);

                View itemView = mInflater.inflate(R.layout.list_item_department_child, null);
                swipeMenuLayout = new SwipeMenuLayout(itemView, menuView);

                holder = new ChildHolder();
                holder.staffName = (TextView) itemView.findViewById(R.id.name);
                holder.staffPosition = (TextView) itemView.findViewById(R.id.department_position);
                holder.info = (TextView) itemView.findViewById(R.id.intro);
                holder.img=(ImageView)itemView.findViewById(R.id.user_img);
                /*holder.extContent= (LinearLayout) itemView.findViewById(R.id.ext_content);
                holder.budget = (TextView) itemView.findViewById(R.id.budget);
                holder.invite = (TextView) itemView.findViewById(R.id.invite);*/
                swipeMenuLayout.setTag(holder);
            } else {
                swipeMenuLayout = (SwipeMenuLayout) convertView;
                holder = (ChildHolder) convertView.getTag();
                swipeMenuLayout.closeMenu();

            }
            swipeMenuLayout.setPosition(groupPosition, childPosition);

            holder.staffName.setText(mDepartmentList.get(groupPosition).getStaffInfoList().get(childPosition).getPeopleName());
            String jobName=mDepartmentList.get(groupPosition).getStaffInfoList().get(childPosition).getJobName();
            holder.staffPosition.setVisibility(TextUtils.isEmpty(jobName) ? View.GONE : View.VISIBLE);
            holder.staffPosition.setText(jobName);

            holder.info.setText(String.format("手机:%s", mDepartmentList.get(groupPosition).getStaffInfoList().get(childPosition).getPhone()));

            if(mDepartmentList.get(groupPosition).getStaffInfoList().get(childPosition).getJobId() == 1){
                ImageLoader.getInstance().displayImage(mComLogo, holder.img, Tools.buildDisplayImageOptionsForAvatar());
            }else{
                String imgUrl=mDepartmentList.get(groupPosition).getStaffInfoList().get(childPosition).getImgUrl();
                ImageLoader.getInstance().displayImage(imgUrl, holder.img, Tools.buildDisplayImageOptionsForAvatar());

            }

            return swipeMenuLayout;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        public void createMenu(SwipeMenu menu) {
            SwipeMenuItem promotionItem = new SwipeMenuItem(getActivity());
            promotionItem.setId(ITEM_OPERATOR_PROMOTION);
            promotionItem.setBackground(new ColorDrawable(0xffF59E34));
            promotionItem.setWidth(PixelUtils.dp2px(90));
            promotionItem.setTitle("升职");
            promotionItem.setTitleSize(16);
            promotionItem.setTitleColor(Color.WHITE);
            menu.addMenuItem(promotionItem);

            SwipeMenuItem fireItem = new SwipeMenuItem(getActivity());
            fireItem.setId(ITEM_OPERATOR_FIRE);
            fireItem.setBackground(new ColorDrawable(0xffFE501B));
            fireItem.setWidth(PixelUtils.dp2px(90));
            fireItem.setTitle("解雇");
            fireItem.setTitleSize(16);
            fireItem.setTitleColor(Color.WHITE);
            menu.addMenuItem(fireItem);
        }

        @Override
        public void onItemClick(SwipeMenuItem menuItem, int groupPosition, int childPosition) {
            StaffInfo staffInfo=mDepartmentList.get(groupPosition).getStaffInfoList().get(childPosition);
            switch (menuItem.getId()){
                case ITEM_OPERATOR_PROMOTION:
                    if(staffInfo.departmentId==0){
                        ToastUtils.toast("该员工还未分配部门！");
                    }else{
                        int jobId = staffInfo.jobId;
                        if(jobId== IronFutureConstants.ROLE_CEO ||jobId==IronFutureConstants.ROLE_CORPORATE || jobId == IronFutureConstants.ROLE_WAREHOUSE_MANAGER
                                || jobId == IronFutureConstants.ROLE_RAW_MATERIALS_MANAGER
                                || jobId == IronFutureConstants.ROLE_PRODUCTION_MANAGER
                                || jobId == IronFutureConstants.ROLE_CONSTRUCTION_MANAGER
                                || jobId == IronFutureConstants.ROLE_LOGISTICS_DEPARTMENT_MANAGER){
                            ToastUtils.toast("管理层或者经理不能升职");
                        }else {
                            editPositionRequest(staffInfo.getPersonId(),mDepartmentList.get(groupPosition).getDeptId(),1);
                        }
                    }
                    break;
                case ITEM_OPERATOR_FIRE:
                    int personId = staffInfo.getPersonId() ;
                    int jobId = staffInfo.getJobId();
                    if(personId != UserInfo.getCurrentUser().getUserID()){
                        if(UserInfo.getCurrentUser().getPostId() == IronFutureConstants.ROLE_CORPORATE){
                            fireRequest(staffInfo.getPersonId());
                        }else{
                            if(jobId == IronFutureConstants.ROLE_CEO || jobId == IronFutureConstants.ROLE_CORPORATE){
                                ToastUtils.toast("没有权限解雇");
                            }else{
                                fireRequest(staffInfo.getPersonId());
                            }
                        }
                    }else{
                        ToastUtils.toast("不能对自己操作");
                    }
                    break;
            }
        }

        private View.OnClickListener mOnInviteClickListener=new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isRequestProcessing(ApiUrls.JOB_POSITION_CODE)){
                    return;
                }

                final DepartmentInfo departmentInfo= (DepartmentInfo) v.getTag();
                JobPositionCodeRequestBean requestBean=new JobPositionCodeRequestBean();
                JobPositionCodeRequestBean.DataEntity data=new JobPositionCodeRequestBean.DataEntity();
                requestBean.setData(data);
                data.setDeptId(departmentInfo.getDeptId());
                showRotateProgressDialog("正在生成邀请码...",false);
                startJsonRequest(ApiUrls.JOB_POSITION_CODE, requestBean, new HttpRequestHandler(DepartmentManagementFragment.this) {
                    @Override
                    public void onRequestFinished(ResultCode resultCode, String result) {
                        closeRotateProgressDialog();
                        switch (resultCode) {
                            case success:
                                JobPositionCodeResponseBean responseBean = Tools.parseJsonTostError(result, JobPositionCodeResponseBean.class);
                                if (responseBean != null) {
                                    mServiceTime=responseBean.getServerTime();
                                    showInviteDialog(responseBean.getData(),departmentInfo.getDeptName());
                                }
                                break;
                            default:
                                ToastUtils.toast(result);
                                break;
                        }
                    }
                });
            }
        };
    }

    private void showInviteDialog(final String inviteCode,String deptName){
        mDialogInvite = Tools.createDialog(getActivity(), R.layout.dialog_department_invite);

        TextView viewCopyBtn=(TextView)mDialogInvite.findViewById(R.id.copy);

        TextView summary=(TextView)mDialogInvite.findViewById(R.id.summary);
        summary.setText(String.format("生成%s的邀请码",deptName));

        TextView viewInviteCode=(TextView)mDialogInvite.findViewById(R.id.invite_code);
        viewInviteCode.setText(inviteCode);

        viewCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(inviteCode, inviteCode);
                cmb.setPrimaryClip(clip);

                ToastUtils.toast("已复制邀请码！");
                mDialogInvite.dismiss();
            }
        });
        mDialogInvite.show();
    }

    //设置部门预算
    private void setBudget(final DepartmentInfo departmentInfo) {
        //设置部门预算时间必须大与解锁时间
        if(mServiceTime>=departmentInfo.getEndLockTime()){
             mDialogSetBudget = Tools.createDialog(getActivity(), R.layout.dialog_department_set_budget);
            final EditText viewBudgetAmount=(EditText)mDialogSetBudget.findViewById(R.id.budget_amount);
            mDialogSetBudget.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialogSetBudget.dismiss();
                }
            });
            mDialogSetBudget.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String budgetAmount=viewBudgetAmount.getText().toString();
                    if(TextUtils.isEmpty(budgetAmount)){
                        ToastUtils.toast("请输入预算金额!");
                        return;
                    }
                    try{
                        int amount=Integer.valueOf(budgetAmount);
                        setBudgetRequest(departmentInfo.getDeptId(),amount);
                    }catch (Exception ex){
                        ToastUtils.toast("请输入正确的金额!");
                    }
                }
            });
            mDialogSetBudget.show();
        }else{
            final Dialog dialog = Tools.createDialog(getActivity(), R.layout.dialog_department_cannot_set_budget);
            TextView viewBudgetEndLockTime=(TextView)dialog.findViewById(R.id.budget_end_lock_time);
            viewBudgetEndLockTime.setText(Tools.parseTime(departmentInfo.getEndLockTime()));
            dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }


    //解锁部门
    private void unlockDept(final DepartmentInfo departmentInfo) {
        if(mDialogConfirmUnlockDept!=null&&mDialogConfirmUnlockDept.isShowing()){
            mDialogConfirmUnlockDept.dismiss();
            mDialogConfirmUnlockDept=null;
        }

        String summaryStrFormat="解锁%s需要%d";
        String summaryStr=String.format(summaryStrFormat,departmentInfo.deptName,departmentInfo.turnOnSum);

        mDialogConfirmUnlockDept = Tools.createDialog(getActivity(), R.layout.dialog_department_unlock);

        TextView viewSummary=(TextView)mDialogConfirmUnlockDept.findViewById(R.id.summary);
        viewSummary.setText(summaryStr);

        mDialogConfirmUnlockDept.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogConfirmUnlockDept.dismiss();
                mDialogConfirmUnlockDept=null;
            }
        });
        View viewOk=mDialogConfirmUnlockDept.findViewById(R.id.ok);
        viewOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlockDeptRequest(departmentInfo.branch);
            }
        });
        mDialogConfirmUnlockDept.show();
    }


    private ExpandableListView.OnChildClickListener mOnChildClickListener=new ExpandableListView.OnChildClickListener(){

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            StaffInfo staffInfo=mDepartmentList.get(groupPosition).getStaffInfoList().get(childPosition);
            showChooseMenu(staffInfo);
            return true;
        }
    };

    private void onRefreshViewComplete() {
        mHandler.removeCallbacks(mRefreshCompleteRunnable);
        mHandler.postDelayed(mRefreshCompleteRunnable, 50);
    }

    private void refreshMenu(){
        if(getActivity() instanceof ActionBarActivity){
            ActionBarActivity actionBarActivity= (ActionBarActivity) getActivity();
            LinearLayout rightContent=actionBarActivity.getActionBarRightContent();
            rightContent.removeAllViews();
            ImageView imageView;
            if(mIsEditMode){
                imageView=new ImageView(getActivity());
                imageView.setImageResource(R.drawable.depart_manager_finish);
                rightContent.addView(imageView);

                //编辑模式显示全部部门
                mDepartmentList=mAllDepartmentList;

                //先收缩
                for(int i=0;i<mDepartmentList.size();i++){
                    mExpandableListView.collapseGroup(i);
                }

            }else{
                imageView=new ImageView(getActivity());
                imageView.setImageResource(R.drawable.depart_manager_edit);
                rightContent.addView(imageView);
                //正常模式显示已解锁部门
                mDepartmentList=mUnlockedDepartmentList;
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIsEditMode=!mIsEditMode;
                    refreshMenu();
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
        mExpandableListView.setCanSwip(!mIsEditMode);
    }

    private Runnable mRefreshCompleteRunnable = new Runnable() {
        @Override
        public void run() {
            mPullToRefreshExpandableListView.onRefreshComplete();
        }
    };

    private void intiPopMenu() {
        mPopupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        int bgColor = getResources().getColor(com.zhan.framework.R.color.main_background);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(bgColor));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.pop_menu_animation);
        mPopupWindow.update();
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
    }

    public boolean closePopWin() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return true;
        }
        return false;
    }

    private void showPopMenu() {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            backgroundAlpha(0.7f);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
    }

    private void showChooseMenu(StaffInfo staffInfo) {
        mDepartmentPopMenuContent = getActivity().getLayoutInflater().inflate(R.layout.pop_memu_switch_deparement, null);
        mPopupWindow.setContentView(mDepartmentPopMenuContent);
        View btnCancel = mDepartmentPopMenuContent.findViewById(R.id.cancel);
        btnCancel.setOnClickListener(mOnPopWinMenuClickListener);
        View btnFinish = mDepartmentPopMenuContent.findViewById(R.id.finish);
        btnFinish.setOnClickListener(mOnPopWinMenuClickListener);
        btnFinish.setTag(staffInfo);

        mPickView = (LoopView) mDepartmentPopMenuContent.findViewById(R.id.picker_view);
        mPickView.setNotLoop();
        getDepartPost(new GetDepartPostCallback() {
            @Override
            public void onGetResult(GetDeptPostResponse data) {
                List<String> departments = new ArrayList<>();
                for(GetDeptPostResponse.DataEntity dept:data.getData()){
                    departments.add(dept.getDeptName());
                }
                mPickView.setArrayList((ArrayList) departments);
            }
        });
        mPickView.setInitPosition(0);

        showPopMenu();
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    private View.OnClickListener mOnPopWinMenuClickListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cancel:
                    closePopWin();
                    break;
                case R.id.finish:
                    int pos=mPickView.getSelectedItem();
                    if(mGetDeptPostResponse!=null&&pos>-1){
                        StaffInfo staffInfo=(StaffInfo)v.getTag();
                        GetDeptPostResponse.DataEntity deptInfo=mGetDeptPostResponse.getData().get(pos);
                        if(deptInfo.getDeptId()!=staffInfo.getDepartmentId()){
                            editPositionRequest(staffInfo.getPersonId(), deptInfo.getDeptId(), null);
                        }else{
                            ToastUtils.toast("您选择的是当前所在部门!");
                        }
                    }else{
                        ToastUtils.toast("请选择部门");
                    }
                    break;
            }
        }
    };


    private class DepartmentInfo {

        private int deptId;
        private String deptName;
        private int companyId;
        private String deptType;
        private String branch;
        private long budget;
        private String remark;
        private long createTime;
        private long updateTime;
        private long endLockTime;
        private int isTurnOn;
        private String deptBussinessID;
        private int turnOnSum;

        private List<StaffInfo> memberList;

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getDeptType() {
            return deptType;
        }

        public void setDeptType(String deptType) {
            this.deptType = deptType;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public long getBudget() {
            return budget;
        }

        public void setBudget(long budget) {
            this.budget = budget;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public long getEndLockTime() {
            return endLockTime;
        }

        public void setEndLockTime(long endLockTime) {
            this.endLockTime = endLockTime;
        }

        public int getIsTurnOn() {
            return isTurnOn;
        }

        public void setIsTurnOn(int isTurnOn) {
            this.isTurnOn = isTurnOn;
        }

        public String getDeptBussinessID() {
            return deptBussinessID;
        }

        public void setDeptBussinessID(String deptBussinessID) {
            this.deptBussinessID = deptBussinessID;
        }

        public int getTurnOnSum() {
            return turnOnSum;
        }

        public void setTurnOnSum(int turnOnSum) {
            this.turnOnSum = turnOnSum;
        }

        public List<StaffInfo> getStaffInfoList() {
            return memberList;
        }

        public void setStaffInfoList(List<StaffInfo> memberList) {
            this.memberList = memberList;
        }

    }

    private class StaffInfo {
        private int personId;
        private String accountName;
        private String peopleName;
        private String phone;
        private int jobId;
        private int companyId;
        private int departmentId;
        private String schoolName;
        private Object realSalary;
        private long lastTimeJobTime;
        private String jobName;
        private String imgUrl;

        public int getPersonId() {
            return personId;
        }

        public void setPersonId(int personId) {
            this.personId = personId;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getPeopleName() {
            return peopleName;
        }

        public void setPeopleName(String peopleName) {
            this.peopleName = peopleName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getJobId() {
            return jobId;
        }

        public void setJobId(int jobId) {
            this.jobId = jobId;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public Object getRealSalary() {
            return realSalary;
        }

        public void setRealSalary(Object realSalary) {
            this.realSalary = realSalary;
        }

        public long getLastTimeJobTime() {
            return lastTimeJobTime;
        }

        public void setLastTimeJobTime(long lastTimeJobTime) {
            this.lastTimeJobTime = lastTimeJobTime;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }


    //以下是请求相关部分
    //解雇
    private void fireRequest(int staffId){
        if(isRequestProcessing(ApiUrls.FIRE)){
            return;
        }
        FireRequestBean requestBean=new FireRequestBean();
        FireRequestBean.DataEntity data=new FireRequestBean.DataEntity();
        data.setStaffId(staffId);
        requestBean.setData(data);
        startJsonRequest(ApiUrls.FIRE, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        BaseResponseBean responseBean = Tools.parseJsonTostError(result, BaseResponseBean.class);
                        if (responseBean != null) {
                            requestData();
                            ToastUtils.toast(responseBean.getMessage());
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }

    //编辑员工职位
    private void editPositionRequest(int staffId,int deptId,Integer jobId) {
        if (isRequestProcessing(ApiUrls.EDIT_JOB_POSITION)) {
            return;
        }
        EditJobPositionRequestBean requestBean = new EditJobPositionRequestBean();
        EditJobPositionRequestBean.DataEntity data=new EditJobPositionRequestBean.DataEntity();
        data.setStaffId(staffId);
        data.setDeptId(deptId);
        data.setJobId(jobId);
        requestBean.setData(data);
        startJsonRequest(ApiUrls.EDIT_JOB_POSITION, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        BaseResponseBean responseBean = Tools.parseJsonTostError(result, BaseResponseBean.class);
                        if (responseBean != null) {
                            closePopWin();
                            requestData();
                            ToastUtils.toast(responseBean.getMessage());
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }

    //设置部门预算
    private void setBudgetRequest(int deptId,int budget) {
        if (isRequestProcessing(ApiUrls.DEPARTMENT_BUDGET)) {
            return;
        }
        SetBudgetRequestBean requestBean = new SetBudgetRequestBean();
        SetBudgetRequestBean.DataEntity data=new SetBudgetRequestBean.DataEntity();
        data.setDeptId(deptId);
        data.setBudget(budget);
        requestBean.setData(data);
        startJsonRequest(ApiUrls.DEPARTMENT_BUDGET, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        BaseResponseBean responseBean = Tools.parseJsonTostError(result, BaseResponseBean.class);
                        if (responseBean != null) {
                            requestData();
                            ToastUtils.toast(responseBean.getMessage());
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
                if(mDialogSetBudget!=null){
                    mDialogSetBudget.dismiss();
                }
            }
        });
    }

    //获取 部门及岗位
    private void getDepartPost(final GetDepartPostCallback callback){
        if(mGetDeptPostResponse!=null){
            callback.onGetResult(mGetDeptPostResponse);
            return;
        }

        BaseRequestBean baseRequestBean=new BaseRequestBean();
        startJsonRequest(ApiUrls.GET_DEPT_POST,baseRequestBean,new HttpRequestHandler(this){
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        GetDeptPostResponse responseBean = Tools.parseJsonTostError(result, GetDeptPostResponse.class);
                        if (responseBean != null&&responseBean.getData()!=null) {
                            mServiceTime=responseBean.getServerTime();
                            mGetDeptPostResponse=responseBean;
                            callback.onGetResult(mGetDeptPostResponse);
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }

    public interface GetDepartPostCallback{
        void onGetResult(GetDeptPostResponse data);
    }


    //解锁部门
    private void unlockDeptRequest(String branchId) {
        if (isRequestProcessing(ApiUrls.UNLOCK_DEPARTMENT)) {
            return;
        }
        UnlockDeptRequestBean requestBean = new UnlockDeptRequestBean();
        UnlockDeptRequestBean.DataEntity data=new UnlockDeptRequestBean.DataEntity();
        data.setBranchId(branchId);
        requestBean.setData(data);
        startJsonRequest(ApiUrls.UNLOCK_DEPARTMENT, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        BaseResponseBean responseBean = Tools.parseJsonTostError(result, BaseResponseBean.class);
                        if (responseBean != null) {
                            requestData();
                            ToastUtils.toast(responseBean.getMessage());
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
                if(mDialogConfirmUnlockDept!=null){
                    mDialogConfirmUnlockDept.dismiss();
                }
            }
        });
    }
}
