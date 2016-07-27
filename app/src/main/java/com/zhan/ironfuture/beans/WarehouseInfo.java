package com.zhan.ironfuture.beans;

import java.io.Serializable;

/**
 * 作者：伍岳 on 2016/5/25 16:19
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
public class WarehouseInfo implements Serializable{
    public boolean selected = false;
    private int spaceId;
    private int storageId;
    private String spaceType;
    private int goodsId;
    private int goodsCount;
    private int spaceSeqNo;
    private String spaceState;
    private int personId;
    private long unlockTime;
    private int storageLevel;
    private int compId;
    private int storageState;
    private long createTime;
    private long modifyTime;
    private String comName;
    private String goodsName;
    private int spaceStateInt;//1正常状态2订单状态3不可发起物流4可装卸
    private String goodsType;

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public void setSpaceSeqNo(int spaceSeqNo) {
        this.spaceSeqNo = spaceSeqNo;
    }

    public void setSpaceState(String spaceState) {
        this.spaceState = spaceState;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public void setUnlockTime(long unlockTime) {
        this.unlockTime = unlockTime;
    }

    public void setStorageLevel(int storageLevel) {
        this.storageLevel = storageLevel;
    }

    public void setCompId(int compId) {
        this.compId = compId;
    }

    public void setStorageState(int storageState) {
        this.storageState = storageState;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public int getStorageId() {
        return storageId;
    }

    public String getSpaceType() {
        return spaceType;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public int getSpaceSeqNo() {
        return spaceSeqNo;
    }

    public String getSpaceState() {
        return spaceState;
    }

    public int getPersonId() {
        return personId;
    }

    public long getUnlockTime() {
        return unlockTime;
    }

    public int getStorageLevel() {
        return storageLevel;
    }

    public int getCompId() {
        return compId;
    }

    public int getStorageState() {
        return storageState;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public int getSpaceStateInt() {
        return spaceStateInt;
    }

    public void setSpaceStateInt(int spaceStateInt) {
        this.spaceStateInt = spaceStateInt;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }
}
