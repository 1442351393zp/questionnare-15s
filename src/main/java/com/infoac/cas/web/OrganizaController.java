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
import com.infoac.cas.dto.OrganizaDTO;
import com.infoac.cas.dto.RoleDTO;
import com.infoac.cas.service.OrganizaService;
import com.infoac.cas.util.Globals;

/**
 * @author 
 * 组织机构
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/organiza")
public class OrganizaController {
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 @Autowired
	 private  OrganizaService organizaService;
	 /*
	  * 组织机构的页面
	  */
	 @RequestMapping(value = "/organiza",produces= {"application/josn;charset=UTF-8"})
	 public ModelAndView loginInfoac(HttpServletRequest request,HttpServletResponse response) {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("reel/institution");
			return mv;
		}
	 /*
	  * 组织机构的列表展示
	  */
	 @RequestMapping(value = "/organizalist",produces= {"application/josn;charset=UTF-8"})
	 @ResponseBody
	 public String organizalist(HttpServletRequest request,OrganizaDTO organiza)throws Exception {
		 JSONObject json = new JSONObject();
		
		 List<OrganizaDTO> organizalist =organizaService.findrolelist(organiza);
		  try {
			  JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(organizalist));
				json.put("code", Globals.SUCCESSE);
				json.put("msg", Globals.MSG_CZ_SUCCESSE);
				json.put("data", jsonArray);
		    } catch (Exception e) {
			   // TODO: handle exception
				json.put("msg", Globals.MSG_CZ_FAIL);
				json.put("code", Globals.FIAL);
		}
		 return json.toString();
		 
	 }
	 
	 

	 /*
	  * 组织机构的添加和修改
	  */
	 @RequestMapping(value = "/organizaAdd",produces= {"application/josn;charset=UTF-8"})
	 @ResponseBody
	 public String organizaAdd(HttpServletRequest request,OrganizaDTO organiza,int type)throws Exception {
		 JSONObject json = new JSONObject();
		 try {
			 if(organiza!=null) {
				    if(type==0) { 
					   //编辑节点
				    	organizaService.organizaupdate(organiza);
				       }else if(type ==1) {
					     //增加子节点权限
					    organizaService.organizaAdd(organiza); 
				     }
				 }
				 json.put("code", "0");
				 json.put("msg", "设置机构成功");
			
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		 
		 return json.toString();
		 
	 }
	 /*
	  * 机构列表的删除
	  */
	 @RequestMapping(value = "/organizadelete",produces= {"application/josn;charset=UTF-8"})
	 @ResponseBody
	 public String organizadelete(HttpServletRequest request,String id) throws Exception {
		 JSONObject json = new JSONObject();
		 
			boolean flag = organizaService.organizadelete(id);
			if(flag) {
				json.put("code", Globals.SUCCESSE);
				json.put("msg", Globals.MSG_SC_SUCCESSE);
			}else {
				json.put("code", Globals.FIAL);
				json.put("msg", Globals.MSG_SC_FAIL);
			 }
			
		 return json.toString();
		 
	 }
}
