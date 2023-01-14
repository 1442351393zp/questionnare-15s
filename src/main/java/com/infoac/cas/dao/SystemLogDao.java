package com.infoac.cas.dao;

import java.util.List;

import com.infoac.cas.dto.SystemLog;

public interface SystemLogDao {

	int insert(SystemLog log);

	List<SystemLog> findbyLoglist(SystemLog systemLog);

	Long findbyLog(SystemLog systemLog);

}
