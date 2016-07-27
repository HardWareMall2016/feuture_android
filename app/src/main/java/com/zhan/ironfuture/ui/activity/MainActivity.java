package com.zhan.ironfuture.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhan.framework.component.container.FragmentContainerActivity;
import com.zhan.framework.ui.activity.BaseActivity;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.ui.fragment.login.LoginFragment;
import com.zhan.ironfuture.ui.widget.arclayout.ArcLayout;
import com.zhan.ironfuture.utils.AnimatorUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public final static String EXT_KEY_SHOW_PAGE = "show_page";

    private LinearLayout mBottomContainer;
    private View mMoreMenuLayout;
    private ArcLayout mMoreArcLayout;

    private List<Page> mPageList = new ArrayList<>();
    private Page mCurPage;
    private Page mMorePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkIsLogin()) {
            return;
        }
        //showActionbar(false);
        showBackIcon(false);
        setContentView(R.layout.activity_main);
        mMoreMenuLayout = findViewById(R.id.menu_layout);
        mMoreMenuLayout.setOnClickListener(this);
        mMoreArcLayout = (ArcLayout) findViewById(R.id.arc_layout);
        initPages();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(UserInfo.getCurrentUser()==null||!UserInfo.getCurrentUser().isLogin()){
            LoginFragment.launch(this);
            finish();
            return;
        }
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            //退出跳转到主页,强制刷新主页
            showFirstPage();
        }
    }

    private void initPages() {
        int role= UserInfo.getCurrentUser().getPostId();

        mPageList.clear();
        mBottomContainer=(LinearLayout)findViewById(R.id.bottom_bar);
        mBottomContainer.removeAllViews();

        RoleManager roleManager=new RoleManager(this);
        List<Page> normalPages=roleManager.generatePages(role);
        mPageList.addAll(normalPages);

        mMorePage=roleManager.generateMorePage(role);
        mPageList.add(mMorePage);
        initMoreContent();

        //只有一个正常页面就直接显示,底部就只显示更多就好了
        if(normalPages.size()<=1){
            mBottomContainer.addView(mMorePage.bottomTitle);
            mMorePage.bottomTitle.setOnClickListener(mOnBottomButtonClickListener);
        }else {
            for (int i = 0; i < normalPages.size(); i++) {
                //中间插入more
                if (i == normalPages.size() / 2) {
                    mBottomContainer.addView(mMorePage.bottomTitle);
                    mMorePage.bottomTitle.setOnClickListener(mOnBottomButtonClickListener);
                }
                Page page = normalPages.get(i);
                mBottomContainer.addView(page.bottomTitle);
                page.bottomTitle.setOnClickListener(mOnBottomButtonClickListener);

            }
        }

        String showPage = getIntent().getStringExtra(EXT_KEY_SHOW_PAGE);
        if (TextUtils.isEmpty(showPage)) {
            showPage = normalPages.get(0).TAG;
        }
        showPage(showPage);
    }

    private void initMoreContent() {
        mMoreArcLayout.removeAllViews();

        boolean oddNum=mMorePage.morePages.size()%2==1;//总数是否是奇数
        int centerPos=mMorePage.morePages.size()/2;
        int totalArc=120;
        if(mMorePage.morePages.size()==2){
            totalArc=90;
        }else if(mMorePage.morePages.size()==5){
            totalArc=150;
        }
        int stepArc=totalArc/(mMorePage.morePages.size()-1);

        for(int i = 0; i < mMorePage.morePages.size(); i++){
            MorePageInfo morePage=mMorePage.morePages.get(i);
            Button item=new Button(this);
            String title=morePage.getTitle();
            if(title.length()>2){
                StringBuilder sb=new StringBuilder(title);
                sb.insert(2,"\n");
                title=sb.toString();
            }
            item.setText(title);
            item.setTextSize(16);
            item.setGravity(Gravity.CENTER);
            item.setBackgroundResource(R.drawable.path_white_oval);
            item.setTextColor(0xffFF9500);
            /*item.setImageResource(morePage.getIcon());
            item.setScaleType(ImageView.ScaleType.FIT_XY);*/

            ArcLayout.LayoutParams lp=new ArcLayout.LayoutParams(PixelUtils.dp2px(60),PixelUtils.dp2px(60));
            //如果总数是奇数,中间的放在90°上
            if(oddNum) {
                lp.angle = 90 + (i - centerPos)*stepArc;
            }else{
                if(i>centerPos){
                    lp.angle = 90 + stepArc/2+ (i - centerPos)*stepArc;
                }else if(i==centerPos){
                    lp.angle = 90 + stepArc/2;
                }else if(i==centerPos-1){
                    lp.angle = 90 - stepArc/2;
                } else{
                    lp.angle = 90 - stepArc/2+ (i - centerPos+1)*stepArc;
                }

            }
            item.setLayoutParams(lp);

            item.setTag(morePage);
            item.setOnClickListener(this);

            mMoreArcLayout.addView(item);
        }
    }

    private void showPage(String tag) {
        for (Page page : mPageList) {
            if (page.TAG.equals(tag)) {
                if (mCurPage != page) {
                    //这里需要做些Actionbar初始化动作
                    initActionbar();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, page.pageFragment);
                    transaction.commit();
                    page.bottomTitle.setCompoundDrawablesWithIntrinsicBounds(0, page.selectedDrawableId, 0, 0);
                    page.bottomTitle.setTextColor(getResources().getColor(R.color.yellow));
                }
                mCurPage = page;
            } else {
                page.bottomTitle.setCompoundDrawablesWithIntrinsicBounds(0, page.normalDrawableId, 0, 0);
                page.bottomTitle.setTextColor(getResources().getColor(R.color.text_color_content));
            }
        }
    }

    private View.OnClickListener mOnBottomButtonClickListener=new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            String tag=(String) view.getTag();
            if(RoleManager.TAG_MORE.equals(tag)){
                showMenu();
            }else{
                showPage(tag);
            }
        }
    };


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.menu_layout) {
            hideMenu();
            return;
        }

        //更多种的按钮
        if (view instanceof Button) {
            hideMenu();
            MorePageInfo morePage=(MorePageInfo)view.getTag();
            FragmentContainerActivity.launch(this, morePage.getFragmentClass(), null);
        }
    }

    private void showFirstPage() {
        showPage(mPageList.get(0).TAG);
    }

    private boolean checkIsLogin() {
        if (UserInfo.getCurrentUser() == null || !UserInfo.getCurrentUser().isLogin()) {
            LoginFragment.launch(this);
            finish();
            return false;
        }
        return true;
    }

    @SuppressWarnings("NewApi")
    private void showMenu() {
        mMoreMenuLayout.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = mMoreArcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(mMoreArcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    @SuppressWarnings("NewApi")
    private void hideMenu() {
        List<Animator> animList = new ArrayList<>();

        for (int i = mMoreArcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(mMoreArcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mMoreMenuLayout.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();

    }

    private Animator createShowItemAnimator(View item) {

        float dx = mBottomContainer.getWidth()/2 - item.getX()-item.getWidth()/2;
        float dy = mBottomContainer.getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        return anim;
    }

    private Animator createHideItemAnimator(final View item) {
        float dx = mBottomContainer.getWidth()/2 - item.getX()-item.getWidth()/2;
        float dy = mBottomContainer.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }
}
