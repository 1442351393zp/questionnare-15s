package com.infoac.cas.dao;

import com.infoac.cas.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionDao {

	void updateReel(@Param("reel")ReelDTO reel);

    ReelDTO findReelSetup(@Param("rId")String rId);

    void updateReelCanalText(@Param("rId")String rId);

    List<String> selectUsers();

    String selectuserids(String rid);

    List<String> selectUserList(String rid);

    List<QuestionStateDTO> getQuestionAllperope(String rid);

    List<QuestionStateDTO> getAnsweredPeoper(String rId);

    List<QuestionStateDTO> getNoQuestionAllperope();
    String getReelUserId(String rId);
    List<OrganVO> getOrganFather(String id);
    List<OrganVO> getorganList(String id);

}
