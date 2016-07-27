package com.zhan.ironfuture.ui.fragment.manufactureDepartment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhan.framework.component.container.FragmentArgs;
import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.framework.view.pickerview.LoopView;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.beans.ProductLineDetailBeginRequestBean;
import com.zhan.ironfuture.beans.ProductLineDetailBeginResponseBean;
import com.zhan.ironfuture.beans.ProductLineDetailRequestBean;
import com.zhan.ironfuture.beans.ProductLineDetailResponseBean;
import com.zhan.ironfuture.beans.ProductLineProductDetailContent;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.MyListView;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 */
public class ProductLineProductDetailFragment extends ABaseFragment{
    private final static String ARG_KEY = "productlinedetail";

    @ViewInject(id = R.id.product_detail_peifang_num)
    TextView mPeiFangName ;
    @ViewInject(id = R.id.product_detail_this_all_goods)
    TextView mProductThisAllGoods ;
    @ViewInject(id = R.id.product_detail_out_goods_name)
    TextView mGoodsName ;
    @ViewInject(id = R.id.product_detail_out_goods_yesterday)
    TextView mGoodsYesterday ;
    @ViewInject(id = R.id.product_detail_time)
    EditText mDetailTime ;

    @ViewInject(id = R.id.change_product_detail,click = "OnClick")
    ImageView mChangeFormula;
    @ViewInject(id = R.id.product_detail_listview)
    MyListView myListView;

    private LoopView mPickView;

    private ArrayList<ProductDetailInfo> mDataList = new ArrayList<>();
    private List<ProductLineDetailResponseBean.DataEntity.RecipeListEntity.DetailsEntity> mDetailsList = new ArrayList<>();

    private ProductDetailAdapter mAdapter;
    private PopupWindow mPopupWindow;
    private View mChangLessonPopMenuContent;
    private ProductLineProductDetailContent mContent ;

    private String detailTime ;
    private int mRecipeId=0 ;


    @Override
    protected int inflateContentView() {
        return R.layout.frag_product_line_product_detail;
    }

    public static void launch(FragmentActivity activity, ProductLineProductDetailContent content) {
        FragmentArgs args = new FragmentArgs();
        args.add(ARG_KEY, content);
        FragmentContainerActivity.launch(activity, ProductLineProductDetailFragment.class, args);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContent = savedInstanceState == null ? (ProductLineProductDetailContent) getArguments().getSerializable(ARG_KEY)
                : (ProductLineProductDetailContent) savedInstanceState.getSerializable(ARG_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_KEY, mContent);
    }
    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("#"+mContent.getProlineId() + "产线");
        intiPopMenu();
    }

    @Override
    public void onPrepareActionbarMenu(TextView menu, Activity activity) {
        super.onPrepareActionbarMenu(menu, activity);
        menu.setText("完成");
        menu.setTextColor(Color.rgb(255, 70, 15));
    }

    @Override
    public void onActionBarMenuClick() {
        super.onActionBarMenuClick();
        if (!checkInput()) {
            return;
        }
        beginProLine();
    }


    private boolean checkInput() {
        detailTime = mDetailTime.getText().toString();
        if (TextUtils.isEmpty(detailTime)) {
            ToastUtils.toast("请输入6的倍数的生产周期");
            return false;
        }
        if(Integer.parseInt(detailTime) < 0){
            ToastUtils.toast("输入的生产周期不符合规则");
            return false;
        }
        return true;
    }

    private void OnClick(View v){
        switch (v.getId()){
            case R.id.change_product_detail:
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mChangeFormula.getWindowToken(), 0); //强制隐藏键盘
                showChooseMenu();
                break;
        }
    }

    private void beginProLine() {
        ProductLineDetailBeginRequestBean request = new ProductLineDetailBeginRequestBean();
        ProductLineDetailBeginRequestBean.DataEntity data = new ProductLineDetailBeginRequestBean.DataEntity();
        data.setProLineId(mContent.getProlineId());
        data.setTime(Long.valueOf(detailTime).longValue());
        data.setRecipeId(mRecipeId);
        request.setData(data);
        startJsonRequest(ApiUrls.BEGINPROLINE, request, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        ProductLineDetailBeginResponseBean responseBean= Tools.parseJsonTostError(result, ProductLineDetailBeginResponseBean.class);
                        if(responseBean!=null){
                            ToastUtils.toast(responseBean.getMessage());
                            getActivity().finish();
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
        ProductLineDetailRequestBean request = new ProductLineDetailRequestBean();
        ProductLineDetailRequestBean.DataEntity data = new ProductLineDetailRequestBean.DataEntity();
        data.setProlineType(mContent.getProlineType());
        data.setProlineId(mContent.getProlineId());
        request.setData(data);
        startJsonRequest(ApiUrls.GEPROLINEINFO, request, new BaseHttpRequestTask<ProductLineDetailResponseBean>() {

            @Override
            public ProductLineDetailResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, ProductLineDetailResponseBean.class);
            }

            @Override
            public String verifyResponseResult(ProductLineDetailResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(ProductLineDetailResponseBean bean) {
                super.onSuccess(bean);
                mDataList.clear();
                mDetailsList.clear();
                if(bean != null && bean.getData() != null && bean.getData().getRecipeList() != null){
                    for (ProductLineDetailResponseBean.DataEntity.RecipeListEntity recipeListEntity : bean.getData().getRecipeList()) {
                        ProductDetailInfo productDetailInfo = new ProductDetailInfo();
                        productDetailInfo.setRecipeId(recipeListEntity.getRecipeId());
                        productDetailInfo.setRecipeName(recipeListEntity.getRecipeName());
                        productDetailInfo.setComId(recipeListEntity.getComId());
                        productDetailInfo.setRecipeType(recipeListEntity.getRecipeType());
                        productDetailInfo.setProOutputId(recipeListEntity.getProOutputId());
                        productDetailInfo.setStatue(recipeListEntity.getStatue());
                        productDetailInfo.setCreateTime(recipeListEntity.getCreateTime());
                        productDetailInfo.setUpdateTime(recipeListEntity.getUpdateTime());
                        productDetailInfo.setProOutputName(recipeListEntity.getProOutputName());
                        productDetailInfo.setGoodsLogo(recipeListEntity.getGoodsLogo());
                        productDetailInfo.setDetailsList(recipeListEntity.getDetails());
                        mDataList.add(productDetailInfo);


                        for (ProductLineDetailResponseBean.DataEntity.RecipeListEntity.DetailsEntity detailsEntity : recipeListEntity.getDetails()) {
                            ProductLineDetailResponseBean.DataEntity.RecipeListEntity.DetailsEntity details = new ProductLineDetailResponseBean.DataEntity.RecipeListEntity.DetailsEntity();
                            details.setDetailId(detailsEntity.getDetailId());
                            details.setRecipeId(detailsEntity.getRecipeId());
                            details.setGoodsId(detailsEntity.getGoodsId());
                            details.setMaterialName(detailsEntity.getMaterialName());
                            details.setMaterialSum(detailsEntity.getMaterialSum());
                            details.setSeqno(detailsEntity.getSeqno());
                            details.setUpdatetime(detailsEntity.getUpdatetime());
                            details.setGoodsLogo(detailsEntity.getGoodsLogo());
                            mDetailsList.add(details);
                        }
                    }

                    for(int i=0;i<mDataList.size();i++){
                        int recipeId=mDataList.get(i).getRecipeId();
                        if(mContent.getRecipeId()==recipeId){
                            mRecipeId = recipeId;
                            refershProductDetailInfo(mDataList.get(i));
                            break;
                        }
                    }

                    mProductThisAllGoods.setText("此次生产周期共" + bean.getData().getProSum() + "件产品");
                    mGoodsYesterday.setText("昨日生产" + bean.getData().getYesterdaySum() + "件，今日生产" + bean.getData().getTodaySum() + "件");
                }

            }
        });
    }

    private void refershProductDetailInfo(ProductDetailInfo productDetailInfo) {
        if(mDataList != null && mDataList.size() != 0){
            mAdapter = new ProductDetailAdapter((ArrayList<ProductLineDetailResponseBean.DataEntity.RecipeListEntity.DetailsEntity>) productDetailInfo.getDetailsList(), getActivity());
            myListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mPeiFangName.setText(productDetailInfo.getRecipeName());
            mGoodsName.setText(productDetailInfo.getProOutputName());
        }
    }

    private class ProductDetailAdapter extends ABaseAdapter<ProductLineDetailResponseBean.DataEntity.RecipeListEntity.DetailsEntity> {

        public ProductDetailAdapter(ArrayList<ProductLineDetailResponseBean.DataEntity.RecipeListEntity.DetailsEntity> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<ProductLineDetailResponseBean.DataEntity.RecipeListEntity.DetailsEntity> newItemView() {
            return new ProductDetailItem();
        }
    }

    private class ProductDetailItem extends ABaseAdapter.AbstractItemView<ProductLineDetailResponseBean.DataEntity.RecipeListEntity.DetailsEntity> {

        @ViewInject(id = R.id.product_detail_name)
        TextView mDetailName ;
        @ViewInject(id = R.id.product_detail_num)
        TextView mDetailNum ;

        @Override
        public int inflateViewId() {
            return R.layout.list_item_product_detail;
        }

        @Override
        public void bindingData(View convertView, ProductLineDetailResponseBean.DataEntity.RecipeListEntity.DetailsEntity data) {
            mDetailName.setText("物品："+data.getMaterialName());
            mDetailNum.setText("数目："+data.getMaterialSum()+"");
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

    public boolean closePopWin() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return true;
        }
        return false;
    }

    private void showChooseMenu() {
        mChangLessonPopMenuContent = getActivity().getLayoutInflater().inflate(R.layout.pop_memu_product_detail, null);
        mPopupWindow.setContentView(mChangLessonPopMenuContent);
        View btnCancel = mChangLessonPopMenuContent.findViewById(R.id.mylesson_exam_time_cancel_time);
        btnCancel.setOnClickListener(mOnExamTimeClickListener);
        View btnFinish = mChangLessonPopMenuContent.findViewById(R.id.pop_menu_product_detail_finish_time);
        btnFinish.setOnClickListener(mOnExamTimeClickListener);
        ArrayList<String> namelist = new ArrayList<>();
        for(int i = 0;i<mDataList.size();i++){
            namelist.add((i+1)+"-"+mDataList.get(i).getRecipeName()+"-"+mDataList.get(i).getProOutputName());
        }
        mPickView = (LoopView) mChangLessonPopMenuContent.findViewById(R.id.pop_menu_product_detail_picker_view);
        mPickView.setNotLoop();
        mPickView.setArrayList(namelist);
        mPickView.setInitPosition(0);

        showPopMenu();
    }

    private View.OnClickListener mOnExamTimeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mylesson_exam_time_cancel_time:
                    closePopWin();
                    break;
                case R.id.pop_menu_product_detail_finish_time:
                    int index = mPickView.getSelectedItem();
                    if(index>=0) {
                        mRecipeId = mDataList.get(index).getRecipeId();
                        refershProductDetailInfo(mDataList.get(index));
                        closePopWin();
                    }
                    break;
            }
        }
    };

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

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    private class ProductDetailInfo{
        private int recipeId;
        private String recipeName;
        private int comId;
        private String recipeType;
        private int proOutputId;
        private int statue;
        private long createTime;
        private long updateTime;
        private String proOutputName;
        private String goodsLogo;
        List<ProductLineDetailResponseBean.DataEntity.RecipeListEntity.DetailsEntity> detailsList = new ArrayList<>();

        public List<ProductLineDetailResponseBean.DataEntity.RecipeListEntity.DetailsEntity> getDetailsList() {
            return detailsList;
        }

        public void setDetailsList(List<ProductLineDetailResponseBean.DataEntity.RecipeListEntity.DetailsEntity> detailsList) {
            this.detailsList = detailsList;
        }


        public int getRecipeId() {
            return recipeId;
        }

        public void setRecipeId(int recipeId) {
            this.recipeId = recipeId;
        }

        public String getRecipeName() {
            return recipeName;
        }

        public void setRecipeName(String recipeName) {
            this.recipeName = recipeName;
        }

        public int getComId() {
            return comId;
        }

        public void setComId(int comId) {
            this.comId = comId;
        }

        public String getRecipeType() {
            return recipeType;
        }

        public void setRecipeType(String recipeType) {
            this.recipeType = recipeType;
        }

        public int getProOutputId() {
            return proOutputId;
        }

        public void setProOutputId(int proOutputId) {
            this.proOutputId = proOutputId;
        }

        public int getStatue() {
            return statue;
        }

        public void setStatue(int statue) {
            this.statue = statue;
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

        public String getProOutputName() {
            return proOutputName;
        }

        public void setProOutputName(String proOutputName) {
            this.proOutputName = proOutputName;
        }

        public String getGoodsLogo() {
            return goodsLogo;
        }

        public void setGoodsLogo(String goodsLogo) {
            this.goodsLogo = goodsLogo;
        }

    }

   /* private class Details{
        private int detailId;
        private int recipeId;
        private int goodsId;
        private String materialName;
        private int materialSum;
        private int seqno;
        private String updatetime;
        private String goodsLogo;

        public int getRecipeId() {
            return recipeId;
        }

        public void setRecipeId(int recipeId) {
            this.recipeId = recipeId;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public int getMaterialSum() {
            return materialSum;
        }

        public void setMaterialSum(int materialSum) {
            this.materialSum = materialSum;
        }

        public int getSeqno() {
            return seqno;
        }

        public void setSeqno(int seqno) {
            this.seqno = seqno;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getGoodsLogo() {
            return goodsLogo;
        }

        public void setGoodsLogo(String goodsLogo) {
            this.goodsLogo = goodsLogo;
        }

        public int getDetailId() {
            return detailId;
        }

        public void setDetailId(int detailId) {
            this.detailId = detailId;
        }
    }
*/
}


