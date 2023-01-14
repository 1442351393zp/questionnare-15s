package com.infoac.cas.service;

import com.infoac.cas.dto.*;
import com.infoac.cas.entity.UserVO;

import java.util.LinkedList;
import java.util.List;

public interface UserService {

	List<UserDTO> getAllUser();

	List<SelectUserDTO> getSelectUser(String reelId);

	void saveSelectUser(String reelId, String userId);
	
	List<UserDTO> finduserlist(UserVO uservo);
	
	Long findUserCount(UserVO uservo);
	
	List<UserDTO> findUser(String username, String password);
	
	void userAdd(UserDTO user);
	
	void userupdate(UserDTO user);
	
	void userupdelete(List<String> list);
    //单条删除用户信息
	void userdelete(String id);
	void userdeleteall();

	UserDTO userFind(String userid);
    //赋予用户新的权限时先清库
	void deleteUserRole(String userid);
	void deleteUserRoleall();
    //保存新用户或者修改用户对应的角色列表
	void saveroleUser(UserRoleKey urk);

	void addUser(UserDTO user);
	void updateuserismanage(UserDTO user);

	List<OrganVO> listAllDepartment(String parentId,String reelId,String isLimit);

	List<SelectDTO> getSelectUserName(String reelId);

    UserDTO userMessage(String username);

	/**
	 * 保存15所的用户数据
	 * @param userDTOS
	 */
	void addUserList(UserDTO userDTOS);

	/**
	 * 保存15所用户和角色的关系
	 * @param urkList
	 */
	void saveroleUserList(UserRoleKey urkList);

    UserDTO findUserByuid(String userid);
}
