package com.infoac.cas.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.infoac.cas.dao.AnswerDao;
import com.infoac.cas.dao.ReelDao;
import com.infoac.cas.dto.*;
import com.infoac.cas.entity.ReelVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoac.cas.dao.TemplateDao;
import com.infoac.cas.service.TemplateService;


@Service
public class TemplateServiceImpl implements TemplateService{
	@Autowired 
	private  TemplateDao  templateDao;
	@Autowired
	private ReelDao reelDao;
	@Autowired
	private AnswerDao answerDao;

	@Override
	public List<TemplateDTO> templatelist() {
		// TODO Auto-generated method stub
		return templateDao.templatelist();
	}

	@Override
	public void updateTemplate(TemplateDTO template) {
		// TODO Auto-generated method stub
		templateDao.updateTemplate(template);
		
	}

	@Override
	public void addperTemplate(TemplateDTO template) {
		// TODO Auto-generated method stub
		templateDao.insertTemplate(template);
		
		
	}

	@Override
	public TemplateDTO findTemplate(String typeid) {
		// TODO Auto-generated method stub
		return templateDao.selectTemplate(typeid);
	}

	@Override
	public boolean deleteTemplate(String typeid) {
		//查看是否有子节点
		List<TemplateDTO> childPer = templateDao.findChildTemplate(typeid);
		if(null != childPer && childPer.size()>0) {
		   return false;
		  
		} 
		//没有子节点时删除数据
		if(templateDao.deletByTemplate(typeid)>0) {
			return true;
		}else {
			return false;	
		}
	}
    
	@Override
	//查询所有的模板数据
	public List<TemplateVO> templatelists() {
		// TODO Auto-generated method stub
		return templateDao.templatelists();
	}

	@Override
	public List<ReelVO> templateLists(ReelVO reelVo) {
		return templateDao.templateLists(reelVo);
	}

	@Override
	public List<SubjectDTO> subjectlist(String reelId) {
		return templateDao.subjectlist(reelId);
	}

    @Override
    public void svaeTemple(ReelDTO reelDTO) {
         templateDao.svaeTemple(reelDTO);
    }

	@Override
	public ReelDTO addTempldateReel(String reelid) {
		return templateDao.addTempldateReel(reelid);
	}

	@Override
	public void addReel(ReelDTO reel) {
		templateDao.addReel(reel);
	}

	@Override
	public List<PageDTO> selectPageTempleate(String reelid) {
		return templateDao.selectPageTempleate(reelid);
	}

	@Override
	public void addPageTempleate(PageDTO page) {
		templateDao.addPageTempleate(page);
	}

	@Override
	public List<SubjectDTO> selectSubjectTemplate(String reelid,String pageid) {
		return templateDao.selectSubjectTemplate(reelid,pageid);
	}

	@Override
	public void addSubjectTemplate(SubjectDTO subject) {
		templateDao.addSubjectTemplate(subject);
	}

	@Override
	public List<OptionsDTO> selectOptionsTemplate(String reelid,String pageID) {
		return templateDao.selectOptionsTemplate(reelid,pageID);
	}

	@Override
	public void addOptionsTemplate(OptionsDTO options) {
		templateDao.addOptionsTemplate(options);
	}

	@Override
	public List<PageDTO> selectNewPage(String rid) {
		return templateDao.selectNewPage(rid);
	}

	@Override
	public Long findTemplateCount(ReelVO reelVo) {
		return templateDao.selectTemplateCount(reelVo);
	}

	@Override
	public AllSubjectDTO queryReelLists(AllSubjectDTO reelVo, String reelId, String userId, String startTime, String endTime) {
		int z = 1;
		String pageId = "";
		String subId = "";
		String subjectId = "";
		String optionsId="";
		PageDTO pageVo = null;
		SubjectDTO subjectV = null;
		SubjectDTO subjectVo = null;
		OptionsDTO optionsVo = null;
		List<SubjectDTO> newSubjectList = null;
		List<OptionsDTO> newOptionsList = null;
		ReelDTO reel = reelDao.findReelByRid(reelId);
		reelId = reel.getrId();
		String title = reel.getTitle();
		reelVo.setrId(reelId);
		reelVo.setTitle(title);
		String createTime = reel.getCreateTime().substring(0,reel.getCreateTime().indexOf(" "));
		reelVo.setCreateTime(createTime);
		if(StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)) {
			startTime = createTime;
			endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		reelVo.setStartTime(startTime);
		reelVo.setEndsTime(endTime);
		List<PageDTO> pageList = reelDao.queryPageList(reelId);             //页
		List<SubjectDTO> subjectList = reelDao.querySubjectList(reelId);    //题
		List<OptionsDTO> optionsList = reelDao.queryOptionsList(reelId);    //选项


		for(int i = 0;i < subjectList.size();i++) {
			subjectVo = subjectList.get(i);
			subId = subjectVo.getSubjectId();

		}
		for (int i = 0; i < pageList.size(); i++) {
			pageVo = pageList.get(i);
			pageId = pageVo.getPageId();
			newSubjectList = new ArrayList<>();

			for(int j = 0;j < subjectList.size();j++) {
				subjectV = subjectList.get(j);
				if(pageId.equals(subjectV.getPageId())) {
					subjectV.setSubNo(String.valueOf(z) + ".");
					newSubjectList.add(subjectV);
					z++;
				}
				pageVo.setSubjectList(newSubjectList);
			}
		}
		reelVo.setPageList(pageList);
		reelVo.setPageMaxNo(pageList.size());
		return reelVo;
	}

	@Override
	public List<ReelVO> templateName(ReelVO reelVo) {
		return templateDao.templateName(reelVo);
	}


}
