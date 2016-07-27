package com.zhan.ironfuture.ui.fragment.managementDepartment;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.framework.view.pickerview.LoopView;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.BaseResponseBean;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.PubJobInfoRequestBean;
import com.zhan.ironfuture.beans.PublishDepartmentInfo;
import com.zhan.ironfuture.beans.StaffListDetailRequestBean;
import com.zhan.ironfuture.beans.StaffListDetailResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;

/**
 * 作者：伍岳 on 2016/6/16 15:28
 * 邮箱：wuyue8512@163.com
 * //
 * //         .............................................
 * //                  美女坐镇                  BUG辟易
 * //         .............................................
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 * //
 */
public class PostingPositionsFragment extends ABaseFragment {

    private final static String ARG_KEY = "posting_positions";

    //Views
    @ViewInject(id = R.id.department_name)
    TextView mViewDepartmentName ;

    @ViewInject(id = R.id.salary)
    EditText mViewSalary;

    @ViewInject(id = R.id.recruitmentSum)
    EditText mViewRecruitmentSum ;

    @ViewInject(id = R.id.remarks)
    EditText mViewRemarks ;

    @ViewInject(id = R.id.post, click = "OnClick")
    TextView mViewPost;

    private PopupWindow mPopupWindow;
    private View mViewPopMenuLayout;
    private LoopView mPickView;

    //Data
    private PublishDepartmentInfo mDepartmentInfo;
    private ArrayList<PostInfo> mPostInfos=new ArrayList<>();
    private int mPostId=-1;

    public static void launch(Activity activity,PublishDepartmentInfo departmentInfo){
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, departmentInfo);
        FragmentContainerActivity.launch(activity, PostingPositionsFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDepartmentInfo = savedInstanceState == null ? (PublishDepartmentInfo) getArguments().getSerializable(ARG_KEY) : (PublishDepartmentInfo) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mDepartmentInfo);
    }

    @Override
    public void onPrepareActionbarMenu(TextView menu, Activity activity) {
        menu.setText("发布");
    }

    @Override
    public void onActionBarMenuClick() {
        publishRequest();
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("发布职位");
        mViewDepartmentName.setText(mDepartmentInfo.getDeptName());
        intiPopMenu();
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_posting_positions;
    }

    public void requestPostList() {
        StaffListDetailRequestBean requestBean = new StaffListDetailRequestBean();
        StaffListDetailRequestBean.DataEntity dataEntity = new StaffListDetailRequestBean.DataEntity();
        dataEntity.setDeptId(mDepartmentInfo.getDeptId());
        dataEntity.setCompanyId(UserInfo.getCurrentUser().getComId());
        requestBean.setData(dataEntity);

        startJsonRequest(ApiUrls.DET_POST_PROCAR_LIST, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        StaffListDetailResponseBean responseBean=Tools.parseJsonTostError(result,StaffListDetailResponseBean.class);
                        if(responseBean!=null){
                            if(responseBean.getData()!=null){
                                mPostInfos.clear();
                                for (StaffListDetailResponseBean.DataEntity item : responseBean.getData()) {
                                    PostInfo postInfo=new PostInfo();
                                    postInfo.postId=item.getPostId();
                                    postInfo.postName=item.getPostName();

                                    mPostInfos.add(postInfo);
                                }
                            }
                            showChooseMenu();
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        });
    }

    private void publishRequest(){
        if(isRequestProcessing(ApiUrls.PUB_JOB_INFO)){
            return;
        }

        if(TextUtils.isEmpty(mViewSalary.getText().toString())){
            ToastUtils.toast("请设置薪资");
            return;
        }
        if(TextUtils.isEmpty(mViewRecruitmentSum.getText().toString())){
            ToastUtils.toast("请设置人数");
            return;
        }
        if(mPostId==-1){
            ToastUtils.toast("请选择岗位");
            return;
        }
        showRotateProgressDialog("发布中...",false);
        PubJobInfoRequestBean requestBean=new PubJobInfoRequestBean();
        PubJobInfoRequestBean.DataEntity data=new PubJobInfoRequestBean.DataEntity();
        requestBean.setData(data);
        data.setComId(UserInfo.getCurrentUser().getComId());
        data.setSalary(Integer.parseInt(mViewSalary.getText().toString()));
        data.setPostId(mPostId);
        data.setRecruitmentSum(Integer.parseInt(mViewRecruitmentSum.getText().toString()));
        data.setJobDescribe(mViewRemarks.getText().toString());

        startJsonRequest(ApiUrls.PUB_JOB_INFO, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                closeRotateProgressDialog();
                switch (resultCode) {
                    case success:
                        BaseResponseBean responseBean = Tools.parseJsonTostError(result, BaseResponseBean.class);
                        if (responseBean != null) {
                            ToastUtils.toast("发布成功！");
                            getActivity().finish();
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        });
    }

    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.post:
                requestPostList();
                break;
        }
    }

    private void showChooseMenu() {
        mViewPopMenuLayout = getActivity().getLayoutInflater().inflate(R.layout.pop_memu_common_pickview, null);
        mPopupWindow.setContentView(mViewPopMenuLayout);
        View btnCancel = mViewPopMenuLayout.findViewById(R.id.cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopWin();
            }
        });

        View btnFinish = mViewPopMenuLayout.findViewById(R.id.finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item=mPickView.getSelectedItem();
                if(item>-1){
                    mPostId=mPostInfos.get(item).postId;
                    mViewPost.setText(mPostInfos.get(item).postName);
                    closePopWin();
                }
            }
        });

        ArrayList<String> nameList = new ArrayList<>();
        for(PostInfo postInfo:mPostInfos){
            nameList.add(postInfo.postName);
        }

        mPickView = (LoopView) mViewPopMenuLayout.findViewById(R.id.picker_view);
        mPickView.setNotLoop();
        mPickView.setArrayList(nameList);
        mPickView.setInitPosition(0);

        showPopMenu();
    }

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
    public boolean closePopWin() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return true;
        }
        return false;
    }
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    private class PostInfo{
        int postId;
        String postName;
    }
}
