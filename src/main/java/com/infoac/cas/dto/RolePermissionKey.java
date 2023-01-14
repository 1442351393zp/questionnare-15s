package com.infoac.cas.dto;
/*
 * 角色和权限的关联实体类
 */

public class RolePermissionKey {
	//菜单id
	private String permitid;
	//角色id
	private String roleid;
	public String getPermitid() {
		return permitid;
	}
	public void setPermitid(String permitid) {
		this.permitid = permitid;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	
	

}
