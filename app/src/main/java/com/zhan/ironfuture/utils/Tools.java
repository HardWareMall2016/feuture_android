package com.zhan.ironfuture.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.ui.activity.MainActivity;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.App;
import com.zhan.ironfuture.base.BaseResponseBean;
import com.zhan.ironfuture.base.UserInfo;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by WuYue on 2016/3/7.
 */
public class Tools {

    public static String md5(String string) {
        byte[] hash = null;
        try {
            hash = string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh,UTF-8 should be supported?", e);
        }
        return computeMD5(hash);
    }

    public static String computeMD5(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input, 0, input.length);
            byte[] md5bytes = md.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < md5bytes.length; i++) {
                String hex = Integer.toHexString(0xff & md5bytes[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



    /***
     * 解析服务器时间，服务器是GMT+08时区
     *
     * @param serverTimeStr
     * @return 返回 时间戳
     */
    /*public static long parseServerTime(String serverTimeStr) {
        if (TextUtils.isEmpty(serverTimeStr)) {
            return 0;
        }
        final String FORMAT = "yyyy-MM-dd HH:mm:ss";
        Date date = DateUtil.parseDate(serverTimeStr, FORMAT, TimeZone.getTimeZone("GMT+08"));
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }*/

    /***
     * 时间戳转换
     */
    public static String parseTime(Long time) {
        Date date=new Date(time);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strTime =format.format(date);
        return strTime;
    }

    public static String parseTimeToDateStr(Long time) {
        Date date=new Date(time);
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String strTime =format.format(date);
        return strTime;
    }

    /***
     * 将时间戳转换为日期或时间字符串，当前显示时间其他显示日期
     * @param time
     * @return
     */
    public static String parseToTimeOrDateStr(Long time) {
        SimpleDateFormat formatDate=new SimpleDateFormat("yyyy-MM-dd");
        String strDate =formatDate.format(time);

        SimpleDateFormat formatTime=new SimpleDateFormat("HH:mm:ss");
        String strTime =formatTime.format(time);

        String strDateToday =formatDate.format(System.currentTimeMillis());
        if(strDateToday.equals(strDate)){
            return strTime;
        }else{
            return strDate;
        }
    }

    /***
     * 将时间戳装换为服务器时间字符串
     * 服务器是GMT+08时区
     *
     * @param time
     * @return
     */
    /*public static String formatToServerTimeStr(long time) {
        if (time > 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            Date date = new Date(time);
            return dateFormat.format(date);
        } else {
            return null;
        }
    }*/


    /***
     * 装换Json
     *
     * @param json
     * @param beanClass
     * @return 如果转换出错 返回
     */
    public static <T extends BaseResponseBean> T parseJson(String json, Class<T> beanClass) {
        T bean = null;
        try {
            bean = JSON.parseObject(json, beanClass);
        } catch (JSONException ex) {
            Log.e("Utils", "fromJson error : " + ex.getMessage());
        }

        return bean;
    }

    /**
     * 将Json String 转换为JsonObject 如果Json格式错误或Code!=0 返回null
     *
     * @param json
     * @param beanClass
     * @param <T>
     * @return 转换正确且Code==0返回 beanClass,否则返回null,并Toast 错误信息
     */
    public static <T extends BaseResponseBean> T parseJsonTostError(String json, Class<T> beanClass) {
        T bean = parseJson(json, beanClass);
        if (bean == null) {
            ToastUtils.toast(R.string.json_syntax_error);
        } else {
            if (bean.getCode() == 0) {
                return bean;
            } else if (bean.getCode() == 3) {
                ToastUtils.toast(R.string.utils_token_timeout);
                UserInfo.logout();
                Intent homePageIntent = new Intent(App.getInstance(), MainActivity.class);
                homePageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                App.getInstance().startActivity(homePageIntent);
            } else {
                ToastUtils.toast(bean.getMessage());
            }
        }
        return null;
    }


    public static String verifyResponseResult(BaseResponseBean bean) {
        String errorMsg = null;
        if (bean == null) {
            errorMsg = App.getInstance().getString(R.string.json_syntax_error);
        } else {
            if (bean.getCode() == 0) {
                errorMsg = null;
            } else if (bean.getCode() == 3) {
                errorMsg = App.getInstance().getString(R.string.utils_token_timeout);
                ToastUtils.toast(errorMsg);
                UserInfo.logout();
                Intent homePageIntent = new Intent(App.getInstance(), MainActivity.class);
                homePageIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                App.getInstance().startActivity(homePageIntent);
            } else {
                errorMsg = bean.getMessage();
            }
        }
        return errorMsg;
    }

    public static boolean sameDate(Calendar cal, Calendar selectedDate) {
        return cal.get(MONTH) == selectedDate.get(MONTH)
                && cal.get(YEAR) == selectedDate.get(YEAR)
                && cal.get(DAY_OF_MONTH) == selectedDate.get(DAY_OF_MONTH);
    }

    public static boolean betweenDates(Calendar cal, Calendar minCal, Calendar maxCal) {
        final Date date = cal.getTime();
        return betweenDates(date, minCal, maxCal);
    }

    public static boolean betweenDates(Date date, Calendar minCal, Calendar maxCal) {
        final Date min = minCal.getTime();
        return (date.equals(min) || date.after(min)) // >= minCal
                && date.before(maxCal.getTime()); // && < maxCal
    }

    public static void hideSoftInputFromWindow(View view) {
        InputMethodManager imm = (InputMethodManager) App.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 下载并安装app
     *
     * @param url
     */
    public static void installApp(String url) {
        final DownloadManager systemService = (DownloadManager) App.getInstance().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "upgrade.apk");
        systemService.enqueue(request);
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                DownloadManager.Query myDownloadQuery = new DownloadManager.Query();
                myDownloadQuery.setFilterById(reference);

                Cursor myDownload = systemService.query(myDownloadQuery);
                if (myDownload.moveToFirst()) {
                    int fileUriIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

                    String fileUri = myDownload.getString(fileUriIdx);

                    Intent ViewInstallIntent = new Intent(Intent.ACTION_VIEW);
                    ViewInstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ViewInstallIntent.setDataAndType(Uri.parse(fileUri), "application/vnd.android.package-archive");
                    context.startActivity(ViewInstallIntent);
                }
                myDownload.close();

                App.getInstance().unregisterReceiver(this);
            }
        };
        App.getInstance().registerReceiver(receiver, filter);
    }


    public static int getScreenWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static Dialog createDialog(Activity context, int dialogLayoutRes) {
        final Dialog dialog = new Dialog(context, com.zhan.framework.R.style.Dialog);
        dialog.setContentView(dialogLayoutRes);
        /*Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        int screenW = getScreenWidth(context);
        lp.width = (int) (0.6 * screenW);*/
        return dialog;
    }

    public static Dialog showConfirmDialog(Activity activity,String title,String summary,View.OnClickListener onConfirmClickListener,Object tag){

        final Dialog confirmDialog=Tools.createDialog(activity, R.layout.dialog_cancel_or_confirm);

        TextView titleView= (TextView)confirmDialog.findViewById(R.id.title);
        titleView.setText(title);
        TextView summaryView= (TextView)confirmDialog.findViewById(R.id.summary);
        summaryView.setText(summary);

        TextView cancelBtn= (TextView)confirmDialog.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
            }
        });

        TextView confirmBtn= (TextView)confirmDialog.findViewById(R.id.confirm);
        confirmBtn.setTag(tag);
        confirmBtn.setOnClickListener(onConfirmClickListener);

        confirmDialog.show();

        return confirmDialog;
    }

    public static boolean checkMobilePhoneNumber(String phoneNumber) {
        String telRegex = "[1]\\d{10}";
        if (TextUtils.isEmpty(phoneNumber)) return false;
        else return phoneNumber.matches(telRegex);
    }

   /* public static ActionSheetDialog createSheetDialog(Activity context, int dialogLayoutRes){
        final Dialog dialog2 = new Dialog(context, com.zhan.framework.R.style.Dialog);
        dialog2.setContentView(dialogLayoutRes);
        *//*Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        int screenW = getScreenWidth(context);
        lp.width = (int) (0.6 * screenW);*//*
        return dialog2;
    }*/

    public static String parseTimeLeftStr(long between){
        int day = (int) (between / (24 * 60 * 60 * 1000));
        int hour = (int) (between / (60 * 60 * 1000) - day * 24);
        int min = (int) ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        int second = (int) (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        return String.format("%02d天 %02d小时 %02d分 %02d秒",day,hour,min,second);
    }

    private static DisplayImageOptions sDefAvatarDisplayImageOptions;

    /**
     * 头像选项
     * @return
     */
    public static DisplayImageOptions buildDisplayImageOptionsForAvatar(){
        if(sDefAvatarDisplayImageOptions ==null){
            sDefAvatarDisplayImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.bg_head_portrait)
                    .showImageForEmptyUri(R.drawable.bg_head_portrait)
                    .showImageOnFail(R.drawable.bg_head_portrait)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .build();
        }
        return sDefAvatarDisplayImageOptions;
    }

    private static DisplayImageOptions sDefDisplayImageOptions;
    /**
     * 默认图片选项
     * @return
     */
    public static DisplayImageOptions buildDefaultDisplayCompanyLogoImageOptions(){
        if(sDefDisplayImageOptions ==null){
            sDefDisplayImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_company_logo)
                    .showImageForEmptyUri(R.drawable.ic_company_logo)
                    .showImageOnFail(R.drawable.ic_company_logo)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .build();
        }
        return sDefDisplayImageOptions;
    }

    /***
     * 商品图标选项
     * goodType p1/p2 或其他
     * @return
     */
    private static DisplayImageOptions sGoodsTypeP1ImageOptions;
    private static DisplayImageOptions sGoodsTypeP2ImageOptions;
    private static DisplayImageOptions sGoodsTypeNormalImageOptions;

    public static DisplayImageOptions buildProductDisplayImageOptions(String goodType){
        if(sGoodsTypeP1ImageOptions ==null){
            sGoodsTypeP1ImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.icon_p1)
                    .showImageForEmptyUri(R.drawable.icon_p1)
                    .showImageOnFail(R.drawable.icon_p1)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .build();
        }
        if(sGoodsTypeP2ImageOptions ==null){
            sGoodsTypeP2ImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.icon_p2)
                    .showImageForEmptyUri(R.drawable.icon_p2)
                    .showImageOnFail(R.drawable.icon_p2)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .build();
        }
        if(sGoodsTypeNormalImageOptions ==null){
            sGoodsTypeNormalImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.icon_product)
                    .showImageForEmptyUri(R.drawable.icon_product)
                    .showImageOnFail(R.drawable.icon_product)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .build();
        }

        if(TextUtils.isEmpty(goodType)){
            return sGoodsTypeNormalImageOptions;
        }else if("GOODS_MATERIAL_P1".equals(goodType.toUpperCase())){
            return sGoodsTypeP1ImageOptions;
        }else if("GOODS_MATERIAL_P2".equals(goodType.toUpperCase())){
            return sGoodsTypeP2ImageOptions;
        }else{
            return sGoodsTypeNormalImageOptions;
        }
    }

    public static void removeImgFromCache(String imgUrl) {
        MemoryCacheUtils.removeFromCache(imgUrl, ImageLoader.getInstance().getMemoryCache());
        DiskCacheUtils.removeFromCache(imgUrl, ImageLoader.getInstance().getDiskCache());
    }

    public static void clearImgCahce() {
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();
    }

    private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static void setTextView(TextView textView,String text){
        if(textView==null){
            return;
        }
        if(TextUtils.isEmpty(text)){
            textView.setText("");
        }else{
            textView.setText(text);
        }
    }

}
