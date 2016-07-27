package com.zhan.ironfuture.utils;

import android.app.Service;
import android.os.Vibrator;

import com.zhan.framework.common.context.GlobalContext;

/**
 * 作者：伍岳 on 2016/6/24 13:15
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
public class VibratorUtil {

    public static void Vibrate() {
        Vibrator vib = (Vibrator) GlobalContext.getInstance().getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(100);
    }

    public static void Vibrate(long[] pattern,boolean isRepeat) {
        Vibrator vib = (Vibrator) GlobalContext.getInstance().getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }
}
