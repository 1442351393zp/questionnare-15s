package com.infoac.cas.dto;

import java.util.List;

/**
 * 创建问卷文本编辑总类
 */
public class ReelTextListDTO {

    private String rid;
    private List<ReelText> list;


    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public List<ReelText> getList() {
        return list;
    }

    public void setList(List<ReelText> list) {
        this.list = list;
    }
}
