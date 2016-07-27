package com.zhan.ironfuture.ui.fragment.manufactureDepartment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.ui.fragment.APullToRefreshListFragment;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.ProductCreatLineRequestBean;
import com.zhan.ironfuture.beans.ProductCreatLineResponseBean;
import com.zhan.ironfuture.beans.ProductLineProductDetailContent;
import com.zhan.ironfuture.beans.ProductLineRequestBean;
import com.zhan.ironfuture.beans.ProductLineResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.utils.Tools;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 生产产线
 */
public class ProductLineFragment extends APullToRefreshListFragment<ProductLineFragment.ProductLineInfo> {
    //Views
    private TextView mViewMaterial;
    private TextView mViewProduct;
    private ImageView mComplete;

    //Tools
    private LayoutInflater mInflater;

    private static final int TAB_MATERIAL = 0;
    private static final int TAB_PRODUCT = 1;
    private static final int TYPE_COUNT = 2;//类型总数

    private String productline = "PROLINE_MATERIAL";
    private int mBuyLinePrice;
    private int mCurTab = TAB_MATERIAL;
    private String prolineType = "RECIPE_MATERIAL_P2";

    private long mServerTime;
    private int recipeSum=0;

    //data

    @Override
    protected int inflateContentView() {
        return R.layout.frag_product_line;
    }

/*此方法只有刷新，没有加载更多
    @Override
    protected void setInitPullToRefresh(ListView listView, PullToRefreshListView pullToRefreshListView, Bundle savedInstanceState) {
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
    }
*/

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("生产产线");
        int TAB_MATERIAL = 0;
        int TAB_PRODUCT = 1;
        int TYPE_COUNT = 2;//类型总数

        String productline = "PROLINE_MATERIAL";
        int mBuyLinePrice;
        int mCurTab = TAB_MATERIAL;
        String prolineType = "RECIPE_MATERIAL_P2";


        mInflater = inflater;
        if (getActivity() instanceof ActionBarActivity) {
            ActionBarActivity actionBarActivity = (ActionBarActivity) getActivity();
            actionBarActivity.setShowActionBarCustomerContent(true);
            FrameLayout actionbarContent = actionBarActivity.getActionBarCustomerContent();

            actionbarContent.removeAllViews();
            View headerLayout = mInflater.inflate(R.layout.header_product_line, null);
            mViewMaterial = (TextView) headerLayout.findViewById(R.id.material);
            mViewProduct = (TextView) headerLayout.findViewById(R.id.product);
            mComplete = (ImageView) headerLayout.findViewById(R.id.product_complete);
            mViewMaterial.setOnClickListener(mOnTabClick);
            mViewProduct.setOnClickListener(mOnTabClick);
            mComplete.setOnClickListener(mOnTabClick);
            actionbarContent.addView(headerLayout);

            mViewMaterial.setBackgroundResource(R.drawable.bg_color3_underline);
            mViewMaterial.setTextColor(0xff17112B);
            mViewMaterial.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
            mViewProduct.setBackgroundResource(R.drawable.default_bg);
            mViewProduct.setTextColor(0xff999999);
            mViewProduct.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
        }

    }


    private View.OnClickListener mOnTabClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.material:
                    mViewMaterial.setBackgroundResource(R.drawable.bg_color3_underline);
                    mViewMaterial.setTextColor(0xff17112B);
                    mViewMaterial.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mViewProduct.setBackgroundResource(R.drawable.default_bg);
                    mViewProduct.setTextColor(0xff999999);
                    mViewProduct.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mCurTab = TAB_MATERIAL;
                    productline = "PROLINE_MATERIAL";
                    prolineType = "RECIPE_MATERIAL_P2";
                    requestData(RefreshMode.refresh);
                    break;
                case R.id.product:
                    mViewMaterial.setBackgroundResource(R.drawable.default_bg);
                    mViewMaterial.setTextColor(0xff999999);
                    mViewMaterial.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mViewProduct.setBackgroundResource(R.drawable.bg_color3_underline);
                    mViewProduct.setTextColor(0xff17112B);
                    mViewProduct.setPadding(PixelUtils.dp2px(16), 0, PixelUtils.dp2px(16), 0);
                    mCurTab = TAB_PRODUCT;
                    productline = "PROLINE_FINAL";
                    prolineType = "RECIPE_PROLINE";
                    requestData(RefreshMode.refresh);
                    break;
                case R.id.product_complete:
                    showDialog();
                    break;
            }
        }
    };

    private void showDialog() {
        final Dialog dialog = Tools.createDialog(getActivity(), R.layout.dialog_product_line);
        TextView owner_title = (TextView) dialog.findViewById(R.id.owner_title);
        owner_title.setText("购买新的产线需要花费" + mBuyLinePrice + " 确认购买吗？");
        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.loading).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatLine();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void creatLine() {
        if(isRequestProcessing(ApiUrls.CREATEPROLINE)){
            return;
        }
        ProductCreatLineRequestBean createRequest = new ProductCreatLineRequestBean();
        ProductCreatLineRequestBean.DataEntity data = new ProductCreatLineRequestBean.DataEntity();
        data.setComId(UserInfo.getCurrentUser().getComId());
        data.setProlineType(productline);
        data.setProlineName("");
        createRequest.setData(data);
        startJsonRequest(ApiUrls.CREATEPROLINE, createRequest, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        ProductCreatLineResponseBean responseBean = Tools.parseJsonTostError(result, ProductCreatLineResponseBean.class);
                        if (responseBean != null) {
                            ToastUtils.toast(responseBean.getMessage());
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                }
            }
        });
    }

    @Override
    protected ABaseAdapter.AbstractItemView<ProductLineInfo> newItemView() {
        return new ProductLineMetailItemView();
    }

    @Override
    public int getItemViewType(int position) {
        return mCurTab;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        if(recipeSum > 0){
            ProductLineProductDetailContent content = new ProductLineProductDetailContent();
            content.setProlineId(getAdapterItems().get((int) id).getProlineId());
            content.setProlineName(getAdapterItems().get((int) id).getProlineName());
            content.setRecipeId(getAdapterItems().get((int) id).getRecipeId());
            content.setProlineType(prolineType);
            ProductLineProductDetailFragment.launch(getActivity(), content);
        }else{
            ToastUtils.toast("您尚未有可提供生产的配方");
        }

    }

    @Override
    protected void requestData(RefreshMode mode) {
        ProductLineRequestBean request = new ProductLineRequestBean();
        ProductLineRequestBean.DataEntity data = new ProductLineRequestBean.DataEntity();
        data.setProlineType(productline);
        request.setData(data);
        startJsonRequest(ApiUrls.GETPROLINELIST, request, new PagingTask<ProductLineResponseBean>(mode) {
            @Override
            public ProductLineResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, ProductLineResponseBean.class);
            }

            @Override
            public String verifyResponseResult(ProductLineResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected List<ProductLineInfo> parseResult(ProductLineResponseBean baseResponseBean) {
                List<ProductLineInfo> beanList = new LinkedList<>();
                if (baseResponseBean != null && baseResponseBean.getData() != null) {
                    mBuyLinePrice = baseResponseBean.getData().getBuyProLinePrice();
                    mServerTime = baseResponseBean.getServerTime();
                    for (ProductLineResponseBean.DataEntity.ProLineBackListEntity item : baseResponseBean.getData().getProLineBackList()) {
                        ProductLineInfo productLineInfo = new ProductLineInfo();
                        productLineInfo.setProlineId(item.getProlineId());
                        productLineInfo.setProlineName(item.getProlineName());
                        productLineInfo.setProOutputId(item.getProOutputId());
                        productLineInfo.setOutputName(item.getOutputName());
                        productLineInfo.setRecipeId(item.getRecipeId());
                        productLineInfo.setRecipeName(item.getRecipeName());
                        productLineInfo.setEndLockTime(item.getEndLockTime());
                        beanList.add(productLineInfo);
                    }
                    recipeSum=baseResponseBean.getData().getRecipeSum();
                }
                return beanList;
            }
        });
    }

    private class ProductLineMetailItemView extends ABaseAdapter.AbstractItemView<ProductLineInfo> {
        //view1
        @ViewInject(id = R.id.product_line_num_title)
        TextView mProductLineTitle;
        @ViewInject(id = R.id.product_line_metail_output_name)
        TextView mProductOutName;
        @ViewInject(id = R.id.product_line_metail_recip_name)
        TextView mProductRecipName;
        @ViewInject(id = R.id.product_line_metail_next_time)
        TextView mNextName;
        //view2
        @ViewInject(id = R.id.product_line_goods_title)
        TextView mGoodsTitle;
        @ViewInject(id = R.id.goods_line_recipe_name)
        TextView mGoodsRecipeName;
        @ViewInject(id = R.id.goods_line_next_time)
        TextView mGoodsTime;

        @Override
        public int inflateViewId() {
            switch (getItemViewType()) {
                case TAB_MATERIAL:
                    return R.layout.list_item_product_line_metail;
                case TAB_PRODUCT:
                    return R.layout.list_item_product_line_goods;
                default:
                    return 0;
            }
        }

        @Override
        public void bindingData(View convertView, ProductLineInfo data) {
            switch (getItemViewType()) {
                case TAB_MATERIAL:
                    mProductLineTitle.setText("#" + data.getProlineId() + "");
                    int endLockTime = (int) data.getEndLockTime();
                    if (data.getRecipeId() == 0) {
                        mProductOutName.setText("未生产");
                        mProductRecipName.setText("***");
                        mNextName.setText("0天0时0分0秒");
                    } else {
                        if (endLockTime <= (int) mServerTime) {
                            mProductOutName.setText("未生产");
                            mProductRecipName.setText("***");
                            mNextName.setText("0天0时0分0秒");
                        } else {
                            mProductRecipName.setText(data.getOutputName());
                            mProductOutName.setText(data.getRecipeName());
                            long leftTime = data.getEndLockTime() - mServerTime;
                            mNextName.setText(Tools.parseTimeLeftStr(leftTime));
                        }
                    }

                    break;
                case TAB_PRODUCT:
                    mGoodsTitle.setText("#" + data.getProlineId() + "");
                    int endLockTime_product = (int) data.getEndLockTime();
                    if(data.getRecipeId() != 0){
                        if (endLockTime_product <= (int) mServerTime) {
                            mGoodsRecipeName.setText("未生产");
                            mGoodsTime.setText("0天0时0分0秒");
                        } else {
                            mGoodsRecipeName.setText(data.getRecipeName());
                            long leftTime = data.getEndLockTime() - mServerTime;
                            mGoodsTime.setText(Tools.parseTimeLeftStr(leftTime));
                        }
                    }else{
                        mGoodsRecipeName.setText("未生产");
                        mGoodsTime.setText("0天0时0分0秒");
                    }
                    break;
            }
        }
    }

    public class ProductLineInfo {
        private int prolineId;
        private String prolineName;
        private int proOutputId;
        private String outputName;
        private int recipeId;
        private String recipeName;
        private long endLockTime;

        public void setProlineId(int prolineId) {
            this.prolineId = prolineId;
        }

        public void setProlineName(String prolineName) {
            this.prolineName = prolineName;
        }

        public void setProOutputId(int proOutputId) {
            this.proOutputId = proOutputId;
        }

        public void setOutputName(String outputName) {
            this.outputName = outputName;
        }

        public void setRecipeId(int recipeId) {
            this.recipeId = recipeId;
        }

        public void setRecipeName(String recipeName) {
            this.recipeName = recipeName;
        }

        public void setEndLockTime(long endLockTime) {
            this.endLockTime = endLockTime;
        }

        public int getProlineId() {
            return prolineId;
        }

        public String getProlineName() {
            return prolineName;
        }

        public int getProOutputId() {
            return proOutputId;
        }

        public String getOutputName() {
            return outputName;
        }

        public int getRecipeId() {
            return recipeId;
        }

        public String getRecipeName() {
            return recipeName;
        }

        public long getEndLockTime() {
            return endLockTime;
        }
    }
}
