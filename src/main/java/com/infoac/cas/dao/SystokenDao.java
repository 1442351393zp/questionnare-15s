package com.infoac.cas.dao;

import com.infoac.cas.entity.Systoken;
import org.apache.ibatis.annotations.Param;

public interface SystokenDao {
    String getAccessToken(@Param("name") String name);

    void updateAccessToken(@Param("systoken") Systoken systoken);

}
