package com.infoac.cas.dto;

import java.util.List;

@SuppressWarnings("serial")
public class PushResult implements java.io.Serializable {

	private  String code;    //返回码
	private  String msg;		//返回结果说明
	private  TokenDTO result; //返回参数


	public TokenDTO getResult() {
		return result;
	}

	public void setResult(TokenDTO result) {
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
