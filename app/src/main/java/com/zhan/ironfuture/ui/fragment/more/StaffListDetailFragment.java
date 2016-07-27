package com.zhan.ironfuture.ui.fragment.more;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.BaseActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.framework.view.pickerview.LoopView;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.StaffListContent;
import com.zhan.ironfuture.beans.StaffListDetailJobRequestBean;
import com.zhan.ironfuture.beans.StaffListDetailJobResponseBean;
import com.zhan.ironfuture.beans.StaffListDetailRequestBean;
import com.zhan.ironfuture.beans.StaffListDetailResponseBean;
import com.zhan.ironfuture.event.PositionChangedEvent;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.Tools;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 */
public class StaffListDetailFragment extends ABaseFragment {
    private final static String ARG_KEY = "staff_list_detail";

    @ViewInject(id = R.id.staff_user_head)
    LinearLayout mUserHeader;

    @ViewInject(id = R.id.staff_position, click = "OnClick")
    RelativeLayout mStaffPostion;

    @ViewInject(id = R.id.staff_name)
    TextView mStaffName ;
    @ViewInject(id = R.id.staff_salary)
    EditText mETSalary ;
    @ViewInject(id = R.id.name)
    TextView mStaffPersonName ;

    private PopupWindow mPopupWindow;

    private int mStatusBarHeight = 0;
    private int mActionbarHeight = 0;

    private String mSalary ;

    private StaffListContent mContent;
    private LoopView mPickView;

    //data
    private ArrayList<PostCarInfo> mPostCarInfos=new ArrayList<>();
    private PostCarInfo mPostCarInfo;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_staff_list_detail;
    }

    /*public static void launchForResult(FragmentActivity activity, StaffListContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launchForResult(activity, StaffListDetailFragment.class, args);
    }*/

    public static void launchForResult(ABaseFragment from, int requestCode, StaffListContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launchForResult(from, StaffListDetailFragment.class, args, requestCode);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = savedInstanceState == null ? (StaffListContent) getArguments().getSerializable(ARG_KEY)
                : (StaffListContent) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mContent);
    }

    @Override
    public void onPrepareSetContentView(BaseActivity activity) {
        activity.setOverlay(true);
        activity.showActionbarUnderline(false);
        mStatusBarHeight = activity.getStatusBarHeight();
        mActionbarHeight = activity.getActionbarHeight();
    }

    @Override
    public void onPrepareActionbarMenu(TextView menu, Activity activity) {
        super.onPrepareActionbarMenu(menu, activity);
        menu.setText("完成");
        ColorStateList whiteSelector;
        Resources resources = activity.getResources();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            whiteSelector = resources.getColorStateList(R.color.text_color_white_selector, null);
        } else {
            whiteSelector = resources.getColorStateList(R.color.text_color_white_selector);
        }
        menu.setTextColor(whiteSelector);
        menu.setPadding(PixelUtils.dp2px(8), PixelUtils.dp2px(4), PixelUtils.dp2px(8), PixelUtils.dp2px(4));
        menu.setBackgroundResource(R.drawable.bg_white_empty_rounded_selector);
    }

    @Override
    public void onActionBarMenuClick() {
        super.onActionBarMenuClick();
        if (!checkInput()) {
            return;
        }
        comJobAllot();
    }

    private void comJobAllot() {
        if(isRequestProcessing(ApiUrls.COMJOBALLOT)){
            return;
        }
        StaffListDetailJobRequestBean request = new StaffListDetailJobRequestBean();
        StaffListDetailJobRequestBean.DataEntity data = new StaffListDetailJobRequestBean.DataEntity();
        data.setPersonId(mContent.getPersonId());
        data.setJobId(mPostCarInfo.postId);
        data.setId(mPostCarInfo.id);
        data.setRealSalary(Integer.parseInt(mSalary));
        request.setData(data);
        startJsonRequest(ApiUrls.COMJOBALLOT, request, new HttpRequestHandler(this) {

            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        StaffListDetailJobResponseBean responseBean=Tools.parseJsonTostError(result,StaffListDetailJobResponseBean.class);
                        if(responseBean!=null){
                            ToastUtils.toast(responseBean.getMessage());
                            //发送消息通知职位改变
                            EventBus.getDefault().post(new PositionChangedEvent());

                            getActivity().setResult(Activity.RESULT_OK);//让上个页面刷新
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

    private boolean checkInput() {
        mSalary = mETSalary.getText().toString();
        if (TextUtils.isEmpty(mSalary)) {
            ToastUtils.toast("请设置工资");
            return false;
        }
        if (mSalary.length()>7) {
            ToastUtils.toast("请设置合理的工资");
            return false;
        }
        return true;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("");
        mETSalary.setText(String.format("%d", mContent.getSalary()));
        int padding = PixelUtils.dp2px(16);
        mUserHeader.setPadding(padding, mStatusBarHeight + mActionbarHeight, padding, padding);
        intiPopMenu();

        mPostCarInfo=new PostCarInfo();
        mPostCarInfo.postId=mContent.getJobid();
        mPostCarInfo.name=mContent.getJobname();

        mStaffName.setText(mPostCarInfo.name);
        mStaffPersonName.setText(mContent.getName() + "");
    }

    @Override
    public void requestData() {
        StaffListDetailRequestBean requestBean = new StaffListDetailRequestBean();
        StaffListDetailRequestBean.DataEntity dataEntity = new StaffListDetailRequestBean.DataEntity();
        dataEntity.setDeptId(UserInfo.getCurrentUser().getDeptid());
        dataEntity.setCompanyId(UserInfo.getCurrentUser().getComId());
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.DET_POST_PROCAR_LIST, requestBean, new BaseHttpRequestTask<StaffListDetailResponseBean>() {

            @Override
            public StaffListDetailResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, StaffListDetailResponseBean.class);
            }

            @Override
            public String verifyResponseResult(StaffListDetailResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected boolean resultIsEmpty(StaffListDetailResponseBean result) {
                return result == null || result.getData() == null;
            }

            @Override
            public void onSuccess(StaffListDetailResponseBean bean) {
                super.onSuccess(bean);

                if (isContentEmpty()) {
                    return;
                }

                mPostCarInfos.clear();

                for (StaffListDetailResponseBean.DataEntity item : bean.getData()) {
                    if(item.getCarsList() != null){
                        for (StaffListDetailResponseBean.DataEntity.CarsListEntity carsInfo : item.getCarsList()) {
                            PostCarInfo postCarInfo = new PostCarInfo();
                            postCarInfo.postId= item.getPostId();
                            postCarInfo.id = carsInfo.getId();
                            postCarInfo.name = carsInfo.getName();
                            mPostCarInfos.add(postCarInfo);
                        }
                    }else if(item.getProLineList() != null){
                        for (StaffListDetailResponseBean.DataEntity.ProLineListEntity itemInfo : item.getProLineList()) {
                            PostCarInfo postCarInfo = new PostCarInfo();
                            postCarInfo.postId= item.getPostId();
                            postCarInfo.id = itemInfo.getId();
                            postCarInfo.name = itemInfo.getName();
                            mPostCarInfos.add(postCarInfo);
                        }
                    }else{
                        PostCarInfo postCarInfo = new PostCarInfo();
                        postCarInfo.postId = item.getPostId();
                        postCarInfo.name = item.getPostName();
                        mPostCarInfos.add(postCarInfo);
                    }
                }
            }
        });
    }


    private void OnClick(View v){
        switch (v.getId()){
            case R.id.staff_position:
                showChooseMenu();
                break;
        }
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

    private void showChooseMenu() {
        View viewPopMenuLayout = getActivity().getLayoutInflater().inflate(R.layout.pop_memu_common_pickview, null);
        mPopupWindow.setContentView(viewPopMenuLayout);
        View btnCancel = viewPopMenuLayout.findViewById(R.id.cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopWin();
            }
        });

        View btnFinish = viewPopMenuLayout.findViewById(R.id.finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item=mPickView.getSelectedItem();
                if(item>-1){
                    mPostCarInfo=mPostCarInfos.get(item);
                    mStaffName.setText(mPostCarInfo.name);
                    closePopWin();
                }
            }
        });

        ArrayList<String> nameList = new ArrayList<>();
        for(PostCarInfo itemInfo :mPostCarInfos){
            nameList.add(itemInfo.name);
        }

        mPickView = (LoopView)viewPopMenuLayout.findViewById(R.id.picker_view);
        mPickView.setNotLoop();
        mPickView.setArrayList(nameList);
        mPickView.setInitPosition(0);

        showPopMenu();
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

    private class PostCarInfo{
        int postId=0;
        int id=0;
        String name;
    }
}
