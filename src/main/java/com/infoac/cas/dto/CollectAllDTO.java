package com.infoac.cas.dto;

import java.util.List;

/**
 * 收藏题目及默认值
 */
public class CollectAllDTO {

    private  String code;
    private String msg;
    private List<CollectDTO> collectList;
    private List<CollectDTO> contentList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CollectDTO> getCollectList() {
        return collectList;
    }

    public void setCollectList(List<CollectDTO> collectList) {
        this.collectList = collectList;
    }

    public List<CollectDTO> getContentList() {
        return contentList;
    }

    public void setContentList(List<CollectDTO> contentList) {
        this.contentList = contentList;
    }
}
