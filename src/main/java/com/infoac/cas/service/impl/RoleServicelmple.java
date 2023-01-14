package com.infoac.cas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoac.cas.dao.RoleDao;
import com.infoac.cas.dto.RoleDTO;
import com.infoac.cas.service.RoleService;
import com.infoac.cas.util.StringUtil;

@Service
public class RoleServicelmple implements RoleService {
	@Autowired
	private RoleDao roleDao;

	@Override
	public List<RoleDTO> findrolelist(RoleDTO role) {
		// TODO Auto-generated method stub
		return roleDao.findrolelist(role);
	}

	@Override
	public void roleAdd(RoleDTO role) {
		RoleDTO roles = new RoleDTO();
		roles.setRoleid(StringUtil.get32UUID()); 
		roles.setRolename(role.getRolename());
		roles.setCode(role.getCode());
		roles.setDescpt(role.getDescpt());
		roleDao.insertrole(roles);
	}

	@Override
	public void roleupdate(RoleDTO role) {
		// TODO Auto-generated method stub
		roleDao.updaterole(role);
	}

	@Override
	public void roledelete(List<String> rolelist) {
		// TODO Auto-generated method stub
		roleDao.deletroles(rolelist);
		
	}
    /*
     * (non-Javadoc)
     * @see com.infoac.cas.service.RoleService#selectuserRole(java.lang.String)
     * 用户对应的角色
     */
	@Override
	public List<RoleDTO> selectuserRole(String userid) {
		// TODO Auto-generated method stub
		return roleDao.selectuserRole(userid);
	}

	@Override
	public void roledeletes(String roleid) {
		// TODO Auto-generated method stub
		roleDao.deleterole(roleid);
	}

}
