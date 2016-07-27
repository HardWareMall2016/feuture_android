package com.zhan.ironfuture.ui.fragment.more;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhan.framework.cache.CacheData;
import com.zhan.framework.cache.CacheManager;
import com.zhan.framework.network.HttpRequestHandler;
import com.zhan.framework.network.HttpRequestUtils;
import com.zhan.framework.support.inject.ViewInject;
import com.zhan.framework.ui.activity.ActionBarActivity;
import com.zhan.framework.ui.activity.BaseActivity;
import com.zhan.framework.ui.fragment.ABaseFragment;
import com.zhan.framework.ui.widget.CircleImageView;
import com.zhan.framework.utils.Consts;
import com.zhan.framework.utils.PixelUtils;
import com.zhan.framework.utils.ToastUtils;
import com.zhan.ironfuture.R;
import com.zhan.ironfuture.base.BaseRequestBean;
import com.zhan.ironfuture.base.BaseResponseBean;
import com.zhan.ironfuture.base.IronFutureConstants;
import com.zhan.ironfuture.base.UserInfo;
import com.zhan.ironfuture.beans.PersonCenterRequestBean;
import com.zhan.ironfuture.beans.PersonCenterResponseBean;
import com.zhan.ironfuture.beans.UpdateUserInfoRequestBean;
import com.zhan.ironfuture.beans.UploadResponseBean;
import com.zhan.ironfuture.network.ApiUrls;
import com.zhan.ironfuture.ui.activity.MainActivity;
import com.zhan.ironfuture.utils.PicturePickHelper;
import com.zhan.ironfuture.utils.PopMenuHelper;
import com.zhan.ironfuture.utils.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Created by WuYue on 2016/4/25.
 * 设置
 */
public class SettingsFragment extends ABaseFragment {

    @ViewInject(id = R.id.user_head)
    LinearLayout mUserHeader;

    @ViewInject(id = R.id.settings_quit, click = "OnClick")
    View mViewQuit;

    @ViewInject(id = R.id.clear_cache, click = "OnClick")
    View mViewClearCache;

    @ViewInject(id = R.id.update_people_name, click = "OnClick")
    View mViewUpdatePeopleName;

    @ViewInject(id = R.id.update_nick_name, click = "OnClick")
    View mViewUpdateNickName;

    @ViewInject(id = R.id.update_school_name, click = "OnClick")
    View mViewUpdateSchoolName;

    @ViewInject(id = R.id.person_name)
    TextView mPersonName;

    @ViewInject(id = R.id.item_phone)
    TextView mViewPhone;

    @ViewInject(id = R.id.item_nick_name)
    TextView mViewItemNickName;

    @ViewInject(id = R.id.item_person_name)
    TextView mViewItemPersonName;

    @ViewInject(id = R.id.item_school_name)
    TextView mViewItemSchoolName;

    @ViewInject(id = R.id.person_departmentname)
    TextView mPersonDepartmentName;

    @ViewInject(id = R.id.user_img, click = "OnClick")
    CircleImageView mUserImage;

    private Dialog mDialog;

    //tools
    private PicturePickHelper mPhotoUtils;
    private PopMenuHelper mPopMenuHelper;
    private LayoutInflater mInflater;

    private int mStatusBarHeight = 0;
    private int mActionbarHeight = 0;

    @Override
    public void onPrepareSetContentView(BaseActivity activity) {
        activity.setOverlay(true);
        activity.showActionbarUnderline(false);
        mStatusBarHeight = activity.getStatusBarHeight();
        mActionbarHeight = activity.getActionbarHeight();
    }

    @Override
    public void onPrepareActionbarMenu(TextView menu, Activity activity) {
        //无业人员是没有辞职选项的
        if(UserInfo.getCurrentUser().getPostId()== IronFutureConstants.ROLE_NONE){
            return;
        }

        if(UserInfo.getCurrentUser().getPostId() == 1){
            return;
        }


        menu.setText("辞职");
        ColorStateList whiteSelector;
        Resources resources = activity.getResources();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            whiteSelector = resources.getColorStateList(R.color.text_color_white_selector, null);
        } else {
            whiteSelector = resources.getColorStateList(R.color.text_color_white_selector);
        }
        menu.setTextColor(whiteSelector);
        menu.setPadding(PixelUtils.dp2px(8), PixelUtils.dp2px(4), PixelUtils.dp2px(8), PixelUtils.dp2px(4));
        menu.setBackgroundResource(R.drawable.bg_white_empty_rounded_selector);
    }

    @Override
    public void onActionBarMenuClick() {
        mDialog=Tools.showConfirmDialog(getActivity(), "辞职申请", "确定要辞职吗", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dimissionApplyRequest();
                if(mDialog!=null&&mDialog.isShowing()){
                    mDialog.dismiss();
                }
            }
        },null);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.frag_settings;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        getActivity().setTitle("");

        ColorStateList whiteSelector;
        Resources resources = getActivity().getResources();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            whiteSelector = resources.getColorStateList(R.color.text_color_white_selector, null);
        } else {
            whiteSelector = resources.getColorStateList(R.color.text_color_white_selector);
        }
        ((ActionBarActivity)getActivity()).getBackBtn().setTextColor(whiteSelector);
        //((ActionBarActivity)getActivity()).getBackBtn().setTextColor(Color.rgb(190, 190, 190));

        int padding = PixelUtils.dp2px(16);
        mUserHeader.setPadding(padding, mStatusBarHeight + mActionbarHeight, padding, padding);

        mPhotoUtils=new PicturePickHelper(this);

        mPopMenuHelper=new PopMenuHelper(getActivity());

        mInflater=inflater;

        ImageLoader.getInstance().displayImage(UserInfo.getCurrentUser().getHeadImgUrl(),mUserImage,Tools.buildDisplayImageOptionsForAvatar());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPhotoUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void requestData() {
        PersonCenterRequestBean request = new PersonCenterRequestBean();
        PersonCenterRequestBean.DataEntity data = new PersonCenterRequestBean.DataEntity();
        request.setData(data);
        startJsonRequest(ApiUrls.GETPERSONCENTER, request, new BaseHttpRequestTask<PersonCenterResponseBean>() {

            @Override
            public PersonCenterResponseBean parseResponseToResult(String content) {
                return Tools.parseJson(content, PersonCenterResponseBean.class);
            }

            @Override
            public String verifyResponseResult(PersonCenterResponseBean result) {
                return Tools.verifyResponseResult(result);
            }

            @Override
            protected void onSuccess(PersonCenterResponseBean bean) {
                super.onSuccess(bean);
                mPersonName.setText(bean.getData().getPeopleName());
                mPersonDepartmentName.setText(bean.getData().getDepartmentName());

                Tools.setTextView(mViewItemNickName, bean.getData().getAccountName());

                Tools.setTextView(mViewItemPersonName, bean.getData().getPeopleName());

                Tools.setTextView(mViewItemSchoolName, bean.getData().getSchoolName());

                Tools.setTextView(mViewPhone, bean.getData().getPhone());

                updateUserInfo(bean);
            }
        });
    }

    private void updateUserInfo(PersonCenterResponseBean bean){
        UserInfo userInfo=UserInfo.getCurrentUser();
        userInfo.setAccountName(bean.getData().getAccountName());
        userInfo.setPeopleName(bean.getData().getPeopleName());
        userInfo.setSchoolName(bean.getData().getSchoolName());
        UserInfo.saveLoginUserInfo(userInfo);
    }


    private void updateUserInfo(UpdateItem type,String name){
        if(isRequestProcessing(ApiUrls.UPDATE_USER_INFO)){
            return;
        }

        UpdateUserInfoRequestBean requestBean=new UpdateUserInfoRequestBean();
        UpdateUserInfoRequestBean.DataEntity data=new UpdateUserInfoRequestBean.DataEntity();
        requestBean.setData(data);
        data.setSchoolName(UserInfo.getCurrentUser().getSchoolName());
        data.setPeopleName(UserInfo.getCurrentUser().getPeopleName());
        data.setAccountName(UserInfo.getCurrentUser().getAccountName());
        switch (type){
            case PEOPLE_NAME:
                data.setPeopleName(name);
                break;
            case NICK_NAME:
                data.setAccountName(name);
                break;
            case SCHOOL_NAME:
                data.setSchoolName(name);
                break;
        }

        startJsonRequest(ApiUrls.UPDATE_USER_INFO, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode) {
                    case success:
                        BaseResponseBean responseBean = Tools.parseJsonTostError(result, BaseResponseBean.class);
                        if (responseBean != null) {

                            Tools.hideSoftInputFromWindow(getRootView());
                            mPopMenuHelper.closePopWin();

                            ToastUtils.toast("修改成功！");

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

    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.settings_quit:
                UserInfo.logout();
                Intent homePageIntent = new Intent(getActivity(), MainActivity.class);
                homePageIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homePageIntent);
                break;
            case R.id.user_img:
                mPhotoUtils.setOnPicturePickerListener(new PicturePickHelper.OnPicturePickerListener() {
                    @Override
                    public void onPictureSelected(String mediaUri) {
                        uploadPicture(mediaUri);
                    }
                });
                mPhotoUtils.showPickerView();
                break;
            case R.id.clear_cache:
                new ClearCacheTask().execute();
                break;
            case R.id.update_people_name:
                updateUserProfile(UpdateItem.PEOPLE_NAME);
                break;
            case R.id.update_nick_name:
                updateUserProfile(UpdateItem.NICK_NAME);
                break;
            case R.id.update_school_name:
                updateUserProfile(UpdateItem.SCHOOL_NAME);
                break;
        }
    }

    private enum UpdateItem{PEOPLE_NAME,NICK_NAME,SCHOOL_NAME};

    private void updateUserProfile(final UpdateItem type){

        View customerView=mInflater.inflate(R.layout.pop_memu_update_userinfo,null);

        final EditText content=(EditText)customerView.findViewById(R.id.content);
        TextView viewTitle = (TextView)customerView.findViewById(R.id.title);
        switch (type){
            case PEOPLE_NAME:
                viewTitle.setText("修改姓名");
                content.setText(UserInfo.getCurrentUser().getPeopleName());
                break;
            case NICK_NAME:
                viewTitle.setText("修改昵称");
                content.setText(UserInfo.getCurrentUser().getAccountName());
                break;
            case SCHOOL_NAME:
                viewTitle.setText("修改学校");
                content.setText(UserInfo.getCurrentUser().getSchoolName());
                break;
        }

        View btnCancel = customerView.findViewById(R.id.cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.hideSoftInputFromWindow(getRootView());
                mPopMenuHelper.closePopWin();
            }
        });

        View btnFinish = customerView.findViewById(R.id.finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=content.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    ToastUtils.toast("输入为空！");
                    return;
                }
                updateUserInfo(type,name);
            }
        });

        mPopMenuHelper.showCustomerMenu(customerView);
    }

    private class ClearCacheTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            showRotateProgressDialog("正在清除缓存",false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            CacheManager.clearAllCache();
            Tools.clearImgCahce();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            closeRotateProgressDialog();
            ToastUtils.toast("清除完成");
        }
    }

    private void uploadPicture(String imgPath){
        RequestParams requestParams=new RequestParams();
        requestParams.put("type", 1);
        requestParams.put("userID", String.valueOf(UserInfo.getCurrentUser().getUserID()));
        try {
            requestParams.put("filePath", new File(imgPath));
        } catch (FileNotFoundException e) {

        }
        HashMap<String,String> header=new HashMap<>();
        header.put("token",UserInfo.getCurrentUser().getToken());
        header.put("userID",String.valueOf(UserInfo.getCurrentUser().getUserID()));

        showRotateProgressDialog("上传中...", false);

        HttpRequestUtils.startRequest(Consts.BASE_URL, ApiUrls.UPLOAD_IMG, requestParams, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                closeRotateProgressDialog();
                switch (resultCode){
                    case success:
                        UploadResponseBean responseBean=Tools.parseJsonTostError(result,UploadResponseBean.class);
                        if(responseBean!=null){
                            UserInfo.getCurrentUser().setHeadImgUrl(responseBean.getData().getImageUrl());
                            ImageLoader.getInstance().displayImage(responseBean.getData().getImageUrl(),mUserImage,Tools.buildDisplayImageOptionsForAvatar());
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }

            }
        }, header, HttpRequestUtils.RequestType.POST);
    }

    private void dimissionApplyRequest(){
        if(isRequestProcessing(ApiUrls.LEAVING_COMPANY)){
            return;
        }
        BaseRequestBean requestBean=new BaseRequestBean();
        startJsonRequest(ApiUrls.LEAVING_COMPANY, requestBean, new HttpRequestHandler(this) {
            @Override
            public void onRequestFinished(ResultCode resultCode, String result) {
                switch (resultCode){
                    case success:
                        BaseResponseBean responseBean=Tools.parseJsonTostError(result,BaseResponseBean.class);
                        if(responseBean!=null){
                            ToastUtils.toast("辞职申请已提交！");
                        }
                        break;
                    default:
                        ToastUtils.toast(result);
                        break;
                }
            }
        });
    }
}