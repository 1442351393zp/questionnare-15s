package com.infoac.cas.service;

import com.infoac.cas.dto.*;

import java.util.List;
import java.util.Map;

public interface ReelService {

	/**
	 * 保存卷
	 * @param ReelDTO
	 */
	void reelSave(ReelDTO reelDTO);
	/**
	 * 保存卷
	 * @param PageDTO
	 */
	void pageSave(PageDTO pageDTO);
	
	void subjectSave(SubjectDTO subjectDTO);
	/**
	 * 保存题的选项
	 * @param optionsDTO
	 */
	void opntionsSave(OptionsDTO optionsDTO);
	/**
	 *根据id查询所有页
	 * @return
	 * @throws Exception
	 */
	List<AllSubjectDTO> queryReel(String rId);
	/**
	 *根据id查询所有题的数据
	 * @return
	 * @throws Exception
	 */
	List<AllSubjectDTO> queryAllSubject(String rId);
	List<AllSubjectDTO> queryAllReel(String status,String year);
	/**
	 *根据id查询所有页
	 * @return
	 * @throws Exception
	 */
	List<PageDTO> queryPage(String rId);
    /**
     *根据id查询所有页序号最大值
     * @return
     * @throws Exception
     */
	PageDTO queryMaxPage(String rId);
	/**
	 *根据页id查询所有题
	 * @return
	 * @throws Exception
	 */
	List<SubjectDTO> querySubject(String pageId);
    /**
     *根据页id查询当前页最大题
     * @return
     * @throws Exception
     */
	SubjectDTO queryMaxSubject(String pageId);
	/**
	 *根据题id查询所有选项
	 * @return
	 * @throws Exception
	 */
	List<OptionsDTO> queryOptions(String subjectId);
	/**
	 *查询所有卷
	 * @return
	 * @throws Exception
	 */
	List<AllSubjectDTO> queryList();
	/**
	 * 修改reel数据
	 * @param rId
	 */
	void updateReel(ReelDTO reelDTO); 
	AllSubjectDTO queryByRIdReel(String rId);
	/**
	 *根据pageid查询一条页的数据
	 * @return
	 * @throws Exception
	 */
	PageDTO queryPageByIdAndrId(String pageId);
	/**
	 *根据pageId删除一条页数据
	 * @return
	 * @throws Exception
	 */
	void deletePageById(String pageId);
	/**
	 *根据subjectId删除选项中的数据
	 * @return
	 * @throws Exception
	 */
	void deleteOptionsById(String subjectId);
	/**
	 *根据pageId删除题的数据
	 * @return
	 * @throws Exception
	 */
	void deleteSubjectById(String pageId);
	/**
	 *查出组装卷题
	 * @return
	 * @throws Exception
	 */
	AllSubjectDTO queryReelList(AllSubjectDTO reelVo, String reelId);
	AllSubjectDTO queryReelListRecord(AllSubjectDTO reelVo, String reelId, String recordId,String userId);
	/**
	 *查出组装卷题，用于统计
	 * @return
	 * @throws Exception
	 */
	AllSubjectDTO queryReelLists(AllSubjectDTO reelVo, String reelId, String userId,String startTime,String endTime,String subjectArr);
	/**
	 *根据rId删除卷的数据
	 * @return
	 * @throws Exception
	 */
	void deleteReelById(String rId);
	/**
	 *移动到垃圾篓
	 * @return
	 * @throws Exception
	 */
	void removeBasket(ReelDTO reelDTO);
	/**
	 *查询废纸篓数据
	 * @return
	 * @throws Exception
	 */
	List<FolderDTO> queryBasketList(String delFlag,String userId);
	/**
	 * 保存文件夹
	 * @param FolderDTO
	 */
	void folderSave(FolderDTO folderDTO);
	/**
	 *根据id查询废纸篓数据
	 * @return
	 * @throws Exception
	 */
	List<FolderDTO> queryByIdBasketList(String folderId);
	/**
	 *根据文件夹id查询卷数据
	 * @return
	 * @throws Exception
	 */
	List<AllSubjectDTO> queryByfolderIdReel(String folderId,String delFlag);
	/**
	 *根据文件夹id查询卷数据
	 * @return
	 * @throws Exception
	 */
	List<AllSubjectDTO> queryTrashBasketList(String userId);
	/**
	 *移出垃圾篓 恢复到正常
	 * @return
	 * @throws Exception
	 */
	void recoverBasket(ReelDTO reelDTO);
	/**
	 *根据rId删除页的数据
	 * @return
	 * @throws Exception
	 */
	void deletePageRId(String rId);
	/**
	 *根据rId查询所有页
	 * @return
	 * @throws Exception
	 */
	List<PageDTO> queryPageRId(String rId);
	/**
	 *根据folderId删除文件夹下所有的数据
	 * @return
	 * @throws Exception
	 */
	void deleteFolder(String folderId);
	/**
	 *根据folderId查询reel数据
	 * @return
	 * @throws Exception
	 */
	List<ReelDTO> queryReelfolderId(ReelDTO reelDTO);
	/**
	 *文件夹移动到垃圾篓
	 * @return
	 * @throws Exception
	 */
	void removeFolderBasket(FolderDTO folderDTO);
	/**
	 *查询已经删除的文件夹数据
	 * @return
	 * @throws Exception
	 */
	List<FolderDTO> queryRemoveFolderList(String userId);
	/**
	 *文件夹移出垃圾篓恢复正常
	 * @return
	 * @throws Exception
	 */
	void recoverFolderBasket(FolderDTO folderDTO);
	/**
	 * 单独保存题目
	 * @param ReelDTO
	 */
	void titleSave(ReelDTO reelDTO);
	/**
	 *修改标题
	 * @param rId
	 */
	void updateTitleReel(ReelDTO ReelDTO);
	/**
	 *修改开始语
	 * @param rId
	 */
	void updateRemarkReel(ReelDTO ReelDTO);
	/**
	 * 根据subjectId删除题数据
	 * @param subjectId
	 */
	void deleteSubjectId(String subjectId);
	/**
	 *根据subjectId查询一条题的数据
	 * @return
	 * @throws Exception
	 */
	SubjectDTO querySubjectId(String subjectId);
	/**
	 * 新增标题
	 * @param subjectId
	 */
	void reeTitlelSave(ReelDTO reelDTO);
	/**
	 * 查询一条卷的数据
	 * @param rId
	 * @return
	 */
	ReelDTO findReelByRid(String rId);
	/**
	 * 修改题
	 * @param subjectId
	 */
	void updateSubject(SubjectDTO subjectDTO);
    /**
     * 修改题
     * @param subjectId
     */
	void updateSubjectNum(SubjectDTO subjectDTO);
	void updatePageNum(PageDTO pageDTO);
	/**
	 * 新增视图状态
	 * @param subjectId
	 */
	void addViewStatus(ViewStatusDTO viewStatusDTO);
	/**
	 * 查询当前登录人用的什么视图
	 * @param userId,userName
	 */
	String queryViewStatus(String userId,String userName);
	
	/**
	 * 根据reelid删除options表数据
	 * @param reelId
	 */
	void deleteOptionsReelId(String reelId);
	/**
	 * 根据reelid删除subject表数据
	 * @param reelId
	 */
	void deleteSubjectReelId(String reelId);
	/**
	 * 根据reelid删除page表数据
	 * @param reelId
	 */
	void deletePageReelId(String reelId);
	/**
	 * 修改视图状态
	 * @param status
	 */
	void updateViewStatus(ViewStatusDTO viewStatusDTO);
	/**
	 *根据页reelId查询所有题
	 * @return
	 * @throws Exception
	 */
	List<SubjectDTO> querySubjectList(String reelId);
	/**
	 * 修改结束语
	 * @param status
	 */
	void updateEndLanguageReel(ReelDTO reelDTO);


	void updateBKsetting(BKsettingDTO bKsettingDTO);

	/**
	 * 列表页进入查询卷表数据和回收量
	 * @param rId
	 * @return
	 */
	List<AllSubjectDTO> queryListRecycle(String userId);
	/**
	 * 列表页和废纸篓抽屉单条数据查询卷表数据和回收量
	 * @param rId
	 * @return
	 */
	AllSubjectDTO queryByRIdReelRecycle(String rId);
	/**
	 *根据subjectId查询有哪几个选项
	 * @return
	 * @throws Exception
	 */
	List<OptionsDTO> querySubjectById(String subjectId);
	/**
	 *根据folderId查询rId
	 * @return
	 * @throws Exception
	 */
	List<String> queryReelId(String folderId);
	/**
	 *根据optionsId修改选项
	 * @return
	 * @throws Exception
	 */
	void opntionsUpdate(OptionsDTO optionsDTO);
	/**
	 *根据optionsId删除数据
	 * @return
	 * @throws Exception
	 */
	void deleteOptionsId(String optionsId);
	/**
	 * 查询0：正常文件夹所有文件夹的名字 1：垃圾篓中文件夹所有文件夹的名字
	 * @return
	 */
	List<FolderDTO> queryAllFolder(String delFlag,String userId);
	/**
	 * 将选中的卷移动到文件夹下
	 * @return
	 */
	void moveToFolder(Map<String, String> mp);
	/**
	 * 将文件夹下的问卷移出
	 * @return
	 */
	void shiftOutFolder(String rId);
	/**
	 * 将垃圾篓文件夹下的问卷移到正常列表中
	 * @return
	 */
	void shiftOutTrashBasketFolder(Map<String, String> mp);
	/**
	 * 文件夹下的问卷移动到垃圾篓
	 * @return
	 */
	void removeFolderToBasket(Map<String, String> mp);
	/**
	 * 根据reelid查询页和题数据
	 * @return
	 */
	List<SubjectDTO> queryPageAndSubect(String reelId);
	/**
	 * 统计报表 
	 * @return
	 */
	List<CountPicDTO> countPic(String reelId, String pageId, String subjectId, String startTime, String endTime);
	
	/**
	 * 统计报表筛选
	 * @return
	 */
	List<CountPicDTO> queryCompareCountPic(String reelId, String pageId, String subjectId, String optionsId);

	List<TextAnswerDTO> getTextAnswerList(Map<String, Object> map);

	/**
	 * 包含选项
	 * @param rId
	 * @param selectedSubjectId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    List<TextAnswerDTO> getIncludeAnswerList(String rId, String selectedSubjectId, String startTime, String endTime);

	/**
	 * 不包含选项
	 * @param rId
	 * @param selectedSubjectId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<TextAnswerDTO> getNoIncludeAnswerList(String rId, String selectedSubjectId, String startTime, String endTime);
}
