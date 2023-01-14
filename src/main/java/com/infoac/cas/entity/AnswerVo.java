package com.infoac.cas.entity;
@SuppressWarnings("serial")
public class AnswerVo extends BasePage implements java.io.Serializable{

    private String title;         //关键字
    private String reelStatus;     //状态
    private String userId;      //用户id
    private String year;      //年份
    private String reelId;      //卷id
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReelStatus() {
		return reelStatus;
	}
	public void setReelStatus(String reelStatus) {
		this.reelStatus = reelStatus;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
    
    
}
