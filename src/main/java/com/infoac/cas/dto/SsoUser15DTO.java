package com.infoac.cas.dto;

@SuppressWarnings("serial")
public class SsoUser15DTO implements java.io.Serializable {
	private String code;		//返回码
	private String data;        //返回结果

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
