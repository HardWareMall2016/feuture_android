package com.zhan.ironfuture.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.zhan.framework.view.pickerview.LoopView;
import com.zhan.ironfuture.R;

import java.util.ArrayList;

/**
 * 作者：伍岳 on 2016/6/29 11:23
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
public class PopMenuHelper {
    //Views
    private PopupWindow mPopupWindow;

    private Activity mActivity;

    public interface OnPickViewListener {
        void onFinishClickListener(int selectedIndex);
    }

    public PopMenuHelper(Activity activity){
        mActivity=activity;
        intiPopMenu();
    }

    public void showChooseMenu(ArrayList<String> itemList, final OnPickViewListener callback) {
        View viewPopMenuLayout = mActivity.getLayoutInflater().inflate(R.layout.pop_memu_common_pickview, null);
        mPopupWindow.setContentView(viewPopMenuLayout);

        final LoopView pickView = (LoopView) viewPopMenuLayout.findViewById(R.id.picker_view);
        pickView.setNotLoop();
        pickView.setArrayList(itemList);
        pickView.setInitPosition(0);

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
                int item = pickView.getSelectedItem();
                if (item > -1) {
                    callback.onFinishClickListener(item);
                    closePopWin();
                }
            }
        });

        showPopMenu();
    }

    public void showCustomerMenu(View customerView){
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setContentView(customerView);
        showPopMenu();
    }

    private void intiPopMenu() {
        mPopupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        int bgColor = mActivity.getResources().getColor(com.zhan.framework.R.color.main_background);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(bgColor));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.pop_menu_animation);
        mPopupWindow.update();
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
    }

    private void showPopMenu() {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
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
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        mActivity.getWindow().setAttributes(lp);
    }
}
