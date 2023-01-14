package com.infoac.cas.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.infoac.cas.entity.Client;
import com.infoac.cas.util.ClientManager;
import com.infoac.cas.util.ContextHolderUtils;
/**
 * 
* 类名称：登录过滤
* 创建时间：2020年2月18日
* 
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
		if(path.matches(".*/((login)|(template)|(newStatis)|(reel)|(question)|(userFifteenSuo)|(answer)).*")){
			return true;
		}else{
			HttpSession session = ContextHolderUtils.getSession();
			Client client = ClientManager.getInstance().getClient(session.getId());
			if(client != null){
				return true;
			}else{
				//登陆过滤
				response.sendRedirect(request.getContextPath()+"/login/login");
				session.setAttribute("flag", "1");
				return false;		
			}
		}
		
	}
	
}
