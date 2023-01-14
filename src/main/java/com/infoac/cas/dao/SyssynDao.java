package com.infoac.cas.dao;

import com.infoac.cas.entity.Syssyn;
import org.apache.ibatis.annotations.Param;

public interface SyssynDao {


    long getStartTime(@Param("name") String name);

    void updateStartTime(@Param("syn") Syssyn syn);


}
