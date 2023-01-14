package com.infoac.cas.dto;

/**
 * 创建问卷文本编辑
 */
public class ReelText {

    private String title;//标题(问卷标题,欢迎语,题目,分页..)
    private String[] options; //选项
    private int pageIndex;//分页标识,0,1,2
    private String type;//标识,0问卷标题header,1欢迎语welcome,2题目radio/checkbox,3分页page
    private String remark;//备注

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
