package com.infoac.cas.dto;
@SuppressWarnings("serial")
public class PermissionDTO extends ModuleEntity implements java.io.Serializable{
	    //权限id
		private String id;
		//菜单名称
		private String name;
		//父菜单id
		private String  pid;
		//菜单排序
		private String zindex;
		//描述
	    private String descpt;
	    //菜单编号
	    private String code;
	    //菜单url
	    private String path;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPid() {
			return pid;
		}
		public void setPid(String pid) {
			this.pid = pid;
		}
		public String getZindex() {
			return zindex;
		}
		public void setZindex(String zindex) {
			this.zindex = zindex;
		}
		public String getDescpt() {
			return descpt;
		}
		public void setDescpt(String descpt) {
			this.descpt = descpt;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
	    
}
