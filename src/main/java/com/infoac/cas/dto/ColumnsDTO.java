package com.infoac.cas.dto;

/**
* @author: Mr.Xu
* @Date: 2019/12/31 19:22
* @Description 动态列
*/
public class ColumnsDTO {

    //列id
    private String columnId;
    //列名称
    private String columnName;
    //列值id
    private String columnValueId;
    //列值名称
    private String columnValueName;
    //显隐性  1:显示  0：隐藏
    private int display;
    //0:固定列 1:动态列
    private int fixed;

    public ColumnsDTO() {
    }

    public ColumnsDTO(String columnId, String columnName, String columnValueId, String columnValueName, int display, int fixed) {
        this.columnId = columnId;
        this.columnName = columnName;
        this.columnValueId = columnValueId;
        this.columnValueName = columnValueName;
        this.display = display;
        this.fixed = fixed;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValueId() {
        return columnValueId;
    }

    public void setColumnValueId(String columnValueId) {
        this.columnValueId = columnValueId;
    }

    public String getColumnValueName() {
        return columnValueName;
    }

    public void setColumnValueName(String columnValueName) {
        this.columnValueName = columnValueName;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public int getFixed() {
        return fixed;
    }

    public void setFixed(int fixed) {
        this.fixed = fixed;
    }
}
