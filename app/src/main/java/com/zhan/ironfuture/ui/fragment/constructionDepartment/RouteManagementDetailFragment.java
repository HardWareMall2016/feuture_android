package com.zhan.ironfuture.ui.fragment.constructionDepartment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.beans.RotateLineContent;
import com.zhan.ironfuture.beans.RouteManagementDetailAddFullRequestBean;
import com.zhan.ironfuture.beans.RouteManagementDetailAddFullResponseBean;
import com.zhan.ironfuture.beans.RouteManagementDetailBuyCarRequestBean;
import com.zhan.ironfuture.beans.RouteManagementDetailBuyCarResponseBean;
import com.zhan.ironfuture.beans.RouteManagementDetailRequestBean;
import com.zhan.ironfuture.beans.RouteManagementDetailResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.MyListView;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/5.
 */
public class RouteManagementDetailFragment extends ABaseFragment {

    private final static String ARG_KEY = "routemanagedetail";

    @ViewInject(id = R.id.route_manage_detail_line_listview)
    MyListView mLineListView;
    @ViewInject(id = R.id.route_manage_detail_car_listview)
    MyListView mCarListView;
    @ViewInject(id = R.id.route_manage_detail_bootm,click = "OnClick")
    RelativeLayout mDetaiBootm ;
    @ViewInject(id = R.id.detail_car_buycar_money)
    TextView mBuyMoney ;

    private ArrayList<RouteManagementDetailLineInfo> mLineList = new ArrayList<>();
    private RouteManagementDetailLineAdapter mLineAdapter;

    private ArrayList<RouteManagementDetailCarInfo> mCarList = new ArrayList<>();
    private RouteManagementDetailCarAdapter mCarAdapter;

    private RotateLineContent mContent;
    private int mRodeLineId;
    private int buyNewCar ;


    @Override
    protected int inflateContentView() {
        return R.layout.frag_route_manage_detail;
    }

    public static void lunch(FragmentActivity activity, RotateLineContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launch(activity, RouteManagementDetailFragment.class, args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = savedInstanceState == null ? (RotateLineContent) getArguments().getSerializable(ARG_KEY)
                : (RotateLineContent) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mContent);
    }


    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        getActivity().setTitle("线路详情");
        mRodeLineId = mContent.getRodeLineId();

    }

    public void OnClick(View view){
        switch (view.getId()){
            case R.id.route_manage_detail_bootm:
                if (Tools.isFastClick()) {
                    return ;
                }
                buyCarDialog();
                break;
        }
    }

    private void buyCarDialog(){
        final Dialog dialog = Tools.createDialog(getActivity(), R.layout.dialog_route_manage_buycar_detail);
        TextView money = (TextView) dialog.findViewById(R.id.dialog_detail_car_money);
        money.setText(String.format("（%d)", buyNewCar));
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyCar();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void buyCar() {
        if(isRequestProcessing(ApiUrls.RODELLINBUYCAR)){
            return;
        }
        RouteManagementDetailBuyCarRequestBean request = new RouteManagementDetailBuyCarRequestBean();
        RouteManagementDetailBuyCarRequestBean.DataEntity data = new RouteManagementDetailBuyCarRequestBean.DataEntity();
        data.setRodeLineId(mRodeLineId);
        request.setData(data);
        startJsonRequest(ApiUrls.RODELLINBUYCAR, request, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        RouteManagementDetailBuyCarResponseBean responseBean= Tools.parseJsonTostError(result, RouteManagementDetailBuyCarResponseBean.class);
                        if(responseBean!=null){
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

    @Override
    public void requestData() {
        RouteManagementDetailRequestBean request = new RouteManagementDetailRequestBean();
        RouteManagementDetailRequestBean.DataEntity data = new RouteManagementDetailRequestBean.DataEntity();
        data.setRodeLineId(mRodeLineId);
        request.setData(data);
        startJsonRequest(ApiUrls.RODELINEDETAIL, request, new BaseHttpRequestTask<RouteManagementDetailResponseBean>() {

            @Override
            public RouteManagementDetailResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, RouteManagementDetailResponseBean.class);
            }

            @Override
            public String verifyResponseResult(RouteManagementDetailResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(RouteManagementDetailResponseBean bean) {
                super.onSuccess(bean);
                //线路详情
                mLineList.clear();
                for (RouteManagementDetailResponseBean.DataEntity.SiteListEntity siteListEntity : bean.getData().getSiteList()) {
                    RouteManagementDetailLineInfo routeManagementDetailLineInfo = new RouteManagementDetailLineInfo();
                    routeManagementDetailLineInfo.setId(siteListEntity.getId());
                    routeManagementDetailLineInfo.setName(siteListEntity.getName());
                    mLineList.add(routeManagementDetailLineInfo);
                }

                mLineAdapter = new RouteManagementDetailLineAdapter(mLineList, getActivity());
                mLineListView.setAdapter(mLineAdapter);
                mLineAdapter.notifyDataSetChanged();

                //车辆详情
                mCarList.clear();
                for (RouteManagementDetailResponseBean.DataEntity.CarListEntity carListEntity : bean.getData().getCarList()) {
                    RouteManagementDetailCarInfo routeManagementDetailCarInfo = new RouteManagementDetailCarInfo();
                    routeManagementDetailCarInfo.setCarId(carListEntity.getCarId());
                    routeManagementDetailCarInfo.setCarName(carListEntity.getCarName());
                    routeManagementDetailCarInfo.setComName(carListEntity.getComName());
                    routeManagementDetailCarInfo.setPeopleSum(carListEntity.getPeopleSum());
                    routeManagementDetailCarInfo.setFuelSum(carListEntity.getFuelSum());
                    routeManagementDetailCarInfo.setAddFuel(carListEntity.getAddFuel());
                    //routeManagementDetailCarInfo.setBuyNewCar(carListEntity.getBuyNewCar());
                    mCarList.add(routeManagementDetailCarInfo);
                }
                buyNewCar = bean.getData().getBuyNewCar();
                mBuyMoney.setText(String.format("购买新货车（花费%d)", buyNewCar));
                mCarAdapter = new RouteManagementDetailCarAdapter(mCarList, getActivity());
                mCarListView.setAdapter(mCarAdapter);
                mCarAdapter.notifyDataSetChanged();

                mCarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                        if (Tools.isFastClick()) {
                            return ;
                        }
                        final int addFuel = mCarList.get((int)id).getAddFuel();

                        final Dialog dialog = Tools.createDialog(getActivity(), R.layout.dialog_route_manage_detail);
                        TextView money = (TextView) dialog.findViewById(R.id.dialog_detail_car_money);
                        money.setText(String.format("%d", addFuel));
                        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.findViewById(R.id.loading).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                supply(mCarList.get((int)id));
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });

            }
        });
    }

    private void supply(RouteManagementDetailCarInfo carInfo) {
        if(isRequestProcessing(ApiUrls.CARADDFULL)){
            return;
        }
        RouteManagementDetailAddFullRequestBean request = new RouteManagementDetailAddFullRequestBean();
        RouteManagementDetailAddFullRequestBean.DataEntity data = new RouteManagementDetailAddFullRequestBean.DataEntity();
        data.setCarsId(carInfo.carId);
        request.setData(data);
        startJsonRequest(ApiUrls.CARADDFULL, request, new AddFuelRequestHandler(this,carInfo));
    }

    private class AddFuelRequestHandler extends HttpRequestHandler{
        private RouteManagementDetailCarInfo mCarInfo;
        public AddFuelRequestHandler(Fragment fragment,RouteManagementDetailCarInfo carInfo) {
            super(fragment);
            mCarInfo=carInfo;
        }
        @Override
        public void onRequestFinished(ResultCode resultCode, String result) {
            switch (resultCode){
                case success:
                    RouteManagementDetailAddFullResponseBean responseBean= Tools.parseJsonTostError(result, RouteManagementDetailAddFullResponseBean.class);
                    if(responseBean!=null){
                        mCarInfo.fuelSum= responseBean.getData().getNowfullSum();
                        mCarAdapter.notifyDataSetChanged();
                        ToastUtils.toast("补充燃料成功");
                    }
                    break;
                default:
                    ToastUtils.toast(result);
            }
        }
    }


    private class RouteManagementDetailLineAdapter extends ABaseAdapter<RouteManagementDetailLineInfo> {

        public RouteManagementDetailLineAdapter(ArrayList<RouteManagementDetailLineInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<RouteManagementDetailLineInfo> newItemView() {
            return new RouteManagementDetailLineItemView();
        }
    }

    private class RouteManagementDetailLineItemView extends ABaseAdapter.AbstractItemView<RouteManagementDetailLineInfo> {

        @ViewInject(id = R.id.detail_line_name)
        TextView mLineName ;
        @ViewInject(id = R.id.detail_company_name)
        TextView mCompanyName;
        @Override
        public int inflateViewId() {
            return R.layout.list_item_route_manage_detail_line;
        }

        @Override
        public void bindingData(View convertView, RouteManagementDetailLineInfo data) {
            mLineName.setText("第"+data.getId()+"站");
            mCompanyName.setText(data.getName());
        }
    }

    private class RouteManagementDetailCarAdapter extends ABaseAdapter<RouteManagementDetailCarInfo> {

        public RouteManagementDetailCarAdapter(ArrayList<RouteManagementDetailCarInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<RouteManagementDetailCarInfo> newItemView() {
            return new RouteManagementDetailCarItemView();
        }
    }

    private class RouteManagementDetailCarItemView extends ABaseAdapter.AbstractItemView<RouteManagementDetailCarInfo> {

        @ViewInject(id = R.id.detail_car_carname)
        TextView mCarName;
        @ViewInject(id = R.id.detail_car_comname)
        TextView mCompanyName ;
        @ViewInject(id = R.id.detail_car_pepnum)
        TextView mPepNum ;
        @ViewInject(id = R.id.detail_car_fuelSum)
        TextView mFuelSum;
        @Override
        public int inflateViewId() {
            return R.layout.list_item_route_manage_detail_car;
        }

        @Override
        public void bindingData(View convertView, RouteManagementDetailCarInfo data) {
            mCarName.setText(data.getCarId()+"车");
            mCompanyName.setText("停靠："+data.getComName()+"公司");
            mPepNum.setText("装卸工："+data.getPeopleSum()+"人");
            if("0".equals(data.getFuelSum())){
                mFuelSum.setText("燃料："+data.getFuelSum());
            }else{
                int surplusFuelSum = (int) (data.getFuelSum()*100);
                mFuelSum.setText("燃料："+surplusFuelSum +"%");
            }
        }
    }

    private class RouteManagementDetailLineInfo {
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

    private class RouteManagementDetailCarInfo {
        private int carId;
        private String carName;
        private String comName;
        private int peopleSum;
        private double fuelSum;
        private int addFuel;
        private int buyNewCar;

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

        public void setComName(String comName) {
            this.comName = comName;
        }

        public void setPeopleSum(int peopleSum) {
            this.peopleSum = peopleSum;
        }

        public void setFuelSum(double fuelSum) {
            this.fuelSum = fuelSum;
        }

        public void setAddFuel(int addFuel) {
            this.addFuel = addFuel;
        }

        public void setBuyNewCar(int buyNewCar) {
            this.buyNewCar = buyNewCar;
        }

        public int getCarId() {
            return carId;
        }

        public String getCarName() {
            return carName;
        }

        public String getComName() {
            return comName;
        }

        public int getPeopleSum() {
            return peopleSum;
        }

        public double getFuelSum() {
            return fuelSum;
        }

        public int getAddFuel() {
            return addFuel;
        }

        public int getBuyNewCar() {
            return buyNewCar;
        }
    }

}
