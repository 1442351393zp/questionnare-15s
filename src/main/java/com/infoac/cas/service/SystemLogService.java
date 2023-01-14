package com.infoac.cas.service;

import java.util.List;

import com.infoac.cas.dto.SystemLog;

public interface SystemLogService {

	int insert(SystemLog log);

	List<SystemLog> findbyLoglist(SystemLog systemLog);

	Long findbyLog(SystemLog systemLog);

}
