package com.infoac.cas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoac.cas.dao.PermissionDao;
import com.infoac.cas.dto.PermissionDTO;
import com.infoac.cas.dto.PermissionVO;
import com.infoac.cas.dto.RolePermissionKey;
import com.infoac.cas.service.PermissionService;
import com.infoac.cas.util.StringUtil;

@Service
public class PermissionServiceimpl implements PermissionService {
	@Autowired
	private PermissionDao  permissionDao;

	@Override
	public List<PermissionDTO> permissionlist(PermissionDTO permission) {
		// TODO Auto-generated method stub
		return permissionDao.permissionDao(permission);
	}

	@Override
	public void updatePerm(PermissionDTO permissionDTO) {
		// TODO Auto-generated method stub
		permissionDao.updatePerm(permissionDTO);
	}

	@Override
	public void Addpermission(PermissionDTO permissionDTO) {
		// TODO Auto-generated method stub
		permissionDTO.setId(StringUtil.get32UUID());
		permissionDao.Addpermission(permissionDTO);
	}

	@Override
	public boolean deletepermisdion(String id) {
		List<PermissionDTO> childPer = permissionDao.findChildPerm(id);
		if(null != childPer && childPer.size()>0) {
		   return false;
		  
		} 
		if(permissionDao.deletByPermission(id)>0) {
			return true;
		}else {
			return false;	
		}
	}

	@Override
	public List<PermissionVO> findPerms(PermissionVO permissionVO) {
		// TODO Auto-generated method stub
		return permissionDao.findPerms(permissionVO);
	}

	@Override
	public List<PermissionVO> selectper(String roleid) {
		// TODO Auto-generated method stub
		return permissionDao.selectper(roleid);
	}
    /*
     * 
     * @see com.infoac.cas.service.PermissionService#deleteRolePermission(java.lang.String)
     * 清除角色对应的权限表数据
     */
	@Override
	public void deleteRolePermission(String roleid) {
		// TODO Auto-generated method stub
		 permissionDao.deleteRolePermission(roleid);
	}
    /*
     * (non-Javadoc)
     * @see com.infoac.cas.service.PermissionService#saveroleUser(com.infoac.cas.dto.RolePermissionKey)
     * 保存新角色或者修改角色对应的权限表数据
     */
	@Override
	public void saveroleUser(RolePermissionKey rpk) {
		// TODO Auto-generated method stub
		permissionDao.insetrUserrole(rpk);
	}

	@Override
	public PermissionDTO findPermission(String id) {
		// TODO Auto-generated method stub
		return permissionDao.findPermission(id);
	}

}
