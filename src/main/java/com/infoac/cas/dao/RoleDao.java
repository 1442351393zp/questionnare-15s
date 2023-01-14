package com.infoac.cas.dao;

import java.util.List;

import com.infoac.cas.dto.RoleDTO;

public interface RoleDao {

	List<RoleDTO> findrolelist(RoleDTO role);

	void insertrole(RoleDTO roles);

	void updaterole(RoleDTO role);

	void deletroles(List<String> rolelist);

	List<RoleDTO> selectuserRole(String userid);

	void deleterole(String roleid);

}
