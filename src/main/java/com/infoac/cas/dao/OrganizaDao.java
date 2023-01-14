package com.infoac.cas.dao;

import com.infoac.cas.dto.OrganVO;
import com.infoac.cas.dto.OrganizaDTO;
import com.infoac.cas.dto.UserOrganKey;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedList;
import java.util.List;

public interface OrganizaDao {

	List<OrganizaDTO> findrolelist(OrganizaDTO organiza);

	void updateorganiza(OrganizaDTO organiza);

	void insertorganiza(OrganizaDTO organiza);

	List<OrganizaDTO> findChildPerm(String id);

	int deletByorganiza(String id);
	void organizadeleteall();

	List<OrganVO> findOrganlist(OrganVO organVO);

	List<OrganVO> selectUserOrgan(String userid);

	void deleteRolePermission(String userid);
	void deleteRolePermissionall();

	void saveroleUser(UserOrganKey uok);

	OrganizaDTO organFind(String organId);

	void organizaListAdd(OrganizaDTO list);

	void saveOrganUserList(UserOrganKey list);
}
