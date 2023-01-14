package com.infoac.cas.dto;

/**
* @author: Mr.Xu
* @Date: 2019/12/31 15:23
* @Description 问卷回收量折线图返回结果
*/
public class ReceiveLineChartDTO {

    private String receivedTime;

    private String receivedCount;

    public ReceiveLineChartDTO() {
    }

    public ReceiveLineChartDTO(String receivedTime, String receivedCount) {
        this.receivedTime = receivedTime;
        this.receivedCount = receivedCount;
    }

    public String getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(String receivedTime) {
        this.receivedTime = receivedTime;
    }

    public String getReceivedCount() {
        return receivedCount;
    }

    public void setReceivedCount(String receivedCount) {
        this.receivedCount = receivedCount;
    }
}
