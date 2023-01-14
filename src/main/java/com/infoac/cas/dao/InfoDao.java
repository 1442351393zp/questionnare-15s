package com.infoac.cas.dao;

import com.infoac.cas.entity.Info;
import org.apache.ibatis.annotations.Param;



/**
 * @Author: SenjiePeng
 * @Description:
 * @Date: Created in  2019/7/9 17:27
 */
public interface InfoDao {
    /**
     * 插入爬取的数据
     */
    Integer insertInfo(@Param("info")Info info);



}
