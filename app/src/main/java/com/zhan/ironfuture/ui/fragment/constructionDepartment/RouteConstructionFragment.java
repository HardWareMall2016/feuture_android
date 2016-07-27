package com.zhan.ironfuture.ui.fragment.constructionDepartment;

import android.animation.LayoutTransition;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.BaseResponseBean;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.CreateRouteLineRequestBean;
import com.zhan.ironfuture.beans.CreateRouteLineResponseBean;
import com.zhan.ironfuture.beans.GetRoteBuildInfoRequestBean;
import com.zhan.ironfuture.beans.GetRoteBuildInfoResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.widget.PinnedHeaderExpandableListView;
import com.zhan.ironfuture.ui.widget.SideBar;
import com.zhan.ironfuture.utils.Tools;

import android.widget.AbsListView.LayoutParams;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by WuYue on 2016/4/25.
 * 路线建设
 */
public class RouteConstructionFragment extends ABaseFragment implements PinnedHeaderExpandableListView.OnHeaderUpdateListener {

    private final static int DRAG_ITEM_VIEW_SIZE=PixelUtils.dp2px(70);

    @ViewInject(id = R.id.company_list)
    PinnedHeaderExpandableListView mExpandableListView;

    @ViewInject(id = R.id.header_content)
    View mHeaderContent;

    @ViewInject(id = R.id.header)
    HorizontalScrollView mViewHeaderContent;

    @ViewInject(id = R.id.route)
    LinearLayout mContentRoute;

    @ViewInject(id = R.id.total_construct_expend)
    TextView mTotalExpend;

    @ViewInject(id = R.id.staff_list_sidrbar)
    SideBar mSideBar ;

    @ViewInject(id = R.id.search)
    EditText mViewSearch ;

    @ViewInject(id = R.id.search_btn, click = "OnClick")
    View mSearchBtn;

    private TextView mBtnCancel;
    private ImageView mBtnAddOrFinish;
    private InputMethodManager mInputMethodManager;

    private String mSearchKey = "";

    /*@ViewInject(id = R.id.pull_refresh_list)
    PullToRefreshExpandableListView mPullToRefreshExpandableListView;

    private ExpandableListView mExpandableListView;*/

    //Tools
    /*private Handler mHandler = new Handler();*/
    private ExpandableAdapter mAdapter;
    private LayoutInflater mInflater;
    private LayoutTransition mLayoutTransition;

    private Bitmap mDragBitmap;

    //Flag
    private boolean mEditMode=false;
    private int mDragViewContentHeight;
    private int mDragViewContentWidth;

    //最多免费线路条数
    private int mMaxFreeLineSum;
    //一条线上最多免费站点数
    private int mMaxFreeStationSum;
    //所有满足免费的线路数
    private int mTotalFreeLineSum;

    //Data
    private List<GroupDataInfo> mDataList = new LinkedList<>();
    private List<LinePriceInfo> mLinePriceInfoList =new LinkedList<>();

    @Override
    protected int inflateContentView() {
        return R.layout.frag_route_construction;
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
        //getActivity().setTitle("路线建设");
        mInflater = inflater;
        mEditMode=false;
        if(getActivity() instanceof ActionBarActivity){
            ActionBarActivity actionBarActivity= (ActionBarActivity)getActivity();
            actionBarActivity.setShowActionBarCustomerContent(true);

            View actionbarContent=mInflater.inflate(R.layout.header_route_construction,null);
            actionBarActivity.getActionBarCustomerContent().removeAllViews();
            actionBarActivity.getActionBarCustomerContent().addView(actionbarContent);

            mBtnCancel=(TextView)actionbarContent.findViewById(R.id.cancel);
            mBtnAddOrFinish=(ImageView)actionbarContent.findViewById(R.id.right_menu);
            mBtnCancel.setOnClickListener(mActionbarBtnClicked);
            mBtnAddOrFinish.setOnClickListener(mActionbarBtnClicked);
        }
        refreshViewByEditMode();

        /*mExpandableListView = mPullToRefreshExpandableListView.getRefreshableView();
        mPullToRefreshExpandableListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mPullToRefreshExpandableListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                requestData();
            }
        });*/

        mAdapter = new ExpandableAdapter();
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setGroupIndicator(null);
        //mExpandableListView.setOnChildClickListener(this);
        mExpandableListView.setOnItemLongClickListener(mOnListItemLongClickListener);
        //不能点击收缩
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        mExpandableListView.setOnHeaderUpdateListener(this);

        mLayoutTransition = new LayoutTransition();
        mContentRoute.setLayoutTransition(mLayoutTransition);
        //mLayoutTransition.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
        mLayoutTransition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
        //设置每个动画持续的时间
        mLayoutTransition.setDuration(300);

        //拖动相关
        mViewHeaderContent.setOnDragListener(mOnDragListener);
        calculateDropViewContentSize();

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
    public void onDestroyView() {
        releaseDragBitmap();
        super.onDestroyView();
    }

    @Override
    public void requestData() {
        GetRoteBuildInfoRequestBean request = new GetRoteBuildInfoRequestBean();
        GetRoteBuildInfoRequestBean.DataEntity data = new GetRoteBuildInfoRequestBean.DataEntity();
        data.setPageId(0);
        data.setPageDirection(IronFutureConstants.PAGE_DIRECTION_DOWN);
        data.setPageCount(IronFutureConstants.DEF_PAGE_SIZE);
        data.setSearchKey(mSearchKey);
        request.setData(data);

        startJsonRequest(ApiUrls.GET_ROTE_BUILD_INFO, request, new BaseHttpRequestTask<BaseResponseBean>() {
            @Override
            public BaseResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, BaseResponseBean.class);
            }

            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                super.onRequestFinished(resultCode, result);
                switch (resultCode) {
                    case success:
                        GetRoteBuildInfoResponseBean bean=Tools.parseJsonTostError(result,GetRoteBuildInfoResponseBean.class);
                        if(bean!=null){
                            mDataList.clear();

                            for(GetRoteBuildInfoResponseBean.DataEntity.ComListEntity comListEntity:bean.getData().getComList()) {
                                GroupDataInfo sectionInfo = new GroupDataInfo();
                                sectionInfo.section = comListEntity.getGroupName();
                                sectionInfo.groupId = comListEntity.getGroupId();

                                for (GetRoteBuildInfoResponseBean.DataEntity.ComListEntity.ComlistEntity comInfo : comListEntity.getComlist()) {
                                    CompanyInfo companyInfo = new CompanyInfo();
                                    companyInfo.id = comInfo.getId();
                                    companyInfo.companyName = comInfo.getName();
                                    companyInfo.companyUrl=comInfo.getComLogo();
                                    sectionInfo.childDataInfoList.add(companyInfo);
                                }
                                mDataList.add(sectionInfo);
                            }
                            mAdapter.notifyDataSetChanged();
                            //默认展开
                            for (int i = 0; i < mDataList.size(); i++) {
                                mExpandableListView.expandGroup(i);
                            }

                            mLinePriceInfoList.clear();
                            for(GetRoteBuildInfoResponseBean.DataEntity.LinePriceListEntity data: bean.getData().getLinePriceList()){
                                LinePriceInfo linePriceInfo=new LinePriceInfo();
                                linePriceInfo.linePrice=data.getLinePrice();
                                linePriceInfo.maxStationSum=data.getLineSum();
                                linePriceInfo.rodeGrade=data.getRodeGrade();
                                mLinePriceInfoList.add(linePriceInfo);
                            }

                            //最多免费线路条数
                            mMaxFreeLineSum=bean.getData().getFreeSum();
                            //一条线上最多免费站点数
                            mMaxFreeStationSum=bean.getData().getSitefreeSum();
                            //所有满足免费的线路数
                            mTotalFreeLineSum=bean.getData().getLineStateSum();
                        }

                        break;
                    default:
                        break;
                }
                //onRefreshViewComplete();
            }
        });
    }

    @Override
    public View getPinnedHeader() {
        View headerView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
        headerView.setBackgroundColor(0xffdddddd);
        headerView.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        return headerView;
    }

    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
        GroupDataInfo firstVisibleGroup = (GroupDataInfo) mAdapter.getGroup(firstVisibleGroupPos);
        TextView textView = (TextView) headerView;
        textView.setText(firstVisibleGroup.section);
    }

    private void refreshViewByEditMode(){
        if(mEditMode){
            mHeaderContent.setVisibility(View.VISIBLE);
            mBtnCancel.setVisibility(View.VISIBLE);
            mBtnAddOrFinish.setImageResource(R.drawable.add_finish);
        }else{
            mHeaderContent.setVisibility(View.GONE);
            mBtnCancel.setVisibility(View.GONE);
            mBtnAddOrFinish.setImageResource(R.drawable.add_load);
            mContentRoute.removeAllViews();
        }
    }

    private View.OnClickListener mActionbarBtnClicked=new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cancel:
                    mEditMode=false;
                    refreshViewByEditMode();
                    break;
                case R.id.right_menu:
                    if(!mEditMode){
                        mContentRoute.removeAllViews();
                        intTotalConstructExpend();
                        mEditMode=!mEditMode;
                        refreshViewByEditMode();
                    }else{
                        if(mContentRoute.getChildCount()<3){
                            ToastUtils.toast("站点数至少是2站!");
                            return;
                        }
                        startCreatRodeLineRequest();
                    }
                    break;
            }
        }
    };

    public class ExpandableAdapter extends BaseExpandableListAdapter {
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
            return mDataList.get(groupPosition).childDataInfoList.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return mDataList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return mDataList.get(groupPosition).childDataInfoList.get(childPosition);
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
                convertView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
                convertView.setBackgroundResource(R.drawable.bg_white_underline_selector);
                holder = new ChildHolder();
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            TextView textView = (TextView) convertView;
            textView.setText(mDataList.get(groupPosition).childDataInfoList.get(childPosition).companyName);

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    /*private void onRefreshViewComplete() {
        mHandler.removeCallbacks(mRefreshCompleteRunnable);
        mHandler.postDelayed(mRefreshCompleteRunnable, 50);
    }

    private Runnable mRefreshCompleteRunnable = new Runnable() {
        @Override
        public void run() {
            mPullToRefreshExpandableListView.onRefreshComplete();
        }
    };*/

    private class GroupDataInfo {
        String section;
        int groupId;
        List<CompanyInfo> childDataInfoList = new LinkedList<>();
    }

    private class CompanyInfo {
        int id;
        String companyName;
        String companyUrl;
    }

    private class LinePriceInfo {
        String rodeGrade;
        int maxStationSum;
        int linePrice;
    }

    //以下是拖到相关
    private View mOccupyView;
    private View mInternalView;

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            mInternalView = v;

            CompanyInfo companyInfo=(CompanyInfo)v.getTag();
            mOccupyView=createOccupyView(companyInfo);

            //移除最后一个station
            removeLastStation();

            //将拖拽的控件位置用占位控件替换掉
            for (int i = 0, j = mContentRoute.getChildCount(); i < j; i++) {
                if (mContentRoute.getChildAt(i) == mInternalView) {
                    mContentRoute.removeView(mInternalView);
                    mContentRoute.addView(mOccupyView, i);
                    break;
                }
            }

            ClipData data = ClipData.newPlainText("", "");
            MyDragShadowBuilder shadowBuilder = new MyDragShadowBuilder(mOccupyView);
            mOccupyView.startDrag(data, shadowBuilder, null, 0);
            return true;
        }
    };


    private AdapterView.OnItemLongClickListener mOnListItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if(!mEditMode){
                return false;
            }

            if(mLinePriceInfoList.size()<=0){
                ToastUtils.toast("没有创建路线的规则!");
                return false;
            }

            //站点数限制
            LinePriceInfo linePriceInfo=mLinePriceInfoList.get(mLinePriceInfoList.size()-1);
            int stationCount=mContentRoute.getChildCount();//节点数
            if(stationCount>linePriceInfo.maxStationSum+1){
                ToastUtils.toast("路线已达到最大站点数!");
                return false;
            }


            int groupPosition , childPosition;
            int type = ExpandableListView.getPackedPositionType(id);
            if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                childPosition = ExpandableListView.getPackedPositionChild(id);
                groupPosition = ExpandableListView.getPackedPositionGroup(id);

                CompanyInfo companyInfo=(CompanyInfo)mAdapter.getChild(groupPosition, childPosition);

                //不能重复选公司
                for(int i=0;i<mContentRoute.getChildCount();i++){
                    CompanyInfo routeCompanyInfo=(CompanyInfo)mContentRoute.getChildAt(i).getTag();
                    if(routeCompanyInfo.id==companyInfo.id){
                        ToastUtils.toast("路线中已包含该公司!");
                        return false;
                    }
                }

                //移除最后一个station
                removeLastStation();

                mInternalView =null;
                mOccupyView=createOccupyView(companyInfo);
                mContentRoute.addView(mOccupyView, mContentRoute.getChildCount());

                ClipData data = ClipData.newPlainText("", "");
                MyDragShadowBuilder shadowBuilder = new MyDragShadowBuilder(mOccupyView);
                mOccupyView.startDrag(data, shadowBuilder, null, 0);
            }
            return true;
        }
    };

    @NonNull
    private View createRoutItemView(CompanyInfo companyInfo,boolean showRemoveBtn) {
        View viewCompany=mInflater.inflate(R.layout.item_route_construcion_company,null);

        View content=viewCompany.findViewById(R.id.content);
        FrameLayout.LayoutParams contentLp = (FrameLayout.LayoutParams) content.getLayoutParams();
        contentLp.leftMargin=DRAG_ITEM_VIEW_SIZE- mDragViewContentWidth;
        contentLp.topMargin=DRAG_ITEM_VIEW_SIZE- mDragViewContentHeight;
        contentLp.height= mDragViewContentHeight;
        contentLp.width= mDragViewContentWidth;

        ImageView compLogo=(ImageView)viewCompany.findViewById(R.id.company_logo);
        ImageLoader.getInstance().displayImage(companyInfo.companyUrl, compLogo, Tools.buildDefaultDisplayCompanyLogoImageOptions());


        View viewRemove=viewCompany.findViewById(R.id.icon_remove);
        viewRemove.setVisibility(showRemoveBtn?View.VISIBLE:View.GONE);
        viewRemove.setOnClickListener(mOnRemoveViewClickListener);
        viewRemove.setTag(viewCompany);

        TextView summary=(TextView)viewCompany.findViewById(R.id.summary);
        summary.setText(companyInfo.companyName);

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin= PixelUtils.dp2px(8);
        viewCompany.setLayoutParams(lp);
        viewCompany.setTag(companyInfo);
        return viewCompany;
    }

    private View createOccupyView(CompanyInfo companyInfo) {
        View occupyView=mInflater.inflate(R.layout.item_route_construcion_drag_view,null);

        TextView summary=(TextView)occupyView.findViewById(R.id.summary);
        summary.setText(companyInfo.companyName);

        ImageView compLogo=(ImageView)occupyView.findViewById(R.id.company_logo);
        ImageLoader.getInstance().displayImage(companyInfo.companyUrl, compLogo, Tools.buildDefaultDisplayCompanyLogoImageOptions());

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(mDragViewContentWidth, mDragViewContentHeight));
        lp.leftMargin= PixelUtils.dp2px(8)+DRAG_ITEM_VIEW_SIZE- mDragViewContentWidth;
        occupyView.setLayoutParams(lp);

        occupyView.setOnLongClickListener(mOnLongClickListener);
        occupyView.setOnDragListener(mOnDragListener);

        DragInfo dragInfo=new DragInfo();
        dragInfo.companyInfo=companyInfo;
        occupyView.setTag(dragInfo);
        return occupyView;
    }

    //移除最后一个站点
    private void removeLastStation(){
        if(mContentRoute.getChildCount()>1){
            mContentRoute.removeViewAt(mContentRoute.getChildCount()-1);
        }
    }

    private void intTotalConstructExpend(){
        mTotalExpend.setText("建设路线共花费:");
    }

    private void showTotalConstructExpend(){

        mTotalExpend.setText("建设路线共花费:");

        SpannableString strTotal=new SpannableString(calculateRouteConstructionExpend());
        strTotal.setSpan(new ForegroundColorSpan(0xffFFC501), 0, strTotal.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTotalExpend.append(strTotal);
    }

    private String calculateRouteConstructionExpend(){

        if(mLinePriceInfoList.size()==0){
            return "没有创建路线的规则";
        }

        int stations=mContentRoute.getChildCount()-1>0?mContentRoute.getChildCount()-1:0;
        boolean isFree=false;
        if(stations<=mMaxFreeStationSum&&mTotalFreeLineSum<mMaxFreeLineSum){
            isFree=true;
        }

        String expendStr="";
        if(isFree){
            expendStr="免费";
        }else{
            for(LinePriceInfo linePriceInfo:mLinePriceInfoList){
                if(linePriceInfo.maxStationSum==stations){
                    expendStr=String.valueOf(linePriceInfo.linePrice);
                    break;
                }
            }
        }

        if(TextUtils.isEmpty(expendStr)){
            LinePriceInfo linePriceInfo=mLinePriceInfoList.get(0);
            expendStr=String.valueOf(linePriceInfo.linePrice);
        }

        return expendStr;
    }

    private View.OnClickListener mOnRemoveViewClickListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            View viewRemoveCompany=(View) v.getTag();
            View firstView=mContentRoute.getChildAt(0);

            boolean removeFirstView=firstView==viewRemoveCompany;

            //第一个需要同时移除最后一个
            if(removeFirstView){
                mContentRoute.removeViewAt(mContentRoute.getChildCount()-1);
            }
            mContentRoute.removeView(viewRemoveCompany);

            //排序
            for (int i = 0, j = mContentRoute.getChildCount(); i < j; i++) {
                View viewCompany=mContentRoute.getChildAt(i);
                TextView orderNum=(TextView)viewCompany.findViewById(R.id.order_num);
                orderNum.setText(String.valueOf(i + 1));
            }

            //在结尾添加一个和首站相同的站点
            if(removeFirstView&&mContentRoute.getChildCount()>0){
                View viewCompany=mContentRoute.getChildAt(0);
                CompanyInfo companyInfo=(CompanyInfo)viewCompany.getTag();

                View newView = createRoutItemView(companyInfo,false);
                TextView orderNum=(TextView)newView.findViewById(R.id.order_num);
                orderNum.setText(String.valueOf(mContentRoute.getChildCount() + 1));

                mContentRoute.addView(newView, mContentRoute.getChildCount());
            }

            showTotalConstructExpend();

        }
    };

    private View.OnDragListener mOnDragListener = new View.OnDragListener() {
        private boolean mHasDrop=false;
        private boolean mEnterRouteContent=false;
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // Do nothing
                    mHasDrop=false;
                    mEnterRouteContent=false;
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    if(v==mViewHeaderContent){
                        mEnterRouteContent=true;
                        return true;
                    }
                    //把位置让开
                    if(mOccupyView ==v){
                        return true;
                    }
                    //移动占位控件到当前位置
                    for (int i = 0, j = mContentRoute.getChildCount(); i < j; i++) {
                        if (mContentRoute.getChildAt(i) == v) {
                            mContentRoute.removeView(mOccupyView);
                            mContentRoute.addView(mOccupyView, i);
                            /*DragInfo dragInfo= (DragInfo) mOccupyView.getTag();
                            if(!dragInfo.hasMoved){
                                dragInfo.hasMoved=true;
                            }*/
                            break;
                        }
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    if(v==mViewHeaderContent) {
                        mEnterRouteContent = false;
                    }
                    break;
                case DragEvent.ACTION_DROP:
                    if(v==mViewHeaderContent) {
                        return true;
                    }

                    mHasDrop=true;
                    int dropPosition=-1;
                    for (int i = 0, j = mContentRoute.getChildCount(); i < j; i++) {
                        if (mContentRoute.getChildAt(i) == v) {
                            dropPosition=i;
                            break;
                        }
                    }
                    if(dropPosition!=-1){
                        View newView= mInternalView;
                        //如果是从底部拖入的,新建一个child
                        if(newView==null){
                            DragInfo dragInfo= (DragInfo) mOccupyView.getTag();
                            newView = createRoutItemView(dragInfo.companyInfo,true);
                            newView.setOnLongClickListener(mOnLongClickListener);
                            newView.setOnDragListener(mOnDragListener);
                        }
                        mContentRoute.addView(newView, dropPosition);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //移除占位控件
                    if(mOccupyView!=null){
                        for (int i = 0, j = mContentRoute.getChildCount(); i < j; i++) {
                            if (mContentRoute.getChildAt(i) == mOccupyView) {
                                mContentRoute.removeView(mOccupyView);
                                DragInfo dragInfo= (DragInfo) mOccupyView.getTag();
                                //如果放弃，移动还原最后位置,占位控件
                                if(!mHasDrop) {
                                    if (mInternalView != null) {
                                        mContentRoute.addView(mInternalView, i);
                                    } else if(mEnterRouteContent){//判断是否移动过
                                        View newView = createRoutItemView(dragInfo.companyInfo,true);
                                        newView.setOnLongClickListener(mOnLongClickListener);
                                        newView.setOnDragListener(mOnDragListener);
                                        mContentRoute.addView(newView, i);
                                    }
                                }
                                break;
                            }
                        }

                        refreshRouterItemOrder();
                        showTotalConstructExpend();
                        mOccupyView =null;
                    }
                default:
                    break;
            }
            return true;
        }
    };

    private class MyDragShadowBuilder extends View.DragShadowBuilder {

        // The drag shadow image, defined as a drawable thing
        private  Drawable shadow;

        // Defines the constructor for myDragShadowBuilder
        public MyDragShadowBuilder(View v) {

            // Stores the View parameter passed to myDragShadowBuilder.
            super(v);
            // Creates a draggable image that will fill the Canvas provided by the system.
            //shadow = new ColorDrawable(Color.LTGRAY);

            releaseDragBitmap();

            Drawable originalBg=v.getBackground();
            v.setBackgroundResource(R.drawable.bg_color4_rounded_white_content);

            v.layout(0, 0, mDragViewContentWidth, mDragViewContentHeight);
            int measuredWidth = View.MeasureSpec.makeMeasureSpec(mDragViewContentWidth, View.MeasureSpec.EXACTLY);
            int measuredHeight = View.MeasureSpec.makeMeasureSpec(mDragViewContentHeight, View.MeasureSpec.EXACTLY);
            v.measure(measuredWidth, measuredHeight);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

            v.setDrawingCacheEnabled(true);
            v.buildDrawingCache();  //启用DrawingCache并创建位图
            mDragBitmap = Bitmap.createBitmap(v.getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
            v.setDrawingCacheEnabled(false);  //禁用DrawingCahce否则会影响性能

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                v.setBackground(originalBg);
            }else{
                v.setBackgroundDrawable(originalBg);
            }

            shadow=new BitmapDrawable(getResources(), mDragBitmap);
            shadow.setColorFilter(0xffffc500, PorterDuff.Mode.MULTIPLY);
        }

        // Defines a callback that sends the drag shadow dimensions and touch point back to the
        // system.
        @Override
        public void onProvideShadowMetrics(Point size, Point touch) {
            // Defines local variables
            //int width, height;

            //width=height=PixelUtils.dp2px(60);

            // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
            // Canvas that the system will provide. As a result, the drag shadow will fill the
            // Canvas.
            shadow.setBounds(0, 0, mDragViewContentWidth, mDragViewContentHeight);

            // Sets the size parameter's width and height values. These get back to the system
            // through the size parameter.
            size.set(mDragViewContentWidth, mDragViewContentHeight);

            // Sets the touch point's position to be in the middle of the drag shadow
            touch.set(mDragViewContentWidth / 2, mDragViewContentHeight / 2);
        }

        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
        @Override
        public void onDrawShadow(Canvas canvas) {
            shadow.draw(canvas);
        }
    }

    private void calculateDropViewContentSize(){
        Drawable removeIconDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            removeIconDrawable=getResources().getDrawable(R.drawable.icon_remove,getActivity().getTheme());
        }else{
            removeIconDrawable=getResources().getDrawable(R.drawable.icon_remove);
        }

        mDragViewContentHeight =DRAG_ITEM_VIEW_SIZE-removeIconDrawable.getIntrinsicHeight()/2;
        mDragViewContentWidth =DRAG_ITEM_VIEW_SIZE-removeIconDrawable.getIntrinsicWidth()/2;
    }

    private void releaseDragBitmap(){
        if(mDragBitmap !=null){
            mDragBitmap.recycle();
            mDragBitmap =null;
        }
    }

    private void refreshRouterItemOrder(){
        for (int i = 0, j = mContentRoute.getChildCount(); i < j; i++) {
            View viewCompany=mContentRoute.getChildAt(i);
            TextView orderNum=(TextView)viewCompany.findViewById(R.id.order_num);
            orderNum.setText(String.valueOf(i + 1));
        }

        //在结尾添加一个和首站相同的站点
        if(mContentRoute.getChildCount()>0){
            View viewCompany=mContentRoute.getChildAt(0);
            CompanyInfo companyInfo=(CompanyInfo)viewCompany.getTag();

            View newView = createRoutItemView(companyInfo,false);
            TextView orderNum=(TextView)newView.findViewById(R.id.order_num);
            orderNum.setText(String.valueOf(mContentRoute.getChildCount() + 1));

            mContentRoute.addView(newView, mContentRoute.getChildCount());
        }
    }

    private void startCreatRodeLineRequest(){
        if(isRequestProcessing(ApiUrls.CREAT_RODE_LINE)){
            return;
        }

        CreateRouteLineRequestBean requestBean=new CreateRouteLineRequestBean();
        CreateRouteLineRequestBean.DataEntity data=new CreateRouteLineRequestBean.DataEntity();
        data.setComId(UserInfo.getCurrentUser().getComId());
        List<Integer> companyList=new ArrayList<>();
        for (int i = 0;i <mContentRoute.getChildCount()-1;i++) {
            View viewCompany=mContentRoute.getChildAt(i);
            CompanyInfo companyInfo=(CompanyInfo)viewCompany.getTag();
            companyList.add(companyInfo.id);
        }
        data.setCompanyList(companyList);
        requestBean.setData(data);

        startJsonRequest(ApiUrls.CREAT_RODE_LINE,requestBean,new HttpRequestHandler(this){
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        CreateRouteLineResponseBean bean=Tools.parseJsonTostError(result,CreateRouteLineResponseBean.class);
                        if(bean!=null){
                            mEditMode=false;
                            refreshViewByEditMode();
                            mTotalFreeLineSum=bean.getData().getLineStateSum();
                            ToastUtils.toast("路线创建成功！");
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        });
    }

    private class DragInfo{
        //boolean hasMoved=false;
        CompanyInfo companyInfo;
    }
}