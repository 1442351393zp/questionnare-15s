package com.infoac.cas.dto;

import java.util.List;

/**
* @author: Mr.Xu
* @Date: 2020/1/3 14:15
* @Description 编辑答案接口请求参数
*/
public class UpdateOptionsParams {
    private String reelId;
    private String subjectId;
    private String receiverId;
    private List<String> optionsId;

    public UpdateOptionsParams() {
    }

    public UpdateOptionsParams(String reelId, String subjectId, String receiverId, List<String> optionsId) {
        this.reelId = reelId;
        this.subjectId = subjectId;
        this.receiverId = receiverId;
        this.optionsId = optionsId;
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

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public List<String> getOptionsId() {
        return optionsId;
    }

    public void setOptionsId(List<String> optionsId) {
        this.optionsId = optionsId;
    }
}
