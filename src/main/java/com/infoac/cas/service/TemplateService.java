package com.infoac.cas.service;

import java.util.List;

import com.infoac.cas.dto.*;
import com.infoac.cas.entity.ReelVO;

public interface TemplateService {

	List<TemplateDTO> templatelist();

	void updateTemplate(TemplateDTO template);

	void addperTemplate(TemplateDTO template);

	TemplateDTO findTemplate(String typeid);

	boolean deleteTemplate(String typeid);

	List<TemplateVO> templatelists();

	List<ReelVO> templateLists(ReelVO reelVo);

	List<SubjectDTO> subjectlist(String reelId);

    void svaeTemple(ReelDTO reelDTO);

    ReelDTO addTempldateReel(String reelid);

	void addReel(ReelDTO reel);

	List<PageDTO> selectPageTempleate(String reelid);

	void addPageTempleate(PageDTO page);

	List<SubjectDTO> selectSubjectTemplate(String reelid,String pageid);

	void addSubjectTemplate(SubjectDTO subject);

	List<OptionsDTO> selectOptionsTemplate(String reelid,String pageid);

	void addOptionsTemplate(OptionsDTO options);

    List<PageDTO> selectNewPage(String rid);

	Long findTemplateCount(ReelVO reelVo);

    AllSubjectDTO queryReelLists(AllSubjectDTO reelVo, String reelId, String userId, String startTime, String endTime);

    List<ReelVO> templateName(ReelVO reelVo);
}
