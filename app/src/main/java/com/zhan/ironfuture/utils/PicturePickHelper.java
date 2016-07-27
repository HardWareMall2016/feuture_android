package com.zhan.ironfuture.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;

import com.zhan.framework.utils.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 作者：伍岳 on 2016/6/15 15:29
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
public class PicturePickHelper {

    private final int PICK_IMAGE_CODE = 100;
    private final int TAKE_PIC_CODE = 200;
    private final int CROP_PIC_CODE = 300;

    private int photoMode;

    private Fragment mFragment;

    private Uri takePhotoUri;

    private String mAvatarLocalPath;

    private OnPicturePickerListener mPicturePickerListener;

    public PicturePickHelper(Fragment fragment) {
        mFragment = fragment;
    }

    public void showPickerView() {

        mAvatarLocalPath = PathUtils.getUserAvatarPath();
        if (mAvatarLocalPath == null) {
            ToastUtils.toast("请插入存储卡");
            return;
        }
        takePhotoUri = Uri.fromFile(new File(mAvatarLocalPath));

        AlertDialog.Builder builder = new AlertDialog.Builder(mFragment.getActivity());
        builder.setAdapter(new ArrayAdapter<>(mFragment.getActivity(),
                        android.R.layout.simple_list_item_1, new String[]{"使用相机拍照", "从手机相册选择"}),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            openSystemCamera();
                        } else {
                            openSystemPickImage();
                        }
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void openSystemCamera() {
        // 创建拍照意图
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 指明图片的保存路径,相机拍照后，裁剪前，会先保存到该路径下；裁剪时，再从该路径加载图片
        // 若无这句，则拍照后，图片会放入内存中，从而由于占用内存太大导致无法剪切或者剪切后无法保存
        camera.putExtra(MediaStore.EXTRA_OUTPUT, takePhotoUri);
        mFragment.startActivityForResult(camera, TAKE_PIC_CODE);
    }


    /**
     * 相册获取
     */
    private void openSystemPickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");// 相片类型
        mFragment.startActivityForResult(intent, PICK_IMAGE_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == PICK_IMAGE_CODE) {
            photoMode = PICK_IMAGE_CODE;
            Uri uri = data.getData();
            takePhotoUri = uri;
            cropImage(uri, 80, 80, CROP_PIC_CODE);

        } else if (requestCode == TAKE_PIC_CODE) {
            photoMode = TAKE_PIC_CODE;
            cropImage(takePhotoUri, 80, 80, CROP_PIC_CODE);
        } else if (requestCode == CROP_PIC_CODE) {
            Bitmap photo;
            Bundle extra = data.getExtras();
            if (extra != null) {
                photo = (Bitmap) extra.get("data");
                if (photo == null) {
                    return;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                if (photoMode == PICK_IMAGE_CODE) {
                    takePhotoUri = Uri.fromFile(new File(mAvatarLocalPath));
                    saveBitmap(takePhotoUri, photo);
                    if(mPicturePickerListener !=null){
                        mPicturePickerListener.onPictureSelected(takePhotoUri.getPath());
                    }
                } else if (photoMode == TAKE_PIC_CODE) {
                    saveBitmap(takePhotoUri, photo);
                    if(mPicturePickerListener !=null){
                        mPicturePickerListener.onPictureSelected(takePhotoUri.getPath());
                    }
                }
            }
        }
    }

    /**
     * 裁剪图片
     *
     * @param uri
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    private void cropImage(Uri uri, int outputX, int outputY, int requestCode) {

        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");

        // 制定待裁剪的 image 所在路径 uri
        intent.setDataAndType(uri, "image/*");

        // 意图的 为 crop(裁剪) true
        intent.putExtra("crop", "true");

        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // 裁剪后，输出图片的尺寸
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);

        // 图片格式
        intent.putExtra("outputFormat", "JPEG");

        // 取消人脸识别功能
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);

        mFragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 将bitmap 保存到sd卡
     *
     * @param uri
     * @param bitmap
     */
    private void saveBitmap(Uri uri, Bitmap bitmap) {
        File file = new File(uri.getPath());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnPicturePickerListener(OnPicturePickerListener listener) {
        this.mPicturePickerListener = listener;
    }

    public interface OnPicturePickerListener {
        void onPictureSelected(String mediaUri);
    }
}
