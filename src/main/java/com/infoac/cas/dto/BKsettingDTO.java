package com.infoac.cas.dto;

/**
 * 皮肤设置
 */
public class BKsettingDTO {

	private String rid ;
	private String bksetting ;
	private String updateTime ;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getBksetting() {
        return bksetting;
    }

    public void setBksetting(String bksetting) {
        this.bksetting = bksetting;
    }
}
