package com.zhan.ironfuture.base;

/**
 * Created by Wuyue on 2015/10/14.
 */
public class UserInfo extends BasePersistObject {
    private boolean isLogin =false;

    private String token;
    private int UserID;
    private int postId;
    private int comId ;
    private int deptid;
    private String peopleName;
    private String schoolName;
    private String accountName;

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }


    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }



    private String HeadImgUrl;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getHeadImgUrl() {
        return HeadImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        HeadImgUrl = headImgUrl;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getDeptid() {
        return deptid;
    }

    public void setDeptid(int deptid) {
        this.deptid = deptid;
    }

    private static UserInfo sUserInfo=null;

    public static UserInfo getCurrentUser() {
        if(sUserInfo==null){
            sUserInfo=getPersisObject(UserInfo.class);
        }
        return sUserInfo;
    }

    public static void saveLoginUserInfo(UserInfo user) {
        //如果登录的不是同一个用户就清除相关数据
        /*if(getCurrentUser()==null||!getCurrentUser().getUserID().equals(user.getUserID())){
            PushMessageInfo.clearPushMessageInfo();
        }*/
        persisObject(user);
        sUserInfo=user;
    }

    public static void logout() {
        if(sUserInfo!=null){
            sUserInfo.setIsLogin(false);
            persisObject(sUserInfo);
        }
        //PushMessageInfo.clearPushMessageInfo();
        /*deletePersistObject(UserInfo.class);
        sUserInfo=null;*/
    }

    /***
     * 是否是管理层
     * @return
     */
    public boolean isManagementLayer(){
        if(postId==IronFutureConstants.ROLE_CEO||postId==IronFutureConstants.ROLE_CORPORATE){
            return true;
        }else{
            return false;
        }
    }

    /***
     * 是否是仓库部门
     * @return
     */
    public boolean isStorePurchaseLayer(){
        if(postId==IronFutureConstants.ROLE_WAREHOUSE_MANAGER||postId==IronFutureConstants.ROLE_WAREHOUSE_STAFF){
            return true;
        }else{
            return false;
        }
    }

    /***
     * 是否是物流部门
     * @return
     */
    public boolean isLogisticsLayer(){
        if(postId==IronFutureConstants.ROLE_LOGISTICS_DEPARTMENT_MANAGER||postId==IronFutureConstants.ROLE_LOGISTICS_DEPARTMENT_STAFF){
            return true;
        }else{
            return false;
        }
    }
}
