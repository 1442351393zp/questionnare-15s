package com.infoac.cas.web;


import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.infoac.cas.dto.PermissionVO;
import com.infoac.cas.dto.RoleDTO;
import com.infoac.cas.dto.RolePermissionKey;
import com.infoac.cas.dto.UserDTO;
import com.infoac.cas.service.PermissionService;
import com.infoac.cas.service.RoleService;
import com.infoac.cas.util.Globals;
import com.infoac.cas.util.TreeBulider;

/**
 * @author 
 * 角色
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/role")
public class RoleController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RoleService roleService;
	@Autowired
	private  PermissionService  permissionService;
	/*
     * 用户页
     */
    @RequestMapping(value = "/role",produces= {"application/josn;charset=UTF-8"})
    public ModelAndView loginInfoac(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("reel/role");
		return mv;
	}
	/*
	 * 角色列表的展示
	 * 
	 */
	 @RequestMapping(value = "/rolelist",produces= {"application/josn;charset=UTF-8"})
	 @ResponseBody
	 public String rolelist(HttpServletRequest request,RoleDTO role)throws Exception {
		 JSONObject json = new JSONObject();
		 try {
			 List<RoleDTO> rolelist =  roleService.findrolelist(role);
			 JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(rolelist));
			 json.put("code", Globals.SUCCESSE);
		     json.put("msg",  Globals.MSG_CZ_SUCCESSE);
		     json.put("data", jsonArray);
			
		  } catch (Exception e) {
			 json.put("code", Globals.FIAL);
			 json.put("msg",  Globals.MSG_CZ_FAIL);
		 }
		return json.toString();
		 
	 }
	 
    /*
     *  角色的添加 
     * 
     */
	 @RequestMapping(value = "/roleAdd",produces= {"application/josn;charset=UTF-8"})
	 @ResponseBody
	 public String roleAdd(HttpServletRequest request,RoleDTO role)throws Exception {
		 JSONObject json = new JSONObject();
		 try {
			    roleService.roleAdd(role);
			    json.put("code", Globals.SUCCESSE);
				json.put("msg", Globals.MSG_CZ_SUCCESSE);
				
		} catch (Exception e) {
			// TODO: handle exception
			json.put("msg", Globals.MSG_CZ_FAIL);
			json.put("code", Globals.FIAL);
		}
		     return json.toString();
		 
	 }
	 /*
	  * 角色的修改
	  */
	 @RequestMapping(value = "/roleupdate",produces= {"application/josn;charset=UTF-8"})
	 @ResponseBody
	 public String roleupdate(HttpServletRequest request,RoleDTO role)throws Exception{
		 JSONObject json = new JSONObject();
		  try {
			 roleService.roleupdate(role);
			 json.put("code", Globals.SUCCESSE);
			 json.put("msg", Globals.MSG_XG_SUCCESSE);
		   } catch (Exception e) {
			// TODO: handle exception
			  json.put("code", Globals.FIAL);
			  json.put("msg", Globals.MSG_XG_FAIL);
		   }
		 
		return json.toString();
		 
	 }
	 /*
	  * 角色的删除
	  */
	 @RequestMapping(value = "/roledelete",produces= {"application/josn;charset=UTF-8"})
	 @ResponseBody
	 public String roledelete(HttpServletRequest request,String[] roleids) throws Exception{
		 JSONObject json = new JSONObject();
		 try {
			 for (String roleid : roleids){
				 if(roleid.equals("4")) {
					 json.put("code", Globals.FIAL);
					 json.put("msg", "系统管理员不可删除");
				  }else {
					  roleService.roledeletes(roleid);
					  json.put("code", Globals.SUCCESSE);
					  json.put("msg", Globals.MSG_SC_SUCCESSE); 
				  }
			   }
		} catch (Exception e) {
			// TODO: handle exception
			json.put("code", Globals.FIAL);
			json.put("msg", Globals.MSG_SC_FAIL);
		}
		 
		return json.toString();
		 
	 }
	 /*
	  * 查询权限数数据
	  * 
	  */
	 @RequestMapping(value = "/findPerms",produces= {"application/josn;charset=UTF-8"})
	 @ResponseBody
	 public String findPerms(PermissionVO permissionVO ,String roleid) {
		 JSONObject json = new JSONObject();
		 PermissionVO vo =null;
		 PermissionVO vo2 =null;
		 PermissionVO vo3 =null;
		 PermissionVO ts = null;
		 try {
			 //查询所有的
			 List <PermissionVO> perVO = permissionService.findPerms(permissionVO);//获取所有数据，未转化成数结构
			 //根据id查询的
			  List <PermissionVO> perlist = permissionService.selectper(roleid);//获取所有数据，未转化成数结构
			 //展示选中的数据
			 for (int i = 0; i < perVO.size(); i++) {
				 vo = perVO.get(i);
				  for (int j = 0; j < perlist.size(); j++) {
					  ts = perlist.get(j);
					if(vo.getId().equals(ts.getId())){
						vo.setChecked("true");
						break;
					  }else {
						vo.setChecked("false");
					}	
				}
			  }
			 

			   //数性结构数据的展示
			   List<PermissionVO>  trperVOS = TreeBulider.buildByRecursive(perVO);
			   JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(trperVOS));
			    json.put("code", "0");
			    json.put("msg", "查询成功");
			    json.put("data", jsonArray);
		   }catch (Exception e) {
			 e.printStackTrace();
			 json.put("code", "1");
			 json.put("msg", "查询失败");
		 }
		 return json.toString();
	 }
	 /*
	  * 根据角色修改权限并保存
	  */
	 @RequestMapping(value = "/updaterolePerms",produces= {"application/josn;charset=UTF-8"})
	 @ResponseBody
	 public String updaterolePerms(String roleid ,String perid) {
	    JSONObject json = new JSONObject(); 
	       //保存角色的赋予的权限是应该先清库
	    try {
	    	permissionService.deleteRolePermission(roleid);
	    	//保存用户的角色
	    	String [] perids = perid.split(",");
	    	RolePermissionKey rpk =  new RolePermissionKey();
	    	for (String peridss : perids) {
	    		rpk.setPermitid(peridss);
	    		rpk.setRoleid(roleid);
	    		//保存新角色对应的权限列表或者保存修改角色对应新的权限列表
	    		permissionService.saveroleUser(rpk);
	    	}
	    	json.put("code", 0);
	    	json.put("msg", "修改成功");
	    	
		} catch (Exception e) {
			json.put("code", "1");
		    json.put("msg", "修改失败");
		}
		 
		    return json.toString();
	 }
	 
	 /*
	  * 恢复权限
	  */
	 @RequestMapping(value = "/deleterolePerms",produces= {"application/josn;charset=UTF-8"})
	 @ResponseBody
	 public String deleterolePerms(String roleid) {
		 JSONObject json = new JSONObject();
		 if(roleid!=null&&roleid.length()>0) {
		     permissionService.deleteRolePermission(roleid);
			 json.put("code", 0);
	         json.put("msg", "恢复成功");
		 }else {
			 json.put("code", "1");
		     json.put("msg", "恢复失败");
		 }
		return json.toString();
	 }
	  
}
