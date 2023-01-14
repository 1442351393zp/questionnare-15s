package com.infoac.cas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoac.cas.dao.SystemLogDao;
import com.infoac.cas.dto.SystemLog;
import com.infoac.cas.service.SystemLogService;

@Service
public class SystemLogServiceImpl implements SystemLogService {
	@Autowired
	private SystemLogDao SystemLogDao;
	
	@Override
	public int insert(SystemLog log) {
		// TODO Auto-generated method stub
		return SystemLogDao.insert(log);
	}

	@Override
	public List<SystemLog> findbyLoglist(SystemLog systemLog) {
		// TODO Auto-generated method stub
		return SystemLogDao.findbyLoglist(systemLog);
	}

	@Override
	public Long findbyLog(SystemLog systemLog) {
		
		return SystemLogDao.findbyLog(systemLog);
	}

}
