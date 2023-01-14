package com.infoac.cas.dto;

import org.apache.commons.lang.StringUtils;

/**
* @author: Mr.Xu
* @Date: 2019/12/30 17:14
* @Description 问卷回收概况查询返回结果
*/
public class ReceiveDetailResultDto {

    //回收量
    private int receivedCount;

    //浏览量
    private int browseCount;

    //回收率
    private String receivedRate;

    //平均回收时间
    private String receivedAvgTime;

    //问卷标题
    private String reelName;

    //问卷id
    private String reelId;

    public ReceiveDetailResultDto() {

    }

    public ReceiveDetailResultDto(int receivedCount, int browseCount, String receivedRate, String receivedAvgTime, String reelName, String reelId) {
        this.receivedCount = receivedCount;
        this.browseCount = browseCount;
        this.receivedRate = receivedRate;
        this.receivedAvgTime = receivedAvgTime;
        this.reelName = reelName;
        this.reelId = reelId;
    }

    public int getReceivedCount() {
        return receivedCount;
    }

    public void setReceivedCount(int receivedCount) {
        this.receivedCount = receivedCount;
    }

    public int getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(int browseCount) {
        this.browseCount = browseCount;
    }

    public String getReceivedRate() {
        return receivedRate;
    }

    public void setReceivedRate(String receivedRate) {
        this.receivedRate = receivedRate;
    }

    public void setReceivedRate() {
        //默认按浏览量和回收量计算
        if(this.browseCount != 0)  setReceivedRate(Math.round(((float)receivedCount*100/(float)browseCount)) + "%");

        if(this.receivedRate == null) this.receivedRate = "0%";
    }

    public String getReceivedAvgTime() {
        return receivedAvgTime;
    }

    public void setReceivedAvgTime(String receivedAvgTime) {
        this.receivedAvgTime = receivedAvgTime;
    }

    public String getReelName() {
        return reelName;
    }

    public void setReelName(String reelName) {
        this.reelName = reelName;
    }

    public String getReelId() {
        return reelId;
    }

    public void setReelId(String reelId) {
        this.reelId = reelId;
    }
}
