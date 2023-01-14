package com.infoac.cas.dao;

import com.infoac.cas.dto.*;
import com.infoac.cas.entity.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedList;
import java.util.List;

public interface UserDao {

	void delSelectUser(@Param("reelId")String reelId);

	void saveSelectUser(List<SelectUserDTO> list);
	void saveSelectUser2(@Param("id")String id,@Param("rid")String rid, @Param("uid")String uid, @Param("username")String username);

	List<SelectUserDTO> getSelectUser(String getrId);

	List<UserDTO> getAllUser();
	
	Long findUserCount(UserVO uservo);

	List<UserDTO> findUser(@Param("username")String username, @Param("password")String password);
	
	List<UserDTO> finduserlist(@Param("uservo")UserVO uservo);
	
	void insertuser(UserDTO users);
	
	void updateuser(UserDTO user);
	
	void deleteusers(List<String> list);

	void deleteuser(String id);
	void userdeleteall();

	UserDTO userFind(String userid);

	void deleteUserRole(String userid);
	void deleteUserRoleall();

	void saveroleUser(UserRoleKey urk);

	List<OrganVO> findForList(String pid);

    List<OrganVO> findBydeptid(String deptid);


    List<SelectDTO> getSelectUserName(String reelId);

    void updateuserismanage(UserDTO users);

    String getUserName(@Param("id")String id);

    UserDTO userMessage(String username);

    void addUserList(UserDTO list);

	void saveroleUserList(UserRoleKey list);

    UserDTO findUserByuid(String userid);
}
