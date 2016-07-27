package com.zhan.ironfuture.ui.activity;

import android.support.v4.app.Fragment;


/**
 * 作者：伍岳 on 2016/4/25 10:29
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
public class MorePageInfo {
    private String title;
    private int icon;
    private Class<? extends Fragment> fragmentClass;


    public MorePageInfo(String title, int icon, Class<? extends Fragment> fragmentClass) {
        this.title = title;
        this.fragmentClass = fragmentClass;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public Class<? extends Fragment> getFragmentClass() {
        return fragmentClass;
    }

    public int getIcon() {
        return icon;
    }

}
