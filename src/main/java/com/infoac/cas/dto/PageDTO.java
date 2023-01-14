package com.infoac.cas.dto;

import java.util.List;

public class PageDTO {
	
	private String pageId;
	private String createTime;
	private String rId;
	private int pagenum;
	private List<SubjectDTO> subjectList;

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public List<SubjectDTO> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<SubjectDTO> subjectList) {
		this.subjectList = subjectList;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getrId() {
		return rId;
	}
	public void setrId(String rId) {
		this.rId = rId;
	}
}
