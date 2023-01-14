package com.infoac.cas.dto;

import com.infoac.cas.entity.BasePage;

@SuppressWarnings("serial")
public class SystemLog extends BasePage implements java.io.Serializable{
	   //日志id
	private String  id;  
       //操作用户
    private String operateor;
       //操作描述
    private String operatetype;
       //操作时间
    private String operatedate;
   
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperateor() {
		return operateor;
	}

	public void setOperateor(String operateor) {
		this.operateor = operateor;
	}

	public String getOperatetype() {
		return operatetype;
	}

	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}

	

	public String getOperatedate() {
		return operatedate;
	}

	public void setOperatedate(String operatedate) {
		this.operatedate = operatedate;
	}
    
}
