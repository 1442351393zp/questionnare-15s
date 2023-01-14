package com.infoac.cas.dto;

import java.util.List;

/*
 * 用户和机构关联
 */
public class UserOrganVO {
	//组织机构父id
	private  String pid ;
	//用户id
	private String userid;
	//机构id
	private String orgid;
	//用户昵称
	private String username;
	//组织机构名称
    private String orname;
    //选种状态
    private String checked;
    private List<UserOrganVO> children;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getOrname() {
		return orname;
	}
	public void setOrname(String orname) {
		this.orname = orname;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public List<UserOrganVO> getChildren() {
		return children;
	}
	public void setChildren(List<UserOrganVO> children) {
		this.children = children;
	}
	
	
	

}
