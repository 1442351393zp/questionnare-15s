package com.infoac.cas.service;

import com.infoac.cas.dto.OrganVO;
import com.infoac.cas.dto.OrganizaDTO;
import com.infoac.cas.dto.UserOrganKey;

import java.util.LinkedList;
import java.util.List;

public interface OrganizaService {

	List<OrganizaDTO> findrolelist(OrganizaDTO organiza);

	void organizaupdate(OrganizaDTO organiza);

	void organizaAdd(OrganizaDTO organiza);

	boolean organizadelete(String id);
    void organizadeleteall();

	List<OrganVO> findOrganlist(OrganVO organVO);

	List<OrganVO> selectUserOrgan(String userid);

	void deleteRolePermission(String userid);
	void deleteRolePermissionall();

	void saveroleUser(UserOrganKey uok);

	OrganizaDTO organFind(String organId);

	/**
	 * 写入15所的组织结构信息
	 * @param organizaList
	 */
	void organizaListAdd(OrganizaDTO organizaList);

	/**
	 * 保存15所用户和组织机构的数据
	 * @param userOrganList
	 */
	void saveOrganUserList(UserOrganKey userOrganList);
}
