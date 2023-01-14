package com.infoac.cas.dto;

/**
 * @author 
 *组织机构实体类
 */
@SuppressWarnings("serial")
public class OrganizaDTO extends ModuleEntity implements java.io.Serializable{
	        //机构id
			private  String id ;
			//父id
			private  String pid ;
			//机构名称
		    private String orname;
		    //所在区域
		    private String orarea;
		    //机构代码
		    private String orcode;
		    //机构分类
		    private String orsort;
		    //排序码
		    private String orrank;
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getPid() {
				return pid;
			}
			public void setPid(String pid) {
				this.pid = pid;
			}
			public String getOrname() {
				return orname;
			}
			public void setOrname(String orname) {
				this.orname = orname;
			}
			public String getOrarea() {
				return orarea;
			}
			public void setOrarea(String orarea) {
				this.orarea = orarea;
			}
			public String getOrcode() {
				return orcode;
			}
			public void setOrcode(String orcode) {
				this.orcode = orcode;
			}
			public String getOrsort() {
				return orsort;
			}
			public void setOrsort(String orsort) {
				this.orsort = orsort;
			}
			public String getOrrank() {
				return orrank;
			}
			public void setOrrank(String orrank) {
				this.orrank = orrank;
			}
		    

}
