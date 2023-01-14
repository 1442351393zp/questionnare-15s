package com.infoac.cas.entity;
@SuppressWarnings("serial")
public class BasePage implements java.io.Serializable {
    //页数
    private int page;
    //每页的条数
    private int recPerPage;
    //所有的数据总数
    private long  recTotal;
    
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRecPerPage() {
		return recPerPage;
	}
	public void setRecPerPage(int recPerPage) {
		this.recPerPage = recPerPage;
	}
	public long getRecTotal() {
		return recTotal;
	}
	public void setRecTotal(long recTotal) {
		this.recTotal = recTotal;
	}
	
	
	
}
