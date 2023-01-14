package com.infoac.cas.entity;

@SuppressWarnings("serial")
public class UserVO extends BasePage implements java.io.Serializable{
	    //用户ID
		private  String id ;
		//用户名
	    private String username;
	    //密码
	    private String password;
	    //呢称
	    private String nickname;
	    //性别
	    private String sex;
	    //邮件
	    private String email;
	    //电话
	    private String phone;
	    //描述
	    private String describe;
	   //验证码
	    private String randCode;
	    private  int ban; // 是否禁用（0：启用；1：禁用)
	    
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getDescribe() {
			return describe;
		}
		public void setDescribe(String describe) {
			this.describe = describe;
		}
		public String getRandCode() {
			return randCode;
		}
		public void setRandCode(String randCode) {
			this.randCode = randCode;
		}

	  public int getBan() {
		return ban;
	  }

	  public void setBan(int ban) {
		this.ban = ban;
	  }
}
