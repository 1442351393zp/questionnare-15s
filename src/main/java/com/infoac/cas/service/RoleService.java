package com.infoac.cas.service;

import java.util.List;

import com.infoac.cas.dto.RoleDTO;

public interface RoleService {

	List<RoleDTO> findrolelist(RoleDTO role);

	void roleAdd(RoleDTO role);

	void roleupdate(RoleDTO role);

	void roledelete(List<String> rolelist);
    //用户对应的角色
	List<RoleDTO> selectuserRole(String userid);
   //删除角色
	void roledeletes(String roleid);

}
