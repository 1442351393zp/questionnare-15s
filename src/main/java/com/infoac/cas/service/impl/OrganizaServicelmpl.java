package com.infoac.cas.service.impl;

import com.infoac.cas.dao.OrganizaDao;
import com.infoac.cas.dto.OrganVO;
import com.infoac.cas.dto.OrganizaDTO;
import com.infoac.cas.dto.UserOrganKey;
import com.infoac.cas.service.OrganizaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class OrganizaServicelmpl implements OrganizaService {
	@Autowired
	private OrganizaDao organizaDao;

	@Override
	public List<OrganizaDTO> findrolelist(OrganizaDTO organiza) {
		// TODO Auto-generated method stub
		return organizaDao.findrolelist(organiza);
	}

	@Override
	public void organizaupdate(OrganizaDTO organiza) {
		// TODO Auto-generated method stub
		organizaDao.updateorganiza(organiza);
	}

	@Override
	public void organizaAdd(OrganizaDTO organiza) {
		// TODO Auto-generated method stub
		organizaDao.insertorganiza(organiza);
	}

	
	@Override
	public boolean organizadelete(String id) {
		//判断父机下面是否有子集如果有,先删除子节点
		List<OrganizaDTO> childPer = organizaDao.findChildPerm(id);
		if(null != childPer && childPer.size()>0) {
		   return false;
		    } 
		 if(organizaDao.deletByorganiza(id)>0){
			return true;
		  }else {
			return false;	
		  }
	   }

    @Override
    public void organizadeleteall() {
        organizaDao.organizadeleteall();
    }

	@Override
	public List<OrganVO> findOrganlist(OrganVO organVO) {
		// TODO Auto-generated method stub
		return organizaDao.findOrganlist(organVO);
	}

	@Override
	public List<OrganVO> selectUserOrgan(String userid) {
		// TODO Auto-generated method stub
	  return organizaDao.selectUserOrgan(userid);
	}
     /*
      * (non-Javadoc)
      * @see com.infoac.cas.service.OrganizaService#deleteRolePermission(java.lang.String)
      * 修改用户对应的组织结构时先清库
      */
	@Override
	public void deleteRolePermission(String userid) {
		// TODO Auto-generated method stub
		organizaDao.deleteRolePermission(userid);
	}

    @Override
    public void deleteRolePermissionall() {
        organizaDao.deleteRolePermissionall();
    }

    /*
      * (non-Javadoc)
      * @see com.infoac.cas.service.OrganizaService#saveroleUser(com.infoac.cas.dto.UserOrganKey)
      * 保存用户对应新的组织机构
      */
	@Override
	public void saveroleUser(UserOrganKey uok) {
		// TODO Auto-generated method stub
		organizaDao.saveroleUser(uok);
	}

	@Override
	public OrganizaDTO organFind(String organId) {
		// TODO Auto-generated method stub
		return organizaDao.organFind(organId);
	}

	@Override
	public void organizaListAdd(OrganizaDTO organizaList) {
		organizaDao.organizaListAdd(organizaList);
	}

	@Override
	public void saveOrganUserList(UserOrganKey userOrganList) {
		organizaDao.saveOrganUserList(userOrganList);
	}


}
