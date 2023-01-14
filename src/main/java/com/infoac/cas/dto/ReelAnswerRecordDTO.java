package com.infoac.cas.dto;

import java.util.List;

/**
* @author: Mr.Xu
* @Date: 2019/12/31 10:17
* @Description 问卷回答记录
*/
public class ReelAnswerRecordDTO {

    private String id;
    //问卷编号
    private String reelId;
    //答题人id
    private String userId;
    //答题人name，如有匿名存***
    private String userName;
    //答题花费时长---时间戳
    private String timeConsuming;
    //答题开始时间
    private String startTime;
    //答题结束时间
    private String endTime;
    //数据状态  0：无效   1：有效
    private int status;
    //记录时间
    private String createTime;
    //记录更新时间
    private String updateTime;
    //操作员编号
    private String createOpId;
    //操作员名称
    private String createOpName;
    //操作员组织编号
    private String createOrgId;
    //操作员组织名称
    private String createOrgName;

    //删除使用
    private List<String> reelAnswerRecordIds;

    public ReelAnswerRecordDTO() {
    }

    public ReelAnswerRecordDTO(String id, String reelId, String userId, String userName, String timeConsuming, String startTime, String endTime, int status, String createTime, String updateTime, String createOpId, String createOpName, String createOrgId, String createOrgName, List<String> reelAnswerRecordIds) {
        this.id = id;
        this.reelId = reelId;
        this.userId = userId;
        this.userName = userName;
        this.timeConsuming = timeConsuming;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createOpId = createOpId;
        this.createOpName = createOpName;
        this.createOrgId = createOrgId;
        this.createOrgName = createOrgName;
        this.reelAnswerRecordIds = reelAnswerRecordIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReelId() {
        return reelId;
    }

    public void setReelId(String reelId) {
        this.reelId = reelId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(String timeConsuming) {
        this.timeConsuming = timeConsuming;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateOpId() {
        return createOpId;
    }

    public void setCreateOpId(String createOpId) {
        this.createOpId = createOpId;
    }

    public String getCreateOpName() {
        return createOpName;
    }

    public void setCreateOpName(String createOpName) {
        this.createOpName = createOpName;
    }

    public String getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(String createOrgId) {
        this.createOrgId = createOrgId;
    }

    public String getCreateOrgName() {
        return createOrgName;
    }

    public void setCreateOrgName(String createOrgName) {
        this.createOrgName = createOrgName;
    }

    public List<String> getReelAnswerRecordIds() {
        return reelAnswerRecordIds;
    }

    public void setReelAnswerRecordIds(List<String> reelAnswerRecordIds) {
        this.reelAnswerRecordIds = reelAnswerRecordIds;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
