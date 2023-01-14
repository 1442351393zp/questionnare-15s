package com.infoac.cas.dto;

@SuppressWarnings("serial")
public class  TokenDTO implements java.io.Serializable {
	private String access_token;		//微服务access_token
	private String token_type;    //token类型
	private Long expires_in;		//有效时间（单位为秒，默认有效期为604800秒，即7天）
	private String scope;		//申请权限的范围
	private Long expriedIn;    //有效时间（单位为秒，默认有效期为7200秒，）
	private String error;		//
	private String error_description;		//
    private String code; //15所判断
	private String visit_token; //15所token
	public Long getExpriedIn() {
		return expriedIn;
	}

	public void setExpriedIn(Long expriedIn) {
		this.expriedIn = expriedIn;
	}

	public  String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public Long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getError_description() {
		return error_description;
	}
	public void setError_description(String error_description) {
		this.error_description = error_description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVisit_token() {
		return visit_token;
	}

	public void setVisit_token(String visit_token) {
		this.visit_token = visit_token;
	}
}
