package com.infoac.cas.entity;

import java.util.List;

@SuppressWarnings("serial")
public class MemberResult implements java.io.Serializable {
	private int rsltcode;	   //返回码
	private String rsltmsg;    //返回结果说明
	private Long timestamp;		//时间戳有效时间（单位为秒，默认有效期为604800秒，即7天）
	private String scope;		//申请权限的范围
	//private List org;		//
	private List<MemberBean> user;		//用户变更数据
	private List<ReDepartment> org;
	
	public int getRsltcode() {
		return rsltcode;
	}
	public void setRsltcode(int rsltcode) {
		this.rsltcode = rsltcode;
	}
	public String getRsltmsg() {
		return rsltmsg;
	}
	public void setRsltmsg(String rsltmsg) {
		this.rsltmsg = rsltmsg;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public List<MemberBean> getUser() {
		return user;
	}
	public void setUser(List<MemberBean> user) {
		this.user = user;
	}
	public List<ReDepartment> getOrg() {
		return org;
	}
	public void setOrg(List<ReDepartment> org) {
		this.org = org;
	}
	

}
