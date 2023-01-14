package com.infoac.cas.service.impl;

import com.infoac.cas.dao.SystokenDao;
import com.infoac.cas.entity.Systoken;
import com.infoac.cas.service.SystokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystokenServiceImpl implements SystokenService {

    @Autowired
    private SystokenDao systokenDao;

    @Override
    public String getAccessToken(String name) {
        return systokenDao.getAccessToken(name);
    }

    @Override
    public void updateAccessToken(Systoken systoken) {
        systokenDao.updateAccessToken(systoken);
    }

}
