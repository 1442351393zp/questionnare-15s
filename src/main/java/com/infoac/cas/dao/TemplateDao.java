package com.infoac.cas.dao;

import java.util.List;

import com.infoac.cas.dto.*;
import com.infoac.cas.entity.ReelVO;
import org.apache.ibatis.annotations.Param;

public interface TemplateDao {

	List<TemplateDTO> templatelist();

	void updateTemplate(TemplateDTO template);

	void insertTemplate(TemplateDTO template);

	TemplateDTO selectTemplate(String typeid);
    //查看是否有子节点
	List<TemplateDTO> findChildTemplate(String typeid);
    //删除子节点数据
	int deletByTemplate(String typeid);

	List<TemplateVO> templatelists();

    List<ReelVO> templateLists(ReelVO reelVo);

	List<SubjectDTO> subjectlist(String reelId);

    void svaeTemple(ReelDTO reelDTO);

    ReelDTO addTempldateReel(String reelid);

	void addReel(ReelDTO reel);

	List<PageDTO> selectPageTempleate(String reelid);

	void addPageTempleate(PageDTO page);

	List<SubjectDTO> selectSubjectTemplate(@Param("reelid")String reelid, @Param("pageid")String pageid);

	void addSubjectTemplate(SubjectDTO subject);

	List<OptionsDTO> selectOptionsTemplate(@Param("reelid")String reelid,@Param("subjectid")String subjectid);

	void addOptionsTemplate(OptionsDTO options);

	List<PageDTO> selectNewPage(String rid);

	Long selectTemplateCount(ReelVO reelVo);

	List<ReelVO> templateName(ReelVO reelVo);
}
