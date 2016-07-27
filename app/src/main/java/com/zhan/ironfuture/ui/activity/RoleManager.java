package com.zhan.ironfuture.ui.activity;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhan.framework.common.context.GlobalContext;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.ui.fragment.managementDepartment.CompanyNewsFragment;
import com.zhan.ironfuture.ui.fragment.managementDepartment.DepartmentManagementFragment;
import com.zhan.ironfuture.ui.fragment.error.ErrorFragment;
import com.zhan.ironfuture.ui.fragment.managementDepartment.FinancialManagementFragment;
import com.zhan.ironfuture.ui.fragment.rawMaterialDepartment.FormulationDevelopmentFragment;
import com.zhan.ironfuture.ui.fragment.other.JobMarketFragment;
import com.zhan.ironfuture.ui.fragment.logisticsDepartment.LogisticsOrderFragment;
import com.zhan.ironfuture.ui.fragment.ordersCenter.LogisticsOrdersCenterFragment;
import com.zhan.ironfuture.ui.fragment.manufactureDepartment.ManufactureContributionFragment;
import com.zhan.ironfuture.ui.fragment.storageProcurementDepartment.MarketingPurchaseFragment;
import com.zhan.ironfuture.ui.fragment.storageProcurementDepartment.MerchandiseSalesFragment;
import com.zhan.ironfuture.ui.fragment.ordersCenter.OrdersCenterFragment;
import com.zhan.ironfuture.ui.fragment.manufactureDepartment.ProductLineFragment;
import com.zhan.ironfuture.ui.fragment.constructionDepartment.RouteConstructionFragment;
import com.zhan.ironfuture.ui.fragment.constructionDepartment.RouteManagementFragment;
import com.zhan.ironfuture.ui.fragment.more.SettingsFragment;
import com.zhan.ironfuture.ui.fragment.more.StaffListFragment;
import com.zhan.ironfuture.ui.fragment.storageProcurementDepartment.StorageRecordFragment;
import com.zhan.ironfuture.ui.fragment.storageProcurementDepartment.StoreManagementFragment;
import com.zhan.ironfuture.ui.fragment.more.SystemMessage;
import com.zhan.ironfuture.ui.fragment.managementDepartment.TalentMarketFragment;
import com.zhan.ironfuture.ui.fragment.logisticsDepartment.TruckDetailsFragment;
import com.zhan.ironfuture.ui.fragment.rawMaterialDepartment.WasteRefiningFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：keke on 2016/4/25 16:41
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
public class RoleManager {

    public static String TAG_MORE = "more";

    private Context mContext;

    public RoleManager(Context context) {
        mContext = context;
    }


    public List<Page> generatePages(int role) {
        List<Page> pageList;

        switch (role) {
            case IronFutureConstants.ROLE_CORPORATE:
            case IronFutureConstants.ROLE_CEO:
                pageList = generateManagementPages();//管理层
                break;
            case IronFutureConstants.ROLE_CONSTRUCTION_MANAGER:
            case IronFutureConstants.ROLE_CONSTRUCTION_STAFF:
                pageList = generateConstructionPages();//基建部门经理
                break;
            case IronFutureConstants.ROLE_LOGISTICS_DEPARTMENT_MANAGER:
                pageList = generateLogisticsDepartmentManager();//物流部门经理
                break;
            case IronFutureConstants.ROLE_RAW_MATERIALS_MANAGER:
            case IronFutureConstants.ROLE_RAW_MATERIALS_STAFF:
            case IronFutureConstants.ROLE_SECONDARY_FORMULA_ADMINISTRATOR:
                pageList = generateRawMaterialDepartmentManager();//原料部门经理
                break;
            case IronFutureConstants.ROLE_PRODUCTION_MANAGER:
                pageList = generateManufactureDepartmentManager();//生产部门经理
                break;
            case IronFutureConstants.ROLE_WAREHOUSE_MANAGER:
            case IronFutureConstants.ROLE_WAREHOUSE_STAFF:
                pageList = generateStorageProcurementDepartmentManager(); //仓储购销经理
                break;
            case IronFutureConstants.ROLE_PRODUCTION_DEPARTMENT_STAFF:
            case IronFutureConstants.ROLE_LOGISTICS_DEPARTMENT_STAFF:
                pageList = generateManufacturingWorkerPages(); //产线生产员工
                break;
            case IronFutureConstants.ROLE_NONE:
                pageList = generateJobMarket();//求职市场
                break;
            case IronFutureConstants.ROLE_PRODUCT_FORMULA_MANAGER:
                //无业人员
                pageList = generateUnknownRolePage();
                break;
            default:
                pageList = generateUnknownRolePage();
        }
        return pageList;
    }

    /**
     * 管理层页面
     *
     * @return
     */
    private List<Page> generateManagementPages() {
        List<Page> pageList = new ArrayList<>();
        //部门管理
        DepartmentManagementFragment departmentManagementFragment = new DepartmentManagementFragment();
        pageList.add(generatePage(departmentManagementFragment, R.string.department_management, R.drawable.tab_icon_normal_depart_manage, R.drawable.tab_icon_depart_manage_pressed));
        //订单中心
        OrdersCenterFragment ordersCenterFragment = new OrdersCenterFragment();
        pageList.add(generatePage(ordersCenterFragment, R.string.orders_center, R.drawable.tab_icon_normal_order_center, R.drawable.tab_icon_order_center_pressed));
        //人才市场
        TalentMarketFragment talentMarketFragment = new TalentMarketFragment();
        pageList.add(generatePage(talentMarketFragment, R.string.talent_market, R.drawable.tab_icon_normal_talent_market, R.drawable.tab_icon_talent_market_pressed));
        //公司消息
        CompanyNewsFragment companyNewsFragment = new CompanyNewsFragment();
        pageList.add(generatePage(companyNewsFragment, R.string.company_news, R.drawable.tab_icon_normal_company_message, R.drawable.tab_icon_company_message_pressed));
        return pageList;
    }

    /**
     * 基建部门经理页面
     *
     * @return
     */
    private List<Page> generateConstructionPages() {
        List<Page> pageList = new ArrayList<>();
        //路线建设
        RouteConstructionFragment routeConstructionFragment = new RouteConstructionFragment();
        pageList.add(generatePage(routeConstructionFragment, R.string.route_construction, R.drawable.tab_icon_normal_route_construct, R.drawable.tab_icon_route_construct_pressed));
        //线路管理
        RouteManagementFragment routeManagementFragment = new RouteManagementFragment();
        pageList.add(generatePage(routeManagementFragment, R.string.route_management, R.drawable.tab_icon_normal_route_manage, R.drawable.tab_icon_route_manage_pressed));
        return pageList;
    }

    /**
     * 物流部门经理页面
     *
     * @return
     */
    private List<Page> generateLogisticsDepartmentManager() {
        List<Page> pageList = new ArrayList<>();
        //货车详情
        TruckDetailsFragment truckDetailsFragment = new TruckDetailsFragment();
        pageList.add(generatePage(truckDetailsFragment, R.string.truck_details, R.drawable.tab_icon_normal_truck_detail, R.drawable.tab_icon_truck_detail_pressed));
        //订单中心
        LogisticsOrdersCenterFragment ordersCenterFragment = new LogisticsOrdersCenterFragment();
        pageList.add(generatePage(ordersCenterFragment, R.string.orders_center, R.drawable.tab_icon_normal_order_center, R.drawable.tab_icon_order_center_pressed));
        return pageList;
    }

    /**
     * 原料部门经理页面
     *
     * @return
     */
    private List<Page> generateRawMaterialDepartmentManager() {
        List<Page> pageList = new ArrayList<>();
        //废料精炼
        WasteRefiningFragment wasteRefiningFragment = new WasteRefiningFragment();
        pageList.add(generatePage(wasteRefiningFragment, R.string.waste_refining, R.drawable.tab_icon_normal_waste, R.drawable.tab_icon_waste_pressed));
        //配方研发
        FormulationDevelopmentFragment formulationDevelopmentFragment = new FormulationDevelopmentFragment();
        pageList.add(generatePage(formulationDevelopmentFragment, R.string.formulation_development, R.drawable.tab_icon_normal_formulation_development, R.drawable.tab_icon_formulation_development_pressed));
        return pageList;
    }

    /**
     * 生产部门经理页面
     *
     * @return
     */
    private List<Page> generateManufactureDepartmentManager() {
        List<Page> pageList = new ArrayList<>();
        //废料精炼
        ProductLineFragment productLineFragment = new ProductLineFragment();
        pageList.add(generatePage(productLineFragment, R.string.product_line, R.drawable.tab_icon_normal, R.drawable.tab_icon_pressed));

        return pageList;
    }

    /**
     * 仓储购销经理页面
     *
     * @return
     */
    private List<Page> generateStorageProcurementDepartmentManager() {
        List<Page> pageList = new ArrayList<>();
        //仓库管理
        StoreManagementFragment storeManagementFragment = new StoreManagementFragment();
        pageList.add(generatePage(storeManagementFragment, R.string.store_management, R.drawable.tab_icon_normal_warehouse_manage, R.drawable.tab_icon_warehouse_manage_pressed));

        //市场采购
        MarketingPurchaseFragment marketingPurchaseFragment = new MarketingPurchaseFragment();
        pageList.add(generatePage(marketingPurchaseFragment, R.string.marketing_purchase, R.drawable.tab_icon_normal_market_purchase, R.drawable.tab_icon_market_purchase_pressed));

        //商品销售
        MerchandiseSalesFragment merchandiseSalesFragment = new MerchandiseSalesFragment();
        pageList.add(generatePage(merchandiseSalesFragment, R.string.merchandise_sales, R.drawable.tab_icon_normal_product_sale, R.drawable.tab_icon_product_sale_pressed));

        //物流订单
        LogisticsOrderFragment logisticsOrderFragment = new LogisticsOrderFragment();
        pageList.add(generatePage(logisticsOrderFragment, R.string.logistics_order, R.drawable.tab_icon_normal_logistics_order, R.drawable.tab_icon_logistics_order_pressed));

        return pageList;
    }

    /**
     * 产线生产员工页面
     *
     * @return
     */
    private List<Page> generateManufacturingWorkerPages() {
        List<Page> pageList = new ArrayList<>();
        //生产贡献
        ManufactureContributionFragment manufactureContributionFragment = new ManufactureContributionFragment();
        pageList.add(generatePage(manufactureContributionFragment, R.string.manufacture_contribution, R.drawable.tab_icon_normal, R.drawable.tab_icon_pressed));
        return pageList;
    }

    /**
     * 求职市场
     */
    private List<Page> generateJobMarket() {
        List<Page> pageList = new ArrayList<>();
        //生产贡献
        JobMarketFragment jobMarketFragment = new JobMarketFragment();
        pageList.add(generatePage(jobMarketFragment, R.string.job_market, R.drawable.tab_icon_normal, R.drawable.tab_icon_pressed));
        return pageList;
    }

    /**
     * 未知权限
     *
     * @return
     */
    private List<Page> generateUnknownRolePage() {
        List<Page> pageList = new ArrayList<>();
        //错误页面
        ErrorFragment errorFragment = ErrorFragment.getInstance("未知权限");
        pageList.add(generatePage(errorFragment, R.string.error, R.drawable.tab_icon_normal, R.drawable.tab_icon_pressed));
        return pageList;
    }

    private Page generatePage(ABaseFragment fragment, int titleRes, int normalDrawableId, int selectedDrawableId) {
        Page page = new Page();
        page.TAG = fragment.getClass().getSimpleName();
        page.bottomTitle = generateBottomTextView(mContext, GlobalContext.getInstance().getString(titleRes));
        page.bottomTitle.setTag(page.TAG);
        //page.bottomImageView = generateBottomImageView(mContext);
        //page.bottomImageView.setTag(page.TAG);
        page.pageFragment = fragment;
        page.normalDrawableId = normalDrawableId;
        page.selectedDrawableId = selectedDrawableId;
        return page;
    }

    //公共模块
    public Page generateMorePage(int role) {
        Page commonPage = new Page();
        commonPage.TAG = TAG_MORE;
        commonPage.bottomTitle = generateBottomMoreTextView(mContext, "更多");
        commonPage.bottomTitle.setTag(commonPage.TAG);
        //commonPage.bottomImageView = generateBottomImageView(mContext);
        //commonPage.bottomImageView.setTag(commonPage.TAG);
        commonPage.pageFragment = null;
        commonPage.normalDrawableId = R.drawable.main_icon_more;
        commonPage.selectedDrawableId = R.drawable.main_icon_more_selected;

        switch (role) {
            case IronFutureConstants.ROLE_CORPORATE:
            case IronFutureConstants.ROLE_CEO:
                //管理层
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.system_message_reminder), R.drawable.more_system_message_selector, SystemMessage.class));
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.settings), R.drawable.more_settings_selector, SettingsFragment.class));
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.staff_manager), R.drawable.more_people_management_selector, StaffListFragment.class));
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.financial_management), R.drawable.more_financial_management_selector, FinancialManagementFragment.class));
                break;
            case IronFutureConstants.ROLE_CONSTRUCTION_MANAGER://基建部门经理
            case IronFutureConstants.ROLE_LOGISTICS_DEPARTMENT_MANAGER://物流部门经理
            case IronFutureConstants.ROLE_PRODUCTION_MANAGER://生产部门经理
            case IronFutureConstants.ROLE_RAW_MATERIALS_MANAGER://原料部门经理
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.system_message),R.drawable.more_system_message_selector, SystemMessage.class));
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.financial_management), R.drawable.more_financial_management_selector,FinancialManagementFragment.class));
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.settings), R.drawable.more_settings_selector,SettingsFragment.class));
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.staff_manager),R.drawable.more_people_management_selector, StaffListFragment.class));
                break;
            case IronFutureConstants.ROLE_WAREHOUSE_MANAGER:
            case IronFutureConstants.ROLE_WAREHOUSE_STAFF:
                //仓储购销经理
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.system_message),R.drawable.more_system_message_selector, SystemMessage.class));
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.financial_management), R.drawable.more_financial_management_selector,FinancialManagementFragment.class));
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.settings),R.drawable.more_settings_selector, SettingsFragment.class));
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.staff_manager),R.drawable.more_people_management_selector, StaffListFragment.class));
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.store_in_out_record),R.drawable.more_io_log_selector, StorageRecordFragment.class));
                break;
          /*case 10://产线生产员工
            case 8://物流装卸员工
            case 14://物流部门职工
            case 9://无业人员*/
            default:
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.system_message_reminder), R.drawable.more_system_message_selector, SystemMessage.class));
                commonPage.morePages.add(new MorePageInfo(GlobalContext.getInstance().getString(R.string.settings), R.drawable.more_system_message_selector, SettingsFragment.class));
                break;
        }
        return commonPage;
    }

    private ImageView generateBottomImageView(Context context) {
        ImageView bottomImageView = new ImageView(context);
        bottomImageView.setImageResource(R.drawable.ic_company_logo);
        return bottomImageView;
    }

    private TextView generateBottomMoreTextView(Context context, String bottomTitle) {
        TextView bottomTextView = new TextView(context);
        bottomTextView.setText(bottomTitle);
        bottomTextView.setCompoundDrawablePadding(PixelUtils.dp2px(2));
        bottomTextView.setTextSize(14);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER;
        bottomTextView.setLayoutParams(params);
        bottomTextView.setGravity(Gravity.CENTER);
        return bottomTextView;
    }

    private TextView generateBottomTextView(Context context, String bottomTitle) {
        TextView bottomTextView = new TextView(context);
        bottomTextView.setText(bottomTitle);
        bottomTextView.setCompoundDrawablePadding(PixelUtils.dp2px(2));
        bottomTextView.setTextSize(16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER;
        bottomTextView.setLayoutParams(params);
        bottomTextView.setGravity(Gravity.CENTER);
        return bottomTextView;
    }

}
