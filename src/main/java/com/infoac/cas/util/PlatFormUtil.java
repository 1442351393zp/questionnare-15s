package com.infoac.cas.util;

import com.infoac.cas.dto.UserDTO;

import javax.servlet.http.HttpSession;

/**
 * 平台工具类
 * 
 * @author liuzhen 2014年7月10日 18:42:31
 * 
 */
public class PlatFormUtil {
	
	/**
	 * 获取当前session中管理员
	 * 
	 * @return
	 */
	public static final UserDTO getSessionUser() {
		HttpSession session = ContextHolderUtils.getSession();
		if(ClientManager.getInstance().getClient(session.getId()) != null) {
			return ClientManager.getInstance().getClient(session.getId()).getUser();
		}
		return null;
	}
	
	/**
	 * 获取当前session中用户
	 * 
	 * @return
	 */
//	public static final MemberEntity getSessionMember() {
//		HttpSession session = ContextHolderUtils.getSession();
//		if(ClientManager.getInstance().getClient(session.getId()) != null) {
//			Client client = ClientManager.getInstance().getClient("front" + session.getId());
//			if (client == null) {
//				return null;
//			}
//			MemberEntity member = client.getMember();
//			return member;
//		}
//		return null;
//	}
}
