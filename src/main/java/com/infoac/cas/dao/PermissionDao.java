package com.infoac.cas.dao;

import java.util.List;

import com.infoac.cas.dto.PermissionDTO;
import com.infoac.cas.dto.PermissionVO;
import com.infoac.cas.dto.RolePermissionKey;

public interface PermissionDao {

	List<PermissionDTO> permissionDao(PermissionDTO permission);

	void updatePerm(PermissionDTO permissionDTO);

	void Addpermission(PermissionDTO permissionDTO);

	List<PermissionDTO> findChildPerm(String id);

	int deletByPermission(String id);

	List<PermissionVO> findPerms(PermissionVO permissionVO);

	List<PermissionVO> selectper(String roleid);

	void deleteRolePermission(String roleid);

	void insetrUserrole(RolePermissionKey rpk);

	PermissionDTO findPermission(String id);

}
