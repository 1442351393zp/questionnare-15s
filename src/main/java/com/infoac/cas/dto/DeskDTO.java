package com.infoac.cas.dto;

/**
 * 用戶未查看問卷數
 */
public class DeskDTO {

	private String access_token ;
	private String client_id ;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
