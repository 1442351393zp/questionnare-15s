package com.infoac.cas.entity;

import com.infoac.cas.dto.UserDTO;

import java.util.Set;

/**
 * 在线用户对象
 * 
 * @author 
 * @date 2013-9-28
 * @version 1.0
 */
public class Client implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private UserDTO user;

	private Set<String> privurl;

	/**用户IP*/
	private java.lang.String ip;
	/**登录时间*/
	private java.util.Date logindatetime;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public java.util.Date getLogindatetime() {
		return logindatetime;
	}

	public void setLogindatetime(java.util.Date logindatetime) {
		this.logindatetime = logindatetime;
	}

	public Set<String> getPrivurl() {
		return privurl;
	}

	public void setPrivurl(Set<String> privurl) {
		this.privurl = privurl;
	}
}
