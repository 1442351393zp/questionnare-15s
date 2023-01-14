package com.infoac.cas.service.impl;

import com.infoac.cas.dao.ReelBrowseRecordDao;
import com.infoac.cas.dto.ReelBrowseRecordDTO;
import com.infoac.cas.service.IReelBrowseRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ReelBrowseRecordServiceImpl implements IReelBrowseRecordService{

    @Resource
    private ReelBrowseRecordDao reelBrowseRecordDao;

    @Override
    public int save(ReelBrowseRecordDTO reelBrowseRecordDto) {
        return reelBrowseRecordDao.save(reelBrowseRecordDto);
    }
}
