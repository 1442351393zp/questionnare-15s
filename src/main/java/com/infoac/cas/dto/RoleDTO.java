package com.infoac.cas.dto;
/**
 * @author 
 * 角色实体类
 */
@SuppressWarnings("serial")

public class RoleDTO extends ModuleEntity implements java.io.Serializable{
	 //角色id
	private  String roleid ;
	//角色名称
    private String rolename;
    //角色编码
    private String code;
    //角色描述
    private String descpt;
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescpt() {
		return descpt;
	}
	public void setDescpt(String descpt) {
		this.descpt = descpt;
	}
    

}
