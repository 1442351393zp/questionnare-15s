package com.infoac.cas.dto;


/**
 * @author zp
 *
 */
public class TemplateDTO {
  /*
   * 模板类型实体类
   */
	//模板类型id
	private String typeid;
	//模板类型父id
	private String typepid;
	//模板类型名称
    private String  typename;
    //排序
    private String typperank;
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getTypepid() {
		return typepid;
	}
	public void setTypepid(String typepid) {
		this.typepid = typepid;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getTypperank() {
		return typperank;
	}
	public void setTypperank(String typperank) {
		this.typperank = typperank;
	}
    
    
	


}
