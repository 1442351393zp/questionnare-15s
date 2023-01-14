package com.infoac.cas.dto;

@SuppressWarnings("serial")
public class SsoResult implements java.io.Serializable {
	private String result;		//返回结果
	private String rsltcode;    //返回码
	private String rsltmsg;		//返回结果说明
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getRsltcode() {
		return rsltcode;
	}
	public void setRsltcode(String rsltcode) {
		this.rsltcode = rsltcode;
	}
	public String getRsltmsg() {
		return rsltmsg;
	}
	public void setRsltmsg(String rsltmsg) {
		this.rsltmsg = rsltmsg;
	}
}
