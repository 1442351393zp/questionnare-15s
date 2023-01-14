package com.infoac.cas.dto;

import java.util.List;

public class PermissionVO {
	    //权限id
		private String id;
		//菜单名称
		private String name;
		//父菜单id
		private String  pid;
		//描述
	    private String descpt;
	    //菜单排序
		private String zindex;
	    //菜单编号
	    private String code;
	    //菜单url
	    private String path;
	    //选种状态
	    private String checked;
	    //所属层级
	    private String tier;
	    
	    private List<PermissionVO> children;
	    
		public List<PermissionVO> getChildren() {
			return children;
		}
		public void setChildren(List<PermissionVO> children) {
			this.children = children;
		}
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
		public String getTier() {
			return tier;
		}
		public void setTier(String tier) {
			this.tier = tier;
		}
		public String getChecked() {
			return checked;
		}
		public void setChecked(String checked) {
			this.checked = checked;
		}
		public String getZindex() {
			return zindex;
		}
		public void setZindex(String zindex) {
			this.zindex = zindex;
		}
		
		
	    

}
