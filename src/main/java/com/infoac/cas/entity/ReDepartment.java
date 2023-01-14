package com.infoac.cas.entity;


/**
 * @author zp
 * 获取部门列表接口返回实体类
 *
 */
public class ReDepartment {
	private String code;//部门简称
	private String  dn ;//目录服务辨别名称
	private String fatherId; //上级部门id
	private  int  isDelete; //是否禁用
	private  int orderId ;// 顺序号
	private String  organId;// 部门id
	private String organName; //部门名称
	private String orguuId;//部门id
	private String p;// 路劲
	private int type;   //0 删除 1修改 2 新增
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public String getFatherId() {
		return fatherId;
	}
	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getOrguuId() {
		return orguuId;
	}
	public void setOrguuId(String orguuId) {
		this.orguuId = orguuId;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	

}
