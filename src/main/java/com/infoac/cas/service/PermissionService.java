package com.infoac.cas.service;

import java.util.List;

import com.infoac.cas.dto.PermissionDTO;
import com.infoac.cas.dto.PermissionVO;
import com.infoac.cas.dto.RolePermissionKey;

public interface PermissionService {

	List<PermissionDTO> permissionlist(PermissionDTO permission);

	void updatePerm(PermissionDTO permissionDTO);

	void Addpermission(PermissionDTO permissionDTO);

	boolean deletepermisdion(String id);

	List<PermissionVO> findPerms(PermissionVO permissionVO);

	List<PermissionVO> selectper(String roleid);

	void deleteRolePermission(String roleid);

	void saveroleUser(RolePermissionKey rpk);

	PermissionDTO findPermission(String id);

}
