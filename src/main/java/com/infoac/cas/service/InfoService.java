package com.infoac.cas.service;

/**
 * @Author: SenjiePeng
 * @Description:
 * @Date: Created in  2019/7/9 11:30
 */

import com.infoac.cas.entity.Info;

/**
 * @describe 插入爬取的数据接口
 *
 */
public interface InfoService {
    /**
     * 插入爬取的数据
     */
    void insertInfo(Info info);
}

