package com.infoac.cas.service;


import com.infoac.cas.entity.Syssyn;

/**
 * 所,中软,组织用户同步修改数据增量时间
 */
public interface SyssynService {


    long getStartTime(String name);

    void updateStartTime(Syssyn syn);
}

