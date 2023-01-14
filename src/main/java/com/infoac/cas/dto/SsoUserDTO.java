package com.infoac.cas.dto;

@SuppressWarnings("serial")
public class SsoUserDTO implements java.io.Serializable {
	private String result;		//返回结果
	private int rsltcode;    //返回码
	private String rsltmsg;		//返回结果说明
//	private String account;     //账号
//	private String attrMap;    //扩展属性
//	private String ca;    //目录服务认证授权中心
//	private boolean delete;    //是否禁用（0：启用； 1：禁用）
//	private String dn;    //目录服务辨别名称
//	private Date editPwdTime;    //密码修改时间
//	private String enddate;    //用户有效期截止时间
//	private String failedLoginCount;    //失败登录统计
	private String fullname;    //全名
//	private String ip;    //ip地址
//	private int isManager;    //管理员类型（0：普通用户； 1：安全审计员； 2：安全管理员； 4：系统管理员）
//	private String loginMode;    //登录模式（允许通过用户名口令时为100，允许通过证书模式时为010，允许通过口令+证书模式时为110）
//	private int manageLevel;    //职务
	private String mobile;    //手机号
//	private String nodeType;    //节点类型
//	private int orderId;    //顺序号
//	private String orgName;    //部门名称
//	private String organId;    //部门Id
//	private String password;    //密码
//	private String secLevel;    //密级（-1：非涉密； 1：一般； 2：重要； 3：核心）
//	private String sex;    //性别（0：男； 1：女）
//	private String sn;    //动态口令卡序列号
//	private String spid;    //动态口令卡型号
//	private String startdate;    //用户有效期开始时间
//	private Long timestamp;    //最后一次操作的时间戳
//	private String tokenId;    //动态口令的种子
//	private int type;    //最后一次操作的类型（0：删除； 1：修改； 2：新增）
	private String userid;    //用户ID
	private String useremail;    //邮箱
//	private String useruuid;    //用户ID
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
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
//	public String getAccount() {
//		return account;
//	}
//	public void setAccount(String account) {
//		this.account = account;
//	}
//	public String getAttrMap() {
//		return attrMap;
//	}
//	public void setAttrMap(String attrMap) {
//		this.attrMap = attrMap;
//	}
//	public String getCa() {
//		return ca;
//	}
//	public void setCa(String ca) {
//		this.ca = ca;
//	}
//	public boolean isDelete() {
//		return delete;
//	}
//	public void setDelete(boolean delete) {
//		this.delete = delete;
//	}
//	public String getDn() {
//		return dn;
//	}
//	public void setDn(String dn) {
//		this.dn = dn;
//	}
//	public Date getEditPwdTime() {
//		return editPwdTime;
//	}
//	public void setEditPwdTime(Date editPwdTime) {
//		this.editPwdTime = editPwdTime;
//	}
//	public String getEnddate() {
//		return enddate;
//	}
//	public void setEnddate(String enddate) {
//		this.enddate = enddate;
//	}
//	public String getFailedLoginCount() {
//		return failedLoginCount;
//	}
//	public void setFailedLoginCount(String failedLoginCount) {
//		this.failedLoginCount = failedLoginCount;
//	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
//	public String getIp() {
//		return ip;
//	}
//	public void setIp(String ip) {
//		this.ip = ip;
//	}
//	public int getIsManager() {
//		return isManager;
//	}
//	public void setIsManager(int isManager) {
//		this.isManager = isManager;
//	}
//	public String getLoginMode() {
//		return loginMode;
//	}
//	public void setLoginMode(String loginMode) {
//		this.loginMode = loginMode;
//	}
//	public int getManageLevel() {
//		return manageLevel;
//	}
//	public void setManageLevel(int manageLevel) {
//		this.manageLevel = manageLevel;
//	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
//	public String getNodeType() {
//		return nodeType;
//	}
//	public void setNodeType(String nodeType) {
//		this.nodeType = nodeType;
//	}
//	public String getOrgName() {
//		return orgName;
//	}
//	public void setOrgName(String orgName) {
//		this.orgName = orgName;
//	}
//	public String getOrganId() {
//		return organId;
//	}
//	public void setOrganId(String organId) {
//		this.organId = organId;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public String getSecLevel() {
//		return secLevel;
//	}
//	public void setSecLevel(String secLevel) {
//		this.secLevel = secLevel;
//	}
//	public String getSex() {
//		return sex;
//	}
//	public void setSex(String sex) {
//		this.sex = sex;
//	}
//	public String getSn() {
//		return sn;
//	}
//	public void setSn(String sn) {
//		this.sn = sn;
//	}
//	public String getSpid() {
//		return spid;
//	}
//	public void setSpid(String spid) {
//		this.spid = spid;
//	}
//	public String getStartdate() {
//		return startdate;
//	}
//	public void setStartdate(String startdate) {
//		this.startdate = startdate;
//	}
//	public Long getTimestamp() {
//		return timestamp;
//	}
//	public void setTimestamp(Long timestamp) {
//		this.timestamp = timestamp;
//	}
//	public String getTokenId() {
//		return tokenId;
//	}
//	public void setTokenId(String tokenId) {
//		this.tokenId = tokenId;
//	}
//	public int getType() {
//		return type;
//	}
//	public void setType(int type) {
//		this.type = type;
//	}
//	public int getOrderId() {
//		return orderId;
//	}
//	public void setOrderId(int orderId) {
//		this.orderId = orderId;
//	}
//	public String getUseruuid() {
//	    return useruuid;
//  }
//  public void setUseruuid(String useruuid) {
//	    this.useruuid = useruuid;
//  }
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	
}
