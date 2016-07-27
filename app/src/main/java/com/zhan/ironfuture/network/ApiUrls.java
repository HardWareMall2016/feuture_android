package com.zhan.ironfuture.network;

/**
 * 作者：伍岳 on 2016/3/7 17:34
 * 邮箱：wuyue8512@163.com
 //
 //         .............................................
 //                  美女坐镇                  BUG辟易
 //         .............................................
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 //
 */
public class ApiUrls {

    //User - 用户登录
    public static final String UPLOAD_IMG = "uploadImg.do";
    //User - 申请离职
    public static final String LEAVING_COMPANY = "leavingCompany.do";

    /***
     * 登录注册
     */
    //User - 用户登录
    public static final String TEACHER_LOGIN = "Login.do";
    //验证手机号和邀请码
    public static final String CHECK_MOBILE_NO = "checkMobileNo.do";
    //注册
    public static final String REGISTER = "Register.do";
    //获取验证码
    public static final String GET_CHECET_CODE = "GetChectCode.do";

    /***
     * 公共-财务管理
     */
    //财务管理-公司财务流水
    public static final String GETCOMMONEYLOG = "getComMoneyLog.do";
    //财务管理-公司列表
    public static final String GETCOMLIST = "getComList.do";
    //财务管理-转账
    public static final String TRANSFERMONEY = "TransferMoney.do";
    /***
     * 公共-个人信息
     */
    //个人信息getPersonCenter
    public static final String GETPERSONCENTER = "getPersonCenter.do";

    public static final String UPDATE_USER_INFO = "updateUserInfo.do";
    /***
     * 公共-人员管理
     */
    //人员列表
    public static final String QUERYPERSONLIST = "queryPersonList.do";
    //职位分配列表
    public static final String DET_POST_PROCAR_LIST = "deptPostProCarList.do";
    //职位分配
    public static final String COMJOBALLOT = "comJobAllot.do";

    /***
     * 管理层
     */
    //部门管理-部门信息
    public static final String DEPARTMENTINFO = "DepartmentInfo.do";
    //人才市场-人才搜索条件
    public static final String GET_QUERYCRITERIA = "getQueryCriteria.do";
    //人才市场-人才搜索
    public static final String SELECTSEEKERS = "SelectSeekers.do";
    //人才市场-人才邀请
    public static final String INVITE_JOB = "inviteJob.do";
    //公司消息-消息列表
    public static final String BBSLISTS = "bbsList.do";
    //公司消息-查询消息详情
    public static final String GETMSGDETAIL = "getMsgDetails.do";
    //公司消息-创建公司消息
    public static final String CREATEBBS = "CreateBBS.do";
    //公司消息-消息审批
    public static final String MSG_REVIEW = "msgReview.do";
    //解雇
    public static final String FIRE = "Dismiss.do";
    //编辑员工职位
    public static final String EDIT_JOB_POSITION = "editJobPosition.do";
    //设置部门预算
    public static final String DEPARTMENT_BUDGET = "DepartmentBudget.do";
    //获取 部门及岗位
    public static final String GET_DEPT_POST = "getDeptPost.do";
    //创建部门职位邀请码
    public static final String JOB_POSITION_CODE = "JobPositionCode.do";
    //解锁部门
    public static final String UNLOCK_DEPARTMENT = "TurnONDepartment.do";
    //商品订单列表
    public static final String CHECK_GOODS_ORDER_LIST = "CheckGoodsOrderList.do";
    //物流订单列表
    public static final String CHECK_LOGISTICS_ORDER_LIST = "CheckLogisticsOrderList.do";
    //确认订单
    public static final String CHECK_ORDER = "CheckOrder.do";
    //新订单审核 - 发布订单
    public static final String CHECK_NEW_ORDER = "order/bs/checkNewOrder.do";
    //新订单审核 - 预接单接口
    public static final String PRE_ACCEPT_ORDER = "order/bs/reReceiveOrder.do";
    //职位发布
    public static final String PUB_JOB_INFO = "pubJobInfo.do";
    /***
     * 仓储部
     */
    //仓库管理--仓库信息
    public static final String WAREHOUSEINFO = "WarehouseInfo.do";
    //仓库管理-仓库升级
    public static final String UPGRADING = "Upgrading.do";
    //仓库管理-移动物品
    public static final String MOVEGOODS = "MoveGoods.do";
    //仓库管理-删除物品
    public static final String DISCARDGOODS = "DiscardGoods.do";
    //采购订单-物品选择
    public static final String GETGOODSLIST = "getGoodsList.do";
    //新增购销订单
    public static final String ORDER_BS_CREATE = "order/bs/create.do";
    //新增物流订单
    public static final String ORDER_LOGIS_CREATE = "order/logis/create.do";
    //查询订单
    public static final String ORDER_BS_QUERY_ORDER = "order/bs/queryOrder.do";
    //购买p1p2级原料
    public static final String BUY_GOODS_P1P2 = "buyGoodsP1P2.do";
    //商品销售-获取商品数目
    public static final String GET_GOODS_SUM_BY_GOODSID = "getGoodsSumByGoodsId.do";

    //市场采购-原料采购
    public static final String GET_GOODS_P1P2_LIST = "getGoodsP1P2List.do";
    //商品销售-交易中心
    public static final String TRADINGCENTER = "TradingCenter.do";
    //商品销售-仓库物品出入库记录
    public static final String STORAGE_IO_INFO = "getInOutStorageInfo.do";

    /***
     * 产线与求职
     */
    //求职市场-职位列表
    public static final String SEARCHJOB = "SearchJob.do";
    //求职市场-求职
    public static final String REQUESTJOB = "RequestJob.do";

    /***
     * 基建部
     */
    //路线建设-线路建设列表
    public static final String GET_ROTE_BUILD_INFO = "getRoteBuildInfo.do";
    //路线建设-创建新的线路
    public static final String CREAT_RODE_LINE = "creatRodeLine.do";

    //路线管理-路线管理列表
    public static final String RODELINELIST = "rodeLineList.do";
    //路线管理-删除线路
    public static final String DEL_RODE_LINE = "delRodeLine.do";
    //路线管理-线路详情
    public static final String RODELINEDETAIL = "rodeLineDetail.do";
    //路线管理-货车补给
    public static final String CARADDFULL = "carAddfull.do";
    //路线管理-购买新货车
    public static final String RODELLINBUYCAR = "rodelLinBuyCar.do";

    /***
     * 生产部门经理
     */
    //生产产线-原料加工、商品生产详细列表
    public static final String GETPROLINELIST = "getProLineList.do";
    //生产产线-购买产线
    public static final String CREATEPROLINE = "creatProLine.do";
    //生产产线-产线详情
    public static final String GEPROLINEINFO = "getProLineInfo.do";
    //生产产线-产出物品
    public static final String BEGINPROLINE = "beginProLine.do";

    //显示当前员工所在产线,昨天和今天创造的贡献
    public static final String GET_PROLINE_PERSON_INFO = "getProLinePersonInfo.do";

    //点击开始工作后增加贡献点
    public static final String ADD_STEPS = "addSteps.do";


    /***
     * 物流部
     */
    //物流-货车详情列表
    public static final String GETCARSINFOLIST = "getCarsInfoList.do";
    //物流-货车装货仓库
    public static final String GETCARSADDINFOLIST = "getCarsAddInfoList.do";
    //物流-货车装货详情(卸货详情)
    public static final String GETCARSADDINFO = "getCarsAddInfo.do";
    //物流-装货
    public static final String ADDLOADING = "addLoading.do";
    //物流-货车卸货
    public static final String GET_CARS_REMOVE_INFO_LIST = "getCarsRemoveInfoList.do";
    //物流-卸货
    public static final String REMOVE_LOADING = "removeLoading.do";

    /***
     * 原料部门
     */
    //原料部门经理-显示废料列表
    public static final String GET_GARBAGE_LIST = "getGarbageList.do";
    //精炼废料 开始
    public static final String START_WASTE_REFINING = "startWasteRefining.do";
    //配方研发列表
    public static final String SELECT_RECIPE_LIST = "selectRecipeList.do";
    //配方研发
    public static final String CREATE_RECIPE = "createRecipe.do";
    //配方删除
    public static final String DELETE_RECIPE = "deleteRecipe.do";

    //添加事件
    public static final String ADD_EVENT = "http://userfeedback.tpooo.com/AppEvent/appEvent/eventmsg/add.do";
}
