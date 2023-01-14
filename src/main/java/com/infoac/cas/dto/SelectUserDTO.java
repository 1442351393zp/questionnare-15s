package com.infoac.cas.dto;
@SuppressWarnings("serial")
public class SelectUserDTO extends ModuleEntity implements java.io.Serializable {

	//主键ID
	private  String id ;
	//卷ID
	private  String reelId;
	//用户ID
	private  String userId;
	//用户名
    private String userName;
    private String type = "person";

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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
