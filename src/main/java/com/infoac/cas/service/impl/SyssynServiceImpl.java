package com.infoac.cas.service.impl;

import com.infoac.cas.dao.SyssynDao;
import com.infoac.cas.entity.Syssyn;
import com.infoac.cas.service.SyssynService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SyssynServiceImpl implements SyssynService {

    @Autowired
    private SyssynDao syssynDao;

    @Override
    public long getStartTime(String name) {
        return syssynDao.getStartTime(name);
    }

    @Override
    public void updateStartTime(Syssyn syn) {
        syssynDao.updateStartTime(syn);
    }
}
