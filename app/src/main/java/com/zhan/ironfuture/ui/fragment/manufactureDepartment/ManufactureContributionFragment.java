package com.zhan.ironfuture.ui.fragment.manufactureDepartment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.BaseRequestBean;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.GetProLinePersonInfoRequestBean;
import com.zhan.ironfuture.beans.GetProLinePersonInfoResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.RoundProgressBar;
import com.zhan.ironfuture.ui.widget.WorkButton;
import com.zhan.ironfuture.utils.Tools;
import com.zhan.ironfuture.utils.VibratorUtil;

/**
 * Created by WuYue on 2016/4/25.
 * 生产贡献
 */
public class ManufactureContributionFragment extends ABaseFragment implements WorkButton.Callback{

    @ViewInject(id = R.id.left_title)
    TextView mViewLeftTitle;

    @ViewInject(id = R.id.right_title)
    TextView mViewRightTitle;

    @ViewInject(id = R.id.roundProgressBar)
    RoundProgressBar mViewRoundProgressBar;

    @ViewInject(id = R.id.product_line_name)
    TextView mViewProductLineName ;

    @ViewInject(id = R.id.product_name)
    TextView mViewProductName ;

    @ViewInject(id = R.id.contribution)
    TextView mViewContribution ;

    @ViewInject(id = R.id.yesterdayPersonSum)
    TextView mViewYesterdayPersonSum ;

    @ViewInject(id = R.id.todayPersonSum)
    TextView mViewTodayPersonSum ;

    @ViewInject(id = R.id.work_btn)
    WorkButton mBtnWork ;

    @Override
    protected int inflateContentView() {
        return R.layout.frag_manufacture_contribution;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("生产贡献");
        mViewRoundProgressBar.setProgress(0);
        mBtnWork.setCallback(this);

        if(!isProductionDepartmentStaff()){
            mViewLeftTitle.setText("线路");
            mViewRightTitle.setText("车辆");
        }
    }


    @Override
    public void requestData() {
        GetProLinePersonInfoRequestBean requestBean=new GetProLinePersonInfoRequestBean();
        GetProLinePersonInfoRequestBean.DataEntity dataEntity = new GetProLinePersonInfoRequestBean.DataEntity();
        dataEntity.setState(isProductionDepartmentStaff() ? 1 : 2);
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.GET_PROLINE_PERSON_INFO, requestBean, new BaseHttpRequestTask<GetProLinePersonInfoResponseBean>() {
            @Override
            public GetProLinePersonInfoResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, GetProLinePersonInfoResponseBean.class);
            }

            @Override
            public String verifyResponseResult(GetProLinePersonInfoResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(GetProLinePersonInfoResponseBean result) {
                super.onSuccess(result);
                populateUI(result);
            }
        });
    }

    private void populateUI(GetProLinePersonInfoResponseBean bean){
        mViewProductLineName.setText(String.format("#%d", bean.getData().getProlineId()));

        mViewProductName.setText(bean.getData().getOutputName());
        mViewContribution.setText(String.format("%d/%d", bean.getData().getPersonSum(), bean.getData().getTeamSum()));
        mViewYesterdayPersonSum.setText(bean.getData().getYesterdayPersonSum() + "点贡献值");
        mViewTodayPersonSum.setText(bean.getData().getTodayPersonSum() + "点贡献值");

        if(bean.getData().getPersonSum()==0||bean.getData().getTeamSum()==0){
            mViewRoundProgressBar.setProgress(0);
        }else{
            mViewRoundProgressBar.setMax(bean.getData().getTeamSum());
            mViewRoundProgressBar.setProgressAnim(bean.getData().getPersonSum(),100);
        }
    }

    @Override
    public void onWorkFinished() {
        VibratorUtil.Vibrate();
        GetProLinePersonInfoRequestBean requestBean=new GetProLinePersonInfoRequestBean();
        GetProLinePersonInfoRequestBean.DataEntity dataEntity = new GetProLinePersonInfoRequestBean.DataEntity();
        dataEntity.setState(isProductionDepartmentStaff()?1:2);
        requestBean.setData(dataEntity);
        startJsonRequest(ApiUrls.ADD_STEPS, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        GetProLinePersonInfoResponseBean bean = Tools.parseJsonTostError(result, GetProLinePersonInfoResponseBean.class);
                        if (bean != null) {
                            populateUI(bean);
                            ToastUtils.toast("工作完成");
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }

    /***
     * 当前角色是否是:生产部门职工，不是则为物流部门职工
     * @return
     */
    private boolean isProductionDepartmentStaff() {
        return UserInfo.getCurrentUser().getPostId()==IronFutureConstants.ROLE_PRODUCTION_DEPARTMENT_STAFF;
    }

}