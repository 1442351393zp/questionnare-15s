package com.infoac.cas.dto;

import java.util.Date;

/**
* @author: Mr.Xu
* @Date: 2019/12/30 15:14
* @Description 问卷浏览记录
*/
public class ReelBrowseRecordDTO {

    private String id;

    //问卷id
    private String reelId;

    //浏览者id
    private String userId;

    //浏览时间
    private Date browseTime;

    //备注
    private String remark;

    //创建人编号
    private String createOpId;

    //创建人名称
    private String createOpName;

    //创建人组织编号
    private String createOrgId;

    //创建人组织名称
    private String createOrgName;

    public ReelBrowseRecordDTO() {
    }

    public ReelBrowseRecordDTO(String id, String reelId, String userId, Date browseTime, String remark, String createOpId, String createOpName, String createOrgId, String createOrgName) {
        this.id = id;
        this.reelId = reelId;
        this.userId = userId;
        this.browseTime = browseTime;
        this.remark = remark;
        this.createOpId = createOpId;
        this.createOpName = createOpName;
        this.createOrgId = createOrgId;
        this.createOrgName = createOrgName;
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

    public Date getBrowseTime() {
        return browseTime;
    }

    public void setBrowseTime(Date browseTime) {
        this.browseTime = browseTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "ReelBrowseRecordDto{" +
                "id=" + id +
                ", reelId='" + reelId + '\'' +
                ", userId='" + userId + '\'' +
                ", browseTime=" + browseTime +
                ", remark='" + remark + '\'' +
                ", createOpId='" + createOpId + '\'' +
                ", createOpName='" + createOpName + '\'' +
                ", createOrgId='" + createOrgId + '\'' +
                ", createOrgName='" + createOrgName + '\'' +
                '}';
    }
}
