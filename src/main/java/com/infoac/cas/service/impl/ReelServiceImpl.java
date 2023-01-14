package com.infoac.cas.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.infoac.cas.dao.AnswerDao;
import com.infoac.cas.dao.ReelDao;
import com.infoac.cas.dto.*;
import com.infoac.cas.service.ReelService;
import com.infoac.cas.util.DateUtil;
import com.infoac.cas.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
@Service
public class ReelServiceImpl implements ReelService{
	
	@Autowired
    private ReelDao reelDao;
	@Autowired
    private AnswerDao answerDao;
	
	
	@Override
	public void reelSave(ReelDTO reelDTO) {
		reelDao.reelSave(reelDTO);
	}
	@Override
	public void pageSave(PageDTO pageDTO) {
		reelDao.pageSave(pageDTO);
	}
	@Override
	public void subjectSave(SubjectDTO subjectDTO) {
		reelDao.subjectSave(subjectDTO);
	}
	@Override
	public void opntionsSave(OptionsDTO optionsDTO) {
		reelDao.opntionsSave(optionsDTO);
		
	}

	
	@Override
	public List<AllSubjectDTO> queryAllSubject(String rId) {
		return reelDao.queryAllSubject(rId);
	}
    @Override
    public List<AllSubjectDTO> queryAllReel(String status,String year) {
        return reelDao.queryAllReel(status,year);
    }

	@Override
	public List<AllSubjectDTO> queryReel(String rId) {
		return reelDao.queryReel(rId);
	}

	@Override
	public List<PageDTO> queryPage(String rId) {
		return reelDao.queryPage(rId);
	}

    @Override
    public PageDTO queryMaxPage(String rId) {
        return reelDao.queryMaxPage(rId);
    }

	@Override
	public List<SubjectDTO> querySubject(String pageId) {
		return reelDao.querySubject(pageId);
	}

    @Override
    public SubjectDTO queryMaxSubject(String pageId) {
        return reelDao.queryMaxSubject(pageId);
    }

	@Override
	public List<OptionsDTO> queryOptions(String subjectId) {
		return reelDao.queryOptions(subjectId);
	}

	@Override
	public List<AllSubjectDTO> queryList() {
		return reelDao.queryList();
	}

	@Override
	public void updateReel(ReelDTO reelDTO) {
		reelDao.updateReel(reelDTO);
	}

	@Override
	public AllSubjectDTO queryByRIdReel(String rId) {
		return reelDao.queryByRIdReel(rId);
	}

	@Override
	public PageDTO queryPageByIdAndrId(String pageId) {
		return reelDao.queryPageByIdAndrId(pageId);
	}

	@Override
	public void deletePageById(String pageId) {
		reelDao.deletePageById(pageId);
	}

	@Override
	public void deleteOptionsById(String subjectId) {
		reelDao.deleteOptionsById(subjectId);	
	}

	
	@Override
	public void deleteSubjectById(String pageId) {
		reelDao.deleteSubjectById(pageId);
		
	}
	/**
	 * 组合问卷数据
	 */
	@Override
	public AllSubjectDTO queryReelList(AllSubjectDTO reelVo, String reelId) {
		int z = 1;
		String pageId = "";
		String subId = "";
		String subjectId = "";
		PageDTO pageVo = null;
		SubjectDTO subjectV = null;
		SubjectDTO subjectVo = null;
		OptionsDTO optionsVo = null;
		List<SubjectDTO> newSubjectList = null;
		List<OptionsDTO> newOptionsList = null;
		
		List<PageDTO> pageList = reelDao.queryPageList(reelId);             //页
		List<SubjectDTO> subjectList = reelDao.querySubjectList(reelId);    //题
		List<OptionsDTO> optionsList = reelDao.queryOptionsList(reelId);    //选项
		
		for(int i = 0;i < subjectList.size();i++) {
			subjectVo = subjectList.get(i);
			subId = subjectVo.getSubjectId();
			newOptionsList = new ArrayList<>();
            for(int j = 0;j < optionsList.size();j++) {
            	optionsVo = optionsList.get(j);
            	subjectId = optionsVo.getSubjectId();
            	if(subjectId.equals(subId)) {
            		newOptionsList.add(optionsVo);
            	}
			}
            subjectVo.setOptionsList(newOptionsList);
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


    /**
     * 组合问卷数据,有答题记录的
     */
    @Override
    public AllSubjectDTO queryReelListRecord(AllSubjectDTO reelVo, String reelId, String recordId,String userid) {
        int z = 1;
        String pageId = "";
        String subId = "";
        String subjectId = "";
        PageDTO pageVo = null;
        SubjectDTO subjectV = null;
        List<SubjectDTO> newSubjectList = null;
        List<OptionsDTO> newOptionsList = null;

        List<PageDTO> pageList = reelDao.queryPageList(reelId);//页
        List<SubjectDTO> subjectList = reelDao.querySubjectListRecord(reelId,"1",recordId,userid);//题
        List<OptionsDTO> optionsList = reelDao.queryOptionsListRecord(reelId,"2",recordId,userid);//选项

        for(int i = 0;i < subjectList.size();i++) {
			SubjectDTO subjectVo = subjectList.get(i);
            String content = subjectVo.getContent();
            if(StringUtils.isNotBlank(content)){
                List<String> strings = JSON.parseArray(content, String.class);
                String newtopic = StringUtil.gapText(subjectVo.getTopic(),strings);
                subjectVo.setTopic(newtopic);
            }
            subId = subjectVo.getSubjectId();
            newOptionsList = new ArrayList<>();
            for(int j = 0;j < optionsList.size();j++) {
				OptionsDTO optionsVo = optionsList.get(j);
                subjectId = optionsVo.getSubjectId();
                if(subjectId.equals(subId)) {
					String content1 = optionsVo.getContent();
					if(StringUtils.isNotBlank(content1)){
						List<String> strings1 = JSON.parseArray(content1, String.class);
						String newtopic1 = StringUtil.gapText(optionsVo.getOptions(),strings1);
						optionsVo.setOptions(newtopic1);
					}
                    newOptionsList.add(optionsVo);
                }
            }
            subjectVo.setOptionsList(newOptionsList);
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
	
	
	/**
	 * 组合问卷数据
	 */
	@Override
	public AllSubjectDTO queryReelLists(AllSubjectDTO reelVo, String reelId,String userId, String startTime,String endTime,String subjectArr) {
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
            String topic = subjectVo.getTopic();
            //去掉html标签
            topic = topic.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "")
                    .replaceAll("</[a-zA-Z]+[1-9]?[^><]*>", "");
            subjectVo.setTopic(topic);
            subId = subjectVo.getSubjectId();
			newOptionsList = new ArrayList<>();
            for(int j = 0;j < optionsList.size();j++) {
            	optionsVo = optionsList.get(j);
            	subjectId = optionsVo.getSubjectId();
            	optionsId = optionsVo.getOptionsId();
                String options = optionsVo.getOptions();
                //去掉html标签
                options = options.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "")
                        .replaceAll("</[a-zA-Z]+[1-9]?[^><]*>", "");
                optionsVo.setOptions(options);
            	if(subjectArr != null) {
            		JSONArray jsonArray = JSONArray.parseArray(subjectArr);
            		JSONObject jsonObj =new JSONObject();
                	for(int f=0;f<jsonArray.size();f++) {
                		jsonObj = (JSONObject) jsonArray.get(f);
                		//String selectedSubjectId = jsonObj.getString("subjectId");
                		String selectedInclude = jsonObj.getString("include");
                		String selectedOptionsId = jsonObj.getString("optionsId");
                	
            		if(selectedOptionsId.equals(optionsId) && selectedInclude.equals("1")) {
                        Map<String,Object> map = new HashMap<>();
                 		List<Integer> years = DateUtil.getYearList();
                 		map.put("optionsId", selectedOptionsId);
                 		map.put("subjectId", subjectId);
                 		map.put("years", years);
                 		map.put("startTime", startTime);
                 		map.put("endTime", endTime);
                 		OptionsDTO optionsDTO = answerDao.queryAnswerOptionsId(map);
                 		if(optionsDTO != null) {
                 			optionsVo.setOptionsId(optionsDTO.getOptionsId());
                     		optionsVo.setCountNum(optionsDTO.getCountNum());
                     		optionsVo.setPercentage(optionsDTO.getPercentage());
                 		}else {
                 			optionsVo.setCountNum("0");
                     		optionsVo.setPercentage("0");
                 		}
                 		
                    	if(subjectId.equals(subId)) {
                    		newOptionsList.add(optionsVo);
                    	}
            		  }
                	}
            	}else {
            		Map<String,Object> map = new HashMap<>();
             		List<Integer> years = DateUtil.getYearList();
             		map.put("optionsId", optionsId);
             		map.put("subjectId", subjectId);
             		map.put("years", years);
             		map.put("startTime", startTime);
             		map.put("endTime", endTime);
             		OptionsDTO optionsDTO = answerDao.queryAnswerOptionsId(map);
             		if(optionsDTO != null) {
             			optionsVo.setOptionsId(optionsDTO.getOptionsId());
                 		optionsVo.setCountNum(optionsDTO.getCountNum());
                 		optionsVo.setPercentage(optionsDTO.getPercentage());
             		}else {
             			optionsVo.setCountNum("0");
                 		optionsVo.setPercentage("0");
             		}
             		
                	if(subjectId.equals(subId)) {
                		newOptionsList.add(optionsVo);
                	}
            	}
			}
            subjectVo.setOptionsList(newOptionsList);
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
	public void removeBasket(ReelDTO reelDTO) {
		reelDao.removeBasket(reelDTO);
	}

	@Override
	public List<FolderDTO> queryBasketList(String delFlag,String userId) {
		return reelDao.queryBasketList(delFlag,userId);
	}

	@Override
	public void folderSave(FolderDTO folderDTO) {
		reelDao.folderSave(folderDTO);
	}

	@Override
	public List<FolderDTO> queryByIdBasketList(String folderId) {
		return reelDao.queryByIdBasketList(folderId);
	}
	@Override
	public List<AllSubjectDTO> queryByfolderIdReel(String folderId,String delFlag) {
		Map<String,Object> map = new HashMap<>();
 		List<Integer> years = DateUtil.getYearList();
 		map.put("years", years);
 		map.put("folderId", folderId);
 		map.put("delFlag", delFlag);
		return reelDao.queryByfolderIdReel(map);
	}

	@Override
	public List<AllSubjectDTO> queryTrashBasketList(String userId) {
		Map<String,Object> map = new HashMap<>();
 		List<Integer> years = DateUtil.getYearList();
 		//map.put("years", years);
 		map.put("userId", userId);
		return reelDao.queryTrashBasketList(map);
	}

	@Override
	public void recoverBasket(ReelDTO reelDTO) {
		reelDao.recoverBasket(reelDTO);
	}

	@Override
	public void deleteReelById(String rId) {
		reelDao.deleteReelById(rId);
		
	}
	@Override
	public void deletePageRId(String rId) {
		reelDao.deletePageRId(rId);
		
	}

	@Override
	public List<PageDTO> queryPageRId(String rId) {
		return reelDao.queryPageRId(rId);
	}

	@Override
	public void deleteFolder(String folderId) {
		reelDao.deleteFolder(folderId);
	}

	@Override
	public List<ReelDTO> queryReelfolderId(ReelDTO reelDTO) {
		return reelDao.queryReelfolderId(reelDTO);
	}

	@Override
	public void removeFolderBasket(FolderDTO folderDTO) {
		reelDao.removeFolderBasket(folderDTO);
	}

	@Override
	public List<FolderDTO> queryRemoveFolderList(String userId) {
		return reelDao.queryRemoveFolderList(userId);
	}
	
	@Override
	public void recoverFolderBasket(FolderDTO folderDTO) {
		reelDao.recoverFolderBasket(folderDTO);
		
	}
	
	@Override
	public void titleSave(ReelDTO reelDTO) {
		reelDao.titleSave(reelDTO);
	}
	
	@Override
	public void updateTitleReel(ReelDTO ReelDTO) {
		reelDao.updateTitleReel(ReelDTO);
		
	}
	
	@Override
	public void deleteSubjectId(String subjectId) {
		reelDao.deleteSubjectId(subjectId);
	}
	@Override
	public SubjectDTO querySubjectId(String subjectId) {
		return reelDao.querySubjectId(subjectId);
	}
	@Override
	public void reeTitlelSave(ReelDTO reelDTO) {
		reelDao.reeTitlelSave(reelDTO);
	}
	
	@Override
	public void updateRemarkReel(ReelDTO ReelDTO) {
		reelDao.updateRemarkReel(ReelDTO);
	}
	@Override
	public ReelDTO findReelByRid(String rId) {
		return reelDao.findReelByRid(rId);
	}
	@Override
	public void updateSubject(SubjectDTO subjectDTO) {
		reelDao.updateSubject(subjectDTO);
	}

    @Override
    public void updateSubjectNum(SubjectDTO subjectDTO) {
        reelDao.updateSubjectNum(subjectDTO);
    }

    @Override
    public void updatePageNum(PageDTO pageDTO) {
        reelDao.updatePageNum(pageDTO);
    }

    @Override
	public void addViewStatus(ViewStatusDTO viewStatusDTO) {
		reelDao.addViewStatus(viewStatusDTO);
	}
	
	@Override
	public String queryViewStatus(String userId,String userName) {
		String status = reelDao.queryViewStatus(userId);
		if(StringUtils.isBlank(status)) {//当为空时，还没查询使用时什么视图，就默认给列表视图
			ViewStatusDTO viewStatusDTO = new ViewStatusDTO();
			String viewId = DateUtil.getCommentId();
			viewStatusDTO.setViewId(viewId);
			viewStatusDTO.setStatus("0");//0是列表视图 1是九宫格视图
			viewStatusDTO.setUserId(userId);
			viewStatusDTO.setUserName(userName);
			viewStatusDTO.setCreateTime(DateUtil.getTime());
			reelDao.addViewStatus(viewStatusDTO);
			status = "0";
		}
		return status;
	}
	
	@Override
	public void deleteOptionsReelId(String reelId) {
		 reelDao.deleteOptionsReelId(reelId);
	}
	
	@Override
	public void deleteSubjectReelId(String reelId) {
		 reelDao.deleteSubjectReelId(reelId);
	}
	
	@Override
	public void deletePageReelId(String reelId) {
		 reelDao.deletePageReelId(reelId);
	}
	
	@Override
	public void updateViewStatus(ViewStatusDTO viewStatusDTO) {
		reelDao.updateViewStatus(viewStatusDTO);
	}
	@Override
	public List<SubjectDTO> querySubjectList(String reelId) {
		return reelDao.querySubjectList(reelId);
	}
	
	@Override
	public void updateEndLanguageReel(ReelDTO reelDTO) {
		reelDao.updateEndLanguageReel(reelDTO);
		
	}


    @Override
    public void updateBKsetting(BKsettingDTO bKsettingDTO) {
        reelDao.updateBKsetting(bKsettingDTO);

    }
	
	@Override
	public List<AllSubjectDTO> queryListRecycle(String userId) {
		Map<String,Object> map = new HashMap<>();
 		List<Integer> years = DateUtil.getYearList();
 		//map.put("years", years);
 		map.put("userId", userId);
		return reelDao.queryListRecycle(map);
	}
	@Override
	public AllSubjectDTO queryByRIdReelRecycle(String rId) {
		Map<String,Object> map = new HashMap<>();
 		List<Integer> years = DateUtil.getYearList();
 		map.put("years", years);
 		map.put("rId", rId);
		return reelDao.queryByRIdReelRecycle(map);
	}
	@Override
	public List<OptionsDTO> querySubjectById(String subjectId) {
		return reelDao.querySubjectById(subjectId);
	}
	
	@Override
	public List<String> queryReelId(String folderId) {
		return reelDao.queryReelId(folderId);
	}
	
	@Override
	public void opntionsUpdate(OptionsDTO optionsDTO) {
		reelDao.opntionsUpdate(optionsDTO);
	}
	
	@Override
	public void deleteOptionsId(String optionsId) {
		reelDao.deleteOptionsId(optionsId);
	}
	
	@Override
	public List<FolderDTO> queryAllFolder(String delFlag,String userId) {
		return reelDao.queryAllFolder(delFlag,userId);
	}
	
	@Override
	public void moveToFolder(Map<String, String> mp) {
		reelDao.moveToFolder(mp);
	}
	
	@Override
	public void shiftOutFolder(String rId) {
		reelDao.shiftOutFolder(rId);
	}

	@Override
	public void shiftOutTrashBasketFolder(Map<String, String> mp) {
		reelDao.shiftOutTrashBasketFolder(mp);
		
	}
	@Override
	public void removeFolderToBasket(Map<String, String> mp) {
		reelDao.removeFolderToBasket(mp);
	}
	
	@Override
	public List<SubjectDTO> queryPageAndSubect(String reelId) {
		return reelDao.queryPageAndSubect(reelId);
	}
	
	@Override
	public List<CountPicDTO> countPic(String reelId, String pageId, String subjectId,String startTime,String endTime) {
		Map<String,Object> map = new HashMap<>();
 		List<Integer> years = DateUtil.getYearList();
 		map.put("years", years);
 		map.put("reelId", reelId);
 		map.put("pageId", pageId);
 		map.put("subjectId", subjectId);
 		map.put("startTime", startTime);
 		map.put("endTime", endTime);
		return reelDao.countPic(map);
	}

	@Override
	public List<CountPicDTO> queryCompareCountPic(String reelId, String pageId, String subjectId,String optionsId) {
		Map<String,Object> map = new HashMap<>();
 		List<Integer> years = DateUtil.getYearList();
 		map.put("years", years);
 		map.put("reelId", reelId);
 		map.put("pageId", pageId);
 		map.put("subjectId", subjectId);
 		map.put("optionsId", optionsId);
		return reelDao.queryCompareCountPic(map);
	}

	/**
	 * 获取所有的文本内容
	 * @param subjectId
	 * @param options
	 */
	@Override
	public List<TextAnswerDTO> getTextAnswerList(Map<String, Object> map) {
		return reelDao.getTextAnswerList(map);
	}

    /**
     * 包含
     * @param rId
     * @param selectedSubjectId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<TextAnswerDTO> getIncludeAnswerList(String rId, String selectedSubjectId, String startTime, String endTime) {
        Map<String,Object> map = new HashMap<>();
        List<Integer> years = DateUtil.getYearList();
        map.put("years", years);
        map.put("rId", rId);
        map.put("subjectId", selectedSubjectId);
        map.put("subjectId", startTime);
        map.put("subjectId", endTime);
        return reelDao.getIncludeAnswerList(map);
    }

    /**
     * 不包含
     * @param rId
     * @param selectedSubjectId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<TextAnswerDTO> getNoIncludeAnswerList(String rId, String selectedSubjectId, String startTime, String endTime) {
        Map<String,Object> map = new HashMap<>();
        List<Integer> years = DateUtil.getYearList();
        map.put("years", years);
        map.put("rId", rId);
        map.put("subjectId", selectedSubjectId);
        map.put("subjectId", startTime);
        map.put("subjectId", endTime);
        return answerDao.getNoIncludeAnswerList(map);
    }


}
