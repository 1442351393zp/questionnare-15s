package com.infoac.cas.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.infoac.cas.dto.UserDTO;
import com.infoac.cas.entity.Client;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
* @ClassName: ContextHolderUtils 
* @Description: (上下文工具类) 
* @author   
* @date 2012-12-15 下午11:27:39 
*
 */
public class ContextHolderUtils {
	/**
	 * SpringMvc下获取request
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;

	}
	/**
	 * SpringMvc下获取session
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;

	}

	public static UserDTO getLoginUser(){
		Client clinet = ClientManager.getInstance().getClient(getSession().getId());
		UserDTO user = clinet.getUser();

		return user;
	}

}
