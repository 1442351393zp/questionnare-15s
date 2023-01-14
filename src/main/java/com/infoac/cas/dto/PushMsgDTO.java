package com.infoac.cas.dto;


import java.util.List;

/**
 * 统计答案
 * @author 
 * @version 1.0
 */
public class PushMsgDTO {
    
	private String rid;
	// private String[] userId;
    private List<String> username;
	private List<String> userId;

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

//	public String[] getUserId() {
//		return userId;
//	}
//
//	public void setUserId(String[] userId) {
//		this.userId = userId;
//	}userId


	public List<String> getUsername() {
		return username;
	}

	public void setUsername(List<String> username) {
		this.username = username;
	}

	public List<String> getUserId() {
		return userId;
	}

	public void setUserId(List<String> userId) {
		this.userId = userId;
	}
}
