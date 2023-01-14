package com.infoac.cas.entity;

@SuppressWarnings("serial")
public class MemberBean  implements java.io.Serializable{
	/**结束时间*/
	private String endDate;
	/**登陆失败次数*/
	private int failedLoginCount;
	private java.lang.String orderId;
	private java.lang.String dn;
	private java.lang.String  account;//用户账号
	private java.lang.String fullname;//用户昵称
	private int type;   //0 删除 1修改 2 新增
	/** 密码 */
	private java.lang.String userid;  
	private java.lang.String password;
	private java.lang.Integer isManager;
	private java.lang.String tel;
	/** 邮箱 */
	private java.lang.String userEmail;
	/** 部门id*/
	private java.lang.String organid;
	private  int isDelete; // 是否禁用（0：启用；1：禁用)
	private java.lang.String sn;
	private java.lang.String ca;
	private java.lang.String ip;
	/** 性别 */
	private java.lang.String sex;
	private java.lang.String timestamp;
	private java.lang.String tokenId;
	private java.lang.String mobile;
	private java.lang.String spId;
	private java.lang.String editPwdTime;


	//todo: 扩展属性,后续需求.map类型
	//private java.lang.Integer extAttribute;
	

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getFailedLoginCount() {
		return failedLoginCount;
	}

	public void setFailedLoginCount(int failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	public java.lang.String getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
	}

	public java.lang.String getDn() {
		return dn;
	}

	public void setDn(java.lang.String dn) {
		this.dn = dn;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public java.lang.String getUserid() {
		return userid;
	}

	public void setUserid(java.lang.String userid) {
		this.userid = userid;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	

	public java.lang.Integer getIsManager() {
		return isManager;
	}

	public void setIsManager(java.lang.Integer isManager) {
		this.isManager = isManager;
	}

	public java.lang.String getTel() {
		return tel;
	}

	public void setTel(java.lang.String tel) {
		this.tel = tel;
	}

	public java.lang.String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(java.lang.String userEmail) {
		this.userEmail = userEmail;
	}

	public java.lang.String getSn() {
		return sn;
	}

	public void setSn(java.lang.String sn) {
		this.sn = sn;
	}

	public java.lang.String getCa() {
		return ca;
	}

	public void setCa(java.lang.String ca) {
		this.ca = ca;
	}

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	

	public java.lang.String getSex() {
		return sex;
	}

	public void setSex(java.lang.String sex) {
		this.sex = sex;
	}

	public java.lang.String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(java.lang.String timestamp) {
		this.timestamp = timestamp;
	}

	public java.lang.String getTokenId() {
		return tokenId;
	}

	public void setTokenId(java.lang.String tokenId) {
		this.tokenId = tokenId;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public java.lang.String getSpId() {
		return spId;
	}

	public void setSpId(java.lang.String spId) {
		this.spId = spId;
	}

	public java.lang.String getEditPwdTime() {
		return editPwdTime;
	}

	public void setEditPwdTime(java.lang.String editPwdTime) {
		this.editPwdTime = editPwdTime;
	}



	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public java.lang.String getOrganid() {
		return organid;
	}

	public void setOrganid(java.lang.String organid) {
		this.organid = organid;
	}


}
