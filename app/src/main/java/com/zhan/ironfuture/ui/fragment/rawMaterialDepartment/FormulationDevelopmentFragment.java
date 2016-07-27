package com.zhan.ironfuture.ui.fragment.rawMaterialDepartment;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.adapter.ABaseAdapter;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.BaseResponseBean;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.CreateRecipeRequestBean;
import com.zhan.ironfuture.beans.DeleteRecipeRequestBean;
import com.zhan.ironfuture.beans.SelectRecipeListRequestBean;
import com.zhan.ironfuture.beans.SelectRecipeListResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.swipemenulistview.SwipeMenu;
import com.zhan.ironfuture.ui.widget.swipemenulistview.SwipeMenuItem;
import com.zhan.ironfuture.ui.widget.swipemenulistview.SwipeMenuLayout;
import com.zhan.ironfuture.ui.widget.swipemenulistview.SwipeMenuView;
import com.zhan.ironfuture.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 配方研发
 */
public class FormulationDevelopmentFragment extends ABaseFragment {

    private final static int DRAG_ITEM_VIEW_SIZE=PixelUtils.dp2px(70);
    //配方类型
    private final static String RECIPE_TYPE_PROLINE="RECIPE_PROLINE";//成品配方
    private final static String RECIPE_TYPE_P2="RECIPE_MATERIAL_P2";//p2级原料配方
    //物品类型
    private final static String GOOODS_TYPE_P1="GOODS_MATERIAL_P1";//p1级原料
    private final static String GOOODS_TYPE_P2="GOODS_MATERIAL_P2";//p2原料

    //Flag
    //是否是研发模式
    private boolean mEditMode=false;
    //当前是否研发的是二级原料
    private boolean mSecondaryFormulation=true;

    //Views
    private TextView mActionbarLeftMenu;
    private TextView mActionbarRightMenu;

    @ViewInject(id = R.id.formulation_content)
    View mViewFormulationContent;

    @ViewInject(id = R.id.material)
    LinearLayout mViewMaterialContent;

    @ViewInject(id = R.id.list_existing_formulation)
    ListView mListViewExistingFormulation;

    @ViewInject(id = R.id.warehouse_gridView)
    GridView mGridView;

    @ViewInject(id = R.id.secondary_material,click = "OnClick")
    TextView mViewSecondaryMaterial;

    @ViewInject(id = R.id.goods_material,click = "OnClick")
    TextView mViewGoodsMaterial;

    //拖拽相关View
    private View mOccupyView;

    private View mInternalView;

    private Dialog mDialog;

    //Data
    private ArrayList<WarehouseInfo> mWarehouseList = new ArrayList<>();
    private ArrayList<SelectRecipeListResponseBean.DataEntity.RecipeListEntity> mExistingFormulationList=new ArrayList<>();


    //Tools
    private WarehouseAdapter mGridViewAdapter;
    private ExistingFormulationAdapter mListAdapter;
    private LayoutInflater mInflater;
    private int mMaterialItemMarginLef;//配方中子view距离左边的边距


    //flag
    private String mRecipeType=RECIPE_TYPE_P2;
    private String mGoodsType=GOOODS_TYPE_P1;


    @Override
    protected int inflateContentView() {
        return R.layout.frag_formulation_development;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        mInflater=inflater;
        initView();
        refreshViewByMode();
    }

    private void initView(){
        if(getActivity() instanceof ActionBarActivity){
            ActionBarActivity actionBarActivity= (ActionBarActivity) getActivity();
            actionBarActivity.setShowActionBarCustomerContent(true);

            View header=mInflater.inflate(R.layout.header_formulation_development,null);
            actionBarActivity.getActionBarCustomerContent().addView(header);

            mActionbarLeftMenu=(TextView)header.findViewById(R.id.cancel);
            mActionbarRightMenu=(TextView)header.findViewById(R.id.right_menu);
            mActionbarLeftMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditMode = false;
                    refreshViewByMode();
                    resotreWarehouse();
                }
            });
            mActionbarRightMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mEditMode){
                        createRecipeQuery();
                    }else{
                        mEditMode=true;
                        refreshViewByMode();
                    }
                }
            });
        }

        mEditMode=false;
        mWarehouseList.clear();

        mSecondaryFormulation=true;
        refreshViewByFormulationType();

        mMaterialItemMarginLef=(Tools.getScreenWidth(getActivity())-4*DRAG_ITEM_VIEW_SIZE)/5;

        LayoutTransition mLayoutTransition = new LayoutTransition();
        mViewMaterialContent.setLayoutTransition(mLayoutTransition);
        mLayoutTransition.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
        mLayoutTransition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
        //设置每个动画持续的时间
        mLayoutTransition.setDuration(300);

        mViewMaterialContent.setOnDragListener(mOnDragListener);

        mGridViewAdapter = new WarehouseAdapter(mWarehouseList, getActivity());
        mGridView.setAdapter(mGridViewAdapter);
        mGridView.setOnItemLongClickListener(mOnItemLongClickListener);
        //mGridView.setOnItemClickListener(mOnItemClickListener);

        mListAdapter=new ExistingFormulationAdapter(mExistingFormulationList,getActivity());
        mListViewExistingFormulation.setAdapter(mListAdapter);

        mListViewExistingFormulation.getLayoutParams().height= getListHeight();
    }

    private int  getListHeight(){
        int materialHeight=(Tools.getScreenWidth(getActivity()) - 5 * PixelUtils.dp2px(16))/4;
        int listItemHeight=materialHeight+PixelUtils.dp2px(16)*2+PixelUtils.dp2px(40);
        return listItemHeight;
    }

    @Override
    public boolean isContentEmpty() {
        return mWarehouseList.size()==0;
    }

    @Override
    public void requestData() {
        SelectRecipeListRequestBean request = new SelectRecipeListRequestBean();
        SelectRecipeListRequestBean.DataEntity data = new SelectRecipeListRequestBean.DataEntity();
        data.setRecipeType(mRecipeType);
        data.setGooodsType(mGoodsType);
        request.setData(data);

        startJsonRequest(ApiUrls.SELECT_RECIPE_LIST, request, new BaseHttpRequestTask<SelectRecipeListResponseBean>() {
            @Override
            public SelectRecipeListResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, SelectRecipeListResponseBean.class);
            }

            @Override
            public String verifyResponseResult(SelectRecipeListResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(SelectRecipeListResponseBean result) {
                super.onSuccess(result);

                mWarehouseList.clear();
                if (result.getData().getSpaceGoodsList() != null && result.getData().getSpaceGoodsList().size() > 0) {
                    for (SelectRecipeListResponseBean.DataEntity.SpaceGoodsListEntity space : result.getData().getSpaceGoodsList()) {
                        WarehouseInfo data = new WarehouseInfo();
                        data.goodsCount = space.getGoodsCount();
                        data.goodsId = space.getGoodsId();
                        data.goodsLogo = space.getGoodsLogo();
                        data.goodsName = space.getGoodsName();
                        data.spaceId = space.getSpaceId();

                        mWarehouseList.add(data);
                    }
                }

                mGridViewAdapter.notifyDataSetChanged();

                mExistingFormulationList.clear();

                if (result.getData().getRecipeList() != null) {
                    for (SelectRecipeListResponseBean.DataEntity.RecipeListEntity data : result.getData().getRecipeList()) {
                        mExistingFormulationList.add(data);
                    }
                }

                mListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void refreshViewByMode(){
        if(!mEditMode){
            mActionbarRightMenu.setText("研发");
            mActionbarLeftMenu.setVisibility(View.GONE);
            mViewFormulationContent.setVisibility(View.GONE);
        }else{
            mActionbarRightMenu.setText("完成");
            mViewFormulationContent.setVisibility(View.VISIBLE);
            mActionbarLeftMenu.setVisibility(View.VISIBLE);
        }
    }

    void OnClick(View view) {
        switch (view.getId()){
            case R.id.secondary_material:
                if(mSecondaryFormulation){
                    return;
                }
                mSecondaryFormulation=true;
                break;
            case R.id.goods_material:
                if(!mSecondaryFormulation){
                    return;
                }
                mSecondaryFormulation=false;
                break;
        }
        refreshViewByFormulationType();
        requestData();
    }

    private void refreshViewByFormulationType(){
        if(mSecondaryFormulation){
            mRecipeType=RECIPE_TYPE_P2;
            mGoodsType=GOOODS_TYPE_P1;

            mViewSecondaryMaterial.setTextColor(getResources().getColor(R.color.text_main_color_selector));
            mViewSecondaryMaterial.setBackgroundResource(R.drawable.transparent);
            mViewGoodsMaterial.setTextColor(getResources().getColor(R.color.text_dark_color_selector));
            mViewGoodsMaterial.setBackgroundResource(R.drawable.default_bg);
            mViewMaterialContent.removeAllViews();
        }else{
            mRecipeType=RECIPE_TYPE_PROLINE;
            mGoodsType=GOOODS_TYPE_P2;

            mViewGoodsMaterial.setTextColor(getResources().getColor(R.color.text_main_color_selector));
            mViewGoodsMaterial.setBackgroundResource(R.drawable.transparent);
            mViewSecondaryMaterial.setTextColor(getResources().getColor(R.color.text_dark_color_selector));
            mViewSecondaryMaterial.setBackgroundResource(R.drawable.default_bg);
            mViewMaterialContent.removeAllViews();
        }
    }

    private AdapterView.OnItemLongClickListener mOnItemLongClickListener = new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if(!mEditMode){
                return false;
            }

            if(mViewMaterialContent.getChildCount()>=4){
                return false;
            }

            WarehouseInfo info = (WarehouseInfo) mGridViewAdapter.getItem(position);
            /*if(info.locked){
                return false;
            }*/

            for(int i=0;i<mViewMaterialContent.getChildCount();i++){
                FormulationItemInfo draggedInfo=(FormulationItemInfo)mViewMaterialContent.getChildAt(i).getTag();
                if(draggedInfo.goodsId==info.goodsId){
                    ToastUtils.toast("该原料已存在！");
                    return false;
                }
            }

            mInternalView =null;
            mOccupyView=createOccupyView(info);
            mViewMaterialContent.addView(mOccupyView, mViewMaterialContent.getChildCount());

            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, info, 0);
            return true;
        }
    };

    private View createMaterialItemView(FormulationItemInfo info) {
        View view=mInflater.inflate(R.layout.item_formulation_material,null);
        view.setTag(info);

        TextView name=(TextView)view.findViewById(R.id.name);
        name.setText(info.goodsName);

        TextView count=(TextView)view.findViewById(R.id.count);
        count.setText(String.valueOf(info.goodsCount));

        View viewRemove=view.findViewById(R.id.icon_remove);
        viewRemove.setOnClickListener(mOnRemoveViewClickListener);
        viewRemove.setTag(view);

        ImageView goodsIcon=(ImageView)view.findViewById(R.id.icon);
        ImageLoader.getInstance().displayImage(info.goodsLogo, goodsIcon, Tools.buildProductDisplayImageOptions(mGoodsType));

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(DRAG_ITEM_VIEW_SIZE, DRAG_ITEM_VIEW_SIZE));
        lp.leftMargin= mMaterialItemMarginLef;
        view.setLayoutParams(lp);

        Drawable removeIconDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            removeIconDrawable=getResources().getDrawable(R.drawable.icon_remove,getActivity().getTheme());
        }else{
            removeIconDrawable=getResources().getDrawable(R.drawable.icon_remove);
        }

        View content=view.findViewById(R.id.content);
        FrameLayout.LayoutParams contentLp = (FrameLayout.LayoutParams) content.getLayoutParams();
        contentLp.leftMargin=removeIconDrawable.getIntrinsicHeight()/2;
        contentLp.topMargin=removeIconDrawable.getIntrinsicWidth()/2;
        contentLp.height=DRAG_ITEM_VIEW_SIZE-removeIconDrawable.getIntrinsicHeight()/2;
        contentLp.width=DRAG_ITEM_VIEW_SIZE-removeIconDrawable.getIntrinsicWidth()/2;

        return view;
    }

    private View createOccupyView(WarehouseInfo info) {
        View occupyView=mInflater.inflate(R.layout.item_formulation_material_drag_view,null);

        ImageView goodsIcon=(ImageView)occupyView.findViewById(R.id.icon);
        ImageLoader.getInstance().displayImage(info.goodsLogo, goodsIcon, Tools.buildProductDisplayImageOptions(mGoodsType));

        TextView name=(TextView)occupyView.findViewById(R.id.name);
        name.setText(info.goodsName);

        TextView summary=(TextView)occupyView.findViewById(R.id.sum);
        summary.setText(String.valueOf(info.goodsCount));

        Drawable removeIconDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            removeIconDrawable=getResources().getDrawable(R.drawable.icon_remove,getActivity().getTheme());
        }else{
            removeIconDrawable=getResources().getDrawable(R.drawable.icon_remove);
        }

        int height=DRAG_ITEM_VIEW_SIZE-removeIconDrawable.getIntrinsicHeight()/2;
        int width=DRAG_ITEM_VIEW_SIZE-removeIconDrawable.getIntrinsicWidth()/2;

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width, height));
        lp.leftMargin= mMaterialItemMarginLef;
        occupyView.setLayoutParams(lp);

        DragInfo dragInfo=new DragInfo();
        occupyView.setTag(dragInfo);

        return occupyView;
    }

    private View.OnClickListener mOnRemoveViewClickListener=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            View materialItemView =(View) v.getTag();
            mViewMaterialContent.removeView(materialItemView);

            FormulationItemInfo info=(FormulationItemInfo)materialItemView.getTag();
            //还原仓库数量
            info.fromWarehouseInfo.goodsCount+=info.goodsCount;
            mGridViewAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 还原仓库
     */
    private void resotreWarehouse(){
        for(int i=0;i<mViewMaterialContent.getChildCount();i++){
            View materialItemView =mViewMaterialContent.getChildAt(i);
            FormulationItemInfo info=(FormulationItemInfo)materialItemView.getTag();
            //还原仓库数量
            info.fromWarehouseInfo.goodsCount+=info.goodsCount;
        }
        mGridViewAdapter.notifyDataSetChanged();

        mViewMaterialContent.removeAllViews();
    }

    private View.OnDragListener mOnDragListener = new View.OnDragListener() {
        private boolean mHasDrop=false;
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // Do nothing
                    mHasDrop=false;
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    boolean hasAdded=false;
                    for (int i = 0, j = mViewMaterialContent.getChildCount(); i < j; i++) {
                        if (mViewMaterialContent.getChildAt(i) == mOccupyView) {
                            hasAdded=true;
                            break;
                        }
                    }
                    if(!hasAdded){
                        mViewMaterialContent.addView(mOccupyView, mViewMaterialContent.getChildCount());
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    boolean hasRemoved=true;
                    for (int i = 0, j = mViewMaterialContent.getChildCount(); i < j; i++) {
                        if (mViewMaterialContent.getChildAt(i) == mOccupyView) {
                            hasRemoved=false;
                            break;
                        }
                    }
                    if(!hasRemoved){
                        mViewMaterialContent.removeView(mOccupyView);
                    }
                    break;
                case DragEvent.ACTION_DROP:
                    mHasDrop=true;
                    for (int i = 0, j = mViewMaterialContent.getChildCount(); i < j; i++) {
                        if (mViewMaterialContent.getChildAt(i) == mOccupyView) {
                            mViewMaterialContent.removeView(mOccupyView);
                            mOccupyView=null;

                            WarehouseInfo info=(WarehouseInfo)event.getLocalState();
                            showDialog(info);

                            break;
                        }
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if(mOccupyView!=null){
                        for (int i = 0, j = mViewMaterialContent.getChildCount(); i < j; i++) {
                            if (mViewMaterialContent.getChildAt(i) == mOccupyView) {
                                mViewMaterialContent.removeView(mOccupyView);
                                break;
                            }
                        }
                        mOccupyView =null;
                    }

                default:
                    break;
            }
            return true;
        }
    };

    private void showDialog(WarehouseInfo info){
        mDialog = Tools.createDialog(getActivity(), R.layout.dialog_input_material_sum);
        final EditText mInputNumber = (EditText)mDialog.findViewById(R.id.material_number);
        TextView confirm = (TextView)mDialog.findViewById(R.id.confirm);
        confirm.setTag(info);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarehouseInfo dragInfo=(WarehouseInfo)v.getTag();

                if (TextUtils.isEmpty(mInputNumber.getText().toString())) {
                    ToastUtils.toast("请输入数量");
                    return;
                }

                int moveSum=Integer.parseInt(mInputNumber.getText().toString());
                if (dragInfo.goodsCount < moveSum) {
                    ToastUtils.toast("输入的数量超出了");
                    return;
                }

                if (moveSum <=0) {
                    ToastUtils.toast("请输入大于0的数字");
                    return;
                }

                FormulationItemInfo newInfo=new FormulationItemInfo();
                newInfo.goodsCount=moveSum;
                newInfo.goodsId=dragInfo.goodsId;
                newInfo.spaceId=dragInfo.spaceId;
                newInfo.goodsName=dragInfo.goodsName;
                newInfo.goodsLogo=dragInfo.goodsLogo;
                newInfo.fromWarehouseInfo=dragInfo;

                View newView = createMaterialItemView(newInfo);
                mViewMaterialContent.addView(newView, mViewMaterialContent.getChildCount());

                mDialog.dismiss();

                dragInfo.goodsCount=dragInfo.goodsCount-moveSum;
                mGridViewAdapter.notifyDataSetChanged();
            }
        });

        TextView cancel = (TextView) mDialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mDialog.show();
    }


    private void createRecipeQuery(){

        if(mViewMaterialContent.getChildCount()!=4){
            ToastUtils.toast("配方必须有4个原料");
            return;
        }
        if(isRequestProcessing(ApiUrls.CREATE_RECIPE)){
            return;
        }

        CreateRecipeRequestBean requestBean=new CreateRecipeRequestBean();
        CreateRecipeRequestBean.DataEntity data=new CreateRecipeRequestBean.DataEntity();
        requestBean.setData(data);
        data.setRecipeType(mRecipeType);
        data.setComId(UserInfo.getCurrentUser().getComId());
        List<CreateRecipeRequestBean.DataEntity.GoodsListEntity> goodsList=new ArrayList<>();
        for(int i=0;i<mViewMaterialContent.getChildCount();i++){

            FormulationItemInfo info=(FormulationItemInfo)mViewMaterialContent.getChildAt(i).getTag();

            CreateRecipeRequestBean.DataEntity.GoodsListEntity goodsListEntity=new CreateRecipeRequestBean.DataEntity.GoodsListEntity();
            goodsListEntity.setGoodsId(info.goodsId);
            goodsListEntity.setSpaceId(info.spaceId);
            goodsListEntity.setGoodsSum(info.goodsCount);
            goodsList.add(goodsListEntity);
        }
        data.setGoodsList(goodsList);

        startJsonRequest(ApiUrls.CREATE_RECIPE, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        BaseResponseBean responseBean=Tools.parseJsonTostError(result,BaseResponseBean.class);
                        if(responseBean!=null){
                            mEditMode=false;

                            mViewMaterialContent.removeAllViews();

                            refreshViewByMode();

                            ToastUtils.toast("研发成功！");

                            //刷新数据
                            requestData();
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        });
    }

    private class WarehouseAdapter extends ABaseAdapter<WarehouseInfo> {

        public WarehouseAdapter(ArrayList<WarehouseInfo> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<WarehouseInfo> newItemView() {
            return new WarehouseItemView();
        }
    }


    private class WarehouseItemView extends ABaseAdapter.AbstractItemView<WarehouseInfo> {
        @ViewInject(id = R.id.icon)
        private ImageView mViewImageView;

        @ViewInject(id = R.id.name)
        private TextView mViewName;

        @ViewInject(id = R.id.sum)
        private TextView mViewSum;


        @Override
        public int inflateViewId() {
            return R.layout.list_item_warehouse;
        }

        @Override
        public void bindingData(View convertView, WarehouseInfo data) {
            ImageLoader.getInstance().displayImage(data.goodsLogo, mViewImageView, Tools.buildProductDisplayImageOptions(mGoodsType));
            mViewName.setText(data.goodsName);
            mViewSum.setText(String.valueOf(data.goodsCount));
        }
    }

    private class ExistingFormulationAdapter extends ABaseAdapter<SelectRecipeListResponseBean.DataEntity.RecipeListEntity> {

        public ExistingFormulationAdapter(ArrayList<SelectRecipeListResponseBean.DataEntity.RecipeListEntity> datas, Activity context) {
            super(datas, context);
        }

        @Override
        protected AbstractItemView<SelectRecipeListResponseBean.DataEntity.RecipeListEntity> newItemView() {
            return new ExistingFormulationItemView();
        }
    }

    private class ExistingFormulationItemView extends ABaseAdapter.AbstractItemView<SelectRecipeListResponseBean.DataEntity.RecipeListEntity> implements SwipeMenuView.OnSwipeItemClickListener{
        @ViewInject(id = R.id.materials_content)
        private LinearLayout mViewMaterialsContent;

        @ViewInject(id = R.id.formulation_name)
        private TextView mViewFormulationName;

        @ViewInject(id = R.id.goods_name)
        private TextView mViewGoodsName;

        public void createMenu(SwipeMenu menu) {
            SwipeMenuItem fireItem = new SwipeMenuItem(getActivity());
            //fireItem.setId(ITEM_OPERATOR_FIRE);
            fireItem.setBackground(new ColorDrawable(0xffFE501B));
            fireItem.setWidth(PixelUtils.dp2px(90));
            fireItem.setTitle("删除");
            fireItem.setTitleSize(18);
            fireItem.setTitleColor(Color.WHITE);
            menu.addMenuItem(fireItem);
        }

        @Override
        public void onItemClick(SwipeMenuItem menuItem, int groupPosition, int childPosition) {
            //ToastUtils.toast("Click item : " + childPosition);
            if(isRequestProcessing(ApiUrls.DELETE_RECIPE)){
                return;
            }
            SelectRecipeListResponseBean.DataEntity.RecipeListEntity itemData=mExistingFormulationList.get(childPosition);

            DeleteRecipeRequestBean requestBean=new DeleteRecipeRequestBean();
            DeleteRecipeRequestBean.DataEntity data=new DeleteRecipeRequestBean.DataEntity();
            requestBean.setData(data);

            data.setRecipeId(itemData.getRecipeId());

            startJsonRequest(ApiUrls.DELETE_RECIPE, requestBean, new HttpRequestHandler(FormulationDevelopmentFragment.this) {
                @Override
                public void onRequestFinished(ResultCode resultCode, String result) {
                    switch (resultCode) {
                        case success:
                            BaseResponseBean responseBean = Tools.parseJsonTostError(result, BaseResponseBean.class);
                            if (responseBean != null) {
                                ToastUtils.toast("删除成功!");
                                //刷新
                                requestData();
                            }
                            break;
                        default:
                            ToastUtils.toast(result);
                            break;
                    }
                }
            });
        }

        @Override
        public View createConvertView(int position) {

            SwipeMenu menu = new SwipeMenu(getActivity());
            createMenu(menu);
            SwipeMenuView menuView = new SwipeMenuView(menu);
            menuView.setOnSwipeItemClickListener(this);

            View itemView = mInflater.inflate(R.layout.list_item_existing_formulation, null);

            SwipeMenuLayout swipeMenuLayout=new SwipeMenuLayout(itemView, menuView);

            return swipeMenuLayout;
        }

        @Override
        public int inflateViewId() {
            return 0;
        }

        @Override
        public void bindingData(View convertView, SelectRecipeListResponseBean.DataEntity.RecipeListEntity data) {

            SwipeMenuLayout swipeMenuLayout= (SwipeMenuLayout) convertView;
            swipeMenuLayout.setPosition(0, getPosition());
            swipeMenuLayout.closeMenu();

            mViewFormulationName.setText(data.getRecipeName());
            mViewGoodsName.setText(data.getProOutputName());

            for(int i=0;i<data.getDetails().size()&&i<4;i++){
                View mViewMaterial=mViewMaterialsContent.getChildAt(i);
                SelectRecipeListResponseBean.DataEntity.RecipeListEntity.DetailsEntity material=data.getDetails().get(i);
                populateMaterialView(mViewMaterial,material);
            }
        }

        private void populateMaterialView(View viewMaterial,SelectRecipeListResponseBean.DataEntity.RecipeListEntity.DetailsEntity data){
            ImageView icon=(ImageView)viewMaterial.findViewById(R.id.icon);
            TextView textViewSummary=(TextView)viewMaterial.findViewById(R.id.summary);
            TextView textViewCount=(TextView)viewMaterial.findViewById(R.id.count);

            ImageLoader.getInstance().displayImage(data.getGoodsLogo(), icon, Tools.buildProductDisplayImageOptions(""));

            textViewSummary.setText(data.getMaterialName());
            textViewCount.setText(String.valueOf(data.getMaterialSum()));
        }

    }

    private class DragInfo{
        boolean hasMoved=false;
    }

    private class WarehouseInfo {
        int spaceId;
        String goodsLogo;
        int goodsId;
        int goodsCount;
        String goodsName;
    }

    private class FormulationItemInfo{
        int goodsId;
        int spaceId;
        int goodsCount;
        String goodsName;
        String goodsLogo;

        WarehouseInfo fromWarehouseInfo;

    }

    private class ExistingFormulation{

    }

}