package com.infoac.cas.dto;

/**
* @author: Mr.Xu
* @Date: 2020/1/2 15:54
* @Description answer年表查询结果
*/
public class YearsTableAnswerDTO {

    //年份
    private String yearStr;
    //
    private String id;
    //
    private String reelId;
    //
    private String subjectId;
    //
    private String optionsId;
    //
    private String userId;
    //
    private String createDate;
    //
    private String pageId;
    //
    private String receiverId;

    public YearsTableAnswerDTO() {
    }

    public YearsTableAnswerDTO(String yearStr, String id, String reelId, String subjectId, String optionsId, String userId, String createDate, String pageId, String receiverId) {
        this.yearStr = yearStr;
        this.id = id;
        this.reelId = reelId;
        this.subjectId = subjectId;
        this.optionsId = optionsId;
        this.userId = userId;
        this.createDate = createDate;
        this.pageId = pageId;
        this.receiverId = receiverId;
    }

    public String getYearStr() {
        return yearStr;
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr;
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

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(String optionsId) {
        this.optionsId = optionsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }
}
