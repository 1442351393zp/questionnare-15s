package com.infoac.cas.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.infoac.cas.dto.PermissionDTO;
import com.infoac.cas.service.PermissionService;
import com.infoac.cas.util.Globals;

import net.sf.json.JSONArray;



/**
 * @author zp
 * 权限
 */
@CrossOrigin
@RestController
@RequestMapping("/premission")

public class PermissionController {
 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private  PermissionService  permissionService;
	/*
     * 菜单页
     */
    @RequestMapping(value = "/premission",produces= {"application/josn;charset=UTF-8"})
    public ModelAndView loginInfoac(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reel/menu");
		return mv;
	}
	/*
	 * 菜单列表的页面
	 */
	 @RequestMapping(value = "/perlist",produces= {"application/josn;charset=UTF-8"})
	 @ResponseBody
	 public String perlist(HttpServletRequest request,PermissionDTO permission) {
		JSONObject json = new JSONObject();
		 try {
			 List <PermissionDTO> permiss =  permissionService.permissionlist(permission);
			 JSONArray jsonArray = JSONArray.fromObject(permiss);
			 json.put("code", Globals.SUCCESSE);
		     json.put("msg",  Globals.MSG_CZ_SUCCESSE);
		     json.put("data", jsonArray);
		  }catch (Exception e) {
			  json.put("code", Globals.FIAL);
			  json.put("msg",  Globals.MSG_CZ_FAIL);
		 }
		  return json.toString();
		  
	 }
	 
	 /*
	  * 新增或修改一个子节点权限
	  * type[0:编辑 ; 1 新增子节点权限]
	  */
	 @RequestMapping(value = "/Addpermission",produces= {"application/josn;charset=UTF-8"})
	 @ResponseBody
	 public String Addpermission(HttpServletResponse resp,PermissionDTO permissionDTO ,int type) {
		 resp.addHeader("Access-Control-Allow-Origin", "*");//跨域测试
		 resp.addHeader("Access-Control-Allow-Method", "*"); //跨域测试
		 JSONObject json = new JSONObject();
		 try {
			 if(permissionDTO!=null) {
			    if(type==0) {
				   //编辑节点
				   permissionService.updatePerm(permissionDTO);
			    }else if(type ==1) {
				   //增加子节点权限
				  permissionService.Addpermission(permissionDTO); 
			   }
			 }
			json.put("code", "0");
			json.put("msg", "设置权限成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", "1");
			json.put("msg", "设置权限失败");
		}
		 
		 
		 return json.toString();
		 
	 }
	 /*
	  * 获取列表数据
	  */
	 @RequestMapping(value = "/findPermission",produces= {"application/json;charset=UTF-8"})
	 @ResponseBody
		public String findPermission(HttpServletRequest request, String id ) {
			JSONObject json = new JSONObject();
			PermissionDTO permission= permissionService.findPermission(id);
	    	if(permission != null) {
	    		json.put("code", 0);
	    		json.put("permission", permission);
	    	}else { 
	    		json.put("code", 1);
	    	}
	    	return json.toString();
	    }
	 /*
	  * 菜单删除
	  * 
	  */
	 @RequestMapping(value = "/deletper",produces= {"application/josn;charset=UTF-8"})
	 @ResponseBody
      public String deletper(HttpServletResponse resp,String id) {
		 JSONObject json = new JSONObject();
		 boolean flag = permissionService.deletepermisdion(id);
		 if(flag) {
			  json.put("code", "0");
			  json.put("msg", "删除成功");
		 }else {
			 json.put("code", "1");
			 json.put("msg", "删除失败 查看是否有子节点");
		 }
		 return json.toString();
		 
	 }

}
