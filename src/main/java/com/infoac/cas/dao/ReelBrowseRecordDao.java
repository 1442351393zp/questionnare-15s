package com.infoac.cas.dao;

import com.infoac.cas.dto.ReelBrowseRecordDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author: Mr.Xu
* @Date: 2019/12/30 15:20
* @Description 问卷浏览记录
*/
@Repository
public interface ReelBrowseRecordDao{

    Integer save(@Param(value = "browse") ReelBrowseRecordDTO reelBrowseRecord);

    Integer getBrowseCount(@Param("reelId") String reelId);

}
