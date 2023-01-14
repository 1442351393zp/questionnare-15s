package com.infoac.cas.web;

import com.infoac.cas.dto.SystemLog;
import com.infoac.cas.service.SystemLogService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
/*
 * 日志管理
 */
@Controller
@RestController
@RequestMapping("/systemLog")
public class SystemLogController {
     @Autowired
     private  SystemLogService  systemLogService; 
      /*
       * 日志列表的展示
       */
     @RequestMapping(value = "/log",produces= {"application/josn;charset=UTF-8"})
     @ResponseBody
     public String log(HttpServletRequest request ,SystemLog  systemLog,HttpServletResponse resp) {
    	 resp.addHeader("Access-Control-Allow-Origin", "*");//跨域测试
		 resp.addHeader("Access-Control-Allow-Method", "*"); //跨域测试
    	 JSONObject json = new JSONObject();
         try {
             request.setCharacterEncoding("utf-8");
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }
         List<SystemLog> Loglist = systemLogService.findbyLoglist(systemLog);
         Long count = systemLogService.findbyLog(systemLog);
         JSONArray jsonArray = JSONArray.fromObject(Loglist);
         json.put("code", 0);
         json.put("msg", "");
         json.put("count", count);
         json.put("data", jsonArray);
         return json.toString();
     }


    /**
     * @Author: PengSenjie
     * @description: 日志页
     * @Date: Created in 20-2-27 上午10:14
     */
    @RequestMapping(value = "/logjsp",produces= {"application/josn;charset=UTF-8"})
    public ModelAndView logjsp(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("reel/log");
        return mv;
    }




}
